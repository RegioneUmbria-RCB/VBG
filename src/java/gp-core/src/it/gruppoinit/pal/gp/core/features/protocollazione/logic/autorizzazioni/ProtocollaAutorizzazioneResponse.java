package it.gruppoinit.pal.gp.core.features.protocollazione.logic.autorizzazioni;

import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;

public class ProtocollaAutorizzazioneResponse {

    private Movimenti movimento;
    private DatiProtocolloResponseType datiProtocollo;

    public Movimenti getMovimento() {

	return movimento;
    }

    public void setMovimento(Movimenti movimento) {

	this.movimento = movimento;
    }

    public DatiProtocolloResponseType getDatiProtocollo() {

	return datiProtocollo;
    }

    public void setDatiProtocollo(DatiProtocolloResponseType datiProtocollo) {

	this.datiProtocollo = datiProtocollo;
    }
}
