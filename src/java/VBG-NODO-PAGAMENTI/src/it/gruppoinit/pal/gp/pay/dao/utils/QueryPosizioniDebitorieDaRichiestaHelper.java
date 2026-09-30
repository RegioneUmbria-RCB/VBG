package it.gruppoinit.pal.gp.pay.dao.utils;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaRequestType;

public class QueryPosizioniDebitorieDaRichiestaHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryPosizioniDebitorieFiltrateHelper.class);
    private PosizioneDebitoriaRequestType filtri;
    private boolean isCount = false;

    @Override
    public void setFilterValues(SQLQuery q) {

	for (IParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	if (isCount) {
	    q.addScalar("conteggio", IntegerType.INSTANCE);
	} else {
	    q.addScalar("idPosizioneDebitoria", IntegerType.INSTANCE);
	}
	//	q.addScalar("iuv", StringType.INSTANCE);
	//	q.addScalar("qrcode", StringType.INSTANCE);
	//	q.addScalar("idDettaglioImporti", IntegerType.INSTANCE);
	//	q.addScalar("importo", BigDecimalType.INSTANCE);
	//	q.addScalar("descrizioneCausale", StringType.INSTANCE);
	//	q.addScalar("datiRiscossione", StringType.INSTANCE);
	//	q.addScalar("annoAccertamento", IntegerType.INSTANCE);
	//	q.addScalar("numeroAccertamento", StringType.INSTANCE);
	//	q.addScalar("idStatoPagamenti", IntegerType.INSTANCE);
	//	q.addScalar("stato", StringType.INSTANCE);
	//	q.addScalar("descrizioneStato", StringType.INSTANCE);
	//	q.addScalar("dataEvento", DateType.INSTANCE);
    }

    public QueryPosizioniDebitorieDaRichiestaHelper(SessionFactoryImplementor sessimpl, PosizioneDebitoriaRequestType filtri, boolean isCount) {

	this.isCount = isCount;
	this.filtri = filtri;
	Dialect dialettoUtilizzato = sessimpl.getDialect();
	log.debug("QueryPosizioniDebitorieFiltrate: Il dialetto della SessionFactoryImplementor è {}", dialettoUtilizzato);
	String hibernateDialect = dialettoUtilizzato.toString();
	this.dialetto = fromString(hibernateDialect);
	log.debug("QueryPosizioniDebitorieFiltrate: Il dialetto è {}", this.dialetto);
    }

    @Override
    public String buildQuery() {

	int position = 0;
	this.parameters.add(new SimpleParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	String sql = "select ";
	if (isCount) {
	    sql += " count(*) as conteggio ";
	} else {
	    sql += " pay_posizioni_debitorie.id as idPosizioneDebitoria ";
	}
	sql += "from " + "  pay_posizioni_debitorie " + "    inner join pay_soggetti_debitori on "
		+ "      pay_posizioni_debitorie.idcomune = pay_soggetti_debitori.idcomune and "
		+ "      pay_posizioni_debitorie.fk_soggetto_debitore = pay_soggetti_debitori.id " + "where "
		+ "  pay_posizioni_debitorie.idcomune = ? ";
	if (!StringUtils.isEmpty(this.filtri.getCodiceAvviso())) {
	    sql += "and pay_posizioni_debitorie.codice_avviso = ? ";
	    this.parameters.add(new SimpleParameterHelper(position, this.filtri.getCodiceAvviso(), new StringType()));
	    position++;
	}
	if (!StringUtils.isEmpty(this.filtri.getDescrizioneCausale())) {
	    sql += "and lower(pay_posizioni_debitorie.descrizione_causale) like ? ";
	    this.parameters.add(new LikeParameterHelper(position, this.filtri.getDescrizioneCausale().toLowerCase()));
	    position++;
	}
	if (!StringUtils.isEmpty(this.filtri.getSoggettoDebitore())) {
	    sql += "and lower(concat_ws('',pay_soggetti_debitori.cognome,' ',pay_soggetti_debitori.nome)) like ? ";
	    this.parameters.add(new LikeParameterHelper(position, this.filtri.getSoggettoDebitore().toLowerCase()));
	}
	if (Boolean.TRUE.equals(this.filtri.getPagamentoCompletato())) {
	    sql += "and not exists " + "( " + " select 1 " + " from" + "   pay_pagamenti" + " where"
		    + "   pay_pagamenti.idcomune = pay_posizioni_debitorie.idcomune and "
		    + "   pay_pagamenti.fk_posizione_saldata = pay_posizioni_debitorie.id " + ") ";
	}
	if (!isCount) {
	    sql += " group by pay_posizioni_debitorie.id";
	}
	return sql;
    }
}
