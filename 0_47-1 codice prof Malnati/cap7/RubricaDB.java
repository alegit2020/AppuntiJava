import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

/** 

        RubricaDB.java

        Corso di Java
        Cap 7 - Database

        Esempi base
        
        Questa classe implementa l'interfaccia RubricaTelefonica per la
        gestione (memorizzazione e recupero) della rubrica telefonica
        attraverso un database.

        Per funzionare questa classe ha bisogno di usare la tabella 'RUBRICA'
        (presente nel database indicato nell'attributo di classe dbUrl), 
        che può essere creata con il seguente statement:
 
        CREATE TABLE "RUBRICA" (
            "NOME" VARCHAR(64) NOT NULL,
            "TELEFONO" VARCHAR(30) NOT NULL,
            PRIMARY KEY ("NOME", "TELEFONO")
        );
    
        Si può eseguire questo esempio, dopo averlo compilato, con 
        
                java RubricaDB
                
        avendo cura di avere nella variabile d'ambiente CLASSPATH il path
        al file interclient.jar che contiene i driver del database Interbase
        
*/      
public class RubricaDB implements RubricaTelefonica {
        
        /*
                parametri necessari alla connessione al database, 
                modificarli opportunamente in modo da riflettere
                la configurazione presente sul proprio PC
        */
        private static final String dbDriverName = "interbase.interclient.Driver";
        private static final String dbUrl ="jdbc:interbase://localhost/c:/Corso Java/prove.gdb";
        private static final String dbUser = "CORSOJAVA";
        private static final String dbPassword = "CorsoJava";

        /*
                se impostato a true l'applicazione notifica all'utente 
                le situazioni d'errore
        */
        private static boolean visualizzaEccezioni = true;

        private Connection c=null;
        
        /*
                RubricaDB:      istanzia i driver e crea una connessione. Nel caso che una di
                                        queste operazioni non vada a buon fine genera un'eccezione
        */
        public RubricaDB(String driver, String dbUrl, String user, String password) 
        	throws SQLException,ClassNotFoundException {
                Class.forName(driver);
                c=DriverManager.getConnection(dbUrl,user,password);
        }
        
        /*
                trovaNome:      implementazione del metodo dell'interfaccia RubricaTelefonica che
                                        permette di recuperare dal database le voci della rubrica 
                                        il cui nome inizia per 'nomeDaTrovare'
        */
        public VoceRubrica[] trovaNome(String nomeDaTrovare) {
                Statement s=null;
                ResultSet rs=null;
                
                try {
                        /* 
			Poiché voglio eseguire una ricerca case insensitive (ovvero non distinguo tra
			lettere minuscole e maiuscole), devo trasformare in maiuscolo il parametro 
			nomeDaTrovare per poi confrontare tale valore con il contenuto del campo 'NOME'
			della tabella 'RUBRICA' trasformato tutto in maiuscolo. Il confronto è effetuato
			dalla seguente clusola WHERE dello statement SQL:                
                                        UPPER(NOME) LIKE '<nomeDaTrovare>%'
                        Il carattere % indica una stringa di caratteri qualsiasi
                        */
                        nomeDaTrovare=nomeDaTrovare.toUpperCase();
                        s=c.createStatement();
                        rs=s.executeQuery("SELECT * FROM RUBRICA WHERE UPPER(NOME) LIKE '"+nomeDaTrovare+"%';");

                        /*
			Inserisco tutti i record trovato in un vettore, poi li ricopio in un array.
			Non posso inserire i record trovati direttamente in un array poiché non ne
			conosco a priori le dimensioni.
                        */
                        Vector voci= new Vector();
                        VoceRubrica vr;
                        String nome,numero;
                        while (rs.next()) {
                                nome=rs.getString("NOME");
                                numero=rs.getString("TELEFONO");
                                vr=new VoceRubrica(nome,numero);
                                voci.add(vr);
                        }
                        VoceRubrica[] risultato=new VoceRubrica[voci.size()];
                        for (int i=0; i<risultato.length; i++) {
                                risultato[i]=(VoceRubrica) voci.get(i);
                        }
                        /*
			attenzione: prima dell'esecuzione di questo return viene eseguita
			la clausola finally sottostante, poi il metodo termina restituendo 
		        il contenuto della variabile risultato
                        */
                        return risultato;
                }
                catch (SQLException e) {
                        visualizzaEccezioneSQL(e);
                        /*
			attenzione: prima dell'esecuzione di questo return viene eseguita
			la clausola finally sottostante, poi il metodo termina restituendo 
			un array vuoto di tipo VoceRubrica
                        */
                        return new VoceRubrica[0];
                }
                /*
                        la clausola finally permette, sia nel caso si verifichi un'eccezione
                        sia nel caso non si verifichi, di chiudere result set e statement, in
                        modo da non lasciare allocate memoria e risorse dopo il loro utilizzo.
                */
                finally {
                        try {
                                if (rs!=null) rs.close();
                                if (s!=null) s.close();
                        }
                        catch (SQLException e) {}
                }
        }
        
