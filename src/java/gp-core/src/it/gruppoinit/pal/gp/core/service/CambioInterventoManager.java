package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.web.CambioInterventoCommand;

public interface CambioInterventoManager {

    /**
     * Popola la sezione documenti (proprietà "docs") e ritorna l'oggetto CambioInterventoCommand.
     * 
     * @param istanza
     * @param cambioInterventoCommand
     * @return
     */
    public CambioInterventoCommand popolaCambioInterventoCommandDocumenti(Istanze istanza, CambioInterventoCommand cambioInterventoCommand);
}
