package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.web.GenerateTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.Cittadinanza;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitaTipologie;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modTSnapshot;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzeAttivitaHelper;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Pentahocfg;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Ricerche;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.SettoriId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.Stradariocolore;
import it.gruppoinit.pal.gp.core.domain.StradariocoloreId;
import it.gruppoinit.pal.gp.core.domain.Stradariozone;
import it.gruppoinit.pal.gp.core.domain.Tipiarchivioistanze;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaistanza;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.IASnapshotViewerHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IAttivitaListHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IAttivitadyn2modTSnapshotHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeHelper;
import it.gruppoinit.pal.gp.core.domain.helper.StampaHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitaCommand;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitaFilter;
import it.gruppoinit.pal.gp.core.domain.web.IstanzePerAttivitaFilter;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaFilter;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaRigheFilter;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.IAttivitaIstanzeService;
import it.gruppoinit.pal.gp.core.features.attivita.istanze.ScollegamentoUnicaIstanzaException;
import it.gruppoinit.pal.gp.core.features.attivita.restrizioni.RestrizioneAttivitaEsistenteException;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.logic.ICalcoloSnapshotService;
import it.gruppoinit.pal.gp.core.features.attivita.snapshots.model.ParametriCalcoloSnapshot;
import it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.ICartograficoService;
import it.gruppoinit.pal.gp.core.features.istanze.attivita.DettaglioIstanzaType;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.jmesa.IAttivitaHelperTable;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AreeService;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.AzioniService;
import it.gruppoinit.pal.gp.core.service.CittadinanzaService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.ProprietaCampi;
import it.gruppoinit.pal.gp.core.service.EsportazioniService;
import it.gruppoinit.pal.gp.core.service.IAttivitaSnapshotService;
import it.gruppoinit.pal.gp.core.service.IAttivitaTipologieService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2datiSnapshotService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2modTSnapshotService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2modellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeattivitaService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.OrariaperturatestataService;
import it.gruppoinit.pal.gp.core.service.PentahoService;
import it.gruppoinit.pal.gp.core.service.PentahocfgService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.RicercheService;
import it.gruppoinit.pal.gp.core.service.SettoriService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StradarioService;
import it.gruppoinit.pal.gp.core.service.StradariocoloreService;
import it.gruppoinit.pal.gp.core.service.StradariozoneService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipiarchivioistanzeService;
import it.gruppoinit.pal.gp.core.service.TipologiaistanzaService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;
import it.gruppoinit.pal.gp.core.utils.LoggerModificheIstanze;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes(value = { "iattivitaCommand", "filterIstanze" })
public class IAttivitaController extends BaseController<IAttivita> {

    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AreeService areeService;
    @Autowired
    private AttivitaService attivitaService;
    @Autowired
    private ConfigurazioneService configurazioneService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private Dyn2CampiService dyn2CampiService;
    @Autowired
    private IAttivitaService iattivitaService;
    @Autowired
    private IAttivitadyn2modellitService iAttivitadyn2modellitService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private IAttivitadyn2datiSnapshotService iAttivitadyn2datiSnapshotService;
    @Autowired
    private IAttivitadyn2modTSnapshotService iAttivitadyn2modTSnapshotService;
    @Autowired
    private IAttivitaSnapshotService iAttivitaSnapshotService;
    @Autowired
    private IAttivitaTipologieService iAttivitaTipologieService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private SettoriService settoriService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private StradarioService stradarioService;
    @Autowired
    private StradariocoloreService stradariocoloreService;
    @Autowired
    private StradariozoneService stradariozoneService;
    @Autowired
    private TipiarchivioistanzeService tipiarchivioistanzeService;
    @Autowired
    private TipologiaistanzaService tipologiaistanzaService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private CittadinanzaService cittadinanzaService;
    @Autowired
    private AzioniService azioniService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private IstanzerichiedentiService istanzerichiedentiService;
    @Autowired
    private IstanzeattivitaService istanzeattivitaService;
    @Autowired
    private OrariaperturatestataService orariaperturatestataService;
    @Autowired
    private IstanzestradarioService istanzestradarioService;
    @Autowired
    private ContenttypesService contenttypesService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private RicercheService ricercheService;
    @Autowired
    private EsportazioniService esportazioniService;
    @Autowired
    private PentahoService pentahoService;
    @Autowired
    private PentahocfgService pentahocfgService;
    @Autowired
    private MailtipoService mailtipoService;
    @Autowired
    private MailServiceWSClient mailServiceWSClient;
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private IAttivitaIstanzeService attivitaIstanzeService;
    @Autowired
    private ICalcoloSnapshotService calcoloSnapshotService;
    @Autowired
    private ICartograficoService cartograficoService;
    private static final String IATTIVITA_FILTER_IN_SESSION = "IATTIVITA_FILTER_IN_SESSION";
    private static final Logger log = LoggerFactory.getLogger(IAttivitaController.class);
    private static final String ID_CODICE = "id.codice";

