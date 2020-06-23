public class Produttore extends Thread {
    private Pallet p;

    public Produttore(Pallet p, String nome) {
        this.p=p;
        setName(nome);
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            p.put(i);
            System.out.println(getName()+": inviato("+ i+")");
            try {
                sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) { }
        }
    }
}
