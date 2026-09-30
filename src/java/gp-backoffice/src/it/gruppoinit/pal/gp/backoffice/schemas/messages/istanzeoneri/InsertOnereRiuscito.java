package it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri;

import java.math.BigInteger;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType;

public class InsertOnereRiuscito extends InsertOnereResponse {

    public InsertOnereRiuscito(BigInteger codiceOnere) {

	this.setEsitoOperazione(new EsitoOperazioneType(1));
	this.setCodiceonere(codiceOnere);
    }
}
