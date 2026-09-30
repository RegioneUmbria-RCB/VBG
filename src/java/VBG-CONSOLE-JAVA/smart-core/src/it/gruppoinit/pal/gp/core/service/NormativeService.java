package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.NormativeDAO;
import it.gruppoinit.pal.gp.core.domain.Normative;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface NormativeService extends BaseService<Normative, PkId> {

    /**
     * @see NormativeDAO#findAll(Integer, Integer)
     */
    public List<Normative> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see NormativeDAO#findByNormativa(String normativa)
     */
    public List<Normative> findByNormativa(String normativa);
}
