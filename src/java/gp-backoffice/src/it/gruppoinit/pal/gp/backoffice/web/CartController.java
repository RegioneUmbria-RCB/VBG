package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.openspcoop.pdd.services.SPCoopException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import it.eng.suap.xengine.model.service.xcommon.AzioneType;
import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.ConfApplicative;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo2;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.ErroreValidazione;
import it.gruppoinit.pal.gp.core.domain.cart.FileInfo;
import it.gruppoinit.pal.gp.core.domain.cart.ModuloRendering;
import it.gruppoinit.pal.gp.core.domain.helper.CartHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CartInfoDizionarioHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.EndoTipo1Helper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.PresentazioneDomandaCartCommand;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AzioniService;
import it.gruppoinit.pal.gp.core.service.CartBaseServiceERO;
import it.gruppoinit.pal.gp.core.service.CartDisponibilitaDizionarioService;
import it.gruppoinit.pal.gp.core.service.CartDisponibilitaSchedaEndo1Service;
import it.gruppoinit.pal.gp.core.service.CartDisponibilitaSchedaEndo2Service;
import it.gruppoinit.pal.gp.core.service.CartInvioDizionarioService;
import it.gruppoinit.pal.gp.core.service.CartInvioSchedaEndo1Service;
import it.gruppoinit.pal.gp.core.service.CartInvioSchedaEndo2Service;
import it.gruppoinit.pal.gp.core.service.CartMappingService;
import it.gruppoinit.pal.gp.core.service.CartModulisticaService;
import it.gruppoinit.pal.gp.core.service.CartPresentazioneDomandaService;
import it.gruppoinit.pal.gp.core.service.CartRichiestaDizionarioService;
import it.gruppoinit.pal.gp.core.service.CartRichiestaSchedaEndo1Service;
import it.gruppoinit.pal.gp.core.service.CartRichiestaSchedaEndo2Service;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.StpTipologieEndo2Service;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.sigepro.cart.service.CartRfcBaseService;
import it.gruppoinit.sigepro.cart.service.helper.CartServiceConfigurationHelper;
import it.gruppoinit.sigepro.cart.service.utils.CartUtils;

@Controller
@SessionAttributes(value = { "stpCommand", "cartPropertiesBean", "cartHelper", "cartInfoDizionarioHelper" })
public class CartController extends BaseController<Object> {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private AzioniService azioniService;
    @Autowired
    private CartDisponibilitaDizionarioService cartDisponibilitaDizionarioVbgService;
    @Autowired
    private CartInvioDizionarioService cartInvioDizionarioService;
    @Autowired
    private CartInvioSchedaEndo1Service cartInvioSchedaEndo1Service;
    @Autowired
    private CartInvioSchedaEndo2Service cartInvioSchedaEndo2Service;
    @Autowired
    private CartRichiestaDizionarioService cartRichiestaDizionarioService;
    @Autowired
    private CartRichiestaSchedaEndo1Service cartRichiestaSchedaEndo1Service;
    @Autowired
    private CartRichiestaSchedaEndo2Service cartRichiestaSchedaEndo2Service;
    @Autowired
    private CartDisponibilitaSchedaEndo1Service cartDisponibilitaSchedaEndo1Service;
    @Autowired
    private CartDisponibilitaSchedaEndo2Service cartDisponibilitaSchedaEndo2Service;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private StpTipologieEndo2Service stpTipologieEndo2Service;
    @Autowired
    private CartModulisticaService cartModulisticaService;
    @Autowired
    private CartMappingService cartMappingService;
    @Autowired
    private CartPresentazioneDomandaService cartPresentazioneDomandaService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;
    @Autowired
    private IstanzeallegatiService istanzeallegatiService;

    @RequestMapping
    public String view(HttpServletRequest request, HttpServletResponse response) throws IOException {

	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	prepareViewPage(request);
	return "cart/form";
    }

    @RequestMapping
    public String viewInvioEndo(@RequestParam("tipo") String tipo, @RequestParam("codice") Integer codice, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	String descrizioneEndo = "";
	PkId id = new PkId(codice);
	if (tipo.equalsIgnoreCase("1")) {
	    Inventarioprocedimenti endo = inventarioprocedimentiService.findById(id);
	    descrizioneEndo = endo.getProcedimento();
	} else {
	    Alberoproc alberoproc = alberoprocService.findById(id);
	    descrizioneEndo = alberoproc.getVwAlberoproc().getScDescrizione();
	}
	request.setAttribute("descrizioneEndo", descrizioneEndo);
	return "cart/invioEndo";
    }

