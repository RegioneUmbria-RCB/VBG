/**
 * 
 */
package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

/**
 * @author francescop
 * 
 */
public class TipoEntrataUscitaCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	String value = (String) ItemUtils.getItemValue(item, property);
	String valueItem = null;
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String entrata = messages.getMessage("form.registrazioniInOut.tipo.e");
	String uscita = messages.getMessage("form.registrazioniInOut.tipo.u");
	if (entrata == null) {
	    entrata = "???form.registrazioniInOut.tipo.e???";
	}
	if (uscita == null) {
	    uscita = "???form.registrazioniInOut.tipo.u???";
	}
	String e = WebConstants.REGISTRAZIONIINOUT_TIPO_E;
	String u = WebConstants.REGISTRAZIONIINOUT_TIPO_U;
	if (value != null) {
	    if (value.equals(e)) {
		valueItem = entrata;
	    }
	    if (value.equals(u)) {
		valueItem = uscita;
	    }
	}
	return valueItem;
    }
}
