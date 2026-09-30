package it.gruppoinit.pal.gp.core.dao.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabilicomuniService;
import it.gruppoinit.pal.gp.core.service.ResponsabilisoftwareService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryDomandeStcScadenzarioDTOHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryDomandeStcScadenzarioDTOHelper.class);
    // Costanti
    private final static String replaceWhereInSoftwareCondition = "SOFTAWARE_IN_CONDITION";
    private final static String replaceWhereInComuniCondition = "COMUNI_IN_CONDITION";
    // Variabili del costruttore
    private boolean isCountQuery;
    private boolean isImportate;
    private UserSecurityService userSecurityService;
    private ResponsabilisoftwareService responsabilisoftwareService;
    private ComuniassociatiService comuniassociatiService;
    private ResponsabilicomuniService responsabilicomuniService;
    private String codiceSoftwareScad;
    // Query
    private String countQuery = "select count(*) as conteggio_domandestc ";
    //i.idcomune as idcomune,
    private String selectQueryDomandeStcImportate = "select i.idcomune,i.codiceistanza, i.numeroistanza, "
	    + "i.data,i.numeroprotocollo,i.dataprotocollo," + "wap.sc_descrizione as scDescrizione," + "si.stato,"
	    + "richiedente.tipoanagrafe,richiedente.nominativo,richiedente.nome,richiedente.partitaiva,richiedente.codicefiscale,"
	    + "richiedente.tipologia,richiedente.flag_disabilitato as flagDisabilitato," + "fg.formagiuridica," + "comune.comune,"
	    + "soft.codice as codSoftware,soft.descrizione as descrizioneSoftware,"
	    + "d.id_nodo as idNodo,d.ultimoerrore,d.id_sportellomitt as sportelloDomanda";
    private String fromQueryDomandeStcImportate = " from" + " " + SCHEMA_NAME + "domandestc d INNER JOIN " + SCHEMA_NAME
	    + "istanze i on i.idcomune = d.idcomune AND i.codiceistanza=d.codiceistanza " + "inner join " + SCHEMA_NAME
	    + "STATIISTANZA si on i.chiusura = si.codicestato and i.idcomune = si.idcomune and i.software = si.software " + "left join "
	    + SCHEMA_NAME + "software soft on soft.CODICE=i.software " + "left join " + SCHEMA_NAME
	    + "Comuni comune on comune.codicecomune=i.codicecomune " + "left join " + SCHEMA_NAME
	    + "Anagrafe richiedente on i.codicerichiedente = richiedente.codiceanagrafe and i.idcomune = richiedente.idcomune " + "left join "
	    + SCHEMA_NAME + "formegiuridiche fg on fg.CODICEFORMAGIURIDICA = richiedente.FORMAGIURIDICA " + "and fg.idcomune = richiedente.idcomune "
	    + "INNER JOIN " + SCHEMA_NAME + "fo_arconfigurazione foac on ( foac.idcomune = i.idcomune or foac.idcomune is null) "
	    + "AND (foac.stato_iniziale_istanza = i.chiusura or foac.stato_iniziale_istanza is null) "
	    + "AND (foac.software = i.software or foac.software is null) " + "INNER JOIN  " + SCHEMA_NAME
	    + "vw_alberoproc wap ON i.idcomune = wap.idcomune AND i.codiceinterventoproc = wap.sc_id ";
    private String whereQuery = " where d.idcomune=? and i.software " + replaceWhereInSoftwareCondition + " AND i.codicecomune "
	    + replaceWhereInComuniCondition + " AND ( d.flag_import = ? ) ";
    private String orderConditionQuery = " order by ";

    public QueryDomandeStcScadenzarioDTOHelper(SessionFactoryImplementor sessimpl, UserSecurityService userSecurityService,
	    ResponsabilisoftwareService responsabilisoftwareService, ComuniassociatiService comuniassociatiService,
	    ResponsabilicomuniService responsabilicomuniService, boolean isCountQuery, boolean isImportate, String codiceSoftwareScad) {

	log.debug("QueryIstanzeHelper: recupero il dialetto della SessionFactoryImplementor");
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryIstanzeHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = fromString(hibernateDialect);
	log.debug("QueryIstanzeHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryIstanzeHelper: schemaName={}", schemaName);
	this.isCountQuery = isCountQuery;
	this.userSecurityService = userSecurityService;
	this.responsabilisoftwareService = responsabilisoftwareService;
	this.comuniassociatiService = comuniassociatiService;
	this.isImportate = isImportate;
	this.responsabilicomuniService = responsabilicomuniService;
	this.codiceSoftwareScad = codiceSoftwareScad;
    }

    @Override
    public String buildQuery() {

	int position = 0;
	// dal filtro costruisco la query
	String result = isCountQuery == true ? countQuery : selectQueryDomandeStcImportate;
	result += fromQueryDomandeStcImportate + whereQuery;
	//	if (isCountQuery) {
	//	    position = 2;
	//	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////// Condizioni di WHERE///////////////////////////////////////////////////
	// 
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	// Gestione del filtro software: 
	// 1.Nel caso il software sia  TT e non sia impostato nessun software di ricerca tra i parametri dell scandezario per l'operatore 
	// loggato [codiceSoftwareScad isBlank ] : devono essere impostati come filtri tutti i software attivi per l'operatore loggato 
	// 2.Nel caso software != TT o codiceSoftwareScad isNoBlank
	//   2.1 Filtro per il software corrente ORMHelper.getSoftware() 
	//   2.2 Filtro per il software passato "codiceSoftwareScad"
	Responsabili responsabili = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	if (ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_TT) && StringUtils.isBlank(codiceSoftwareScad)) {
	    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ////////////////////////////////////////// CASO 1 /////////////////////////////////////////////////////////
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    if (log.isDebugEnabled()) {
		log.debug("buildQuery#Software {}, query applicato per tutti i software attivi per l'operatore", WebConstants.SOFTWARE_TT);
	    }
	    List<Responsabilisoftware> responsabilisoftwares = responsabilisoftwareService.findByResponsabile(responsabili);
	    //Inizializzo la condizione di in
	    String INConditionSoftware = "IN ( ";
	    for (Responsabilisoftware responsabilisoftware : responsabilisoftwares) {
		// Inserisco il parametro sulla query ed incremento il contatore
		parameters.add(new ParameterHelper(position, responsabilisoftware.getSoftware().getCodice(), new StringType()));
		position++;
		INConditionSoftware += "?,";
	    }
	    // Rimuovo l'ultmima virgola che non serve
	    INConditionSoftware = StringUtils.removeEnd(INConditionSoftware, ",");
	    INConditionSoftware += ") ";
	    result = result.replace(replaceWhereInSoftwareCondition, INConditionSoftware);
	} else {
	    ////////////////////////////////// CASO 2 //////////////////////////////////////////////////
	    ///////////////////////////////////////////////////////////////////////////////////////////
	    // 2.1
	    if (StringUtils.isBlank(codiceSoftwareScad)) {
		if (log.isDebugEnabled()) {
		    log.debug("buildQuery#Software {}, query applicata solo per il software corrente {}", new Object[] { ORMHelper.getSoftware(),
			    ORMHelper.getSoftware() });
		}
		// Inserisco il parametro sulla query ed incremento il contatore
		parameters.add(new ParameterHelper(position, ORMHelper.getSoftware(), new StringType()));
	    } else {
		log.debug("buildQuery#Software {}, query applicata solo per il software passato {}", new Object[] { codiceSoftwareScad,
			codiceSoftwareScad });
		// Inserisco il parametro sulla query ed incremento il contatore
		parameters.add(new ParameterHelper(position, codiceSoftwareScad, new StringType()));
	    }
	    result = result.replace(replaceWhereInSoftwareCondition, "= ?");
	    position++;
	}
	// Gestione istanze multi comune
	boolean isComuneassociato = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	// Devo controllare anche che non stiamo controllando le istanze non importate in quanto queste non hanno 
	// un codice comune. Il codice comune è legato all'istanza vera e propria inserita e in questo caso 
	// non ne esiste una.
	if (isImportate) {
	    if (isComuneassociato) {
		if (log.isDebugEnabled()) {
		    log.debug("buildQuery#Installazione multicomune, cerco per tutti i comuni per cui l'operatore è abilitato");
		}
		//Inizializzo la condizione di IN
		String INConditionComune = " IN ( ";
		List<Responsabilicomuni> responsabilicomunis = responsabilicomuniService.findByOperatore(responsabili);
		for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
		    // Inserisco il parametro sulla query ed incremento il contatore
		    parameters.add(new ParameterHelper(position, responsabilicomuni.getComune().getCodicecomune(), new StringType()));
		    position++;
		    INConditionComune += "?,";
		}
		// Rimuovo l'ultmima virgola che non serve
		INConditionComune = StringUtils.removeEnd(INConditionComune, ",");
		INConditionComune += ") ";
		result = result.replace(replaceWhereInComuniCondition, INConditionComune);
	    } else {
		if (log.isDebugEnabled()) {
		    log.debug("buildQuery#Installazione singolo comune");
		}
		// Inserisco il parametro sulla query ed incremento il contatore
		parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
		result = result.replace(replaceWhereInComuniCondition, "= ?");
		position++;
	    }
	}
	// Condizione flag_import
	parameters.add(new ParameterHelper(position, isImportate, new BooleanType()));
	if (!isCountQuery) {
	    result += orderConditionQuery;
	    if (isImportate) {
		result += "comune.comune,";
	    }
	    result += "soft.descrizione";
	}
	if (StringUtils.isNotBlank(schemaName)) {
	    result = result.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), result);
	return result;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	int _position = 0;
	//	log.debug("param {}={}", _position, ORMHelper.getIdcomune());
	//	q.setString(_position, ORMHelper.getIdcomune()); // IDCOMUNE
	//	_position++;
	for (ParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("codiceistanza", Hibernate.INTEGER);
	q.addScalar("numeroistanza", Hibernate.STRING);
	q.addScalar("descrizioneSoftware", Hibernate.STRING);
	q.addScalar("codSoftware", Hibernate.STRING);
	q.addScalar("data");
	q.addScalar("numeroprotocollo", Hibernate.STRING);
	q.addScalar("dataprotocollo");
	q.addScalar("scDescrizione", Hibernate.STRING);
	q.addScalar("stato", Hibernate.STRING);
	q.addScalar("tipoanagrafe", Hibernate.STRING);
	q.addScalar("nominativo", Hibernate.STRING);
	q.addScalar("nome", Hibernate.STRING);
	q.addScalar("partitaiva", Hibernate.STRING);
	q.addScalar("codicefiscale", Hibernate.STRING);
	q.addScalar("tipologia", Hibernate.STRING);
	q.addScalar("flagDisabilitato", Hibernate.BOOLEAN);
	q.addScalar("formagiuridica", Hibernate.STRING);
	q.addScalar("comune", Hibernate.STRING);
	q.addScalar("idNodo", Hibernate.STRING);
	q.addScalar("ultimoerrore", Hibernate.STRING);
	q.addScalar("sportelloDomanda", Hibernate.STRING);
    }
}
