package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.BollettazioneIstanzeFiltriRicerca;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class QueryBollettazioneIstanzeoneriHelper extends BaseQueryHelper {

    public static enum TIPO_QUERY {
	COUNT,
	INSERIMENTO,
	SELECT
    }

    private static final Logger log = LoggerFactory.getLogger(QueryBollettazioneIstanzeoneriHelper.class);
    private Integer idTestata;
    private BollettazioneIstanzeFiltriRicerca filtriRicerca;
    private boolean conguaglio;
    private boolean isAzienda;
    private TIPO_QUERY tipoQuery;
    private String guid;
    private int valoreBassoSequenzaBollGestDettaglio = 0;
    private int valoreBassoSequenzaBollGestIstanzeOneri = 0;

    public static QueryBollettazioneIstanzeoneriHelper forCount(SessionFactoryImplementor sessimpl, BollettazioneIstanzeFiltriRicerca filtriRicerca,
	    boolean conguaglio, boolean isAzienda) {

	return new QueryBollettazioneIstanzeoneriHelper(sessimpl, filtriRicerca, conguaglio, isAzienda,
		QueryBollettazioneIstanzeoneriHelper.TIPO_QUERY.COUNT, null, null, 0, 0);
    }

    public static QueryBollettazioneIstanzeoneriHelper forInsert(SessionFactoryImplementor sessimpl, BollettazioneIstanzeFiltriRicerca filtriRicerca,
	    boolean conguaglio, boolean isAzienda, String guid, Integer idTestata, int valoreBassoSequenzaBollGestDettaglio,
	    int valoreBassoSequenzaBollGestIstanzeOneri) {

	return new QueryBollettazioneIstanzeoneriHelper(sessimpl, filtriRicerca, conguaglio, isAzienda,
		QueryBollettazioneIstanzeoneriHelper.TIPO_QUERY.INSERIMENTO, guid, idTestata, valoreBassoSequenzaBollGestDettaglio,
		valoreBassoSequenzaBollGestIstanzeOneri);
    }

    private QueryBollettazioneIstanzeoneriHelper(SessionFactoryImplementor sessimpl, BollettazioneIstanzeFiltriRicerca filtriRicerca,
	    boolean conguaglio, boolean isAzienda, TIPO_QUERY tipoQuery, String guid, Integer idTestata, int valoreBassoSequenzaBollGestDettaglio,
	    int valoreBassoSequenzaBollGestIstanzeOneri) {

	this.idTestata = idTestata;
	this.filtriRicerca = filtriRicerca;
	this.conguaglio = conguaglio;
	this.isAzienda = isAzienda;
	this.tipoQuery = tipoQuery;
	this._dialetto = DialettoEnum.fromHibernateDialect(sessimpl.getDialect().toString());
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	this.guid = guid;
	this.valoreBassoSequenzaBollGestDettaglio = valoreBassoSequenzaBollGestDettaglio;
	this.valoreBassoSequenzaBollGestIstanzeOneri = valoreBassoSequenzaBollGestIstanzeOneri;
    }

    @Override
    public String buildQuery() {

	String sql = "";
	switch (tipoQuery) {
	    case COUNT:
		sql = prepareCountSQL();
		break;
	    case SELECT:
		sql = prepareSelectSQL();
		break;
	    case INSERIMENTO:
		sql = prepareInsertSQL();
		break;
	}
	sql = sql.replaceAll(SCHEMA_NAME, (StringUtils.isBlank(schemaName) ? "" : schemaName + "."));
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }

    private String prepareInsertSQL() {

	String sql = "insert into boll_gest_oneri_sequence (idcomune, fk_codiceistanzeoneri, id_bollegest_dett, id_bollegest_oneri, guid, fk_bollgest_id) ";
	int position = 0;
	switch (this._dialetto) {
	    case MYSQL:
		//(select idcomune, id, (@row_number :=  @row_number + 1) , ? from istanzeoneri,(SELECT @row_number := ? ) AS contatore where idcomune=? limit 10)
		sql += "(select istanzeoneri.idcomune, istanzeoneri.id, (@row_dett :=  @row_dett + 1) ,(@row_oneri :=  @row_oneri + 1) , ?, ? from (SELECT @row_dett := ? ) AS cont_dett, (SELECT @row_oneri := ? ) AS cont_oneri, ";
		parameters.add(new ParameterHelper(position, this.guid, new StringType()));
		position++;
		parameters.add(new ParameterHelper(position, this.idTestata, new IntegerType()));
		position++;
		parameters.add(new ParameterHelper(position, this.valoreBassoSequenzaBollGestDettaglio, new IntegerType()));
		position++;
		parameters.add(new ParameterHelper(position, this.valoreBassoSequenzaBollGestIstanzeOneri, new IntegerType()));
		position++;
		break;
	    case ORACLE:
		sql += "(select istanzeoneri.idcomune, istanzeoneri.id,  ROWNUM + ? AS ID_BOLLGESTDETTAGLIO, ROWNUM + ? AS ID_BOLLGESTONERI, ?, ?  from ";
		parameters.add(new ParameterHelper(position, this.valoreBassoSequenzaBollGestDettaglio, new IntegerType()));
		position++;
		parameters.add(new ParameterHelper(position, this.valoreBassoSequenzaBollGestIstanzeOneri, new IntegerType()));
		position++;
		parameters.add(new ParameterHelper(position, this.guid, new StringType()));
		position++;
		parameters.add(new ParameterHelper(position, this.idTestata, new IntegerType()));
		position++;
		break;
	    default:
		log.error("Funzionalità non implementata per il database {}", this._dialetto);
		throw new NotImplementedException("Funzionalità non implementata per il database " + this._dialetto);
	}
	sql = sql + getSqlFrom(position) + ")";
	return sql;
    }

    private String prepareSelectSQL() {

	String sql = "select istanzeoneri.id as idRiferimento, tipicausalioneridettaglio.fkconto as idConto,  " +
		applyConcatFunction("", "'Istanza ' ", "istanze.numeroistanza", "' del '", applyDateToString_DDMMYYYY("istanze.data"), "' - '",
			"tipicausalioneri.co_descrizione", "' ( '", applyDateToString_DDMMYYYY("istanzeoneri.data"), "' )'") +
		" as descrizione,  istanzeoneri.prezzo as importoTotale, ";
	if (this.isAzienda) {
	    sql += applySimpleNVLFunction("istanze.codicetitolarelegale", "istanze.codicerichiedente");
	} else {
	    sql += "istanze.codicerichiedente";
	}
	sql += " as idAnagrafe, conti.iva from " + getSqlFrom(0);
	sql += " order by " +
		applySimpleNVLFunction("istanze.codicetitolarelegale", "istanze.codicerichiedente") +
		", istanze.codiceistanza, tipicausalioneri.co_id, istanzeoneri.id";
	return sql;
    }

    private String prepareCountSQL() {

	return "select count(*) as conta from " + getSqlFrom(0);
    }

    private String getSqlFrom(int position) {

	String sql = " " +
		SCHEMA_NAME +
		"istanzeoneri" + //
		"   inner join " +
		SCHEMA_NAME + //
		"tipicausalioneri on tipicausalioneri.idcomune = istanzeoneri.idcomune and tipicausalioneri.co_id = istanzeoneri.fkidtipocausale" + //
		"   inner join " +
		SCHEMA_NAME + //
		"tipicausalioneridettaglio on tipicausalioneridettaglio.idcomune = tipicausalioneri.idcomune and " + //
		"tipicausalioneridettaglio.fkcausale = tipicausalioneri.co_id" + //
		"   inner join " +
		SCHEMA_NAME + //
		"conti on tipicausalioneridettaglio.idcomune = conti.idcomune and tipicausalioneridettaglio.fkconto = conti.id and " + //
		" tipicausalioneridettaglio.flag_attivo = 1 and ( conti.datascadenza is null or conti.datascadenza >= ?)" + //
		"   inner join " +
		SCHEMA_NAME + // 
		"istanze on istanze.idcomune = istanzeoneri.idcomune and istanze.codiceistanza = istanzeoneri.codiceistanza" + //  
		"   inner join " + //
		SCHEMA_NAME +
		"alberoproc on alberoproc.idcomune = istanze.idcomune and alberoproc.sc_id = istanze.codiceinterventoproc ";
	sql += " where  istanzeoneri.datapagamento is null and " +
		" not exists (select 1 from istoneri_dett_posizioni where istoneri_dett_posizioni.idcomune = istanzeoneri.idcomune and istoneri_dett_posizioni.fk_istanzeoneri_id = istanzeoneri.id )  and " +
		applySimpleNVLFunction("istanzeoneri.prezzo", "0") +
		" > 0 and " +
		" istanze.idcomune = ? and" +
		" istanze.software = ?";
	if (!this.conguaglio) {
	    sql += " and not exists ( select  1 from  boll_gest_istanzeoneri where " + //
		    " boll_gest_istanzeoneri.idcomune = istanzeoneri.idcomune and " + //
		    " boll_gest_istanzeoneri.fk_codiceistanzeoneri = istanzeoneri.id" +
		    ") ";
	}
	//filtro data scadenza conto
	Calendar t = Calendar.getInstance();
	t.setTime(new Date());
	t.set(Calendar.HOUR, 0);
	t.set(Calendar.MINUTE, 0);
	t.set(Calendar.SECOND, 0);
	parameters.add(new ParameterHelper(position, t.getTime(), new TimestampType()));
	position++;
	// filtro idcomune
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	//filtro software
	parameters.add(new ParameterHelper(position, ORMHelper.getSoftware(), new StringType()));
	position++;
	if (filtriRicerca.getFiltriCodiceComune() != null && filtriRicerca.getFiltriCodiceComune().size() > 0) {
	    String qm = StringUtils.repeat("?,", filtriRicerca.getFiltriCodiceComune().size());
	    qm = qm.substring(0, qm.length() - 1);
	    sql += " and istanze.codicecomune in (" + qm + ") ";
	    for (String codicecomune : filtriRicerca.getFiltriCodiceComune()) {
		parameters.add(new ParameterHelper(position, codicecomune, new StringType()));
		position++;
	    }
	}
	if (filtriRicerca.getFiltriScCodice() != null && filtriRicerca.getFiltriScCodice().size() > 0) {
	    sql += " and (";
	    for (String scCodice : filtriRicerca.getFiltriScCodice()) {
		sql += " alberoproc.sc_codice like ? or ";
		parameters.add(new ParameterHelper(position, scCodice + "%", new StringType()));
		position++;
	    }
	    sql = sql.substring(0, sql.length() - 3);
	    sql += ")";
	}
	if (filtriRicerca.getFiltriCausaleOnere() != null && filtriRicerca.getFiltriCausaleOnere().size() > 0) {
	    String qm = StringUtils.repeat("?,", filtriRicerca.getFiltriCausaleOnere().size());
	    qm = qm.substring(0, qm.length() - 1);
	    sql += " and istanzeoneri.fkidtipocausale in (" + qm + ") ";
	    for (Integer causale : filtriRicerca.getFiltriCausaleOnere()) {
		parameters.add(new ParameterHelper(position, causale, new IntegerType()));
		position++;
	    }
	}
	sql += " and istanzeoneri.data >= ?" + " and istanzeoneri.data <= ?";
	parameters.add(new ParameterHelper(position, filtriRicerca.getIntervalloDate().getDataInizio(), new TimestampType()));
	position++;
	parameters.add(new ParameterHelper(position, filtriRicerca.getIntervalloDate().getDataFine(), new TimestampType()));
	position++;
	if (filtriRicerca.getFiltriCodiceEndo() != null && filtriRicerca.getFiltriCodiceEndo().size() > 0) {
	    String qm = StringUtils.repeat("?,", filtriRicerca.getFiltriCodiceEndo().size());
	    qm = qm.substring(0, qm.length() - 1);
	    sql += " and exists ( select 1 from " +
		    SCHEMA_NAME +
		    "istanzeprocedimenti where istanze.idcomune = istanzeprocedimenti.idcomune and istanze.codiceistanza = istanzeprocedimenti.codiceistanza and istanzeprocedimenti.codiceinventario in (" +
		    qm +
		    "))";
	    for (Integer endo : filtriRicerca.getFiltriCodiceEndo()) {
		parameters.add(new ParameterHelper(position, endo, new IntegerType()));
		position++;
	    }
	}
	return sql;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	for (ParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	switch (tipoQuery) {
	    case COUNT:
		q.addScalar("conta", Hibernate.BIG_DECIMAL);
		break;
	    case INSERIMENTO:
		q.addScalar("idRiferimento", Hibernate.INTEGER);
		q.addScalar("idConto", Hibernate.INTEGER);
		q.addScalar("descrizione", Hibernate.STRING);
		q.addScalar("importoTotale", Hibernate.BIG_DECIMAL);
		q.addScalar("idAnagrafe", Hibernate.INTEGER);
		q.addScalar("iva", Hibernate.INTEGER);
	}
    }

    public List<ParameterHelper> getParameters() {

	return this.parameters;
    }
}
