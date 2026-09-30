package org.jmesa.filter;

/**
 * 
 */
import org.apache.commons.lang.StringUtils;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.web.SpringWebContext;
import org.jmesa.web.WebContext;
import org.jmesa.web.WebContextSupport;

public class SiNoFilterMatcher extends DroplistFilterEditor implements FilterMatcher, WebContextSupport {

    private WebContext webContext;

    @Override
    public boolean evaluate(Object itemValue, String filterValue) {

	try {
	    String item = StringUtils.lowerCase(String.valueOf(itemValue));
	    String filter = StringUtils.lowerCase(String.valueOf(filterValue));
	    SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	    String siMessage = messages.getMessage("label.si");
	    String noMessage = messages.getMessage("label.no");
	    //System.out.println(getCoreContext());
	    String si = null;
	    if (siMessage != null) {
		si = StringUtils.lowerCase(siMessage);
	    }
	    String no = null;
	    if (noMessage != null) {
		no = StringUtils.lowerCase(noMessage);
	    }
	    if (si == null) {
		si = "???label.si???";
	    }
	    if (no == null) {
		no = "???label.no???";
	    }
	    if ((filter.equals(si) && item.equals("true")) || (filter.equals(no) && item.equals("false"))) {
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