import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Microscopio extends Canvas implements Runnable {
	private Vector batteri;
	private int numeroBatteri=0;
	private Random rng;
	
	public Microscopio() {
		setSize(200,200);
		rng= new Random();
		batteri= new Vector();
		for (int i=0; i<10; i++) 
			creaBatterio();
		Thread t= new Thread(this);
		t.start();			
	}
	
	public void creaBatterio() {
		Dimension d= getSize();
		Batterio b= new Batterio(
			rng.nextInt(d.width),
			rng.nextInt(d.height),
			this,
			new Random(rng.nextLong())
		);
		aggiungiBatterio(b);
	}
	
	public synchronized void aggiungiBatterio(Batterio b) {
		batteri.add(b);
		numeroBatteri++;
	}
	
	public synchronized void rimuoviBatterio(Batterio b) {
		batteri.remove(b);
		numeroBatteri--;
	}
	
	public synchronized Object[] getBatteri() {
		return batteri.toArray();
	}

	public void paint(Graphics g) {
		Object[] bs= getBatteri();
		for (int i=0; i<bs.length; i++)
			((Batterio) bs[i]).disegna(g);
		g.setColor(Color.red);
		g.drawString("Numero batteri: "+bs.length, 10,10);
	}
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(200);
				repaint();
			}
			catch (InterruptedException ie) {return;}
		}
	}
	
	
	public static void main(String[] args) {
		final Microscopio m= new Microscopio();
		Frame f= new Frame("Microscopio");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		f.add(m,"Center");
		Button b= new Button("Infetta");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				m.creaBatterio();
			}
		});
		f.add(b,"South");
		f.setSize(240,240);
		f.setVisible(true);
	}
	
}