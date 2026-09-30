package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class LinkIstanzeprocedimentiHelperCellEditor extends AbstractCellEditor {

    private String codiceIstanza;
    private String uriBack;

    public LinkIstanzeprocedimentiHelperCellEditor(HttpServletRequest request, String codiceIstanza, String urlBack) {

	super();
	this.codiceIstanza = codiceIstanza;
	this.uriBack = Utilities.buildHistoryBackFromRequest(request, urlBack);
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Object oggetto = (Object) item;
	BigDecimal codIstanza = (BigDecimal) UtilityJmesa.getParametro(oggetto, codiceIstanza);
	BigDecimal countEndoprocedimenti = null;
	countEndoprocedimenti = (BigDecimal) UtilityJmesa.getParametro(oggetto, property);
	if (countEndoprocedimenti.intValue() > 0) {
	    String uriTo = "../istanzeprocedimenti/riepilogo.htm?codiceIstanza=" + codIstanza;
	    try {
		uriTo = URLEncoder.encode(uriTo, "UTF-8");
	    } catch (Exception e) {
	    }
	    String p = getLabel("label.P");
	    String anchorName = Utilities.getHashText(uriTo, Utilities.ALGORITHM_MD5, false);
	    String anchorNameEncoded = "";
	    try {
		anchorNameEncoded = URLEncoder.encode(URLEncoder.encode("#", "UTF-8") + anchorName, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	    }
	    String valueItem = "<a name=\"" + anchorName + "\" style=\"cursor: pointer;\" href=\"javascript:doHref('../history/set.htm?ReturnTo="
		    + uriBack + anchorNameEncoded + "&" + WebConstants.GOTO + "=" + uriTo + "')\" title=\" " + p + "\">" + p + "</a>";
	    return valueItem;
	}
	return "";
    }

    // recupera la label dal CoreContext
    private String getLabel(String label) {

	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String message = messages.getMessage(label);
	if (message == null) {
	    message = "???" + label + "???";
	}
	return message;
    }
}
