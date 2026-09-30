package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.PeopleprocsportelliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Peopleprocsportelli;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author
 */
@Repository
public class PeopleprocsportelliDAOImpl extends BaseDAOImpl<Peopleprocsportelli, PkId> implements PeopleprocsportelliDAO {

    @Override
    public Class<Peopleprocsportelli> getEntityClass() {

	return Peopleprocsportelli.class;
    }

    @Override
    public List<Peopleprocsportelli> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }

    @Override
    public <T> void save(T entity) {

	_validateEntityForInsertOrUpdate2(entity);
	getHibernateTemplate().merge(entity);
    }

    @Override
    public <T> void save(List<T> entityList) {

	for (T entity : entityList) {
	    _validateEntityForInsertOrUpdate2(entity);
	    getHibernateTemplate().merge(entity);
	}
    }
}
