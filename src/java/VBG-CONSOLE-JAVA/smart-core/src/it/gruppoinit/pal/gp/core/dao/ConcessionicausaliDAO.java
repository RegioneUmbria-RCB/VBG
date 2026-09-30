package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface ConcessionicausaliDAO extends BaseDAO<Concessionicausali, PkId> {

    /**
     * Torna la lista di concessioni causali del modulo software attivo ordinati per la proprietà descrizione dalla A
     * alla Z
     * 
     */
    public List<Concessionicausali> findAll(Integer firstResult, Integer maxResult);

    public List<Concessionicausali> findByDescrizioneAndFlagStorico(Concessionicausali entity);

    public List<Concessionicausali> findAllbyCausaleStorico(boolean isStorico);
}
