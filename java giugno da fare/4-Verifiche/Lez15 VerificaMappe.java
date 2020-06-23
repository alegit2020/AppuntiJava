/* obiettivo: verificare comprensione uso mappe

	- quali coppie contiene la mappa dopo l'ultimo
	  inserimento ?
	- perche' ?
	- compilare ed eseguire il programma per verificare
	  la propria risposta
*/
import java.util.*;

public class VerificaMappe{

	public static void main(String[] argc) {
		Map<String,Integer> mappa = new TreeMap<String,Integer>();

		int i = 1;

		mappa.put("Luca",i);
		System.out.println(mappa);

		mappa.put("Anna",i);
		System.out.println(mappa);

		i = 50;
		mappa.put("Antonio",i);
		System.out.println(mappa);

		i = 30;
		mappa.put("Anna", i);
		System.out.println(mappa);
	}
}
