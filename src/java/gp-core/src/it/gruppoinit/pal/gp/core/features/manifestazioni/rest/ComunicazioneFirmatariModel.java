package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.MassiveTFirmatari;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "firmatario")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComunicazioneFirmatariModel {

    @XmlElement(name = "nominativo")
    private String nominativo;
    @XmlElement(name = "firma_completata")
    private boolean firmaCompletata;

    public ComunicazioneFirmatariModel() {

	super();
    }

    public ComunicazioneFirmatariModel(String nominativo, Boolean firmaCompletata) {

	super();
	this.nominativo = nominativo;
	this.firmaCompletata = firmaCompletata;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public boolean isFirmaCompletata() {

	return firmaCompletata;
    }

    public void setFirmaCompletata(boolean firmaCompletata) {

	this.firmaCompletata = firmaCompletata;
    }

    @Override
    public String toString() {

	try {
	    return Utilities.marshalJsonObject(this, this.getClass(), true, Utilities.JAXB_ENCODING_UTF_8);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }

    public static ComunicazioneFirmatariModel fromMassiveTFirmatari(MassiveTFirmatari firmatario) {

	if (firmatario == null || firmatario.getResponsabili() == null || firmatario.getResponsabili().getId() == null) {
	    return null;
	}
	ComunicazioneFirmatariModel model = new ComunicazioneFirmatariModel();
	model.setNominativo(firmatario.getResponsabili().getResponsabile());
	return model;
    }
}