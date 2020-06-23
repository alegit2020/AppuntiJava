public class Pallet {
    private int dato;
    private boolean datoPresente= false;

    public synchronized int get() {
        while (! datoPresente) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        datoPresente = false;
        notify();
        return dato;
    }

    public synchronized void put(int dato) {
        while (datoPresente) {
            try {
                wait();
            } catch (InterruptedException e) { }
        }
        this.dato = dato;
        datoPresente = true;
        notify();
    }
}
