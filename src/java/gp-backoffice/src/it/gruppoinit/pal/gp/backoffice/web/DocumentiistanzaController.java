package it.gruppoinit.pal.gp.backoffice.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenticat;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.CdsattiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.CambioInterventoCommand;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.DocumentiistanzaCommand;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneDTO;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneService;
import it.gruppoinit.pal.gp.core.features.common.bean.BaseEsitoOperazione;
import it.gruppoinit.pal.gp.core.features.common.bean.BaseEsitoOperazione.ESITO;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.IApplicaQRCodeService;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione.VerticalizzazioneQRCodeServiceImpl;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AnagrafedocumentiService;
import it.gruppoinit.pal.gp.core.service.CambioInterventoManager;
import it.gruppoinit.pal.gp.core.service.CdsattiService;
import it.gruppoinit.pal.gp.core.service.DocumentazioneIstanzaHelperService;
import it.gruppoinit.pal.gp.core.service.DocumentiHelperService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocureService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.StcService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni.TIPO_DOCUMENTO;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.DOCERWSClient;

/**
 * 
 * @author Luca Proietti
 */
@Controller
@SessionAttributes(value = { "documentiistanza", "cambioInterventoCommand" })
public class DocumentiistanzaController extends BaseController<Documentiistanza> {

    private static final String ISTANZA = "I";
    private static final Logger log = LoggerFactory.getLogger(DocumentiistanzaController.class);
    @Autowired
    private AnagrafedocumentiService anagrafedocumentiService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;
    @Autowired
    private DocumentiHelperService documentiHelperService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private StcService stcService;
    @Autowired
    private CdsattiService cdsattiService;
    @Autowired
    private IstanzeprocureService istanzeprocureService;
    @Autowired
    private MovimentiallegatiService movimentiallegatiService;
    @Autowired
    private DocumentiAutorizzazioneService documentiautorizzazioneService;
    @Autowired
    private IstanzeallegatiService istanzeallegatiService;
    @Autowired
    private IstanzerichiedentiService istanzerichiedentiService;
    @Autowired
    private CambioInterventoManager cambioInterventoManager;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private DocumentazioneIstanzaHelperService documentazioneIstanzaHelperService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private IApplicaQRCodeService applicaQRCodeService;

