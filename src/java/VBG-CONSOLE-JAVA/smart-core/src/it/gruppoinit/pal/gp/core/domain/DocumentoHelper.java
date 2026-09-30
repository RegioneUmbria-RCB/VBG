package it.gruppoinit.pal.gp.core.domain;

import it.init.sigepro.rte.types.DocumentiType;

public class DocumentoHelper {

    private DocumentiType doc;
    private String procedimentoId;
    private String interventoId;
    private String fileTempId;
    private boolean richiedeFirma;
    private boolean obbligatorio;
    private String noteFrontend;
    private String indirizzoWeb;
    private String tipoDownload;
    private Integer codiceOggettoModello;

    public String getNoteFrontend() {

	return org.apache.commons.lang.StringEscapeUtils.escapeJavaScript(noteFrontend);
    }

    public void setNoteFrontend(String noteFrontend) {

	this.noteFrontend = noteFrontend;
    }

    public String getIndirizzoWeb() {

	return indirizzoWeb;
    }

    public void setIndirizzoWeb(String indirizzoWeb) {

	this.indirizzoWeb = indirizzoWeb;
    }

    public String getTipoDownload() {

	return tipoDownload;
    }

    public void setTipoDownload(String tipoDownload) {

	this.tipoDownload = tipoDownload;
    }

    public DocumentoHelper(DocumentiType doc) {

	this.doc = doc;
    }

    public DocumentiType getDoc() {

	return doc;
    }

    public void setDoc(DocumentiType doc) {

	this.doc = doc;
    }

    public boolean isRichiedeFirma() {

	return richiedeFirma;
    }

    public void setRichiedeFirma(boolean richiedeFirma) {

	this.richiedeFirma = richiedeFirma;
    }

    public boolean isObbligatorio() {

	return obbligatorio;
    }

    public void setObbligatorio(boolean obbligatorio) {

	this.obbligatorio = obbligatorio;
    }

    public String getProcedimentoId() {

	return procedimentoId;
    }

    public void setProcedimentoId(String procedimentoId) {

	this.procedimentoId = procedimentoId;
    }

    public String getInterventoId() {

	return interventoId;
    }

    public void setInterventoId(String interventoId) {

	this.interventoId = interventoId;
    }

    public String getFileTempId() {

	return fileTempId;
    }

    public void setFileTempId(String fileTempId) {

	this.fileTempId = fileTempId;
    }

    public Integer getCodiceOggettoModello() {

	return codiceOggettoModello;
    }

    public void setCodiceOggettoModello(Integer codiceOggettoModello) {

	this.codiceOggettoModello = codiceOggettoModello;
    }
}
