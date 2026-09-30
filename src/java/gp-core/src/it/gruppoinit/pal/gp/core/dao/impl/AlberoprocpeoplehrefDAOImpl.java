/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocpeoplehrefDAO;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Alberoprocpeoplehref;
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
public class AlberoprocpeoplehrefDAOImpl extends BaseDAOImpl<Alberoprocpeoplehref, PkId> implements AlberoprocpeoplehrefDAO {

    @Override
    public Class<Alberoprocpeoplehref> getEntityClass() {

	return Alberoprocpeoplehref.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Alberoprocpeoplehref> findByAlberoProc(Alberoproc alberoproc) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("alberoproc", alberoproc));
	return (List<Alberoprocpeoplehref>) getHibernateTemplate().findByCriteria(det);
    }
}
