package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkActionBaseCellEditor extends AbstractCellEditor {

    private static final Logger log = LoggerFactory.getLogger(LinkActionBaseCellEditor.class);
    private String historyBack = "";
    private String urlGoTo = "";
    private String typeAction = "";
    private String path = "";
    private boolean isReplace = false;

    // Costruttore che gestisce la history back
    public LinkActionBaseCellEditor(HttpServletRequest request, String servletPathUriBack, String urlGoTo, String typeAction, String path) {

	super();
	this.historyBack = Utilities.buildHistoryBackFromRequest(request, servletPathUriBack);
	this.urlGoTo = urlGoTo;
	this.typeAction = typeAction;
	this.path = path;
    }

    // Costruttore che non  gestisce la history back
    public LinkActionBaseCellEditor(HttpServletRequest request, String urlGoTo, String typeAction, String path) {

	super();
	this.urlGoTo = urlGoTo;
	this.typeAction = typeAction;
	this.path = path;
    }

    public LinkActionBaseCellEditor(HttpServletRequest request, String uriback, String urlGoTo, String typeAction, String path,
	    String messageToConfirm, boolean isReplace) {

	super();
	this.historyBack = Utilities.buildHistoryBackFromRequest(request, uriback);
	this.urlGoTo = urlGoTo;
	this.typeAction = typeAction;
	this.path = path;
	this.customLink = urlGoTo;
	this.messageToConfirmLink = messageToConfirm;
	link = createLink(historyBack);
	placeHolders = getLinkPlaceHolders();
	isReplace = true;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	Object oggetto = (Object) item;
	// Recupera il valore per il path passato
	Object parametro = UtilityJmesa.getParametro(oggetto, path);
	if(parametro == null){
	    parametro = "";
	}
	String goTo = "";
	goTo = urlGoTo;
	try {
	    goTo = URLEncoder.encode(goTo, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    log.error("UnsupportedEncodingException: ", e);
	    //e.printStackTrace();
	}
	// Valore di ritorno
	String valueItem = "";
	//-----------------------------------------------------------------------------------------------------------------------------///
	//---------------------------------------------------- CASO LINK DI DETTAGLIO--------------------------------------------------///
	//-----------------------------------------------------------------------------------------------------------------------------///
	if (typeAction.equalsIgnoreCase("detail")) {
	    // Recupero delle label standard
	    String message = getLabel("label.edit.record");
	    //controllo se ho il link di ritorno dell'history back
	    // 1- Si: creo il link con l'history set
	    // 2- No: creo il link senza history set
	    if (StringUtils.isNotBlank(historyBack)) {
		valueItem = "<a class=\"dettaglioColumn\" href=\"../history/set.htm?" + WebConstants.GOTO + "=" + goTo + parametro + "&"
			+ WebConstants.RETURNTO + "=" + historyBack + "\" title=\"" + message + "\"${bandi_var.id.codice}\">" + "<label>" + message
			+ "</label></a>";
	    } else// non devo fare l'history set
	    {
		valueItem = "<a class=\"dettaglioColumn\" href=\"javascript:doHref('" + goTo + parametro + "','')\" title=\"" + message
			+ "\"> <label>" + message + "</label></a>";
	    }
	}
	//-----------------------------------------------------------------------------------------------------------------------------///
	//---------------------------------------------------- CASO LINK DI CANCELLAZIONE----------------------------------------------///
	//-----------------------------------------------------------------------------------------------------------------------------///
	if (typeAction.equalsIgnoreCase("delete")) {
	    // Recupero delle label standard
	    String message = getLabel("label.elimina");
	    String alert = getLabel("javascript.confirm.delete");
	    valueItem = "<a class=\"eliminaRiga\" href=\"javascript:doHref('" + goTo + parametro + "','" + alert + "')\" title=\"" + message
		    + "\"> <label>" + message + "</label></a>";
	}
	//-----------------------------------------------------------------------------------------------------------------------------///
	//---------------------------------------------------- CASO LINK PER AGGIUNGERE RECORD------------------------------------------///
	//-----------------------------------------------------------------------------------------------------------------------------///
	if (typeAction.equalsIgnoreCase("add")) {
	    // Recupero delle label standard
	    String message = getLabel("label.aggiungi");
	    //controllo se ho il link di ritorno dell'history back
	    // 1- Si: creo il link con l'history set
	    // 2- No: creo il link senza history set
	    if (StringUtils.isNotBlank(historyBack)) {
		valueItem = "<a class=\"addColumn\" href=\"../history/set.htm?" + WebConstants.GOTO + "=" + goTo + parametro + "&"
			+ WebConstants.RETURNTO + "=" + historyBack + "\" title=\"" + message + "\"${bandi_var.id.codice}\">" + "<label>" + message
			+ "</label></a>";
	    } else// non devo fare l'history set
	    {
		valueItem = "<a class=\"addColumn\" href=\"javascript:doHref('" + goTo + parametro + "','')\" title=\"" + message + "\"> <label>"
			+ message + "</label></a>";
	    }
	}
	//-----------------------------------------------------------------------------------------------------------------------------///
	//---------------------------------------------------- CASO LINK DOWNLOAD FILE------------------------------------------///
	//-----------------------------------------------------------------------------------------------------------------------------///
	if (typeAction.equalsIgnoreCase("view_doc")) {
	    String message = getLabel("label.visualizza");
	    //	    valueItem = "<a class=\"visualizzaDocColumn\" href=\"javascript:doHref('" + goTo + parametro + "','')\" title=\"" + message
	    //		    + "\"><label>" + message + "</label> </a>";
	    valueItem = "<a class=\"visualizzaDocColumn\" href=\"" + urlGoTo + parametro + "\" title=\"" + message + "\"><label>" + message
		    + "</label> </a>";
	}
	//-----------------------------------------------------------------------------------------------------------------------------///
	//---------------------------------------------------- CASO LINK PER IMPORTARE RECORD------------------------------------------///
	//-----------------------------------------------------------------------------------------------------------------------------///
	if (typeAction.equalsIgnoreCase("import")) {
	    // Recupero delle label standard
	    String message = getLabel("label.importa");
	    //controllo se ho il link di ritorno dell'history back
	    // 1- Si: creo il link con l'history set
	    // 2- No: creo il link senza history set
	    if (StringUtils.isNotBlank(historyBack)) {
		valueItem = "<a class=\"addColumn\" href=\"../history/set.htm?" + WebConstants.GOTO + "=" + goTo + parametro + "&"
			+ WebConstants.RETURNTO + "=" + historyBack + "\" title=\"" + message + "\"${bandi_var.id.codice}\">" + "<label>" + message
			+ "</label></a>";
	    } else// non devo fare l'history set
	    {
		valueItem = "<a class=\"addColumn\" href=\"javascript:doHref('" + goTo + parametro + "','')\" title=\"" + message + "\"> <label>"
			+ message + "</label></a>";
	    }
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
	//	String message = getCoreContext().getMessage(label);
	//	if (message == null) {
	//	    message = "???" + label + "???";
	//	}
	return message;
    }

    /**
     * metodo per la creazione del link al dettaglio istanza
     * 
     * @param property
     *            nome della property dell'oggetto Istanze contenuta nell'oggetto corrente.( se l'oggetto corrente è
     *            Istanze stesso lasciare a null o stringa vuota)
     * 
     * @param uriBack
     *            uri della funzionalità a cui tornare. (se passato l'url utilizza la historySet, altrimenti l'url è un
     *            normale link)
     * 
     * @return
     */
    private String createLink(String uriBack) {

	if (StringUtils.isNotBlank(uriBack)) {
	    this.link = "javascript:historySet('" + uriBack + "','" + customLink + "','" + StringUtils.defaultIfEmpty(this.messageToConfirmLink, "")
		    + "');";
	}
	return this.link;
    }

    /**
     * recupera la lista dei segnaposto presenti nella property 'link'
     * 
     * @return
     */
    private String[] getLinkPlaceHolders() {

	List<String> matches = new ArrayList<String>();
	String regex = "<((\\w+)(\\.)?)+>";
	Pattern p = Pattern.compile(regex);
	Matcher m = p.matcher(link);
	while (m.find()) {
	    matches.add(m.group());
	}
	return matches.toArray(new String[matches.size()]);
    }

    public String getLink() {

	return link;
    }

    public String[] getPlaceHolders() {

	return placeHolders;
    }

    private String messageToConfirmLink;
    private String customLink;
    private String link;
    private String[] placeHolders;
}
