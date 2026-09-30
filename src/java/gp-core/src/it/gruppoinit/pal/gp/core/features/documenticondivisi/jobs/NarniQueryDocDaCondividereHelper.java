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

public class NarniQueryDocDaCondividereHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(NarniQueryDocDaCondividereHelper.class);

    @Override
    public void setFilterValues(SQLQuery q) {

	for (ParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idDocumento", Hibernate.INTEGER);
	q.addScalar("codiceIstanza", Hibernate.INTEGER);
	q.addScalar("codiceMovimento", Hibernate.INTEGER);
    }

    public NarniQueryDocDaCondividereHelper(SessionFactoryImplementor sessimpl) {

	Dialect dialetto = sessimpl.getDialect();
	log.debug("NarniQueryDocDaCondividereHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("NarniQueryDocDaCondividereHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("NarniQueryDocDaCondividereHelper: schemaName={}", schemaName);
    }

    @Override
    public String buildQuery() {

	String sql = "select" +
		" movimentiallegati.codiceoggetto as idDocumento, istanze.codiceIstanza, movimentiallegati.codiceMovimento " +
		"from" +
		" movimentiallegati" +
		"   inner join movimenti on" +
		"     movimentiallegati.idcomune = movimenti.idcomune and" +
		"     movimentiallegati.codicemovimento = movimenti.codicemovimento and" +
		"     movimenti.numeroprotocollo is not null and" +
		"     movimenti.dataprotocollo is not null" +
		"   inner join istanze on" +
		"     movimenti.idcomune = istanze.idcomune and" +
		"     movimenti.codiceistanza = istanze.codiceistanza and" +
		"     istanze.posizionearchivio is not null" +
		"   inner join statiistanza on" +
		"     istanze.idcomune = statiistanza.idcomune and" +
		"     istanze.software = statiistanza.software and" +
		"     istanze.chiusura = statiistanza.codicestato and" +
		"     statiistanza.fkcodcomportamento <> 0" +
		"   inner join oggetti on" +
		"     movimentiallegati.idcomune = oggetti.idcomune and" +
		"     movimentiallegati.codiceoggetto = oggetti.codiceoggetto and" +
		"     upper(oggetti.nomefile) not like '%.RTF'" +
		"   left join documenti_condivisi on" +
		"     movimentiallegati.idcomune = documenti_condivisi.idcomune and" +
		"     movimentiallegati.codiceoggetto = documenti_condivisi.codiceoggetto " +
		"where" +
		" movimentiallegati.idcomune = ? and" +
		" movimentiallegati.codiceoggetto is not null and" +
		" documenti_condivisi.codiceoggetto is null" +
		" union " +
		"select" +
		" documentiistanza.codiceoggetto as idDocumento, istanze.codiceIstanza, null as codiceMovimento " +
		"from" +
		" documentiistanza" +
		"   inner join istanze on" +
		"     documentiistanza.idcomune = istanze.idcomune and" +
		"     documentiistanza.codiceistanza = istanze.codiceistanza and" +
		"     istanze.numeroprotocollo is not null and" +
		"     istanze.dataprotocollo is not null and" +
		"     istanze.posizionearchivio is not null" +
		"   inner join statiistanza on" +
		"     istanze.idcomune = statiistanza.idcomune and" +
		"     istanze.software = statiistanza.software and" +
		"     istanze.chiusura = statiistanza.codicestato and" +
		"     statiistanza.fkcodcomportamento <> 0" +
		"   inner join oggetti on" +
		"     documentiistanza.idcomune = oggetti.idcomune and" +
		"     documentiistanza.codiceoggetto = oggetti.codiceoggetto and" +
		"     upper(oggetti.nomefile) not like '%.RTF'" +
		"   left join documenti_condivisi on" +
		"     documentiistanza.idcomune = documenti_condivisi.idcomune and" +
		"     documentiistanza.codiceoggetto = documenti_condivisi.codiceoggetto " +
		"where" +
		" documentiistanza.idcomune = ? and" +
		" documentiistanza.codiceoggetto is not null and" +
		" documenti_condivisi.codiceoggetto is null" +
		" union " +
		"select" +
		" istanzeallegati.codiceoggetto as idDocumento, istanze.codiceIstanza, null as codiceMovimento " +
		"from" +
		" istanzeallegati" +
		"   inner join istanze on" +
		"     istanzeallegati.idcomune = istanze.idcomune and" +
		"     istanzeallegati.codiceistanza = istanze.codiceistanza and" +
		"     istanze.numeroprotocollo is not null and" +
		"     istanze.dataprotocollo is not null and" +
		"     istanze.posizionearchivio is not null" +
		"   inner join statiistanza on" +
		"     istanze.idcomune = statiistanza.idcomune and" +
		"     istanze.software = statiistanza.software and" +
		"     istanze.chiusura = statiistanza.codicestato and" +
		"     statiistanza.fkcodcomportamento <> 0" +
		"   inner join oggetti on" +
		"     istanzeallegati.idcomune = oggetti.idcomune and" +
		"     istanzeallegati.codiceoggetto = oggetti.codiceoggetto and" +
		"     upper(oggetti.nomefile) not like '%.RTF'" +
		"   left join documenti_condivisi on" +
		"     istanzeallegati.idcomune = documenti_condivisi.idcomune and" +
		"     istanzeallegati.codiceoggetto = documenti_condivisi.codiceoggetto " +
		"where" +
		" istanzeallegati.idcomune = ? and" +
		" istanzeallegati.codiceoggetto is not null and" +
		" documenti_condivisi.codiceoggetto is null" +
		" union " +
		"select" +
		" istanzeprocure.codiceoggettoprocura as idDocumento, istanze.codiceIstanza, null as codiceMovimento " +
		"from" +
		" istanzeprocure" +
		"   inner join istanze on" +
		"     istanzeprocure.idcomune = istanze.idcomune and" +
		"     istanzeprocure.codiceistanza = istanze.codiceistanza and" +
		"     istanze.numeroprotocollo is not null and" +
		"     istanze.dataprotocollo is not null and" +
		"     istanze.posizionearchivio is not null" +
		"   inner join statiistanza on" +
		"     istanze.idcomune = statiistanza.idcomune and" +
		"     istanze.software = statiistanza.software and" +
		"     istanze.chiusura = statiistanza.codicestato and" +
		"     statiistanza.fkcodcomportamento <> 0" +
		"   inner join oggetti on" +
		"     istanzeprocure.idcomune = oggetti.idcomune and" +
		"     istanzeprocure.codiceoggettoprocura = oggetti.codiceoggetto and" +
		"     upper(oggetti.nomefile) not like '%.RTF'" +
		"   left join documenti_condivisi on" +
		"     istanzeprocure.idcomune = documenti_condivisi.idcomune and" +
		"     istanzeprocure.codiceoggettoprocura = documenti_condivisi.codiceoggetto " +
		"where" +
		" istanzeprocure.idcomune = ? and" +
		" istanzeprocure.codiceoggettoprocura is not null and" +
		" documenti_condivisi.codiceoggetto is null";
	int position = 0;
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	if (StringUtils.isNotBlank(schemaName)) {
	    sql = sql.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }
}
