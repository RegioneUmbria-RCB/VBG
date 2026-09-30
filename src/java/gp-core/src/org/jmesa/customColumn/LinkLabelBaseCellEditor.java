package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkLabelBaseCellEditor extends AbstractCellEditor {

    private static final Logger log = LoggerFactory.getLogger(LinkLabelBaseCellEditor.class);
    private String historyBack = "";
    private String urlGoTo = "";
    private String nameLink = "";
    private String path = "";

    // Costruttore che gestisce la history back
    public LinkLabelBaseCellEditor(HttpServletRequest request, String nameLink, String urlGoTo, String servletPathUriBack, String path) {

	super();
	this.historyBack = Utilities.buildHistoryBackFromRequest(request, servletPathUriBack);
	this.urlGoTo = urlGoTo;
	this.nameLink = nameLink;
	this.path = path;
    }

    // Costruttore che non  gestisce la history back
    public LinkLabelBaseCellEditor(HttpServletRequest request, String nameLink, String urlGoTo, String path) {

	super();
	this.urlGoTo = urlGoTo;
	this.nameLink = nameLink;
	this.path = path;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	Object oggetto = (Object) item;
	String goTo = "";
	Object parametro = null;
	parametro = UtilityJmesa.getParametro(oggetto, path);
	try {
	    goTo = urlGoTo;
	    goTo = URLEncoder.encode(goTo, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    log.debug("UnsupportedEncodingException: " + e);
	    e.printStackTrace();
	}
	// recupera label dal dai message property.
	String nameLinkConvert = getCoreContext().getMessage(nameLink);
	if (nameLinkConvert == null) {
	    nameLinkConvert = "???nameLink???";
	}
	String valueItem = "";
	//controllo se ho il link di ritorno dell'history back
	// 1- Si: creo il link con l'history set
	// 2- No: creo il link senza history set
	String anchorName = Utilities.getHashText(goTo + parametro, Utilities.ALGORITHM_MD5, false);
	String anchorNameEncoded = "";
	try {
	    anchorNameEncoded = URLEncoder.encode("#" + anchorName, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    log.debug("UnsupportedEncodingException: " + e);
	    e.printStackTrace();
	}
	if (StringUtils.isNotBlank(historyBack)) {
	    valueItem = "<a name=\"" + anchorName + "\" href=\"../history/set.htm?" + WebConstants.GOTO + "=" + goTo + parametro + "&"
		    + WebConstants.RETURNTO + "=" + historyBack + anchorNameEncoded + "\" title=\"" + nameLinkConvert + "\">" + nameLinkConvert
		    + "</a>";
	} else {
	    valueItem = "<a  href=\"javascript:doHref('" + goTo + parametro + "','')\" title=\"" + nameLinkConvert + "\"> " + nameLinkConvert
		    + "</a>";
	}
	return valueItem;
    }
}
