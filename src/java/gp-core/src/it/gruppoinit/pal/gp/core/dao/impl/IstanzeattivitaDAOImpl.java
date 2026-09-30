package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeattivitaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeattivita;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Settoriavvisi;
import it.gruppoinit.pal.gp.core.domain.helper.SuperficiAttivitaHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Luca Proietti
 */
@Repository
public class IstanzeattivitaDAOImpl extends BaseDAOImpl<Istanzeattivita, PkId> implements IstanzeattivitaDAO {

    @Override
    public Class<Istanzeattivita> getEntityClass() {

	return Istanzeattivita.class;
    }

    public List<Istanzeattivita> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Settoriavvisi> findAvvisiIstanza(Istanze istanza) {

	DetachedCriteria criteria = DetachedCriteria.forClass(Settoriavvisi.class);
	criteria.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	criteria.createAlias("settore", "settore");
	criteria.createAlias("settore.attivitas", "attivitas");
	criteria.createAlias("attivitas.istanzeattivitas", "istanzeattivitas");
	if (EntityUtils.getNestedProperty(istanza, "id.codice") != null) {
	    criteria.add(Restrictions.eq("istanzeattivitas.istanzaId", istanza.getId().getCodice()));
	}
	criteria.add(Restrictions.or(Restrictions.and(Restrictions.geProperty("istanzeattivitas.metriq", "rangeda"),
		Restrictions.leProperty("istanzeattivitas.metriq", "rangea")), Restrictions.and(
		Restrictions.or(Restrictions.isNull("istanzeattivitas.metriq"), Restrictions.eq("istanzeattivitas.metriq", BigDecimal.ZERO)),
		Restrictions.and(Restrictions.isNull("rangeda"), Restrictions.isNull("rangea")))));
	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	List<Settoriavvisi> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public List<SuperficiAttivitaHelper> getSommaSuperficiAttivitaPerSettore(Istanze istanza, String perSettore) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	criteria.createAlias("attivita", "attivita");
	criteria.createAlias("attivita.settori", "settore", CriteriaSpecification.LEFT_JOIN);
	criteria.createAlias("attivita.settori.tipiunitamisura", "um", CriteriaSpecification.LEFT_JOIN);
	ProjectionList projections = Projections.projectionList();
	//projections.add(Projections.groupProperty("attivita.istat").as("istat"));
	projections.add(Projections.groupProperty("settore.settore"));
	projections.add(Projections.groupProperty("um.umDescrbreve")); 
	projections.add(Projections.sum("metriq").as("superficie_totale"));
	criteria.setProjection(projections);
	criteria.add(Restrictions.eq("istanza", istanza));
	if (perSettore != null) {
	    criteria.add(Restrictions.eq("settore.id.codicesettore", perSettore));
	}
	//criteria.
	List<Object[]> tempResults = getHibernateTemplate().findByCriteria(criteria);
	List<SuperficiAttivitaHelper> results = new ArrayList<SuperficiAttivitaHelper>(tempResults.size());
	SuperficiAttivitaHelper sah = null;
	for (Object[] rowData : tempResults) {
	    if(rowData != null){
		sah = new SuperficiAttivitaHelper();
		//sah.setAttivitaIstat((String)rowData[0]);
		sah.setSettore((String)rowData[0]);
		sah.setUnitaMisura((String)rowData[1]);
		sah.setSuperficieTotale((BigDecimal)rowData[2]);
		results.add(sah);
	    }
	}
	return results;
    }

    @Override
    public List<SuperficiAttivitaHelper> getSommaSuperficiAttivitaPerAttivita(Istanze istanza, String perAttivita) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	criteria.createAlias("attivita", "attivita");
	criteria.createAlias("attivita.settori", "settore", CriteriaSpecification.LEFT_JOIN);
	criteria.createAlias("attivita.settori.tipiunitamisura", "um", CriteriaSpecification.LEFT_JOIN);
	ProjectionList projections = Projections.projectionList();
	projections.add(Projections.groupProperty("attivita.istat").as("istat"));
	projections.add(Projections.groupProperty("settore.settore"));
	projections.add(Projections.groupProperty("um.umDescrbreve")); 
	projections.add(Projections.sum("metriq").as("superficie_totale"));
	criteria.setProjection(projections);
	criteria.add(Restrictions.eq("istanza", istanza));
	if (perAttivita != null) {
	    criteria.add(Restrictions.eq("attivita.id.codiceistat", perAttivita));
	}
	List<Object[]> tempResults = getHibernateTemplate().findByCriteria(criteria);
	List<SuperficiAttivitaHelper> results = new ArrayList<SuperficiAttivitaHelper>(tempResults.size());
	SuperficiAttivitaHelper sah = null;
	for (Object[] rowData : tempResults) {
	    if(rowData != null){
		sah = new SuperficiAttivitaHelper();
		sah.setAttivitaIstat((String)rowData[0]);
		sah.setSettore((String)rowData[1]);
		sah.setUnitaMisura((String)rowData[2]);
		sah.setSuperficieTotale((BigDecimal)rowData[3]);
		results.add(sah);
	    }
	}
	return results;
    }
}
