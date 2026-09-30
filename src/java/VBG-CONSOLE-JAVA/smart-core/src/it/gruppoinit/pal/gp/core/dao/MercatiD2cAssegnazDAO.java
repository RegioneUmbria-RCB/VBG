package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MercatiD2cAssegnaz;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface MercatiD2cAssegnazDAO extends BaseDAO<MercatiD2cAssegnaz, PkId> {

    public List<MercatiD2cAssegnaz> findAll(Integer firstResult, Integer maxResult);
}
