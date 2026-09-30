package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.Calendar;
import java.util.Date;

import org.jmesa.view.editor.AbstractCellEditor;

public class VwConcessioniDataCessazioneCellEditor extends AbstractCellEditor {

    public VwConcessioniDataCessazioneCellEditor() {

    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Object oggetto = (Object) item;
	Date dataStorico = (Date) UtilityJmesa.getParametro(oggetto, "dataStorico");
	String valueItem = "";
	if (dataStorico != null) {
	    Calendar d_31_12_9999 = Utilities.getDate("31/12/9999", WebConstants.DATE_FORMAT_PATTERN);
	    int compare = Utilities.compareDates(dataStorico, d_31_12_9999.getTime());
	    if (compare != 0) {
		valueItem = Utilities.formatDate(dataStorico, false);
	    }
	}
	return valueItem;
    }
}
