package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author
 */
public interface AlberoprocProtocolloDAO extends BaseDAO<AlberoprocProtocollo, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<AlberoprocProtocollo> findAll(Integer firstResult, Integer maxResult);

    public Object findProprietaByAlberoprocId(Integer codiceAlberoproc, String propertyName, String codiceComune);

    public Map<String, AlberoprocProtocollo> findConfigurazioniHelper(Integer codiceAlberoproc);
}
