package it.gruppoinit.stc.dao.impl;

import it.gruppoinit.stc.dao.BaseDAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class BaseDAOImpl<E, F extends Serializable> extends HibernateDaoSupport implements BaseDAO<E, F> {

    /**
     * metodo per l'autowire della sessionFactory
     * 
     * @param sessionFactoryWrapper
     */
    @Autowired
    public void setSessionFactoryWrapper(@Qualifier("sessionFactory") SessionFactory sessionFactoryWrapper) {
	this.setSessionFactory(sessionFactoryWrapper);
    }

    @Override
    public void insert(E entity) {
	getHibernateTemplate().merge(entity);

    }

    @Override
    public void update(E entity) {
	getHibernateTemplate().merge(entity);

    }

    @Override
    public void delete(E entity) {
	getHibernateTemplate().delete(entity);

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getDefaultCriteria();

	if (null != firstResult && null != maxResult) {
	    return (List<E>) getHibernateTemplate().findByCriteria(det, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<E>) getHibernateTemplate().findByCriteria(det);
	}
    }

    @Override
    @SuppressWarnings("unchecked")
    public E findById(F id) {
	if (null == id) {
	    return null;
	}
	return (E) getHibernateTemplate().get(getEntityClass(), id);
    }

    @Override
    public abstract Class<E> getEntityClass();

    /**
     * Crea un oggetto DetachedCriteria
     * 
     * @return DetachedCriteria
     */
    protected DetachedCriteria getDefaultCriteria() {
	DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
	return criteria;
    }

}
