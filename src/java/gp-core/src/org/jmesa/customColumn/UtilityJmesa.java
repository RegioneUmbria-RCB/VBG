package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.utils.CustomHtmlBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.AbstractContextSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

public class UtilityJmesa extends AbstractContextSupport {

    private static final Logger log = LoggerFactory.getLogger(UtilityJmesa.class);

    public static Object getParametro(Object oggetto, String path) {

	Object parametro = null;
	String[] field = path.split("\\.");
	try {
	    Object object = null;
	    Method get = oggetto.getClass().getMethod("get" + StringUtils.capitalize(field[0]));
	    object = get.invoke(oggetto, new Object[0]);
	    for (int i = 1; i < field.length - 1; i++) {
		get = object.getClass().getMethod("get" + StringUtils.capitalize(field[i]));
		object = get.invoke(object, new Object[0]);
	    }
	    if (field.length > 1) {
		Method get2 = object.getClass().getMethod("get" + StringUtils.capitalize(field[field.length - 1]));
		parametro = get2.invoke(object, new Object[0]);
	    } else {
		parametro = object;
	    }
	} catch (SecurityException e) {
	    log.debug("SecurityException: " + e);
	    e.printStackTrace();
	} catch (NoSuchMethodException e) {
	    log.debug("NoSuchMethodException " + e);
	} catch (IllegalArgumentException e) {
	    log.debug("IllegalArgumentException " + e);
	} catch (IllegalAccessException e) {
	    log.debug("IllegalAccessException " + e);
	} catch (InvocationTargetException e) {
	    log.debug("InvocationTargetException " + e);
	} catch (NullPointerException e) {
	    return null;
	}
	return parametro;
    }

    /**
     * <pre>
     * Il metodo crea codice html che riproduce il filtro sugli header delle tabelle jmesa per un campo date.
     * Va usato all'interno di una classe NomecampoFilter (Es. DataregistrazioneFilter) presenti nella pakage
     * org.jmesa.custom.
     * Il metodo in particolare crea un campo di testo (non editabile) con un icona accanto che permette di aprire 
     * un calendario per scegliere la data. Il codice conterra anche la dichiarazione dei javascript per la generazione
     * del calendario e per la creazione in modo dinamico del filtro jmesa.
     * 
     *     
     * @param idJmesaTable	: id della tabella jmesa
     * @param nomeCampo		: nome del campo date da filtrare
     * @return
     * 
     * </pre>
     */
    public static String getCustomFilterDate(String idJmesaTable, String nomeCampo) {

	String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getContextPath();
	CustomHtmlBuilder html = new CustomHtmlBuilder();
	String div_id = nomeCampo + "div_id";
	String img_id = nomeCampo + "img_id";
	// vengono tolti se esistono dei punti perchè se il campo si chiama nomeoggetto.data il nome setupCalnomeoggetto.data crea problemi
	String dummyString = nomeCampo.replace(".", "");
	html.div()
		.styleClass("dynFilter")
		.id(div_id)
		.style("width: 70px;float:left;")
		.close()
		.divEnd()
		.append("&nbsp;")
		.img()
		.id(img_id)
		.append("src=\"")
		.append(path + "/images/cal.gif\"")
		.onclick("setupCal" + dummyString + "('" + div_id + "','" + img_id + "')")
		.close()
		.append("<script type=\"text/javascript\"> var setupCal" + dummyString + " = function(idField,trigger){" + "new Calendar({"
			+ "inputField: idField," + "dateFormat: \"%d/%m/%Y\"," + "trigger: trigger," + "bottomBar: false," + "onSelect: function() {"
			+ "jQuery.jmesa.createDynFilter($(idField), '" + idJmesaTable + "','" + nomeCampo + "');" + "this.hide();" + "}" + "});"
			+ "};" + "</script>");
	return html.toString();
    }
}
