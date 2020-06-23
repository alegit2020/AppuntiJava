import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;


public class AppletConvertitore extends Applet implements ActionListener {
	private final double fattoreDiConversione=1936.27;
	private TextField t;
	private Checkbox lire, euro;
	private Button b;
	
	public void init() {
		//Gestore della disposizione
		setLayout(new BorderLayout());
		setBackground(Color.white);
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
		euro= new Checkbox("Euro",false,cbg);

		//Raggruppa alcuni componenti, così da presentarli adiacenti
		Panel p= new Panel();
		p.add(lire);
		p.add(euro);
		p.add(b);
		
		//aggiungi il campo di testo in alto 
		add(t,"North");
		//aggiungi il pannello in basso 
		add(p,"South");
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

}