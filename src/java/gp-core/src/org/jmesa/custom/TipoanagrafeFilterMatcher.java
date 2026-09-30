package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

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
public class TipoanagrafeFilterMatcher implements FilterMatcher, WebContextSupport {

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
	    String fisicaMessage = messages.getMessage("list.jmesa.celleditor.persona_fisica");
	    String giuridicaMessage = messages.getMessage("list.jmesa.celleditor.persona_giuridica");
	    String fisica = null;
	    if (fisicaMessage != null) {
		fisica = StringUtils.lowerCase(fisicaMessage);
	    }
	    String giuridica = null;
	    if (giuridicaMessage != null) {
		giuridica = StringUtils.lowerCase(giuridicaMessage);
	    }
	    if (fisica == null) {
		fisica = "???list.jmesa.celleditor.persona_fisica???";
	    }
	    if (giuridica == null) {
		giuridica = "???list.jmesa.celleditor.persona_giuridica???";
	    }
	    if ((filter.equals(fisica) && item.toUpperCase().equals(WebConstants.PERSONA_FISICA) || (filter.equals(giuridica) && item.toUpperCase()
		    .equals(WebConstants.PERSONA_GIURIDICA)))) {
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
