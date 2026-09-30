package it.gruppoinit.pal.gp.areariservata.web;

import it.gruppoinit.pal.gp.areariservata.web.util.DeployProperties;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Sdeproxy;
import it.gruppoinit.pal.gp.core.service.SdeproxyService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;
import it.init.sigepro.rte.types.ErroreType;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);
    private static String ERRORE = "_ERRORE";
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private SdeproxyService sdeproxyService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private DeployProperties deployProperties;

    /**
     * Mette in sessione un messaggio di errore. Utile in caso di redirect
     * 
     * @param request
     * @param messaggioErrore
     */
    protected void setErroreInSessione(HttpServletRequest request, String messaggioErrore) {

	request.getSession().setAttribute(ERRORE, messaggioErrore);
    }

    /**
     * torna il messaggio di errore messo in sessione e rimuove il messaggio di errore dalla sessione
     * 
     * @param request
     * @return
     */
    protected String getErroreInSessione(HttpServletRequest request) {

	String retVal = (String) request.getSession().getAttribute(ERRORE);
	request.getSession().removeAttribute(ERRORE);
	return retVal;
    }

    /**
     * metodo per ricavare il valore del parametro ReturnTo dalla stringa salvata nella variabile di sessione
     * URL_FIRST_REQUEST se il parametro non è presente il metodo restituisce stringa vuota.
     * 
     * @param request
     * @return
     */
    protected String getReturnTo(HttpServletRequest request) {

	log.debug("getReturnTo");
	String urlFirstReq = (String) request.getSession().getAttribute(WebConstants.URL_FIRST_REQUEST);
	String returnTo = "";
	try {
	    urlFirstReq = URLDecoder.decode(urlFirstReq, "UTF-8");
	    int idx = urlFirstReq.indexOf(WebConstants.RETURNTO);
	    if (idx != -1) {
		String temp = urlFirstReq.substring(idx + WebConstants.RETURNTO.length() + 1);
		int idx1 = temp.indexOf("&");
		if (idx1 == -1) {
		    idx1 = temp.length();
		}
		returnTo = URLDecoder.decode(temp.substring(0, idx1), "UTF-8");
	    }
	} catch (Exception e) {
	    log.error("getReturnTo", e);
	}
	return returnTo;
    }

    protected void removeTokenFromUrlFirstReq(HttpServletRequest request) throws Exception {

	log.debug("removeTokenFromUrlFirstReq");
	String urlFirstReq = (String) request.getSession().getAttribute(WebConstants.URL_FIRST_REQUEST);
	urlFirstReq = URLDecoder.decode(urlFirstReq, "UTF-8");
	int idx = urlFirstReq.indexOf("&" + WebConstants.TOKEN);
	if (idx != -1) {
	    String pre = urlFirstReq.substring(0, idx);
	    String post = urlFirstReq.substring(idx + 1 + WebConstants.TOKEN.length() + 1);
	    if (post.indexOf("&") != -1) {
		post = post.substring(post.indexOf("&"));
	    } else {
		post = "";
	    }
	    urlFirstReq = pre + post;
	    request.getSession().setAttribute(WebConstants.URL_FIRST_REQUEST, urlFirstReq);
	}
    }

    protected Boolean isCentroServizi(HttpServletRequest request) {

	return (Boolean) request.getSession().getAttribute("CENTRO_SERVIZI");
    }

    protected Boolean isUrlBrevi(HttpServletRequest request) {

	return (Boolean) request.getSession().getAttribute("CENTRO_SERVIZI_URL_BREVI");
    }

    @SuppressWarnings("unchecked")
    protected void addErrorToModel(String errMessage, Model model) {

	ErroreType et = new ErroreType();
	et.setDescrizione(errMessage);
	List<ErroreType> errors = null;
	if (model.containsAttribute("errors")) {
	    Map<String, Object> attributes = model.asMap();
	    errors = (List<ErroreType>) attributes.get("errors");
	    errors.add(et);
	} else {
	    errors = new ArrayList<ErroreType>();
	    errors.add(et);
	    model.addAttribute("errors", errors);
	}
    }

    @SuppressWarnings("unchecked")
    protected void addErrorsToModel(List<ErroreType> errors, Model model) {

	if (model.containsAttribute("errors")) {
	    Map<String, Object> attributes = model.asMap();
	    List<ErroreType> _errors = (List<ErroreType>) attributes.get("errors");
	    _errors.addAll(errors);
	} else {
	    model.addAttribute("errors", errors);
	}
    }

    protected void addErrorsToResult(List<ErroreType> errors, BindingResult result) {

	for (ErroreType erroreType : errors) {
	    result.reject("", null, erroreType.getDescrizione());
	}
    }

    public String verificaIdEnte(HttpServletRequest request, boolean resetComuneSelezionato) {

	String idEnte = ORMHelper.getIdente();
	if (StringUtils.isBlank(idEnte)) {
	    log.error("IdEnte non valido: {}", request.getQueryString());
	    throw new SecurityException("Inizializzazione applicativo non riuscita");
	}
	Sdeproxy p = getProxy(idEnte, true);
	request.getSession().setAttribute(WebConstants.ENTE_IN_SESSION_VARIABLE_NAME, p.getDescrizione());
	if (resetComuneSelezionato) {
	    List<Comuni> list = sdeproxyService.findComuniAssociati(idEnte);
	    Comuni comuneSel = new Comuni();
	    if (list.size() == 1) {
		comuneSel = list.get(0);
	    }
	    request.getSession().setAttribute(WebConstants.COMUNE_SELEZIONATO_DOMANDA_ATTIVA_SESSION_VARIABLE_NAME, comuneSel);
	}
	return idEnte;
    }

    public List<Comuni> findlistaComuniPerIdEnte(HttpServletRequest request, String idente) {

	verificaIdEnte(request, false);
	List<Comuni> list = sdeproxyService.findComuniAssociati(idente);
	return list;
    }

    protected Sdeproxy getProxy(String idEnte, boolean rilanciaEccezioneSeNonTrovato) {

	Sdeproxy sdeproxy = sdeproxyService.findById(idEnte);
	if (sdeproxy == null) {
	    if (rilanciaEccezioneSeNonTrovato) {
		log.error("IdEnte non configurato: {}", idEnte);
		throw new SecurityException("Inizializzazione applicativo non riuscita. idente [" + idEnte + "] non valido");
	    }
	}
	return sdeproxy;
    }

    protected Sdeproxy getProxyPerDomanda(String idEnte) {

	Sdeproxy sdeproxy = getProxy(idEnte, true);
	//	if (StringUtils.isBlank(sdeproxy.getNomePdPresdom())) {
	//	    log.error("L'IdEnte non ha configurato il nome porta di dominio: {}", idEnte);
	//	    throw new SecurityException("Inizializzazione applicativo non riuscita. idente [" + idEnte + "] non correttamente configurato");
	//	}
	return sdeproxy;
    }

    public boolean checkAccessoComunica(HttpServletRequest request) {

	Boolean accessoComunica = (Boolean) request.getSession().getAttribute(WebConstants.ACCESSO_SERVIZIO_COMUNICA);
	return BooleanUtils.isTrue(accessoComunica);
    }

    /**
     * In caso di accesso al servizio nuovadomanda si deve controllare che se l'utente è già autenticato e COMUNICA true
     * il CF deve essere quello dell'utente anonimo
     * 
     * @param req
     */
    public void checkAccessoComunicaPerNuovaDomanda(HttpServletRequest req) {

	if (checkAccessoComunica(req)) {
	    String cfUtente = deployProperties.getServiziUserid();
	    Anagrafe s = (Anagrafe) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    if (s == null) {
		// UTENTE NON ANCORA AUTENTICATO
		return;
	    }
	    if (!StringUtils.defaultString(cfUtente, "GGGGGGGGGGGGGGGG").equalsIgnoreCase(s.getCodicefiscale())) {
		log.error("Tentativo di accesso al servizio {} con l'utente {}", req.getRequestURI() + "?" + req.getQueryString(),
			s.getCodicefiscale());
		throw new RuntimeException("Il servizio è riservato ad utenti autenticati. si prega di chiudere il browser e riprovare.");
	    }
	}
    }

    /**
     * metodo per il recupero delle info principali dell'utente loggato
     * 
     * @return
     */
    protected UserDetails getCurrentlyAuthenticatedUser() {

	return userSecurityService.getCurrentlyAuthenticatedUser();
    }

    /**
     * metodo per il recupero dei dettagli dell'utente loggato
     * 
     * @return
     */
    protected Anagrafe getCurrentlyAuthenticatedUserDetails() {

	Object a = userSecurityService.getCurrentlyAuthenticatedUserDetails();
	if (a != null) {
	    if (a instanceof Anagrafe) {
		return (Anagrafe) a;
	    }
	    throw new SecurityException("L'utente loggato non sembra appartenere all'anagrafe " + getCurrentlyAuthenticatedUser().getUsername());
	}
	return null;
    }
}
