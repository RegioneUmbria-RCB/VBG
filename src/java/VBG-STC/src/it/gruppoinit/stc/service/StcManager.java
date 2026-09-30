package it.gruppoinit.stc.service;

import it.init.sigepro.rte.NotificaAttivitaRequest;
import it.init.sigepro.rte.NotificaAttivitaResponse;

public interface StcManager {

    public NotificaAttivitaResponse notificaAttivita(NotificaAttivitaRequest request);
}
