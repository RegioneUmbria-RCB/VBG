package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface AllegatiDAO extends BaseDAO<Allegati, PkId> {

    /**
     * Lista di allegati filtrati per idcomune e ordinata per il campo allegato
     * 
     */
    public List<Allegati> findAll(Integer firstResult, Integer maxResult);
}