    @RequestMapping
    public String eseguiOperazione(@RequestParam("operazione") String operazione, @RequestParam(value = "idEgov", required = false) String idEgov,
	    @RequestParam("servizio") String servizio,
	    @RequestParam(value = "disableSchemaValidation", required = false) Boolean disableSchemaValidation, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	CartBaseServiceERO service = trovaServizio(servizio);
	String status_msg = "02";
	boolean isErroreSPCoop = false;
	boolean proponiSenzaValidazione = false;
	if (disableSchemaValidation != null) {
	    if (disableSchemaValidation.booleanValue()) {
		service.setEffettuaValidazioneSchema(false);
	    }
	}
	if (service != null) {
	    try {
		if (operazione.equalsIgnoreCase("elabora")) {
		    proponiSenzaValidazione = true;
		    service.elaboraMessaggio(idEgov);
		} else if (operazione.equalsIgnoreCase("cancella")) {
		    status_msg = "05";
		    service.deleteMessage(idEgov);
		} else if (operazione.equalsIgnoreCase("cancellaTutti")) {
		    status_msg = "05";
		    service.deleteAllMessages();
		}
		if (operazione.equalsIgnoreCase("elaboratutti")) {
		    // BOCCI 2013-01-07 LE RICHIESTE DI ELABORAZIONE DI TUTTI I MESSAGGI NON DEVONO PREVEDERE DI DEFAULT LA VALIDAZIONE FORMALE.
		    service.setEffettuaValidazioneSchema(false);
		    service.elaboraTuttiMessaggi();
		}
	    } catch (Exception e) {
		status_msg = "03";
		List<String> warnings = new ArrayList<String>();
		log.error("Si è verificato un errore durante la comunicazione con i servizi CART: ", e);
		if (e instanceof SPCoopException) {
		    isErroreSPCoop = true;
		    warnings.add(
			    "Codice Errore[" + ((SPCoopException) e).getCodiceEccezione() + "] - " + ((SPCoopException) e).getDescrizioneEccezione());
		} else {
		    warnings.add(e.getMessage());
		}
		FlashMessages.setWarnings(warnings);
	    } finally {
		service.setEffettuaValidazioneSchema(true);
	    }
	}
	String appendQstring = "";
	if (proponiSenzaValidazione) {
	    if (!isErroreSPCoop) {
		if (disableSchemaValidation == null) { // la stringa la  appendo solamente la prima volta
		    appendQstring = "&operazione=" + operazione + "&idEgov=" + idEgov + "&servizio=" + servizio + "&disableSchemaValidation=true";
		}
	    }
	}
	return "redirect:view.htm?status_msg=" + status_msg + "&ts_" + System.currentTimeMillis() + appendQstring;
    }

    private CartBaseServiceERO trovaServizio(String servizio) {

	if (servizio.equalsIgnoreCase("disponibilitaDizionario")) {
	    return cartDisponibilitaDizionarioVbgService;
	}
	if (servizio.equalsIgnoreCase("invioDizionario")) {
	    return cartInvioDizionarioService;
	}
	if (servizio.equalsIgnoreCase("disponibilitaEndo1")) {
	    return cartDisponibilitaSchedaEndo1Service;
	}
	if (servizio.equalsIgnoreCase("invioSchedaEndo1")) {
	    return cartInvioSchedaEndo1Service;
	}
	if (servizio.equalsIgnoreCase("disponibilitaEndo2")) {
	    return cartDisponibilitaSchedaEndo2Service;
	}
	if (servizio.equalsIgnoreCase("invioSchedaEndo2")) {
	    return cartInvioSchedaEndo2Service;
	}
	return null;
    }

    /**
     * @param request
     */
    private void prepareViewPage(HttpServletRequest request) {

	//cercaMessaggiPerServizio(request, cartDisponibilitaSchedaEndo1Service, "DisponibilitaEndo1");
	//cercaMessaggiPerServizio(request, cartDisponibilitaSchedaEndo2Service, "DisponibilitaEndo2");
	//cercaMessaggiPerServizio(request, cartDisponibilitaDizionarioVbgService, "DisponibilitaDizionario");
	cercaMessaggiPerServizio(request, cartInvioDizionarioService, "InvioDizionario");
	//cercaMessaggiPerServizio(request, cartInvioSchedaEndo1Service, "InvioSchedaEndo1");
	//cercaMessaggiPerServizio(request, cartInvioSchedaEndo2Service, "InvioSchedaEndo2");
	//cercaMessaggiPerServizio(request, cartDisponibilitaDizionarioVbgService, "DisponibilitaDizionario");
    }

    /**
     * @param request
     */
    private void cercaMessaggiPerServizio(HttpServletRequest request, CartBaseServiceERO service, String descrizioneMessaggioApplicativo) {

	try {
	    String[] messaggiInvioDizionario = service.getNextMessagesId(100);
	    request.setAttribute("messaggi" + descrizioneMessaggioApplicativo, messaggiInvioDizionario);
	} catch (SPCoopException e) {
	    if (e.getCodiceEccezione().equalsIgnoreCase("Cart_406")) {
		request.setAttribute("messaggioErrore" + descrizioneMessaggioApplicativo, "Non ci sono messaggi");
	    } else {
		request.setAttribute("messaggioErrore" + descrizioneMessaggioApplicativo,
			"Errore nel recupero dei messaggi. Codice[" + e.getCodiceEccezione() + "]-" + e.getDescrizioneEccezione());
	    }
	} catch (Exception e) {
	    String messaggioErrore = "Non è stato possibile contattare il servizio a causa di:" + e.getMessage();
	    request.setAttribute("messaggioErrore" + descrizioneMessaggioApplicativo, messaggioErrore);
	}
    }

