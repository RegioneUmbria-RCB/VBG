package org.jmesa.customColumn;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.editor.AbstractCellEditor;

public class FoDomandeFrontendAzioni extends AbstractCellEditor {

    private String cfUtenteLoggato;

    public FoDomandeFrontendAzioni(String cfUtenteLoggato) {

	super();
	this.cfUtenteLoggato = cfUtenteLoggato;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Integer codice = (Integer) UtilityJmesa.getParametro(item, "id.codice");
	String identificativodomanda = (String) UtilityJmesa.getParametro(item, "identificativodomanda");
	String cfPresentatore = (String) UtilityJmesa.getParametro(item, "presentatoreCodfiscale");
	String riprendi = "riprendi"; // getLabel("button.riprendi");
	String messaggio = "riprendi la domanda "/*getLabel("label.riprendi-domanda") */+ " " + identificativodomanda;
	String messaggioelimina = "riprendi la domanda "/*getLabel("label.elimina-domanda") */+ " " + identificativodomanda;
	String elimina = "elimina"; //getLabel("button.elimina");
	String result = "";
	if (StringUtils.defaultString(cfUtenteLoggato).equalsIgnoreCase(cfPresentatore)) {
	    // solo il presentatore e non il titolare può riprendere la domanda MODIFICHE SCRIVANIA VIRTUALE
	    result = "<a class=\"table_button\" href=\"javascript:riprendiDomanda('" + codice.intValue() + "', '" + identificativodomanda
		    + "')\" title=\"" + messaggio + "\">" + riprendi + "</a>";
	}
	result += "<a class=\"table_button\" href=\"javascript:eliminaDomanda('" + codice.intValue() + "', '" + identificativodomanda
		+ "')\" title=\"" + messaggioelimina + "\">" + elimina + "</a>";
	if (StringUtils.defaultString(cfUtenteLoggato).equalsIgnoreCase(cfPresentatore)) {
	    // solo il presentatore può eliminare domande a gruppi
	    result += "&nbsp;<input type=\"checkbox\" name=\"id\" data-tipo=\"eliminazione\" onclick=\"javascript:checkElimina()\" value=\""
		    + codice.intValue() + "\">";
	}
	return result;
    }
}
