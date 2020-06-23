import java.awt.*;

//Questo programma mostra la creazione di una finestra in Java
//
public class Finestra1 {
	public static void main(String[] args) {
		Frame f=new Frame("Esempio");	//Titolo della finestra
		f.setSize(320,200);				//Dimensioni
		f.setLocation(200,200);			//Posizione sullo schermo
		f.setVisible(true);				//Visualizzazione
	}
}