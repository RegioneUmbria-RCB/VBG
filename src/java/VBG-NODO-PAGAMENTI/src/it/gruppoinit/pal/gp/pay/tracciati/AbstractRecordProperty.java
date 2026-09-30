package it.gruppoinit.pal.gp.pay.tracciati;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public abstract class AbstractRecordProperty<E> implements RecordProperty<E> {

    private static final char NULL_VALUE_PAD_CHAR = ' ';
    private String name;
    private int length;
    private String format;
    private PAD_TYPE padType = PAD_TYPE.NONE;
    private char padWith = ' ';
    private boolean truncate = true;
    private E value;

    public AbstractRecordProperty(String name, int length, String format, PAD_TYPE padType, char padWith, boolean truncate) {

	this.name = name;
	this.length = length;
	this.format = format;
	this.padType = padType;
	this.padWith = padWith;
	this.truncate = truncate;
    }
    
    @Override
    public String getName() {

	return name;
    }

    @Override
    public int getLength() {

	return length;
    }

    @Override
    public String getFormat() {

	return format;
    }

    @Override
    public PAD_TYPE getPaddingType() {

	return padType;
    }

    @Override
    public char getPaddingChar() {

	return padWith;
    }

    public boolean isTruncate() {

	return truncate;
    }

    @Override
    public E getValue() {

	return value;
    }

    @Override
    public void setValue(E value) {

	this.value = value;
    }

    @Override
    public String writeValue() {

	String out = this.formatValue();
	char padChar = this.padWith;
	if (out == null) {
	    padChar = NULL_VALUE_PAD_CHAR;
	    out = "";
	}
	if (out.length() < this.length) {
	    switch (getPaddingType()) {
	    case LEFT:
		out = StringUtils.leftPad(out, this.length, padChar);
		break;
	    case RIGHT:
		out = StringUtils.rightPad(out, this.length, padChar);
		break;
	    default:
		break;
	    }
	} else if (out.length() > this.length && this.length > 0) {
	    if (isTruncate()) {
		out = StringUtils.left(out, this.length);
	    } else {
		throw new TracciatoRecordException("La proprietà " +
			getName() +
			" contiene il valore " +
			out +
			" che supera la lunghezza massima di " +
			getLength() +
			" caratteri.");
	    }
	}
	return out;
    }
    
    

    @Override
    public void readValue(String val) {

	//elimino i caratteri aggiunti per paddare il valore
	String cleanVal = val;
	if (cleanVal.length() > 0) {
	    if (this.padType.equals(PAD_TYPE.RIGHT)) {
		cleanVal = StringUtils.stripEnd(val, new String(new char[] { this.padWith }));
	    } else if (this.padType.equals(PAD_TYPE.LEFT)) {
		cleanVal = StringUtils.stripStart(val, new String(new char[] { this.padWith }));
	    }
	    if(cleanVal.length() == 0) {
		cleanVal = new String(new char[] { this.padWith });
	    }
	}
	this.value = this.parseValue(cleanVal);
    }

    /** 
     * Da ridefinire nelle sottoclassi per la formattazione del tipo di dato specifico in base al format specificato.
     * Se il valore della proprietà può restituire null che darà luogo ad una stringa vuota nell'output del record.
     * 
     * @return the formatted value or null if value is null
     */
    protected abstract String formatValue();
    
    /**
     * Da ridefinire nelle sottoclassi per il parsing dei valori stringa e la costruzione di un oggetto del tipo specifico per la proprietà.
     * La stringa passata in input è già stata trimmata e ripulita dai caratteri di padding
     * @param val
     * @return
     */
    protected abstract E parseValue(String val); 

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    /**
     * Due proprietà sono considerate uguali quando hanno lo stesso nome
     */
    @Override
    public boolean equals(Object obj) {

	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (!RecordProperty.class.isAssignableFrom(obj.getClass())) {
	    return false;
	}
	RecordProperty other = (RecordProperty) obj;
	if (name == null) {
	    if (other.getName() != null) {
		return false;
	    }
	} else if (!name.equals(other.getName())) {
	    return false;
	}
	return true;
    }
}
