/*
  Obiettivo: verifica comprensione tipi statici e dinamici

  Il seguente programma contiene molti errori.
  - quali?
  - compilare il programma e verificare le proprie risposte

*/

interface Edificio{}

public class Villa implements Edificio {
	void numeroPiani(){}

	public static void main(String[] argc){

		Edificio e = new Edificio();
		Villa v = new Villa();
		Edificio e1 = new Villa();

		e=v;
		v=e1;
		e1.numeroPiani();
		e = new Palazzo();
		v=e;
	}

}
