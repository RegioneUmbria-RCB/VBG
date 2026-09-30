package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.service.VwIAttivitaautorizzazioniService;

import java.math.BigDecimal;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.view.editor.AbstractCellEditor;
import org.springframework.web.context.ContextLoader;

public class LinkAutorizzazioniIstanzeAttivitaCellEditor extends AbstractCellEditor {

    public LinkAutorizzazioniIstanzeAttivitaCellEditor(HttpServletRequest request) {

    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	VwIAttivitaautorizzazioniService vwIAttivitaautorizzazioniService = (VwIAttivitaautorizzazioniService) ContextLoader
		.getCurrentWebApplicationContext().getBean("vwIAttivitaautorizzazioniServiceImpl", VwIAttivitaautorizzazioniService.class);
	Object oggetto = (Object) item;
	BigDecimal codiceattivita = (BigDecimal) UtilityJmesa.getParametro(oggetto, "id");
	String valueItem = "";
	int numAutt = vwIAttivitaautorizzazioniService.countByAttivita(new Integer(codiceattivita.intValue()));
	if (numAutt > 0) {
	    String uriTo = "../autorizzazioni/create.htm?codiceIstanza=" + codiceattivita;
	    String linkText = "";
	    linkText = getCoreContext().getMessage("label.A");
	    try {
		uriTo = URLEncoder.encode(uriTo, "UTF-8");
	    } catch (Exception e) {
	    }
	    String goTo = "javascript:void(0)";
	    //String historySetUrl = "../history/set.htm?ReturnTo=" + uriBack + "&" + WebConstants.GOTO + "=" + uriTo;
	    //	    valueItem = "<a id=\"link_autAndConc_id" + rowcount + "\"  href=\"" + historySetUrl + "\" onmouseover=\"ajaxCall('link_autAndConc_id"
	    //		    + rowcount + "','../autorizzazioni/ajaxFindAutorizzazioniAndConcessioniByAttivita.htm?codiceAttivita=" + codiceattivita + "')\" >"
	    //		    + linkText;
	    valueItem = "<a id=\"link_autAndConc_id" + rowcount + "\"  href=\"" + goTo + "\" onmouseover=\"ajaxCall('link_autAndConc_id" + rowcount
		    + "','../autorizzazioni/ajaxFindAutorizzazioniAndConcessioniByAttivita.htm?codiceAttivita=" + codiceattivita + "')\" >"
		    + linkText;
	}
	return valueItem;
    }
}
