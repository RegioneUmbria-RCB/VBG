package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.IstanzeAccessoAttiLogDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiLog;
import it.gruppoinit.pal.gp.core.domain.IstanzeAccessoAttiLogId;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeAccessoAttiFilter;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;

/**
 * 
 * @author
 */
@Repository
public class IstanzeAccessoAttiLogDAOImpl extends BaseDAOImpl<IstanzeAccessoAttiLog, IstanzeAccessoAttiLogId> implements IstanzeAccessoAttiLogDAO {

    @Override
    public Class<IstanzeAccessoAttiLog> getEntityClass() {

	return IstanzeAccessoAttiLog.class;
    }

    @Override
    public List<IstanzeAccessoAttiLog> findAll(Integer firstResult, Integer maxResult) {

	//throw new NotImplementedException();
	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "istanzeAccessoAttiT", DAOOrderTypeEnum.ASC);
    }

    @Override
    public int countByFilter(IstanzeAccessoAttiFilter filter) {

	DetachedCriteria criteria = createCriteriaFilter(filter);
	criteria.setProjection(Projections.rowCount());
	int ris = ((Integer) getHibernateTemplate().findByCriteria(criteria).get(0)).intValue();
	return ris;
    }

    private DetachedCriteria createCriteriaFilter(IstanzeAccessoAttiFilter filter) {

	DetachedCriteria det = getEmptyCriteriaForClass();
	if (filter.getAllaData() != null) {
	    det.add(Restrictions.le("dataora", filter.getAllaData()));
	}
	if (filter.getDallaData() != null) {
	    det.add(Restrictions.ge("dataora", filter.getDallaData()));
	}
	if (filter.getAnagrafe() != null && filter.getAnagrafe().getId() != null && filter.getAnagrafe().getId().getCodice() != null) {
	    det.add(Restrictions.eq("anagrafe.id.codice", filter.getAnagrafe().getId().getCodice()));
	}
	det.createAlias("istanze", "_ist");
	if (filter.getIstanzeFilter().getNumeroistanza() != null) {
	    det.add(Restrictions.eq("_ist.numeroistanza", filter.getIstanzeFilter().getNumeroistanza()));
	}
	det.createAlias("anagrafe", "_anag");
	String[] padNumeroistanza = new String[] { "20", "' '" };
	if (filter.getOrderBy() != null) {
	    String[] field = filter.getOrderBy().split(",");
	    for (int i = 0; i < field.length; i++) {
		if (field[i].equals("anagrafe.descrizioneRichiedente")) {
		    setOrdine("_anag.nominativo", det, filter.getOrderAscDesc(), "standard", null);
		}
		if (field[i].equals("istanzeFilter.numeroistanza")) {
		    setOrdine("_ist.numeroistanza", det, filter.getOrderAscDesc(), "leftpad", padNumeroistanza);
		}
	    }
	}
	return det;
    }

    @Override
    public List<IstanzeAccessoAttiLog> findIstanzeAccessoAttiLogByFilter(IstanzeAccessoAttiFilter filter, Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = createCriteriaFilter(filter);
	List<IstanzeAccessoAttiLog> list = new ArrayList<IstanzeAccessoAttiLog>();
	if (null != firstResult && null != maxResult) {
	    list = getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
	} else {
	    list = getHibernateTemplate().findByCriteria(criteria);
	}
	return list;
    }

    /**
     * <pre>
     * Aggionge al DetachedCriteria passato l'ordinamento dove:
     * &#64;param FiledOrder: obbligatorio - indica il campo per cui ordinare 
     * &#64;param det : obbligatorio - DetachedCriteria a cui aggiungere l'ordinamento 
     * &#64;param orderTypeEnum : Obbligatorio - ASC o DESC
     * &#64;param tipoDatoOrdinamento : tipologia di ordinamento 
     * 					standard : campo semplice
     *                                  data     : campo data (applica ordinamento fatto tramite la funzione FunctionsEnum.NLV_FUNCTION )
     *                                  leftpad  : campo stringa (applica ordinamento fatto tramite la funzione FunctionsEnum.LPAD_FUNCTION )
     * &#64;param parametro
     * </pre>
     */
    private void setOrdine(String FiledOrder, DetachedCriteria det, OrderTypeEnum orderTypeEnum, String tipoDatoOrdinamento, String... parametro) {

	if (tipoDatoOrdinamento.equalsIgnoreCase("standard")) {
	    if (orderTypeEnum.name().equalsIgnoreCase("ASC")) {
		det.addOrder(Order.asc(FiledOrder));
	    } else {
		det.addOrder(Order.desc(FiledOrder));
	    }
	}
	if (tipoDatoOrdinamento.equalsIgnoreCase("data")) {
	    if (orderTypeEnum.name().equalsIgnoreCase("ASC")) {
		det.addOrder(
			OrderBySqlFormula.asc(FiledOrder, FunctionsEnum.NVL_FUNCTION, "'01/01/2999'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	    } else {
		det.addOrder(
			OrderBySqlFormula.desc(FiledOrder, FunctionsEnum.NVL_FUNCTION, "'01/01/2999'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	    }
	}
	if (tipoDatoOrdinamento.equalsIgnoreCase("leftpad")) {
	    if (orderTypeEnum.name().equalsIgnoreCase("ASC")) {
		det.addOrder(OrderBySqlFormula.asc(FiledOrder, FunctionsEnum.LPAD_FUNCTION, parametro));
	    } else {
		det.addOrder(OrderBySqlFormula.desc(FiledOrder, FunctionsEnum.LPAD_FUNCTION, parametro));
	    }
	}
    }
}
