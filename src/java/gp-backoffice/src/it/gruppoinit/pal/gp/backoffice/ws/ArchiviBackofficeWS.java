package it.gruppoinit.pal.gp.backoffice.ws;

import it.gruppoinit.pal.gp.backoffice.definitions.archivibackoffice.ArchiviBackoffice;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice.AlberoRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice.AlberoResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice.AlberoprocEndo;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice.AlberoprocEndoRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice.AlberoprocEndoResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice.CategoriaEndoprocedimento;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice.EndoprocedimentiRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice.EndoprocedimentiResponse;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice.Endoprocedimento;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice.FamigliaEndoprocedimento;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice.NodoAlbero;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice.NodoAlberoProcHelper;
import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tempificazioni;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
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
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.EntityValidationException;
import it.gruppoinit.pal.gp.core.utils.LoggerArchiviBackoffice;
import it.gruppoinit.pal.gp.core.ws.BaseWS;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@javax.jws.WebService(serviceName = "ArchiviBackofficeService", portName = "ArchiviBackoffice11", targetNamespace = "http://gruppoinit.it/sigepro/definitions/archiviBackoffice", endpointInterface = "it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice.ArchiviBackoffice")
public class ArchiviBackofficeWS extends BaseWS implements ArchiviBackoffice {

    private static final Logger log = LoggerFactory.getLogger(ArchiviBackofficeWS.class);
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
    private NatureProcedureService natureProcedureService;
    /////////////////////////////////////////////////////////
    @Autowired
    private ArchiviBackofficeAlberoProcHelperService archiviBackofficeAlberoProcHelperService;

