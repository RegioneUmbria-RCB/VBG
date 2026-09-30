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

public class LinkFiledBaseCellEditor extends AbstractCellEditor {

    private static final Logger log = LoggerFactory.getLogger(LinkFiledBaseCellEditor.class);
    private String historyBack = "";
    private String urlGoTo = "";
    private String pathParametro = "";
    private String path = "";

    // Costruttore che gestisce la history back
    public LinkFiledBaseCellEditor(HttpServletRequest request, String path, String urlGoTo, String servletPathUriBack, String pathParametro) {

	super();
	this.historyBack = Utilities.buildHistoryBackFromRequest(request, servletPathUriBack);
	this.urlGoTo = urlGoTo;
	this.pathParametro = pathParametro;
	this.path = path;
    }

    // Costruttore che non  gestisce la history back
    public LinkFiledBaseCellEditor(HttpServletRequest request, String path, String urlGoTo, String pathParametro) {

	super();
	this.urlGoTo = urlGoTo;
	this.pathParametro = pathParametro;
	this.path = path;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	Object oggetto = (Object) item;
	String goTo = "";
	Object parametro = null;
	Object nameLinkFinal = null;
	// recupera il parametro per il path passato (parametro da passare al controller)
	nameLinkFinal = UtilityJmesa.getParametro(oggetto, path);
	// recupera il parametro per il path passato (nome del link )
	parametro = UtilityJmesa.getParametro(oggetto, pathParametro);
	try {
	    goTo = urlGoTo;
	    goTo = URLEncoder.encode(goTo, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    log.debug("UnsupportedEncodingException: " + e);
	    e.printStackTrace();
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
		    + WebConstants.RETURNTO + "=" + historyBack + anchorNameEncoded + "\" title=\"" + nameLinkFinal + "\">" + nameLinkFinal + "</a>";
	} else {
	    valueItem = "<a  href=\"javascript:doHref('" + goTo + parametro + "','')\" title=\"" + nameLinkFinal + "\"> " + nameLinkFinal + "</a>";
	}
	return valueItem;
    }
}
