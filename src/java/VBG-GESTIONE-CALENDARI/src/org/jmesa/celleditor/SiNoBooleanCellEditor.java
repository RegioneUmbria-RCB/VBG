/**
 * 
 */
package org.jmesa.celleditor;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

/**
 * @author fabrizioc
 * 
 */
public class SiNoBooleanCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Boolean value = (Boolean) ItemUtils.getItemValue(item, property);
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String si = messages.getMessage("label.si");
	String no = messages.getMessage("label.no");
	if (si == null) {
	    si = "???label.si???";
	}
	if (no == null) {
	    no = "???label.no???";
	}
	String valueItem = no;
	if (value != null && value.booleanValue()) {
	    valueItem = si;
	}
	return valueItem;
    }
}
