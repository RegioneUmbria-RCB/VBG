package it.gruppoinit.pal.gp.pay.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.dao.PayRegcausaliParametriDAO;
import it.gruppoinit.pal.gp.pay.domain.PayRegcausaliParametri;

@Repository
public class PayRegcausaliParametriDAOImpl extends BaseDAOImpl<PayRegcausaliParametri, PkId> implements PayRegcausaliParametriDAO {

    @Override
    public Class<PayRegcausaliParametri> getEntityClass() {

	return PayRegcausaliParametri.class;
    }

    @Override
    public String findValoreByCausaleAndChiave(Integer idCausale, String chiave) {

	DetachedCriteria crit = getIdcomuneCriteria();
	crit.add(Restrictions.eq("payRegistrazioniCausaliId", idCausale));
	crit.add(Restrictions.eq("chiave", chiave));
	List<PayRegcausaliParametri> result = (List<PayRegcausaliParametri>) getHibernateTemplate().findByCriteria(crit);
	if (!result.isEmpty()) {
	    return result.get(0).getValore();
	}
	return null;
    }

    @Override
    public List<PayRegcausaliParametri> findByCausale(Integer idCausale) {

	DetachedCriteria crit = getIdcomuneCriteria();
	crit.add(Restrictions.eq("payRegistrazioniCausaliId", idCausale));
	List<PayRegcausaliParametri> result = (List<PayRegcausaliParametri>) getHibernateTemplate().findByCriteria(crit);
	return result;
    }
}
