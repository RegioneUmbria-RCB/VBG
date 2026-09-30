package org.jmesa.view.editor;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.time.DateFormatUtils;
import org.jmesa.util.ItemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * An editor to work with dates. Just send in a valid date pattern and the date will be formated.
 * 
 * @since 2.0
 * @author Jeff Johnston
 * @author francescop
 */
public class DateCellEditor extends AbstractPatternCellEditor {

    private static final Logger logger = LoggerFactory.getLogger(DateCellEditor.class);

    public DateCellEditor() {

	// default constructor
    }

    /**
     * @param pattern
     *            The pattern to use.
     */
    public DateCellEditor(String pattern) {

	setPattern(pattern);
    }

    /**
     * Get the formatted date value based on the pattern set.
     */
    public Object getValue(Object item, String property, int rowcount) {

	Object itemValue = null;
	try {
	    itemValue = ItemUtils.getItemValue(item, property);
	    if (itemValue == null) {
		return null;
	    }
	    if (itemValue instanceof String) {
		String data = (String) itemValue;
		// Effettuo il controllo se la data che arriva è in formato di stringa senza "/"
		if (!data.contains("/")) {
		    DateFormat myDateFormat = new SimpleDateFormat("yyyyMMdd");
		    Date myDate = null;
		    try {
			myDate = myDateFormat.parse(data);
		    } catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("ERRORE: non è possibile trasformare la data");
		    }
		    DateFormat myDateFormatOut = new SimpleDateFormat("dd/MM/yyyy");
		    myDateFormatOut.format(myDate);
		    String outdate = myDateFormatOut.format(myDate);
		    itemValue = myDateFormatOut.parse(outdate);
		}
	    }
	    // modifica per esportare le date nel formato fissato nelle WebConstants
	    // cambiato : Locale locale = getWebContext().getLocale();
	    // cambiato : itemValue = DateFormatUtils.format((Date) itemValue, getPattern(), locale);
	    Locale locale = LocaleContextHolder.getLocale();
	    itemValue = DateFormatUtils.format((Date) itemValue, WebConstants.DATE_FORMAT_PATTERN, locale);
	} catch (Exception e) {
	    logger.warn("Could not process date editor with property " + property, e);
	}
	return itemValue;
    }
}
