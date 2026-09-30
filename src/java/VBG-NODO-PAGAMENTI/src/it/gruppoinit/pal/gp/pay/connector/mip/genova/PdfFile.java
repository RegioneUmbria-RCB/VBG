package it.gruppoinit.pal.gp.pay.connector.mip.genova;

public class PdfFile {

    private String nomeFile;
    private int numPagine;

    public PdfFile(String nomeFile, int numPagine) {

	super();
	this.nomeFile = nomeFile;
	this.numPagine = numPagine;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public int getNumPagine() {

	return numPagine;
    }
}
