package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzepeopledDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzepeopled;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 */
@Repository
public class IstanzepeopledDAOImpl extends BaseDAOImpl<Istanzepeopled, PkId> implements IstanzepeopledDAO {

    @Override
    public Class<Istanzepeopled> getEntityClass() {

	return Istanzepeopled.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Istanzepeopled findByIstanza(Istanze istanze) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("istanze.id.codice", istanze.getId().getCodice()));
	List<Istanzepeopled> istanzepeopleds = getHibernateTemplate().findByCriteria(det);
	if (!istanzepeopleds.isEmpty()) {
	    return istanzepeopleds.get(0);
	}
	return null;
    }
}
