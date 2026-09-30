/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VwAlberoprocDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwAlberoproc;

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
public class VwAlberoprocDAOImpl extends BaseDAOImpl<VwAlberoproc, PkId> implements VwAlberoprocDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<VwAlberoproc> findByFilter(VwAlberoproc entity) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (entity.getScDescrizione() != null && !entity.getScDescrizione().equals("") && !entity.getScDescrizione().equals("%")) {
	    det.add(Restrictions.ilike("scDescrizione", entity.getScDescrizione(), MatchMode.ANYWHERE));
	}
	if (entity.getId() != null && entity.getId().getCodice() != null && !entity.getId().getIdcomune().equals("")) {
	    det.add(Restrictions.eq("id", entity.getId()));
	}
	return (List<VwAlberoproc>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public Class<VwAlberoproc> getEntityClass() {

	return VwAlberoproc.class;
    }
}
