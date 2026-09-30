package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AmministrProtocollo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author 
 */
public interface AmministrProtocolloDAO extends BaseDAO<AmministrProtocollo, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<AmministrProtocollo> findAll(Integer firstResult, Integer maxResult);
}
