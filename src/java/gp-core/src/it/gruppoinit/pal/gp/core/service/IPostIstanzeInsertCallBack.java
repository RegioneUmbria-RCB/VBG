package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.service.exception.OperazioniAutomaticheException;

public interface IPostIstanzeInsertCallBack {

    void callback(Istanze istanza) throws OperazioniAutomaticheException;
}
