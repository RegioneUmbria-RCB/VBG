package it.gruppoinit.pal.gp.backoffice.web.util;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * Classe per la trasformazione di una lista di parametri in forma di stringa nella corripondente Lista di oggetti con
 * la proprietà specificata nel costruttore settata al valore fornito. Utilizzata per popolare oggetti della Lista con
 * proprietà di tipo Integer
 * 
 * @author fabrizioc
 * 
 * @param <T>
 *            Tipo della classe della Lista creata
 */
public class IntegerToCollectionPropertyEditor<T> extends PropertyEditorSupport {

    private Class<T> clazz;
    private String clazzProperty;

    /**
     * 
     * @param clazz
     *            Classe da istanziare in modo da creare una Collection con tanti oggetti quanti sono i token della
     *            stringa passata al metodo setAsText
     * @param clazzProperty
     *            nome della proprietà della classe clazz da popolare con il valore del singolo token.
     */
    public IntegerToCollectionPropertyEditor(Class<T> clazz, String clazzProperty) {

	super();
	this.clazz = clazz;
	this.clazzProperty = clazzProperty;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

	Collection<T> list = new ArrayList<T>();
	StringTokenizer st = new StringTokenizer(text, ",");
	while (st.hasMoreTokens()) {
	    Integer token = new Integer(st.nextToken());
	    T obj = null;
	    try {
		obj = clazz.newInstance();
	    } catch (InstantiationException e) {
		throw new IllegalArgumentException(e);
	    } catch (IllegalAccessException e) {
		throw new IllegalArgumentException(e);
	    }
	    Method method = ReflectionUtils.findMethod(clazz, "set" + StringUtils.capitalize(clazzProperty), new Class[] { Integer.class });
	    ReflectionUtils.invokeMethod(method, obj, new Integer[] { token });
	    list.add(obj);
	}
	if (list.size() > 0) {
	    setValue(list);
	} else {
	    setValue(null);
	}
    }
}
