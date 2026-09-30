package it.gruppoinit.pal.gp.core.features.nodopagamenti.rest;

import javax.xml.bind.annotation.XmlElement;

public class ProfiliListType {

    @XmlElement(name = "")
    private String profili;

    public String getProfili() {

	return profili;
    }

    public void setProfili(String profili) {

	this.profili = profili;
    }
}
