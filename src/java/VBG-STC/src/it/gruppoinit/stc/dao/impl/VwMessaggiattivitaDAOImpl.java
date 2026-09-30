package it.gruppoinit.stc.dao.impl;

import it.gruppoinit.stc.dao.VwMessaggiattivitaDAO;
import it.gruppoinit.stc.domain.VwMessaggiattivita;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VwMessaggiattivitaDAOImpl extends BaseDAOImpl<VwMessaggiattivita, Integer> implements VwMessaggiattivitaDAO {

    @Override
    public Class<VwMessaggiattivita> getEntityClass() {

	return VwMessaggiattivita.class;
    }

    @Override
    public Collection<VwMessaggiattivita> findByFilter(VwMessaggiattivita vwMessaggiattivita, Integer firstResult, Integer maxResult) {

	DetachedCriteria det = criteriaFromFilter(vwMessaggiattivita);
	if (null != firstResult && null != maxResult) {
	    return (List<VwMessaggiattivita>) getHibernateTemplate().findByCriteria(det, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<VwMessaggiattivita>) getHibernateTemplate().findByCriteria(det);
	}
    }

    @Override
    public int countByFilter(VwMessaggiattivita vwMessaggiattivita) {

	DetachedCriteria det = criteriaFromFilter(vwMessaggiattivita);
	det.setProjection(Projections.rowCount());
	int ris = ((Integer) getHibernateTemplate().findByCriteria(det).get(0)).intValue();
	return ris;
    }

    private DetachedCriteria criteriaFromFilter(VwMessaggiattivita vwMessaggiattivita) {

	DetachedCriteria det = getDefaultCriteria();
	if (vwMessaggiattivita != null) {
	    if (vwMessaggiattivita.getId() != null) {
		det.add(Restrictions.eq("id", vwMessaggiattivita.getId()));
	    }
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getMittIdnodo())) {
	    det.add(Restrictions.ilike("mittIdnodo", vwMessaggiattivita.getMittIdnodo()));
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getMittIdente())) {
	    det.add(Restrictions.ilike("mittIdente", vwMessaggiattivita.getMittIdente()));
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getMittIdpratica())) {
	    det.add(Restrictions.ilike("mittIdpratica", vwMessaggiattivita.getMittIdpratica()));
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getMittIdsportello())) {
	    det.add(Restrictions.ilike("mittIdsportello", vwMessaggiattivita.getMittIdsportello()));
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getMittNumpratica())) {
	    det.add(Restrictions.ilike("mittNumpratica", vwMessaggiattivita.getMittNumpratica(), MatchMode.ANYWHERE));
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getMittAttId())) {
	    det.add(Restrictions.like("mittAttId", vwMessaggiattivita.getMittAttId()));
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getMittAttTipo())) {
	    det.add(Restrictions.like("mittAttTipo", vwMessaggiattivita.getMittAttTipo()));
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getMittAttIdproc())) {
	    det.add(Restrictions.like("mittAttIdproc", vwMessaggiattivita.getMittAttIdproc()));
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getDestIdnodo())) {
	    det.add(Restrictions.ilike("destIdnodo", vwMessaggiattivita.getDestIdnodo()));
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getDestIdente())) {
	    det.add(Restrictions.ilike("destIdente", vwMessaggiattivita.getDestIdente()));
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getDestIdpratica())) {
	    det.add(Restrictions.ilike("destIdpratica", vwMessaggiattivita.getDestIdpratica()));
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getDestIdsportello())) {
	    det.add(Restrictions.ilike("destIdsportello", vwMessaggiattivita.getDestIdsportello()));
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getDestNumpratica())) {
	    det.add(Restrictions.ilike("destNumpratica", vwMessaggiattivita.getDestNumpratica(), MatchMode.ANYWHERE));
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getDestAttId())) {
	    det.add(Restrictions.ilike("destAttId", vwMessaggiattivita.getDestAttId()));
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getDestAttTipo())) {
	    det.add(Restrictions.ilike("destAttTipo", vwMessaggiattivita.getDestAttTipo()));
	}
	if (StringUtils.isNotBlank(vwMessaggiattivita.getDestAttIdproc())) {
	    det.add(Restrictions.ilike("destAttIdproc", vwMessaggiattivita.getDestAttIdproc()));
	}
	return det;
    }
}
