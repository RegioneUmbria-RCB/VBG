package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.IntervalloDate;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class QueryBollettazioneIstanzeHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryBollettazioneIstanzeHelper.class);
    private List<String> filtriCodiceComune;
    private List<String> filtriScCodice;
    private List<Integer> filtriCodiceEndo;
    private List<Integer> filtriCausaleOnere;
    private IntervalloDate intervalloDate;
    private Boolean conguaglio;
    private boolean isAzienda;

    @Override
    public void setFilterValues(SQLQuery q) {

	for (ParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idRiferimento", Hibernate.INTEGER);
	q.addScalar("idConto", Hibernate.INTEGER);
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("importoTotale", Hibernate.BIG_DECIMAL);
	q.addScalar("idAnagrafe", Hibernate.INTEGER);
	q.addScalar("iva", Hibernate.INTEGER);
    }

    public QueryBollettazioneIstanzeHelper(SessionFactoryImplementor sessimpl, List<String> filtriCodiceComune, List<String> filtriScCodice,
	    List<Integer> filtriCodiceEndo, List<Integer> filtriCausaleOnere, IntervalloDate intervalloDate, Boolean conguaglio, boolean isAzienda) {

	this.filtriCodiceComune = filtriCodiceComune;
	this.filtriScCodice = filtriScCodice;
	this.filtriCodiceEndo = filtriCodiceEndo;
	this.filtriCausaleOnere = filtriCausaleOnere;
	this.intervalloDate = intervalloDate;
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryBollettazioneIstanzeHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryBollettazioneIstanzeHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryBollettazioneIstanzeHelper: schemaName={}", schemaName);
	this.conguaglio = conguaglio;
	this.isAzienda = isAzienda;
    }

    @Override
    public String buildQuery() {

	int position = 0;
	String sql = "select " +
		" istanzeoneri.id as idRiferimento, tipicausalioneridettaglio.fkconto as idConto,  " +
		applyConcatFunction("", "'Istanza ' ", "istanze.numeroistanza", "' del '", applyDateToString_DDMMYYYY("istanze.data"), "' - '",
			"tipicausalioneri.co_descrizione", "' ( '", applyDateToString_DDMMYYYY("istanzeoneri.data"), "' )'") +
		" as descrizione, " +
		" istanzeoneri.prezzo as importoTotale, ";
	if (this.isAzienda) {
	    sql += applySimpleNVLFunction("istanze.codicetitolarelegale", "istanze.codicerichiedente");
	} else {
	    sql += "istanze.codicerichiedente";
	}
	sql += " as idAnagrafe, conti.iva from " +
		SCHEMA_NAME +
		" istanzeoneri" +
		"   inner join " +
		SCHEMA_NAME +
		"tipicausalioneri on tipicausalioneri.idcomune = istanzeoneri.idcomune and tipicausalioneri.co_id = istanzeoneri.fkidtipocausale" +
		"   inner join " +
		SCHEMA_NAME +
		"tipicausalioneridettaglio on tipicausalioneridettaglio.idcomune = tipicausalioneri.idcomune and tipicausalioneridettaglio.fkcausale = tipicausalioneri.co_id" +
		"   inner join " +
		SCHEMA_NAME +
		"conti on tipicausalioneridettaglio.idcomune = conti.idcomune and tipicausalioneridettaglio.fkconto = conti.id and tipicausalioneridettaglio.flag_attivo = 1 and ( conti.datascadenza is null or conti.datascadenza >= ?)" +
		"   inner join " +
		SCHEMA_NAME +
		"istanze on istanze.idcomune = istanzeoneri.idcomune and istanze.codiceistanza = istanzeoneri.codiceistanza" +
		"   inner join " +
		SCHEMA_NAME +
		"alberoproc on alberoproc.idcomune = istanze.idcomune and alberoproc.sc_id = istanze.codiceinterventoproc " +
		"where" +
		" istanzeoneri.datapagamento is null and " +
		" not exists (select 1 from istoneri_dett_posizioni where istoneri_dett_posizioni.idcomune = istanzeoneri.idcomune and istoneri_dett_posizioni.fk_istanzeoneri_id = istanzeoneri.id )  and " +
		applySimpleNVLFunction("istanzeoneri.prezzo", "0") +
		" > 0 and " +
		" istanze.idcomune = ? and" +
		" istanze.software = ?";
	if (!this.conguaglio) {
	    sql += " and not exists " +
		    "( " +
		    "select " +
		    " 1 " +
		    "from " +
		    " boll_gest_istanzeoneri " +
		    "where " +
		    " boll_gest_istanzeoneri.idcomune = istanzeoneri.idcomune and " +
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
	if (filtriCodiceComune != null && filtriCodiceComune.size() > 0) {
	    String qm = StringUtils.repeat("?,", filtriCodiceComune.size());
	    qm = qm.substring(0, qm.length() - 1);
	    sql += " and istanze.codicecomune in (" + qm + ") ";
	    for (String codicecomune : filtriCodiceComune) {
		parameters.add(new ParameterHelper(position, codicecomune, new StringType()));
		position++;
	    }
	}
	if (filtriScCodice != null && filtriScCodice.size() > 0) {
	    sql += " and (";
	    for (String scCodice : filtriScCodice) {
		sql += " alberoproc.sc_codice like ? or ";
		parameters.add(new ParameterHelper(position, scCodice + "%", new StringType()));
		position++;
	    }
	    sql = sql.substring(0, sql.length() - 3);
	    sql += ")";
	}
	if (filtriCausaleOnere != null && filtriCausaleOnere.size() > 0) {
	    String qm = StringUtils.repeat("?,", filtriCausaleOnere.size());
	    qm = qm.substring(0, qm.length() - 1);
	    sql += " and istanzeoneri.fkidtipocausale in (" + qm + ") ";
	    for (Integer causale : filtriCausaleOnere) {
		parameters.add(new ParameterHelper(position, causale, new IntegerType()));
		position++;
	    }
	}
	sql += " and istanzeoneri.data >= ?" + " and istanzeoneri.data <= ?";
	parameters.add(new ParameterHelper(position, intervalloDate.getDataInizio(), new TimestampType()));
	position++;
	parameters.add(new ParameterHelper(position, intervalloDate.getDataFine(), new TimestampType()));
	position++;
	if (filtriCodiceEndo != null && filtriCodiceEndo.size() > 0) {
	    String qm = StringUtils.repeat("?,", filtriCodiceEndo.size());
	    qm = qm.substring(0, qm.length() - 1);
	    sql += " and exists ( select 1 from " +
		    SCHEMA_NAME +
		    "istanzeprocedimenti where istanze.idcomune = istanzeprocedimenti.idcomune and istanze.codiceistanza = istanzeprocedimenti.codiceistanza and istanzeprocedimenti.codiceinventario in (" +
		    qm +
		    "))";
	    for (Integer endo : filtriCodiceEndo) {
		parameters.add(new ParameterHelper(position, endo, new IntegerType()));
		position++;
	    }
	}
	sql += " order by " +
		applySimpleNVLFunction("istanze.codicetitolarelegale", "istanze.codicerichiedente") +
		", istanze.codiceistanza, tipicausalioneri.co_id, istanzeoneri.id";
	if (StringUtils.isNotBlank(schemaName)) {
	    sql = sql.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }
}