    @RequestMapping
    public void ajaxRichiestaDizionario(HttpServletRequest request, HttpServletResponse response) throws IOException {

	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	String messaggioRisposta = getMessageFromBundle("stp.label.invioavvenuto", null);
	try {
	    cartRichiestaDizionarioService.inviaRichiestaDizionario();
	} catch (SPCoopException e) {
	    log.error("ajaxRichiestaDizionario: {}", e.getMessage());
	    messaggioRisposta = renderSPCoopException(e);
	} catch (Exception e) {
	    log.error("ajaxRichiestaDizionario: {}", e.getMessage());
	    messaggioRisposta = renderException(e);
	}
	response.getWriter().write(messaggioRisposta);
    }

    @RequestMapping
    public String richiestaSchedeDizionario(@RequestParam("tipo") String tipo, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	try {
	    int schedeRichieste = 0;
	    if (tipo.equalsIgnoreCase("1")) {
		schedeRichieste = cartRichiestaSchedaEndo1Service.inviaRichiestaSchedeDizionario(CartRfcBaseService.TipoRichiesta.Invio);
	    } else {
		schedeRichieste = cartRichiestaSchedaEndo2Service.inviaRichiestaSchedeDizionario(CartRfcBaseService.TipoRichiesta.Invio);
	    }
	    FlashMessages.getInfos().add(getMessageFromBundle("stp.label.schede.invioavvenuto", new Object[Integer.valueOf(schedeRichieste)]));
	} catch (Exception e) {
	    FlashMessages.getWarnings().add("Errore generico nell'invio dei messaggi: [" + e.getMessage() + "]");
	}
	return "redirect:view.htm?" + WebConstants.SOFTWARE + "=" + ORMHelper.getSoftware();
    }

    @RequestMapping
    public void ajaxRicaricaConfigurazioni(HttpServletRequest request, HttpServletResponse response) throws IOException {

	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	String messaggioRisposta = getMessageFromBundle("02", null);
	try {
	    //cartDisponibilitaDizionarioVbgService.reloadConfiguration();
	    //cartDisponibilitaSchedaEndo1Service.reloadConfiguration();
	    //cartDisponibilitaSchedaEndo2Service.reloadConfiguration();
	    cartInvioDizionarioService.reloadConfiguration();
	    //cartInvioSchedaEndo1Service.reloadConfiguration();
	    //cartInvioSchedaEndo2Service.reloadConfiguration();
	    cartRichiestaDizionarioService.reloadConfiguration();
	    //cartRichiestaSchedaEndo1Service.reloadConfiguration();
	    //cartRichiestaSchedaEndo2Service.reloadConfiguration();
	} catch (Exception e) {
	    log.error("ajaxRichiestaDizionario: {}", e.getMessage());
	    messaggioRisposta = renderException(e);
	}
	response.getWriter().write(messaggioRisposta);
    }

    @RequestMapping
    public void ajaxRichiestaSchedaEndo1(@RequestParam("tiporichiesta") String tiporichiesta, @RequestParam("codice") Integer codice,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	String messaggioRisposta = getMessageFromBundle("stp.label.invioavvenuto", null);
	try {
	    cartRichiestaSchedaEndo1Service.inviaRichiestaSchedaEndo(codice, getTipoRichiestaEnumValue(tiporichiesta));
	} catch (SPCoopException e) {
	    log.error("ajaxRichiestaSchedaEndo1: CodiceErrore={}, descrizione={}", e.getCodiceEccezione(), e.getDescrizioneEccezione());
	    messaggioRisposta = renderSPCoopException(e);
	} catch (Exception e) {
	    log.error("ajaxRichiestaSchedaEndo1: {}", e.getMessage());
	    messaggioRisposta = renderException(e);
	}
	response.getWriter().write(messaggioRisposta);
    }

    private CartRfcBaseService.TipoRichiesta getTipoRichiestaEnumValue(String tiporichiesta) {

	if (tiporichiesta.equalsIgnoreCase(CartRfcBaseService.TipoRichiesta.Invio.name())) {
	    return CartRfcBaseService.TipoRichiesta.Invio;
	} else {
	    return CartRfcBaseService.TipoRichiesta.Disponibilità;
	}
    }

    @RequestMapping
    public void ajaxRichiestaSchedaEndo2(@RequestParam("tiporichiesta") String tiporichiesta, @RequestParam("codice") Integer codice,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	String messaggioRisposta = getMessageFromBundle("stp.label.invioavvenuto", null);
	try {
	    cartRichiestaSchedaEndo2Service.inviaRichiestaSchedaEndo(codice, getTipoRichiestaEnumValue(tiporichiesta));
	} catch (SPCoopException e) {
	    log.error("ajaxRichiestaSchedaEndo2: CodiceErrore={}, descrizione={}", e.getCodiceEccezione(), e.getDescrizioneEccezione());
	    messaggioRisposta = renderSPCoopException(e);
	} catch (Exception e) {
	    log.error("ajaxRichiestaSchedaEndo2: {}", e.getMessage());
	    messaggioRisposta = renderException(e);
	}
	response.getWriter().write(messaggioRisposta);
    }

