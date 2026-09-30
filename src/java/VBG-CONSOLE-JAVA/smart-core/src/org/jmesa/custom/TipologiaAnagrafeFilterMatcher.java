package org.jmesa.custom;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.message.SpringMessages;
import org.jmesa.web.SpringWebContext;
import org.jmesa.web.WebContext;
import org.jmesa.web.WebContextSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author gianpaolot
 * 
 */
public class TipologiaAnagrafeFilterMatcher implements FilterMatcher, WebContextSupport {

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
	    String siMessage = messages.getMessage("list.jmesa.celleditor.si");
	    String noMessage = messages.getMessage("list.jmesa.celleditor.no");
	    String si = null;
	    if (siMessage != null) {
		si = StringUtils.lowerCase(siMessage);
	    }
	    String no = null;
	    if (noMessage != null) {
		no = StringUtils.lowerCase(noMessage);
	    }
	    if (si == null) {
		si = "???list.jmesa.celleditor.si???";
	    }
	    if (no == null) {
		no = "???list.jmesa.celleditor.no???";
	    }
	    if ((filter.equals(si) && item.equals("-1")) || (filter.equals(no) && item.equals("0"))) {
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
