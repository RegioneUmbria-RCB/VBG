package org.jmesa.customColumn;

import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.renderer.HtmlCellRendererImpl;

public class SplittedStringCellRenderer extends HtmlCellRendererImpl {

    private String separator = " ";

    @Override
    public Object render(Object item, int rowcount) {

	String propName = getColumn().getProperty();
	String propVal = (String) getCellEditor().getValue(item, propName, rowcount);
	HtmlBuilder html = new HtmlBuilder();
	html.td(1);
	html.close();
	String[] splittedValue = propVal.split(getSeparator());
	if (splittedValue.length > 0) {
	    for (int i = 0; i < splittedValue.length - 1; i++) {
		html.span().close().append(splittedValue[i]);
		html.spanEnd().br();
	    }
	    html.span().close().append(splittedValue[splittedValue.length - 1]);
	    html.spanEnd();
	}
	html.tdEnd();
	return html.toString();
    }

    public String getSeparator() {

	return separator;
    }

    public void setSeparator(String separator) {

	this.separator = separator;
    }
}
