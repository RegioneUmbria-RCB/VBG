package it.gruppoinit.pal.gp.pay.dao.utils;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class QueryPosizioniDebitorieFiltrateHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryPosizioniDebitorieFiltrateHelper.class);
    private List<Integer> idPosizioni;

    @Override
    public void setFilterValues(SQLQuery q) {

	for (IParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idPosizioneDebitoria", IntegerType.INSTANCE);
	q.addScalar("descrizione", StringType.INSTANCE);
	q.addScalar("uuid", StringType.INSTANCE);
	q.addScalar("iuv", StringType.INSTANCE);
	q.addScalar("qrcode", StringType.INSTANCE);
	q.addScalar("codiceAvviso", StringType.INSTANCE);
	q.addScalar("dataRegistrazione", DateType.INSTANCE);
	q.addScalar("flagOtf", BooleanType.INSTANCE);
	q.addScalar("idDettaglioImporti", IntegerType.INSTANCE);
	q.addScalar("importo", BigDecimalType.INSTANCE);
	q.addScalar("descrizioneCausale", StringType.INSTANCE);
	q.addScalar("datiRiscossione", StringType.INSTANCE);
	q.addScalar("annoAccertamento", IntegerType.INSTANCE);
	q.addScalar("numeroAccertamento", StringType.INSTANCE);
	q.addScalar("idStatoPagamenti", IntegerType.INSTANCE);
	q.addScalar("stato", StringType.INSTANCE);
	q.addScalar("descrizioneStato", StringType.INSTANCE);
	q.addScalar("dataEvento", DateType.INSTANCE);
    }

    public QueryPosizioniDebitorieFiltrateHelper(SessionFactoryImplementor sessimpl, List<Integer> idPosizioni) {

	this.idPosizioni = idPosizioni;
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
	String sql = "select pay_posizioni_debitorie.id as idPosizioneDebitoria,pay_posizioni_debitorie.descrizione_causale as descrizione,pay_posizioni_debitorie.uuid as uuid, pay_posizioni_debitorie.iuv,"
		+ "  pay_posizioni_debitorie.qrcode,pay_posizioni_debitorie.codice_avviso as codiceAvviso,pay_posizioni_debitorie.data_registrazione as dataRegistrazione, pay_posizioni_debitorie.flag_otf as flagOtf, pay_dettaglio_importi.id as idDettaglioImporti,  pay_dettaglio_importi.importo,"
		+ "  pay_dettaglio_importi.desc_causale as descrizioneCausale,  pay_dettaglio_importi.dati_riscossione as datiRiscossione,"
		+ "  pay_dettaglio_importi.anno_accertamento as annoAccertamento,"
		+ "  pay_dettaglio_importi.numero_accertamento as numeroAccertamento,  pay_stato_pagamenti.id as idStatoPagamenti,"
		+ "  pay_stato_pagamenti.stato, pay_stato_pagamenti.desc_stato as descrizioneStato,"
		+ "  pay_stato_pagamenti.data_evento as dataEvento  from   pay_posizioni_debitorie "
		+ "    inner join pay_soggetti_debitori on      pay_posizioni_debitorie.idcomune = pay_soggetti_debitori.idcomune and "
		+ "      pay_posizioni_debitorie.fk_soggetto_debitore = pay_soggetti_debitori.id     inner join pay_dettaglio_importi on "
		+ "      pay_posizioni_debitorie.idcomune = pay_dettaglio_importi.idcomune and "
		+ "      pay_posizioni_debitorie.id = pay_dettaglio_importi.fk_posizione_debitoria  left join pay_stato_pagamenti on "
		+ "      pay_posizioni_debitorie.idcomune = pay_stato_pagamenti.idcomune and "
		+ "      pay_posizioni_debitorie.id = pay_stato_pagamenti.fk_posizione_debitoria where " + "  pay_posizioni_debitorie.idcomune = ? ";
	sql += "and pay_posizioni_debitorie.id in (";
	sql += "?";
	this.parameters.add(new SimpleParameterHelper(position, -100, new IntegerType()));// condizione mai vera
	position++;
	for (Integer id : idPosizioni) {
	    sql += ",?";
	    this.parameters.add(new SimpleParameterHelper(position, id.intValue(), new IntegerType()));
	    position++;
	}
	sql += ") ";
	sql += "order by pay_posizioni_debitorie.id asc, pay_stato_pagamenti.data_evento desc";
	return sql;
    }
}
