package it.gruppoinit.pal.gp.core.domain.helper;

public class CodiceFileIdDescrizioneBean extends CodiceDescrizioneBean {

    private String fileId;
    private String mimeType;
    private String esitoFirma;
    private String statusFirma;

    public CodiceFileIdDescrizioneBean() {

    }

    public String getFileId() {

	return fileId;
    }

    public void setFileId(String fileId) {

	this.fileId = fileId;
    }

    public String getMimeType() {

	return mimeType;
    }

    public void setMimeType(String mimeType) {

	this.mimeType = mimeType;
    }

    public String getEsitoFirma() {

	return esitoFirma;
    }

    public void setEsitoFirma(String esitoFirma) {

	this.esitoFirma = esitoFirma;
    }

    public String getStatusFirma() {

	return statusFirma;
    }

    public void setStatusFirma(String statusFirma) {

	this.statusFirma = statusFirma;
    }
}
