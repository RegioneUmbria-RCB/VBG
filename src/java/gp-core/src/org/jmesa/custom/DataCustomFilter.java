package org.jmesa.custom;

import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractFilterEditor;

public class DataCustomFilter extends AbstractFilterEditor {

    @Override
    public Object getValue() {

	return UtilityJmesa.getCustomFilterDate(getCoreContext().getLimit().getId(), "data");
    }
}
