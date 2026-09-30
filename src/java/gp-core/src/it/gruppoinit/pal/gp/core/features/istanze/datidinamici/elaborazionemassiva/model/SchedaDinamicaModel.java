package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model;

import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.core.domain.Dyn2Massiveschede;

public class SchedaDinamicaModel {

    private int idScheda;
    private String descrizione;
    private int ordine;

    public SchedaDinamicaModel() {

    }

    @XmlElement(name = "id_scheda")
    public int getIdScheda() {

	return idScheda;
    }

    public void setIdScheda(int idScheda) {

	this.idScheda = idScheda;
    }

    @XmlElement(name = "descrizione")
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @XmlElement(name = "ordine")
    public int getOrdine() {

	return ordine;
    }

    public void setOrdine(int ordine) {

	this.ordine = ordine;
    }

    public SchedaDinamicaModel(int idScheda, String descrizione, int ordine) {

	this.idScheda = idScheda;
	this.descrizione = descrizione;
	this.ordine = ordine;
    }

    public static SchedaDinamicaModel fromMassiveSchede(Dyn2Massiveschede s) {

	return new SchedaDinamicaModel(s.getId().getFkD2mtId(), s.getDyn2Modellit().getDescrizione(), s.getOrdine());
    }
}
