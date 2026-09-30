package it.gruppoinit.pal.gp.core.features.nodopagamenti.jobs.csipiemonte;

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

public class QueryPosizioniDebitorieDaAllineareHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryPosizioniDebitorieDaAllineareHelper.class);

    public QueryPosizioniDebitorieDaAllineareHelper(SessionFactoryImplementor sessimpl) {

	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryPosizioniDebitorieDaAllineareHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryPosizioniDebitorieDaAllineareHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryPosizioniDebitorieDaAllineareHelper: schemaName={}", schemaName);
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

	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("stato", Hibernate.STRING);
	q.addScalar("descStato", Hibernate.STRING);
	q.addScalar("dataEvento", Hibernate.DATE);
	q.addScalar("iuv", Hibernate.STRING);
	q.addScalar("codiceAvviso", Hibernate.STRING);
	q.addScalar("qrCode", Hibernate.STRING);
    }

    @Override
    public String buildQuery() {

	String sql = "" +
		"select" +
		" dett_posizione_debitoria.id," +
		" pay_stato_pagamenti.stato," +
		" pay_stato_pagamenti.desc_stato as descStato," +
		" pay_stato_pagamenti.data_evento as dataEvento," +
		" pay_posizioni_debitorie.iuv," +
		" pay_posizioni_debitorie.codice_avviso as codiceAvviso," +
		" pay_posizioni_debitorie.qrcode as qrCode " +
		"from" +
		"  pay_stato_pagamenti" +
		"   inner join " +
		"   (" +
		"     select " +
		"      pay_stato_pagamenti.idcomune," +
		"      pay_stato_pagamenti.fk_posizione_debitoria," +
		"      max(pay_stato_pagamenti.id) as id" +
		"     from" +
		"      pay_stato_pagamenti" +
		"        inner join " +
		"        (" +
		"          select" +
		"            pay_stato_pagamenti.idcomune," +
		"            pay_stato_pagamenti.fk_posizione_debitoria," +
		"            max(data_evento) as data_evento" +
		"          from" +
		"            pay_stato_pagamenti" +
		"          where " +
		"            pay_stato_pagamenti.idcomune = ? " +
		"          group by" +
		"            pay_stato_pagamenti.idcomune," +
		"            pay_stato_pagamenti.fk_posizione_debitoria" +
		"        ) pay_stato_pagamenti_max_data on" +
		"          pay_stato_pagamenti.idcomune = pay_stato_pagamenti_max_data.idcomune and " +
		"          pay_stato_pagamenti.fk_posizione_debitoria = pay_stato_pagamenti_max_data.fk_posizione_debitoria and " +
		"          pay_stato_pagamenti.data_evento = pay_stato_pagamenti_max_data.data_evento " +
		"     group by" +
		"      pay_stato_pagamenti.idcomune," +
		"      pay_stato_pagamenti.fk_posizione_debitoria" +
		"   ) pay_stato_pagamenti_max on" +
		"     pay_stato_pagamenti.idcomune = pay_stato_pagamenti_max.idcomune and " +
		"     pay_stato_pagamenti.id = pay_stato_pagamenti_max.id" +
		"   inner join dett_posizione_debitoria on" +
		"    pay_stato_pagamenti.idcomune = dett_posizione_debitoria.idcomune and" +
		"    pay_stato_pagamenti.fk_posizione_debitoria = dett_posizione_debitoria.id_posizione_debitoria" +
		"   inner join pay_posizioni_debitorie on" +
		"    pay_stato_pagamenti.idcomune = pay_posizioni_debitorie.idcomune and" +
		"    pay_stato_pagamenti.fk_posizione_debitoria = pay_posizioni_debitorie.id " +
		"where" +
		" pay_stato_pagamenti.stato <> dett_posizione_debitoria.stato";
	int position = 0;
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	if (StringUtils.isNotBlank(schemaName)) {
	    sql = sql.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }
}
