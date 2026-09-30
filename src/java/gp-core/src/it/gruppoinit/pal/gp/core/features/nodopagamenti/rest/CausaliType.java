package it.gruppoinit.pal.gp.core.features.nodopagamenti.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "causali")
@XmlSeeAlso({ ParametriType.class })
public class CausaliType {

    @XmlElement(name = "id")
    private String id;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "codice_versamento")
    private String codiceVersamento;
    @XmlElement(name = "mappatura_client")
    private String mappaturaClient;
    @XmlElement(name = "parametri")
    private List<ParametriType> parametri;

    public CausaliType() {

	// il costruttore rimane vuoto
    }

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getCodiceVersamento() {

	return codiceVersamento;
    }

    public void setCodiceVersamento(String codiceVersamento) {

	this.codiceVersamento = codiceVersamento;
    }

    public String getMappaturaClient() {

	return mappaturaClient;
    }

    public void setMappaturaClient(String mappaturaClient) {

	this.mappaturaClient = mappaturaClient;
    }

    public List<ParametriType> getParametri() {

	if (parametri == null) {
	    parametri = new ArrayList<ParametriType>();
	}
	return parametri;
    }

    public void setParametri(List<ParametriType> parametri) {

	this.parametri = parametri;
    }
}
