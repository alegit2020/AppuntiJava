import java.util.*;

public class RWLock  {

	class Elemento {
		public static final int LETTORE =0 ;
		public static final int SCRITTORE =1;
		Thread t;
		int tipo;
		int contatore;
		
		Elemento(Thread t, int tipo) {
			this.t=t;
			this.tipo=tipo;
		}
		
	}
	
	private Vector inAttesa=new Vector();
	
	private int indicePrimoScrittore() {
		Enumeration e;
		int i=0;
		for (e=inAttesa.elements(); e.hasMoreElements(); i++) {
			Elemento elemento= (Elemento) e.nextElement();
			if (elemento.tipo==Elemento.SCRITTORE)
				return i;
		}
		return Integer.MAX_VALUE;
	}
	
	private int indiceElemento(Thread t) {
		Enumeration e;
		int i=0;
		for (e=inAttesa.elements(); e.hasMoreElements(); i++) {
			Elemento elemento= (Elemento) e.nextElement();
			if (elemento.t==t)
				return i;
		}
		return -1;
	}
	
	public synchronized void richiediLettura() {
		Elemento elemento;
		Thread me= Thread.currentThread();
		int i= indiceElemento(me);
		
		if (i==-1) {
			elemento= new Elemento(me, Elemento.LETTORE);
			inAttesa.addElement(elemento);
		}
		else
			elemento=(Elemento) inAttesa.elementAt(i);
		
		while (indiceElemento(me)>indicePrimoScrittore()) {
			try {
				wait();
			} catch (Exception e) {}
		}
		elemento.contatore++;
	}
	
	public synchronized void richiediScrittura() {
		Elemento elemento;
		Thread me=Thread.currentThread();
		int i=indiceElemento(me);
		if (i== -1) {
			elemento=new Elemento(me, Elemento.SCRITTORE);
			inAttesa.addElement(elemento);
		}
		else {
			elemento=(Elemento) inAttesa.elementAt(i);
			if (elemento.tipo==Elemento.LETTORE)
				throw new IllegalArgumentException("Tentativo di aumentare il livello di privilegio");
			elemento.tipo=Elemento.SCRITTORE;
		}
		while (indiceElemento(me) != 0) {
			try {
				wait();
			} catch (Exception e) {}
		}
		elemento.contatore++;
	}
	
	public synchronized void rilascia() {
		Elemento elemento;
		Thread me= Thread.currentThread();
		int i=indiceElemento(me);
		
		if (i > indicePrimoScrittore()) 
			throw new IllegalArgumentException("Permesso non posseduto");
		elemento = (Elemento) inAttesa.elementAt(i);
		elemento.contatore--;
		if (elemento.contatore ==0) {
			inAttesa.removeElementAt(i);
			notifyAll();
		}
	}
}