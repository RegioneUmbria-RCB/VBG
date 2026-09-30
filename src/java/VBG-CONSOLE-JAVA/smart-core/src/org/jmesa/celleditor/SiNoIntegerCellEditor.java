package org.jmesa.celleditor;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.message.SpringMessages;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

/**
 * E' un un cell editor costum che trasforma in si o no un intero, in base alla codifica passata al costrutore
 * 
 * @author gianpaolot
 * 
 */
public class SiNoIntegerCellEditor extends AbstractCellEditor {

    private Map<String, Integer> mapLabelValore;

    public SiNoIntegerCellEditor(Map<String, Integer> mapLabelValore) {

	super();
	this.mapLabelValore = mapLabelValore;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Integer value = (Integer) ItemUtils.getItemValue(item, property);
	String valueItem = null;
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	Set<String> listaChiavi = mapLabelValore.keySet();
	for (String key : listaChiavi) {
	    if (value == null) {
		value = 0;
	    }
	    if (value.equals(mapLabelValore.get(key))) {
		String decodificakey = messages.getMessage(key);
		if (decodificakey == null) {
		    decodificakey = "???" + key + "???";
		}
		valueItem = decodificakey;
	    }
	}
	//	String si = messages.getMessage("label.si");
	//	String no = messages.getMessage("label.no");
	//	
	//	if (si == null) {
	//	    si = "???label.si???";
	//	}
	//	if (no == null) {
	//	    no = "???label.no???";
	//	}
	//	if (value != null) {
	//	    if (value.intValue() == valoreCodificaSi) {
	//		valueItem = si;
	//	    }
	//	    if (value.intValue() == valoreCodificaNo) {
	//		valueItem = no;
	//	    }
	//	}
	return valueItem;
    }
}
