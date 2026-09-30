package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author
 */
public interface EsportazioniDAO extends BaseDAO<Esportazioni, PkId> {

    public List<Esportazioni> findAll(Integer firstResult, Integer maxResult);

    public void inserisci(Esportazioni esportazioni);
}
