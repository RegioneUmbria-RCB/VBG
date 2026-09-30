package org.jmesa.custom;

import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractFilterEditor;

public class DataSorteggioSorteggiTCustomFilter extends AbstractFilterEditor {

    @Override
    public Object getValue() {

	return UtilityJmesa.getCustomFilterDate("sorteggitestata_id", "stDatasorteggio");
    }
}
