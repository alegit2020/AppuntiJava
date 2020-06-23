import java.awt.*;
import java.awt.event.*;
import java.text.*;


public class Convertitore implements ActionListener {
	private final double fattoreDiConversione=1936.27;
	private Frame f;
	private TextField t;
	private Checkbox lire, euro;
	private Button b;
	
	public Convertitore() {
		//Finestra principale dell'applicazione
		f= new Frame("Convertitore £/€");
		f.setSize(160,100);
		f.setLocation(200,200);
		//La chiusura della finestra implica la terminazione
		//del programma
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(1);
			}
		});
		
		//Campo di testo: valore da convertire
		t=new TextField("");
		
		//Bottone: richiede la conversione
		b=new Button("Converti");
		//registrazione dell'oggetto corrente 
		//come interessato alla pressione del bottone
		b.addActionListener(this);
		
		//Costruzione del selettore di valuta
		//cbg serve a garantire la mutua esclusione
		CheckboxGroup cbg= new CheckboxGroup();
		//due checkbox, appartenenti al gruppo cbg
		//la prima inizialmente selezionata
		lire= new Checkbox("£",true,cbg);
		//la seconda no
		euro= new Checkbox("€",false,cbg);

		//Raggruppa alcuni componenti, così da presentarli adiacenti
		Panel p= new Panel();
		p.add(lire);
		p.add(euro);
		p.add(b);
		
		//aggiungi il campo di testo in alto al frame
		f.add(t,"North");
		//aggiungi il pannello in basso al frame
		f.add(p,"South");
		//visualizza il frame
		f.setVisible(true);
	}
	
	//Viene chiamato ogni volta in cui si preme il bottone 
	public void actionPerformed(ActionEvent e) {
		//leggi cosa è scritto nel campo di testo 
		String valore=t.getText();
		double d;
		try {
			//trasforma la scritta in numero
			d=NumberFormat.getInstance().parse(valore).doubleValue();
		} catch (ParseException nfe) {
			//in caso di errore
			d=0.0;
		}
		if (lire.getState()) {	//Valuta corrente in lire: trasforma in euro
			d=d/fattoreDiConversione;
			euro.setState(true);	//Cambia il tipo di valuta
		}
		else {					//Valuta corrente in euro: trasforma in lire
			d=d*fattoreDiConversione;
			lire.setState(true);	//Cambia il tipo di valuta
		}
		//Converti d in una stringa e visualizzala nel campo di testo
		t.setText(NumberFormat.getInstance().format(d));		
	}
	
	public static void main(String[] args) {
		//crea un convertitore
		Convertitore c= new Convertitore();
	}

}