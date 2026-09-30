package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.ArrayList;
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
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.HelperTypeEnum;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * La classe che costruisce la query SQL
 * 
 * @author riccardob
 * 
 */
public class QueryIstanzeComMassHelper extends BaseQueryHelper {

    /**
     * Variabili aggiunte per gestire la paginazione su oracle. In caso di ricerca utilizzando i campi dinamici si crea
     * un errore dovuta alla paginazione "ROWNUM" impostata da hibernate. In questo caso dobbiamo evitare di far gestire
     * la paginazione ad hibernate e impostarla direttamente sulla query.
     */
    private Integer firstResult; //per ora non servono, capire se possono servire
    private Integer maxResults;

    /**
     * * @param istanzeFilter l'oggetto per costruire i filtri di selezione
     * 
     * @param sessimpl
     *            la session factory implementor
     * @param alberoprocDAO
     *            questo DAO serve per il filtro su alberoproc
     * @param statiistanzaDAO
     *            questo DAO serve per il filtro su chiusura istanza
     * @param isCountQuery
     *            se true torna la query con count(*) altrimenti con la lista dei campi selezionati
     */
    public QueryIstanzeComMassHelper(IstanzeFilter istanzeFilter, SessionFactoryImplementor sessimpl, AlberoprocDAO alberoprocDAO,
	    StatiistanzaDAO statiistanzaDAO, Dyn2CampiDAO dyn2CampiDAO, ComuniassociatiService comuniassociatiService,
	    VerticalizzazioniService verticalizzazioniService, HelperTypeEnum helperTypeEnum, Integer firstResult, Integer maxResults) {

	this.filter = istanzeFilter;
	log.debug("QueryIstanzeHelper: recupero il dialetto della SessionFactoryImplementor");
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryIstanzeHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryIstanzeHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryIstanzeHelper: schemaName={}", schemaName);
	this.alberoprocDAO = alberoprocDAO;
	this.statiistanzaDAO = statiistanzaDAO;
	this.dyn2CampiDAO = dyn2CampiDAO;
	this.comuniassociatiService = comuniassociatiService;
	this.verticalizzazioniService = verticalizzazioniService;
	this.firstResult = firstResult;
	this.maxResults = maxResults;
	this.helperTypeEnum = helperTypeEnum;
    }