    ////////////////////////////////////////////////////////////
    /**
     * NB: ad oggi non è pensato per aggiornare la posizione di un nodo esistete e mappato. In caso di nodo già presente
     * e mappato quello che cambieremo ora è la descrizione.
     */
    @Override
    public AlberoResponse importAlberoInterventi(AlberoRequest alberoRequest) {

	log.debug("importAlberoInterventi# setORMHelper. software = {}", alberoRequest.getSoftware());
	setORMHelper(alberoRequest.getSoftware(), alberoRequest.getToken());
	StringBuffer sb = new StringBuffer();
	sb = sb.append("IdcomuneAlias = ").append(ORMHelper.getIdcomuneAlias()).append(" Idcomune = ").append(ORMHelper.getIdcomune())
		.append(" Software = ").append(ORMHelper.getSoftware());
	LoggerArchiviBackoffice.logArchiviBackofficeINFO("START - IMPORT ALBERO PROCEDIMENTI. " + sb.toString());
	Software sw = softwareService.findById(alberoRequest.getSoftware());
	boolean isAggiorna = alberoRequest.isAggiorna();
	boolean isErroriPresenti = false;
	LoggerArchiviBackoffice.logArchiviBackofficeINFO("MODALITA AGGIORNA: " + BooleanUtils.toStringTrueFalse(isAggiorna).toUpperCase());
	AlberoResponse ar = new AlberoResponse();
	log.debug("importAlberoInterventi# modalità isAggiorna = {}", isAggiorna);
	Map<String, NodoAlberoProcHelper> mappaNodi = new LinkedHashMap<String, NodoAlberoProcHelper>();
	log.debug("importAlberoInterventi# isIniziaDallaRadice = {}", alberoRequest.getAlbero().isIniziaDallaRadice());
	if (alberoRequest.getAlbero().isIniziaDallaRadice()) {
	    log.debug("importAlberoInterventi# Normalizzo struttura ad albero su una mappa ordinata. Per ogni nodo tengo traccia del suo padre");
	    normalizzaAlberoSuHashMap(alberoRequest.getAlbero().getNodoIniziale(), null, mappaNodi);
	} else {
	    // caso non esaminato per ora
	    throw new NotImplementedException("isIniziaDallaRadice: " + alberoRequest.getAlbero().isIniziaDallaRadice() + " non implementato");
	}
	Iterator<Map.Entry<String, NodoAlberoProcHelper>> it = mappaNodi.entrySet().iterator();
	while (it.hasNext()) {
	    Map.Entry<String, NodoAlberoProcHelper> pair = it.next();
	    try {
		boolean insertNodo = true;
		if (StringUtils.isNotBlank(pair.getValue().getNodoAlbero().getProprietaBase().getCodiceSistemaEsterno())
			&& StringUtils.isNotBlank(pair.getValue().getNodoAlbero().getProprietaBase().getCodiceBackoffice())
			&& pair.getValue().getNodoAlbero().getProprietaBase().getCodiceSistemaEsterno().equalsIgnoreCase("RADICE")) {
		    insertNodo = false;
		}
		if (insertNodo) {
		    if (StringUtils.isNotBlank(pair.getValue().getNodoAlbero().getProprietaBase().getCodiceSistemaEsterno())) {
			log.debug("importAlberoInterventi# ");
			String codiceNaturaProcedura = "";
			if (pair.getValue().getNodoAlbero().getProcedura() != null) {
			    codiceNaturaProcedura = pair.getValue().getNodoAlbero().getProcedura().getProprietaBase().getCodiceSistemaEsterno();
			}
			archiviBackofficeAlberoProcHelperService.insertOrUpdateAlberoProcSistemaEsterno(pair.getValue().getNodoAlbero()
				.getProprietaBase().getCodiceSistemaEsterno(), pair.getValue().getNodoAlbero().getProprietaBase().getDescrizione(),
				codiceNaturaProcedura, pair.getValue().getIdNodoPradre(), isAggiorna, sw);
		    } else if (StringUtils.isNotBlank(pair.getValue().getNodoAlbero().getProprietaBase().getCodiceBackoffice())) {
			// caso non esaminato per ora
			throw new NotImplementedException("Caso Albero con nodi con codice backoffice non implementato");
			///
		    } else {
			// caso non esaminato per ora
			throw new NotImplementedException("Caso Albero con nodi senza codici non implementato");
		    }
		}
	    } catch (Exception e) {
		isErroriPresenti = true;
		if (e instanceof EntityValidationException) {
		    String errore = e.getMessage() + " - ";
		    List<InvalidValue> l = ((EntityValidationException) e).getInvalidValues();
		    for (InvalidValue invalidValue : l) {
			errore += " " + invalidValue.getMessage() + " " + invalidValue.getPropertyName();
		    }
		    LoggerArchiviBackoffice.logArchiviBackofficeERROR("Errore durante l'inserimento del nodo con codice stp = "
			    + pair.getValue().getNodoAlbero().getProprietaBase().getCodiceSistemaEsterno() + ", codice backoffice = "
			    + pair.getValue().getNodoAlbero().getProprietaBase().getCodiceBackoffice() + ", descrizione =  "
			    + pair.getValue().getNodoAlbero().getProprietaBase().getDescrizione() + ". Errore = " + errore);
		    log.error(
			    "importAlberoInterventi# Errore durante l'inserimento del nodo con codice stp = {}, codice backoffice = {}, descrizione = {}. Errore = {}",
			    new Object[] { pair.getValue().getNodoAlbero().getProprietaBase().getCodiceSistemaEsterno(),
				    pair.getValue().getNodoAlbero().getProprietaBase().getCodiceBackoffice(),
				    pair.getValue().getNodoAlbero().getProprietaBase().getDescrizione(), errore });
		} else {
		    LoggerArchiviBackoffice.logArchiviBackofficeERROR("Errore durante l'inserimento del nodo con codice stp = "
			    + pair.getValue().getNodoAlbero().getProprietaBase().getCodiceSistemaEsterno() + ", codice backoffice = "
			    + pair.getValue().getNodoAlbero().getProprietaBase().getCodiceBackoffice() + ", descrizione =  "
			    + pair.getValue().getNodoAlbero().getProprietaBase().getDescrizione() + ". Errore = " + e.getMessage());
		    log.error(
			    "importAlberoInterventi# Errore durante l'inserimento del nodo con codice stp = {}, codice backoffice = {}, descrizione = {}. Errore = {}",
			    new Object[] { pair.getValue().getNodoAlbero().getProprietaBase().getCodiceSistemaEsterno(),
				    pair.getValue().getNodoAlbero().getProprietaBase().getCodiceBackoffice(),
				    pair.getValue().getNodoAlbero().getProprietaBase().getDescrizione(), e.getMessage() });
		}
	    }
	}
	if (isErroriPresenti) {
	    ar.setEsito("KO - Si sono verificati errori durante l'importazione dell'albero dei procedimenti. Controllare il file di log  Archivibackoffice.log nella cartella audit del tomcat del backoffice");
	} else {
	    ar.setEsito("OK");
	}
	LoggerArchiviBackoffice.logArchiviBackofficeINFO("END - IMPORT ALBERO PROCEDIMENTI");
	return ar;
    }

