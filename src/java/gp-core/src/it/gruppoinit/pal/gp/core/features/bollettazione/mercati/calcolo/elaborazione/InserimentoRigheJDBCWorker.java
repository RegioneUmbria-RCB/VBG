package it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.elaborazione;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.jdbc.Work;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.domain.BollCfgMercati;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.domain.BollGestFiltri;
import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.BollettazioneAuditLogger;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CreazioneBollTestata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.IntervalloDate;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.ValoriLivelloServizio;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.AbstractQueryBollettazioneMercatiHelper;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollettazioneDAO;
import it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.coefficienti.ChiaveCoefficienteMercato;
import it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.coefficienti.ValoreCoefficienteMercato;
import it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.livelliservizio.ChiaveLivelloServizio;
import it.gruppoinit.pal.gp.core.features.bollettazione.mercati.calcolo.livelliservizio.ValoreLivelloServizio;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IRecuperaInformazioniGiornataService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleBuilder;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class InserimentoRigheJDBCWorker implements Work {

    private final Map<ChiaveCoefficienteMercato, List<ValoreCoefficienteMercato>> mappaCoefficienti;
    private final Map<ChiaveLivelloServizio, List<ValoreLivelloServizio>> mappaLivelliServizio;
    private final IRecuperaInformazioniGiornataService recuperaInformazioniGiornataService;
    private final BollGestTestata testata;
    private final BollettazioneDAO bollettazioneDAO;
    private final String guidOperazione;
    private final IntervalloDate intervalloDate;
    private final AbstractQueryBollettazioneMercatiHelper queryHelper;
    private final int batchSize = 1000;
    private final DialettoEnum _dialetto;

    //Gli worker non prevedono un ritorno per cui utilizzo una variabile in get che viene popolata a fine operazione
    public Integer getIdTestata() {

	return (testata != null && testata.getId() != null) ? testata.getId().getCodice() : null;
    }

    public InserimentoRigheJDBCWorker(SessionFactoryImplementor sfi, IRecuperaInformazioniGiornataService recuperaInformazioniGiornataService,
	    AbstractQueryBollettazioneMercatiHelper queryHelper, Boolean intestaAzienda, String guidOperazione, IntervalloDate intervalloDate,
	    BollettazioneDAO bollettazioneDAO, CreazioneBollTestata creazioneBollTestata, BollCfgTipo bollCfgTipo,
	    Map<ChiaveCoefficienteMercato, List<ValoreCoefficienteMercato>> mappaCoefficienti,
	    Map<ChiaveLivelloServizio, List<ValoreLivelloServizio>> mappaLivelliServizio) {

	this.recuperaInformazioniGiornataService = recuperaInformazioniGiornataService;
	this.queryHelper = queryHelper;
	this.guidOperazione = guidOperazione;
	this.intervalloDate = intervalloDate;
	this.bollettazioneDAO = bollettazioneDAO;
	this.testata = new BollGestTestata(creazioneBollTestata, bollCfgTipo);
	this.mappaCoefficienti = mappaCoefficienti;
	this.mappaLivelliServizio = mappaLivelliServizio;
	this._dialetto = DialettoEnum.fromHibernateDialect(sfi.getDialect().toString());
    }

    @Override
    public void execute(Connection connection) throws SQLException {

	BollettazioneAuditLogger.logger.info("{} - Inizio elaborazione fase1", this.guidOperazione);
	this.inserisciFase1(connection);
	connection.commit();
	Integer countRecord = this.verificaPresenzaDati(connection);
	BollettazioneAuditLogger.logger.info("{} - Record da elaborare {}", new Object[] { this.guidOperazione, countRecord });
	if (countRecord == 0) {
	    return;
	}
	BollettazioneAuditLogger.logger.info("{} - Inserimento BollGestTestata", this.guidOperazione);
	this.inserisciBollGestTestata();
	this.bollettazioneDAO.flush();
	BollettazioneAuditLogger.logger.info("{} - Inserimento BollGestFiltri", this.guidOperazione);
	this.inserisciBollGestFiltri();
	this.bollettazioneDAO.flush();
	BollettazioneAuditLogger.logger.info("{} - Inizio elaborazione fase2", this.guidOperazione);
	this.inserisciFase2(connection);
	connection.commit();
	this.bollettazioneDAO.flush();
	BollettazioneAuditLogger.logger.info("{} - Inizio elaborazione fase3", this.guidOperazione);
	this.inserisciFase3(connection);
	connection.commit();
	this.bollettazioneDAO.flush();
	BollettazioneAuditLogger.logger.info("{} - Inizio elaborazione formule", this.guidOperazione);
	this.elaboraFormule(connection);
	connection.commit();
	this.bollettazioneDAO.flush();
	BollettazioneAuditLogger.logger.info("{} - Inizio elaborazione fase4", this.guidOperazione);
	this.inserisciFase4(connection);
	connection.commit();
	this.bollettazioneDAO.flush();
	BollettazioneAuditLogger.logger.info("{} - Inserimento BollGestDettaglio", this.guidOperazione);
	this.inserisciBollGestDettaglio(connection);
	connection.commit();
	this.bollettazioneDAO.flush();
	BollettazioneAuditLogger.logger.info("{} - Inizio elaborazione fase5", this.guidOperazione);
	this.inserisciFase5(connection);
	connection.commit();
	this.bollettazioneDAO.flush();
	BollettazioneAuditLogger.logger.info("{} - Inserimento BollGestDettAutoriz", this.guidOperazione);
	this.inserisciBollGestDettAutoriz(connection);
	connection.commit();
	this.bollettazioneDAO.flush();
	BollettazioneAuditLogger.logger.info("{} - Inserimento BollGestMercatiDett", this.guidOperazione);
	this.inserisciBollGestMercatiDett(connection);
	connection.commit();
	this.bollettazioneDAO.flush();
	BollettazioneAuditLogger.logger.info("{} - Cancellazione tabelle temporanee", this.guidOperazione);
	this.cancellaFasi(connection);
	connection.commit();
	this.bollettazioneDAO.flush();
	BollettazioneAuditLogger.logger.info("{} - Fine elaborazione", this.guidOperazione);
    }

    private void cancellaFasi(Connection connection) throws SQLException {

	BollettazioneAuditLogger.logger.debug("{} - Cancellazione dati delle fasi intermedie", this.guidOperazione);
	String sqlDelete5 = "delete from boll_gest_fase5 where idcomune = ? and guid = ?";
	final PreparedStatement pstmt5 = connection.prepareStatement(sqlDelete5);
	int position = 1;
	pstmt5.setString(position++, ORMHelper.getIdcomune());
	pstmt5.setString(position++, this.guidOperazione);
	pstmt5.execute();
	pstmt5.close();
	String sqlDelete4 = "delete from boll_gest_fase4 where idcomune = ? and guid = ?";
	final PreparedStatement pstmt4 = connection.prepareStatement(sqlDelete4);
	position = 1;
	pstmt4.setString(position++, ORMHelper.getIdcomune());
	pstmt4.setString(position++, this.guidOperazione);
	pstmt4.execute();
	pstmt4.close();
	String sqlDelete3 = "delete from boll_gest_fase3 where idcomune = ? and guid = ?";
	final PreparedStatement pstmt3 = connection.prepareStatement(sqlDelete3);
	position = 1;
	pstmt3.setString(position++, ORMHelper.getIdcomune());
	pstmt3.setString(position++, this.guidOperazione);
	pstmt3.execute();
	pstmt3.close();
	String sqlDelete2 = "delete from boll_gest_fase2 where idcomune = ? and guid = ?";
	final PreparedStatement pstmt2 = connection.prepareStatement(sqlDelete2);
	position = 1;
	pstmt2.setString(position++, ORMHelper.getIdcomune());
	pstmt2.setString(position++, this.guidOperazione);
	pstmt2.execute();
	pstmt2.close();
	String sqlDelete1 = "delete from boll_gest_fase1 where idcomune = ? and guid = ?";
	final PreparedStatement pstmt1 = connection.prepareStatement(sqlDelete1);
	position = 1;
	pstmt1.setString(position++, ORMHelper.getIdcomune());
	pstmt1.setString(position++, this.guidOperazione);
	pstmt1.execute();
	pstmt1.close();
    }

    private void inserisciBollGestFiltri() {

	BollettazioneAuditLogger.logger.debug("{} - Salvataggio dei filtri", this.guidOperazione);
	List<BollGestFiltri> filtri = new ArrayList<BollGestFiltri>();
	for (BollCfgMercati mercatiConfigurati : testata.getBollCfgTipo().getBollCfgMercatis()) {
	    BollGestFiltri filtro = new BollGestFiltri();
	    filtro.setBollGestTestata(testata);
	    filtro.setMercati(mercatiConfigurati.getMercati());
	    mercatiConfigurati.getMercati();
	    filtri.add(filtro);
	}
	this.bollettazioneDAO.save(filtri);
    }

    private void inserisciBollGestTestata() {

	BollettazioneAuditLogger.logger.debug("{} - Creazione della testata", this.guidOperazione);
	this.bollettazioneDAO.save(testata);
    }

    private void inserisciFase1(Connection connection) throws SQLException {

	BollettazioneAuditLogger.logger.debug("{} - Salvataggio dati fase 1", this.guidOperazione);
	String sqlInsert = "INSERT INTO BOLL_GEST_FASE1(IDCOMUNE,GUID,PROVENIENZA,SUBENTRO,IDGIORNATA,DATAGIORNATA,IDPOSTEGGIO,IDANAGRAFE,IDRIFERIMENTO,IDAUTORIZZAZIONECONCESSIONE,ASSENZAGIUSTIFICATA,CONCPRESENTE,SPUNTPRESENTE,CATMERC,IDUSO,IDAUTORIZZAZIONISUBENTRI)";
	sqlInsert += " ";
	sqlInsert += queryHelper.buildQuery();
	final PreparedStatement pstmt = connection.prepareStatement(sqlInsert);
	for (ParameterHelper p : this.queryHelper.getParameters()) {
	    Type type = p.getType();
	    if (type instanceof TimestampType) {
		pstmt.setDate((p.getPosition() + 1), getData((Date) p.getValue()));
	    } else if (type instanceof StringType) {
		pstmt.setString((p.getPosition() + 1), (String) p.getValue());
	    } else if (type instanceof IntegerType) {
		pstmt.setInt((p.getPosition() + 1), (Integer) p.getValue());
	    } else {
		throw new NotImplementedException("Tipo non ancora gestito " + type);
	    }
	}
	pstmt.executeUpdate();
	pstmt.close();
    }

    private int verificaPresenzaDati(Connection connection) throws SQLException {

	String sqlSelect = "select count(*) from boll_gest_fase1 where idcomune = ? and guid = ?";
	final PreparedStatement pstmt = connection.prepareStatement(sqlSelect);
	int retVal = 0;
	try {
	    int position = 1;
	    pstmt.setString(position++, ORMHelper.getIdcomune());
	    pstmt.setString(position++, this.guidOperazione);
	    final ResultSet rs = pstmt.executeQuery();
	    if (rs.next()) {
		retVal = rs.getInt(1);
	    }
	    rs.close();
	    return retVal;
	} finally {
	    pstmt.close();
	}
    }

    private void inserisciFase2(Connection connection) throws SQLException {

	BollettazioneAuditLogger.logger.debug("{} - Salvataggio dati fase 2", this.guidOperazione);
	String sqlInsert = "insert into boll_gest_fase2(idcomune,guid,idcalcolo,calcolo,inizio_validita_calcolo,fine_validita_calcolo,formula,iduso,idcontabilita,contabilita,inizio_validita_contabilita,\r\n" +
		"  fine_validita_contabilita,idconto,conto,iva,fine_validita_conto)" + //
		" " + //
		" select" + //
		"   mercati_formule_calcolo.idcomune," + //
		"   ? as guid," + //
		"   mercati_formule_calcolo.id as idcalcolo," + //
		"   mercati_formule_calcolo.descrizione as calcolo," + //
		"   mercati_formule_calcolo.data_inizio_validita as inizio_validita_calcolo," + //
		"   coalesce(mercati_formule_calcolo.data_fine_validita,?) as fine_validita_calcolo," + //
		"   mercati_formule_calcolo.formula," + //
		"   mercati_formule_calcolo.fk_uso as iduso," + //
		"   mercati_contabilita_tributi.id as idcontabilita," + //
		"   mercati_contabilita_tributi.descrizione as contabilita," + //
		"   mercati_contabilita_tributi.data_inizio_validita as inizio_validita_contabilita," + //
		"   coalesce(mercati_contabilita_tributi.data_fine_validita,?) as fine_validita_contabilita," + //
		"   conti.id as idconto," + //
		"   conti.descrizione as conto," + //
		"   conti.iva," + //
		"   coalesce(conti.datascadenza,?) as fine_validita_conto" + //
		"  from" + //
		"   mercati_contabilita_tributi" + //
		"     inner join mercati_formule_calcolo on" + //
		"       mercati_contabilita_tributi.idcomune = mercati_formule_calcolo.idcomune and" + //
		"       mercati_contabilita_tributi.fk_mercati_formule = mercati_formule_calcolo.id" + //
		"     inner join conti on " + //
		"       mercati_contabilita_tributi.idcomune = conti.idcomune and" + //
		"       mercati_contabilita_tributi.fk_conti = conti.id" + //
		"  where" + //
		"   mercati_formule_calcolo.idcomune = ? and" + //
		"   mercati_formule_calcolo.contesto = ? and" + //
		"   (conti.datascadenza is null or conti.datascadenza >= ?) and" + //
		"   mercati_contabilita_tributi.data_inizio_validita <= ? and" + //
		"   mercati_contabilita_tributi.data_fine_validita >= ? and" + //
		"   mercati_formule_calcolo.data_inizio_validita <= ? and" + //
		"   mercati_formule_calcolo.data_fine_validita >= ?";
	final PreparedStatement pstmt = connection.prepareStatement(sqlInsert);
	try {
	    java.sql.Date dataInizio = this.getData(this.intervalloDate.getDataInizio());
	    java.sql.Date dataFine = this.getData(this.intervalloDate.getDataFine());
	    int position = 1;
	    pstmt.setString(position++, this.guidOperazione);
	    pstmt.setDate(position++, dataFine);
	    pstmt.setDate(position++, dataFine);
	    pstmt.setDate(position++, dataFine);
	    pstmt.setString(position++, ORMHelper.getIdcomune());
	    pstmt.setString(position++, "BOLLETTAZIONE");
	    pstmt.setDate(position++, dataInizio);
	    pstmt.setDate(position++, dataFine);
	    pstmt.setDate(position++, dataInizio);
	    pstmt.setDate(position++, dataFine);
	    pstmt.setDate(position++, dataInizio);
	    pstmt.executeUpdate();
	} finally {
	    pstmt.close();
	}
    }

    private void inserisciFase3(Connection connection) throws SQLException {

	BollettazioneAuditLogger.logger.debug("{} - Salvataggio dati fase 3", this.guidOperazione);
	String sqlInsert = "insert into boll_gest_fase3(idcomune,guid,provenienza,calcolo,idanagrafe,idgiornata,idposteggio,iduso,concpresente,spuntpresente,assenzagiustificata,catmerc,idconto,formula,importo_senza_iva,iva,importo_totale,flag_conguaglio)" + //
		" " + //
		"select" + //
		" boll_gest_fase1.idcomune," + //
		" boll_gest_fase1.guid," + //
		" boll_gest_fase1.provenienza," + //
		" boll_gest_fase2.calcolo," + //
		" boll_gest_fase1.idanagrafe," + //
		" boll_gest_fase1.idgiornata," + //
		" boll_gest_fase1.idposteggio," + //
		" boll_gest_fase1.iduso," + //
		" boll_gest_fase1.concpresente," + //
		" boll_gest_fase1.spuntpresente," + //
		" boll_gest_fase1.assenzagiustificata," + //
		" boll_gest_fase1.catmerc," + //
		" boll_gest_fase2.idconto," + //
		" boll_gest_fase2.formula," + //
		" null as importo_senza_iva," + //
		" boll_gest_fase2.iva," + //
		" null as importo_totale," + //
		" 0 as flag_conguaglio " + //
		"from" + //
		" boll_gest_fase1" + //
		"  inner join boll_gest_fase2 on" + //
		"    boll_gest_fase1.idcomune = boll_gest_fase2.idcomune and" + //
		"    boll_gest_fase1.guid = boll_gest_fase2.guid and" + //
		"    boll_gest_fase1.iduso = boll_gest_fase2.iduso and" + //
		"    boll_gest_fase1.datagiornata between boll_gest_fase2.inizio_validita_calcolo and boll_gest_fase2.fine_validita_calcolo and" + //
		"    boll_gest_fase1.datagiornata between boll_gest_fase2.inizio_validita_contabilita and boll_gest_fase2.fine_validita_contabilita and" + //
		"    boll_gest_fase1.datagiornata <= boll_gest_fase2.fine_validita_conto " + //
		"where" + //
		" boll_gest_fase1.idcomune = ? and" + //
		" boll_gest_fase1.guid = ?";
	final PreparedStatement pstmt = connection.prepareStatement(sqlInsert);
	try {
	    int position = 1;
	    pstmt.setString(position++, ORMHelper.getIdcomune());
	    pstmt.setString(position++, this.guidOperazione);
	    pstmt.executeUpdate();
	} finally {
	    pstmt.close();
	}
    }

    private String getQueryPerFase4() {

	String sql = " select" + //
		"  boll_gest_fase3.idcomune," + //
		"  boll_gest_fase3.guid," + //
		"  mercati.codicemercato as idmercato," + //
		"  boll_gest_fase3.idposteggio,";
	if (DialettoEnum.ORACLE.equals(this._dialetto)) {
	    sql += " boll_gest_fase3.calcolo || ' ' || mercati.descrizione || ' ' || mercati_d.codiceposteggio as descrizione,";
	} else {
	    sql += "  group_concat(distinct concat_ws(' ',boll_gest_fase3.calcolo, mercati.descrizione, mercati_d.codiceposteggio) separator ', ') as descrizione,";
	}
	sql += "  boll_gest_fase3.idanagrafe as fk_codiceanagrafe," + //
		"  boll_gest_fase3.idconto as fk_conto_id," + //
		"  boll_gest_fase3.iva," + //
		"  sum(boll_gest_fase3.importo_senza_iva) as importo_senza_iva," + //
		"  sum(boll_gest_fase3.importo_totale) as importo_totale " + //
		" from" + //
		"  boll_gest_fase3" + //
		"    inner join mercati_d on " + //
		"      boll_gest_fase3.idcomune = mercati_d.idcomune and " + //
		"      boll_gest_fase3.idposteggio = mercati_d.idposteggio" + //
		"    inner join mercati on " + //
		"      mercati_d.idcomune = mercati.idcomune and " + //
		"      mercati_d.fkcodicemercato = mercati.codicemercato " + //
		" where" + //
		"  boll_gest_fase3.idcomune = ? and" + //
		"  boll_gest_fase3.guid = ? " + //
		" group by" + //
		"  boll_gest_fase3.idcomune, boll_gest_fase3.guid, boll_gest_fase3.idanagrafe, boll_gest_fase3.idconto," + //
		"  boll_gest_fase3.iva, mercati.codicemercato, mercati.descrizione, boll_gest_fase3.idposteggio, " + //
		"  mercati_d.codiceposteggio, boll_gest_fase3.calcolo";
	return sql;
    }

    private Integer contaRecordPerFase4(Connection connection) throws SQLException {

	String sqlCount = "select count(*) from (" + this.getQueryPerFase4() + ") tmp";
	final PreparedStatement pstmt = connection.prepareStatement(sqlCount);
	Integer retVal = null;
	try {
	    int position = 1;
	    pstmt.setString(position++, ORMHelper.getIdcomune());
	    pstmt.setString(position++, this.guidOperazione);
	    final ResultSet rs = pstmt.executeQuery();
	    if (rs.next()) {
		retVal = rs.getInt(1);
	    } else {
		retVal = 1;
	    }
	    rs.close();
	    return retVal;
	} finally {
	    pstmt.close();
	}
    }

    private void inserisciFase4(Connection connection) throws SQLException {

	Integer recordTotali = this.contaRecordPerFase4(connection);
	Integer primoIdDettaglio = this.bollettazioneDAO.aggiornaSequenzaBollGestDettaglio(recordTotali);
	BollettazioneAuditLogger.logger.info("{} - Record da inserire: {}, primo id: {}",
		new Object[] { this.guidOperazione, recordTotali, primoIdDettaglio });
	primoIdDettaglio--;
	if (DialettoEnum.MYSQL.equals(this._dialetto)) {
	    String sqlSet = "set @idbollgestdett:= ?";
	    final PreparedStatement pstmtSet = connection.prepareStatement(sqlSet);
	    try {
		pstmtSet.setInt(1, primoIdDettaglio);
		pstmtSet.executeUpdate();
	    } finally {
		pstmtSet.close();
	    }
	}
	String sqlInsert = "insert into boll_gest_fase4(idcomune,guid,idbollgest,idbollgestdett,idmercato,idposteggio," + //
		"flag_validata,fk_rettifica_id,descrizione,flag_ins_auto," + //
		"fk_codiceanagrafe,fk_conto_id,fk_posdebdettaglio_id,flag_eliminata,note_utente,note_sistema," + //
		"flag_rettificata,importo_senza_iva,iva,importo_totale,flag_conguaglio,data_scadenza)" + //
		" " + //
		"select idcomune, guid, ? as idbollgest, ";
	if (DialettoEnum.ORACLE.equals(this._dialetto)) {
	    sqlInsert += "? + ROW_NUMBER() over  (ORDER BY idcomune, guid) as idbollgestdett,";
	} else {
	    sqlInsert += "@idbollgestdett := @idbollgestdett + 1 as idbollgestdett,";
	}
	sqlInsert += " idmercato, idposteggio, ? as flag_validata, null as fk_rettifica_id, descrizione, ? as flag_ins_auto, " + //
		"fk_codiceanagrafe, fk_conto_id, null as fk_posdebdettaglio_id, " + //
		"? as flag_eliminata, null as note_utente, null as note_sistema, ? as flag_rettificata, importo_senza_iva," + //
		"iva, importo_totale, ? as flag_conguaglio, ? as data_scadenza " + //
		"from " + //
		"( " + //
		this.getQueryPerFase4() + //
		") tmp";
	final PreparedStatement pstmt = connection.prepareStatement(sqlInsert);
	try {
	    int position = 1;
	    pstmt.setInt(position++, this.testata.getId().getCodice());
	    if (DialettoEnum.ORACLE.equals(this._dialetto)) {
		pstmt.setInt(position++, primoIdDettaglio); // valore iniziale di idbollgestdett
	    }
	    pstmt.setInt(position++, 0); // flag_validata
	    pstmt.setInt(position++, 1); // flag_ins_auto
	    pstmt.setInt(position++, 0); // flag_eliminata
	    pstmt.setInt(position++, 0); // flag_rettificata
	    pstmt.setInt(position++, 0); // flag_conguaglio
	    pstmt.setDate(position++, this.getData(this.testata.getDataScadenza()));
	    pstmt.setString(position++, ORMHelper.getIdcomune());
	    pstmt.setString(position++, this.guidOperazione);
	    pstmt.executeUpdate();
	} finally {
	    pstmt.close();
	}
    }

    private String getQueryPerMercatiDett() {

	return "select " + //
	       " boll_gest_fase4.idcomune," + //
	       " boll_gest_dett_autorizz.id as fk_id_gest_autorizzazioni," + //
	       " boll_gest_fase1.idposteggio as fk_idposteggio," + //
	       " boll_gest_fase1.iduso as fk_mercato_uso," + //
	       " boll_gest_fase1.idgiornata as fk_mercatipresenzet_id," + //
	       " boll_gest_fase3.importo_totale," + //
	       " boll_gest_fase4.idbollgest as fk_bollgest_id " + //
	       "from" + //
	       " boll_gest_fase4" + //
	       "   inner join boll_gest_fase3 on" + //
	       "       boll_gest_fase4.idcomune = boll_gest_fase3.idcomune and" + //
	       "       boll_gest_fase4.guid = boll_gest_fase3.guid and" + //
	       "       boll_gest_fase4.fk_conto_id = boll_gest_fase3.idconto and" + //
	       "       boll_gest_fase4.fk_codiceanagrafe = boll_gest_fase3.idanagrafe and" + //
	       "       boll_gest_fase4.idposteggio = boll_gest_fase3.idposteggio" + //
	       "   inner join boll_gest_fase1 on" + //
	       "        boll_gest_fase3.idcomune = boll_gest_fase1.idcomune and" + //
	       "        boll_gest_fase3.guid = boll_gest_fase1.guid and" + //
	       "        boll_gest_fase3.idgiornata = boll_gest_fase1.idgiornata and" + //
	       "        boll_gest_fase3.idposteggio = boll_gest_fase1.idposteggio and" + //
	       "        boll_gest_fase3.iduso = boll_gest_fase1.iduso and" + //
	       "        boll_gest_fase3.idanagrafe = boll_gest_fase1.idanagrafe" + //
	       "   inner join boll_gest_dett_autorizz on" + //
	       "        boll_gest_dett_autorizz.idcomune = boll_gest_fase4.idcomune and" + //
	       "        boll_gest_dett_autorizz.fk_bollgestdet_id = boll_gest_fase4.idbollgestdett and" + //
	       "        boll_gest_dett_autorizz.idcomune = boll_gest_fase1.idcomune and" + //
	       "        boll_gest_dett_autorizz.fk_autorizzazione_id = boll_gest_fase1.idriferimento " + //
	       "where" + //
	       " boll_gest_fase4.idcomune = ? and " + //
	       " boll_gest_fase4.guid = ? and " + //
	       " boll_gest_fase4.importo_totale > 0";
    }

    private Integer contaRecordPerDettAutorizz(Connection connection) throws SQLException {

	String sqlCount = "select count(*) from boll_gest_fase5 where idcomune = ? and guid = ?";
	final PreparedStatement pstmt = connection.prepareStatement(sqlCount);
	Integer retVal = null;
	try {
	    int position = 1;
	    pstmt.setString(position++, ORMHelper.getIdcomune());
	    pstmt.setString(position++, this.guidOperazione);
	    final ResultSet rs = pstmt.executeQuery();
	    if (rs.next()) {
		retVal = rs.getInt(1);
	    } else {
		retVal = 1;
	    }
	    rs.close();
	    return retVal;
	} finally {
	    pstmt.close();
	}
    }

    private Integer contaRecordPerMercatiDett(Connection connection) throws SQLException {

	String sqlCount = "select count(*) from (" + this.getQueryPerMercatiDett() + ") tmp";
	final PreparedStatement pstmt = connection.prepareStatement(sqlCount);
	Integer retVal = null;
	try {
	    int position = 1;
	    pstmt.setString(position++, ORMHelper.getIdcomune());
	    pstmt.setString(position++, this.guidOperazione);
	    final ResultSet rs = pstmt.executeQuery();
	    if (rs.next()) {
		retVal = rs.getInt(1);
	    } else {
		retVal = 1;
	    }
	    return retVal;
	} finally {
	    pstmt.close();
	}
    }

    private void inserisciFase5(Connection connection) throws SQLException {

	String sqlInsert = "insert into boll_gest_fase5 (idcomune, guid, fk_autorizzazione_id, " + //
		"fk_bollgestdet_id, fk_bollgest_id, fk_autsubentri_id)" + //
		" " + //
		"select " + //
		" boll_gest_fase4.idcomune," + //
		" boll_gest_fase4.guid, " + //
		" boll_gest_fase1.idriferimento as fk_autorizzazione_id," + //
		" boll_gest_fase4.idbollgestdett as fk_bollgestdet_id," + //
		" boll_gest_fase4.idbollgest as fk_bollgest_id," + //
		" boll_gest_fase1.idautorizzazionisubentri as fk_autsubentri_id " + //
		"from" + //
		" boll_gest_fase4" + //
		"   inner join boll_gest_fase3 on" + //
		"     boll_gest_fase4.idcomune = boll_gest_fase3.idcomune and" + //
		"     boll_gest_fase4.guid = boll_gest_fase3.guid and" + //
		"     boll_gest_fase4.fk_codiceanagrafe = boll_gest_fase3.idanagrafe and" + //
		"     boll_gest_fase4.fk_conto_id = boll_gest_fase3.idconto and" + //
		"     boll_gest_fase4.idposteggio = boll_gest_fase3.idposteggio" + //
		"   inner join boll_gest_fase1 on" + //
		"     boll_gest_fase4.idcomune = boll_gest_fase1.idcomune and" + //
		"     boll_gest_fase4.guid = boll_gest_fase1.guid and" + //
		"     boll_gest_fase4.fk_codiceanagrafe = boll_gest_fase1.idanagrafe and" + //
		"     boll_gest_fase4.idposteggio = boll_gest_fase1.idposteggio " + //
		"where" + //
		" boll_gest_fase4.idcomune = ? and " + //
		" boll_gest_fase4.guid = ? and " + //
		" boll_gest_fase4.importo_totale > 0 " + //
		"group by" + //
		" boll_gest_fase4.idcomune," + //
		" boll_gest_fase4.guid, " + //
		" boll_gest_fase1.idriferimento," + //
		" boll_gest_fase4.idbollgestdett," + //
		" boll_gest_fase4.idbollgest," + //
		" boll_gest_fase1.idautorizzazionisubentri";
	final PreparedStatement pstmt = connection.prepareStatement(sqlInsert);
	try {
	    int position = 1;
	    pstmt.setString(position++, ORMHelper.getIdcomune());
	    pstmt.setString(position++, this.guidOperazione);
	    pstmt.executeUpdate();
	} finally {
	    pstmt.close();
	}
    }

    private void inserisciBollGestDettAutoriz(Connection connection) throws SQLException {

	Integer recordTotali = this.contaRecordPerDettAutorizz(connection);
	Integer primoId = this.bollettazioneDAO.aggiornaSequenzaBollGestDettAutorizz(recordTotali);
	BollettazioneAuditLogger.logger.info("{} - Record da inserire: {}, primo id: {}",
		new Object[] { this.guidOperazione, recordTotali, primoId });
	primoId--;
	if (DialettoEnum.MYSQL.equals(this._dialetto)) {
	    String sqlSet = "set @id:= ?";
	    final PreparedStatement pstmtSet = connection.prepareStatement(sqlSet);
	    try {
		pstmtSet.setInt(1, primoId);
		pstmtSet.executeUpdate();
	    } finally {
		pstmtSet.close();
	    }
	}
	String sqlInsert = "insert into boll_gest_dett_autorizz(idcomune,id,fk_autorizzazione_id,fk_bollgestdet_id," + //
		"fk_bollgest_id, fk_autsubentri_id)" + //
		" " + //
		"select" + //
		" idcomune, ";
	if (DialettoEnum.ORACLE.equals(this._dialetto)) {
	    sqlInsert += "? + ROW_NUMBER() over  (ORDER BY idcomune, fk_autorizzazione_id,fk_bollgestdet_id,fk_bollgest_id, fk_autsubentri_id) as id,";
	} else {
	    sqlInsert += "@id := @id + 1 as id,";
	}
	sqlInsert += " fk_autorizzazione_id, fk_bollgestdet_id, fk_bollgest_id, fk_autsubentri_id " + //
		"from " + //
		" boll_gest_fase5 " + //
		"where " + //
		" boll_gest_fase5.idcomune = ? and" + //
		" boll_gest_fase5.guid = ?";
	final PreparedStatement pstmt = connection.prepareStatement(sqlInsert);
	try {
	    int position = 1;
	    if (DialettoEnum.ORACLE.equals(this._dialetto)) {
		pstmt.setInt(position++, primoId); // valore iniziale di idbollgestdett
	    }
	    pstmt.setString(position++, ORMHelper.getIdcomune());
	    pstmt.setString(position++, this.guidOperazione);
	    pstmt.executeUpdate();
	} finally {
	    pstmt.close();
	}
    }

    private void inserisciBollGestMercatiDett(Connection connection) throws SQLException {

	Integer recordTotali = this.contaRecordPerMercatiDett(connection);
	Integer primoId = this.bollettazioneDAO.aggiornaSequenzaBollGestMercatiDett(recordTotali);
	primoId--;
	BollettazioneAuditLogger.logger.info("{} - Record da inserire: {}, primo id: {}",
		new Object[] { this.guidOperazione, recordTotali, primoId });
	if (DialettoEnum.MYSQL.equals(this._dialetto)) {
	    String sqlSet = "set @id:= ?";
	    final PreparedStatement pstmtSet = connection.prepareStatement(sqlSet);
	    try {
		pstmtSet.setInt(1, primoId);
		pstmtSet.executeUpdate();
	    } finally {
		pstmtSet.close();
	    }
	}
	String sqlInsert = "insert into boll_gest_mercati_dett(idcomune,id,fk_id_gest_autorizzazioni,fk_idposteggio," + //
		"fk_mercato_uso, fk_mercatipresenzet_id,importo_totale,fk_bollgest_id)" + //
		" " + //
		"select idcomune,";
	if (DialettoEnum.ORACLE.equals(this._dialetto)) {
	    sqlInsert += "? + ROW_NUMBER() over  (ORDER BY idcomune, fk_id_gest_autorizzazioni, fk_idposteggio, fk_mercato_uso, fk_mercatipresenzet_id, importo_totale, fk_bollgest_id) as id,";
	} else {
	    sqlInsert += "@id := @id + 1 as id,";
	}
	sqlInsert += " fk_id_gest_autorizzazioni, fk_idposteggio, fk_mercato_uso, fk_mercatipresenzet_id,importo_totale,fk_bollgest_id " + //
		"from " + //
		"( " + //
		this.getQueryPerMercatiDett() + //
		") tmp";
	final PreparedStatement pstmt = connection.prepareStatement(sqlInsert);
	try {
	    int position = 1;
	    if (DialettoEnum.ORACLE.equals(this._dialetto)) {
		pstmt.setInt(position++, primoId); // valore iniziale di idbollgestdett
	    }
	    pstmt.setString(position++, ORMHelper.getIdcomune());
	    pstmt.setString(position++, this.guidOperazione);
	    pstmt.executeUpdate();
	} finally {
	    pstmt.close();
	}
    }

    private void inserisciBollGestDettaglio(Connection connection) throws SQLException {

	BollettazioneAuditLogger.logger.debug("{} - Salvataggio boll_gest_dettaglio", this.guidOperazione);
	String sqlInsert = "insert into boll_gest_dettaglio(idcomune,id,fk_bollgest_id,flag_validata,fk_rettifica_id," + //
		"descrizione,flag_ins_auto,fk_codiceanagrafe,fk_conto_id,fk_posdebdettaglio_id,flag_eliminata," + //
		"note_utente,note_sistema,flag_rettificata,importo_senza_iva,iva,importo_totale,flag_conguaglio,data_scadenza)" + //
		" " + //
		"select" + //
		" idcomune, idbollgestdett as id, idbollgest as fk_bollgest_id, flag_validata, fk_rettifica_id," + //
		" descrizione, flag_ins_auto, fk_codiceanagrafe, fk_conto_id, fk_posdebdettaglio_id, flag_eliminata, note_utente," + //
		" note_sistema, flag_rettificata, importo_senza_iva, iva, importo_totale, flag_conguaglio, data_scadenza " + //
		"from" + //
		" boll_gest_fase4 " + //
		"where" + //
		" idcomune = ? and " + //
		" guid = ? and " + //
		" importo_totale > 0";
	final PreparedStatement pstmt = connection.prepareStatement(sqlInsert);
	try {
	    int position = 1;
	    pstmt.setString(position++, ORMHelper.getIdcomune());
	    pstmt.setString(position++, this.guidOperazione);
	    pstmt.executeUpdate();
	} finally {
	    pstmt.close();
	}
    }

    private void elaboraFormule(Connection connection) throws SQLException {

	//una tantum: coefficienti mercati
	List<LivelloServizio> serviziDisponibili = recuperaInformazioniGiornataService.livelliDiServizioElencoCompletoDisponibili();
	//rileggo i dati
	String sqlSelect = "" + //
		"select " + //
		" boll_gest_fase3.formula, boll_gest_fase3.idconto, boll_gest_fase3.iva," + //
		" boll_gest_fase3.concpresente, boll_gest_fase3.spuntpresente, boll_gest_fase3.assenzagiustificata," + //
		" boll_gest_fase3.catmerc, boll_gest_fase3.provenienza, boll_gest_fase3.idgiornata," + //
		" boll_gest_fase3.idposteggio, boll_gest_fase3.iduso," + //
		" mercati.codicemercato, mercati.fk_categoria_mercato," + //
		" mercati_d.fk_posteggisettori_id, mercatipresenze_d.cat_merc," + //
		" mercatipresenze_t.fk_codice_conc_uso, mercatipresenze_t.dataregistrazione " + //
		"from " + //
		" boll_gest_fase3 " + //
		"  inner join mercati_d on" + //
		"   boll_gest_fase3.idcomune = mercati_d.idcomune and" + //
		"   boll_gest_fase3.idposteggio = mercati_d.idposteggio " + //
		"  inner join mercati on" + //
		"   mercati_d.idcomune = mercati.idcomune and" + //
		"   mercati_d.fkcodicemercato = mercati.codicemercato " + //
		"  inner join mercatipresenze_t on" + //
		"   boll_gest_fase3.idcomune = mercatipresenze_t.idcomune and" + //
		"   boll_gest_fase3.idgiornata = mercatipresenze_t.id " + //
		"  inner join mercatipresenze_d on" + //
		"   boll_gest_fase3.idcomune = mercatipresenze_d.idcomune and" + //
		"   boll_gest_fase3.idgiornata = mercatipresenze_d.fkidtestata and" + //
		"   boll_gest_fase3.idposteggio = mercatipresenze_d.fkidposteggio " + //
		"where" + //
		" boll_gest_fase3.idcomune = ? and" + //
		" boll_gest_fase3.guid = ?";
	PreparedStatement pstmtSelect = null;
	ResultSet rs = null;
	PreparedStatement pstmtUpdate = null;
	try {
	    //
	    pstmtSelect = connection.prepareStatement(sqlSelect);
	    int position = 1;
	    pstmtSelect.setString(position++, ORMHelper.getIdcomune());
	    pstmtSelect.setString(position++, this.guidOperazione);
	    rs = pstmtSelect.executeQuery();
	    //
	    String sqlUpdate = "" + //
		    "update" + //
		    " boll_gest_fase3 " + //
		    "set" + //
		    " importo_senza_iva = ?," + //
		    " importo_totale = ? " + //
		    "where" + //
		    " idcomune = ? and" + //
		    " guid = ? and" + //
		    " idgiornata = ? and" + //
		    " iduso = ? and" + //
		    " idposteggio = ? and" + //
		    " idconto = ?";
	    pstmtUpdate = connection.prepareStatement(sqlUpdate);
	    int count = 0;
	    //FormulaEvalService formulaEvalService = new FormulaEvalService();
	    while (rs.next()) {
		String formula = rs.getString("formula");
		Integer idConto = rs.getInt("idconto");
		Integer iva = rs.getInt("iva");
		boolean concessionarioPresente = rs.getInt("concpresente") == 1;
		boolean spuntistaPresente = rs.getInt("spuntpresente") == 1;
		boolean assenzaGiustificata = rs.getInt("assenzagiustificata") == 1;
		String catMerc = rs.getString("catmerc");
		String provenienza = rs.getString("provenienza");
		Integer idGiornata = rs.getInt("idgiornata");
		Integer idMercato = rs.getInt("codicemercato");
		Integer idCategoriaMercato = rs.getInt("fk_categoria_mercato");
		Integer idPosteggio = rs.getInt("idposteggio");
		Integer idSettorePosteggio = rs.getInt("fk_posteggisettori_id");
		Integer idUso = rs.getInt("iduso");
		String codiceIstat = rs.getString("cat_merc");
		Integer idConcessioneUso = rs.getInt("fk_codice_conc_uso");
		Date giornata = rs.getDate("dataregistrazione");
		//calcolati
		BigDecimal coefficienteMercato = this.getCoefficienteMercatoByContoGiornataEPosteggio(idConto, idMercato, giornata,
			idCategoriaMercato, idSettorePosteggio, codiceIstat, idConcessioneUso);
		boolean isBattitore = recuperaInformazioniGiornataService.isBattitore(catMerc);
		List<ValoriLivelloServizio> serviziConfigurati = this.getLivelliDiServizioConfiguratiPerGiornataAndPosteggioAndUso(giornata,
			idPosteggio, idUso);
		String formulaSostituita = new SegnapostoFormuleBuilder(formula, idConto, coefficienteMercato, serviziConfigurati, serviziDisponibili,
			isBattitore, provenienza, assenzaGiustificata, concessionarioPresente, spuntistaPresente).build();
		//ImportoIvato importoFinale = new AliquotaIVA(iva).applica(formulaEvalService.importoFormula(formulaSostituita));
		Expression expression = new ExpressionBuilder(formulaSostituita).build();
		Double importoSenzaIva = expression.evaluate();
		expression = new ExpressionBuilder("(" + formulaSostituita + ") * (" + iva + " / 100 + 1)").build();
		Double importoFinale = expression.evaluate();
		//update
		position = 1;
		pstmtUpdate.setBigDecimal(position++, BigDecimal.valueOf(importoSenzaIva));
		pstmtUpdate.setBigDecimal(position++, BigDecimal.valueOf(importoFinale));
		pstmtUpdate.setString(position++, ORMHelper.getIdcomune());
		pstmtUpdate.setString(position++, this.guidOperazione);
		pstmtUpdate.setInt(position++, idGiornata);
		pstmtUpdate.setInt(position++, idUso);
		pstmtUpdate.setInt(position++, idPosteggio);
		pstmtUpdate.setInt(position++, idConto);
		pstmtUpdate.addBatch();
		if (++count % batchSize == 0) {
		    pstmtUpdate.executeBatch();
		}
	    }
	    pstmtUpdate.executeBatch();
	} catch (Exception e) {
	    throw new RuntimeException(e);
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pstmtSelect != null) {
		pstmtSelect.close();
	    }
	    if (pstmtUpdate != null) {
		pstmtUpdate.close();
	    }
	}
    }

    private List<ValoriLivelloServizio> getLivelliDiServizioConfiguratiPerGiornataAndPosteggioAndUso(Date giornata, Integer idPosteggio,
	    Integer idUso) {

	ChiaveLivelloServizio chiave = new ChiaveLivelloServizio();
	chiave.setIdPosteggio(idPosteggio);
	chiave.setIdUso(idUso);
	List<ValoreLivelloServizio> livelliConfigurati = mappaLivelliServizio.get(chiave);
	if (livelliConfigurati == null) {
	    return new ArrayList<ValoriLivelloServizio>();
	}
	List<ValoriLivelloServizio> retVal = new ArrayList<ValoriLivelloServizio>();
	for (ValoreLivelloServizio livello : livelliConfigurati) {
	    if (this.giornataNellIntervalloConsentito(livello.getInizioValiditaLivelloMercato(), livello.getFineValiditaLivelloMercato(), giornata)) {
		if (this.giornataNellIntervalloConsentito(livello.getInizioValiditaLivelloPosteggio(), livello.getFineValiditaLivelloPosteggio(),
			giornata)) {
		    ValoriLivelloServizio valore = new ValoriLivelloServizio();
		    valore.setQuantita(livello.getQuantita());
		    valore.setSegnaposto(livello.getSegnaposto());
		    valore.setTariffa(livello.getTariffa());
		    retVal.add(valore);
		}
	    }
	}
	return retVal;
    }

    private BigDecimal getCoefficienteMercatoByContoGiornataEPosteggio(Integer idConto, Integer idMercato, Date giornata, Integer idCategoriaMercato,
	    Integer idSettorePosteggio, String codiceIstat, Integer idConcessioneUso) {

	ChiaveCoefficienteMercato chiave = new ChiaveCoefficienteMercato();
	chiave.setIdConto(idConto);
	List<ValoreCoefficienteMercato> coefficienti = mappaCoefficienti.get(chiave);
	if (coefficienti == null) {
	    return null;
	}
	for (ValoreCoefficienteMercato coefficiente : coefficienti) {
	    if (this.giornataNellIntervalloConsentito(coefficiente.getInizioValidita(), coefficiente.getFineValidita(), giornata)) {
		if (this.mercatiCategoriaConsentita(idCategoriaMercato, coefficiente.getIdCategoriaMercato())) {
		    if (this.concessioniUsoConsentito(idConcessioneUso, coefficiente.getIdConcessioneUso())) {
			if (this.attivitaIstatConsentita(codiceIstat, coefficiente.getCodiceIstat())) {
			    if (this.settorePosteggioConsentito(idSettorePosteggio, coefficiente.getIdSettorePosteggio())) {
				return coefficiente.getImporto();
			    }
			}
		    }
		}
	    }
	}
	return null;
    }

    private boolean giornataNellIntervalloConsentito(Date dataInizioValidita, Date dataFineValidita, Date giornata) {

	if (giornata == null) {
	    return false;
	}
	boolean afterOrEqual = (dataInizioValidita == null) || !giornata.before(dataInizioValidita);
	boolean beforeOrEqual = (dataFineValidita == null) || !giornata.after(dataFineValidita);
	return afterOrEqual && beforeOrEqual;
    }

    private boolean mercatiCategoriaConsentita(Integer idCategoriaDellagiornataMercato, Integer idCategoriaConfigurata) {

	if (idCategoriaConfigurata == null) {
	    return true;
	}
	return idCategoriaConfigurata.equals(idCategoriaDellagiornataMercato);
    }

    private boolean concessioniUsoConsentito(Integer idUsoConcessione, Integer idUsoConfigurato) {

	if (idUsoConfigurato == null) {
	    return true;
	}
	return idUsoConfigurato.equals(idUsoConcessione);
    }

    private boolean attivitaIstatConsentita(String attivitaPresenza, String attivitaIstatConfigurata) {

	if (StringUtils.isBlank(attivitaIstatConfigurata)) {
	    return true;
	}
	return attivitaIstatConfigurata.equalsIgnoreCase(attivitaPresenza);
    }

    private boolean settorePosteggioConsentito(Integer idSettorePosteggioConcessione, Integer idSettorePosteggioConfigurato) {

	if (idSettorePosteggioConfigurato == null) {
	    return true;
	}
	return idSettorePosteggioConfigurato.equals(idSettorePosteggioConcessione);
    }

    private java.sql.Date getData(Date data) {

	Calendar c = Calendar.getInstance();
	c.setTime(data);
	return new java.sql.Date(c.getTimeInMillis());
    }
}
