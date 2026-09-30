package it.gruppoinit.stc.dao.impl;

import it.gruppoinit.stc.dao.VwMessaggipraticheDAO;
import it.gruppoinit.stc.domain.VwMessaggipratiche;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VwMessaggipraticheDAOImpl extends BaseDAOImpl<VwMessaggipratiche, Integer> implements VwMessaggipraticheDAO {

    @Override
    public Class<VwMessaggipratiche> getEntityClass() {

	return VwMessaggipratiche.class;
    }

    @Override
    public Collection<VwMessaggipratiche> findByFilter(VwMessaggipratiche vwMessaggipratiche, Integer firstResult, Integer maxResult) {

	DetachedCriteria det = criteriaFromFilter(vwMessaggipratiche);
	if (null != firstResult && null != maxResult) {
	    return (List<VwMessaggipratiche>) getHibernateTemplate().findByCriteria(det, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<VwMessaggipratiche>) getHibernateTemplate().findByCriteria(det);
	}
    }

    @Override
    public int countByFilter(VwMessaggipratiche vwMessaggipratiche) {

	DetachedCriteria det = criteriaFromFilter(vwMessaggipratiche);
	det.setProjection(Projections.rowCount());
	int ris = ((Integer) getHibernateTemplate().findByCriteria(det).get(0)).intValue();
	return ris;
    }

    private DetachedCriteria criteriaFromFilter(VwMessaggipratiche vwMessaggipratiche) {

	DetachedCriteria det = getDefaultCriteria();
	if (vwMessaggipratiche != null) {
	    if (vwMessaggipratiche.getId() != null) {
		det.add(Restrictions.eq("id", vwMessaggipratiche.getId()));
	    }
	    if (StringUtils.isNotBlank(vwMessaggipratiche.getMittIdnodo())) {
		det.add(Restrictions.ilike("mittIdnodo", vwMessaggipratiche.getMittIdnodo()));
	    }
	    if (StringUtils.isNotBlank(vwMessaggipratiche.getMittIdente())) {
		det.add(Restrictions.ilike("mittIdente", vwMessaggipratiche.getMittIdente()));
	    }
	    if (StringUtils.isNotBlank(vwMessaggipratiche.getMittIdpratica())) {
		det.add(Restrictions.ilike("mittIdpratica", vwMessaggipratiche.getMittIdpratica(), MatchMode.START));
	    }
	    if (StringUtils.isNotBlank(vwMessaggipratiche.getMittIdsportello())) {
		det.add(Restrictions.ilike("mittIdsportello", vwMessaggipratiche.getMittIdsportello()));
	    }
	    if (StringUtils.isNotBlank(vwMessaggipratiche.getMittNumpratica())) {
		det.add(Restrictions.ilike("mittNumpratica", vwMessaggipratiche.getMittNumpratica(), MatchMode.ANYWHERE));
	    }
	    if (StringUtils.isNotBlank(vwMessaggipratiche.getDestIdnodo())) {
		det.add(Restrictions.ilike("destIdnodo", vwMessaggipratiche.getDestIdnodo()));
	    }
	    if (StringUtils.isNotBlank(vwMessaggipratiche.getDestIdente())) {
		det.add(Restrictions.ilike("destIdente", vwMessaggipratiche.getDestIdente()));
	    }
	    if (StringUtils.isNotBlank(vwMessaggipratiche.getDestIdpratica())) {
		det.add(Restrictions.ilike("destIdpratica", vwMessaggipratiche.getDestIdpratica(), MatchMode.START));
	    }
	    if (StringUtils.isNotBlank(vwMessaggipratiche.getDestIdsportello())) {
		det.add(Restrictions.ilike("destIdsportello", vwMessaggipratiche.getDestIdsportello()));
	    }
	    if (StringUtils.isNotBlank(vwMessaggipratiche.getDestNumpratica())) {
		det.add(Restrictions.ilike("destNumpratica", vwMessaggipratiche.getDestNumpratica(), MatchMode.ANYWHERE));
	    }
	}
	return det;
    }
}
