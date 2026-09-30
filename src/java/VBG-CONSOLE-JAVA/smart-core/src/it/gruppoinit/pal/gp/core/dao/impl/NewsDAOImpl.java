package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.NewsDAO;
import it.gruppoinit.pal.gp.core.domain.News;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class NewsDAOImpl extends BaseDAOImpl<News, PkId> implements NewsDAO {

    @Override
    public Class<News> getEntityClass() {

	return News.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<News> findByFilter(Set<Software> softwareList) {

	DetachedCriteria detachedCriteria = getIdcomuneCriteria();
	if (softwareList.size() != 0) {
	    detachedCriteria.add(Restrictions.in("software", softwareList));
	}
	return getHibernateTemplate().findByCriteria(detachedCriteria);
    }
}
