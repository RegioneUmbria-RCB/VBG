package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.backoffice.definitions.nlapec.gestionemail.NlaGestioneMail;
import it.gruppoinit.pal.gp.backoffice.definitions.nlapec.gestionemail.NlaGestioneMailWSClient;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.ScaricaMessaggioInviatoBinarioRequest;
import it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail.ScaricaMessaggioInviatoBinarioResponse;
import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ContestiMailTipoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AmministrProtocollo;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazioniresponsabili;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PecInbox;
import it.gruppoinit.pal.gp.core.domain.PecInboxId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.ProtocolloFlusso;
import it.gruppoinit.pal.gp.core.domain.ProtocolloMezzi;
import it.gruppoinit.pal.gp.core.domain.ProtocolloModalitainvio;
import it.gruppoinit.pal.gp.core.domain.ProtocolloSmistamenti;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.PECMessageHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AnagrafeFilter;
import it.gruppoinit.pal.gp.core.domain.web.BaseCommand;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.MovimentimailCommand;
import it.gruppoinit.pal.gp.core.domain.web.PECCommand;
import it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand;
import it.gruppoinit.pal.gp.core.domain.web.ProtocolloSoggettoCommand;
import it.gruppoinit.pal.gp.core.domain.web.SessionDetails;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocolloSourceEnum;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.sistema.IVerticalizzazioneParametriSistemaService;
import it.gruppoinit.pal.gp.core.features.suapxml.ISuapXmlService;
import it.gruppoinit.pal.gp.core.features.suapxml.VerticalizzazioneSuapXmlServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocProtocolloService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrProtocolloService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.DocumentiHelperService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;
import it.gruppoinit.pal.gp.core.service.MovimentiBaseService.SceltaMovimentiEnum;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.PecInboxService;
import it.gruppoinit.pal.gp.core.service.ProtocolloFlussoService;
import it.gruppoinit.pal.gp.core.service.ProtocolloMezziService;
import it.gruppoinit.pal.gp.core.service.ProtocolloModalitainvioService;
import it.gruppoinit.pal.gp.core.service.ProtocolloSmistamentiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.ResponsabilisoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.protocollo.schemas.messages.AllegatoResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiFascType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloAnnullatoResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloFascicolatoResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloLettoResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;
import it.gruppoinit.protocollo.schemas.messages.EnumAnnullatoType;
import it.gruppoinit.protocollo.schemas.messages.EnumFascicolatoType;

@Controller
@SessionAttributes(value = { "protocolloCommand", "datiProtocolloLetto", "movimentimail", "documentiHelper" })
public class ProtocollazioneController extends BaseController<ProtocollazioneCommand> {

