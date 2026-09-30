package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.mutable.MutableInt;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.Dyn2CampiDAO;
import it.gruppoinit.pal.gp.core.dao.StatiistanzaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.dao.helper.TipoQueryHelperEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class QueryIstanzeStradarioHelper extends BaseQueryHelper {

    private static final Logger logger = LoggerFactory.getLogger(QueryIstanzeStradarioHelper.class);
    private IstanzeFilter filter;
    private AlberoprocDAO alberoprocDAO;
    private StatiistanzaDAO statiistanzaDAO;
    private ComuniassociatiService comuniassociatiService;
    private TipoQueryHelperEnum tipoQueryHelperEnum;
    private Integer firstResult;
    private Integer maxResults;
    private String countQuery = "select count(*) as conteggio ";
    private String whereQuery = " where istanze.idcomune=?";

    public QueryIstanzeStradarioHelper(IstanzeFilter istanzeFilter, SessionFactoryImplementor sessimpl, AlberoprocDAO alberoprocDAO,
	    StatiistanzaDAO statiistanzaDAO, Dyn2CampiDAO dyn2CampiDAO, ComuniassociatiService comuniassociatiService,
	    TipoQueryHelperEnum tipoQueryHelperEnum, Integer firstResult, Integer maxResults) {

	this.filter = istanzeFilter;
	logger.debug("QueryIstanzeStradarioHelper: recupero il dialetto della SessionFactoryImplementor");
	Dialect dialetto = sessimpl.getDialect();
	logger.debug("QueryIstanzeStradarioHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	logger.debug("QueryIstanzeStradarioHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	logger.debug("QueryIstanzeStradarioHelper: schemaName={}", schemaName);
	this.alberoprocDAO = alberoprocDAO;
	this.statiistanzaDAO = statiistanzaDAO;
	this.dyn2CampiDAO = dyn2CampiDAO;
	this.comuniassociatiService = comuniassociatiService;
	this.tipoQueryHelperEnum = tipoQueryHelperEnum;
	this.firstResult = firstResult;
	this.maxResults = maxResults;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	int position = 0;
	String debugParam = "param {}={}";
	logger.debug(debugParam, position, ORMHelper.getIdcomune());
	q.setString(position, ORMHelper.getIdcomune()); // IDCOMUNE
	for (ParameterHelper parameter : parameters) {
	    logger.debug(debugParam, parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idComune", Hibernate.STRING);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("codiceComune", Hibernate.STRING);
	q.addScalar("comune", Hibernate.STRING);
	q.addScalar("codiceIstat", Hibernate.STRING);
	q.addScalar("codiceIstanza", Hibernate.INTEGER);
	q.addScalar("numeroIstanza", Hibernate.STRING);
	q.addScalar("uuidIstanzeStradario", Hibernate.STRING);
	q.addScalar("codiceViario", Hibernate.STRING);
	q.addScalar("civico", Hibernate.STRING);
	q.addScalar("km", Hibernate.STRING);
	q.addScalar("primario", Hibernate.INTEGER);
	q.addScalar("latitudine", Hibernate.STRING);
	q.addScalar("longitudine", Hibernate.STRING);
    }

    @Override
    public String buildQuery() {

	String result = "";
	int position = 1;
	switch (tipoQueryHelperEnum) {
	    case COUNT: {
		result = countQuery;
		break;
	    }
	    case SELECT: {
		if (!DialettoEnum.ORACLE.equals(_dialetto)) {
		    result = "select " + this.getSelectQuery();
		} else {
		    // Necessario per la paginazione oracle, in alcuni casi (ricerca per campi dinamici) la paginazione standard
		    // applicata da hibernate "ROWNUM < x" non funzionava.
		    result = "SELECT * FROM ( select ROW_NUMBER() AS PAGINAZIONE, " + this.getSelectQuery();
		}
		break;
	    }
	    default: {
		break;
	    }
	}
	result += this.getFromQuery() + whereQuery;
	// Software
	if (filter.getModulo() != null && StringUtils.isNotBlank(filter.getModulo().getCodice())
		&& !StringUtils.defaultString(filter.getModulo().getCodice()).equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
	    result += " and istanze.software=?";
	    parameters.add(new ParameterHelper(position, filter.getModulo().getCodice(), new StringType()));
	    position++;
	}
	//Comune
	if (EntityUtils.getNestedProperty(filter, "comune.codicecomune") != null) {
	    if (StringUtils.isNotBlank(filter.getComune().getCodicecomune())) {
		if (filter.getComune().getCodicecomune().indexOf(",") > 0) {
		    String[] comuni = filter.getComune().getCodicecomune().split(",");
		    String qm = StringUtils.repeat("?,", comuni.length);
		    qm = qm.substring(0, qm.length() - 1);
		    result += " and istanze.codicecomune in (" + qm + ")";
		    for (String codicecomune : comuni) {
			parameters.add(new ParameterHelper(position, codicecomune, new StringType()));
			position++;
		    }
		} else {
		    result += " and istanze.codicecomune=?";
		    parameters.add(new ParameterHelper(position, filter.getComune().getCodicecomune(), new StringType()));
		    position++;
		}
	    }
	} else {
	    // Controllo se è un installazione multi comune, nel caso la chiamata alla query potrebbe essere stata fatta dal pannellino
	    // che su searchIstanze.jsp mostra le pratiche perventute dai vari front. In questo caso dovranno essere mostratate sulla lista
	    // solo le pratiche che appartengoni ai comuni per cui l'operatore loggato è responsabile.
	    //
	    // Filtro per codice comune, se è un installazione multi comune, l'operatore deve vedere solo le scadenze per i comuni 
	    // per cui è abilitato.
	    logger.debug("buildQuery# Controllo l' installazione con idcomune {} è multi comune", ORMHelper.getIdcomune());
	    boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	    if (isComuniAssociati) {
		logger.debug("buildQuery# E' un installazione multicomune, recupero i comuni configurati per l'opertaore");
		List<Responsabilicomuni> responsabilicomunis = comuniassociatiService.checkComuniAbilitatiPerResponsabile(false);
		if (responsabilicomunis != null && !responsabilicomunis.isEmpty()) {
		    String[] codicicomune = new String[responsabilicomunis.size()];
		    result += " and (istanze.codicecomune in  (";
		    StringBuilder comuniattiviQm = new StringBuilder("");
		    for (int i = 0; i < responsabilicomunis.size(); i++) {
			Responsabilicomuni responsabilicomunimuni = responsabilicomunis.get(i);
			codicicomune[i] = responsabilicomunimuni.getId().getCodicecomune();
			comuniattiviQm.append(",?");
			parameters.add(new ParameterHelper(position, codicicomune[i], new StringType()));
			position++;
		    }
		    result += comuniattiviQm.toString().replaceFirst(",", "") + "))";
		}
	    }
	}
	if (filter.getComune() != null) {
	    if (StringUtils.isNotBlank(filter.getComune().getCodiceistat())) {
		result += " and comuni.codiceistat=?";
		parameters.add(new ParameterHelper(position, filter.getComune().getCodiceistat(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getComune().getComune())) {
		result += " and comuni.comune=?";
		parameters.add(new ParameterHelper(position, filter.getComune().getComune(), new StringType()));
		position++;
	    }
	}
	//Numero istanza
	if (isStringNotEmptyOrWildCard(filter.getNumeroistanza())) {
	    if (filter.isRicercaNumPraticaConLike()) {
		result += " and lower(istanze.numeroistanza) like ?";
		parameters.add(new ParameterHelper(position, filter.getNumeroistanza().trim().toLowerCase() + "%", new StringType()));
		position++;
	    } else {
		result += " and lower(istanze.numeroistanza) = ?";
		parameters.add(new ParameterHelper(position, filter.getNumeroistanza().trim().toLowerCase(), new StringType()));
		position++;
	    }
	}
	//Codice istanza
	if (filter.getCodiceIstanza() != null) {
	    result += " and lower(istanze.codiceistanza) = ?";
	    parameters.add(new ParameterHelper(position, filter.getCodiceIstanza(), new IntegerType()));
	    position++;
	}
	//Codice pratica telematica
	if (isStringNotEmptyOrWildCard(filter.getCodicepraticatel())) {
	    result += " and lower(istanze.codicepraticatel) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getCodicepraticatel().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	//Amministrazione
	if (filter.getAmministrazioni() != null && filter.getAmministrazioni().getId() != null
		&& filter.getAmministrazioni().getId().getCodice() != null) {
	    result += " and istanze.codiceamministrazione = ?";
	    parameters.add(new ParameterHelper(position, filter.getAmministrazioni().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//Data presentazione: dal
	if (EntityUtils.getNestedProperty(filter, "dallaData") != null) {
	    result += " and istanze.data >= ?";
	    Calendar t = Calendar.getInstance();
	    t.setTime(filter.getDallaData());
	    t.set(Calendar.HOUR, 0);
	    t.set(Calendar.MINUTE, 0);
	    t.set(Calendar.SECOND, 0);
	    parameters.add(new ParameterHelper(position, t.getTime(), new TimestampType()));
	    position++;
	}
	//Data presentazione: al
	if (EntityUtils.getNestedProperty(filter, "allaData") != null) {
	    result += " and istanze.data <= ?";
	    Calendar t = Calendar.getInstance();
	    t.setTime(filter.getAllaData());
	    t.set(Calendar.HOUR, 23);
	    t.set(Calendar.MINUTE, 59);
	    t.set(Calendar.SECOND, 59);
	    parameters.add(new ParameterHelper(position, t.getTime(), new TimestampType()));
	    position++;
	}
	//Data di validità: dal
	if (EntityUtils.getNestedProperty(filter, "dallaDataValidita") != null) {
	    result += " and istanze.datavalidita >= ?";
	    parameters.add(new ParameterHelper(position, filter.getDallaDataValidita(), new DateType()));
	    position++;
	}
	//Data di validità: al
	if (EntityUtils.getNestedProperty(filter, "allaDataValidita") != null) {
	    result += " and istanze.datavalidita <= ?";
	    parameters.add(new ParameterHelper(position, filter.getAllaDataValidita(), new DateType()));
	    position++;
	}
	//Archivio pratiche
	if (EntityUtils.getNestedProperty(filter, "tipiarchivioistanza.id.codice") != null) {
	    result += " and istanze.tipoarchivio=?";
	    parameters.add(new ParameterHelper(position, filter.getTipiarchivioistanza().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//Posizione archivio
	if (isStringNotEmptyOrWildCard(filter.getPosizionearchivio())) {
	    result += " and lower(istanze.posizionearchivio) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getPosizionearchivio().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	//Tipologia istanza
	if (EntityUtils.getNestedProperty(filter, "tipologiaistanza.id.codice") != null) {
	    result += " and istanze.fkidtipologiaistanza=?";
	    parameters.add(new ParameterHelper(position, filter.getTipologiaistanza().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//Professionista
	if (EntityUtils.getNestedProperty(filter, "professionista.id.codice") != null) {
	    result += " and istanze.codiceprofessionista=?";
	    parameters.add(new ParameterHelper(position, filter.getProfessionista().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//Alberoproc: sc_codice
	if (EntityUtils.getNestedProperty(filter, "alberoproc.id.codice") != null) {
	    Alberoproc alberoproc = alberoprocDAO.findById(filter.getAlberoproc().getId());
	    result += " and alberoproc.sc_codice like ?";
	    parameters.add(new ParameterHelper(position, alberoproc.getScCodice() + "%", new StringType()));
	    position++;
	}
	//Alberoproc: anagrafe tributaria
	if (BooleanUtils.isTrue(filter.getChkexportanagrafetrib())) {
	    result += " and alberoproc.atrib_tipologiaintervento is not null ";
	}
	//Procedura
	if (EntityUtils.getNestedProperty(filter, "procedura.id.codice") != null) {
	    result += " and istanze.codiceprocedura = ?";
	    parameters.add(new ParameterHelper(position, filter.getProcedura().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//Lavori
	if (isStringNotEmptyOrWildCard(filter.getLavori())) {
	    result += " and lower(istanze.lavori) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getLavori().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	//Domicilio elettronico
	if (isStringNotEmptyOrWildCard(filter.getDomicilioElettronico())) {
	    result += " and lower(istanze.domicilio_elettronico) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getDomicilioElettronico().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	//Nome attività
	if (isStringNotEmptyOrWildCard(filter.getNomeattivita())) {
	    result += " and lower(istanze.nomeattivita) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getNomeattivita().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	//Note
	if (isStringNotEmptyOrWildCard(filter.getLavoriestesa())) {
	    result += " and lower(istanze.lavoriestesa) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getLavoriestesa().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	// Codice domanda STC
	if (isStringNotEmptyOrWildCard(filter.getCodicedomandastc()) && !filter.isCercasolodomandestc()) {
	    result += " and exists (select dstc.id_domandamitt from " +
		    SCHEMA_NAME +
		    "domandestc dstc where dstc.idcomune=istanze.idcomune and " +
		    "dstc.codiceistanza=istanze.codiceistanza and lower(dstc.id_domandamitt) like ?)";
	    parameters.add(new ParameterHelper(position, "%" + filter.getCodicedomandastc().trim().toLowerCase() + "%", new StringType()));
	    position++;
	} else {
	    if (filter.isCercasolodomandestc()) {
		result += " and exists (select dstc.id_domandamitt from " +
			SCHEMA_NAME +
			"domandestc dstc where dstc.idcomune=istanze.idcomune and dstc.codiceistanza=istanze.codiceistanza ";
		// Filtri aggiunti per cercare le istanze quando si recuperano dal pannello presente nella pagina "searchIstanze"
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (isStringNotEmptyOrWildCard(filter.getIdNodoStc())) {
		    result += " and dstc.id_nodo=?";
		    parameters.add(new ParameterHelper(position, filter.getIdNodoStc().trim(), new StringType()));
		    position++;
		}
		if (isStringNotEmptyOrWildCard(filter.getIdentemitt())) {
		    result += " and dstc.id_entemitt=?";
		    parameters.add(new ParameterHelper(position, filter.getIdentemitt().trim(), new StringType()));
		    position++;
		}
		if (isStringNotEmptyOrWildCard(filter.getIdsportellomitt())) {
		    result += " and dstc.id_sportellomitt=?";
		    parameters.add(new ParameterHelper(position, filter.getIdsportellomitt().trim(), new StringType()));
		    position++;
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (isStringNotEmptyOrWildCard(filter.getCodicedomandastc())) {
		    result += " and lower(dstc.id_domandamitt) like ?";
		    parameters.add(new ParameterHelper(position, "%" + filter.getCodicedomandastc().trim().toLowerCase() + "%", new StringType()));
		    position++;
		}
		result += ")";
	    }
	}
	//Bandi 
	if (EntityUtils.getNestedProperty(filter.getBandi(), "id.codice") != null) {
	    result += "and exists (select 1 from " +
		    SCHEMA_NAME +
		    "graduatoried," +
		    SCHEMA_NAME +
		    "graduatoriet," +
		    SCHEMA_NAME +
		    "bandi " +
		    " where  istanze.idcomune=graduatoried.idcomune and istanze.codiceistanza=graduatoried.codiceistanza  " +
		    " and  graduatoried.idcomune=graduatoriet.idcomune " +
		    " and graduatoried.fk_gt_id=graduatoriet.id  and  graduatoriet.idcomune=bandi.idcomune " +
		    " and graduatoriet.fk_ba_id=bandi.id  and  bandi.software =? and  bandi.id = ?  ";
	    parameters.add(new ParameterHelper(position, ORMHelper.getSoftware(), new StringType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getBandi().getId().getCodice(), new IntegerType()));
	    position++;
	    if (EntityUtils.getNestedProperty(filter.getGraduatoriet(), "id.codice") != null) {
		result += " and graduatoriet.id= ? ";
		parameters.add(new ParameterHelper(position, filter.getGraduatoriet().getId().getCodice(), new IntegerType()));
		position++;
	    }
	    result += ")";
	}
	// Pratiche da accettare come istruttore
	if (filter.getUtenteLoggato() != null && filter.getUtenteLoggato().getId() != null && filter.getUtenteLoggato().getId().getCodice() != null
		&& filter.getIsPraticheDaAccettareComeIstruttore() != null) {
	    // Se sto ricercando le pratiche che l'utente loggato deve trovare sul pannellino di accettazione
	    // non devo considereare i permessi 
	    // FUNZIONINALITA' ANTI - CORRUZIONE
	    if (Boolean.FALSE.equals(filter.getIsPraticheDaAccettareComeIstruttore())) {
		// LA FUNZIONALITA' VA BENE COSì PERCHE' E' STATO STABILITO CHE ANCHE L'OPERATORE CN FLAG_READONLY POSSA VEDERE SOLAMENTE LE ISTANZE 
		// PER LE QUALI SIASI STATO INSERITO IN PERMISTANZE E ISTANZERUOLI 
		result += " and (exists (select 1 from " +
			SCHEMA_NAME +
			"permistanze pi where pi.idcomune=istanze.idcomune and pi.codiceresponsabile=? and" +
			" pi.codiceistanza=istanze.codiceistanza) or" +
			"  exists (select 1 from " +
			SCHEMA_NAME +
			"istanzeruoli iru, " +
			SCHEMA_NAME +
			"responsabiliruoli rr where iru.idcomune=rr.idcomune and iru.idruolo=rr.idruolo" +
			" and rr.codiceresponsabile=? and rr.idcomune=istanze.idcomune and iru.codiceistanza=istanze.codiceistanza)";
		result += ")";
		parameters.add(new ParameterHelper(position, filter.getUtenteLoggato().getId().getCodice(), new IntegerType()));
		position++;
		parameters.add(new ParameterHelper(position, filter.getUtenteLoggato().getId().getCodice(), new IntegerType()));
		position++;
	    } else {
		result += " AND istruttoretemp.codiceresponsabile = ?  ";
		// aggiungere filtro resp proc
		parameters.add(new ParameterHelper(position, filter.getUtenteLoggato().getId().getCodice(), new IntegerType()));
		position++;
	    }
	}
	// Autorizzazioni
	if (filter.getDatiAutorizzazione() != null && (isStringNotEmptyOrWildCard(filter.getDatiAutorizzazione().getAutoriznumero())
		|| filter.getDatiAutorizzazione().getAutorizdata() != null
		|| EntityUtils.getNestedProperty(filter, "datiAutorizzazione.tipologiaregistro.id.codice") != null
		|| isStringNotEmptyOrWildCard(filter.getDatiAutorizzazione().getAutorizcomune().getCodicecomune()))) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "autorizzazioni aut " +
		    " where aut.idcomune=istanze.idcomune and aut.fkidistanza=istanze.codiceistanza ";
	    if (isStringNotEmptyOrWildCard(filter.getDatiAutorizzazione().getAutoriznumero())) {
		result += " and aut.autoriznumero like ?";
		parameters.add(new ParameterHelper(position, filter.getDatiAutorizzazione().getAutoriznumero().trim() + "%", new StringType()));
		position++;
	    }
	    if (filter.getDatiAutorizzazione().getAutorizdata() != null) {
		result += " and aut.autorizdata=? ";
		parameters.add(new ParameterHelper(position, filter.getDatiAutorizzazione().getAutorizdata(), new DateType()));
		position++;
	    }
	    if (EntityUtils.getNestedProperty(filter, "datiAutorizzazione.tipologiaregistro.id.codice") != null) {
		result += " and aut.fkidregistro=? ";
		parameters.add(
			new ParameterHelper(position, filter.getDatiAutorizzazione().getTipologiaregistro().getId().getCodice(), new IntegerType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getDatiAutorizzazione().getAutorizcomune().getCodicecomune())) {
		result += " and aut.autorizcomune=? ";
		parameters.add(new ParameterHelper(position, filter.getDatiAutorizzazione().getAutorizcomune().getCodicecomune(), new StringType()));
		position++;
	    }
	    result += ") ";
	}
	//Protocollo: Data
	boolean isSearchRangeDataProt = (EntityUtils.getNestedProperty(filter, "dallaDataProtocollo") != null
		|| EntityUtils.getNestedProperty(filter, "allaDataProtocollo") != null);
	if (isSearchRangeDataProt) {
	    result += " and ( (";
	    boolean isDa = false;
	    if (EntityUtils.getNestedProperty(filter, "dallaDataProtocollo") != null) {
		isDa = true;
		result += "  istanze.dataprotocollo >= ?";
		Calendar t = Calendar.getInstance();
		t.setTime(filter.getDallaDataProtocollo());
		parameters.add(new ParameterHelper(position, t.getTime(), new DateType()));
		position++;
	    }
	    if (EntityUtils.getNestedProperty(filter, "allaDataProtocollo") != null) {
		result += (isDa ? " and " : "") + " istanze.dataprotocollo <= ?";
		Calendar t = Calendar.getInstance();
		t.setTime(filter.getAllaDataProtocollo());
		parameters.add(new ParameterHelper(position, t.getTime(), new DateType()));
		position++;
	    }
	    result += " ) ";
	    //FILTRO PROTOCOLLO PER RANGE DATA : filtro impostato sulla data protocollo dei movimenti
	    //. Controllo se impostato filtro range date per protocollo per movimento
	    // se filtro per range imposto la variabile isSearchRangeDataProtMov=true, esclude successive ricerca per data
	    if (filter.isCercaprotocolloinmovimenti()) {
		logger.debug(
			"buildQuery# Impostato filtro ricerca : Range date protocollo nei movimenti. Controllo se sono state impostate le date, se almeno una " +
				"delle due date non è impostata non effettuo la ricerca");
		if (EntityUtils.getNestedProperty(filter, "dallaDataProtocollo") != null
			|| EntityUtils.getNestedProperty(filter, "allaDataProtocollo") != null) {
		    logger.debug("buildQuery# Ricerca 'range date protocollo nei movimenti' : Impostata almeno una data del range");
		    // Creo la porzione di query che permette di effettuare la ricerca nei movimenti
		    result += " or exists (select 1 from " +
			    SCHEMA_NAME +
			    "movimenti mp where mp.idcomune=istanze.idcomune and mp.codiceistanza=istanze.codiceistanza ";
		    if (filter.getModulo() != null && StringUtils.isNotBlank(filter.getModulo().getCodice())) {
			result += " and istanze.software=?";
			parameters.add(new ParameterHelper(position, filter.getModulo().getCodice(), new StringType()));
			position++;
		    }
		    // Verifico se sono state impostate date per la ricerca del protocollo su range di date
		    if (EntityUtils.getNestedProperty(filter, "dallaDataProtocollo") != null) {
			logger.debug("buildQuery# Filtro data protocollo : Da {}", filter.getDallaDataProtocollo());
			result += " and mp.dataprotocollo >= ?";
			Calendar t = Calendar.getInstance();
			t.setTime(filter.getDallaDataProtocollo());
			parameters.add(new ParameterHelper(position, t.getTime(), new DateType()));
			position++;
		    }
		    if (EntityUtils.getNestedProperty(filter, "allaDataProtocollo") != null) {
			logger.debug("buildQuery# Filtro data protocollo : A {}", filter.getDallaDataProtocollo());
			result += " and mp.dataprotocollo <= ?";
			Calendar t = Calendar.getInstance();
			t.setTime(filter.getAllaDataProtocollo());
			parameters.add(new ParameterHelper(position, t.getTime(), new DateType()));
			position++;
		    }
		    result += " ) ";
		}
	    }
	    result += " ) ";
	}
	//Protocollo: numero
	if (isStringNotEmptyOrWildCard(filter.getNumeroprotocollo())) {
	    String numProt = filter.getNumeroprotocollo();
	    String likeOrEquals = " = ";
	    if (numProt.indexOf("%") >= 0) {
		likeOrEquals = " like ";
	    }
	    result += " and ( (numeroprotocollo " + likeOrEquals + " ? ";
	    parameters.add(new ParameterHelper(position, filter.getNumeroprotocollo().trim(), new StringType()));
	    position++;
	    if (filter.getDataprotocollo() != null && !isSearchRangeDataProt) {
		result += " and dataprotocollo=? ";
		parameters.add(new ParameterHelper(position, filter.getDataprotocollo(), new DateType()));
		position++;
	    }
	    result += " ) ";
	    // anche tra i movimenti
	    if (filter.isCercaprotocolloinmovimenti()) {
		// Si vuole 
		result += " or exists (select 1 from " +
			SCHEMA_NAME +
			"movimenti mp where mp.idcomune=istanze.idcomune and mp.codiceistanza=istanze.codiceistanza and mp.numeroprotocollo" +
			likeOrEquals +
			" ? ";
		parameters.add(new ParameterHelper(position, filter.getNumeroprotocollo().trim(), new StringType()));
		position++;
		if (filter.getDataprotocollo() != null) {
		    result += " and  mp.dataprotocollo=? ";
		    parameters.add(new ParameterHelper(position, filter.getDataprotocollo(), new DateType()));
		    position++;
		}
		result += " ) ";
	    }
	    result += ") ";
	    // fine
	}
	//Istanze in warning
	if (filter.getIstanzeInWarning() != null && filter.getIstanzeInWarning().booleanValue()) {
	    List<Statiistanza> statistStatiistanzas = statiistanzaDAO.findStatiInWarning();
	    if (statistStatiistanzas.size() > 0) {
		String[] stati = new String[statistStatiistanzas.size()];
		String qm = StringUtils.repeat("?,", stati.length);
		qm = qm.substring(0, qm.length() - 1);
		result += " and istanze.chiusura in (" + qm + ")";
		for (Statiistanza statiistanza : statistStatiistanzas) {
		    parameters.add(new ParameterHelper(position, statiistanza.getId().getCodicestato(), new StringType()));
		    position++;
		}
	    }
	} else {
	    if (EntityUtils.getNestedProperty(filter, "chiusura.id.codicestato") != null) {
		String stato = filter.getChiusura().getId().getCodicestato();
		if (!stato.equals("stato_tutte")) { // non esegue filtri su stato
		    if (stato.equalsIgnoreCase("stato_chiuse") || stato.equalsIgnoreCase("stato_aperte")
			    || stato.equalsIgnoreCase("stato_chiuse_negativamente") || stato.equalsIgnoreCase("stato_chiuse_positivamente")) {
			List<Statiistanza> statistStatiistanzas = null;
			if (stato.equalsIgnoreCase("stato_chiuse")) {
			    statistStatiistanzas = statiistanzaDAO.findByStatocomportamentoChiuse();
			} else if (stato.equalsIgnoreCase("stato_chiuse_negativamente")) {
			    statistStatiistanzas = statiistanzaDAO.findByStatocomportamentoChiuseNegativamente();
			} else if (stato.equalsIgnoreCase("stato_chiuse_positivamente")) {
			    statistStatiistanzas = statiistanzaDAO.findByStatocomportamentoChiusePositivamente();
			} else {
			    statistStatiistanzas = statiistanzaDAO.findByStatocomportamentoAperte();
			}
			if (statistStatiistanzas.size() > 0) {
			    String[] stati = new String[statistStatiistanzas.size()];
			    String qm = StringUtils.repeat("?,", stati.length);
			    qm = qm.substring(0, qm.length() - 1);
			    result += " and istanze.chiusura in (" + qm + ")";
			    for (Statiistanza statiistanza : statistStatiistanzas) {
				parameters.add(new ParameterHelper(position, statiistanza.getId().getCodicestato(), new StringType()));
				position++;
			    }
			}
		    } else {
			result += " and istanze.chiusura=?";
			parameters.add(new ParameterHelper(position, stato.trim(), new StringType()));
			position++;
		    }
		}
	    }
	}
	// Soggetti dell'istanza: Nominativo
	if (StringUtils.isNotBlank(filter.getSoggettiistanza())) {
	    result += " and ( ";
	    result += " lower(" + applyConcatFunction("' '", new String[] { "richiedente.nominativo", "richiedente.nome" }) + ") like ?";
	    parameters.add(new ParameterHelper(position, filter.getSoggettiistanza().toLowerCase().trim() + "%", new StringType()));
	    position++;
	    result += " OR ";
	    result += " lower(" +
		    applyConcatFunction("' '", new String[] { "aziendarichiedente.nominativo", "aziendarichiedente.nome" }) +
		    ") like ?";
	    parameters.add(new ParameterHelper(position, filter.getSoggettiistanza().toLowerCase().trim() + "%", new StringType()));
	    position++;
	    result += " OR ";
	    result += " exists (select 1 from " +
		    SCHEMA_NAME +
		    "anagrafe professionista where professionista.idcomune=istanze.idcomune and professionista.codiceanagrafe=istanze.codiceprofessionista and " +
		    " lower(" +
		    applyConcatFunction("' '", new String[] { "professionista.nominativo", "professionista.nome" }) +
		    ") like ? )";
	    parameters.add(new ParameterHelper(position, filter.getSoggettiistanza().toLowerCase().trim() + "%", new StringType()));
	    position++;
	    if (filter.isCercaInAnagrafestorico()) {
		//
		result += " or ( ";
		result += " lower(" +
			applyConcatFunction("' '", new String[] { "richiedentestorico.nominativo", "richiedentestorico.nome" }) +
			") like ?";
		parameters.add(new ParameterHelper(position, filter.getSoggettiistanza().toLowerCase().trim() + "%", new StringType()));
		position++;
		result += " OR ";
		result += " lower(" + applyConcatFunction("' '", new String[] { "aziendastorico.nominativo", "aziendastorico.nome" }) + ") like ?";
		parameters.add(new ParameterHelper(position, filter.getSoggettiistanza().toLowerCase().trim() + "%", new StringType()));
		position++;
		result += " OR ";
		result += " exists (select 1 from " +
			SCHEMA_NAME +
			"anagrafestorico professionistastorico where professionistastorico.idcomune=istanze.idcomune and professionistastorico.id=istanze.fk_professionistastorico_id and " +
			" lower(" +
			applyConcatFunction("' '", new String[] { "professionistastorico.nominativo", "professionistastorico.nome" }) +
			") like ? )";
		parameters.add(new ParameterHelper(position, filter.getSoggettiistanza().toLowerCase().trim() + "%", new StringType()));
		position++;
		result += " )";
		//		
	    }
	    result += " )";
	}
	//Soggetti dell'istanza: P.IVA / Codice Fiscale
	if (StringUtils.isNotBlank(filter.getSoggettiistanzaPivaCF())) {
	    result += " and ( ";
	    result += " ( lower(richiedente.codicefiscale) like ? or lower(richiedente.partitaiva) like ? ) ";
	    parameters.add(new ParameterHelper(position, filter.getSoggettiistanzaPivaCF().toLowerCase().trim() + "%", new StringType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getSoggettiistanzaPivaCF().toLowerCase().trim() + "%", new StringType()));
	    position++;
	    result += " OR ";
	    result += " ( lower(aziendarichiedente.codicefiscale) like ? or lower(aziendarichiedente.partitaiva) like ? ) ";
	    parameters.add(new ParameterHelper(position, filter.getSoggettiistanzaPivaCF().toLowerCase().trim() + "%", new StringType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getSoggettiistanzaPivaCF().toLowerCase().trim() + "%", new StringType()));
	    position++;
	    result += " OR ";
	    result += " exists (select 1 from " +
		    SCHEMA_NAME +
		    "anagrafe professionista2 where professionista2.idcomune=istanze.idcomune and professionista2.codiceanagrafe=istanze.codiceprofessionista " +
		    " and ( lower(professionista2.codicefiscale) like ? or lower(professionista2.partitaiva) like ? ) )";
	    parameters.add(new ParameterHelper(position, filter.getSoggettiistanzaPivaCF().toLowerCase().trim() + "%", new StringType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getSoggettiistanzaPivaCF().toLowerCase().trim() + "%", new StringType()));
	    position++;
	    result += " )";
	}
	//Soggetti dell'istanza: codice
	if (EntityUtils.getNestedProperty(filter, "richiedente.id.codice") != null) {
	    result += " and ( ";
	    result += " ( istanze.codicerichiedente = ? or istanze.codicetitolarelegale = ? or istanze.codiceprofessionista = ? ) ";
	    parameters.add(new ParameterHelper(position, filter.getRichiedente().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getRichiedente().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getRichiedente().getId().getCodice(), new IntegerType()));
	    position++;
	    result += " OR ";
	    result += " exists (select 1 from " +
		    SCHEMA_NAME +
		    "istanzerichiedenti ir where ir.idcomune=istanze.idcomune and ir.codiceistanza=istanze.codiceistanza " +
		    " and  (ir.codicerichiedente = ? or ir.codiceanagrafecoll = ? or ir.codiceprocuratore = ? )";
	    parameters.add(new ParameterHelper(position, filter.getRichiedente().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getRichiedente().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getRichiedente().getId().getCodice(), new IntegerType()));
	    position++;
	    result += " ) )";
	}
	//Tutti i responsabili: codice
	if (EntityUtils.getNestedProperty(filter, "tuttiResponsabili.id.codice") != null) {
	    result += " and ( istanze.codiceresponsabile = ? or istanze.codiceresponsabileproc = ? or istanze.codiceistruttore = ?) ";
	    parameters.add(new ParameterHelper(position, filter.getTuttiResponsabili().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getTuttiResponsabili().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getTuttiResponsabili().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//Responsabile: codice
	if (EntityUtils.getNestedProperty(filter, "responsabile.id.codice") != null) {
	    result += " and istanze.codiceresponsabile = ? ";
	    parameters.add(new ParameterHelper(position, filter.getResponsabile().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//Responsabile del procedimento: codice
	if (EntityUtils.getNestedProperty(filter, "responsabileProcedimento.id.codice") != null) {
	    result += " and  istanze.codiceresponsabileproc = ? ";
	    parameters.add(new ParameterHelper(position, filter.getResponsabileProcedimento().getId().getCodice(), new IntegerType()));
	    position++;
	}
	//Responsabile dell'istruttoria: codice
	if (EntityUtils.getNestedProperty(filter, "responsabileIstruttoria.id.codice") != null) {
	    result += " and istanze.codiceistruttore = ? ";
	    parameters.add(new ParameterHelper(position, filter.getResponsabileIstruttoria().getId().getCodice(), new IntegerType()));
	    position++;
	    result += "  ";
	}
	//Aree
	if (EntityUtils.getNestedProperty(filter, "istanzearee.id.codicearea") != null) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "istanzearee iaa " +
		    " where iaa.idcomune=istanze.idcomune and iaa.codiceistanza=istanze.codiceistanza ";
	    result += " and iaa.codicearea = ? ) ";
	    parameters.add(new ParameterHelper(position, filter.getIstanzearee().getId().getCodicearea(), new IntegerType()));
	    position++;
	}
	//Localizzazione
	boolean cercaLocalizzazione = false;
	if (EntityUtils.getNestedProperty(filter, "istanzestradario.stradario.id.codice") != null
		|| StringUtils.isNotBlank(filter.getIstanzestradario().getCircoscrizione())
		|| StringUtils.isNotBlank(filter.getIstanzestradario().getNote())
		|| StringUtils.isNotBlank(filter.getIstanzestradario().getEsponente())
		|| StringUtils.isNotBlank(filter.getIstanzestradario().getScala()) || StringUtils.isNotBlank(filter.getIstanzestradario().getPiano()) //
		|| StringUtils.isNotBlank(filter.getIstanzestradario().getInterno())
		|| StringUtils.isNotBlank(filter.getIstanzestradario().getEsponenteinterno())
		|| StringUtils.isNotBlank(filter.getIstanzestradario().getFabbricato())
		|| StringUtils.isNotBlank(filter.getIstanzestradario().getFrazione()) || StringUtils.isNotBlank(filter.getIstanzestradario().getCap())
		|| StringUtils.isNotBlank(filter.getIstanzestradario().getQuartiere()) // 
		|| (EntityUtils.getNestedProperty(filter, "istanzestradario.stradariocolore.id.codicecolore") != null) //
		|| StringUtils.isNotBlank(filter.getStradarioCodViario()) //
		|| StringUtils.isNotBlank(filter.getStradarioDescrizione()) //
		|| (StringUtils.isNotBlank(filter.getIstanzestradario().getStradariocolore().getId().getCodicecolore()))) {
	    cercaLocalizzazione = true;
	}
	if (cercaLocalizzazione) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "istanzestradario istr " +
		    " where istr.idcomune=istanze.idcomune and istr.codiceistanza=istanze.codiceistanza ";
	    //Localizzazione: id
	    if (EntityUtils.getNestedProperty(filter, "istanzestradario.stradario.id.codice") != null) {
		result += " and istr.codicestradario = ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getStradario().getId().getCodice(), new IntegerType()));
		position++;
	    }
	    //Localizzazione: civico
	    String singoloValoreCivicoRange = isRangeCampoCivicoPresente(filter);
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getCivico()) || StringUtils.isNotBlank(singoloValoreCivicoRange)) {
		result += " and lower(istr.civico) like ? ";
		if (StringUtils.isNotBlank(singoloValoreCivicoRange)) {
		    logger.debug(
			    "buildQuery# Filtro civico : singolo campo {} - Ricerca tipo 'like' (Il valore è stato preso da uno dei campi di ricerca " +
				    "civico range in quanto è stato popolato solo uno) ",
			    singoloValoreCivicoRange);
		    parameters.add(new ParameterHelper(position, singoloValoreCivicoRange.toLowerCase().trim(), new StringType()));
		} else {
		    logger.debug("buildQuery# Filtro civico : singolo campo {} - Ricerca tipo 'like' ",
			    filter.getIstanzestradario().getCivico().toLowerCase().trim());
		    parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getCivico().toLowerCase().trim(), new StringType()));
		}
		position++;
	    } else {
		logger.debug("buildQuery# Filtro civico : range campi - Ricerca tipo ... ");
		if (StringUtils.isNotBlank(filter.getCivicoA()) && StringUtils.isNotBlank(filter.getCivicoDa())) {
		    List<Integer> civici = Utilities.contaInteri(Integer.parseInt(filter.getCivicoDa().trim()),
			    Integer.parseInt(filter.getCivicoA().trim()));
		    if (!civici.isEmpty()) {
			result += " and lower(istr.civico) in ( ";
			for (Integer civico : civici) {
			    // Inserisco il parametro sulla query ed incremento il contatore
			    parameters.add(new ParameterHelper(position, civico.toString(), new StringType()));
			    position++;
			    result += "?,";
			}
			// Rimuovo l'ultmima virgola che non serve
			result = StringUtils.removeEnd(result, ",");
			result += ") ";
		    }
		}
	    }
	    //Localizzazione: km
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getKm())) {
		result += " and lower(istr.km) like ? ";
		logger.debug("buildQuery# Filtro km : singolo campo {} - Ricerca tipo 'like' ",
			filter.getIstanzestradario().getKm().toLowerCase().trim());
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getKm().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    //Localizzazione: km dal
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getTransientDalKM())) {
		result += rangeKm(true);
		String kmSistematoANumero = sistemaKmFiltro(filter.getIstanzestradario().getTransientDalKM().toLowerCase().trim());
		logger.debug("buildQuery# Filtro dal km : singolo campo {} - Ricerca tipo range {}",
			filter.getIstanzestradario().getTransientDalKM().toLowerCase().trim(), kmSistematoANumero);
		parameters.add(new ParameterHelper(position, kmSistematoANumero, new StringType()));
		position++;
	    }
	    //Localizzazione: km al
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getTransientAlKM())) {
		result += rangeKm(false);
		String kmSistematoANumero = sistemaKmFiltro(filter.getIstanzestradario().getTransientAlKM().toLowerCase().trim());
		logger.debug("buildQuery# Filtro al km : singolo campo {} - Ricerca tipo range {}",
			filter.getIstanzestradario().getTransientDalKM().toLowerCase().trim(), kmSistematoANumero);
		parameters.add(new ParameterHelper(position, kmSistematoANumero, new StringType()));
		position++;
	    }
	    //Localizzazione: codice viario
	    if (StringUtils.isNotBlank(filter.getStradarioCodViario())) {
		result += " and stradario.codviario = ? ";
		parameters.add(new ParameterHelper(position, filter.getStradarioCodViario(), new StringType()));
		position++;
	    }
	    //Localizzazione: descrizione
	    if (StringUtils.isNotBlank(filter.getStradarioDescrizione())) {
		result += " and trim(lower(" + applyConcatFunction("' '", "stradario.prefisso", "stradario.descrizione") + ")) like ? ";
		parameters.add(new ParameterHelper(position, filter.getStradarioDescrizione().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    //Localizzazione: esponente
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getEsponente())) {
		result += " and lower(istr.esponente) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getEsponente().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    //Localizzazione: scala
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getScala())) {
		result += " and lower(istr.scala) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getScala().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    //Localizzazione: piano
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getPiano())) {
		result += " and lower(istr.piano) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getPiano().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    //Localizzazione: interno
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getInterno())) {
		result += " and lower(istr.interno) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getInterno().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    //Localizzazione: esponente interno
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getEsponenteinterno())) {
		result += " and lower(istr.esponenteinterno) like ? ";
		parameters.add(
			new ParameterHelper(position, filter.getIstanzestradario().getEsponenteinterno().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    //Localizzazione: fabbricato
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getFabbricato())) {
		result += " and lower(istr.fabbricato) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getFabbricato().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    //Localizzazione: frazione
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getFrazione())) {
		result += " and lower(istr.frazione) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getFrazione().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    //Localizzazione: cap
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getCap())) {
		result += " and lower(istr.cap) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getCap().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    //Localizzazione: quartiere
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getQuartiere())) {
		result += " and lower(istr.quartiere) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getQuartiere().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    //Localizzazione: circoscrizione
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getCircoscrizione())) {
		result += " and lower(istr.circoscrizione) like ? ";
		parameters.add(new ParameterHelper(position, "%" + filter.getIstanzestradario().getCircoscrizione().toLowerCase().trim() + "%",
			new StringType()));
		position++;
	    }
	    //Localizzazione: note
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getNote())) {
		result += " and lower(istr.note) like ? ";
		parameters.add(
			new ParameterHelper(position, "%" + filter.getIstanzestradario().getNote().toLowerCase().trim() + "%", new StringType()));
		position++;
	    }
	    //Localizzazione: colore
	    if (EntityUtils.getNestedProperty(filter, "istanzestradario.stradariocolore.id.codicecolore") != null
		    && StringUtils.isNotBlank(filter.getIstanzestradario().getStradariocolore().getId().getCodicecolore())) {
		result += " and istr.colore = ? ";
		parameters.add(
			new ParameterHelper(position, filter.getIstanzestradario().getStradariocolore().getId().getCodicecolore(), new StringType()));
		position++;
	    }
	    //Localizzazione: solo primario
	    if (!filter.isCercalocalizzazioneinaltri()) {
		result += " and istr.primario = ? ";
		parameters.add(new ParameterHelper(position, 1, new IntegerType()));
		position++;
	    }
	    result += " )";
	}
	//Dati catastali
	boolean isCercaMappali = false;
	if (StringUtils.isNotBlank(filter.getIstanzemappali().getCatasto().getCodice())
		|| StringUtils.isNotBlank(filter.getIstanzemappali().getFoglio())
		|| StringUtils.isNotBlank(filter.getIstanzemappali().getParticella())
		|| StringUtils.isNotBlank(filter.getIstanzemappali().getSub())) {
	    isCercaMappali = true;
	}
	if (isCercaMappali) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "istanzemappali im " +
		    " where im.idcomune=istanze.idcomune and im.fkcodiceistanza=istanze.codiceistanza ";
	    //Dati catastali: Catasto
	    if (StringUtils.isNotBlank(filter.getIstanzemappali().getCatasto().getCodice())) {
		result += " and im.codicecatasto = ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzemappali().getCatasto().getCodice(), new StringType()));
		position++;
	    }
	    //Dati catastali: foglio
	    if (StringUtils.isNotBlank(filter.getIstanzemappali().getFoglio())) {
		result += " and im.foglio like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzemappali().getFoglio(), new StringType()));
		position++;
	    }
	    //Dati catastali: particella
	    if (StringUtils.isNotBlank(filter.getIstanzemappali().getParticella())) {
		result += " and im.particella like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzemappali().getParticella(), new StringType()));
		position++;
	    }
	    //Dati catastali: sub
	    if (StringUtils.isNotBlank(filter.getIstanzemappali().getSub())) {
		result += " and im.sub like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzemappali().getSub(), new StringType()));
		position++;
	    }
	    result += " )";
	}
	//Tipomovimento
	if (StringUtils.isNotBlank((String) EntityUtils.getNestedProperty(filter, "tipoMovimento.id.tipomovimento"))) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "movimenti mv " +
		    " where mv.idcomune=istanze.idcomune and mv.codiceistanza=istanze.codiceistanza and mv.data is not null ";
	    result += " and mv.tipomovimento = ?";
	    parameters.add(new ParameterHelper(position, filter.getTipoMovimento().getId().getTipomovimento(), new StringType()));
	    position++;
	    // Se lo metto qua crea problemi di position?
	    if (EntityUtils.getNestedProperty(filter, "dallaDataTipoMov") != null) {
		result += " and mv.data >= ?";
		Calendar t = Calendar.getInstance();
		t.setTime(filter.getDallaDataTipoMov());
		t.set(Calendar.HOUR, 0);
		t.set(Calendar.MINUTE, 0);
		t.set(Calendar.SECOND, 0);
		parameters.add(new ParameterHelper(position, t.getTime(), new TimestampType()));
		position++;
	    }
	    if (EntityUtils.getNestedProperty(filter, "allaDataTipoMov") != null) {
		result += " and mv.data <= ?";
		Calendar t = Calendar.getInstance();
		t.setTime(filter.getAllaDataTipoMov());
		t.set(Calendar.HOUR, 23);
		t.set(Calendar.MINUTE, 59);
		t.set(Calendar.SECOND, 59);
		parameters.add(new ParameterHelper(position, t.getTime(), new TimestampType()));
		position++;
	    }
	    result += " )";
	}
	//Endoprocedimenti
	boolean isIstanzeProcedimenti = false;
	if (EntityUtils.getNestedProperty(filter, "inventarioprocedimenti.tipoendo.tipifamiglieendo.id.codice") != null
		|| EntityUtils.getNestedProperty(filter, "inventarioprocedimenti.tipoendo.id.codice") != null
		|| EntityUtils.getNestedProperty(filter, "inventarioprocedimenti.id.codice") != null) {
	    isIstanzeProcedimenti = true;
	}
	if (isIstanzeProcedimenti) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "istanzeprocedimenti isp join " +
		    SCHEMA_NAME +
		    "inventarioprocedimenti ip " +
		    " on ip.idcomune=isp.idcomune and isp.codiceinventario=ip.codiceinventario left join " +
		    SCHEMA_NAME +
		    "tipiendo te " +
		    " on te.idcomune=ip.idcomune and te.codice=ip.codicetipo where isp.idcomune=istanze.idcomune and isp.codiceistanza=istanze.codiceistanza and ";
	    if (EntityUtils.getNestedProperty(filter, "inventarioprocedimenti.tipoendo.tipifamiglieendo.id.codice") != null) {
		if (EntityUtils.getNestedProperty(filter, "inventarioprocedimenti.tipoendo.id.codice") == null) {
		    if (EntityUtils.getNestedProperty(filter, "inventarioprocedimenti.id.codice") == null) {
			result += " te.codicefamigliaendo = ? ";
			parameters.add(new ParameterHelper(position,
				filter.getInventarioprocedimenti().getTipoendo().getTipifamiglieendo().getId().getCodice(), new IntegerType()));
			position++;
		    }
		}
	    }
	    if (EntityUtils.getNestedProperty(filter, "inventarioprocedimenti.tipoendo.id.codice") != null) {
		if (EntityUtils.getNestedProperty(filter, "inventarioprocedimenti.id.codice") == null) {
		    result += " ip.codicetipo = ? ";
		    parameters.add(
			    new ParameterHelper(position, filter.getInventarioprocedimenti().getTipoendo().getId().getCodice(), new IntegerType()));
		    position++;
		}
	    }
	    if (EntityUtils.getNestedProperty(filter, "inventarioprocedimenti.id.codice") != null) {
		result += " isp.codiceinventario = ? ";
		parameters.add(new ParameterHelper(position, filter.getInventarioprocedimenti().getId().getCodice(), new IntegerType()));
		position++;
	    }
	    result += " )";
	}
	//Attivita
	boolean isIstanzeAttivita = false;
	if (StringUtils.isNotBlank(filter.getIstanzeattivita().getAttivita().getSettori().getId().getCodicesettore())
		|| StringUtils.isNotBlank(filter.getIstanzeattivita().getAttivita().getId().getCodiceistat())) {
	    isIstanzeAttivita = true;
	}
	if (isIstanzeAttivita) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "istanzeattivita iatt join " +
		    SCHEMA_NAME +
		    "attivita att on " +
		    "att.idcomune=iatt.idcomune and att.codiceistat=iatt.codiceattivita " +
		    " where iatt.idcomune=istanze.idcomune and iatt.codiceistanza=istanze.codiceistanza and ";
	    if (StringUtils.isNotBlank(filter.getIstanzeattivita().getAttivita().getSettori().getId().getCodicesettore())) {
		if (StringUtils.isBlank(filter.getIstanzeattivita().getAttivita().getId().getCodiceistat())) {
		    result += " att.codicesettore = ? ";
		    parameters.add(new ParameterHelper(position, filter.getIstanzeattivita().getAttivita().getSettori().getId().getCodicesettore(),
			    new StringType()));
		    position++;
		}
	    }
	    if (StringUtils.isNotBlank(filter.getIstanzeattivita().getAttivita().getId().getCodiceistat())) {
		result += " iatt.codiceattivita = ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzeattivita().getAttivita().getId().getCodiceistat(), new StringType()));
		position++;
	    }
	    result += " )";
	}
	//Pratiche da assegnare: istruttore
	if (Boolean.TRUE.equals(filter.getIsPraticheDaAssegnareAdIstruttore())) {
	    result += " AND responsabileprocedimento.codiceresponsabile = ?  ";
	    // aggiungere filtro resp proc
	    parameters.add(new ParameterHelper(position, filter.getUtenteLoggato().getId().getCodice(), new IntegerType()));
	    position++;
	    result += " and grpistruttori.id is not null  ";
	    result += " and istruttoretemp.codiceresponsabile is null  ";
	    result += " and istruttore.codiceresponsabile is null  ";
	}
	//Pratiche da accettare: istruttore
	if (Boolean.TRUE.equals(filter.getIsPraticheDaAccettareComeIstruttore())) {
	    result += " and grpistruttori.id is not null  ";
	    result += " and istruttoretemp.codiceresponsabile is not null  ";
	    result += " and istruttore.codiceresponsabile is null  ";
	}
	//Soggetti dell'istanza: P.IVA o Codice Fiscale
	if (filter.getSoggettiIstanzaFilterCF() != null && filter.getSoggettiIstanzaFilterCF().isPopolato()) {
	    result += " and ( ";
	    if (StringUtils.isNotBlank(filter.getSoggettiIstanzaFilterCF().getRichiedenteCF())) {
		result += "  ( lower(richiedente.codicefiscale) like ? or lower(richiedente.partitaiva) like ? ) or ";
		parameters.add(new ParameterHelper(position, filter.getSoggettiIstanzaFilterCF().getRichiedenteCF().toLowerCase(), new StringType()));
		position++;
		parameters.add(new ParameterHelper(position, filter.getSoggettiIstanzaFilterCF().getRichiedenteCF().toLowerCase(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getSoggettiIstanzaFilterCF().getProfessionistaCF())) {
		result += " exists (select 1 from " +
			SCHEMA_NAME +
			"anagrafe professionista2 where professionista2.idcomune=istanze.idcomune and professionista2.codiceanagrafe=istanze.codiceprofessionista " +
			" and ( lower(professionista2.codicefiscale) like ? or lower(professionista2.partitaiva) like ? ) )  or ";
		parameters.add(
			new ParameterHelper(position, filter.getSoggettiIstanzaFilterCF().getProfessionistaCF().toLowerCase(), new StringType()));
		position++;
		parameters.add(
			new ParameterHelper(position, filter.getSoggettiIstanzaFilterCF().getProfessionistaCF().toLowerCase(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getSoggettiIstanzaFilterCF().getTitolarelegaleCF())) {
		result += " ( lower(aziendarichiedente.codicefiscale) like ? or lower(aziendarichiedente.partitaiva) like ? ) or  ";
		parameters.add(
			new ParameterHelper(position, filter.getSoggettiIstanzaFilterCF().getTitolarelegaleCF().toLowerCase(), new StringType()));
		position++;
		parameters.add(
			new ParameterHelper(position, filter.getSoggettiIstanzaFilterCF().getTitolarelegaleCF().toLowerCase(), new StringType()));
		position++;
	    }
	    //Sorteggi: data 
	    if (filter.getDallaDataSorteggio() != null || filter.getAllaDataSorteggio() != null) {
		result += " and exists (select 1 from " +
			SCHEMA_NAME +
			" sorteggitestata inner join sorteggidettaglio on sorteggitestata.idcomune = sorteggidettaglio.idcomune " +
			"and sorteggitestata.st_id = sorteggidettaglio.sd_fk_stid " +
			"where " +
			"sorteggidettaglio.idcomune = istanze.idcomune and sorteggidettaglio.codiceistanza = istanze.codiceistanza ";
		if (filter.getDallaDataSorteggio() != null) {
		    result += " and  sorteggitestata.st_datasorteggio >=  ?";
		    Calendar t = Calendar.getInstance();
		    t.setTime(filter.getDallaDataSorteggio());
		    parameters.add(new ParameterHelper(position, t.getTime(), new DateType()));
		    position++;
		}
		if (filter.getAllaDataSorteggio() != null) {
		    result += " and sorteggitestata.st_datasorteggio <= ?";
		    Calendar t = Calendar.getInstance();
		    t.setTime(filter.getAllaDataSorteggio());
		    parameters.add(new ParameterHelper(position, t.getTime(), new DateType()));
		    position++;
		}
		result += " )";
	    }
	    result += " 1=0)";
	}
	//Stato movimento anomalia STC
	if (isStringNotEmptyOrWildCard(filter.getStatomovimentoAnomaliaStc())) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "movimenti mov where mov.idcomune=istanze.idcomune and " +
		    "mov.codiceistanza=istanze.codiceistanza and mov.data is not null and mov.id_att_dest is not null ";
	    if (filter.getStatomovimentoAnomaliaStc().equalsIgnoreCase("IN_ATTESA")) {
		result += " and mov.stato_att_dest is null )";
	    } else {
		result += " and mov.stato_att_dest = ? )";
		parameters.add(new ParameterHelper(position, filter.getStatomovimentoAnomaliaStc().trim(), new StringType()));
		position++;
	    }
	}
	//Escludi risultati dalla ricerca
	Boolean flagEscludiRisultatiRicerca = filter.getFlagEscludiRisultatiDaRicercaPubblica();
	if (flagEscludiRisultatiRicerca != null && BooleanUtils.isTrue(flagEscludiRisultatiRicerca)) {
	    String codiceSoftware = (isFilterModuloNotNullAndFilterCodiceSoftwareNotBlankAndNotEqualsToTT()) ? filter.getModulo().getCodice()
		    : ORMHelper.getSoftware();
	    List<Alberoproc> listaInterventi = getListaInterventi(codiceSoftware);
	    if (listaInterventi != null && !listaInterventi.isEmpty()) {
		for (Alberoproc intervento : listaInterventi) {
		    result += " and not istanze.codiceistanza in (select codiceistanza from " +
			    SCHEMA_NAME +
			    "istanze ist2 inner join " +
			    SCHEMA_NAME +
			    "alberoproc ap2 on ist2.idcomune = ap2.idcomune and ist2.codiceinterventoproc = ap2.sc_id " +
			    "where ist2.idcomune = ? and istanze.idcomune = ? and ist2.idcomune = istanze.idcomune and istanze.software = ? " +
			    "and ist2.codiceistanza = istanze.codiceistanza and alberoproc.software = ist2.software " +
			    "and alberoproc.sc_codice like ? ) ";
		    parameters.add(new ParameterHelper(position, intervento.getId().getIdcomune(), new StringType()));
		    position++;
		    parameters.add(new ParameterHelper(position, intervento.getId().getIdcomune(), new StringType()));
		    position++;
		    parameters.add(new ParameterHelper(position, codiceSoftware, new StringType()));
		    position++;
		    parameters.add(new ParameterHelper(position, intervento.getScCodice().trim() + "%", new StringType()));
		    position++;
		}
	    }
	}
	//Filtri schede dinamiche
	if (filter.getSchedaDinamicaFilter() != null && filter.getSchedaDinamicaFilter().getScheda() != null
		&& !filter.getSchedaDinamicaFilter().getRighe().isEmpty()) {
	    MutableInt posRef = new MutableInt(position);
	    result += createSQLFragment("istanze.", "CODICEISTANZA", "CODICEISTANZA", "ISTANZEDYN2DATI", schemaName, filter.getSchedaDinamicaFilter(),
		    posRef);
	}
	//////////////////////////////////////////// FINE //////////////////////////////////////////
	if (TipoQueryHelperEnum.SELECT.equals(tipoQueryHelperEnum) && DialettoEnum.ORACLE.equals(_dialetto)) {
	    result += " )";
	    if (firstResult == null) {
		firstResult = Integer.valueOf(0);
	    }
	    if (maxResults != null) {
		result += " WHERE PAGINAZIONE >" + firstResult + " AND PAGINAZIONE <=" + (firstResult + maxResults);
	    }
	}
	if (StringUtils.isNotBlank(schemaName)) {
	    result = result.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	logger.debug("{}#buildQuery: {}", getClass().getSimpleName(), result);
	return result;
    }

    private String isRangeCampoCivicoPresente(IstanzeFilter filter) {

	String result = "";
	if (StringUtils.isNotBlank(filter.getCivicoDa()) && StringUtils.isNotBlank(filter.getCivicoA())) {
	    return result;
	} else {
	    if (StringUtils.isNotBlank(filter.getCivicoDa())) {
		result = filter.getCivicoDa();
	    }
	    if (StringUtils.isNotBlank(filter.getCivicoA())) {
		result = filter.getCivicoA();
	    }
	}
	return result;
    }

    private String rangeKm(boolean isDalKm) {

	if (DialettoEnum.ORACLE.equals(_dialetto)) {
	    return " AND COALESCE(to_number(TRIM(REGEXP_REPLACE(REGEXP_REPLACE(istr.km,'[a-zA-Z]',''),'[/+,]','.')), '99999999.999',' NLS_NUMERIC_CHARACTERS = '',.'' '),0) " +
		    (isDalKm ? " >= ? " : " <= ? ");
	}
	if (DialettoEnum.MYSQL.equals(_dialetto)) {
	    return " and CAST(REPLACE(REPLACE(REPLACE(istr.km,',','.'),'+','.'),'/','.') AS DECIMAL(10,3)) " +
		    (isDalKm ? " >= cast( ? AS DECIMAL(10,3)) " : " <= cast( ? AS DECIMAL(10,3)) ");
	}
	return " and istr.km  " + (isDalKm ? " >= ? " : " <= ? ");
    }

    private boolean isFilterModuloNotNullAndFilterCodiceSoftwareNotBlankAndNotEqualsToTT() {

	return filter.getModulo() != null && StringUtils.isNotBlank(filter.getModulo().getCodice())
		&& !StringUtils.defaultString(filter.getModulo().getCodice()).equalsIgnoreCase(WebConstants.SOFTWARE_TT);
    }

    private String sistemaKmFiltro(String numero) {

	return numero.replace(",", ".").replace("+", ".").replace("/", ".");
    }

    private List<Alberoproc> getListaInterventi(String codiceSoftware) {

	FilterTable ftable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	if (codiceSoftware != null && codiceSoftware.length() > 0) {
	    filterRestriction.addFilterField(FilterUtils.equals("software.codice", codiceSoftware, String.class));
	}
	filterRestriction.addFilterField(FilterUtils.equals("flagEscludiRisultatiRicerca", true, Boolean.class));
	ftable.addRestriction(filterRestriction);
	return alberoprocDAO.findByFilterTable(ftable, null, null);
    }

    private String getSelectQuery() {

	return "" + //
		" istanze.idcomune as idComune, istanze.software, istanze.codicecomune as codiceComune," +
		" comuni.comune, comuni.codiceistat as codiceIstat," +
		" istanze.codiceistanza as codiceIstanza, istanze.numeroistanza as numeroIstanza," +
		" istanzestradario.uuid as uuidIstanzeStradario," +
		" stradario.codviario as codiceViario, istanzestradario.civico, istanzestradario.km, istanzestradario.primario, " +
		" istanzestradario.latitudine, istanzestradario.longitudine";
    }

    private String getFromQuery() {

	return " from" +
		" " +
		SCHEMA_NAME +
		"istanze join " +
		SCHEMA_NAME +
		"anagrafe richiedente on istanze.idcomune=richiedente.idcomune and istanze.codicerichiedente=richiedente.codiceanagrafe" +
		" left join " +
		SCHEMA_NAME +
		"anagrafestorico richiedentestorico on istanze.idcomune=richiedentestorico.idcomune and istanze.fk_richiedentestorico_id=richiedentestorico.id " +
		" left join " +
		SCHEMA_NAME +
		"anagrafestorico aziendastorico on istanze.idcomune=aziendastorico.idcomune and istanze.fk_titolarelegalestorico_id=aziendastorico.id " +
		" left join " +
		SCHEMA_NAME +
		"anagrafe tecnico on istanze.idcomune=tecnico.idcomune and istanze.codiceprofessionista=tecnico.codiceanagrafe" +
		" join " +
		SCHEMA_NAME +
		"responsabili operatore on istanze.idcomune=operatore.idcomune and istanze.codiceresponsabile=operatore.codiceresponsabile" +
		" join " +
		SCHEMA_NAME +
		"statiistanza on istanze.idcomune=statiistanza.idcomune and istanze.software=statiistanza.software and istanze.chiusura=statiistanza.codicestato" +
		" join " +
		SCHEMA_NAME +
		"alberoproc on istanze.idcomune=alberoproc.idcomune and istanze.codiceinterventoproc=alberoproc.sc_id" +
		" join " +
		SCHEMA_NAME +
		"tipiprocedure on istanze.idcomune=tipiprocedure.idcomune and istanze.codiceprocedura=tipiprocedure.codiceprocedura" +
		" join " +
		SCHEMA_NAME +
		"comuni on istanze.codicecomune=comuni.codicecomune" +
		" left join " +
		SCHEMA_NAME +
		"anagrafe aziendarichiedente on istanze.idcomune=aziendarichiedente.idcomune and istanze.codicetitolarelegale=aziendarichiedente.codiceanagrafe" +
		" left join " +
		SCHEMA_NAME +
		"responsabili responsabileprocedimento on istanze.idcomune=responsabileprocedimento.idcomune and istanze.codiceresponsabileproc=responsabileprocedimento.codiceresponsabile" +
		" left join " +
		SCHEMA_NAME +
		"responsabili istruttore on istanze.idcomune=istruttore.idcomune and istanze.codiceistruttore=istruttore.codiceresponsabile" +
		" left join " +
		SCHEMA_NAME +
		"responsabili istruttoretemp on istanze.idcomune=istruttoretemp.idcomune and istanze.fk_istruttore_temp=istruttoretemp.codiceresponsabile" +
		" left join "
		//.
		+
		SCHEMA_NAME +
		"gruppi_istruttori grpistruttori on istanze.idcomune=grpistruttori.idcomune and istanze.fk_grp_istruttori=grpistruttori.id" +
		" left join " +
		SCHEMA_NAME +
		"istanzestradario on istanze.idcomune=istanzestradario.idcomune and istanze.codiceistanza=istanzestradario.codiceistanza" +
		" left join " +
		SCHEMA_NAME +
		"stradario on istanzestradario.idcomune=stradario.idcomune and istanzestradario.codicestradario=stradario.codicestradario" +
		" left join " +
		SCHEMA_NAME +
		"tipiarchivioistanze on istanze.idcomune=tipiarchivioistanze.idcomune and istanze.tipoarchivio=tipiarchivioistanze.codicearchivio" +
		" left join " +
		SCHEMA_NAME +
		"tipologiaistanza on istanze.idcomune=tipologiaistanza.idcomune and istanze.fkidtipologiaistanza=tipologiaistanza.ti_id" +
		" left join " +
		SCHEMA_NAME +
		"tipisoggetto on istanze.idcomune=tipisoggetto.idcomune and istanze.fkcodicesoggetto=tipisoggetto.codicetiposoggetto left join " +
		SCHEMA_NAME +
		"stradariocolore colore on istanzestradario.idcomune=colore.idcomune and istanzestradario.colore=colore.codicecolore " +
		" inner join " +
		SCHEMA_NAME +
		"software on istanze.software=software.codice " +
		" left join " +
		SCHEMA_NAME +
		"responsabili opincarico on istanze.idcomune=opincarico.idcomune and istanze.operatore_in_carico=opincarico.codiceresponsabile ";
    }
}
