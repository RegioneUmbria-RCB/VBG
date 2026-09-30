package it.gruppoinit.pal.gp.core.features.segnaposto.legacy.shared;

public class SostituzioneNelModello {

    private TypeFiled type;
    private String[] valori;

    public SostituzioneNelModello(TypeFiled type, String valore) {

	super();
	this.type = type;
	this.valori = new String[] { valore };
    }

    public SostituzioneNelModello(TypeFiled typeFiled, String[] valori) {

	super();
	this.type = typeFiled;
	this.valori = valori;
	if (this.getValori() == null) {
	    this.valori = new String[0];
	}
    }

    public TypeFiled getType() {

	return type;
    }

    public String[] getValori() {

	return valori;
    }
}
