package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.List;

public class ModulisticaBean {

    private String codice;
    private Boolean obbligatorio;
    private String descrizione;
    private String titolo;
    private String fileId;
    private String link;
    private List<DownloadBean> downloads;
    private int ordine;

    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    public Boolean getObbligatorio() {

	return obbligatorio;
    }

    public void setObbligatorio(Boolean obbligatorio) {

	this.obbligatorio = obbligatorio;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getTitolo() {

	return titolo;
    }

    public void setTitolo(String titolo) {

	this.titolo = titolo;
    }

    public String getFileId() {

	return fileId;
    }

    public void setFileId(String fileId) {

	this.fileId = fileId;
    }

    public String getLink() {

	return link;
    }

    public void setLink(String link) {

	this.link = link;
    }

    public List<DownloadBean> getDownloads() {

	return downloads;
    }

    public void setDownloads(List<DownloadBean> downloads) {

	this.downloads = downloads;
    }

    public int getOrdine() {

	return ordine;
    }

    public void setOrdine(int ordine) {

	this.ordine = ordine;
    }
}