package it.gruppoinit.pal.gp.core.dao.helper;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.domain.helper.PayPosizioniDebitorieFilter;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class PayPosizioniDebitorieQueryHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(PayPosizioniDebitorieQueryHelper.class);
    /**
     * Variabili aggiunte per gestire la paginazione su oracle. In caso di ricerca utilizzando i campi dinamici si crea
     * un errore dovuta alla paginazione "ROWNUM" impostata da hibernate. In questo caso dobbiamo evitare di far gestire
     * la paginazione ad hibernate e impostarla direttamente sulla query.
     */
    private Integer firstResult;
    private Integer maxResults;
    private PayPosizioniDebitorieFilter filter;
    private TipoQueryHelperEnum tipoQueryHelperEnum;

    public PayPosizioniDebitorieQueryHelper(PayPosizioniDebitorieFilter filter, SessionFactoryImplementor sessimpl,
	    TipoQueryHelperEnum tipoQueryHelperEnum, Integer firstResult, Integer maxResults) {

	this.filter = filter;
	Dialect dialetto = sessimpl.getDialect();
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	this.tipoQueryHelperEnum = tipoQueryHelperEnum;
	this.firstResult = firstResult;
	this.maxResults = maxResults;
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("presenzaspuntista", Hibernate.INTEGER);
	q.addScalar("statopagato", Hibernate.INTEGER);
	q.addScalar("statopagamentoattivo", Hibernate.INTEGER);
	q.addScalar("nome", Hibernate.STRING);
	q.addScalar("cognome", Hibernate.STRING);
	q.addScalar("cfpiva", Hibernate.STRING);
	q.addScalar("email", Hibernate.STRING);
	q.addScalar("idposizionedebitoria", Hibernate.INTEGER);
	q.addScalar("idcomuneposizionedebitoria", Hibernate.STRING);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("descrizionecausale", Hibernate.STRING);
	q.addScalar("datascadenza", Hibernate.DATE);
	q.addScalar("numrata", Hibernate.INTEGER);
	q.addScalar("iuv", Hibernate.STRING);
	q.addScalar("codicesoggettodebitore", Hibernate.INTEGER);
	q.addScalar("codiceavviso", Hibernate.STRING);
	q.addScalar("idposizionepsp", Hibernate.STRING);
	q.addScalar("datainvioapsp", Hibernate.DATE);
	q.addScalar("dataannullamento", Hibernate.DATE);
	q.addScalar("dataregistrazione", Hibernate.DATE);
	q.addScalar("anno", Hibernate.INTEGER);
    }

    @Override
    public String buildQuery() {

	String result = "";
	int position = 0;
	// dal filtro costruisco la query
	switch (tipoQueryHelperEnum) {
	    case COUNT:
		result = countQuery;
		position = 1;
		break;
	    case SELECT:
		position = 6;
		if (!DialettoEnum.ORACLE.equals(_dialetto)) {
		    result = "select " + selectQuery;
		} else {
		    // Necessario per la paginazione oracle, in alcuni casi (ricerca per campi dinamici) la paginazione standard
		    // applicata da hibernate "ROWNUM < x" non funzionava.
		    result = "SELECT * FROM ( select ROW_NUMBER() OVER ( " + getOrderFilter(filter) + " ) AS PAGINAZIONE, " + selectQuery;
		}
		break;
	    default:
		break;
	}
	//	String result = isCountQuery == true ? countQuery : selectQuery;
	result += fromQuery + whereQuery;
	//	int position = 3;
	//	if (isCountQuery) {
	//	    position = 2;
	//	}
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	// FILTRI
	if (filter.getPresenza() != null) {
	    if (filter.getPresenza().booleanValue()) {
		result += " and exists (select 1 from " +
			SCHEMA_NAME +
			"mercatipresenze_d where mercatipresenze_d.idcomune=pd.idcomune and  mercatipresenze_d.fk_pay_pos_deb=pd.id) ";
	    } else {
		result += " and not exists (select 1 from " +
			SCHEMA_NAME +
			"mercatipresenze_d where mercatipresenze_d.idcomune=pd.idcomune and  mercatipresenze_d.fk_pay_pos_deb=pd.id) ";
	    }
	}
	if (StringUtils.isNotBlank(filter.getDescrizione())) {
	    result += " and lower(pd.descrizione_causale) like ? ";
	    parameters.add(new ParameterHelper(position, "%" + filter.getDescrizione().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	if (filter.getId() != null) {
	    result += " and pd.id = ? ";
	    parameters.add(new ParameterHelper(position, filter.getId(), new IntegerType()));
	    position++;
	}
	if (StringUtils.isNotBlank(filter.getRichiedente())) {
	    result += " and ( lower(psd.nome) like ? or lower(psd.cognome) like ? or lower(psd.cf_pi) like ? or lower(psd.email ) like ? )";
	    parameters.add(new ParameterHelper(position, "%" + filter.getRichiedente().trim().toLowerCase() + "%", new StringType()));
	    position++;
	    parameters.add(new ParameterHelper(position, "%" + filter.getRichiedente().trim().toLowerCase() + "%", new StringType()));
	    position++;
	    parameters.add(new ParameterHelper(position, "%" + filter.getRichiedente().trim().toLowerCase() + "%", new StringType()));
	    position++;
	    parameters.add(new ParameterHelper(position, "%" + filter.getRichiedente().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	if (filter.getPagato() != null) {
	    if (filter.getPagato().booleanValue()) {
		result += " and exists (select 1 from " +
			SCHEMA_NAME +
			"pay_stato_pagamenti pm where pm.idcomune=pd.idcomune and  pm.fk_posizione_debitoria=pd.id and " //
			+
			"pm.stato in (?,?,?,?)     " //
			+
			") ";
	    } else {
		result += " and not exists (select 1 from " +
			SCHEMA_NAME +
			"pay_stato_pagamenti pm where pm.idcomune=pd.idcomune and  pm.fk_posizione_debitoria=pd.id and " //
			+
			"pm.stato in (?,?,?,?)     " //
			+
			") ";
	    }
	    parameters.add(new ParameterHelper(position, "PAGATO_OFFLINE_DA_ANNULLARE", new StringType()));
	    position++;
	    parameters.add(new ParameterHelper(position, "PAGATO_OFFLINE_ANNULLATO", new StringType()));
	    position++;
	    parameters.add(new ParameterHelper(position, "NOTIFICATO_DA_PSP", new StringType()));
	    position++;
	    parameters.add(new ParameterHelper(position, "RENDICONTATO_DA_IC", new StringType()));
	    position++;
	}
	// DATI ISTANZA		
	switch (tipoQueryHelperEnum) {
	    case SELECT:
		result += getOrderFilter(filter);
		if (DialettoEnum.ORACLE.equals(_dialetto)) {
		    result += " )";
		    if (firstResult == null) {
			firstResult = Integer.valueOf(0);
		    }
		    if (maxResults != null) {
			result += " WHERE PAGINAZIONE >" + firstResult + " AND PAGINAZIONE <=" + (firstResult + maxResults);
		    }
		}
		break;
	    default:
		break;
	}
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	if (StringUtils.isNotBlank(schemaName)) {
	    result = result.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), result);
	return result;
    }

    private String getOrderFilter(PayPosizioniDebitorieFilter filter) {

	String ris = "";
	if (StringUtils.isNotBlank(filter.getOrderBy())) {
	    ris += " order by ";
	    if (filter.getOrderBy().equalsIgnoreCase("dataregistrazione")) {
		ris += " pd.data_registrazione " + filter.getOrderAscDesc();
	    }
	} else {
	    ris += " order by ";
	    ris += " pd.data_registrazione " + filter.getOrderAscDesc();
	}
	return ris;
    }

    private String countQuery = "select count(*) as conteggio_posizioni ";

    @Override
    public void setFilterValues(SQLQuery q) {

	int _position = 0;
	switch (tipoQueryHelperEnum) {
	    case COUNT:
		break;
	    case SELECT:
		// 'PAGATO_OFFLINE_DA_ANNULLARE','PAGATO_OFFLINE_ANNULLATO','NOTIFICATO_DA_PSP','RENDICONTATO_DA_IC'
		q.setString(_position++, "PAGATO_OFFLINE_DA_ANNULLARE");
		q.setString(_position++, "PAGATO_OFFLINE_ANNULLATO");
		q.setString(_position++, "NOTIFICATO_DA_PSP");
		q.setString(_position++, "RENDICONTATO_DA_IC");
		// 
		q.setString(_position++, "ATTIVATO_IN_PSP");
		break;
	    default:
		break;
	}
	q.setString(_position++, ORMHelper.getIdcomune());
	for (ParameterHelper parameter : parameters) {
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    private String selectQuery = "(select count(*) from " +
	    SCHEMA_NAME +
	    "mercatipresenze_d where mercatipresenze_d.idcomune=pd.idcomune and  mercatipresenze_d.fk_pay_pos_deb=pd.id ) as presenzaspuntista, " //
	    +
	    "(select count(*) from " +
	    SCHEMA_NAME +
	    "pay_stato_pagamenti pm where pm.idcomune=pd.idcomune and  pm.fk_posizione_debitoria=pd.id and " //
	    +
	    "pm.stato in (?,?,?,?)     " //
	    +
	    ") as statopagato,    " //
	    +
	    "(select count(*) from " +
	    SCHEMA_NAME +
	    "pay_stato_pagamenti pm where pm.idcomune=pd.idcomune and  pm.fk_posizione_debitoria=pd.id and " //
	    +
	    "pm.stato = ?) as statopagamentoattivo," //
	    +
	    "psd.nome as nome," //
	    +
	    "psd.cognome as cognome," //
	    +
	    "psd.cf_pi as cfpiva," //
	    +
	    "psd.email as email," //
	    +
	    "pd.id as idposizionedebitoria," //
	    +
	    "pd.idcomune as idcomuneposizionedebitoria, " //
	    +
	    "pd.software as software, " //
	    +
	    "pd.descrizione_causale as descrizionecausale,                  " //
	    +
	    "pd.data_scadenza as datascadenza,     " //
	    +
	    "pd.num_rata as numrata, " //
	    +
	    "pd.iuv as iuv,      " //
	    +
	    "pd.fk_soggetto_debitore as codicesoggettodebitore,                 " //
	    +
	    "pd.codice_avviso as codiceavviso,     " //
	    +
	    "pd.id_posizione_psp as idposizionepsp,  " //
	    +
	    "pd.data_invio_a_psp as datainvioapsp,  " //
	    +
	    "pd.data_annullamento as dataannullamento, " //
	    +
	    "pd.data_registrazione as dataregistrazione," //
	    +
	    "pd.anno as anno,         " //
	    +
	    "pd.qrcode as qrcode ";
    private String fromQuery = " from" + " " + SCHEMA_NAME + "pay_posizioni_debitorie pd" // 
	    + "	inner join " // 
	    + SCHEMA_NAME + "pay_soggetti_debitori psd on " // 
	    + "	psd.idcomune=pd.idcomune and " // 
	    + "	psd.id=pd.fk_soggetto_debitore ";
    private String whereQuery = " where pd.idcomune=?";
}
