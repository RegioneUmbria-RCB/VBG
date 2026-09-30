package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VwConcessioniattiveDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwConcessioniattive;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class VwConcessioniattiveDAOImpl extends BaseDAOImpl<VwConcessioniattive, PkId> implements VwConcessioniattiveDAO {

    @Override
    public void delete(VwConcessioniattive entity) {

	throw new NotImplementedException();
    }

    @Override
    public Class<VwConcessioniattive> getEntityClass() {

	return VwConcessioniattive.class;
    }

    @Override
    public void insert(VwConcessioniattive entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(VwConcessioniattive entity) {

	throw new NotImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public VwConcessioniattive findByMercatoUsoPosteggio(Integer codiceMercato, Integer codiceUso, Integer codicePosteggio) {

	VwConcessioniattive vwConcessioniattive = null;
	;
	// Prima era presente getDefaultCriteria
	// Nella vista è presente il software, però è inutile perchè si ha un collegamento univoco tra mercato e
	// software.
	// per correttezza si utilizza getSoftwareCriteria.
	DetachedCriteria crit = getIdcomuneAndSoftwareCriteria();
	crit.add(Restrictions.eq("mercato.id.codice", codiceMercato));
	crit.add(Restrictions.eq("mercatiUso.id.codice", codiceUso));
	crit.add(Restrictions.eq("mercatiD.id.codice", codicePosteggio));
	List<VwConcessioniattive> list = (List<VwConcessioniattive>) getHibernateTemplate().findByCriteria(crit);
	if (list != null && list.size() > 0) {
	    vwConcessioniattive = list.get(0);
	}
	return vwConcessioniattive;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VwConcessioniattive> findByMercatoAndPosteggio(Integer codiceMercato, Integer codicePosteggio) {

	// Prima era presente getDefaultCriteria
	// Nella vista è presente il software, però è inutile perchè si ha un collegamento univoco tra mercato e
	// software.
	// per correttezza si utilizza getSoftwareCriteria.
	DetachedCriteria crit = getIdcomuneAndSoftwareCriteria();
	crit.add(Restrictions.eq("mercato.id.codice", codiceMercato));
	crit.add(Restrictions.eq("mercatiD.id.codice", codicePosteggio));
	crit.addOrder(Order.asc("mercatiUso.id.codice"));
	List<VwConcessioniattive> list = (List<VwConcessioniattive>) getHibernateTemplate().findByCriteria(crit);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VwConcessioniattive> findByMercatoMercatoUsoAndPosteggio(Integer codiceMercato, Integer codiceUso, Integer codicePosteggio) {

	// Prima era presente getDefaultCriteria
	// Nella vista è presente il software, però è inutile perchè si ha un collegamento univoco tra mercato e
	// software.
	// per correttezza si utilizza getSoftwareCriteria.
	DetachedCriteria crit = getIdcomuneAndSoftwareCriteria();
	crit.add(Restrictions.eq("mercato.id.codice", codiceMercato));
	crit.add(Restrictions.eq("mercatiD.id.codice", codicePosteggio));
	// crit.createCriteria("mercatiUso", "_mercatouso");
	if (codiceUso != null) {
	    crit.add(Restrictions.eq("mercatiUso.id.codice", codiceUso));
	}
	crit.addOrder(Order.asc("mercatiUso.id.codice"));
	List<VwConcessioniattive> list = (List<VwConcessioniattive>) getHibernateTemplate().findByCriteria(crit);
	return list;
    }
}
