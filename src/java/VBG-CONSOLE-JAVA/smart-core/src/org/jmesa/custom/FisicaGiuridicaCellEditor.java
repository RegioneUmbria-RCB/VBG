package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class FisicaGiuridicaCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	String value = (String) ItemUtils.getItemValue(item, property);
	String valueItem = null;
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String fisica = messages.getMessage("list.jmesa.celleditor.persona_fisica");
	String giuridica = messages.getMessage("list.jmesa.celleditor.persona_giuridica");
	if (fisica == null) {
	    fisica = "???list.jmesa.celleditor.persona_fisica???";
	}
	if (giuridica == null) {
	    giuridica = "???list.jmesa.celleditor.persona_giuridica???";
	}
	if (value != null) {
	    if (value.equals(WebConstants.PERSONA_FISICA.toUpperCase())) {
		valueItem = fisica;
	    }
	    if (value.equals(WebConstants.PERSONA_GIURIDICA.toUpperCase())) {
		valueItem = giuridica;
	    }
	}
	return valueItem;
    }
}
