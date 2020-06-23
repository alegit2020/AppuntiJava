import java.awt.*;
import java.awt.event.*;

//Questo programma mostra la creazione di una finestra in Java
//
public class Finestra3 {
	
	public static void main(String[] args) {
		Frame f=new Frame("Esempio");	//Titolo della finestra
		f.setSize(320,200);				//Dimensioni
		f.setLocation(200,200);			//Posizione sullo schermo
		
		f.addWindowListener(new WindowAdapter() {	//Gestore degli eventi finestra
			public void windowClosing(WindowEvent we) {	
				//Quando viene premuto il bottone di chiusura
				System.exit(1);			// Esce dal programma
			}
		});
		
		Label l= new Label("Java è facile!",Label.CENTER);	//Scritta
		f.add(l,"Center");						//Aggiunta al centro del frame
		Button b= new Button("Ok");				//Bottone
		f.add(b,"South");						//Aggiunto in basso al frame
		
		f.setVisible(true);				//Visualizzazione
	}
}