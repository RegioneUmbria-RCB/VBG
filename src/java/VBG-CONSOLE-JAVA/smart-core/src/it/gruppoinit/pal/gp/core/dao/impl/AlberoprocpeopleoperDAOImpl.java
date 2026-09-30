/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocpeopleoperDAO;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Alberoprocpeopleoper;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
@Repository
public class AlberoprocpeopleoperDAOImpl extends BaseDAOImpl<Alberoprocpeopleoper, PkId> implements AlberoprocpeopleoperDAO {

    @Override
    public Class<Alberoprocpeopleoper> getEntityClass() {

	return Alberoprocpeopleoper.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Alberoprocpeopleoper> findByAlberoProc(Alberoproc alberoproc) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("alberoproc", alberoproc));
	return (List<Alberoprocpeopleoper>) getHibernateTemplate().findByCriteria(det);
    }
}
