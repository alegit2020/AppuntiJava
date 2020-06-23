import java.awt.*;
import java.awt.event.*;

//Questo programma mostra la creazione di una finestra in Java
//
public class Finestra2 {
	public static void main(String[] args) {
		Frame f=new Frame("Esempio");	//Titolo della finestra
		f.setSize(320,200);				//Dimensioni
		f.setLocation(200,200);			//Posizione sullo schermo
		
		WindowListener wl;		//Gestore degli eventi della finestra	
		wl= new WindowAdapter() {	//Crea una sottoclasse anonima che ridefinisce 
									//il metodo windowClosing()
			public void windowClosing(WindowEvent we) {	
				//Quando viene premuto il bottone di chiusura
				System.exit(1);			// Esce dal programma
			}
		};
		f.addWindowListener(wl);		//registra wl come interessato
										//agli eventi legati alla finestra
		
		f.setVisible(true);				//Visualizzazione
	}
}