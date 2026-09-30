package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "procedimento")
public class ProcedimentoSimpleBean {

    @XmlElement(name = "id")
    private String id;
    @XmlElement(name = "descrizione")
    private String text;
    @XmlElement(name = "sotto_cartelle")
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
