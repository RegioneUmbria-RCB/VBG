package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipibandoinputDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibando;
import it.gruppoinit.pal.gp.core.domain.Tipibandoinput;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * @author francescop
 */
@Repository
public class TipibandoinputDAOImpl extends BaseDAOImpl<Tipibandoinput, PkId> implements TipibandoinputDAO {

    @Override
    public Class<Tipibandoinput> getEntityClass() {

	return Tipibandoinput.class;
    }

    @SuppressWarnings("unchecked")
    public List<Tipibandoinput> findTipiBanInp(Tipibandoinput tipibandoinput) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("tipoinput", tipibandoinput.getTipoinput()));
	det.add(Restrictions.eq("tipibando", tipibandoinput.getTipibando()));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tipibandoinput> findByFilterTipobando(Tipibando tipibando) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("tipibando", tipibando));
	det.addOrder(Order.asc("etichetta"));
	return getHibernateTemplate().findByCriteria(det);
    }
}
