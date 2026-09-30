package org.jmesa.custom;

import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractFilterEditor;

public class DataIstanzaCommissioneRCustomFilter extends AbstractFilterEditor {

    @Override
    public Object getValue() {

	return UtilityJmesa.getCustomFilterDate(getCoreContext().getLimit().getId(), "movimento.istanza.data");
    }
}
