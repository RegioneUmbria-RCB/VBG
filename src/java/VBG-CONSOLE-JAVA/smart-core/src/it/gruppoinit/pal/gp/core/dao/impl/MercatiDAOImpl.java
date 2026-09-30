/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiEnum;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class MercatiDAOImpl extends BaseDAOImpl<Mercati, PkId> implements MercatiDAO {

    @Override
    public Class<Mercati> getEntityClass() {

	return Mercati.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mercati> findByDescrizione(String descrizione) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (descrizione != null && !descrizione.equals("") && !descrizione.equals("%")) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
	}
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mercati> findByDescrizione(String descrizione, MercatiEnum mercatiEnum) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (descrizione != null && !descrizione.equals("") && !descrizione.equals("%")) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
	}
	switch (mercatiEnum) {
	case ACTIVE:
	    det.add(Restrictions.eq("attivo", true));
	    break;
	case DISABLED:
	    det.add(Restrictions.eq("attivo", false));
	    break;
	}
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mercati> findByFlagContabilita(String descrizione, boolean isFlagContabilita, MercatiEnum mercatiEnum) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (descrizione != null && !descrizione.equals("") && !descrizione.equals("%")) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
	}
	switch (mercatiEnum) {
	case ACTIVE:
	    det.add(Restrictions.eq("attivo", true));
	    break;
	case DISABLED:
	    det.add(Restrictions.eq("attivo", false));
	    break;
	}
	det.add(Restrictions.eq("flagContabilita", isFlagContabilita));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Mercati> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mercati> findAllMercatiAttivi(Integer firstResult, Integer maxResult) {

	DetachedCriteria detachedCriteria;
	detachedCriteria = getIdcomuneAndSoftwareCriteria();
	detachedCriteria.addOrder(Order.asc("descrizione"));
	detachedCriteria.add(Restrictions.eq("attivo", true));
	if (null != firstResult && null != maxResult) {
	    return (List<Mercati>) getHibernateTemplate().findByCriteria(detachedCriteria, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<Mercati>) getHibernateTemplate().findByCriteria(detachedCriteria);
	}
    }
}
