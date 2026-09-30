package org.jmesa.filter;

import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractFilterEditor;

public class DataCustomFilterEditor extends AbstractFilterEditor {

    private String idTable;
    private String propertyPath;
    private String idFilter;

    public DataCustomFilterEditor(String idTable, String propertyPath, String idFilter) {

	super();
	this.idTable = idTable;
	this.propertyPath = propertyPath;
	this.idFilter = idFilter;
    }

    @Override
    public Object getValue() {

	return UtilityJmesa.getCustomFilterDate(idTable, propertyPath);
    }
}
