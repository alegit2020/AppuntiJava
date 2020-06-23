import java.sql.*;
import java.io.*;

/*
 * PrenotaCinema.java
 *
 * Il primo che esegue questa applicazione mette in attesa
 * altre istanza di questa stessa applicazione che vogliono 
 * prenotare posti nella stessa sala, finché non conferma 
 * o disdice la prenotazione tramite tastiera
 *	
 */
 
public class PrenotaCinema {

	private static OperazioniCinema oc;
	
	/** 
	* prenota() 		prenota il numero di posti indicati in input nella sala richiesta
	*
	* parametri        sala
	*					posti
	*					nome	nome a cui effettuare la prenotazione
	*
	* eccezioni		SQLException	riscontrando problemi ralativi al database
	*/
	static public void prenota(String sala, int posti, String nome) throws SQLException {
		// questo flag indica se ri-eseguire, in caso di deadlock, la transazione
		boolean riprova=false;	
		do {
			if (oc.verificaDisponibilità(sala,posti)) {
				try {
					System.out.println("Cinema "+sala);
					System.out.println("Prenotazione a nome di "+nome);
					// inserisco la prenotazione 
					oc.aggiornaRegistro(sala,posti,nome);
					oc.effettuaPrenotazione(sala,posti,nome);
					//chiedo se confermare o meno la prenotazione
					if(confermaPrenotazione()) {
						// commit
						oc.commit();
						System.out.println("Posti prenotati!");
						riprova=false;
					}
					else {
						// rollback
						oc.rollback();
						System.out.println("Nessun posto prenotato");
						riprova=false;
					}
				}
				catch (SQLException sqle) {
					if(sqle.getErrorCode()==335544336) {
						System.out.println("Si e' venuta a creare una anomalia write-write");
						System.out.println("Ritento");
						riprova=true;
						oc.rollback();
					}
					else throw sqle;
				}
			}
			else {
				System.out.println("Non ci sono abbastanza posti disponibili al cinema "+sala);
				riprova=false;
			}                
	    } while(riprova==true);
	}
	

	// Richiede all'utente di confermare la prenotazione
	private static boolean confermaPrenotazione() {
	        System.out.println("Confermi la prenotazione? (S/N)");
	        
	        try {
	        	int ans=System.in.read();
	        	if(ans=='S' || ans=='s') {
	        		return true;
	        	}
	        }
	        catch (IOException ioe) {
	        	System.out.println("ERRORE! Problemi con l'input da tastiera exception="+ioe);
	        }
	        
	        return false;
	}
    
    
    // Questo metodo mostra le eccezioni SQL eventualmente generate
	private static void showSQLException(java.sql.SQLException e) {
		SQLException next = e;
		while (next != null) {
			System.out.println (next.getMessage());
			System.out.println ("Error Code: " + next.getErrorCode());
			System.out.println ("SQL State: " + next.getSQLState());
			next = next.getNextException();
		}
	}
    
        
	/**
	* main()    se i parametri passati sono corretti costruisce un oggetto PrenotaCinema4
	* parametri args[0] sala in cui si intende prenotare
	*           args[1] numero di posti da prenotare
	*           args[2] nome a cui si vuole prenotare
	*/
	public static void main (String args[]) {
		oc=null;
		if(args.length==3) {
			try {
				oc=new CinemaDB();
				int posti=Integer.parseInt(args[1]);
				// eseguo la prenotazione per la sala args[0] di args[1] posti a nome di args[2]
				prenota(args[0], posti, args[2]);
			}
			catch (ClassNotFoundException e) {
				System.out.println("ERRORE!: non riesco a trovare InterClient nel classpath.");
				System.out.println(e.getMessage());
				return;
			}
			catch (SQLException e) {
				if (oc!=null) 
				    	try {
				    		oc.rollback();
				    	} catch (SQLException e1) {}
				System.out.println("ERRORE SQL!");
				showSQLException(e);
			}
			// blocco di codice eseguito anche in caso di errore, prima di uscire
			finally {
				if (oc!=null)
					try {
						//Rilascia le risorse
						oc.close();
					} catch (SQLException e2) {
						System.out.println("ERRORE SQL!");
						showSQLException(e2);
					}
			}            
		}
		else {
			// il numero di parametri passati non è corretto!
			System.out.println("Numero di parametri errato, sintassi:");
			System.out.println();
			System.out.println("java PrenotaCinema <sala> <posti richiesti> <nome>");
		}
	}
	
}
