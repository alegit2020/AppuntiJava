import java.util.*;

public class RubricaSemplice implements RubricaTelefonica {
	private final int DIMENSIONE=100;
	
	private Vector voci;
	
	public RubricaSemplice() {
		voci= new Vector();
	}
	
	public VoceRubrica[] trovaNome(String nome) {
		nome=nome.toLowerCase();
		Vector temp=new Vector();
		for (Enumeration e = voci.elements(); e.hasMoreElements(); ) {
			VoceRubrica v= (VoceRubrica) e.nextElement();
			if (v.getNome().toLowerCase().startsWith(nome))
				temp.add(v);	
       		}
       		VoceRubrica[] risultato= new VoceRubrica[temp.size()];
       		int i=0;
       		for (Enumeration e = temp.elements(); e.hasMoreElements(); i++) 
       			risultato[i]=(VoceRubrica) e.nextElement();
       		return risultato;
	}
	
	public VoceRubrica[] trovaNumero(String numero) {
		numero=numero.toLowerCase();
		Vector temp=new Vector();
		for (Enumeration e = voci.elements(); e.hasMoreElements(); ) {
			VoceRubrica v= (VoceRubrica) e.nextElement();
			if (v.getNumero().toLowerCase().startsWith(numero))
				temp.add(v);	
       		}
       		VoceRubrica[] risultato= new VoceRubrica[temp.size()];
       		int i=0;
       		for (Enumeration e = temp.elements(); e.hasMoreElements(); i++) 
       			risultato[i]=(VoceRubrica) e.nextElement();
       		return risultato;
	}
	
	public void inserisci(VoceRubrica v) {
		if (voci.contains(v)) return;
		else voci.add(v);
	}
	
	public void cancella(VoceRubrica v) throws VoceAssente {
		if (! voci.remove(v)) throw new VoceAssente();
	}
	
	public void modifica(VoceRubrica voceVecchia, VoceRubrica voceNuova) throws VoceAssente {
		if (! voci.remove(voceVecchia)) throw new VoceAssente();
		else voci.add(voceNuova);
	}
}