    @RequestMapping
    public String ajaxLoadStpTipologiaEndo2(Model model, HttpServletRequest request, HttpServletResponse response) {

	List<StpTipologieEndo2> stpTipologieEndo2s = stpTipologieEndo2Service.findAll(null, null);
	model.addAttribute("stpTipologieEndo2s", stpTipologieEndo2s);
	model.addAttribute("cartHelper", new CartHelper());
	response.setContentType("text/plain");
	return "cart/aggiungiInterventiForm";
    }

    @RequestMapping
    public String ajaxPreElabora(@RequestParam(value = "idEgov", required = false) String idEgov, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	if (log.isDebugEnabled()) {
	    log.debug("ajaxPreElabora# Inizio fase di configurazione  dell' elaborazione dei messaggi di invio dizionario.....");
	}
	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	CartServiceConfigurationHelper.setCurrentSoftware(ORMHelper.getSoftware());
	CartBaseServiceERO service = cartInvioDizionarioService;
	//	List<String> listAmministrazioni = new ArrayList<String>();
	CartInfoDizionarioHelper cartInfoDizionarioHelper = new CartInfoDizionarioHelper();
	// Recupero le configurazioni delle amministrazioni e le imposto
	try {
	    boolean isValidazione = service.isEffettuavalidazioneSchema();
	    service.setEffettuaValidazioneSchema(false);
	    cartInfoDizionarioHelper = service.preElaboraMessaggio(idEgov);
	    log.debug("ajaxPreElabora# Recupero le preferenze applicative e pre popolo i campi amministrazione e movimenti");
	    loadPreferenzeElaborazione(cartInfoDizionarioHelper);
	    service.setEffettuaValidazioneSchema(isValidazione);
	} catch (SPCoopException e) {
	    e.printStackTrace();
	} catch (RemoteException e) {
	    e.printStackTrace();
	}
	// List<EndoTipo1Helper> endoTipo1Helpers = cartInfoDizionarioHelper.getEndoTipo1Helpers();
	//	for (EndoTipo1Helper endoTipo1Helper : endoTipo1Helpers) {
	//	    String ammCart = endoTipo1Helper.getCodiceAmministrazioneCart();
	//	    Amministrazioni amministrazioni = amministrazioniService.findByCodiceamministrazioneCart(ammCart);
	//	    if (amministrazioni != null) {
	//		endoTipo1Helper.setAmministrazioni(amministrazioni);
	//	    }
	//	}
	List<Azioni> azionis = azioniService.findAll(null, null);
	response.setContentType("text/plain");
	model.addAttribute("cartInfoDizionarioHelper", cartInfoDizionarioHelper);
	model.addAttribute("azionis", azionis);
	return "cart/preElaboraview";
    }

    @RequestMapping
    public String proseguiElaborazione(@ModelAttribute("cartInfoDizionarioHelper") CartInfoDizionarioHelper cartInfoDizionarioHelper, Model model,
	    HttpServletRequest request, HttpServletResponse response) {

	// della tabella configApplicative
	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	CartServiceConfigurationHelper.setCurrentSoftware(ORMHelper.getSoftware());
	// definisco il service che dovrò utilizzare per l'inserimento e update dei dati
	CartBaseServiceERO service = cartInvioDizionarioService;
	// Eseguo le configurazione e controllo se siano complete, nel caso non lo sia rilancio un errore che notifica all'operatore
	// di completare le operazioni di notifica prima di proseguire.
	if (log.isDebugEnabled()) {
	    log.debug("proseguiElaborazione# Inzio salvataggio configurazione......");
	}
	//Salvo nelle preferenze applicative tutte le informazioni che ho passato dalla maschera
	// di pre-elaborazione.
	log.debug("proseguiElaborazione# Aggiorno le preferenze applicative : Amministrazioni e Movimenti da associare");
	salvaPreferenzeElaborazione(cartInfoDizionarioHelper, request, response);
	// Controllo se sono presenti errori in fase di configurazione
	// Sono presenti errori se non sono state effettuate tutte le configurazioni obbligatorie:
	// Amministrazioni e tipo movimento per EndooTipo1
	// Azione per TipologiaEndo2
	List<String> msgs = eseguiAggiornamentoConfigurazioni(cartInfoDizionarioHelper);
	if (!msgs.isEmpty()) {
	    if (log.isErrorEnabled()) {
		log.error("proseguiElaborazione# Errore, non è stata completata la configurazione ");
	    }
	    FlashMessages.setWarnings(msgs);
	    return "redirect:view.htm?" + WebConstants.SOFTWARE + "=" + ORMHelper.getSoftware();
	} else// Configurazioni effettuate correttamente preseguo con l'elaborazione dei messaggi dell'invio dizionario
	{
	    if (log.isDebugEnabled()) {
		log.debug("proseguiElaborazione# Fine salvataggio configurazione......");
	    }
	    // Inizio elaborazione dei messaggi dei messaggi del dizionario
	    try {
		if (log.isDebugEnabled()) {
		    log.debug("proseguiElaborazione# Inizio elaborazione dei messaggi del dizionario");
		}
		service.elaboraMessaggio(cartInfoDizionarioHelper);
		FlashMessages.getInfos().add("Aggiornamento effettuato correttamente");
	    } catch (SPCoopException e) {
		log.error("proseguiElaborazione# Errore durante l'elaborazione: {}", e);
		String errorMessage = CartUtils.SPCoopExceptionToString(e, true);
		FlashMessages.getWarnings().add(errorMessage);
	    } catch (RemoteException e) {
		FlashMessages.getWarnings().add(e.getMessage());
		log.error("proseguiElaborazione# Errore durante l'elaborazione: {}", e);
	    }
	    try {
		if (log.isDebugEnabled()) {
		    log.debug("proseguiElaborazione# Provo ad eliminare i messaggi del dizionario");
		}
		service.deleteMessage(cartInfoDizionarioHelper.getIdEgov());
	    } catch (SPCoopException e) {
		String errorMessage = CartUtils.SPCoopExceptionToString(e, true);
		log.error("proseguiElaborazione# Errore durante la cancellazione dell'idEgov: {}, {}", cartInfoDizionarioHelper.getIdEgov(),
			errorMessage);
		// FlashMessages.getWarnings().add(" Errore durante la cancellazione dell'idEgov ");
	    } catch (Exception e) {
		// FlashMessages.getWarnings().add(e.getMessage());
		log.error("proseguiElaborazione# Errore durante l'elaborazione: {}", e);
	    }
	}
	return "redirect:view.htm?" + WebConstants.SOFTWARE + "=" + ORMHelper.getSoftware();
    }

