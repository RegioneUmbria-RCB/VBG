package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Concessionitipi;

import java.util.List;

public interface ConcessionitipiDAO extends BaseDAO<Concessionitipi, String> {

    public List<Concessionitipi> findByDescrizione(Concessionitipi entity);

    public List<Concessionitipi> findAll(Integer firstResult, Integer maxResult);
}
