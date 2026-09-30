package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ComunicazioneMassivaRigaModel;

public class ComunicazioneMassivaRigaModelGen extends ComunicazioneMassivaRigaModel {

    @XmlElement(name = "autorizzazioni")
    private List<String> autorizzazioni;
    @XmlElement(name = "istanze")
    private List<String> istanze;

    public List<String> getAutorizzazioni() {

	return autorizzazioni;
    }

    public void setAutorizzazioni(List<String> autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
    }

    public List<String> getIstanze() {

	return istanze;
    }

    public void setIstanze(List<String> istanze) {

	this.istanze = istanze;
    }
}
