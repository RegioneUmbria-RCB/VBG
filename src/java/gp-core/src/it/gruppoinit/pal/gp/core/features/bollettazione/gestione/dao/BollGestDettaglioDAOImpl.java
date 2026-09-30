package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BollGestDettaglio;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.BollettazioneAuditLogger;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.BollGestDettaglioDTO;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.FiltriBollgestDettaglioDTO;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.IdDettaglioBollettazioneSoftwareComune;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.VerificaCausaliDettaglioBollettazioneBean;
import it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti.VerificaPosizioneDebitoriaBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.SoftwareComuneDataBean;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

/**
 * 
 * @author
 */
@Repository
public class BollGestDettaglioDAOImpl extends BaseDAOImpl<BollGestDettaglio, PkId> implements BollGestDettaglioDAO {

    @Override
    public Class<BollGestDettaglio> getEntityClass() {

	return BollGestDettaglio.class;
    }

    @Override
    public List<BollGestDettaglio> findAll(Integer firstResult, Integer maxResult) {

	//return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, PROP_DELLA_ENTITY, DAOOrderTypeEnum.ASC);
	throw new NotImplementedException();
    }

    @Override
    public List<BollGestDettaglioDTO> findByIdBollettazioneAndAnagrafe(Integer idBollettazione, Integer idAnagrafica) {

	return findBollGestDettaglioDTOByFiltri(
		new FiltriBollgestDettaglioDTO(ORMHelper.getIdcomune(), idBollettazione, Boolean.FALSE, idAnagrafica));
    }

