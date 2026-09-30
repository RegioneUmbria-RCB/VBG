package org.jmesa.custom;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.message.SpringMessages;
import org.jmesa.web.SpringWebContext;
import org.jmesa.web.WebContext;
import org.jmesa.web.WebContextSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatoAnagrafeFilterMatcher implements FilterMatcher, WebContextSupport {

    private WebContext webContext;
    private static final Logger log = LoggerFactory.getLogger(StatoAnagrafeFilterMatcher.class);

    /*
     * controllo se il valore è true,false e null.
     * 
     * @param itemValue : valore della proprietà del bean
     * 
     * @param filterValue : valore inserito nel filtro
     * 
     * @return: true if match , false if unmatch
     */
    @Override
    public boolean evaluate(Object itemValue, String filterValue) {

	try {
	    String item = StringUtils.lowerCase(String.valueOf(itemValue));
	    String filter = StringUtils.lowerCase(String.valueOf(filterValue));
	    SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	    String attivoMessage = messages.getMessage("list.jmesa.celleditor.attivo");
	    String disattivoMessage = messages.getMessage("list.jmesa.celleditor.disattivo");
	    String sospesoMessage = messages.getMessage("list.jmesa.celleditor.sospeso");
	    String attivo = null;
	    if (attivoMessage != null) {
		attivo = StringUtils.lowerCase(attivoMessage);
	    }
	    String disattivo = null;
	    if (disattivoMessage != null) {
		disattivo = StringUtils.lowerCase(disattivoMessage);
	    }
	    String sospeso = null;
	    if (sospesoMessage != null) {
		sospeso = StringUtils.lowerCase(sospesoMessage);
	    }
	    if (attivo == null) {
		attivo = "???list.jmesa.celleditor.attivo???";
	    }
	    if (disattivo == null) {
		disattivo = "???list.jmesa.celleditor.disattivo???";
	    }
	    if (sospeso == null) {
		sospeso = "???list.jmesa.celleditor.sospeso???";
	    }
	    if ((filter.equals(attivo) && item.equals("0")) || (filter.equals(disattivo) && item.equals("1"))) {
		return true;
	    }
	    if ((filter.equals(sospeso) && item.equals("2"))) {
		return true;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    log.debug("Error in Filter Matcher");
	    return false;
	}
	return false;
    }

    @Override
    public WebContext getWebContext() {

	return this.webContext;
    }

    @Override
    public void setWebContext(WebContext arg0) {

	this.webContext = arg0;
    }
}
