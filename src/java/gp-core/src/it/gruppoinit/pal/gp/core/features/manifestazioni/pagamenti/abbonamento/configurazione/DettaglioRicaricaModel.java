package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.core.domain.BorsellinoRicariche;

@XmlAccessorType(XmlAccessType.FIELD)
public class DettaglioRicaricaModel {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "tipologia")
    private TipologiaRicaricaModel tipologia;
    @XmlElement(name = "etichetta")
    private String etichetta;
    @XmlElement(name = "importo")
    private Integer importo;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public TipologiaRicaricaModel getTipologia() {

	return tipologia;
    }

    public void setTipologia(TipologiaRicaricaModel tipologia) {

	this.tipologia = tipologia;
    }

    public Integer getImporto() {

	return importo;
    }

    public void setImporto(Integer importo) {

	this.importo = importo;
    }

    public String getEtichetta() {

	return etichetta;
    }

    public void setEtichetta(String etichetta) {

	this.etichetta = etichetta;
    }

    public static DettaglioRicaricaModel fromBorsellinoRicariche(BorsellinoRicariche ricarica) {

	if (ricarica == null || ricarica.getId() == null || ricarica.getId().getCodice() == null) {
	    return new DettaglioRicaricaModel();
	}
	DettaglioRicaricaModel model = new DettaglioRicaricaModel();
	model.setId(ricarica.getId().getCodice());
	model.setImporto(ricarica.getImporto());
	model.setTipologia(TipologiaRicaricaModel.fromValue(ricarica.getTipo()));
	model.setEtichetta(TipoRicaricaEnum.valueOf(ricarica.getTipo()).getEtichetta());
	return model;
    }
}
