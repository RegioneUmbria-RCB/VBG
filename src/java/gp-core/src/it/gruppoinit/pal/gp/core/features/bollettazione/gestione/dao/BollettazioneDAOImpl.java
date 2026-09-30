package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.LivelloServizioDAO;
import it.gruppoinit.pal.gp.core.dao.MercatiFormuleCalcoloDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.SequenceJDBCWorker;
import it.gruppoinit.pal.gp.core.dao.helper.TitolaritaPagamentiEnum;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentriConc;
import it.gruppoinit.pal.gp.core.domain.BollGestDettAutorizz;
import it.gruppoinit.pal.gp.core.domain.BollGestDettRate;
import it.gruppoinit.pal.gp.core.domain.BollGestDettaglio;
import it.gruppoinit.pal.gp.core.domain.BollGestIstanzeoneri;
import it.gruppoinit.pal.gp.core.domain.BollGestMercatiDett;
import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.ConcessioneHelper;
import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CreazioneBollTestata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.DettaglioRateizzazione;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.IntervalloDate;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RigaDettaglioCalcolo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RigaDettaglioCalcoloMercati;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RigaDettaglioCalcoloMercatiIdPosteggioDataContoComparator;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.BollGestDettaglioDTO;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.BollettazioneIstanzeFiltriRicerca;
import it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti.VerificaPosizioneDebitoriaBean;
import it.gruppoinit.pal.gp.core.features.contabilita.ImportoIvato;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Repository
public class BollettazioneDAOImpl extends BaseDAOImpl implements BollettazioneDAO {

    private static final Logger log = LoggerFactory.getLogger(BollettazioneDAOImpl.class);
    private BollGestDettaglioDAO bollGestDettaglioDAO;
    private BollGestFiltriDAO bollGestFiltriDAO;
    private BollGestIstanzeoneriDAO bollGestIstanzeOneriDAO;
    private BollGestDettAutorizzDAO bollGestDettAutorizzDAO;
    private BollGestTestataDAO bollGestTestataDAO;
    private LivelloServizioDAO livelloServizioDAO;
    private MercatiFormuleCalcoloDAO mercatiFormuleCalcoloDAO;
    private BollGestMercatiDettDAO bollGestMercatiDettDAO;
    private BollGestDettRateDAO bollGestDettRateDAO;
    private BollGestOneriSequenceDAO bollGestOneriSequenceDAO;

    @Autowired
    public void setBollGestMercatiDettDAO(BollGestMercatiDettDAO bollGestMercatiDettDAO) {

	this.bollGestMercatiDettDAO = bollGestMercatiDettDAO;
    }

    @Autowired
    public void setBollGestDettRateDAO(BollGestDettRateDAO bollGestDettRateDAO) {

	this.bollGestDettRateDAO = bollGestDettRateDAO;
    }

    @Autowired
    public void setBollGestOneriSequenceDAO(BollGestOneriSequenceDAO bollGestOneriSequenceDAO) {

	this.bollGestOneriSequenceDAO = bollGestOneriSequenceDAO;
    }

    @Autowired
    public BollettazioneDAOImpl(BollGestDettaglioDAO bollGestDettaglio, BollGestFiltriDAO bollGestFiltriDAO,
	    BollGestIstanzeoneriDAO bollGestIstanzeOneriDAO, BollGestTestataDAO bollGestTestataDAO, LivelloServizioDAO livelloServizioDAO,
	    BollGestDettAutorizzDAO bollGestDettAutorizzDAO, MercatiFormuleCalcoloDAO mercatiFormuleCalcoloDAO) {

	this.bollGestDettaglioDAO = bollGestDettaglio;
	this.bollGestFiltriDAO = bollGestFiltriDAO;
	this.bollGestIstanzeOneriDAO = bollGestIstanzeOneriDAO;
	this.bollGestTestataDAO = bollGestTestataDAO;
	this.livelloServizioDAO = livelloServizioDAO;
	this.bollGestDettAutorizzDAO = bollGestDettAutorizzDAO;
	this.mercatiFormuleCalcoloDAO = mercatiFormuleCalcoloDAO;
    }

