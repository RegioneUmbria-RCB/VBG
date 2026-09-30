package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

public class LetteraGenerataPerComunicazione {

    private byte[] contenutoFile;
    private String nomeFile;

    public LetteraGenerataPerComunicazione(byte[] contenutoFile, String nomeFile) {

	this.contenutoFile = contenutoFile;
	this.nomeFile = nomeFile;
    }

    public byte[] getContenutoFile() {

	return contenutoFile;
    }

    public String getNomeFile() {

	return nomeFile;
    }
}