    /**
     * 
     * @param nodoFiglio
     * @param nodoPadre
     * @param sw
     * @return
     */
    @Override
    public EndoprocedimentiResponse importEndoprocedimenti(EndoprocedimentiRequest endoprocedimentiRequest) {

	EndoprocedimentiResponse endoprocedimentiResponse = new EndoprocedimentiResponse();
	log.debug("importEndoprocedimenti# setORMHelper. software = {}", endoprocedimentiRequest.getSoftware());
	setORMHelper(endoprocedimentiRequest.getSoftware(), endoprocedimentiRequest.getToken());
	StringBuffer sb = new StringBuffer();
	sb = sb.append("IdcomuneAlias = ").append(ORMHelper.getIdcomuneAlias()).append(" Idcomune = ").append(ORMHelper.getIdcomune())
		.append(" Software = ").append(ORMHelper.getSoftware());
	LoggerArchiviBackoffice.logArchiviBackofficeINFO("START - IMPORT ENDO PROCEDIMENTI. " + sb.toString());
	Software sw = softwareService.findById(endoprocedimentiRequest.getSoftware());
	boolean isAggiorna = endoprocedimentiRequest.isAggiorna();
	LoggerArchiviBackoffice.logArchiviBackofficeINFO("MODALITA AGGIORNA: " + BooleanUtils.toStringTrueFalse(isAggiorna).toUpperCase());
	log.debug("importEndoprocedimenti# modalità isAggiorna = {}", isAggiorna);
	List<FamigliaEndoprocedimento> famigliaEndoprocedimentos = endoprocedimentiRequest.getFamigliaEndoprocedimento();
	Tipifamiglieendo tfe = null;
	boolean isErroriPresenti = false;
	for (FamigliaEndoprocedimento famigliaEndoprocedimento : famigliaEndoprocedimentos) {
	    log.debug("importEndoprocedimenti# Insert/Update TIPO FAMIGLIE ENDO");
	    boolean isFamigliaInserita = true;
	    if (!"GENERICA".equalsIgnoreCase(famigliaEndoprocedimento.getProprietaBase().getCodiceBackoffice())
		    || !"GENERICA".equalsIgnoreCase(famigliaEndoprocedimento.getProprietaBase().getCodiceSistemaEsterno())) {
		try {
		    if (StringUtils.isNotBlank(famigliaEndoprocedimento.getProprietaBase().getCodiceSistemaEsterno())) {
			tfe = archiviBackofficeAlberoProcHelperService.insertOrUpdateTipiFamiglieEndoSistemaEsterno(famigliaEndoprocedimento
				.getProprietaBase().getCodiceSistemaEsterno(), famigliaEndoprocedimento.getProprietaBase().getDescrizione(),
				isAggiorna, sw);
		    } else if (StringUtils.isNotBlank(famigliaEndoprocedimento.getProprietaBase().getCodiceBackoffice())) {
			throw new NotImplementedException("CASO: CodiceBackoffice popolato non implementato");
		    } else {
			throw new NotImplementedException("CASO: codici non popolati non implementato");
		    }
		} catch (Exception e) {
		    isErroriPresenti = true;
		    if (e instanceof EntityValidationException) {
			String errore = e.getMessage() + " - ";
			List<InvalidValue> l = ((EntityValidationException) e).getInvalidValues();
			for (InvalidValue invalidValue : l) {
			    errore += " " + invalidValue.getMessage() + " " + invalidValue.getPropertyName();
			}
			LoggerArchiviBackoffice
				.logArchiviBackofficeERROR("Errore durante l'inserimento/aggiornamento della tipo famiglia endo.CodiceStp: "
					+ famigliaEndoprocedimento.getProprietaBase().getCodiceSistemaEsterno() + ", CodiceBackoffice: "
					+ famigliaEndoprocedimento.getProprietaBase().getCodiceBackoffice() + "Errore = " + errore);
			log.error(
				"importEndoprocedimenti# Errore durante l'inserimento di una tipo famiglia endo. CodiceStp = {}, CodiceBackoffice = {}. Errore = {}",
				new Object[] { famigliaEndoprocedimento.getProprietaBase().getCodiceSistemaEsterno(),
					famigliaEndoprocedimento.getProprietaBase().getCodiceBackoffice(), errore });
		    } else {
			LoggerArchiviBackoffice
				.logArchiviBackofficeERROR("Errore durante l'inserimento/aggiornamento della tipo famiglia endo.CodiceStp: "
					+ famigliaEndoprocedimento.getProprietaBase().getCodiceSistemaEsterno() + ", CodiceBackoffice: "
					+ famigliaEndoprocedimento.getProprietaBase().getCodiceBackoffice() + "Errore = " + e.getMessage());
			log.error(
				"importEndoprocedimenti# Errore durante l'inserimento di una tipo famiglia endo. CodiceStp = {}, CodiceBackoffice = {}. Errore = {}",
				new Object[] { famigliaEndoprocedimento.getProprietaBase().getCodiceSistemaEsterno(),
					famigliaEndoprocedimento.getProprietaBase().getCodiceBackoffice(), e.getMessage() });
		    }
		}
		if (EntityUtils.getNestedProperty(tfe, "id.codice") == null) {
		    isFamigliaInserita = false;
		    LoggerArchiviBackoffice.logArchiviBackofficeINFO("Tipo famiglia endo non inserita. CodiceStp: "
			    + famigliaEndoprocedimento.getProprietaBase().getCodiceSistemaEsterno() + ", CodiceBackoffice: "
			    + famigliaEndoprocedimento.getProprietaBase().getCodiceBackoffice()
			    + ". Non verranno inserite le categorie endo e gli endoprocedimenti collegati");
		}
	    } else {
		log.debug("importEndoprocedimenti# Tipo Famiglia endo non passata. isFamigliaInserita = {}", isFamigliaInserita);
		famigliaEndoprocedimento = null;
	    }
	    if (isFamigliaInserita) {
		//
		log.debug("importEndoprocedimenti# Insert/Update TIPO CATEGORIE ENDO");
		List<CategoriaEndoprocedimento> categoriaEndoprocedimentos = famigliaEndoprocedimento.getCategoriaEndoprocedimento();
		Tipiendo tipiEndo = null;
		for (CategoriaEndoprocedimento categoriaEndoprocedimento : categoriaEndoprocedimentos) {
		    try {
			if (StringUtils.isNotBlank(categoriaEndoprocedimento.getProprietaBase().getCodiceSistemaEsterno())) {
			    tipiEndo = archiviBackofficeAlberoProcHelperService.insertOrUpdateTipiCategorieEndoSistemaEsterno(
				    categoriaEndoprocedimento.getProprietaBase().getCodiceSistemaEsterno(), categoriaEndoprocedimento
					    .getProprietaBase().getDescrizione(), isAggiorna, tfe, sw);
			} else if (StringUtils.isNotBlank(categoriaEndoprocedimento.getProprietaBase().getCodiceBackoffice())) {
			    throw new NotImplementedException("CASO: CodiceBackoffice popolato non implementato");
			} else {
			    throw new NotImplementedException("CASO: CodiceBackoffice popolato non implementato");
			}
		    } catch (Exception e) {
			isErroriPresenti = true;
			if (e instanceof EntityValidationException) {
			    String errore = e.getMessage() + " - ";
			    List<InvalidValue> l = ((EntityValidationException) e).getInvalidValues();
			    for (InvalidValue invalidValue : l) {
				errore += " " + invalidValue.getMessage() + " " + invalidValue.getPropertyName();
			    }
			    LoggerArchiviBackoffice
				    .logArchiviBackofficeERROR("Errore durante l'inserimento/aggiornamento della tipo catogoria endo.CodiceStp: "
					    + categoriaEndoprocedimento.getProprietaBase().getCodiceSistemaEsterno() + ", CodiceBackoffice: "
					    + categoriaEndoprocedimento.getProprietaBase().getCodiceBackoffice() + ". Errore = " + errore);
			    log.error(
				    "importEndoprocedimenti# Errore durante l'inserimento di un tipo categoria endo. CodiceStp = {}, CodiceBackoffice = {}. Errore = {}",
				    new Object[] { categoriaEndoprocedimento.getProprietaBase().getCodiceSistemaEsterno(),
					    categoriaEndoprocedimento.getProprietaBase().getCodiceBackoffice(), errore });
			} else {
			    LoggerArchiviBackoffice
				    .logArchiviBackofficeERROR("Errore durante l'inserimento/aggiornamento della tipo catogoria endo.CodiceStp: "
					    + categoriaEndoprocedimento.getProprietaBase().getCodiceSistemaEsterno() + ", CodiceBackoffice: "
					    + categoriaEndoprocedimento.getProprietaBase().getCodiceBackoffice() + ". Errore = " + e.getMessage());
			    log.error(
				    "importEndoprocedimenti# Errore durante l'inserimento di un tipo categoria endo. CodiceStp = {}, CodiceBackoffice = {}. Errore = {}",
				    new Object[] { categoriaEndoprocedimento.getProprietaBase().getCodiceSistemaEsterno(),
					    categoriaEndoprocedimento.getProprietaBase().getCodiceBackoffice(), e.getMessage() });
			}
		    }
		    //
		    boolean isCategoriaEndoPresente = false;
		    if (EntityUtils.getNestedProperty(tipiEndo, "id.codice") != null) {
			isCategoriaEndoPresente = true;
		    } else {
			LoggerArchiviBackoffice.logArchiviBackofficeINFO("Tipo categoria endo non inserita. CodiceStp: "
				+ categoriaEndoprocedimento.getProprietaBase().getCodiceSistemaEsterno() + ", CodiceBackoffice: "
				+ categoriaEndoprocedimento.getProprietaBase().getCodiceBackoffice()
				+ " Non saranno inseriti gli endoprocedimenti collegati");
		    }
		    //
		    if (isCategoriaEndoPresente) {
			log.debug("importEndoprocedimenti# Insert/Update ENDOPROCEDIMENTI");
			List<Endoprocedimento> endoprocedimentos = categoriaEndoprocedimento.getEndoprocedimento();
			Inventarioprocedimenti inventarioprocedimenti = null;
			for (Endoprocedimento endoprocedimento : endoprocedimentos) {
			    try {
				Tempificazioni tempificazione = tempificazioniService.findById(new PkId(Integer.parseInt(endoprocedimento
					.getTempistiche().getProprietaBase().getCodiceSistemaEsterno())));
				if (EntityUtils.getNestedProperty(tempificazione, "id.codice") == null) {
				    log.error("importEndoprocedimenti# Tempificazione non trovata per natura base = {}", endoprocedimento
					    .getTempistiche().getProprietaBase().getCodiceSistemaEsterno().toLowerCase());
				}
				Naturaendo naturaendo = naturaendoService.findByNaturaBase(endoprocedimento.getNatureEndo().getProprietaBase()
					.getCodiceSistemaEsterno().toLowerCase());
				if (EntityUtils.getNestedProperty(naturaendo, "id.codice") == null) {
				    log.warn("importEndoprocedimenti# Natura endo non trovata per natura base = {}. Imposto default <Ordinario>",
					    endoprocedimento.getNatureEndo().getProprietaBase().getCodiceSistemaEsterno().toLowerCase());
				    naturaendo = naturaendoService.findByNaturaBase("ordinazio");
				}
				Amministrazioni amministrazione = amministrazioniService.findById(new PkId(Integer.parseInt(endoprocedimento
					.getAmministrazione().getProprietaBase().getCodiceSistemaEsterno())));
				if (EntityUtils.getNestedProperty(amministrazione, "id.codice") == null) {
				    log.error("importEndoprocedimenti# Amministrazione non trovata per natura base = {}", endoprocedimento
					    .getAmministrazione().getProprietaBase().getCodiceSistemaEsterno().toLowerCase());
				}
				if (StringUtils.isNotBlank(endoprocedimento.getProprietaBase().getCodiceSistemaEsterno())) {
				    inventarioprocedimenti = archiviBackofficeAlberoProcHelperService
					    .insertOrUpdateInventarioProcedimentiSistemaEsterno(endoprocedimento.getProprietaBase()
						    .getCodiceSistemaEsterno(), endoprocedimento.getProprietaBase().getDescrizione(),
						    endoprocedimento.getPrefixMapping(), tipiEndo, tempificazione, naturaendo, amministrazione,
						    isAggiorna, sw);
				} else if (StringUtils.isNotBlank(endoprocedimento.getProprietaBase().getCodiceBackoffice())) {
				    throw new NotImplementedException("CASO: CodiceBackoffice popolato non implementato");
				} else {
				    throw new NotImplementedException("CASO: CodiceBackoffice popolato non implementato");
				}
			    } catch (Exception e) {
				isErroriPresenti = true;
				if (e instanceof EntityValidationException) {
				    String errore = e.getMessage() + " - ";
				    List<InvalidValue> l = ((EntityValidationException) e).getInvalidValues();
				    for (InvalidValue invalidValue : l) {
					errore += " " + invalidValue.getMessage() + " " + invalidValue.getPropertyName();
				    }
				    LoggerArchiviBackoffice.logArchiviBackofficeERROR("Endo procedimento non inserito. Errore = " + errore
					    + " .CodiceStp: " + endoprocedimento.getProprietaBase().getCodiceSistemaEsterno()
					    + ", CodiceBackoffice: " + endoprocedimento.getProprietaBase().getCodiceBackoffice() + "");
				} else {
				    LoggerArchiviBackoffice.logArchiviBackofficeERROR("Endo procedimento non inserito. Errore = " + e.getMessage()
					    + " CodiceStp: " + endoprocedimento.getProprietaBase().getCodiceSistemaEsterno() + ", CodiceBackoffice: "
					    + endoprocedimento.getProprietaBase().getCodiceBackoffice() + " ");
				}
			    }
			    if (EntityUtils.getNestedProperty(inventarioprocedimenti, "id.codice") != null) {
				// stampa logi FIXME
				// endo non inseriro/aggiornato
			    }
			}
		    }
		}
	    }
	}
	if (isErroriPresenti) {
	    endoprocedimentiResponse
		    .setEsito("KO - Si sono verificati errori durante l'importazione dei procedimenti. Controllare il file di log  Archivibackoffice.log nella cartella audit del tomcat del backoffice");
	} else {
	    endoprocedimentiResponse.setEsito("OK");
	}
	LoggerArchiviBackoffice.logArchiviBackofficeINFO("END - IMPORT ENDO PROCEDIMENTI");
	return endoprocedimentiResponse;
    }

