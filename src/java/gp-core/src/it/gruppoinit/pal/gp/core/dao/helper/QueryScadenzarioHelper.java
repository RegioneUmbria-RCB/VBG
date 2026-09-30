package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
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
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmSca;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;

public class QueryScadenzarioHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryScadenzarioHelper.class);

    public QueryScadenzarioHelper(BatchScadenzarioFilter filter, SessionFactoryImplementor sessimpl, AlberoprocDAO alberoprocDAO,
	    SoftwareDAO softwareDAO, ResponsabiliDAO responsabiliDAO, ComuniassociatiService comuniassociatiService, boolean isCountQuery) {

	this.filter = filter;
	log.debug("QueryScadenzarioHelper: recupero il dialetto della SessionFactoryImplementor");
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryScadenzarioHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryScadenzarioHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryScadenzarioHelper: schemaName={}", schemaName);
	this.alberoprocDAO = alberoprocDAO;
	this.softwareDAO = softwareDAO;
	this.responsabiliDAO = responsabiliDAO;
	this.comuniassociatiService = comuniassociatiService;
	this.isCountQuery = isCountQuery;
	// DEVO INIZIALIZZARLA QUI SE NON APPLYSIMPLENVLFUNCTION NON HA IL DIALETTO INIZIALIZZATO E DA' ERRORE
	this.countQuery = "select " + applyHintIndexOracle() + " count(*) as conteggio_scadenze";
	this.selectQuery = "select "//
		+ applyHintIndexOracle() + " mdf.codicemovimento as id," //
		+ " mdf.idcomune as idcomune," + " istanze.software as software, "//
		+ " software.descrizione as softwaredescrizione," //
		+ " istanze.codiceistanza as codiceistanza," // 
		+ " istanze.numeroistanza as numeroistanza," //
		+ " istanze.data as dataistanza, " //
		+ " comuni.comune as comune, "//
		+
		" istanze.codicerichiedente as codicerichiedente," +
		" richiedente.nominativo as richiedentenominativo," +
		" richiedente.nome as richiedentenome," +
		" richiedente.codicefiscale as richiedentecodicefiscale," +
		" richiedente.partitaiva as richiedentepartitaiva," +
		" istanze.fk_richiedentestorico_id as richstoricoid," +
		" richiedentestorico.nominativo as richstoriconominativo," +
		" richiedentestorico.nome as richstoriconome," +
		" richiedentestorico.codicefiscale as richstoricocodicefiscale," +
		" richiedentestorico.partitaiva as richstoricopartitaiva," +
		" istanze.chiusura as codicestato,"//
		+
		" statiistanza.stato as stato," //
		+
		" istanze_tempistica.datafine as termineprocedimento, " //
		+
		" alberoproc.descrizione_completa as intervento," +
		" mf.codicemovimento as codicemovimento," +
		applySimpleNVLFunction("mf.movimento", "tmf.movimento") +
		" as descrmovimento," +
		" mdf.tipomovimento as tipomovimentodafare," +
		applySimpleNVLFunction("mdf.movimento", "tm.movimento") +
		" as descrmovimentodafare," +
		" mdf.codiceinventario as codiceinventario," +
		" ipdf.procedimento as endoprocedimento,"//
		+
		" mdf.codiceamministrazione as codiceamministrazione," //
		+
		" ammdf.amministrazione as amministrazione," //
		+
		" mdf.data_scadenza as datascadenza, " //
		+
		" inqualita.tiposoggetto as tiposoggetto, "//
		+
		" azienda.codiceanagrafe as codiceazienda, " //
		+
		" azienda.nominativo as aziendanominativo," //
		+
		" azienda.nome as aziendanome," //
		+
		" azienda.codicefiscale as aziendacodicefiscale," //
		+
		" azienda.partitaiva as aziendapartitaiva," //
		+
		" istanze.fk_titolarelegalestorico_id as azstoricoid," //
		+
		" azstorico.nominativo as azstoriconominativo," // //
		+
		" azstorico.nome as azstoriconome," //
		+
		" azstorico.codicefiscale as azstoricocodicefiscale," //
		+
		" azstorico.partitaiva as azstoricopartitaiva, " +
		" istanze.posizionearchivio as posizionearchivio, " +
		" responsabili.responsabile as responsabile, " //
		+
		" tipiprocedure.procedura as procedura, " //
		+
		" istruttori.responsabile as istruttore ";
    }

    private String applyHintIndexOracle() {

	if (DialettoEnum.ORACLE.equals(_dialetto)) {
	    return "  "; // AL MOMENTO SEMBRA CHE IN CONTESTI CON UN SOLO COMUNE PEGGIORI DI MOLTO LA SITUAZIONE
	}
	return " ";
    }

    private String applyMovimentiHintMySQL() {

	if (DialettoEnum.MYSQL.equals(_dialetto)) {
	    if (calcolaNoEsclude()) {
		return " FORCE INDEX(IDX_MOVIMENTI_006) ";
	    }
	    return " FORCE INDEX(IDX_MOVIMENTI_004) ";
	}
	return " ";
    }

    @Override
    public String buildQuery() {

	String result = isCountQuery == true ? countQuery : selectQuery;
	result += fromQuery() + whereQuery;
	int position = 2;
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
	boolean filtraPerData = false;
	if (filter.getDallaData() != null && filter.getAllaData() != null) {
	    // mostrare solo le scadenze dell'intervallo
	    filtraPerData = true;
	    result += " and ((mdf.data_scadenza between ? and ?) ";
	    parameters.add(new ParameterHelper(position, filter.getDallaData(), new DateType()));
	    position++;
	    parameters.add(new ParameterHelper(position, filter.getAllaData(), new DateType()));
	    position++;
	} else {
	    if (filter.getDallaData() != null) {
		filtraPerData = true;
		// mostrare solo le scadenze dalla data in poi
		result += " and ((mdf.data_scadenza >= ?) ";
		parameters.add(new ParameterHelper(position, filter.getDallaData(), new DateType()));
		position++;
	    }
	    if (filter.getAllaData() != null) {
		filtraPerData = true;
		// mostrare solo le scadenze fino alla data
		result += " and ((mdf.data_scadenza <= ?) ";
		parameters.add(new ParameterHelper(position, filter.getAllaData(), new DateType()));
		position++;
	    }
	}
	if (filtraPerData) {
	    result += " or mdf.data_scadenza is null ) ";
	} else {
	    result += "";
	}
	if (StringUtils.isNotBlank(filter.getNumeroIstanza())) {
	    result += " and lower(istanze.numeroistanza) like ? ";
	    parameters.add(new ParameterHelper(position, filter.getNumeroIstanza().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	if (filter.getMovimentiFilter() != null && StringUtils.isNotBlank(filter.getMovimentiFilter().getNumeroistanza())) {
	    result += " and lower(istanze.numeroistanza) like ? ";
	    parameters
		    .add(new ParameterHelper(position, filter.getMovimentiFilter().getNumeroistanza().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	if (filter.getMovimentiFilter() != null && StringUtils.isNotBlank(filter.getMovimentiFilter().getTipomovimento())) {
	    result += " and lower(" + applySimpleNVLFunction("mdf.movimento", "tm.movimento") + ") like ? ";
	    parameters.add(
		    new ParameterHelper(position, "%" + filter.getMovimentiFilter().getTipomovimento().trim().toLowerCase() + "%", new StringType()));
	    position++;
	}
	// Filtro per richiedente
	if (filter.getMovimentiFilter() != null && StringUtils.isNotBlank(filter.getMovimentiFilter().getRichiedentenominativo())) {
	    result += " and  (";
	    result += " lower(" + applyConcatFunction("' '", new String[] { "richiedente.nominativo", "richiedente.nome" }) + ") like ?";
	    parameters.add(new ParameterHelper(position, filter.getMovimentiFilter().getRichiedentenominativo().toLowerCase().trim() + "%",
		    new StringType()));
	    position++;
	    result += " OR ";
	    result += " lower(" + applyConcatFunction("' '", new String[] { "azienda.nominativo", "azienda.nome" }) + ") like ? ";
	    parameters.add(new ParameterHelper(position, filter.getMovimentiFilter().getRichiedentenominativo().toLowerCase().trim() + "%",
		    new StringType()));
	    position++;
	    result += ") ";
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
		    result += " and ( exists (select 1 from " +
			    SCHEMA_NAME +
			    "permistanze pis " +
			    " where pis.idcomune=istanze.idcomune and pis.codiceistanza=istanze.codiceistanza ";
		    result += " and pis.codiceresponsabile = ? ";
		    parameters.add(new ParameterHelper(position, responsabile.getId().getCodice(), new IntegerType()));
		    position++;
		    result += " ) or ";
		    result += "  exists ( ";
		    result += "select 1 from " + //
			      SCHEMA_NAME + //
			      "responsabiliruoli rr join " + SCHEMA_NAME + //
			      "istanzeruoli ir on rr.idcomune = ir.idcomune and rr.idruolo = ir.idruolo " + //
			      " inner join " + SCHEMA_NAME + "ruoli r on (r.idcomune = ir.idcomune) and (r.id = ir.idruolo) " + // 
			      "  and (r.readonly = ? or r.flag_gestmovimenti = ? or r.flag_disgestmovamm = ?) " + // 
			      " where ir.idcomune=istanze.idcomune and ir.codiceistanza = istanze.codiceistanza and rr.codiceresponsabile = ? ";
		    parameters.add(new ParameterHelper(position, 0, new IntegerType()));
		    position++;
		    parameters.add(new ParameterHelper(position, 1, new IntegerType()));
		    position++;
		    parameters.add(new ParameterHelper(position, 1, new IntegerType()));
		    position++;
		    parameters.add(new ParameterHelper(position, responsabile.getId().getCodice(), new IntegerType()));
		    position++;
		    result += " ) )";
		}
	    }
	}
	if (EntityUtils.getNestedProperty(filter.getResponsabile(), "id.codice") != null) {
	    result += " and (istanze.codiceresponsabile = ? or istanze.codiceistruttore=? or istanze.codiceresponsabileproc=?) ";
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
	    result += " and (istanze.software = ? ) ";
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
		    result += " and (istanze.software in  (";
		    String softQm = "";
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
		result += " and (istanze.software = ? ) ";
		parameters.add(new ParameterHelper(position, ORMHelper.getSoftware(), new StringType()));
		position++;
	    }
	}
	if (filter.getIntervento().getId().getCodice() != null) {
	    Alberoproc alberoproc = alberoprocDAO.findById(new PkId(filter.getIntervento().getId().getCodice()));
	    result += " and (alberoproc.sc_codice like ?) ";
	    parameters.add(new ParameterHelper(position, alberoproc.getScCodice() + "%", new StringType()));
	    position++;
	}
	if (StringUtils.isNotBlank(filter.getTipoMovimentoFatto().getId().getTipomovimento())) {
	    result += " and (mf.tipomovimento=?) ";
	    parameters.add(new ParameterHelper(position, filter.getTipoMovimentoFatto().getId().getTipomovimento(), new StringType()));
	    position++;
	}
	if (StringUtils.isNotBlank(filter.getTipoMovimentoDaFare().getId().getTipomovimento())) {
	    result += " and (mdf.tipomovimento=?) ";
	    parameters.add(new ParameterHelper(position, filter.getTipoMovimentoDaFare().getId().getTipomovimento(), new StringType()));
	    position++;
	}
	// Limita la ricerca delle scadenze "DA EFFETTUARE" ai seguenti tipi movimento
	if (calcolaNoEsclude()) {
	    result += " and (mdf.tipomovimento in  (";
	    String tmsQm = "";
	    for (ResponsabiliTmSca tms : filter.getTipimovimentoSca()) {
		if (BooleanUtils.isFalse(tms.getFlagEsclude())) {
		    tmsQm += ",?";
		    parameters.add(new ParameterHelper(position, tms.getId().getTipomovimento(), new StringType()));
		    position++;
		}
	    }
	    tmsQm = tmsQm.replaceFirst(",", "");
	    result += tmsQm + "))";
	}
	// Esclude dalla ricerca delle scadenze "DA EFFETTUARE" i seguenti tipi movimento	
	if (calcolaEsclude()) {
	    result += " and (mdf.tipomovimento not in  (";
	    String tmsQm = "";
	    for (ResponsabiliTmSca tms : filter.getTipimovimentoSca()) {
		if (BooleanUtils.isTrue(tms.getFlagEsclude())) {
		    tmsQm += ",?";
		    parameters.add(new ParameterHelper(position, tms.getId().getTipomovimento(), new StringType()));
		    position++;
		}
	    }
	    tmsQm = tmsQm.replaceFirst(",", "");
	    result += tmsQm + "))";
	}
	List<Statiistanza> statiIstanza = filter.getStatiIstanza();
	if (statiIstanza != null && !statiIstanza.isEmpty()) {
	    String[] stati = new String[statiIstanza.size()];
	    result += " and (istanze.chiusura in  (";
	    String statiQm = "";
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
		result += " and (statiistanza.fkcodcomportamento=?) ";
		parameters.add(new ParameterHelper(position, 0, new IntegerType()));
		position++;
	    } else {
		result += " and (statiistanza.fkcodcomportamento in (?,?)) ";
		parameters.add(new ParameterHelper(position, 1, new IntegerType()));
		position++;
		parameters.add(new ParameterHelper(position, -1, new IntegerType()));
		position++;
	    }
	}
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
	    String ascDesc = "desc";
	    if (filter.getOrdinamentoScadenze() != null) {
		ascDesc = StringUtils.defaultIfEmpty(filter.getOrdinamentoScadenze().toString().toLowerCase(), "desc");
	    }
	    result += " datascadenza " + ascDesc;
	    result += ", softwaredescrizione asc";
	    result += "," + applyLpadFunction("numeroistanza", "20", "' '") + " asc ";
	}
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	if (StringUtils.isNotBlank(schemaName)) {
	    result = result.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), result);
	return result;
    }

    private boolean calcolaEsclude() {

	if (filter.getSoloScadenzeImportanti() != null && filter.getSoloScadenzeImportanti().booleanValue() && filter.getTipimovimentoSca() != null) {
	    for (ResponsabiliTmSca tms : filter.getTipimovimentoSca()) {
		if (BooleanUtils.isTrue(tms.getFlagEsclude())) {
		    return true;
		}
	    }
	}
	return false;
    }

    private boolean calcolaNoEsclude() {

	if (filter.getSoloScadenzeImportanti() != null && filter.getSoloScadenzeImportanti().booleanValue() && filter.getTipimovimentoSca() != null) {
	    // Verifico se la Lista di ResponsabiliTmSca contiene elementi con flagEsclude=FALSE
	    for (ResponsabiliTmSca tms : filter.getTipimovimentoSca()) {
		if (BooleanUtils.isFalse(tms.getFlagEsclude())) {
		    return true;
		}
	    }
	}
	return false;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	int _position = 0;
	log.debug("param {}={}", _position, ORMHelper.getIdcomune());
	q.setString(_position, ORMHelper.getIdcomune()); // IDCOMUNE
	_position++;
	// mdf.flag_disabilitato=?
	log.debug("param {}={}", _position, 0);
	q.setInteger(_position, 0); // mdf.flag_disabilitato=0
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
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("softwaredescrizione", Hibernate.STRING);
	q.addScalar("codiceistanza", Hibernate.INTEGER);
	q.addScalar("numeroistanza", Hibernate.STRING);
	q.addScalar("dataistanza");
	q.addScalar("comune", Hibernate.STRING);
	q.addScalar("codicerichiedente", Hibernate.INTEGER);
	q.addScalar("richiedentenominativo", Hibernate.STRING);
	q.addScalar("richiedentenome", Hibernate.STRING);
	q.addScalar("richiedentecodicefiscale", Hibernate.STRING);
	q.addScalar("richiedentepartitaiva", Hibernate.STRING);
	q.addScalar("richstoricoid", Hibernate.INTEGER);
	q.addScalar("richstoriconominativo", Hibernate.STRING);
	q.addScalar("richstoriconome", Hibernate.STRING);
	q.addScalar("richstoricocodicefiscale", Hibernate.STRING);
	q.addScalar("richstoricopartitaiva", Hibernate.STRING);
	q.addScalar("codicestato", Hibernate.STRING);
	q.addScalar("stato", Hibernate.STRING);
	q.addScalar("intervento", Hibernate.STRING);
	q.addScalar("codicemovimento", Hibernate.INTEGER);
	q.addScalar("descrmovimento", Hibernate.STRING);
	q.addScalar("tipomovimentodafare", Hibernate.STRING);
	q.addScalar("descrmovimentodafare", Hibernate.STRING);
	q.addScalar("codiceinventario", Hibernate.INTEGER);
	q.addScalar("endoprocedimento", Hibernate.STRING);
	q.addScalar("codiceamministrazione", Hibernate.INTEGER);
	q.addScalar("amministrazione", Hibernate.STRING);
	q.addScalar("datascadenza");
	q.addScalar("tiposoggetto", Hibernate.STRING);
	q.addScalar("codiceazienda", Hibernate.INTEGER);
	q.addScalar("aziendanominativo", Hibernate.STRING);
	q.addScalar("aziendanome", Hibernate.STRING);
	q.addScalar("aziendacodicefiscale", Hibernate.STRING);
	q.addScalar("aziendapartitaiva", Hibernate.STRING);
	q.addScalar("azstoricoid", Hibernate.INTEGER);
	q.addScalar("azstoriconominativo", Hibernate.STRING);
	q.addScalar("azstoriconome", Hibernate.STRING);
	q.addScalar("azstoricocodicefiscale", Hibernate.STRING);
	q.addScalar("azstoricopartitaiva", Hibernate.STRING);
	q.addScalar("posizionearchivio", Hibernate.STRING);
	q.addScalar("procedura", Hibernate.STRING);
	q.addScalar("termineprocedimento");
	q.addScalar("responsabile", Hibernate.STRING);
	q.addScalar("istruttore", Hibernate.STRING);
    }

    private AlberoprocDAO alberoprocDAO;
    private SoftwareDAO softwareDAO;
    private ResponsabiliDAO responsabiliDAO;
    private ComuniassociatiService comuniassociatiService;
    private BatchScadenzarioFilter filter;
    private String countQuery = " ";
    private String selectQuery = ""; // inizializzata nel costruttore della classe

    private String fromQuery() {

	return " from " + //
		SCHEMA_NAME + //
		"movimenti mdf " +
		applyMovimentiHintMySQL() + //
		"inner join " + //
		SCHEMA_NAME + //
		"istanze " +
		applyIstanzeHintMySQL() +
		" on istanze.idcomune=mdf.idcomune " + // + //
		"and istanze.codiceistanza=mdf.codiceistanza " + //
		" inner join " + //
		SCHEMA_NAME + //
		"anagrafe richiedente " + //
		" on istanze.idcomune=richiedente.idcomune " + //
		"and istanze.codicerichiedente=richiedente.codiceanagrafe " + //
		" left join " + //
		SCHEMA_NAME + //
		"anagrafestorico richiedentestorico " + //
		" on istanze.idcomune=richiedentestorico.idcomune" + //
		" and istanze.fk_richiedentestorico_id=richiedentestorico.id " + //
		" inner join " + //
		SCHEMA_NAME + //
		"alberoproc " + //
		" on istanze.idcomune=alberoproc.idcomune " + //
		" and istanze.codiceinterventoproc=alberoproc.sc_id " + //
		" inner join " + //
		SCHEMA_NAME + //
		"software " + //
		" on istanze.software=software.codice " + //
		" left join " //
		// Comune
		+ //
		SCHEMA_NAME + //
		" comuni " + //
		" on istanze.codicecomune=comuni.codicecomune " + //
		" left join " //
		// Comune end
		+ //
		SCHEMA_NAME + //
		"statiistanza on istanze.idcomune=statiistanza.idcomune and istanze.software=statiistanza.software and istanze.chiusura=statiistanza.codicestato " + //
		" left join " //
		// istanzeTempistica //
		+ //
		SCHEMA_NAME + //
		"istanze_tempistica on istanze.idcomune=istanze_tempistica.idcomune and istanze.codiceistanza=istanze_tempistica.codiceistanza " + //
		" left join " //
		// Responsabili
		+ //
		SCHEMA_NAME + //
		"responsabili on istanze.idcomune=responsabili.idcomune and istanze.codiceresponsabile=responsabili.codiceresponsabile " + //
		" inner join "// 	   
		+ //
		SCHEMA_NAME + //
		"tipimovimento tm on tm.idcomune=mdf.idcomune and tm.tipomovimento=mdf.tipomovimento " + //
		" left join " //
		+ //
		SCHEMA_NAME + //
		"inventarioprocedimenti ipdf on mdf.idcomune=ipdf.idcomune and mdf.codiceinventario=ipdf.codiceinventario " + //
		" left join " //
		+ //
		SCHEMA_NAME + //
		"amministrazioni ammdf on mdf.idcomune=ammdf.idcomune and mdf.codiceamministrazione=ammdf.codiceamministrazione " + //
		"left join " //
		+ //
		SCHEMA_NAME + //
		"movimenti_contromovimenti mcm on mcm.idcomune=mdf.idcomune and mcm.codicecontromovimento=mdf.codicemovimento " + //
		"left join "// 
		+ //
		SCHEMA_NAME + //
		"movimenti mf on mf.idcomune=mcm.idcomune and mf.codicemovimento=mcm.codicemovimento " // 
		+ //
		"left join " //
		+ //
		SCHEMA_NAME + //
		"tipimovimento tmf on tmf.idcomune=mf.idcomune and tmf.tipomovimento= mf.tipomovimento " // 
		+ //
		" left join " //
		+ //
		SCHEMA_NAME //
		+ //
		"tipisoggetto inqualita " //
		+ //
		" on istanze.idcomune=inqualita.idcomune " //
		+ //
		"and istanze.fkcodicesoggetto=inqualita.codicetiposoggetto " //
		+ //
		" left join " //
		+ //
		SCHEMA_NAME //
		+ //
		"anagrafe azienda " //
		+ //
		" on istanze.idcomune=azienda.idcomune " //
		+ //
		" and istanze.codicetitolarelegale=azienda.codiceanagrafe " //
		+ //
		" left join " //
		+ //
		SCHEMA_NAME //
		+ //
		" anagrafestorico azstorico " // // //
		+ //
		" on istanze.idcomune=azstorico.idcomune" //
		+ //
		" and istanze.fk_titolarelegalestorico_id=azstorico.id " //
		+ //
		" left join " + //
		SCHEMA_NAME //
		+ //
		" tipiprocedure  " //
		+ //
		" on istanze.idcomune=tipiprocedure.idcomune " //
		+ //
		" and istanze.codiceprocedura = tipiprocedure.codiceprocedura "//
		+ //
		" left join "
		// istruttori
		+ //
		SCHEMA_NAME + //
		"responsabili istruttori on istanze.idcomune=istruttori.idcomune and istanze.codiceistruttore=istruttori.codiceresponsabile ";
    }

    private String applyIstanzeHintMySQL() {

	if (DialettoEnum.MYSQL.equals(_dialetto)) {
	    return " FORCE INDEX(PRIMARY) ";
	}
	return " ";
    }

    private String whereQuery = " where mdf.idcomune=? and mdf.data is null and (mdf.flag_disabilitato is null or mdf.flag_disabilitato=?) ";
}
