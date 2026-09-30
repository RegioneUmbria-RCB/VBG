package it.gruppoinit.pal.gp.core.web.util;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.util.StringUtils;

public class CustomXMLGregorianCalendarEditor extends PropertyEditorSupport {

    private final DateFormat dateFormat;
    private final boolean allowEmpty;
    private final int exactDateLength;

    /**
     * Create a new CustomDateEditor instance, using the given DateFormat for parsing and rendering.
     * <p>
     * The "allowEmpty" parameter states if an empty String should be allowed for parsing, i.e. get interpreted as null
     * value. Otherwise, an IllegalArgumentException gets thrown in that case.
     * 
     * @param dateFormat
     *            DateFormat to use for parsing and rendering
     * @param allowEmpty
     *            if empty strings should be allowed
     */
    public CustomXMLGregorianCalendarEditor(DateFormat dateFormat, boolean allowEmpty) {

	this.dateFormat = dateFormat;
	this.allowEmpty = allowEmpty;
	this.exactDateLength = -1;
    }

    /**
     * Create a new CustomDateEditor instance, using the given DateFormat for parsing and rendering.
     * <p>
     * The "allowEmpty" parameter states if an empty String should be allowed for parsing, i.e. get interpreted as null
     * value. Otherwise, an IllegalArgumentException gets thrown in that case.
     * <p>
     * The "exactDateLength" parameter states that IllegalArgumentException gets thrown if the String does not exactly
     * match the length specified. This is useful because SimpleDateFormat does not enforce strict parsing of the year
     * part, not even with <code>setLenient(false)</code>. Without an "exactDateLength" specified, the "01/01/05" would
     * get parsed to "01/01/0005".
     * 
     * @param dateFormat
     *            DateFormat to use for parsing and rendering
     * @param allowEmpty
     *            if empty strings should be allowed
     * @param exactDateLength
     *            the exact expected length of the date String
     */
    public CustomXMLGregorianCalendarEditor(DateFormat dateFormat, boolean allowEmpty, int exactDateLength) {

	this.dateFormat = dateFormat;
	this.allowEmpty = allowEmpty;
	this.exactDateLength = exactDateLength;
    }

    /**
     * Parse the XMLGregorianCalendar from the given text, using the specified DateFormat.
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

	if (this.allowEmpty && !StringUtils.hasText(text)) {
	    // Treat empty String as null value.
	    setValue(null);
	} else if (text != null && this.exactDateLength >= 0 && text.length() != this.exactDateLength) {
	    throw new IllegalArgumentException("Could not parse date: it is not exactly" + this.exactDateLength + "characters long");
	} else {
	    try {
		Date d = this.dateFormat.parse(text);
		GregorianCalendar gCal = new GregorianCalendar();
		gCal.setTime(d);
		XMLGregorianCalendar cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCal);
		setValue(cal);
	    } catch (Exception ex) {
		throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
	    }
	}
    }

    /**
     * Format the XMLGregorianCalendar as String, using the specified DateFormat.
     */
    @Override
    public String getAsText() {

	XMLGregorianCalendar greg = (XMLGregorianCalendar) getValue();
	if (greg != null) {
	    Date value = greg.toGregorianCalendar().getTime();
	    return this.dateFormat.format(value);
	}
	return "";
    }
}
