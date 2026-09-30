package it.gruppoinit.pal.gp.backoffice.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.features.sistema.IVerticalizzazioneParametriSistemaService;
import it.gruppoinit.pal.gp.core.features.sistema.SecurityUtils;

@Controller
public class HistoryController extends BaseController {

    @Autowired
    private IVerticalizzazioneParametriSistemaService verticalizzazioneParametriSistemaService;
    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);

    /**
     * metodo per lo svuotamento del buffer history
     * 
     * @param urlTo
     *            url di destinazione
     * @param request
     * @return
     */
    @RequestMapping
    public String clear(@RequestParam(value = WebConstants.GOTO) String urlTo, HttpServletRequest request) {

	checkUrlEsterniEParametri(urlTo);
	urlTo = decode(urlTo);
	Stack<String> history = getHistoryStack(request);
	history.clear();
	log.debug("history clear! redirect to: {}", urlTo);
	return WebConstants.SPRING_REDIRECT_TO + urlTo;
    }

    /**
     * metodo per l'inserimento di un url nel buffer history
     * 
     * @param urlTo
     *            url di destinazione
     * @param urlBack
     *            url di ritorno (da inserire nel buffer)
     * @param request
     * @return
     */
    @RequestMapping
    public String set(@RequestParam(value = WebConstants.GOTO) String urlTo, @RequestParam(value = WebConstants.RETURNTO) String urlBack,
	    HttpServletRequest request) {

	checkUrlEsterniEParametri(urlBack);
	checkUrlEsterniEParametri(urlTo);
	urlBack = decode(urlBack);
	Stack<String> history = getHistoryStack(request);
	history.push(urlBack);
	log.debug("history set url: {}, redirect to: {}", urlBack, urlTo);
	return WebConstants.SPRING_REDIRECT_TO + urlTo;
    }

    @RequestMapping
    public void ajaxSet(@RequestParam(value = WebConstants.RETURNTO) String urlBack, HttpServletRequest request) {

	checkUrlEsterniEParametri(urlBack);
	urlBack = decode(urlBack);
	Stack<String> history = getHistoryStack(request);
	history.push(urlBack);
	if (log.isDebugEnabled()) {
	    log.debug("history stack push: {}, stack size: {}, stack ordered elements: {} ", new Object[] { urlBack, history.size(), history });
	}
    }

    /**
     * metodo per il recupero dell'url corretto di destinazione
     * 
     * @param urlTo
     *            url di destinazione di default (utilizzato in caso di buffer vuoto)
     * @param request
     * @return
     */
    @RequestMapping
    public String back(@RequestParam(value = WebConstants.GOTO) String urlTo, HttpServletRequest request) {

	urlTo = decode(urlTo);
	Stack<String> history = getHistoryStack(request);
	try {
	    urlTo = history.pop();
	    if (log.isDebugEnabled()) {
		log.debug("history back! redirect to: {}", urlTo);
	    }
	} catch (EmptyStackException e) {
	    log.warn("history stack is empty! redirect to: {}", urlTo);
	}
	checkUrlEsterniEParametri(urlTo);
	urlTo = pulisciUrlFinale(urlTo);
	return WebConstants.SPRING_REDIRECT_TO + urlTo;
    }

    private String replaceCharsNonRfc3986Rfc7230(String urlTo) {

	if(StringUtils.isNotBlank(urlTo)) {
	    urlTo = urlTo.replace("[", "%5B");
	    urlTo = urlTo.replace("]", "%5D");
	    urlTo = urlTo.replace("|", "%7C");
	}
	return urlTo;
    }
    
    private String encodeUtf8MultiByteChars(String urlTo){

	String urlFinale = urlTo;
	try {	    	    
	    for (char c : urlTo.toCharArray()) {
		if (c > 127) {
		    log.debug("Individuato carattere non ASCII: {}", c);
		    String cEncoded = URLEncoder.encode(String.valueOf(c), "UTF-8");
		    log.debug("Carattere {} encoded = {}", c, cEncoded);
		    urlFinale = urlFinale.replaceFirst(String.valueOf(c), cEncoded);
		}
	    }
	} catch (Exception e) {
	    log.error("Errore durante l encoding", e);
	    urlFinale = urlTo;
	}
	return urlFinale;
    }
    
    private String pulisciUrlFinale(String urlTo){
	return encodeUtf8MultiByteChars(replaceCharsNonRfc3986Rfc7230(urlTo));
    }

    @SuppressWarnings("unchecked")
    private Stack<String> getHistoryStack(HttpServletRequest request) {

	Stack<String> history = (Stack<String>) request.getSession().getAttribute(WebConstants.HISTORY_BUFFER);
	if (history == null) {
	    history = new Stack<String>();
	    setHistoryStack(request, history);
	    if (log.isDebugEnabled()) {
		log.debug("history stack created!");
	    }
	}
	return history;
    }

    private void setHistoryStack(HttpServletRequest request, Stack<String> history) {

	request.getSession().setAttribute(WebConstants.HISTORY_BUFFER, history);
    }

    private String decode(String enc) {

	String dec = "";
	try {
	    dec = URLDecoder.decode(enc, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    log.error(e.getMessage());
	}
	return dec;
    }

    private void checkUrlEsterniEParametri(String urlTo) {

	SecurityUtils.checkUrlEsterni(urlTo, verticalizzazioneParametriSistemaService.attivaComportamentiSicurezza());
    }

    @Override
    protected void setPageAttributes(Model model) {

	// niente da gestire
    }

    @Override
    protected void fixMergeEntityProperty(Object entity) {

	// niente da gestire
    }

    @Override
    protected void fixRenderEntityProperty(Object entity) {

	// niente da gestire
    }
}
