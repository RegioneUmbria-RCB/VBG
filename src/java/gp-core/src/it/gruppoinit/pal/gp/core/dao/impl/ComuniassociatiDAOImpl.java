/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.ComuniassociatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;

/**
 * @author francescop
 * 
 */
@Repository
public class ComuniassociatiDAOImpl extends BaseDAOImpl<Comuniassociati, ComuniassociatiId> implements ComuniassociatiDAO {

    @Override
    public void delete(Comuniassociati entity) {

	throw new UnsupportedOperationException();
    }

    @Override
    public Class<Comuniassociati> getEntityClass() {

	return Comuniassociati.class;
    }

    //    @Override
    //    public void insert(Comuniassociati entity) {
    //
    //	throw new UnsupportedOperationException();
    //    }
    //
    //    @Override
    //    public void update(Comuniassociati entity) {
    //
    //	throw new UnsupportedOperationException();
    //    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Comuniassociati> findByIdcomune(String idcomune) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("id.idcomune", idcomune));
	DetachedCriteria comune = det.createAlias("comune", "_comune");
	//	comune.addOrder(Order.asc("regione"));
	//	comune.addOrder(Order.asc("provincia"));
	//	comune.addOrder(Order.asc("comune"));
	comune.addOrder(Order.asc("_comune.regione"));
	comune.addOrder(Order.asc("_comune.provincia"));
	comune.addOrder(Order.asc("_comune.comune"));
	List<Comuniassociati> comuniassociatiList = (List<Comuniassociati>) getHibernateTemplate().findByCriteria(det);
	return comuniassociatiList;
    }

    @Override
    public List<Comuniassociati> findByComuniEsclusioni(String[] codicecomuni) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	if (codicecomuni != null && codicecomuni.length > 0) {
	    det.add(Restrictions.not(Restrictions.in("id.codicecomune", codicecomuni)));
	}
	List<Comuniassociati> comuniassociatiList = (List<Comuniassociati>) getHibernateTemplate().findByCriteria(det);
	return comuniassociatiList;
    }
}
