package it.gruppoinit.pal.gp.areariservata.web.util;

import java.util.Date;
import java.util.Locale;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.time.DateFormatUtils;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.DateCellEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtendedDateCellEditor extends DateCellEditor {

    private Logger logger = LoggerFactory.getLogger(ExtendedDateCellEditor.class);

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Object itemValue = null;
	try {
	    itemValue = ItemUtils.getItemValue(item, property);
	    if (itemValue == null) {
		return null;
	    }
	    Locale locale = getWebContext().getLocale();
	    if (itemValue instanceof XMLGregorianCalendar) {
		itemValue = DateFormatUtils.format(((XMLGregorianCalendar) itemValue).toGregorianCalendar().getTime(), getPattern(), locale);
	    } else {
		itemValue = DateFormatUtils.format((Date) itemValue, getPattern(), locale);
	    }
	} catch (Exception e) {
	    logger.warn("Could not process date editor with property " + property, e);
	}
	return itemValue;
    }
}
