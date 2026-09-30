package it.gruppoinit.pal.gp.areariservata.web.util;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.util.EmptyStackException;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlBackHelper {

    private static final Logger log = LoggerFactory.getLogger(UrlBackHelper.class);

    public static void set(String urlBack, HttpServletRequest request) {

	Stack<String> history = getHistoryStack(request);
	history.push(urlBack);
	log.debug("push url [{}]", urlBack);
    }

    public static String get(String defaultUrlTo, HttpServletRequest request) {

	Stack<String> history = getHistoryStack(request);
	try {
	    defaultUrlTo = history.pop();
	    log.debug("get url [{}]", defaultUrlTo);
	} catch (EmptyStackException e) {
	    log.debug("get default url [{}]", defaultUrlTo);
	}
	return defaultUrlTo;
    }

    @SuppressWarnings("unchecked")
    private static Stack<String> getHistoryStack(HttpServletRequest request) {

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

    private static void setHistoryStack(HttpServletRequest request, Stack<String> history) {

	request.getSession().setAttribute(WebConstants.HISTORY_BUFFER, history);
    }
}
