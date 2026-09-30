package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class LinkMailComunicazioni extends AbstractCellEditor {

    private HttpServletRequest request;
    private String pathGoTo;

    // Costruttore che gestisce la history back
    public LinkMailComunicazioni(HttpServletRequest request, String pathGoTo) {

	super();
	this.request = request;
	this.pathGoTo = pathGoTo;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	//	String movimentimail_label_lista_movimentimail_title = getLabel("movimentimail.label.lista_movimentimail.title");
	String label_lista_movimentimail_title = getLabel("movimentimail.label.lista_movimentimail.title");
	Object oggetto = (Object) item;
	Integer $graduatoriedComDTO_var_movimenti = (Integer) UtilityJmesa.getParametro(oggetto, "movimenti");
	Integer $graduatoriedComDTO_var_istanza = (Integer) UtilityJmesa.getParametro(oggetto, "istanza.id.codice");
	Boolean accettata = (Boolean) UtilityJmesa.getParametro(oggetto, "accettata");
	Boolean consegnata = (Boolean) UtilityJmesa.getParametro(oggetto, "consegnata");
	Integer codiceMail = (Integer) UtilityJmesa.getParametro(oggetto, "movimentimail");
	//	Integer codiceGraduatoria = (Integer) UtilityJmesa.getParametro(oggetto, "id.codice");
	String contextPath = request.getContextPath();
	StringBuffer buffer = new StringBuffer();
	String immage = "<img src=\"../images/email.png\"/>";
	String uriTo = "../movimentimail/list.htm?codicemovimento=" + $graduatoriedComDTO_var_movimenti + "&software=CO&codiceistanza="
		+ $graduatoriedComDTO_var_istanza;
	try {
	    uriTo = URLEncoder.encode(uriTo, "UTF-8");
	} catch (Exception e) {
	}
	String uriBack = Utilities.buildHistoryBackFromRequest(request, pathGoTo);
	String historySetUrl = "../history/set.htm?ReturnTo=" + uriBack + "&" + WebConstants.GOTO + "=" + uriTo;
	String valueItem = "<a href=\"" + historySetUrl + "\" title=\"" + label_lista_movimentimail_title + "\">" + immage + "</a>";
	if (codiceMail != null) {
	    buffer.append(openTable);
	    buffer.append(openTr);
	    ///////////////////////////////////////////////////////////////////////////////
	    buffer.append(openTd);
	    buffer.append("&nbsp;").append(valueItem);
	    buffer.append(closeTd);
	    //////////////////////////////////////////////////////////////////////////
	    buffer.append(openPaddingTd);
	    if (accettata) {
		buffer.append("<img title=\"" + getLabel("label.accettata") + "\" src=\"" + contextPath + "/images/accept.png\"/>");
	    } else {
		buffer.append("&nbsp;");
	    }
	    buffer.append(closeTd);
	    ///////////////////////////////////////////////////////////////////////
	    buffer.append(openPaddingTd);
	    if (consegnata) {
		buffer.append("<img title=\"" + getLabel("label.consegnata") + "\" src=\"" + contextPath + "/images/accept.png\"/>");
	    } else {
		buffer.append("&nbsp;");
	    }
	    buffer.append(closeTd);
	    ////////////////////////////////////////////////////////////////////////////
	    buffer.append(closeTr);
	    buffer.append(closeTable);
	}
	return buffer.toString();
    }

    private static final String openTable = "<table>";
    private static final String closeTable = "</table>";
    private static final String openTr = "<tr>";
    private static final String closeTr = "</tr>";
    private static final String openTd = "<td>";
    private static final String closeTd = "</td>";
    private static final String openPaddingTd = "<td style=\"padding-left: 40px;\">";

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
