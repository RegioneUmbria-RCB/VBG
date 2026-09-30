/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VwOneriregulusDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.VwOneriregulus;
import it.gruppoinit.pal.gp.core.domain.VwOneriregulusId;
import it.gruppoinit.regulus.gestoreincassi.RegulusConstants;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class VwOneriregulusDAOImpl extends BaseDAOImpl<VwOneriregulus, VwOneriregulusId> implements VwOneriregulusDAO {

    @Override
    public Class<VwOneriregulus> getEntityClass() {

	return VwOneriregulus.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VwOneriregulus> getDebtSituationOneriRegulus(String codiceFiscale) {

	DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
	criteria.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	if (codiceFiscale.length() == RegulusConstants.LUNGHEZZA_CODICEFISCALE) {
	    criteria.add(Restrictions.or(Restrictions.ilike("codifiscale", codiceFiscale), Restrictions.ilike("codifiscaleImpresa", codiceFiscale)));
	} else if (codiceFiscale.length() == RegulusConstants.LUNGHEZZA_PARTITAIVA) {
	    criteria.add(Restrictions.or(Restrictions.ilike("partitaiva", codiceFiscale), Restrictions.ilike("partitaivaImpresa", codiceFiscale)));
	}
	criteria.addOrder(Order.asc("id.nrDocumento"));
	List<VwOneriregulus> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }
}
