/**
 * 
 */
package it.gruppoinit.pal.gp.pay.tracciati;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @author Franco.Leone
 *
 */
public class DateRecordProperty extends AbstractRecordProperty<Date> {

    /**
     * 
     */
    private static final long serialVersionUID = -6798105663403158540L;

    /**
     * Crea una prorpietà di tipo {@link Date} del tracciato record col nome ed il formato specificato
     * @param name
     * @param format
     */
    public DateRecordProperty(String name, int length, String format) {

	super(name, length, format, PAD_TYPE.RIGHT, ' ', false);
    }

    @Override
    public Class<Date> getType() {

	return Date.class;
    }

    @Override
    protected String formatValue() {

	if (getFormat() == null) {
	    throw new TracciatoRecordException("formato data non specificato per la proprietà " + getName());
	}
	if (getValue() != null) {
	    try {
		SimpleDateFormat sdf = new SimpleDateFormat(getFormat());
		return sdf.format(getValue());
	    } catch (Exception e) {
		throw new TracciatoRecordException("errore nella formattazione della data per la proprietà " + getName(), e);
	    }
	}
	return null;
    }

    @Override
    protected Date parseValue(String val) {

	if (getFormat() == null) {
	    throw new TracciatoRecordException("formato data non specificato per la proprietà " + getName());
	}
	if(StringUtils.isNotBlank(val)) {
	    try {
		SimpleDateFormat sdf = new SimpleDateFormat(getFormat());
		return sdf.parse(val);
	    } catch (Exception e) {
		throw new TracciatoRecordException("errore nel parsing della data per la proprietà " + getName() + " dal valore " + val, e);
	    }
	}
	return null;
    }
    
    
}