    private void salvaPreferenzeElaborazione(CartInfoDizionarioHelper cartInfoDizionarioHelper, HttpServletRequest request,
	    HttpServletResponse response) {

	if (cartInfoDizionarioHelper.getCodiciAmministrazioniCart() != null) {
	    String[] amministrazioniCart = StringUtils.splitPreserveAllTokens(cartInfoDizionarioHelper.getCodiciAmministrazioniCart().trim(), ",");
	    String[] codiciAmministrazioni = StringUtils.splitPreserveAllTokens(cartInfoDizionarioHelper.getCodiciAmministrazioni().trim(), ",");
	    String[] codiciMovimento = StringUtils.splitPreserveAllTokens(cartInfoDizionarioHelper.getCodiciTipimovimento().trim(), ",");
	    String keyStandar = WebConstants.CONF_APPLICATIVE_CART + "#" + WebConstants.CONF_APPLICATIVE_DIZ + "#";
	    String keyAMM = keyStandar + WebConstants.CONF_APPLICATIVE_AMM + "#";
	    String keyMOV = keyStandar + WebConstants.CONF_APPLICATIVE_MOV + "#";
	    for (int i = 0; i < amministrazioniCart.length; i++) {
		if (StringUtils.isNotBlank(codiciAmministrazioni[i])) {
		    salvaPreferenzaApplicative(keyAMM + amministrazioniCart[i], codiciAmministrazioni[i], request, response);
		}
		if (StringUtils.isNotBlank(codiciMovimento[i])) {
		    salvaPreferenzaApplicative(keyMOV + amministrazioniCart[i], codiciMovimento[i], request, response);
		}
	    }
	}
    }

