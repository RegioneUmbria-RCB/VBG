package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.IstanzeRiDAO;
import it.gruppoinit.pal.gp.core.domain.IstanzeRi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface IstanzeRiService extends BaseService<IstanzeRi, PkId> {

    /**
     * @see IstanzeRiDAO#findAll(Integer, Integer)
     */
    public List<IstanzeRi> findAll(Integer firstResult, Integer maxResult);

    public List<IstanzeRi> findByIstanza(Integer codiceIstanza);
}
