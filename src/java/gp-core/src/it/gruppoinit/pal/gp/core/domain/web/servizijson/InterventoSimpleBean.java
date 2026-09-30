package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class InterventoSimpleBean {

    private Integer id;
    private String text;
    private String note;
    private Boolean hasChilds;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

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

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }
}
