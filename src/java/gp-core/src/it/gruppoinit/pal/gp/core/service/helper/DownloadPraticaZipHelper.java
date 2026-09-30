package it.gruppoinit.pal.gp.core.service.helper;

import java.io.InputStream;

public class DownloadPraticaZipHelper {

    private String nomeFile;
    private InputStream contenuto;

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public InputStream getContenuto() {

	return contenuto;
    }

    public void setContenuto(InputStream contenuto) {

	this.contenuto = contenuto;
    }
}