    @Override
    public List<BollGestDettaglio> findByIdBollettazione(Integer idBollettazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("bollGestTestataId", idBollettazione, Integer.class));
	fr.addFilterField(FilterUtils.equals("flagEliminata", false, Boolean.class));
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }

    @Override
    public void deleteByIdBollettazione(Integer idBollettazione) {

	if (idBollettazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo deleteByIdBollettazione senza passare il riferimento della testata della bollettazione");
	}
	//1. Tolgo i riferimenti delle rettifiche
	String sql = "update boll_gest_dettaglio set fk_rettifica_id = null where idcomune = ? and fk_bollgest_id = ?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BollGestDettaglio.class);
	int index = 0;
	q.setParameter(index, ORMHelper.getIdcomune(), new StringType());
	index++;
	q.setParameter(index, idBollettazione, new IntegerType());
	q.executeUpdate();
	//2. Cancello
	sql = "delete from boll_gest_dettaglio where idcomune = ? and fk_bollgest_id = ?";
	q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BollGestDettaglio.class);
	index = 0;
	q.setParameter(index, ORMHelper.getIdcomune(), new StringType());
	index++;
	q.setParameter(index, idBollettazione, new IntegerType());
	q.executeUpdate();
    }

    @Override
    public Boolean existsRigheInviateASistemaPagamenti(Integer idBollettazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("bollGestTestataId", idBollettazione, Integer.class));
	fr.addFilterField(FilterUtils.isNotNull("dettPosizioneDebitoriaId"));
	ft.addRestriction(fr);
	return this.countRecord(ft) > 0;
    }

    @Override
    public void updateRiferimentoPosizioneDebitoria(List<Integer> idRigheBollettazione, Integer idDettPosizioneDebitoria) {

	String sql = "update " + //
		" boll_gest_dettaglio " + //
		" set " + //
		" fk_posdebdettaglio_id = ? " + //
		" where " + //
		" idcomune = ? and id in ( QMARKS )";
	String[] qMarks = new String[idRigheBollettazione.size()];
	Arrays.fill(qMarks, "?");
	String qMark = StringUtils.join(qMarks, ",");
	SQLQuery query = getSession().createSQLQuery(sql.replace("QMARKS", qMark)).addSynchronizedEntityClass(BollGestDettaglio.class);
	int pos = 0;
	query.setInteger(pos++, idDettPosizioneDebitoria);
	query.setString(pos++, ORMHelper.getIdcomune());
	for (Integer i : idRigheBollettazione) {
	    query.setInteger(pos++, i);
	}
	query.executeUpdate();
	this.flush(); // rendo disponibile a hibernate le informazioni
    }

    @Override
    public void updateAnnullaRettifiche(Integer idBollettazione) {

	String hql = "update BollGestDettaglio set bollGestDettaglioId = null where id.idcomune = ? and bollGestTestataId = ?";
	getHibernateTemplate().bulkUpdate(hql, new Object[] { ORMHelper.getIdcomune(), idBollettazione, });
    }

    @Override
    public Set<VerificaPosizioneDebitoriaBean> findDettaglioPosizioniDebitorieByBollettazioneEAnagrafe(Integer idBollettazione,
	    Integer idAnagrafica) {

	return findDettaglioPosizioniDebitorie(idBollettazione, idAnagrafica);
    }

    @Override
    public Set<VerificaPosizioneDebitoriaBean> findDettaglioPosizioniDebitorieByBollettazione(Integer idBollettazione) {

	return findDettaglioPosizioniDebitorie(idBollettazione, null);
    }

    private Set<VerificaPosizioneDebitoriaBean> findDettaglioPosizioniDebitorie(Integer idBollettazione, Integer idAnagrafica) {

	String sql = "SELECT COALESCE(rate.id, dett_posizione_debitoria.id) AS dettPosizioneDebitoriaId " + //
		     " ,COALESCE(rate.CF_ENTE_CREDITORE, dett_posizione_debitoria.CF_ENTE_CREDITORE) AS cfEnteCreditore " + //
		     " FROM BOLL_GEST_DETTAGLIO " + //
		     " LEFT JOIN dett_posizione_debitoria ON BOLL_GEST_DETTAGLIO.idcomune = dett_posizione_debitoria.idcomune " + //
		     " AND BOLL_GEST_DETTAGLIO.FK_POSDEBDETTAGLIO_ID = dett_posizione_debitoria.id " + //
		     " LEFT JOIN boll_gest_dett_rate ON BOLL_GEST_DETTAGLIO.idcomune = boll_gest_dett_rate.idcomune " + //
		     " AND BOLL_GEST_DETTAGLIO.id = boll_gest_dett_rate.FK_BOLLGESTDET_ID " + //
		     " LEFT JOIN dett_posizione_debitoria rate ON boll_gest_dett_rate.idcomune = rate.idcomune " + //
		     " AND boll_gest_dett_rate.FK_POSDEBDETTAGLIO_ID = rate.id " + //
		     " WHERE BOLL_GEST_DETTAGLIO.idcomune = :idcomune " + //
		     " AND BOLL_GEST_DETTAGLIO.FK_BOLLGEST_ID = :idbollettazione ";
	if (idAnagrafica != null) {
	    sql += " AND BOLL_GEST_DETTAGLIO.FK_CODICEANAGRAFE = :codiceanagrafe ";
	}
	sql += " AND COALESCE(rate.id,dett_posizione_debitoria.id) IS NOT NULL " + //
	       " GROUP BY rate.id " + //
	       " ,dett_posizione_debitoria.id " + //
	       " ,rate.CF_ENTE_CREDITORE " + //
	       " ,dett_posizione_debitoria.CF_ENTE_CREDITORE";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BollGestDettaglio.class);
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setInteger("idbollettazione", idBollettazione);
	if (idAnagrafica != null) {
	    query.setInteger("codiceanagrafe", idAnagrafica);
	}
	query.addScalar("dettPosizioneDebitoriaId", Hibernate.INTEGER);
	query.addScalar("cfEnteCreditore", Hibernate.STRING);
	query.setResultTransformer(Transformers.aliasToBean(VerificaPosizioneDebitoriaBean.class));
	List<VerificaPosizioneDebitoriaBean> l = query.list();
	return new HashSet<VerificaPosizioneDebitoriaBean>(l);
    }

    @Override
    public BollGestDettaglio getImportoRigaValidaByTestataAnagrafeEContoEAutorizzazione(Integer idBollettazione, Integer idAnagrafe, Integer idConto,
	    Integer idAutorizzazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("bollGestTestataId", idBollettazione, Integer.class));
	fr.addFilterField(FilterUtils.equals("anagrafeId", idAnagrafe, Integer.class));
	fr.addFilterField(FilterUtils.equals("contiId", idConto, Integer.class));
	fr.addFilterField(FilterUtils.equals("flagConguaglio", Boolean.FALSE, Boolean.class));
	fr.addFilterField(FilterUtils.equals("flagValidata", Boolean.TRUE, Boolean.class));
	fr.addFilterField(FilterUtils.equals("id.codice", idAutorizzazione, "bollGestDettAutorizzazioni.autorizzazione", Integer.class));
	ft.addRestriction(fr);
	List<BollGestDettaglio> dettagli = findByFilterTable(ft);
	if (dettagli.size() == 0) {
	    return null;
	}
	if (dettagli.size() > 1) {
	    String error_message = "Ci sono più righe nella bollettazione precedente che potrebbero prendere parte al conguaglio a parità di anagrafica e conto. Impossibile procedere con la bollettazione";
	    BollettazioneAuditLogger.logger.error(error_message);
	    throw new RuntimeException(error_message);
	}
	return dettagli.get(0);
    }

    @Override
    public List<BollGestDettaglio> findRigheValidabili(Integer idBollettazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("bollGestTestataId", idBollettazione, Integer.class));
	fr.addFilterField(FilterUtils.equals("flagEliminata", Boolean.FALSE, Boolean.class));
	fr.addFilterField(FilterUtils.equals("flagRettificata", Boolean.FALSE, Boolean.class));
	fr.addFilterField(FilterUtils.isNull("dettPosizioneDebitoriaId"));
	fr.addFilterField(FilterUtils.isNull("dettPosizioneDebitoriaId", "bollGestDettRate"));
	ft.addRestriction(fr);
	return (List<BollGestDettaglio>) this.findByFilterTable(ft);
    }

    @Override
    public String getComune(Integer idRigaDettaglio) {

	List<ISoftwareComuneData> softwareComuneFromIdDettaglioList = this.getSoftwareAndComunePerRiga(idRigaDettaglio);
	if (softwareComuneFromIdDettaglioList.isEmpty()) {
	    return null;
	}
	if (softwareComuneFromIdDettaglioList.size() > 1) {
	    String error_message = "Ops! sembra che il tuo configuratore non abbia fatto il suo lavoro oppure un bel BUG da schiacciare. Allontanarsi immediatamente dalla postazione...";
	    BollettazioneAuditLogger.logger.error(error_message);
	    throw new InvalidConfigurationException(error_message);
	}
	return softwareComuneFromIdDettaglioList.get(0).getCodiceComune();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ISoftwareComuneData> getSoftwareAndComunePerRiga(Integer idRigaDettaglio) {

	String sql = this.sqlGetCodicecomuneAndSoftware(false);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("codiceComune", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idRigaDettaglio);
	q.setInteger(2, 1); // lag_validata
	q.setResultTransformer(Transformers.aliasToBean(SoftwareComuneDataBean.class));
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ISoftwareComuneData> getSoftwareAndComuneForBollettazione(Integer idRigaTestataBollettazione) {

	String sql = this.sqlGetCodicecomuneAndSoftware(true);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("codiceComune", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idRigaTestataBollettazione);
	q.setInteger(2, 1); // validata
	q.setResultTransformer(Transformers.aliasToBean(SoftwareComuneDataBean.class));
	return q.list();
    }

    private String sqlGetCodicecomuneAndSoftware(boolean isTestata) {

	StringBuilder query = new StringBuilder();
	query.append("SELECT boll_cfg_tipo.software AS software, ")
		.append("CASE WHEN istanze.codicecomune IS NULL THEN mercati.codicecomune ELSE istanze.codicecomune END AS codiceComune ")
		.append("FROM boll_gest_dettaglio ")
		.append("INNER JOIN boll_gest_testata ON boll_gest_testata.idcomune=boll_gest_dettaglio.idcomune ")
		.append("AND boll_gest_testata.id=boll_gest_dettaglio.fk_bollgest_id ")
		.append("INNER JOIN boll_cfg_tipo ON boll_cfg_tipo.idcomune=boll_gest_testata.idcomune ")
		.append("AND boll_cfg_tipo.id=boll_gest_testata.fk_bolltipo_id ")
		.append("LEFT JOIN boll_gest_istanzeoneri ON boll_gest_dettaglio.idcomune = boll_gest_istanzeoneri.idcomune ")
		.append("AND boll_gest_dettaglio.id = boll_gest_istanzeoneri.fk_bollgestdet_id ")
		.append("LEFT JOIN istanzeoneri ON istanzeoneri.idcomune = boll_gest_istanzeoneri.idcomune ")
		.append("AND istanzeoneri.id = boll_gest_istanzeoneri.fk_codiceistanzeoneri ")
		.append("LEFT JOIN istanze ON istanzeoneri.idcomune = istanze.idcomune ") //
		.append("AND istanzeoneri.codiceistanza = istanze.codiceistanza ") //
		.append("left join boll_gest_dett_autorizz on ") //
		.append("boll_gest_dettaglio.idcomune = boll_gest_dett_autorizz.idcomune ") //
		.append("and boll_gest_dettaglio.id = boll_gest_dett_autorizz.fk_bollgestdet_id ") //
		.append("left join boll_gest_mercati_dett on ") //
		.append("boll_gest_dett_autorizz.idcomune = boll_gest_mercati_dett.idcomune ") //
		.append("and boll_gest_dett_autorizz.id = boll_gest_mercati_dett.fk_id_gest_autorizzazioni ") // 	
		.append("left join mercati_d on ") //
		.append("boll_gest_mercati_dett.idcomune = mercati_d.idcomune ") //
		.append("and boll_gest_mercati_dett.fk_idposteggio = mercati_d.idposteggio ") //
		.append("LEFT JOIN mercati ON mercati_d.idcomune = mercati.idcomune ") //
		.append("AND mercati_d.fkcodicemercato = mercati.codicemercato ") //
		.append("WHERE boll_gest_dettaglio.idcomune = ? "); //
	if (isTestata) {
	    query.append(" AND boll_gest_testata.id = ? "); //
	} else {
	    query.append(" AND boll_gest_dettaglio.id = ? "); //
	}
	query.append(" AND boll_gest_dettaglio.flag_validata = ? ") //
		.append("GROUP BY  boll_cfg_tipo.software, istanze.codicecomune,mercati.codicecomune");
	return query.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BollGestDettaglio> findByIdDettPosizioneDebitoria(Integer idDettPosizioneDebitoria) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createAlias("bollGestDettRate", "_bollGestDettRate", Criteria.LEFT_JOIN);
	det.add(Restrictions.or(Restrictions.eq("dettPosizioneDebitoriaId", idDettPosizioneDebitoria),
		Restrictions.eq("_bollGestDettRate.dettPosizioneDebitoriaId", idDettPosizioneDebitoria)));
	det.addOrder(Order.asc("id.codice"));
	det.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	return getHibernateTemplate().findByCriteria(det);
	/*
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("dettPosizioneDebitoriaId", idDettPosizioneDebitoria, Integer.class));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
	*/
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ISoftwareComuneData> getSoftwareAndComunePerDettaglioComunicazione(int idDettaglioComunicazione) {

	String sql = this.sqlGetCodicecomuneAndSoftware();
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("codiceComune", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idDettaglioComunicazione);
	q.setInteger(2, 1); // validata
	q.setResultTransformer(Transformers.aliasToBean(SoftwareComuneDataBean.class));
	return q.list();
    }

    private String sqlGetCodicecomuneAndSoftware() {

	StringBuilder query = new StringBuilder();
	query.append("SELECT boll_cfg_tipo.software AS software, ")
		.append("CASE WHEN istanze.codicecomune IS NULL THEN mercati.codicecomune ELSE istanze.codicecomune END AS codiceComune ")
		.append("FROM boll_massive_d ")
		.append("inner join boll_gest_dettaglio on boll_massive_d.idcomune=boll_gest_dettaglio.idcomune and boll_massive_d.fkid_boll_gest_dettaglio=boll_gest_dettaglio.id ")
		.append("INNER JOIN boll_gest_testata ON boll_gest_testata.idcomune=boll_gest_dettaglio.idcomune ")
		.append("AND boll_gest_testata.id=boll_gest_dettaglio.fk_bollgest_id ")
		.append("INNER JOIN boll_cfg_tipo ON boll_cfg_tipo.idcomune=boll_gest_testata.idcomune ")
		.append("AND boll_cfg_tipo.id=boll_gest_testata.fk_bolltipo_id ")
		.append("LEFT JOIN boll_gest_istanzeoneri ON boll_gest_dettaglio.idcomune = boll_gest_istanzeoneri.idcomune ")
		.append("AND boll_gest_dettaglio.id = boll_gest_istanzeoneri.fk_bollgestdet_id ")
		.append("LEFT JOIN istanzeoneri ON istanzeoneri.idcomune = boll_gest_istanzeoneri.idcomune ")
		.append("AND istanzeoneri.id = boll_gest_istanzeoneri.fk_codiceistanzeoneri ")
		.append("LEFT JOIN istanze ON istanzeoneri.idcomune = istanze.idcomune ") //
		.append("AND istanzeoneri.codiceistanza = istanze.codiceistanza ") //
		.append("left join boll_gest_dett_autorizz on ") //
		.append("boll_gest_dettaglio.idcomune = boll_gest_dett_autorizz.idcomune ") //
		.append("and boll_gest_dettaglio.id = boll_gest_dett_autorizz.fk_bollgestdet_id ") //
		.append("left join boll_gest_mercati_dett on ") //
		.append("boll_gest_dett_autorizz.idcomune = boll_gest_mercati_dett.idcomune ") //
		.append("and boll_gest_dett_autorizz.id = boll_gest_mercati_dett.fk_id_gest_autorizzazioni ") // 	
		.append("left join mercati_d on ") //
		.append("boll_gest_mercati_dett.idcomune = mercati_d.idcomune ") //
		.append("and boll_gest_mercati_dett.fk_idposteggio = mercati_d.idposteggio ") //
		.append("LEFT JOIN mercati ON mercati_d.idcomune = mercati.idcomune ") //
		.append("AND mercati_d.fkcodicemercato = mercati.codicemercato ") //
		.append("WHERE boll_massive_d.idcomune = ? ")//
		.append(" AND boll_massive_d.fkid_massive_d = ? ") //
		.append(" AND boll_gest_dettaglio.flag_validata = ? ") //
		.append("GROUP BY  boll_cfg_tipo.software, istanze.codicecomune, mercati.codicecomune");
	return query.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VerificaCausaliDettaglioBollettazioneBean> verificaConfigurazioniCausali(Set<Integer> idRigheDettaglioBollettazione) {

	String sql = this.sqlGetCodiceTipoCausaleOneri(idRigheDettaglioBollettazione);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("iddettaglio", Hibernate.INTEGER);
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("codiceanagrafe", Hibernate.INTEGER);
	q.addScalar("nominativo", Hibernate.STRING);
	q.addScalar("nome", Hibernate.STRING);
	q.addScalar("codicefiscale", Hibernate.STRING);
	q.addScalar("partitaiva", Hibernate.STRING);
	q.addScalar("idcausaleonere", Hibernate.INTEGER);
	q.addScalar("mappaturanodopag", Hibernate.STRING);
	q.addScalar("conto", Hibernate.STRING);
	q.addScalar("idconto", Hibernate.INTEGER);
	q.addScalar("attivo", Hibernate.BOOLEAN);
	int pos = 0;
	q.setString(pos++, ORMHelper.getIdcomune());
	q.setInteger(pos++, 1); // attivo
	for (Integer idRiga : idRigheDettaglioBollettazione) {
	    q.setInteger(pos++, idRiga);
	}
	q.setResultTransformer(Transformers.aliasToBean(VerificaCausaliDettaglioBollettazioneBean.class));
	return q.list();
    }

    private String sqlGetCodiceTipoCausaleOneri(Set<Integer> idRigheDettaglioBollettazione) {

	StringBuilder query = new StringBuilder();
	query.append("SELECT ") //
		.append("  boll_gest_dettaglio.id AS iddettaglio, ") //
		.append("  boll_gest_dettaglio.descrizione, ") //
		.append("  anagrafe.codiceanagrafe, ") //
		.append("  anagrafe.nominativo, ") //
		.append("  anagrafe.nome, ") //
		.append("  anagrafe.codicefiscale, ") //
		.append("  anagrafe.partitaiva, ") //
		.append("  tipicausalioneridettaglio.fkcausale AS idcausaleonere, ") //
		.append("  tipicausalioneridettaglio.flag_attivo AS attivo, ") //
		.append("  CONTI.MAPPATURANODOPAG, ") //
		.append("  CONTI.descrizione AS conto, ") //
		.append("  CONTI.id AS idconto ") //
		.append("FROM ") //
		.append("  boll_gest_dettaglio ") //
		.append("  INNER JOIN anagrafe ON  ") //
		.append("  anagrafe.idcomune=boll_gest_dettaglio.idcomune AND ") //
		.append("  anagrafe.CODICEANAGRAFE=boll_gest_dettaglio.FK_CODICEANAGRAFE ") //
		.append("  LEFT JOIN tipicausalioneridettaglio ON tipicausalioneridettaglio.idcomune = boll_gest_dettaglio.idcomune ") //
		.append("  AND boll_gest_dettaglio.fk_conto_id = tipicausalioneridettaglio.fkconto ") //
		.append("  LEFT JOIN tipicausalioneri ON tipicausalioneridettaglio.idcomune = tipicausalioneri.idcomune ") //
		.append("  AND tipicausalioneridettaglio.fkcausale = tipicausalioneri.co_id ") //
		.append("  LEFT JOIN CONTI ON  ") //
		.append("  CONTI.IDCOMUNE=boll_gest_dettaglio.IDCOMUNE AND ") //
		.append("  CONTI.ID=boll_gest_dettaglio.FK_CONTO_ID ") //
		.append("WHERE ") //
		.append("  boll_gest_dettaglio.idcomune = ? and ") //
		.append("  tipicausalioneridettaglio.flag_attivo = ? and "); //
	String qMarks = "";
	int num = idRigheDettaglioBollettazione.size();
	if (num < 1000) {
	    String qm = StringUtils.repeat("?,", num);
	    qm = qm.substring(0, qm.length() - 1);
	    qMarks = " boll_gest_dettaglio.id in  (" + qm + ")";
	} else {
	    Double filter_getListaCodiceAttivita_length = Double.valueOf(num);
	    Double cicli = filter_getListaCodiceAttivita_length / 1000;
	    int cicliDaMille = cicli.intValue();
	    int resto = num - (cicliDaMille * 1000);
	    qMarks += " ( 1=2 ";
	    for (int i = 0; i < cicliDaMille; i++) {
		String qm = StringUtils.repeat("?,", 1000);
		qm = qm.substring(0, qm.length() - 1);
		qMarks += " or boll_gest_dettaglio.id in  (" + qm + ")";
	    }
	    if (resto > 0) {
		String qm = StringUtils.repeat("?,", resto);
		qm = qm.substring(0, qm.length() - 1);
		qMarks += " or boll_gest_dettaglio.id in (" + qm + ")";
	    }
	    qMarks += ")";
	}
	query = query.append(qMarks);
	return query.toString();
    }

    @Override
    public List<BollGestDettaglioDTO> findBollGestDettaglioDTOByTestata(Integer idBollettazione) {

	return findBollGestDettaglioDTOByFiltri(new FiltriBollgestDettaglioDTO(ORMHelper.getIdcomune(), idBollettazione, Boolean.FALSE));
    }

    @SuppressWarnings("unchecked")
    private List<BollGestDettaglioDTO> findBollGestDettaglioDTOByFiltri(FiltriBollgestDettaglioDTO filtri) {

	SQLQuery q = createQueryBollGestDettaglioDTO(filtri);
	q.setString("idcomune", filtri.getIdcomune());
	q.setInteger("idtestata", filtri.getIdTestata());
	if (filtri.getFlagEliminata() != null) {
	    q.setInteger("flag_eliminata", filtri.getFlagEliminata().booleanValue() ? 1 : 0);
	}
	if (filtri.getCodiceAnagrafe() != null) {
	    q.setInteger("codiceanagrafe", filtri.getCodiceAnagrafe());
	}
	q.setResultTransformer(Transformers.aliasToBean(BollGestDettaglioDTO.class));
	return q.list();
    }

    private SQLQuery createQueryBollGestDettaglioDTO(FiltriBollgestDettaglioDTO filtri) {

	String sql = this.sqlBollGestDettaglio(filtri);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("codiceanagrafe", Hibernate.INTEGER);
	q.addScalar("nominativo", Hibernate.STRING);
	q.addScalar("nome", Hibernate.STRING);
	q.addScalar("codicefiscale", Hibernate.STRING);
	q.addScalar("partitaiva", Hibernate.STRING);
	q.addScalar("tipoanagrafe", Hibernate.STRING);
	q.addScalar("tipologia", Hibernate.INTEGER);
	q.addScalar("formagiuridica", Hibernate.STRING);
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("fkbollgestid", Hibernate.INTEGER);
	q.addScalar("flagvalidata", Hibernate.BOOLEAN);
	q.addScalar("fkrettificaid", Hibernate.INTEGER);
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("flaginsauto", Hibernate.BOOLEAN);
	q.addScalar("fkcodiceanagrafe", Hibernate.INTEGER);
	q.addScalar("fkcontoid", Hibernate.INTEGER);
	q.addScalar("fkposdebdettaglioid", Hibernate.INTEGER);
	q.addScalar("flageliminata", Hibernate.BOOLEAN);
	q.addScalar("noteutente", Hibernate.STRING);
	q.addScalar("notesistema", Hibernate.STRING);
	q.addScalar("flagrettificata", Hibernate.BOOLEAN);
	q.addScalar("importosenzaiva", Hibernate.BIG_DECIMAL);
	q.addScalar("iva", Hibernate.INTEGER);
	q.addScalar("importototale", Hibernate.BIG_DECIMAL);
	q.addScalar("flagconguaglio", Hibernate.BOOLEAN);
	q.addScalar("datascadenza", Hibernate.DATE);
	q.addScalar("conto", Hibernate.STRING);
	q.addScalar("idposizionerateizzata", Hibernate.INTEGER);
	return q;
    }

    private String sqlBollGestDettaglio(FiltriBollgestDettaglioDTO filtri) {

	StringBuilder sb = new StringBuilder("SELECT ") //
		.append("  anagrafe.codiceanagrafe AS codiceanagrafe, ") //
		.append("  anagrafe.nominativo AS nominativo, ") //
		.append("  anagrafe.nome AS nome, ") //
		.append("  anagrafe.codicefiscale AS codicefiscale, ") //
		.append("  anagrafe.partitaiva AS partitaiva, ") //
		.append("  anagrafe.tipoanagrafe as tipoanagrafe,")//
		.append("  anagrafe.tipologia as tipologia,")//
		.append("  formegiuridiche.formagiuridica as formagiuridica,")//
		.append("  boll_gest_dettaglio.id AS id, ") //
		.append("  boll_gest_dettaglio.fk_bollgest_id AS fkbollgestid, ") //
		.append("  boll_gest_dettaglio.flag_validata AS flagvalidata, ") //
		.append("  boll_gest_dettaglio.fk_rettifica_id AS fkrettificaid, ") //
		.append("  boll_gest_dettaglio.descrizione AS descrizione, ") //
		.append("  boll_gest_dettaglio.flag_ins_auto AS flaginsauto, ") //
		.append("  boll_gest_dettaglio.fk_codiceanagrafe AS fkcodiceanagrafe, ") //
		.append("  boll_gest_dettaglio.fk_conto_id AS fkcontoid, ") //
		.append("  boll_gest_dettaglio.fk_posdebdettaglio_id AS fkposdebdettaglioid, ") //
		.append("  boll_gest_dettaglio.flag_eliminata AS flageliminata, ") //
		.append("  boll_gest_dettaglio.note_utente AS noteutente, ") //
		.append("  boll_gest_dettaglio.note_sistema AS notesistema, ") //
		.append("  boll_gest_dettaglio.flag_rettificata AS flagrettificata, ") //
		.append("  boll_gest_dettaglio.importo_senza_iva AS importosenzaiva, ") //
		.append("  boll_gest_dettaglio.iva AS iva, ") //
		.append("  boll_gest_dettaglio.importo_totale AS importototale, ") //
		.append("  boll_gest_dettaglio.flag_conguaglio AS flagconguaglio, ") //
		.append("  boll_gest_dettaglio.data_scadenza AS datascadenza, ") //
		.append("  conti.descrizione AS conto, ") //
		.append("  boll_gest_dett_rate.fk_posdebdettaglio_id AS idposizionerateizzata ") //
		.append(" FROM ") //
		.append("  boll_gest_dettaglio ") //
		.append(" INNER JOIN anagrafe ON anagrafe.idcomune = boll_gest_dettaglio.idcomune ") //
		.append("  AND anagrafe.codiceanagrafe = boll_gest_dettaglio.fk_codiceanagrafe ") //		
		.append(" LEFT JOIN formegiuridiche ON ") //
		.append("  anagrafe.idcomune=formegiuridiche.idcomune AND ") //
		.append("  anagrafe.FORMAGIURIDICA=formegiuridiche.codiceformagiuridica ") //		
		.append(" LEFT JOIN conti ON conti.idcomune = boll_gest_dettaglio.idcomune ") //
		.append("  AND conti.id = boll_gest_dettaglio.fk_conto_id ") //
		.append(" LEFT JOIN boll_gest_dett_rate ON ") //
		.append("  boll_gest_dett_rate.idcomune=boll_gest_dettaglio.idcomune AND ") //
		.append("  boll_gest_dett_rate.FK_BOLLGESTDET_ID=boll_gest_dettaglio.id ") //
		.append(" WHERE ") //
		.append("  boll_gest_dettaglio.idcomune = :idcomune ") //
		.append("  AND boll_gest_dettaglio.fk_bollgest_id = :idtestata "); //
	if (filtri.getFlagEliminata() != null) {
	    sb = sb.append("  AND boll_gest_dettaglio.flag_eliminata = :flag_eliminata");
	}
	if (filtri.getCodiceAnagrafe() != null) {
	    sb = sb.append("  AND boll_gest_dettaglio.fk_codiceanagrafe = :codiceanagrafe");
	}
	return sb.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Integer, Set<String>> getComuniPerDettagliBollettazione(int idTestataBollettazione) {

	Map<Integer, Set<String>> ret = new HashMap<Integer, Set<String>>();
	String sql = this.sqlGetCodicecomuneAndSoftwareAndIdDettaglio(idTestataBollettazione);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("codiceComune", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestataBollettazione);
	q.setInteger(2, 1); // validata
	q.setResultTransformer(Transformers.aliasToBean(IdDettaglioBollettazioneSoftwareComune.class));
	List<IdDettaglioBollettazioneSoftwareComune> list = q.list();
	for (IdDettaglioBollettazioneSoftwareComune bean : list) {
	    Set<String> listaComuni = ret.get(bean.getId());
	    if (listaComuni == null) {
		listaComuni = new HashSet<String>();
	    }
	    if (StringUtils.isNotBlank(bean.getCodiceComune())) {
		listaComuni.add(bean.getCodiceComune());
	    }
	    ret.put(bean.getId(), listaComuni);
	}
	return ret;
    }

    private String sqlGetCodicecomuneAndSoftwareAndIdDettaglio(int idTestata) {

	StringBuilder query = new StringBuilder();
	query.append("SELECT boll_gest_dettaglio.id as id, boll_cfg_tipo.software AS software, ")
		.append("CASE WHEN istanze.codicecomune IS NULL THEN mercati.codicecomune ELSE istanze.codicecomune END AS codiceComune ")
		.append("FROM boll_gest_dettaglio ")
		.append("INNER JOIN boll_gest_testata ON boll_gest_testata.idcomune=boll_gest_dettaglio.idcomune ")
		.append("AND boll_gest_testata.id=boll_gest_dettaglio.fk_bollgest_id ")
		.append("INNER JOIN boll_cfg_tipo ON boll_cfg_tipo.idcomune=boll_gest_testata.idcomune ")
		.append("AND boll_cfg_tipo.id=boll_gest_testata.fk_bolltipo_id ")
		.append("LEFT JOIN boll_gest_istanzeoneri ON boll_gest_dettaglio.idcomune = boll_gest_istanzeoneri.idcomune ")
		.append("AND boll_gest_dettaglio.id = boll_gest_istanzeoneri.fk_bollgestdet_id ")
		.append("LEFT JOIN istanzeoneri ON istanzeoneri.idcomune = boll_gest_istanzeoneri.idcomune ")
		.append("AND istanzeoneri.id = boll_gest_istanzeoneri.fk_codiceistanzeoneri ")
		.append("LEFT JOIN istanze ON istanzeoneri.idcomune = istanze.idcomune ") //
		.append("AND istanzeoneri.codiceistanza = istanze.codiceistanza ") //
		.append("left join boll_gest_dett_autorizz on ") //
		.append("boll_gest_dettaglio.idcomune = boll_gest_dett_autorizz.idcomune ") //
		.append("and boll_gest_dettaglio.id = boll_gest_dett_autorizz.fk_bollgestdet_id ") //
		.append("left join boll_gest_mercati_dett on ") //
		.append("boll_gest_dett_autorizz.idcomune = boll_gest_mercati_dett.idcomune ") //
		.append("and boll_gest_dett_autorizz.id = boll_gest_mercati_dett.fk_id_gest_autorizzazioni ") // 	
		.append("left join mercati_d on ") //
		.append("boll_gest_mercati_dett.idcomune = mercati_d.idcomune ") //
		.append("and boll_gest_mercati_dett.fk_idposteggio = mercati_d.idposteggio ") //
		.append("LEFT JOIN mercati ON mercati_d.idcomune = mercati.idcomune ") //
		.append("AND mercati_d.fkcodicemercato = mercati.codicemercato ") //
		.append("WHERE boll_gest_dettaglio.idcomune = ? ") //
		.append(" AND boll_gest_testata.id = ? ") //
		.append(" AND boll_gest_dettaglio.flag_validata = ? "); //
	return query.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> recuperaMappaturaNodoPagDaIdDettaglio(Integer idRigaDettaglio) {

	String sql = this.sqlGetMapNodoPagRaggruppatoDaDettaglioBollettazione();
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("mappaturanodopag", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idRigaDettaglio);
	q.setInteger(2, 1);
	return q.list();
    }

    private String sqlGetMapNodoPagRaggruppatoDaDettaglioBollettazione() {

	StringBuilder query = new StringBuilder();
	query.append("SELECT CONTI.mappaturanodopag as mappaturanodopag")//
		.append(" FROM boll_gest_dettaglio ")//
		.append(" INNER JOIN tipicausalioneridettaglio ON tipicausalioneridettaglio.idcomune = boll_gest_dettaglio.idcomune ")//
		.append(" AND boll_gest_dettaglio.fk_conto_id = tipicausalioneridettaglio.fkconto ")//
		.append(" INNER JOIN tipicausalioneri ON tipicausalioneridettaglio.idcomune = tipicausalioneri.idcomune ")//
		.append(" AND tipicausalioneridettaglio.fkcausale = tipicausalioneri.co_id ")//
		.append(" inner join conti on tipicausalioneridettaglio.idcomune = conti.idcomune")//
		.append(" and tipicausalioneridettaglio.fkconto = conti.id")//
		.append(" WHERE ")//
		.append(" boll_gest_dettaglio.idcomune = ? ")//
		.append(" and boll_gest_dettaglio.ID = ?  ")//
		.append(" and tipicausalioneridettaglio.flag_attivo = ? ")//
		.append(" and conti.mappaturanodopag is not null")//
		.append(" group by conti.mappaturanodopag ")//
		.append(" order by conti.mappaturanodopag asc");
	return query.toString();
    }

    @Override
    public String findArrotondamentoByBollettazione(Integer idBollettazione) {

	String sql = this.sqlGetArrotondamentoDaBollettazione();
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("arrotondamento", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idBollettazione);
	List<String> list = q.list();
	return list.get(0);
    }

    private String sqlGetArrotondamentoDaBollettazione() {

	StringBuilder query = new StringBuilder();
	query.append("select bct.arrotondamento as arrotondamento ")//
		.append("from boll_gest_dettaglio bgd ")//
		.append("inner join boll_gest_testata bgt on ")//
		.append("bgd.IDCOMUNE = bgt.IDCOMUNE ")//
		.append("and bgt.ID = bgd.FK_BOLLGEST_ID ")//
		.append("inner join boll_cfg_tipo bct  on ")//
		.append("bgt.IDCOMUNE = bct.IDCOMUNE ")//
		.append("and bct.ID = bgt.FK_BOLLTIPO_ID  ")//
		.append("where ")//
		.append("bct.IDCOMUNE = ? ")//
		.append("and bgt.ID = ? ")//
		.append("group by ")//
		.append("bct.arrotondamento ");//
	return query.toString();
    }
}
