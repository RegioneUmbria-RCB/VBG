package it.gruppoinit.pal.gp.core.domain.helper;

import javax.activation.DataHandler;

public class AllegatoDataHandlerHelper {

    private String identificativo;
    private DataHandler dataHandler;
    private String nomeFile;
    private String contentType;

    public DataHandler getDataHandler() {

	return dataHandler;
    }

    public void setDataHandler(DataHandler dataHandler) {

	this.dataHandler = dataHandler;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public String getContentType() {

	return contentType;
    }

    public void setContentType(String contentType) {

	this.contentType = contentType;
    }

    public String getIdentificativo() {

	return identificativo;
    }

    public void setIdentificativo(String identificativo) {

	this.identificativo = identificativo;
    }
}