    /**
     * 
     * @param nodoFiglio
     * @param nodoPadre
     * @param sw
     * @returni
     */
    @Override
    public AlberoprocEndoResponse collegaEndoprocedimentiAdIntervento(AlberoprocEndoRequest alberoprocEndoRequest) {

	AlberoprocEndoResponse alberoprocEndoResponse = new AlberoprocEndoResponse();
	boolean isErroriPresenti = false;
	log.debug("importEndoprocedimenti# setORMHelper. software = {}", alberoprocEndoRequest.getSoftware());
	setORMHelper(alberoprocEndoRequest.getSoftware(), alberoprocEndoRequest.getToken());
	StringBuffer sb = new StringBuffer();
	sb = sb.append("IdcomuneAlias = ").append(ORMHelper.getIdcomuneAlias()).append(" Idcomune = ").append(ORMHelper.getIdcomune())
		.append(" Software = ").append(ORMHelper.getSoftware());
	LoggerArchiviBackoffice.logArchiviBackofficeINFO("START - COLLEGA ENDO PROCEDIMENTI A VOCE DELL' ALEBERO. " + sb.toString());
	Software sw = softwareService.findById(alberoprocEndoRequest.getSoftware());
	List<AlberoprocEndo> ape = alberoprocEndoRequest.getAlberoprocEndo();
	for (AlberoprocEndo alberoprocEndo : ape) {
	    try {
		if (StringUtils.isNotBlank(alberoprocEndo.getNodo().getProprietaBase().getCodiceSistemaEsterno())
			&& StringUtils.isNotBlank(alberoprocEndo.getEndoprocedimento().getProprietaBase().getCodiceSistemaEsterno())) {
		    isErroriPresenti = archiviBackofficeAlberoProcHelperService.insertOrUpdateAlberoprocEndoSistemaEsterno(alberoprocEndo.getNodo()
			    .getProprietaBase().getCodiceSistemaEsterno(), alberoprocEndo.getEndoprocedimento().getProprietaBase()
			    .getCodiceSistemaEsterno(), alberoprocEndo.getEndoprocedimento().getPrefixMapping(), alberoprocEndo.isFlagPrincipale(),
			    alberoprocEndo.isFlagPubblicato(), alberoprocEndo.isFlagRichiesto(), alberoprocEndo.isFlagUsaNelBack(), alberoprocEndo
				    .getAzione().getProprietaBase().getCodiceSistemaEsterno(), sw);
		    /////
		} else if (StringUtils.isNotBlank(alberoprocEndo.getNodo().getProprietaBase().getCodiceBackoffice())
			&& StringUtils.isNotBlank(alberoprocEndo.getEndoprocedimento().getProprietaBase().getCodiceBackoffice())) {
		    throw new NotImplementedException("CASO: CodiceBackoffice popolato non implementato");
		} else {
		    LoggerArchiviBackoffice
			    .logArchiviBackofficeERROR("Errore durante il collegamento di un endoprocedimento ad una voce dell'albero, dati incongruenti."
				    + "CodiceStp albero proc: "
				    + alberoprocEndo.getNodo().getProprietaBase().getCodiceSistemaEsterno()
				    + ", CodiceStp procedimento: "
				    + alberoprocEndo.getEndoprocedimento().getProprietaBase().getCodiceSistemaEsterno()
				    + "Codice Backoffice albero proc: "
				    + alberoprocEndo.getNodo().getProprietaBase().getCodiceBackoffice()
				    + ", Codice Backoffice procedimento: "
				    + alberoprocEndo.getEndoprocedimento().getProprietaBase().getCodiceBackoffice());
		    isErroriPresenti = true;
		}
	    } catch (Exception e) {
		if (e instanceof BusinessValidationException) {
		    String errore = e.getMessage() + " - ";
		    List<InvalidValue> l = ((BusinessValidationException) e).getInvalidValues();
		    for (InvalidValue invalidValue : l) {
			errore += " " + invalidValue.getMessage() + " " + invalidValue.getPropertyName();
		    }
		    LoggerArchiviBackoffice
			    .logArchiviBackofficeERROR("Errore durante il collegamento di un endoprocedimento ad una voce dell'albero: " + errore
				    + ". CodiceStp albero proc: " + alberoprocEndo.getNodo().getProprietaBase().getCodiceSistemaEsterno()
				    + ", CodiceStp procedimento: "
				    + alberoprocEndo.getEndoprocedimento().getProprietaBase().getCodiceSistemaEsterno()
				    + "Codice Backoffice albero proc: " + alberoprocEndo.getNodo().getProprietaBase().getCodiceBackoffice()
				    + ", Codice Backoffice procedimento: "
				    + alberoprocEndo.getEndoprocedimento().getProprietaBase().getCodiceBackoffice());
		} else {
		    LoggerArchiviBackoffice
			    .logArchiviBackofficeERROR("Errore durante il collegamento di un endoprocedimento ad una voce dell'albero: " + e
				    + ". CodiceStp albero proc: " + alberoprocEndo.getNodo().getProprietaBase().getCodiceSistemaEsterno()
				    + ", CodiceStp procedimento: "
				    + alberoprocEndo.getEndoprocedimento().getProprietaBase().getCodiceSistemaEsterno()
				    + "Codice Backoffice albero proc: " + alberoprocEndo.getNodo().getProprietaBase().getCodiceBackoffice()
				    + ", Codice Backoffice procedimento: "
				    + alberoprocEndo.getEndoprocedimento().getProprietaBase().getCodiceBackoffice());
		}
		isErroriPresenti = true;
	    }
	}
	if (isErroriPresenti) {
	    alberoprocEndoResponse
		    .setEsito("KO - Si sono verificati errori durante il collegamento di un endoprocedimento ad un intervento. Controllare il file di log  Archivibackoffice.log nella cartella audit del tomcat del backoffice");
	} else {
	    alberoprocEndoResponse.setEsito("OK");
	}
	LoggerArchiviBackoffice.logArchiviBackofficeINFO("END - COLLEGA ENDO PROCEDIMENTI A VOCE DELL' ALEBERO");
	return alberoprocEndoResponse;
    }

