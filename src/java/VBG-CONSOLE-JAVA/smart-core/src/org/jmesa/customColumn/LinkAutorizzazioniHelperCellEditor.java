package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.view.editor.AbstractCellEditor;

public class LinkAutorizzazioniHelperCellEditor extends AbstractCellEditor {

    private String uriBack;

    public LinkAutorizzazioniHelperCellEditor(HttpServletRequest request, String uriBack) {

	super();
	this.uriBack = Utilities.buildHistoryBackFromRequest(request, uriBack);
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Object oggetto = (Object) item;
	BigDecimal codiceistanza = (BigDecimal) UtilityJmesa.getParametro(oggetto, "codiceistanza");
	BigDecimal countautorizzazioni = null;
	countautorizzazioni = (BigDecimal) UtilityJmesa.getParametro(oggetto, property);
	String valueItem = "";
	if (countautorizzazioni.intValue() > 0) {
	    String uriTo = "../autorizzazioni/create.htm?codiceIstanza=" + codiceistanza;
	    String linkText = "";
	    linkText = getCoreContext().getMessage("label.A");
	    try {
		uriTo = URLEncoder.encode(uriTo, "UTF-8");
	    } catch (Exception e) {
	    }
	    String anchorName = Utilities.getHashText(uriTo, Utilities.ALGORITHM_MD5, false);
	    String anchorNameEncoded = "";
	    try {
		anchorNameEncoded = URLEncoder.encode("#" + anchorName, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	    }
	    String historySetUrl = "../history/set.htm?ReturnTo=" + uriBack + anchorNameEncoded + "&" + WebConstants.GOTO + "=" + uriTo;
	    valueItem = "<a name=\"" + anchorName + "\" id=\"link_autAndConc_id" + rowcount + "\"  href=\"" + historySetUrl
		    + "\" onmouseover=\"ajaxCall('link_autAndConc_id" + rowcount
		    + "','../autorizzazioni/ajaxFindAutorizzazioniAndConcessioniByIstanza.htm?codiceIstanza=" + codiceistanza + "')\" >" + linkText;
	}
	return valueItem;
    }
}
