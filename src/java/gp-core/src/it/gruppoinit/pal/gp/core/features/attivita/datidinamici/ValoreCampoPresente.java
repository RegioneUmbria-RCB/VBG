package it.gruppoinit.pal.gp.core.features.attivita.datidinamici;

public class ValoreCampoPresente {

    private String valore;
    private String valoreDecodificato;

    public ValoreCampoPresente() {

    }

    public ValoreCampoPresente(String valore, String valoreDecodificato) {

	super();
	this.valore = valore;
	this.valoreDecodificato = valoreDecodificato;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public String getValoreDecodificato() {

	return valoreDecodificato;
    }

    public void setValoreDecodificato(String valoreDecodificato) {

	this.valoreDecodificato = valoreDecodificato;
    }
}
