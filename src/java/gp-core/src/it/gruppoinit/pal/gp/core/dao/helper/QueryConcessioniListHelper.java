package it.gruppoinit.pal.gp.core.dao.helper;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.ShortType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.domain.VwConcessionilista;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class QueryConcessioniListHelper extends BaseQueryHelper {

    private VwConcessionilista vwConcessionilista;
    private TipoQueryHelperEnum tipoQueryHelperEnum;

    public QueryConcessioniListHelper(SessionFactoryImplementor sessimpl, VwConcessionilista vwConcessionilista,
	    TipoQueryHelperEnum tipoQueryHelperEnum) {

	log.debug("QueryConcessioniListHelper: recupero il dialetto della SessionFactoryImplementor");
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryConcessioniListHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryConcessioniListHelper: Il dialetto è {}", _dialetto);
	this.vwConcessionilista = vwConcessionilista;
	this.tipoQueryHelperEnum = tipoQueryHelperEnum;
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

	switch (tipoQueryHelperEnum) {
	    case SELECT_GROUP_BY_CONC_ID:
		q.addScalar("idcomune", Hibernate.STRING);
		q.addScalar("conc_id", Hibernate.BIG_INTEGER);
		break;
	    default:
		q.addScalar("origine", Hibernate.STRING);
		q.addScalar("progressivo", Hibernate.BIG_INTEGER);
		q.addScalar("conc_autorizresponsabile", Hibernate.STRING);
		q.addScalar("idcomune", Hibernate.STRING);
		q.addScalar("iconc_codicecausale", Hibernate.SHORT);
		q.addScalar("data_storico", Hibernate.DATE);
		q.addScalar("data_istanza", Hibernate.DATE);
		q.addScalar("software", Hibernate.STRING);
		q.addScalar("ist_codiceistanza", Hibernate.BIG_INTEGER);
		q.addScalar("ist_numeroistanza", Hibernate.STRING);
		q.addScalar("ist_codicerichiedente", Hibernate.BIG_INTEGER);
		q.addScalar("conc_id", Hibernate.BIG_INTEGER);
		q.addScalar("conc_numero", Hibernate.STRING);
		q.addScalar("conc_datavalidita", Hibernate.DATE);
		q.addScalar("datavalidita", Hibernate.DATE);
		q.addScalar("conc_datarilascio", Hibernate.DATE);
		q.addScalar("datarilascio", Hibernate.DATE);
		q.addScalar("conc_datascadenza", Hibernate.DATE);
		q.addScalar("conc_codicetipo", Hibernate.STRING);
		q.addScalar("conc_tipo", Hibernate.STRING);
		q.addScalar("conc_stagionalea", Hibernate.STRING);
		q.addScalar("conc_stagionaleda", Hibernate.STRING);
		q.addScalar("conc_attiva", Hibernate.BOOLEAN);
		q.addScalar("conc_codicetitolare", Hibernate.BIG_INTEGER);
		q.addScalar("conc_fkidregistro", Hibernate.BIG_INTEGER);
		q.addScalar("conc_registro", Hibernate.STRING);
		q.addScalar("conc_titolare", Hibernate.STRING);
		q.addScalar("conc_tit_cf", Hibernate.STRING);
		q.addScalar("conc_tit_piva", Hibernate.STRING);
		q.addScalar("conc_tit_tipoanagrafe", Hibernate.STRING);
		q.addScalar("aut_numero", Hibernate.STRING);
		q.addScalar("aut_data", Hibernate.DATE);
		q.addScalar("autorizdata", Hibernate.DATE);
		q.addScalar("aut_codregistro", Hibernate.BIG_INTEGER);
		q.addScalar("aut_registro", Hibernate.STRING);
		q.addScalar("ist_nominativo", Hibernate.STRING);
		q.addScalar("ist_ric_tipoanagrafe", Hibernate.STRING);
		q.addScalar("ist_nominativoazienda", Hibernate.STRING);
		q.addScalar("ist_azi_tipoanagrafe", Hibernate.STRING);
		q.addScalar("ist_codicefiscale", Hibernate.STRING);
		q.addScalar("ist_partitaiva", Hibernate.STRING);
		q.addScalar("iconc_causale", Hibernate.STRING);
		q.addScalar("iconc_codicecausalestorico", Hibernate.BIG_INTEGER);
		//q.addScalar("icon_causalesorico", Hibernate.STRING);
		q.addScalar("conc_idmercato", Hibernate.BIG_INTEGER);
		q.addScalar("conc_mercato", Hibernate.STRING);
		q.addScalar("merc_attivo", Hibernate.BOOLEAN);
		q.addScalar("conc_idposteggio", Hibernate.BIG_INTEGER);
		q.addScalar("conc_posteggio", Hibernate.STRING);
		q.addScalar("conc_pos_larghezza", Hibernate.BIG_DECIMAL);
		q.addScalar("conc_pos_lunghezza", Hibernate.BIG_DECIMAL);
		q.addScalar("conc_pos_superficie", Hibernate.BIG_DECIMAL);
		q.addScalar("post_idtipospazio", Hibernate.BIG_INTEGER);
		q.addScalar("post_tipospazio", Hibernate.STRING);
		q.addScalar("conc_idmercatiuso", Hibernate.BIG_INTEGER);
		q.addScalar("conc_descrizioneuso", Hibernate.STRING);
		q.addScalar("peso_mercato", Hibernate.BIG_INTEGER);
		q.addScalar("merc_iduso", Hibernate.BIG_INTEGER);
		q.addScalar("conc_pos_codicestradario", Hibernate.BIG_INTEGER);
		q.addScalar("stradario_descrizione", Hibernate.STRING);
		q.addScalar("conc_pos_note", Hibernate.STRING);
		q.addScalar("conc_enterilascio", Hibernate.STRING);
		q.addScalar("ist_codicecomune", Hibernate.STRING);
		q.addScalar("conc_codicecomune", Hibernate.STRING);
		//
		q.addScalar("conc_codiceoccupante", Hibernate.BIG_INTEGER);
		q.addScalar("conc_occupante", Hibernate.STRING);
		q.addScalar("conc_occ_cf", Hibernate.STRING);
		q.addScalar("conc_occ_piva", Hibernate.STRING);
		q.addScalar("conc_occ_tipoanagrafe", Hibernate.STRING);
		break;
	}
    }

    private String buildQuery1() {

	String result_query_1 = "";
	switch (tipoQueryHelperEnum) {
	    case COUNT:
		result_query_1 = countQuery + fromQuery_1 + whereQuery_1;
		break;
	    case COUNT_DISTINCT_CONC_ID:
		result_query_1 = selectQueryDistinctConcId1 + fromQuery_1 + whereQuery_1;
		break;
	    case SELECT_GROUP_BY_CONC_ID:
		result_query_1 = selectQuery_1 + fromQuery_1 + whereQuery_1;
		break;
	    case SELECT_WHERE_CONC_ID:
		result_query_1 = selectQuery_1 + fromQuery_1 + whereQuery_1;
		break;
	    case SELECT:
		result_query_1 = selectQuery_1 + fromQuery_1 + whereQuery_1;
		break;
	    case PENTAHO_EXP:
		result_query_1 = selectPentahoExpQuery_1 + fromQuery_1 + whereQuery_1;
		break;
	    default:
		break;
	}
	// Per prima cosa imposto l'idcomune
	result_query_1 += "  AUTORIZZAZIONI.IDCOMUNE = ?";
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	result_query_1 += " AND  ISTANZE.SOFTWARE = ?";
	parameters.add(new ParameterHelper(position, ORMHelper.getSoftware(), new StringType()));
	position++;
	//OK. CONC_ID
	if (vwConcessionilista.getConcId() != null) {
	    result_query_1 += " AND AUTORIZZAZIONI.ID = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getConcId(), new IntegerType()));
	    position++;
	}
	//OK
	if (StringUtils.isNotBlank(vwConcessionilista.getConcNumero())) {
	    if (StringUtils.indexOf(vwConcessionilista.getConcNumero(), "%") >= 0) {
		result_query_1 += " AND lower(AUTORIZZAZIONI.AUTORIZNUMERO) like ?";
		parameters.add(new ParameterHelper(position, vwConcessionilista.getConcNumero().trim().toLowerCase(), new StringType()));
		position++;
	    } else {
		result_query_1 += " AND lower(AUTORIZZAZIONI.AUTORIZNUMERO) = ?";
		parameters.add(new ParameterHelper(position, vwConcessionilista.getConcNumero().trim().toLowerCase(), new StringType()));
		position++;
	    }
	}
	//OK
	if (vwConcessionilista.getConcIdmercato() != null && vwConcessionilista.getConcIdmercato() != 0) {
	    result_query_1 += " AND AUTORIZZAZIONI_CONCESSIONI.FK_CODICEMERCATO = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getConcIdmercato(), new IntegerType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getConcIdposteggio() != null && vwConcessionilista.getConcIdposteggio() != 0) {
	    result_query_1 += " AND AUTORIZZAZIONI_CONCESSIONI.FK_IDPOSTEGGIO = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getConcIdposteggio(), new IntegerType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getConcIdmercatiuso() != null && vwConcessionilista.getConcIdmercatiuso() != 0) {
	    result_query_1 += " AND AUTORIZZAZIONI_CONCESSIONI.FK_IDMERCATIUSO = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getConcIdmercatiuso(), new IntegerType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getIconcCodicecausale() != null && vwConcessionilista.getIconcCodicecausale() != 0) {
	    result_query_1 += " AND AUTORIZZAZIONI.FK_CAUSALE_ACQUISIZIONE = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIconcCodicecausale(), new ShortType()));
	    position++;
	}
	//OK
	if (EntityUtils.getNestedProperty(vwConcessionilista.getContipologiaregistri(), "id.codice") != null) {
	    result_query_1 += " AND AUTORIZZAZIONI.FKIDREGISTRO = ? ";
	    // System.out.println(vwConcessionilista.getContipologiaregistri().getId().getCodice());
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getContipologiaregistri().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getDataInizioRilascio() != null) {
	    result_query_1 += " AND AUTORIZZAZIONI.AUTORIZDATA >= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getDataInizioRilascio(), new DateType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getDataFineRilascio() != null) {
	    result_query_1 += " AND AUTORIZZAZIONI.AUTORIZDATA <= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getDataFineRilascio(), new DateType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getDataInizioScadenze() != null) {
	    result_query_1 += " AND AUTORIZZAZIONI.DATASCADENZA >= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getDataInizioScadenze(), new DateType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getDataFineScadenze() != null) {
	    result_query_1 += " AND AUTORIZZAZIONI.DATASCADENZA <= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getDataFineScadenze(), new DateType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getDataStoricoDaTransient() != null) {
	    result_query_1 += " AND AUTORIZZAZIONI.DATA_CESSAZIONE >= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getDataStoricoDaTransient(), new DateType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getDataStoricoATransient() != null) {
	    result_query_1 += " AND AUTORIZZAZIONI.DATA_CESSAZIONE <= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getDataStoricoATransient(), new DateType()));
	    position++;
	}
	//OK
	if (EntityUtils.getNestedProperty(vwConcessionilista.getTitolareConcessione(), "id.codice") != null) {
	    result_query_1 += " AND ( AUTORIZZAZIONI.FK_CODICEANAGRAFE = ? or  AUTORIZZAZIONI.CODICEOCCUPANTE = ?)";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getTitolareConcessione().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getTitolareConcessione().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//OK
	if (!(vwConcessionilista.getIstCodicerichiedente() == null || vwConcessionilista.getIstCodicerichiedente().equals(0))) {
	    result_query_1 += " AND ISTANZE.CODICERICHIEDENTE = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstCodicerichiedente(), new IntegerType()));
	    position++;
	}
	// E' stato aggiunto un ulteriore controllo perchè nel caso sto verificando "attive ad una certa data"
	// non devo escludere quelle "non attive" oggi. Il filtro giusto sarà impostato sulle date nella sezione 
	// sotto (vedi FILTRI PER GESTIRE IL CASO FLAG_SOLO_ATTIVE == true e DATA FINO A != NULL)
	if (vwConcessionilista.isConcAttiva() && vwConcessionilista.getAttiveAllaDataTransient() == null) {
	    result_query_1 += " AND AUTORIZZAZIONI.FLAG_ATTIVA = ? ";
	    parameters.add(new ParameterHelper(position, true, new BooleanType()));
	    position++;
	}
	///////////////////////////////////////////// Filtri per l'istanza /////////////////////////////////////////
	///////////////////////////////////////////////////// //////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//det.createAlias("istanza", "_istanza");
	//OK
	if (StringUtils.isNotBlank(vwConcessionilista.getIstanza().getNumeroistanza())) {
	    result_query_1 += " AND ISTANZE.NUMEROISTANZA = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanza().getNumeroistanza(), new StringType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getIstanzadataDa() != null) {
	    result_query_1 += " AND ISTANZE.DATA >= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanzadataDa(), new DateType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getIstanzadataA() != null) {
	    result_query_1 += " AND ISTANZE.DATA <= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanzadataA(), new DateType()));
	    position++;
	}
	//OK
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanza().getAlberoproc(), "id.codice") != null) {
	    result_query_1 += " AND ISTANZE.CODICEINTERVENTOPROC = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanza().getAlberoproc().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//OK
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanza().getProcedura(), "id.codice") != null) {
	    result_query_1 += " AND ISTANZE.CODICEPROCEDURA = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanza().getProcedura().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//OK
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanzestradario().getStradario(), "id.codice") != null) {
	    result_query_1 += " AND ISTANZESTRADARIO.CODICESTRADARIO = ? ";
	    parameters.add(
		    new ParameterHelper(position, vwConcessionilista.getIstanzestradario().getStradario().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//OK
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanzestradario(), "id.codice") != null
		&& StringUtils.isNotBlank(vwConcessionilista.getIstanzestradario().getCap())) {
	    result_query_1 += " AND ISTANZESTRADARIO.CAP = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanzestradario().getCap(), new IntegerType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getIstanza().getComune() != null
		&& StringUtils.isNotBlank(vwConcessionilista.getIstanza().getComune().getCodicecomune())) {
	    result_query_1 += " AND ISTANZE.CODICECOMUNE = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanza().getComune().getCodicecomune(), new StringType()));
	    position++;
	}
	//OK
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanza().getRichiedente(), "id.codice") != null) {
	    result_query_1 += " AND ISTANZE.CODICERICHIEDENTE = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanza().getRichiedente().getId().getCodice(), new IntegerType()));
	    position++;
	}
	// FILTRI PER GESTIRE IL CASO FLAG_SOLO_ATTIVE == true e DATA FINO A != NULL
	if (vwConcessionilista.isConcAttiva() && vwConcessionilista.getAttiveAllaDataTransient() != null) {
	    result_query_1 += " AND (( AUTORIZZAZIONI.DATA_CESSAZIONE IS NULL AND AUTORIZZAZIONI.AUTORIZDATA <= ? ) " +
		    " OR (AUTORIZZAZIONI.DATA_CESSAZIONE > ? AND AUTORIZZAZIONI.AUTORIZDATA <= ? ))  ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getAttiveAllaDataTransient(), new DateType()));
	    position++;
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getAttiveAllaDataTransient(), new DateType()));
	    position++;
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getAttiveAllaDataTransient(), new DateType()));
	    position++;
	}
	/**
	 * 
	 */
	return result_query_1;
    }

    private String buildQuery2() {

	String result_query_2 = "";
	switch (tipoQueryHelperEnum) {
	    case COUNT:
		result_query_2 = countQuery + fromQuery_2 + whereQuery_2;
		// position = 2;
		break;
	    case COUNT_DISTINCT_CONC_ID:
		result_query_2 = selectQueryDistinctConcId2 + fromQuery_2 + whereQuery_2;
		break;
	    case SELECT_GROUP_BY_CONC_ID:
		result_query_2 = selectQuery_2 + fromQuery_2 + whereQuery_2;
		break;
	    case SELECT_WHERE_CONC_ID:
		result_query_2 = selectQuery_2 + fromQuery_2 + whereQuery_2;
		break;
	    case SELECT:
		result_query_2 = selectQuery_2 + fromQuery_2 + whereQuery_2;
		//position = 2;
		break;
	    case PENTAHO_EXP:
		result_query_2 = selectPentahoExpQuery_2 + fromQuery_2 + whereQuery_2;
		// position = 2;
		break;
	    default:
		break;
	}
	// Per prima cosa imposto l'idcomune 
	result_query_2 += "  AUTORIZZAZIONI_SUBENTRI.IDCOMUNE = ?";
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	result_query_2 += " AND  ISTANZE.SOFTWARE = ?";
	parameters.add(new ParameterHelper(position, ORMHelper.getSoftware(), new StringType()));
	position++;
	//OK CONC_ID
	if (vwConcessionilista.getConcId() != null) {
	    result_query_2 += " AND AUTORIZZAZIONI_SUBENTRI.FK_IDAUT_ATTUALE = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getConcId(), new IntegerType()));
	    position++;
	}
	//OK
	if (StringUtils.isNotBlank(vwConcessionilista.getConcNumero())) {
	    if (StringUtils.indexOf(vwConcessionilista.getConcNumero(), "%") >= 0) {
		result_query_2 += " AND lower(AUTORIZZAZIONI_SUBENTRI.AUTORIZNUMERO) like ?";
		parameters.add(new ParameterHelper(position, vwConcessionilista.getConcNumero().trim().toLowerCase(), new StringType()));
		position++;
	    } else {
		result_query_2 += " AND AUTORIZZAZIONI_SUBENTRI.AUTORIZNUMERO = ?";
		parameters.add(new ParameterHelper(position, vwConcessionilista.getConcNumero().trim().toLowerCase(), new StringType()));
		position++;
	    }
	}
	//OK
	if (vwConcessionilista.getConcIdmercato() != null && vwConcessionilista.getConcIdmercato() != 0) {
	    result_query_2 += " AND AUTORIZZAZIONI_SUBENTRI_CONC.FK_CODICEMERCATO = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getConcIdmercato(), new IntegerType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getConcIdposteggio() != null && vwConcessionilista.getConcIdposteggio() != 0) {
	    result_query_2 += " AND AUTORIZZAZIONI_SUBENTRI_CONC.FK_IDPOSTEGGIO = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getConcIdposteggio(), new IntegerType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getConcIdmercatiuso() != null && vwConcessionilista.getConcIdmercatiuso() != 0) {
	    result_query_2 += " AND AUTORIZZAZIONI_SUBENTRI_CONC.FK_IDMERCATIUSO = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getConcIdmercatiuso(), new IntegerType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getIconcCodicecausale() != null && vwConcessionilista.getIconcCodicecausale() != 0) {
	    result_query_2 += " AND AUTORIZZAZIONI_SUBENTRI.FK_CAUSALE_ACQUISIZIONE = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIconcCodicecausale(), new ShortType()));
	    position++;
	}
	//OK
	if (EntityUtils.getNestedProperty(vwConcessionilista.getContipologiaregistri(), "id.codice") != null) {
	    result_query_2 += " AND AUTORIZZAZIONI_SUBENTRI.FKIDREGISTRO = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getContipologiaregistri().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getDataInizioRilascio() != null) {
	    result_query_2 += " AND AUTORIZZAZIONI_SUBENTRI.AUTORIZDATA >= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getDataInizioRilascio(), new DateType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getDataFineRilascio() != null) {
	    result_query_2 += " AND AUTORIZZAZIONI_SUBENTRI.AUTORIZDATA <= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getDataFineRilascio(), new DateType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getDataInizioScadenze() != null) {
	    result_query_2 += " AND AUTORIZZAZIONI_SUBENTRI.DATASCADENZA >= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getDataInizioScadenze(), new DateType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getDataFineScadenze() != null) {
	    result_query_2 += " AND AUTORIZZAZIONI_SUBENTRI.DATASCADENZA <= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getDataFineScadenze(), new DateType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getDataStoricoDaTransient() != null) {
	    result_query_2 += " AND AUTORIZZAZIONI_SUBENTRI.DATA_CESSAZIONE >= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getDataStoricoDaTransient(), new DateType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getDataStoricoATransient() != null) {
	    result_query_2 += " AND AUTORIZZAZIONI_SUBENTRI.DATA_CESSAZIONE <= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getDataStoricoATransient(), new DateType()));
	    position++;
	}
	//OK
	if (EntityUtils.getNestedProperty(vwConcessionilista.getTitolareConcessione(), "id.codice") != null) {
	    result_query_2 += " AND (AUTORIZZAZIONI_SUBENTRI.FK_CODICEANAGRAFE = ? OR AUTORIZZAZIONI_SUBENTRI.CODICEOCCUPANTE = ?)";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getTitolareConcessione().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getTitolareConcessione().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//OK
	if (!(vwConcessionilista.getIstCodicerichiedente() == null || vwConcessionilista.getIstCodicerichiedente().equals(0))) {
	    result_query_2 += " AND ISTANZE.CODICERICHIEDENTE = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstCodicerichiedente(), new IntegerType()));
	    position++;
	}
	/// FLAG_ATTIVA non viene filtrata perchè è fissa a =
	if (vwConcessionilista.isConcAttiva()) {
	    if (vwConcessionilista.getAttiveDallaDataTransient() != null || vwConcessionilista.getAttiveAllaDataTransient() != null) {
		// INTERVALLO DI DATE ATTIVO, QUINDI DEVE CERCARE TRA I SUBENTRI QUELLI CHE ERANO ATTIVI NELL'INTERVALLO DI DATE PASSATO
		if (vwConcessionilista.getAttiveDallaDataTransient() != null) {
		    //FIXME ad oggi si è deciso di non utilizzarlo in quanto cercheremo solo quelle attive da una fino a una certa data
		}
		// CODICE SE SELEZIONATO "SOLO ATTIVE" e PRESENTE LA DATA
		if (vwConcessionilista.getAttiveAllaDataTransient() != null) {
		    //		    // DATA  CESSAZIONE
		    //		    result_query_2 += " AND AUTORIZZAZIONI_SUBENTRI.DATA_CESSAZIONE <= ? ";
		    //		    parameters.add(new ParameterHelper(position, vwConcessionilista.getAttiveAllaDataTransient(), new DateType()));
		    //		    position++;
		    //		    // AUT_COLLEGATA.DATACESSAZIONE AS DATACESSAZIONE_AUT
		    //		    result_query_2 += " AND (AUT_COLLEGATA.DATA_CESSAZIONE IS NULL OR AUT_COLLEGATA.DATA_CESSAZIONE > ? ) ";
		    //		    parameters.add(new ParameterHelper(position, vwConcessionilista.getAttiveAllaDataTransient(), new DateType()));
		    //		    position++;
		    result_query_2 += " AND AUTORIZZAZIONI_SUBENTRI.DATA_CESSAZIONE > ? ";
		    parameters.add(new ParameterHelper(position, vwConcessionilista.getAttiveAllaDataTransient(), new DateType()));
		    position++;
		    // AUT_COLLEGATA.DATACESSAZIONE AS DATACESSAZIONE_AUT
		    result_query_2 += " AND  AUTORIZZAZIONI_SUBENTRI.AUTORIZDATA <= ? ";
		    parameters.add(new ParameterHelper(position, vwConcessionilista.getAttiveAllaDataTransient(), new DateType()));
		    position++;
		}
	    } else {
		// SUI SUBENTRI SE RICERCO LE CONCESSIONI ATTIVE SENZA IMPOSTARE UN INTERVALLO DI DATE 
		// DEVO METTERE UNA CODIZIONE SEMPRE FALSA PERCHE' I SUBENTRI SONO SEMPRE NON ATTIVI
		result_query_2 += " AND 1=2 ";
	    }
	}
	///////////////////////////////////////////// Filtri per l'istanza /////////////////////////////////////////
	///////////////////////////////////////////////////// //////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//det.createAlias("istanza", "_istanza");
	//OK
	if (StringUtils.isNotBlank(vwConcessionilista.getIstanza().getNumeroistanza())) {
	    result_query_2 += " AND ISTANZE.NUMEROISTANZA = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanza().getNumeroistanza(), new StringType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getIstanzadataDa() != null) {
	    result_query_2 += " AND ISTANZE.DATA >= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanzadataDa(), new DateType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getIstanzadataA() != null) {
	    result_query_2 += " AND ISTANZE.DATA <= ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanzadataA(), new DateType()));
	    position++;
	}
	//OK
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanza().getAlberoproc(), "id.codice") != null) {
	    result_query_2 += " AND ISTANZE.CODICEINTERVENTOPROC = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanza().getAlberoproc().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//OK
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanza().getProcedura(), "id.codice") != null) {
	    result_query_2 += " AND ISTANZE.CODICEPROCEDURA = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanza().getProcedura().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//OK
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanzestradario().getStradario(), "id.codice") != null) {
	    result_query_2 += " AND ISTANZESTRADARIO.CODICESTRADARIO = ? ";
	    parameters.add(
		    new ParameterHelper(position, vwConcessionilista.getIstanzestradario().getStradario().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//OK
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanzestradario(), "id.codice") != null
		&& StringUtils.isNotBlank(vwConcessionilista.getIstanzestradario().getCap())) {
	    result_query_2 += " AND ISTANZESTRADARIO.CAP = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanzestradario().getCap(), new IntegerType()));
	    position++;
	}
	//OK
	if (vwConcessionilista.getIstanza().getComune() != null
		&& StringUtils.isNotBlank(vwConcessionilista.getIstanza().getComune().getCodicecomune())) {
	    result_query_2 += " AND ISTANZE.CODICECOMUNE = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanza().getComune().getCodicecomune(), new StringType()));
	    position++;
	}
	//OK
	if (EntityUtils.getNestedProperty(vwConcessionilista.getIstanza().getRichiedente(), "id.codice") != null) {
	    result_query_2 += " AND ISTANZE.CODICERICHIEDENTE = ? ";
	    parameters.add(new ParameterHelper(position, vwConcessionilista.getIstanza().getRichiedente().getId().getCodice(), new IntegerType()));
	    position++;
	}
	return result_query_2;
    }

    private String orderBy() {

	String orderBy = " ORDER BY ";
	if (BooleanUtils.toBoolean(vwConcessionilista.isConcAttiva()) && vwConcessionilista.getAttiveAllaDataTransient() != null) {
	    //if (false) {
	    // ASC - FISSO CONC_ID CONC_DATARILASCIO
	    orderBy += " CONC_ID ASC , CONC_DATARILASCIO ASC ";
	} else {
	    String[] campiDaOrdinare = vwConcessionilista.getOrderBy().split(",");
	    switch (vwConcessionilista.getOrderAscDesc()) {
		case ASC:
		    for (int i = 0; i < campiDaOrdinare.length; i++) {
			orderBy += campiDaOrdinare[i] + " ASC, ";
		    }
		    break;
		case DESC:
		    for (int i = 0; i < campiDaOrdinare.length; i++) {
			orderBy += campiDaOrdinare[i] + " DESC, ";
		    }
		    break;
		default:
		    break;
	    }
	    orderBy = StringUtils.substring(orderBy, 0, orderBy.length() - 2);
	}
	return orderBy;
    }

    @Override
    public String buildQuery() {

	// faccio la union tra le due query
	String result = "";
	switch (tipoQueryHelperEnum) {
	    case COUNT:
		log.debug("buildQuery# Query di COUNT.....");
		result = "SELECT sum(conteggio_concessioni) as conteggio_concessioni FROM ( " +
			buildQuery1() +
			" UNION ALL " +
			buildQuery2() +
			") TAB1 ";
		break;
	    case COUNT_DISTINCT_CONC_ID:
		log.debug("buildQuery# Query di COUNT DISTINCT CONC ID.....");
		result = "SELECT count(distinct (CONC_ID)) as conteggio_concessioni  FROM ( " +
			buildQuery1() +
			" UNION ALL " +
			buildQuery2() +
			") TAB1 ";
		break;
	    case SELECT_GROUP_BY_CONC_ID:
		log.debug("buildQuery# Query di SELECT GROUP BY CONC ID.....");
		result = "SELECT CONC_ID,IDCOMUNE  FROM ( " +
			buildQuery1() +
			" UNION ALL " +
			buildQuery2() +
			") TAB1 "/**
				  * + orderBy()
				  **/
			+
			" GROUP BY CONC_ID,IDCOMUNE";
		break;
	    case SELECT_WHERE_CONC_ID:
		log.debug("buildQuery# Query di SELECT WHERE CONC ID.....");
		result = "SELECT * FROM ( " +
			buildQuery1() +
			" UNION " +
			buildQuery2() +
			" )  TAB1 " +
			" ORDER BY CONC_ID DESC , CONC_DATARILASCIO DESC";
		break;
	    case SELECT:
		log.debug("buildQuery# Query di SELECT.....");
		result = "SELECT * FROM ( " + buildQuery1() + " UNION " + buildQuery2() + " )  TAB1 " + orderBy();
		break;
	    case PENTAHO_EXP:
		log.debug("buildQuery# Query di SELECT.....");
		result = "SELECT * FROM ( " + buildQuery1() + " UNION " + buildQuery2() + " )  TAB1 ";
		break;
	    default:
		break;
	}
	if (tipoQueryHelperEnum != TipoQueryHelperEnum.COUNT || tipoQueryHelperEnum != TipoQueryHelperEnum.COUNT_DISTINCT_CONC_ID) {
	    String titolareconcessione = applyConcatFunction("' '", new String[] { "TITOLARE.NOMINATIVO", "TITOLARE.NOME" }) + " AS CONC_TITOLARE";
	    log.debug("buildQuery# Sostituisco la funzione di concat [titolare concessione] per il dialetto {} : {}",
		    new Object[] { _dialetto, titolareconcessione });
	    result = StringUtils.replace(result, "#TITOLARE_CONCESSIONE#", titolareconcessione);
	    //CONCAT_WS('',IST_RICHIEDENTE.NOMINATIVO,' ',IST_RICHIEDENTE.NOME) AS IST_NOMINATIVO,
	    String istanzarichiedente = applyConcatFunction("' '", new String[] { "IST_RICHIEDENTE.NOMINATIVO", "IST_RICHIEDENTE.NOME" }) +
		    " AS IST_NOMINATIVO ";
	    log.debug("buildQuery# Sostituisco la funzione di concat [Richiedente istanza ] per il dialetto {} : {}",
		    new Object[] { _dialetto, istanzarichiedente });
	    result = StringUtils.replace(result, "#RICHIEDENTE_ISTANZA#", istanzarichiedente);
	    String istanzaaziendarichiedente = applyConcatFunction("' '", new String[] { "IST_AZIENDA.NOMINATIVO", "IST_AZIENDA.NOME" }) +
		    " AS IST_NOMINATIVOAZIENDA ";
	    log.debug("buildQuery# Sostituisco la funzione di concat [Richiedente istanza azienda ] per il dialetto {} : {}",
		    new Object[] { _dialetto, istanzaaziendarichiedente });
	    result = StringUtils.replace(result, "#AZIENDA_ISTANZA#", istanzaaziendarichiedente);
	    //IFNULL(AUTORIZZAZIONI.DATA_CESSAZIONE,STR_TO_DATE('31/12/9999','%D/%M/%Y')) AS DATA_STORICO
	    String stringToDate = stringToDate_DDMMYYYY("31/12/9999", _dialetto).toString();
	    String data_storico = applySimpleNVLFunction("AUTORIZZAZIONI.DATA_CESSAZIONE", stringToDate).toString();
	    log.debug("buildQuery# Sostituisco la funzione di NLV e to_date per data cessazione per il dialetto {} : {}",
		    new Object[] { _dialetto, data_storico });
	    result = StringUtils.replace(result, "#_DATA_STORICO_#", data_storico);
	    //
	    String occupanteconcessione = applyConcatFunction("' '", new String[] { "OCCUPANTE.NOMINATIVO", "OCCUPANTE.NOME" }) +
		    " AS CONC_OCCUPANTE";
	    log.debug("buildQuery# Sostituisco la funzione di concat [occupante della concessione] per il dialetto {} : {}",
		    new Object[] { _dialetto, occupanteconcessione });
	    result = StringUtils.replace(result, "#OCCUPANTE_CONCESSIONE#", occupanteconcessione);
	}
	return result;
    }

    private static final Logger log = LoggerFactory.getLogger(QueryIstanzeHelper.class);
    private int position = 0;
    private String countQuery = "select count(*) as conteggio_concessioni ";
    // Effettua una ricerca distinc sul campo conc_id. La distinc verrà fatta sul risultato della ricerca
    // in union delle tabelle AUTORIZZAZIONI e AUTORIZZAZIONI_SUBENTRI
    private String selectQueryDistinctConcId1 = " select AUTORIZZAZIONI.ID AS CONC_ID ";
    private String selectQueryDistinctConcId2 = " select AUTORIZZAZIONI_SUBENTRI.FK_IDAUT_ATTUALE AS CONC_ID ";
    private String selectPentahoExpQuery_1 = "select AUTORIZZAZIONI.IDCOMUNE,'" +
	    ORMHelper.getToken() +
	    "',AUTORIZZAZIONI.ID,AUTORIZZAZIONI.AUTORIZCOMUNE, " +
	    "AUTORIZZAZIONI.AUTORIZDATA ";
    private String selectPentahoExpQuery_2 = "select AUTORIZZAZIONI_SUBENTRI.IDCOMUNE,'" +
	    ORMHelper.getToken() +
	    "',AUTORIZZAZIONI_SUBENTRI.ID,AUTORIZZAZIONI_SUBENTRI.AUTORIZCOMUNE, " +
	    "AUTORIZZAZIONI_SUBENTRI.AUTORIZDATA ";
    private String selectQuery_1 = "SELECT 'ATTUALE' AS ORIGINE, 99999999 AS PROGRESSIVO,AUTORIZZAZIONI.AUTORIZRESPONSABILE AS CONC_AUTORIZRESPONSABILE," +
	    "AUTORIZZAZIONI.IDCOMUNE AS IDCOMUNE," +
	    "AUTORIZZAZIONI.FK_CAUSALE_ACQUISIZIONE AS ICONC_CODICECAUSALE," //
	    /* Funzione?*/
	    //+ "IFNULL(AUTORIZZAZIONI.DATA_CESSAZIONE,STR_TO_DATE('31/12/9999','%D/%M/%Y')) AS DATA_STORICO,"
	    +
	    "#_DATA_STORICO_#  AS DATA_STORICO," +
	    "ISTANZE.SOFTWARE AS SOFTWARE," +
	    "ISTANZE.CODICEISTANZA AS IST_CODICEISTANZA," +
	    "ISTANZE.NUMEROISTANZA AS IST_NUMEROISTANZA," +
	    "ISTANZE.DATA AS DATA_ISTANZA," +
	    "ISTANZE.CODICERICHIEDENTE AS IST_CODICERICHIEDENTE," +
	    "AUTORIZZAZIONI.ID AS CONC_ID," +
	    "AUTORIZZAZIONI.AUTORIZNUMERO AS CONC_NUMERO," +
	    "AUTORIZZAZIONI.AUTORIZDATA AS CONC_DATAVALIDITA," +
	    "AUTORIZZAZIONI.AUTORIZDATA AS DATAVALIDITA," +
	    "AUTORIZZAZIONI.DATA_RILASCIO AS CONC_DATARILASCIO," +
	    "AUTORIZZAZIONI.DATA_RILASCIO AS DATARILASCIO," +
	    "AUTORIZZAZIONI.DATASCADENZA AS CONC_DATASCADENZA," +
	    "AUTORIZZAZIONI.DATA_CESSAZIONE AS CONC_DATA_CESSAZIONE," +
	    "AUTORIZZAZIONI_CONCESSIONI.FK_TIPOCONCESSIONE AS CONC_CODICETIPO," +
	    "CONCESSIONITIPI.DESCRIZIONE AS CONC_TIPO," +
	    "AUTORIZZAZIONI_CONCESSIONI.STAGIONALEA AS CONC_STAGIONALEA," +
	    "AUTORIZZAZIONI_CONCESSIONI.STAGIONALEDA AS CONC_STAGIONALEDA," +
	    "AUTORIZZAZIONI.FLAG_ATTIVA AS CONC_ATTIVA," +
	    "AUTORIZZAZIONI.FK_CODICEANAGRAFE AS CONC_CODICETITOLARE," +
	    "AUTORIZZAZIONI.FKIDREGISTRO AS CONC_FKIDREGISTRO," +
	    "CONC_REGISTRO.TR_DESCRIZIONE AS CONC_REGISTRO," +
	    "#TITOLARE_CONCESSIONE# ,"
	    ///*Funzione*/+ "CONCAT_WS('',TITOLARE.NOMINATIVO,' ',TITOLARE.NOME) AS CONC_TITOLARE,"
	    +
	    "TITOLARE.CODICEFISCALE AS CONC_TIT_CF," +
	    "TITOLARE.PARTITAIVA AS CONC_TIT_PIVA," +
	    "TITOLARE.TIPOANAGRAFE AS CONC_TIT_TIPOANAGRAFE," +
	    "AUT_COLLEGATA.AUTORIZNUMERO AS AUT_NUMERO," +
	    "AUT_COLLEGATA.AUTORIZDATA AS AUT_DATA," +
	    "AUT_COLLEGATA.AUTORIZDATA AS AUTORIZDATA," +
	    "AUT_COLLEGATA.FKIDREGISTRO AS AUT_CODREGISTRO," +
	    "TIPOLOGIAREGISTRI.TR_DESCRIZIONE AS AUT_REGISTRO," //
	    +
	    "#RICHIEDENTE_ISTANZA# ,"
	    ///* Funzione*/+ "CONCAT_WS('',IST_RICHIEDENTE.NOMINATIVO,' ',IST_RICHIEDENTE.NOME) AS IST_NOMINATIVO,"
	    +
	    "IST_RICHIEDENTE.TIPOANAGRAFE AS IST_RIC_TIPOANAGRAFE," +
	    "#AZIENDA_ISTANZA# ,"
	    ///* Funzione */+ "CONCAT_WS('',IST_AZIENDA.NOMINATIVO,' ',IST_AZIENDA.NOME) AS IST_NOMINATIVOAZIENDA,"
	    +
	    "IST_AZIENDA.TIPOANAGRAFE AS IST_AZI_TIPOANAGRAFE," +
	    "IST_RICHIEDENTE.CODICEFISCALE AS IST_CODICEFISCALE," +
	    "IST_RICHIEDENTE.PARTITAIVA AS IST_PARTITAIVA," +
	    "CAUS_ATTUALE.DESCRIZIONE AS ICONC_CAUSALE," +
	    "AUTORIZZAZIONI.FK_CAUSALE_CESSAZIONE AS ICONC_CODICECAUSALESTORICO," +
	    "CAUS_STORICO.DESCRIZIONE AS ICONC_CAUSALESTORICO," +
	    "AUTORIZZAZIONI_CONCESSIONI.FK_CODICEMERCATO AS CONC_IDMERCATO," +
	    "MERCATI.DESCRIZIONE AS CONC_MERCATO," +
	    "MERCATI.ATTIVO AS MERC_ATTIVO," +
	    "AUTORIZZAZIONI_CONCESSIONI.FK_IDPOSTEGGIO AS CONC_IDPOSTEGGIO," +
	    "MERCATI_D.CODICEPOSTEGGIO AS CONC_POSTEGGIO," +
	    "MERCATI_D.LARGHEZZA AS CONC_POS_LARGHEZZA," +
	    "MERCATI_D.LUNGHEZZA AS CONC_POS_LUNGHEZZA," +
	    "MERCATI_D.SUPERFICIE AS CONC_POS_SUPERFICIE," +
	    "MERCATI_D.FKCODICETIPOSPAZIO AS POST_IDTIPOSPAZIO," +
	    "POSTEGGITIPOSPAZIO.TIPOSPAZIO AS POST_TIPOSPAZIO," +
	    "AUTORIZZAZIONI_CONCESSIONI.FK_IDMERCATIUSO AS CONC_IDMERCATIUSO," +
	    "MERCATI_USO.DESCRIZIONE AS CONC_DESCRIZIONEUSO," +
	    "MERCATI_USO.PESO AS PESO_MERCATO," +
	    "MERCATI_USO.FKCODICEUSO AS MERC_IDUSO," +
	    "MERCATI_D.FKCODICESTRADARIO AS CONC_POS_CODICESTRADARIO," +
	    "MERCATI_D.NOTE AS CONC_POS_NOTE, " +
	    "VW_ENTILOCALI.COMUNE AS CONC_ENTERILASCIO, " +
	    "STRADARIO.DESCRIZIONE AS STRADARIO_DESCRIZIONE, "
	    // Codice comune
	    +
	    "ISTANZE.CODICECOMUNE AS IST_CODICECOMUNE, " +
	    "AUTORIZZAZIONI.AUTORIZCOMUNE AS CONC_CODICECOMUNE, " +
	    "AUTORIZZAZIONI.CODICEOCCUPANTE AS CONC_CODICEOCCUPANTE," +
	    "#OCCUPANTE_CONCESSIONE# ," +
	    "OCCUPANTE.CODICEFISCALE AS CONC_OCC_CF," +
	    "OCCUPANTE.PARTITAIVA AS CONC_OCC_PIVA," +
	    "OCCUPANTE.TIPOANAGRAFE AS CONC_OCC_TIPOANAGRAFE ";
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////// Sezione FROM query 1 ////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    String fromQuery_1 = " FROM AUTORIZZAZIONI INNER JOIN VW_ENTILOCALI ON AUTORIZZAZIONI.AUTORIZCOMUNE = VW_ENTILOCALI.CODICECOMUNE " +
	    "INNER JOIN ISTANZE ON AUTORIZZAZIONI.IDCOMUNE = ISTANZE.IDCOMUNE AND AUTORIZZAZIONI.FKIDISTANZA = ISTANZE.CODICEISTANZA " +
	    "LEFT JOIN ANAGRAFE IST_AZIENDA ON ISTANZE.IDCOMUNE = IST_AZIENDA.IDCOMUNE AND ISTANZE.CODICETITOLARELEGALE = IST_AZIENDA.CODICEANAGRAFE " +
	    "INNER JOIN ANAGRAFE IST_RICHIEDENTE ON ISTANZE.IDCOMUNE = IST_RICHIEDENTE.IDCOMUNE AND ISTANZE.CODICERICHIEDENTE = IST_RICHIEDENTE.CODICEANAGRAFE " +
	    "INNER JOIN TIPOLOGIAREGISTRI CONC_REGISTRO ON  AUTORIZZAZIONI.IDCOMUNE = CONC_REGISTRO.IDCOMUNE AND AUTORIZZAZIONI.FKIDREGISTRO = CONC_REGISTRO.TR_ID " +
	    "INNER JOIN ANAGRAFE TITOLARE ON AUTORIZZAZIONI.IDCOMUNE = TITOLARE.IDCOMUNE AND AUTORIZZAZIONI.FK_CODICEANAGRAFE = TITOLARE.CODICEANAGRAFE " +
	    "LEFT JOIN CONCESSIONICAUSALI CAUS_ATTUALE ON AUTORIZZAZIONI.IDCOMUNE = CAUS_ATTUALE.IDCOMUNE AND AUTORIZZAZIONI.FK_CAUSALE_ACQUISIZIONE = CAUS_ATTUALE.CODICECAUSALE " +
	    "INNER JOIN AUTORIZZAZIONI_CONCESSIONI ON  AUTORIZZAZIONI.IDCOMUNE = AUTORIZZAZIONI_CONCESSIONI.IDCOMUNE AND AUTORIZZAZIONI.ID = AUTORIZZAZIONI_CONCESSIONI.FK_IDAUT_ATTUALE " +
	    "INNER JOIN MERCATI ON AUTORIZZAZIONI_CONCESSIONI.IDCOMUNE = MERCATI.IDCOMUNE AND AUTORIZZAZIONI_CONCESSIONI.FK_CODICEMERCATO = MERCATI.CODICEMERCATO " +
	    "INNER JOIN MERCATI_D ON AUTORIZZAZIONI_CONCESSIONI.IDCOMUNE = MERCATI_D.IDCOMUNE AND AUTORIZZAZIONI_CONCESSIONI.FK_IDPOSTEGGIO = MERCATI_D.IDPOSTEGGIO " +
	    "INNER JOIN MERCATI_USO ON AUTORIZZAZIONI_CONCESSIONI.IDCOMUNE = MERCATI_USO.IDCOMUNE AND AUTORIZZAZIONI_CONCESSIONI.FK_IDMERCATIUSO = MERCATI_USO.ID " +
	    "LEFT OUTER JOIN POSTEGGITIPOSPAZIO ON MERCATI_D.IDCOMUNE = POSTEGGITIPOSPAZIO.IDCOMUNE AND MERCATI_D.FKCODICETIPOSPAZIO = POSTEGGITIPOSPAZIO.CODICE " +
	    "INNER JOIN CONCESSIONITIPI ON AUTORIZZAZIONI_CONCESSIONI.FK_TIPOCONCESSIONE = CONCESSIONITIPI.TIPOCONCESSIONE " +
	    "LEFT JOIN CONCESSIONICAUSALI CAUS_STORICO ON AUTORIZZAZIONI.IDCOMUNE = CAUS_STORICO.IDCOMUNE AND AUTORIZZAZIONI.FK_CAUSALE_CESSAZIONE = CAUS_STORICO.CODICECAUSALE " +
	    "LEFT JOIN AUTORIZZAZIONI AUT_COLLEGATA ON  AUTORIZZAZIONI_CONCESSIONI.IDCOMUNE = AUT_COLLEGATA.IDCOMUNE AND AUTORIZZAZIONI_CONCESSIONI.FK_IDAUT_COLLEGATA = AUT_COLLEGATA.ID " +
	    "LEFT JOIN TIPOLOGIAREGISTRI ON AUT_COLLEGATA.IDCOMUNE = TIPOLOGIAREGISTRI.IDCOMUNE AND AUT_COLLEGATA.FKIDREGISTRO = TIPOLOGIAREGISTRI.TR_ID " +
	    "LEFT JOIN ISTANZESTRADARIO ON ISTANZE.IDCOMUNE = ISTANZESTRADARIO.IDCOMUNE AND ISTANZE.CODICEISTANZA = ISTANZESTRADARIO.CODICEISTANZA AND ISTANZESTRADARIO.PRIMARIO = 1 " +
	    "LEFT JOIN STRADARIO ON ISTANZESTRADARIO.IDCOMUNE = STRADARIO.IDCOMUNE AND ISTANZESTRADARIO.CODICESTRADARIO = STRADARIO.CODICESTRADARIO " +
	    "LEFT JOIN ANAGRAFE OCCUPANTE ON AUTORIZZAZIONI.IDCOMUNE = OCCUPANTE.IDCOMUNE AND AUTORIZZAZIONI.CODICEOCCUPANTE = OCCUPANTE.CODICEANAGRAFE ";
    //// QUERY 2 ///////////////////////// 
    private String selectQuery_2 = "SELECT 'STORICO' AS ORIGINE, AUTORIZZAZIONI_SUBENTRI.ID AS PROGRESSIVO," +
	    "AUTORIZZAZIONI_SUBENTRI.AUTORIZRESPONSABILE AS CONC_AUTORIZRESPONSABILE," +
	    "AUTORIZZAZIONI_SUBENTRI.IDCOMUNE AS IDCOMUNE," +
	    "AUTORIZZAZIONI_SUBENTRI.FK_CAUSALE_ACQUISIZIONE AS ICONC_CODICECAUSALE," +
	    "AUTORIZZAZIONI_SUBENTRI.DATA_CESSAZIONE AS DATA_STORICO," +
	    "ISTANZE.SOFTWARE AS SOFTWARE," +
	    "ISTANZE.CODICEISTANZA AS IST_CODICEISTANZA," +
	    "ISTANZE.NUMEROISTANZA AS IST_NUMEROISTANZA," +
	    "ISTANZE.DATA AS DATA_ISTANZA," +
	    "ISTANZE.CODICERICHIEDENTE AS IST_CODICERICHIEDENTE," +
	    "AUTORIZZAZIONI_SUBENTRI.FK_IDAUT_ATTUALE AS CONC_ID," +
	    "AUTORIZZAZIONI_SUBENTRI.AUTORIZNUMERO AS CONC_NUMERO," +
	    "AUTORIZZAZIONI_SUBENTRI.AUTORIZDATA AS CONC_DATAVALIDITA," +
	    "AUTORIZZAZIONI_SUBENTRI.AUTORIZDATA AS DATAVALIDITA," +
	    "AUTORIZZAZIONI_SUBENTRI.DATA_RILASCIO AS CONC_DATARILASCIO," +
	    "AUTORIZZAZIONI_SUBENTRI.DATA_RILASCIO AS DATARILASCIO," +
	    "AUTORIZZAZIONI_SUBENTRI.DATASCADENZA AS CONC_DATASCADENZA," +
	    "AUTORIZZAZIONI_SUBENTRI.DATA_CESSAZIONE AS CONC_DATACESSAZIONE," +
	    "AUTORIZZAZIONI_SUBENTRI_CONC.FK_TIPOCONCESSIONE AS CONC_CODICETIPO," +
	    "CONCESSIONITIPI.DESCRIZIONE AS CONC_TIPO," +
	    "AUTORIZZAZIONI_SUBENTRI_CONC.STAGIONALEA AS CONC_STAGIONALEA," +
	    "AUTORIZZAZIONI_SUBENTRI_CONC.STAGIONALEDA AS CONC_STAGIONALEDA," +
	    "0 AS CONC_ATTIVA," +
	    "AUTORIZZAZIONI_SUBENTRI.FK_CODICEANAGRAFE AS CONC_CODICETITOLARE," +
	    "AUTORIZZAZIONI_SUBENTRI.FKIDREGISTRO AS CONC_FKIDREGISTRO," +
	    "CONC_REGISTRO.TR_DESCRIZIONE AS CONC_REGISTRO," +
	    "#TITOLARE_CONCESSIONE# ,"
	    //	    /*Funzione*/+ "CONCAT_WS('',TITOLARE.NOMINATIVO,' ',TITOLARE.NOME) AS CONC_TITOLARE," 
	    +
	    "TITOLARE.CODICEFISCALE AS CONC_TIT_CF," +
	    "TITOLARE.PARTITAIVA AS CONC_TIT_PIVA," +
	    "TITOLARE.TIPOANAGRAFE AS CONC_TIT_TIPOANAGRAFE," +
	    "AUT_COLLEGATA.AUTORIZNUMERO AS AUT_NUMERO," +
	    "AUT_COLLEGATA.AUTORIZDATA AS AUT_DATA," +
	    "AUT_COLLEGATA.AUTORIZDATA AS AUTORIZDATA," +
	    "AUT_COLLEGATA.FKIDREGISTRO AS AUT_CODREGISTRO,"
	    //	    //. autorizzazioni.DATA_CESSAZIONE (AGGIUNTA) NON DOVREBBE SERVIRE
	    //	    + "AUT_COLLEGATA.DATA_CESSAZIONE AS DATACESSAZIONE_AUT,"
	    +
	    "TIPOLOGIAREGISTRI.TR_DESCRIZIONE AS AUT_REGISTRO," +
	    "#RICHIEDENTE_ISTANZA# ,"
	    //* funzione */+ "CONCAT_WS('',IST_RICHIEDENTE.NOMINATIVO,' ',IST_RICHIEDENTE.NOME) AS IST_NOMINATIVO,"
	    +
	    "IST_RICHIEDENTE.TIPOANAGRAFE AS IST_RIC_TIPOANAGRAFE," +
	    "#AZIENDA_ISTANZA# ,"
	    //* funzione */ + "CONCAT_WS('',IST_AZIENDA.NOMINATIVO,' ',IST_AZIENDA.NOME) AS IST_NOMINATIVOAZIENDA,"
	    +
	    "IST_AZIENDA.TIPOANAGRAFE AS IST_AZI_TIPOANAGRAFE," +
	    "IST_RICHIEDENTE.CODICEFISCALE AS IST_CODICEFISCALE," +
	    "IST_RICHIEDENTE.PARTITAIVA AS IST_PARTITAIVA," +
	    "CAUS_ATTUALE.DESCRIZIONE AS ICONC_CAUSALE," +
	    "AUTORIZZAZIONI_SUBENTRI.FK_CAUSALE_CESSAZIONE AS ICONC_CODICECAUSALESTORICO," +
	    "CAUS_STORICO.DESCRIZIONE AS ICONC_CAUSALESTORICO," +
	    "AUTORIZZAZIONI_SUBENTRI_CONC.FK_CODICEMERCATO AS CONC_IDMERCATO," +
	    "MERCATI.DESCRIZIONE AS CONC_MERCATO," +
	    "MERCATI.ATTIVO AS MERC_ATTIVO," +
	    "AUTORIZZAZIONI_SUBENTRI_CONC.FK_IDPOSTEGGIO AS CONC_IDPOSTEGGIO," +
	    "MERCATI_D.CODICEPOSTEGGIO AS CONC_POSTEGGIO," +
	    "MERCATI_D.LARGHEZZA AS CONC_POS_LARGHEZZA," +
	    "MERCATI_D.LUNGHEZZA AS CONC_POS_LUNGHEZZA," +
	    "MERCATI_D.SUPERFICIE AS CONC_POS_SUPERFICIE," +
	    "MERCATI_D.FKCODICETIPOSPAZIO AS POST_IDTIPOSPAZIO," +
	    "POSTEGGITIPOSPAZIO.TIPOSPAZIO AS POST_TIPOSPAZIO," +
	    "AUTORIZZAZIONI_SUBENTRI_CONC.FK_IDMERCATIUSO AS CONC_IDMERCATIUSO," +
	    "MERCATI_USO.DESCRIZIONE AS CONC_DESCRIZIONEUSO," +
	    "MERCATI_USO.PESO AS PESO_MERCATO," +
	    "MERCATI_USO.FKCODICEUSO AS MERC_IDUSO," +
	    "MERCATI_D.FKCODICESTRADARIO AS CONC_POS_CODICESTRADARIO," +
	    "MERCATI_D.NOTE AS CONC_POS_NOTE, " +
	    "VW_ENTILOCALI.COMUNE AS CONC_ENTERILASCIO, " +
	    "STRADARIO.DESCRIZIONE AS STRADARIO_DESCRIZIONE, "
	    // Codice comune
	    +
	    "ISTANZE.CODICECOMUNE AS IST_CODICECOMUNE, " +
	    "AUTORIZZAZIONI_SUBENTRI.AUTORIZCOMUNE AS CONC_CODICECOMUNE, " +
	    "AUTORIZZAZIONI_SUBENTRI.CODICEOCCUPANTE AS CONC_CODICEOCCUPANTE," +
	    "#OCCUPANTE_CONCESSIONE# ," +
	    "OCCUPANTE.CODICEFISCALE AS CONC_OCC_CF," +
	    "OCCUPANTE.PARTITAIVA AS CONC_OCC_PIVA," +
	    "OCCUPANTE.TIPOANAGRAFE AS CONC_OCC_TIPOANAGRAFE ";
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////// Sezione FROM query 2 ////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    String fromQuery_2 = "FROM AUTORIZZAZIONI_SUBENTRI INNER JOIN VW_ENTILOCALI ON VW_ENTILOCALI.CODICECOMUNE = AUTORIZZAZIONI_SUBENTRI.AUTORIZCOMUNE " +
	    "INNER JOIN ISTANZE ON AUTORIZZAZIONI_SUBENTRI.IDCOMUNE = ISTANZE.IDCOMUNE AND AUTORIZZAZIONI_SUBENTRI.FKIDISTANZA = ISTANZE.CODICEISTANZA " +
	    "LEFT JOIN ANAGRAFE IST_AZIENDA ON ISTANZE.IDCOMUNE = IST_AZIENDA.IDCOMUNE AND ISTANZE.CODICETITOLARELEGALE = IST_AZIENDA.CODICEANAGRAFE " +
	    "INNER JOIN ANAGRAFE IST_RICHIEDENTE ON ISTANZE.IDCOMUNE = IST_RICHIEDENTE.IDCOMUNE AND ISTANZE.CODICERICHIEDENTE = IST_RICHIEDENTE.CODICEANAGRAFE " +
	    "LEFT JOIN CONCESSIONICAUSALI CAUS_STORICO ON AUTORIZZAZIONI_SUBENTRI.IDCOMUNE = CAUS_STORICO.IDCOMUNE AND AUTORIZZAZIONI_SUBENTRI.FK_CAUSALE_CESSAZIONE = CAUS_STORICO.CODICECAUSALE " +
	    "LEFT JOIN CONCESSIONICAUSALI CAUS_ATTUALE ON AUTORIZZAZIONI_SUBENTRI.IDCOMUNE = CAUS_ATTUALE.IDCOMUNE AND AUTORIZZAZIONI_SUBENTRI.FK_CAUSALE_ACQUISIZIONE = CAUS_ATTUALE.CODICECAUSALE " +
	    "INNER JOIN ANAGRAFE TITOLARE ON AUTORIZZAZIONI_SUBENTRI.IDCOMUNE = TITOLARE.IDCOMUNE AND AUTORIZZAZIONI_SUBENTRI.FK_CODICEANAGRAFE = TITOLARE.CODICEANAGRAFE " +
	    "INNER JOIN TIPOLOGIAREGISTRI CONC_REGISTRO ON AUTORIZZAZIONI_SUBENTRI.IDCOMUNE = CONC_REGISTRO.IDCOMUNE AND AUTORIZZAZIONI_SUBENTRI.FKIDREGISTRO = CONC_REGISTRO.TR_ID " +
	    "INNER JOIN AUTORIZZAZIONI_SUBENTRI_CONC ON AUTORIZZAZIONI_SUBENTRI.IDCOMUNE = AUTORIZZAZIONI_SUBENTRI_CONC.IDCOMUNE AND AUTORIZZAZIONI_SUBENTRI.ID = AUTORIZZAZIONI_SUBENTRI_CONC.FK_AUTSUB_ID " +
	    "INNER JOIN MERCATI_USO ON AUTORIZZAZIONI_SUBENTRI_CONC.IDCOMUNE = MERCATI_USO.IDCOMUNE AND AUTORIZZAZIONI_SUBENTRI_CONC.FK_IDMERCATIUSO = MERCATI_USO.ID " +
	    "INNER JOIN MERCATI_D ON AUTORIZZAZIONI_SUBENTRI_CONC.IDCOMUNE = MERCATI_D.IDCOMUNE AND AUTORIZZAZIONI_SUBENTRI_CONC.FK_IDPOSTEGGIO = MERCATI_D.IDPOSTEGGIO " +
	    "LEFT JOIN POSTEGGITIPOSPAZIO ON MERCATI_D.IDCOMUNE = POSTEGGITIPOSPAZIO.IDCOMUNE AND MERCATI_D.FKCODICETIPOSPAZIO = POSTEGGITIPOSPAZIO.CODICE " +
	    "INNER JOIN MERCATI ON AUTORIZZAZIONI_SUBENTRI_CONC.IDCOMUNE = MERCATI.IDCOMUNE AND AUTORIZZAZIONI_SUBENTRI_CONC.FK_CODICEMERCATO = MERCATI.CODICEMERCATO " +
	    "INNER JOIN CONCESSIONITIPI ON CONCESSIONITIPI.TIPOCONCESSIONE = AUTORIZZAZIONI_SUBENTRI_CONC.FK_TIPOCONCESSIONE " +
	    "LEFT JOIN AUTORIZZAZIONI_SUBENTRI AUT_COLLEGATA ON AUTORIZZAZIONI_SUBENTRI_CONC.IDCOMUNE = AUT_COLLEGATA.IDCOMUNE AND AUTORIZZAZIONI_SUBENTRI_CONC.FK_IDAUTSUB_AUTCOLL = AUT_COLLEGATA.ID " +
	    "LEFT JOIN TIPOLOGIAREGISTRI ON AUT_COLLEGATA.IDCOMUNE = TIPOLOGIAREGISTRI.IDCOMUNE AND AUT_COLLEGATA.FKIDREGISTRO = TIPOLOGIAREGISTRI.TR_ID " +
	    "LEFT OUTER JOIN ISTANZESTRADARIO ON ISTANZE.IDCOMUNE = ISTANZESTRADARIO.IDCOMUNE AND ISTANZE.CODICEISTANZA = ISTANZESTRADARIO.CODICEISTANZA AND ISTANZESTRADARIO.PRIMARIO = 1 " +
	    "LEFT JOIN STRADARIO ON ISTANZESTRADARIO.IDCOMUNE = STRADARIO.IDCOMUNE AND ISTANZESTRADARIO.CODICESTRADARIO = STRADARIO.CODICESTRADARIO " +
	    "LEFT JOIN ANAGRAFE OCCUPANTE ON AUTORIZZAZIONI_SUBENTRI.IDCOMUNE = OCCUPANTE.IDCOMUNE AND AUTORIZZAZIONI_SUBENTRI.CODICEOCCUPANTE = OCCUPANTE.CODICEANAGRAFE ";
    private String whereQuery_1 = " where ";
    private String whereQuery_2 = " where ";
}