    /**
     * <pre>
     * Il metodo effettua una visita ricorsiva dell'albero  dal nodo radice (nodo passato) fino ad arrivare alle foglie. Il metodo normalizza l'albero
     * generando una mappa ordinata con chiave l'id del padre del nodo che stiamo esaminando e come oggetto un bean (NodoAlberoProcHelper) che conterrà:  
     * 		1. Il nodo in esame (Nodo di tipo backoffice)
     *          2. Id del padre del nodo che stiamo esaminando
     * @param nodo
     * @param idNodoPadre
     * @param mappaNodi
     * </pre>
     */
    private void normalizzaAlberoSuHashMap(NodoAlbero nodo, String idNodoPadre, Map<String, NodoAlberoProcHelper> mappaNodi) {

	NodoAlberoProcHelper nodoNuovoHelper = new NodoAlberoProcHelper();
	nodoNuovoHelper.setIdNodoPradre(idNodoPadre);
	nodoNuovoHelper.setNodoAlbero(nodo);
	mappaNodi.put(nodo.getProprietaBase().getCodiceSistemaEsterno(), nodoNuovoHelper);
	List<NodoAlbero> listNodi = nodo.getNodoFiglio();
	for (NodoAlbero nodoAlbero : listNodi) {
	    normalizzaAlberoSuHashMap(nodoAlbero, nodo.getProprietaBase().getCodiceSistemaEsterno(), mappaNodi);
	}
    }
}
