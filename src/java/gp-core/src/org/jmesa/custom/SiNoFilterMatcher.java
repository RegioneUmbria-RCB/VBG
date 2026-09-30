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
 * @author francescop
 * 
 */
public class SiNoFilterMatcher implements FilterMatcher, WebContextSupport {

    private WebContext webContext;
    private static final Logger log = LoggerFactory.getLogger(SiNoFilterMatcher.class);

    @Override
    public boolean evaluate(Object itemValue, String filterValue) {

	try {
	    String item = StringUtils.lowerCase(String.valueOf(itemValue));
	    String filter = StringUtils.lowerCase(String.valueOf(filterValue));
	    // In alcuni casi il filtro jmesa creato da una dropList che contiene "si" e "no" li può converire
	    // ai valori boolenai 0 e 1. Per non modificare la logica di confronto andremo a normalizzare tali valori, secondo la logica
	    // Se la stringa passata è un valore numerico allora allora :
	    // 1="si"
	    // 0="no"
	    // Nel caso il valore non sia numeri allora non facciamo niente
	    try {
		if (StringUtils.isNotBlank(filter)) {
		    Integer.parseInt(filter);
		    filter = (filter.equals("1") ? "si" : "no");
		} else {
		    filter = "no";
		}
	    } catch (Exception e) {
		log.debug("Il valore passato dal filtro della jmesa table è gia normalizzato a si e no");
	    }
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
	    // BOCCI 2012-08-29: NULL PER UN VALORE BOOLEANO È = FALSE
	    // PER CUI QUANDO FILTRO USO ANCHE NULL PER I VALORI NEGATIVI
	    //if ((filter.equals(si) && item.equals("true")) || (filter.equals(no) && item.equals("false") )) {
	    if ((filter.equals(si) && item.equals("true")) || (filter.equals(no) && (item.equals("false") || item.equalsIgnoreCase("null")))) {
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
