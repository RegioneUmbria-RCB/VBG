package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipibandooutputDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipibandooutput;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author lucap
 * 
 */
@Repository
public class TipibandooutputDAOImpl extends BaseDAOImpl<Tipibandooutput, PkId> implements TipibandooutputDAO {

    @Override
    public Class<Tipibandooutput> getEntityClass() {

	return Tipibandooutput.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tipibandooutput> findByGraduatoriat(Tipibandooutput tipibandooutput) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.eq("tipigraduatoriet", tipibandooutput.getTipigraduatoriet()));
	return getHibernateTemplate().findByCriteria(det);
    }
}
