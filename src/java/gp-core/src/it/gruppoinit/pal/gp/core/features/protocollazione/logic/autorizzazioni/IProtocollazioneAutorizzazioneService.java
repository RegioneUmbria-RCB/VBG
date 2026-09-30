package it.gruppoinit.pal.gp.core.features.protocollazione.logic.autorizzazioni;

import java.util.Calendar;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.exceptions.ProtocollazioneFallitaException;

public interface IProtocollazioneAutorizzazioneService {

    ProtocollaAutorizzazioneResponse protocolla(Tipologiaregistri registro, String codiceComune, String software, Calendar dataAutorizzazione,
	    Autorizzazioni autorizzazione, int codiceResponsabile, String token) throws ProtocollazioneFallitaException;
}
