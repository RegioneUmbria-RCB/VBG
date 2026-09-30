/**
 * 
 */
package it.gruppoinit.pal.gp.pay.tracciati;

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * @author Franco.Leone
 *
 */
public class IntegerRecordProperty extends AbstractRecordProperty<Integer> {

    private NumberFormat defaultFormat;
    /**
     * 
     */
    private static final long serialVersionUID = -7165862047534007909L;

    /**
     * Crea una proprietà di tipo Integer col nome, la lunghezza massima e il carattere di padding specificato.
     * Per tutti i tipi numerici se la lunghezza del valore eccede la lunghezza massima si ha un'eccezione.
     * Se il format viene specificato deve esguire le regole dei pattern per i numeri della classe {@link MessageFormat}, 
     * se non specificato viene formattato utilizzando DecimalFormat.getIntegerInstance()
     * @param name
     * @param length
     * @param padWith
     */
    public IntegerRecordProperty(String name, int length, String format, char padWith) {

	super(name, length, format, PAD_TYPE.LEFT, padWith, false);
	defaultFormat = NumberFormat.getIntegerInstance(Locale.ITALY);
	defaultFormat.setGroupingUsed(false);
    }

    /**
     * Crea una proprietà di tipo Integer col nome, la lunghezza massima specificati.
     * Per tutti i tipi numerici se la lunghezza del valore eccede la lunghezza massima si ha un'eccezione.
     * viene formattato utilizzando DecimalFormat.getIntegerInstance() e paddato con zeri a sinistra
     * @param name
     * @param length
     * @param padWith
     */
    public IntegerRecordProperty(String name, int length) {

	super(name, length, null, PAD_TYPE.LEFT, '0', false);
	defaultFormat = NumberFormat.getIntegerInstance(Locale.ITALY);
	defaultFormat.setGroupingUsed(false);
    }

    @Override
    public Class<Integer> getType() {

	return Integer.class;
    }

    @Override
    protected String formatValue() {

	if (getValue() == null) {
	    return null;
	} else {
	    if (StringUtils.isNotBlank(getFormat())) {
		return MessageFormat.format(getFormat(), getValue());
	    } else {
		return this.defaultFormat.format(getValue());
	    }
	}
    }

    @Override
    protected Integer parseValue(String val) {

	if (StringUtils.isBlank(val)) {
	    return null;
	}
	try {
	    if (StringUtils.isNotBlank(getFormat())) {
		MessageFormat mf = new MessageFormat(getFormat());
		Object[] values = mf.parse(val);
		if (values.length > 0) {
		    return (Integer) values[0];
		}
		else {
		    return null;
		}
	    } else {
		return this.defaultFormat.parse(val).intValue();
	    }
	} catch (ParseException e) {
	    throw new TracciatoRecordException("errore nel parsing della proprietà " + getName() + " di tipo int dal valore " + val, e);
	}
    }
}
