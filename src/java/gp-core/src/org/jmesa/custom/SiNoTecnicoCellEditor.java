package org.jmesa.custom;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

/**
 * 
 * @author gianpaolot La classe serve per editare la cella tipologia nella lista delle anagrafiche. La tipologia di un
 *         anagrafica può assumere due valori: Tecnico (-1), Richiedente (0). Il cell editor è implementato in modo da
 *         restistuire il valore "si" se è un tecnico (-1) e no se è un richiedente (0)
 */
public class SiNoTecnicoCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Integer value = (Integer) ItemUtils.getItemValue(item, property);
	String valueItem = null;
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String si = messages.getMessage("list.jmesa.celleditor.si");
	String no = messages.getMessage("list.jmesa.celleditor.no");
	if (si == null) {
	    si = "???list.jmesa.celleditor.si???";
	}
	if (no == null) {
	    no = "???list.jmesa.celleditor.si???";
	}
	if (value != null) {
	    if (value.equals(new Integer("-1"))) {
		valueItem = si;
	    }
	    if (value.equals(new Integer("0"))) {
		valueItem = no;
	    }
	}
	return valueItem;
    }
}
