package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PosteggiDisabilitatiResponse {

    @XmlElement(name = "disabilitati")
    private List<Integer> disabilitati = new ArrayList<Integer>();

    public List<Integer> getDisabilitati() {

	return disabilitati;
    }

    public void setDisabilitati(List<Integer> disabilitati) {

	this.disabilitati = disabilitati;
    }

    public PosteggiDisabilitatiResponse() {

	super();
    }

    public PosteggiDisabilitatiResponse(List<Integer> disabilitati) {

	if (disabilitati != null) {
	    this.disabilitati = disabilitati;
	}
    }
}
