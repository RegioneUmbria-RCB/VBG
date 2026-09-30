package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.TmpEsportazioni;

import java.util.List;

/**
 * 
 * @author
 */
public interface TmpEsportazioniDAO extends BaseDAO<TmpEsportazioni, Integer> {

    public List<TmpEsportazioni> findAll(Integer firstResult, Integer maxResult);

    public void deleteBysessionId(String sessionId);
}
