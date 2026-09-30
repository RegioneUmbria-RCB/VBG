package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AnagrafestoricoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafestorico;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class AnagrafestoricoDAOImpl extends BaseDAOImpl<Anagrafestorico, PkId> implements AnagrafestoricoDAO {

    @Override
    public Class<Anagrafestorico> getEntityClass() {

	return Anagrafestorico.class;
    }

    @Override
    public List<Anagrafestorico> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "nominativo", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Anagrafestorico findStoricoId(Anagrafe entity, Date data) {

	if (data == null) {
	    throw new IllegalArgumentException("AnagraficaStoricoDAOImpl.findStoricoId: la data non può essere null");
	}
	if (EntityUtils.getNestedProperty(entity, "id.codice") == null) {
	    throw new IllegalArgumentException("AnagraficaStoricoDAOImpl.findStoricoId: l'oggetto anagrafe non può essere null ");
	}
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("anagrafeId", entity.getId().getCodice()));
	criteria.add(Restrictions.le("datainiziovalidita", data));
	criteria.add(Restrictions.gt("datafinevalidita", data));
	criteria.addOrder(OrderBySqlFormula.desc("datafinevalidita", FunctionsEnum.NVL_FUNCTION, "'01/01/2999'",
		OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	criteria.addOrder(Order.desc("id.codice"));
	List<Anagrafestorico> anagrafestoricos = getHibernateTemplate().findByCriteria(criteria);
	if (!anagrafestoricos.isEmpty()) {
	    return anagrafestoricos.get(0);
	} else {
	    criteria = getIdcomuneCriteria();
	    criteria.add(Restrictions.eq("anagrafeId", entity.getId().getCodice()));
	    criteria.add(Restrictions.isNull("datainiziovalidita"));
	    criteria.add(Restrictions.gt("datafinevalidita", data));
	    criteria.addOrder(OrderBySqlFormula.desc("datafinevalidita", FunctionsEnum.NVL_FUNCTION, "'01/01/2999'",
		    OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	    criteria.addOrder(Order.desc("id.codice"));
	    anagrafestoricos = getHibernateTemplate().findByCriteria(criteria);
	    if (!anagrafestoricos.isEmpty()) {
		return anagrafestoricos.get(0);
	    } else {
		criteria = getIdcomuneCriteria();
		criteria.add(Restrictions.eq("anagrafeId", entity.getId().getCodice()));
		criteria.add(Restrictions.isNull("datafinevalidita"));
		criteria.addOrder(OrderBySqlFormula.desc("datafinevalidita", FunctionsEnum.NVL_FUNCTION, "'01/01/2999'",
			OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
		criteria.addOrder(Order.desc("id.codice"));
		anagrafestoricos = getHibernateTemplate().findByCriteria(criteria);
		if (!anagrafestoricos.isEmpty()) {
		    return anagrafestoricos.get(0);
		} else {
		    return null;
		}
	    }
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public Anagrafestorico findUltimoAnagrafestoricoByAnagrafe(Anagrafe entity) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("anagrafe.id.codice", entity.getId().getCodice()));
	criteria.add(Restrictions.isNull("datafinevalidita"));
	List<Anagrafestorico> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	} else {
	    throw new RuntimeException(
		    "findUltimoAnagrafestoricoByAnagrafe: Anomalia nella ricerca dei record in anagrafestorico, nessun record presente per l' anagrafe ["
			    + entity.getId() + "] e datafinevalidità null");
	}
    }

    @Override
    public List<Anagrafestorico> findStoricoByAnagrafe(Anagrafe entity) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("anagrafe.id.codice", entity.getId().getCodice()));
	//criteria.addOrder(Order.asc("datainiziovalidita"));
	criteria.addOrder(OrderBySqlFormula.desc("datafinevalidita", FunctionsEnum.NVL_FUNCTION, "'31/12/9999'",
		OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	criteria.addOrder(OrderBySqlFormula.desc("datainiziovalidita", FunctionsEnum.NVL_FUNCTION, "'01/01/0001'",
		OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	criteria.addOrder(Order.desc("id.codice"));
	List<Anagrafestorico> list = getHibernateTemplate().findByCriteria(criteria);
	if (list != null && !list.isEmpty()) {
	    list.get(0).setIsFirst(true);
	    list.get(list.size() - 1).setIsLast(true);
	}
	return list;
    }

    @Override
    public void ricalcoloStoricoAnagrafiche(Anagrafestorico anagrafeStorico) {

	//Recupero la lista dello storico
	List<Anagrafestorico> list = this.findStoricoByAnagrafe(anagrafeStorico.getAnagrafe());
	// Variabile di controllo 
	// se true  : anagrafica da modificare
	// se false : anagrafica da non modificare
	boolean cambia = false;
	for (Anagrafestorico anagrafestoricoTemp : list) {
	    if (cambia == true) {
		anagrafestoricoTemp.setDatafinevalidita(anagrafeStorico.getDatafinevalidita());
		this.update(anagrafeStorico);
		break;
	    }
	    if (anagrafestoricoTemp.getId().getCodice().equals(anagrafeStorico.getId().getCodice())) {
		cambia = true;
	    }
	}
    }
}
