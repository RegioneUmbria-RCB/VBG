/**
 * 
 */
package org.jmesa.celleditor;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

/**
 * @author gianpaolot
 * 
 */
public class SiNoBooleanCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Boolean value = (Boolean) ItemUtils.getItemValue(item, property);
	String valueItem = null;
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String si = messages.getMessage("label.si");
	String no = messages.getMessage("label.no");
	//	String si = "Si";
	//	String no = "No";
	if (si == null) {
	    si = "???label.si???";
	}
	if (no == null) {
	    no = "???label.no???";
	}
	if (value != null) {
	    if (value.booleanValue()) {
		valueItem = si;
	    }
	    if (!value.booleanValue()) {
		valueItem = no;
	    }
	} else {
	    // BOCCI 2012-08-29: NULL PER UN VALORE BOOLEANO È = FALSE
	    // PER CUI IN VISUALIZZAZIONE METTO "No" PER I VALORI NULLI
	    valueItem = no;
	}
	return valueItem;
    }
}
