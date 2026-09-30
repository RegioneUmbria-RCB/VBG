package it.gruppoinit.stc.web.helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.jmesa.limit.Filter;
import org.jmesa.limit.FilterSet;
import org.jmesa.limit.Limit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasePageItem<E> {

    private static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy";
    private static final Logger log = LoggerFactory.getLogger(BasePageItem.class);

    public E getFilterJmesaToBean(FilterSet setFiltriJmesa, E oggetto) {

	// recupero il set di filtri passati a jmesa
	Collection<Filter> filters = setFiltriJmesa.getFilters();
	// cliclo tutti i filtri
	for (Filter filter : filters) {
	    try {
		// Controllo se la proprietà semplice o annidata.
		// Nel caso sia annidata (Es bean1.bean2.proprieta)
		if (filter.getProperty().contains(".")) {
		    // Faccio lo split con il regex "." e recupero metto il paht della proprietà
		    // in un array di stringhe.
		    String[] field = filter.getProperty().split("\\.");
		    Object object = null;
		    // Tramite la reflection faccio il get del primo valore dell'array [bean1]
		    Method get = oggetto.getClass().getMethod("get" + StringUtils.capitalize(field[0]));
		    object = get.invoke(oggetto, new Object[0]);
		    // ricorsivamente all'oggetto objec ricavato faccio il get usando il valore dell 'array
		    //[1,arrray.lenght-1]
		    for (int i = 1; i < field.length - 1; i++) {
			get = object.getClass().getMethod("get" + StringUtils.capitalize(field[i]));
			object = get.invoke(object, new Object[0]);
		    }
		    // L'ultimo campo dell'array rappresenta il valore mostrato sulla tabella e per cui vogliamo filtrare il risulato
		    // Il metodo get mi serve solo per recuperare il tipo di ogetto (Integer,String,Date....)
		    Method get2 = object.getClass().getMethod("get" + StringUtils.capitalize(field[field.length - 1]));
		    get2.getReturnType();
		    // A questo punto creo con la reflection il metodo set dela proprietà e setto il valore del filtro jmesa
		    Method set = object.getClass().getMethod("set" + StringUtils.capitalize(field[field.length - 1]), get2.getReturnType());
		    // L'oggetto con il tipo che la signatura del metodo si aspetta (Integer ,Date,String,..etc)
		    Object typePropertySet = convertStringToPrimitiveObject(filter.getValue(), get2.getReturnType().getName());
		    set.invoke(object, typePropertySet);
		} else {// Nel caso non sia annidata (proprieta)
			// esuguo subito il set della proprietà passata
		    Method get2 = oggetto.getClass().getMethod("get" + StringUtils.capitalize(filter.getProperty()));
		    Method set = oggetto.getClass().getMethod("set" + StringUtils.capitalize(filter.getProperty()), get2.getReturnType());
		    // L'oggetto con il tipo che la signatura del metodo si aspetta (Integer ,Date,String,..etc)
		    Object object = convertStringToPrimitiveObject(filter.getValue(), get2.getReturnType().getName());
		    set.invoke(oggetto, object);
		}
	    } catch (SecurityException e) {
		log.debug("SecurityException: " + e);
		e.printStackTrace();
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
	return oggetto;
    }

    private Object convertStringToPrimitiveObject(String filterValue, String type) {

	Object risultato = null;
	if (type.contains("Date")) {
	    risultato = new GregorianCalendar();
	    risultato = getDate(filterValue, DATE_FORMAT_PATTERN).getTime();
	}
	if (type.contains("Integer"))
	    risultato = (Integer) new Integer(filterValue);
	if (type.contains("String"))
	    risultato = (String) filterValue;
	if (type.contains("Boolean"))
	    risultato = Boolean.valueOf(filterValue);
	return risultato;
    }

    private GregorianCalendar getDate(String date, String format) {

	SimpleDateFormat sdf = null;
	GregorianCalendar gd = new GregorianCalendar();
	try {
	    if (StringUtils.isNotBlank(format)) {
		sdf = new SimpleDateFormat(format);
	    } else {
		sdf = new SimpleDateFormat(DATE_FORMAT_PATTERN);
	    }
	    Date d = sdf.parse(date);
	    gd.setTime(d);
	} catch (ParseException e) {
	    log.error("getDate(): {}", e.getMessage());
	}
	return gd;
    }

    public E getFilterQuery(E oggetto, Limit limit) {

	FilterSet filterSet = limit.getFilterSet();
	return getFilterJmesaToBean(filterSet, oggetto);
    }
}
