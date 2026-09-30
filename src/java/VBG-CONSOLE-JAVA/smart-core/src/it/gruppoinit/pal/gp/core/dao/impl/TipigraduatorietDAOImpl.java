package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipigraduatorietDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipigraduatoriet;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipigraduatorietDAOImpl extends BaseDAOImpl<Tipigraduatoriet, PkId> implements TipigraduatorietDAO {

    @Override
    public Class<Tipigraduatoriet> getEntityClass() {

	return Tipigraduatoriet.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tipigraduatoriet> findByTipibando(Tipigraduatoriet tipigraduatoriet) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("tipibando", tipigraduatoriet.getTipibando()));
	return getHibernateTemplate().findByCriteria(det);
    }
}
