package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.VwIstanzeconcessionilistaDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwIstanzeconcessionilista;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface VwIstanzeconcessionilistaService extends BaseService<VwIstanzeconcessionilista, PkId> {

    /**
     * @see VwIstanzeconcessionilistaDAO#findAll(Integer, Integer)
     */
    public List<VwIstanzeconcessionilista> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see VwIstanzeconcessionilistaDAO#findByPosteggioAndUso(MercatiD mercatid, MercatiUso mercatiUso)
     */
    public List<VwIstanzeconcessionilista> findByPosteggioAndUso(MercatiD mercatid, MercatiUso mercatiUso);
}
