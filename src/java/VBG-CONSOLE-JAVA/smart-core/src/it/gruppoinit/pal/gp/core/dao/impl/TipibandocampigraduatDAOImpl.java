package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipibandocampigraduatDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibandocampigraduat;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipibandocampigraduatDAOImpl extends BaseDAOImpl<Tipibandocampigraduat, PkId> implements TipibandocampigraduatDAO {

    @Override
    public Class<Tipibandocampigraduat> getEntityClass() {

	return Tipibandocampigraduat.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tipibandocampigraduat> findByTipigraduatoriet(Tipibandocampigraduat tipibandocampigraduat) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("tipigraduatoriet", tipibandocampigraduat.getTipigraduatoriet()));
	det.addOrder(Order.asc("ordine"));
	return getHibernateTemplate().findByCriteria(det);
    }
}
