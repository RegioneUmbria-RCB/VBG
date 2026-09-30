package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class ScadenzaAvvisoCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	String value = (String) ItemUtils.getItemValue(item, property);
	String valueItem = null;
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String scadenza = messages.getMessage("list.jmesa.celleditor.scadenza");
	String avviso = messages.getMessage("list.jmesa.celleditor.avviso");
	String interdetti = messages.getMessage("list.jmesa.celleditor.interdizione");
	if (scadenza == null) {
	    scadenza = "???list.jmesa.celleditor.scadenza???";
	}
	if (avviso == null) {
	    avviso = "???list.jmesa.celleditor.avviso???";
	}
	if (interdetti == null) {
	    interdetti = "???list.jmesa.celleditor.interdizione???";
	}
	if (value != null) {
	    if (value.equals(WebConstants.SCADENZA)) {
		valueItem = scadenza;
	    }
	    if (value.equals(WebConstants.AVVISO)) {
		valueItem = avviso;
	    }
	    if (value.equals(WebConstants.INTERDETTI)) {
		valueItem = interdetti;
	    }
	}
	return valueItem;
    }
}
