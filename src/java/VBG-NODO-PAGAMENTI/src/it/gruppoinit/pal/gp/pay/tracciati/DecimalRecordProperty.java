/**
 * 
 */
package it.gruppoinit.pal.gp.pay.tracciati;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * @author Franco.Leone
 *
 */
public class DecimalRecordProperty extends AbstractRecordProperty<BigDecimal> {

    /**
     * 
     */
    private static final long serialVersionUID = -7165862047534007909L;

    /**
     * Crea una proprietà di tipo {@link BigDecimal} col nome, la lunghezza massima e il carattere di padding specificato.
     * Per tutti i tipi numerici se la lunghezza del valore eccede la lunghezza massima si ha un'eccezione.
     * Se il format viene specificato deve esguire le regole dei pattern per i numeri della classe {@link MessageFormat}, 
     * se non specificato viene formattato utilizzando DecimalFormat.getInstance()
     * @param name
     * @param length
     * @param padWith
     */
    public DecimalRecordProperty(String name, int length, String format, char padWith) {

	super(name, length, format, PAD_TYPE.LEFT, padWith, false);
    }

    /**
     * Crea una proprietà di tipo {@link BigDecimal} col nome, la lunghezza massima specificati.
     * Per tutti i tipi numerici se la lunghezza del valore eccede la lunghezza massima si ha un'eccezione.
     * viene formattato utilizzando DecimalFormat.getInstance() e paddato con zeri a sinistra
     * @param name
     * @param length
     * @param padWith
     */
    public DecimalRecordProperty(String name, int length) {

	super(name, length, null, PAD_TYPE.LEFT, '0', false);
    }
    
    

    @Override
    public Class<BigDecimal> getType() {

	return BigDecimal.class;
    }

    @Override
    protected String formatValue() {

	if (getValue() == null) {
	    return null;
	} else {
	    if (StringUtils.isNotBlank(getFormat())) {
		return MessageFormat.format(getFormat(), getValue());
	    } else {
		return DecimalFormat.getInstance().format(getValue());
	    }
	}
    }
    
    @Override
    protected BigDecimal parseValue(String val) {

	if (StringUtils.isBlank(val)) {
	    return null;
	}
	try {
	    if (StringUtils.isNotBlank(getFormat())) {
		MessageFormat mf = new MessageFormat(getFormat());
		Object[] values = mf.parse(val);
		if (values.length > 0) {
		    double doubleVal = ((Number) values[0]).doubleValue();
		    return new BigDecimal(doubleVal);
		}
		else {
		    return null;
		}
	    } else {
		//NumberFormat df = DecimalFormat.getInstance(Locale.ITALY);
		//sostituisco eventuali virgole con punti
		val = val.replace(',', '.');
		return new BigDecimal(val);
	    }
	} catch (ParseException e) {
	    throw new TracciatoRecordException("errore nel parsing della proprietà " + getName() + " di tipo int dal valore " + val, e);
	}
    }
}
