package it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.paevolution.ws.pagamenti_types.StatoPagamentoType;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.UpgradeDettPosizioniDebitorieBean;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BollGestDettaglio;
import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.BollettazioneBean;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.PageResult;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollGestTestataDAO;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.DettPosizioneDebitoriaProvenienzaEnum;
import it.gruppoinit.pal.gp.core.features.rabbitmq.posizionidebitorie.ProvenienzaBean;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

/**
 * 
 * @author
 */
@Repository
public class DettPosizioneDebitoriaDAOImpl extends BaseDAOImpl<DettPosizioneDebitoria, PkId> implements DettPosizioneDebitoriaDAO {

    BollGestTestataDAO bollGestTestataDAO;
    private static final Logger log = LoggerFactory.getLogger(DettPosizioneDebitoriaDAOImpl.class);

    @Override
    public Class<DettPosizioneDebitoria> getEntityClass() {

	return DettPosizioneDebitoria.class;
    }

    @Override
    public List<DettPosizioneDebitoria> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public Set<BollGestDettaglio> findBollGestDettByIdBollGestTestata(Integer idBollGestTestata) {

	BollGestTestata bollGestTestata = bollGestTestataDAO.findById(new PkId(idBollGestTestata));
	Set<BollGestDettaglio> bollGestDettaglios = bollGestTestata.getBollGestDettaglios();
	return bollGestDettaglios;
    }

