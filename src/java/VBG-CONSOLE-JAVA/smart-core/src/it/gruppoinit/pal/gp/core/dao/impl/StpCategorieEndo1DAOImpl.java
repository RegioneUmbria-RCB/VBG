package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.StpCategorieEndo1DAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpCategorieEndo1;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class StpCategorieEndo1DAOImpl extends BaseDAOImpl<StpCategorieEndo1, PkId> implements StpCategorieEndo1DAO {

    @Override
    public Class<StpCategorieEndo1> getEntityClass() {

	return StpCategorieEndo1.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public StpCategorieEndo1 findByStpCodice(String idcomune, Integer stpCodice) {

	DetachedCriteria criteria = getIdcomuneCriteria(idcomune);
	criteria.add(Restrictions.eq("codiceStp", stpCodice));
	List<StpCategorieEndo1> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public StpCategorieEndo1 findByTipiendo(Tipiendo tipiendo) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("tipiendo", tipiendo));
	List<StpCategorieEndo1> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }
}