        /*
		trovaNumero:implementazione del metodo dell'interfaccia RubricaTelefonica che
		permette di recuperare dal database le voci della rubrica il cui
		numero telefonico inizia per 'numeroDaTrovare'
		Questo metodo è analogo a trovaNome
        */
        public VoceRubrica[] trovaNumero(String numeroDaTrovare) {
                Statement s=null;
                ResultSet rs=null;
                
                try {
                        numeroDaTrovare=numeroDaTrovare.toUpperCase();
                        s=c.createStatement();
                        rs=s.executeQuery("SELECT * FROM RUBRICA WHERE UPPER(TELEFONO) LIKE '"+numeroDaTrovare+"%';");
                        Vector voci= new Vector();
                        VoceRubrica vr;
                        String nome,numero;
                        while (rs.next()) {
                                nome=rs.getString("NOME");
                                numero=rs.getString("TELEFONO");
                                vr=new VoceRubrica(nome,numero);
                                voci.add(vr);
                        }
                        VoceRubrica[] risultato=new VoceRubrica[voci.size()];
                        for (int i=0; i<risultato.length; i++) {
                                risultato[i]=(VoceRubrica) voci.get(i);
                        }
                        return risultato;
                }
                catch (SQLException e) {
                        visualizzaEccezioneSQL(e);
                        return new VoceRubrica[0];
                }
                finally {
                        try {
                                if (rs!=null) {
                                        rs.close();
                                        rs=null;
                                }
                                if (s!=null) {
                                        s.close();
                                        s=null;
                                }
                        }
                        catch (SQLException e) {
                                visualizzaEccezioneSQL(e);
                        }
                }
                
        }
        
        /*
                inserisci: inserisce la voce passata nella tabella 'RUBRICA' attraverso
		lo statement SQL
				INSERT INTO RUBRICA (NOME,TELEFONO) VALUES ('<nome>','<telefono>');
        */
        public void inserisci(VoceRubrica v) {
                String query="INSERT INTO RUBRICA (NOME, TELEFONO) VALUES ("
                        +"'"+v.getNome()+"', "+"'"+v.getNumero()+"'"+ ");";
                Statement s=null;
                try {
                        s=c.createStatement();
                        s.executeUpdate(query);
                }
                catch (SQLException e) {
                        visualizzaEccezioneSQL(e);
                }
                finally {
                        try { if (s!=null) s.close (); } catch (SQLException e) {
                                visualizzaEccezioneSQL(e);
                        }
                }            
        }
        
        /*
                cancella: cancella la voce passata dalla tabella 'RUBRICA' attraverso
		lo statement SQL
				DELETE FROM RUBRICA WHERE NOME='<nome>' AND TELEFONO='<telefono>';
        */
        public void cancella(VoceRubrica v) throws VoceAssente {
                String query="DELETE FROM RUBRICA WHERE NOME = '"
                        +v.getNome()+"' AND TELEFONO = '"+v.getNumero()+"';";
                Statement s=null; 
                try {
                        s=c.createStatement();
                        int i= s.executeUpdate(query);
                        /*
                                se lo statement eseguito non ha cancellato alcuna riga dalla tabella
                                lancio l'eccezione VoceAssente
                        */
                        if (i==0) 
                                throw new VoceAssente();
                }
                catch (SQLException e) {
                        visualizzaEccezioneSQL(e);
                }
                finally {
                        try { if (s!=null) s.close (); } catch (SQLException e) {
                                visualizzaEccezioneSQL(e);
                        }
                }            
        }
        
