package projects.precipitazioni.base;

public class VisualizzatoreMatrice {

	private Precipitazioni precipitazioni;

	public void setPrecipitazioni (Precipitazioni p) {
		precipitazioni = p;
	}

	public void aggiornaSchermata(int passo) {
		System.out.println("Passo " + passo);
		for (int i=0; i<precipitazioni.getCartaPrecipitazioni().length; i++) {
			for (int j=0; j<precipitazioni.getCartaPrecipitazioni()[i].length; j++)
				System.out.print(precipitazioni.getCartaPrecipitazioni()[i][j]+" ");
			System.out.println();
		}
	}
}
