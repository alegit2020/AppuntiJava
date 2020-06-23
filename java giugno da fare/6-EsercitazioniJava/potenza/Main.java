class Main {
Potenza p;

  public static void main(String[] args) {
    Potenza p;
    p = new Potenza(3);
    System.out.println("Hello world!");
    System.out.print("\n2^n=" + p.pow());
    p.pow_void();

    p.cambiabase(5);
    System.out.print("\ncambio base 2^n=" + p.pow());
  }
}