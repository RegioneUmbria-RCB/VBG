package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import javax.xml.bind.annotation.XmlElement;

public class TempoFoDModel {

    @XmlElement(name = "id")
    private Integer idTempod;
    @XmlElement(name = "titolo")
    private String titolo;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "tipo")
    private String tipo;
    @XmlElement(name = "giorni")
    private Integer giorni;
    @XmlElement(name = "scadenza")
    private String scadenza;
    @XmlElement(name = "ordine")
    private Integer ordine;

    public Integer getIdTempod() {

	return idTempod;
    }

    public void setIdTempod(Integer idTempod) {

	this.idTempod = idTempod;
    }

    public String getTitolo() {

	return titolo;
    }

    public void setTitolo(String titolo) {

	this.titolo = titolo;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getTipo() {

	return tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }

    public Integer getGiorni() {

	return giorni;
    }

    public void setGiorni(Integer giorni) {

	this.giorni = giorni;
    }

    public String getScadenza() {

	return scadenza;
    }

    public void setScadenza(String scadenza) {

	this.scadenza = scadenza;
    }

    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }
}
