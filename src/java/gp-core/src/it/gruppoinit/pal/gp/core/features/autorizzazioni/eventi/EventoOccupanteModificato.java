package it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;

public class EventoOccupanteModificato implements IEvent {

    private Integer idAuOConc;
    private Integer vecchioOccupante;
    private Integer nuovoOccupante;
    private EsitoModificaOccupante esito;

    public EventoOccupanteModificato(Integer idAuOConc, Integer vecchioOccupante, Integer nuovoOccupante, EsitoModificaOccupante esito) {

	super();
	this.idAuOConc = idAuOConc;
	this.vecchioOccupante = vecchioOccupante;
	this.nuovoOccupante = nuovoOccupante;
	this.esito = esito;
    }

    public Integer getIdAuOConc() {

	return idAuOConc;
    }

    public Integer getVecchioOccupante() {

	return vecchioOccupante;
    }

    public Integer getNuovoOccupante() {

	return nuovoOccupante;
    }

    public EsitoModificaOccupante getEsito() {

	if (esito == null) {
	    esito = new EsitoModificaOccupante();
	}
	return esito;
    }
}
