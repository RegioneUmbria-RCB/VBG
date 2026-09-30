package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.SettoriavvisiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Settoriavvisi;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 * 
 */
@Repository
public class SettoriavvisiDAOImpl extends BaseDAOImpl<Settoriavvisi, PkId> implements SettoriavvisiDAO {

    @Override
    public Class<Settoriavvisi> getEntityClass() {

	return Settoriavvisi.class;
    }

    @Override
    public List<Settoriavvisi> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Settoriavvisi> findByFilter(Settoriavvisi filter) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	if (filter.getSettore() != null && filter.getSettore().getId() != null && !filter.getSettore().getId().getCodicesettore().equals("")) {
	    criteria.createCriteria("settore", "_settore");
	    criteria.add(Restrictions.eq("_settore.id.codicesettore", filter.getSettore().getId().getCodicesettore()));
	}
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
