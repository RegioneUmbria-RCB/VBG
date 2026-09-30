package org.jmesa.custom;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class StatoApertaChiusaCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Boolean value = (Boolean) ItemUtils.getItemValue(item, property);
	String valueItem = null;
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String aperta = getCoreContext().getMessage("list.jmesa.celleditor.aperta");
	String chiusa = messages.getMessage("list.jmesa.celleditor.chiusa");
	if (aperta == null) {
	    aperta = "???list.jmesa.celleditor.aperta???";
	}
	if (chiusa == null) {
	    chiusa = "???list.jmesa.celleditor.chiusa???";
	}
	if (value != null) {
	    if (value.booleanValue()) {
		valueItem = aperta;
	    }
	    if (!value.booleanValue()) {
		valueItem = chiusa;
	    }
	}
	return valueItem;
    }
}
