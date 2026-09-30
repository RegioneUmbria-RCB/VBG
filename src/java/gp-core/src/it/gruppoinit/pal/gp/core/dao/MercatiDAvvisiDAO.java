package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MercatiDAvvisi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface MercatiDAvvisiDAO extends BaseDAO<MercatiDAvvisi, PkId> {

    /**
     * TODO inserire il commento
     * 
     */
    public List<MercatiDAvvisi> findAll(Integer firstResult, Integer maxResult);

    public List<Integer> findAvvisiByPosteggioAndAnagrafeDistincCausaliOneri(Integer codiceAnagrafe, Integer codicePosteggio);
}
