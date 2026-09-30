package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;

/**
 * 
 * @author
 */
@Repository
public class BollGestTestataDAOImpl extends BaseDAOImpl<BollGestTestata, PkId> implements BollGestTestataDAO {

    @Override
    public Class<BollGestTestata> getEntityClass() {

	return BollGestTestata.class;
    }

    @Override
    public List<BollGestTestata> findAll(Integer firstResult, Integer maxResult) {

	//return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY, DAOOrderTypeEnum.ASC);
	throw new NotImplementedException();
    }

    @Override
    public List<BollGestTestata> findByResponsabiliRuoli(List<Integer> ruoliId, Integer firstResult, Integer maxResult) {

	FilterTable ft = getFilterTablePerRuoli(ruoliId, false);
	return findByFilterTable(ft, firstResult, maxResult);
    }

    private FilterTable getFilterTablePerRuoli(List<Integer> ruoliId, boolean isCount) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	//	fr.addFilterField(FilterUtils.equals("bollCfgTipo.software.codice", ORMHelper.getSoftware(), String.class));
	fr.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "bollCfgTipo.software", String.class));
	if (ruoliId != null && ruoliId.size() > 0) {
	    Integer[] ruoliIds = new Integer[ruoliId.size()];
	    ruoliIds = ruoliId.toArray(ruoliIds);
	    fr.addFilterField(FilterUtils.in("id.fkRuoliId", ruoliIds, "bollCfgTipo.bllCfgRuolis", Integer.class));
	}
	ft.addRestriction(fr);
	if (!isCount) {
	    ft.addOrder(FilterUtils.orderDesc("dallaData"));
	    ft.addOrder(FilterUtils.orderAsc("descrizione"));
	}
	return ft;
    }

    @Override
    public int countByResponsabiliRuoli(List<Integer> ruoliId) {

	FilterTable ft = getFilterTablePerRuoli(ruoliId, true);
	return countRecord(ft);
    }

    @Override
    public int countByTipoBollettazione(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("bollCfgTipo.id.codice", codice, Integer.class));
	ft.addRestriction(fr);
	return this.countRecord(ft);
    }

    public void updateStato(Integer idBollettazione, String stato) {

	BollGestTestata entity = this.findById(new PkId(idBollettazione));
	entity.setStato(stato);
	this.update(entity);
    }

    @Override
    public List<BollGestTestata> findByBollCfgTipo(Integer bollcfgTipoId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("bollCfgTipo.id.codice", bollcfgTipoId, Integer.class));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }

    @Override
    public BollGestTestata findBollettazionePrecedenteByDataAndTipologia(Integer codiceTipologiaBollettazione,
	    Date dataPartenzaBollettazioneAttuale) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.smaller("allaData", dataPartenzaBollettazioneAttuale, Date.class));
	fr.addFilterField(FilterUtils.equals("bollCfgTipoId", codiceTipologiaBollettazione, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.order("dallaData", OrderTypeEnum.DESC));
	List<BollGestTestata> lista = findByFilterTable(ft, 0, 1);
	if (lista != null && lista.size() > 0)
	    return lista.get(0);
	return null;
    }

    @Override
    public String findImplementazioneByPosDeb(Integer codicePosDeb) {

	String sql = " select " + //
		     " bgt.IMPLEMENTAZIONE as implementazione  " + //
		     " from boll_gest_testata bgt " + //
		     " inner join boll_gest_dettaglio bgd on " + //
		     " bgt.IDCOMUNE = bgd.IDCOMUNE " + //
		     " and bgt.ID = bgd.FK_BOLLGEST_ID " + //
		     " where " + //
		     " bgt.IDCOMUNE = ?  " + //
		     " and bgd.FK_POSDEBDETTAGLIO_ID = ? ";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("implementazione", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codicePosDeb);
	List<String> list = q.list();
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }

    @Override
    public Integer findCodIstanzaByDettPosDebitoria(Integer codicePosDeb) {

	String sql = " select " + //
		     " i.CODICEISTANZA as codiceistanza " + //
		     " from " + //
		     " boll_gest_istanzeoneri bgi " + //
		     " inner join istanzeoneri i on " + //
		     " bgi.IDCOMUNE = i.IDCOMUNE " + //
		     " and bgi.FK_CODICEISTANZEONERI = i.ID " + //
		     " inner join boll_gest_dettaglio bgd on " + //
		     " bgi.IDCOMUNE = bgd.IDCOMUNE " + //
		     " and bgi.FK_BOLLGESTDET_ID = bgd.ID " + //
		     " where " + //
		     " i.IDCOMUNE = ? " + //
		     " and bgd.FK_POSDEBDETTAGLIO_ID = ? ";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("codiceistanza", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codicePosDeb);
	List<Integer> list = q.list();
	if (list.isEmpty()) {
	    return null;
	}
	return list.get(0);
    }
}
