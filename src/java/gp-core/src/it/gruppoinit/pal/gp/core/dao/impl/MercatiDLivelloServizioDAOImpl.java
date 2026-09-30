package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiDLivelloServizioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.MercatiDLivelloServizio;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDLivelloServizioDTO;

import java.util.Calendar;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class MercatiDLivelloServizioDAOImpl extends BaseDAOImpl<MercatiDLivelloServizio, PkId> implements MercatiDLivelloServizioDAO {

    @Override
    public Class<MercatiDLivelloServizio> getEntityClass() {

	return MercatiDLivelloServizio.class;
    }

    @Override
    public List<MercatiDLivelloServizio> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<MercatiDLivelloServizioDTO> findByPosteggio(Integer codiceposteggio, boolean attivi, boolean scaduti) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("mercatiD", "_mercatiD");
	criteria.createAlias("mercatiLivelloServizio", "_mercatiLivelloServizio");
	criteria.createAlias("_mercatiLivelloServizio.mercatiUso", "_mercatiUso");
	if (attivi) {
	    criteria.add(Restrictions.eq("_mercatiLivelloServizio.attivo", attivi));
	}
	if (!scaduti) {
	    Calendar _date = Calendar.getInstance();
	    _date.set(Calendar.HOUR, 23);
	    _date.set(Calendar.MINUTE, 59);
	    _date.set(Calendar.SECOND, 59);
	    Criterion isNull = Restrictions.isNull("dataFine");
	    Criterion maggioredi = Restrictions.ge("dataFine", _date.getTime());
	    LogicalExpression orData = Restrictions.or(maggioredi, isNull);
	    criteria.add(orData);
	}
	criteria.add(Restrictions.eq("_mercatiD.id.codice", codiceposteggio));
	//
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "CODICE");
	plist.add(Projections.property("fattoreMoltiplicativo"), "FATTOREMOLTIPLICATIVO");
	plist.add(Projections.property("usaMqPosteggio"), "USAMQPOSTEGGIO");
	plist.add(Projections.property("dataInizio"), "DATAINIZIO");
	plist.add(Projections.property("dataFine"), "DATAFINE");
	//
	plist.add(Projections.property("_mercatiUso.id.codice"), "MERCATIUSO_ID_CODICE");
	plist.add(Projections.property("_mercatiUso.descrizione"), "MERCATIUSO_DESCRIZIONE");
	plist.add(Projections.property("_mercatiLivelloServizio.id.codice"), "CODICESERVIZIO");
	plist.add(Projections.property("_mercatiLivelloServizio.descrizione"), "DESCRIZIONE");
	plist.add(Projections.property("_mercatiLivelloServizio.attivo"), "ATTIVO");
	plist.add(Projections.property("_mercatiLivelloServizio.tariffa"), "TARIFFA");
	plist.add(Projections.property("_mercatiD.id.codice"), "CODICEPOSTEGGIO");
	criteria.setProjection(plist);
	criteria.addOrder(Order.asc("_mercatiUso.descrizione"));
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(MercatiDLivelloServizioDTO.class));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
