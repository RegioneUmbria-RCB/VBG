package org.jmesa.custom;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class TipibandoCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	String value = (String) ItemUtils.getItemValue(item, property);
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String si = messages.getMessage("form.tipibando.celleditor.si");
	String no = messages.getMessage("form.tipibando.celleditor.no");
	if (si == null) {
	    si = "???form.tipibando.celleditor.si???";
	}
	if (no == null) {
	    no = "???form.tipibando.celleditor.no???";
	}
	String valueItem = no;
	if (value != null) {
	    if (value.equals("1")) {
		valueItem = si;
	    }
	}
	return valueItem;
    }
}
