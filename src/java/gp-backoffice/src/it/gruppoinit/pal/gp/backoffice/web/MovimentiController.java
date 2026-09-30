package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.backoffice.web.util.ApplicationLinkUtil;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.SituazioneAllegato;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazionireferenti;
import it.gruppoinit.pal.gp.core.domain.AppIoCodaMovimenti;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiContromovimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PecInbox;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.TipimovStcMapping;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentodoctipo;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.MovimentiCommand;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.comunicazioni.appio.IAppIoCodaMovimentiService;
import it.gruppoinit.pal.gp.core.features.movimenti.allegati.resolver.MovimentiAllegatiResolverException;
import it.gruppoinit.pal.gp.core.features.movimenti.allegati.resolver.MovimentiAllegatiResolverFactoryService;
import it.gruppoinit.pal.gp.core.features.movimenti.configurazione.doctipo.TipimovimentodoctipoService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieRService;
import it.gruppoinit.pal.gp.core.service.DocumentiHelperService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiManager;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.MovimentimailService;
import it.gruppoinit.pal.gp.core.service.PecInboxService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipimovStcMappingService;
import it.gruppoinit.pal.gp.core.service.exception.BaseValidationException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;
import it.gruppoinit.pal.gp.core.utils.LoggerModificheIstanze;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloEsitatoResponseType;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloLettoResponseType;
import it.gruppoinit.protocollo.schemas.messages.EnumEsitatoType;
import it.init.sigepro.rte.NotificaAttivitaRequest;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("movimentiCommand")
public class MovimentiController extends BaseController<Movimenti> {

    private static final Logger log = LoggerFactory.getLogger(MovimentiController.class);
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private DocumentiHelperService documentiHelperService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private MovimentiallegatiService movimentiallegatiService;
    @Autowired
    private MovimentimailService movimentimailService;
    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private MovimentiManager movimentiManager;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private StatiistanzaService statiistanzaService;
    @Autowired
    private TipicausalioneriService tipicausalioneriService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private CommissioniedilizieRService commissioniedilizieRService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private TipimovStcMappingService tipimovStcMappingService;
    @Autowired
    private MovimentiNoSecurityService movimentiNoSecurityService;
    @Autowired
    private PecInboxService pecInboxService;
    @Autowired
    private MovimentiZipLogicoService movimentiZipLogicoService;
    @Autowired
    private IAppIoCodaMovimentiService appIoCodaMovimentiService;
    private MovimentiAllegatiResolverFactoryService movimentiAllegatiResolverFactoryService;
    @Autowired
    private TipimovimentodoctipoService tipimovimentodoctipoService;
    @Autowired
    private ProtocollazioneService protocollazioneService;

    @Autowired
    public void setMovimentiAllegatiResolverFactoryService(MovimentiAllegatiResolverFactoryService movimentiAllegatiResolverFactoryService) {

	this.movimentiAllegatiResolverFactoryService = movimentiAllegatiResolverFactoryService;
    }

    @RequestMapping
    public ModelMap list(@RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request, HttpServletResponse response) {

	PkId idIstanza = new PkId(codiceIstanza);
	Istanze istanza = new Istanze();
	istanza.setId(idIstanza);
	istanza = istanzeService.bindDomainObject(istanza, PkId.class, "id.codice");
	checkAccessoInformazioni(istanza, false);
	List<Movimenti> movimentiList = movimentiService.findEseguitiByIstanza(istanza);
	ModelMap model = new ModelMap(movimentiList);
	boolean export = createJMesaExport(request, response, movimentiList);
	if (export) {
	    return null;
	}
	model.addAttribute("istanza", istanza);
	model.addAttribute("movimentiList", movimentiList);
	String codiceComune = istanza.getComune().getCodicecomune();
	listPageAttributes(request, codiceComune, codiceIstanza);
	return model;
    }

