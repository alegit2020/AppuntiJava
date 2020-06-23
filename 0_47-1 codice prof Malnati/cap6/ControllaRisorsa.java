public class ControllaRisorsa {
	private Thread owner=null;
	private int counter=0;
	
	public synchronized void assumiControllo() {
		while (! tentaAssunzione()) {	//prova a prendere il controllo
			try {
				wait();		// se non riesce, aspetta
			} catch (InterruptedException ie) {
			}
		}
	}
	
	public synchronized void rilasciaControllo() {
		if (owner==Thread.currentThread()) { 	//verifica di possedere il controllo
			counter--;			//decrementa il contatore
			if (counter==0) {		//se la risorsa è stato rilasciato 
							//tante volte quanto è stata richiesta
				owner=null;		//rilascia il controllo
				notify();		//sveglia eventuali thread in attesa
			}
		}
		
	}
	
	private synchronized boolean tentaAssunzione() {
		Thread me=Thread.currentThread();
		if (owner==null) {//Nessuno ha il controllo, il thread corrente lo può assumere
			owner=me;
			counter=1;
			return true;
		}
		else if (owner==me) { //Il thread ha gia' il controllo, incrementa il contatore
			counter++;
			return true;
		} else 
			return false; //Risorsa occupata		
	}
	
}
