import java.sql.*;

public class CinemaDB implements OperazioniCinema {
/* 
 *	Questa applicazione utilizza le tabelle create con i comandi
 	
		CREATE TABLE CINEMA (
			SALA VARCHAR(30) NOT NULL,
			POSTITOTALI INTEGER NOT NULL,
			POSTILIBERI INTEGER NOT NULL CHECK (POSTILIBERI >=0),
			PRIMARY KEY (SALA)
		);	
					
		CREATE TABLE PRENOTAZIONICINEMA (
			  NOME VARCHAR(30) NOT NULL,
			  SALA VARCHAR(30) NOT NULL,
			  POSTI INTEGER NOT NULL
		);		
		
 *	La prima tabella deve essere popolata con istruzioni del tipo
 		
 		INSERT INTO CINEMA VALUES (
 			'NomeCinema',
 			100,
 			5
 		);

 *	La seconda tabella deve essere popolata con istruzioni del tipo

 		INSERT INTO PRENOTAZIONICINEMA VALUES (
 			'Tizio',
 			'NomeCinema',
 			numeroPostiPrenotati
 		);
 		
 		
 */

	static private final String driver = "interbase.interclient.Driver";
	static private final String databaseURL = "jdbc:interbase://localhost/c:/Corso Java/prove.gdb";
	static private final String user = "CORSOJAVA";
	static private final String password = "CorsoJava";
    
	private Connection c = null;
	private Statement s = null;
	private ResultSet rs = null;

	public CinemaDB() throws SQLException, ClassNotFoundException {
		try {
	                // carico i driver JDBC di Interbase (Interclient)
	                Class.forName(driver);
	                // ottengo una connessione al database
	                c = DriverManager.getConnection(databaseURL, user, password);
	                // imposto autocommit a false: occorre fare un commit/rollback esplicito per
	                // terminare la sessione
	                c.setAutoCommit(false);       
	                // creo uno statement con cui eseguire select, insert e update
	                s = c.createStatement();
		} catch (SQLException e1) {
       			//Se si verifica un eccezione rilascio eventuali risorse allocate
			try { 
				if (s!=null) s.close (); 
	        	} catch (SQLException e2) {
	        	} finally {
	        		s=null;
	        	}
	            	try {
	            		if (c!=null) c.close (); 
	            	} catch (SQLException e3) {
	            	} finally {
	            		c=null;
	            	}
	            	//Dopodiché notifico l'eccezione al chiamante
	            	throw e1;
	       }            
	}
	
	public boolean verificaDisponibilità(String sala, int posti) throws SQLException {
		String query="select * from cinema where UPPER(sala)='"+sala.toUpperCase()+"' and postiLiberi>="+posti+";" ;
		boolean result=false;
		try {
			rs=s.executeQuery(query);
			// C'e' posto se ho trovato una riga (sono sicuro che sia al massimo una riga 
			// perché ho messo nel WHERE la PrimaryKey della tabella
			result=rs.next();
			rs.close();
			return result;
		} 
		finally {
			if (rs!=null) {
				try {
					rs.close();
				}
				finally {
					rs=null;
				}
			}
		}

	}
	
	public void effettuaPrenotazione(String sala, int posti, String cliente) throws SQLException {
		String query="update cinema set postiLiberi=postiLiberi-"+posti+" where upper(sala)='"+sala.toUpperCase()+"'";
		System.out.println(query);
		s.executeUpdate(query);
	}
	
	public void aggiornaRegistro(String sala, int posti, String cliente) throws SQLException {
		String query="insert into prenotazioniCinema values ('"+cliente+"','"+sala+"',"+posti+")";
		System.out.println(query);
		s.executeUpdate(query);
	}

	public void commit() throws SQLException {
		c.commit();
	}

	public void rollback() throws SQLException {
		c.rollback();
	}
	
	public void close() throws SQLException  {
		SQLException e=null;
		try {
			if (rs!=null) rs.close();
		} catch (SQLException e1) {
			if (e==null) e=e1;
		} finally {
			rs=null;
		}
		try { 
			if (s!=null) s.close (); 
		} catch (SQLException e2) {
			if (e==null) e=e2;
		} finally {
			s=null;
		}
		try {
			if (c!=null) c.close (); 
		} catch (SQLException e3) {
			if (e==null) e=e3;
		} finally {
			c=null;
		}
		
            	//Dopodiché notifico l'eventuale eccezione al chiamante
            	//Se se ne è verificata più di una, notifico la prima
		if (e!=null)
	            	throw e;
	
	}
	
}