/**
 * 
 */
package org.jmesa.custom;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

/**
 * Custom Cell Editor per poter visualizzare pari e dispari invece di true e false. Tale classe è richiamata nella jsp
 * di interesse.
 * 
 * @author Francesco Palenga
 * 
 */
public class PariDispariCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Boolean value = (Boolean) ItemUtils.getItemValue(item, property);
	String valueItem = null;
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String pari = messages.getMessage("form.areedettagli.celleditor.pari");
	String dispari = messages.getMessage("form.areedettagli.celleditor.dispari");
	if (pari == null) {
	    pari = "???form.areedettagli.celleditor.pari???";
	}
	if (dispari == null) {
	    dispari = "???form.areedettagli.celleditor.dispari???";
	}
	if (value != null) {
	    if (value.booleanValue()) {
		valueItem = pari;
	    }
	    if (!value.booleanValue()) {
		valueItem = dispari;
	    }
	}
	return valueItem;
    }
}
