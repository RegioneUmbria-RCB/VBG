package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class InterventoSimpleBean {

    private Integer id;
    private String scCodice;
    private String text;
    private Boolean hasChilds;
    private Integer scOrdine;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getScCodice() {

	return scCodice;
    }

    public void setScCodice(String scCodice) {

	this.scCodice = scCodice;
    }

    public String getText() {

	return text;
    }

    public void setText(String text) {

	this.text = text;
    }

    public Boolean getHasChilds() {

	return hasChilds;
    }

    public void setHasChilds(Boolean hasChilds) {

	this.hasChilds = hasChilds;
    }

    public Integer getScOrdine() {

	return scOrdine;
    }

    public void setScOrdine(Integer scOrdine) {

	this.scOrdine = scOrdine;
    }
}
