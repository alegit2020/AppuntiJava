public interface RubricaTelefonica {
	//Trova le voci il cui nome comincia con la stringa indicata
	public VoceRubrica[] trovaNome(String nome);
	
	//Trova le voci il cui numero comincia con la stringa indicata
	public VoceRubrica[] trovaNumero(String numero);
	
	//Inserisce una nuova voce
	public void inserisci(VoceRubrica v);
	
	//Cancella una voce
	public void cancella(VoceRubrica v) throws VoceAssente;
	
	//Modifica una voce
	public void modifica(
			VoceRubrica voceVecchia, 
			VoceRubrica voceNuova) throws VoceAssente;
}