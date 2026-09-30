package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VwAssenzeconsessionariDAO;
import it.gruppoinit.pal.gp.core.domain.AssenzeFilter;
import it.gruppoinit.pal.gp.core.domain.VwAssenzeconcessionari;
import it.gruppoinit.pal.gp.core.domain.VwAssenzeconcessionariId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VwAssenzeconcessionariDAOImpl extends BaseDAOImpl<VwAssenzeconcessionari, VwAssenzeconcessionariId> implements VwAssenzeconsessionariDAO {

    @Override
    public Class<VwAssenzeconcessionari> getEntityClass() {

	return VwAssenzeconcessionari.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VwAssenzeconcessionari> findByAssenzeFilter(AssenzeFilter assenzeFilter) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	if (assenzeFilter.getAnno() != null) {
	    criteria.add(Restrictions.eq("id.anno", assenzeFilter.getAnno()));
	}
	if (assenzeFilter.getMercati() != null && assenzeFilter.getMercati().getId().getCodice() != null) {
	    criteria.add(Restrictions.eq("id.codicemercato", assenzeFilter.getMercati().getId().getCodice()));
	}
	if (assenzeFilter.getMercatiUso() != null && assenzeFilter.getMercatiUso().getId().getCodice() != null) {
	    criteria.add(Restrictions.eq("id.codiceuso", assenzeFilter.getMercatiUso().getId().getCodice()));
	}
	if (assenzeFilter.getAssenze() != null) {
	    criteria.add(Restrictions.ge("numeroAssenze", assenzeFilter.getAssenze()));
	}
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
