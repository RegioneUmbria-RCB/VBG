package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipiunitamisuraDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiunitamisura;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class TipiunitamisuraDAOImpl extends BaseDAOImpl<Tipiunitamisura, PkId> implements TipiunitamisuraDAO {

    @Override
    public Class<Tipiunitamisura> getEntityClass() {

	return Tipiunitamisura.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipiunitamisura> findByFilter(Tipiunitamisura entity) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.ilike("umDescrbreve", entity.getUmDescrbreve(), MatchMode.ANYWHERE));
	return (List<Tipiunitamisura>) getHibernateTemplate().findByCriteria(det);
    }
}
