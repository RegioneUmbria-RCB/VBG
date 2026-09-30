package it.gruppoinit.pal.gp.pay.dao.utils;

import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.DateType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams;
import it.gruppoinit.pal.gp.pay.ws.rest.PagamentiAnnullatiRestRequest;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;

public class QueryRicercaPosizioniAnnullateHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryRicercaPosizioniAnnullateHelper.class);
    private PagamentiAnnullatiRestRequest richiesta;

    public QueryRicercaPosizioniAnnullateHelper(SessionFactoryImplementor sessimpl, PagamentiAnnullatiRestRequest richiesta) {

	this.richiesta = richiesta;
	Dialect dialettoUtilizzato = sessimpl.getDialect();
	log.debug("Il dialetto della SessionFactoryImplementor è {}", dialettoUtilizzato);
	String hibernateDialect = dialettoUtilizzato.toString();
	this.dialetto = fromString(hibernateDialect);
	log.debug("Il dialetto è {}", this.dialetto);
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	for (IParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("alias", StringType.INSTANCE);
	q.addScalar("idcomune", StringType.INSTANCE);
	q.addScalar("cfEnteCreditore", StringType.INSTANCE);
	q.addScalar("uuid", StringType.INSTANCE);
	q.addScalar("riferimentoClient", StringType.INSTANCE);
	q.addScalar("cfPiva", StringType.INSTANCE);
	q.addScalar("nominativo", StringType.INSTANCE);
	q.addScalar("stato", StringType.INSTANCE);
	q.addScalar("dataEvInternal", DateType.INSTANCE);
    }

    @Override
    public String buildQuery() {

	int position = 0;
	String sql = "SELECT "; //
	sql += " PAY_CONNECTOR_CONFIG_VALUES.VALORE AS alias"; //
	sql += " ,pay_posizioni_debitorie.idcomune AS idcomune "; //
	sql += " ,pay_profili_enti_creditori.cf_codice_profilo AS cfEnteCreditore"; //
	sql += " ,pay_posizioni_debitorie.uuid AS uuid"; //
	sql += " ,pay_posizioni_debitorie.RIFERIMENTO_CLIENT AS riferimentoClient"; //
	sql += " ,pay_soggetti_debitori.CF_PI AS cfPiva"; //
	sql += " ,TRIM(" + applyConcatFunction("' '", "pay_soggetti_debitori.nome", "pay_soggetti_debitori.cognome") + ") AS nominativo,"; //
	sql += "  pay_stato_pagamenti.stato as stato,";//
	sql += "  pay_stato_pagamenti.data_evento as dataEvInternal";//
	sql += "  FROM pay_posizioni_debitorie "; //
	sql += " INNER JOIN pay_profili_enti_creditori ON "; //
	sql += "     pay_posizioni_debitorie.idcomune=pay_profili_enti_creditori.idcomune AND"; //
	sql += "     pay_posizioni_debitorie.FK_PROFILO_ENTE=pay_profili_enti_creditori.id "; //
	sql += " INNER JOIN pay_soggetti_debitori ON     "; //
	sql += "     pay_soggetti_debitori.idcomune=pay_posizioni_debitorie.idcomune AND"; //
	sql += "     pay_soggetti_debitori.id=pay_posizioni_debitorie.FK_SOGGETTO_DEBITORE"; //
	sql += " INNER JOIN PAY_CONNECTOR_CONFIG_VALUES ON "; //
	sql += "     PAY_CONNECTOR_CONFIG_VALUES.IDCOMUNE=pay_profili_enti_creditori.IDCOMUNE AND"; //
	sql += "     PAY_CONNECTOR_CONFIG_VALUES.CODICE_CONNETTORE = pay_profili_enti_creditori.CODICE_CONNETTORE AND"; //
	sql += "     PAY_CONNECTOR_CONFIG_VALUES.CONFIG_PARAM=?"; //
	this.parameters.add(new SimpleParameterHelper(position++, PayConnectorConfigParams.ConfigParamNames.SECURITY_ALIAS.name(), new StringType()));
	sql += " INNER JOIN PAY_STATO_PAGAMENTI  ON "; //
	sql += "         PAY_STATO_PAGAMENTI.IDCOMUNE=pay_posizioni_debitorie.IDCOMUNE AND"; //
	sql += "         PAY_STATO_PAGAMENTI.FK_POSIZIONE_DEBITORIA=pay_posizioni_debitorie.ID "; //
	sql += "   INNER JOIN (SELECT "; //
	sql += "              pay_stato_pagamenti.idcomune,"; //
	sql += "              pay_stato_pagamenti.fk_posizione_debitoria,"; //
	sql += "              MAX(pay_stato_pagamenti.id) AS id"; //
	sql += "             FROM"; //
	sql += "              pay_stato_pagamenti"; //
	sql += "                INNER JOIN "; //
	sql += "                ("; //
	sql += "                  SELECT"; //
	sql += "                    pay_stato_pagamenti.idcomune,"; //
	sql += "                    pay_stato_pagamenti.fk_posizione_debitoria,"; //
	sql += "                    MAX(data_evento) AS data_evento"; //
	sql += "                  FROM"; //
	sql += "                    pay_stato_pagamenti"; //
	sql += "                  GROUP BY"; //
	sql += "                    pay_stato_pagamenti.idcomune,"; //
	sql += "                    pay_stato_pagamenti.fk_posizione_debitoria"; //
	sql += "                ) pay_stato_pagamenti_max_data ON"; //
	sql += "                  pay_stato_pagamenti.idcomune = pay_stato_pagamenti_max_data.idcomune AND "; //
	sql += "                  pay_stato_pagamenti.fk_posizione_debitoria = pay_stato_pagamenti_max_data.fk_posizione_debitoria AND "; //
	sql += "                  pay_stato_pagamenti.data_evento = pay_stato_pagamenti_max_data.data_evento "; //
	sql += "             GROUP BY"; //
	sql += "              pay_stato_pagamenti.idcomune,"; //
	sql += "              pay_stato_pagamenti.fk_posizione_debitoria"; //
	sql += "           ) pay_stato_pagamenti_max ON                "; //
	sql += "             pay_stato_pagamenti_max.idcomune=pay_stato_pagamenti.idcomune AND "; //
	sql += "             pay_stato_pagamenti_max.id = pay_stato_pagamenti.id"; //
	sql += " WHERE "; //
	sql += " NOT EXISTS (SELECT 1 FROM PAY_MESSAGGI_RABBIT_PAGAMENTI inner join pay_messaggi_rabbit on PAY_MESSAGGI_RABBIT_PAGAMENTI.idcomune=pay_messaggi_rabbit.idcomune and PAY_MESSAGGI_RABBIT_PAGAMENTI.FK_MSGRABBIT_GUID=pay_messaggi_rabbit.guid WHERE PAY_MESSAGGI_RABBIT_PAGAMENTI.IDCOMUNE = pay_posizioni_debitorie.idcomune AND PAY_MESSAGGI_RABBIT_PAGAMENTI.UUID_POSIZIONE_DEB = pay_posizioni_debitorie.uuid and pay_messaggi_rabbit.topic=?)"; //
	this.parameters.add(new SimpleParameterHelper(position++, richiesta.getTopic(), new StringType()));
	sql += " AND pay_stato_pagamenti.stato in (?,?)"; //
	this.parameters.add(new SimpleParameterHelper(position++, StatoPagamentoType.ANNULLAMENTO_RICHIESTO.name(), new StringType()));
	this.parameters.add(new SimpleParameterHelper(position++, StatoPagamentoType.ANNULLATO.name(), new StringType()));
	sql += " AND pay_stato_pagamenti.DATA_evento > ?"; //
	sql += " order by pay_stato_pagamenti.DATA_evento asc"; //
	this.parameters.add(new SimpleParameterHelper(position++, getData(richiesta.getDataInizioRicerca()), new DateType()));
	return sql;
    }
}
