package it.gruppoinit.pal.gp.areariservata.web;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.init.sigepro.rte.types.ErroreType;

public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);
    private static String ERRORE = "_ERRORE";

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

	String[] denylist = new String[] { "class.*", "Class.*", "*.class.*", "*.Class.*" };
	binder.setDisallowedFields(denylist);
    }

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
}