    private CartInfoDizionarioHelper loadPreferenzeElaborazione(CartInfoDizionarioHelper cartInfoDizionarioHelper) {

	String keyStandar = WebConstants.CONF_APPLICATIVE_CART + "#" + WebConstants.CONF_APPLICATIVE_DIZ + "#";
	String keyAMM = keyStandar + WebConstants.CONF_APPLICATIVE_AMM + "#";
	String keyMOV = keyStandar + WebConstants.CONF_APPLICATIVE_MOV + "#";
	log.debug("loadPreferenzeElaborazione# Inzio recupero preferenze applicative {},{}", new Object[] { keyAMM, keyMOV });
	List<EndoTipo1Helper> endoTipo1Helpers = cartInfoDizionarioHelper.getEndoTipo1Helpers();
	for (EndoTipo1Helper endoTipo1Helper : endoTipo1Helpers) {
	    String codiceamministrazioneCart = endoTipo1Helper.getCodiceAmministrazioneCart();
	    ConfApplicative confApplicativeAMM = loadCofingApplicaticaByKey(keyAMM + codiceamministrazioneCart);
	    ConfApplicative confApplicativeMOV = loadCofingApplicaticaByKey(keyMOV + codiceamministrazioneCart);
	    Amministrazioni amministrazioni = new Amministrazioni();
	    if (confApplicativeAMM != null && StringUtils.isNotBlank(confApplicativeAMM.getValore())) {
		amministrazioni = amministrazioniService.findById(new PkId(Integer.parseInt(confApplicativeAMM.getValore())));
		endoTipo1Helper.setAmministrazioni(amministrazioni);
	    }
	    if (confApplicativeMOV != null && StringUtils.isNotBlank(confApplicativeMOV.getValore())) {
		Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(confApplicativeMOV.getValore()));
		endoTipo1Helper.setTipimovimento(tipimovimento);
	    }
	}
	log.debug("loadPreferenzeElaborazione# Fine recupero preferenze applicative");
	return cartInfoDizionarioHelper;
    }

    private List<String> eseguiAggiornamentoConfigurazioni(CartInfoDizionarioHelper cartInfoDizionarioHelper) {

	List<String> msgs = new ArrayList<String>();
	// Controllo di aver passato tutti i dati
	// Estraggo i codici amministrazioni passati
	boolean checkConfigurazioneAmministrazioni = true; //amministrazioniService.updateAmministrazioniCartAndValidateConfiguration(codiciAmministrazioni,codiciAmministrazioniCart);
	boolean checkConfigurazioniTipimovimentoCart = true; //amministrazioniService.updateTipimovimentoCar
	log.debug("eseguiAggiornamentoConfigurazioni#Inizio Aggiornamento e verifica Amministrazioni e Movimenti da associare.....");
	if (cartInfoDizionarioHelper.getCodiciAmministrazioni() != null) {
	    String[] codiciAmministrazioni = StringUtils.splitPreserveAllTokens(cartInfoDizionarioHelper.getCodiciAmministrazioni(), ",");
	    // Estraggo i codici amministrazioni cart trovati (parte testuale della tipologia endo 1 trovata)
	    String[] codiciAmministrazioniCart = cartInfoDizionarioHelper.getCodiciAmministrazioniCart().split(",");
	    //Estraggo i codici dei tipi movimento passati
	    String[] codiciTipoMov = StringUtils.splitPreserveAllTokens(cartInfoDizionarioHelper.getCodiciTipimovimento(), ",");
	    List<EndoTipo1Helper> list = cartInfoDizionarioHelper.getEndoTipo1Helpers();
	    for (EndoTipo1Helper et1h : list) {
		String codiceAmministrazioneCART = et1h.getCodiceAmministrazioneCart();
		for (int i = 0; i < codiciAmministrazioniCart.length; i++) {
		    String cartc = codiciAmministrazioniCart[i];
		    String codiceAmministrazione = codiciAmministrazioni[i];
		    String tipomovimento = codiciTipoMov[i];
		    if (cartc.equalsIgnoreCase(codiceAmministrazioneCART)) {
			if (StringUtils.isNotBlank(codiceAmministrazione)) {
			    Amministrazioni a = amministrazioniService.findById(new PkId(Integer.parseInt(codiceAmministrazione)));
			    et1h.setAmministrazioni(a);
			} else {
			    if (EntityUtils.getNestedProperty(et1h.getAmministrazioni(), "id.codice") == null) {
				checkConfigurazioneAmministrazioni = false;
			    }
			}
			if (StringUtils.isNotBlank(tipomovimento)) {
			    Tipimovimento tm = tipiMovimentoService.findById(new TipimovimentoId(tipomovimento));
			    et1h.setTipimovimento(tm);
			} else {
			    if (EntityUtils.getNestedProperty(et1h.getTipimovimento(), "id.tipomovimento") == null) {
				checkConfigurazioniTipimovimentoCart = false;
			    }
			}
			break;
		    }
		}
	    }
	}
	// Estraggo i codici delle azioni passate
	String[] codiciAzioni = cartInfoDizionarioHelper.getCodiciAzioni().split(",");
	// Estraggo i codici delle tipologie endo 2 passate
	String[] codiciTipologieEndo2 = cartInfoDizionarioHelper.getCodiceTipologieEndo2().split(",");
	// Estraggo le descrizioni delle tipologio endo 2 passate
	String[] descrizioneTipologieEndo2 = cartInfoDizionarioHelper.getDescrizioneTipologieEndo2().split(",");
	//	boolean checkConfigurazioneAmministrazioni = true; //amministrazioniService.updateAmministrazioniCartAndValidateConfiguration(codiciAmministrazioni,codiciAmministrazioniCart);
	//	boolean checkConfigurazioniTipimovimentoCart = true; //amministrazioniService.updateTipimovimentoCartAndValidateConfiguration(codiciTipoMov,codiciAmministrazioniCart);
	log.debug("eseguiAggiornamentoConfigurazioni# Eseguo aggiornamento e controllo delle configurazione per stpTipologieendo2...");
	boolean checkConfigurazioniStpTipologiaEndo2 = stpTipologieEndo2Service.updateStpTipologieEndo2AndValidateConfiguration(codiciAzioni,
		codiciTipologieEndo2, descrizioneTipologieEndo2);
	if (checkConfigurazioneAmministrazioni == false || checkConfigurazioniStpTipologiaEndo2 == false
		|| checkConfigurazioniTipimovimentoCart == false) {
	    String mgsPrincipale = getMessageFromBundle("service_error.stp.elaborazione_messaggi_dizionario", null);
	    msgs.add(mgsPrincipale);
	    msgs.add("<ol>");
	    if (checkConfigurazioneAmministrazioni == false) {
		msgs.add("<li>Non sono state configurate le amministrazioni per le tipologie di endo trovate</li>");
	    }
	    if (checkConfigurazioniTipimovimentoCart == false) {
		msgs.add("<li>Non sono stati configurati i tipi movimenti per le tipologie di endo trovate</li>");
	    }
	    if (checkConfigurazioniStpTipologiaEndo2 == false) {
		msgs.add("<li>Non sono state configurate le azioni per le tipologie di endo 2 trovate</li>");
	    }
	    msgs.add("</ol>");
	    msgs.add("Per eseguire l'elaborazione completare la configurazione sul pannello visibile all'invocazione della funzionalità \"Elabora\"");
	    FlashMessages.setWarnings(msgs);
	}
	return msgs;
    }

    @RequestMapping
    public String importaInterventi(Model model, @ModelAttribute("cartHelper") CartHelper cartHelper, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	try {
	    cartInvioDizionarioService.insertInterventiDaCart(cartHelper.getCodiciStpTipologiaEndo2());
	} catch (Exception e) {
	    log.error("Errore nell'importazione dei procedimenti, {}", e);
	    throw new RuntimeException("Errore nell'importazione dei procedimenti, " + e);
	}
	return "redirect:view.htm?" + WebConstants.SOFTWARE + "=" + ORMHelper.getSoftware() + "&status_msg=02";
    }

    @RequestMapping
    public String caricaModulistica(Model model, @ModelAttribute("presentazioneDomandaCommand") PresentazioneDomandaCartCommand command,
	    HttpServletRequest request) {

	String goTo = "cart/moduli";
	DatiDomandaCart datiDomanda = caricaDatiDomanda(request);
	if (datiDomanda != null) {
	    //recupero i dati dell'istanza
	    Istanze istanza = this.istanzeService.findById(new PkId(command.getCodiceIstanza()));
	    //recupero l'elenco degli allegati associati all'istanza o ad uno dei sui endoprocedimenti
	    List<DocumentiistanzaDTO> allegatiIstanza = this.documentiistanzaService.findDocumentiistanzaDTOByIstanza(command.getCodiceIstanza(),
		    false);
	    List<DocumentiistanzaDTO> allegatiIstanzaDyn = this.documentiistanzaService.findDocumentiistanzaDTOByIstanza(command.getCodiceIstanza(),
		    true);
	    List<IstanzeallegatiDTO> allegatiEndo = this.istanzeallegatiService.findIstanzeallegatiDTOByIstanza(command.getCodiceIstanza());
	    List<FileInfo> allegatiDomanda = new ArrayList<FileInfo>();
	    for (DocumentiistanzaDTO allegatoIstanza : allegatiIstanza) {
		if (allegatoIstanza.getCodiceOggetto() != null) {
		    if (!allegatoIstanza.getNomeFile().contains(StringUtils.defaultString(istanza.getCodicepraticatel())) && !allegatoIstanza
			    .getNomeFile().contains(StringUtils.defaultString(datiDomanda.getDatiContestoDomanda().getIdDomandaCart()))) {
			FileInfo val = new FileInfo();
			val.setIdOggetto(allegatoIstanza.getCodiceOggetto());
			val.setNomeFile(allegatoIstanza.getNomeFile());
			val.setDimensione(allegatoIstanza.getDimensioneFile());
			allegatiDomanda.add(val);
		    }
		}
	    }
	    for (DocumentiistanzaDTO allegatoIstanzaDyn : allegatiIstanzaDyn) {
		if (allegatoIstanzaDyn.getCodiceOggetto() != null) {
		    FileInfo val = new FileInfo();
		    val.setIdOggetto(allegatoIstanzaDyn.getCodiceOggetto());
		    val.setNomeFile(allegatoIstanzaDyn.getNomeFile());
		    val.setDimensione(allegatoIstanzaDyn.getDimensioneFile());
		    allegatiDomanda.add(val);
		}
	    }
	    for (IstanzeallegatiDTO allegatoEndo : allegatiEndo) {
		if (allegatoEndo.getCodiceOggetto() != null) {
		    if (!allegatoEndo.getNomeFile().contains(StringUtils.defaultString(istanza.getCodicepraticatel())) && !allegatoEndo.getNomeFile()
			    .contains(StringUtils.defaultString(datiDomanda.getDatiContestoDomanda().getIdDomandaCart()))) {
			FileInfo val = new FileInfo();
			val.setIdOggetto(allegatoEndo.getCodiceOggetto());
			val.setNomeFile(allegatoEndo.getNomeFile());
			val.setDimensione(allegatoEndo.getDimensioneFile());
			allegatiDomanda.add(val);
		    }
		}
	    }
	    datiDomanda.getAllegati().clear();
	    datiDomanda.getAllegati().addAll(allegatiDomanda);
	    //imposto i valori di default per gli id semantici che lo prevedono
	    this.cartModulisticaService.impostaValoriDefault(FACCTConstants.STANDARD_0, datiDomanda);
	    //Rendering della modulistica CART da visualizzare all'utente (versione backoffice)
	    Map<Object, Object> contextData = new HashMap<Object, Object>();
	    contextData.put(FACCTConstants.VELOCITY_CONTEXT_KEY_CONTEXT_PATH, request.getContextPath());
	    contextData.put(FACCTConstants.VELOCITY_CONTEXT_KEY_IS_FRONT_OFFICE, Boolean.FALSE);
	    contextData.put(FACCTConstants.VELOCITY_CONTEXT_KEY_CODICE_ISTANZA, command.getCodiceIstanza());//TODO verificare se utilizzato nei templates di velocity, se non usato eliminare
	    List<ModuloRendering> moduli = cartModulisticaService.renderModulistica(datiDomanda, contextData);
	    model.addAttribute("idalberoproc", datiDomanda.getDatiContestoDomanda().getIdAlberoProc());
	    model.addAttribute("allegatiIstanza", allegatiDomanda);
	    model.addAttribute("modulistica", moduli);
	    model.addAttribute("valoreAzioneAvvio", AzioneType.AVVIO);
	} else {
	    //TODO gestire condizione di errore
	    log.error("caricaModulistica - impossibile caricare i dati della domanda CART popolati precedentemente tramite le mappature CART-VBG.");
	}
	return goTo;
    }

    @RequestMapping
    public ModelAndView ajaxConfermaQuadro(@RequestParam(value = "modulo_attivo", required = true) String idModulo,
	    @RequestParam(value = "quadro_attivo", required = true) String idQuadro,
	    @RequestParam(value = "riferimento_modulo_attivo", required = true) String refModulo,
	    @RequestParam(value = "presenta_domanda", required = false) Boolean presentaDomanda, HttpServletRequest request) {

	// §§§BEGIN§§§
	Map<String, Object> model = new HashMap<String, Object>();
	if (null == presentaDomanda) {
	    presentaDomanda = Boolean.FALSE;
	}
	if (log.isDebugEnabled())
	    log.debug("ajaxConfermaQuadro() - MODULO={}, RIFERIMENTO={}, ID_QUADRO={}, PRESENTA_DOMANDA={}",
		    new String[] { idModulo, refModulo, idQuadro, presentaDomanda.toString() });
	//ModulisticaContentType modulistica = getModulisticaInUso(request, null);
	List<ErroreValidazione> errors = new ArrayList<ErroreValidazione>();
	//Lettura dei parametri della request tramite Apache Commons Fileupload per poter gestire anche gli ulpoad multipli dai campi file della modulistica
	DatiDomandaCart dati = null;
	if (StringUtils.isNotBlank(refModulo) && StringUtils.isNotBlank(idQuadro)) {
	    dati = leggiDatiQuadro(request, request, refModulo, idQuadro);
	} else {
	    String msg = MessageFormat.format("non sono stati postati l''id del quadro e/o del modulo: riferimento modulo = {0}, id quadro = {1}.",
		    refModulo, idQuadro);
	    log.info("confermaQuadro() - {}", msg);
	    dati = (DatiDomandaCart) request.getSession().getAttribute(FACCTConstants.SESSION_KEY_DATI_UTENTE);
	}
	//se il parametro request "presenta_domanda" ha valore true, valido l'intera domanda, se no valido solo il quadro corrente
	if (presentaDomanda) {
	    errors = this.cartModulisticaService.validazioneDomanda(dati);
	} else {
	    errors = this.cartModulisticaService.validazioneQuadro(dati, refModulo, idQuadro);
	}
	salvaDatiDomanda(dati, request);
	//request.getSession().setAttribute(FACCTConstants.SESSION_KEY_DATI_UTENTE, dati);
	model.put("validationErrors", errors);
	return new ModelAndView("jsonView", model);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private void salvaDatiDomanda(DatiDomandaCart dati, HttpServletRequest request) {

	request.getSession().setAttribute(FACCTConstants.SESSION_KEY_DATI_UTENTE, dati);
    }

    private DatiDomandaCart caricaDatiDomanda(HttpServletRequest request) {

	return (DatiDomandaCart) request.getSession().getAttribute(FACCTConstants.SESSION_KEY_DATI_UTENTE);
    }

    private DatiDomandaCart leggiDatiQuadro(HttpServletRequest req, HttpServletRequest request, String refModulo, String idQuadro) {

	// §§§BEGIN§§§
	DatiDomandaCart dati = (DatiDomandaCart) req.getSession().getAttribute(FACCTConstants.SESSION_KEY_DATI_UTENTE);
	return cartModulisticaService.leggiDatiQuadro(request, refModulo, idQuadro, dati);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private String renderSPCoopException(SPCoopException e) {

	String result = getMessageFromBundle("stp.label.noinvioavvenuto", null);
	result = "<br />errore #[" + e.getCodiceEccezione() + "] - " + e.getDescrizioneEccezione();
	return result;
    }

    private String renderException(Exception e) {

	String result = getMessageFromBundle("stp.label.noinvioavvenuto", null);
	result = "<br />errore: " + e.getMessage();
	return result;
    }

    @Override
    protected void setPageAttributes(Model model) {

    }

    @Override
    protected void fixMergeEntityProperty(Object entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Object entity) {

    }
}
