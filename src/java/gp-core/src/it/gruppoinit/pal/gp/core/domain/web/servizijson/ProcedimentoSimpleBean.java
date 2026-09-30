package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class ProcedimentoSimpleBean {

    private String id;
    private String text;
    private Boolean hasChilds;

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
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
}
