/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ContiDAO;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * 
 */
@Repository
public class ContiDAOImpl extends BaseDAOImpl<Conti, PkId> implements ContiDAO {

    @Override
    public Class<Conti> getEntityClass() {

	return Conti.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Conti> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Conti> findByDescrizione(String descrizione) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (descrizione != null && !descrizione.equals("") && !descrizione.equals("%")) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
	}
	return (List<Conti>) getHibernateTemplate().findByCriteria(det);
    }
}
