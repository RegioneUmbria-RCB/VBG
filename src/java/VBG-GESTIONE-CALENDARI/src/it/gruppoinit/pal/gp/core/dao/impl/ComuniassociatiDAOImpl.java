/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ComuniassociatiDAO;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;
import it.gruppoinit.pal.gp.core.helper.ORMHelper;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author fabrizioc
 * 
 */
@Repository
public class ComuniassociatiDAOImpl extends BaseDAOImpl<Comuniassociati, ComuniassociatiId> implements ComuniassociatiDAO {

    @SuppressWarnings("unchecked")
    @Override
    public List<Comuniassociati> findAll() {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	DetachedCriteria comune = det.createAlias("comune", "_comune");
	comune.addOrder(Order.asc("comune"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public Class<Comuniassociati> getEntityClass() {

	return Comuniassociati.class;
    }
}
