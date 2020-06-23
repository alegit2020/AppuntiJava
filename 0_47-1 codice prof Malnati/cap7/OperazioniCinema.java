import java.sql.*;

public interface OperazioniCinema {
	public boolean verificaDisponibilità(String sala, int posti) throws SQLException;
	public void effettuaPrenotazione(String sala, int posti, String cliente) throws SQLException;
	public void aggiornaRegistro(String sala, int posti, String cliente) throws SQLException;
	public void commit() throws SQLException;
	public void rollback() throws SQLException;
	public void close() throws SQLException;
}