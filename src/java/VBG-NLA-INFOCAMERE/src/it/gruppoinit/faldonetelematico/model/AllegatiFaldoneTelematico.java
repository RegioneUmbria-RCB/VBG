package it.gruppoinit.faldonetelematico.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "allegati", propOrder = { "codiceAllegati", "urnAllegati" })
public class AllegatiFaldoneTelematico {

    @XmlElement(name = "codice_allegato")
    List<CodiceAllegato> codiceAllegati;
    @XmlElement(name = "urn_allegato")
    List<UrnAllegato> urnAllegati;

    public List<CodiceAllegato> getCodiceAllegati() {

	return codiceAllegati;
    }

    public void setCodiceAllegati(List<CodiceAllegato> codiceAllegati) {

	this.codiceAllegati = codiceAllegati;
    }

    public List<UrnAllegato> getUrnAllegati() {

	return urnAllegati;
    }

    public void setUrnAllegati(List<UrnAllegato> urnAllegati) {

	this.urnAllegati = urnAllegati;
    }
}