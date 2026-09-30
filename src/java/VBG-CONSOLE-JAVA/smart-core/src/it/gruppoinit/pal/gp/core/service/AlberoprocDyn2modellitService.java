package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellit;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDyn2modellitId;

import java.util.List;

public interface AlberoprocDyn2modellitService extends BaseService<AlberoprocDyn2modellit, AlberoprocDyn2modellitId> {

    /**
     * Ritorna i modelli dinamici (AlberoprocDyn2modellit) associati alla voce dell' albero passato (AlberoProc). Il
     * risultato è ordinato per il campo ordine (Asc) e per il campo descrizione del modello associato (asc)
     * 
     * @param codiceAlberoproc
     * @return
     */
    public List<AlberoprocDyn2modellit> findByAlberoProc(Integer codiceAlberoproc);

    public AlberoprocDyn2modellit findByAlberoProcAndDyn2modellit(Integer codiceAlberoproc, Integer codiceDyn2modellit);
}
