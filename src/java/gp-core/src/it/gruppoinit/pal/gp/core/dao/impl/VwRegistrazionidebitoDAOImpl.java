package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VwRegistrazionidebitiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RateNonpagateFilter;
import it.gruppoinit.pal.gp.core.domain.VwRegistrazionidebiti;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VwRegistrazionidebitoDAOImpl extends BaseDAOImpl<VwRegistrazionidebiti, PkId> implements VwRegistrazionidebitiDAO {

    @Override
    public Class<VwRegistrazionidebiti> getEntityClass() {

	return VwRegistrazionidebiti.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VwRegistrazionidebiti> findByFilter(RateNonpagateFilter rateNonpagateFilter) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	criteria.createAlias("registrazione", "_registrazione");
	if (rateNonpagateFilter.getAnno() != null) {
	    criteria.add(Restrictions.eq("_registrazione.anno", rateNonpagateFilter.getAnno()));
	}
	if (rateNonpagateFilter.getRegistrazioniCausali() != null && rateNonpagateFilter.getRegistrazioniCausali().getId().getCodice() != null) {
	    criteria.add(Restrictions.eq("_registrazione.registrazioniCausali.id.codice", rateNonpagateFilter.getRegistrazioniCausali().getId()
		    .getCodice()));
	}
	if (rateNonpagateFilter.getMercati() != null && rateNonpagateFilter.getMercati().getId().getCodice() != null) {
	    criteria.createAlias("_registrazione.mercatiD", "_mercatiD");
	    criteria.add(Restrictions.eq("_mercatiD.mercati.id.codice", rateNonpagateFilter.getMercati().getId().getCodice()));
	}
	if (rateNonpagateFilter.getMercatiUso() != null && rateNonpagateFilter.getMercatiUso().getId().getCodice() != null) {
	    criteria.add(Restrictions.eq("_registrazione.mercatiUso.id.codice", rateNonpagateFilter.getMercatiUso().getId().getCodice()));
	}
	if (rateNonpagateFilter.getRateNonPagate() != null) {
	    criteria.add(Restrictions.ge("rateNonPagate", rateNonpagateFilter.getRateNonPagate()));
	}
	if (rateNonpagateFilter.getImportoDaIncassareInf() != null) {
	    criteria.add(Restrictions.ge("daIncassare", rateNonpagateFilter.getImportoDaIncassareInf()));
	} else {
	    criteria.add(Restrictions.ge("daIncassare", new BigDecimal(0)));
	}
	if (rateNonpagateFilter.getImportoDaIncassareSup() != null) {
	    criteria.add(Restrictions.le("daIncassare", rateNonpagateFilter.getImportoDaIncassareSup()));
	}
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
