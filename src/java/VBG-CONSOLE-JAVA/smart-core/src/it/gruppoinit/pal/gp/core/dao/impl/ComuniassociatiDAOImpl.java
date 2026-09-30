/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ComuniassociatiDAO;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;

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
	comune.addOrder(Order.asc("comune"));
	List<Comuniassociati> comuniassociatiList = (List<Comuniassociati>) getHibernateTemplate().findByCriteria(det);
	return comuniassociatiList;
    }
}
