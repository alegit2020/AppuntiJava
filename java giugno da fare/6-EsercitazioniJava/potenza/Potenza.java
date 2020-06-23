public class Potenza {
  private int base;
  double argomento;
  double risultato;

  public Potenza(double n){
    this.base=2;
    this.argomento = n;
    this.risultato = Math.pow( this.base , this.argomento);
  }

  public void pow_void(){
    System.out.print("\nvoid 2^n=" + this.risultato);
  }

  public double pow(){
    return this.risultato;
  }

  public void cambiabase(int b){
    this.base=b;
    this.risultato= Math.pow(this.base , this.argomento);    
  }
}