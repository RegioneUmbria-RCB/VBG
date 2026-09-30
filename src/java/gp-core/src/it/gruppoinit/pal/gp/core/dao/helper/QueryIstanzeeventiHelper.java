package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.DateType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.ResponsabiliDAO;
import it.gruppoinit.pal.gp.core.dao.SoftwareDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;

public class QueryIstanzeeventiHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryIstanzeeventiHelper.class);

    public QueryIstanzeeventiHelper(BatchScadenzarioFilter filter, SessionFactoryImplementor sessimpl, AlberoprocDAO alberoprocDAO,
	    SoftwareDAO softwareDAO, ResponsabiliDAO responsabiliDAO, ComuniassociatiService comuniassociatiService, boolean isCountQuery,
	    boolean consideraDate) {

	this.consideraDate = consideraDate;
	this.filter = filter;
	log.debug("QueryIstanzeeventiHelper: recupero il dialetto della SessionFactoryImplementor");
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryIstanzeeventiHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryIstanzeeventiHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryIstanzeeventiHelper: schemaName={}", schemaName);
	this.alberoprocDAO = alberoprocDAO;
	this.softwareDAO = softwareDAO;
	this.responsabiliDAO = responsabiliDAO;
	this.comuniassociatiService = comuniassociatiService;
	this.isCountQuery = isCountQuery;
	// DEVO INIZIALIZZARLA QUI SE NON APPLYSIMPLENVLFUNCTION NON HA IL DIALETTO INIZIALIZZATO E DA' ERRORE
	this.selectQuery = "select istanzeeventi.idevento as id," //
		+ "istanzeeventi.idcomune as idcomune," + "istanzeeventi.fkidcategoriaevento," //
		+ "categorieeventibase.descrizione as categoria," //
		+ "istanzeeventi.data as dataevento," + "istanzeeventi.descrizione as evento," //
		+
		"istanzeeventi.flag_letto as flag_letto," +
		applySimpleNVLFunction("istanzaevento.codiceistanza", "istanzamovimento.codiceistanza") +
		" as codiceistanza," +
		applySimpleNVLFunction("istanzaevento.numeroistanza", "istanzamovimento.numeroistanza") +
		" as numeroistanza," //
		+
		applySimpleNVLFunction("softwareistanza.codice", "softwaremovimento.codice") +
		" as codicesoftware," +
		applySimpleNVLFunction("softwareistanza.descrizione", "softwaremovimento.descrizione") +
		" as descrizionesoftware," +
		"movimenti.codicemovimento as codicemovimento," // 
		+
		" movimenti.movimento as movimento," //
		+
		" movimenti.tipomovimento as tipomovimento, "// 
		+
		" richiedente.nome as nomeRichiedente, " +
		" richiedente.nominativo as nominativoRichiedente, "//
		+
		" titolarelegale.nome as nomeTitolareLegale, " //
		+
		" titolarelegale.nominativo as nominativoTitolareLegale, "//
		+
		" tipisoggetto.tiposoggetto as inQualitaDi ";
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	int _position = 0;
	log.debug("param {}={}", _position, ORMHelper.getIdcomune());
	q.setString(_position, ORMHelper.getIdcomune()); // IDCOMUNE
	_position++;
	for (ParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("id", Hibernate.BIG_DECIMAL);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("fkidcategoriaevento", Hibernate.STRING);
	q.addScalar("categoria", Hibernate.STRING);
	q.addScalar("dataevento");
	q.addScalar("evento", Hibernate.STRING);
	q.addScalar("flag_letto", Hibernate.INTEGER);
	q.addScalar("codiceistanza", Hibernate.INTEGER);
	q.addScalar("numeroistanza", Hibernate.STRING);
	q.addScalar("codicesoftware", Hibernate.STRING);
	q.addScalar("descrizionesoftware", Hibernate.STRING);
	q.addScalar("codicemovimento", Hibernate.INTEGER);
	q.addScalar("movimento", Hibernate.STRING);
	q.addScalar("tipomovimento", Hibernate.STRING);
	q.addScalar("nomeRichiedente", Hibernate.STRING);
	q.addScalar("nominativoRichiedente", Hibernate.STRING);
	q.addScalar("nomeTitolareLegale", Hibernate.STRING);
	q.addScalar("nominativoTitolareLegale", Hibernate.STRING);
	q.addScalar("inQualitaDi", Hibernate.STRING);
    }

    @Override
    public String buildQuery() {

	String result = isCountQuery == true ? countQuery : selectQuery;
	result += fromQuery + whereQuery;
	int position = 1;
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	//______  _____  _    _____ ______  _____   _____  _   _  _____  ______ _____  _____ 
	//|  ___||_   _|| |  |_   _|| ___ \|_   _| |_   _|| \ | ||_   _||___  /|_   _||  _  |
	//| |_     | |  | |    | |  | |_/ /  | |     | |  |  \| |  | |     / /   | |  | | | |
	//|  _|    | |  | |    | |  |    /   | |     | |  | . ` |  | |    / /    | |  | | | |
	//| |     _| |_ | |____| |  | |\ \  _| |_   _| |_ | |\  | _| |_ ./ /___ _| |_ \ \_/ /
	//\_|     \___/ \_____/\_/  \_| \_| \___/   \___/ \_| \_/ \___/ \_____/ \___/  \___/ 
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	if (filter.getFlagLetto() != null) {
	    result += " and (istanzeeventi.flag_letto = ? ) ";
	    int letto = 0;
	    if (filter.getFlagLetto().booleanValue()) {
		letto = 1;
	    }
	    parameters.add(new ParameterHelper(position, letto, new IntegerType()));
	    position++;
	}
	if (this.consideraDate) {
	    if (filter.getDallaData() != null && filter.getAllaData() != null) {
		// mostrare solo le scadenze dell'intervallo
		result += " and (istanzeeventi.data between ? and ?) ";
		parameters.add(new ParameterHelper(position, filter.getDallaData(), new DateType()));
		position++;
		parameters.add(new ParameterHelper(position, filter.getAllaData(), new DateType()));
		position++;
	    } else {
		if (filter.getDallaData() != null) {
		    // mostrare solo le scadenze dalla data in poi
		    result += " and (istanzeeventi.data >= ?) ";
		    parameters.add(new ParameterHelper(position, filter.getDallaData(), new DateType()));
		    position++;
		}
		if (filter.getAllaData() != null) {
		    // mostrare solo le scadenze fino alla data
		    result += " and (istanzeeventi.data <= ?) ";
		    parameters.add(new ParameterHelper(position, filter.getAllaData(), new DateType()));
		    position++;
		}
	    }
	}
	if (StringUtils.isNotBlank(filter.getNumeroIstanza())) {
	    result += " and (lower(istanzaevento.numeroistanza) like ? or lower(istanzamovimento.numeroistanza) like ?)";
	    parameters.add(new ParameterHelper(position, filter.getNumeroIstanza().trim().toLowerCase() + "%", new StringType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getNumeroIstanza().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	boolean isAmministratore = false;
	boolean isAmministratoreSoftware = false;
	boolean isOperatoreSettato = false;
	Responsabili responsabile = null;
	if (filter.getUtenteLoggato().getId().getCodice() != null) {
	    responsabile = responsabiliDAO.findById(new PkId(filter.getUtenteLoggato().getId().getCodice()));
	    if (responsabile != null) {
		isOperatoreSettato = true;
		isAmministratore = StringUtils.defaultIfEmpty(responsabile.getAmministratore(), "0").equalsIgnoreCase("1") ? true : false;
		isAmministratoreSoftware = StringUtils.defaultIfEmpty(responsabile.getAmministratoresoftware(), "0").equalsIgnoreCase("1") ? true
			: false;
		if (!(isAmministratore || isAmministratoreSoftware)) {
		    result += " and ( ( exists (select 1 from " +
			    SCHEMA_NAME +
			    "permistanze pis " +
			    " where pis.idcomune=istanzaevento.idcomune and pis.codiceistanza=istanzaevento.codiceistanza ";
		    result += " and pis.codiceresponsabile = ?) or exists (select 1 from " +
			    SCHEMA_NAME +
			    "permistanze pis2 " +
			    " where pis2.idcomune=istanzamovimento.idcomune and pis2.codiceistanza=istanzamovimento.codiceistanza ";
		    result += " and pis2.codiceresponsabile = ?) ";
		    parameters.add(new ParameterHelper(position, responsabile.getId().getCodice(), new IntegerType()));
		    position++;
		    parameters.add(new ParameterHelper(position, responsabile.getId().getCodice(), new IntegerType()));
		    position++;
		    result += " ) or ";
		    result += "  ( exists (select 1 from " +
			    SCHEMA_NAME +
			    "vw_istanze_ope_ruoli vwior " +
			    " where vwior.idcomune=istanzaevento.idcomune and vwior.codiceistanza=istanzaevento.codiceistanza ";
		    result += " and vwior.codiceresponsabile = ?) or  exists (select 1 from " +
			    SCHEMA_NAME +
			    "vw_istanze_ope_ruoli vwior2 " +
			    " where vwior2.idcomune=istanzamovimento.idcomune and vwior2.codiceistanza=istanzamovimento.codiceistanza ";
		    result += " and vwior2.codiceresponsabile = ?) ";
		    parameters.add(new ParameterHelper(position, responsabile.getId().getCodice(), new IntegerType()));
		    position++;
		    parameters.add(new ParameterHelper(position, responsabile.getId().getCodice(), new IntegerType()));
		    position++;
		    result += " ) )";
		}
	    }
	}
	if (EntityUtils.getNestedProperty(filter.getResponsabile(), "id.codice") != null) {
	    result += " and ((istanzaevento.codiceresponsabile = ? or istanzaevento.codiceistruttore=? or istanzaevento.codiceresponsabileproc=?) or " +
		    "(istanzamovimento.codiceresponsabile = ? or istanzamovimento.codiceistruttore=? or istanzamovimento.codiceresponsabileproc=?)) ";
	    parameters.add(new ParameterHelper(position, filter.getResponsabile().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getResponsabile().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getResponsabile().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getResponsabile().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getResponsabile().getId().getCodice(), new IntegerType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getResponsabile().getId().getCodice(), new IntegerType()));
	    position++;
	}
	String filtroSoftware = EntityUtils.getNestedProperty(filter.getScadSoftware(), "codice") == null ? null
		: filter.getScadSoftware().getCodice();
	if (!StringUtils.defaultIfEmpty(filtroSoftware, WebConstants.SOFTWARE_TT).equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
	    result += " and (istanzaevento.software = ? or istanzamovimento.software = ?) ";
	    parameters.add(new ParameterHelper(position, filter.getScadSoftware().getCodice(), new StringType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getScadSoftware().getCodice(), new StringType()));
	    position++;
	} else {
	    if (ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
		// BOCCI 2011-11-16 SE SPECIFICATO L'OPERATORE DEVO RICERCARE NON IN TUTTI I SOFTWARE ATTIVI X IL COMUNE
		// MA IN QUELLI ABILITATI PER L'OPERATORE
		// se la chiamata arriva da TT allora recupero le istanze per tutti i software attivi
		List<Software> softwareAttiviList = new ArrayList<Software>();
		if (isOperatoreSettato && responsabile != null) {
		    softwareAttiviList = softwareDAO.findSoftwareAbilitati(responsabile, false);
		} else {
		    softwareAttiviList = softwareDAO.findSoftwareAttivi(false);
		}
		if (!softwareAttiviList.isEmpty()) {
		    result += " and (istanzaevento.software in  (";
		    String softQm = "";
		    for (int i = 0; i < softwareAttiviList.size(); i++) {
			Software softwareAttivo = (Software) softwareAttiviList.get(i);
			softQm += ",?";
			parameters.add(new ParameterHelper(position, softwareAttivo.getCodice(), new StringType()));
			position++;
		    }
		    softQm = softQm.replaceFirst(",", "");
		    result += softQm + ") or istanzamovimento.software in (";
		    softQm = "";
		    for (int i = 0; i < softwareAttiviList.size(); i++) {
			Software softwareAttivo = (Software) softwareAttiviList.get(i);
			softQm += ",?";
			parameters.add(new ParameterHelper(position, softwareAttivo.getCodice(), new StringType()));
			position++;
		    }
		    softQm = softQm.replaceFirst(",", "");
		    result += softQm + "))";
		}
	    } else {
		result += " and (istanzaevento.software = ? or istanzamovimento.software = ?) ";
		parameters.add(new ParameterHelper(position, ORMHelper.getSoftware(), new StringType()));
		position++;
		parameters.add(new ParameterHelper(position, ORMHelper.getSoftware(), new StringType()));
		position++;
	    }
	}
	// TODO
	//	if (filter.getIntervento().getId().getCodice() != null) {
	//	    Alberoproc alberoproc = alberoprocDAO.findById(new PkId(filter.getIntervento().getId().getCodice()));
	//	    result += " and (vw_alberoproc.sc_codice like = ?) ";
	//	    parameters.add(new ParameterHelper(position, alberoproc.getScCodice() + "%", new StringType()));
	//	    position++;
	//	}
	List<Statiistanza> statiIstanza = filter.getStatiIstanza();
	if (statiIstanza != null && !statiIstanza.isEmpty()) {
	    String[] stati = new String[statiIstanza.size()];
	    result += " and (istanzaevento.chiusura in  (";
	    String statiQm = "";
	    for (int i = 0; i < statiIstanza.size(); i++) {
		Statiistanza statoIstanza = (Statiistanza) statiIstanza.get(i);
		stati[i] = statoIstanza.getId().getCodicestato();
		statiQm += ",?";
		parameters.add(new ParameterHelper(position, stati[i], new StringType()));
		position++;
	    }
	    statiQm = statiQm.replaceFirst(",", "");
	    result += statiQm + ") or istanzamovimento.chiusura in (";
	    statiQm = "";
	    for (int i = 0; i < statiIstanza.size(); i++) {
		Statiistanza statoIstanza = (Statiistanza) statiIstanza.get(i);
		stati[i] = statoIstanza.getId().getCodicestato();
		statiQm += ",?";
		parameters.add(new ParameterHelper(position, stati[i], new StringType()));
		position++;
	    }
	    statiQm = statiQm.replaceFirst(",", "");
	    result += statiQm + "))";
	}
	if (filter.getScadComportamento() != null) {
	    if (filter.getScadComportamento().equals(Integer.valueOf(0))) {
		result += " and (chiusuraistanzaevento.fkcodcomportamento=? or chiusuraistanzamovimento.fkcodcomportamento=?) ";
		parameters.add(new ParameterHelper(position, 0, new IntegerType()));
		position++;
		parameters.add(new ParameterHelper(position, 0, new IntegerType()));
		position++;
	    } else {
		result += " and (chiusuraistanzaevento.fkcodcomportamento in (?,?) or chiusuraistanzamovimento.fkcodcomportamento in (?,?)) ";
		parameters.add(new ParameterHelper(position, 1, new IntegerType()));
		position++;
		parameters.add(new ParameterHelper(position, -1, new IntegerType()));
		position++;
		parameters.add(new ParameterHelper(position, 1, new IntegerType()));
		position++;
		parameters.add(new ParameterHelper(position, -1, new IntegerType()));
		position++;
	    }
	}
	//
	// Filtro per codice comune, se è un installazione multi comune, l'operatore deve vedere solo le scadenze per i comuni 
	// per cui è abilitato.
	if (log.isDebugEnabled()) {
	    log.debug("buildQuery# Controllo se si tratta di un installazione con idcomune {} è multi comune", ORMHelper.getIdcomune());
	}
	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	if (isComuniAssociati) {
	    if (log.isDebugEnabled()) {
		log.debug("buildQuery# E' un installazione multicomune, recupero i comuni configurati per l'opertaore {} ({})",
			new Object[] { responsabile.getResponsabile(), responsabile.getId().getCodice() });
	    }
	    List<Responsabilicomuni> responsabilicomunis = comuniassociatiService.checkComuniAbilitatiPerResponsabile(false);
	    if (responsabilicomunis != null && !responsabilicomunis.isEmpty()) {
		String[] codicicomune = new String[responsabilicomunis.size()];
		result += " and ((istanzaevento.codicecomune in  (";
		String comuniattiviQm = "";
		for (int i = 0; i < responsabilicomunis.size(); i++) { // ciclo istanzeeventi * istanza
		    Responsabilicomuni responsabilicomunimuni = (Responsabilicomuni) responsabilicomunis.get(i);
		    codicicomune[i] = responsabilicomunimuni.getId().getCodicecomune();
		    comuniattiviQm += ",?";
		    parameters.add(new ParameterHelper(position, codicicomune[i], new StringType()));
		    position++;
		}
		for (int i = 0; i < responsabilicomunis.size(); i++) { // ciclo istanzeeventi per movimento
		    Responsabilicomuni responsabilicomunimuni = (Responsabilicomuni) responsabilicomunis.get(i);
		    codicicomune[i] = responsabilicomunimuni.getId().getCodicecomune();
		    parameters.add(new ParameterHelper(position, codicicomune[i], new StringType()));
		    position++;
		}
		comuniattiviQm = comuniattiviQm.replaceFirst(",", "");
		result += comuniattiviQm +
			") or istanzaevento.codicecomune is null) and (istanzamovimento.codicecomune in (" +
			comuniattiviQm +
			") or istanzamovimento.codicecomune is null))";
	    }
	}
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	//______  _____  _    _____ ______  _____  ______  _____  _   _  _____ 
	//|  ___||_   _|| |  |_   _|| ___ \|_   _| |  ___||_   _|| \ | ||  ___|
	//| |_     | |  | |    | |  | |_/ /  | |   | |_     | |  |  \| || |__  
	//|  _|    | |  | |    | |  |    /   | |   |  _|    | |  | . ` ||  __| 
	//| |     _| |_ | |____| |  | |\ \  _| |_  | |     _| |_ | |\  || |___ 
	//\_|     \___/ \_____/\_/  \_| \_| \___/  \_|     \___/ \_| \_/\____/ 
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	//	  _____ ______ ______  _____  _   _   ___  ___  ___ _____  _   _  _____  _____ 
	//	 |  _  || ___ \|  _  \|_   _|| \ | | / _ \ |  \/  ||  ___|| \ | ||_   _||_   _|
	//	 | | | || |_/ /| | | |  | |  |  \| |/ /_\ \| .  . || |__  |  \| |  | |    | |  
	//	 | | | ||    / | | | |  | |  | . ` ||  _  || |\/| ||  __| | . ` |  | |    | |  
	//	 \ \_/ /| |\ \ | |/ /  _| |_ | |\  || | | || |  | || |___ | |\  |  | |   _| |_ 
	//	  \___/ \_| \_||___/   \___/ \_| \_/\_| |_/\_|  |_/\____/ \_| \_/  \_/   \___/ 
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	if (!isCountQuery) {
	    result += " order by ";
	    result += " dataevento desc ";
	}
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	if (StringUtils.isNotBlank(schemaName)) {
	    result = result.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), result);
	return result;
    }

    private boolean consideraDate = false;
    private AlberoprocDAO alberoprocDAO;
    private SoftwareDAO softwareDAO;
    private ResponsabiliDAO responsabiliDAO;
    private ComuniassociatiService comuniassociatiService;
    private BatchScadenzarioFilter filter;
    private String countQuery = "select count(*) as conteggio_eventi ";
    private String selectQuery = ""; // inizializzata nel costruttore della classe
    private String fromQuery = " from " + SCHEMA_NAME + "istanzeeventi istanzeeventi " // 
	    +
	    " left join " +
	    SCHEMA_NAME +
	    "categorieeventibase categorieeventibase on " +
	    " istanzeeventi.fkidcategoriaevento=categorieeventibase.id " //
	    +
	    " LEFT OUTER JOIN " +
	    SCHEMA_NAME +
	    "istanze istanzaevento " // 
	    +
	    " ON istanzeeventi.codiceistanza=istanzaevento.codiceistanza " +
	    " AND istanzeeventi.idcomune=istanzaevento.idcomune " // 
	    +
	    " LEFT OUTER JOIN " +
	    SCHEMA_NAME +
	    "statiistanza chiusuraistanzaevento " +
	    " ON istanzaevento.chiusura =chiusuraistanzaevento.codicestato " // 
	    +
	    " AND istanzaevento.idcomune=chiusuraistanzaevento.idcomune " // 
	    +
	    " and istanzaevento.software=chiusuraistanzaevento.software " +
	    " LEFT OUTER JOIN " +
	    SCHEMA_NAME +
	    "software softwareistanza " //
	    +
	    " on istanzaevento.software=softwareistanza.codice " +
	    " LEFT OUTER JOIN " +
	    SCHEMA_NAME +
	    "movimenti movimenti " //
	    +
	    " ON istanzeeventi.codicemovimento=movimenti.codicemovimento " +
	    " and istanzeeventi.idcomune =movimenti.idcomune " // 
	    +
	    " LEFT OUTER JOIN " +
	    SCHEMA_NAME +
	    "istanze istanzamovimento " +
	    " ON movimenti.codiceistanza=istanzamovimento.codiceistanza " //
	    +
	    " and movimenti.idcomune =istanzamovimento.idcomune " //
	    +
	    " LEFT OUTER JOIN " +
	    SCHEMA_NAME +
	    "statiistanza chiusuraistanzamovimento " +
	    " ON istanzamovimento.chiusura =chiusuraistanzamovimento.codicestato " +
	    " AND istanzamovimento.idcomune=chiusuraistanzamovimento.idcomune " +
	    " and istanzamovimento.software=chiusuraistanzamovimento.software " //
	    +
	    " left outer join " //
	    +
	    SCHEMA_NAME +
	    "software softwaremovimento "//
	    +
	    " ON istanzamovimento.software =softwaremovimento.codice " //
	    +
	    " LEFT OUTER JOIN " +
	    SCHEMA_NAME +
	    "anagrafe richiedente "//
	    +
	    " ON istanzaevento.codicerichiedente =richiedente.codiceanagrafe "//
	    +
	    " AND istanzaevento.idcomune=richiedente.idcomune " //
	    +
	    " LEFT OUTER JOIN " //
	    +
	    SCHEMA_NAME +
	    "anagrafe titolarelegale "//
	    +
	    " ON istanzaevento.CODICETITOLARELEGALE =titolarelegale.codiceanagrafe " //
	    +
	    " AND istanzaevento.idcomune=titolarelegale.idcomune " +
	    " LEFT OUTER JOIN " //
	    +
	    SCHEMA_NAME +
	    "tipisoggetto "//
	    +
	    " ON istanzaevento.FKCODICESOGGETTO =tipisoggetto.codicetiposoggetto " //
	    +
	    " AND istanzaevento.idcomune=tipisoggetto.idcomune ";
    private String whereQuery = " where istanzeeventi.idcomune=? ";
}