    @Override
    public Set<Integer> findFkIdPosizioneDebitoriaByIdList(Set<Integer> id) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.in("id.codice", id));
	criteria.add(Restrictions.isNotNull("idPosizioneDebitoria"));
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("idPosizioneDebitoria"));
	criteria.setProjection(plist);
	@SuppressWarnings("unchecked")
	List<Integer> l = (List<Integer>) getHibernateTemplate().findByCriteria(criteria);
	Set<Integer> hSet = new HashSet<Integer>(l);
	return hSet;
    }

    @Override
    public Set<Integer> findIdDettaglioByListaStati(String[] statiDaVerificare) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.in("stato", statiDaVerificare));
	criteria.add(Restrictions.isNotNull("idPosizioneDebitoria"));
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"));
	criteria.setProjection(plist);
	@SuppressWarnings("unchecked")
	List<Integer> l = (List<Integer>) getHibernateTemplate().findByCriteria(criteria);
	Set<Integer> hSet = new HashSet<Integer>(l);
	return hSet;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String findStato(Integer id) {

	String hql = "Select d.stato from DettPosizioneDebitoria d where d.id.idcomune=? and d.id.codice = ?";
	Object[] params = new Object[] { ORMHelper.getIdcomune(), id };
	List<String> values = getHibernateTemplate().find(hql, params);
	if (values.size() > 0)
	    return values.get(0);
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findCodiciDettaglioPosizioniSpuntistiNonPagatePerBlackList() {

	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = getSQL().replaceAll("#SCHEMA_NAME#", schemaName + ".");
	StatoPagamentoType[] statiPagamentoBlackListNonPagati = new StatiPosizioniDebitorieConverter().getStatiPosizioniPagabili();
	int stati = statiPagamentoBlackListNonPagati.length;
	String listaStati = "(";
	for (int i = 0; i < stati; i++) {
	    //
	    listaStati += "?,";
	}
	listaStati = listaStati.substring(0, (listaStati.length() - 1));
	listaStati += ")";
	sql = sql.replaceAll("LISTA_STATI", listaStati);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	int pos = 1;
	for (StatoPagamentoType statoPagamentoType : statiPagamentoBlackListNonPagati) {
	    q.setString(pos, statoPagamentoType.name());
	    pos++;
	}
	/////////////////////////	
	addScalar(q);
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findCodiciDettaglioPosizioniNonPagate() {

	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = getSQL().replace("#SCHEMA_NAME#", schemaName + ".");
	StatoPagamentoType[] statiPagamentoBlackListNonPagati = new StatiPosizioniDebitorieConverter().getStatiPosizioniPagabili();
	int stati = statiPagamentoBlackListNonPagati.length;
	StringBuilder listaStati = new StringBuilder();
	listaStati.append("(");
	for (int i = 0; i < stati; i++) {
	    listaStati.append("?,");
	}
	listaStati.deleteCharAt(listaStati.length() - 1);
	listaStati.append(")");
	sql = sql.replace("LISTA_STATI", listaStati);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	int pos = 1;
	for (StatoPagamentoType statoPagamentoType : statiPagamentoBlackListNonPagati) {
	    q.setString(pos, statoPagamentoType.name());
	    pos++;
	}
	addScalar(q);
	return q.list();
    }

    private void addScalar(SQLQuery q) {

	q.addScalar("id", Hibernate.INTEGER);
    }

    private String getSQL() {

	return "select" + "  dett_posizione_debitoria.id as id " + "from " + "  #SCHEMA_NAME#dett_posizione_debitoria " +
	       "    inner join #SCHEMA_NAME#mercatipresenze_d on " + "      mercatipresenze_d.fk_pay_pos_deb=dett_posizione_debitoria.id and " +
	       "      mercatipresenze_d.idcomune=dett_posizione_debitoria.idcomune " + "    inner join #SCHEMA_NAME#autorizzazioni on " +
	       "      mercatipresenze_d.idcomune = autorizzazioni.idcomune and " +
	       "      mercatipresenze_d.fk_autorizzazioni_id = autorizzazioni.id " + "where " + "  dett_posizione_debitoria.idcomune = ? and " +
	       "  dett_posizione_debitoria.stato in LISTA_STATI " + "group by " + "  dett_posizione_debitoria.id";
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UpgradeDettPosizioniDebitorieBean> findDettaglioDaBlackList() {

	SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	String sql = getSQLDettaglioBlakList().replace("#SCHEMA_NAME#", schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setInteger(0, 1);
	/////////////////////////	
	addScalarBlackList(q);
	q.setResultTransformer(Transformers.aliasToBean(UpgradeDettPosizioniDebitorieBean.class));
	return q.list();
    }

    private void addScalarBlackList(SQLQuery q) {

	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("idposizionedebitoria", Hibernate.INTEGER);
	q.addScalar("dataultimostato", Hibernate.DATE);
	q.addScalar("iuv", Hibernate.STRING);
	q.addScalar("codiceavviso", Hibernate.STRING);
	q.addScalar("qrcode", Hibernate.STRING);
	q.addScalar("importo", Hibernate.BIG_DECIMAL);
	q.addScalar("dataregistrazione", Hibernate.DATE);
	q.addScalar("descrizionecausale", Hibernate.STRING);
	q.addScalar("codiceanagrafe", Hibernate.INTEGER);
    }

    private String getSQLDettaglioBlakList() {

	StringBuilder sql = new StringBuilder("SELECT");
	sql.append(" blacklist_src_p_deb_sp.idcomune as idcomune,");
	sql.append(" blacklist_src_p_deb_sp.fk_id_paypos_deb AS id,");
	sql.append(" blacklist_src_p_deb_sp.fk_id_paypos_deb AS idposizionedebitoria,");
	sql.append(" MAX(pay_stato_pagamenti.data_evento) AS dataultimostato,");
	sql.append(" pay_posizioni_debitorie.iuv as iuv,");
	sql.append(" pay_posizioni_debitorie.codice_avviso as codiceavviso,");
	sql.append(" pay_posizioni_debitorie.qrcode as qrcode,");
	sql.append(" pay_pagamenti.importo_pagato AS importo,");
	sql.append(" pay_pagamenti.data_sistema AS dataregistrazione,");
	sql.append(" pay_pagamenti.descrizione_causale as descrizionecausale,");
	sql.append(" blacklist_autorizzazioni.fk_codiceanagrafe AS codiceanagrafe");
	sql.append(" FROM");
	sql.append(" #SCHEMA_NAME#blacklist_src_p_deb_sp");
	sql.append(" INNER JOIN #SCHEMA_NAME#blacklist_autorizzazioni ON blacklist_src_p_deb_sp.idcomune = blacklist_autorizzazioni.idcomune");
	sql.append(" AND blacklist_src_p_deb_sp.fk_id_blacklist_mot = blacklist_autorizzazioni.fk_id_blacklist_mot");
	sql.append(" AND blacklist_autorizzazioni.flag_principale = ?");
	sql.append(" INNER JOIN #SCHEMA_NAME#pay_posizioni_debitorie ON pay_posizioni_debitorie.idcomune = blacklist_src_p_deb_sp.idcomune");
	sql.append(" AND pay_posizioni_debitorie.id = blacklist_src_p_deb_sp.fk_id_paypos_deb");
	sql.append(" LEFT JOIN #SCHEMA_NAME#dett_posizione_debitoria ON blacklist_src_p_deb_sp.idcomune = dett_posizione_debitoria.idcomune");
	sql.append(" AND blacklist_src_p_deb_sp.fk_id_paypos_deb = dett_posizione_debitoria.id");
	sql.append(" LEFT JOIN #SCHEMA_NAME#pay_stato_pagamenti ON pay_stato_pagamenti.idcomune = pay_posizioni_debitorie.idcomune");
	sql.append(" AND pay_stato_pagamenti.fk_posizione_debitoria = pay_posizioni_debitorie.id");
	sql.append(" LEFT JOIN #SCHEMA_NAME#pay_pagamenti ON pay_posizioni_debitorie.idcomune = pay_pagamenti.idcomune");
	sql.append("  AND pay_posizioni_debitorie.id = pay_pagamenti.fk_posizione_saldata");
	sql.append(" WHERE");
	sql.append(" dett_posizione_debitoria.id IS NULL");
	sql.append(" GROUP BY");
	sql.append(" blacklist_src_p_deb_sp.idcomune,");
	sql.append(" blacklist_src_p_deb_sp.fk_id_paypos_deb,");
	sql.append(" pay_posizioni_debitorie.iuv,");
	sql.append(" pay_posizioni_debitorie.codice_avviso,");
	sql.append(" pay_posizioni_debitorie.qrcode,");
	sql.append(" pay_pagamenti.importo_pagato,");
	sql.append(" pay_pagamenti.data_sistema,");
	sql.append(" pay_pagamenti.descrizione_causale,");
	sql.append(" blacklist_autorizzazioni.fk_codiceanagrafe,");
	sql.append(" blacklist_src_p_deb_sp.fk_id_blacklist_mot");
	sql.append(" ORDER BY");
	sql.append(" blacklist_src_p_deb_sp.fk_id_paypos_deb ASC,");
	sql.append(" blacklist_src_p_deb_sp.fk_id_blacklist_mot DESC");
	return sql.toString();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ImportoResponseType recuperaImporti(int idDettPosizioniDebitorie) {

	int numeroRataDefault = 1;
	StringBuilder sql = new StringBuilder();
	sql.append("SELECT  ");
	sql.append(" COALESCE(istanzeoneri.numerorata, boll_istanzeoneri.numerorata, boll_gest_dett_rate.numerorata, ?) AS numerorata, ");
	sql.append(" COALESCE(istanzeoneri.datascadenza,  ");
	sql.append("  boll_istanzeoneri.datascadenza,  ");
	sql.append("  boll_gest_dettaglio.data_scadenza,  ");
	sql.append("  boll_gest_dett_rate.scadenza,  ");
	sql.append("  dett_posizione_debitoria.data_scadenza) AS datascadenza, ");
	sql.append(
		" COALESCE(raggruppamentocausalioneri.rco_descr, boll_raggruppamento.rco_descr, rate_raggruppamento.rco_descr) AS raggruppamento, ");
	sql.append(" COALESCE(tipicausalioneri.co_descrizione, boll_tipicausalioneri.co_descrizione ");
	sql.append("  , rate_tipicausalioneri.co_descrizione, conti.descrizione, rate_conti.descrizione ");
	sql.append("  , conti_borsellino.DESCRIZIONE) AS causale, ");
	sql.append(
		" sum(COALESCE(istanzeoneri.prezzo, boll_istanzeoneri.prezzo, boll_gest_dettaglio.importo_totale, boll_gest_dett_rate.importo_totale ");
	sql.append("   ,borsellino_movimenti_importi.importo)) AS importo  ");
	sql.append(" FROM ");
	sql.append("   dett_posizione_debitoria ");
	sql.append("     LEFT JOIN istoneri_dett_posizioni ON ");
	sql.append("    dett_posizione_debitoria.idcomune = istoneri_dett_posizioni.idcomune AND ");
	sql.append("    dett_posizione_debitoria.id = istoneri_dett_posizioni.fk_dettposdebitoria_id ");
	sql.append("     LEFT JOIN istanzeoneri ON ");
	sql.append("    istoneri_dett_posizioni.idcomune = istanzeoneri.idcomune AND ");
	sql.append("    istoneri_dett_posizioni.fk_istanzeoneri_id = istanzeoneri.id ");
	sql.append("     LEFT JOIN tipicausalioneri ON ");
	sql.append("    istanzeoneri.idcomune = tipicausalioneri.idcomune AND ");
	sql.append("    istanzeoneri.fkidtipocausale = tipicausalioneri.co_id ");
	sql.append("     LEFT JOIN raggruppamentocausalioneri ON ");
	sql.append("    tipicausalioneri.idcomune = raggruppamentocausalioneri.idcomune AND ");
	sql.append("    tipicausalioneri.fk_rco_id = raggruppamentocausalioneri.rco_id ");
	sql.append("     LEFT JOIN boll_gest_dettaglio ON ");
	sql.append("    dett_posizione_debitoria.idcomune = boll_gest_dettaglio.idcomune AND ");
	sql.append("    dett_posizione_debitoria.id = boll_gest_dettaglio.fk_posdebdettaglio_id ");
	sql.append("     LEFT JOIN boll_gest_istanzeoneri ON ");
	sql.append("    boll_gest_dettaglio.idcomune = boll_gest_istanzeoneri.idcomune AND ");
	sql.append("    boll_gest_dettaglio.id = boll_gest_istanzeoneri.fk_bollgestdet_id ");
	sql.append("     LEFT JOIN istanzeoneri boll_istanzeoneri ON ");
	sql.append("    boll_gest_istanzeoneri.idcomune = boll_istanzeoneri.idcomune AND ");
	sql.append("    boll_gest_istanzeoneri.fk_codiceistanzeoneri = boll_istanzeoneri.id ");
	sql.append("     LEFT JOIN tipicausalioneri boll_tipicausalioneri ON ");
	sql.append("    boll_istanzeoneri.idcomune = boll_tipicausalioneri.idcomune AND ");
	sql.append("    boll_istanzeoneri.fkidtipocausale = boll_tipicausalioneri.co_id ");
	sql.append("     LEFT JOIN raggruppamentocausalioneri boll_raggruppamento ON ");
	sql.append("    boll_tipicausalioneri.idcomune = boll_raggruppamento.idcomune AND ");
	sql.append("    boll_tipicausalioneri.fk_rco_id = boll_raggruppamento.rco_id ");
	sql.append("     LEFT JOIN conti ON ");
	sql.append("    boll_gest_dettaglio.idcomune = conti.idcomune AND ");
	sql.append("    boll_gest_dettaglio.fk_conto_id = conti.id ");
	sql.append("     LEFT JOIN boll_gest_dett_rate ON  ");
	sql.append("       dett_posizione_debitoria.idcomune = boll_gest_dett_rate.idcomune AND ");
	sql.append("    dett_posizione_debitoria.id = boll_gest_dett_rate.fk_posdebdettaglio_id ");
	sql.append("     LEFT JOIN boll_gest_dettaglio rate_bolldettaglio ON  ");
	sql.append("    boll_gest_dett_rate.idcomune = rate_bolldettaglio.idcomune AND  ");
	sql.append("    boll_gest_dett_rate.fk_bollgestdet_id = rate_bolldettaglio.id ");
	sql.append("     LEFT JOIN boll_gest_istanzeoneri rate_bollistanzeoneri ON  ");
	sql.append("    rate_bolldettaglio.idcomune = rate_bollistanzeoneri.idcomune AND ");
	sql.append("    rate_bolldettaglio.id = rate_bollistanzeoneri.fk_bollgestdet_id ");
	sql.append("     LEFT JOIN istanzeoneri rate_istanzeoneri ON ");
	sql.append("    rate_bollistanzeoneri.idcomune = rate_istanzeoneri.idcomune AND ");
	sql.append("    rate_bollistanzeoneri.fk_codiceistanzeoneri = rate_istanzeoneri.id ");
	sql.append("     LEFT JOIN tipicausalioneri rate_tipicausalioneri ON ");
	sql.append("    rate_istanzeoneri.idcomune = rate_tipicausalioneri.idcomune AND ");
	sql.append("    rate_istanzeoneri.fkidtipocausale = rate_tipicausalioneri.co_id ");
	sql.append("     LEFT JOIN raggruppamentocausalioneri  rate_raggruppamento ON  ");
	sql.append("    rate_tipicausalioneri.idcomune = rate_raggruppamento.idcomune AND ");
	sql.append("    rate_tipicausalioneri.fk_rco_id = rate_raggruppamento.rco_id ");
	sql.append("     LEFT JOIN conti rate_conti ON ");
	sql.append("    rate_bolldettaglio.idcomune = rate_conti.idcomune AND ");
	sql.append("    rate_bolldettaglio.fk_conto_id = rate_conti.id  ");
	sql.append("     LEFT JOIN borsellino_movimenti on  ");
	sql.append("  borsellino_movimenti.idcomune=dett_posizione_debitoria.idcomune and ");
	sql.append("  borsellino_movimenti.fkid_dettposizionedebitoria=dett_posizione_debitoria.id ");
	sql.append("     LEFT JOIN borsellino_movimenti_importi on  ");
	sql.append("  borsellino_movimenti_importi.idcomune=borsellino_movimenti.idcomune and ");
	sql.append("  borsellino_movimenti_importi.fkid_borsellinomovimenti=borsellino_movimenti.id ");
	sql.append("     LEFT JOIN conti conti_borsellino on  ");
	sql.append("  borsellino_movimenti_importi.idcomune=conti_borsellino.idcomune and ");
	sql.append("  borsellino_movimenti_importi.fkid_conti=conti_borsellino.id              ");
	sql.append(" WHERE ");
	sql.append("     dett_posizione_debitoria.idcomune = ? AND  ");
	sql.append("  dett_posizione_debitoria.id = ? ");
	sql.append("  GROUP BY ");
	sql.append("   istanzeoneri.numerorata,boll_istanzeoneri.numerorata,boll_gest_dett_rate.numerorata, ");
	sql.append(
		"   istanzeoneri.datascadenza,boll_istanzeoneri.datascadenza,boll_gest_dettaglio.data_scadenza,boll_gest_dett_rate.scadenza, dett_posizione_debitoria.data_scadenza, ");
	sql.append("   raggruppamentocausalioneri.rco_descr,boll_raggruppamento.rco_descr,rate_raggruppamento.rco_descr, ");
	sql.append(
		"   tipicausalioneri.co_descrizione,boll_tipicausalioneri.co_descrizione,rate_tipicausalioneri.co_descrizione,conti.descrizione,rate_conti.descrizione,conti_borsellino.descrizione ");
	sql.append(" ORDER BY ");
	sql.append("   istanzeoneri.numerorata,boll_istanzeoneri.numerorata,boll_gest_dett_rate.numerorata, ");
	sql.append("   raggruppamentocausalioneri.rco_descr,boll_raggruppamento.rco_descr,rate_raggruppamento.rco_descr, ");
	sql.append(
		"   tipicausalioneri.co_descrizione,boll_tipicausalioneri.co_descrizione,rate_tipicausalioneri.co_descrizione,conti.descrizione,rate_conti.descrizione");
	SQLQuery q = getSession().createSQLQuery(sql.toString());
	q.setInteger(0, numeroRataDefault);
	q.setString(1, ORMHelper.getIdcomune());
	q.setInteger(2, idDettPosizioniDebitorie);
	q.addScalar("numeroRata", Hibernate.INTEGER);
	q.addScalar("dataScadenza", Hibernate.DATE);
	q.addScalar("raggruppamento", Hibernate.STRING);
	q.addScalar("causale", Hibernate.STRING);
	q.addScalar("importo", Hibernate.BIG_DECIMAL);
	q.setResultTransformer(Transformers.aliasToBean(ImportoBean.class));
	List<ImportoBean> elenco = q.list();
	ImportoResponseType retVal = new ImportoResponseType();
	Integer numeroRata = -1;
	RataResponseType rata = new RataResponseType();
	for (ImportoBean importo : elenco) {
	    if (!numeroRata.equals(importo.getNumeroRata())) {
		rata = new RataResponseType(importo.getNumeroRata(), importo.getDataScadenza());
	    }
	    numeroRata = importo.getNumeroRata();
	    rata.getDettagli().add(new OnereResponseType(importo.getRaggruppamento(), importo.getCausale(), importo.getImporto()));
	    retVal.getRate().add(rata);
	}
	return retVal;
    }

    @Override
    public DettPosizioneDebitoria findByFkIdPosizioneDebitoriaAndCfEnteCreditore(Integer fkIdPosizioneDebitoria, String cfEnteCreditore) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("idPosizioneDebitoria", fkIdPosizioneDebitoria, Integer.class));
	fr.addFilterField(FilterUtils.equals("cfEnteCreditore", cfEnteCreditore, String.class));
	ft.addRestriction(fr);
	List<DettPosizioneDebitoria> elenco = findByFilterTable(ft, 0, 1);
	if (elenco.size() == 0)
	    return null;
	return elenco.get(0);
    }

    @Override
    public DettPosizioneDebitoria findByCfEnteEUuid(String cfEnte, String uuid) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("cfEnteCreditore", cfEnte, String.class));
	fr.addFilterField(FilterUtils.equals("uuid", uuid, String.class));
	ft.addRestriction(fr);
	List<DettPosizioneDebitoria> elenco = findByFilterTable(ft, 0, 1);
	if (elenco.size() == 0)
	    return null;
	return elenco.get(0);
    }

    @Override
    public DettPosizioneDebitoriaProvenienzaEnum calcolaProvenienza(Integer idPosizioneDebitoria) {

	String sql = "select " + //
		     "i.fk_dettposdebitoria_id  as idOnere, " + //
		     "bm.FKID_DETTPOSIZIONEDEBITORIA  as idBorsellino, " + //
		     "bgd.FK_POSDEBDETTAGLIO_ID  as idBollettazione, " + //
		     "md.FK_PAY_POS_DEB  as idMercato, " + //
		     "bgdr.FK_POSDEBDETTAGLIO_ID as idBollettazioneRate " + //
		     "from dett_posizione_debitoria dpd " + //
		     "left join istoneri_dett_posizioni i on " + //
		     "dpd.IDCOMUNE = i.IDCOMUNE " + //
		     "and dpd.id = i.fk_dettposdebitoria_id " + //
		     "left join borsellino_movimenti bm on " + //
		     "dpd.IDCOMUNE = bm.IDCOMUNE " + //
		     "and dpd.id = bm.FKID_DETTPOSIZIONEDEBITORIA " + //
		     "left join boll_gest_dettaglio bgd on " + //
		     "dpd.IDCOMUNE = bgd.IDCOMUNE " + //
		     "and dpd.id = bgd.FK_POSDEBDETTAGLIO_ID " + //
		     "left join mercatipresenze_d md on " + //
		     "dpd.IDCOMUNE = md.IDCOMUNE " + //
		     "and dpd.id = md.FK_PAY_POS_DEB " + //
		     "left join boll_gest_dett_rate bgdr on " + //
		     "dpd.IDCOMUNE = bgdr.IDCOMUNE " + //
		     "and dpd.id = bgdr.FK_POSDEBDETTAGLIO_ID " + //
		     "where " + //
		     "dpd.IDCOMUNE = ? " + //
		     "and dpd.id = ? " + //
		     "group by i.fk_dettposdebitoria_id, " + //
		     "bm.FKID_DETTPOSIZIONEDEBITORIA, " + //
		     "bgd.FK_POSDEBDETTAGLIO_ID, " + //
		     "md.FK_PAY_POS_DEB, " + //
		     "bgdr.FK_POSDEBDETTAGLIO_ID";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("idOnere", Hibernate.INTEGER);
	q.addScalar("idBorsellino", Hibernate.INTEGER);
	q.addScalar("idBollettazione", Hibernate.INTEGER);
	q.addScalar("idMercato", Hibernate.INTEGER);
	q.addScalar("idBollettazioneRate", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idPosizioneDebitoria);
	q.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(ProvenienzaBean.class));
	List<ProvenienzaBean> list = q.list();
	if (list.isEmpty()) {
	    return null;
	}
	ProvenienzaBean pb = list.get(0);
	return trasformaInDettPosizioneEnum(pb);
    }

    private DettPosizioneDebitoriaProvenienzaEnum trasformaInDettPosizioneEnum(ProvenienzaBean pb) {

	if (pb.getIdBollettazione() != null) {
	    return DettPosizioneDebitoriaProvenienzaEnum.BOLLETTAZIONE;
	} else if (pb.getIdBorsellino() != null) {
	    return DettPosizioneDebitoriaProvenienzaEnum.ABBONAMENTO;
	} else if (pb.getIdMercato() != null) {
	    return DettPosizioneDebitoriaProvenienzaEnum.MERCATIPRESENZE_D;
	} else if (pb.getIdOnere() != null) {
	    return DettPosizioneDebitoriaProvenienzaEnum.ISTANZEONERI;
	} else if (pb.getIdBollettazioneRate() != null) {
	    return DettPosizioneDebitoriaProvenienzaEnum.ABBONAMENTO;
	} else {
	    return null;
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageResult<BollettazioneBean> getPosizioneDebitorieBollettazione(Integer[] autorizzazioniIds, boolean validata,
	    FiltroPagamentoEnum filtroPagamentoEnum, int pagina, int sizePagina) {

	if (autorizzazioniIds == null || autorizzazioniIds.length == 0) {
	    return new PageResult<BollettazioneBean>(new ArrayList<BollettazioneBean>(), 0, pagina, sizePagina, 0);
	}
	log.debug("autorizzazion trovate {}", autorizzazioniIds.length);
	StatiPosizioniDebitorieConverter c = new StatiPosizioniDebitorieConverter();
	List<String> statiDaVerificare = null;
	String sqlCount = "SELECT  COUNT(*) FROM (";
	String sqlFields = "SELECT " + //
			   "BOLL_GEST_DETTAGLIO.FK_BOLLGEST_ID AS idBollettazione, " + //
			   "BOLL_GEST_DETTAGLIO.FK_CODICEANAGRAFE AS codiceAnagrafe, " + //
			   "COALESCE(rate.IMPORTO_IVATO, dett_posizione_debitoria.IMPORTO_IVATO) AS importo, " + //
			   "COALESCE(rate.DATA_REGISTRAZIONE, dett_posizione_debitoria.DATA_REGISTRAZIONE) AS dataRegistrazione, " + //
			   "COALESCE(rate.STATO, dett_posizione_debitoria.STATO) AS statoPagamento,  " + //
			   "COALESCE(rate.IUV, dett_posizione_debitoria.IUV) AS codiceIuv, " + //
			   "COALESCE(rate.CODICE_AVVISO, dett_posizione_debitoria.CODICE_AVVISO) AS codiceAvviso, " + //
			   "COALESCE(rate.DESCRIZIONE_CAUSALE, dett_posizione_debitoria.DESCRIZIONE_CAUSALE) AS descrizioneCausale, " + //
			   "COALESCE(rate.id, dett_posizione_debitoria.id) AS idPagamento";
	String selectBody = " FROM BOLL_GEST_DETTAGLIO " + //
			    " INNER JOIN boll_gest_dett_autorizz ON boll_gest_dett_autorizz.IDCOMUNE = BOLL_GEST_DETTAGLIO.IDCOMUNE " + //
			    " AND boll_gest_dett_autorizz.FK_BOLLGESTDET_ID = BOLL_GEST_DETTAGLIO.ID " + //
			    " INNER JOIN autorizzazioni ON boll_gest_dett_autorizz.idcomune = autorizzazioni.idcomune " + //
			    " AND boll_gest_dett_autorizz.FK_AUTORIZZAZIONE_ID = autorizzazioni.id " + //
			    " LEFT JOIN dett_posizione_debitoria ON BOLL_GEST_DETTAGLIO.idcomune = dett_posizione_debitoria.idcomune " + //
			    " AND BOLL_GEST_DETTAGLIO.FK_POSDEBDETTAGLIO_ID = dett_posizione_debitoria.id " + //
			    " LEFT JOIN boll_gest_dett_rate ON BOLL_GEST_DETTAGLIO.idcomune = boll_gest_dett_rate.idcomune " + //
			    " AND BOLL_GEST_DETTAGLIO.id = boll_gest_dett_rate.FK_BOLLGESTDET_ID " + //
			    " LEFT JOIN dett_posizione_debitoria rate ON boll_gest_dett_rate.idcomune = rate.idcomune " + //
			    "  AND boll_gest_dett_rate.FK_POSDEBDETTAGLIO_ID = rate.id " + //
			    " WHERE BOLL_GEST_DETTAGLIO.idcomune = :idComune " + //
			    " AND BOLL_GEST_DETTAGLIO.FLAG_VALIDATA = :valid "; //
	if (filtroPagamentoEnum.equals(FiltroPagamentoEnum.DA_PAGARE)) {
	    selectBody = selectBody + " AND (  dett_posizione_debitoria.stato IN ( :statiPagamento ) OR rate.stato IN ( :statiPagamento ) ) ";
	    statiDaVerificare = c.getStatiPosizioniAsStringList(c.getStatiPosizioniPagabili());
	} else if (filtroPagamentoEnum.equals(FiltroPagamentoEnum.PAGATE)) {
	    selectBody = selectBody + " AND ( dett_posizione_debitoria.stato IN ( :statiPagamento ) OR rate.stato IN ( :statiPagamento ) ) ";
	    statiDaVerificare = c.getStatiPosizioniAsStringList(c.getStatiPagamentoCheNonSiPossonoCancellare());
	}
	selectBody = selectBody + " AND autorizzazioni.ID IN ( :autorizzazioni ) AND  " + //
		     " ( rate.id IS NOT NULL OR dett_posizione_debitoria.id IS NOT NULL)" + //
		     " GROUP BY " + //
		     "BOLL_GEST_DETTAGLIO.FK_BOLLGEST_ID ,  " + //
		     "   BOLL_GEST_DETTAGLIO.FK_CODICEANAGRAFE,  " + //
		     "   COALESCE(rate.IMPORTO_IVATO, dett_posizione_debitoria.IMPORTO_IVATO) ,  " + //
		     "   COALESCE(rate.DATA_REGISTRAZIONE, dett_posizione_debitoria.DATA_REGISTRAZIONE) , " + //
		     "   COALESCE(rate.ID, dett_posizione_debitoria.ID) ,  " + //
		     "   COALESCE(rate.STATO, dett_posizione_debitoria.STATO),   " + //
		     "   COALESCE(rate.IUV, dett_posizione_debitoria.IUV) ,  " + //
		     "   COALESCE(rate.CODICE_AVVISO, dett_posizione_debitoria.CODICE_AVVISO) ,  " + //
		     "   COALESCE(rate.DESCRIZIONE_CAUSALE, dett_posizione_debitoria.DESCRIZIONE_CAUSALE) ,  " + //
		     "   COALESCE(rate.id, dett_posizione_debitoria.id) ";
	Session session = getSession();
	String sqlCountFinale = sqlCount + sqlFields + selectBody + " ) tmp_" + System.currentTimeMillis();
	SQLQuery sqlQuery = session.createSQLQuery(sqlCountFinale);
	log.debug("sqlQueryCount {}", sqlCountFinale);
	// SET PARAMETERS
	sqlQuery.setParameter("idComune", ORMHelper.getIdcomune());
	sqlQuery.setParameter("valid", validata);
	if (statiDaVerificare != null) {
	    sqlQuery.setParameterList("statiPagamento", statiDaVerificare);
	}
	sqlQuery.setParameterList("autorizzazioni", autorizzazioniIds);
	// END SET PARAMETERS
	BigInteger count = (BigInteger) sqlQuery.uniqueResult();
	int totalCount = count.intValue();
	if (totalCount == 0) {
	    return new PageResult<BollettazioneBean>(new ArrayList<BollettazioneBean>(0), 0, pagina, sizePagina, totalCount);
	}
	log.debug("Conteggio {}", totalCount);
	//
	sqlQuery = session.createSQLQuery(sqlFields + selectBody) //
		.addScalar("idBollettazione", Hibernate.INTEGER) //
		.addScalar("codiceAnagrafe", Hibernate.INTEGER) //
		.addScalar("importo", Hibernate.BIG_DECIMAL) //
		.addScalar("dataRegistrazione", Hibernate.TIMESTAMP) //
		.addScalar("statoPagamento", Hibernate.STRING) //
		.addScalar("codiceIuv", Hibernate.STRING) //
		.addScalar("codiceAvviso", Hibernate.STRING) // 
		.addScalar("descrizioneCausale", Hibernate.STRING) //
		.addScalar("idPagamento", Hibernate.INTEGER);
	log.debug("queryFields {}{}", sqlFields, selectBody);
	// SET PARAMETERS
	sqlQuery.setParameter("idComune", ORMHelper.getIdcomune());
	sqlQuery.setParameter("valid", validata);
	if (statiDaVerificare != null) {
	    sqlQuery.setParameterList("statiPagamento", statiDaVerificare);
	}
	sqlQuery.setParameterList("autorizzazioni", autorizzazioniIds);
	// END SET PARAMETERS
	// paginazione
	int firstResult = (pagina - 1) * sizePagina;
	sqlQuery.setFirstResult(firstResult);
	sqlQuery.setMaxResults(sizePagina);
	log.debug("firstResult {}, maxResults{}", firstResult, sizePagina);
	sqlQuery.setResultTransformer(Transformers.aliasToBean(BollettazioneBean.class));
	List<BollettazioneBean> elems = sqlQuery.list();
	return new PageResult<BollettazioneBean>(elems, elems.size(), pagina, sizePagina, totalCount);
    }
}
