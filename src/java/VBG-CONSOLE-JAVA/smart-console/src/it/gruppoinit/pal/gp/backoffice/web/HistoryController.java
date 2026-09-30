package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HistoryController {

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

	urlTo = decode(urlTo);
	Stack<String> history = getHistoryStack(request);
	history.clear();
	if (log.isDebugEnabled()) {
	    log.debug("history clear! redirect to: " + urlTo);
	}
	return "redirect:" + urlTo;
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

	urlBack = decode(urlBack);
	Stack<String> history = getHistoryStack(request);
	history.push(urlBack);
	if (log.isDebugEnabled()) {
	    log.debug("history set url: " + urlBack);
	    log.debug("history set! redirect to: " + urlTo);
	}
	return "redirect:" + urlTo;
    }

    @RequestMapping
    public void ajaxSet(@RequestParam(value = WebConstants.RETURNTO) String urlBack, HttpServletRequest request) {

	// urlTo = decode(urlTo);
	urlBack = decode(urlBack);
	Stack<String> history = getHistoryStack(request);
	history.push(urlBack);
	if (log.isDebugEnabled()) {
	    log.debug("history stack push: " + urlBack);
	    log.debug("history stack size: " + history.size());
	    log.debug("history stack ordered elements: " + history);
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
		log.debug("history back! redirect to: " + urlTo);
	    }
	} catch (EmptyStackException e) {
	    log.warn("history stack is empty! redirect to: " + urlTo);
	}
	return "redirect:" + urlTo;
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
}
