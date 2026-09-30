/**
 * 
 */
package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.message.SpringMessages;
import org.jmesa.web.SpringWebContext;
import org.jmesa.web.WebContext;
import org.jmesa.web.WebContextSupport;

/**
 * @author francescop
 * 
 */
public class TipoEntrataUscitaFilterMatcher implements FilterMatcher, WebContextSupport {

    private WebContext webContext;

    @Override
    public boolean evaluate(Object itemValue, String filterValue) {

	try {
	    String item = String.valueOf(itemValue);
	    String filter = StringUtils.lowerCase(String.valueOf(filterValue));
	    SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	    String entrataMessage = messages.getMessage("list.jmesa.celleditor.si");
	    String uscitaMessage = messages.getMessage("list.jmesa.celleditor.no");
	    String entrata = null;
	    if (entrataMessage != null) {
		entrata = StringUtils.lowerCase(entrataMessage);
	    }
	    String uscita = null;
	    if (uscitaMessage != null) {
		uscita = StringUtils.lowerCase(uscitaMessage);
	    }
	    if (entrata == null) {
		entrata = "???form.registrazioniInOut.tipo.e???";
	    }
	    if (uscita == null) {
		uscita = "???form.registrazioniInOut.tipo.u???";
	    }
	    String e = WebConstants.REGISTRAZIONIINOUT_TIPO_E;
	    String u = WebConstants.REGISTRAZIONIINOUT_TIPO_U;
	    if ((filter.equals(entrata) && item.equals(e)) || (filter.equals(uscita) && item.equals(u))) {
		return true;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
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
