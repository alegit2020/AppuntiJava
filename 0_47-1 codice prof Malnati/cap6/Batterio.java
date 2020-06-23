import java.awt.*;
import java.util.*;

public class Batterio implements Runnable {
	
	private Random rng;
	private int x,y;
	private int dim;
	private int eta;
	private Microscopio m;
	
	public Batterio(int xIniziale, int yIniziale, Microscopio m, Random rng) {
		this.rng=rng;
		this.m=m;
		eta=0;
		dim=10;
		x=xIniziale;
		y=yIniziale;
		Thread t= new Thread(this);
		t.start();
	}
	
	public synchronized void disegna(Graphics g) {
		int c=Math.min(eta,240);
		g.setColor(new Color(c,c,c));
		g.fillOval(x,y,dim,dim);
	}
	
	private synchronized void muovi() {
		//Aggiorna la posizione
		x += rng.nextInt(11)-5;
		y += rng.nextInt(11)-5;

		//Confina le particelle nel microscopio
		Dimension d=m.getSize();
		if (x+dim>d.width) x= d.width-dim;
		else if (x<0) x= 0;
		if (y+dim>d.height) y= d.height-dim;
		else if (y<0) y= 0;
		
		//Ciclo di crescita: probabilità 10%
		if (rng.nextInt(10)==0) dim++;
		//Se la dimensione è maggiore di quella critica, sdoppiamento
		if (dim>=15) {
			dim=10;
			Batterio b= new Batterio(x,y,m,new Random(rng.nextLong()));
			m.aggiungiBatterio(b);
		}
		
	}
	
	public void run(){
		while (eta<255) {
			try {
				int i=rng.nextInt(10);
				Thread.sleep(i*100);
				muovi();
				eta += i;
			}
			catch (InterruptedException ie) {
				return;
			}
		}
		m.rimuoviBatterio(this);		
	}
}
