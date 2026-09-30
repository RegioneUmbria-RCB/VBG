package it.gruppoinit.pal.gp.core.features.segnaposto.v2;

public class StrutturaSegnaposto {

    private String nome;
    private String segnapostoOriginale;
    private String[] argomenti;

    public StrutturaSegnaposto(String nome) {

	this(nome, nome, (String[]) null);
    }

    public StrutturaSegnaposto(String nome, String argomenti) {

	this(String.format("%s(%s)", nome, argomenti), nome, argomenti.split(","));
    }

    private StrutturaSegnaposto(String segnapostoOriginale, String nome, String[] argomenti) {

	this.segnapostoOriginale = segnapostoOriginale;
	this.nome = nome;
	this.argomenti = argomenti;
	if (this.getArgomenti() == null) {
	    this.argomenti = new String[0];
	}
	for (int i = 0; i < this.argomenti.length; i++) {
	    this.argomenti[i] = this.argomenti[i].trim();
	}
    }

    public String getNome() {

	return nome;
    }

    public String[] getArgomenti() {

	return argomenti;
    }

    public String toStringaSegnaposto() {

	return segnapostoOriginale;
    }
}
