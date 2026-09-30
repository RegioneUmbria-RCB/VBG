package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.CommedilizieConvocazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface CommedilizieConvocazioniDAO extends BaseDAO<CommedilizieConvocazioni, PkId> {

    /**
     * Lista di commisssioni edilizie convocazioni filtrat per idcomune
     * 
     */
    public List<CommedilizieConvocazioni> findAll(Integer firstResult, Integer maxResult);
}
