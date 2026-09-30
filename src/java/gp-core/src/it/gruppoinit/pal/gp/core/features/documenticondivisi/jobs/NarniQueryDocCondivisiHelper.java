package it.gruppoinit.pal.gp.core.features.documenticondivisi.jobs;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.features.documenticondivisi.StatiDocumentiCondivisiEnum;

public class NarniQueryDocCondivisiHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(NarniQueryDocCondivisiHelper.class);
    private static final String STATO = StatiDocumentiCondivisiEnum.CONDIVISO.getValore();

    @Override
    public void setFilterValues(SQLQuery q) {

	for (ParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("codiceMovimento", Hibernate.INTEGER);
	q.addScalar("codiceOggetto", Hibernate.INTEGER);
	q.addScalar("codiceIstanza", Hibernate.INTEGER);
	q.addScalar("stato", Hibernate.STRING);
	q.addScalar("nomeFile", Hibernate.STRING);
    }

    public NarniQueryDocCondivisiHelper(SessionFactoryImplementor sessimpl) {

	Dialect dialetto = sessimpl.getDialect();
	log.debug("NarniQueryDocCondivisiHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("NarniQueryDocCondivisiHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("NarniQueryDocCondivisiHelper: schemaName={}", schemaName);
    }

    @Override
    public String buildQuery() {

	String sql = "select " +
		" documenti_condivisi.id, " +
		" documenti_condivisi.codiceMovimento, " +
		" documenti_condivisi.codiceOggetto, " +
		" documenti_condivisi.codiceIstanza, " +
		" documenti_condivisi.stato, " +
		" oggetti.nomeFile  " +
		"from " +
		" documenti_condivisi " +
		"   inner join oggetti on  " +
		"    documenti_condivisi.idcomune = oggetti.idcomune and  " +
		"    documenti_condivisi.codiceoggetto = oggetti.codiceoggetto and " +
		"    lower(oggetti.nomefile) not like '%.rtf' " +
		"   inner join istanze on  " +
		"    documenti_condivisi.idcomune = istanze.idcomune and  " +
		"    documenti_condivisi.codiceistanza = istanze.codiceistanza and " +
		"    istanze.posizionearchivio is not null " +
		"   inner join statiistanza on " +
		"    istanze.idcomune = statiistanza.idcomune and " +
		"    istanze.software = statiistanza.software and " +
		"    istanze.chiusura = statiistanza.codicestato and " +
		"    statiistanza.fkcodcomportamento <> 0 " +
		"where " +
		" documenti_condivisi.idcomune = ? and " +
		" documenti_condivisi.stato <> ? " +
		"order by " +
		" documenti_condivisi.codiceIstanza asc, documenti_condivisi.codiceMovimento asc, documenti_condivisi.codiceOggetto asc";
	int position = 0;
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	parameters.add(new ParameterHelper(position, STATO, new StringType()));
	if (StringUtils.isNotBlank(schemaName)) {
	    sql = sql.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }
}
