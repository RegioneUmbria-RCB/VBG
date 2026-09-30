package it.gruppoinit.pal.gp.core.features.movimenti.ziplogico;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class QueryTestateMancantiHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryTestateMancantiHelper.class);

    @Override
    public void setFilterValues(SQLQuery q) {

	throw new NotImplementedException();
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idComune", Hibernate.STRING);
	q.addScalar("codiceMovimento", Hibernate.INTEGER);
    }

    public QueryTestateMancantiHelper(SessionFactoryImplementor sessimpl) {

	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryTestateMancantiHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryTestateMancantiHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryTestateMancantiHelper: schemaName={}", schemaName);
    }

    @Override
    public String buildQuery() {

	String sql = "select " +
		"movimenti_zip_logico.idcomune as idComune, " +
		"movimenti_zip_logico.codicemovimento as codiceMovimento " +
		"from " +
		"movimenti_zip_logico " +
		"left join movimenti_zip_logico_testata on " +
		"movimenti_zip_logico_testata.idcomune = movimenti_zip_logico_testata.idcomune and " +
		"movimenti_zip_logico_testata.codicemovimento = movimenti_zip_logico_testata.codicemovimento " +
		"where " +
		"movimenti_zip_logico_testata.guid is null " +
		"group by " +
		"movimenti_zip_logico.idcomune, " +
		"movimenti_zip_logico.codicemovimento " +
		"order by " +
		"movimenti_zip_logico.idcomune asc, " +
		"movimenti_zip_logico.codicemovimento asc";
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }
}
