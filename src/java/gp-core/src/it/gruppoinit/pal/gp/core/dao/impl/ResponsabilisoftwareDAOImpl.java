/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabilisoftwareDAO;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.ResponsabilisoftwareId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class ResponsabilisoftwareDAOImpl extends BaseDAOImpl<Responsabilisoftware, ResponsabilisoftwareId> implements ResponsabilisoftwareDAO {

    @Override
    public Class<Responsabilisoftware> getEntityClass() {

	return Responsabilisoftware.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Responsabilisoftware> findByResponsabile(Responsabili responsabili) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("id.codiceresponsabile", responsabili.getId().getCodice()));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
