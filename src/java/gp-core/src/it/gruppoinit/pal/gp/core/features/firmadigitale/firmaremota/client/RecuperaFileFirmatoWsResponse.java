package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client;

import java.io.InputStream;

public class RecuperaFileFirmatoWsResponse {

    private String guid;
    private String tipoFirma;
    private String nomeFile;
    private InputStream content;

    public RecuperaFileFirmatoWsResponse(String guid, String tipoFirma, String nomeFile, InputStream content) {

	this.guid = guid;
	this.tipoFirma = tipoFirma;
	this.nomeFile = nomeFile;
	this.content = content;
    }

    public String getGuid() {

	return guid;
    }

    public String getTipoFirma() {

	return tipoFirma;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public InputStream getContent() {

	return content;
    }
}
