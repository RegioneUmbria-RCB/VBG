package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkStampaAutorizzazioni extends AbstractCellEditor {

    private static final Logger log = LoggerFactory.getLogger(LinkStampaAutorizzazioni.class);
    private HttpServletRequest request;

    // Costruttore che gestisce la history back
    public LinkStampaAutorizzazioni(HttpServletRequest request) {

	super();
	this.request = request;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	Object oggetto = (Object) item;
	Integer codiceLetteraTipo = null;
	if (UtilityJmesa.getParametro(oggetto, "istanza.procedura.letteretipo.id.codice") != null) {
	    codiceLetteraTipo = (Integer) UtilityJmesa.getParametro(oggetto, "istanza.procedura.letteretipo.id.codice");
	}
	Integer codiceTipologiaregistro = (Integer) UtilityJmesa.getParametro(oggetto, "tipologiaregistro.id.codice");
	Integer codiceIstanza = (Integer) UtilityJmesa.getParametro(oggetto, "istanza.id.codice");
	String goTo = BackofficeNETConstants.getURL_STAMPA_LETTERE_TIPO_AUTORIZZAZIONI() + "?CodiceRegistro=" + codiceTipologiaregistro
		+ "&CodiceLettera=" + codiceLetteraTipo + "&CodiceIstanza=" + codiceIstanza + "&InvioRichiedente=on";
	goTo = BackofficeNETConstants.getUrlTo(request, goTo, "%2F", ORMHelper.getSoftware(), true);
	String valueItem = "";
	if (codiceLetteraTipo != null) {
	    String stampa = getLabel("label.stampa");
	    valueItem = "<a style=\"cursor: pointer;\" class=\"stampaColumn\" href=\"javascript:void 0\" title=\"" + stampa
		    + "\" onclick=\"window.open('" + goTo
		    + "',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');\"><label>" + stampa + "</label></a>";
	}
	return valueItem;
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
