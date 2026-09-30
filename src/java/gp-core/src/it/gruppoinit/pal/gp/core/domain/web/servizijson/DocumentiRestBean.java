package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class DocumentiRestBean {

    private String nomeFile;
    private String descrizione;
    private String downloadUrl;

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getDownloadUrl() {

	return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {

	this.downloadUrl = downloadUrl;
    }
}