    public static final String PROTOCOLLAZIONE_MAGIC_IN_SESSION = "_PROTOCOLLAZIONE_MAGIC_";
    private static final Logger log = LoggerFactory.getLogger(ProtocollazioneController.class);
    private AlberoprocService alberoprocService;
    private AlberoprocProtocolloService alberoprocProtocolloService;
    private AmministrazioniService amministrazioniService;
    private AmministrProtocolloService amministrProtocolloService;
    private AnagrafeService anagrafeService;
    private DocumentiistanzaService documentiistanzaService;
    private IstanzeService istanzeService;
    private MailConfigService mailConfigService;
    private MailtipoService mailtipoService;
    private MovimentiService movimentiService;
    private MovimentiallegatiService movimentiallegatiService;
    private ProtocollazioneService protocollazioneService;
    private ResponsabiliService responsabiliService;
    private TipiMovimentoService tipiMovimentoService;
    private TipologiaregistriService tipologiaregistriService;
    private VerticalizzazioniService verticalizzazioniService;
    private IVerticalizzazioneParametriSistemaService verticalizzazioneParametriSistemaService;
    private VerticalizzazioniparametriService verticalizzazioniparametriService;
    private ProtocolloMezziService protocolloMezziService;
    private ProtocolloModalitainvioService protocolloModalitainvioService;
    private ProtocolloSmistamentiService protocolloSmistamentiService;
    private ProtocolloFlussoService protocolloFlussoService;
    private PecInboxService pecInboxService;
    private DocumentiHelperService documentiHelperService;
    private ResponsabilisoftwareService responsabilisoftwareService;
    private SoftwareService softwareService;
    private ComuniService comuniService;
    private OggettiService oggettiService;
    private MovimentiZipLogicoService movimentiZipLogicoService;
    private ISuapXmlService suapXmlService;
    private Map<String, Set<String>> mappaTokenUsati = new HashMap<String, Set<String>>();

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setAlberoprocProtocolloService(AlberoprocProtocolloService alberoprocProtocolloService) {

	this.alberoprocProtocolloService = alberoprocProtocolloService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setAmministrProtocolloService(AmministrProtocolloService amministrProtocolloService) {

	this.amministrProtocolloService = amministrProtocolloService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setMailConfigService(MailConfigService mailConfigService) {

	this.mailConfigService = mailConfigService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setProtocollazioneService(ProtocollazioneService protocollazioneService) {

	this.protocollazioneService = protocollazioneService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setTipologiaregistriService(TipologiaregistriService tipologiaregistriService) {

	this.tipologiaregistriService = tipologiaregistriService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setVerticalizzazioneParametriSistemaService(IVerticalizzazioneParametriSistemaService verticalizzazioneParametriSistemaService) {

	this.verticalizzazioneParametriSistemaService = verticalizzazioneParametriSistemaService;
    }

    @Autowired
    public void setVerticalizzazioniparametriService(VerticalizzazioniparametriService verticalizzazioniparametriService) {

	this.verticalizzazioniparametriService = verticalizzazioniparametriService;
    }

    @Autowired
    public void setProtocolloMezziService(ProtocolloMezziService protocolloMezziService) {

	this.protocolloMezziService = protocolloMezziService;
    }

    @Autowired
    public void setProtocolloModalitainvioService(ProtocolloModalitainvioService protocolloModalitainvioService) {

	this.protocolloModalitainvioService = protocolloModalitainvioService;
    }

    @Autowired
    public void setProtocolloSmistamentiService(ProtocolloSmistamentiService protocolloSmistamentiService) {

	this.protocolloSmistamentiService = protocolloSmistamentiService;
    }

    @Autowired
    public void setProtocolloFlussoService(ProtocolloFlussoService protocolloFlussoService) {

	this.protocolloFlussoService = protocolloFlussoService;
    }

    @Autowired
    public void setPecInboxService(PecInboxService pecInboxService) {

	this.pecInboxService = pecInboxService;
    }

    @Autowired
    public void setDocumentiHelperService(DocumentiHelperService documentiHelperService) {

	this.documentiHelperService = documentiHelperService;
    }

    @Autowired
    public void setResponsabilisoftwareService(ResponsabilisoftwareService responsabilisoftwareService) {

	this.responsabilisoftwareService = responsabilisoftwareService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setMovimentiZipLogicoService(MovimentiZipLogicoService movimentiZipLogicoService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
    }

    @Autowired
    public void setSuapXmlService(ISuapXmlService suapXmlService) {

	this.suapXmlService = suapXmlService;
    }

    @ModelAttribute(value = "protocolloCommand")
    public ProtocollazioneCommand initializeProtocollazioneCommand(HttpServletRequest request) {

	ProtocollazioneCommand retCmd = null;
	Object sesObj = request.getSession().getAttribute("protocolloCommand");
	if (null != sesObj) {
	    retCmd = (ProtocollazioneCommand) sesObj;
	} else {
	    retCmd = new ProtocollazioneCommand();
	}
	return retCmd;
    }

    @RequestMapping
    public String create(@RequestParam(value = "mettiallafirma", required = false) Boolean mettiallafirma,
	    @RequestParam(value = "registrazioneDocer", required = false) Boolean registrazioneDocer,
	    @RequestParam(value = "provenienza", required = true) String provenienza,
	    @RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento,
	    @RequestParam(value = "codicePEC", required = false) String codicePEC,
	    @ModelAttribute(value = "protocolloCommand") ProtocollazioneCommand command, HttpServletRequest request, Model model) {

	// Controllo aggiunto perchè il metodo del controller viene richiamato dal metodo PecInBoxController.protocolloDaPEC.
	// Nel caso venga chiamato da questo metodo, il command è gia popolato e non deve essere rinizzializato.
	String codiceComune = null;
	if (!provenienza.equals(ProtocollazioneCommand.PROVENIENZA_PEC)) {
	    command = new ProtocollazioneCommand();
	}
	if (provenienza.equals(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
	    if (codiceMovimento == null) {
		throw new RuntimeException("E' necessario specificare il parametro codiceMovimento");
	    }
	} else if (provenienza.equals(ProtocollazioneCommand.PROVENIENZA_PEC)) {
	    if (StringUtils.isBlank(codicePEC)) {
		throw new RuntimeException("E' necessario specificare il parametro codicePEC");
	    }
	} else if (provenienza.equals(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
	    if (codiceIstanza == null) {
		throw new RuntimeException("E' necessario specificare il parametro codiceIstanza");
	    }
	} else if (provenienza.equals(ProtocollazioneCommand.PROVENIENZA_AUTORIZZAZIONI)) {
	} else {
	    throw new RuntimeException("Valore del parametro 'provenienza' non valido: " + provenienza);
	}
	if (registrazioneDocer != null) {
	    command.setRegistrazioneParticolareDocEr(registrazioneDocer.booleanValue());
	}
	command.setProvenienza(provenienza);
	command.setMettiAllaFirma(false);
	command.setDisplayMode(ProtocollazioneCommand.NEW);
	String oggetto = "";
	if (codiceMovimento != null) {
	    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	    command.setMovimento(movimento);
	    if (provenienza.equals(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
		Mailtipo mailTipoMovimento = movimentiService.findProtocolloOggetto(movimento);
		if (mailTipoMovimento != null) {
		    oggetto = mailTipoMovimento.getOggetto();
		}
		if (codiceIstanza == null) {
		    codiceIstanza = movimento.getIstanza().getId().getCodice();
		}
		// Verifico se esistono degli allegati eml da scaricare dal server di posta
		log.debug("create# verifico se ci sono degli eml da scaricare dal server di posta.");
		downloadEmls(movimento);
	    }
	}
	Istanze entity = null;
	if (codiceIstanza != null) {
	    entity = istanzeService.findById(new PkId(codiceIstanza));
	    codiceComune = entity.getComune().getCodicecomune();
	    Comuni c = comuniService.findById(codiceComune);
	    command.setComune(c);
	    command.setProtSoftware(entity.getSoftware());
	    command.setEntity(entity);
	    if (provenienza.equals(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
		Mailtipo mailTipoIstanza = istanzeService.findProtocolloOggetto(entity);
		if (mailTipoIstanza != null) {
		    oggetto = mailTipoIstanza.getOggetto();
		}
	    }
	}
	SessionDetails sessionDetails = getSessionDetails(request);
	if (provenienza.equals(ProtocollazioneCommand.PROVENIENZA_PEC)) {
	    codiceComune = command.getComune().getCodicecomune();
	    PecInbox pec = null;
	    Map<String, Object> modelMap = model.asMap();
	    if (modelMap.containsKey("protocolloPecCommand")) {
		PECCommand pecCommand = (PECCommand) modelMap.get("protocolloPecCommand");
		pec = pecCommand.getPec();
	    } else {
		/*
		 * nel caso in cui il PecCommand non sia stato già caricato da PECInboxController 
		 * ne viene predisposto uno ma senza l'elenco degli allegati
		 */
		pec = pecInboxService.findById(new PecInboxId(codicePEC));
		PECCommand pecCommand = new PECCommand();
		pecCommand.setPec(pec);
		model.addAttribute("protocolloPecCommand", pecCommand);
	    }
	    oggetto = pecInboxService.findProtocolloOggetto(pec);
	    command.setPec(pec);
	}
	Verticalizzazioniparametri tipoProtocollo = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOPROTOCOLLO, codiceComune,
		command.getProtSoftware().getCodice());
	if (tipoProtocollo != null && tipoProtocollo.getValore() != null) {
	    Verticalizzazioniparametri mostra_metti_alla_firma = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MOSTRA_METTI_ALLA_FIRMA, codiceComune);
	    if (mostra_metti_alla_firma != null) {
		if (StringUtils.defaultIfEmpty(mostra_metti_alla_firma.getValore(), "N").equalsIgnoreCase("S")) {
		    command.setMettiAllaFirma(mettiallafirma);
		}
	    }
	}
	command.setOggetto(oggetto);
	String classifica = protocollazioneService.findClassifica(entity, codiceComune, command.getProtSoftware().getCodice());
	command.setClassifica(classifica);
	String tipoDocumento = findTipodocumento(entity, provenienza, codiceComune, command.getProtSoftware().getCodice());
	command.setTipoDocumento(tipoDocumento);
	String smistamento = protocollazioneService.findProtocolloSmistamentoDefault(codiceComune, command.getProtSoftware().getCodice());
	command.setSmistamento(smistamento);
	command.setToken(sessionDetails.getToken());
	model.addAttribute("protocolloCommand", command);
	Responsabili responsabile = sessionDetails.getResponsabile();
	responsabile = responsabiliService.findById(new PkId(responsabile.getId().getCodice()));
	Set<ProtocolloFlusso> flussi = responsabile.getProtocolloFlussos();
	if (flussi.isEmpty()) {
	    throw new SecurityException("Il responsabile non è abilitato alla protocollazione");
	}
	List<ProtocolloFlusso> flussiInrequest = new ArrayList<ProtocolloFlusso>();
	Map<String, String> flussiPerProvenienza = getFlussiPerProvenienza(command.getProvenienza());
	for (ProtocolloFlusso protocolloFlusso : flussi) {
	    String codiceFlusso = flussiPerProvenienza.get(protocolloFlusso.getCodice());
	    if (codiceFlusso != null) {
		flussiInrequest.add(protocolloFlusso);
	    }
	}
	model.addAttribute("flussiInRequest", flussiInrequest);
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// /////////////////////Gestione del Flusso di default da mostrare nella maschera ////////////////////////////
	if (!flussiInrequest.isEmpty()) {
	    String codiceFlusso = recuperaFlussoDefault(codiceComune, codiceMovimento, flussiInrequest);
	    command.setFlusso(codiceFlusso);
	    return "redirect:changeFlusso.htm";
	}
	return "protocollazione/form";
    }

    private String recuperaFlussoDefault(String codiceComune, Integer codiceMovimento, List<ProtocolloFlusso> flussiInrequest) {

	String codiceFlusso = "";
	if (codiceMovimento != null) {
	    Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_FLUSSO_MOSTRATO_DEFAULT, codiceComune,
		    ORMHelper.getSoftware());
	    if (verticalizzazioniparametri != null) {
		log.debug("recuperaFlussoDefault# parametro {} della verticalizzazione {} configurato",
			new Object[] { VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
				VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_FLUSSO_MOSTRATO_DEFAULT });
		//  
		if (StringUtils.isNotBlank(verticalizzazioniparametri.getValore())) {
		    // recupero il flusso con codice passato, nel caso il valore passato sia "-", ritorerà sicuramente un valore
		    // null e quindi metterò come di default il seleziona.
		    log.debug("recuperaFlussoDefault# Recupero il flusso per il valore {}", verticalizzazioniparametri.getValore());
		    ProtocolloFlusso protocolloFlusso = protocolloFlussoService.findById(verticalizzazioniparametri.getValore());
		    if (protocolloFlusso != null && StringUtils.isNotBlank(protocolloFlusso.getCodice())) {
			codiceFlusso = protocolloFlusso.getCodice();
		    }
		} else // Non c'è un valore impostato allora considero come il caso di parametro non attivo
		{
		    log.debug("recuperaFlussoDefault# Valore non impostato, equivalente al caso di parametro non attivo");
		    codiceFlusso = flussiInrequest.get(0).getCodice();
		}
	    } else {
		log.debug("recuperaFlussoDefault# Parametro {} non attivo",
			VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_FLUSSO_MOSTRATO_DEFAULT);
		codiceFlusso = flussiInrequest.get(0).getCodice();
	    }
	} else {
	    log.debug("recuperaFlussoDefault# Codice movimento NULL, il comportamento non segue le regole della verticalizzazione {}",
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_FLUSSO_MOSTRATO_DEFAULT);
	    codiceFlusso = flussiInrequest.get(0).getCodice();
	}
	return codiceFlusso;
    }

    @RequestMapping
    public String createUnitaDocumentale(@RequestParam(value = "provenienza", required = true) String provenienza,
	    @RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento,
	    @RequestParam(value = "codicePEC", required = false) String codicePEC,
	    @ModelAttribute(value = "protocolloCommand") ProtocollazioneCommand command, HttpServletRequest request, Model model) {

	String codiceComune = null;
	if (!provenienza.equals(ProtocollazioneCommand.PROVENIENZA_PEC)) {
	    command = new ProtocollazioneCommand();
	}
	if (provenienza.equals(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
	    if (codiceMovimento == null) {
		throw new RuntimeException("E' necessario specificare il parametro codiceMovimento");
	    }
	} else if (provenienza.equals(ProtocollazioneCommand.PROVENIENZA_PEC)) {
	    throw new RuntimeException("La funzionalita' non può essere richiamata dal pannello di gestione PEC");
	} else if (provenienza.equals(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
	    if (codiceIstanza == null) {
		throw new RuntimeException("E' necessario specificare il parametro codiceIstanza");
	    }
	} else if (provenienza.equals(ProtocollazioneCommand.PROVENIENZA_AUTORIZZAZIONI)) {
	} else {
	    throw new RuntimeException("Valore del parametro 'provenienza' non valido: " + provenienza);
	}
	command.setProvenienza(provenienza);
	command.setMettiAllaFirma(Boolean.FALSE);
	command.setDisplayMode(ProtocollazioneCommand.NEW);
	if (codiceMovimento != null) {
	    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	    command.setMovimento(movimento);
	    if (codiceIstanza == null) {
		codiceIstanza = movimento.getIstanza().getId().getCodice();
	    }
	}
	Istanze entity = null;
	if (codiceIstanza != null) {
	    entity = istanzeService.findById(new PkId(codiceIstanza));
	    codiceComune = entity.getComune().getCodicecomune();
	    Comuni c = comuniService.findById(codiceComune);
	    command.setComune(c);
	    command.setProtSoftware(entity.getSoftware());
	    command.setEntity(entity);
	}
	SessionDetails sessionDetails = getSessionDetails(request);
	if (provenienza.equals(ProtocollazioneCommand.PROVENIENZA_PEC)) {
	    codiceComune = command.getComune().getCodicecomune();
	    PecInbox pec = null;
	    Map<String, Object> modelMap = model.asMap();
	    if (modelMap.containsKey("protocolloPecCommand")) {
		PECCommand pecCommand = (PECCommand) modelMap.get("protocolloPecCommand");
		pec = pecCommand.getPec();
	    } else {
		pec = pecInboxService.findById(new PecInboxId(codicePEC));
		PECCommand pecCommand = new PECCommand();
		pecCommand.setPec(pec);
		model.addAttribute("protocolloPecCommand", pecCommand);
	    }
	    command.setPec(pec);
	}
	CodiceDescrizioneBean[] documentiList = protocollazioneService.getListaTipiDocumento(command.getProtSoftware().getCodice(),
		command.getComune().getCodicecomune());
	if (documentiList == null || documentiList.length < 1) {
	    throw new RuntimeException("Non sono stati trovati tipi di documento");
	}
	String tipoDocumento = findTipodocumento(entity, provenienza, codiceComune, command.getProtSoftware().getCodice());
	command.setTipoDocumento(tipoDocumento);
	command.setToken(sessionDetails.getToken());
	model.addAttribute("protocolloCommand", command);
	DocumentiHelper documentiHelper = popolaDocumenti(command);
	command.setDocumentiHelper(documentiHelper);
	model.addAttribute("documentiList", documentiList);
	return "protocollazione/formUnitaDocumentale";
    }

    @RequestMapping
    public String insertUnitaDocumentale(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Integer codiceMovimento = null;
	Integer codiceIstanza = null;
	if (command.getMovimento() != null && command.getMovimento().getId() != null) {
	    codiceMovimento = command.getMovimento().getId().getCodice();
	    codiceIstanza = command.getMovimento().getIstanza().getId().getCodice();
	}
	if (command.getEntity() != null && command.getEntity().getId() != null) {
	    codiceIstanza = command.getEntity().getId().getCodice();
	}
	try {
	    String res = protocollazioneService.creaUnitadocumentale(command.getProtSoftware().getCodice(), command.getComune().getCodicecomune(),
		    command);
	    status.setComplete();
	    return "redirect:unitaDocResult.htm?status_msg=01&cod=" + res;
	} catch (Exception e) {
	    copyErrorsToFlashMessages(command, false, "", e);
	    return "redirect:../protocollazione/createUnitaDocumentale.htm?provenienza=" + command.getProvenienza() + "&codiceIstanza=" +
		   (codiceIstanza == null ? "" : codiceIstanza) + "&codiceMovimento=" + (codiceMovimento == null ? "" : codiceMovimento);
	}
    }

    @RequestMapping
    public String unitaDocResult(@RequestParam("cod") String codice, Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command,
	    HttpServletRequest request) {

	return "protocollazione/udocresult";
    }

    private String findClassificaFascicolo(Istanze entity, String codiceComune, String software, ProtocollazioneCommand command) {

	String classifica = (String) alberoprocService.findParametroprotocollo(entity.getAlberoproc(), "scFascclassifica", codiceComune);
	if (StringUtils.isBlank(StringUtils.defaultString(classifica).trim())) {
	    // recupero la classifica di default dalle verticalizzazioni
	    Verticalizzazioniparametri classificaDef = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CLASSIFICA_FASC_DEFAULT_BO, codiceComune);
	    if (classificaDef != null) {
		String valore = StringUtils.defaultString(classificaDef.getValore()).trim();
		if (StringUtils.isNotBlank(valore)) {
		    classifica = classificaDef.getValore(); // non lo posso trimmare
		}
	    }
	}
	command.setClassificaFascicoloAlberoIntervento(classifica);
	return classifica;
    }

    private String findNumeroFascicolo(Istanze entity, String codiceComune, String software, ProtocollazioneCommand command) {

	String numeroFascicolo = (String) alberoprocService.findParametroprotocollo(entity.getAlberoproc(), "scFascnumero", codiceComune);
	command.setNumeroFascicoloAlberoIntervento(numeroFascicolo);
	return numeroFascicolo;
    }

    private String findTipodocumento(Istanze entity, String provenienza, String codiceComune, String software) {

	String tipoDocumento = "";
	// BOCCI 2012-07-25: Insieme a Chiocci e Ingi abbiamo deciso che
	// 		     Il tipo documento di default viene letto anche per la protocollazione nella maschera delle istanze.
	if (provenienza.equals(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI) || provenienza.equals(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
	    tipoDocumento = (String) alberoprocService.findParametroprotocollo(entity.getAlberoproc(), "scProttipodocumento", codiceComune);
	}
	//LION 2013-10-25: nel caso di protocollazione PEC viene sempre preso il valore della verticalizzazzione
	if (StringUtils.isBlank(tipoDocumento)) {
	    Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPODOCUMENTODEFAULTBO, codiceComune, software);
	    if (verticalizzazioniparametri != null) {
		tipoDocumento = verticalizzazioniparametri.getValore();
	    }
	}
	return tipoDocumento;
    }

    private void prepareViewLeggiProtocollo(ProtocollazioneCommand command, HttpServletRequest request, Model model, SessionDetails sessionDetails,
	    boolean checkOperatoreAbilitato) {

	Responsabili responsabile = sessionDetails.getResponsabile();
	responsabile = responsabiliService.findById(new PkId(responsabile.getId().getCodice()));
	Set<ProtocolloFlusso> flussi = responsabile.getProtocolloFlussos();
	if (checkOperatoreAbilitato && flussi.isEmpty()) {
	    throw new SecurityException("Il responsabile non è abilitato alla protocollazione");
	}
	List<ProtocolloFlusso> flussiInrequest = new ArrayList<ProtocolloFlusso>();
	Map<String, String> flussiPerProvenienza = getFlussiPerProvenienza(command.getProvenienza());
	for (ProtocolloFlusso protocolloFlusso : flussi) {
	    String codiceFlusso = flussiPerProvenienza.get(protocolloFlusso.getCodice());
	    if (codiceFlusso != null) {
		flussiInrequest.add(protocolloFlusso);
	    }
	}
	model.addAttribute("flussiInRequest", flussiInrequest);
	boolean isVerticalizzazioneProtocollo = isVerticalizzazioneAttivaPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE, request, command.getComune().getCodicecomune());
	boolean isProtocolloSigepro = false;
	boolean isVisualizzaValoriNulli = false;
	if (isVerticalizzazioneProtocollo) {
	    // CONTROLLA SE è ATTIVA LA VERTICALIZZAZIONE PROTOCOLLO CON TIPOLOGIA "SIGEPRO"
	    Verticalizzazioniparametri tipoProtocollo = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOPROTOCOLLO,
		    command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	    if (tipoProtocollo != null && tipoProtocollo.getValore().equals(WebConstants.TIPO_PROTOCOLLO_SIGEPRO)) {
		isProtocolloSigepro = true;
	    }
	    // Verifico il parametro VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MOSTRA_DATI_PROTOCOLLO_NULLI della verticalizzazione PROTOCOLLO_ATTIVO
	    // che permette di decide se visualizzare in fase di lettura del protocollo i dati con valore nullo
	    Verticalizzazioniparametri visualizzaValoriNulli = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MOSTRA_DATI_PROTOCOLLO_NULLI,
		    command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	    if (visualizzaValoriNulli != null && visualizzaValoriNulli.getValore().equals("1")) {
		isVisualizzaValoriNulli = true;
	    }
	}
	// verifico se è attiva la funzionalità aggiungi documenti
	Verticalizzazioniparametri visualizzaFunzionalitaAddDocumenti = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VIS_BOTTONE_ADD_DOCUMENTO,
		command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	boolean isVisualizzaAddDoc = false;
	if (visualizzaFunzionalitaAddDocumenti != null && StringUtils.isNotBlank(visualizzaFunzionalitaAddDocumenti.getValore())
		&& visualizzaFunzionalitaAddDocumenti.getValore().equalsIgnoreCase("1"))
	    isVisualizzaAddDoc = true;
	{
	    model.addAttribute("isVisualizzaAddDoc", isVisualizzaAddDoc);
	}
	model.addAttribute("isProtocolloSigepro", isProtocolloSigepro);
	model.addAttribute("isVisualizzaValoriNulli", isVisualizzaValoriNulli);
    }

    private void prepareView(ProtocollazioneCommand command, HttpServletRequest request, Model model, SessionDetails sessionDetails,
	    boolean checkOperatoreAbilitato) {

	///////////// GESTIONE MEZZI E MODALITA' INVIO 
	List<ProtocolloMezzi> protocolloMezzis = protocolloMezziService.findByComuneAndSoftware(command.getComune().getCodicecomune(),
		command.getProtSoftware().getCodice());
	model.addAttribute("protocolloMezzis", protocolloMezzis);
	List<ProtocolloModalitainvio> protocolloModalitainvios = protocolloModalitainvioService
		.findByComuneAndSoftware(command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	model.addAttribute("protocolloModalitainvios", protocolloModalitainvios);
	List<ProtocolloSmistamenti> protocolloSmistamentis = protocolloSmistamentiService
		.findByComuneAndSoftware(command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	model.addAttribute("protocolloSmistamentis", protocolloSmistamentis);
	if (command.isRegistrazioneParticolareDocEr()) {
	    List<ChiaveValoreBean<String, String>> trList = tipologiaregistriService.findAllDocer();
	    if (trList.isEmpty()) {
		throw new RuntimeException(
			"Attenzione! Non è stato impostato alcun registro da poter inviare a DOCER. Controllare la configurazione negli archivi.");
	    }
	    model.addAttribute("regitriList", trList);
	}
	if (EntityUtils.getNestedProperty(command.getEntity(), "id.codice") != null) {
	    Istanze entity = istanzeService.findById(new PkId(command.getEntity().getId().getCodice()));
	    command.setEntity(entity);
	}
	if (EntityUtils.getNestedProperty(command.getMovimento(), "id.codice") != null) {
	    Movimenti movimento = movimentiService.findById(new PkId(command.getMovimento().getId().getCodice()));
	    command.setMovimento(movimento);
	}
	Responsabili responsabile = sessionDetails.getResponsabile();
	responsabile = responsabiliService.findById(new PkId(responsabile.getId().getCodice()));
	Set<ProtocolloFlusso> flussi = responsabile.getProtocolloFlussos();
	if (checkOperatoreAbilitato && flussi.isEmpty()) {
	    throw new SecurityException("Il responsabile non è abilitato alla protocollazione");
	}
	List<ProtocolloFlusso> flussiInrequest = new ArrayList<ProtocolloFlusso>();
	Map<String, String> flussiPerProvenienza = getFlussiPerProvenienza(command.getProvenienza());
	for (ProtocolloFlusso protocolloFlusso : flussi) {
	    String codiceFlusso = flussiPerProvenienza.get(protocolloFlusso.getCodice());
	    if (codiceFlusso != null) {
		flussiInrequest.add(protocolloFlusso);
	    }
	}
	model.addAttribute("documentiList",
		protocollazioneService.getListaTipiDocumento(command.getProtSoftware().getCodice(), command.getComune().getCodicecomune()));
	List<Amministrazioni> amministrazionis = findAmministrazioni(command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	model.addAttribute("amministrazioniList", amministrazionis);
	model.addAttribute("flussiInRequest", flussiInrequest);
	setPageAttributes(model, command.getComune().getCodicecomune());
	Date oggi = Calendar.getInstance().getTime();
	command.setDataFascicolo(oggi);
	// GESTIONE DEI CAMPI CLASSIFICA DEL PROTOCOLLO E CLASSIFICA DEI PARAMETRI DI PROTOCOLLAZIONE
	boolean isVerticalizzazioneProtocollo = isVerticalizzazioneAttivaPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE, request, command.getComune().getCodicecomune());
	if (isVerticalizzazioneProtocollo) {
	    Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTISCI_FASCICOLAZIONE,
		    command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	    if (vp != null) {
		String valore = StringUtils.defaultIfEmpty(vp.getValore(), "0");
		model.addAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTISCI_FASCICOLAZIONE, valore);
	    }
	    CodiceDescrizioneBean[] classifica = protocollazioneService.getListaClassifiche(command.getProtSoftware().getCodice(),
		    command.getComune().getCodicecomune());
	    model.addAttribute("listaClassifiches", classifica);
	    boolean isModificaClassifica = false;
	    Verticalizzazioniparametri modificaClassifica = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MODIFICA_CLASSIFICA,
		    command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	    if (modificaClassifica != null && modificaClassifica.getValore().equals("1")) {
		isModificaClassifica = true;
	    }
	    // CONTROLLA SE è ATTIVA LA VERTICALIZZAZIONE PROTOCOLLO CON TIPOLOGIA "SIGEPRO"
	    boolean isProtocolloSigepro = false;
	    boolean isProtocolloDOCER = false;
	    Verticalizzazioniparametri tipoProtocollo = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOPROTOCOLLO,
		    command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	    if (tipoProtocollo != null && tipoProtocollo.getValore().equals(WebConstants.TIPO_PROTOCOLLO_SIGEPRO)) {
		isProtocolloSigepro = true;
	    } else if (tipoProtocollo != null && tipoProtocollo.getValore().equals(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER)) {
		isProtocolloDOCER = true;
	    }
	    model.addAttribute("isDocEr", isProtocolloDOCER);
	    model.addAttribute("isModificaClassifica", isModificaClassifica);
	    model.addAttribute("isProtocolloSigepro", isProtocolloSigepro);
	    Verticalizzazioniparametri tiPEC = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER, WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER_TIPO_INVIO_PEC,
		    command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	    Boolean isInvioPecDocer = Boolean.FALSE;
	    if (tiPEC != null) {
		String valore = StringUtils.defaultIfEmpty(tiPEC.getValore(), "0");
		if (valore.equalsIgnoreCase("2")) {
		    isInvioPecDocer = Boolean.TRUE;
		}
	    }
	    model.addAttribute("IS_DOCER_TIPOINVIO_PEC_MANUALE", isInvioPecDocer.booleanValue());
	}
	// recupero gli allegati degli altri movimenti appartenenti all'istanza, solo nel caso che sia una protocollazione da movimento
	if (EntityUtils.getNestedProperty(command.getMovimento(), "id.codice") != null) {
	    log.debug(
		    "prepareView# Protocollazione da movimento, recupero tutti gli allegati per i movimenti dell' istanza {}[{}] escluso il movimento {}",
		    new Object[] { command.getEntity().getNumeroistanza(), command.getEntity().getId().getCodice(),
			    command.getMovimento().getId().getCodice() });
	}
	CodiceDescrizioneBean[] classifica = protocollazioneService.getListaClassifiche(command.getProtSoftware().getCodice(),
		command.getComune().getCodicecomune());
	model.addAttribute("listaClassificheFascicolis", classifica);
	boolean isModificaClassificafasc = false;
	Verticalizzazioniparametri modificaClassificaParametriProt = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MODIFICA_CLASSIFICA_PARAMETRI_PROT,
		command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	if (modificaClassificaParametriProt != null && modificaClassificaParametriProt.getValore().equals("1")) {
	    isModificaClassificafasc = true;
	}
	model.addAttribute("isModificaClassificaParametriProt", isModificaClassificafasc);
	popolaFascicoloMovimentoIstanza(command, true);
	////////////////////////// GESTIONE VISUALIZZAZIONE OPZIONE INVIO DOCUMENTO COME LINK///////// //////////////////
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC)) {
	    boolean isVerticSezionePerInvioAllegatiComeLink = false;
	    Verticalizzazioniparametri modificaInviaDocumentiComeLink = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC, WebConstants.VERTICALIZZAZIONE_PARAMETRI_INVIA_DOC_COME_LINK_IN_PROT,
		    command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	    if (modificaInviaDocumentiComeLink != null && modificaInviaDocumentiComeLink.getValore().equals("1")) {
		isVerticSezionePerInvioAllegatiComeLink = true;
	    }
	    model.addAttribute("isVerticSezionePerInvioAllegatiComeLink", isVerticSezionePerInvioAllegatiComeLink);
	    // Recupero dal tipo movimento se sono presenti le configurazione per l'invio
	    if (EntityUtils.getNestedProperty(command.getMovimento(), "id.codice") != null) {
		Tipimovimento tipimovimento = command.getMovimento().getTipomovimento();
		command.setFlgProtocollalinkall(tipimovimento.getFlgProtocollalinkall());
		// prima controllo se è già popolato, nel caso il metodo venga chimato dopo il verificarsi
		// di un eccezione vogliamo che il tipo di documento venga popolato con quello popolato
		// sulla maschera (l'utente potrebbe averlo cambiato)
		if (EntityUtils.getNestedProperty(tipimovimento.getLetteraTipoAllegati(), "id.codice") != null) {
		    command.setLetteraTipoAllegati(tipimovimento.getLetteraTipoAllegati());
		} else {
		    command.setLetteraTipoAllegati(new Letteretipo());
		}
	    }
	}
	// GESTISCE LO SMISTAMENTO MULTIPLO 
	// Se il parametro PROTOCOLLO_ATTIVO.IS_SMISTAMENTO_MULTIPLO==1 ALLORA SI POSSONO INSERIRE N DESTINATARI PER FLUSSO
	// INTERNO E ARRIVO
	// (DESTINATARI : AMMINISTRAZIONI INTERNE)
	int smistamento = protocollazioneService.findSmistamentoMultiplo(command.getComune().getCodicecomune(),
		command.getProtSoftware().getCodice());
	model.addAttribute("isSmistamentoMultiploAttivoArrivo", Boolean.valueOf((smistamento == 2 || smistamento == 1)));
	model.addAttribute("isSmistamentoMultiploAttivoInterno", Boolean.valueOf((smistamento == 3 || smistamento == 1)));
	boolean isViewInviaEmail = true;
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE)
		&& verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE)) {
	    Verticalizzazioniparametri vpInvioEmail = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE,
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_NON_MOSTRARE_INVIOMAIL);
	    if (vpInvioEmail != null && StringUtils.isNotBlank(vpInvioEmail.getValore()) && vpInvioEmail.getValore().equalsIgnoreCase("1")) {
		isViewInviaEmail = false;
	    }
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////// VERTICALIZZAZIONE CHE GESTISCE L'ATTIVAZIONE DELLA FUNZIONALITA' /////////////////////////////////////
	//////////////////////////// INTEGRAZIONE SUAP/EDILIZIA TRAMITE CANALE DI PROTOCOLLAZIONE //////////////////////////////////////////
	VerticalizzazioneSuapXmlServiceImpl vertSuapXmlService = new VerticalizzazioneSuapXmlServiceImpl(verticalizzazioniService,
		command.getComune().getCodicecomune());
	boolean isIntegrazioneSuapEdilizia = vertSuapXmlService.isAttiva() && vertSuapXmlService.visualizzaBottoneSuProtocollazioneMovimento();
	boolean isAllegatoXmlPraticaPresente = false;
	// controllo se ho già il file di pratica suap allora metto solo il messaggio di indicazione protocollazione
	if (EntityUtils.getNestedProperty(command.getMovimento(), "id.codice") != null) {
	    isAllegatoXmlPraticaPresente = this.suapXmlService.isDocPresenteSuMovimento(command.getMovimento().getId().getCodice());
	}
	Verticalizzazioniparametri vpANEL = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VIS_PANEL_RICERCA_FASCICOLO,
		command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	model.addAttribute("isPanelRicercaFascicoli", Boolean.FALSE);
	if (vpANEL != null) {
	    String valore = StringUtils.defaultIfEmpty(vpANEL.getValore(), "0");
	    if (valore.equalsIgnoreCase("1")) {
		model.addAttribute("isPanelRicercaFascicoli", Boolean.TRUE);
	    }
	}
	Verticalizzazioniparametri vpANEL_pre_fasc = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_PANEL_RIC_FASC_PREC_CLASSIF,
		command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	model.addAttribute("isPanelPrecompilaClassifica", Boolean.FALSE);
	if (vpANEL_pre_fasc != null) {
	    String valore = StringUtils.defaultIfEmpty(vpANEL_pre_fasc.getValore(), "0");
	    if (valore.equalsIgnoreCase("1")) {
		model.addAttribute("isPanelPrecompilaClassifica", Boolean.TRUE);
	    }
	}
	Verticalizzazioniparametri vpAbilitaIndirizziMail = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_ABILITA_INDIRIZZI_EMAIL,
		command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	model.addAttribute("abilitaIndirizziMail", Boolean.FALSE);
	if (vpAbilitaIndirizziMail != null && StringUtils.defaultIfEmpty(vpAbilitaIndirizziMail.getValore(), "0").equalsIgnoreCase("1")) {
	    model.addAttribute("abilitaIndirizziMail", Boolean.TRUE);
	}
	model.addAttribute("isAllegatoXmlPraticaPresente", isAllegatoXmlPraticaPresente);
	model.addAttribute("isIntegrazioneSuapEdilizia", isIntegrazioneSuapEdilizia);
	model.addAttribute("isViewInviaEmail", isViewInviaEmail);
	boolean isZipLogico = ifZipLogicoExist(command.getMovimento().getId().getCodice());
	model.addAttribute("isZipLogico", isZipLogico);
	if (isZipLogico && command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_PARTENZA)) {
	    command.setFlgProtocollaZipLogico(Boolean.TRUE);
	}
	String magic = request.getSession().getId() + UUID.randomUUID();
	request.getSession().setAttribute(PROTOCOLLAZIONE_MAGIC_IN_SESSION, magic);
	if (command.getMittente() != null && command.getMittente().getAmministrazioni() == null) {
	    command.getMittente().setAmministrazioni(new Amministrazioni()); // FIX NullValueInNestedPathException: Invalid property 'mittente.amministrazioni'
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    private Boolean ifZipLogicoExist(Integer codicemovimento) {

	return this.movimentiZipLogicoService.isZipLogicoExistInMovimento(codicemovimento);
    }

    private DocumentiHelper popolaDocumenti(ProtocollazioneCommand command) {

	if (EntityUtils.getNestedProperty(command.getMovimento(), "id.codice") != null) {
	    return documentiHelperService.findDocumentiInvioDocumentiProtocollo(command.getMovimento().getId().getCodice(),
		    command.getMovimento().getIstanza().getId().getCodice(), true);
	} else {
	    return documentiHelperService.findDocumentiInvioDocumentiProtocollo(null, command.getEntity().getId().getCodice(), false);
	}
    }

    private List<Amministrazioni> findAmministrazioni(String codiceComune, String software) {

	List<Amministrazioni> amministrazionis = amministrazioniService.findAmministrazioniByDescrizioneForProtocolloRegistri("", false, codiceComune,
		software);
	Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZAUORAGGRUPPATE, codiceComune, software);
	List<Amministrazioni> amministrazionis2 = new ArrayList<Amministrazioni>();
	if (verticalizzazioniparametri != null) {
	    String valore = StringUtils.defaultIfEmpty(verticalizzazioniparametri.getValore(), "");
	    valore = valore.trim();
	    Map<String, Amministrazioni> mapAmm = new HashMap<String, Amministrazioni>();
	    String propertyField = "";
	    if (valore.equalsIgnoreCase("PROT_UO")) {
		propertyField = "protUo";
	    } else if (valore.equalsIgnoreCase("PROT_RUOLO")) {
		propertyField = "protRuolo";
	    } else {
		propertyField = "amministrazione";
	    }
	    for (Amministrazioni amministrazioni : amministrazionis) {
		if (propertyField.equalsIgnoreCase("amministrazione")) {
		    Field field = ReflectionUtils.findField(Amministrazioni.class, propertyField);
		    String propValue = "";
		    try {
			Method get = amministrazioni.getClass().getMethod("get" + StringUtils.capitalize(field.getName()));
			propValue = (String) get.invoke(amministrazioni, new Object[0]);
		    } catch (Exception e) {
			log.error("findAmministrazioni: errore nell'invocare il metodo get {}: {}", propertyField, e.getMessage());
			throw new RuntimeException(e.getMessage());
		    }
		    if (StringUtils.isNotBlank(propValue)) {
			mapAmm.put(propValue, amministrazioni);
		    }
		} else {
		    AmministrProtocollo s = amministrProtocolloService.findByAmministrazioneComuneESoftware(amministrazioni.getId().getCodice(),
			    codiceComune, software);
		    String propValue = "";
		    if (propertyField.equalsIgnoreCase("protUo")) {
			if (StringUtils.isNotBlank(s.getProtUo())) {
			    propValue = s.getProtUo();
			}
		    } else if (propertyField.equalsIgnoreCase("protRuolo") && StringUtils.isNotBlank(s.getProtRuolo())) {
			propValue = s.getProtRuolo();
		    }
		    if (StringUtils.isNotBlank(propValue)) {
			mapAmm.put(propValue, amministrazioni);
		    }
		}
	    }
	    Set<Entry<String, Amministrazioni>> ammSet = mapAmm.entrySet();
	    for (Entry<String, Amministrazioni> entry : ammSet) {
		Amministrazioni amm = new Amministrazioni();
		amm.setId(entry.getValue().getId());
		if (propertyField.equalsIgnoreCase("amministrazione")) {
		    AmministrProtocollo ammtemp = amministrProtocolloService
			    .findByAmministrazioneComuneESoftware(entry.getValue().getId().getCodice(), codiceComune, software);
		    String descrizione = entry.getValue().getAmministrazione();
		    if (ammtemp != null) {
			descrizione += " [";
			if (StringUtils.isNotBlank(ammtemp.getProtUo())) {
			    descrizione += ammtemp.getProtUo();
			}
			if (StringUtils.isNotBlank(ammtemp.getProtRuolo())) {
			    descrizione += " - " + ammtemp.getProtRuolo();
			}
			descrizione += "]";
		    }
		    amm.setAmministrazione(descrizione);
		} else {
		    amm.setAmministrazione(entry.getKey());
		}
		amministrazionis2.add(amm);
	    }
	} else {
	    amministrazionis2 = amministrazionis;
	}
	return amministrazionis2;
    }

    @RequestMapping
    public String removeSoggetto(@RequestParam("idx") Integer idx, @RequestParam(required = false, value = "mittOrDest") String mittOrDest,
	    Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, HttpServletRequest request) {

	if (StringUtils.isNotBlank(command.getFlusso())) {
	    List<ProtocolloSoggettoCommand> soggetti = null;
	    if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_INTERNO)) {
		// Non c'è bisogno di controllare se è per mittente o destinatario, per ora in 
		// PARTENZA POSSONO ESSERE MULTIPLI SOLO I DESTINATARI
		soggetti = command.getDestinataris();
	    } else if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_PARTENZA)) {
		// Non c'è bisogno di controllare se è per mittente o destinatario, per ora in 
		// PARTENZA POSSONO ESSERE MULTIPLI SOLO I DESTINATARI
		soggetti = command.getDestinataris();
	    } else if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_ARRIVO)) {
		if (StringUtils.isNotBlank(mittOrDest) && mittOrDest.equalsIgnoreCase("D")) {
		    soggetti = command.getDestinataris();
		} else {
		    soggetti = command.getMittentis();
		}
	    }
	    ProtocolloSoggettoCommand soggettoDaRimuovere = null;
	    if (soggetti != null) {
		for (int a = 0; a < soggetti.size(); a++) {
		    if (a == idx.intValue()) {
			soggettoDaRimuovere = soggetti.get(a);
		    }
		}
	    }
	    if (soggettoDaRimuovere != null) {
		soggetti.remove(soggettoDaRimuovere);
	    }
	    ProtocolloSoggettoCommand[] pscArray = new ProtocolloSoggettoCommand[soggetti.size()];
	    pscArray = soggetti.toArray(pscArray);
	    List<ProtocolloSoggettoCommand> nuovaLista = new ArrayList<ProtocolloSoggettoCommand>(pscArray.length);
	    for (int i = 0; i < pscArray.length; i++) {
		nuovaLista.add(i, pscArray[i]);
	    }
	    if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_PARTENZA)) {
		// Non c'è bisogno di controllare se è per mittente o destinatario, per ora in 
		// PARTENZA POSSONO ESSERE MULTIPLI SOLO I DESTINATARI
		command.setDestinataris(nuovaLista);
	    } else if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_ARRIVO)) {
		if (StringUtils.isNotBlank(mittOrDest) && mittOrDest.equalsIgnoreCase("D")) {
		    command.setDestinataris(nuovaLista);
		} else {
		    command.setMittentis(nuovaLista);
		}
	    } else if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_INTERNO)) {
		// Non c'è bisogno di controllare se è per mittente o destinatario, per ora in 
		// PARTENZA POSSONO ESSERE MULTIPLI SOLO I DESTINATARI
		command.setDestinataris(nuovaLista);
	    }
	}
	return "redirect:viewCreate.htm";
    }

    /**
     * Utilizzato solo nel caso di protocollazione da PEC per eliminare in un solo colpo tutti i mittenti che il sistema
     * popola in automatico ma in alcuni casi sono troppi e sbagliati.
     * 
     * @param model
     * @param command
     * @param request
     * @return
     */
    @RequestMapping
    public String eliminaMittenti(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, HttpServletRequest request) {

	List<ProtocolloSoggettoCommand> mitts = command.getMittentis();
	if (mitts != null) {
	    mitts.clear();
	}
	// FIX : Popolo la lista con un mittente fittizio vuoto, per evitare un errore 
	//javascript che non mi permette di aggiungere nuovamente nuovi mittenti
	command.getMittentis().add(ProtocolloSoggettoCommand.fromGeneric());
	return "redirect:viewCreate.htm";
    }

    @RequestMapping
    public String ajaxCercaFascicoli(@RequestParam("anno") String anno, @RequestParam("numeroFascicolo") String numeroFascicolo,
	    @RequestParam("oggetto") String oggetto, @RequestParam("classifica") String classifica, Model model,
	    @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, HttpServletRequest request) {

	DatiFascType df = new DatiFascType();
	df.setAnnoFascicolo(anno);
	df.setNumeroFascicolo(numeroFascicolo);
	df.setOggettoFascicolo(oggetto);
	df.setClassificaFascicolo(classifica);
	try {
	    List<DatiFascType> list = protocollazioneService.cercaFascicoli(command.getProtSoftware().getCodice(),
		    command.getComune().getCodicecomune(), df);
	    model.addAttribute("list", list);
	} catch (Exception e) {
	    model.addAttribute("errore", e.getMessage());
	}
	return "protocollazione/ajaxCercaFascicoli";
    }

    @RequestMapping
    public String aggiorna(@RequestParam("tipoSoggetto") String tipoSoggetto, Model model,
	    @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, HttpServletRequest request) {

	// Verifica se è attivo lo smistamento multiplo, agisce su Flusso INTERNO ed ARRIVO, pemette di inserire più destinatari
	int smistamento = protocollazioneService.findSmistamentoMultiplo(command.getComune().getCodicecomune(),
		command.getProtSoftware().getCodice());
	command.setDisplayMode(ProtocollazioneCommand.NEW);
	Integer codiceIstanza = null;
	Integer codiceMovimento = null;
	if (command.getEntity() != null && command.getEntity().getId() != null && command.getEntity().getId().getCodice() != null) {
	    codiceIstanza = command.getEntity().getId().getCodice();
	}
	if (command.getMovimento() != null && command.getMovimento().getId() != null && command.getMovimento().getId().getCodice() != null) {
	    codiceMovimento = command.getMovimento().getId().getCodice();
	}
	Verticalizzazioniparametri vpAbilitaIndirizziMail = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_ABILITA_INDIRIZZI_EMAIL,
		command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	boolean abilitaIndirizziMail = false;
	if (vpAbilitaIndirizziMail != null && StringUtils.defaultIfEmpty(vpAbilitaIndirizziMail.getValore(), "0").equalsIgnoreCase("1")) {
	    abilitaIndirizziMail = true;
	}
	if (StringUtils.isNotBlank(command.getFlusso())) {
	    //. GESTIONE FLUSSO INTERNO
	    if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_INTERNO)) {
		List<ProtocolloSoggettoCommand> destinatari = command.getDestinataris();
		int j = 0;
		//. GESTIONE SMISTAMENTO ATTIVO
		if (smistamento == 3 || smistamento == 1) {
		    for (int a = 0; a < destinatari.size(); a++) {
			ProtocolloSoggettoCommand psc = destinatari.get(a);
			Amministrazioni amm = psc.getAmministrazioni();
			amm = amministrazioniService.bindDomainObject(amm, PkId.class, "id.codice");
			if (amm != null) {
			    psc.setAmministrazioni(amm);
			}
			j++;
			checkTipoSoggetto(tipoSoggetto, psc);
		    }
		}
		ProtocolloSoggettoCommand vuoto = ProtocolloSoggettoCommand.fromGeneric();
		setMezziEmodInvioDefault(vuoto, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
		if (smistamento == 3 || smistamento == 1) {
		    destinatari.add(j, vuoto);
		    command.setDestinataris(destinatari);
		}
	    } else if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_PARTENZA)) {
		//. GESTIONE FLUSSO PARTENZA
		List<ProtocolloSoggettoCommand> destinatari = command.getDestinataris();
		int i = 0;
		for (int a = 0; a < destinatari.size(); a++) {
		    ProtocolloSoggettoCommand psc = destinatari.get(a);
		    Amministrazioni amm = psc.getAmministrazioni();
		    amm = amministrazioniService.bindDomainObject(amm, PkId.class, "id.codice");
		    Anagrafe anagrafe = psc.getAnagrafe();
		    anagrafe = anagrafeService.bindDomainObject(anagrafe, PkId.class, "id.codice");
		    if (amm != null) {
			psc.setAmministrazioni(amm);
			if (abilitaIndirizziMail && StringUtils.isBlank(psc.getEmail())) {
			    psc.setEmail(StringUtils.defaultIfEmpty(amm.getPec(), amm.getEmail()));
			}
		    }
		    if (anagrafe != null) {
			if (abilitaIndirizziMail && StringUtils.isBlank(psc.getEmail())) {
			    String email = istanzeService.findEmailSoggettoPratica(anagrafe.getId().getCodice(), codiceIstanza, codiceMovimento);
			    psc.setEmail(email);
			}
			psc.setAnagrafe(anagrafe);
		    }
		    i++;
		    checkTipoSoggetto(tipoSoggetto, psc);
		}
		ProtocolloSoggettoCommand vuoto = ProtocolloSoggettoCommand.fromGeneric();
		setMezziEmodInvioDefault(vuoto, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
		destinatari.add(i, vuoto);
		command.setDestinataris(destinatari);
	    } else if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_ARRIVO)) {
		//. GESTIONE FLUSSO ARRIVO
		List<ProtocolloSoggettoCommand> mittenti = command.getMittentis();
		int i = 0;
		for (int a = 0; a < mittenti.size(); a++) {
		    ProtocolloSoggettoCommand psc = mittenti.get(a);
		    Amministrazioni amm = psc.getAmministrazioni();
		    amm = amministrazioniService.bindDomainObject(amm, PkId.class, "id.codice");
		    Anagrafe anagrafe = psc.getAnagrafe();
		    anagrafe = anagrafeService.bindDomainObject(anagrafe, PkId.class, "id.codice");
		    if (amm != null) {
			psc.setAmministrazioni(amm);
		    }
		    if (anagrafe != null) {
			psc.setAnagrafe(anagrafe);
		    }
		    i++;
		    checkTipoSoggetto(tipoSoggetto, psc);
		}
		List<ProtocolloSoggettoCommand> destinatari = command.getDestinataris();
		int j = 0;
		//. GESTIONE SMISTAMENTO ATTIVO
		if (smistamento == 2 || smistamento == 1) {
		    for (int a = 0; a < destinatari.size(); a++) {
			ProtocolloSoggettoCommand psc = destinatari.get(a);
			Amministrazioni amm = psc.getAmministrazioni();
			amm = amministrazioniService.bindDomainObject(amm, PkId.class, "id.codice");
			if (amm != null) {
			    psc.setAmministrazioni(amm);
			}
			j++;
			checkTipoSoggetto(tipoSoggetto, psc);
		    }
		}
		ProtocolloSoggettoCommand vuoto = ProtocolloSoggettoCommand.fromGeneric();
		setMezziEmodInvioDefault(vuoto, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
		mittenti.add(i, vuoto);
		command.setMittentis(mittenti);
		if (smistamento == 2 || smistamento == 1) {
		    destinatari.add(j, vuoto);
		    command.setDestinataris(destinatari);
		}
	    }
	}
	return "redirect:viewCreate.htm";
    }

    private void checkTipoSoggetto(String tipoSoggetto, ProtocolloSoggettoCommand psc) {

	Integer codiceAmministrazione = (Integer) EntityUtils.getNestedProperty(psc.getAmministrazioni(), "id.codice");
	Integer codiceAnagrafe = (Integer) EntityUtils.getNestedProperty(psc.getAnagrafe(), "id.codice");
	if (codiceAmministrazione != null && codiceAnagrafe != null) {
	    if (tipoSoggetto.equalsIgnoreCase("R")) {
		psc.setAmministrazioni(new Amministrazioni());
	    } else {
		psc.setAnagrafe(new Anagrafe());
	    }
	}
    }

    @RequestMapping
    public String changeFlusso(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, HttpServletRequest request) {

	command.setOggettoProtocolloMail(null);
	command.setCorpoProtocolloMail(null);
	boolean isMostraConfigurazioneInvioDocComeLink = false;
	command.setDisplayMode(ProtocollazioneCommand.NEW);
	command.resetSoggetti();
	if (StringUtils.isNotBlank(command.getFlusso())) {
	    SessionDetails sessDetails = getSessionDetails(request);
	    Amministrazioni amm1 = bindAmministrazione(sessDetails, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice(),
		    command);
	    // BOCCI 2012-09-04 BUGZILLA [649] 
	    if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_INTERNO)) {
		//
		Amministrazioni amm2 = bindAmministrazioneFromMovimento(command.getMovimento());
		if (amm2 != null) {
		    // BOCCI 2012-09-04 BUGZILLA [649] 
		    // 			Se flusso: INTERNO: mette l’amministrazione AMM-2 come destinataria come proposta e AMM-1 come mittente
		    command.getDestinatario().setAmministrazioni(amm2);
		}
		command.getMittente().setAmministrazioni(amm1);
	    } else if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_PARTENZA)) {
		command.getMittente().setAmministrazioni(amm1);
		// Imposta la variabile che permette di mostrare la configurazione dell'invio allegati
		// Tramite PEC come link
		isMostraConfigurazioneInvioDocComeLink = true;
		try {
		    //I DUE CASI NON POSSONO INCROCIARSI
		    if (ProtocollazioneCommand.PROVENIENZA_ISTANZA.equals(command.getProvenienza()) && command.getEntity() != null) {
			Mailtipo mailTipoIstanze = istanzeService.findProtocolloOggetto(command.getEntity());
			command.setOggettoProtocolloMail(mailTipoIstanze.getProtocolloOggettoMail());
			command.setCorpoProtocolloMail(mailTipoIstanze.getProtocolloCorpoMail());
		    }
		    if (ProtocollazioneCommand.PROVENIENZA_MOVIMENTI.equals(command.getProvenienza()) && command.getMovimento() != null) {
			Mailtipo mailTipoMovimento = movimentiService.findProtocolloOggetto(command.getMovimento());
			command.setOggettoProtocolloMail(mailTipoMovimento.getProtocolloOggettoMail());
			command.setCorpoProtocolloMail(mailTipoMovimento.getProtocolloCorpoMail());
		    }
		} catch (Exception e) {
		    log.error("errore durante la lettura di oggetto protocollo e oggetto protocollo mail", e);
		    command.setOggettoProtocolloMail(null);
		    command.setCorpoProtocolloMail(null);
		}
	    } else if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_ARRIVO)) {
		command.getDestinatario().setAmministrazioni(amm1);
		List<ProtocolloSoggettoCommand> mittenti = bindSoggetti(command, sessDetails);
		command.setMittentis(mittenti);
	    }
	    // Gestione destinatari multipli. I destinatari multimpli sono gestiti di deafult da un protocollo in partenza.
	    // Per i protocolli INTERNO ed ARRRIVO la gestione dei destinatari multipli verrà attivata solo se attivo il parametro 
	    // PROTOCOLLO_ATTIVO.IS_SMISTAMENTO_MULTIPLO==1
	    gestioneDestinatariMultipli(command, sessDetails);
	}
	command.setMostraSezioneConfigurazione(isMostraConfigurazioneInvioDocComeLink);
	return "redirect:viewCreate.htm";
    }

    @RequestMapping
    public String generaPraticaXml(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, HttpServletRequest request) {

	try {
	    Integer codiceMovimento = command.getMovimento().getId().getCodice();
	    log.debug("generaPraticaXml# Genero pratica xml per l'istanza {} e movimento {}",
		    new Object[] { command.getEntity().getNumeroistanza(), command.getMovimento().getMovimento() });
	    movimentiallegatiService.insertCreaAllegatoXmlDomandaSuapRegistroImprese(codiceMovimento);
	} catch (RuntimeException e) {
	    FlashMessages.getWarnings().add(e.getMessage());
	    return "redirect:viewCreate.htm";
	}
	return "redirect:viewCreate.htm";
    }

    /**
     * </pre>
     * Il metodo prepopola nell'oggetto ProtocolloCommand passato la proprietà destinataris (questo permette
     * sull'intefaccia) di poter scegliere più destinatari. Destinatari multimpli ammessi per a. Flusso PARTENZA b.
     * Flusso ARRIVO ed INTERNO e PROTOCOLLO_ATTIVO.IS_SMISTAMENTO_MULTIPLO==1.
     * 
     * Se PROTOCOLLO_ATTIVO.IS_SMISTAMENTO_MULTIPLO==1, sicuramente dovrò prepolare il la proprietà
     * ProtocolloCommand.destinataris Se PROTOCOLLO_ATTIVO.IS_SMISTAMENTO_MULTIPLO==0, popoloa proprietà
     * ProtocolloCommand.destinataris solo se Flusso == Partenza
     * </pre>
     */
    private void gestioneDestinatariMultipli(ProtocollazioneCommand command, SessionDetails sessDetails) {

	int smistamento = protocollazioneService.findSmistamentoMultiplo(command.getComune().getCodicecomune(),
		command.getProtSoftware().getCodice());
	if (smistamento > 0) {
	    List<ProtocolloSoggettoCommand> destinatari = bindSoggetti(command, sessDetails);
	    command.setDestinataris(destinatari);
	} else {
	    if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_PARTENZA)) {
		List<ProtocolloSoggettoCommand> destinatari = bindSoggetti(command, sessDetails);
		command.setDestinataris(destinatari);
	    }
	}
    }

    private Amministrazioni bindAmministrazioneFromMovimento(Movimenti movimento) {

	if (movimento != null && movimento.getAmministrazioni() != null && movimento.getAmministrazioni().getId() != null
		&& movimento.getAmministrazioni().getId().getCodice() != null) {
	    Amministrazioni amm = amministrazioniService.bindDomainObject(movimento.getAmministrazioni(), PkId.class, "id.codice");
	    return amministrazioniDTO(amm);
	}
	return null;
    }

    private Amministrazioni amministrazioniDTO(Amministrazioni ammSrc) {

	if (ammSrc == null) {
	    return null;
	}
	Amministrazioni ammDTO = new Amministrazioni();
	ammDTO.setId(new PkId());
	ammDTO.getId().setIdcomune(ammSrc.getId().getIdcomune());
	ammDTO.getId().setCodice(ammSrc.getId().getCodice());
	ammDTO.setAmministrazione(ammSrc.getAmministrazione());
	return ammDTO;
    }

    @RequestMapping
    public String assegnaSoggetto(@RequestParam("tipoSoggetto") String tipoSoggetto, Model model,
	    @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, HttpServletRequest request) {

	command.setDisplayMode(ProtocollazioneCommand.NEW);
	if (StringUtils.isNotBlank(command.getFlusso())) {
	    List<ProtocolloSoggettoCommand> soggetti = null;
	    if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_INTERNO)) {
		// ..non fa niente
	    } else if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_PARTENZA)) {
		// assegna sui destinatari
		soggetti = assegnaSoggetti(command, command.getDestinataris(), tipoSoggetto);
		command.setDestinataris(soggetti);
	    } else if (command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_ARRIVO)) {
		// assegna sui mittenti
		soggetti = assegnaSoggetti(command, command.getMittentis(), tipoSoggetto);
		command.setMittentis(soggetti);
	    }
	}
	return "redirect:viewCreate.htm";
    }

    private List<ProtocolloSoggettoCommand> assegnaSoggetti(ProtocollazioneCommand command, List<ProtocolloSoggettoCommand> soggetti,
	    String tipoSoggetto) {

	Integer codiceIstanza = null;
	Integer codiceMovimento = null;
	if (command.getEntity() != null && command.getEntity().getId() != null && command.getEntity().getId().getCodice() != null) {
	    codiceIstanza = command.getEntity().getId().getCodice();
	}
	if (command.getMovimento() != null && command.getMovimento().getId() != null && command.getMovimento().getId().getCodice() != null) {
	    codiceMovimento = command.getMovimento().getId().getCodice();
	}
	Verticalizzazioniparametri vpAbilitaIndirizziMail = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_ABILITA_INDIRIZZI_EMAIL,
		command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	boolean abilitaIndirizziMail = false;
	if (vpAbilitaIndirizziMail != null && StringUtils.defaultIfEmpty(vpAbilitaIndirizziMail.getValore(), "0").equalsIgnoreCase("1")) {
	    abilitaIndirizziMail = true;
	}
	if (!command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_PARTENZA)) {
	    abilitaIndirizziMail = false;
	}
	Istanze istanza = istanzeService.findById(command.getEntity().getId());
	Map<Integer, Boolean> mapAnagrafe = new HashMap<Integer, Boolean>();
	for (ProtocolloSoggettoCommand psc : soggetti) {
	    if (EntityUtils.getNestedProperty(psc.getAnagrafe(), "id.codice") != null) {
		mapAnagrafe.put(psc.getAnagrafe().getId().getCodice(), true);
	    }
	}
	if (tipoSoggetto.equalsIgnoreCase("S")) {
	    // soggetti collegati
	    Set<Istanzerichiedenti> richiedentis = istanza.getIstanzerichiedentis();
	    for (Istanzerichiedenti istanzerichiedenti : richiedentis) {
		Integer codiceRichiedente = istanzerichiedenti.getRichiedente().getId().getCodice();
		if (!mapAnagrafe.containsKey(codiceRichiedente)) {
		    ProtocolloSoggettoCommand soggetto = ProtocolloSoggettoCommand.fromGeneric();
		    Anagrafe anagrafe = anagrafeService.bindDomainObject(istanzerichiedenti.getRichiedente(), PkId.class, "id.codice");
		    soggetto.setAnagrafe(anagrafe);
		    if (abilitaIndirizziMail) {
			String email = istanzeService.findEmailSoggettoPratica(anagrafe.getId().getCodice(), codiceIstanza, codiceMovimento);
			soggetto.setEmail(email);
		    }
		    setMezziEmodInvioDefault(soggetto, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
		    soggetti.add(soggetto);
		}
	    }
	} else if (tipoSoggetto.equalsIgnoreCase("T")) {
	    // tecnico
	    Anagrafe tecnico = istanza.getProfessionista();
	    if (tecnico != null) {
		Integer codiceTecnico = tecnico.getId().getCodice();
		if (codiceTecnico != null && !mapAnagrafe.containsKey(codiceTecnico)) {
		    ProtocolloSoggettoCommand soggetto = ProtocolloSoggettoCommand.fromGeneric();
		    Anagrafe anagrafe = anagrafeService.bindDomainObject(tecnico, PkId.class, "id.codice");
		    if (abilitaIndirizziMail) {
			String email = istanzeService.findEmailSoggettoPratica(anagrafe.getId().getCodice(), codiceIstanza, codiceMovimento);
			soggetto.setEmail(email);
		    }
		    setMezziEmodInvioDefault(soggetto, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
		    soggetto.setAnagrafe(anagrafe);
		    soggetti.add(soggetto);
		}
	    }
	} else if (tipoSoggetto.equalsIgnoreCase("A")) {
	    // azienda
	    Anagrafe azienda = istanza.getTitolarelegale();
	    if (azienda != null) {
		Integer codiceAzienda = azienda.getId().getCodice();
		if (codiceAzienda != null && !mapAnagrafe.containsKey(codiceAzienda)) {
		    ProtocolloSoggettoCommand soggetto = ProtocolloSoggettoCommand.fromGeneric();
		    Anagrafe anagrafe = anagrafeService.bindDomainObject(azienda, PkId.class, "id.codice");
		    if (abilitaIndirizziMail) {
			String email = istanzeService.findEmailSoggettoPratica(anagrafe.getId().getCodice(), codiceIstanza, codiceMovimento);
			soggetto.setEmail(email);
		    }
		    setMezziEmodInvioDefault(soggetto, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
		    soggetto.setAnagrafe(anagrafe);
		    soggetti.add(soggetto);
		}
	    }
	} else if (tipoSoggetto.equalsIgnoreCase("R")) {
	    // richiedente
	    Anagrafe richiedente = istanza.getRichiedente();
	    if (richiedente != null) {
		Integer codiceRichiedente = richiedente.getId().getCodice();
		if (codiceRichiedente != null && !mapAnagrafe.containsKey(codiceRichiedente)) {
		    //ProtocolloSoggettoCommand soggetto = ProtocolloSoggettoCommand.fromRichiedente(, richiedente, tipoSoggetto, null)
		    ProtocolloSoggettoCommand soggetto = ProtocolloSoggettoCommand.fromGeneric();
		    Anagrafe anagrafe = anagrafeService.bindDomainObject(richiedente, PkId.class, "id.codice");
		    if (abilitaIndirizziMail) {
			String email = istanzeService.findEmailSoggettoPratica(anagrafe.getId().getCodice(), codiceIstanza, codiceMovimento);
			soggetto.setEmail(email);
		    }
		    setMezziEmodInvioDefault(soggetto, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
		    soggetto.setAnagrafe(anagrafe);
		    soggetti.add(soggetto);
		}
	    }
	} else if (tipoSoggetto.equalsIgnoreCase("AMM")) {
	    // amministrazione
	    Map<Integer, Boolean> mapAmministrazioni = new HashMap<Integer, Boolean>();
	    for (ProtocolloSoggettoCommand psc : soggetti) {
		if (EntityUtils.getNestedProperty(psc.getAmministrazioni(), "id.codice") != null) {
		    mapAmministrazioni.put(psc.getAmministrazioni().getId().getCodice(), true);
		}
	    }
	    Set<Istanzeprocedimenti> istanzeprocedimentis = istanza.getIstanzeprocedimentis();
	    for (Istanzeprocedimenti ip : istanzeprocedimentis) {
		Amministrazioni amm = ip.getInventarioprocedimenti().getAmministrazioni();
		if (amm != null) {
		    Integer codiceAmministrazione = amm.getId().getCodice();
		    if (codiceAmministrazione != null && !mapAmministrazioni.containsKey(codiceAmministrazione)) {
			ProtocolloSoggettoCommand soggetto = ProtocolloSoggettoCommand.fromGeneric();
			amm = amministrazioniService.bindDomainObject(amm, PkId.class, "id.codice");
			setMezziEmodInvioDefault(soggetto, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
			if (abilitaIndirizziMail) {
			    soggetto.setEmail(StringUtils.defaultIfEmpty(amm.getPec(), amm.getEmail()));
			}
			soggetto.setAmministrazioni(amm);
			soggetti.add(soggetto);
		    }
		}
	    }
	    if (command.getMovimento() != null && command.getMovimento().getId() != null && command.getMovimento().getId().getCodice() != null) {
		Movimenti m = movimentiService.findById(new PkId(command.getMovimento().getId().getCodice()));
		if (m != null) {
		    Amministrazioni amm = m.getAmministrazioni();
		    if (amm != null) {
			Integer codiceAmministrazione = amm.getId().getCodice();
			if (codiceAmministrazione != null && !mapAmministrazioni.containsKey(codiceAmministrazione)) {
			    ProtocolloSoggettoCommand soggetto = ProtocolloSoggettoCommand.fromGeneric();
			    amm = amministrazioniService.bindDomainObject(amm, PkId.class, "id.codice");
			    setMezziEmodInvioDefault(soggetto, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
			    if (abilitaIndirizziMail) {
				soggetto.setEmail(StringUtils.defaultIfEmpty(amm.getPec(), amm.getEmail()));
			    }
			    soggetto.setAmministrazioni(amm);
			    soggetti.add(soggetto);
			}
		    }
		}
	    }
	}
	return soggetti;
    }

    @RequestMapping
    public String viewCreate(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, HttpServletRequest request) {

	SessionDetails sessionDetails = getSessionDetails(request);
	DocumentiHelper documentiHelper = popolaDocumenti(command);
	command.setDocumentiHelper(documentiHelper);
	prepareView(command, request, model, sessionDetails, true);
	return "protocollazione/form";
    }

    @RequestMapping
    public String protocolla(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	if (request.getParameter("magic") != null) {
	    String m = StringUtils.defaultString(request.getParameter("magic"));
	    Set<String> s = mappaTokenUsati.get(ORMHelper.getIdcomuneAlias());
	    if (s == null) {
		s = new HashSet<String>();
	    }
	    if (s.contains(m)) {
		log.error("Attenzione non è stato possibile effettuare la protocollazione (ERRORE= Doppia protocollazione da maschera).");
		throw new SecurityException(
			"Attenzione non è stato possibile effettuare la protocollazione (ERRORE= Doppia protocollazione da maschera).");
	    }
	    s.add(m);
	    mappaTokenUsati.put(ORMHelper.getIdcomuneAlias(), s);
	}
	SessionDetails sessionDetails = getSessionDetails(request);
	debugCommand(command, request);
	try {
	    if (command.isRegistrazioneParticolareDocEr()) {
		String provenienza = command.getProvenienza();
		if (provenienza.equals(ProtocollazioneCommand.PROVENIENZA_PEC)) {
		    throw new RuntimeException("La funzione non è invocabile se provenienza = PEC");
		}
		DatiProtocolloResponseType dati = protocollazioneService.registrazioneDocer(command);
		command.setDatiProtocollo(dati);
	    } else {
		DatiProtocolloResponseType dati = protocollazioneService.protocolla(ProtocolloSourceEnum.PROT_IST_MOV_AUT_BO, command,
			command.getProtSoftware().getCodice(), command.getComune().getCodicecomune());
		command.setDatiProtocollo(dati);
	    }
	    return "redirect:protocollazioneResult.htm?status_msg=01";
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, command, e);
	    fixRenderEntityProperty(command);
	    prepareView(command, request, model, sessionDetails, true);
	    return "protocollazione/form";
	}
    }

    private void debugCommand(ProtocollazioneCommand command, HttpServletRequest request) {

	if (log.isDebugEnabled() && command != null) {
	    String debugInfo = "\n---------------------------------------------------\n[SessionId=" + request.getSession().getId();
	    debugInfo += "\nProvenienza=" + command.getProvenienza();
	    if (StringUtils.defaultString(command.getProvenienza()).equals(ProtocollazioneCommand.PROVENIENZA_PEC)) {
		debugInfo += "\npec=" + command.getPecCommand().getPec().getId();
	    } else if (StringUtils.defaultString(command.getProvenienza()).equals(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
		debugInfo += "\nistanza=" + command.getEntity().getId().getCodice();
	    } else if (StringUtils.defaultString(command.getProvenienza()).equals(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
		debugInfo += "\nmovimento=" + command.getMovimento().getId().getCodice();
	    }
	    debugInfo += "]\n---------------------------------------------------\n" + command.getMovimento().getId().getCodice();
	    log.debug(debugInfo);
	}
    }

    @RequestMapping
    public String protocollazioneResult(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command,
	    HttpServletRequest request) {

	SessionDetails sessionDetails = getSessionDetails(request);
	prepareView(command, request, model, sessionDetails, true);
	if (command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_PEC)) {
	    //Lion caricamento lista dei software per popolare la select del pannello scelta software
	    List<Software> softwareList = new ArrayList<Software>();
	    if (ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
		List<Responsabilisoftware> responsabilisoftwares = responsabilisoftwareService.findBySoftware(getCurrentlyAuthenticatedUserDetails(),
			new Software());
		for (Responsabilisoftware responsabilisoftware : responsabilisoftwares) {
		    if (BooleanUtils.isTrue(responsabilisoftware.getSoftware().getModuloopzionale())) {
			softwareList.add(responsabilisoftware.getSoftware());
		    }
		}
	    } else {
		Software software = softwareService.findById(ORMHelper.getSoftware());
		softwareList.add(software);
	    }
	    model.addAttribute("softwareListCreaPraticaOrMovimento", softwareList);
	}
	//END Lion
	return "protocollazione/result";
    }

    @RequestMapping
    public String createMail(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	// ATTENZIONE!!! QUANDO SI MODIFICA QUESTO METODO LA STESSA MODIFICA VA PORTATA IN 
	// 		 MOVIMENTIMAILCONTROLLER.CREATEMAIL
	MovimentimailCommand movimentimail = new MovimentimailCommand();
	Istanze istanza = command.getMovimento().getIstanza();
	Movimenti movimento = command.getMovimento();
	movimento = movimentiService.findById(new PkId(movimento.getId().getCodice()));
	movimentimail.setFlgInvialinkallmail(movimento.getTipomovimento().getFlgInvialinkallmail());
	movimentimail.setLetteraTipoAllegati(movimento.getTipomovimento().getLetteraTipoAllegati());
	MailConfig mailConfigDefault = mailConfigService.findPrepopolaInvioEmailBySoftwareAndCodiceComune(ORMHelper.getSoftware(),
		istanza.getComune().getCodicecomune(), false);
	movimentimail.setMailConfig(mailConfigDefault);
	List<MailConfig> listMailConfig = mailConfigService.findForInvioEmailBySoftwareAndCodiceComuneAbilitati(ORMHelper.getSoftware(),
		istanza.getComune().getCodicecomune(), false);
	Movimentimail entity = new Movimentimail();
	PkId pkId = new PkId();
	entity.setId(pkId);
	entity.setMovimento(movimento);
	if (EntityUtils.getNestedProperty(mailConfigDefault, "id.codice") != null) {
	    entity.setMittente(mailConfigDefault.getSenderaddress());
	}
	entity.setMailConfig(mailConfigDefault);
	Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(movimento.getTipomovimento().getId().getTipomovimento()));
	boolean isDpr160 = false;
	Mailtipo mailtipo = null;
	// seleziona la mail tipo configurata
	if (EntityUtils.getNestedProperty(tipimovimento.getMailtipoByFkTipimovricTelMailtipo(), "id.codice") != null) {
	    isDpr160 = true;
	    mailtipo = tipimovimento.getMailtipoByFkTipimovricTelMailtipo();
	} else if (EntityUtils.getNestedProperty(tipimovimento.getMailtipoByFkTipimovcomTelMailtipo(), "id.codice") != null) {
	    isDpr160 = true;
	    mailtipo = tipimovimento.getMailtipoByFkTipimovcomTelMailtipo();
	}
	// Prepopolo gli eventuali destinatari della mail
	setDestinatari(entity, command, istanza, isDpr160);
	List<Mailtipo> mailtipi = new ArrayList<Mailtipo>();
	if (isDpr160) {
	    mailtipi.add(mailtipo);
	    // prepopola il form della mail con oggetto e corpo della mailtipo
	    model.addAttribute("setmailtipo", true);
	} else {
	    mailtipi = mailtipoService.findAllBySoftwareAndTT(ContestiMailTipoEnum.MAIL);
	}
	model.addAttribute("mailtipi", mailtipi);
	movimentimail.setEntity(entity);
	model.addAttribute("movimentimail", movimentimail);
	model.addAttribute("istanza", istanza);
	model.addAttribute("listMailConfig", listMailConfig);
	// per evitare LazyLoading error in caso di movimenti ed istanza senza allegati e documenti
	command.setMovimento(movimentiService.findById(new PkId(movimento.getId().getCodice())));
	command.setEntity(istanzeService.findById(new PkId(movimento.getIstanza().getId().getCodice())));
	// Recupera tutti gli oggetti presneti in DocumentiHelper che hanno il flag transientInvio posto a true
	DocumentiHelper documentiHelper = documentiHelperService.findDocumentiInvioTrue(command.getDocumentiHelper());
	movimentimail.setDocumentiHelper(documentiHelper);
	fixRenderMovimentiMailCommandProperty(movimentimail);
	model.addAttribute("movimentimail", movimentimail);
	setPageAttributes(model, istanza.getComune().getCodicecomune());
	/////////////////// verifica se la verticalizzazione è VERTICALIZZAZIONE_ALLEGATI_PEC /////////////////////////////////
	/////////////////// serve per visualizzare o no i campi necessari per l'utilizzo della
	boolean isAttiva = isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC, request);
	if (!isAttiva) {
	    movimentimail.setFlgInvialinkallmail(isAttiva);
	}
	// ATTENZIONE!!! QUANDO SI MODIFICA QUESTO METODO LA STESSA MODIFICA VA PORTATA IN 
	// 		 MOVIMENTIMAILCONTROLLER.CREATEMAIL
	return "movimentimail/inviomail";
    }

    private void fixRenderMovimentiMailCommandProperty(MovimentimailCommand movimentimailCommand) {

	if (movimentimailCommand.getLetteraTipoAllegati() == null) {
	    movimentimailCommand.setLetteraTipoAllegati(new Letteretipo());
	}
    }

    private void setDestinatari(Movimentimail movimentimail, ProtocollazioneCommand protocollazioneCommand, Istanze istanza, boolean isDpr160) {

	StringBuilder destinatari = new StringBuilder("");
	StringBuilder destinatariCc = new StringBuilder("");
	// Gestione del destinario in caso di movimento
	if (isDpr160) {
	    String pecDestinatari = "";
	    if (istanza.getTitolarelegale() != null && StringUtils.isNotBlank(istanza.getTitolarelegale().getPec())) {
		String pecImpresa = istanza.getTitolarelegale().getPec();
		pecDestinatari += pecImpresa;
	    }
	    if (istanza.getRichiedente() != null && StringUtils.isNotBlank(istanza.getRichiedente().getPec())) {
		String pecRichiedente = istanza.getRichiedente().getPec();
		pecRichiedente += ";";
		pecDestinatari += pecRichiedente;
	    }
	    destinatari.append(pecDestinatari);
	}
	// Popolo i destinatario proveniente dalla maschera di protocollazione
	log.debug("setDestinatari# Ricerco la mail del destinatario");
	if (protocollazioneCommand.getDestinatario() != null) {
	    log.debug("setDestinatari# Ricerco la mail del destinatario......");
	    ProtocolloSoggettoCommand soggettoDestinatario = protocollazioneCommand.getDestinatario();
	    String emailDest = getEmailFromProtocolloSoggettoCommand(soggettoDestinatario);
	    if (StringUtils.isNotBlank(emailDest)) {
		if (StringUtils.isNotBlank(soggettoDestinatario.getModInvio().getCodice())
			&& soggettoDestinatario.getModInvio().getCodice().equals("CC")) {
		    destinatariCc = (StringUtils.isNotBlank(destinatariCc.toString()) ? destinatariCc.append(";").append(emailDest)
			    : destinatariCc.append(emailDest));
		} else {
		    destinatari = (StringUtils.isNotBlank(destinatari.toString()) ? destinatari.append(";").append(emailDest)
			    : destinatari.append(emailDest));
		}
	    }
	}
	// Popolo altri destinatari provenienti dalla maschera di protocollazione
	List<ProtocolloSoggettoCommand> destinataris = protocollazioneCommand.getDestinataris();
	log.debug("setDestinatari#Ciclo la lista dei destinatari......");
	for (ProtocolloSoggettoCommand protocolloSoggettoCommand : destinataris) {
	    log.debug("setDestinatari#Recupero la mail del destinatario......");
	    String email = getEmailFromProtocolloSoggettoCommand(protocolloSoggettoCommand);
	    if (StringUtils.isNotBlank(email)) {
		if (StringUtils.isNotBlank(protocolloSoggettoCommand.getModInvio().getCodice())
			&& protocolloSoggettoCommand.getModInvio().getId().getCodice().equals("CC")) {
		    log.debug("setDestinatari#Destinatario {} è in 'CC'", email);
		    destinatariCc = (StringUtils.isNotBlank(destinatariCc.toString()) ? destinatariCc.append(";").append(email)
			    : destinatariCc.append(email));
		} else {
		    log.debug("setDestinatari#Destinatario {} è in 'A'", email);
		    destinatari = (StringUtils.isNotBlank(destinatari.toString()) ? destinatari.append(";").append(email)
			    : destinatari.append(email));
		}
	    }
	}
	movimentimail.setDestinatario(destinatari.toString());
	movimentimail.setDestinatariocc(destinatariCc.toString());
    }

    private String getEmailFromProtocolloSoggettoCommand(ProtocolloSoggettoCommand destinatario) {

	String dest = "";
	if (EntityUtils.getNestedProperty(destinatario.getAmministrazioni(), "id.codice") != null) {
	    Amministrazioni amministrazioni = destinatario.getAmministrazioni();
	    log.debug("getEmailFromProtocolloSoggettoCommand# Cerco email dell'amministrazione: {}({})",
		    new Object[] { amministrazioni.getAmministrazione(), amministrazioni.getId().getCodice() });
	    // Controllo se c'è la PEC
	    if (StringUtils.isNotBlank(amministrazioni.getPec())) {
		log.debug("getEmailFromProtocolloSoggettoCommand# Trovata una PEC configurata");
		return amministrazioni.getPec();
	    }
	    // Controllo se c'è la mail
	    if (StringUtils.isNotBlank(amministrazioni.getEmail())) {
		log.debug("getEmailFromProtocolloSoggettoCommand# Trovata una email configurata");
		return amministrazioni.getEmail();
	    }
	} else {
	    Anagrafe anagrafe = destinatario.getAnagrafe();
	    log.debug("getEmailFromProtocolloSoggettoCommand# Cerco email dell'anagrafica: {}({})",
		    new Object[] { anagrafe.getDescrizioneRichiedente(), anagrafe.getId().getCodice() });
	    // Controllo se c'è la PEC
	    if (StringUtils.isNotBlank(anagrafe.getPec())) {
		log.debug("getEmailFromProtocolloSoggettoCommand# Trovata una PEC configurata");
		return anagrafe.getPec();
	    }
	    // Controllo se c'è la mail
	    if (StringUtils.isNotBlank(anagrafe.getEmail())) {
		log.debug("getEmailFromProtocolloSoggettoCommand# Trovata una email configurata");
		return anagrafe.getEmail();
	    }
	}
	log.warn("getEmailFromProtocolloSoggettoCommand# Per il soggetto (Amministrazione o Anagrafica ) mail non configurata");
	return dest;
    }

    @RequestMapping
    public String leggiProtocollo(@RequestParam(value = "codiceIstanza", required = false) Integer codiceIstanza,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento,
	    @RequestParam(value = "codicePEC", required = false) String codicePec, Model model, HttpServletRequest request) {

	Istanze istanza = null;
	ProtocollazioneCommand command = new ProtocollazioneCommand();
	if (codiceIstanza != null) {
	    istanza = istanzeService.findById(new PkId(codiceIstanza));
	    command.setEntity(istanza);
	    command.setProvenienza(ProtocollazioneCommand.PROVENIENZA_ISTANZA);
	    command.setComune(istanza.getComune());
	    command.setProtSoftware(istanza.getSoftware());
	}
	if (codiceMovimento != null) {
	    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	    command.setMovimento(movimento);
	    command.setProvenienza(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI);
	    command.setComune(movimento.getIstanza().getComune());
	    command.setProtSoftware(movimento.getIstanza().getSoftware());
	}
	if (StringUtils.isNotBlank(codicePec)) {
	    //se la chiamata viene da PEC INBOX tutti i dati della PEC sono già nel model ed evito di interrogare il DB
	    PecInbox pec = null;
	    Map<String, Object> modelMap = model.asMap();
	    if (modelMap.containsKey("protocolloPecCommand")) {
		PECCommand pecCommand = (PECCommand) modelMap.get("protocolloPecCommand");
		pec = pecCommand.getPec();
	    } else {
		/*
		 * nel caso in cui il PecCommand non sia stato già caricato da PECInboxController 
		 * ne viene predisposto uno ma senza l'elenco degli allegati
		 */
		pec = pecInboxService.findById(new PecInboxId(codicePec));
		PECCommand pecCommand = new PECCommand();
		pecCommand.setPec(pec);
		model.addAttribute("protocolloPecCommand", pecCommand);
		if (pec.getComuniProt() != null) {
		    command.setComune(pec.getComuniProt());
		}
		if (pec.getSoftwareProt() != null) {
		    command.setProtSoftware(pec.getSoftwareProt());
		}
	    }
	    command.setPec(pec);
	    command.setProvenienza(ProtocollazioneCommand.PROVENIENZA_PEC);
	}
	DatiProtocolloLettoResponseType datiProtocolloLetto = null;
	SessionDetails sessionDetails = getSessionDetails(request);
	Map<Integer, String> map = new HashMap<Integer, String>();
	if (command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
	    if (log.isDebugEnabled()) {
		log.debug("leggiProtocollo# Scarico file dal movimento con  codice {}", codiceMovimento);
	    }
	    List<MovimentiallegatiDTO> listAllegatiMov = movimentiallegatiService.findMovimentiallegatiDTOByMovimenti(codiceMovimento);
	    datiProtocolloLetto = protocollazioneService.leggiProtocollo(sessionDetails.getToken(), command.getMovimento());
	    // Ritorna una mappa contente i file già salvati in movimenti  allegati che appartengono al protocollo
	    if (datiProtocolloLetto.getAllegati() != null && !datiProtocolloLetto.getAllegati().getAllegatoResponseType().isEmpty()) {
		map = getAllegatiPresentiInMovimentiAllegati(listAllegatiMov, datiProtocolloLetto.getAllegati().getAllegatoResponseType());
	    }
	} else if (command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
	    if (log.isDebugEnabled()) {
		log.debug("leggiProtocollo# Scarico file dall' istanza con codice {}", codiceIstanza);
	    }
	    List<DocumentiistanzaDTO> list = documentiistanzaService.findDocumentiistanzaDTOByIstanza(istanza.getId().getCodice(), false);
	    datiProtocolloLetto = protocollazioneService.leggiProtocollo(sessionDetails.getToken(), command.getEntity());
	    // Ritorna una mappa contente i file già salvati in documenti istanza che appartengono al protocollo
	    if (datiProtocolloLetto.getAllegati() != null && !datiProtocolloLetto.getAllegati().getAllegatoResponseType().isEmpty()) {
		map = getAllegatiPresentiInDocumentiIstanza(list, datiProtocolloLetto.getAllegati().getAllegatoResponseType());
	    }
	} else if (command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_PEC)) {
	    if (log.isDebugEnabled()) {
		log.debug("leggiProtocollo# Scarico file dalla PEC con codice {}", codicePec);
	    }
	    datiProtocolloLetto = protocollazioneService.leggiProtocollo(sessionDetails.getToken(), command.getPec(),
		    command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	    // Ritorna una mappa contente il file contenente l'intero messaggio della PEC che è stato associato al protocollo
	    if (datiProtocolloLetto.getAllegati() != null && !datiProtocolloLetto.getAllegati().getAllegatoResponseType().isEmpty()) {
		map = getAllegatiPresentiInPEC(command.getPec(), datiProtocolloLetto.getAllegati().getAllegatoResponseType());
	    }
	}
	model.addAttribute("protocolloCommand", command);
	model.addAttribute("datiProtocolloLetto", datiProtocolloLetto);
	model.addAttribute("mappaDocScaricati", map);
	prepareViewLeggiProtocollo(command, request, model, sessionDetails, false);
	return "protocollazione/leggiProtocollo";
    }

    @RequestMapping
    public String inviaDocumenti(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand protocolloCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Integer codiceIstanza = null;
	Integer codiceMovimento = null;
	String parametriRequest = "";
	if (EntityUtils.getNestedProperty(protocolloCommand.getMovimento(), "id.codice") != null) {
	    codiceIstanza = protocolloCommand.getMovimento().getIstanza().getId().getCodice();
	    codiceMovimento = protocolloCommand.getMovimento().getId().getCodice();
	    parametriRequest = "codiceIstanza=" + codiceIstanza + "&codiceMovimento=" + codiceMovimento;
	} else {
	    codiceIstanza = protocolloCommand.getEntity().getId().getCodice();
	    parametriRequest = "codiceIstanza=" + codiceIstanza;
	}
	try {
	    protocollazioneService.updateInviaDocumenti(protocolloCommand);
	    return "redirect:leggiProtocollo.htm?" + parametriRequest + "&status_msg=01";
	} catch (Exception e) {
	    String errorMessage = e.getMessage();
	    FlashMessages.getWarnings().add(errorMessage);
	    return "redirect:leggiProtocollo.htm?" + parametriRequest + "&status_msg=03";
	}
    }

    @RequestMapping
    public void ajaxVisualizzaAllegato(@RequestParam("idBase") String idBase, @RequestParam("codiceComune") String codiceComune,
	    @RequestParam("pSoftware") String pSoftware, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	AllegatoResponseType allegatoDaVisualizzare = protocollazioneService.leggiAllegato(ORMHelper.getToken(), idBase, pSoftware, codiceComune);
	if (allegatoDaVisualizzare != null) {
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + allegatoDaVisualizzare.getSerial() + "\"");
	    response.setHeader("Content-transfer-encoding", "binary");
	    response.setContentType(allegatoDaVisualizzare.getContentType());
	    ServletOutputStream out = response.getOutputStream();
	    DataHandler dh = allegatoDaVisualizzare.getImage();
	    if (dh != null) {
		dh.writeTo(out);
	    }
	    out.flush();
	}
    }

    @RequestMapping
    public void ajaxVisualizzaAllegatoUORuolo(@RequestParam("idBase") String idBase, @RequestParam("uo") String uo,
	    @RequestParam("ruolo") String ruolo, @RequestParam("codiceComune") String codiceComune, @RequestParam("pSoftware") String pSoftware,
	    Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	AllegatoResponseType allegatoDaVisualizzare = protocollazioneService.leggiAllegatoUORuolo(ORMHelper.getToken(), idBase, uo, ruolo, pSoftware,
		codiceComune);
	if (allegatoDaVisualizzare != null) {
	    response.setHeader("Pragma", "public");
	    response.setHeader("Cache-Control", "max-age=0");
	    response.setHeader("Content-Disposition", "attachment; filename=\"" + allegatoDaVisualizzare.getSerial() + "\"");
	    response.setHeader("Content-transfer-encoding", "binary");
	    response.setContentType(allegatoDaVisualizzare.getContentType());
	    ServletOutputStream out = response.getOutputStream();
	    DataHandler dh = allegatoDaVisualizzare.getImage();
	    if (dh != null) {
		dh.writeTo(out);
	    }
	    out.flush();
	}
    }

    @RequestMapping
    public void ajaxSalvaAllegatoInDocumentiIstanza(@RequestParam("idBase") String idBase, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam("descrizioneFile") String descrizioneFile,
	    @ModelAttribute("datiProtocolloLetto") DatiProtocolloLettoResponseType datiProtocolloLetto, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	try {
	    documentiistanzaService.salvaDocumentoProtocollo(idBase, codiceIstanza, descrizioneFile, true);
	    StringBuilder buffer = new StringBuilder("Salvataggio eseguito");
	    response.setContentType("text/plain");
	    response.getWriter().write(buffer.toString());
	} catch (Exception e) {
	    StringBuilder buffer = new StringBuilder("<label style=\"color: red;\">Impossibile salvare il documento:</labe>" + e.getMessage());
	    response.setContentType("text/plain");
	    response.getWriter().write(buffer.toString());
	}
    }

    @RequestMapping
    public void ajaxSalvaAllegatoInMovimentiAllegati(@RequestParam("idBase") String idBase, @RequestParam("codiceMovimento") Integer codiceMovimento,
	    @RequestParam("descrizioneFile") String descrizioneFile,
	    @ModelAttribute("datiProtocolloLetto") DatiProtocolloLettoResponseType datiProtocolloLetto, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	try {
	    movimentiallegatiService.salvaDocumentoProtocollo(idBase, codiceMovimento, descrizioneFile, true);
	    StringBuilder buffer = new StringBuilder("Salvataggio eseguito");
	    response.setContentType("text/plain");
	    response.getWriter().write(buffer.toString());
	} catch (Exception e) {
	    StringBuilder buffer = new StringBuilder("<label style=\"color: red;\">Impossibile salvare l'allegato:</labe>" + e.getMessage());
	    response.setContentType("text/plain");
	    response.getWriter().write(buffer.toString());
	}
    }

    @RequestMapping
    public String salvaTuttiAllegatiDaProtocollo(@RequestParam(required = false, value = "codiceMovimento") Integer codiceMovimento,
	    @RequestParam("codiceIstanza") Integer codiceIstanza, Model model,
	    @ModelAttribute("datiProtocolloLetto") DatiProtocolloLettoResponseType datiProtocolloLetto, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	ProtocollazioneCommand command = new ProtocollazioneCommand();
	command.setEntity(istanza);
	datiProtocolloLetto = null;
	SessionDetails sessionDetails = getSessionDetails(request);
	Map<Integer, String> mappaGiaSalvati = new HashMap<Integer, String>();
	try {
	    // Verifico se stiamo scaricando i documenti del protocollo dell'istanza o di un movimento
	    if (codiceMovimento != null) {
		Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
		if (log.isDebugEnabled()) {
		    log.debug("salvaTuttiAllegatiDaProtocollo# Sto salvando gli allegati per il protocollo del movimento :{}[{}]",
			    new Object[] { movimento.getMovimento(), codiceMovimento });
		}
		command.setMovimento(movimento);
		datiProtocolloLetto = protocollazioneService.leggiProtocollo(sessionDetails.getToken(), movimento);
		List<MovimentiallegatiDTO> listAllegatiMov = movimentiallegatiService.findMovimentiallegatiDTOByMovimenti(codiceMovimento);
		mappaGiaSalvati = getAllegatiPresentiInMovimentiAllegati(listAllegatiMov,
			datiProtocolloLetto.getAllegati().getAllegatoResponseType());
		movimentiallegatiService.salvaDocumentiProtocollo(mappaGiaSalvati, datiProtocolloLetto.getAllegati().getAllegatoResponseType(),
			codiceMovimento);
	    } else if (codiceIstanza != null) {
		if (log.isDebugEnabled()) {
		    log.debug("salvaTuttiAllegatiDaProtocollo# Sto salvando gli allegati per il protocollo dell' istanza :{}[{}]",
			    new Object[] { istanza.getNumeroistanza(), codiceIstanza });
		}
		datiProtocolloLetto = protocollazioneService.leggiProtocollo(sessionDetails.getToken(), istanza);
		List<DocumentiistanzaDTO> list = documentiistanzaService.findDocumentiistanzaDTOByIstanza(codiceIstanza, false);
		// ritorna una mappa contenete i file che già sono stati salvati su db dal protocollo
		mappaGiaSalvati = getAllegatiPresentiInDocumentiIstanza(list, datiProtocolloLetto.getAllegati().getAllegatoResponseType());
		documentiistanzaService.salvaDocumentiProtocollo(mappaGiaSalvati, datiProtocolloLetto.getAllegati().getAllegatoResponseType(),
			codiceIstanza);
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, command, e);
	    fixRenderEntityProperty(command);
	    model.addAttribute("protocolloCommand", command);
	    model.addAttribute("datiProtocolloLetto", datiProtocolloLetto);
	    model.addAttribute("mappaDocScaricati", mappaGiaSalvati);
	    prepareViewLeggiProtocollo(command, request, model, sessionDetails, false);
	    return "protocollazione/leggiProtocollo";
	}
	if (codiceMovimento != null) {
	    return "redirect:leggiProtocollo.htm?codiceIstanza=" + codiceIstanza + "&codiceMovimento=" + codiceMovimento + "&status_msg=02";
	} else {
	    return "redirect:leggiProtocollo.htm?codiceIstanza=" + codiceIstanza + "&status_msg=02";
	}
    }

    @RequestMapping
    public String ajaxCheckProtocolloAnnullato(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	DatiProtocolloAnnullatoResponseType datiProtocolloAnnullato = null;
	String fkidProtocollo = "";
	String numeroProtocollo = "";
	Date dataProtocollo = null;
	Istanze istanza = null;
	if (codiceMovimento != null) {
	    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	    if (movimento != null) {
		numeroProtocollo = movimento.getNumeroprotocollo();
		dataProtocollo = movimento.getDataprotocollo();
		fkidProtocollo = movimento.getFkidprotocollo();
	    }
	    istanza = istanzeService.findById(new PkId(movimento.getIstanza().getId().getCodice()));
	} else {
	    istanza = istanzeService.findById(new PkId(codiceIstanza));
	    if (istanza != null) {
		numeroProtocollo = istanza.getNumeroprotocollo();
		dataProtocollo = istanza.getDataprotocollo();
		fkidProtocollo = istanza.getFkidprotocollo();
	    }
	}
	if (StringUtils.isNotBlank(numeroProtocollo) && (dataProtocollo != null)) {
	    String software = istanza.getSoftware().getCodice();
	    String codiceComune = istanza.getComune().getCodicecomune();
	    try {
		datiProtocolloAnnullato = protocollazioneService.isAnnullato(ORMHelper.getToken(), fkidProtocollo, numeroProtocollo, dataProtocollo,
			software, codiceComune);
		if (datiProtocolloAnnullato.getErrore() != null) {
		    String errori = "Il servizio di protocollazione ha tornato il seguente errore: " +
				    datiProtocolloAnnullato.getErrore().getDescrizione();
		    request.setAttribute("errori", errori);
		    return "protocollazione/formProtocolloAnnullato";
		}
		if (datiProtocolloAnnullato.getAnnullato().equals(EnumAnnullatoType.NO)) {
		    return "includes/blank";
		}
		if (datiProtocolloAnnullato.getAnnullato().equals(EnumAnnullatoType.NONDEFINITO)
			&& StringUtils.isBlank(datiProtocolloAnnullato.getMotivoAnnullamento())
			&& StringUtils.isBlank(datiProtocolloAnnullato.getNoteAnnullamento())) {
		    return "includes/blank";
		}
		request.setAttribute("datiProtocolloAnnullato", datiProtocolloAnnullato);
		return "protocollazione/formProtocolloAnnullato";
	    } catch (Exception e) {
		String errori = "Non è stato possibile contattare il servizio di protocollazione a causa di=" + e.getMessage();
		request.setAttribute("errori", errori);
		return "protocollazione/formProtocolloAnnullato";
	    }
	}
	return "includes/blank";
    }

    @RequestMapping
    public void ajaxCheckFascicolato(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	DatiProtocolloFascicolatoResponseType datiProtocolloFascicolato = null;
	String risposta = "";
	String fkidProtocollo = "";
	String numeroProtocollo = "";
	Date dataProtocollo = null;
	Istanze istanza = null;
	if (codiceMovimento != null) {
	    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	    if (movimento == null) {
		risposta = "Non è stato trovato il movimento con codice: " + codiceMovimento;
	    } else {
		numeroProtocollo = movimento.getNumeroprotocollo();
		dataProtocollo = movimento.getDataprotocollo();
		fkidProtocollo = movimento.getFkidprotocollo();
	    }
	    istanza = istanzeService.findById(new PkId(movimento.getIstanza().getId().getCodice()));
	} else {
	    istanza = istanzeService.findById(new PkId(codiceIstanza));
	    if (istanza == null) {
		risposta = "Non è stato trovata l'istanza con codice: " + codiceIstanza;
	    } else {
		numeroProtocollo = istanza.getNumeroprotocollo();
		dataProtocollo = istanza.getDataprotocollo();
		fkidProtocollo = istanza.getFkidprotocollo();
	    }
	}
	if (StringUtils.isNotBlank(numeroProtocollo) && (dataProtocollo != null)) {
	    String software = istanza.getSoftware().getCodice();
	    String codiceComune = istanza.getComune().getCodicecomune();
	    try {
		datiProtocolloFascicolato = protocollazioneService.isFascicolato(ORMHelper.getToken(), fkidProtocollo, numeroProtocollo,
			dataProtocollo, software, codiceComune);
		if (datiProtocolloFascicolato.getErrore() != null) {
		    risposta = "Il servizio di protocollazione ha tornato il seguente errore: " +
			       datiProtocolloFascicolato.getErrore().getDescrizione();
		} else {
		    if (datiProtocolloFascicolato.getFascicolato().equals(EnumFascicolatoType.NO)) {
			risposta = "no";
		    } else if (datiProtocolloFascicolato.getFascicolato().equals(EnumFascicolatoType.SI)) {
			risposta = "si";
			if (codiceMovimento != null) {
			    risposta = getMessageFromBundle("label.protocollo_e_stato_fascicolato", null) + "<br />" +
				       getMessageFromBundle("label.numero_fascicolo", null) + ": " + datiProtocolloFascicolato.getNumeroFascicolo() +
				       "<br />" + getMessageFromBundle("label.data_fascicolo", null) + ": " +
				       datiProtocolloFascicolato.getDataFascicolo();
			}
		    } else {
			risposta = datiProtocolloFascicolato.getNoteFascicolo();
			if (codiceMovimento != null) {
			    risposta = "AVVERTIMENTO" + risposta;
			}
		    }
		}
	    } catch (Exception e) {
		log.error("ajaxCheckFascicolato", e);
		risposta = "Attenzione! Non è stato possibile contattare il servizio di protocollazione a causa di=" + e.getMessage();
		if (codiceMovimento != null) {
		    risposta = "AVVERTIMENTO" + risposta;
		}
	    }
	}
	ServletOutputStream out = response.getOutputStream();
	response.setContentType("text/plain");
	out.write(risposta.getBytes());
	out.flush();
    }

    @RequestMapping
    public String annullaProtocolloView(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento, Model model, HttpServletRequest request) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	SessionDetails sessionDetails = getSessionDetails(request);
	ProtocollazioneCommand command = new ProtocollazioneCommand();
	command.setDisplayMode(BaseCommand.NEW);
	if (codiceMovimento != null) {
	    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	    command.setMovimento(movimento);
	}
	command.setEntity(istanza);
	model.addAttribute("protocolloCommand", command);
	String software = istanza.getSoftware().getCodice();
	String codiceComune = istanza.getComune().getCodicecomune();
	List<CodiceDescrizioneBean> motiviAnnullamento = protocollazioneService.getMotiviAnnullamento(sessionDetails.getToken(), software,
		codiceComune);
	model.addAttribute("motiviAnnullamento", motiviAnnullamento);
	return "protocollazione/annullaProtocollo";
    }

    @RequestMapping
    public String annullaProtocollo(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	SessionDetails sessionDetails = getSessionDetails(request);
	String fkidprotocollo = "";
	String numeroProtocollo = "";
	Date dataProtocollo = null;
	if (EntityUtils.getNestedProperty(command.getMovimento(), "id.codice") != null) {
	    fkidprotocollo = command.getMovimento().getFkidprotocollo();
	    numeroProtocollo = command.getMovimento().getNumeroprotocollo();
	    dataProtocollo = command.getMovimento().getDataprotocollo();
	} else {
	    fkidprotocollo = command.getEntity().getFkidprotocollo();
	    numeroProtocollo = command.getEntity().getNumeroprotocollo();
	    dataProtocollo = command.getEntity().getDataprotocollo();
	}
	String software = command.getProtSoftware().getCodice();
	String codiceComune = command.getComune().getCodicecomune();
	try {
	    protocollazioneService.annullaProtocollo(sessionDetails.getToken(), fkidprotocollo, dataProtocollo, numeroProtocollo,
		    command.getMotivoAnnullamento(), command.getNoteAnnullamento(), software, codiceComune);
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, command, e);
	    fixRenderEntityProperty(command);
	    List<CodiceDescrizioneBean> motiviAnnullamento = protocollazioneService.getMotiviAnnullamento(sessionDetails.getToken(), software,
		    codiceComune);
	    model.addAttribute("motiviAnnullamento", motiviAnnullamento);
	    return "protocollazione/annullaProtocollo";
	}
	return "redirect:protocolloAnnullato.htm?status_msg=02";
    }

    @RequestMapping
    public String protocolloAnnullato(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, HttpServletRequest request) {

	command.setDisplayMode(BaseCommand.EDIT);
	return "protocollazione/annullaProtocollo";
    }

    @RequestMapping
    public String cambiaFascicoloView(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento, Model model, HttpServletRequest request) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	ProtocollazioneCommand command = new ProtocollazioneCommand();
	command.setDisplayMode(BaseCommand.EDIT);
	command.setProvenienza(ProtocollazioneCommand.PROVENIENZA_ISTANZA);
	command.setProtSoftware(istanza.getSoftware());
	command.setComune(istanza.getComune());
	if (codiceMovimento != null) {
	    command.setProvenienza(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI);
	    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	    command.setMovimento(movimento);
	}
	command.setEntity(istanza);
	model.addAttribute("protocolloCommand", command);
	fascicoloPagesAttribute(command, model, request, false, true);
	return "protocollazione/formFascicolo";
    }

    @RequestMapping
    public String creaFascicoloView(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento, @RequestParam("provenienza") String provenienza,
	    Model model, HttpServletRequest request) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	ProtocollazioneCommand command = new ProtocollazioneCommand();
	command.setProvenienza(provenienza);
	command.setDisplayMode(BaseCommand.NEW);
	command.setEntity(istanza);
	if (codiceMovimento != null) {
	    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	    command.setMovimento(movimento);
	}
	model.addAttribute("protocolloCommand", command);
	fascicoloPagesAttribute(command, model, request, true, true);
	return "protocollazione/formFascicolo";
    }

    @RequestMapping
    public String creaFascicolo(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	model.addAttribute("protocolloCommand", command);
	fascicoloPagesAttribute(command, model, request, true, false);
	if (EntityUtils.getNestedProperty(command.getMovimento(), "id.codice") != null) {
	    protocollazioneService.fascicolaMovimentoXml(ORMHelper.getToken(), command);
	} else {
	    protocollazioneService.fascicolaIstanzaXml(ORMHelper.getToken(), command);
	}
	return "redirect:fascicoloModificato.htm?status_msg=02";
    }

    @RequestMapping
    public String cambiaFascicolo(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	model.addAttribute("protocolloCommand", command);
	fascicoloPagesAttribute(command, model, request, false, false);
	protocollazioneService.cambiaFascicoloIstanzaXml(ORMHelper.getToken(), command);
	return "redirect:fascicoloModificato.htm?status_msg=02";
    }

    @RequestMapping
    public String fascicoloModificato(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, HttpServletRequest request) {

	command.setDisplayMode(BaseCommand.EDIT);
	fascicoloPagesAttribute(command, model, request, false, true);
	return "protocollazione/formFascicolo";
    }

    private void fascicoloPagesAttribute(ProtocollazioneCommand command, Model model, HttpServletRequest request, boolean isCrea,
	    boolean isViewPage) {

	Integer codiceistanza = null;
	if (command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_ISTANZA)
		|| command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
	    if (command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
		codiceistanza = command.getEntity().getId().getCodice();
	    } else {
		Integer codiceMovimento = command.getMovimento().getId().getCodice();
		Movimenti m = movimentiService.findById(new PkId(codiceMovimento));
		codiceistanza = m.getIstanza().getId().getCodice();
	    }
	    Istanze i = istanzeService.findById(new PkId(codiceistanza));
	    command.setComune(i.getComune());
	    command.setProtSoftware(i.getSoftware());
	} else {
	    throw new RuntimeException("Funzionalità invocabile solo da ISTANZE o MOVIMENTI");
	}
	List<CodiceDescrizioneBean> listaFascicoli = null;
	if (EntityUtils.getNestedProperty(command.getMovimento(), "id.codice") != null) {
	    Movimenti movimento = movimentiService.findById(new PkId(command.getMovimento().getId().getCodice()));
	    command.setMovimento(movimento);
	} else {
	    listaFascicoli = protocollazioneService.getFascicoliPerIstanza(ORMHelper.getToken(), command.getEntity());
	}
	Date oggi = Calendar.getInstance().getTime();
	if (listaFascicoli != null && !listaFascicoli.isEmpty() && isCrea) {
	    CodiceDescrizioneBean valoreVuoto = new CodiceDescrizioneBean();
	    SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	    valoreVuoto.setCodice(sdf.format(oggi));
	    valoreVuoto.setDescrizione("");
	    listaFascicoli.add(0, valoreVuoto);
	}
	boolean isProtocolloDOCER = false;
	Verticalizzazioniparametri tipoProtocollo = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOPROTOCOLLO,
		command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	if (tipoProtocollo != null && tipoProtocollo.getValore().equals(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER)) {
	    isProtocolloDOCER = true;
	}
	Verticalizzazioniparametri vPanel = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VIS_PANEL_RICERCA_FASCICOLO,
		command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	model.addAttribute("isPanelRicercaFascicoli", Boolean.FALSE);
	if (vPanel != null) {
	    String valore = StringUtils.defaultIfEmpty(vPanel.getValore(), "0");
	    if (valore.equalsIgnoreCase("1")) {
		model.addAttribute("isPanelRicercaFascicoli", Boolean.TRUE);
	    }
	}
	Verticalizzazioniparametri vPanePreFasc = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_PANEL_RIC_FASC_PREC_CLASSIF,
		command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	model.addAttribute("isPanelPrecompilaClassifica", Boolean.FALSE);
	if (vPanePreFasc != null) {
	    String valore = StringUtils.defaultIfEmpty(vPanePreFasc.getValore(), "0");
	    if (valore.equalsIgnoreCase("1")) {
		model.addAttribute("isPanelPrecompilaClassifica", Boolean.TRUE);
	    }
	}
	model.addAttribute("isDocEr", isProtocolloDOCER);
	model.addAttribute("listaFascicoli", listaFascicoli);
	model.addAttribute("isCrea", isCrea);
	CodiceDescrizioneBean[] classifica = protocollazioneService.getListaClassifiche(command.getProtSoftware().getCodice(),
		command.getComune().getCodicecomune());
	model.addAttribute("listaClassificheFascicolis", classifica);
	boolean isModificaClassificafasc = false;
	Verticalizzazioniparametri modificaClassificaParametriProt = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MODIFICA_CLASSIFICA_PARAMETRI_PROT,
		command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	if (modificaClassificaParametriProt != null && modificaClassificaParametriProt.getValore().equals("1")) {
	    isModificaClassificafasc = true;
	}
	model.addAttribute("isModificaClassificaParametriProt", isModificaClassificafasc);
	popolaFascicoloMovimentoIstanza(command, isViewPage);
	model.addAttribute("protocolloCommand", command);
    }

    /**
     * @param command
     */
    private void popolaFascicoloMovimentoIstanza(ProtocollazioneCommand command, boolean isViewPage) {

	if (isViewPage) {
	    // dati della classifica nel caso si stia protocollando un istanza
	    if (command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
		String numeroFascicolo = findNumeroFascicolo(command.getEntity(), command.getComune().getCodicecomune(),
			command.getProtSoftware().getCodice(), command);
		command.setNumeroFascicolo(numeroFascicolo);
		String classifica = findClassificaFascicolo(command.getEntity(), command.getComune().getCodicecomune(),
			command.getProtSoftware().getCodice(), command);
		command.setClassificaFascicolo(classifica);
		String oggetto = "";
		oggetto = istanzeService.findFascicoloOggetto(command.getEntity());
		command.setOggettoFascicolo(oggetto);
		popolaAnnoFascicoloInRicerca(command.getEntity().getDataprotocollo(), command);
	    } else if (command.getProvenienza().equals(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
		// dati della classifica nel caso si stia protocollando un movimento
		Integer codiceMovimento = command.getMovimento().getId().getCodice();
		Movimenti m = movimentiService.findById(new PkId(codiceMovimento));
		String software = m.getIstanza().getSoftware().getCodice();
		String codiceComune = m.getIstanza().getComune().getCodicecomune();
		String nprot = command.getEntity().getNumeroprotocollo();
		Date datapr = command.getEntity().getDataprotocollo();
		String idProtocollo = command.getEntity().getFkidprotocollo();
		DatiProtocolloFascicolatoResponseType dpf = protocollazioneService.isFascicolato(ORMHelper.getToken(), idProtocollo, nprot, datapr,
			software, codiceComune);
		if (dpf != null) {
		    command.setClassificaFascicolo(dpf.getClassifica());
		    command.setOggettoFascicolo(dpf.getOggetto());
		    command.setNumeroFascicolo(dpf.getNumeroFascicolo());
		    if (StringUtils.isNotBlank(dpf.getAnnoFascicolo()) && Utilities.isInteger(dpf.getAnnoFascicolo())) {
			command.setAnnoFascicolo(Integer.parseInt(dpf.getAnnoFascicolo()));
		    }
		    if (StringUtils.isNotBlank(dpf.getDataFascicolo())) {
			Date dataFascicolo = Utilities.parseDateString(dpf.getDataFascicolo(), false);
			command.setDataFascicolo(dataFascicolo);
		    } else {
			command.setDataFascicolo(null);
		    }
		} else {
		    command.setDataFascicolo(null);
		}
		findClassificaFascicolo(command.getEntity(), command.getComune().getCodicecomune(), command.getProtSoftware().getCodice(), command);
		findNumeroFascicolo(command.getEntity(), command.getComune().getCodicecomune(), command.getProtSoftware().getCodice(), command);
		popolaAnnoFascicoloInRicerca(m.getDataprotocollo(), command);
	    }
	}
    }

    private void popolaAnnoFascicoloInRicerca(Date dataprotocollo, ProtocollazioneCommand command) {

	if (dataprotocollo != null) {
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(dataprotocollo);
	    int year = calendar.get(Calendar.YEAR);
	    command.setAnnoFascicoloProtocolloRicerca(String.valueOf(year));
	}
    }

    @RequestMapping
    public String stampaView(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento, Model model, HttpServletRequest request) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	ProtocollazioneCommand command = new ProtocollazioneCommand();
	command.setDisplayMode(ProtocollazioneCommand.NEW);
	command.setEntity(istanza);
	SessionDetails sessionDetails = getSessionDetails(request);
	model.addAttribute("protocolloCommand", command);
	if (codiceMovimento != null) {
	    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	    command.setMovimento(movimento);
	}
	Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONESTAMPA_URL,
		istanza.getComune().getCodicecomune(), istanza.getSoftware().getCodice());
	if (vp != null && StringUtils.isNotBlank(vp.getValore())) {
	    String urlTo = vp.getValore();
	    urlTo = replaceVariables(urlTo, command);
	    String urlBack = "../istanze/view.htm?codice=" + codiceIstanza;
	    if (codiceMovimento != null) {
		urlBack = "../movimenti/view.htm?codice=" + codiceMovimento;
	    }
	    urlTo = BackofficeNETConstants.getUrlTo(request, urlTo, urlBack, ORMHelper.getSoftware(), false);
	    return "redirect:" + urlTo;
	}
	List<String> listaStampanti = protocollazioneService.getListaStampanti(sessionDetails.getToken());
	if (listaStampanti.isEmpty()) {
	    throw new RuntimeException("Non è stato possibile recuperare la lista delle stampanti del server. Controllare le impostazioni.");
	}
	model.addAttribute("listaStampanti", listaStampanti);
	return "protocollazione/formStampa";
    }

    private String replaceVariables(String urlTo, ProtocollazioneCommand command) {

	if (StringUtils.isNotBlank(urlTo) && command != null) {
	    urlTo = urlTo.replaceAll("LABEL_SOFTWARE", ORMHelper.getSoftware());
	    urlTo = urlTo.replaceAll("LABEL_TOKEN", ORMHelper.getToken());
	    String codiceIstanza = "";
	    String codiceMovimento = "";
	    if (EntityUtils.getNestedProperty(command.getEntity(), "id.codice") != null) {
		codiceIstanza = ((Integer) EntityUtils.getNestedProperty(command.getEntity(), "id.codice")).toString();
	    }
	    urlTo = urlTo.replaceAll("LABEL_CODICEISTANZA", codiceIstanza);
	    if (EntityUtils.getNestedProperty(command.getMovimento(), "id.codice") != null) {
		codiceMovimento = ((Integer) EntityUtils.getNestedProperty(command.getMovimento(), "id.codice")).toString();
	    }
	    urlTo = urlTo.replaceAll("LABEL_CODICEMOVIMENTO", codiceMovimento);
	}
	return urlTo;
    }

    @RequestMapping
    public String stampa(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, BindingResult result, SessionStatus status,
	    HttpServletRequest request) {

	SessionDetails sessionDetails = getSessionDetails(request);
	String fkidprotocollo = "";
	String numeroprotocollo = "";
	Date dataProtocollo = null;
	if (EntityUtils.getNestedProperty(command.getMovimento(), "id.codice") != null) {
	    Movimenti movimento = movimentiService.findById(new PkId(command.getMovimento().getId().getCodice()));
	    command.setMovimento(movimento);
	    fkidprotocollo = movimento.getFkidprotocollo();
	    numeroprotocollo = movimento.getNumeroprotocollo();
	    dataProtocollo = movimento.getDataprotocollo();
	} else {
	    fkidprotocollo = command.getEntity().getFkidprotocollo();
	    numeroprotocollo = command.getEntity().getNumeroprotocollo();
	    dataProtocollo = command.getEntity().getDataprotocollo();
	}
	try {
	    protocollazioneService.stampaEtichette(sessionDetails.getToken(), fkidprotocollo, numeroprotocollo, dataProtocollo,
		    command.getNumeroCopie(), command.getStampante(), command.getProtSoftware().getCodice(), command.getComune().getCodicecomune());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, command, e);
	    fixRenderEntityProperty(command);
	    List<String> listaStampanti = protocollazioneService.getListaStampanti(sessionDetails.getToken());
	    if (listaStampanti.isEmpty()) {
		throw new RuntimeException("Non è stato possibile recuperare la lista delle stampanti del server. Controllare le impostazioni.");
	    }
	    model.addAttribute("listaStampanti", listaStampanti);
	    return "protocollazione/formStampa";
	}
	return "redirect:stampaEffettuata.htm?status_msg=02";
    }

    @RequestMapping
    public String stampaEffettuata(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, HttpServletRequest request) {

	command.setDisplayMode(BaseCommand.EDIT);
	SessionDetails sessionDetails = getSessionDetails(request);
	List<String> listaStampanti = protocollazioneService.getListaStampanti(sessionDetails.getToken());
	if (listaStampanti.isEmpty()) {
	    throw new RuntimeException("Non è stato possibile recuperare la lista delle stampanti del server. Controllare le impostazioni.");
	}
	model.addAttribute("listaStampanti", listaStampanti);
	return "protocollazione/formStampa";
    }

    @RequestMapping
    public String invioPecDocer(Model model, @ModelAttribute("protocolloCommand") ProtocollazioneCommand command, HttpServletRequest request) {

	String status = "01";
	try {
	    protocollazioneService.invioPECDocer(command.getMovimento().getId().getCodice());
	} catch (Exception e) {
	    copyErrorsToFlashMessages(command, true, "entity", e);
	    status = "03";
	}
	return "redirect:protocollazioneResult.htm?status_msg=" + status;
    }

    // Compila gli eventuali destinatari di un protocollo in partenza
    private List<ProtocolloSoggettoCommand> bindSoggetti(ProtocollazioneCommand command, SessionDetails sessDetails) {

	List<ProtocolloSoggettoCommand> soggetti = new ArrayList<ProtocolloSoggettoCommand>();
	int index = 0;
	Verticalizzazioniparametri vpAbilitaIndirizziMail = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_ABILITA_INDIRIZZI_EMAIL,
		command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	boolean abilitaIndirizziMail = false;
	if (vpAbilitaIndirizziMail != null && StringUtils.defaultIfEmpty(vpAbilitaIndirizziMail.getValore(), "0").equalsIgnoreCase("1")) {
	    abilitaIndirizziMail = true;
	}
	if (!command.getFlusso().equalsIgnoreCase(ProtocollazioneCommand.FLUSSO_PARTENZA)) {
	    abilitaIndirizziMail = false;
	}
	if (command.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
	    Anagrafe richiedente = command.getEntity().getRichiedente();
	    richiedente = anagrafeService.bindDomainObject(richiedente, PkId.class, "id.codice");
	    ProtocolloSoggettoCommand richComm = ProtocolloSoggettoCommand.fromGeneric();
	    richComm.setAnagrafe(richiedente);
	    if (abilitaIndirizziMail) {
		String email = istanzeService.findEmailSoggettoPratica(richiedente.getId().getCodice(), command.getEntity().getId().getCodice(),
			null);
		richComm.setEmail(email);
	    }
	    setMezziEmodInvioDefault(richComm, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	    soggetti.add(index, richComm);
	} else if (command.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
	    boolean compilaMittDest = true;
	    if (command != null && command.getMovimento() != null && EntityUtils.getNestedProperty(command.getMovimento(), "id.codice") != null) {
		Verticalizzazioniparametri param = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
			VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
			VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MOVNOPRECOMPILAMITT_DEST,
			command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
		if (param != null) {
		    String valore = StringUtils.defaultIfEmpty(param.getValore(), "0");
		    if (valore.equalsIgnoreCase("1")) {
			compilaMittDest = false;
		    }
		}
	    }
	    if (compilaMittDest) {
		Amministrazioni amm = command.getMovimento().getAmministrazioni();
		if (amm == null) {
		    Anagrafe richiedente = command.getEntity().getRichiedente();
		    richiedente = anagrafeService.bindDomainObject(richiedente, PkId.class, "id.codice");
		    ProtocolloSoggettoCommand soggetto = ProtocolloSoggettoCommand.fromGeneric();
		    soggetto.setAnagrafe(richiedente);
		    if (abilitaIndirizziMail) {
			String email = istanzeService.findEmailSoggettoPratica(richiedente.getId().getCodice(),
				command.getEntity().getId().getCodice(), command.getMovimento().getId().getCodice());
			soggetto.setEmail(email);
		    }
		    setMezziEmodInvioDefault(soggetto, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
		    soggetti.add(index, soggetto);
		} else {
		    amm = bindAmministrazioneFromMovimento(command.getMovimento());
		    ProtocolloSoggettoCommand soggetto = ProtocolloSoggettoCommand.fromGeneric();
		    soggetto.setAmministrazioni(amm);
		    if (abilitaIndirizziMail && StringUtils.isBlank(soggetto.getEmail())) {
			soggetto.setEmail(StringUtils.defaultIfEmpty(amm.getPec(), amm.getEmail()));
		    }
		    setMezziEmodInvioDefault(soggetto, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
		    soggetti.add(index, soggetto);
		}
	    }
	} else if (command.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_PEC)) {
	    /*
	     * cerco di preselezionare i mittenti del protocollo individuandoli fra le anagrafiche o le amministrazioni 
	     * in base all'indirizzo mail del mittente della PEC
	     */
	    PecInbox pec = command.getPec();
	    PECMessageHelper pmh = new PECMessageHelper();
	    pmh.setMittentiString(pec.getPecFrom());
	    List<String> mittenti = pmh.getMittenti();
	    AnagrafeFilter filter = new AnagrafeFilter();
	    Anagrafe datiFiltro = new Anagrafe();
	    datiFiltro.setFlagDisabilitato(0);
	    //dell'IDCOMUNE corrente
	    filter.setDefaultWhereCondition(DAOEnum.FIND_BY_IDCOMUNE);
	    filter.setDatiAnagrafe(datiFiltro);
	    List<Anagrafe> anagMittenti = new ArrayList<Anagrafe>();
	    List<Amministrazioni> ammMittenti = new ArrayList<Amministrazioni>();
	    for (String mittentePec : mittenti) {
		ammMittenti.addAll(amministrazioniService.findAmministrazioniByPECAddress(mittentePec));
		datiFiltro.setPec(mittentePec);
		anagMittenti.addAll(anagrafeService.findByFilter(filter));
	    }
	    for (Anagrafe anagrafe : anagMittenti) {
		ProtocolloSoggettoCommand psc = ProtocolloSoggettoCommand.fromGeneric();
		psc.setAnagrafe(anagrafe);
		setMezziEmodInvioDefault(psc, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
		soggetti.add(psc);
	    }
	    for (Amministrazioni amministrazione : ammMittenti) {
		ProtocolloSoggettoCommand psc = ProtocolloSoggettoCommand.fromGeneric();
		psc.setAmministrazioni(amministrazione);
		setMezziEmodInvioDefault(psc, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
		soggetti.add(psc);
	    }
	}
	if (index > 0) {
	    index++;
	}
	ProtocolloSoggettoCommand vuoto = ProtocolloSoggettoCommand.fromGeneric();
	setMezziEmodInvioDefault(vuoto, command.getComune().getCodicecomune(), command.getProtSoftware().getCodice());
	soggetti.add(index, vuoto);
	return soggetti;
    }

    /**
     * @param sessDetails
     * @return
     */
    private Amministrazioni bindAmministrazione(SessionDetails sessDetails, String codiceComune, String software, ProtocollazioneCommand command) {

	Amministrazioni amm = null;
	Responsabili responsabile = sessDetails.getResponsabile();
	responsabile = responsabiliService.findById(responsabile.getId());
	Set<Amministrazioniresponsabili> ammresp = responsabile.getResponsabiliamministrazionis();
	for (Amministrazioniresponsabili amministrazioniresponsabili : ammresp) {
	    Amministrazioni amministrazione = amministrazioniresponsabili.getAmministrazioni();
	    AmministrProtocollo amp = amministrProtocolloService.findByAmministrazioneComuneESoftware(amministrazione.getId().getCodice(),
		    codiceComune, software);
	    if (amp != null) {
		if (StringUtils.isNotBlank(amp.getProtUo()) || StringUtils.isNotBlank(amp.getProtRuolo())) {
		    amm = amministrazione;
		    break;
		}
	    }
	}
	if (command != null) {
	    if (command.getProvenienza() != null && (command.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_ISTANZA)
		    || command.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI))) {
		Integer codiceIntervento = null;
		Integer codiceIstanza = null;
		if (command.getProvenienza().equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
		    codiceIstanza = command.getEntity().getId().getCodice();
		} else {
		    codiceIstanza = command.getMovimento().getIstanza().getId().getCodice();
		}
		if (codiceIstanza != null) {
		    Istanze i = istanzeService.findById(new PkId(codiceIstanza));
		    codiceIntervento = i.getAlberoproc().getId().getCodice();
		    if (codiceIntervento != null) {
			Integer ammId = alberoprocProtocolloService.findAmministrazioniByAlberoprocIdAndComune(codiceIntervento, codiceComune);
			if (ammId != null) {
			    amm = amministrazioniService.findById(new PkId(ammId));
			}
		    }
		}
	    }
	}
	if (amm == null) {
	    Verticalizzazioniparametri verticalizzazioniparametri = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CODICEAMMINISTRAZIONEDEFAULT, codiceComune,
		    software);
	    if (verticalizzazioniparametri != null
		    && verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro().equalsIgnoreCase(
			    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_CODICEAMMINISTRAZIONEDEFAULT)) {
		String codiceAmministrazioneStr = verticalizzazioniparametri.getValore();
		Integer codiceAmmnistrazione = null;
		try {
		    codiceAmmnistrazione = Integer.valueOf(codiceAmministrazioneStr);
		} catch (Exception e) {
		}
		if (codiceAmmnistrazione != null) {
		    amm = amministrazioniService.findById(new PkId(codiceAmmnistrazione));
		}
	    }
	}
	return amministrazioniDTO(amm);
    }

    private Map<String, String> getFlussiPerProvenienza(String provenienza) {

	Map<String, String> result = new HashMap<String, String>();
	if (provenienza != null) {
	    if (provenienza.equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_ISTANZA)) {
		result.put(ProtocollazioneCommand.FLUSSO_INTERNO, ProtocollazioneCommand.FLUSSO_INTERNO);
		result.put(ProtocollazioneCommand.FLUSSO_ARRIVO, ProtocollazioneCommand.FLUSSO_ARRIVO);
	    } else if (provenienza.equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_MOVIMENTI)) {
		result.put(ProtocollazioneCommand.FLUSSO_INTERNO, ProtocollazioneCommand.FLUSSO_INTERNO);
		result.put(ProtocollazioneCommand.FLUSSO_ARRIVO, ProtocollazioneCommand.FLUSSO_ARRIVO);
		result.put(ProtocollazioneCommand.FLUSSO_PARTENZA, ProtocollazioneCommand.FLUSSO_PARTENZA);
	    } else if (provenienza.equalsIgnoreCase(ProtocollazioneCommand.PROVENIENZA_PEC)) {
		//LION 2013-10-25: per il protocollo della PEC esiste solo il flusso in arrivo
		result.put(ProtocollazioneCommand.FLUSSO_ARRIVO, ProtocollazioneCommand.FLUSSO_ARRIVO);
	    }
	} else {
	    result.put(ProtocollazioneCommand.FLUSSO_INTERNO, ProtocollazioneCommand.FLUSSO_INTERNO);
	    result.put(ProtocollazioneCommand.FLUSSO_ARRIVO, ProtocollazioneCommand.FLUSSO_ARRIVO);
	    result.put(ProtocollazioneCommand.FLUSSO_PARTENZA, ProtocollazioneCommand.FLUSSO_PARTENZA);
	}
	return result;
    }

    protected void setPageAttributes(Model model, String codiceComune) {

	List<Verticalizzazioniparametri> parametris = verticalizzazioniparametriService
		.findParametriConfiguratiByModuloAndComune(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE, codiceComune);
	for (Verticalizzazioniparametri verticalizzazioniparametri : parametris) {
	    model.addAttribute(verticalizzazioniparametri.getVerticalizzazioniparametribase().getId().getParametro(),
		    verticalizzazioniparametri.getValore());
	}
    }

    private ProtocolloMezzi getMezzoDefault(String codiceComune, String software) {

	ProtocolloMezzi mezzoDefault = null;
	Verticalizzazioniparametri vpMezzi = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MEZZO_DEFAULT, codiceComune, software);
	if (vpMezzi != null) {
	    String mezzoDefaultCodice = StringUtils.defaultString(vpMezzi.getValore()).trim();
	    if (StringUtils.isNotBlank(mezzoDefaultCodice)) {
		mezzoDefault = protocolloMezziService.findByCodiceMezzoAndComuneAndSoftware(mezzoDefaultCodice, codiceComune, software);
	    }
	}
	return mezzoDefault;
    }

    private ProtocolloModalitainvio getModalitainvioDefault(String codiceComune, String software) {

	ProtocolloModalitainvio modalitaInvioDefault = null;
	Verticalizzazioniparametri vpModalitaInvio = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MODALITA_TRASMISSIONE_DEFAULT, codiceComune,
		software);
	if (vpModalitaInvio != null) {
	    String modalitaInvioDefaultCodice = StringUtils.defaultString(vpModalitaInvio.getValore()).trim();
	    if (StringUtils.isNotBlank(modalitaInvioDefaultCodice)) {
		modalitaInvioDefault = protocolloModalitainvioService.findByCodiceModalitaAndComuneAndSoftware(modalitaInvioDefaultCodice,
			codiceComune, software);
	    }
	}
	return modalitaInvioDefault;
    }

    private void setMezziEmodInvioDefault(ProtocolloSoggettoCommand soggettoCommand, String codiceComune, String software) {

	ProtocolloMezzi mezzoDefault = getMezzoDefault(codiceComune, software);
	ProtocolloModalitainvio modInvioDefault = getModalitainvioDefault(codiceComune, software);
	if (mezzoDefault != null) {
	    boolean inserisciMezzo = true;
	    if (soggettoCommand.getMezzo() != null && soggettoCommand.getMezzo().getId() != null
		    && StringUtils.isNotBlank(soggettoCommand.getMezzo().getCodice())) {
		inserisciMezzo = false;
	    }
	    if (inserisciMezzo) {
		soggettoCommand.setMezzo(mezzoDefault);
	    }
	}
	if (modInvioDefault != null) {
	    boolean inserisciMod = true;
	    if (soggettoCommand.getModInvio() != null && soggettoCommand.getModInvio().getId() != null
		    && StringUtils.isNotBlank(soggettoCommand.getModInvio().getCodice())) {
		inserisciMod = false;
	    }
	    if (inserisciMod) {
		soggettoCommand.setModInvio(modInvioDefault);
	    }
	}
    }

    /**
     * <pre>
     * Il metodo ritorna una mappa contenete un intero calcolato in modo progressivo a partire da zero e una stringa che
     * rappresenta il nome dell'allegato.All'interno della mappa saranno inseriti solo i valori che rispettano la seguente logica:
     * documentiistanza.idbase !=null and documentiistanza.idbase==idbase dell'oggetto Allegato che torna dal protocollo.
     * La chiave int viene comunque aggiornata ad ogni ciclo
     * 
     * Es. nomeFile di documentiistanza : allegato1.pdf
     *     allegato.serial 		: allegato1 - (allegato1.pdf)
     *     
     *     nella mappa verrà inserito il valore n,allegato1 , dove n è l'n-esimo passaggio
     * Es. nomeFile di documentiistanza : allegato1.pdf
     *     allegato.serial 		: allegato2 - (allegato2.pdf)
     *     
     *     nella mappa non verrà inserito nessun valore,  n viene ugualmente inmcrementato di un unità
     * &#64;param list
     * &#64;param listAllegatos
     * &#64;return
     * </pre>
     */
    private Map<Integer, String> getAllegatiPresentiInDocumentiIstanza(List<DocumentiistanzaDTO> list, List<AllegatoResponseType> listAllegatos) {

	Map<Integer, String> map = new HashMap<Integer, String>();
	int key = 0;
	for (AllegatoResponseType allegato : listAllegatos) {
	    for (DocumentiistanzaDTO documentiistanzaDTO : list) {
		String idbaseAllegato = allegato.getIDBase();
		if (StringUtils.isNotBlank(documentiistanzaDTO.getIdBase()) && StringUtils.equals(documentiistanzaDTO.getIdBase(), idbaseAllegato)) {
		    String nomeFile = allegato.getSerial();
		    if (StringUtils.isNotBlank(allegato.getSerial())) {
			nomeFile = allegato.getCommento();
		    }
		    map.put(key, nomeFile);
		}
	    }
	    key++;
	}
	return map;
    }

    /**
     * <pre>
     * Il metodo ritorna una mappa contente un intero calcolato in modo progressivo a partire da zero e una stringa che
     * rappresenta il nome dell'allegato.All'interno della mappa saranno inseriti solo i valori che rispettano la seguente logica:
     * allegatomovimento.idbase !=null and allegatomovimento.idbase==idbase dell'oggetto Allegato che torna dal protocollo.
     * La chiave int viene comunque aggiornata ad ogni ciclo
     * 
     * Es. nomeFile di movimentiallegati : allegato1.pdf
     *     allegato.serial 		 : allegato1 - (allegato1.pdf)
     *     
     *     nella mappa verrà inserito il valore n,allegato1 , dove n è l'n-esimo passaggio
     * Es. nomeFile di movimentiallegati : allegato1.pdf
     *     allegato.serial 		 : allegato2 - (allegato2.pdf)
     *     
     *     nella mappa non verrà inserito nessun valore,  n viene ugualmente inmcrementato di un unità
     * &#64;param list
     * &#64;param listAllegatos
     * &#64;return
     * </pre>
     */
    private Map<Integer, String> getAllegatiPresentiInMovimentiAllegati(List<MovimentiallegatiDTO> list, List<AllegatoResponseType> listAllegatos) {

	Map<Integer, String> map = new HashMap<Integer, String>();
	int key = 0;
	for (AllegatoResponseType allegato : listAllegatos) {
	    for (MovimentiallegatiDTO movimentiallegatiDTO : list) {
		String idbaseAllegato = allegato.getIDBase();
		if (StringUtils.isNotBlank(movimentiallegatiDTO.getIdBase())
			&& StringUtils.equals(movimentiallegatiDTO.getIdBase(), idbaseAllegato)) {
		    String nomeFile = allegato.getSerial();
		    if (StringUtils.isNotBlank(allegato.getSerial())) {
			nomeFile = allegato.getCommento();
		    }
		    map.put(key, nomeFile);
		}
	    }
	    key++;
	}
	return map;
    }

    private Map<Integer, String> getAllegatiPresentiInPEC(PecInbox pec, List<AllegatoResponseType> listAllegatos) {

	Map<Integer, String> map = new HashMap<Integer, String>();
	int key = 0;
	for (AllegatoResponseType allegato : listAllegatos) {
	    map.put(key, allegato.getSerial());
	    key++;
	}
	return map;
    }

    private void downloadEmls(Movimenti movimento) {

	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAIONE_MAIL_SERVICE)) {
	    String folder = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAIONE_MAIL_SERVICE,
		    WebConstants.VERTICALIZZAIONE_MAIL_SERVICE_POSTA_USCITA_FOLDERNAME);
	    if (StringUtils.isNotBlank(folder)) {
		log.debug("downloadEml# verifico se c'è da fare il download di allegati eml per del movimento {} [{}]",
			new Object[] { movimento.getTipomovimento().getMovimento(), movimento.getId() });
		List<Movimentiallegati> allegatiMov = movimentiallegatiService.findByMovimento(movimento.getId().getCodice());
		for (Movimentiallegati movimentiallegati : allegatiMov) {
		    if (EntityUtils.getNestedProperty(movimentiallegati.getOggetto(), "id.codice") == null
			    && StringUtils.isNotBlank(movimentiallegati.getMessageId())) {
			downloadEml(movimentiallegati, folder);
		    }
		}
		log.debug("downloadEmls# verifico se c'è da fare il download di allegati eml per gli altri movimenti dell'istanza {} [{}] ",
			new Object[] { movimento.getIstanza().getNumeroistanza(), movimento.getIstanza().getId() });
		List<Movimenti> altriMovs = movimentiService.findByIstanzaAndExcludeMovimento(movimento.getIstanza().getId().getCodice(),
			movimento.getId().getCodice(), SceltaMovimentiEnum.ESEGUITI);
		for (Movimenti altriMovimento : altriMovs) {
		    log.debug("downloadEml# verifico se c'è da fare il download di allegati eml per altro movimento {} [{}]",
			    new Object[] { altriMovimento.getTipomovimento().getMovimento(), altriMovimento.getId() });
		    List<Movimentiallegati> altroMovallegati = movimentiallegatiService.findByMovimento(altriMovimento.getId().getCodice());
		    for (Movimentiallegati movimentiallegati : altroMovallegati) {
			if (EntityUtils.getNestedProperty(movimentiallegati.getOggetto(), "id.codice") == null
				&& StringUtils.isNotBlank(movimentiallegati.getMessageId())) {
			    downloadEml(movimentiallegati, folder);
			}
		    }
		}
	    }
	} else {
	    log.debug("downloadEmls# Funzionalità di download degli eml dal server di posta per un movimento non configurata");
	}
    }

    private void downloadEml(Movimentiallegati movimentiallegatiEml, String folder) {

	Oggetti oggetti = null;
	//il processamento delle pec è una procedura lenta quindi imposto il timeout a 15 min.
	NlaGestioneMail pecWs = null;
	ScaricaMessaggioInviatoBinarioRequest scaricaMessaggioInviatoBinarioRequest = null;
	ScaricaMessaggioInviatoBinarioResponse scaricaMessaggioInviatoBinarioResponse = null;
	try {
	    log.debug("downloadEml# genero il port del ws....");
	    String wsUrl = getNlaGestioneMailWSURL();
	    NlaGestioneMailWSClient nlaGestioneMailWSClient = new NlaGestioneMailWSClient(wsUrl, this.verticalizzazioneParametriSistemaService);
	    pecWs = nlaGestioneMailWSClient.getWSPort();
	    log.debug("downloadEml# cartella configurata: {}", folder);
	    scaricaMessaggioInviatoBinarioRequest = new ScaricaMessaggioInviatoBinarioRequest();
	    scaricaMessaggioInviatoBinarioRequest.setSoftware(ORMHelper.getSoftware());
	    scaricaMessaggioInviatoBinarioRequest.setToken(ORMHelper.getToken());
	    scaricaMessaggioInviatoBinarioRequest.setFolderName(folder);
	    scaricaMessaggioInviatoBinarioRequest.setIdentificativoBackofficeMessaggio(movimentiallegatiEml.getMessageId());
	    scaricaMessaggioInviatoBinarioResponse = pecWs.scaricaMessaggioInviatoBinario(scaricaMessaggioInviatoBinarioRequest);
	    pecWs.scaricaMessaggioInviatoBinario(scaricaMessaggioInviatoBinarioRequest);
	    if (scaricaMessaggioInviatoBinarioResponse != null) {
		byte[] b = Utilities.dataHandlerToBytes(scaricaMessaggioInviatoBinarioResponse.getContent());
		oggetti = new Oggetti();
		oggetti.setDimensioneFile(b.length);
		oggetti.setNomefile(scaricaMessaggioInviatoBinarioResponse.getNomeFile());
		oggetti.setOggetto(b);
		movimentiallegatiEml.setOggetto(oggetti);
		oggettiService.insert(oggetti);
		movimentiallegatiService.update(movimentiallegatiEml);
	    }
	} catch (Exception e) {
	    log.error("Attenzione, impossibile fare il download della email per l'allegato codice {} del movimento {}[{}] : {}",
		    new Object[] { movimentiallegatiEml.getId().getCodice(), movimentiallegatiEml.getMovimento().getMovimento(),
			    movimentiallegatiEml.getMovimento().getId().getCodice(), e.getMessage() });
	}
    }

    private String getNlaGestioneMailWSURL() {

	return this.pecInboxService.getNlaGestioneMailWSURL();
    }

    @Override
    protected void setPageAttributes(Model model) {

	// non necessario
    }

    @Override
    protected void fixMergeEntityProperty(ProtocollazioneCommand entity) {

	// non necessario
    }

    @Override
    protected void fixRenderEntityProperty(ProtocollazioneCommand entity) {

	// non necessario
    }
}
