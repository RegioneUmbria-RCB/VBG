package org.jmesa.custom;

import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractFilterEditor;

public class DataInizioCoefficienteCustomFilter extends AbstractFilterEditor {

    @Override
    public Object getValue() {

	return UtilityJmesa.getCustomFilterDate("ccvaliditacoefficienti_id", "datainiziovalidita");
    }
}
