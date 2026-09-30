package org.jmesa.custom;

import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractFilterEditor;

public class DataValiditaIstanzacollegataCustomFilter extends AbstractFilterEditor {

    @Override
    public Object getValue() {

	String idtabella = (getCoreContext().getLimit().getId());
	return UtilityJmesa.getCustomFilterDate(idtabella, "istanze.datavalidita");
    }
}
