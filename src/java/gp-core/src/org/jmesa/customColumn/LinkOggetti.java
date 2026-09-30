package org.jmesa.customColumn;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.kahadb.util.ByteArrayInputStream;
import org.jmesa.view.editor.AbstractCellEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkOggetti extends AbstractCellEditor {

    private static final Logger log = LoggerFactory.getLogger(LinkOggetti.class);
    private HttpServletRequest request;

    public LinkOggetti(HttpServletRequest request) {

	super();
	this.request = request;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	Object oggetto = (Object) item;
	Integer codiceOggetto = (Integer) UtilityJmesa.getParametro(oggetto, "oggetto");
	String goTo = "";
	String goToEncode = "";
	java.io.InputStream inp = null;
	try {
	    if (codiceOggetto != null) {
		goToEncode = URLEncoder.encode(goTo, "UTF-8");
		inp = request.getSession().getServletContext().getResourceAsStream("/WEB-INF/jsp/includes/visualizzaOggetto.jsp");
		if (inp == null) {
		    log.error("Non ho trovato il file \"visualizzaOggetto.jsp\", nella cartella \"WEB-INF/jsp/includes\"");
		    return "";
		}
		String inputStreamString = new Scanner(inp, "UTF-8").useDelimiter("\\A").next();
		inputStreamString = inputStreamString.replace("${param.idElemento}", codiceOggetto.toString());
		inputStreamString = inputStreamString.replace("${param.fileId}", codiceOggetto.toString());
		inputStreamString = inputStreamString.replace("${param.mostralabel}", "true");
		inputStreamString = inputStreamString.replace("${param.showEditDocs}", "false");
		inputStreamString = inputStreamString.replace("${param.mostraNomeFile}", "true");
		inputStreamString = inputStreamString.replace("${param.readonly}", "true");
		inputStreamString = inputStreamString.replace("${param.styleHref}", "");
		inputStreamString = inputStreamString.replace("<c:set var=\"currtime\"><%=System.currentTimeMillis() %></c:set>", "");
		inputStreamString = inputStreamString.replace("${currtime}", String.valueOf(System.currentTimeMillis()));
		// Elimino gli include
		log.debug("Elimino i page include dalla jsp");
		int i = StringUtils.indexOf(inputStreamString, "<%@");
		int i1 = 0;
		while (i != -1) {
		    i1 = StringUtils.indexOf(inputStreamString, "%>");
		    inputStreamString = inputStreamString.substring(i1 + 2);
		    i = StringUtils.indexOf(inputStreamString, "<%@");
		}
		// Elimino i commenti
		log.debug("Elimino i commenti jsp");
		int j = StringUtils.indexOf(inputStreamString, "<%--");
		int j1 = 0;
		while (j != -1) {
		    j1 = StringUtils.indexOf(inputStreamString, "--%>");
		    inputStreamString = inputStreamString.substring(j1 + 4);
		    j = StringUtils.indexOf(inputStreamString, "<%--");
		}
		return inputStreamString;
	    } else {
		log.debug("Codice oggetto null");
		return "";
	    }
	} catch (Exception e) {
	    log.error("Errore nel rendering del link oggetti.Errore: {}[{}] ", new Object[] { e.getMessage(), e });
	    return "";
	}
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

	String inps = "${param.mostralabel}&showEditDocs=${param.showEditDocs}&mostraNomeFile=${param.mostraNomeFile}&mostrastorico=true&readonly=${param.readonly}&jsFx=viewOggetto_2305673_1769101247169_fx&styleHref=${param.styleHref}";
	InputStream inp = new ByteArrayInputStream(inps.getBytes("UTF-8"));
	String inputStreamString = new Scanner(inp, "UTF-8").useDelimiter("\\A").next();
	inputStreamString = inputStreamString.replace("${param.idElemento}", "100");
	inputStreamString = inputStreamString.replace("${param.fileId}", "101");
	inputStreamString = inputStreamString.replace("<c:set var=\"currtime\"><%=System.currentTimeMillis() %></c:set>", "");
	inputStreamString = inputStreamString.replace("${currtime}", String.valueOf(System.currentTimeMillis()));
	System.out.println(inputStreamString);
    }
}
