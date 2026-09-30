/**
 * 
 */
package org.jmesa.custom;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

/**
 * @author francescop
 * 
 */
public class ResponsabiliCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	String value = (String) ItemUtils.getItemValue(item, property);
	String valueItem = null;
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String s = messages.getMessage("responsabili.label.amministratori_value.table");
	String falseValue = "";
	if (s == null) {
	    s = "???responsabili.label.amministratori_value.table???";
	}
	if (value != null) {
	    if (value.equals("1")) {
		valueItem = s;
	    }
	    if (!value.equals("1")) {
		valueItem = falseValue;
	    }
	}
	return valueItem;
    }
}