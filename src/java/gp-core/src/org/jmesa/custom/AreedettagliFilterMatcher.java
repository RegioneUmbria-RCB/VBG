/**
 * 
 */
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
 * Filter Matcher Custom per fare il match tra il valore della label inserita nel filter input e il valore effettivo
 * presente nella proprietà del domain.
 * 
 * @author Francesco Palenga
 */
public class AreedettagliFilterMatcher implements FilterMatcher, WebContextSupport {

    private WebContext webContext;
    private static final Logger log = LoggerFactory.getLogger(AreedettagliFilterMatcher.class);

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
	    String pariMessage = messages.getMessage("form.areedettagli.droplist.pari");
	    String dispariMessage = messages.getMessage("form.areedettagli.droplist.dispari");
	    String tuttiMessage = messages.getMessage("form.areedettagli.droplist.tutti");
	    String pari = null;
	    if (pariMessage != null) {
		pari = StringUtils.lowerCase(pariMessage);
	    }
	    String dispari = null;
	    if (dispariMessage != null) {
		dispari = StringUtils.lowerCase(dispariMessage);
	    }
	    String tutti = null;
	    if (tuttiMessage != null) {
		tutti = StringUtils.lowerCase(tuttiMessage);
	    }
	    if (pari == null) {
		pari = "???form.areedettagli.droplist.pari???";
	    }
	    if (dispari == null) {
		dispari = "???form.areedettagli.droplist.dispari???";
	    }
	    if (tutti == null) {
		tutti = "???form.areedettagli.droplist.tutti???";
	    }
	    if ((filter.equals(pari) && item.equals("true")) || (filter.equals(dispari) && item.equals("false"))) {
		return true;
	    }
	    if ((filter.equals(tutti) && item.equals("null"))) {
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
