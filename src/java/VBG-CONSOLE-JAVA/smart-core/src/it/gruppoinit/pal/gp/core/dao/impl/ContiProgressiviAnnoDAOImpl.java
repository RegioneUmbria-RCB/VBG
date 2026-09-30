/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ContiProgressiviAnnoDAO;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.ContiProgressiviAnno;
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
public class ContiProgressiviAnnoDAOImpl extends BaseDAOImpl<ContiProgressiviAnno, PkId> implements ContiProgressiviAnnoDAO {

    @Override
    public Class<ContiProgressiviAnno> getEntityClass() {

	return ContiProgressiviAnno.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ContiProgressiviAnno> findContiProgressivi(Conti conti) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("conti", conti));
	return getHibernateTemplate().findByCriteria(det);
    }
}
