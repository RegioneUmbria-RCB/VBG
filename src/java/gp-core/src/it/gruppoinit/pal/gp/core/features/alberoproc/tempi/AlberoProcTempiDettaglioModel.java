package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.core.domain.TempiFoD;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class AlberoProcTempiDettaglioModel {

    @XmlElement(name = "id")
    private Integer id;
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

    public static AlberoProcTempiDettaglioModel FromTempiFoD(TempiFoD dettaglio) {

	if (dettaglio == null) {
	    return null;
	}
	AlberoProcTempiDettaglioModel model = new AlberoProcTempiDettaglioModel();
	model.setId(dettaglio.getId().getCodice());
	model.setTitolo(dettaglio.getTitolo());
	model.setDescrizione(dettaglio.getDescrizione());
	model.setTipo(dettaglio.getTipo());
	model.setGiorni(dettaglio.getGiorni());
	model.setScadenza(Utilities.formatDate(dettaglio.getScadenza(), "dd/MM/yyyy"));
	model.setOrdine(dettaglio.getOrdine());
	return model;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
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