    /**
     * Costruisce la query a partire dall'oggetto filter passato
     * 
     * @return la stringa SQL per creare la SQLQUERY
     */
    @Override
    public String buildQuery() {

	String result = "";
	int position = 2;
	if (isRicercaSuTuttiIdComune()) {
	    position = 1;
	}
	
	if(helperTypeEnum == HelperTypeEnum .ISTANZE){
	    result = "select istanze.codiceistanza as codiceIstanza, istanze.codicerichiedente, istanze.codiceprofessionista, istanze.codicetitolarelegale ";
	}else if(helperTypeEnum == HelperTypeEnum .SOFTWAREANDCOMUNE){
	    result = "select distinct istanze.software, istanze.codicecomune ";
	}else{
	    throw new RuntimeException("No valid helperTypeEnum");
	}
	
	// Nel caso sia configirato il parametro COMPORTAMENTI_ISTANZE.VIS_ICONA_A_IN_LISTAISTANZE = SOLO_ATTIVE
	// alla query che ritorna il numero della autorizzazione presenti per singola istanza viene applicato
	// l'ulteriorio filtro "autorizzazioni.flag_attiva = 1"
	String subQueryVisLog = queryPerLogicaVisIconaA();
	result = StringUtils.replace(result, "${SOLO_ATTIVE}", subQueryVisLog);
	if (isRicercaSuTuttiIdComune()) {
	    result += fromQuery + whereQueryNoIdComune;
	} else {
	    result += fromQuery + whereQuery;
	}
	
	// FILTRI
	// DATI ISTANZA	
	if (!filter.getListaIdcomuneFiltro().isEmpty()) {
	    String qm = StringUtils.repeat("?,", filter.getListaIdcomuneFiltro().size());
	    qm = qm.substring(0, qm.length() - 1);
	    result += " and istanze.idcomune in (" + qm + ")";
	    for (String idcomune : filter.getListaIdcomuneFiltro()) {
		parameters.add(new ParameterHelper(position, idcomune, new StringType()));
		position++;
	    }
	}
	if (filter.getModulo() != null && StringUtils.isNotBlank(filter.getModulo().getCodice())
		&& !StringUtils.defaultString(filter.getModulo().getCodice()).equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
	    result += " and istanze.software=?";
	    parameters.add(new ParameterHelper(position, filter.getModulo().getCodice(), new StringType()));
	    position++;
	}
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
	} else
	// Controllo se è un installazione multi comune, nel caso la chiamata alla query potrebbe essere stata fatta dal pannellino
	// che su searchIstanze.jsp mostra le pratiche perventute dai vari front. In questo caso dovranno essere mostratate sulla lista
	// solo le pratiche che appartengoni ai comuni per cui l'operatore loggato è responsabile.
	{
	    //
	    // Filtro per codice comune, se è un installazione multi comune, l'operatore deve vedere solo le scadenze per i comuni 
	    // per cui è abilitato.
	    if (log.isDebugEnabled()) {
		log.debug("buildQuery# Controllo se si tratta di un installazione con idcomune {} è multi comune", ORMHelper.getIdcomune());
	    }
	    if (!isRicercaSuTuttiIdComune()) {
		boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
		if (isComuniAssociati) {
		    if (log.isDebugEnabled()) {
			log.debug("buildQuery# E' un installazione multicomune, recupero i comuni configurati per l'opertaore {} ({})");
		    }
		    List<Responsabilicomuni> responsabilicomunis = comuniassociatiService.checkComuniAbilitatiPerResponsabile(false);
		    if (responsabilicomunis != null && !responsabilicomunis.isEmpty()) {
			String[] codicicomune = new String[responsabilicomunis.size()];
			result += " and (istanze.codicecomune in  (";
			String comuniattiviQm = "";
			for (int i = 0; i < responsabilicomunis.size(); i++) {
			    Responsabilicomuni responsabilicomunimuni = (Responsabilicomuni) responsabilicomunis.get(i);
			    codicicomune[i] = responsabilicomunimuni.getId().getCodicecomune();
			    comuniattiviQm += ",?";
			    parameters.add(new ParameterHelper(position, codicicomune[i], new StringType()));
			    position++;
			}
			comuniattiviQm = comuniattiviQm.replaceFirst(",", "");
			result += comuniattiviQm + "))";
		    }
		}
	    }
	}
	// ALTRE RICERCHE PER L'OGGETTO COMUNE
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
	// Filtro per il codice istanza
	if (filter.getCodiceIstanza() != null) {
	    result += " and lower(istanze.codiceistanza) = ?";
	    parameters.add(new ParameterHelper(position, filter.getCodiceIstanza(), new IntegerType()));
	    position++;
	}
	if (isStringNotEmptyOrWildCard(filter.getCodicepraticatel())) {
	    result += " and lower(istanze.codicepraticatel) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getCodicepraticatel().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	if (filter.getAmministrazioni() != null && filter.getAmministrazioni().getId() != null
		&& filter.getAmministrazioni().getId().getCodice() != null) {
	    result += " and istanze.codiceamministrazione = ?";
	    parameters.add(new ParameterHelper(position, filter.getAmministrazioni().getId().getCodice(), new IntegerType()));
	    position++;
	}
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
	if (EntityUtils.getNestedProperty(filter, "dallaDataValidita") != null) {
	    result += " and istanze.DATAVALIDITA >= ?";
	    parameters.add(new ParameterHelper(position, filter.getDallaDataValidita(), new DateType()));
	    position++;
	}
	if (EntityUtils.getNestedProperty(filter, "allaDataValidita") != null) {
	    result += " and istanze.DATAVALIDITA <= ?";
	    parameters.add(new ParameterHelper(position, filter.getAllaDataValidita(), new DateType()));
	    position++;
	}
	if (EntityUtils.getNestedProperty(filter, "tipiarchivioistanza.id.codice") != null) {
	    result += " and istanze.tipoarchivio=?";
	    parameters.add(new ParameterHelper(position, filter.getTipiarchivioistanza().getId().getCodice(), new IntegerType()));
	    position++;
	}
	if (isStringNotEmptyOrWildCard(filter.getPosizionearchivio())) {
	    result += " and lower(istanze.posizionearchivio) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getPosizionearchivio().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	if (EntityUtils.getNestedProperty(filter, "tipologiaistanza.id.codice") != null) {
	    result += " and istanze.fkidtipologiaistanza=?";
	    parameters.add(new ParameterHelper(position, filter.getTipologiaistanza().getId().getCodice(), new IntegerType()));
	    position++;
	}
	if (EntityUtils.getNestedProperty(filter, "professionista.id.codice") != null) {
	    result += " and istanze.codiceprofessionista=?";
	    parameters.add(new ParameterHelper(position, filter.getProfessionista().getId().getCodice(), new IntegerType()));
	    position++;
	}
	if (EntityUtils.getNestedProperty(filter, "alberoproc.id.codice") != null) {
	    Alberoproc alberoproc = alberoprocDAO.findById(filter.getAlberoproc().getId());
	    result += " and alberoproc.sc_codice like ?";
	    parameters.add(new ParameterHelper(position, alberoproc.getScCodice() + "%", new StringType()));
	    position++;
	}
	if (BooleanUtils.isTrue(filter.getChkexportanagrafetrib())) {
	    result += " and alberoproc.atrib_tipologiaintervento is not null ";
	}
	if (EntityUtils.getNestedProperty(filter, "procedura.id.codice") != null) {
	    result += " and istanze.codiceprocedura = ?";
	    parameters.add(new ParameterHelper(position, filter.getProcedura().getId().getCodice(), new IntegerType()));
	    position++;
	}
	if (isStringNotEmptyOrWildCard(filter.getLavori())) {
	    result += " and lower(istanze.lavori) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getLavori().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	if (isStringNotEmptyOrWildCard(filter.getDomicilioElettronico())) {
	    result += " and lower(istanze.domicilio_elettronico) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getDomicilioElettronico().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	if (isStringNotEmptyOrWildCard(filter.getNomeattivita())) {
	    result += " and lower(istanze.nomeattivita) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getNomeattivita().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	if (isStringNotEmptyOrWildCard(filter.getLavoriestesa())) {
	    result += " and lower(istanze.lavoriestesa) like ?";
	    parameters.add(new ParameterHelper(position, "%" + filter.getLavoriestesa().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	// Filtro per il campo id domanda mittente delle istanze pervenute da STC.
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
	// Filtri per bando e graduatorie t. Metto una query di select (tramite la sintassi exists) nella where solo se è popolato il bando 
	if (EntityUtils.getNestedProperty(filter.getBandi(), "id.codice") != null) {
	    //	    result += " and exists (select 1 from " + SCHEMA_NAME + "GRADUATORIED," + SCHEMA_NAME + "GRADUATORIET," + SCHEMA_NAME + "BANDI  "
	    //		    + " where " + " ISTANZE.IDCOMUNE=GRADUATORIED.IDCOMUNE and ISTANZE.CODICEISTANZA=GRADUATORIED.CODICEISTANZA " + " and "
	    //		    + " GRADUATORIED.IDCOMUNE=GRADUATORIET.IDCOMUNE and GRADUATORIED.FK_GT_ID=GRADUATORIET.ID " + " and "
	    //		    + " GRADUATORIET.IDCOMUNE=BANDI.IDCOMUNE and GRADUATORIET.FK_BA_ID=BANDI.ID " + " and " + " BANDI.SOFTWARE=? and  BANDI.ID= ?  ";
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
	// permessi utente loggato
	if (filter.getUtenteLoggato() != null) {
	    if (filter.getUtenteLoggato().getId() != null) {
		if (filter.getUtenteLoggato().getId().getCodice() != null) {
		    // Se sto ricercando le pratiche che l'utente loggato deve trovare sul pannellino di accettazione
		    // non devo considereare i permessi 
		    // FUNZIONINALITA' ANTI - CORRUZIONE
		    if (!filter.getIsPraticheDaAccettareComeIstruttore()) {
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
		    }
		    if (filter.getIsPraticheDaAccettareComeIstruttore()) {
			result += " AND istruttoretemp.codiceresponsabile = ?  ";
			// aggiungere filtro resp proc
			parameters.add(new ParameterHelper(position, filter.getUtenteLoggato().getId().getCodice(), new IntegerType()));
			position++;
		    }
		}
	    }
	}
	// DATI DELLE AUTORIZZAZIONI
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
	//. FILTRO PROTOCOLLO PER RANGE DATA
	//.FILTRO PROTOCOLLO PER RANGE DATA : filtro impostato sulla data protocollo dell'istanza
	// se filtro per range imposto la variabile isSearchRangeDataProt=true, esclude successive ricerca per data
	boolean isSearchRangeDataProt = false;
	if (EntityUtils.getNestedProperty(filter, "dallaDataProtocollo") != null
		|| EntityUtils.getNestedProperty(filter, "allaDataProtocollo") != null) {
	    isSearchRangeDataProt = true;
	}
	boolean isSearchRangeDataProtMov = false;
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
		log.debug(
			"buildQuery# Impostato filtro ricerca : Range date protocollo nei movimenti. Controllo se sono state impostate le date, se almeno una " +
				"delle due date non è impostata non effettuo la ricerca");
		if (EntityUtils.getNestedProperty(filter, "dallaDataProtocollo") != null
			|| EntityUtils.getNestedProperty(filter, "allaDataProtocollo") != null) {
		    log.debug("buildQuery# Ricerca 'range date protocollo nei movimenti' : Impostata almeno una data del range");
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
			log.debug("buildQuery# Filtro data protocollo : Da {}", filter.getDallaDataProtocollo());
			isSearchRangeDataProt = true;
			result += " and mp.dataprotocollo >= ?";
			Calendar t = Calendar.getInstance();
			t.setTime(filter.getDallaDataProtocollo());
			parameters.add(new ParameterHelper(position, t.getTime(), new DateType()));
			position++;
		    }
		    if (EntityUtils.getNestedProperty(filter, "allaDataProtocollo") != null) {
			log.debug("buildQuery# Filtro data protocollo : A {}", filter.getDallaDataProtocollo());
			isSearchRangeDataProt = true;
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
	//. FILTRO PROTOCOLLO PER NUMERO
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
		if (filter.getDataprotocollo() != null && !isSearchRangeDataProtMov) {
		    result += " and  mp.dataprotocollo=? ";
		    parameters.add(new ParameterHelper(position, filter.getDataprotocollo(), new DateType()));
		    position++;
		}
		result += " ) ";
	    }
	    result += ") ";
	    // fine
	}
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
	// Soggetti dell'istanza (Ricerca per nominativo)
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
	// Piva CodiceFiscale Soggetti
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
	if (EntityUtils.getNestedProperty(filter, "tuttiResponsabili.id.codice") != null) {
	    result += " and ( istanze.codiceresponsabile = ? or istanze.codiceresponsabileproc = ? or istanze.codiceistruttore = ?) ";
	    parameters.add(new ParameterHelper(position, filter.getTuttiResponsabili().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getTuttiResponsabili().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getTuttiResponsabili().getId().getCodice(), new IntegerType()));
	    position++;
	}
	if (EntityUtils.getNestedProperty(filter, "responsabile.id.codice") != null) {
	    result += " and istanze.codiceresponsabile = ? ";
	    parameters.add(new ParameterHelper(position, filter.getResponsabile().getId().getCodice(), new IntegerType()));
	    position++;
	}
	if (EntityUtils.getNestedProperty(filter, "responsabileProcedimento.id.codice") != null) {
	    result += " and  istanze.codiceresponsabileproc = ? ";
	    parameters.add(new ParameterHelper(position, filter.getResponsabileProcedimento().getId().getCodice(), new IntegerType()));
	    position++;
	}
	if (EntityUtils.getNestedProperty(filter, "responsabileIstruttoria.id.codice") != null) {
	    result += " and istanze.codiceistruttore = ? ";
	    parameters.add(new ParameterHelper(position, filter.getResponsabileIstruttoria().getId().getCodice(), new IntegerType()));
	    position++;
	    result += "  ";
	}
	// DATI DELLA LOCALIZZAZIONE
	if (EntityUtils.getNestedProperty(filter, "istanzearee.id.codicearea") != null) {
	    result += " and exists (select 1 from " +
		    SCHEMA_NAME +
		    "istanzearee iaa " +
		    " where iaa.idcomune=istanze.idcomune and iaa.codiceistanza=istanze.codiceistanza ";
	    result += " and iaa.codicearea = ? ) ";
	    parameters.add(new ParameterHelper(position, filter.getIstanzearee().getId().getCodicearea(), new IntegerType()));
	    position++;
	}
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
	    if (EntityUtils.getNestedProperty(filter, "istanzestradario.stradario.id.codice") != null) {
		result += " and istr.codicestradario = ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getStradario().getId().getCodice(), new IntegerType()));
		position++;
	    }
	    //. RICERCA CIVICO PER SINGOLO CAMPO
	    //GIANPAOLO
	    // Il metodo controlla se è stata impostata la modalità range civico, è stato impostato
	    // solo uno dei due campi, nel caso ritorna il singolo valore che verrà utilizzato
	    /// per una ricerca classica
	    String singoloValoreCivicoRange = isRangeCampoCivicoPresente(filter);
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getCivico()) || StringUtils.isNotBlank(singoloValoreCivicoRange)) {
		result += " and lower(istr.civico) like ? ";
		if (StringUtils.isNotBlank(singoloValoreCivicoRange)) {
		    log.debug(
			    "buildQuery# Filtro civico : singolo campo {} - Ricerca tipo 'like' (Il valore è stato preso da uno dei campi di ricerca " +
				    "civico range in quanto è stato popolato solo uno) ",
			    singoloValoreCivicoRange);
		    parameters.add(new ParameterHelper(position, singoloValoreCivicoRange.toLowerCase().trim(), new StringType()));
		} else {
		    log.debug("buildQuery# Filtro civico : singolo campo {} - Ricerca tipo 'like' ",
			    filter.getIstanzestradario().getCivico().toLowerCase().trim());
		    parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getCivico().toLowerCase().trim(), new StringType()));
		}
		position++;
	    } else { //. RICERCA CIVICO PER RANGE DI CAMPI
		log.debug("buildQuery# Filtro civico : range campi - Ricerca tipo ... ");
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
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getKm())) {
		result += " and lower(istr.km) like ? ";
		log.debug("buildQuery# Filtro km : singolo campo {} - Ricerca tipo 'like' ",
			filter.getIstanzestradario().getKm().toLowerCase().trim());
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getKm().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getTransientDalKM())) {
		result += rangeKm(true);
		String kmSistematoANumero = sistemaKmFiltro(filter.getIstanzestradario().getTransientDalKM().toLowerCase().trim());
		log.debug("buildQuery# Filtro dal km : singolo campo {} - Ricerca tipo range {}",
			filter.getIstanzestradario().getTransientDalKM().toLowerCase().trim(), kmSistematoANumero);
		parameters.add(new ParameterHelper(position, kmSistematoANumero, new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getTransientAlKM())) {
		result += rangeKm(false);
		String kmSistematoANumero = sistemaKmFiltro(filter.getIstanzestradario().getTransientAlKM().toLowerCase().trim());
		log.debug("buildQuery# Filtro al km : singolo campo {} - Ricerca tipo range {}",
			filter.getIstanzestradario().getTransientDalKM().toLowerCase().trim(), kmSistematoANumero);
		parameters.add(new ParameterHelper(position, kmSistematoANumero, new StringType()));
		position++;
	    }
	    // RICERCHE PER COD VIARIO E DESCRIZIONE STRADARIO
	    if (StringUtils.isNotBlank(filter.getStradarioCodViario())) {
		result += " and stradario.codviario = ? ";
		parameters.add(new ParameterHelper(position, filter.getStradarioCodViario(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getStradarioDescrizione())) {
		result += " and trim(lower(" +
			applyConcatFunction("' '", new String[] { "stradario.prefisso", "stradario.descrizione" }) +
			")) like ? ";
		// result += " and stradario.descrizione like ? ";
		parameters.add(new ParameterHelper(position, filter.getStradarioDescrizione().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    ////////////
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getEsponente())) {
		result += " and lower(istr.esponente) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getEsponente().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getScala())) {
		result += " and lower(istr.scala) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getScala().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getPiano())) {
		result += " and lower(istr.piano) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getPiano().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getInterno())) {
		result += " and lower(istr.interno) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getInterno().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getEsponenteinterno())) {
		result += " and lower(istr.esponenteinterno) like ? ";
		parameters.add(
			new ParameterHelper(position, filter.getIstanzestradario().getEsponenteinterno().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getFabbricato())) {
		result += " and lower(istr.fabbricato) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getFabbricato().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getFrazione())) {
		result += " and lower(istr.frazione) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getFrazione().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getCap())) {
		result += " and lower(istr.cap) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getCap().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getQuartiere())) {
		result += " and lower(istr.quartiere) like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getQuartiere().toLowerCase().trim(), new StringType()));
		position++;
	    }
	    ////////////
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getCircoscrizione())) {
		result += " and lower(istr.circoscrizione) like ? ";
		parameters.add(new ParameterHelper(position, "%" + filter.getIstanzestradario().getCircoscrizione().toLowerCase().trim() + "%",
			new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getIstanzestradario().getNote())) {
		result += " and lower(istr.note) like ? ";
		parameters.add(
			new ParameterHelper(position, "%" + filter.getIstanzestradario().getNote().toLowerCase().trim() + "%", new StringType()));
		position++;
	    }
	    if (EntityUtils.getNestedProperty(filter, "istanzestradario.stradariocolore.id.codicecolore") != null) {
		if (StringUtils.isNotBlank(filter.getIstanzestradario().getStradariocolore().getId().getCodicecolore())) {
		    result += " and istr.colore = ? ";
		    parameters.add(new ParameterHelper(position, filter.getIstanzestradario().getStradariocolore().getId().getCodicecolore(),
			    new StringType()));
		    position++;
		}
	    }
	    if (!filter.isCercalocalizzazioneinaltri()) {
		result += " and istr.primario = ? ";
		parameters.add(new ParameterHelper(position, 1, new IntegerType()));
		position++;
	    }
	    result += " )";
	}
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
	    if (StringUtils.isNotBlank(filter.getIstanzemappali().getCatasto().getCodice())) {
		result += " and im.codicecatasto = ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzemappali().getCatasto().getCodice(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getIstanzemappali().getFoglio())) {
		result += " and im.foglio like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzemappali().getFoglio(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getIstanzemappali().getParticella())) {
		result += " and im.particella like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzemappali().getParticella(), new StringType()));
		position++;
	    }
	    if (StringUtils.isNotBlank(filter.getIstanzemappali().getSub())) {
		result += " and im.sub like ? ";
		parameters.add(new ParameterHelper(position, filter.getIstanzemappali().getSub(), new StringType()));
		position++;
	    }
	    result += " )";
	}
	//	// DATI PROGETTO
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
	if (filter.getIsPraticheDaAssegnareAdIstruttore()) {
	    result += " AND responsabileprocedimento.codiceresponsabile = ?  ";
	    // aggiungere filtro resp proc
	    parameters.add(new ParameterHelper(position, filter.getUtenteLoggato().getId().getCodice(), new IntegerType()));
	    position++;
	    result += " and grpistruttori.id is not null  ";
	    result += " and istruttoretemp.codiceresponsabile is null  ";
	    result += " and istruttore.codiceresponsabile is null  ";
	}
	// Condizione per verificare che ci siano pratiche in attesa di conferma di accettazione da parte 
	// del'utente loggato. La parte di codice che setta il codice dell'utente loggato nella query
	// si trova nella sezione che gestisce i filtri sui permessi
	if (filter.getIsPraticheDaAccettareComeIstruttore()) {
	    //	    result += " AND istruttoretemp.codiceresponsabile = ?  ";
	    //	    // aggiungere filtro resp proc
	    //	    parameters.add(new ParameterHelper(position, filter.getUtenteLoggato().getId().getCodice(), new IntegerType()));
	    //	    position++;
	    result += " and grpistruttori.id is not null  ";
	    result += " and istruttoretemp.codiceresponsabile is not null  ";
	    result += " and istruttore.codiceresponsabile is null  ";
	}
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
	    if (StringUtils.isNotBlank(filter.getSoggettiIstanzaFilterCF().getIstanzerichiedentisCF())) {
		// TODO MI FERMO QUI CHE' HO PAURA
		//		result += " exists (select 1 from " + SCHEMA_NAME
		//			+ "istanzerichiedenti ir where ir.idcomune=istanze.idcomune and ir.codiceistanza=istanze.codiceistanza "
		//			+ " and  (ir.codicerichiedente = ? or ir.codiceanagrafecoll = ? or ir.codiceprocuratore = ? )";
		//		parameters.add(new ParameterHelper(position, filter.getSoggettiIstanzaFilterCF().getIstanzerichiedentisCF().toLowerCase(), new StringType()));
		//		position++;
		//		parameters.add(new ParameterHelper(position, filter.getSoggettiIstanzaFilterCF().getIstanzerichiedentisCF().toLowerCase(), new StringType()));
		//		position++;
		//		parameters.add(new ParameterHelper(position, filter.getSoggettiIstanzaFilterCF().getIstanzerichiedentisCF().toLowerCase(), new StringType()));
		//		position++;
		//		result += " ) or ";
	    }
	    result += " 1=0)";
	}
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
	Boolean flagEscludiRisultatiRicerca = filter.getFlagEscludiRisultatiDaRicercaPubblica();
	if (flagEscludiRisultatiRicerca != null && BooleanUtils.isTrue(flagEscludiRisultatiRicerca)) {
	    String codiceSoftware = (isFilterModuloNotNullAndFilterCodiceSoftwareNotBlankAndNotEqualsToTT()) ? filter.getModulo().getCodice()
		    : ORMHelper.getSoftware();
	    List<Alberoproc> listaInterventi = getListaInterventi(codiceSoftware);
	    if (listaInterventi != null && listaInterventi.size() > 0) {
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
	if (filter.getSchedaDinamicaFilter() != null && filter.getSchedaDinamicaFilter().getScheda() != null
		&& !filter.getSchedaDinamicaFilter().getRighe().isEmpty()) {
	    MutableInt posRef = new MutableInt(position);
	    result += createSQLFragment("istanze.", "CODICEISTANZA", "CODICEISTANZA", "ISTANZEDYN2DATI", schemaName, filter.getSchedaDinamicaFilter(),
		    posRef);
	}
	
	if (StringUtils.isNotBlank(schemaName)) {
	    result = result.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), result);
	return result;
    }

    private String sistemaKmFiltro(String numero) {

	return numero.replace(",", ".").replace("+", ".").replace("/", ".");
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

    private List<Alberoproc> getListaInterventi(String codiceSoftware) {

	List<Alberoproc> listInterventi = new ArrayList<Alberoproc>();
	FilterTable ftable = (isRicercaSuTuttiIdComune()) ? new FilterTable(DAOEnum.FIND_ALL) : new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction filterRestriction = new FilterRestriction();
	if (codiceSoftware != null && codiceSoftware.length() > 0) {
	    filterRestriction.addFilterField(FilterUtils.equals("software.codice", codiceSoftware, String.class));
	}
	filterRestriction.addFilterField(FilterUtils.equals("flagEscludiRisultatiRicerca", true, Boolean.class));
	ftable.addRestriction(filterRestriction);
	listInterventi = alberoprocDAO.findByFilterTable(ftable, null, null);
	return listInterventi;
    }

    private boolean isFilterModuloNotNullAndFilterCodiceSoftwareNotBlankAndNotEqualsToTT() {

	return filter.getModulo() != null && StringUtils.isNotBlank(filter.getModulo().getCodice())
		&& !StringUtils.defaultString(filter.getModulo().getCodice()).equalsIgnoreCase(WebConstants.SOFTWARE_TT);
    }

    /**
     * Setta i valori a seconda del filtro. DEVE Essere chiamato obbligatoriamente dopo aver invocato
     * {@link QueryIstanzeComMassHelper}{@link #buildQuery()}
     * 
     * @param q
     */
    @Override
    public void setFilterValues(SQLQuery q) {

	int position = 0;
	String debugParam = "param {}={}";
	log.debug(debugParam, position, 1);
	q.setInteger(position, 1); // PRIMARIO=1
	position++;
	if (!isRicercaSuTuttiIdComune()) {
	    // sullo specifico comune
	    log.debug(debugParam, position, ORMHelper.getIdcomune());
	    q.setString(position, ORMHelper.getIdcomune()); // IDCOMUNE
	}
	for (ParameterHelper parameter : parameters) {
	    log.debug(debugParam, parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	if(helperTypeEnum == HelperTypeEnum .ISTANZE){
	    q.addScalar("codiceIstanza", Hibernate.INTEGER);
	    q.addScalar("codicerichiedente", Hibernate.INTEGER);
	    q.addScalar("codiceprofessionista", Hibernate.INTEGER);
	    q.addScalar("codicetitolarelegale", Hibernate.INTEGER);
	}else if(helperTypeEnum == HelperTypeEnum .SOFTWAREANDCOMUNE){
	    q.addScalar("software", Hibernate.STRING);
	    q.addScalar("codicecomune", Hibernate.STRING);
	}else{
	    throw new RuntimeException("No valid helperTypeEnum");
	}
	
    }

    private static final Logger log = LoggerFactory.getLogger(QueryIstanzeComMassHelper.class);
    private AlberoprocDAO alberoprocDAO;
    private StatiistanzaDAO statiistanzaDAO;
    private ComuniassociatiService comuniassociatiService;
    private VerticalizzazioniService verticalizzazioniService;
    private IstanzeFilter filter;
    private HelperTypeEnum helperTypeEnum;

    /**
     * <pre>
     * la porsione di query viene gestita dal parametro COMPORTAMENTO_ISTANZE.VIS_ICONA_A_IN_LISTAISTANZE
     * 
     * 1. Non attiva o vuota 	: countautorizzazioni --> numero di tutte le conc/aut collegate all'istanza
     * 2. SOLO_ATTIVE		: countautorizzazioni --> numero di tutte le conc/aut collegate all'istanza attive
     * &#64;return
     * </pre>
     */
    private String queryPerLogicaVisIconaA() {

	log.debug("queryPerLogicaVisIconaA# Verifico se è attiva la visualizzazione dell'icona 'A' solo se autorizzazione/concessioni sono attive");
	String queryCountSoloAutConAttive = "";
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE)) {
	    Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE,
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_COMPORTAMENTO_VIS_ICONA_A_IN_LISTAISTANZE);
	    if (vp != null && StringUtils.isNotBlank(vp.getValore()) && "SOLO_ATTIVE".equals(vp.getValore())) {
		log.debug("queryPerLogicaVisIconaA# {}.{}: {}", new Object[] { WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE,
			WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_COMPORTAMENTO_VIS_ICONA_A_IN_LISTAISTANZE, vp.getValore() });
		queryCountSoloAutConAttive = " and autorizzazioni.flag_attiva = 1 ";
	    }
	}
	return queryCountSoloAutConAttive;
    }

    private String fromQuery = " from" +
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
	    "istanzestradario on istanze.idcomune=istanzestradario.idcomune and istanze.codiceistanza=istanzestradario.codiceistanza and istanzestradario.primario=?" +
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
    private String whereQuery = " where istanze.idcomune=?";
    private String whereQueryNoIdComune = " where 1=1 ";

    /**
     * Il metodo controlla se entrambi i campi del range sono popolati, nel caso sia popolato solamente uno ritorna il
     * valore che sarà utilizzato per la ricerca classica con singolo campo
     * 
     * @return
     */
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

    /**
     * Torna TRUE se ho passato come condizione di ricercare sull-intera base dati indipendentemente da IDCOMUNE ovvero
     * istanzeFilter#setDefaultWhereCondition(DAOEnum.FIND_ALL);
     * 
     * @return
     */
    private boolean isRicercaSuTuttiIdComune() {

	return this.filter.getDefaultWhereCondition() != null
		&& DAOEnum.FIND_ALL.name().equalsIgnoreCase(this.filter.getDefaultWhereCondition().name());
    }
}