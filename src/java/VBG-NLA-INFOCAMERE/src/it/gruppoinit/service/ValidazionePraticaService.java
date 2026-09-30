package it.gruppoinit.service;

import it.init.sigepro.rte.NotificaAttivitaResponse;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.SportelloType;

public interface ValidazionePraticaService {

    public NotificaAttivitaResponse insertAttivitaDiValidazione(DettaglioPraticaType dettaglioPraticaType, SportelloType nodoMittente,
	    SportelloType nodoDestinatario, String software, String tokenApp, String tokenStc);
}
