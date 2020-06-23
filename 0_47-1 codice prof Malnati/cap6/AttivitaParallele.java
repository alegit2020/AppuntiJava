public class AttivitaParallele implements Runnable {

	public AttivitaParallele() {
		Thread t= new Thread(this);
		t.start();
		eseguiAttivita1();
	}
	
	public void run() {
		eseguiAttivita2();
	}
	
	private void eseguiAttivita1() {
		for(int i=0; i<50; i++) {
			//Attendi per un po' di tempo...
			try {
				int durata=(int) (1000*Math.random());
				Thread.sleep(durata);
				System.out.println("Attivita' 1 in corso...");
			} catch (InterruptedException e) {
			}
		}
	}
	
	private void eseguiAttivita2() {
		for(int i=0; i<50; i++) {
			//Attendi per un po' di tempo...
			try {
				int durata=(int) (1000*Math.random());
				Thread.sleep(durata);
				System.out.println("Attivita' 2 in corso...");
			} catch (InterruptedException e) {
			}
		}
	}
	
	public static void main(String[] args) {
		AttivitaParallele ap= new AttivitaParallele();
	}
}		