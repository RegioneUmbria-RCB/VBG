package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.dossier.messages.RiversaIstanzeRequest;
import it.gruppoinit.dossier.messages.RiversaIstanzeResponse;

public interface DossierService {

    public RiversaIstanzeResponse uploadIstanzeInDossier(RiversaIstanzeRequest riversaIstanzeRequest);
}
