package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CvsRecordsHelper<E> {

    private static final Logger log = LoggerFactory.getLogger(CvsRecordsHelper.class);
    private List<String> header = new ArrayList<String>();
    private List<List<String>> records = new ArrayList<List<String>>();

    public CvsRecordsHelper(List<E> listaOggettiDaEsportare, Map<String, String> heardCampi) {

	get(listaOggettiDaEsportare, heardCampi);
    }

    public List<String> getHeader() {

	return header;
    }

    public void setHeader(List<String> header) {

	this.header = header;
    }

    public List<List<String>> getRecords() {

	return records;
    }

    public void setRecords(List<List<String>> records) {

	this.records = records;
    }

    private void get(List<E> listaOggettiDaEsportare, Map<String, String> heardCampi) {

	try {
	    log.debug("get# populate propriera CvsRecordsHelper.header ");
	    for (Map.Entry<String, String> entry : heardCampi.entrySet()) {
		//cvsRecordsHelper.getHeader().add(entry.getKey());
		this.header.add(entry.getKey());
	    }
	    log.debug("get# populate propriera CvsRecordsHelper.records ");
	    int numrecord = 0;
	    for (E oggettiDaEsportare : listaOggettiDaEsportare) {
		List<String> record = new ArrayList<String>();
		for (Map.Entry<String, String> entry : heardCampi.entrySet()) {
		    String path = entry.getValue();
		    log.debug("get# recupero valore per la proprietà {} per il record num: {}", path, numrecord);
		    // Controllo se la proprietà semplice o annidata.
		    // Nel caso sia annidata (Es bean1.bean2.proprieta)
		    if (entry.getValue().contains(".")) {
			//			// Faccio lo split con il regex "." e recupero metto il paht della proprietà
			//			// in un array di stringhe.
			//			String[] field = path.split("\\.");
			//			// Tramite la reflection faccio il get del primo valore dell'array [bean1]
			//			Method get = oggettiDaEsportare.getClass().getMethod("get" + StringUtils.capitalize(field[0]));
			//			object = get.invoke(oggettiDaEsportare, new Object[0]);
			//			// ricorsivamente all'oggetto objec ricavato faccio il get usando il valore dell 'array
			//			//[1,arrray.lenght-1]
			//			for (int i = 1; i < field.length - 1; i++) {
			//			    get = object.getClass().getMethod("get" + StringUtils.capitalize(field[i]));
			//			    object = get.invoke(object, new Object[0]);
			//			}
			//			// L'ultimo campo dell'array rappresenta il valore mostrato sulla tabella e per cui vogliamo filtrare il risulato
			//			// Il metodo get mi serve solo per recuperare il tipo di ogetto (Integer,String,Date....)
			//			Method get2 = object.getClass().getMethod("get" + StringUtils.capitalize(field[field.length - 1]));
			//			get2.getReturnType();
			//			object = get2.invoke(object, new Object[0]);
			throw new NotImplementedException(
				"Le proprietà annidate (bean1.bean2.proprieta) non sono ancora supportate. Vedere CvsRecordsHelper.get()");
		    } else {// Nel caso non sia annidata (proprieta)
			    // esuguo subito il set della proprietà passata
			log.debug("get# eseguo metodo: get{}", StringUtils.capitalize(path));
			Method get2 = oggettiDaEsportare.getClass().getMethod("get" + StringUtils.capitalize(path));
			Object object1 = get2.invoke(oggettiDaEsportare, new Object[0]);
			log.debug("get# formatto valore");
			String valore = format(object1, get2.getReturnType().getName());
			record.add(valore);
		    }
		}
		numrecord++;
		records.add(record);
	    }
	} catch (SecurityException e) {
	    log.debug("SecurityException: " + e);
	} catch (NoSuchMethodException e) {
	    log.debug("NoSuchMethodException " + e);
	} catch (IllegalArgumentException e) {
	    log.debug("IllegalArgumentException " + e);
	} catch (IllegalAccessException e) {
	    log.debug("IllegalAccessException " + e);
	} catch (InvocationTargetException e) {
	    log.debug("InvocationTargetException " + e);
	}
    }

    private String format(Object valore, String type) {

	String risultato = "";
	try {
	    if (valore != null) {
		if (type.contains("Date")) {
		    log.debug("format# Tipo valore {}", type);
		    risultato = Utilities.formatDate((Date) valore, WebConstants.DATE_FORMAT_PATTERN);
		} else if (type.contains("Integer")) {
		    Integer i = new Integer(0);
		    log.debug("format# Tipo valore {}", type);
		    i = (Integer) valore;
		    risultato = i.toString();
		} else if (type.contains("String")) {
		    log.debug("format# Tipo valore {}", type);
		    risultato = (String) valore;
		} else if (type.contains("Boolean")) {
		    log.debug("format# Tipo valore {}", type);
		    risultato = Boolean.toString((Boolean) valore);
		}
	    }
	} catch (Exception e) {
	    System.out.println("");
	}
	return risultato;
    }
}
