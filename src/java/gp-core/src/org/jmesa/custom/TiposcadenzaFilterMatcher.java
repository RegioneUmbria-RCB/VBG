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

public class TiposcadenzaFilterMatcher implements FilterMatcher, WebContextSupport {

    private WebContext webContext;
    private static final Logger log = LoggerFactory.getLogger(TipibandoFilterMatcher.class);

    @Override
    public boolean evaluate(Object itemValue, String filterValue) {

	try {
	    String item = StringUtils.lowerCase(String.valueOf(itemValue));
	    String filter = StringUtils.lowerCase(String.valueOf(filterValue));
	    SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	    String scadenzaMessage = messages.getMessage("list.jmesa.celleditor.scadenza");
	    String avvisoMessage = messages.getMessage("list.jmesa.celleditor.avviso");
	    String interdizioneMessage = messages.getMessage("list.jmesa.celleditor.interdizione");
	    String avviso = null;
	    if (avvisoMessage != null) {
		avviso = StringUtils.lowerCase(avvisoMessage);
	    }
	    String scadenza = null;
	    if (scadenzaMessage != null) {
		scadenza = StringUtils.lowerCase(scadenzaMessage);
	    }
	    String interdizione = null;
	    if (interdizioneMessage != null) {
		interdizione = StringUtils.lowerCase(interdizioneMessage);
	    }
	    if (avviso == null) {
		avviso = "???list.jmesa.celleditor.avviso???";
	    }
	    if (scadenza == null) {
		scadenza = "???list.jmesa.celleditor.scadenza???";
	    }
	    if (interdizione == null) {
		interdizione = "???list.jmesa.celleditor.interdizione???";
	    }
	    if ((filter.equals(scadenza) && item.equals(WebConstants.SCADENZA.toLowerCase()))
		    || (filter.equals(avviso) && item.equals(WebConstants.AVVISO.toLowerCase()))
		    || (filter.equals(interdizione) && item.equals(WebConstants.INTERDETTI.toLowerCase()))) {
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
