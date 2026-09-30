/**
 * 
 */
package it.gruppoinit.pal.gp.pay.tracciati;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

/**
 * Implementazione di {@link Iterator} che itera sui valori dei singoli campi di un tracciato record
 * @author Franco.Leone
 *
 */
public class TracciatoRecordPositionalIterator implements Iterator<String> {

    private byte[] data = null;
    private List<RecordProperty<?>> recordProps = null;
    private int position = 0;
    private int propIndex = 0;

    /**
     * Crea un istanza di TracciatoRecordPositionalIterator inizializzato con la lista di {@link RecordProperty} che definiscono il tracciato e il valore dell'intero record 'fullRecord'.
     */
    public TracciatoRecordPositionalIterator(String fullRecord, List<RecordProperty<?>> recordProps) {

	this.data = fullRecord.getBytes();
	this.recordProps = recordProps;
    }

    @Override
    public boolean hasNext() {

	if (this.recordProps == null) {
	    return false;
	}
	RecordProperty<?> prop = nextProperty();
	if (prop == null) {
	    return false;
	}
	return position > -1 && position + 1 < data.length;
    }

    @Override
    public String next() {

	RecordProperty<?> prop = nextProperty();
	byte[] bytes = ArrayUtils.subarray(data, position, position + prop.getLength());
	propIndex++;
	position = position + prop.getLength();
	return new String(bytes);
    }

    @Override
    public void remove() {

	throw new UnsupportedOperationException("operazione remove non supportata da questo iteraror");
    }

    private RecordProperty<?> nextProperty() {

	if (recordProps != null && propIndex < recordProps.size()) {
	    return this.recordProps.get(propIndex);
	}
	return null;
    }
}
