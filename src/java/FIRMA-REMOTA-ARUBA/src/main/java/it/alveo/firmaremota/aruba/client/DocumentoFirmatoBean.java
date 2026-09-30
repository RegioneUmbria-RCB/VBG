package it.alveo.firmaremota.aruba.client;

public class DocumentoFirmatoBean {

    private String status;
    private String esito;
    private String returnCode;
    private String guid;
    private String description;
    private String nomeFile;

    public String getStatus() {

	return status;
    }

    public void setStatus(String status) {

	this.status = status;
    }

    public String getEsito() {

	return esito;
    }

    public void setEsito(String esito) {

	this.esito = esito;
    }

    public String getReturnCode() {

	return returnCode;
    }

    public void setReturnCode(String returnCode) {

	this.returnCode = returnCode;
    }

    public String getGuid() {

	return guid;
    }

    public void setGuid(String guid) {

	this.guid = guid;
    }

    public String getDescription() {

	return description;
    }

    public void setDescription(String description) {

	this.description = description;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }
}
