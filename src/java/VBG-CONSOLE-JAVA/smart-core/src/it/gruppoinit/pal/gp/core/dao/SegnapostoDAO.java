package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Segnaposto;


public interface SegnapostoDAO extends BaseDAO<Segnaposto, Integer> {
    
    public Segnaposto findByTag(String tagContent);
}
