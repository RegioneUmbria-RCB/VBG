package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.MercatiD2cAssegnazDAO;
import it.gruppoinit.pal.gp.core.domain.MercatiD2cAssegnaz;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface MercatiD2cAssegnazService extends BaseService<MercatiD2cAssegnaz, PkId> {

    /**
     * @see MercatiD2cAssegnazDAO#findAll(Integer, Integer)
     */
    public List<MercatiD2cAssegnaz> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di campi dinamici filtrati per mercato
     * 
     * @param codiceMercato
     * @return
     */
    public List<MercatiD2cAssegnaz> findByMercato(Integer codiceMercato);
}
