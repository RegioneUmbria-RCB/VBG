/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.EndoCausaliDAO;
import it.gruppoinit.pal.gp.core.domain.EndoCausali;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class EndoCausaliDAOImpl extends BaseDAOImpl<EndoCausali, PkId> implements EndoCausaliDAO {

    @Override
    public Class<EndoCausali> getEntityClass() {

	return EndoCausali.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EndoCausali> findByInventarioprocedimenti(EndoCausali entity) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("inventarioprocedimenti", entity.getInventarioprocedimenti()));
	return (List<EndoCausali>) getHibernateTemplate().findByCriteria(det);
    }
}
