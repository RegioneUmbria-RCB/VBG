package org.jmesa.custom;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.message.SpringMessages;
import org.jmesa.web.SpringWebContext;
import org.jmesa.web.WebContext;
import org.jmesa.web.WebContextSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatoCommissioneEdiliziaTFilterMatcher implements FilterMatcher, WebContextSupport {

    private WebContext webContext;
    private static final Logger log = LoggerFactory.getLogger(TipologiaAnagrafeFilterMatcher.class);

    /*
     * controllo se il valore è true,false.
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
	    String apertaCommissione = messages.getMessage("list.jmesa.celleditor.aperta");
	    String chiusaCommissione = messages.getMessage("list.jmesa.celleditor.chiusa");
	    String aperta = null;
	    if (apertaCommissione != null) {
		aperta = StringUtils.lowerCase(apertaCommissione);
	    }
	    String chiusa = null;
	    if (chiusaCommissione != null) {
		chiusa = StringUtils.lowerCase(chiusaCommissione);
	    }
	    if (aperta == null) {
		aperta = "???list.jmesa.celleditor.aperta???";
	    }
	    if (chiusa == null) {
		chiusa = "???list.jmesa.celleditor.chiusa???";
	    }
	    if ((filter.equals(aperta) && item.equals("true")) || (filter.equals(chiusa) && item.equals("false"))) {
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
