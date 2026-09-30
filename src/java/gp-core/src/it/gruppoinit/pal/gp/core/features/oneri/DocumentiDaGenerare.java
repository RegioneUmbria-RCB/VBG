package it.gruppoinit.pal.gp.core.features.oneri;

public class DocumentiDaGenerare {

    private boolean generaFattura;
    private boolean generaAvviso;

    public DocumentiDaGenerare(boolean generaFattura, boolean generaAvviso) {

	this.generaFattura = generaFattura;
	this.generaAvviso = generaAvviso;
    }

    public boolean getGeneraAvviso() {

	return generaAvviso;
    }

    public boolean getGeneraFattura() {

	return generaFattura;
    }

    public boolean generazioneDocumentiNecessaria() {

	return this.generaAvviso || this.generaFattura;
    }
}
