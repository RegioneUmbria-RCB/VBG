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
public class SiNoCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Boolean value = (Boolean) ItemUtils.getItemValue(item, property);
	String valueItem = null;
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String si = getCoreContext().getMessage("list.jmesa.celleditor.si");
	String no = messages.getMessage("list.jmesa.celleditor.no");
	if (si == null) {
	    si = "???list.jmesa.celleditor.si???";
	}
	if (no == null) {
	    no = "???list.jmesa.celleditor.no???";
	}
	if (value != null) {
	    if (value.booleanValue()) {
		valueItem = si;
	    }
	    if (!value.booleanValue()) {
		valueItem = no;
	    }
	}
	return valueItem;
    }
}
