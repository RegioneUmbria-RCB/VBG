package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzemappaliDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
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
public class IstanzemappaliDAOImpl extends BaseDAOImpl<Istanzemappali, PkId> implements IstanzemappaliDAO {

    @Override
    public Class<Istanzemappali> getEntityClass() {

	return Istanzemappali.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Istanzemappali findByPrimarioIstanza(Istanze istanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("istanza.id.codice", istanza.getId().getCodice()));
	det.add(Restrictions.eq("primario", true));
	List<Istanzemappali> istanzemappalis = getHibernateTemplate().findByCriteria(det);
	if (!istanzemappalis.isEmpty()) {
	    return istanzemappalis.get(0);
	}
	return null;
    }
}
