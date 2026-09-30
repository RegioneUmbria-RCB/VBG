package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.core.domain.BorsellinoInformative;

public class InformativeAbbonamento {

    @XmlElement
    private Integer id;
    @XmlElement
    private String testo;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getTesto() {

	return testo;
    }

    public void setTesto(String testo) {

	this.testo = testo;
    }

    public static List<InformativeAbbonamento> fromInformative(List<BorsellinoInformative> info) {

	if (info == null || info.isEmpty()) {
	    return new ArrayList<InformativeAbbonamento>();
	}
	List<InformativeAbbonamento> ret = new ArrayList<InformativeAbbonamento>();
	for (BorsellinoInformative b : info) {
	    InformativeAbbonamento el = new InformativeAbbonamento();
	    el.setId(b.getId().getCodice());
	    el.setTesto(b.getInformativa());
	    ret.add(el);
	}
	return ret;
    }
}
