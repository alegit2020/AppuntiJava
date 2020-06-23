public class Consumatore extends Thread {
    private Pallet p;

    public Consumatore(Pallet p, String nome) {
        this.p= p;
        setName(nome);
    }

    public void run() {
	int d;
        for (int i = 0; i < 10; i++) {
            d = p.get();
            System.out.println(getName()+": ricevuto("+ d+")");
        }
    }
}