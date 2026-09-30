/**
 * 
 */
package it.gruppoinit.pal.gp.pay.tracciati;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.iterators.ArrayIterator;
import org.apache.commons.collections4.list.SetUniqueList;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Franco.Leone
 *
 */
public class TracciatoRecord {

    private String separator;
    public List<RecordProperty<?>> proprieta = SetUniqueList.setUniqueList(new ArrayList<RecordProperty<?>>());

    public TracciatoRecord() {

    }

    public String getSeparator() {

	return separator;
    }

    public void setSeparator(String separator) {

	this.separator = separator;
    }

    public <T extends RecordProperty<?>> void addProperty(T prop) {

	proprieta.add(prop);
    }

    public <T extends RecordProperty<?>> RecordProperty<?> getProperty(String propName) {

	int idxProp = proprieta.indexOf(new StringRecordProperty(propName, 0));
	if (idxProp > -1) {
	    return this.proprieta.get(idxProp);
	} else {
	    return null;
	}
    }

    public <T> void setValue(String propName, T value) {

	RecordProperty<?> prop = getProperty(propName);
	if (prop != null) {
	    if (value != null) {
		if (prop.getType().isAssignableFrom(value.getClass())) {
		    ((RecordProperty<T>) prop).setValue(value);
		} else {
		    throw new TracciatoRecordException("La proprietà " +
			    propName +
			    " è di tipo " +
			    prop.getType().getName() +
			    " e non può accettare valori del tipo " +
			    value.getClass().getName());
		}
	    } else {
		prop.setValue(null);
	    }
	} else {
	    throw new TracciatoRecordException("La proprietà " + propName + " non esiste nel tracciato: impossibile impostare il valore");
	}
    }

    public <T> T getValue(String propName) {

	RecordProperty<?> prop = getProperty(propName);
	if (prop != null) {
	    return ((RecordProperty<T>) prop).getValue();
	} else {
	    throw new TracciatoRecordException("La proprietà " + propName + " non esiste nel tracciato: impossibile recuperare il valore");
	}
    }

    public String writeRecord() {

	StringBuilder sbRec = new StringBuilder();
	Iterator<RecordProperty<?>> propIter = proprieta.iterator();
	RecordProperty<?> prop = null;
	while (propIter.hasNext()) {
	    prop = propIter.next();
	    sbRec.append(prop.writeValue());
	    if (propIter.hasNext()) {
		sbRec.append(StringUtils.defaultString(getSeparator()));
	    }
	}
	return sbRec.toString();
    }

    public void readRecord(String rawData) {

	Iterator<String> valoriIter = getIteratorValoriTracciato(rawData);
	Iterator<RecordProperty<?>> propsIter = this.proprieta.iterator();
	RecordProperty<?> recordProperty = null;
	while (propsIter.hasNext()) {
	    if(!valoriIter.hasNext()) {
		String msg = recordProperty == null ? "i dati del tracciato sono vuoti" : "il tracciato si interrompe dopo la proprietà " + recordProperty.getName();
		throw new TracciatoRecordException(msg);
	    }
	    recordProperty = propsIter.next();
	    String val = valoriIter.next();
	    recordProperty.readValue(val);
	}
    }

    @SuppressWarnings("unchecked")
    private Iterator<String> getIteratorValoriTracciato(String recordData){
	
	if(StringUtils.isEmpty(this.getSeparator())) {
	    return new TracciatoRecordPositionalIterator(recordData, this.proprieta);
	}
	else {
	    //TODO realizzare in iterator che restituisce i valori parsando la stringa di input fino al separatore successivo tenendo conto che il separatore potrebbe essere presente nei valori stringa
	    String[] splittedValues = StringUtils.splitPreserveAllTokens(recordData, this.separator);
	    return new ArrayIterator(splittedValues);
	}
    }
}
