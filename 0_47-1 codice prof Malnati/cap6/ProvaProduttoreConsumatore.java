public class ProvaProduttoreConsumatore {
    public static void main(String[] args) {
		Pallet pallet = new Pallet();
        Produttore  p = new Produttore(pallet,"Produttore1");
        Consumatore c = new Consumatore(pallet,"Consumatore1");

        p.start();
        c.start();
    }
}
