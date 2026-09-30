package it.gruppoinit.pal.gp.pay.tracciati;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;

public class StringRecordProperty extends AbstractRecordProperty<String> {

    private static final long serialVersionUID = 2921331784280895708L;

    public StringRecordProperty(String name, int length, String format, PAD_TYPE padType, char padWith, boolean truncate) {

	super(name, length, format, padType, padWith, truncate);
    }

    /**
     * Costruttore semplificato per proprietà di tipo stringa che paddano a destra con spazi senza formattazioni particolari del valore e lo troncano se eccede la lunghezza massima
     */
    public StringRecordProperty(String name, int length) {

	super(name, length, null, PAD_TYPE.RIGHT, ' ', true);
    }

    /**
     * Costruttore semplificato per proprietà di tipo stringa che paddano a destra con spazi senza formattazioni particolari del valore
     */
    public StringRecordProperty(String name, int length, boolean truncate) {

	super(name, length, null, PAD_TYPE.RIGHT, ' ', truncate);
    }
    
    

    @Override
    public Class<String> getType() {

	return String.class;
    }

    @Override
    protected String formatValue() {

	if (getValue() != null) {
	    if (StringUtils.isNotBlank(getFormat())) {
		try {
		    return MessageFormat.format(getFormat(), getValue());
		} catch (IllegalArgumentException e) {
		    throw new TracciatoRecordException("formattazione non valida per il tipo stringa nella proprietà " + getName(), e);
		}
	    } else {
		return getValue();
	    }
	} else {
	    return null;
	}
    }

    @Override
    protected String parseValue(String val) {

	return StringUtils.trimToNull(val);
    }
    
    
}