    @RequestMapping
    public String search(@RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "pSoftware", required = false) String pSoftware,
	    @RequestParam(value = "utilityAttivita", required = false) String utilityAttivita,
	    @RequestParam(value = "id_ricerca", required = false) String idRicerca,
	    @RequestParam(value = "indice_combo", required = false) String indiceCombo, HttpServletRequest request, Model model) {

	// Verifico se la funzionalità è chiamata dal menu di ricerca di attivita o dalla funzionalità di utility
	// (admin/view) che permette di ricercare una lista di istanze e collegare ad essa una scheda.
	// Nel caso non sia passata la stringa o il suo valre sia divero sa true si presuppone che la funzionalità
	// sia richiamata dalla ricerca delle attività
	boolean isFunzioneDiUtility = false;
	if (StringUtils.isNotBlank(utilityAttivita) && utilityAttivita.equals("true")) {
	    isFunzioneDiUtility = true;
	}
	// VERIFICO CHE IN SESSION NON SIA IMPOSTATO IL FILTRO NEL CASO LO RIMUOVO
	if (request.getSession().getAttribute(IATTIVITA_FILTER_IN_SESSION) != null) {
	    request.getSession().removeAttribute(IATTIVITA_FILTER_IN_SESSION);
	}
	IAttivitaCommand command = new IAttivitaCommand();
	if (StringUtils.isNotBlank(pSoftware)) {
	    Software softwareSelezionato = softwareService.findById(pSoftware);
	    command.getAttivitaFilter().setSoftware(softwareSelezionato);
	}
	if (codiceIstanza != null) {
	    Istanze istanza = new Istanze();
	    PkId idIstanza = new PkId(codiceIstanza);
	    istanza.setId(idIstanza);
	    istanza = istanzeService.bindDomainObject(istanza, PkId.class, ID_CODICE);
	    command.setIstanza(istanza);
	}
	// GESTISCE LA CONFIGURAZIONE DELLE SEZIONE SALVA RICERCHE
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_GESTIONE_RICERCHE, "0", request);
	if (StringUtils.isNotBlank(idRicerca)) {
	    Ricerche ricerche = ricercheService.findById(new PkId(Integer.parseInt(idRicerca)));
	    IAttivitaFilter attivitaFilter = createAttivitaFilter(ricerche.getFiltro());
	    command.setAttivitaFilter(attivitaFilter);
	    command.setCodiceRicerca(Integer.parseInt(indiceCombo));
	}
	model.addAttribute("iattivitaCommand", command);
	model.addAttribute("isFunzioneDiUtility", isFunzioneDiUtility);
	model.addAttribute("codiceIstanza", codiceIstanza);
	searchPageAttributes(model, request, isFunzioneDiUtility);
	setPageAttributes(model);
	return "iattivita/search";
    }

    @RequestMapping
    public String ricerca(@RequestParam(value = "pSoftware") String pSoftware, HttpServletRequest request, Model model) {

	// Verifico se la funzionalità è chiamata dal menu di ricerca di attivita o dalla funzionalità di utility
	// (admin/view) che permette di ricercare una lista di istanze e collegare ad essa una scheda.
	// Nel caso non sia passata la stringa o il suo valre sia divero sa true si presuppone che la funzionalità
	// sia richiamata dalla ricerca delle attività
	boolean isFunzioneDiUtility = false;
	// VERIFICO CHE IN SESSION NON SIA IMPOSTATO IL FILTRO NEL CASO LO RIMUOVO
	if (request.getSession().getAttribute(IATTIVITA_FILTER_IN_SESSION) != null) {
	    request.getSession().removeAttribute(IATTIVITA_FILTER_IN_SESSION);
	}
	IAttivitaCommand command = new IAttivitaCommand();
	if (StringUtils.isNotBlank(pSoftware)) {
	    Software softwareSelezionato = softwareService.findById(pSoftware);
	    command.getAttivitaFilter().setSoftware(softwareSelezionato);
	}
	// GESTISCE LA CONFIGURAZIONE DELLE SEZIONE SALVA RICERCHE	
	model.addAttribute("iattivitaCommand", command);
	model.addAttribute("isFunzioneDiUtility", isFunzioneDiUtility);
	searchPageAttributes(model, request, isFunzioneDiUtility);
	setPageAttributes(model);
	return "iattivita/ricerca";
    }

    private IAttivitaFilter createAttivitaFilter(byte[] bdata) {

	String filtroString = new String(bdata);
	IAttivitaFilter attivitaFilter = new IAttivitaFilter();
	String[] filtri = filtroString.split("&");
	Map<String, String> map = new HashMap<String, String>();
	Map<String, String> mapSchedeFilter = new HashMap<String, String>();
	for (String string : filtri) {
	    String[] campi = string.split("=");
	    if (!campi[0].contains("schedaDinamicaFilter")) {
		if (campi.length > 1) {
		    map.put(campi[0], campi[1]);
		} else {
		    map.put(campi[0], "");
		}
	    } else {
		String key = StringUtils.remove(campi[0], "attivitaFilter.schedaDinamicaFilter.");
		if (campi.length > 1) {
		    mapSchedeFilter.put(key, campi[1]);
		} else {
		    mapSchedeFilter.put(key, "");
		}
	    }
	}
	////////////// Setto l'albero proc //////////////////////////////////////////
	String codiceAlberoProc = map.get("attivitaFilter.alberoproc.id.codice");
	if (StringUtils.isNotBlank(codiceAlberoProc)) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(Integer.parseInt(codiceAlberoProc)));
	    attivitaFilter.setScCodice(alberoproc.getScCodice());
	}
	//////////// Setto l'aree //////////////////////////////////////////////////////////
	String codiceAree = map.get("attivitaFilter.aree.id.codice");
	if (StringUtils.isNotBlank(codiceAree)) {
	    Aree aree = areeService.findById(new PkId(Integer.parseInt(codiceAree)));
	    attivitaFilter.setAree(aree);
	}
	/////////// Setto attiva ////////////////////////////////////////////
	String isAttiva = map.get("attivitaFilter.attiva");
	if (StringUtils.isNotBlank(isAttiva)) {
	    attivitaFilter.setAttiva(isAttiva.equals("1"));
	} else {
	    attivitaFilter.setAttiva(null);
	}
	/////////// Setto attivita tipologia ////////////////////////////////////////////
	String codiceAttivitaTipologia = map.get("attivitaFilter.attivitaTipologie.id.codice");
	if (StringUtils.isNotBlank(codiceAttivitaTipologia)) {
	    IAttivitaTipologie iAttivitaTipologie = iAttivitaTipologieService.findById(new PkId(Integer.parseInt(codiceAttivitaTipologia)));
	    attivitaFilter.setAttivitaTipologie(iAttivitaTipologie);
	}
	/////////// Setto CAP ////////////////////////////////////////////
	String cap = map.get("attivitaFilter.cap");
	if (StringUtils.isNotBlank(cap)) {
	    attivitaFilter.setCap(cap);
	}
	attivitaFilter.setCheckIntervento(false);
	String checkIntervento = map.get("attivitaFilter.checkIntervento");
	if (StringUtils.isNotBlank(checkIntervento) && checkIntervento.equals("true")) {
	    attivitaFilter.setCheckIntervento(true);
	}
	String codiceCittadinanza = map.get("attivitaFilter.cittadinanza.codice");
	if (StringUtils.isNotBlank(codiceCittadinanza)) {
	    Cittadinanza cittadinanza = cittadinanzaService.findById(Integer.parseInt(codiceCittadinanza));
	    attivitaFilter.setCittadinanza(cittadinanza);
	}
	/////////////////// Setto il civico ////////////////////////
	String civico = map.get("attivitaFilter.civico");
	if (StringUtils.isNotBlank(civico)) {
	    attivitaFilter.setCivico(civico);
	}
	////////// Setto il comune //////////////////////////////
	String codiceComune = map.get("attivitaFilter.comune.codicecomune");
	if (StringUtils.isNotBlank(codiceComune)) {
	    Comuni comune = comuniService.findById(codiceComune);
	    attivitaFilter.setComune(comune);
	}
	/////////// Setto denominazione ////////////////////////////
	String denominazioneAttivita = map.get("attivitaFilter.denominazioneAttivita");
	if (StringUtils.isNotBlank(denominazioneAttivita)) {
	    attivitaFilter.setDenominazioneAttivita(denominazioneAttivita);
	}
	///////// Setto descrizioni lavori ////////////////////////////////
	String descrizioneLavori = map.get("attivitaFilter.descrizioneLavori");
	if (StringUtils.isNotBlank(descrizioneLavori)) {
	    attivitaFilter.setDescrizioneLavori(descrizioneLavori);
	}
	////////// Setto dettaglio informazione
	String codiceDettInformazione = map.get("attivitaFilter.dettaglioInformazione.id.codiceistat");
	if (StringUtils.isNotBlank(codiceDettInformazione)) {
	    Attivita attivita = attivitaService.findById(new AttivitaId(codiceDettInformazione));
	    attivitaFilter.setDettaglioInformazione(attivita);
	}
	//////// Setto esponente /////////////////////////////////////////////
	String esponente = map.get("attivitaFilter.esponente");
	if (StringUtils.isNotBlank(esponente)) {
	    attivitaFilter.setEsponente(esponente);
	}
	// Setto esponente interno
	String esponenteinterno = map.get("attivitaFilter.esponenteinterno");
	if (StringUtils.isNotBlank(esponenteinterno)) {
	    attivitaFilter.setEsponenteinterno(esponenteinterno);
	}
	// Setto esponente fabricato
	String fabbricato = map.get("attivitaFilter.fabbricato");
	if (StringUtils.isNotBlank(fabbricato)) {
	    attivitaFilter.setFabbricato(fabbricato);
	}
	// Setto esponente frazione
	String frazione = map.get("attivitaFilter.frazione");
	if (StringUtils.isNotBlank(frazione)) {
	    attivitaFilter.setFrazione(frazione);
	}
	// Setto esponente interno
	String interno = map.get("attivitaFilter.interno");
	if (StringUtils.isNotBlank(interno)) {
	    attivitaFilter.setInterno(interno);
	}
	// Setto inventarioprocedimenti 
	String codiceInventarioprocedimenti = map.get("attivitaFilter.inventarioprocedimenti.id.codice");
	if (StringUtils.isNotBlank(codiceInventarioprocedimenti)) {
	    Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService
		    .findById(new PkId(Integer.parseInt(codiceInventarioprocedimenti)));
	    attivitaFilter.setInventarioprocedimenti(inventarioprocedimenti);
	}
	// Setto  note
	String note = map.get("attivitaFilter.note");
	if (StringUtils.isNotBlank(note)) {
	    attivitaFilter.setNote(note);
	}
	// Setto operante
	String operante = map.get("attivitaFilter.operante");
	if (StringUtils.isNotBlank(operante)) {
	    attivitaFilter.setOperante(operante.equals("1"));
	} else {
	    attivitaFilter.setOperante(null);
	}
	// Setto  piano
	String piano = map.get("attivitaFilter.piano");
	if (StringUtils.isNotBlank(piano)) {
	    attivitaFilter.setPiano(piano);
	}
	// Setto  posizioneInArchivio
	String posizioneInArchivio = map.get("attivitaFilter.posizioneInArchivio");
	if (StringUtils.isNotBlank(posizioneInArchivio)) {
	    attivitaFilter.setPosizioneInArchivio(posizioneInArchivio);
	}
	// Setto  quartiere
	String quartiere = map.get("attivitaFilter.quartiere");
	if (StringUtils.isNotBlank(quartiere)) {
	    attivitaFilter.setQuartiere(quartiere);
	}
	// Setto  richidente
	String codiceRichiedente = map.get("attivitaFilter.richiedente.id.codice");
	if (StringUtils.isNotBlank(codiceRichiedente)) {
	    Anagrafe richiedente = anagrafeService.findById(new PkId(Integer.parseInt(codiceRichiedente)));
	    attivitaFilter.setRichiedente(richiedente);
	}
	// Setto  scala
	String scala = map.get("attivitaFilter.scala");
	if (StringUtils.isNotBlank(scala)) {
	    attivitaFilter.setScala(scala);
	}
	// Setto  software
	String codiceSoftware = map.get("attivitaFilter.software.codice");
	if (StringUtils.isNotBlank(codiceSoftware)) {
	    attivitaFilter.setSoftware(softwareService.findById(codiceSoftware));
	}
	// Setto  stradario
	String codicestradario = map.get("attivitaFilter.stradario.id.codice");
	if (StringUtils.isNotBlank(codicestradario)) {
	    Stradario stradario = stradarioService.findById(new PkId(Integer.parseInt(codicestradario)));
	    attivitaFilter.setStradario(stradario);
	}
	// Setto  stradario colore
	String codiceStradarioColore = map.get("attivitaFilter.stradariocolore.id.codicecolore");
	if (StringUtils.isNotBlank(codiceStradarioColore)) {
	    Stradariocolore stradariocolore = stradariocoloreService.findById(new StradariocoloreId(codiceStradarioColore));
	    attivitaFilter.setStradariocolore(stradariocolore);
	}
	// Setto  stradario zone
	String codiceStradarioZone = map.get("attivitaFilter.stradariozone.id.codice");
	if (StringUtils.isNotBlank(codiceStradarioZone)) {
	    Stradariozone stradariozone = stradariozoneService.findById((new PkId(Integer.parseInt(codiceStradarioZone))));
	    attivitaFilter.setStradariozone(stradariozone);
	}
	// Setto tipo archivio istanza
	String codiceTipoArchIstanze = map.get("attivitaFilter.tipiarchivioistanze.id.codice");
	if (StringUtils.isNotBlank(codiceTipoArchIstanze)) {
	    Tipiarchivioistanze tipiarchivioistanze = tipiarchivioistanzeService.findById((new PkId(Integer.parseInt(codiceTipoArchIstanze))));
	    attivitaFilter.setTipiarchivioistanze(tipiarchivioistanze);
	}
	// Setto tipo mov
	String codiceTipoMovimento = map.get("attivitaFilter.tipimovimento.id.tipomovimento");
	if (StringUtils.isNotBlank(codiceTipoMovimento)) {
	    Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(codiceTipoMovimento));
	    attivitaFilter.setTipimovimento(tipimovimento);
	}
	// Setto  tipo informazione
	String codiceTipoInformazione = map.get("attivitaFilter.tipoInformazione.id.codicesettore");
	if (StringUtils.isNotBlank(codiceTipoInformazione)) {
	    Settori settori = settoriService.findById((new SettoriId(codiceTipoInformazione)));
	    attivitaFilter.setTipoInformazione(settori);
	}
	// Setto  tipologia istanza
	String codiceTipologiaIstanza = map.get("attivitaFilter.tipologiaistanza.id.codice");
	if (StringUtils.isNotBlank(codiceStradarioZone)) {
	    Tipologiaistanza tipologiaistanza = tipologiaistanzaService.findById((new PkId(Integer.parseInt(codiceTipologiaIstanza))));
	    attivitaFilter.setTipologiaistanza(tipologiaistanza);
	}
	/////////////////////// GESTIONE DELLA SEZIONE DI RICERCA DELLE SCHEDE ///////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Recupero la scheda associata alla ricera sui campi dinamici
	String codiceSchedaDinamica = mapSchedeFilter.get("scheda.id.codice");
	if (StringUtils.isNotBlank(codiceSchedaDinamica)) {
	    // recupero il modello e lo setto al filtro
	    Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(new PkId(Integer.parseInt(codiceSchedaDinamica)));
	    SchedaDinamicaFilter schedaDinamicaFilter = new SchedaDinamicaFilter();
	    schedaDinamicaFilter.setScheda(dyn2Modellit);
	    // Conto il numero delle righe
	    Set<Integer> numerorighe = getNumeroRighe(mapSchedeFilter);
	    List<SchedaDinamicaRigheFilter> righe = new ArrayList<SchedaDinamicaRigheFilter>();
	    SchedaDinamicaRigheFilter dinamicaRigheFilter = null;
	    for (Integer riga_num : numerorighe) {
		dinamicaRigheFilter = populateRiga(mapSchedeFilter, riga_num);
		righe.add(dinamicaRigheFilter);
	    }
	    schedaDinamicaFilter.setRighe(righe);
	    attivitaFilter.setSchedaDinamicaFilter(schedaDinamicaFilter);
	}
	return attivitaFilter;
    }

    /**
     * <pre>
     * Controlla tutta la mappa e veriffica il numero di righe: 
     * 	    1. Verfifica che la key della mappa contenga la stringa "righe[" 
     * 	       1.1 si : estrae il valore copreso tra "righe[" e "]" 
     *         1.2 no : passa al sucessivo 
     *      2. Aggiunge il valore sul set (Usato un set inmodo che non duplica i valori)
     * 
     * &#64;param mapSchedeFilter
     * &#64;return
     * </pre>
     */
    private Set<Integer> getNumeroRighe(Map<String, String> mapSchedeFilter) {

	Set<String> keySchede = mapSchedeFilter.keySet();
	Set<Integer> numerorighe = new HashSet<Integer>();
	for (String string : keySchede) {
	    if (StringUtils.contains(string, "righe[")) {
		String numString = StringUtils.substring(string, string.indexOf("righe[") + 6, string.indexOf("]"));
		numerorighe.add(Integer.parseInt(numString));
	    }
	}
	return numerorighe;
    }

    /**
     * Popola un oggetto SchedaDinamicaRigheFilter per la riga passata
     * 
     * @param mapSchedeFilter
     * @param numeroRiga
     * @return
     */
    private SchedaDinamicaRigheFilter populateRiga(Map<String, String> mapSchedeFilter, int numeroRiga) {

	SchedaDinamicaRigheFilter dinamicaRigheFilter = new SchedaDinamicaRigheFilter();
	String andOr = mapSchedeFilter.get("righe[" + numeroRiga + "].andOr");
	if (StringUtils.isNotBlank(andOr)) {
	    dinamicaRigheFilter.setAndOr(AndOrRestriction.valueOf(andOr));
	}
	String parentesiSx = mapSchedeFilter.get("righe[" + numeroRiga + "].parentesiSx");
	dinamicaRigheFilter.setParentesiSx(parentesiSx);
	String campoid = mapSchedeFilter.get("righe[" + numeroRiga + "].campo.id.codice");
	if (StringUtils.isNotBlank(campoid)) {
	    Dyn2Campi campo = dyn2CampiService.findById(new PkId(Integer.parseInt(campoid)));
	    dinamicaRigheFilter.setCampo(campo);
	}
	String tipoContronto = mapSchedeFilter.get("righe[" + numeroRiga + "].tipoConfronto");
	if (StringUtils.isNotBlank(tipoContronto)) {
	    dinamicaRigheFilter.setTipoConfronto(FieldOperationsEnum.valueOf(tipoContronto));
	}
	String valore = mapSchedeFilter.get("righe[" + numeroRiga + "].valore");
	dinamicaRigheFilter.setValore(valore);
	String parentesidx = mapSchedeFilter.get("righe[" + numeroRiga + "].parentesiDx");
	dinamicaRigheFilter.setParentesiDx(parentesidx);
	return dinamicaRigheFilter;
    }

    @RequestMapping
    public String removeCampoScheda(@RequestParam(value = "idx", required = true) Integer idx,
	    @RequestParam(value = "utilityAttivita", required = false) String utilityAttivita, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, HttpServletRequest request) {

	// Verifico se la funzionalità è chiamata dal menu di ricerca di attivita o dalla funzionalità di utility
	// (admin/view) che permette di ricercare una lista di istanze e collegare ad essa una scheda.
	// Nel caso non sia passata la stringa o il suo valore sia diverso da true si presuppone che la funzionalità
	// sia richiamata dalla ricerca delle attività
	boolean isFunzioneDiUtility = false;
	if (StringUtils.isNotBlank(utilityAttivita) && utilityAttivita.equals("true")) {
	    isFunzioneDiUtility = true;
	}
	SchedaDinamicaFilter sf = iattivitaCommand.getAttivitaFilter().getSchedaDinamicaFilter();
	if (sf != null) {
	    List<SchedaDinamicaRigheFilter> righe = sf.getRighe();
	    if (righe != null) {
		for (int i = 0; i < righe.size(); i++) {
		    if (i == idx.intValue()) {
			righe.remove(i);
			break;
		    }
		}
	    }
	    List<SchedaDinamicaRigheFilter> ricalcoloIndici = new ArrayList<SchedaDinamicaRigheFilter>(righe.size());
	    int i = 0;
	    for (SchedaDinamicaRigheFilter schedaDinamicaRigheFilter : righe) {
		ricalcoloIndici.add(i, schedaDinamicaRigheFilter);
		i++;
	    }
	    sf.setRighe(ricalcoloIndici);
	}
	model.addAttribute("isFunzioneDiUtility", isFunzioneDiUtility);
	searchPageAttributes(model, request, isFunzioneDiUtility);
	setPageAttributes(model);
	return "iattivita/search";
    }

    @RequestMapping
    public String addCampoAScheda(@RequestParam(value = "utilityAttivita", required = false) String utilityAttivita, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, HttpServletRequest request) {

	// Verifico se la funzionalità è chiamata dal menu di ricerca di attivita o dalla funzionalità di utility
	// (admin/view) che permette di ricercare una lista di istanze e collegare ad essa una scheda.
	// Nel caso non sia passata la stringa o il suo valre sia divero sa true si presuppone che la funzionalità
	// sia richiamata dalla ricerca delle attività
	boolean isFunzioneDiUtility = false;
	if (StringUtils.isNotBlank(utilityAttivita) && utilityAttivita.equals("true")) {
	    isFunzioneDiUtility = true;
	}
	SchedaDinamicaFilter sf = iattivitaCommand.getAttivitaFilter().getSchedaDinamicaFilter();
	SchedaDinamicaRigheFilter riga = new SchedaDinamicaRigheFilter();
	sf.getRighe().add(riga);
	model.addAttribute("isFunzioneDiUtility", isFunzioneDiUtility);
	searchPageAttributes(model, request, isFunzioneDiUtility);
	setPageAttributes(model);
	return "iattivita/search";
    }

    @RequestMapping
    public String changeScheda(@RequestParam(value = "utilityAttivita", required = false) String utilityAttivita, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, HttpServletRequest request) {

	// Verifico se la funzionalità è chiamata dal menu di ricerca di attivita o dalla funzionalità di utility
	// (admin/view) che permette di ricercare una lista di istanze e collegare ad essa una scheda.
	// Nel caso non sia passata la stringa o il suo valre sia divero sa true si presuppone che la funzionalità
	// sia richiamata dalla ricerca delle attività
	boolean isFunzioneDiUtility = false;
	if (StringUtils.isNotBlank(utilityAttivita) && utilityAttivita.equals("true")) {
	    isFunzioneDiUtility = true;
	}
	SchedaDinamicaFilter sf = iattivitaCommand.getAttivitaFilter().getSchedaDinamicaFilter();
	if (sf.getScheda() != null) {
	    if (sf.getScheda().getId() != null) {
		if (sf.getScheda().getId().getCodice() != null) {
		    Dyn2Modellit scheda = dyn2ModellitService.findById(new PkId(sf.getScheda().getId().getCodice()));
		    List<Dyn2Campi> d2cs = dyn2CampiService.findByDescrizioneAndSoftwareAndModello("", sf.getScheda().getId().getCodice(), null);
		    sf.getListaCampiModello().addAll(d2cs);
		    sf.setScheda(scheda);
		} else {
		    sf.setScheda(new Dyn2Modellit());
		}
	    } else {
		sf.getScheda().setId(new PkId());
	    }
	} else {
	    sf.setScheda(new Dyn2Modellit());
	}
	List<SchedaDinamicaRigheFilter> righe = sf.getRighe();
	SchedaDinamicaRigheFilter riga = new SchedaDinamicaRigheFilter();
	righe.add(riga);
	sf.setRighe(righe);
	model.addAttribute("isFunzioneDiUtility", isFunzioneDiUtility);
	searchPageAttributes(model, request, isFunzioneDiUtility);
	setPageAttributes(model);
	return "iattivita/search";
    }

    protected String calendarString(String id, String name) {

	return "jQuery(document).ready(function(){Calendar.setup({inputField     :    \"" + id + "\",    button         :    \"" + name + "\" });});";
    }

    protected String getProprietaCampo(Set<Dyn2Campiproprieta> proprietaCampo, ProprietaCampi proprieta, String defaultValue) {

	String result = StringUtils.defaultIfEmpty(defaultValue, "");
	for (Dyn2Campiproprieta dyn2Campiproprieta : proprietaCampo) {
	    if (dyn2Campiproprieta.getId().getProprieta().equalsIgnoreCase(proprieta.name())) {
		result = dyn2Campiproprieta.getValore();
	    }
	}
	return result;
    }

    protected String getPrintValue(String value) {

	return "<b>" + value + "</b>";
    }

    public String getNameOrIdFromCampo(String nomeCampo, boolean isId) {

	String result = nomeCampo;
	if (isId) {
	    result = result.replaceAll("\\.", "_").replaceAll("\\[", "").replaceAll("\\]", "") + "_id";
	} else {
	    return nomeCampo;
	}
	return result;
    }

    protected Object spanErrors(String elementId, String elementName, String errMessage) {

	String display = "display: none;";
	if (StringUtils.isNotBlank(errMessage)) {
	    display = "";
	}
	return "<span id=\"" + elementId + "_ERRORS\" style=\"clear: left;" + display + "\" class=\"error\">" +
	       StringUtils.defaultIfEmpty(errMessage, "") + "</span>";
    }

    @RequestMapping
    public String list(@RequestParam(required = false, value = "isFunzioneDiUtility") Boolean isFunzioneDiUtility,
	    @RequestParam(required = false, value = "codiceIstanza") Integer codiceIstanza, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	// Controllo se è la funzionalità di ricerca delle attività o la ricerca delle attività per la funzionalità
	// di aggiunta massiva di una scheda dinamica.
	if (isFunzioneDiUtility == null) {
	    isFunzioneDiUtility = false;
	}
	if (result.hasErrors()) {
	    return "iattivita/search";
	}
	//------------------------------------------------------ IMPOSTO FILTRO------------------------------------------------------------
	// FILTRO VIENE PRESO DALLA SESSIONE PER RICOSTRUIRE LA RICERCA NEI VARI HISTORY BACK
	if (request.getSession().getAttribute(IATTIVITA_FILTER_IN_SESSION) == null) {
	    request.getSession().setAttribute(IATTIVITA_FILTER_IN_SESSION, IAttivitaFilter.FromIAttivitaCommand(iattivitaCommand, alberoprocService));
	}
	IAttivitaFilter filter = (IAttivitaFilter) request.getSession().getAttribute(IATTIVITA_FILTER_IN_SESSION);
	GenerateTable<IAttivitaListHelper> iattGenerateTable = new IAttivitaHelperTable(filter, isFunzioneDiUtility);
	// scelta se esportare o no la lista
	boolean isExport = true;
	if (isFunzioneDiUtility) {
	    isExport = false;
	}
	String htmlTable = iattGenerateTable.createJMesaList(request, response, "label.gestione_attivita", "iattivita_id", isExport);
	if (htmlTable == null) {
	    return null;
	}
	boolean seUnicoRecordVaiADettaglio = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTATTIVITA_VISATTIVITA, "1", request)
		.equals("1");
	// Controllo se la lista ritorna almeno un record come risultato. Questo mi permetterà di controllare
	// sulla jsp se attivare le funzionmalità di export
	Integer numeroAttivitaTrovate = ((IAttivitaHelperTable) iattGenerateTable).getConteggioAttivita();
	if (numeroAttivitaTrovate == null) {
	    numeroAttivitaTrovate = Integer.valueOf(0);
	}
	// se la ricerca ritorna una sola attività e codiceistanza ==null entro direttamente nel dettaglio  
	//(stiamo utilizzando la funzionalità cerca)
	// Se codiceistanza!=null allora significa che stiamo utilizzando la ricerca per collegare un istanza ad una attività
	//(non deve avere il comportamento specificato sopra) 
	if (numeroAttivitaTrovate == 1 && codiceIstanza == null && seUnicoRecordVaiADettaglio) {
	    Integer codice = ((IAttivitaHelperTable) iattGenerateTable).getCodiceAttivita();
	    return "redirect:view.htm?codice=" + codice;
	}
	model.addAttribute("htmltable", htmlTable);
	// Responsabile per Export
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	iattivitaCommand.setResponsabile(responsabile);
	boolean isCartograficoAttivo = this.cartograficoService.getInfoConnettore().getUtilizzo().getAttivita().getElenco();
	model.addAttribute("cartograficoAttivo", isCartograficoAttivo);
	model.addAttribute("iattivitaCommand", iattivitaCommand);
	model.addAttribute("isFunzioneDiUtility", isFunzioneDiUtility);
	model.addAttribute("numeroAttivitaTrovate", numeroAttivitaTrovate);
	// GESTIONE DEL TIPO ESPORTAZIONE : si verifica la verticalizzazione OBS_EXPORT è attiva:
	// Attiva 	: espoertazione con WS ASP
	// Non attiva	: espoertazione PENTAHO
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAIONE_OBS_EXPORT, request);
	return "iattivita/list";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	IAttivita iattivita = iattivitaService.findById(id);
	fixRenderEntityProperty(iattivita);
	IAttivitaCommand iattivitaCommand = new IAttivitaCommand();
	iattivitaCommand.setEntity(iattivita);
	iattivitaCommand.setDisplayMode(IAttivitaCommand.VIEW);
	int numeroSchede = iAttivitadyn2modellitService.countByAttivita(codice);
	boolean isCartograficoAttivo = this.cartograficoService.getInfoConnettore().getUtilizzo().getAttivita().getModifica();
	model.addAttribute("cartograficoAttivo", isCartograficoAttivo);
	model.addAttribute("iattivitaCommand", iattivitaCommand);
	model.addAttribute("isSchedePresenti", (numeroSchede > 0 ? true : false));
	prepareViewPage(model, request, iattivita, iattivitaCommand);
	setPageAttributes(model);
	return "iattivita/form";
    }

    @RequestMapping
    public void returnCartografico(@RequestParam("codice") Integer codice, @RequestParam("uuidLocalizzazione") String uuidLocalizzazione, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	//se la lettura dei parametri ritorna una lista di attività, va fatta la redirect verso la lista visualizzando
	//solamente quelle attività, se ne ritorna una va mostrato il dettaglio
	List<Integer> idAttivitaList = this.iattivitaService.findIdAttivitaDaiParametriDelCartografico(uuidLocalizzazione);
	if ((idAttivitaList == null || idAttivitaList.isEmpty()) && codice != null) {
	    idAttivitaList = new ArrayList<Integer>();
	    idAttivitaList.add(codice);
	}
	if (idAttivitaList == null) {
	    idAttivitaList = new ArrayList<Integer>();
	}
	if (idAttivitaList.size() == 1) {
	    response.setHeader("Location", "./view.htm?codice=" + idAttivitaList.get(0).toString());
	} else {
	    if (idAttivitaList.size() > 0) {
		IAttivitaFilter filter = new IAttivitaFilter();
		filter.setListaCodiceAttivita(idAttivitaList);
		request.getSession().setAttribute(IATTIVITA_FILTER_IN_SESSION, filter);
	    }
	    response.setHeader("Location", "./list.htm?isFunzioneDiUtility=false&codiceIstanza=");
	}
	response.setStatus(302);
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	this.fixMergeEntityProperty(iattivitaCommand.getEntity());
	try {
	    this.iattivitaService.update(iattivitaCommand.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, iattivitaCommand.getEntity(), true, e);
	    prepareViewPage(model, request, iattivitaCommand.getEntity(), iattivitaCommand);
	    fixRenderEntityProperty(iattivitaCommand.getEntity());
	    return "iattivita/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + iattivitaCommand.getEntity().getId().getCodice() + "&status_msg=02";
    }

    /**
     * 
     * *********************************************************************************************************
     * ********************************************************************************************************* NON
     * USARE COME CHIAMATA WEB , CREATO PER GESTIRE UNA PROCEDURA PER AGGIORNAMNENTO MASSIVO
     **********************************************************************************************************
     ************************************************************************************************************
     * Il metodo replica il comportamento di IAttivitaController.update(...), ma lo fa recupernado l'attività tramite il
     * codice dell'attività. In caso di errore sarà solamente loggato e non restituito per bloccare la procedura
     */
    @RequestMapping
    public String updateById(@RequestParam("codiceAttivita") Integer codiceAttivita, Model model, HttpServletRequest request) {

	try {
	    this.calcoloSnapshotService.ricalcola(codiceAttivita);
	} catch (Exception e) {
	    log.error("Errore durante l'aggiornamento dell'attivita {}: {}[{}]", new Object[] { codiceAttivita, e.getMessage(), e });
	}
	return "redirect:view.htm?codice=" + codiceAttivita + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	IAttivita objToDelete = iattivitaService.findById(iattivitaCommand.getEntity().getId());
	try {
	    iattivitaService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, iattivitaCommand.getEntity(), true, e);
	    prepareViewPage(model, request, objToDelete, iattivitaCommand);
	    fixRenderEntityProperty(iattivitaCommand.getEntity());
	    return "iattivita/form";
	}
	return "redirect:result.htm?status_msg=05";
    }

    @RequestMapping
    public String viewOrAssign(@RequestParam("codice") Integer codice, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	PkId id = new PkId(codice);
	IAttivita iattivita = iattivitaService.findById(id);
	fixRenderEntityProperty(iattivita);
	Istanze istanza = iattivitaCommand.getIstanza();
	if (istanza == null) {
	    return "redirect:view.htm?codice=" + codice;
	} else {
	    istanza = istanzeService.bindDomainObject(istanza, PkId.class, ID_CODICE);
	    checkAccessoInformazioni(istanza, true);
	    try {
		this.attivitaIstanzeService.collegaIstanze(iattivita, istanza);
	    } catch (Exception e) {
		iattivitaCommand.setEntity(iattivita);
		copyErrorsToBindingResult(result, iattivitaCommand.getEntity(), true, e);
		searchPageAttributes(model, request, false);
		setPageAttributes(model);
		fixRenderEntityProperty(iattivitaCommand.getEntity());
		return "iattivita/search";
	    }
	    model.addAttribute("codiceIstanza", istanza.getId().getCodice());
	    return "redirect:result.htm?status_msg=02";
	}
    }

    @RequestMapping
    public String updateCollegaIstanza(@RequestParam("codiceAttivita") Integer codiceAttivita, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    Model model, HttpServletRequest request) {

	PkId id = new PkId(codiceAttivita);
	IAttivita iattivita = iattivitaService.findById(id);
	fixRenderEntityProperty(iattivita);
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	if (istanza == null) {
	    return "redirect:view.htm?codice=" + codiceAttivita;
	} else {
	    model.addAttribute("codiceIstanza", codiceIstanza);
	    istanza = istanzeService.bindDomainObject(istanza, PkId.class, ID_CODICE);
	    checkAccessoInformazioni(istanza, true);
	    try {
		this.attivitaIstanzeService.collegaIstanze(iattivita, istanza);
	    } catch (Exception e) {
		copyErrorsToFlashMessages(iattivita, false, "", e);
		return "redirect:result.htm?status_msg=03";
	    }
	    return "redirect:result.htm?status_msg=02";
	}
    }

    @RequestMapping
    public String result(HttpServletRequest request) {

	return "iattivita/result";
    }

    /**
     * <pre>
     * Il metodo crea una maschera di filtri per recuperare le istanze 
     * che verranno utilizzare per creare le attività (Tabella I_ATTIVITA)
     * 
     * &#64;param request
     * &#64;param model
     * &#64;return
     * </pre>
     * 
     * @RequestMapping public String searchIstanze(HttpServletRequest request, Model model) {
     * 
     *                 // §§§BEGIN§§§ IstanzePerAttivitaFilter filter = new IstanzePerAttivitaFilter(); List<Azioni>
     *                 listAzioni = azioniService.findAll(null, null); List<Software> listSoftwareAbilitati =
     *                 softwareService.findSoftwareAttivi(false); model.addAttribute("codiceComune",
     *                 ORMHelper.getIdcomune()); model.addAttribute("listSoftwareAbilitati", listSoftwareAbilitati);
     *                 model.addAttribute("listAzioni", listAzioni); model.addAttribute("filterIstanze", filter);
     *                 setPageAttributes(model); return "iattivita/searchIstanze"; // §§§END§§§ // @@@ALTERNATIVEEXIT@@@
     *                 return null;@@@ENDALTERNATIVEEXIT@@@ }
     */
    /**
     * <pre>
     * Il metodo crea una maschera di filtri per recuperare le istanze 
     * che verranno utilizzare per creare le attività (Tabella I_ATTIVITA)
     * 
     * &#64;param request
     * &#64;param model
     * &#64;return
     * </pre>
     */
    @RequestMapping
    public String creaAttivita(Model model, @ModelAttribute("filterIstanze") IstanzePerAttivitaFilter istanzePerAttivitaFilter,
	    HttpServletRequest request) {

	//attivitaService.
	if (StringUtils.isNotBlank(istanzePerAttivitaFilter.getComuni().getCodicecomune())) {
	    Comuni comuni = comuniService.findById(istanzePerAttivitaFilter.getComuni().getCodicecomune());
	    istanzePerAttivitaFilter.setComuni(comuni);
	}
	List<Azioni> listAzioni = azioniService.findAll(null, null);
	List<Software> listSoftwareAbilitati = softwareService.findSoftwareAttivi(false);
	model.addAttribute("codiceComune", ORMHelper.getIdcomune());
	model.addAttribute("listSoftwareAbilitati", listSoftwareAbilitati);
	model.addAttribute("listAzioni", listAzioni);
	model.addAttribute("filterIstanze", istanzePerAttivitaFilter);
	return "iattivita/searchIstanze";
    }

    @RequestMapping
    public void ajaxExport(@RequestParam("codice") String codice, @RequestParam("descrizione") String descrizione,
	    @RequestParam("email") String emailResponsabile, @RequestParam("isInviaMail") boolean isInviaMail, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, HttpServletRequest request, HttpServletResponse response) {

	try {
	    String[] codiceEsportazione = codice.split("\\|");
	    String idComuneEsportazione = codiceEsportazione[1];
	    Integer codiceEsp = Integer.parseInt(codiceEsportazione[0]);
	    byte[] responseByte = iattivitaService.exportIAttivita(IAttivitaFilter.FromIAttivitaCommand(iattivitaCommand, alberoprocService),
		    codiceEsp, idComuneEsportazione, emailResponsabile, isInviaMail);
	    String filename = descrizione + "_" + ORMHelper.getIdcomune() + "_" + ORMHelper.getSoftware() + "_" + System.currentTimeMillis() + ".zip";
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    response.setHeader("Content-Disposition", "attachment; filename=" + filename);
	    response.setHeader("Content-transfer-encoding", "binary");
	    String cType = contenttypesService.findMimeTypeByFileName(filename);
	    response.setContentType(cType);
	    response.setContentLength(responseByte.length);
	    ServletOutputStream out = response.getOutputStream();
	    out.write(responseByte);
	    out.flush();
	} catch (Exception e) {
	    log.error("ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
	    throw new RuntimeException("ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
	}
	// §§§END§§§
    }

    @RequestMapping
    public void ajaxExportInData(@RequestParam("codice") String codice, @RequestParam("descrizione") String descrizione,
	    @RequestParam("email") String emailResponsabile, @RequestParam("isInviaMail") boolean isInviaMail,
	    @RequestParam("dataEsportazione") String dataEsportazione, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, HttpServletRequest request, HttpServletResponse response) {

	try {
	    Date dataExp = new Date();
	    if (StringUtils.isNotBlank(dataEsportazione)) {
		dataExp = Utilities.getDate(dataEsportazione, WebConstants.DATE_FORMAT_PATTERN).getTime();
	    }
	    String[] codiceEsportazione = codice.split("\\|");
	    String idComuneEsportazione = codiceEsportazione[1];
	    Integer codiceEsp = Integer.parseInt(codiceEsportazione[0]);
	    byte[] responseByte = iattivitaService.exportIAttivita(IAttivitaFilter.FromIAttivitaCommand(iattivitaCommand, alberoprocService),
		    codiceEsp, idComuneEsportazione, emailResponsabile, dataExp, isInviaMail);
	    String filename = descrizione + "_" + ORMHelper.getIdcomune() + "_" + ORMHelper.getSoftware() + "_" + System.currentTimeMillis() + ".zip";
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    response.setHeader("Content-Disposition", "attachment; filename=" + filename);
	    response.setHeader("Content-transfer-encoding", "binary");
	    String cType = contenttypesService.findMimeTypeByFileName(filename);
	    response.setContentType(cType);
	    response.setContentLength(responseByte.length);
	    ServletOutputStream out = response.getOutputStream();
	    out.write(responseByte);
	    out.flush();
	} catch (Exception e) {
	    log.error("ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
	    throw new RuntimeException("ERRORE WEBSERVICE SIGEPROEXPORT(service error):" + e.getMessage());
	}
	// §§§END§§§
    }

    @RequestMapping
    public String popupstampa(Model model, @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, HttpServletRequest request,
	    HttpServletResponse response) {

	StampaHelper helper = configStampaHelper(IAttivitaFilter.FromIAttivitaCommand(iattivitaCommand, alberoprocService));
	model.addAttribute("helper", helper);
	return "iattivita/popupstampa";
    }

    @RequestMapping
    public String copiaSoggettiCollegati(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam("codiceIstanzaSorgente") Integer codiceIstanzaSorgente, @RequestParam("codice") Integer codiceAttivita, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Istanze istanzaDestinatario = istanzeService.findById(new PkId(codiceIstanza));
	IAttivita iattivita = iattivitaService.findById(new PkId(codiceAttivita));
	Istanze istanzaSorgente = istanzeService.findById(new PkId(codiceIstanzaSorgente));
	checkAccessoInformazioni(istanzaDestinatario, true);
	try {
	    istanzerichiedentiService.copiaIstanzeRichiedenti(istanzaSorgente, istanzaDestinatario);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(iattivita, false, "", e);
	    return "redirect:view.htm?codice=" + codiceAttivita + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + codiceAttivita + "&status_msg=02";
    }

    @RequestMapping
    public String copiaDettaglioInformazioni(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam("codiceIstanzaSorgente") Integer codiceIstanzaSorgente, @RequestParam("codice") Integer codiceAttivita, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Istanze istanzaDestinatario = istanzeService.findById(new PkId(codiceIstanza));
	IAttivita iattivita = iattivitaService.findById(new PkId(codiceAttivita));
	Istanze istanzaSorgente = istanzeService.findById(new PkId(codiceIstanzaSorgente));
	checkAccessoInformazioni(istanzaDestinatario, true);
	try {
	    istanzeattivitaService.copiaIstanzeAttivita(istanzaSorgente, istanzaDestinatario);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(iattivita, false, "", e);
	    return "redirect:view.htm?codice=" + codiceAttivita + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + codiceAttivita + "&status_msg=02";
    }

    @RequestMapping
    public String copiaOrariApertura(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam("codiceIstanzaSorgente") Integer codiceIstanzaSorgente, @RequestParam("codice") Integer codiceAttivita, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Istanze istanzaDestinatario = istanzeService.findById(new PkId(codiceIstanza));
	IAttivita iattivita = iattivitaService.findById(new PkId(codiceAttivita));
	Istanze istanzaSorgente = istanzeService.findById(new PkId(codiceIstanzaSorgente));
	checkAccessoInformazioni(istanzaDestinatario, true);
	try {
	    orariaperturatestataService.copiaOrariAperturaAttivita(istanzaSorgente, istanzaDestinatario);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(iattivita, false, "", e);
	    return "redirect:view.htm?codice=" + codiceAttivita + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + codiceAttivita + "&status_msg=02";
    }

    @RequestMapping
    public String copiaMappali(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam("codiceIstanzaSorgente") Integer codiceIstanzaSorgente, @RequestParam("codice") Integer codiceAttivita, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Istanze istanzaDestinatario = istanzeService.findById(new PkId(codiceIstanza));
	IAttivita iattivita = iattivitaService.findById(new PkId(codiceAttivita));
	Istanze istanzaSorgente = istanzeService.findById(new PkId(codiceIstanzaSorgente));
	checkAccessoInformazioni(istanzaDestinatario, true);
	try {
	    istanzestradarioService.copiaLocalizzazioniWithMappali(istanzaSorgente, istanzaDestinatario, true);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(iattivita, false, "", e);
	    return "redirect:view.htm?codice=" + codiceAttivita + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + codiceAttivita + "&status_msg=02";
    }

    @RequestMapping
    public String copiaLocalizzazioni(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam("codiceIstanzaSorgente") Integer codiceIstanzaSorgente, @RequestParam("codice") Integer codiceAttivita, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Istanze istanzaDestinatario = istanzeService.findById(new PkId(codiceIstanza));
	IAttivita iattivita = iattivitaService.findById(new PkId(codiceAttivita));
	Istanze istanzaSorgente = istanzeService.findById(new PkId(codiceIstanzaSorgente));
	checkAccessoInformazioni(istanzaDestinatario, true);
	try {
	    istanzestradarioService.copiaLocalizzazioniWithMappali(istanzaSorgente, istanzaDestinatario, false);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(iattivita, false, "", e);
	    return "redirect:view.htm?codice=" + codiceAttivita + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + codiceAttivita + "&status_msg=02";
    }

    /**
     * Reupera le schede dimaniche dell'istanza sorgente (Istanzedyn2modellit e IstanzeDyn2dati) e le copia sull'istanza
     * destinatario
     * 
     * @param codiceIstanza
     *            : codice dell'istanza destinataria
     * @param codiceAttivita
     *            : codice dell'atività
     * @param model
     * @param iattivitaCommand
     * @param result
     * @param status
     * @param request
     * @return
     */
    @RequestMapping
    public String copiaSchedeDinamiche(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam("codiceIstanzaSorgente") Integer codiceIstanzaSorgente, @RequestParam("codice") Integer codiceAttivita, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Istanze istanzaDestinatario = istanzeService.findById(new PkId(codiceIstanza));
	IAttivita iattivita = iattivitaService.findById(new PkId(codiceAttivita));
	Istanze istanzaSorgente = istanzeService.findById(new PkId(codiceIstanzaSorgente));
	checkAccessoInformazioni(istanzaDestinatario, true);
	try {
	    log.debug("Inizio copia schede");
	    istanzeService.updateCopiaSchedeIstanza(istanzaSorgente, istanzaDestinatario);
	    log.debug(
		    "copiaSchedeDinamiche# eseguo l'operazione per ricalcolare gli snapshot come se avessi salvato uno/più campi sulla scheda dinamica dell'istanza ");
	    iAttivitaSnapshotService.updateRutineSnapShot(false, istanzaDestinatario.getId().getCodice());
	    log.debug("Copia schede terminata");
	} catch (Exception e) {
	    copyErrorsToFlashMessages(iattivita, false, "", e);
	    return "redirect:view.htm?codice=" + codiceAttivita + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + codiceAttivita + "&status_msg=02";
    }

    /**
     * Farà la copia di:
     * 
     * 1- Soggetti collegati 2- Dettaglio informazionie 3- Orari apertura 4- Mappali
     * 
     * @param codiceIstanza
     * @param codiceAttivita
     * @param model
     * @param request
     * @return
     */
    @RequestMapping
    public String copiaTutti(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam("codiceIstanzaSorgente") Integer codiceIstanzaSorgente, @RequestParam("codice") Integer codiceAttivita, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	Istanze istanzaDestinatario = istanzeService.findById(new PkId(codiceIstanza));
	IAttivita iattivita = iattivitaService.findById(new PkId(codiceAttivita));
	Istanze istanzaSorgente = istanzeService.findById(new PkId(codiceIstanzaSorgente));
	checkAccessoInformazioni(istanzaDestinatario, true);
	try {
	    iattivitaService.insertCopiaTutteLeInfo(istanzaSorgente, istanzaDestinatario);
	} catch (Exception e) {
	    copyErrorsToFlashMessages(iattivita, false, "", e);
	    return "redirect:view.htm?codice=" + codiceAttivita + "&status_msg=03";
	}
	return "redirect:view.htm?codice=" + codiceAttivita + "&status_msg=02";
    }

    @RequestMapping
    public void ajaxPrivateCreaAttivita(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam("skipControlloEsistenza") Boolean skipControlloEsistenza, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws RestrizioneAttivitaEsistenteException {

	// §§§BEGIN§§§
	PkId codiceIstanzaPk = new PkId(codiceIstanza);
	Istanze istanza = istanzeService.findById(codiceIstanzaPk);
	checkAccessoInformazioni(istanza, true);
	if (istanza != null) {
	    iattivitaService.creaAttivita(istanza, skipControlloEsistenza);
	}
    }

    @RequestMapping
    public void ajaxPrivateCollegaAttivita(@RequestParam("codiceAttivita") Integer codiceAttivita,
	    @RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request, HttpServletResponse response) {

	IAttivita attivita = iattivitaService.findById(new PkId(codiceAttivita));
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, true);
	if (istanza != null && attivita != null) {
	    this.attivitaIstanzeService.collegaIstanze(attivita, istanza);
	}
    }

    @RequestMapping
    public void ajaxPrivateScollegaIstanza(@RequestParam("codiceAttivita") Integer codiceAttivita,
	    @RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request, HttpServletResponse response) {

	PkId codiceIstanzaPk = new PkId(codiceIstanza);
	Istanze istanza = istanzeService.findById(codiceIstanzaPk);
	checkAccessoInformazioni(istanza, true);
	if (istanza != null) {
	    try {
		this.attivitaIstanzeService.scollegaIstanze(this.istanzeService.findById(new PkId(codiceIstanza)));
	    } catch (ScollegamentoUnicaIstanzaException ex) {
		String messaggio = getMessageFromBundle(ex.getMessage(), new Object[] { istanza.getNumeroistanza(), ex.getDenominazioneAttivita() });
		throw new RuntimeException(messaggio);
	    }
	}
    }

    @RequestMapping
    public String updateOrdineIstanza(Model model, @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(iattivitaCommand.getEntity());
	try {
	    String ordini = iattivitaCommand.getOrdineIstanze();
	    String codici = iattivitaCommand.getCodiceIstanze();
	    if (!StringUtils.isBlank(ordini) && !StringUtils.isBlank(codici)) {
		String[] strCodici = codici.split(",");
		String[] strOrdini = ordini.split(",");
		Integer[] intCodici = new Integer[strCodici.length];
		Integer[] intOrdini = new Integer[strOrdini.length];
		for (int i = 0; i < strCodici.length; i++) {
		    intCodici[i] = Integer.parseInt(strCodici[i]);
		}
		for (int i = 0; i < strOrdini.length; i++) {
		    intOrdini[i] = Integer.parseInt(strOrdini[i]);
		}
		this.attivitaIstanzeService.updateOrdine(iattivitaCommand.getEntity().getId().getCodice(), intCodici, intOrdini);
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, iattivitaCommand.getEntity(), true, e);
	    prepareViewPage(model, request, iattivitaCommand.getEntity(), iattivitaCommand);
	    fixRenderEntityProperty(iattivitaCommand.getEntity());
	    return "iattivita/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + iattivitaCommand.getEntity().getId().getCodice() + "&status_msg=02";
    }

    /**
     * Lista degli snapshot associati all'attivita
     * 
     * @param codiceattivita
     * @param request
     * @param response
     * @return
     */
    @RequestMapping
    public ModelMap listIattivitaSnapshot(@RequestParam("codiceattivita") Integer codiceattivita, HttpServletRequest request,
	    HttpServletResponse response) {

	IAttivita iAttivita = iattivitaService.findById(new PkId(codiceattivita));
	List<IAttivitaSnapshot> attivitaSnapshots = iAttivitaSnapshotService.findByAttivita(codiceattivita);
	ModelMap model = new ModelMap(attivitaSnapshots);
	boolean export = createJMesaExport(request, response, attivitaSnapshots);
	if (export) {
	    return null;
	}
	model.addAttribute("iattivitaSnapshotList", attivitaSnapshots);
	model.addAttribute("iAttivita", iAttivita);
	return model;
    }

    @RequestMapping
    public ModelMap viewStoricoSnapshot(@RequestParam("codiceattivita") Integer codiceattivita, HttpServletRequest request,
	    HttpServletResponse response) {

	IAttivita iAttivita = iattivitaService.findById(new PkId(codiceattivita));
	IASnapshotViewerHelper ivh = iAttivitaSnapshotService.populateSnapshotViewerHelper(codiceattivita);
	ModelMap model = new ModelMap(ivh);
	model.addAttribute("helper", ivh);
	model.addAttribute("iAttivita", iAttivita);
	return model;
    }

    @RequestMapping
    public String viewIattivitaSnapshot(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	IAttivitaSnapshot iAttivitaSnapshot = iAttivitaSnapshotService.findById(id);
	fixRenderIAttivitaSnapshotProperty(iAttivitaSnapshot);
	model.addAttribute("iAttivitaSnapshot", iAttivitaSnapshot);
	prepareIAttivitadyn2modTSnapshot(iAttivitaSnapshot, model);
	setPageAttributes(model);
	return "iattivita/iattivitaSnapshotform";
    }

    @RequestMapping
    public String addSchedaDinamicaToIattivita(@RequestParam("codicescheda") Integer codicescheda, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	// Devo recuperare l'oggetto filter per poter ricavare quale sono le attivita a cui verrà collegata la scheda dinamica
	String risultato = "";
	if (result.hasErrors()) {
	    return "iattivita/search";
	}
	//------------------------------------------------------ IMPOSTO FILTRO------------------------------------------------------------
	// FILTRO VIENE PRESO DALLA SESSIONE PER RICOSTRUIRE LA RICERCA NEI VARI HISTORY BACK
	if (request.getSession().getAttribute(IATTIVITA_FILTER_IN_SESSION) == null) {
	    request.getSession().setAttribute(IATTIVITA_FILTER_IN_SESSION, IAttivitaFilter.FromIAttivitaCommand(iattivitaCommand, alberoprocService));
	}
	IAttivitaFilter filter = (IAttivitaFilter) request.getSession().getAttribute(IATTIVITA_FILTER_IN_SESSION);
	try {
	    risultato = iattivitaService.updateAddSchedeDinamiche(filter, codicescheda);
	} catch (Exception e) {
	    log.error("Errore durante l'inserimento di una scheda dinamica alla lista di attività: {}", e);
	    throw new RuntimeException("Errore durante l'inserimento di una scheda dinamica alla lista di attività: " + e);
	}
	request.getSession().setAttribute("RISULTATO", risultato);
	return "redirect:../admin/view.htm?status_msg=02";
    }

    @RequestMapping
    public String elaboraSnapshot(HttpServletRequest request, HttpServletResponse response) {

	String risultato = "";
	try {
	    // Per tutte le attività che non hanno lo snapshot li calcola e li aggiunge
	    risultato = iAttivitaSnapshotService.updateElaboraSnapshotAttivita();
	} catch (Exception e) {
	    log.error("Errore durante l'elaborazione dello snap shot delle attività: {}", e);
	    throw new RuntimeException("Errore durante l'elaborazione dello snap shot delle attività:: " + e);
	}
	request.getSession().setAttribute("RISULTATO", risultato);
	return "redirect:../admin/view.htm?status_msg=02";
    }

    @RequestMapping
    public String scambiaOrdine(@RequestParam("idAttivita") Integer idAttivita, @RequestParam("codiceIstanzaPrec") Integer codiceIstanzaPrec,
	    @RequestParam("codiceIstanzaSuc") Integer codiceIstanzaSuc, HttpServletRequest request, HttpServletResponse response) {

	try {
	    // Per tutte le attività che non hanno lo snapshot li calcola e li aggiunge
	    this.iattivitaService.scambiaOrdine(idAttivita, codiceIstanzaPrec, codiceIstanzaSuc);
	} catch (Exception e) {
	    log.error("Errore durante l'elaborazione dell'ordine : {}", e);
	    throw new RuntimeException("Errore durante l'elaborazione dell'ordine: " + e);
	}
	return "redirect:../iattivita/view.htm?codice=" + idAttivita + "&status_msg=02";
    }

    /**
     * Crea il pannello per la scelta delle opzioni per l'export per l'esportazine delle attività tramite il componente
     * esterno Pentaho
     * 
     * @param model
     * @param contestoExport
     *            parametro che indica di tipo di contesto, se non passato di default prede il valore "ATT"
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping
    public String createExportModalitaPentaho(Model model, @RequestParam(required = false, value = "contestoExport") String contestoExport,
	    @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "_comune") String _comune, @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand,
	    BindingResult result, SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws IOException {

	boolean iscontestoExportPresente = true;
	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	iattivitaCommand.setResponsabile(responsabile);
	if (StringUtils.isBlank(contestoExport)) {
	    iscontestoExportPresente = false;
	    contestoExport = "ATT";
	}
	List<Esportazioni> listaEsportazioni;
	if (StringUtils.isBlank(codiceEsportazione)) {
	    if (contestoExport.equals("ATS")) {
		listaEsportazioni = esportazioniService.findEsportazioni(TipicontestoesportazioniEnum.ATTIVITA_SNAPSHOT);
	    } else {
		listaEsportazioni = esportazioniService.findEsportazioni(TipicontestoesportazioniEnum.ATTIVITA);
	    }
	    if (!listaEsportazioni.isEmpty()) {
		iattivitaCommand.setEsportazioni(listaEsportazioni.get(0));
	    }
	} else {
	    List<PkId> ids = new ArrayList<PkId>();
	    PkId id = new PkId(_comune, Integer.parseInt(codiceEsportazione));
	    ids.add(id);
	    if (contestoExport.equals("ATS")) {
		listaEsportazioni = esportazioniService.findEsportazioniEscludiRecord(TipicontestoesportazioniEnum.ATTIVITA_SNAPSHOT, ids);
	    } else {
		listaEsportazioni = esportazioniService.findEsportazioniEscludiRecord(TipicontestoesportazioniEnum.ATTIVITA, ids);
	    }
	    Esportazioni esportazioni = esportazioniService.findById(id);
	    listaEsportazioni.add(0, esportazioni);
	    iattivitaCommand.setEsportazioni(esportazioni);
	}
	model.addAttribute("listaEsportazioni", listaEsportazioni);
	model.addAttribute("iattivitaCommand", iattivitaCommand);
	model.addAttribute("codiceEsportazione", codiceEsportazione);
	String codiceComune = null;
	if (iattivitaCommand.getAttivitaFilter().getComune() != null
		&& StringUtils.isNotBlank(iattivitaCommand.getAttivitaFilter().getComune().getCodicecomune())) {
	    codiceComune = iattivitaCommand.getAttivitaFilter().getComune().getCodicecomune();
	}
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(), codiceComune,
		false);
	model.addAttribute("listMailConfig", listMailConfig);
	// Se il contesto è presente si verifica quale contesto è stato scelto
	if (iscontestoExportPresente) {
	    if (contestoExport.equals("ATS")) {
		model.addAttribute("contestoExport", "ATS");
		return "iattivita/exportIAttivitaInDataPentaho";
	    } else {
		log.error("ATTENZIONE: non è stato scelto nessun contetso di esportazione. Contattare l'assistenza");
		throw new RuntimeException("ATTENZIONE: non è stato scelto nessun contetso di esportazione. Contattare l'assistenza");
	    }
	} else// Se non è passoto di default si va alla jsp che gestisce il contesto di tipo "ATT"
	{
	    return "iattivita/exportIAttivitaPentaho";
	}
    }

    @RequestMapping
    public void ajaxExportModalitaPentaho(@RequestParam(required = false, value = "contestoExport") String contestoExport,
	    @RequestParam(required = false, value = "email") String emailResponsabile,
	    @RequestParam(required = false, value = "isInviaMail") Boolean isInviaMail,
	    @RequestParam(required = false, value = "codiceEsportazione") String codiceEsportazione,
	    @RequestParam(required = false, value = "dataEsportazione") Date dataEsportazione,
	    @RequestParam(required = false, value = "idAccount") String idAccount, Model model,
	    @ModelAttribute("iattivitaCommand") IAttivitaCommand iattivitaCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	// §§§BEGIN§§§
	log.debug("exportModalitaPentaho# Esportazione tramite funzionalità Penthao....");
	log.debug("exportModalitaPentaho# Ricerca della attività le salva sulla tabella TMP_ESPORTAZIONI ");
	String sessionId = iattivitaService.exportModalitaPentaho(iattivitaCommand.getAttivitaFilter(), iattivitaCommand.getEsportazioni(),
		dataEsportazione, StringUtils.defaultIfEmpty(emailResponsabile, ""), contestoExport, BooleanUtils.toBoolean(isInviaMail));
	log.debug("exportModalitaPentaho# Invoco il link che attiva il job di Pentaho");
	try {
	    String pathFile = pentahoService.callTrasformazione(iattivitaCommand.getEsportazioni().getTrasformazione(),
		    iattivitaCommand.getEsportazioni().getParametriesportaziones(), sessionId, response);
	    if (Boolean.TRUE.equals(isInviaMail) && StringUtils.isNotBlank(emailResponsabile)) {
		Pentahocfg pentahocfg = pentahocfgService.findById(ORMHelper.getIdcomune());
		if (EntityUtils.getNestedProperty(pentahocfg.getMailtipo(), ID_CODICE) != null) {
		    Mailtipo mailtipo = mailtipoService.findById(new PkId(pentahocfg.getMailtipo().getId().getCodice()));
		    MailMessageType mailMessage = mailtipoService.populateMailMessageForExport(emailResponsabile, pathFile, mailtipo);
		    String codiceComune = null;
		    if (iattivitaCommand.getAttivitaFilter().getComune() != null
			    && StringUtils.isNotBlank(iattivitaCommand.getAttivitaFilter().getComune().getCodicecomune())) {
			codiceComune = iattivitaCommand.getAttivitaFilter().getComune().getCodicecomune();
		    }
		    Integer localIdAccount = null;
		    if (StringUtils.isNotBlank(idAccount)) {
			localIdAccount = Integer.parseInt(idAccount);
		    }
		    mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), localIdAccount, codiceComune, ORMHelper.getToken(), mailMessage);
		} else {
		    log.error("exportModalitaPentaho#Tipo email non configurato ");
		    FlashMessages.getWarnings()
			    .add("Attenzione, non è stato possibile inviare l'email. Mail tipo non presente nella configurazione di Penthao");
		}
	    }
	} catch (Exception e) {
	    log.error("Errore durante la chiamata alla trasformazione " + iattivitaCommand.getEsportazioni().getDescrizione() + ". Err: ", e);
	    response.getOutputStream().write(("Errore nella generazione del report " + e.getMessage() + "").getBytes());
	}
    }

    private void prepareIAttivitadyn2modTSnapshot(IAttivitaSnapshot iAttivitaSnapshot, Model model) {

	Integer codiceAttivitaSnapshot = iAttivitaSnapshot.getId().getCodice();
	Integer codiceAttivita = iAttivitaSnapshot.getAttivita().getId().getCodice();
	// Definisco gli oggetti di appoggio in cui incapsulo le informazioni da passare alla jsp per la visulaizzazione.
	List<IAttivitadyn2modTSnapshotHelper> listaModTSnapshotHelpers = new ArrayList<IAttivitadyn2modTSnapshotHelper>();
	IAttivitadyn2modTSnapshotHelper modelliTSnapshotHelper = null;
	// Recupero tutti i modelli snapshot associati a IAttivita Snapshot,per ogni modello dovremo recuperare i campi che sono associati
	// e visualizzare il valore associati all' IAttivitaSnapshot passata.
	List<IAttivitadyn2modTSnapshot> listModelliSnapShot = iAttivitadyn2modTSnapshotService
		.findByfindByIAttivitaSnapshot(iAttivitaSnapshot.getId().getCodice());
	// Per ogni modello_T snapshot devo recuperare i campi associati al modello a cui si riferisce.
	for (IAttivitadyn2modTSnapshot iAttivitadyn2modTSnapshot : listModelliSnapShot) {
	    // Creo l'oggetto di appoggio e gli setto il modello t snapshot
	    modelliTSnapshotHelper = new IAttivitadyn2modTSnapshotHelper();
	    modelliTSnapshotHelper.setAttivitadyn2modTSnapshot(iAttivitadyn2modTSnapshot);
	    List<Dyn2Campi> dyn2Campis = dyn2CampiService.findByIdModello(iAttivitadyn2modTSnapshot.getId().getFkD2mtId());
	    // Per ogni campo controllo se lo stesso campo è presnete snapshot dell'attivita
	    List<IAttivitadyn2datiSnapshot> listadatiSnapshots = new ArrayList<IAttivitadyn2datiSnapshot>();
	    for (Dyn2Campi dyn2Campi : dyn2Campis) {
		List<IAttivitadyn2datiSnapshot> listadatiSnapshot = iAttivitadyn2datiSnapshotService
			.findByAttivitaAndAttivitaSnapshotAndCampo(codiceAttivita, codiceAttivitaSnapshot, dyn2Campi.getId().getCodice());
		if (!listadatiSnapshot.isEmpty()) {
		    for (IAttivitadyn2datiSnapshot iAttivitadyn2datiSnapshot : listadatiSnapshot) {
			listadatiSnapshots.add(iAttivitadyn2datiSnapshot);
		    }
		}
	    }
	    modelliTSnapshotHelper.setAttivitadyn2datiSnapshots(listadatiSnapshots);
	    listaModTSnapshotHelpers.add(modelliTSnapshotHelper);
	}
	model.addAttribute("listaModTSnapshotHelpers", listaModTSnapshotHelpers);
    }

    private void fixRenderIAttivitaSnapshotProperty(IAttivitaSnapshot iAttivitaSnapshot) {

	if (EntityUtils.getNestedProperty(iAttivitaSnapshot.getIstanza(), ID_CODICE) == null) {
	    iAttivitaSnapshot.setIstanza(new Istanze());
	}
	if (EntityUtils.getNestedProperty(iAttivitaSnapshot.getAttivita(), ID_CODICE) == null) {
	    iAttivitaSnapshot.setAttivita(new IAttivita());
	}
    }

    private StampaHelper configStampaHelper(IAttivitaFilter filter) {

	// §§§BEGIN§§§
	StampaHelper helper = new StampaHelper();
	if (!StringUtils.isBlank(filter.getPosizioneInArchivio())) {
	    helper.setPosizionearchivio(filter.getPosizioneInArchivio());
	}
	if (EntityUtils.getNestedProperty(filter, "stradario.id.codice") != null) {
	    helper.setCodicestradario(String.valueOf(filter.getStradario().getId().getCodice()));
	    if (!StringUtils.isBlank(filter.getStradario().getDescrizioneCompleta())) {
		helper.setStradario(filter.getStradario().getDescrizioneCompleta());
	    }
	}
	if (EntityUtils.getNestedProperty(filter, "stradariozone.id.codice") != null) {
	    helper.setFkidzona(String.valueOf(filter.getStradariozone().getId().getCodice()));
	    if (!StringUtils.isBlank(filter.getStradariozone().getZona())) {
		helper.setFkidzonadesc(filter.getStradariozone().getZona());
	    }
	}
	if (!StringUtils.isBlank(filter.getDenominazioneAttivita())) {
	    helper.setDenominazioneattivita(filter.getDenominazioneAttivita());
	}
	if (!StringUtils.isBlank(filter.getCivico())) {
	    helper.setNumerocivico(filter.getCivico());
	}
	if (filter.getAttiva() != null) {
	    if (BooleanUtils.isTrue(filter.getAttiva())) {
		helper.setAttiva("1");
	    } else {
		helper.setAttiva("0");
	    }
	}
	if (EntityUtils.getNestedProperty(filter, "stradariocolore.id.codicecolore") != null
		&& StringUtils.isNotBlank(filter.getStradariocolore().getId().getCodicecolore())) {
	    helper.setColore(filter.getStradariocolore().getId().getCodicecolore());
	}
	if (filter.getOperante() != null) {
	    if (BooleanUtils.isTrue(filter.getOperante())) {
		helper.setOperante("1");
	    } else {
		helper.setOperante("0");
	    }
	}
	if (EntityUtils.getNestedProperty(filter, "software.codice") != null) {
	    helper.setParametroSoftware(filter.getSoftware().getCodice());
	} else {
	    StringBuilder softwareList = new StringBuilder();
	    List<Software> softwares = softwareService.findSoftwareAbilitati(getCurrentlyAuthenticatedUserDetails(), true);
	    for (Software software : softwares) {
		softwareList.append(software.getCodice()).append(",");
	    }
	    helper.setParametroSoftware(softwareList.substring(0, softwareList.length() - 1));
	}
	if (!StringUtils.isBlank(filter.getScCodice())) {
	    Alberoproc albero = alberoprocService.findByScCodice(filter.getScCodice());
	    helper.setCodiceinterventoproc(filter.getScCodice());
	    helper.setCodiceintervento(String.valueOf(albero.getId().getCodice()));
	    if (!StringUtils.isBlank(albero.getScDescrizione())) {
		helper.setInterventoproc(albero.getScDescrizione());
	    }
	}
	if (BooleanUtils.isTrue(filter.getCheckIntervento())) {
	    helper.setChkcercatutte("1");
	} else {
	    helper.setChkcercatutte("0");
	}
	if (EntityUtils.getNestedProperty(filter, "richiedente.id.codice") != null) {
	    helper.setCodicerichiedente(String.valueOf(filter.getRichiedente().getId().getCodice()));
	    if (!StringUtils.isBlank(filter.getRichiedente().getRichiedente())) {
		helper.setRichiedente(filter.getRichiedente().getRichiedente());
	    }
	}
	if (EntityUtils.getNestedProperty(filter, "tipimovimento.id.tipomovimento") != null) {
	    helper.setCodicetipomovimento(filter.getTipimovimento().getId().getTipomovimento());
	    if (!StringUtils.isBlank(filter.getTipimovimento().getMovimento())) {
		helper.setDescrizionetipomovimento(filter.getTipimovimento().getMovimento());
	    }
	}
	if (EntityUtils.getNestedProperty(filter, "dettaglioInformazione.id.codiceistat") != null) {
	    helper.setCodiceattivita(filter.getDettaglioInformazione().getId().getCodiceistat());
	    if (!StringUtils.isBlank(filter.getDettaglioInformazione().getIstat())) {
		helper.setCodiceattivitadesc(filter.getDettaglioInformazione().getIstat());
	    }
	}
	if (EntityUtils.getNestedProperty(filter, "tipiarchivioistanze.id.codice") != null) {
	    helper.setCodicetipoarchivio(String.valueOf(filter.getTipiarchivioistanze().getId().getCodice()));
	    if (!StringUtils.isBlank(filter.getTipiarchivioistanze().getArchivio())) {
		helper.setCodicetipoarchiviodesc(filter.getTipiarchivioistanze().getArchivio());
	    }
	}
	if (!StringUtils.isBlank(filter.getNote())) {
	    helper.setLavoriestesa(filter.getNote());
	}
	if (EntityUtils.getNestedProperty(filter, "aree.id.codice") != null) {
	    helper.setCodicearea(String.valueOf(filter.getAree().getId().getCodice()));
	    if (!StringUtils.isBlank(filter.getAree().getDenominazione())) {
		helper.setArea(filter.getAree().getDenominazione());
	    }
	}
	if (EntityUtils.getNestedProperty(filter, "tipoInformazione.id.codicesettore") != null) {
	    helper.setCodicesettore2(String.valueOf(filter.getTipoInformazione().getId().getCodicesettore()));
	    if (!StringUtils.isBlank(filter.getTipoInformazione().getSettore())) {
		helper.setCodicesettore2desc(filter.getTipoInformazione().getSettore());
	    }
	}
	if (!StringUtils.isBlank(filter.getDescrizioneLavori())) {
	    helper.setLavori(filter.getDescrizioneLavori());
	}
	if (EntityUtils.getNestedProperty(filter, "tipologiaistanza.id.codice") != null) {
	    helper.setFkidtipologiaistanza(String.valueOf(filter.getTipologiaistanza().getId().getCodice()));
	    if (!StringUtils.isBlank(filter.getTipologiaistanza().getTiDescrizione())) {
		helper.setFkidtipologiaistanzadesc(filter.getTipologiaistanza().getTiDescrizione());
	    }
	}
	if (EntityUtils.getNestedProperty(filter, "comune.codicecomune") != null) {
	    helper.setCodicecomune(filter.getComune().getCodicecomune());
	}
	String urlStampa = BackofficeNETConstants.getURL_STAMPA_IATTIVITA();
	helper.setUrl_stampa(urlStampa);
	return helper;
    }

    @Override
    protected void fixMergeEntityProperty(IAttivita entity) {

	//Nessuna fix necessaria
    }

    @Override
    protected void fixRenderEntityProperty(IAttivita entity) {

	// Codice necessario in fase di errore altrimenti la pagina di form restituisce questo errore: 
	//Error reading 'transientRichiedenteQualitaAzienda' on type it.gruppoinit.pal.gp.core.domain.Istanze
	if (EntityUtils.getNestedProperty(entity.getIstanza(), ID_CODICE) != null) {
	    Istanze istanza = istanzeService.findById(new PkId(entity.getIstanza().getId().getCodice()));
	    entity.setIstanza(istanza);
	}
	if (EntityUtils.getNestedProperty(entity.getTipologiaAttivita(), ID_CODICE) == null) {
	    entity.setTipologiaAttivita(new IAttivitaTipologie());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	// §§§BEGIN§§§
	Configurazione configurazione = configurazioneService.findById(new ConfigurazioneId());
	if (configurazione == null) {
	    throw new InvalidConfigurationException("Nessuna configurazione trovata per il software [" + ORMHelper.getSoftware() + "]");
	}
	model.addAttribute("configurazione", configurazione);
	boolean isTipologiaistanza = tipologiaistanzaService.existsRecords();
	model.addAttribute("isTipologiaistanzaVisible", isTipologiaistanza);
	boolean isArchiviopratiche = tipiarchivioistanzeService.existsRecords();
	model.addAttribute("isArchiviopraticheVisible", isArchiviopratiche);
	boolean isStradariocolore = stradariocoloreService.existsRecords();
	model.addAttribute("isStradariocoloreVisible", isStradariocolore);
	boolean isSettori = settoriService.existsRecords();
	model.addAttribute("isSettoriVisible", isSettori);
	boolean isAttivita = attivitaService.existsRecords();
	model.addAttribute("isAttivitaVisible", isAttivita);
	boolean isAree = areeService.existsRecords();
	model.addAttribute("isAreeVisibile", isAree);
	Boolean visualizzaDataInizioFine = Boolean.FALSE;
	Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_I_ATTIVITA,
		WebConstants.VERTICALIZZAZIONE_I_ATTIVITA_CAMPO_DYN_FINE_ATT);
	if (vp != null && StringUtils.isNotBlank(vp.getValore())) {
	    visualizzaDataInizioFine = Boolean.TRUE;
	}
	model.addAttribute("visualizzaDataInizioFine", visualizzaDataInizioFine);
    }

    private void prepareViewPage(Model model, HttpServletRequest request, IAttivita iattivita, IAttivitaCommand iattivitaCommand) {

	// §§§BEGIN§§§
	String visstorico = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VIS_VISSTORICOATTIVITA, "0", request);
	boolean isStoricoAttivita = BooleanUtils.toBoolean(visstorico, "1", "0");
	iattivitaCommand.setVisstorico(isStoricoAttivita);
	/////////------------------------------------------------------------------------------------------------------------------------//////
	/////////.---------------------- Gestione dei parameri di configurazione per lo storico delle singole sezioni--------------------//////
	////////-------------------------------------------------------------------------------------------------------------------------/////
	// CONFIG. LOCALIZZAZIONE
	String visstoricoLocalizzazioni = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_STORICO_ATTIVITA_LOCALIZZAZIONI, "0", request);
	boolean isStoricoLocalizzazioni = BooleanUtils.toBoolean(visstoricoLocalizzazioni, "1", "0");
	iattivitaCommand.setFlagStoricoLocalizzazioni(isStoricoLocalizzazioni);
	// CONFIG. SOGGETTI COLLEGATI
	String visstoricoSoggettiCollegati = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_STORICO_ATTIVITA_SOGGETTI_COLLEGATI, "0",
		request);
	boolean isSoggettiCollegati = BooleanUtils.toBoolean(visstoricoSoggettiCollegati, "1", "0");
	iattivitaCommand.setFlagStoricoSoggettiCollegati(isSoggettiCollegati);
	// CONFIG. DETTAGLIO INFO
	String visstoricoDettaglioInfo = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_STORICO_ATTIVITA_DETTAGLIO_INFO, "0", request);
	boolean isDettaglioInfo = BooleanUtils.toBoolean(visstoricoDettaglioInfo, "1", "0");
	iattivitaCommand.setFlagStoricoDettaglioInfo(isDettaglioInfo);
	// CONFIG. ORARI
	String visstoricoOrari = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_STORICO_ATTIVITA_ORARI, "0", request);
	boolean isOrari = BooleanUtils.toBoolean(visstoricoOrari, "1", "0");
	iattivitaCommand.setFlagStoricoOrari(isOrari);
	// CONFIG. AUTORIZZAZIONI
	String visstoricoAutorizzazioni = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_STORICO_ATTIVITA_AUTORIZZAZIONI, "0", request);
	boolean isStoricoAutorizzazioni = BooleanUtils.toBoolean(visstoricoAutorizzazioni, "1", "0");
	iattivitaCommand.setFlagStoricoAutorizzazioni(isStoricoAutorizzazioni);
	boolean isRaggruppaAutoPerIstanza = BooleanUtils
		.toBoolean(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_RAGGRUPPA_AUT_ATT_PER_ISTANZA, "0", request), "1", "0");
	iattivitaCommand.setFlagRaggruppaAutPerIstanza(isRaggruppaAutoPerIstanza);
	// CONFIG. CONCESSIONI
	String visstoricoConcessioni = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_STORICO_ATTIVITA_CONCESSIONI, "0", request);
	boolean isStoricoConcessioni = BooleanUtils.toBoolean(visstoricoConcessioni, "1", "0");
	iattivitaCommand.setFlagStoricoConcessioni(isStoricoConcessioni);
	boolean isRaggruppaConcPerIstanza = BooleanUtils
		.toBoolean(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_RAGGRUPPA_CONC_ATT_PER_ISTANZA, "0", request), "1", "0");
	iattivitaCommand.setFlagRaggruppaConcPerIstanza(isRaggruppaConcPerIstanza);
	// CONFIG. PROCEDIMENTI
	String visstoricoInventarioprocedimenti = leggiParametroConfigurazioneUtente(
		WebConstants.CONF_UTENTE_STORICO_ATTIVITA_INVENTARIO_PROCEDIMENTI, "0", request);
	boolean isInventarioprocedimenti = BooleanUtils.toBoolean(visstoricoInventarioprocedimenti, "1", "0");
	iattivitaCommand.setFlagStoricoEndoprocedimenti(isInventarioprocedimenti);
	// CONFIG. ONERI
	String visstoricoOneri = leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_STORICO_ATTIVITA_ONERI, "0", request);
	boolean isOneri = BooleanUtils.toBoolean(visstoricoOneri, "1", "0");
	iattivitaCommand.setFlagStoricoOneri(isOneri);
	// Controlla quante istanze sono collegate all'attività con data di validità null
	int istanzaDataValiditaNull = iattivitaService.countIstanzeWithDateValiditaNull(iattivita, isStoricoAttivita);
	model.addAttribute("istanzaDataValiditaNull", istanzaDataValiditaNull);
	//----------------------------------------------------------------------------------------------------------------------------------//////
	///--------------------------------------------------------------------------------------------------------------------------------/////
	boolean verticalizzazioneOsservatorioRegionale = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_OSSERVATORIO_REGIONALE);
	model.addAttribute("vert_osservatorio_attivo", verticalizzazioneOsservatorioRegionale);
	boolean verticalizzazioneOsservatorioFvg = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_OSSERVATORIO_FVG);
	model.addAttribute("vert_osservatorio_fvg_attivo", verticalizzazioneOsservatorioFvg);
	IstanzeHelper istanzeHelper = iattivitaService.populateIstanzeHelper(iattivita, isStoricoAttivita);
	//
	//
	IstanzeHelper istanzeHelper1 = iattivitaService.populateIstanzeHelper(iattivita, isStoricoAutorizzazioni);
	List<IstanzeAttivitaHelper> istanzeAttivitaHelpers = iattivitaService
		.populateIstanzeAttivitaAutorizzazioniHelper(istanzeHelper1.getIstanzes(), isRaggruppaAutoPerIstanza);
	//
	model.addAttribute("istanzeAttivitaAutorizzazioniHelpers", istanzeAttivitaHelpers);
	istanzeHelper1 = iattivitaService.populateIstanzeHelper(iattivita, isStoricoConcessioni);
	List<IstanzeAttivitaHelper> istanzeAttivitaConcHelpers = iattivitaService
		.populateIstanzeAttivitaConcessioniHelper(istanzeHelper1.getIstanzes(), isRaggruppaConcPerIstanza);
	//
	model.addAttribute("istanzeAttivitaConcHelpers", istanzeAttivitaConcHelpers);
	model.addAttribute("istanzeHelper", istanzeHelper);
	iattivitaCommand.setIstanzeHelper(istanzeHelper);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VIS_LOCALIZZAZIONI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VIS_MAPPALI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VIS_SOGGCOLL, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VIS_DETTINFO, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VIS_AUTORIZZAZIONI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VIS_CONCESSIONI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VIS_ISTANZE, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VIS_ORARI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VIS_ENDOPROCEDIMENTI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VIS_CALCOLOCANONI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VIS_ONERI, "1", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VIS_VISSTORICOATTIVITA, "0", request);
	//
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_RAGGRUPPA_AUT_ATT_PER_ISTANZA, "1", request);
	model.addAttribute("CONF_UTENTE_VIS_VISSTORICOATTIVITA_CHECKED", toCheckedString(visstorico));
	Verticalizzazioniparametri vRICHSTORICO = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		WebConstants.VERTICALIZZAZIONE_I_ATTIVITA, WebConstants.VERTICALIZZAZIONE_I_ATTIVITA_INVERTI_RICHIEDENTE_STORICO, null);
	Boolean invertiRich = Boolean.FALSE;
	if (vRICHSTORICO != null && StringUtils.isNotBlank(vRICHSTORICO.getValore())) {
	    invertiRich = StringUtils.defaultString(vRICHSTORICO.getValore(), "0").equalsIgnoreCase("1");
	}
	model.addAttribute("INVERTI_RICHIEDENTE", invertiRich);
    }

    private void searchPageAttributes(Model model, HttpServletRequest request, boolean isFunzioneDiUtility) {

	List<Software> softwares = softwareService.findSoftwareAbilitati(getCurrentlyAuthenticatedUserDetails(), true);
	List<Software> softwaresVert = new ArrayList<Software>();
	Verticalizzazioniparametri vertParamGRUPPOSOFTWARE = verticalizzazioniService.getVerticalizzazioniparametri("I_ATTIVITA", "GRUPPOSOFTWARE");
	if (vertParamGRUPPOSOFTWARE != null && StringUtils.isNotBlank(vertParamGRUPPOSOFTWARE.getValore()) && !isFunzioneDiUtility) {
	    StringTokenizer stringTokenizer = new StringTokenizer(vertParamGRUPPOSOFTWARE.getValore(), ",");
	    while (stringTokenizer.hasMoreElements()) {
		String codiceSoftware = (String) stringTokenizer.nextElement();
		for (Software software : softwares) {
		    if (software.getCodice().equals(codiceSoftware)) {
			softwaresVert.add(software);
			break;
		    }
		}
	    }
	    model.addAttribute("softwares", softwaresVert);
	    model.addAttribute("vert_iattivita_grupposoftware", true);
	} else {
	    model.addAttribute("softwares", softwares);
	}
	boolean osservatorio = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_OSSERVATORIO_REGIONALE);
	model.addAttribute("OSSERVATORIO_REGIONALE", Boolean.valueOf(osservatorio));
	boolean isStradariocolore = stradariocoloreService.existsRecords();
	model.addAttribute("isStradariocoloreVisible", isStradariocolore);
	if (isStradariocolore) {
	    List<Stradariocolore> stradariocoloris = stradariocoloreService.findAll();
	    model.addAttribute("stradariocoloris", stradariocoloris);
	}
	model.addAttribute("CONF_UTENTE_LISTATTIVITA_VISATTIVITA_CHECKED",
		toCheckedString(leggiParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_LISTATTIVITA_VISATTIVITA, "1", request)));
    }

    private String toCheckedString(String valore) {

	String result = "";
	if (StringUtils.defaultIfEmpty(valore, "0").equalsIgnoreCase("1")) {
	    result = " checked ";
	}
	return result;
    }

    @RequestMapping
    public void ajaxUpdateSnapshot(@RequestParam("idsnapshot") Integer idsnapshot, @RequestParam("valore") Integer valore, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	IAttivitaSnapshot m = iAttivitaSnapshotService.findById(new PkId(idsnapshot));
	if (m != null) {
	    m.setCodiceOsservatorio(valore);
	    iAttivitaSnapshotService.update(m);
	}
    }

    @RequestMapping
    public void ajaxUpdateIattivitaSnapshots(@RequestParam("idAttivita") Integer idAttivita, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	this.calcoloSnapshotService.ricalcola(idAttivita);
    }

    @RequestMapping
    public void ajaxUpdateAndAggiornaSnapshot(@RequestParam("idAttivita") Integer idAttivita,
	    @RequestParam("dataSnapshotPartenza") String dataSnapshotPartenza, @RequestParam("codiceIstanza") Integer codiceIstanza, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	GregorianCalendar dataSnapshotPartenzaDate = Utilities.getDate(dataSnapshotPartenza, WebConstants.DATE_FORMAT_PATTERN);
	this.calcoloSnapshotService
		.calcola(new ParametriCalcoloSnapshot(idAttivita, dataSnapshotPartenzaDate.getTime(), dataSnapshotPartenzaDate.getTime()));
    }

    @RequestMapping
    public void ajaxPrivateSistemaDenominazioneAttivita(@RequestParam(value = "codiceAttivita", required = false) Integer codiceAttivita, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	try {
	    iattivitaService.updateSistemaDenominazioneAttivita(codiceAttivita);
	} catch (Exception e) {
	    response.getOutputStream().write(e.getMessage().getBytes());
	    return;
	}
	response.getOutputStream().write("OK".getBytes());
    }

    @RequestMapping
    public void ajaxAbilitaModificaPosArchivio(@RequestParam(value = "codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	LoggedUser userDetail = (LoggedUser) getCurrentlyAuthenticatedUser();
	PkId idResponsabile = new PkId(userDetail.getCodiceResponsabile());
	Responsabili responsabile = responsabiliService.findById(idResponsabile);
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	TipoAccessoEnum result = istanzeService.checkAccessoIstanza(istanza, responsabile);
	boolean modificaistanza = istanza.getChiusura().getModificaistanza();
	String res = result.equals(TipoAccessoEnum.CONSENTITO) && modificaistanza ? "OK" : "KO";
	response.getOutputStream().write(res.getBytes());
    }

    @RequestMapping
    public void ajaxUpdateIstanza(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("posArchivio") String posArchivio, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (codiceIstanza == null) {
	    throw new RuntimeException("Impossibile aggiornare i dati dell'istanza senza passare il codice");
	}
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	istanza.setPosizionearchivio(posArchivio);
	try {
	    istanzeService.update(istanza);
	    String messaggio = "MODIFICA POSIZIONEARCHIVIO: l'utente " + getCurrentlyAuthenticatedUserDetails() + " ha modificato l'istanza " +
			       istanza.toString();
	    LoggerModificheIstanze.log(messaggio);
	    response.getOutputStream().write("Ok".getBytes());
	} catch (Exception e) {
	    String risp = e.getMessage();
	    response.getOutputStream().write(risp.getBytes());
	}
    }

    @RequestMapping
    public void ajaxSaveResponsabile(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("codiceOperatore") Integer codiceOperatore,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	if (codiceIstanza == null) {
	    throw new RuntimeException("Impossibile aggiornare i dati senza passare il codice dell'istanza");
	}
	if (codiceOperatore == null) {
	    throw new RuntimeException("Impossibile aggiornare i dati senza passare il codice dell'operatore");
	}
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	Responsabili operatore = responsabiliService.findById(new PkId(codiceOperatore));
	istanza.setResponsabile(operatore);
	try {
	    istanzeService.update(istanza);
	    String messaggio = "MODIFICA OPERATORE: l'utente " + getCurrentlyAuthenticatedUserDetails() +
			       " ha modificato l'operatore per l'istanza " + istanza.toString();
	    LoggerModificheIstanze.log(messaggio);
	    response.getOutputStream().write("Ok".getBytes());
	} catch (Exception e) {
	    String risp = e.getMessage();
	    response.getOutputStream().write(risp.getBytes());
	}
    }

    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping
    public void ajaxViewDettagliIStanza(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException, JAXBException {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	DettaglioIstanzaType istanzaType = new DettaglioIstanzaType(istanza);
	String retVal = Utilities.marshalJsonObject(istanzaType, DettaglioIstanzaType.class, false, Utilities.JAXB_ENCODING_UTF_8);
	response.setContentType("application/json");
	response.getOutputStream().write(retVal.getBytes());
    }
}
