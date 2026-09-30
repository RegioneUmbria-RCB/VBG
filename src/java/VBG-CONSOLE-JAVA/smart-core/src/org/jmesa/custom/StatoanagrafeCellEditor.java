package org.jmesa.custom;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class StatoanagrafeCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Integer value = (Integer) ItemUtils.getItemValue(item, property);
	String valueItem = null;
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String attivo = messages.getMessage("list.jmesa.celleditor.attivo");
	String disattivo = messages.getMessage("list.jmesa.celleditor.disattivo");
	String sospeso = messages.getMessage("list.jmesa.celleditor.sospeso");
	if (attivo == null) {
	    attivo = "???list.jmesa.celleditor.attivo???";
	}
	if (disattivo == null) {
	    disattivo = "???list.jmesa.celleditor.disattivo???";
	}
	if (sospeso == null) {
	    sospeso = "???list.jmesa.celleditor.sospeso???";
	}
	if (value != null) {
	    if (value.equals(Integer.valueOf(0))) {
		valueItem = attivo;
	    }
	    if (value.equals(Integer.valueOf(1))) {
		valueItem = disattivo;
	    }
	    if (value.equals(Integer.valueOf(2))) {
		valueItem = sospeso;
	    }
	}
	return valueItem;
    }
}