        /*
		modifica: modifica la voce passata nella tabella 'RUBRICA' attraverso
		lo statement SQL
			UPDATE RUBRICA SET NOME='<nuovonome>', TELEFONO='<nuovotelefono>' 
				WHERE NOME='<vecchionome>' AND TELEFONO='<vecchiotelefono>';
        */
        public void modifica(VoceRubrica voceVecchia, VoceRubrica voceNuova) throws VoceAssente {
                String query="UPDATE RUBRICA SET NOME = '"+voceNuova.getNome()+"', TELEFONO = '"+
                        voceNuova.getNumero()+"' WHERE NOME = '"+
                        voceVecchia.getNome()+"' AND TELEFONO = '"+
                        voceVecchia.getNumero()+"';";
                Statement s=null; 
                try {
                        s=c.createStatement();
                        int i= s.executeUpdate(query);
                        
                        /*
                                se lo statement eseguito non ha modificato alcuna riga dalla tabella
                                lancio l'eccezione VoceAssente
                        */
                        if (i==0) 
                                throw new VoceAssente();
                }
                catch (SQLException e) {
                        visualizzaEccezioneSQL(e);
                }
                finally {
                        try { if (s!=null) s.close (); } catch (SQLException e) {
                                visualizzaEccezioneSQL(e);
                        }
                }            
        }
        
        /*
                termina: rilascia le risorse allocate
        */
        public void termina() {
                try {
                        if (c!=null) c.close();
                }
                catch (SQLException e) {
                        visualizzaEccezioneSQL(e);
                }
                finally {
                        c=null;
                }
        }

        /*
		visualizzaEccezioneSQL: se l'attributo di classe visualizzaEccezioni è impostato
		a true, questo metodo apre una dialog box al contenente la causa dell'eccezione
        */
        private static void visualizzaEccezioneSQL(SQLException se) {
                StringBuffer sb=new StringBuffer();
                if (visualizzaEccezioni) {
                        while (se != null) {
                                sb.append(se.getMessage());
                                sb.append("\n");
                                sb.append("Codice di errore: ");
                                sb.append(se.getErrorCode());
                                sb.append("\n");
                                se= se.getNextException();
                        }
                        JOptionPane.showMessageDialog(null, sb.toString(), 
                        	"Eccezione SQL",JOptionPane.ERROR_MESSAGE);
                }
        }

        /*
		main: permette di avviare la GUI della rubrica telefonica usando un database
		per la gestione delle voci nella rubrica.

		Nel caso in cui fallisca il collegamento al database o la localizzazione
		dei driver, viene aperta una dialog box contenente la causa dell'errore.
        */
        public static void main(String[] args) {
                try {
                        final RubricaDB rdb=new RubricaDB(dbDriverName, dbUrl, dbUser, dbPassword);
                        GUIRubrica gui= new GUIRubrica(rdb);
                        JFrame f= new JFrame("Rubrica Telefonica");
                        f.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent we) {
                                        rdb.termina();
                                        System.exit(0);
                                }
                        });
                        f.setSize(320,240);
                        f.setResizable(false);
                        f.getContentPane().add(gui,"Center");
                        f.setVisible(true);
                }
                catch (ClassNotFoundException cnfe) {
                        JOptionPane.showMessageDialog(null, "Impossibile localizzare il driver del database", "Attenzione",
                                    JOptionPane.ERROR_MESSAGE); 
                        System.exit(1);
                }
                catch (SQLException se) {
                        visualizzaEccezioneSQL(se);
                        System.exit(2);
                }
        }       
}