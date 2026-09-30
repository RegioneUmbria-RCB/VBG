package it.gruppoinit.pal.gp.core.features.nodopagamenti.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "parametri")
@XmlSeeAlso({ ProfiliListType.class })
public class ParametriListType {

    @XmlElement(name = "profili")
    private List<String> parametri;

    public List<String> getParametri() {

	return parametri;
    }

    public void setParametri(List<String> parametri) {

	this.parametri = parametri;
    }
}
