package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeeventiListHelper;
import it.gruppoinit.pal.gp.core.domain.web.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeeventiFilter;

import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface IstanzeeventiDAO extends BaseDAO<Istanzeeventi, PkId> {

    /**
     * recupera tutti gli eventi ordinati per data DESC
     * 
     */
    public List<Istanzeeventi> findAll(Integer firstResult, Integer maxResult);

    /**
     * recupera tutti gli eventi in base ai filtri:<br />
     * categorieeventibase,istanze,movimenti,flagLetto,descrizione ordinati per data desc e flagLetto asc.
     */
    public List<Istanzeeventi> findByFilter(IstanzeeventiFilter filter, Integer firstResult, Integer maxResult);

    public List<IstanzeeventiListHelper> findByScadenzarioFilterHelper(BatchScadenzarioFilter batchScadenzarioFilter, Integer firstResult,
	    Integer maxResult);

    public int countByScadenzarioFilterHelper(BatchScadenzarioFilter batchScadenzarioFilter);
}
