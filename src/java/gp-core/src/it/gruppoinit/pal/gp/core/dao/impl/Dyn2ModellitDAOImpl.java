package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.Dyn2ModellitDAO;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class Dyn2ModellitDAOImpl extends BaseDAOImpl<Dyn2Modellit, PkId> implements Dyn2ModellitDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Dyn2Modellit> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public Class<Dyn2Modellit> getEntityClass() {

	return Dyn2Modellit.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Dyn2Modellit> findByDescrizione(Dyn2Modellit entity) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (StringUtils.isNotBlank(entity.getDescrizione())) {
	    det.add(Restrictions.ilike("descrizione", entity.getDescrizione(), MatchMode.ANYWHERE));
	}
	det.addOrder(Order.asc("descrizione"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Dyn2Modellit> findAllByDescrizione(String descrizione) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(descrizione)) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
	}
	DetachedCriteria software = det.createCriteria("software");
	software.addOrder(Order.asc("ordine"));
	det.addOrder(Order.asc("descrizione"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Dyn2Modellit> findByDescrizioneAndSoftware(Dyn2Modellit entity, String codicesoftware) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("software.codice", codicesoftware));
	if (StringUtils.isNotBlank(entity.getDescrizione())) {
	    det.add(Restrictions.ilike("descrizione", entity.getDescrizione(), MatchMode.ANYWHERE));
	}
	det.addOrder(Order.asc("descrizione"));
	return getHibernateTemplate().findByCriteria(det);
    }
}
