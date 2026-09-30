package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocOneriDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocOneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 */
@Repository
public class AlberoprocOneriDAOImpl extends BaseDAOImpl<AlberoprocOneri, PkId> implements AlberoprocOneriDAO {

    @Override
    public Class<AlberoprocOneri> getEntityClass() {

	return AlberoprocOneri.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AlberoprocOneri> findAllByAlberoproc(String idcomune, Integer codiceAlberoproc) {

	DetachedCriteria det = getIdcomuneCriteria(idcomune);
	det.add(Restrictions.eq("alberoproc.id.codice", codiceAlberoproc));
	return getHibernateTemplate().findByCriteria(det);
    }
}
