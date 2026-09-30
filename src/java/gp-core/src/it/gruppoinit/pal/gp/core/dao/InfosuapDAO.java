package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Infosuap;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author lucap
 */
public interface InfosuapDAO extends BaseDAO<Infosuap, PkId> {

    /**
     * Lista di Informazioni del Suap filtrata per Idcomune e ordinata per ordine ASC
     */
    public List<Infosuap> findAll(Integer firstResult, Integer maxResult);
}
