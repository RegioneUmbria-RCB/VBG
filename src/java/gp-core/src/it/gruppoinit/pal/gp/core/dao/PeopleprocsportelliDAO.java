package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Peopleprocsportelli;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface PeopleprocsportelliDAO extends BaseDAO<Peopleprocsportelli, PkId> {

    public List<Peopleprocsportelli> findAll(Integer firstResult, Integer maxResult);

    public <T> void save(T entity);

    public <T> void save(List<T> entityList);
}