    private void listPageAttributes(HttpServletRequest request, String codiceComune, Integer codiceIstanza) {

	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_STC, request);
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_INFOCAMERA, request);
	request.setAttribute("isCds", Boolean.valueOf(commissioniedilizieRService.countCommissioniByIstanza(codiceIstanza) > 0));
    }

    @RequestMapping
    public String create(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "inserimentoVeloce", required = false) Boolean inserimentoVeloce, Model model, HttpServletRequest request) {

	PkId idIstanza = new PkId(codiceIstanza);
	Istanze istanza = new Istanze();
	istanza.setId(idIstanza);
	istanza = istanzeService.bindDomainObject(istanza, PkId.class, "id.codice");
	// BOCCI 12-11-2012 SE L'OPERATORE NON PUO' AGGIORNARE LA PRATICA (ESEMPIO OPERATORE CON RUOLO READONLY E WRITE SU MOVIMENTO) ALLORA DA' ERRORE
	// IN QUESTO CASO NON DOBBIAMO FAR ESEGUIRE LA MODIFICA DELLO STATO ISTANZA E L'ELABORAZIONE
	TipoAccessoEnum tipoaccesso = istanzeService.checkAccessoIstanza(istanza, getCurrentlyAuthenticatedUserDetails());
	if (tipoaccesso == TipoAccessoEnum.SOLA_LETTURA) {
	    String message = "TENTATIVO DI INSERIMENTO MOVIMENTO: l'utente: " +
		    getCurrentlyAuthenticatedUserDetails() +
		    " ha tentato di inserire un movimenti nell'istanza " +
		    istanza.toString();
	    LoggerModificheIstanze.log(message);
	    throw new SecurityException("L'Utente non può eseguire l'operazione");
	}
	MovimentiCommand movimentiCommand = new MovimentiCommand();
	if (inserimentoVeloce != null) {
	    movimentiCommand.setInserimentoVeloce(inserimentoVeloce.booleanValue());
	}
	movimentiCommand.setDisplayMode(MovimentiCommand.NEW);
	movimentiCommand.getEntity().setIstanza(istanza);
	Verticalizzazioniparametri vertNonProporreData = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE, WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_MOV_NON_PROPORRE_DATA);
	String nonProporreData = "N";
	if (vertNonProporreData != null) {
	    if (StringUtils.isNotBlank(vertNonProporreData.getValore())) {
		nonProporreData = vertNonProporreData.getValore();
	    }
	}
	if (StringUtils.defaultString(nonProporreData, "N").equalsIgnoreCase("N")) {
	    movimentiCommand.getEntity().setData(Calendar.getInstance().getTime());
	}
	movimentiCommand.setStatiistanza(istanza.getChiusura());
	fixRenderEntityProperty(movimentiCommand.getEntity());
	model.addAttribute("movimentiCommand", movimentiCommand);
	//gestione sulla configurazione utente del parametro salva e esci
	// il flag Salva ed esci è un parametro che viene recuperato dalla configurazione utente
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_MOVIMENTI_OK_CHIUDI, "1", request);
	setPageAttributes(model);
	setPageAttributes(model, movimentiCommand.getEntity());
	return "movimenti/form";
    }

    @RequestMapping
    public String insert(Model model, @ModelAttribute("movimentiCommand") MovimentiCommand movimentiCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(movimentiCommand.getEntity());
	try {
	    movimentiCommand.getEntity().setResponsabile(getCurrentlyAuthenticatedUserDetails());
	    Movimenti movimento = movimentiCommand.getEntity();
	    movimentiService.insert(movimento);
	    // BOCCI 12-11-2012 SE L'OPERATORE NON PUO' AGGIORNARE LA PRATICA (ESEMPIO OPERATORE CON RUOLO READONLY E WRITE SU MOVIMENTO) ALLORA DA' ERRORE
	    // IN QUESTO CASO NON DOBBIAMO FAR ESEGUIRE LA MODIFICA DELLO STATO ISTANZA E L'ELABORAZIONE
	    Integer codiceIstanza = movimentiCommand.getEntity().getIstanza().getId().getCodice();
	    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	    TipoAccessoEnum tipoaccesso = istanzeService.checkAccessoIstanza(istanza, getCurrentlyAuthenticatedUserDetails());
	    if (tipoaccesso == TipoAccessoEnum.CONSENTITO) {
		MovimentiHelper mh = checkUpdateStatoistanzaGetMovHelper(movimentiCommand, istanza);
		if (mh != null) {
		    if (mh.isRilascioAutorizzazione()) {
			FlashMessages.getWarnings().add(
				"Il cambiamento di stato dell'istanza ha generato il movimento di chiusura e questo prevede il rilascio dell'atto conclusivo.");
			return "redirect:../autorizzazioni/createAutorizzazione.htm?codiceIstanza=" +
				codiceIstanza +
				"&codiceMovimento=" +
				mh.getMovimento().getId().getCodice() +
				"&codiceStato=" +
				movimentiCommand.getStatiistanza().getId().getCodicestato();
		    }
		}
		Date dataDaElaborare = getDataMovimentoDaElaborare(movimentiCommand.getEntity());
		istanzeService.elabora(codiceIstanza, false, dataDaElaborare);
	    }
	    // BOCCI 12-11-2012 
	    MovimentiHelper helper = movimentiService.findCaratteristicheMovimento(movimentiCommand.getEntity());
	    if (helper.isRilascioAutorizzazione()) {
		status.setComplete();
		return "redirect:../autorizzazioni/createAutorizzazione.htm?codiceIstanza=" +
			codiceIstanza +
			"&codiceMovimento=" +
			movimentiCommand.getEntity().getId().getCodice();
	    }
	    if (helper.isEffettuaChiusura()) {
		status.setComplete();
		return "redirect:chiudiIstanza.htm?codice=" + movimentiCommand.getEntity().getId().getCodice();
	    }
	    if (helper.isCdsCommissione()) {
		status.setComplete();
		return listCommissioniDelMovimento(movimentiCommand.getEntity());
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, movimentiCommand.getEntity(), true, e);
	    prepareView(model, movimentiCommand, request, true);
	    fixRenderEntityProperty(movimentiCommand.getEntity());
	    gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_MOVIMENTI_OK_CHIUDI, "1", request);
	    setPageAttributes(model);
	    setPageAttributes(model, movimentiCommand.getEntity());
	    return "movimenti/form";
	}
	// BOCCI 12/01/2012 QUANDO SALVO UN MOVIMENTO DALLO SCADENZARIO SE AZZERO LO STATUS DA ERRORE
	// status.setComplete();
	// BOCCI 12/01/2012 QUANDO SALVO UN MOVIMENTO DALLO SCADENZARIO SE AZZERO LO STATUS DA ERRORE
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_MOVIMENTI_OK_CHIUDI, "1", request);
	// se true: al salvataggio si reindirizza l'utenete alla lista delle elaborazioni
	// se false: l'utente viene reindirizzato alla pagina di modifica del movimento
	if (movimentiCommand.getSalvaEdEsci()) {
	    return getHistoryBack();
	} else {
	    return "redirect:view.htm?codice=" + movimentiCommand.getEntity().getId().getCodice() + "&status_msg=01";
	}
    }

    private MovimentiHelper checkUpdateStatoistanzaGetMovHelper(MovimentiCommand movimentiCommand, Istanze istanza) {

	// BOCCI 2012-03-08 BUGZILLA ID 466 3.3
	// In MovimentiController nel metodo insert inibire la chiamata a stanzeService.updateStatoIstanza() nel caso che il tipomovimento abbia configurato questo comportamento
	Tipimovimento tm = tipiMovimentoService.findById(movimentiCommand.getEntity().getTipomovimento().getId());
	boolean eseguiUpdateStatoIstanza = true;
	if (EntityUtils.getNestedProperty(tm, "statoistanza.id") != null) {
	    if (StringUtils.isNotBlank(tm.getStatoistanza().getId().getCodicestato())) {
		eseguiUpdateStatoIstanza = false;
	    }
	}
	if (eseguiUpdateStatoIstanza) {
	    MovimentiHelper helper = istanzeService.updateStatoIstanza(istanza, movimentiCommand.getStatiistanza().getId().getCodicestato());
	    return helper;
	}
	return null;
    }

    @RequestMapping
    public String createScadenza(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request) {

	PkId idIstanza = new PkId(codiceIstanza);
	Istanze istanza = new Istanze();
	istanza.setId(idIstanza);
	istanza = istanzeService.bindDomainObject(istanza, PkId.class, "id.codice");
	MovimentiCommand movimentiCommand = new MovimentiCommand();
	movimentiCommand.setDisplayMode(MovimentiCommand.NEW_SCADENZA);
	movimentiCommand.getEntity().setIstanza(istanza);
	fixRenderEntityProperty(movimentiCommand.getEntity());
	movimentiCommand.setStatiistanza(istanza.getChiusura());
	model.addAttribute("movimentiCommand", movimentiCommand);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_MOVIMENTI_OK_CHIUDI, "1", request);
	setPageAttributes(model);
	setPageAttributes(model, movimentiCommand.getEntity());
	return "movimenti/form";
    }

    @RequestMapping
    public String insertScadenza(Model model, @ModelAttribute("movimentiCommand") MovimentiCommand movimentiCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(movimentiCommand.getEntity());
	try {
	    movimentiService.insertScadenza(movimentiCommand.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, movimentiCommand.getEntity(), true, e);
	    prepareView(model, movimentiCommand, request, true);
	    fixRenderEntityProperty(movimentiCommand.getEntity());
	    setPageAttributes(model);
	    setPageAttributes(model, movimentiCommand.getEntity());
	    return "movimenti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + movimentiCommand.getEntity().getId().getCodice() + "&status_msg=01";
    }

    @RequestMapping
    public String updateScadenza(Model model, @ModelAttribute("movimentiCommand") MovimentiCommand movimentiCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(movimentiCommand.getEntity());
	try {
	    movimentiService.updateScadenza(movimentiCommand.getEntity());
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, movimentiCommand.getEntity(), true, e);
	    prepareView(model, movimentiCommand, request, false);
	    fixRenderEntityProperty(movimentiCommand.getEntity());
	    setPageAttributes(model);
	    setPageAttributes(model, movimentiCommand.getEntity());
	    return "movimenti/form";
	}
	status.setComplete();
	return "redirect:view.htm?codice=" + movimentiCommand.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String view(@RequestParam("codice") Integer codice, @RequestParam(value = "esito", required = false) Boolean esito, Model model,
	    HttpServletRequest request) {

	PkId id = new PkId(codice);
	MovimentiCommand movimentiCommand = new MovimentiCommand();
	movimentiCommand.setDisplayMode(MovimentiCommand.VIEW);
	Movimenti movimenti = movimentiService.findById(id);
	if (movimenti.getData() == null) {
	    movimentiCommand.setDisplayMode(MovimentiCommand.NEW);
	    if (movimenti.getTipomovimento() != null) {
		if (BooleanUtils.isTrue(movimenti.getTipomovimento().getFlagDisabilitato())) {
		    movimentiCommand.setEntity(movimenti);
		    model.addAttribute("causa", "Tipo movimento disabilitato");
		    model.addAttribute("movimentiCommand", movimentiCommand);
		    return "movimenti/eseguiElaborazione";
		}
	    }
	    if (movimenti.getAmministrazioni() != null) {
		if (movimenti.getAmministrazioni().getId() != null) {
		    if (movimenti.getAmministrazioni().getId().getCodice() != null) {
			Amministrazioni ammDisabilitata = amministrazioniService
				.findById(new PkId(movimenti.getAmministrazioni().getId().getCodice()));
			if (ammDisabilitata.getFlagDisabilitato() != null) {
			    if (ammDisabilitata.getFlagDisabilitato().booleanValue() == true) {
				movimentiCommand.setEntity(movimenti);
				model.addAttribute("causa", "Amministrazione disabilitata");
				model.addAttribute("movimentiCommand", movimentiCommand);
				return "movimenti/eseguiElaborazione";
			    }
			}
		    }
		}
	    }
	    // Se flagAccediSchede=true il click su un movimento da eseguire, che ha configurate delle schede dimaniche, 
	    // lo eseguirà automaticamente e l'utente sarà inviato alla pagina di gestione schede.
	    if (movimenti.getTipomovimento() != null && movimenti.getTipomovimento().getFlagAccediSchede() != null) {
		if (BooleanUtils.isTrue(movimenti.getTipomovimento().getFlagAccediSchede())) {
		    if (!movimenti.getTipomovimento().getTipimovimentidyn2modellits().isEmpty()) {
			movimenti.setEsito(esito);
			movimenti.setData(Calendar.getInstance().getTime());
			movimentiService.insert(movimenti);
			String uriBack = "/movimenti/listElaborazione.htm?codiceIstanza=" + movimenti.getIstanza().getId().getCodice();
			//			String uriTo = "/aspnet/Istanze/DatiDinamici/IstanzeDyn2Modelli.aspx?CodiceIstanza="
			//				+ movimenti.getIstanza().getId().getCodice() + "&CodiceMovimento=" + movimenti.getId().getCodice();
			return "redirect:" +
				ApplicationLinkUtil.getLinkSchede(request, uriBack, ORMHelper.getSoftware(), false,
					movimenti.getIstanza().getId().getCodice(), movimenti.getId().getCodice());
		    }
		}
	    }
	    // BOCCI 2012-08-31: NON DEVO IMPOSTARE LA DATA SE NULLA LO FACCIO NELLA JSP
	    //	    if (movimenti.getDataScadenza() != null) {
	    //		movimenti.setData(movimenti.getDataScadenza());
	    //	    } else {
	    //		movimenti.setData(Calendar.getInstance().getTime());
	    //	    }
	    Verticalizzazioniparametri vertNonProporreData = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE, WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_MOV_NON_PROPORRE_DATA);
	    String nonProporreData = "N";
	    if (vertNonProporreData != null) {
		if (StringUtils.isNotBlank(vertNonProporreData.getValore())) {
		    nonProporreData = vertNonProporreData.getValore();
		}
	    }
	    if (StringUtils.defaultString(nonProporreData, "N").equalsIgnoreCase("N")) {
		movimentiCommand.getEntity().setData(Calendar.getInstance().getTime());
	    }
	}
	//Controlla se il movimento è stato creato da una commissione, nel caso recupera il dettaglio dell' esito della 
	// commissione e riporta le info sulla jsp
	CommissioniedilizieR commissioniedilizieR = commissioniedilizieRService.findByMovimentorientro(movimenti);
	if (commissioniedilizieR != null) {
	    movimentiCommand.setCommissioniedilizieR(commissioniedilizieR);
	}
	//gestione sulla configurazione utente del parametro salva e esci
	// il flag Salva ed esci è un parametro che viene recuperato dalla configurazione utente
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_MOVIMENTI_OK_CHIUDI, "1", request);
	fixRenderEntityProperty(movimenti);
	movimentiCommand.setEntity(movimenti);
	model.addAttribute("movimentiCommand", movimentiCommand);
	prepareView(model, movimentiCommand, request, false);
	setPageAttributes(model);
	setPageAttributes(model, movimenti);
	return "movimenti/form";
    }

    @RequestMapping
    public String dettaglioNotifica(@RequestParam("codicemovimento") Integer codicemovimento, Model model, HttpServletRequest request) {

	PkId id = new PkId(codicemovimento);
	Movimenti movimenti = movimentiService.findById(id);
	NotificaAttivitaRequest narequest = null;
	Oggetti oggettoNotifica = movimenti.getOggettoNotifica();
	if (oggettoNotifica != null) {
	    oggettoNotifica = oggettiService.findById(oggettoNotifica.getId());
	    byte[] content = null;
	    if (oggettoNotifica != null) {
		content = oggettoNotifica.getOggetto();
		if (content != null) {
		    if (content.length > 0) {
			String nars = new String(content);
			narequest = (NotificaAttivitaRequest) Utilities.unMarshallString(nars, NotificaAttivitaRequest.class);
		    }
		}
	    }
	}
	fixRenderEntityProperty(movimenti);
	model.addAttribute("movimento", movimenti);
	model.addAttribute("notificarequest", narequest);
	return "movimenti/dettaglioNotifica";
    }

    private void prepareView(Model model, MovimentiCommand movimentiCommand, HttpServletRequest request, boolean isInsert) {

	Movimenti movimento = movimentiCommand.getEntity();
	String codiceComune = movimento.getIstanza().getComune().getCodicecomune();
	String softwareIstanza = movimento.getIstanza().getSoftware().getCodice();
	model.addAttribute("visualizzaBottoneSbloccaNotifica", Boolean.FALSE);
	if (movimento.getInviatoConStc() != null && movimento.getInviatoConStc().equals(Integer.valueOf(1))) {
	    if (StringUtils.defaultString(movimento.getStatoAttDest()).equalsIgnoreCase("ko")) {
		model.addAttribute("visualizzaBottoneSbloccaNotifica", Boolean.TRUE);
	    }
	}
	boolean isVerticalizzazioneProtocollo = isVerticalizzazioneAttivaPerComune(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE, request, codiceComune);
	if (isVerticalizzazioneProtocollo) {
	    boolean isProtocolloDOCER = false;
	    Verticalizzazioniparametri tipoProtocollo = verticalizzazioniService.getVerticalizzazioniparametriPerComuneESoftware(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_TIPOPROTOCOLLO, codiceComune, softwareIstanza);
	    if (tipoProtocollo != null && tipoProtocollo.getValore().equals(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_DOCER)) {
		isProtocolloDOCER = true;
	    }
	    model.addAttribute("isDocEr", isProtocolloDOCER);
	    Verticalizzazioniparametri vertLeggiProtocollo = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONELEGGI, codiceComune);
	    String valore = "0";
	    if (vertLeggiProtocollo != null) {
		if (StringUtils.isNotBlank(vertLeggiProtocollo.getValore())) {
		    valore = vertLeggiProtocollo.getValore();
		}
	    }
	    model.addAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONELEGGI, valore);
	    
	    
	    String mostraAccettaProtocollo = "0";
	    String erroreTitolo = "";
	    String erroreMessaggio = "";
	    Verticalizzazioniparametri gestAccettazione = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTIONE_ACCETTAZIONE, codiceComune);
	    if (gestAccettazione != null && StringUtils.isNotBlank(gestAccettazione.getValore()) && "1".equals(gestAccettazione.getValore())) {
		
		if("1".equals(model.asMap().get(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONELEGGI+"")) && !StringUtils.isBlank(movimento.getNumeroprotocollo())) {		    
		    try{
			DatiProtocolloLettoResponseType protocolloLetto = protocollazioneService.leggiProtocolloForView(ORMHelper.getToken(),
				movimento);
			
			if(protocolloLetto != null){
			    
			    if(protocolloLetto.getErrore() != null){
				erroreTitolo = "Errore sulla lettura del protocollo";
				if(protocolloLetto.getErrore().getDescrizione() != null){
				    erroreMessaggio = protocolloLetto.getErrore().getDescrizione();
				}else{
				    erroreMessaggio = "Errore sulla lettura del protocollo, guardare i log per i dettagli";
				}
				mostraAccettaProtocollo = "KO";
			    }else if("A".equals(protocolloLetto.getOrigine()) || "I".equals(protocolloLetto.getOrigine())){
				    mostraAccettaProtocollo = "1";
			    }
			    
			}
			
		    }catch(Exception e){
			mostraAccettaProtocollo = "KO";
			erroreTitolo = "Errore sulla lettura del protocollo";
			erroreMessaggio = e + "";
		    }
		    
		    if("1".equals(mostraAccettaProtocollo)){
			try{
			    
			    String token = ORMHelper.getToken();
			    String numeroprotocollo = movimento.getNumeroprotocollo();
			    String annoprotocollo;
			    if (movimento.getDataprotocollo() != null) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(movimento.getDataprotocollo());
				annoprotocollo = calendar.get(Calendar.YEAR) + "";
			    } else {
				annoprotocollo = null;
			    }
			    String idprotocollo = movimento.getFkidprotocollo();
			    
			    log.debug("sto leggendo isEsitato: {idprotocollo: " + idprotocollo + " annoprotocollo: " + annoprotocollo + " numeroprotocollo: " + numeroprotocollo + "}");
			    
			    DatiProtocolloEsitatoResponseType resp = protocollazioneService.isEsitato(token, numeroprotocollo, annoprotocollo, idprotocollo, softwareIstanza, codiceComune);

			    if(EnumEsitatoType.NO == resp.getEsitato()){
				mostraAccettaProtocollo = "1";
			    }else if(EnumEsitatoType.SI == resp.getEsitato()){
				mostraAccettaProtocollo = "0";
			    }else{
				mostraAccettaProtocollo = "KO";
				erroreTitolo = "Errore sul check esito protocollo";
				if(resp.getErrore() != null && resp.getErrore().getDescrizione() != null ){
				    erroreMessaggio = "Non è stato possibile contattare il servizio di protocollazione a causa di=" + new FunzioneBusinessRemotaException(resp.getErrore().getDescrizione());
				}else{
				    erroreMessaggio = "Non è stato possibile contattare il servizio di protocollazione, vedere log per i dettagli";
				}
				
				log.error("IsEsitato, errore su protocollo {idprotocollo: " + idprotocollo + " annoprotocollo: " + annoprotocollo + " numeroprotocollo: " + numeroprotocollo + "}");
				if(resp.getErrore() != null && resp.getErrore().getStackTrace() != null ){
				    log.error("Errore durante la lettura dell'esito protocollo: " + resp.getErrore().getStackTrace() );
				}else{
				    log.error("Errore durante la lettura dell'esito protocollo: stackTrace non definito");
				}
			    }
			    
			}catch(Exception e){
			    mostraAccettaProtocollo = "KO";
			    erroreTitolo = "Errore sul check esito protocollo";
			    erroreMessaggio = e + "";
			    
			    if(movimento != null){
				log.error("IsEsitato, errore su protocollo {idprotocollo: " + movimento.getFkidprotocollo() + " dataprotocollo: " + movimento.getDataprotocollo() + " numeroprotocollo: " + movimento.getNumeroprotocollo() + "}");
			    }
			    log.error("Errore durante la lettura dell'esito protocollo" , e);
			}
		    }
		    
		    
		    
		}
		
	    }
	    model.addAttribute("accettaProtocolloMovimenti", mostraAccettaProtocollo);
	    model.addAttribute("erroreTitoloAccettaProtocollo", erroreTitolo);
	    model.addAttribute("erroreMessaggioAccettaProtocollo",erroreMessaggio);
	    
	    //	    if (tipoProtocollo != null
	    //		    && tipoProtocollo.getValore() != null
	    //		    && (tipoProtocollo.getValore().equals(WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_IRIDE) || tipoProtocollo.getValore().equals(
	    //			    WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_IRIDE2))) {
	    if (tipoProtocollo != null && tipoProtocollo.getValore() != null) {
		//		Verticalizzazioniparametri mostra_metti_alla_firma = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		//			WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_IRIDE, WebConstants.VERTICALIZZAZIONE_PROTOCOLLO_IRIDE_MOSTRA_METTI_ALLA_FIRMA,
		//			codiceComune);
		Verticalizzazioniparametri mostra_metti_alla_firma = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
			VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
			VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_MOSTRA_METTI_ALLA_FIRMA, codiceComune);
		if (mostra_metti_alla_firma != null) {
		    if (StringUtils.defaultIfEmpty(mostra_metti_alla_firma.getValore(), "N").equalsIgnoreCase("S")) {
			model.addAttribute("metti_alla_firma", true);
		    }
		}
	    }
	    Verticalizzazioniparametri vertBottoneStampa = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONESTAMPA, codiceComune);
	    valore = "0";
	    if (vertBottoneStampa != null) {
		if (StringUtils.isNotBlank(vertBottoneStampa.getValore())) {
		    valore = vertBottoneStampa.getValore();
		}
	    }
	    model.addAttribute(VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_VISUALIZZABOTTONESTAMPA, valore);
	    Verticalizzazioniparametri gestisciFascicoloParam = verticalizzazioniService.getVerticalizzazioniparametriPerComune(
		    VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_GESTISCI_FASCICOLAZIONE, codiceComune);
	    boolean gestiscifascicolo = false;
	    if (gestisciFascicoloParam != null) {
		if (StringUtils.defaultIfEmpty(gestisciFascicoloParam.getValore(), "0").equalsIgnoreCase("1")) {
		    gestiscifascicolo = true;
		}
	    }
	    model.addAttribute("gestisciFascicolo", Boolean.valueOf(gestiscifascicolo));
	    //	    if (StringUtils.isNotBlank(movimento.getNumeroprotocollo()) && (movimento.getDataprotocollo() != null)) {
	    //		DatiProtocolloAnnullato datiProtocolloAnnullato = null;
	    //		DatiProtocolloFascicolato datiProtocolloFascicolato = null;
	    //		try {
	    //		    datiProtocolloAnnullato = protocollazioneService.isAnnullato(ORMHelper.getToken(), movimento.getFkidprotocollo(),
	    //			    movimento.getNumeroprotocollo(), movimento.getDataprotocollo());
	    //		    datiProtocolloFascicolato = protocollazioneService.isFascicolato(ORMHelper.getToken(), movimento.getFkidprotocollo(),
	    //			    movimento.getNumeroprotocollo(), movimento.getDataprotocollo());
	    //		} catch (Exception e) {
	    //		    log.error("prepareView: {}", e.getMessage());
	    //		    List<String> warnings = new ArrayList<String>();
	    //		    warnings.add("Non è stato possibile contattare il servizio di protocollazione");
	    //		    FlashMessages.setWarnings(warnings);
	    //		}
	    //		model.addAttribute("datiProtocolloAnnullato", datiProtocolloAnnullato);
	    //		model.addAttribute("datiProtocolloFascicolato", datiProtocolloFascicolato);
	    //	    }
	}
	if (!isInsert) {
	    // verifica DPR160
	    if (movimentiService.isEffettuato(movimento)) {
		boolean isDPR160 = movimentiService.isDPR160(movimento);
		model.addAttribute("isDPR160", isDPR160);
		if (isDPR160) {
		    // gestione allegati DPR160
		    movimentiService.gestioneAllegatiPerComunicazioniTelematiche(movimento);
		    boolean isP7mDPR160Allegato = movimentiService.checkAllegatoFirmatoPerComunicazioniTelematiche(movimento);
		    model.addAttribute("isP7mDPR160Allegato", isP7mDPR160Allegato);
		}
	    }
	}
	Istanze istanza = istanzeService.findById(movimento.getIstanza().getId());
	movimentiCommand.setStatiistanza(istanza.getChiusura());
	if (!isInsert) {
	    boolean isModificaMovimento = movimentiService.isMovimentoModificabile(movimento);
	    model.addAttribute("isModificaMovimento", isModificaMovimento);
	    List<Movimentimail> mails = movimentimailService.findByMovimento(movimento);
	    model.addAttribute("isArchivioMailVisibile", mails.size() > 0);
	} else {
	    model.addAttribute("isModificaMovimento", Boolean.TRUE);
	    model.addAttribute("isArchivioMailVisibile", Boolean.FALSE);
	}
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_STC, request);
	isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_INFOCAMERA, request);
	if (!(isInsert)) {
	    if (EntityUtils.getNestedProperty(movimento, "id.codice") != null) {
		// FIX SE ERRORE LETTURA PROTOCOLLO TRANSAZIONE NON VIENE TRASPORTATA NELLA VIEW E DEVO RILEGGERE
		movimento = movimentiService.findById(movimento.getId());
		movimentiCommand.setEntity(movimento);
		fixRenderEntityProperty(movimento);
		try {
		    MovimentiHelper helper = movimentiService.findCaratteristicheMovimento(movimento);
		    model.addAttribute("movimentiHelper", helper);
		} catch (Exception e) {
		    log.error(e.getMessage());
		}
	    }
	}
	boolean isFileSystemAttivo = isSalvaFileInFileSystem();
	model.addAttribute("isFileSystemAttivo", isFileSystemAttivo);
	// Controllo se nel movimento sono presenti documenti e/o mail, se sono presenti devo imposto nel model 
	// il parametro isDocOrMailPresenti a true. Nella jsp in caso di parametro a true verrà chiesto all'utente
	// l'assunzione di responsabiltà nel cancellare il movimento.
	boolean isDocOrMailPresenti = false;
	List<PecInbox> pecId = null;
	if (!isInsert) {
	    if (movimentiallegatiService.countByMovimento(movimento.getId().getCodice()) > 0
		    || movimentimailService.existsByMovimento(movimento.getId().getCodice())) {
		isDocOrMailPresenti = true;
	    }
	    pecId = pecInboxService.findByMovimento(movimento.getId().getCodice(), 0, 1);
	}
	model.addAttribute("isDocOrMailPresenti", isDocOrMailPresenti);
	model.addAttribute("pecId", pecId);
	// Gestione visualizzazione della funzionalità "Invio email"
	boolean isViewInviaEmail = true;
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE)) {
	    Verticalizzazioniparametri vpInvioEmail = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE,
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_NON_MOSTRARE_INVIOMAIL);
	    if (vpInvioEmail != null && StringUtils.isNotBlank(vpInvioEmail.getValore()) && vpInvioEmail.getValore().equalsIgnoreCase("1")) {
		isViewInviaEmail = false;
	    }
	}
	model.addAttribute("isViewInviaEmail", isViewInviaEmail);
    }

    private java.util.Date getDataMovimentoDaElaborare(Movimenti entity) {

	Verticalizzazioniparametri elaboraMovimentiDopoDataMov = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE,
		WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_ELAB_MOVIMENTI_DOPO_DATA_MOV);
	String elaboraMovimentiDopoDataMovStr = "N";
	if (elaboraMovimentiDopoDataMov != null) {
	    if (StringUtils.isNotBlank(elaboraMovimentiDopoDataMov.getValore())) {
		elaboraMovimentiDopoDataMovStr = elaboraMovimentiDopoDataMov.getValore();
	    }
	}
	if (StringUtils.defaultString(elaboraMovimentiDopoDataMovStr, "N").equalsIgnoreCase("S")) {
	    if (entity != null) {
		return entity.getData();
	    }
	}
	return null;
    }

    @RequestMapping
    public String update(Model model, @ModelAttribute("movimentiCommand") MovimentiCommand movimentiCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	fixMergeEntityProperty(movimentiCommand.getEntity());
	try {
	    movimentiManager.update(movimentiCommand.getEntity());
	    // BOCCI 12-11-2012 SE L'OPERATORE NON PUO' AGGIORNARE LA PRATICA (ESEMPIO OPERATORE CON RUOLO READONLY E WRITE SU MOVIMENTO) ALLORA DA' ERRORE
	    // IN QUESTO CASO NON DOBBIAMO FAR ESEGUIRE LA MODIFICA DELLO STATO ISTANZA E L'ELABORAZIONE
	    Integer codiceIstanza = movimentiCommand.getEntity().getIstanza().getId().getCodice();
	    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	    TipoAccessoEnum tipoaccesso = istanzeService.checkAccessoIstanza(istanza, getCurrentlyAuthenticatedUserDetails());
	    if (tipoaccesso == TipoAccessoEnum.CONSENTITO) {
		String codicestatoattuale = movimentiCommand.getEntity().getIstanza().getChiusura().getId().getCodicestato();
		String nuovoStato = "";
		if (movimentiCommand.getStatiistanza() != null) {
		    if (movimentiCommand.getStatiistanza().getId() != null) {
			nuovoStato = movimentiCommand.getStatiistanza().getId().getCodicestato();
		    }
		}
		MovimentiHelper mh = checkUpdateStatoistanzaGetMovHelper(movimentiCommand, istanza);
		if (mh != null) {
		    if (mh.isRilascioAutorizzazione()) {
			if (!StringUtils.defaultString(codicestatoattuale).equalsIgnoreCase(StringUtils.defaultString(nuovoStato))) {
			    FlashMessages.getWarnings().add(
				    "Il cambiamento di stato dell'istanza ha generato il movimento di chiusura e questo prevede il rilascio dell'atto conclusivo.");
			}
			return "redirect:../autorizzazioni/createAutorizzazione.htm?codiceIstanza=" +
				codiceIstanza +
				"&codiceMovimento=" +
				mh.getMovimento().getId().getCodice() +
				"&codiceStato=" +
				movimentiCommand.getStatiistanza().getId().getCodicestato();
		    }
		}
		Date dataDaElaborare = getDataMovimentoDaElaborare(movimentiCommand.getEntity());
		istanzeService.elabora(codiceIstanza, false, dataDaElaborare);
	    }
	    // BOCCI 12-11-2012
	    MovimentiHelper helper = movimentiService.findCaratteristicheMovimento(movimentiCommand.getEntity());
	    if (helper.isRilascioAutorizzazione()) {
		status.setComplete();
		return "redirect:../autorizzazioni/createAutorizzazione.htm?codiceIstanza=" +
			codiceIstanza +
			"&codiceMovimento=" +
			movimentiCommand.getEntity().getId().getCodice();
	    }
	    if (helper.isEffettuaChiusura()) {
		status.setComplete();
		return "redirect:chiudiIstanza.htm?codice=" + movimentiCommand.getEntity().getId().getCodice();
	    }
	    if (helper.isCdsCommissione()) {
		status.setComplete();
		return listCommissioniDelMovimento(movimentiCommand.getEntity());
	    }
	} catch (Exception e) {
	    copyErrorsToBindingResult(result, movimentiCommand.getEntity(), true, e);
	    prepareView(model, movimentiCommand, request, false);
	    gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_MOVIMENTI_OK_CHIUDI, "1", request);
	    fixRenderEntityProperty(movimentiCommand.getEntity());
	    setPageAttributes(model);
	    setPageAttributes(model, movimentiCommand.getEntity());
	    return "movimenti/form";
	}
	// BOCCI 12/01/2012 QUANDO SALVO UN MOVIMENTO DALLO SCADENZARIO SE AZZERO LO STATUS DA ERRORE
	// status.setComplete();
	// BOCCI 12/01/2012 QUANDO SALVO UN MOVIMENTO DALLO SCADENZARIO SE AZZERO LO STATUS DA ERRORE
	// se true: al salvataggio si reindirizza l'utenete alla lista delle elaborazioni
	// se false: l'utente viene reindirizzato alla pagina di modifica del movimento
	if (movimentiCommand.getSalvaEdEsci()) {
	    return getHistoryBack();
	} else {
	    return "redirect:view.htm?codice=" + movimentiCommand.getEntity().getId().getCodice() + "&status_msg=02";
	}
    }

    private String listCommissioniDelMovimento(Movimenti entity) {

	return "redirect:../commissioniediliziet/listCommissioniDelMovimento.htm?codiceMovimento=" + entity.getId().getCodice();
    }

    @RequestMapping
    public String updateRimuoviNotificaConErrore(Model model, @ModelAttribute("movimentiCommand") MovimentiCommand movimentiCommand,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	try {
	    movimentiService.updateRimuoviNotificaConErrore(movimentiCommand.getEntity().getId().getCodice());
	} catch (Exception e) {
	    copyErrorsToFlashMessages(movimentiCommand.getEntity(), true, "movimenti", e);
	}
	return "redirect:view.htm?codice=" + movimentiCommand.getEntity().getId().getCodice() + "&status_msg=02";
    }

    @RequestMapping
    public String delete(Model model, @ModelAttribute("movimentiCommand") MovimentiCommand movimentiCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Movimenti objToDelete = movimentiService.findById(new PkId(movimentiCommand.getEntity().getId().getCodice()));
	if (objToDelete == null) {
	    throw new RuntimeException("Non è stato trovato il movimento con codice " + movimentiCommand.getEntity().getId());
	}
	String errorCancellazione = checkDeleteMovimento(objToDelete, model, movimentiCommand, result, status, request);
	if (StringUtils.isNotBlank(errorCancellazione)) {
	    return errorCancellazione;
	}
	boolean isMovimentiChiusura = false;
	Integer codiceIstanza = objToDelete.getIstanza().getId().getCodice();
	Movimenti movChiusura = movimentiService.findMovimentoChiusuraIstanza(codiceIstanza);
	if (movChiusura != null) {
	    if (movChiusura.getId().getCodice().equals(objToDelete.getId().getCodice())) {
		isMovimentiChiusura = true;
	    }
	}
	// boolean isEndo = objToDelete.getEndoprocedimento() != null;
	Responsabili userlogged = getCurrentlyAuthenticatedUserDetails();
	String responsabile = userlogged.toString();
	String descrizioneMovimento = objToDelete.toString();
	String descrizioneIstanza = objToDelete.getIstanza().toString();
	Integer codiceistanza = objToDelete.getIstanza().getId().getCodice();
	try {
	    movimentiService.delete(objToDelete);
	    LoggerCancellazioni.logCancellazioneMovimento(responsabile, descrizioneMovimento, descrizioneIstanza);
	    if (isMovimentiChiusura) {
		LoggerCancellazioni.log("Il movimento " +
			descrizioneMovimento +
			" è di chiusura istanza eseguo l'elaborazione e indirizzo l'operatore alla modifica di stato per l'istanza " +
			descrizioneIstanza);
	    }
	    Date dataDaElaborare = getDataMovimentoDaElaborare(movimentiCommand.getEntity());
	    istanzeService.elabora(movimentiCommand.getEntity().getIstanza().getId().getCodice(), false, dataDaElaborare);
	} catch (Exception e) {
	    objToDelete = movimentiService.findById(movimentiCommand.getEntity().getId());
	    if (objToDelete == null) {
		FlashMessages.getWarnings()
			.add("Si è verificato un errore inaspettato nella cancellazione del movimento. Dettaglio:" + e.getMessage());
		return "redirect:listElaborazione.htm?codiceIstanza=" + codiceistanza + "&status_msg=03";
	    }
	    movimentiCommand.setEntity(objToDelete);
	    copyErrorsToBindingResult(result, movimentiCommand.getEntity(), true, e);
	    prepareView(model, movimentiCommand, request, false);
	    fixRenderEntityProperty(movimentiCommand.getEntity());
	    setPageAttributes(model);
	    setPageAttributes(model, objToDelete);
	    return "movimenti/form";
	}
	status.setComplete();
	if (isMovimentiChiusura) {
	    return "redirect:riapriIstanza.htm?codiceIstanza=" + codiceIstanza + "&refMovId=" + movimentiCommand.getEntity().getId().getCodice();
	}
	//	if (isEndo) {
	//	    // nel caso di cancellazione di un movimento dell'endo rieseguo l'elaborazione
	//	    return "redirect:../movimenti/elabora.htm?codiceIstanza=" + codiceistanza;
	//	}
	// altrimenti history back
	return "redirect:../history/back.htm?GoTo=%2F";
    }

    private String checkDeleteMovimento(Movimenti objToDelete, Model model, @ModelAttribute("movimentiCommand") MovimentiCommand movimentiCommand,
	    BindingResult result, SessionStatus status, HttpServletRequest request) {

	String redirect = null;
	boolean existsAllegatiSTC = movimentiallegatiService.existsProvenientiDaSTCPerMovimenti(objToDelete.getId().getCodice());
	boolean existsMovimentiMail = movimentimailService.existsByMovimento(objToDelete.getId().getCodice());
	boolean existsAllegati = (movimentiallegatiService.countByMovimento(objToDelete.getId().getCodice()) > 0 ? true : false);
	if (existsAllegatiSTC || existsMovimentiMail || existsAllegati) {
	    try {
		String errmesg = getMessageFromBundle("service_error.non_e_possibile_cancellare_movimento_con_allegati", null);
		throw new Exception(errmesg);
	    } catch (Exception e) {
		objToDelete = movimentiService.findById(movimentiCommand.getEntity().getId());
		movimentiCommand.setEntity(objToDelete);
		copyErrorsToBindingResult(result, movimentiCommand.getEntity(), true, e);
		prepareView(model, movimentiCommand, request, false);
		fixRenderEntityProperty(movimentiCommand.getEntity());
		setPageAttributes(model);
		setPageAttributes(model, objToDelete);
	    }
	    redirect = "movimenti/form";
	}
	return redirect;
    }

    @RequestMapping
    public String listElaborazione(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	PkId idIstanza = new PkId(codiceIstanza);
	Istanze istanza = new Istanze();
	istanza.setId(idIstanza);
	istanza = istanzeService.bindDomainObject(istanza, PkId.class, "id.codice");
	checkAccessoInformazioni(istanza, false);
	TipoAccessoEnum tipoAccesso = istanzeService.checkAccessoIstanza(istanza, getCurrentlyAuthenticatedUserDetails());
	if (!(tipoAccesso.equals(TipoAccessoEnum.SOLA_LETTURA) || tipoAccesso.equals(TipoAccessoEnum.SOLA_LETTURA_MOVIMENTI_AMM_INTERNA))) {
	    istanzeService.primaElaborazione(istanza);
	    if (istanza.getIstanzeTempistica() == null) {
		istanzeService.calcolaTempisticaIstanza(istanza);
	    }
	}
	List<Movimenti> movimentiEseguitiList = movimentiService.findEseguitiByIstanza(istanza);
	int i = 0;
	for (Movimenti movimento : movimentiEseguitiList) {
	    List<AppIoCodaMovimenti> movimentiCoda = appIoCodaMovimentiService.findByMovimento(movimento.getId().getCodice());
	    if (!movimentiCoda.isEmpty()) {
		movimentiEseguitiList.get(i).setIsCodaIo(true);
	    }
	    i++;
	}
	List<Movimenti> movimentiDaEseguireList = movimentiService.findDaEseguireByIstanza(istanza);
	List<Movimenti> movimentiDisabilitatiList = movimentiService.findDisabilitatiByIstanza(istanza);
	model.addAttribute("istanza", istanza);
	model.addAttribute("movimentiEseguitiList", movimentiEseguitiList);
	model.addAttribute("movimentiDaEseguireList", movimentiDaEseguireList);
	model.addAttribute("isMovimentiDisabilitati", movimentiDisabilitatiList.size() > 0);
	documentazioneAllegatiView(istanza, model);
	gestisciParametroConfigurazioneUtente(WebConstants.CONF_UTENTE_ATTMSGELABORAZIONE, "1", request);
	String codiceComune = istanza.getComune().getCodicecomune();
	listPageAttributes(request, codiceComune, codiceIstanza);
	return "movimenti/listelaborazione";
    }

    @RequestMapping
    public String chiudiIstanza(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request, HttpServletResponse response) {

	PkId idMovimento = new PkId(codice);
	Movimenti movimento = new Movimenti();
	movimento.setId(idMovimento);
	movimento = movimentiService.bindDomainObject(movimento, PkId.class, "id.codice");
	MovimentiCommand command = new MovimentiCommand();
	command.setEntity(movimento);
	command.setDisplayMode(MovimentiCommand.VIEW);
	model.addAttribute("movimentiCommand", command);
	List<Statiistanza> statiistanzaList = statiistanzaService.findBySoftware(ORMHelper.getSoftware());
	model.addAttribute("statiistanzaList", statiistanzaList);
	if (movimento.getEsito() != null) {
	    // recupero gli stati con comportamento chiusura
	    List<Statiistanza> sis = statiistanzaService.findByStatocomportamentoChiuse();
	    //
	    // verifico se il movimento deve impostare uno stato particolare
	    if (movimento.getTipomovimento().getStatoistanza() != null && movimento.getTipomovimento().getStatoistanza().getId() != null) {
		command.setStatiistanza(movimento.getTipomovimento().getStatoistanza());
	    } else {
		for (Statiistanza statiistanza : sis) {
		    // se l'esito del movimento è positivo, propongo il primo stato di chiusura positiva
		    if (movimento.getEsito().booleanValue() && statiistanza.getStaticomportamento().getCodcomportamento().intValue() == 1) {
			command.setStatiistanza(statiistanza);
			break;
		    }
		    // se l'esito del movimento è negativo, propongo il primo stato di chiusura negativa
		    if (!movimento.getEsito().booleanValue() && statiistanza.getStaticomportamento().getCodcomportamento().intValue() == -1) {
			command.setStatiistanza(statiistanza);
			break;
		    }
		}
	    }
	}
	return "movimenti/chiudiIstanza";
    }

    @RequestMapping
    public String chiudiIstanzaUpdate(Model model, @ModelAttribute("movimentiCommand") MovimentiCommand movimentiCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	checkAccessoInformazioni(movimentiCommand.getEntity().getIstanza(), true);
	istanzeService.updateStatoIstanza(movimentiCommand.getEntity().getIstanza(), movimentiCommand.getStatiistanza().getId().getCodicestato());
	istanzeService.elabora(movimentiCommand.getEntity().getIstanza().getId().getCodice(), false);
	status.setComplete();
	return "redirect:../movimenti/listElaborazione.htm?codiceIstanza=" + movimentiCommand.getEntity().getIstanza().getId().getCodice();
    }

    @RequestMapping
    public String riapriIstanza(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("refMovId") Integer refMovId, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	PkId idIstanza = new PkId(codiceIstanza);
	Istanze istanza = istanzeService.findById(idIstanza);
	model.addAttribute("istanza", istanza);
	List<Statiistanza> statiistanzaList = statiistanzaService.findByStatocomportamentoAperte();
	model.addAttribute("statiistanzaList", statiistanzaList);
	LoggerCancellazioni.log("riapriIstanza# utente collegato alla funzionalità di apertura dell'istanza " + istanza);
	return "movimenti/riapriIstanza";
    }

    @RequestMapping
    public String riapriIstanzaUpdate(@RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("refMovId") Integer refMovId,
	    @RequestParam("codiceStato") String codiceStato, Model model, HttpServletRequest request) {

	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	checkAccessoInformazioni(istanza, true);
	istanzeService.updateStatoIstanza(istanza, codiceStato);
	LoggerCancellazioni.log("riapriIstanzaUpdate# modificato lo stato dell'istanza " + istanza);
	istanzeService.elabora(codiceIstanza, false);
	return "redirect:../movimenti/listElaborazione.htm?codiceIstanza=" + codiceIstanza;
    }

    @RequestMapping
    public String elabora(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	PkId idIstanza = new PkId(codiceIstanza);
	Istanze istanza = new Istanze();
	istanza.setId(idIstanza);
	istanza = istanzeService.bindDomainObject(istanza, PkId.class, "id.codice");
	List<String> msgs = new ArrayList<String>();
	try {
	    checkAccessoInformazioni(istanza, true);
	    istanzeService.elabora(istanza.getId().getCodice(), false);
	    msgs.add("Elaborazione avvenuta con successo");
	    FlashMessages.setInfos(msgs);
	} catch (Exception e) {
	    //FIX unificare gestione errori con basecontroller
	    String msg = "Elaborazione non avvenuta correttamente: ";
	    if (e instanceof UncategorizedSQLException) {
		if (e.getMessage() != null && (e.getMessage().indexOf("Connection has timed out") > -1)) {
		    msg += (this.getMessageFromBundle("error.db_connection_timeout", null));
		} else {
		    msg += (e.getMessage());
		}
	    } else {
		msg += (e.getMessage());
	    }
	    msgs.add(msg);
	    FlashMessages.setWarnings(msgs);
	}
	return "redirect:listElaborazione.htm?codiceIstanza=" + codiceIstanza + "&_ts=" + System.currentTimeMillis();
    }

    private void documentazioneAllegatiView(Istanze istanza, Model model) {

	Map<SituazioneAllegato, Integer> situazioniAllegati = documentiHelperService
		.findSituazioneDocumentiIstanzaEdEndo(istanza.getId().getCodice());
	//IstanzeAllegatiControlloHelper helper = istanzeallegatiService.controlloDocumentazione(istanza);
	model.addAttribute("allegatiRichiesti", situazioniAllegati.get(SituazioneAllegato.RICHIESTO));
	model.addAttribute("allegatiPresentati", situazioniAllegati.get(SituazioneAllegato.PRESENTE));
	model.addAttribute("allegatiNonValidi", situazioniAllegati.get(SituazioneAllegato.NON_VALIDO));
	model.addAttribute("allegatiValidi", situazioniAllegati.get(SituazioneAllegato.VALIDO));
	model.addAttribute("situazioniAllegati", situazioniAllegati);
    }

    @RequestMapping
    public String disabilitaMovimento(@RequestParam("codice") Integer codice, Model model, HttpServletRequest request) {

	PkId id = new PkId(codice);
	Movimenti movimenti = movimentiService.findById(id);
	Integer codiceIstanza = movimenti.getIstanza().getId().getCodice();
	String status_msg = "05";
	try {
	    movimentiService.disabilitaMovimento(movimenti);
	} catch (Exception e) {
	    log.error("disabilitaMovimento: {}", e.getMessage());
	    if (e instanceof BaseValidationException) {
		List<InvalidValue> ivs = ((BaseValidationException) e).getInvalidValues();
		if (ivs != null && ivs.size() > 0) {
		    for (InvalidValue iv : ivs) {
			log.error("disabilitaMovimento: {}", getMessageFromBundle(iv.getMessage(), new Object[] { iv.getValue() }));
		    }
		}
	    }
	}
	return "redirect:listElaborazione.htm?codiceIstanza=" + codiceIstanza + "&status_msg=" + status_msg;
    }

    @RequestMapping
    public String riattiva(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceMovimentoDisabilitato", required = false) Integer[] codiceMovimentoDisabilitato, Model model,
	    HttpServletRequest request) {

	if (codiceMovimentoDisabilitato != null) {
	    for (Integer integer : codiceMovimentoDisabilitato) {
		Movimenti mov = new Movimenti();
		mov.getId().setCodice(integer);
		mov = movimentiService.bindDomainObject(mov, PkId.class, "id.codice");
		movimentiService.abilitaMovimento(mov);
	    }
	}
	return "redirect:listElaborazione.htm?codiceIstanza=" + codiceIstanza;
    }

    @RequestMapping
    public String ajaxListaTipimovimentoDisabilitati(@RequestParam("codiceIstanza") Integer codiceIstanza, Model model, HttpServletRequest request) {

	Istanze istanza = new Istanze();
	istanza.getId().setCodice(codiceIstanza);
	istanza = istanzeService.bindDomainObject(istanza, PkId.class, "id.codice");
	List<Movimenti> movimentidiabilitati = movimentiService.findDisabilitatiByIstanza(istanza);
	model.addAttribute("movimentidiabilitati", movimentidiabilitati);
	return "movimenti/listadisabilitati";
    }

    @RequestMapping
    public String ajaxDettaglioMovimentoDisabilitato(@RequestParam("codice") Integer codiceMovimento, Model model, HttpServletRequest request) {

	Movimenti movimento = new Movimenti();
	movimento.getId().setCodice(codiceMovimento);
	movimento = movimentiService.bindDomainObject(movimento, PkId.class, "id.codice");
	model.addAttribute("mov", movimento);
	boolean isEffettuato = movimentiService.isEffettuato(movimento);
	model.addAttribute("isEffettuato", Boolean.valueOf(isEffettuato));
	return "movimenti/ajaxDettaglioMovimentoDisabilitato";
    }

    @RequestMapping
    public String ajaxListaMovimentiContromovimenti(@RequestParam("codiceMovimento") Integer codiceMovimento, @RequestParam("tipo") String tipo,
	    Model model, HttpServletRequest request) {

	Movimenti movimento = new Movimenti();
	movimento.getId().setCodice(codiceMovimento);
	movimento = movimentiService.bindDomainObject(movimento, PkId.class, "id.codice");
	Set<MovimentiContromovimenti> contromovimentis = null;
	if (tipo.equals("figlio")) {
	    contromovimentis = movimento.getMovimentiContromovimentisForFkPadre();
	} else {
	    contromovimentis = movimento.getMovimentiContromovimentisForFkFiglio();
	}
	model.addAttribute("movimento", movimento);
	model.addAttribute("movimentiList", contromovimentis);
	model.addAttribute("tipo", tipo);
	return "movimenti/listamovimenticontromovimenti";
    }

    @RequestMapping
    public void ajaxUpdateProperty(@RequestParam("codice") Integer codice, @RequestParam("propertyToUpdate") String propertyToUpdate, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	Movimenti mov = movimentiService.findById(new PkId(codice));
	if (StringUtils.defaultIfEmpty(propertyToUpdate, "").equalsIgnoreCase("inviatoConStc")) {
	    Integer value = mov.getInviatoConStc();
	    if (value == null) {
		value = MovimentiService.STC_DISATTIVATO_DA_OPERATORE;
	    }
	    if (value.equals(MovimentiService.STC_DISATTIVATO_DA_OPERATORE)) {
		value = MovimentiService.STC_NON_INVIATO;
	    } else {
		value = MovimentiService.STC_DISATTIVATO_DA_OPERATORE;
	    }
	    movimentiService.updateIntegerProperty(codice, propertyToUpdate, value);
	} else if (StringUtils.defaultIfEmpty(propertyToUpdate, "").equalsIgnoreCase("flagDaLeggere")) {
	    Boolean value = mov.getFlagDaLeggere();
	    if (value != null) {
		if (value.booleanValue()) {
		    value = Boolean.FALSE;
		}
	    }
	    movimentiService.updateBooleanProperty(codice, propertyToUpdate, value);
	}
	response.getWriter().write(getMessageFromBundle("label.datoaggiornato", null));
    }

    @RequestMapping
    public String updateProperty(@RequestParam("codice") Integer codice, @RequestParam("propertyToUpdate") String propertyToUpdate, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	Movimenti mov = movimentiService.findById(new PkId(codice));
	if (StringUtils.defaultIfEmpty(propertyToUpdate, "").equalsIgnoreCase("inviatoConStc")) {
	    Integer value = mov.getInviatoConStc();
	    if (value == null) {
		value = MovimentiService.STC_DISATTIVATO_DA_OPERATORE;
	    }
	    if (value.equals(MovimentiService.STC_DISATTIVATO_DA_OPERATORE)) {
		value = MovimentiService.STC_NON_INVIATO;
	    } else {
		value = MovimentiService.STC_DISATTIVATO_DA_OPERATORE;
	    }
	    movimentiService.updateIntegerProperty(codice, propertyToUpdate, value);
	} else if (StringUtils.defaultIfEmpty(propertyToUpdate, "").equalsIgnoreCase("flagDaLeggere")) {
	    Boolean value = mov.getFlagDaLeggere();
	    if (value != null) {
		if (value.booleanValue()) {
		    value = Boolean.FALSE;
		}
	    }
	    movimentiService.updateBooleanProperty(codice, propertyToUpdate, value);
	}
	return getHistoryBack();
    }

    @RequestMapping
    public String riattivanotificaSTC(Model model, @RequestParam("codice") Integer codice, HttpServletRequest request) {

	try {
	    movimentiService.updateIntegerProperty(codice, "inviatoConStc", MovimentiService.STC_NON_INVIATO);
	} catch (Exception e) {
	    return "redirect:view.htm?codice=" + codice + "&status_msg=03";
	}
	//return "redirect:view.htm?codice=" + codice + "&status_msg=02";
	return getHistoryBack();
    }

    @RequestMapping
    public String associaEnteDestinatario(Model model, @RequestParam("codiceMovimento") Integer codiceMovimento, HttpServletRequest request) {

	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	if (movimento == null) {
	    throw new RuntimeException("Il movimento con Id(" + codiceMovimento + ") non è presente nel sistema[" + ORMHelper.getIdcomune() + "]");
	}
	boolean isModificabile = movimentiService.isMovimentoModificabile(movimento);
	if (isModificabile == false) {
	    throw new RuntimeException("Il movimento non è modificabile. Rif[" + codiceMovimento + "," + ORMHelper.getIdcomune() + "]");
	}
	checkAccessoMovimento(movimento);
	if (!EntityUtils.isNestedPropertyBlank(movimento.getAmministrazioniStc(), "id.codice")) {
	    return "redirect:../stc/createNotifica.htm?codiceMovimento=" + movimento.getId().getCodice();
	}
	MovimentiCommand movimentiCommand = new MovimentiCommand();
	movimentiCommand.setEntity(movimento);
	List<TipimovStcMapping> mappings = tipimovStcMappingService
		.findByTipimovimento(new TipimovimentoId(movimento.getTipomovimento().getId().getTipomovimento()));
	List<Amministrazioni> amministrazionis = new ArrayList<Amministrazioni>();
	Integer codiceAmministrazioneMovimento = null;
	if (movimento.getAmministrazioni() != null) {
	    if (movimento.getAmministrazioni().getId() != null) {
		codiceAmministrazioneMovimento = movimento.getAmministrazioni().getId().getCodice();
	    }
	}
	String codiceComune = movimento.getIstanza().getComune().getCodicecomune();
	List<Amministrazioni> amms = amministrazioniService.findByCodicecomune(codiceComune, 0, 2);// cerco le amministrazioni configurate con codice comune. 
												   // nel caso di regione che deve notificare a più enti / comuni e potrebbe sbagliare
												   // ad individuarla la proponiamo
	Integer codiceAmministrazionePratica = null;
	if (amms.size() == 1) {
	    codiceAmministrazionePratica = amms.get(0).getId().getCodice();
	}
	Integer codiceAmministrazioneMapping = null;
	for (TipimovStcMapping mapp : mappings) {
	    if (codiceAmministrazioneMovimento != null) {
		if (codiceAmministrazioneMovimento.equals(mapp.getAmministrazioni().getId().getCodice())) {
		    codiceAmministrazioneMapping = codiceAmministrazioneMovimento;
		}
	    }
	    if (codiceAmministrazioneMapping == null && codiceAmministrazionePratica != null) {
		codiceAmministrazioneMapping = codiceAmministrazionePratica;
	    }
	    Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(mapp.getAmministrazioni().getId().getCodice()));
	    amministrazionis.add(amministrazioni);
	}
	movimentiService.evict(movimento);
	if (codiceAmministrazioneMapping != null) {
	    Amministrazioni ammStc = amministrazioniService.findById(new PkId(codiceAmministrazioneMapping));
	    if (ammStc != null) {
		movimento.setAmministrazioniStc(ammStc);
	    }
	}
	if (EntityUtils.isNestedPropertyBlank(movimentiCommand.getEntity(), "amministrazioniStc")) {
	    movimento.setAmministrazioniStc(new Amministrazioni());
	}
	model.addAttribute("movimento", movimento);
	model.addAttribute("amministrazionis", amministrazionis);
	model.addAttribute("movimentiCommand", movimentiCommand);
	return "movimenti/scegliEnteDestinatario";
    }

    @RequestMapping
    public String scegliEnteTerzoUpdate(Model model, @ModelAttribute("movimentiCommand") MovimentiCommand movimentiCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Movimenti movimento = movimentiService.findById(movimentiCommand.getEntity().getId());
	if (movimento == null) {
	    throw new RuntimeException("Non è stato trovato il movimento con codice " + movimentiCommand.getEntity().getId());
	}
	Integer codiceAmministrazione = movimentiCommand.getEntity().getAmministrazioniStc().getId().getCodice();
	Amministrazioni amministrazioniStc = amministrazioniService.findById(new PkId(codiceAmministrazione));
	movimento.setAmministrazioniStc(amministrazioniStc);
	if (amministrazioniStc == null) {
	    throw new RuntimeException("Non è stato trovata l'amministrazione con codice " + codiceAmministrazione);
	}
	try {
	    movimentiService.updateAmministrazioniStc(movimento.getId().getCodice(), codiceAmministrazione);
	} catch (Exception e) {
	    movimento = movimentiService.findById(movimentiCommand.getEntity().getId());
	    movimentiCommand.setEntity(movimento);
	    copyErrorsToBindingResult(result, movimentiCommand.getEntity(), true, e);
	    prepareView(model, movimentiCommand, request, false);
	    fixRenderEntityProperty(movimentiCommand.getEntity());
	    setPageAttributes(model);
	    setPageAttributes(model, movimento);
	    List<TipimovStcMapping> mappings = tipimovStcMappingService
		    .findByTipimovimento(new TipimovimentoId(movimento.getTipomovimento().getId().getTipomovimento()));
	    List<Amministrazioni> amministrazionis = new ArrayList<Amministrazioni>();
	    for (TipimovStcMapping mapp : mappings) {
		Amministrazioni amministrazioni = amministrazioniService.findById(new PkId(mapp.getAmministrazioni().getId().getCodice()));
		amministrazionis.add(amministrazioni);
	    }
	    model.addAttribute("movimento", movimento);
	    model.addAttribute("amministrazionis", amministrazionis);
	    if (EntityUtils.isNestedPropertyBlank(movimentiCommand.getEntity(), "amministrazioniStc")) {
		movimentiCommand.getEntity().setAmministrazioniStc(new Amministrazioni());
	    }
	    return "movimenti/scegliEnteDestinatario";
	}
	return "redirect:../stc/createNotifica.htm?codiceMovimento=" + movimento.getId().getCodice();
    }

    @RequestMapping
    public String cambiaAmministrazioneStc(@RequestParam("codiceMovimento") Integer codiceMovimento, Model model, HttpServletRequest request) {

	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	if (movimento == null) {
	    throw new RuntimeException("Non è stato trovato il movimento con codice " + codiceMovimento);
	}
	movimento.setAmministrazioniStc(null);
	String statusCode = "02";
	try {
	    movimentiService.update(movimento);
	} catch (Exception e) {
	    statusCode = "03";
	    FlashMessages.getWarnings().add("Non è stato possibile cambiare l'amministrazione a causa di un errore di sistema: " + e.getMessage());
	}
	return "redirect:../movimenti/view.htm?codice=" + movimento.getId().getCodice() + "&status_msg=" + statusCode;
    }

    /**
     * 
     * <pre>
     * Chiamta ajax che popola e richiama la pagina createSearchDocumentiAllegati.jsp che mostra le lettere tipo da stampare:
     * 
     *  CASO A: il tipo movivimento ha uno o più lettere tipo configurate allora verrà mostrata la lista di queste con l'opportunità di passare
     *   	Alla ricerca ajax di tutte le lettere tipo del software corrente o TT
     *  CASO B: il tipo movivimento non ha  lettere tipo configurate allora verrà mostrata la ricerca ajax di tutte le lettere tipo del software corrente o TT 
     * &#64;param codiceMovimento
     * &#64;param codiceTipomovimento
     * &#64;param model
     * &#64;param response
     * &#64;return
     * &#64;throws IOException
     * 
     * </pre>
     */
    @RequestMapping
    public String ajaxCreateRicercaLettereTipo(@ModelAttribute("movimentiCommand") MovimentiCommand movimentiCommand, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	// Movimenti movimento = movimentiService.findById(new PkId());
	Tipimovimento tipimovimento = tipiMovimentoService
		.findById(new TipimovimentoId(movimentiCommand.getEntity().getTipomovimento().getId().getTipomovimento()));
	response.setContentType("text/plain");
	boolean isAttiva = isVerticalizzazioneAttiva(WebConstants.VERTICALIZZAZIONE_ALLEGATI_PEC, request);
	model.addAttribute("isAttiva", isAttiva);
	model.addAttribute("movimentiCommand", movimentiCommand);
	model.addAttribute("tipimovimento", tipimovimento);
	boolean isSalvaFileInFileSystem = isSalvaFileInFileSystem();
	model.addAttribute("isSalvaFileInFileSystem", isSalvaFileInFileSystem);
	return "movimenti/createSearchDocumentiAllegati";
    }

    /**
     * <pre>
     * Crea l'allegato al movimento con una chiamata a una pagnina ASP, la pagina asp ritorna una pagina contenete solo
     * il codice dell'allegato che ha creato. Il codice sarà utilizzato per richiamare la funzionalità che avvia la
     * applet per la gestione del saltataggio inline dei documenti
     * 
     * &#64;param codiceDocumento
     * &#64;param codiceIstanza
     * &#64;param codiceMovimento
     * &#64;param tipoMovimento
     * &#64;param model
     * &#64;param request
     * &#64;param response
     * &#64;return
     * &#64;throws IOException
     * </pre>
     */
    @Autowired
    private DocumentMergeService documentMergeService;

    @RequestMapping
    public String createLettereTipo(@RequestParam("codiceDocumento") Integer codiceDocumento, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam("codiceMovimento") Integer codiceMovimento, @RequestParam("tipoMovimento") String tipoMovimento, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	documentMergeService.insertAllegatoDaDocumentoTipo(codiceDocumento, codiceIstanza, codiceMovimento, new DocumentMergeHelper());
	return "";
    }

    @RequestMapping
    public String ajaxCreateLettereTipo(@RequestParam("codiceDocumento") Integer codiceDocumento,
	    @RequestParam("codiceIstanza") Integer codiceIstanza, @RequestParam("codiceMovimento") Integer codiceMovimento,
	    @RequestParam("tipoMovimento") String tipoMovimento, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException, MovimentiAllegatiResolverException {

	DocumentMergeHelper dh = populateFromRequest(request);
	Movimentiallegati allegato = this.movimentiAllegatiResolverFactoryService.build(dh, codiceMovimento, codiceDocumento);
	request.setAttribute("codiceAllegato", allegato.getOggetto().getId().getCodice());
	if (isSalvaFileInFileSystem()) {
	    return "redirect:../file/ajaxDownload.htm?fileId=" + allegato.getOggetto().getId().getCodice();
	}
	return "movimenti/viewOrModify";
	/*
	Letteretipo letteretipo = letteretipoService.findById(new PkId(codiceDocumento));
	// Serve la IF perchè creiamo il documento con due tecnologie differenti, se anche quello rtf sarà creato in java non servirà più il 
	// discriminare,verrà fatto già dentro "createAllegatoDaDocumentoTipo"
	DocumentMergeHelper dh = populateFromRequest(request);
	if (letteretipo.getFile().getNomefile().toLowerCase().endsWith(".odt")) {
	    Oggetti oggetto = documentMergeService.createAllegatoDaDocumentoTipo(codiceDocumento, codiceIstanza, codiceMovimento, dh);
	    Movimentiallegati movimentiallegati = new Movimentiallegati();
	    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	    movimentiallegati.setMovimento(movimento);
	    movimentiallegati.setOggetto(oggetto);
	    movimentiallegati.setDescrizione(letteretipo.getDescrizione());
	    movimentiallegatiService.insert(movimentiallegati);
	    // Chiamo la applet per la gestionedei file
	    request.setAttribute("codiceAllegato", oggetto.getId().getCodice());
	    if (isSalvaFileInFileSystem()) {
		return "redirect:../file/ajaxDownload.htm?fileId=" + oggetto.getId().getCodice();
	    }
	} else if (letteretipo.getFile().getNomefile().toLowerCase().endsWith(".rtf")) {
	    boolean paginaStampaDocTipoJava = this.verticalizzazioneTipoInstallazioneService.isAttiva()
		    && TecnologiaPaginaEnum.JAVA.equals(this.verticalizzazioneTipoInstallazioneService.paginaStampeDocTipo());
	    if (paginaStampaDocTipoJava) {
		Oggetti oggetto = documentMergeService.createAllegatoDaDocumentoTipo(codiceDocumento, codiceIstanza, codiceMovimento, dh);
		Movimentiallegati movimentiallegati = new Movimentiallegati();
		Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
		movimentiallegati.setMovimento(movimento);
		movimentiallegati.setOggetto(oggetto);
		movimentiallegati.setDescrizione(letteretipo.getDescrizione());
		movimentiallegatiService.insert(movimentiallegati);
		// Chiamo la applet per la gestionedei file
		request.setAttribute("codiceAllegato", oggetto.getId().getCodice());
		if (isSalvaFileInFileSystem()) {
		    return "redirect:../file/ajaxDownload.htm?fileId=" + oggetto.getId().getCodice();
		}
	    } else {
		// Creo l'URL della chiamata alla pagina ASP
		String urlcreaallegato = documentMergeService.getUrlGeneraAllegato() + "?codiceDocumento=" + codiceDocumento + "&codiceIstanza=" +
					 codiceIstanza + "&codiceMovimento=" + codiceMovimento + "&TipoMovimento=" + tipoMovimento + "&" +
					 WebConstants.SOFTWARE + "=" + ORMHelper.getSoftware() + "&" + WebConstants.TOKEN + "=" +
					 ORMHelper.getToken();
		// Recupero tramite HTTP cliet il contenuto della pagina e lo metto su uno stream	
		HttpClient cli = new HttpClient();
		HttpMethod method = null;
		int status = 0;
		try {
		    method = new GetMethod(urlcreaallegato);
		    status = cli.executeMethod(method);
		} catch (Exception e) {
		    response.setStatus(500);
		    response.getOutputStream()
			    .print("<b>Si e' verificato un errore nella stampa del documento contattare l'assistenza</b>.<p /> " +
				   "<i style=\"color: red\">[Funzionalita': MOVIMENTI.ajaxCreateLettereTipo,Dettaglio errore: " + e + "]</i> ");
		    return null;
		}
		InputStream inputStream = method.getResponseBodyAsStream();
		// Trasformo lo stream in una stringa (conterrà solo il codice)
		//InputStream inputStream = method.getResponseBodyAsStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
		    sb.append(line);
		}
		inputStream.close();
		String co = sb.toString();
		if (status == 200) {
		    // Chiamo la applet per la gestionedei file
		    Integer codiceOggetto = Integer.parseInt(co);
		    documentMergeService.verificaConvertiRtfInOdt(codiceOggetto, true);
		    request.setAttribute("codiceAllegato", codiceOggetto);
		    if (isSalvaFileInFileSystem()) {
			return "redirect:../file/ajaxDownload.htm?fileId=" + codiceOggetto;
		    }
		    //return "redirect:../file/editDocApplet.htm?fileId=" + codice + "&func=closeEditDocs";
		}
		if (status == 500) {
		    response.setStatus(500);
		    response.getOutputStream().print(sb.toString());
		    return null;
		}
	    }
	} else {
	    Oggetti template = oggettiService.findById(new PkId(letteretipo.getFile().getId().getCodice()));
	    Movimentiallegati movimentiallegati = new Movimentiallegati();
	    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	    movimentiallegati.setMovimento(movimento);
	    Oggetti oggetto = new Oggetti();
	    oggetto.setOggetto(template.getOggetto());
	    oggetto.setNomefile(template.getNomefile());
	    oggettiService.insert(oggetto);
	    movimentiallegati.setOggetto(oggetto);
	    movimentiallegati.setDescrizione(letteretipo.getDescrizione());
	    movimentiallegatiService.insert(movimentiallegati);
	    request.setAttribute("codiceAllegato", oggetto.getId().getCodice());
	    if (isSalvaFileInFileSystem()) {
		return "redirect:../file/ajaxDownload.htm?fileId=" + oggetto.getId().getCodice();
	    }
	}
	return "movimenti/viewOrModify";
	*/
    }

    @SuppressWarnings("unchecked")
    private DocumentMergeHelper populateFromRequest(HttpServletRequest request) {

	DocumentMergeHelper dmh = new DocumentMergeHelper();
	Map<String, String[]> allMap = request.getParameterMap();
	for (String key : allMap.keySet()) {
	    String valore = request.getParameter(key);
	    dmh.getParams().put(key.toUpperCase(), valore);
	}
	return dmh;
    }

    @RequestMapping
    public void ajaxTestCreaGMT(@RequestParam("codiceDocumento") Integer codiceDocumento, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	DocumentMergeHelper userData = populateFromRequest(request);
	byte[] re = documentMergeService.eseguiSostituzioniBaseDocumento(codiceDocumento, null, null, userData);
	response.setHeader("Pragma", "public");
	response.setHeader("Cache-Control", "max-age=0");
	response.setHeader("Content-Disposition", "attachment; filename=\"test.rtf");
	response.setHeader("Content-transfer-encoding", "binary");
	response.setContentType("application/rtf");
	response.setContentLength(re.length);
	ServletOutputStream out = response.getOutputStream();
	out.write(re);
	out.flush();
    }

    @RequestMapping
    public String ajaxModificaAllegatoCreato(@RequestParam("codiceDocumento") Integer codiceDocumento, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	return "redirect:../file/editDocApplet.htm?fileId=" + codiceDocumento + "&func=closeEditDocs";
    }

    @RequestMapping
    public String prepareCreateDocumentoConLink(@RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam("codiceMovimento") Integer codiceMovimento, Model model, HttpServletRequest request, HttpServletResponse response) {

	PkId idIstanza = new PkId(codiceIstanza);
	Istanze istanza = istanzeService.findById(idIstanza);
	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	DocumentiHelper documentiHelper = documentiHelperService.findDocumentiInvioMailDaMovimento(codiceMovimento);
	MovimentiCommand command = new MovimentiCommand();
	command.setDocumentiHelper(documentiHelper);
	command.setEntity(movimento);
	Tipimovimento tipimovimento = movimento.getTipomovimento();
	if (EntityUtils.getNestedProperty(tipimovimento.getLetteraTipoAllegati(), "id.codice") != null) {
	    command.setLetteretipo(tipimovimento.getLetteraTipoAllegati());
	} else {
	    command.setLetteretipo(new Letteretipo());
	}
	List<Tipimovimentodoctipo> docConfiguratiPerMov = tipimovimentodoctipoService
		.findByTipoMovimento(movimento.getTipomovimento().getId().getTipomovimento());
	Boolean isZipLogicoDocumentoAllegato = this.movimentiZipLogicoService.isZipLogicoDocumentoAllegato(codiceMovimento);
	model.addAttribute("isDocumentoZipLogicoCollegato", isZipLogicoDocumentoAllegato);
	model.addAttribute("istanza", istanza);
	model.addAttribute("movimentiCommand", command);
	model.addAttribute("ifZipLogicoExist", ifZipLogicoExist(movimento.getId().getCodice()));
	model.addAttribute("docConfiguratiPerMov", docConfiguratiPerMov);
	return "movimenti/prepareCreateDocumentoConLink";
    }

    @RequestMapping
    public String createDocumentoConLink(Model model, @ModelAttribute("movimentiCommand") MovimentiCommand movimentiCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	Integer codiceOggettoMovimento = null;
	try {
	    if (EntityUtils.getNestedProperty(movimentiCommand.getLetteretipo(), "id.codice") != null) {
		Integer codiceLetteraTipo = movimentiCommand.getLetteretipo().getId().getCodice();
		codiceOggettoMovimento = movimentiallegatiService.createDocumentoConLink(movimentiCommand.getEntity(), codiceLetteraTipo,
			movimentiCommand.getDocumentiHelper(), movimentiCommand.getFlgZipLogico());
	    } else {
		List<String> warnings = new ArrayList<String>();
		warnings.add("Deve essere selezionata una lettera tipo");
		FlashMessages.setWarnings(warnings);
	    }
	} catch (Exception e) {
	    copyErrorsToFlashMessages(movimentiCommand.getEntity(), true, "entity", e);
	    PkId idIstanza = new PkId(movimentiCommand.getEntity().getIstanza().getId().getCodice());
	    Istanze istanza = istanzeService.findById(idIstanza);
	    model.addAttribute("istanza", istanza);
	    model.addAttribute("movimentiCommand", movimentiCommand);
	    return "movimenti/prepareCreateDocumentoConLink";
	}
	String uriBackEncoded = "../movimenti/view.htm?codice=" + movimentiCommand.getEntity().getId().getCodice();
	String uriToEncoded = "../movimentiallegati/view.htm?codice=" + codiceOggettoMovimento;
	String historySetUrl = "../history/set.htm?ReturnTo=" + uriBackEncoded + "&" + WebConstants.GOTO + "=" + uriToEncoded;
	//return "redirect:../movimentiallegati/view.htm?codice=" + codiceOggettoMovimento;
	return "redirect:" + historySetUrl;
    }

    /**
     * Controlla se sono attive tutte le condizione per cui i file si trovano su file system possono essere salvati
     * direttamente
     * 
     * @return
     */
    private boolean isSalvaFileInFileSystem() {

	boolean _isSalvaFileInFileSystem = false;
	boolean isFileSystemAttiva = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_FILESYSTEM, ORMHelper.getSoftware());
	// Se è attiva allo controllo i parametri se sono attivi
	if (isFileSystemAttiva) {
	    Verticalizzazioniparametri verticalizzazioniparametri_READONLY = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_FILESYSTEM, WebConstants.VERTICALIZZAZIONE_FILESYSTEM_READONLY, ORMHelper.getSoftware());
	    Verticalizzazioniparametri verticalizzazioniparametri_SHAREDPAT = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_FILESYSTEM, WebConstants.VERTICALIZZAZIONE_FILESYSTEM_SHAREDPATH, ORMHelper.getSoftware());
	    // Controllo se non configurati entrambi i parametri
	    if (verticalizzazioniparametri_READONLY != null && verticalizzazioniparametri_SHAREDPAT != null) {
		// Devo verificare che verticalizzazioniparametri_READONLY sia null o 0 e che verticalizzazioniparametri_SHAREDPAT sia diverso da null
		if ((StringUtils.isBlank(verticalizzazioniparametri_READONLY.getValore())
			|| verticalizzazioniparametri_READONLY.getValore().equals("0"))
			&& (StringUtils.isNotBlank(verticalizzazioniparametri_SHAREDPAT.getValore()))) {
		    _isSalvaFileInFileSystem = true;
		}
	    }
	}
	return _isSalvaFileInFileSystem;
    }

    @Override
    protected void fixMergeEntityProperty(Movimenti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Movimenti entity) {

	if (EntityUtils.getNestedProperty(entity.getAmministrazioni(), "id.codice") == null) {
	    entity.setAmministrazioni(new Amministrazioni());
	}
	if (EntityUtils.getNestedProperty(entity.getAmministrazioniStc(), "id.codice") == null) {
	    entity.setAmministrazioniStc(new Amministrazioni());
	}
	if (EntityUtils.getNestedProperty(entity.getEndoprocedimento(), "id.codice") == null) {
	    entity.setEndoprocedimento(new Inventarioprocedimenti());
	}
	if (EntityUtils.getNestedProperty(entity.getTipomovimento(), "id.tipomovimento") == null) {
	    entity.setTipomovimento(new Tipimovimento());
	}
	if (EntityUtils.getNestedProperty(entity.getAmministrazionireferenti(), "id.codice") == null) {
	    entity.setAmministrazionireferenti(new Amministrazionireferenti());
	}
	if (entity.getOggettoNotifica() == null) {
	    entity.setOggettoNotifica(new Oggetti());
	}
    }

    @Override
    protected void setPageAttributes(Model model) {

	List<Tipicausalioneri> tipicausalionerilist = tipicausalioneriService.findByDescrizioneAndFlagEndo(null, Boolean.TRUE, null);
	model.addAttribute("tipicausalionerilist", tipicausalionerilist);
    }

    private void setPageAttributes(Model model, Movimenti movimento) {

	Integer codiceIstanza = movimento.getIstanza().getId().getCodice();
	if (movimentiNoSecurityService.findMovimentoChiusuraIstanza(codiceIstanza) == null) {
	    List<Statiistanza> statiistanzaList = statiistanzaService.findAll(null, null); //findByStatocomportamentoAperte();
	    model.addAttribute("statiistanzaList", statiistanzaList);
	}
	Verticalizzazioniparametri vertNonProporreData = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE, WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_MOV_NON_PROPORRE_DATA);
	String nonProporreData = "N";
	if (vertNonProporreData != null) {
	    if (StringUtils.isNotBlank(vertNonProporreData.getValore())) {
		nonProporreData = vertNonProporreData.getValore();
	    }
	}
	model.addAttribute("isNonProponiData", StringUtils.defaultString(nonProporreData, "N").equalsIgnoreCase("S"));
	model.addAttribute("PROTOCOLLO_READONLY", Boolean.FALSE);
	boolean canDelete = true;
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE)) {
	    Verticalizzazioniparametri vp = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE,
		    WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_DIS_CANC_MOV_PROTOCOLLATI);
	    if (vp != null && StringUtils.defaultIfEmpty(vp.getValore(), "0").trim().equalsIgnoreCase("1")) {
		canDelete = false;
	    }
	}
	if (!canDelete) {
	    if (StringUtils.isNotBlank(movimento.getNumeroprotocollo())) {
		model.addAttribute("PROTOCOLLO_READONLY", Boolean.TRUE);
	    }
	}
	//	Posso modificare la data del movimento?
	//	No se:
	//	    - Movimento Avvio
	//	    - Movimento Notificato tramite STC
	//	Si se:
	//	    - Non è movimento di Avvio
	//	    - Se non notificato da STC
	//	    -- Se notificato da STC ma regola VERTICALIZZAZIONE_STC_DIS_MOD_DATA_NOTIFICA = 1
	// Se Movimento di avvio viene decido nell'oggetto di MovimentiHelper messo nella request prima
	Boolean readOnlyMovStc = movimento.getInviatoConStc() == null ? Boolean.FALSE : movimento.getInviatoConStc() > 0;
	if (readOnlyMovStc) {
	    boolean isAttivaVerticalizzazioneSTC = verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC);
	    if (isAttivaVerticalizzazioneSTC) {
		Verticalizzazioniparametri parametroDisabiltaModificaData = verticalizzazioniService
			.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC, WebConstants.VERTICALIZZAZIONE_STC_DIS_MOD_DATA_NOTIFICA);
		if (parametroDisabiltaModificaData != null
			&& StringUtils.defaultIfEmpty(parametroDisabiltaModificaData.getValore(), "0").trim().equalsIgnoreCase("1")) {
		    readOnlyMovStc = false;
		}
	    }
	}
	model.addAttribute("readOnlyMovStc", Boolean.valueOf(readOnlyMovStc));
	Boolean ifZipLogicoExist = movimentiZipLogicoService.isZipLogicoExistInMovimento(movimento.getId().getCodice());
	model.addAttribute("ifZipLogicoExists", ifZipLogicoExist);
    }

    private Boolean ifZipLogicoExist(Integer codicemovimento) {

	return this.movimentiZipLogicoService.isZipLogicoExistInMovimento(codicemovimento);
    }

    @RequestMapping
    public String ajaxSezioneAllegati(@RequestParam("codicemovimento") Integer codicemovimento, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	List<MovimentiallegatiDTO> allegati = movimentiallegatiService.findMovimentiallegatiDTOByMovimenti(codicemovimento);
	model.addAttribute("allegati", allegati);
	return "movimenti/ajaxSezioneAllegati";
    }

    @RequestMapping
    public String ajaxSezioneInfoAttivitaTipoMovimento(@RequestParam("codicemovimento") Integer codicemovimento, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	Movimenti mov = movimentiNoSecurityService.findById(new PkId(codicemovimento));
	model.addAttribute("mov", mov);
	return "movimenti/ajaxSezioneInfoAttivitaTipoMovimento";
    }
}
