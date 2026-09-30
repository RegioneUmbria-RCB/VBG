package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class QueryMercatiDLivelliServizioHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryMercatiDLivelliServizioHelper.class);
    private Date dataGiornata;
    private Integer idPosteggio;
    private Integer idMercatiUso;

    @Override
    public void setFilterValues(SQLQuery q) {

	for (ParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("segnaposto", Hibernate.STRING);
	q.addScalar("tariffa", Hibernate.BIG_DECIMAL);
	q.addScalar("quantita", Hibernate.BIG_DECIMAL);
    }

    public QueryMercatiDLivelliServizioHelper(SessionFactoryImplementor sessimpl, Date datagiornata, Integer idPosteggio, Integer idMercatiUso) {

	super();
	this.dataGiornata = datagiornata;
	this.idPosteggio = idPosteggio;
	this.idMercatiUso = idMercatiUso;
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryBollettazioneMercatiHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryBollettazioneMercatiHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryBollettazioneMercatiHelper: schemaName={}", schemaName);
    }

    @Override
    public String buildQuery() {

	int position = 0;
	String sql = "SELECT" + //
		"    livello_servizio.segnaposto, " +
		"    mercati_livello_servizio.tariffa, " +
		"    SUM(" +
		"        CASE mercati_d_livello_servizio.usa_mq_posteggio " +
		"            WHEN 1   THEN mercati_d.superficie " +
		"            ELSE mercati_d_livello_servizio.fattore_moltiplicativo " +
		"        END " +
		"    ) AS quantita " +
		" FROM " + //
		SCHEMA_NAME + //
		"mercati_d_livello_servizio " + //
		"    INNER JOIN  " + //
		SCHEMA_NAME + //"
		"MErcati_D on " + //
		"    MErcati_D.IDCOMUNE = mercati_d_livello_servizio.IDCOMUNE and " + //
		"    MErcati_D.IDPOSTEGGIO = mercati_d_livello_servizio.FK_MERCATO_D " + //
		"    INNER JOIN " + //
		SCHEMA_NAME + //"
		"mercati_livello_servizio ON mercati_livello_servizio.idcomune = mercati_d_livello_servizio.idcomune " + //
		"  AND mercati_livello_servizio.id = mercati_d_livello_servizio.fk_merc_servizio " + //
		"    INNER JOIN " + //
		SCHEMA_NAME + //
		"livello_servizio ON livello_servizio.idcomune = mercati_livello_servizio.idcomune " + //
		"                                   AND livello_servizio.id = mercati_livello_servizio.fk_servizio " + //
		" WHERE " + //
		"    mercati_d_livello_servizio.idcomune = ? " + //
		"    AND   mercati_d_livello_servizio.fk_mercato_d = ? " + //
		"    AND   mercati_livello_servizio.attivo = ? " + //
		"    AND   (" + //
		"        mercati_livello_servizio.data_inizio_validita <= ? " + //
		"        AND   mercati_livello_servizio.data_fine_validita >= ? " + //
		"    ) " + //
		"    AND   (" + //
		"        mercati_d_livello_servizio.data_inizio <= ? " + //
		"        AND   mercati_d_livello_servizio.data_fine >= ? " + //
		"    ) " + //  
		"  and   mercati_livello_servizio.fk_mercato_uso=? " + //
		" GROUP BY" +
		"    livello_servizio.segnaposto," +
		"    mercati_livello_servizio.tariffa " +
		"ORDER BY " + //
		"    segnaposto";
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	parameters.add(new ParameterHelper(position, idPosteggio, new IntegerType()));
	position++;
	parameters.add(new ParameterHelper(position, 1, new IntegerType()));
	position++;
	Calendar tFine = Calendar.getInstance();
	tFine.setTime(dataGiornata);
	tFine.set(Calendar.HOUR, 23);
	tFine.set(Calendar.MINUTE, 59);
	tFine.set(Calendar.SECOND, 59);
	Calendar tInizio = Calendar.getInstance();
	tInizio.setTime(dataGiornata);
	tInizio.set(Calendar.HOUR, 0);
	tInizio.set(Calendar.MINUTE, 0);
	tInizio.set(Calendar.SECOND, 0);
	//"        mercati_livello_servizio.data_inizio_validita <= ?" + //
	//"        AND   mercati_livello_servizio.data_fine_validita >= ?" + //
	parameters.add(new ParameterHelper(position, tInizio.getTime(), new DateType()));
	position++;
	parameters.add(new ParameterHelper(position, tFine.getTime(), new DateType()));
	position++;
	//	"    AND   (" + //
	//	"        mercati_d_livello_servizio.data_inizio <= ?" + //
	//	"        AND   mercati_d_livello_servizio.data_fine >= ?" + //
	parameters.add(new ParameterHelper(position, tInizio.getTime(), new DateType()));
	position++;
	parameters.add(new ParameterHelper(position, tFine.getTime(), new DateType()));
	position++;
	parameters.add(new ParameterHelper(position, idMercatiUso, new IntegerType()));
	position++;
	if (StringUtils.isNotBlank(schemaName)) {
	    sql = sql.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }
}
