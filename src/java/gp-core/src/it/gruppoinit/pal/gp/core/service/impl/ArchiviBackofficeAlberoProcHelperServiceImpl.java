package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoId;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentipeople;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.StpCategorieEndo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo1;
import it.gruppoinit.pal.gp.core.domain.Tempificazioni;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ArchiviBackofficeAlberoProcHelperService;
import it.gruppoinit.pal.gp.core.service.AzioniService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentipeopleService;
import it.gruppoinit.pal.gp.core.service.NaturaendoService;
import it.gruppoinit.pal.gp.core.service.NatureProcedureService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StpCategorieEndo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.StpTipologieEndo1Service;
import it.gruppoinit.pal.gp.core.service.TempificazioniService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.utils.LoggerArchiviBackoffice;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArchiviBackofficeAlberoProcHelperServiceImpl implements ArchiviBackofficeAlberoProcHelperService {

    private static final Logger log = LoggerFactory.getLogger(ArchiviBackofficeAlberoProcHelperServiceImpl.class);
    @Autowired
    private AlberoprocDAO alberoprocDAO;
    @Autowired
    private StpTipologieEndo1Service stpTipologieEndo1Service;
    @Autowired
    private StpCategorieEndo1Service stpCategorieEndo1Service;
    @Autowired
    private StpEndoTipo1Service stpEndoTipo1Service;
    @Autowired
    private InventarioprocedimentipeopleService inventarioprocedimentipeopleService;
    @Autowired
    private TipifamiglieendoService tipifamiglieendoService;
    @Autowired
    private TipiendoService tipiendoService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private TempificazioniService tempificazioniService;
    @Autowired
    private NaturaendoService naturaendoService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private StpEndoTipo2Service stpEndoTipo2Service;
    @Autowired
    private AlberoprocEndoService alberoprocEndoService;
    @Autowired
    private AzioniService azioniService;
    @Autowired
    private TipiprocedureService tipiprocedureService;
    @Autowired
    private NatureProcedureService natureProcedureService;

    @Override
    public void insertOrUpdateAlberoProcSistemaEsterno(String codiceSistemaEsterno, String descrizione, String codicenaturaProcedura,
	    String idNodoPadre, boolean isAggiorna, Software sw) {

	log.debug("insertOrUpdateAlberoProcSistemaEsterno# Nodo albero proveniente da sistema esterno. Codice STP = {}", codiceSistemaEsterno);
	Alberoproc apPadre = null;
	if (StringUtils.isBlank(idNodoPadre)) {
	    log.debug("insertOrUpdateAlberoProcSistemaEsterno# Il nodo con codice  stp = {}, non ha padre", codiceSistemaEsterno);
	    ////////////////////////////////////// SEZIONE INSERIMENTO NODO SENZA PADRE //////////////////////////////////////////////////////////
	    StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findbyStpCodiceAndSoftwareAlberoProc(Integer.parseInt(codiceSistemaEsterno),
		    sw.getCodice());
	    if (EntityUtils.getNestedProperty(stpEndoTipo2, "id.codice") != null) {
		log.debug(
			"insertOrUpdateAlberoProcSistemaEsterno# NODO codice STP = {} e software (alberoproc) = {}  già inserito e mappato. Codice backoffice = {}",
			new Object[] { stpEndoTipo2.getCodiceStp(), sw.getCodice(), stpEndoTipo2.getAlberoproc().getId().getCodice() });
		if (isAggiorna) {
		    // FIXME devo estrarre quello per il software in esame
		    Alberoproc alberoproc = alberoprocService.findById(new PkId(stpEndoTipo2.getAlberoproc().getId().getCodice()));
		    alberoproc.setScDescrizione(descrizione);
		    //		    // setto procedura se presente
		    if (StringUtils.isNotBlank(codicenaturaProcedura)) {
			Tipiprocedure tp = natureProcedureService.findByNatura(codicenaturaProcedura);
			if (EntityUtils.getNestedProperty(tp, "id.codice") != null) {
			    tp = tipiprocedureService.findById(new PkId(tp.getId().getCodice()));
			    alberoproc.setTipoProcedura(tp);
			}
		    }
		    alberoprocService.update(alberoproc);
		}
	    } else {
		log.debug(
			"insertOrUpdateAlberoProcSistemaEsterno# NODO codice STP = {}, software = {} non mappato e non inserito. Inserisco come AreaPrimaria",
			codiceSistemaEsterno, sw.getCodice());
		insertAlberoProc(codiceSistemaEsterno, descrizione, codicenaturaProcedura, null, sw);
	    }
	} else {
	    /////////////////////////////////////////////////// SEZIONE INSERIMENTO NODO CON PADRE //////////////////////////////////////////////////////
	    log.debug("insertOrUpdateAlberoProcSistemaEsterno# NODO codiceStp = {}, software = {} con padre = {}.", new Object[] {
		    codiceSistemaEsterno, sw.getCodice(), idNodoPadre });
	    StpEndoTipo2 stpEndoTipo2NodoFiglio = stpEndoTipo2Service.findbyStpCodiceAndSoftwareAlberoProc(Integer.parseInt(codiceSistemaEsterno),
		    sw.getCodice());
	    if (EntityUtils.getNestedProperty(stpEndoTipo2NodoFiglio, "id.codice") != null) {
		if (isAggiorna) {
		    log.debug(
			    "insertOrUpdateAlberoProcSistemaEsterno# NODO codice STP = {} e software (alberoproc) = {}  già inserito e mappato. Codice backoffice = {}",
			    new Object[] { stpEndoTipo2NodoFiglio.getCodiceStp(), sw.getCodice(),
				    stpEndoTipo2NodoFiglio.getAlberoproc().getId().getCodice() });
		    Alberoproc alberoproc = alberoprocService.findById(new PkId(stpEndoTipo2NodoFiglio.getAlberoproc().getId().getCodice()));
		    alberoproc.setScDescrizione(descrizione);
		    // setto procedura se presente
		    if (StringUtils.isNotBlank(codicenaturaProcedura)) {
			Tipiprocedure tp = natureProcedureService.findByNatura(codicenaturaProcedura);
			if (EntityUtils.getNestedProperty(tp, "id.codice") != null) {
			    tp = tipiprocedureService.findById(new PkId(tp.getId().getCodice()));
			    alberoproc.setTipoProcedura(tp);
			}
		    }
		    alberoprocService.update(alberoproc);
		}
	    } else {
		log.debug("insertOrUpdateAlberoProcSistemaEsterno# NODO codice STP = {}, software (albero proc) = {} non mappato e non inserito.",
			codiceSistemaEsterno, sw.getCodice());
		//StpEndoTipo2 stpendo2 = stpEndoTipo2Service.findbyStpCodice(Integer.parseInt(idNodoPadre));
		StpEndoTipo2 stpendo2 = stpEndoTipo2Service.findbyStpCodiceAndSoftwareAlberoProc(Integer.parseInt(idNodoPadre), sw.getCodice());
		//			// il get dava problema di java assist
		if (EntityUtils.getNestedProperty(stpendo2, "id.codice") != null) {
		    apPadre = alberoprocService.findById(new PkId(stpendo2.getAlberoproc().getId().getCodice()));
		    insertAlberoProc(codiceSistemaEsterno, descrizione, codicenaturaProcedura, apPadre, sw);
		} else {
		    LoggerArchiviBackoffice.logArchiviBackofficeERROR("Non è stato possibile inserire il nodo con codice stp = {}"
			    + codiceSistemaEsterno + " Descrizione = " + descrizione + ". Padre con codice stp = " + idNodoPadre + "non trovato.");
		}
	    }
	}
    }

    @Override
    public Tipifamiglieendo insertOrUpdateTipiFamiglieEndoSistemaEsterno(String codiceSistemaEsterno, String descrizione, boolean isAggiorna,
	    Software sw) {

	Tipifamiglieendo tfe = null;
	log.debug(
		"insertOrUpdateTipiFamiglieEndoSistemaEsterno# CASO TFE 1: Passato codice famiglia endo del sistema esterno. CodiceStp = {},Software (famiglie endo) = {}",
		codiceSistemaEsterno, sw.getCodice());
	StpTipologieEndo1 stpTipologieEndo1 = stpTipologieEndo1Service.findbyStpCodiceAndSoftwareTipiFamEndo(Integer.parseInt(codiceSistemaEsterno),
		sw.getCodice());
	if (EntityUtils.getNestedProperty(stpTipologieEndo1, "id.codice") != null) {
	    log.debug(
		    "insertOrUpdateTipiFamiglieEndoSistemaEsterno# Tipo famiglia endo presente e mappato. CodiceStp = {},Software (famiglie endo) = {}, CodiceBackoffice = {}   ",
		    new Object[] { stpTipologieEndo1.getCodiceStp(), sw.getCodice(), stpTipologieEndo1.getTipifamiglieendo().getId().getCodice() });
	    if (isAggiorna) {
		tfe = tipifamiglieendoService.findById(new PkId(stpTipologieEndo1.getTipifamiglieendo().getId().getCodice()));
		tfe.setTipo(descrizione);
		tipifamiglieendoService.update(tfe);
	    }
	} else {
	    log.debug("insertOrUpdateTipiFamiglieEndoSistemaEsterno# Tipo famiglia endo non presente. CodiceStp = {}, software (famiglie endo) = {}",
		    codiceSistemaEsterno, sw.getCodice());
	    tfe = insertTipiFamigliaEndo(codiceSistemaEsterno, descrizione, sw);
	}
	return tfe;
    }

    @Override
    public Tipiendo insertOrUpdateTipiCategorieEndoSistemaEsterno(String codiceSistemaEsterno, String descrizione, boolean isAggiorna,
	    Tipifamiglieendo tipifamiglieendo, Software sw) {

	Tipiendo tipiEndo = null;
	log.debug(
		"insertOrUpdateTipiCategorieEndoSistemaEsterno# CASO TCE 1: Passato codice categoria endo del sistema esterno. CodiceStp = {}, Software(tipi endo) = {}",
		codiceSistemaEsterno, sw.getCodice());
	StpCategorieEndo1 stpCategorieEndo1 = stpCategorieEndo1Service.findByStpCodiceAndSoftwareTipiEndo(Integer.parseInt(codiceSistemaEsterno),
		sw.getCodice());
	if (EntityUtils.getNestedProperty(stpCategorieEndo1, "id.codice") != null) {
	    log.debug(
		    "insertOrUpdateTipiCategorieEndoSistemaEsterno# Tipo categoria endo presente e mappato. CodiceStp = {}, Software(tipi endo) = {}, CodiceBackoffice = {}  ",
		    new Object[] { stpCategorieEndo1.getCodiceStp(), sw.getCodice(), stpCategorieEndo1.getTipiendo().getId().getCodice() });
	    if (isAggiorna) {
		tipiEndo = tipiendoService.findById(new PkId(stpCategorieEndo1.getTipiendo().getId().getCodice()));
		tipiEndo.setTipo(descrizione);
		tipiendoService.update(tipiEndo);
	    }
	} else {
	    log.debug("insertOrUpdateTipiCategorieEndoSistemaEsterno# Tipo categoria endo non presente. CodiceStp = {}, software (tipi endo) = {}",
		    new Object[] { codiceSistemaEsterno, sw.getCodice() });
	    tipiEndo = insertTipiendo(codiceSistemaEsterno, descrizione, sw, tipifamiglieendo);
	}
	return tipiEndo;
    }

    @Override
    public Inventarioprocedimenti insertOrUpdateInventarioProcedimentiSistemaEsterno(String codiceSistemaEsterno, String descrizione, String prefix,
	    Tipiendo tipiEndo, Tempificazioni tempificazione, Naturaendo naturaendo, Amministrazioni amministrazione, boolean isAggiorna, Software sw) {

	Inventarioprocedimenti inventarioprocedimenti = null;
	log.debug("insertOrUpdateInventarioProcedimentiSistemaEsterno# CASO ENDOPROC 1: Passato codice endo del sistema esterno. CodiceStp = {}",
		codiceSistemaEsterno);
	Inventarioprocedimentipeople inventarioprocedimentipeople = inventarioprocedimentipeopleService.findByCodiceSTP(
		prefix + codiceSistemaEsterno, sw.getCodice());
	if (EntityUtils.getNestedProperty(inventarioprocedimentipeople, "id.codice") != null) {
	    log.debug("insertOrUpdateInventarioProcedimentiSistemaEsterno# Endo presente e mappato. CodiceStp = {}, CodiceBackoffice = {}  ",
		    new Object[] { inventarioprocedimentipeople.getCodProcPeople(),
			    inventarioprocedimentipeople.getInventarioprocedimenti().getId().getCodice() });
	    if (isAggiorna) {
		inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(inventarioprocedimentipeople.getInventarioprocedimenti()
			.getId().getCodice()));
		inventarioprocedimenti.setProcedimento(descrizione);
		inventarioprocedimentiService.update(inventarioprocedimenti);
	    }
	} else {
	    log.debug("insertOrUpdateInventarioProcedimentiSistemaEsterno# Endo non presente. CodiceStp = {}", new Object[] { codiceSistemaEsterno });
	    insertInventarioprocedimenti(codiceSistemaEsterno, descrizione, prefix, sw, tipiEndo, amministrazione, tempificazione, naturaendo);
	}
	return inventarioprocedimenti;
    }

    @Override
    public boolean insertOrUpdateAlberoprocEndoSistemaEsterno(String codiceSistemaEsternoAlberoProc, String codiceSistemaEsternoInventarioproc,
	    String prefix, boolean flagPrincipale, boolean flagPubblicato, boolean flagRichiesto, boolean flagUsaNelback, String azAzione, Software sw) {

	boolean isErroriPresenti = false;
	log.debug(
		"insertOrUpdateAlberoprocEndoSistemaEsterno# Recupero la voce dell'albero inserita e mappata con codiceStp = {}, software (alberoproc) = {}",
		codiceSistemaEsternoAlberoProc, sw.getCodice());
	//StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findbyStpCodice(Integer.parseInt(codiceSistemaEsternoAlberoProc));
	StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findbyStpCodiceAndSoftwareAlberoProc(Integer.parseInt(codiceSistemaEsternoAlberoProc),
		sw.getCodice());
	if (EntityUtils.getNestedProperty(stpEndoTipo2, "id.codice") != null) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(stpEndoTipo2.getAlberoproc().getId().getCodice()));
	    Inventarioprocedimentipeople inventarioprocedimentipeople = inventarioprocedimentipeopleService.findByCodiceSTP(prefix
		    + codiceSistemaEsternoInventarioproc, sw.getCodice());
	    if (EntityUtils.getNestedProperty(inventarioprocedimentipeople, "id.codice") != null) {
		Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(inventarioprocedimentipeople
			.getInventarioprocedimenti().getId().getCodice()));
		it.gruppoinit.pal.gp.core.domain.AlberoprocEndo alberoprocEndoBACKOFFICE = alberoprocEndoService.findById(new AlberoprocEndoId(
			ORMHelper.getIdcomune(), alberoproc.getId().getCodice(), inventarioprocedimenti.getId().getCodice()));
		if (alberoprocEndoBACKOFFICE != null && alberoprocEndoBACKOFFICE.getId() != null
			&& alberoprocEndoBACKOFFICE.getId().getCodiceinventario() != null && alberoprocEndoBACKOFFICE.getId().getFkscid() != null) {
		} else {
		    insertAlberoProcEndo(alberoproc, inventarioprocedimenti, flagPrincipale, flagPubblicato, flagRichiesto, flagUsaNelback, azAzione);
		}
	    } else {
		LoggerArchiviBackoffice.logArchiviBackofficeERROR("Errore durante il collegamento dell' endoprocedimento" + "CodiceStp  = "
			+ codiceSistemaEsternoAlberoProc + ". Non è stato trovato/mappato nel backoffice");
		isErroriPresenti = true;
	    }
	} else {
	    LoggerArchiviBackoffice.logArchiviBackofficeERROR("Errore durante il collegamento dell'endoprocedimento" + "CodiceStp  = "
		    + codiceSistemaEsternoInventarioproc + ". Non è stata trovata la voce dell'albero " + "CodiceStp = "
		    + codiceSistemaEsternoAlberoProc);
	    isErroriPresenti = true;
	}
	return isErroriPresenti;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////// SEZIONE METODI PRIVATI ////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Alberoproc insertAlberoProc(String codiceAlberoProcSistemaEsterno, String descrizione, String codicenaturaPorcedura,
	    Alberoproc nodoPadre, Software sw) {

	log.debug("insertAlberoProc# record albero proc codiceSistemaEsterno = {}", codiceAlberoProcSistemaEsterno);
	Alberoproc apFilgio = new Alberoproc();
	apFilgio.setScDescrizione(descrizione);
	if (EntityUtils.getNestedProperty(nodoPadre, "id.codice") != null) {
	    apFilgio.setAreaPrimaria(false);
	} else {
	    apFilgio.setAreaPrimaria(true);
	}
	apFilgio.setSoftware(sw);
	// setto procedura se presente
	if (StringUtils.isNotBlank(codicenaturaPorcedura)) {
	    Tipiprocedure tp = natureProcedureService.findByNatura(codicenaturaPorcedura);
	    if (EntityUtils.getNestedProperty(tp, "id.codice") != null) {
		tp = tipiprocedureService.findById(new PkId(tp.getId().getCodice()));
		apFilgio.setTipoProcedura(tp);
	    }
	}
	if (EntityUtils.getNestedProperty(nodoPadre, "id.codice") != null) {
	    alberoprocService.insertAlberoproc(apFilgio, nodoPadre);
	} else {
	    alberoprocService.insertAlberoproc(apFilgio, null);
	}
	if (StringUtils.isNotBlank(codiceAlberoProcSistemaEsterno)) {
	    log.debug("insertAlberoProc# Creo mapping su StpEndoTipo2. Codice stp = {}, Codice backoffice = {}", codiceAlberoProcSistemaEsterno,
		    apFilgio.getId().getCodice());
	    StpEndoTipo2 stpEndoTipo2 = new StpEndoTipo2();
	    stpEndoTipo2.setCodiceStp(Integer.parseInt(codiceAlberoProcSistemaEsterno));
	    stpEndoTipo2.setAlberoproc(apFilgio);
	    stpEndoTipo2Service.insert(stpEndoTipo2);
	}
	alberoprocDAO.flush();
	alberoprocDAO.commit();
	log.debug("insertAlberoProc# Insert record Albero proc ok.....");
	return apFilgio;
    }

    private Tipifamiglieendo insertTipiFamigliaEndo(String codiceSistemaEsterno, String descrizione, Software sw) {

	log.debug("insertTipiFamigliaEndo# Insert Tipo famiglia endo codiceSistemaEsterno = {}", codiceSistemaEsterno);
	Tipifamiglieendo tfe = new Tipifamiglieendo();
	tfe.setSoftware(sw);
	tfe.setTipo(descrizione);
	tipifamiglieendoService.insert(tfe);
	if (StringUtils.isNotBlank(codiceSistemaEsterno)) {
	    StpTipologieEndo1 mapping = new StpTipologieEndo1();
	    mapping.setCodiceStp(Integer.parseInt(codiceSistemaEsterno));
	    mapping.setTipifamiglieendo(tfe);
	    stpTipologieEndo1Service.insert(mapping);
	}
	alberoprocDAO.flush();
	alberoprocDAO.commit();
	log.debug("insertTipiFamigliaEndo# Insert Tipo famiglia endo ok.....");
	return tfe;
    }

    private Tipiendo insertTipiendo(String codiceSistemaEsterno, String descrizione, Software sw, Tipifamiglieendo tipifamiglieendo) {

	log.debug("insertTipiendo# Insert Tipo categoria endo codiceSistemaEsterno = {}", codiceSistemaEsterno);
	Tipiendo te = new Tipiendo();
	te.setSoftware(sw);
	te.setTipo(descrizione);
	if (EntityUtils.getNestedProperty(tipifamiglieendo, "id.codice") != null) {
	    te.setTipifamiglieendo(tipifamiglieendo);
	}
	tipiendoService.insert(te);
	if (StringUtils.isNotBlank(codiceSistemaEsterno)) {
	    StpCategorieEndo1 mapping = new StpCategorieEndo1();
	    mapping.setCodiceStp(Integer.parseInt(codiceSistemaEsterno));
	    mapping.setTipiendo(te);
	    stpCategorieEndo1Service.insert(mapping);
	}
	alberoprocDAO.flush();
	alberoprocDAO.commit();
	log.debug("insertTipiendo# Insert Tipo categoria endo ok.....");
	return te;
    }

    private Inventarioprocedimenti insertInventarioprocedimenti(String codiceSistemaEsterno, String descrizione, String prefix, Software sw,
	    Tipiendo tipoendo, Amministrazioni amministrazione, Tempificazioni tempificazione, Naturaendo naturaendo) {

	log.debug("insertInventarioprocedimenti# Insert Inventarioprocedimenti codiceSistemaEsterno = {}", codiceSistemaEsterno);
	Inventarioprocedimenti inventarioprocedimenti = new Inventarioprocedimenti();
	inventarioprocedimenti.setSoftware(sw);
	inventarioprocedimenti.setTipoendo(tipoendo);
	inventarioprocedimenti.setProcedimento(descrizione);
	inventarioprocedimenti.setTempificazione(tempificazione);
	inventarioprocedimenti.setNaturaendo(naturaendo);
	inventarioprocedimenti.setAmministrazioni(amministrazione);
	inventarioprocedimentiService.insert(inventarioprocedimenti);
	if (StringUtils.isNotBlank(codiceSistemaEsterno)) {
	    Inventarioprocedimentipeople inventarioprocedimentipeople = new Inventarioprocedimentipeople();
	    inventarioprocedimentipeople.setCodProcPeople(StringUtils.defaultIfEmpty(prefix, "") + codiceSistemaEsterno);
	    inventarioprocedimentipeople.setInventarioprocedimenti(inventarioprocedimenti);
	    inventarioprocedimentipeopleService.insert(inventarioprocedimentipeople);
	}
	alberoprocDAO.flush();
	alberoprocDAO.commit();
	log.debug("insertInventarioprocedimenti# Insert Inventarioprocedimenti ok.....");
	return inventarioprocedimenti;
    }

    private it.gruppoinit.pal.gp.core.domain.AlberoprocEndo insertAlberoProcEndo(Alberoproc alberoproc,
	    Inventarioprocedimenti inventarioprocedimenti, boolean flagPrincipale, boolean flagPubblicato, boolean flagRichiesto,
	    boolean flagUsaNelback, String azAzione) {

	it.gruppoinit.pal.gp.core.domain.AlberoprocEndo albProcEndo = new it.gruppoinit.pal.gp.core.domain.AlberoprocEndo();
	albProcEndo.setAlberoproc(alberoproc);
	albProcEndo.setInventarioprocedimento(inventarioprocedimenti);
	albProcEndo.setFlagPrincipale(BooleanUtils.toBoolean(flagPrincipale));
	albProcEndo.setFlagPubblica(flagPubblicato);
	albProcEndo.setFlagRichiesto(flagRichiesto);
	albProcEndo.setFlagRichiestoBo(flagUsaNelback);
	Azioni azione = azioniService.findByAzione(azAzione.trim());
	if (azione != null) {
	    albProcEndo.setAzione(azione);
	} else {
	    LoggerArchiviBackoffice.logArchiviBackofficeERROR("Non è stata trovata l'azione con codice =" + azAzione.trim() + ". Per codice endo  = "
		    + inventarioprocedimenti.getId().getCodice() + " e Codice AlberoProc = " + alberoproc.getId().getCodice()
		    + ". Non è stato possibile agganciare l'azione.");
	}
	albProcEndo.setId(new AlberoprocEndoId(ORMHelper.getIdcomune(), alberoproc.getId().getCodice(), inventarioprocedimenti.getId().getCodice()));
	alberoprocEndoService.insert(albProcEndo);
	alberoprocDAO.flush();
	alberoprocDAO.commit();
	return albProcEndo;
    }
}
