package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.GraduatorietCom;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface GraduatorietComDAO extends BaseDAO<GraduatorietCom, PkId> {

    public List<GraduatorietCom> findAll(Integer firstResult, Integer maxResult);
}
