package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.RaggruppamentocausalioneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Raggruppamentocausalioneri;
import it.gruppoinit.pal.gp.core.domain.helper.RaggruppamentocausalioneriHelper;
import it.gruppoinit.pal.gp.core.domain.helper.RaggruppamentocausalioneriHelperComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Riccardo Bocci
 */
@Repository
public class RaggruppamentocausalioneriDAOImpl extends BaseDAOImpl<Raggruppamentocausalioneri, PkId> implements RaggruppamentocausalioneriDAO {

    @Override
    public Class<Raggruppamentocausalioneri> getEntityClass() {

	return Raggruppamentocausalioneri.class;
    }

    @Override
    public List<Raggruppamentocausalioneri> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "rcoDescr", DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<Raggruppamentocausalioneri> findByIstanza(Istanze istanza) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createCriteria("tipicausalioneris", "_tipicausalioneri", DetachedCriteria.LEFT_JOIN);
	criteria.createCriteria("_tipicausalioneri.istanzeoneris", "_istanzeoneri", DetachedCriteria.LEFT_JOIN);
	criteria.add(Restrictions.eq("_istanzeoneri.istanzaId", istanza.getId().getCodice()));
	ProjectionList projectionListPrecedenti = Projections.projectionList();
	projectionListPrecedenti.add(Projections.distinct(Projections.property("_tipicausalioneri.raggruppamentocausalioneriId")));
	criteria.setProjection(projectionListPrecedenti);	
	List<Integer> listCodiciRaggruppameni = getHibernateTemplate().findByCriteria(criteria);
	List<Raggruppamentocausalioneri> risultato = new ArrayList<Raggruppamentocausalioneri>();
	for (Integer codice : listCodiciRaggruppameni) {
	    risultato.add(this.findById(new PkId(codice)));
	}
	return risultato;
    }

    @Override
    public List<RaggruppamentocausalioneriHelper> findByIstanzaAndDataPagamento(Istanze istanza) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createCriteria("tipicausalioneris", "_tipicausalioneri", DetachedCriteria.LEFT_JOIN);
	criteria.createCriteria("_tipicausalioneri.istanzeoneris", "_istanzeoneri", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_tipicausalioneri.raggruppamentocausalioneri", "_raggruppamentocausalioneri",DetachedCriteria.LEFT_JOIN);
	criteria.add(Restrictions.eq("_istanzeoneri.istanzaId", istanza.getId().getCodice()));
	//criteria.addOrder(Order.asc("id.codice"));
	
	ProjectionList projectionListPrecedenti = Projections.projectionList();
	projectionListPrecedenti.add(Projections.distinct(Projections.property("_tipicausalioneri.raggruppamentocausalioneriId")));
	projectionListPrecedenti.add(Projections.property("_istanzeoneri.datapagamento"));
	projectionListPrecedenti.add(Projections.property("_raggruppamentocausalioneri.rcoDescr"));
	criteria.setProjection(projectionListPrecedenti);
	List<Object[]> listCodiciRaggruppameni = getHibernateTemplate().findByCriteria(criteria);
	
	List<RaggruppamentocausalioneriHelper> risultato = new ArrayList<RaggruppamentocausalioneriHelper>();
	RaggruppamentocausalioneriHelper raggruppamentocausalioneriHelper = null;
	for (Object[] codice : listCodiciRaggruppameni) {
	    raggruppamentocausalioneriHelper = new RaggruppamentocausalioneriHelper();
	    raggruppamentocausalioneriHelper.setRaggruppamentocausalioneri(this.findById(new PkId((Integer) codice[0])));
	    raggruppamentocausalioneriHelper.setDate((Date) codice[1]);
	    risultato.add(raggruppamentocausalioneriHelper);
	}
	Collections.sort(risultato, new RaggruppamentocausalioneriHelperComparator());
	return risultato;
    }
}
