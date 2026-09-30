/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoStatiDomandaDAO;
import it.gruppoinit.pal.gp.core.domain.FoStatiDomanda;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author francol
 *
 */
@Repository
public class FoStatiDomandaDAOImpl extends BaseDAOImpl<FoStatiDomanda, PkId> implements FoStatiDomandaDAO {

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl#getEntityClass()
     */
    @Override
    public Class<FoStatiDomanda> getEntityClass() {

	return FoStatiDomanda.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<FoStatiDomanda> findByIdDomanda(String idcomune, Integer idDomanda) {

	DetachedCriteria crit = getIdcomuneCriteria(idcomune);
	crit.add(Restrictions.eq("fkDomanda", idDomanda));
	crit.addOrder(Order.asc("data"));
	List<FoStatiDomanda> eventi = getHibernateTemplate().findByCriteria(crit);
	return eventi;
    }
}
