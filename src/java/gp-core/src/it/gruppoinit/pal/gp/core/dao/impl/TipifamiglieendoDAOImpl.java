package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipifamiglieendoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipifamiglieendoDAOImpl extends BaseDAOImpl<Tipifamiglieendo, PkId> implements TipifamiglieendoDAO {

    @Override
    public Class<Tipifamiglieendo> getEntityClass() {

	return Tipifamiglieendo.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipifamiglieendo> findByFilter(Tipifamiglieendo entity) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (entity.getSoftware() != null && StringUtils.isNotBlank(entity.getSoftware().getCodice())) {
	    det.add(Restrictions.eq("software.codice", entity.getSoftware().getCodice()));
	} else {
	    det.add(Restrictions.eq("software.codice", ORMHelper.getSoftware()));
	}
	if (StringUtils.isNotBlank(entity.getTipo())) {
	    try {
		det.add(Restrictions.eq("id.codice", Integer.parseInt(entity.getTipo().replaceAll("%", ""))));
	    } catch (Exception e) {
		det.add(Restrictions.ilike("tipo", entity.getTipo(), MatchMode.ANYWHERE));
	    }
	}
	//det.add(Restrictions.ilike("tipo", entity.getTipo(), MatchMode.ANYWHERE));
	return (List<Tipifamiglieendo>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tipifamiglieendo> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipifamiglieendo> findByDescSWeTT(String textToSearch) {

	//DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.ilike("tipo", textToSearch, MatchMode.ANYWHERE));
	det.add(Restrictions.in("software.codice", new Object[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }));
	return (List<Tipifamiglieendo>) getHibernateTemplate().findByCriteria(det);
    }
}
