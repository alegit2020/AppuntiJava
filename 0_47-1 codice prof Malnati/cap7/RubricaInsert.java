import java.sql.*;

/** 

	Corso per programmatori Java
	Cap 7 - Database

	Inserimento di dati in una tabella
	
	Semplice applicazione console per l'inserimento di righe nella tabella rubrica

	Questa classe inserisce i dati passati sulla riga di comando in una 
	tabella rubrica creata in un db (di cui occore specificare
	driver, url, user e password nei relativi attributi della classe) con
	il seguente statement sql
 
        CREATE TABLE "RUBRICA" (
            "NOME" VARCHAR(64) NOT NULL,
            "TELEFONO" VARCHAR(32) NOT NULL,
            PRIMARY KEY ("NOME", "TELEFONO")
        );

	Il formato della linea di comando è
	
		java RubricaInsert <nome> <telefono>

		
    
*/	
public class RubricaInsert {

    private String driver = "interbase.interclient.Driver";
    private String databaseURL = "jdbc:interbase://localhost/c:/documenti/java/prove.gdb";
    private String user = "CORSOJAVA";
    private String password = "CorsoJava";
    
    private Connection c = null;
    private Statement s = null;

    /** 
     * RubricaInsert()   inserisce nella tabella rubrica una nuova riga con i parametri passati
     * parametri        nome    
     *                  telefono
     */
    public RubricaInsert(String nome, String telefono) {
        try {
            try {
                // carico i driver JDBC di Interbase (Interclient)
                Class.forName(driver);
                
                // ottengo una connessione al database
                c = DriverManager.getConnection(databaseURL, user, password);
                System.out.println("Connessione stabilita");
    
                // imposto autocommit
                c.setAutoCommit(true);
                System.out.println("Autocommit impostato");
                
                // creo uno statement con cui eseguire l'insert
                s = c.createStatement();
            }
            catch (ClassNotFoundException e) {
                System.out.println("ERRORE!: non riesco a trovare InterClient nel classpath.");
                System.out.println(e.getMessage());
                return;
            }
            catch (SQLException e) {
                System.out.println("ERRORE SQL!");
                showSQLException(e);
                return;
            }
            
            // creo uno statement con cui eseguire l'insert
            String query="insert into rubrica (nome, telefono) values ("
                +"'"+nome+"', "+"'"+telefono+"'"+ ")";
            System.out.println("Eseguo la query "+query);
            try {
                s.executeUpdate(query);
                System.out.println("Query eseguita");
            }
            catch (SQLException e) {
            	if(e.getErrorCode()==335544665) {
            		System.out.println("Attenzione: PK duplicata nella query '"+query+"'");
            		System.out.println("Esiste gia' un record con nome="+nome+" e telefono="+telefono);
            	}
            	else {
            		System.out.println("ERRORE SQL!: non è possibile eseguire la query '"+query+"'");
                	showSQLException(e);
                }
            }
        }
        // blocco di codice eseguito anche in caso di errore, prima di effettuare qualsiasi return
        finally {
            // è opportuno chiudere esplicitamente ResultSet, Statement e Connection poiché non è detto
            // (e comunque non è determinato il momento) che il finalizzatore chiami i distruttori 
            // relativi a tali oggetti
            System.out.println ("Chiusura del database.");
            try { if (s!=null) s.close (); } catch (SQLException e) { showSQLException (e); }
            try { if (c!=null) c.close (); } catch (SQLException e) { showSQLException (e); }
        }            
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
     * main()    se i parametri passati sono corretti costruisce un oggetto RubricaInsert
     * parametri args[0] nome da inserire nella tabella rubrica
     *           args[2] telefono
     */
    public static void main (String args[]) {
        if(args.length==2) {
            new RubricaInsert(args[0],args[1]);
        }
        else {
            System.out.println("java RubricaInsert <nome> <telefono>");
        }
    }

}
