package it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.ErroreBackofficeType;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType;

public class InsertOnereFallito extends InsertOnereResponse {

    public InsertOnereFallito(List<ErroreBackofficeType> errori) {

	this.setEsitoOperazione(new EsitoOperazioneType(0, errori));
    }

    public InsertOnereFallito(ErroreBackofficeType errore) {

	List<ErroreBackofficeType> errori = new ArrayList<ErroreBackofficeType>();
	errori.add(errore);
	this.setEsitoOperazione(new EsitoOperazioneType(0, errori));
    }

    public String getTestoErrore() {

	StringBuffer errori = new StringBuffer();
	for (ErroreBackofficeType erroreBackofficeType : this.getEsitoOperazione().getListaErrori()) {
	    errori.append(erroreBackofficeType.getDescrizione()).append("\n");
	}
	return errori.toString();
    }
}