    @RequestMapping
    public String list(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request, HttpServletResponse response) {

	// Lista documentiistanza esclusi quelli provenienti da campi dinamici
	DocumentiistanzaCommand documentiistanza = new DocumentiistanzaCommand();
	List<DocumentiistanzaDTO> documentiistanzaListDTO = documentiistanzaService.findDocumentiistanzaDTOByIstanza(codiceIstanza, Boolean.FALSE);
	Boolean isCategoria = Boolean.FALSE;
	for (DocumentiistanzaDTO documentiistanzaDTO : documentiistanzaListDTO) {
	    if (StringUtils.isNotBlank(documentiistanzaDTO.getAlberoprocDocumentiCat())) {
		isCategoria = Boolean.TRUE;
		break;
	    }
	}
	model.addAttribute("categoria_presente", isCategoria);
	documentiistanza.setDocumentiistanzaListDTO(documentiistanzaListDTO);
	// Lista documenti provenienti da campi dinamici
	List<Documentiistanza> documentiistanzaDynList = documentiistanzaService.findByIstanza(codiceIstanza, Boolean.TRUE);
	documentiistanza.setDocumentiistanzaDynList(documentiistanzaDynList);
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(documentiistanza.getIstanza(), false);
	documentiistanza.setIstanza(istanza);
	// Recupero la lista dei documenti dei movimenti associati all'istanza
	List<MovimentiallegatiDTO> lisMovimentiallegatiDTOs = movimentiallegatiService.findMovimentiallegatiDTOByIstanza(codiceIstanza);
	documentiistanza.setMovimentiallegatiDTOs(lisMovimentiallegatiDTOs);
	List<CdsattiDTO> cdsattiDTOs = cdsattiService.findDTOByIstanza(codiceIstanza, Boolean.TRUE);
	documentiistanza.setCdsattiDTOs(cdsattiDTOs);
	// Recupero i documenti delle autorizzazioni e concessioni
	List<DocumentiAutorizzazioneDTO> documentiautorizzazioneDTOs = new ArrayList<DocumentiAutorizzazioneDTO>();
	// 1. recupero le autorizzazioni dell'istanza
	Set<Autorizzazioni> autorizzazioni = istanza.getAutorizzazionis();
	if (!autorizzazioni.isEmpty()) {
	    for (Autorizzazioni aut : autorizzazioni) {
		List<DocumentiAutorizzazioneDTO> docAutDTOs = documentiautorizzazioneService
			.findDocumentiAutorizzazioneDTOByAutorizzazione(aut.getId().getCodice());
		if (docAutDTOs != null) {
		    documentiautorizzazioneDTOs.addAll(docAutDTOs);
		}
	    }
	    // 2. controllo se sono state recuperate autorizzazioni
	    if (documentiautorizzazioneDTOs != null && !documentiautorizzazioneDTOs.isEmpty()) {
		documentiistanza.setAutorizzazionedocumentiDTOs(documentiautorizzazioneDTOs);
	    }
	}
	// Recupero documenti degli endo dell'istanza
	List<IstanzeallegatiDTO> istanzeallegatiDTOs = istanzeallegatiService.findIstanzeallegatiDTOByIstanza(codiceIstanza);
	documentiistanza.setIstanzeallegatiDTOs(istanzeallegatiDTOs);
	// Recupero i documenti delle anagrafiche associate all'istanza
	List<AnagrafedocumentiDTO> listDocAnagrafeTot = new ArrayList<AnagrafedocumentiDTO>();
	Anagrafe richiedente = istanza.getRichiedente();
	List<AnagrafedocumentiDTO> listaDocumentiRichiedete = anagrafedocumentiService.findByIstanzaAndAnagrafeDTO(istanza, richiedente, false);
	listDocAnagrafeTot.addAll(listaDocumentiRichiedete);
	// Recupero i soggetti collegati dell'istanza
	List<Istanzerichiedenti> istanzerichiedentis = istanzerichiedentiService.findByIstanza(istanza);
	for (Istanzerichiedenti istanzerichiedenti : istanzerichiedentis) {
	    List<AnagrafedocumentiDTO> listaDocumentiSoggColl = anagrafedocumentiService.findByIstanzaAndAnagrafeDTO(istanza,
		    istanzerichiedenti.getRichiedente(), false);
	    listDocAnagrafeTot.addAll(listaDocumentiSoggColl);
	}
	documentiistanza.setAnagrafedocumentiDTOs(listDocAnagrafeTot);
	boolean export = createJMesaExport(request, response, documentiistanzaListDTO);
	if (export) {
	    return null;
	}
	// Variabile di controllo che gestisce la funzionalità di di copia massiva degli allegati presenti derivanti da STC.
	// Se true mostrerà attivo il bottone "Salva allegati STC";se false mostrerà il bottone ma disabilitato.
	boolean isAllegatiStcPresenti = stcService.isAllegatiStcExist(codiceIstanza);
	setListPageAttributes(model, request, codiceIstanza, documentiistanza);
	model.addAttribute("documentiistanza", documentiistanza);
	model.addAttribute("isAllegatiStcPresenti", isAllegatiStcPresenti);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_VISUALIZZA_FILE_XML, "1", request);
	return "documentiistanza/list";
    }

    @RequestMapping
    public String pannelloRicercaDocer(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request,
	    HttpServletResponse response) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, false);
	List<ChiaveValoreBean<String, String>> tipiDoc = new ArrayList<ChiaveValoreBean<String, String>>();
	DOCERWSClient dc = new DOCERWSClient(istanza.getComune().getCodicecomune(), ORMHelper.getSoftware(), verticalizzazioniService);
	String token = null;
	try {
	    token = getTokenDocer(dc, request);
	    tipiDoc = dc.getTipiDocumentoByAoo(token);
	} catch (InvalidConfigurationException e) {
	    log.error("pannelloRicercaDocer# {}", e);
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("pannelloRicercaDocer# {}", e);
	}
	model.addAttribute("tipiDocsDocer", tipiDoc);
	model.addAttribute("codiceComune", istanza.getComune().getCodicecomune());
	model.addAttribute("pSoftware", istanza.getSoftware().getCodice());
	return "documentiistanza/pannelloRicercaDocer";
    }

    @RequestMapping
    public String create(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(required = false, value = "isCaricamnetoMultiplo") Boolean isCaricamnetoMultiplo, Model model) {

	if (isCaricamnetoMultiplo == null) {
	    isCaricamnetoMultiplo = false;
	}
	DocumentiistanzaCommand documentiistanza = new DocumentiistanzaCommand();
	Documentiistanza entity = new Documentiistanza();
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	entity.setIstanza(istanza);
	documentiistanza.setEntity(entity);
	documentiistanza.setIstanza(istanza);
	fixRenderEntityProperty(documentiistanza.getEntity());
	documentiistanza.setDisplayMode(DocumentiistanzaCommand.NEW);
	model.addAttribute("documentiistanza", documentiistanza);
	model.addAttribute("isCaricamnetoMultiplo", isCaricamnetoMultiplo);
	setPageAttributesIstanza(model, codiceIstanza);
	return "documentiistanza/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("documentiistanza") DocumentiistanzaCommand documentiistanza, BindingResult result,
	    SessionStatus status, HttpServletRequest request, HttpServletResponse response) throws Exception {

	Documentiistanza entity = documentiistanza.getEntity();
	Integer codiceIstanza = entity.getIstanza().getId().getCodice();
	if (null == entity.getData()) {
	    entity.setData(new Date());
	}
	checkAccessoInformazioni(documentiistanza.getEntity().getIstanza(), true);
	// Recupero dalla request multipart i file passati, se ci sono (sono presenti solo in caso
	// di modalità inserimento multiplo)
	List<MultipartFile> lMultipartFiles = Utilities.getFileFromMultipartRequest(request);
	Integer codiceDocumento = null;
	try {
	    codiceDocumento = documentiistanzaService.insertSingoloOrMultiFile(entity, lMultipartFiles);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, documentiistanza.getEntity(), true, e);
	    fixRenderEntityProperty(documentiistanza.getEntity());
	    boolean isCaricamnetoMultiplo = !lMultipartFiles.isEmpty();
	    model.addAttribute("isCaricamnetoMultiplo", isCaricamnetoMultiplo);
	    documentiistanza.setDisplayMode(DocumentiistanzaCommand.NEW);
	    setPageAttributesIstanza(model, codiceIstanza);
	    return "documentiistanza/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + codiceDocumento + "&status_msg=01";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	DocumentiistanzaCommand documentiistanza = new DocumentiistanzaCommand();
	Documentiistanza entity = documentiistanzaService.findById(id);
	Integer codiceIstanza = entity.getIstanza().getId().getCodice();
	documentiistanza.setEntity(entity);
	documentiistanza.setIstanza(entity.getIstanza());
	checkAccessoInformazioni(documentiistanza.getEntity().getIstanza(), false);
	fixRenderEntityProperty(documentiistanza.getEntity());
	documentiistanza.setDisplayMode(DocumentiistanzaCommand.VIEW);
	model.addAttribute("documentiistanza", documentiistanza);
	setPageAttributesIstanza(model, codiceIstanza);
	if (EntityUtils.getNestedProperty(entity.getOggetto(), "id.codice") != null) {
	    Integer codiceResponsabile = ((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails()).getId().getCodice();
	    oggettiService.updateFileRimuoviBloccoModifica(entity.getOggetto().getId().getCodice(), codiceResponsabile);
	}
	return "documentiistanza/form";
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("documentiistanza") DocumentiistanzaCommand documentiistanza, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Documentiistanza entity = documentiistanza.getEntity();
	Integer codiceIstanza = entity.getIstanza().getId().getCodice();
	if (null == entity.getData()) {
	    entity.setData(new Date());
	}
	try {
	    checkAccessoInformazioni(documentiistanza.getEntity().getIstanza(), true);
	    loggaCancellazioneOggettoIstanza(request);
	    documentiistanzaService.update(entity);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, documentiistanza.getEntity(), true, e);
	    fixRenderEntityProperty(documentiistanza.getEntity());
	    documentiistanza.setDisplayMode(DocumentiistanzaCommand.VIEW);
	    setPageAttributesIstanza(model, codiceIstanza);
	    return "documentiistanza/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + documentiistanza.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("documentiistanza") DocumentiistanzaCommand documentiistanza, BindingResult result,
	    SessionStatus status) {

	Documentiistanza objToDelete = documentiistanzaService.findById(documentiistanza.getEntity().getId());
	Integer codiceIstanza = objToDelete.getIstanza().getId().getCodice();
	Responsabili userlogged = getCurrentlyAuthenticatedUserDetails();
	if (StringUtils.isNotBlank(objToDelete.getStcIdallegato()) || StringUtils.isNotBlank(objToDelete.getStcIddocumento())) {
	    boolean isCancellaMail = userlogged.getFlagCancelladocumentistc() == null ? false
		    : userlogged.getFlagCancelladocumentistc().booleanValue();
	    if (!isCancellaMail) {
		String messaggioErrore = getMessageFromBundle("javascript.alert.operatore_non_puo_cancellare_documento_stc", null);
		throw new SecurityException(messaggioErrore);
	    }
	}
	String descrizioneIstanza = objToDelete.getIstanza().toString();
	String descrizioneDocumento = objToDelete.toString();
	try {
	    checkAccessoInformazioni(objToDelete.getIstanza(), true);
	    documentiistanzaService.delete(objToDelete);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, objToDelete, e);
	    fixRenderEntityProperty(documentiistanza.getEntity());
	    documentiistanza.setDisplayMode(DocumentiistanzaCommand.VIEW);
	    setPageAttributesIstanza(model, codiceIstanza);
	    return "documentiistanza/form";
	}
	LoggerCancellazioni.logCancellazioneDocumentoistanza(userlogged.toString(), descrizioneDocumento, descrizioneIstanza,
		TIPO_DOCUMENTO.Documento);
	status.setComplete();
	return "redirect:list.htm?codiceIstanza=" + documentiistanza.getIstanza().getId().getCodice();
    }

    @RequestMapping
    public String createAllineaDocumenti(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	CambioInterventoCommand cambioInterventoCommand = new CambioInterventoCommand();
	cambioInterventoCommand = cambioInterventoManager.popolaCambioInterventoCommandDocumenti(istanza, cambioInterventoCommand);
	model.addAttribute("cambioInterventoCommand", cambioInterventoCommand);
	return "documentiistanza/allineaDocumenti";
    }

    @RequestMapping
    public String aggiornaDocumenti(@ModelAttribute("documentiistanza") DocumentiistanzaCommand documentiistanza, BindingResult result,
	    SessionStatus status) {

	Istanze istanza = istanzeService.findById(documentiistanza.getIstanza().getId());
	checkAccessoInformazioni(istanza, true);
	Set<Documentiistanza> documentiistanzaList = istanza.getDocumentiistanzas();
	AlberoprocHelper alberoprocHelper = alberoprocService.findAlberoprocHelper(istanza.getAlberoproc());
	Set<AlberoprocDocumenti> alberoprocDocumentis = alberoprocHelper.getAlberoprocDocumentis();
	for (AlberoprocDocumenti alberoprocDocumenti : alberoprocDocumentis) {
	    Boolean trovato = false;
	    for (Documentiistanza documentiistanzaPresente : documentiistanzaList) {
		if (alberoprocDocumenti.getDescrizione().equalsIgnoreCase(documentiistanzaPresente.getDocumento())) {
		    trovato = true;
		    break;
		}
	    }
	    if (!trovato) {
		Documentiistanza documentiistanzaNew = new Documentiistanza();
		documentiistanzaNew.setData(istanza.getData());
		documentiistanzaNew.setDocumento(alberoprocDocumenti.getDescrizione());
		documentiistanzaNew.setNote(alberoprocDocumenti.getNote());
		documentiistanzaNew.setNecessario(alberoprocDocumenti.getRichiesto());
		documentiistanzaNew.setPresente(false);
		documentiistanzaNew.setAlberoprocDocumenticat(alberoprocDocumenti.getAlberoprocDocumenticat());
		documentiistanzaNew.setIstanza(istanza);
		documentiistanzaService.insert(documentiistanzaNew);
	    }
	}
	status.setComplete();
	return "redirect:list.htm?codiceIstanza=" + documentiistanza.getIstanza().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String allineaDocumenti(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model,
	    @ModelAttribute("cambioInterventoCommand") CambioInterventoCommand cambioInterventoCommand, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	PkId id = new PkId(codiceIstanza);
	Istanze istanza = istanzeService.findById(id);
	documentiistanzaService.updateAllineaDocumenti(istanza, cambioInterventoCommand.getDocs());
	return "redirect:list.htm?codiceIstanza=" + cambioInterventoCommand.getEntity().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String eliminaDocumenti(@ModelAttribute("documentiistanza") DocumentiistanzaCommand documentiistanza, BindingResult result,
	    SessionStatus status, Model model, HttpServletRequest request) {

	Responsabili userlogged = getCurrentlyAuthenticatedUserDetails();
	Istanze istanza = istanzeService.findById(documentiistanza.getIstanza().getId());
	String descrizioneIstanza = istanza.toString();
	String descrizioneDocumento = "";
	checkAccessoInformazioni(istanza, true);
	try {
	    if (null == istanza.getDocumentiistanzas() || istanza.getDocumentiistanzas().isEmpty()) {
		throw new IllegalArgumentException("La lista dei documentiistanza è vuota");
	    }
	    List<Documentiistanza> documentisToDelete = new ArrayList<Documentiistanza>();
	    String[] codicidocumentiToDelete = request.getParameterValues("chk_seleziona");
	    if (codicidocumentiToDelete != null) {
		for (int i = 0; i < codicidocumentiToDelete.length; i++) {
		    Documentiistanza objToDelete = documentiistanzaService.findById(new PkId(Integer.parseInt(codicidocumentiToDelete[i])));
		    if (StringUtils.isNotBlank(objToDelete.getStcIdallegato()) || StringUtils.isNotBlank(objToDelete.getStcIddocumento())) {
			boolean isCancellaMail = userlogged.getFlagCancelladocumentistc() == null ? false
				: userlogged.getFlagCancelladocumentistc().booleanValue();
			if (!isCancellaMail) {
			    String messaggioErrore = getMessageFromBundle("javascript.alert.operatore_non_puo_cancellare_documento_stc", null);
			    throw new SecurityException(messaggioErrore);
			}
		    }
		    descrizioneDocumento += "-" + objToDelete.toString();
		    documentisToDelete.add(objToDelete);
		}
	    }
	    documentiistanzaService.deleteDocumentiistanzas(documentisToDelete);
	    LoggerCancellazioni.logCancellazioneDocumentiistanza(userlogged.toString(), descrizioneDocumento, descrizioneIstanza,
		    TIPO_DOCUMENTO.Documento);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, documentiistanza.getEntity(), true, e);
	    model.addAttribute("documentiistanza", documentiistanza);
	    setListPageAttributes(model, request, istanza.getId().getCodice(), documentiistanza);
	    return "documentiistanza/list";
	}
	status.setComplete();
	return "redirect:list.htm?codiceIstanza=" + documentiistanza.getIstanza().getId().getCodice() + "&status_msg=05";
    }

    @RequestMapping
    public void ajaxAbilitaDisabilitaPresente(@RequestParam("codice") Integer codice, @RequestParam("presente") Boolean presente, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	PkId id = new PkId(codice);
	Documentiistanza documentiistanza = documentiistanzaService.findById(id);
	try {
	    checkAccessoInformazioni(documentiistanza.getIstanza(), true);
	    documentiistanza.setPresente(presente);
	    documentiistanzaService.update(documentiistanza);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	}
    }

    @RequestMapping
    public void ajaxAbilitaDisabilitaNecessario(@RequestParam("codice") Integer codice, @RequestParam("necessario") Boolean necessario, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	PkId id = new PkId(codice);
	Documentiistanza documentiistanza = documentiistanzaService.findById(id);
	try {
	    checkAccessoInformazioni(documentiistanza.getIstanza(), true);
	    documentiistanza.setNecessario(necessario);
	    documentiistanzaService.update(documentiistanza);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	}
    }

    //    /**
    //     * Aggiornat tutti i valori del campo presnete per i documenti dell'istanza passato, il campo presente potrà assumer
    //     * i seguenti valori: true e false.
    //     * 
    //     * @param codice
    //     * @param presente
    //     * @param model
    //     * @param request
    //     * @param response
    //     * @throws IOException
    //     */
    //    @RequestMapping
    //    public void ajaxImpostaFlagPresenti(@RequestParam("codice") Integer codice, @RequestParam("presente") String presente, Model model,
    //	    HttpServletRequest request, HttpServletResponse response) throws IOException {
    //
    //	try {
    //	    List<Documentiistanza> lisDocumentiistanzas = documentiistanzaService.findByIstanza(codice);
    //	    Istanze istanze = istanzeService.findById(new PkId(codice));
    //	    checkAccessoInformazioni(istanze, true);
    //	    boolean _presente = (StringUtils.equals(presente, "true") ? true : false);
    //	    for (Documentiistanza documentiistanza : lisDocumentiistanzas) {
    //		documentiistanza.setPresente(_presente);
    //		documentiistanzaService.update(documentiistanza);
    //	    }
    //	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
    //	} catch (Exception e) {
    //	    response.setStatus(500);
    //	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
    //	}
    //    }
    /**
     * Aggiornat tutti i valori del campo presnete per i documenti dell'istanza passato, il campo presente potrà assumer
     * i seguenti valori: true e false.
     * 
     * @param codice
     * @param presente
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void ajaxImpostaAllFlagNecessario(@RequestParam("codice") Integer codice, @RequestParam("necessario") String necessario, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	try {
	    List<DocumentiistanzaDTO> lisDocumentiistanzas = documentiistanzaService.findDocumentiistanzaDTOByIstanza(codice, false);
	    Istanze istanze = istanzeService.findById(new PkId(codice));
	    checkAccessoInformazioni(istanze, true);
	    boolean _necessario = (StringUtils.equals(necessario, "true") ? true : false);
	    for (DocumentiistanzaDTO documentiistanza : lisDocumentiistanzas) {
		documentiistanza.setNecessario(_necessario);
		documentiistanzaService.updateNecessario(documentiistanza.getId().getCodice(), _necessario);
	    }
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	}
    }

    /**
     * IL METODO AGGIORNA LA DATA DEL DOCUMENTO GIà INSERITO
     * 
     * @param codice
     * @param data
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping
    public void ajaxChangeData(@RequestParam("codice") Integer codice, @RequestParam("data") String data, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	Documentiistanza documentiistanza = documentiistanzaService.findById(new PkId(codice));
	checkAccessoInformazioni(documentiistanza.getIstanza(), true);
	try {
	    SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    Date date = null;
	    try {
		date = dateFormat.parse(data);
	    } catch (ParseException e) {
		e.printStackTrace();
	    }
	    documentiistanza.setData(date);
	    documentiistanzaService.update(documentiistanza);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento: " + e.getMessage(), null));
	}
    }

    @RequestMapping
    public void ajaxChangeValueFieldValido(@RequestParam("codice") Integer codice, @RequestParam("valido") Integer valido, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	PkId id = new PkId(codice);
	Documentiistanza documentiistanza = documentiistanzaService.findById(id);
	try {
	    checkAccessoInformazioni(documentiistanza.getIstanza(), true);
	    documentiistanza.setControllook(valido);
	    documentiistanzaService.update(documentiistanza);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	}
    }

    @RequestMapping
    public void ajaxSaveOggettoInDocIstanza(@RequestParam("codiceIstanza") Integer codiceIst, @RequestParam("codiceOggetto") Integer codOggetto,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	try {
	    documentiistanzaService.insertAllegatoInIstanza(codiceIst, codOggetto);
	    response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
	} catch (Exception e) {
	    response.setStatus(500);
	    response.getWriter().write(getMessageFromBundle("label.erroreaggiornamento", null) + ": " + e.getMessage());
	}
    }

    @RequestMapping
    public String impostaTuttiFlagPresenti(@RequestParam("codice") Integer codice, @RequestParam("presente") String presente, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	StringBuffer allertMessage = new StringBuffer("Attenzione, non è stato possibile aggiornare lo stato dei documenti elencati." +
		"I documenti potrebbero essere presenti fisicamente o potrebbero essere Verificati/Validati.\n<ul>");
	boolean isAllertMessagePresent = false;
	try {
	    List<DocumentiistanzaDTO> lisDocumentiistanzas = documentiistanzaService.findDocumentiistanzaDTOByIstanza(codice, false);
	    Istanze istanze = istanzeService.findById(new PkId(codice));
	    checkAccessoInformazioni(istanze, true);
	    boolean _presente = (StringUtils.equals(presente, "true") ? true : false);
	    // Se il flag deve essere posto a true lo setto a tutti
	    if (_presente == true) {
		for (DocumentiistanzaDTO documentiistanzaDTO : lisDocumentiistanzas) {
		    documentiistanzaService.updatePresente(documentiistanzaDTO.getId().getCodice(), _presente);
		}
	    } else {
		for (DocumentiistanzaDTO documentiistanzaDTO : lisDocumentiistanzas) {
		    if (documentiistanzaDTO.getControllook() == null) {
			documentiistanzaService.updatePresente(documentiistanzaDTO.getId().getCodice(), _presente);
		    } else {
			allertMessage.append("<li>" + documentiistanzaDTO.getDocumento());
			String nomeOggetti = (StringUtils.isNotBlank(documentiistanzaDTO.getNomeFile())
				? "(" + documentiistanzaDTO.getNomeFile() + ")" + "</li>"
				: "");
			allertMessage = allertMessage.append(nomeOggetti);
			isAllertMessagePresent = true;
		    }
		}
	    }
	} catch (Exception e) {
	    log.error("Errore durante l'aggiornamento del checkbox presente del documento dell'istanza. Descrizione errore {} [{}]",
		    new Object[] { e.getMessage(), e });
	    FlashMessages.getWarnings().add(e.toString());
	    return "redirect:list.htm?codiceIstanza=" + codice;
	}
	if (isAllertMessagePresent) {
	    allertMessage = allertMessage.append("</ul>");
	    FlashMessages.getWarnings().add(allertMessage.toString());
	}
	return "redirect:list.htm?codiceIstanza=" + codice;
    }

    @RequestMapping
    public void ajaxExportDocumentazioneCsv(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String filename = "documentazione_istanza_export.csv";
	try {
	    byte[] b = documentazioneIstanzaHelperService.exportCsv(codiceIstanza, filename);
	    if (b != null) {
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		response.setHeader("Content-transfer-encoding", "binary");
		response.setContentType("text/csv");
		response.setContentLength(b.length);
		ServletOutputStream out = response.getOutputStream();
		out.write(b);
		out.flush();
	    } else {
		log.error("File vuoto....");
		FlashMessages flashMessages = new FlashMessages();
		List<String> warnings = new ArrayList<String>();
		warnings.add("Impossibile produrre l'exoport, il file creato è vuoto");
		flashMessages.setWarnings(warnings);
		//		throw new RuntimeException("File vuoto.");
	    }
	} catch (Exception e) {
	    FlashMessages flashMessages = new FlashMessages();
	    List<String> warnings = new ArrayList<String>();
	    warnings.add("Errore durante export: " + e.getMessage());
	    flashMessages.setWarnings(warnings);
	    //throw new RuntimeException("Errore durante export: " + e.getMessage());
	}
    }

    @RequestMapping
    public String preDownloadDocumentiZip(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	DocumentiistanzaCommand documentiistanzaCommand = new DocumentiistanzaCommand();
	DocumentiHelper documentiHelper = documentiHelperService.findDocumentiDownloadZip(codiceIstanza);
	documentiistanzaCommand.setDocumentiHelper(documentiHelper);
	model.addAttribute("documentiistanza", documentiistanzaCommand);
	model.addAttribute("codiceIstanza", codiceIstanza);
	return "documentiistanza/listDocPerDownload";
    }

    @RequestMapping
    public void ajaxDownloadDocumentiZip(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model,
	    @ModelAttribute("documentiistanza") DocumentiistanzaCommand documentiistanza, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	ByteArrayOutputStream zipFile = documentiistanzaService.downloadDocumentiZip(documentiistanza.getDocumentiHelper());
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	String filename = "";
	if (StringUtils.isNotBlank(istanza.getCodicepraticatel())) {
	    filename = istanza.getCodicepraticatel() + ".zip";
	} else {
	    filename = "Documenti_istanza_" + istanza.getNumeroistanza() + ".zip";
	}
	if (zipFile != null) {
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    if (request.getParameter("no_dialog") == null) {
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
	    }
	    response.setHeader("Content-transfer-encoding", "binary");
	    response.setContentType("application/zip");
	    response.setContentLength(zipFile.size());
	    ServletOutputStream out = response.getOutputStream();
	    zipFile.writeTo(out);
	    out.flush();
	} else {
	    log.error("File vuoto.");
	    throw new RuntimeException("File vuoto.");
	}
    }

    @Override
    protected void fixMergeEntityProperty(Documentiistanza entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Documentiistanza entity) {

	if (entity.getOggetto() == null) {
	    entity.setOggetto(new Oggetti());
	}
	if (entity.getIstanza() == null) {
	    entity.setIstanza(new Istanze());
	}
	if (entity.getAlberoprocDocumenticat() == null) {
	    entity.setAlberoprocDocumenticat(new AlberoprocDocumenticat());
	}
    }

    @RequestMapping
    public String applicaLayerProtocolloPdf(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("codiceOggetto") String codiceOggetto,
	    Model model, HttpServletRequest request, HttpServletResponse response) {

	try {
	    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	    Oggetti o = oggettiService.findById(new PkId(new Integer(codiceOggetto)));
	    o = movimentiallegatiService.applicaAnnotazioneProtocolloPdf(istanza, null, o);
	    oggettiService.update(o);
	} catch (InvalidConfigurationException e) {
	    FlashMessages.getWarnings().add("Operazione eseguita: " + e.getMessage());
	    return "redirect:list.htm?codiceIstanza=" + codiceIstanza;
	} catch (Exception ex) {
	    FlashMessages.getWarnings().add("Operazione eseguita: " + ex.getMessage());
	    return "redirect:list.htm?codiceIstanza=" + codiceIstanza;
	}
	return "redirect:list.htm?codiceIstanza=" + codiceIstanza + "&status_msg=02";
    }

    @RequestMapping
    public String applicaQrcodePdf(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("codiceOggetto") String codiceOggetto,
	    Model model, HttpServletRequest request, HttpServletResponse response) {

	try {
	    Oggetti o = oggettiService.findById(new PkId(new Integer(codiceOggetto)));
	    applicaQRCodeService.applicaQRCode(codiceIstanza, o, true);
	    oggettiService.update(o);
	} catch (Exception ex) {
	    FlashMessages.getWarnings().add("Operazione eseguita: " + ex.getMessage());
	    return "redirect:list.htm?codiceIstanza=" + codiceIstanza;
	}
	return "redirect:list.htm?codiceIstanza=" + codiceIstanza + "&status_msg=02";
    }

    private void setListPageAttributes(Model model, HttpServletRequest request, Integer codiceIstanza, DocumentiistanzaCommand documentiistanza) {

	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCENDO_DIV, "0", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCMOV_DIV, "0", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCAUT_DIV, "0", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCDYN_DIV, "0", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCANAGR_DIV, "0", request);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCCDS_DIV, "0", request);
	//List<Istanzeprocure> istanzeprocures = istanzeprocureService.findByIstanza(codiceIstanza);
	List<IstanzeprocureDTO> istanzeprocureDTOs = istanzeprocureService.findIstanzeprocureDTOByIstanza(codiceIstanza);
	//model.addAttribute("istanzeprocures", istanzeprocures);
	model.addAttribute("istanzeprocures", istanzeprocureDTOs);
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	boolean docERAttivo = verticalizzazioniService.isAttivaPerComuneESoftware(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER,
		istanza.getSoftware().getCodice(), istanza.getComune().getCodicecomune());
	model.addAttribute("isDocErAttivo", docERAttivo);
	Boolean isAttivoChiusura = Boolean.FALSE;
	List<DocumentiistanzaDTO> docIst = documentiistanza.getDocumentiistanzaListDTO();
	if (!docIst.isEmpty()) {
	    for (DocumentiistanzaDTO docDTO : docIst) {
		isAttivoChiusura = istanzeService.isDataSuccessivaAllaChiusura(codiceIstanza, docDTO.getData());
		docDTO.setIsAttivoAllaChiusura(isAttivoChiusura);
	    }
	}
	String isProtLayer = new VerticalizzazioneProtocolloAttivoServiceImpl(verticalizzazioniService, ORMHelper.getIdcomune())
		.isAttivoApplicaLayer();
	boolean isAttivoLayer = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_APPLICA_LAYER_PROT_PDF);
	boolean isAttivoLayerPDF = isAttivoLayer && isProtLayer.equalsIgnoreCase("S");
	model.addAttribute("isAttivoLayerPDF", isAttivoLayerPDF);
	boolean isAttivoQr = new VerticalizzazioneQRCodeServiceImpl(verticalizzazioniService, ORMHelper.getIdcomune()).isAttivoTemplate();
	model.addAttribute("isAttivoQr", isAttivoQr);
    }

    @RequestMapping
    public void ajaxSpostaCopia(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("codiceIstanzaDest") Integer codiceIstanzaDest,
	    @RequestParam("isCopia") Boolean isCopia, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, JAXBException {

	BaseEsitoOperazione esito = null;
	try {
	    Set<Integer> docIstanzaId = new HashSet<Integer>();
	    String[] parameterValues = request.getParameterValues("docIstanzaId");
	    for (String string : parameterValues) {
		if (Utilities.isInteger(string)) {
		    docIstanzaId.add(Integer.parseInt(string));
		}
	    }
	    esito = documentiistanzaService.updateSpostaCopiaDocumenti(codiceIstanza, codiceIstanzaDest, isCopia, docIstanzaId);
	} catch (Exception e) {
	    log.error("Errore in ajaxSpostaCopia", e);
	    esito = new BaseEsitoOperazione(ESITO.ERROR).addError(e.getMessage());
	}
	try {
	    String risposta = Utilities.marshalJsonObject(esito, esito.getClass(), false, Utilities.JAXB_ENCODING_UTF_8);
	    response.setContentType("application/json");
	    response.getOutputStream().write(risposta.getBytes("utf-8"));
	} catch (Exception e) {
	    log.error("Errore in ajaxSpostaCopia", e);
	    response.setContentType("application/json");
	    response.getOutputStream().write("{\"esito\": \"ERROR\"}".getBytes("utf-8"));
	}
    }

    protected void setPageAttributesIstanza(Model model, Integer codiceIstanza) {

	setPageAttributes(model);
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	boolean docERAttivo = verticalizzazioniService.isAttivaPerComuneESoftware(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER,
		istanza.getSoftware().getCodice(), istanza.getComune().getCodicecomune());
	model.addAttribute("isDocErAttivo", docERAttivo);
    }

    @Override
    protected void setPageAttributes(Model model) {

	Responsabili resp = getCurrentlyAuthenticatedUserDetails();
	model.addAttribute("userlogged", resp);
    }
}
