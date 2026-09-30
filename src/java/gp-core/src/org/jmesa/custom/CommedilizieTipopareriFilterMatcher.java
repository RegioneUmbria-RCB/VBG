package org.jmesa.custom;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.web.WebContext;
import org.jmesa.web.WebContextSupport;

public class CommedilizieTipopareriFilterMatcher implements FilterMatcher, WebContextSupport {

    private WebContext webContext;

    @Override
    public boolean evaluate(Object itemValue, String filterValue) {

	try {
	    String item = StringUtils.lowerCase(String.valueOf(itemValue));
	    String filter = StringUtils.lowerCase(String.valueOf(filterValue));
	    if ((filter.equals(item) || (filter.equals("???label.esito???") && item.equals("null")))) {
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
