import java.util.*;

public class AvvisoRipetuto {
	public static void main(String[] args) {
		Timer t = new Timer();
		
		TimerTask compito= new TimerTask() {
			public void run() {
				System.out.println("Attenzione: la batteria si sta esaurendo");
			}
		};
		try {
			//Attendi 5 secondi e poi invia un messaggio ogni 4 secondi
			t.scheduleAtFixedRate(compito, 5000,4000);
			//Attendi la pressione di un tasto...
			System.in.read();
		}
		catch (Exception e) {
		}
		finally {
			//Cancella l'esecuzione del timer
			t.cancel();
		}
	}
}