package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "modulistica")
public class ModulisticaBean {

    @XmlElement(name = "codice")
    private String codice;
    @XmlElement(name = "obbligatorio")
    private Boolean obbligatorio;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "titolo")
    private String titolo;
    @XmlElement(name = "file_id")
    private String fileId;
    @XmlElement(name = "link")
    private String link;
    @XmlElement(name = "download")
    private List<DownloadBean> downloads;

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
}