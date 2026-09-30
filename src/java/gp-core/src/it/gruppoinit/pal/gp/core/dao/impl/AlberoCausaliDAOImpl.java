/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoCausaliDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoCausali;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * 
 */
@Repository
public class AlberoCausaliDAOImpl extends BaseDAOImpl<AlberoCausali, PkId> implements AlberoCausaliDAO {

    @Override
    public Class<AlberoCausali> getEntityClass() {

	return AlberoCausali.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AlberoCausali> findByAlberoProc(AlberoCausali entity) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("alberoproc", entity.getAlberoproc()));
	return (List<AlberoCausali>) getHibernateTemplate().findByCriteria(det);
    }
}
