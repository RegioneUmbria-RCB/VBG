package org.jmesa.celleditor;

import java.util.Map;
import java.util.Set;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class CustomStringCellEditor extends AbstractCellEditor {

    private Map<String, String> mapLabelValore;

    public CustomStringCellEditor(Map<String, String> mapLabelValore) {

	super();
	this.mapLabelValore = mapLabelValore;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	String value = (String) ItemUtils.getItemValue(item, property);
	String valueItem = null;
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	Set<String> listaChiavi = mapLabelValore.keySet();
	for (String key : listaChiavi) {
	    if (value.equals(mapLabelValore.get(key))) {
		String decodificakey = messages.getMessage(key);
		if (decodificakey == null) {
		    decodificakey = "???" + key + "???";
		}
		valueItem = decodificakey;
	    }
	}
	return valueItem;
    }
}