    @Override
    public List<RigaDettaglioCalcolo> findByFiltriBollettazioneIstanza(List<String> filtriCodiceComune, List<String> filtriScCodice,
	    List<Integer> filtriCodiceEndo, List<Integer> filtriCausaleOnere, IntervalloDate intervalloDate, Boolean conguaglio, boolean isAzienda) {

	log.debug("findByFiltriBollettazioneIstanza: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryBollettazioneIstanzeHelper queryHelper = new QueryBollettazioneIstanzeHelper(sessimpl, filtriCodiceComune, filtriScCodice,
		filtriCodiceEndo, filtriCausaleOnere, intervalloDate, conguaglio, isAzienda);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(RigaDettaglioCalcolo.class));
	return (List<RigaDettaglioCalcolo>) q.list();
    }

    @Override
    public Class getEntityClass() {

	return null;
    }

    @Override
    public <T> T getByIdForBollettazione(Class<T> cls, Integer id) {

	return (T) super.getById(cls, id);
    }

    public <T /*extends HasPkId*/> void save(List<T> entityList) {

	for (T entity : entityList) {
	    _validateEntityForInsertOrUpdate2(entity);
	    getHibernateTemplate().merge(entity);
	}
    }

    public <T /*extends HasPkId*/> void save(T entity) {

	_validateEntityForInsertOrUpdate2(entity);
	getHibernateTemplate().merge(entity);
    }

    @Override
    public void copiaRiferimentiIstanzeOneriSuBollGestDettaglio(BollGestDettaglio oldEntity, BollGestDettaglio newEntity) {

	Set<BollGestIstanzeoneri> bollGestIstanzeoneri = oldEntity.getBollGestIstanzeoneris();
	if (bollGestIstanzeoneri == null || bollGestIstanzeoneri.isEmpty()) {
	    return;
	}
	for (BollGestIstanzeoneri bollGestIstanzeonere : bollGestIstanzeoneri) {
	    BollGestIstanzeoneri newBollGestIstanzeonere = (BollGestIstanzeoneri) SerializationUtils.clone(bollGestIstanzeonere);
	    newBollGestIstanzeonere.setId(new PkId());
	    newBollGestIstanzeonere.setBollGestDettaglio(newEntity);
	    newBollGestIstanzeonere.setBollGestTestata(newEntity.getBollGestTestata());
	    this.save(newBollGestIstanzeonere);
	}
    }

    @Override
    public List<BollGestTestata> findTestateByCodiciRuoli(List<Integer> codiciRuoli, Integer firstResult, Integer maxResult) {

	return this.bollGestTestataDAO.findByResponsabiliRuoli(codiciRuoli, firstResult, maxResult);
    }

    @Override
    public Integer countTestateByCodiciRuoli(List<Integer> codiciRuoli) {

	return this.bollGestTestataDAO.countByResponsabiliRuoli(codiciRuoli);
    }

    @Override
    public void updateStatoTestata(Integer idBollettazione, String descrizioneStato) {

	this.bollGestTestataDAO.updateStato(idBollettazione, descrizioneStato);
    }

    @Override
    public List<BollGestDettaglioDTO> findRigheByIdBollettazioneAndAnagrafe(Integer idBollettazione, Integer idAnagrafica) {

	return this.bollGestDettaglioDAO.findByIdBollettazioneAndAnagrafe(idBollettazione, idAnagrafica);
    }

    @Override
    public Boolean esistonoRigheInviateASistemaPagamenti(Integer idBollettazione) {

	return this.bollGestDettaglioDAO.existsRigheInviateASistemaPagamenti(idBollettazione);
    }

    @Override
    public void updateRigheSetRiferimentiPosizioneDebitoria(Integer idDettPosizioneDebitoria, List<Integer> idRighe) {

	this.bollGestDettaglioDAO.updateRiferimentoPosizioneDebitoria(idRighe, idDettPosizioneDebitoria);
    }

    @Override
    public Set<VerificaPosizioneDebitoriaBean> findDettaglioPosizioniDebitorieByBollettazioneEAnagrafe(Integer idBollettazione,
	    Integer idAnagrafica) {

	return this.bollGestDettaglioDAO.findDettaglioPosizioniDebitorieByBollettazioneEAnagrafe(idBollettazione, idAnagrafica);
    }

    @Override
    public Set<VerificaPosizioneDebitoriaBean> findDettaglioPosizioniDebitorieByBollettazione(Integer idBollettazione) {

	return this.bollGestDettaglioDAO.findDettaglioPosizioniDebitorieByBollettazione(idBollettazione);
    }

    @Override
    public List<RigaDettaglioCalcoloMercati> findByFiltriBollettazioneMercato(String guid, TitolaritaPagamentiEnum titolarita,
	    List<Integer> filtriMercati, IntervalloDate intervalloDate, Boolean conguaglio) {

	log.debug("findByFiltriBollettazioneMercato: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	AbstractQueryBollettazioneMercatiHelper queryHelper = this.getQueryBuilder(guid, titolarita, sessimpl, filtriMercati, intervalloDate,
		conguaglio);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(RigaDettaglioCalcoloMercati.class));
	List<RigaDettaglioCalcoloMercati> righe = (List<RigaDettaglioCalcoloMercati>) q.list();
	if (!righe.isEmpty()) {
	    Collections.sort(righe, new RigaDettaglioCalcoloMercatiIdPosteggioDataContoComparator());
	}
	// Ordinamento per posteggio / data
	if (titolarita.equals(TitolaritaPagamentiEnum.PRIMO_CONCESSIONARIO)) {
	    Integer idPosteggio = -1;
	    Integer idAnagrafe = -1;
	    for (RigaDettaglioCalcoloMercati riga : righe) {
		if (idPosteggio.compareTo(riga.getIdPosteggio()) != 0) {
		    idAnagrafe = riga.getIdAnagrafe();
		}
		riga.setIdAnagrafe(idAnagrafe);
		idPosteggio = riga.getIdPosteggio();
	    }
	}
	return righe;
    }

    @Override
    public AbstractQueryBollettazioneMercatiHelper getQueryBuilder(String guid, TitolaritaPagamentiEnum titolarita, List<Integer> filtriMercati,
	    IntervalloDate intervalloDate, Boolean conguaglio) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	return this.getQueryBuilder(guid, titolarita, sessimpl, filtriMercati, intervalloDate, conguaglio);
    }

    private AbstractQueryBollettazioneMercatiHelper getQueryBuilder(String guid, TitolaritaPagamentiEnum titolarita,
	    SessionFactoryImplementor sessimpl, List<Integer> filtriMercati, IntervalloDate intervalloDate, Boolean conguaglio) {

	AbstractQueryBollettazioneMercatiHelper queryHelper = null;
	switch (titolarita) {
	    case CONCESSIONARIO_ATTUALE:
		queryHelper = new QueryBollettazioneMercatiAttualeConcessionarioHelper(guid, sessimpl, filtriMercati, intervalloDate, conguaglio);
		break;
	    case PRESENZE_EFFETTIVE:
		queryHelper = new QueryBollettazioneMercatiPresenzeEffettiveHelper(guid, sessimpl, filtriMercati, intervalloDate, conguaglio);
		break;
	    default:
		queryHelper = new QueryBollettazioneMercatiPresenzeAssenzeHelper(guid, sessimpl, filtriMercati, intervalloDate);
		break;
	}
	return queryHelper;
    }

    /*
    @SuppressWarnings("unchecked")
    @Override
    public List<ValoriLivelloServizio> findServiziConfiguratiByGiornataAndPosteggioAndUso(Date dataGiornata, Integer idPosteggio, Integer codiceUso) {
    
    log.debug("findServiziConfiguratiByGiornataAndPosteggioAndUso: recupero la sessionfactory e la casto a SessionFactoryImplementor");
    SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
    QueryMercatiDLivelliServizioHelper queryHelper = new QueryMercatiDLivelliServizioHelper(sessimpl, dataGiornata,
    	idPosteggio, codiceUso);
    String sql = queryHelper.buildQuery();
    SQLQuery q = getSession().createSQLQuery(sql);
    queryHelper.setFilterValues(q);
    queryHelper.setScalarProperties(q);
    q.setResultTransformer(Transformers.aliasToBean(ValoriLivelloServizio.class));
    List<ValoriLivelloServizio> result = (List<ValoriLivelloServizio>) q.list();
    return result;
    }
    */
    @Override
    public List<LivelloServizio> findLivelliServizio() {

	return livelloServizioDAO.findAll(null, null, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public Boolean esisteBollettazioneStessoPeriodo(CreazioneBollTestata bollTestata) {

	List<BollGestTestata> bollGestTestate = bollGestTestataDAO.findByBollCfgTipo(bollTestata.getBollCfgTipoId());
	if (bollGestTestate.isEmpty()) {
	    return false;
	} else {
	    for (BollGestTestata bgt : bollGestTestate) {
		if (bgt.getDallaData().compareTo(bollTestata.getIntervalloDate().getDataInizio()) == 0
			&& bgt.getAllaData().compareTo(bollTestata.getIntervalloDate().getDataFine()) == 0) {
		    return true;
		}
	    }
	}
	return false;
    }

    public ConcessioneHelper getInfoConcessione(Integer idAutorizzazioniConcessione, Boolean passaggioStorico) {

	ConcessioneHelper retVal = null;
	if (!passaggioStorico) {
	    AutorizzazioniConcessioni concessione = (AutorizzazioniConcessioni) this.getById(AutorizzazioniConcessioni.class,
		    idAutorizzazioniConcessione);
	    if (concessione != null) {
		retVal = new ConcessioneHelper(concessione.getTransientEstremiConcessione(), concessione.getMercatiUso(),
			concessione.getAutorizzazioniByFkAutconcAutatt());
	    }
	} else {
	    AutorizzazioniSubentriConc concessione = (AutorizzazioniSubentriConc) this.getById(AutorizzazioniSubentriConc.class,
		    idAutorizzazioniConcessione);
	    if (concessione != null) {
		retVal = new ConcessioneHelper(concessione.getTransientEstremiConcessione(), concessione.getMercatiUso(),
			concessione.getAutorizzazioniByFkAutconcAutatt());
	    }
	}
	return retVal;
    }

    @Override
    public ImportoIvato getImportoRigaValidaByTestataAnagrafeEContoEAutorizzazione(Integer idBollettazione, Integer idAnagrafe, Integer idConto,
	    Integer idAutorizzazione) {

	BollGestDettaglio riga = this.bollGestDettaglioDAO.getImportoRigaValidaByTestataAnagrafeEContoEAutorizzazione(idBollettazione, idAnagrafe,
		idConto, idAutorizzazione);
	if (riga != null) {
	    return new ImportoIvato(riga.getImportoSenzaIva(), riga.getIva(), riga.getImportoTotale());
	}
	return null;
    }

    @Override
    public BollGestTestata findBollettazionePrecedenteByDataAndTipologia(Integer codiceTipologiaBollettazione,
	    Date dataPartenzaBollettazioneAttuale) {

	return this.bollGestTestataDAO.findBollettazionePrecedenteByDataAndTipologia(codiceTipologiaBollettazione, dataPartenzaBollettazioneAttuale);
    }

    @Override
    public List<MercatiFormuleCalcolo> findFormuleByIntervallo(Date dataInizio, Date dataFine) {

	List<MercatiFormuleCalcolo> mfc = mercatiFormuleCalcoloDAO.findAll(null, null);
	for (Iterator<MercatiFormuleCalcolo> i = mfc.iterator(); i.hasNext();) {
	    MercatiFormuleCalcolo a = i.next();
	    if (a.getDataInizioValidita().after(dataFine)) {
		i.remove();
	    }
	    if (a.getDataFineValidita() != null && a.getDataFineValidita().before(dataInizio)) {
		i.remove();
	    }
	}
	return mfc;
    }

    @Override
    public void delete(Integer idBollettazione) {

	this.deleteBollGestMercatiDettByIdBollettazione(idBollettazione);
	this.deleteBollGestDettAutorizzByIdBollettazione(idBollettazione);
	this.deleteBollGestDettRateByIdBollettazione(idBollettazione);
	this.deleteFiltriByIdBollettazione(idBollettazione);
	this.deleteBollGestIstanzeOneriByIdBollettazione(idBollettazione);
	this.deleteOnerSequenceDaIdBollettazione(idBollettazione);
	this.deleteDettagliDaIdBollettazione(idBollettazione);
	this.delete(BollGestTestata.class, idBollettazione);
    }

    private void deleteBollGestDettAutorizzByIdBollettazione(Integer idBollettazione) {

	this.bollGestDettAutorizzDAO.deleteBollGestAutorizzazioniByIdBollettazione(idBollettazione);
    }

    private void deleteBollGestDettRateByIdBollettazione(Integer idBollettazione) {

	this.bollGestDettRateDAO.deleteByIdBollettazione(idBollettazione);
    }

    private void deleteFiltriByIdBollettazione(Integer idBollettazione) {

	this.bollGestFiltriDAO.deleteByIdBollettazione(idBollettazione);
    }

    private void deleteBollGestIstanzeOneriByIdBollettazione(Integer idBollettazione) {

	this.bollGestIstanzeOneriDAO.deleteByIdBollettazione(idBollettazione);
    }

    private void deleteBollGestMercatiDettByIdBollettazione(Integer idBollettazione) {

	this.bollGestMercatiDettDAO.deleteByIdBollettazione(idBollettazione);
    }

    private void deleteOnerSequenceDaIdBollettazione(Integer idBollettazione) {

	this.bollGestOneriSequenceDAO.deleteByIdBollettazione(idBollettazione);
    }

    private void deleteDettagliDaIdBollettazione(Integer idBollettazione) {

	//this.bollGestDettaglioDAO.updateAnnullaRettifiche(idBollettazione);
	this.bollGestDettaglioDAO.deleteByIdBollettazione(idBollettazione);
    }

    protected final String SCHEMA_NAME = "#SCHEMA_NAME#";

    @Override
    public Integer recuperaCodiceLetteraAccompagnamento(String cf_ente_creditore, Integer riIdPosizioneDebitoria) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String sql = "SELECT " + // 
		" boll_cfg_tipo.cod_lett_accompagnamento " + //
		"FROM " + //
		SCHEMA_NAME +
		"dett_posizione_debitoria " + //
		" INNER JOIN " + //
		SCHEMA_NAME +
		"boll_gest_dettaglio ON dett_posizione_debitoria.idcomune = boll_gest_dettaglio.idcomune " + //
		" AND dett_posizione_debitoria.id = boll_gest_dettaglio.fk_posdebdettaglio_id " + //
		" INNER JOIN " +
		SCHEMA_NAME +
		"boll_gest_testata ON boll_gest_dettaglio.idcomune = boll_gest_testata.idcomune " +
		" AND boll_gest_dettaglio.fk_bollgest_id = boll_gest_testata.id " +
		" INNER JOIN " +
		SCHEMA_NAME +
		"boll_cfg_tipo ON boll_gest_testata.idcomune = boll_cfg_tipo.idcomune " +
		" AND boll_gest_testata.fk_bolltipo_id = boll_cfg_tipo.id " +
		"WHERE " +
		" dett_posizione_debitoria.idcomune = ? " +
		" AND dett_posizione_debitoria.id_posizione_debitoria = ?" +
		" AND dett_posizione_debitoria.cf_ente_creditore = ?";
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	sql = sql.replaceAll(SCHEMA_NAME, schemaName + ".");
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, riIdPosizioneDebitoria);
	q.setString(2, cf_ente_creditore);
	q.addScalar("cod_lett_accompagnamento", Hibernate.INTEGER);
	List<Integer> result = (List<Integer>) q.list();
	if (result.size() > 0) {
	    return result.get(0);
	}
	return null;
    }

    @Override
    public List<BollGestDettaglio> findRigheValidabili(Integer idBollettazione) {

	return this.bollGestDettaglioDAO.findRigheValidabili(idBollettazione);
    }

    @Override
    public void copiaRiferimentiConcessioniSuBollGestDettaglio(BollGestDettaglio oldEntity, BollGestDettaglio newEntity) {

	Set<BollGestDettAutorizz> bollGestDettAutorizzazioni = oldEntity.getBollGestDettAutorizzazioni();
	if (bollGestDettAutorizzazioni == null || bollGestDettAutorizzazioni.isEmpty()) {
	    return;
	}
	for (BollGestDettAutorizz bollGestdettAutoriz : bollGestDettAutorizzazioni) {
	    BollGestDettAutorizz newBollGestDettAutoriz = (BollGestDettAutorizz) SerializationUtils.clone(bollGestdettAutoriz);
	    newBollGestDettAutoriz.setId(new PkId());
	    newBollGestDettAutoriz.setBollGestDettaglio(newEntity);
	    newBollGestDettAutoriz.setBollGestTestata(bollGestdettAutoriz.getBollGestTestata());
	    this.save(newBollGestDettAutoriz);
	    Set<BollGestMercatiDett> bollGestMercatiDetts = bollGestdettAutoriz.getBollGestMercatiDetts();
	    for (BollGestMercatiDett source : bollGestMercatiDetts) {
		BollGestMercatiDett copia = new BollGestMercatiDett();
		copia.setBollGestDettAutorizz(newBollGestDettAutoriz);
		copia.setImportoTotale(source.getImportoTotale());
		copia.setMercatiPresenzeT(source.getMercatiPresenzeT());
		copia.setMercatoUso(source.getMercatoUso());
		copia.setPosteggio(source.getPosteggio());
		copia.setBollGestTestata(source.getBollGestTestata());
		this.save(copia);
	    }
	}
    }

    @Override
    public void aggiornaDataScadenza(String idcomune, Integer idBollettazione, Date dataScadenza) {

	if (StringUtils.isBlank(idcomune) || idBollettazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile aggiornare la data di scadenza senza specificare l'identificativo della bollettazione da aggiornare");
	}
	if (dataScadenza == null) {
	    throw new IllegalArgumentException("La data di scadenza della bollettazione non può essere vuota");
	}
	BollGestTestata testata = (BollGestTestata) this.getById(BollGestTestata.class, idBollettazione);
	testata.setDataScadenza(dataScadenza);
	this.update(testata);
    }

    @Override
    public void inserisciRata(BollGestDettRate rata) {

	this.insert(rata);
    }

    @Override
    public List<DettaglioRateizzazione> findDettaglioRateizzazione(Integer idRiga) {

	String sql = "select " + //
		"numerorata as numeroRata, scadenza, importo_totale as importoTotale " + //
		"from " + //
		"boll_gest_dett_rate " + //
		"where " + // 
		"idcomune = ? and " + //
		"fk_bollgestdet_id = ? " + //
		"order by " + //
		"numerorata asc";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idRiga);
	q.addScalar("numeroRata", Hibernate.INTEGER);
	q.addScalar("scadenza", Hibernate.DATE);
	q.addScalar("importoTotale", Hibernate.BIG_DECIMAL);
	q.setResultTransformer(Transformers.aliasToBean(DettaglioRateizzazione.class));
	return (List<DettaglioRateizzazione>) q.list();
    }

    @Override
    public List<DettaglioRateizzazione> findDettaglioRateizzazionePerAnagrafica(Integer idBollettazione, Integer idAnagrafica) {

	String sql = "select " + //
		"boll_gest_dett_rate.numerorata as numeroRata, " + //
		"boll_gest_dett_rate.scadenza, " + //
		"boll_gest_dett_rate.importo_totale as importoTotale " + //
		"from " + //
		"boll_gest_dett_rate " + //
		"inner join boll_gest_dettaglio on " + //
		"boll_gest_dett_rate.idcomune = boll_gest_dettaglio.idcomune and " + //
		"boll_gest_dett_rate.fk_bollgestdet_id = boll_gest_dettaglio.id " + //
		"where " + // 
		"boll_gest_dettaglio.idcomune = ? and " + //
		"boll_gest_dettaglio.fk_bollgest_id = ? and " + //
		"boll_gest_dettaglio.fk_codiceanagrafe = ? and " + //
		"boll_gest_dettaglio.flag_validata = ? " + //
		"group by " + //
		"boll_gest_dett_rate.numerorata, boll_gest_dett_rate.scadenza, boll_gest_dett_rate.importo_totale " + //
		"order by " + //
		"boll_gest_dett_rate.numerorata asc";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idBollettazione);
	q.setInteger(2, idAnagrafica);
	q.setBoolean(3, true);
	q.addScalar("numeroRata", Hibernate.INTEGER);
	q.addScalar("scadenza", Hibernate.DATE);
	q.addScalar("importoTotale", Hibernate.BIG_DECIMAL);
	q.setResultTransformer(Transformers.aliasToBean(DettaglioRateizzazione.class));
	return (List<DettaglioRateizzazione>) q.list();
    }

    @Override
    public String findDescrizionePosteggio(Integer idPosteggio) {

	String sql = "SELECT mercati.descrizione,mercati_d.codiceposteggio FROM" + //
		" mercati_d INNER JOIN mercati ON mercati.idcomune=mercati_d.idcomune AND " + // 
		" mercati.codicemercato=mercati_d.fkcodicemercato  WHERE mercati_d.idcomune=? " + //
		" and IDPOSTEGGIO=?";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idPosteggio);
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("codiceposteggio", Hibernate.STRING);
	List<Object[]> l = q.list();
	for (Object[] o : l) {
	    String descrizione = (String) o[0];
	    String posteggio = (String) o[1];
	    return descrizione + " - " + posteggio;
	}
	return String.valueOf(idPosteggio);
    }

    @Override
    public int aggiornaSequenzaBollGestIstanzeOneri(final int totaleRecordDaInserire) {

	return super.aggiornaSequenza("BOLL_GEST_ISTANZEONERI.ID", "BOLL_GEST_ISTANZEONERI", "ID", totaleRecordDaInserire);
    }

    @Override
    public int aggiornaSequenzaBollGestDettaglio(int totaleRecordDaInserire) {

	return super.aggiornaSequenza("BOLL_GEST_DETTAGLIO.ID", "BOLL_GEST_DETTAGLIO", "ID", totaleRecordDaInserire);
    }

    @Override
    public int aggiornaSequenzaBollGestDettAutorizz(int totaleRecordDaInserire) {

	return super.aggiornaSequenza("BOLL_GEST_DETT_AUTORIZZ.ID", "BOLL_GEST_DETT_AUTORIZZ", "ID", totaleRecordDaInserire);
    }

    @Override
    public int aggiornaSequenzaBollGestMercatiDett(int totaleRecordDaInserire) {

	return super.aggiornaSequenza("BOLL_GEST_MERCATI_DETT.ID", "BOLL_GEST_MERCATI_DETT", "ID", totaleRecordDaInserire);
    }

    @Override
    public void inserisciRigheIstanzeOneri(BollettazioneIstanzeFiltriRicerca filtriRicerca, int numeroInizialeBollGestDettaglio,
	    int numeroInizialeBollGestIstanzeOneri, boolean conguaglio, boolean azienda, String guid, Integer bollTestataId, Date dataScadenza) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	getSession().flush();
	Dialect dialect = ((SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory()).getDialect();
	QueryBollettazioneIstanzeoneriHelper queryHelper = QueryBollettazioneIstanzeoneriHelper.forInsert(sessimpl, filtriRicerca, conguaglio,
		azienda, guid, bollTestataId, numeroInizialeBollGestDettaglio, numeroInizialeBollGestIstanzeOneri);
	String sql = queryHelper.buildQuery();
	InserimentoRigheJDBCWorker worker = new InserimentoRigheJDBCWorker(sql, queryHelper.getParameters(), azienda, dialect, guid, bollTestataId,
		dataScadenza);
	getSession().doWork(worker);
    }

    @Override
    public int contaRigheIstanzeOneri(BollettazioneIstanzeFiltriRicerca filtriRicerca, boolean conguaglio, boolean azienda) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryBollettazioneIstanzeoneriHelper queryHelper = QueryBollettazioneIstanzeoneriHelper.forCount(sessimpl, filtriRicerca, conguaglio,
		azienda);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	List<BigDecimal> rs = q.list();
	return ((BigDecimal) rs.get(0)).intValue();
    }

    @Override
    public List<BollGestDettaglioDTO> findBollGestDettaglioDTOByTestata(Integer idBollettazione) {

	return this.bollGestDettaglioDAO.findBollGestDettaglioDTOByTestata(idBollettazione);
    }

    @Override
    public List<Object[]> getSummaryForBollettazione(int idbollettazionetestata, int idanagrafe, boolean soloValidati) {

	String sql = "select boll_gest_testata.descrizione " + // 
		     " ,anagrafe.nominativo " + //
		     " ,anagrafe.nome " + //
		     " ,mercati.descrizione as manifestazione " + // 
		     " ,mercatipresenze_t.dataregistrazione " + //
		     " ,mercati_d.codiceposteggio " + //
		     " ,conti.descrizione as conto " + // 
		     " ,b.importo_totale as importo " + //
		     " ,COALESCE(autorizzazioni_subentri.autoriznumero, autorizzazioni.autoriznumero) AS autorizzazione " + //
		     " FROM boll_gest_mercati_dett b " + //
		     " INNER JOIN boll_gest_dett_autorizz ON boll_gest_dett_autorizz.idcomune = b.idcomune " + //
		     " AND boll_gest_dett_autorizz.id = b.fk_id_gest_autorizzazioni " + //
		     " LEFT JOIN autorizzazioni_subentri ON " + //
		     " autorizzazioni_subentri.idcomune=boll_gest_dett_autorizz.idcomune and " + //
		     " autorizzazioni_subentri.id=boll_gest_dett_autorizz.fk_autsubentri_id " + //
		     " inner join boll_gest_dettaglio on boll_gest_dettaglio.idcomune = boll_gest_dett_autorizz.idcomune " + //
		     " and boll_gest_dettaglio.id = boll_gest_dett_autorizz.fk_bollgestdet_id " + //
		     " inner join boll_gest_testata on boll_gest_dettaglio.idcomune = boll_gest_testata.idcomune " + //
		     " and boll_gest_dettaglio.fk_bollgest_id = boll_gest_testata.id " + //
		     " inner join anagrafe on anagrafe.idcomune = boll_gest_dettaglio.idcomune " + //
		     " and anagrafe.codiceanagrafe = boll_gest_dettaglio.fk_codiceanagrafe " + //
		     " inner join autorizzazioni on autorizzazioni.idcomune = boll_gest_dett_autorizz.idcomune " + //
		     " and autorizzazioni.id = boll_gest_dett_autorizz.fk_autorizzazione_id " + // 
		     " inner join mercati_uso on mercati_uso.idcomune = b.idcomune " + //
		     " and mercati_uso.id = b.fk_mercato_uso " + //
		     " inner join mercati on mercati_uso.idcomune = mercati.idcomune " + //
		     " and mercati_uso.fkcodicemercato = mercati.codicemercato " + //
		     " inner join mercatipresenze_t on mercatipresenze_t.idcomune = b.idcomune " + //
		     " and mercatipresenze_t.id = b.fk_mercatipresenzet_id " + //
		     " inner join mercati_d on mercati_d.idcomune = b.idcomune " + //
		     " and mercati_d.idposteggio = b.fk_idposteggio " + //
		     " inner join conti on conti.idcomune = boll_gest_dettaglio.idcomune " + //
		     " and conti.id = boll_gest_dettaglio.fk_conto_id " + //
		     " where " + //
		     " b.idcomune=:idcomune " + //
		     " and b.fk_bollgest_id = :idbollettazionetestata " + //
		     " and boll_gest_dettaglio.fk_codiceanagrafe = :codiceanagrafe "; //
	if (soloValidati) {
	    sql += " and boll_gest_dettaglio.flag_validata = :validata " + //
		   " and boll_gest_dettaglio.flag_eliminata = :eliminata "; //
	}
	sql += " order by mercatipresenze_t.dataregistrazione " + // 
	       " ,mercati.descrizione " + //
	       " ,mercati_d.codiceposteggio " + //
	       " ,conti.descrizione ";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setParameter("idcomune", ORMHelper.getIdcomune());
	q.setParameter("idbollettazionetestata", idbollettazionetestata);
	q.setParameter("codiceanagrafe", idanagrafe);
	if (soloValidati) {
	    q.setParameter("validata", 1);
	    q.setParameter("eliminata", 0);
	}
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("nominativo", Hibernate.STRING);
	q.addScalar("nome", Hibernate.STRING);
	q.addScalar("manifestazione", Hibernate.STRING);
	q.addScalar("dataregistrazione", Hibernate.TIMESTAMP);
	q.addScalar("codiceposteggio", Hibernate.STRING);
	q.addScalar("conto", Hibernate.STRING);
	q.addScalar("importo", Hibernate.BIG_DECIMAL);
	q.addScalar("autorizzazione", Hibernate.STRING);
	return q.list();
    }
}
