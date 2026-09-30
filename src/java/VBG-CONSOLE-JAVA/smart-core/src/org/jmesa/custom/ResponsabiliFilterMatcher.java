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

/**
 * @author francescop
 * 
 */
public class ResponsabiliFilterMatcher implements FilterMatcher, WebContextSupport {

    private WebContext webContext;

    @Override
    public boolean evaluate(Object itemValue, String filterValue) {

	try {
	    String item = StringUtils.lowerCase(String.valueOf(itemValue));
	    String filter = StringUtils.lowerCase(String.valueOf(filterValue));
	    SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	    String siMessage = messages.getMessage("responsabili.label.amministratori_value.table");
	    String si = null;
	    if (siMessage != null) {
		si = StringUtils.lowerCase(siMessage);
	    }
	    if (si == null) {
		si = "???responsabili.label.amministratori_value.table???";
	    }
	    if ((filter.equals(si) && item.equals("1"))) {
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
