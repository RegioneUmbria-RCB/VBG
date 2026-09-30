package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.ConfApplicative;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.helper.CartInfoDizionarioHelper;
import it.gruppoinit.pal.gp.core.domain.helper.EndoTipo1Helper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AzioniService;
import it.gruppoinit.pal.gp.core.service.PannelloControlloConsoleService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.sigepro.cart.service.helper.CartServiceConfigurationHelper;

@Controller
@SessionAttributes(value = { "cartInfoDizionarioHelper" })
public class PannelloConsoleController extends BaseController<Object> {

    private static final Logger log = LoggerFactory.getLogger(PannelloConsoleController.class);
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private TipiMovimentoService tipiMovimentoService;
    @Autowired
    private AzioniService azioniService;
    @Autowired
    private PannelloControlloConsoleService pannelloControlloConsoleService;

    @RequestMapping
    public String view(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	CartInfoDizionarioHelper cartInfoDizionarioHelper = new CartInfoDizionarioHelper();
	model.addAttribute("cartInfoDizionarioHelper", cartInfoDizionarioHelper);
	prepareViewPage(request);
	return "pannelloConsole/form";
    }

    private void prepareViewPage(HttpServletRequest request) {

	// TODO Auto-generated method stub
    }

    @RequestMapping
    public String preElabora(Model model, @ModelAttribute("cartInfoDizionarioHelper") CartInfoDizionarioHelper cartInfoDizionarioHelper,
	    HttpServletRequest request, HttpServletResponse response) {

	if (log.isDebugEnabled()) {
	    log.debug("ajaxPreElabora# Inizio fase di configurazione  dell' elaborazione dei messaggi di invio dizionario.....");
	}
	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	CartServiceConfigurationHelper.setCurrentSoftware(ORMHelper.getSoftware());
	boolean disabilita = false;
	if (cartInfoDizionarioHelper != null) {
	    disabilita = cartInfoDizionarioHelper.isEscludiDisabilitati();
	}
	cartInfoDizionarioHelper = new CartInfoDizionarioHelper();
	try {
	    cartInfoDizionarioHelper = pannelloControlloConsoleService.preElaboraMessaggio();
	    log.debug("ajaxPreElabora# Recupero le preferenze applicative e pre popolo i campi amministrazione e movimenti");
	    loadPreferenzeElaborazione(cartInfoDizionarioHelper);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	cartInfoDizionarioHelper.setEscludiDisabilitati(disabilita);
	List<Azioni> azionis = azioniService.findAll(null, null);
	response.setContentType("text/plain");
	model.addAttribute("cartInfoDizionarioHelper", cartInfoDizionarioHelper);
	model.addAttribute("azionis", azionis);
	return "pannelloConsole/preElaboraview";
    }

    @RequestMapping
    public String proseguiElaborazioneSenzaValidare(@ModelAttribute("cartInfoDizionarioHelper") CartInfoDizionarioHelper cartInfoDizionarioHelper,
	    Model model, HttpServletRequest request, HttpServletResponse response) {

	// della tabella configApplicative
	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	CartServiceConfigurationHelper.setCurrentSoftware(ORMHelper.getSoftware());
	// definisco il service che dovrò utilizzare per l'inserimento e update dei dati
	// Eseguo le configurazione e controllo se siano complete, nel caso non lo sia rilancio un errore che notifica all'operatore
	// di completare le operazioni di notifica prima di proseguire.
	if (log.isDebugEnabled()) {
	    log.debug("proseguiElaborazione# Inzio salvataggio configurazione......");
	}
	log.debug("proseguiElaborazione# Aggiorno le preferenze applicative : Amministrazioni e Movimenti da associare");
	salvaPreferenzeElaborazione(cartInfoDizionarioHelper, request, response);
	// List<String> msgs = eseguiAggiornamentoConfigurazioni(cartInfoDizionarioHelper);
	if (log.isDebugEnabled()) {
	    log.debug("proseguiElaborazione# Fine salvataggio configurazione......");
	}
	// Inizio elaborazione dei messaggi dei messaggi del dizionario
	// System.out.println("Elaboro");
	try {
	    if (log.isDebugEnabled()) {
		log.debug("proseguiElaborazione# Inizio elaborazione dei messaggi del dizionario");
	    }
	    pannelloControlloConsoleService.elaboraMessaggio(cartInfoDizionarioHelper);
	    FlashMessages.getInfos().add("Aggiornamento effettuato correttamente");
	} catch (Exception e) {
	    log.error("proseguiElaborazione# Errore durante l'elaborazione: {}", e);
	    String errorMessage = e.getMessage();
	    FlashMessages.getWarnings().add(errorMessage);
	}
	return "redirect:view.htm?" + WebConstants.SOFTWARE + "=" + ORMHelper.getSoftware();
    }

    @RequestMapping
    public String proseguiElaborazione(@ModelAttribute("cartInfoDizionarioHelper") CartInfoDizionarioHelper cartInfoDizionarioHelper, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	// della tabella configApplicative
	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	CartServiceConfigurationHelper.setCurrentSoftware(ORMHelper.getSoftware());
	// definisco il service che dovrò utilizzare per l'inserimento e update dei dati
	// Eseguo le configurazione e controllo se siano complete, nel caso non lo sia rilancio un errore che notifica all'operatore
	// di completare le operazioni di notifica prima di proseguire.
	if (log.isDebugEnabled()) {
	    log.debug("proseguiElaborazione# Inzio salvataggio configurazione......");
	}
	// Controllo se sono presenti errori in fase di configurazione
	// Sono presenti errori se non sono state effettuate tutte le configurazioni obbligatorie:
	// Amministrazioni e tipo movimento per EndooTipo1
	// Azione per TipologiaEndo2
	salvaPreferenzeElaborazione(cartInfoDizionarioHelper, request, response);
	List<String> msgs = eseguiAggiornamentoConfigurazioni(cartInfoDizionarioHelper);
	if (!msgs.isEmpty()) {
	    if (log.isErrorEnabled()) {
		log.error("proseguiElaborazione# Errore, non è stata completata la configurazione ");
	    }
	    FlashMessages.setWarnings(msgs);
	} else {
	    FlashMessages.getInfos().add("Configurazione validata");
	}
	return "redirect:preElabora.htm?" + WebConstants.SOFTWARE + "=" + ORMHelper.getSoftware();
    }

    @RequestMapping
    public void ajaxStatus(@ModelAttribute("cartInfoDizionarioHelper") CartInfoDizionarioHelper cartInfoDizionarioHelper, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws IOException {

	// della tabella configApplicative
	CartServiceConfigurationHelper.setIdEnte(ORMHelper.getIdcomune());
	CartServiceConfigurationHelper.setAliasEnte(ORMHelper.getIdcomuneAlias());
	CartServiceConfigurationHelper.setCurrentSoftware(ORMHelper.getSoftware());
	try {
	    String statoElaborazione = pannelloControlloConsoleService.statoElaborazione(cartInfoDizionarioHelper);
	    response.getOutputStream().write(StringUtils.defaultString(statoElaborazione).getBytes());
	} catch (Exception e) {
	    log.error("proseguiElaborazione# Errore durante l'elaborazione: {}", e);
	    String errorMessage = e.getMessage();
	    response.getOutputStream().write(errorMessage.getBytes());
	}
    }

    @RequestMapping
    public void ajaxEliminaMovimento(@RequestParam("tipo") String tipo, @RequestParam("amministrazione") String amministrazione,
	    @ModelAttribute("cartInfoDizionarioHelper") CartInfoDizionarioHelper cartInfoDizionarioHelper, Model model, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	boolean trovato = false;
	List<EndoTipo1Helper> endoTipo1Helpers = cartInfoDizionarioHelper.getEndoTipo1Helpers();
	for (EndoTipo1Helper e1th : endoTipo1Helpers) {
	    String amm = StringUtils.defaultString(e1th.getCodiceAmministrazioneCart());
	    if (amm.equalsIgnoreCase(amministrazione)) {
		if (StringUtils.defaultString(tipo).equalsIgnoreCase("scia")) {
		    e1th.setTipimovimento(null);
		    trovato = true;
		    eliminaPreferenzaApplicative(getKeyMovimentoScia() + amministrazione);
		} else if (StringUtils.defaultString(tipo).equalsIgnoreCase("ordinario")) {
		    e1th.setTipimovimentoOrdinario(null);
		    trovato = true;
		    eliminaPreferenzaApplicative(getKeyMovimentoOrdinario() + amministrazione);
		} else if (StringUtils.defaultString(tipo).equalsIgnoreCase("comunicazione")) {
		    e1th.setTipimovimentoComunicazione(null);
		    eliminaPreferenzaApplicative(getKeyMovimentoComunicazione() + amministrazione);
		    trovato = true;
		}
	    }
	}
	response.getOutputStream().write(String.valueOf(trovato).getBytes());
    }

    private String getKeyMovimentoScia() {

	return getKeyStandard() + WebConstants.CONF_APPLICATIVE_MOV_SCIA + "#";
    }

    private String getKeyMovimentoOrdinario() {

	return getKeyStandard() + WebConstants.CONF_APPLICATIVE_MOV_ORDINARIO + "#";
    }

    private String getKeyMovimentoComunicazione() {

	return getKeyStandard() + WebConstants.CONF_APPLICATIVE_MOV_COMUNICAZIONE + "#";
    }

    private String getKeyAmministrazioni() {

	return getKeyStandard() + WebConstants.CONF_APPLICATIVE_AMM + "#";
    }

    private String getKeyStandard() {

	return WebConstants.CONF_APPLICATIVE_CONSOLE + "#" + WebConstants.CONF_APPLICATIVE_DIZ + "#";
    }

    private void salvaPreferenzeElaborazione(CartInfoDizionarioHelper cartInfoDizionarioHelper, HttpServletRequest request,
	    HttpServletResponse response) {

	if (cartInfoDizionarioHelper == null) {
	    return;
	}
	String[] amministrazioniCart = StringUtils
		.splitPreserveAllTokens(StringUtils.defaultString(cartInfoDizionarioHelper.getCodiciAmministrazioniCart()).trim(), ",");
	String[] codiciAmministrazioni = StringUtils
		.splitPreserveAllTokens(StringUtils.defaultString(cartInfoDizionarioHelper.getCodiciAmministrazioni()).trim(), ",");
	String[] codiciMovimento = StringUtils
		.splitPreserveAllTokens(StringUtils.defaultString(cartInfoDizionarioHelper.getCodiciTipimovimento()).trim(), ",");
	String[] codiciMovimentoOrdinario = StringUtils
		.splitPreserveAllTokens(StringUtils.defaultString(cartInfoDizionarioHelper.getCodiciTipimovimentoOrdinario()).trim(), ",");
	String[] codiciMovimentoComunicazione = StringUtils
		.splitPreserveAllTokens(StringUtils.defaultString(cartInfoDizionarioHelper.getCodiciTipimovimentoComunicazione()).trim(), ",");
	String keyAMM = getKeyAmministrazioni();
	String keyMOVscia = getKeyMovimentoScia();
	String keyMOVordinario = getKeyMovimentoOrdinario();
	String keyMOVcomunicazione = getKeyMovimentoComunicazione();
	for (int i = 0; i < amministrazioniCart.length; i++) {
	    if (StringUtils.isNotBlank(codiciAmministrazioni[i])) {
		salvaPreferenzaApplicative(keyAMM + amministrazioniCart[i], codiciAmministrazioni[i], request, response);
	    }
	    if (StringUtils.isNotBlank(codiciMovimento[i])) {
		salvaPreferenzaApplicative(keyMOVscia + amministrazioniCart[i], codiciMovimento[i], request, response);
	    }
	    if (StringUtils.isNotBlank(codiciMovimentoOrdinario[i])) {
		salvaPreferenzaApplicative(keyMOVordinario + amministrazioniCart[i], codiciMovimentoOrdinario[i], request, response);
	    }
	    if (StringUtils.isNotBlank(codiciMovimentoComunicazione[i])) {
		salvaPreferenzaApplicative(keyMOVcomunicazione + amministrazioniCart[i], codiciMovimentoComunicazione[i], request, response);
	    }
	}
    }

    private CartInfoDizionarioHelper loadPreferenzeElaborazione(CartInfoDizionarioHelper cartInfoDizionarioHelper) {

	String keyAMM = getKeyAmministrazioni();
	String keyMOVscia = getKeyMovimentoScia();
	String keyMOVordinario = getKeyMovimentoOrdinario();
	String keyMOVcomunicazione = getKeyMovimentoComunicazione();
	log.debug("loadPreferenzeElaborazione# Inzio recupero preferenze applicative {},{},{},{}",
		new Object[] { keyAMM, keyMOVscia, keyMOVordinario, keyMOVcomunicazione });
	List<EndoTipo1Helper> endoTipo1Helpers = cartInfoDizionarioHelper.getEndoTipo1Helpers();
	for (EndoTipo1Helper endoTipo1Helper : endoTipo1Helpers) {
	    String codiceamministrazioneCart = endoTipo1Helper.getCodiceAmministrazioneCart();
	    ConfApplicative confApplicativeAMM = loadCofingApplicaticaByKey(keyAMM + codiceamministrazioneCart);
	    ConfApplicative confApplicativeMOVscia = loadCofingApplicaticaByKey(keyMOVscia + codiceamministrazioneCart);
	    ConfApplicative confApplicativeMOVORDINARIO = loadCofingApplicaticaByKey(keyMOVordinario + codiceamministrazioneCart);
	    ConfApplicative confApplicativeMOVCOMUNICAZIONE = loadCofingApplicaticaByKey(keyMOVcomunicazione + codiceamministrazioneCart);
	    Amministrazioni amministrazioni = new Amministrazioni();
	    if (confApplicativeAMM != null && StringUtils.isNotBlank(confApplicativeAMM.getValore())) {
		amministrazioni = amministrazioniService.findById(new PkId(Integer.parseInt(confApplicativeAMM.getValore())));
		endoTipo1Helper.setAmministrazioni(amministrazioni);
	    }
	    if (confApplicativeMOVscia != null && StringUtils.isNotBlank(confApplicativeMOVscia.getValore())) {
		Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(confApplicativeMOVscia.getValore()));
		endoTipo1Helper.setTipimovimento(tipimovimento);
	    }
	    if (confApplicativeMOVORDINARIO != null && StringUtils.isNotBlank(confApplicativeMOVORDINARIO.getValore())) {
		Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(confApplicativeMOVORDINARIO.getValore()));
		endoTipo1Helper.setTipimovimentoOrdinario(tipimovimento);
	    }
	    if (confApplicativeMOVCOMUNICAZIONE != null && StringUtils.isNotBlank(confApplicativeMOVCOMUNICAZIONE.getValore())) {
		Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(confApplicativeMOVCOMUNICAZIONE.getValore()));
		endoTipo1Helper.setTipimovimentoComunicazione(tipimovimento);
	    }
	}
	log.debug("loadPreferenzeElaborazione# Fine recupero preferenze applicative");
	return cartInfoDizionarioHelper;
    }

    private List<String> eseguiAggiornamentoConfigurazioni(CartInfoDizionarioHelper cartInfoDizionarioHelper) {

	List<String> msgs = new ArrayList<String>();
	// Controllo di aver passato tutti i dati
	// Estraggo i codici amministrazioni passati
	log.debug("eseguiAggiornamentoConfigurazioni#Inizio Aggiornamento e verifica Amministrazioni e Movimenti da associare.....");
	String[] codiciAmministrazioni = StringUtils.splitPreserveAllTokens(cartInfoDizionarioHelper.getCodiciAmministrazioni(), ",");
	// Estraggo i codici amministrazioni cart trovati (parte testuale della tipologia endo 1 trovata)
	String[] codiciAmministrazioniCart = cartInfoDizionarioHelper.getCodiciAmministrazioniCart().split(",");
	//Estraggo i codici dei tipi movimento passati
	String[] codiciTipoMov = StringUtils.splitPreserveAllTokens(cartInfoDizionarioHelper.getCodiciTipimovimento(), ",");
	String[] codiciMovimentoOrdinario = StringUtils.splitPreserveAllTokens(cartInfoDizionarioHelper.getCodiciTipimovimentoOrdinario().trim(),
		",");
	String[] codiciMovimentoComunicazione = StringUtils
		.splitPreserveAllTokens(cartInfoDizionarioHelper.getCodiciTipimovimentoComunicazione().trim(), ",");
	boolean checkConfigurazioneAmministrazioni = true; //amministrazioniService.updateAmministrazioniCartAndValidateConfiguration(codiciAmministrazioni,codiciAmministrazioniCart);
	boolean checkConfigurazioniTipimovimentoCart = true; //amministrazioniService.updateTipimovimentoCar
	List<EndoTipo1Helper> list = cartInfoDizionarioHelper.getEndoTipo1Helpers();
	for (EndoTipo1Helper et1h : list) {
	    String codiceAmministrazioneCART = et1h.getCodiceAmministrazioneCart();
	    for (int i = 0; i < codiciAmministrazioniCart.length; i++) {
		String cartc = codiciAmministrazioniCart[i];
		String codiceAmministrazione = codiciAmministrazioni[i];
		String tipomovimento = codiciTipoMov[i];
		String tipomovimentoOrdinario = codiciMovimentoOrdinario[i];
		String tipomovimentoComunicazione = codiciMovimentoComunicazione[i];
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
		    if (StringUtils.isNotBlank(tipomovimentoOrdinario)) {
			Tipimovimento tm = tipiMovimentoService.findById(new TipimovimentoId(tipomovimentoOrdinario));
			et1h.setTipimovimento(tm);
		    } else {
			if (EntityUtils.getNestedProperty(et1h.getTipimovimentoOrdinario(), "id.tipomovimento") == null) {
			    checkConfigurazioniTipimovimentoCart = false;
			}
		    }
		    if (StringUtils.isNotBlank(tipomovimentoComunicazione)) {
			Tipimovimento tm = tipiMovimentoService.findById(new TipimovimentoId(tipomovimentoComunicazione));
			et1h.setTipimovimento(tm);
		    } else {
			if (EntityUtils.getNestedProperty(et1h.getTipimovimentoComunicazione(), "id.tipomovimento") == null) {
			    checkConfigurazioniTipimovimentoCart = false;
			}
		    }
		    break;
		}
	    }
	}
	// Estraggo i codici delle azioni passate
	// String[] codiciAzioni = cartInfoDizionarioHelper.getCodiciAzioni().split(",");
	// Estraggo i codici delle tipologie endo 2 passate
	// String[] codiciTipologieEndo2 = cartInfoDizionarioHelper.getCodiceTipologieEndo2().split(",");
	// Estraggo le descrizioni delle tipologio endo 2 passate
	// String[] descrizioneTipologieEndo2 = cartInfoDizionarioHelper.getDescrizioneTipologieEndo2().split(",");
	if (checkConfigurazioneAmministrazioni == false || checkConfigurazioniTipimovimentoCart == false) {
	    String mgsPrincipale = getMessageFromBundle("service_error.stp.elaborazione_messaggi_dizionario", null);
	    msgs.add(mgsPrincipale);
	    msgs.add("<ol>");
	    if (checkConfigurazioneAmministrazioni == false) {
		msgs.add("<li>Non sono state configurate le amministrazioni per le tipologie di endo trovate</li>");
	    }
	    if (checkConfigurazioniTipimovimentoCart == false) {
		msgs.add("<li>Non sono stati configurati i tipi movimenti per le tipologie di endo trovate</li>");
	    }
	    msgs.add("</ol>");
	    msgs.add("Per eseguire l'elaborazione completare la configurazione sul pannello visibile all'invocazione della funzionalità \"Elabora\"");
	    FlashMessages.setWarnings(msgs);
	}
	return msgs;
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(Object entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(Object entity) {

	// TODO Auto-generated method stub
    }
}
