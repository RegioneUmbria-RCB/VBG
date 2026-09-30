package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import it.gruppoinit.pal.gp.core.domain.BorsellinoRicariche;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.TipoRicaricaEnum;

public class TagliRicaricaAbbonamento {

    @XmlElement
    private Integer importo;

    public Integer getImporto() {

	return importo;
    }

    public void setImporto(Integer importo) {

	this.importo = importo;
    }

    @XmlTransient
    public static List<TagliRicaricaAbbonamento> fromRicaricheFisse(List<BorsellinoRicariche> ricariche) {

	if (ricariche == null || ricariche.isEmpty()) {
	    return new ArrayList<TagliRicaricaAbbonamento>();
	}
	List<TagliRicaricaAbbonamento> ret = new ArrayList<TagliRicaricaAbbonamento>();
	for (BorsellinoRicariche b : ricariche) {
	    if (TipoRicaricaEnum.FISSO.name().equalsIgnoreCase(b.getTipo())) {
		TagliRicaricaAbbonamento el = new TagliRicaricaAbbonamento();
		el.setImporto(b.getImporto().intValue());
		ret.add(el);
	    }
	}
	return ret;
    }
}
