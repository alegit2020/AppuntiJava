public class VoceRubrica {
	private String nome;
	private String numero;
	
	public VoceRubrica(String nome, String numero) {
		this.nome=nome;
		this.numero=numero;
	}
	
	public String getNome() {
		return nome;
	}
	public String getNumero() {
		return numero;
	}
	
	public boolean equals(Object o) {
		if (o instanceof VoceRubrica) {
			VoceRubrica v= (VoceRubrica) o;
			boolean b=true;
			if (nome==null && v.nome!=null) b=false;
			if (nome!=null && ! nome.equalsIgnoreCase(v.nome)) b=false;
			if (numero==null && v.numero!=null) b=false;
			if (numero!=null && ! numero.equalsIgnoreCase(v.numero)) b=false;
			return b;
		} else 
			return false;
	}
	
	public String toString() {
		return nome+": "+numero;
	}
}