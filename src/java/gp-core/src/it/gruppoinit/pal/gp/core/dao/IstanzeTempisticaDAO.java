package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.IstanzeTempistica;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeTempisticaDAO extends BaseDAO<IstanzeTempistica, PkId> {

    /**
     * non implementato
     * 
     * @throws new
     *             {@link NotImplementedException}
     */
    public List<IstanzeTempistica> findAll(Integer firstResult, Integer maxResult);
}
