/*
 * Copyright 2004 original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.jmesa.view.csv;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.List;

import org.jmesa.core.CoreContext;
import org.jmesa.view.AbstractExportView;
import org.jmesa.view.component.Column;
import org.jmesa.view.component.Table;

/**
 * @since 2.0
 * @author Jeff Johnston
 * @author francescop
 */
public class CsvView extends AbstractExportView {

    public CsvView(Table table, CoreContext coreContext) {

	super(table, coreContext);
    }

    public byte[] getBytes() {

	String render = (String) render();
	return render.getBytes();
    }

    public Object render() {

	StringBuilder data = new StringBuilder();
	List<Column> columns = getTable().getRow().getColumns();
	int rowcount = 0;
	Collection<?> items = getCoreContext().getPageItems();
	for (Object item : items) {
	    rowcount++;
	    for (Column column : columns) {
		/**
		 * Precedente versione for (Column column : columns) {
		 * data.append(column.getCellRenderer().render(item,rowcount)); }
		 */
		Object value = column.getCellRenderer().getCellEditor().getValue(item, column.getProperty(), rowcount);
		// START: Format BigDecimal
		// Controllo se il valore è un BigDecimal in tal caso lo formatto ###,##0.00
		if (value instanceof BigDecimal) {
		    NumberFormat numberFormat = new DecimalFormat("###,##0.00");
		    String valueFormat = numberFormat.format(((BigDecimal) value).doubleValue());
		    data.append("\"" + valueFormat + "\",");
		    // END: Format BigDecimal
		} else {
		    if (!((String) column.getCellRenderer().render(item, rowcount)).contains("null")) {
			data.append(column.getCellRenderer().render(item, rowcount));
		    } else {
			data.append("\"\",");
		    }
		}
	    }
	    data.append("\r\n");
	}
	return data.toString();
    }
}
