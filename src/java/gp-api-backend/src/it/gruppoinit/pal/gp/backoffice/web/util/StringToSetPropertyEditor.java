package it.gruppoinit.pal.gp.backoffice.web.util;

import java.beans.PropertyEditorSupport;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * Classe per la trasformazione di una lista di parametri in forma di stringa nel corrispondente set di oggetti con la
 * proprietà specificata nel costruttore settata al valore fornito. Utilizzata per popolare oggetti del set con
 * proprietà di tipo String
 * 
 * @author fabrizioc
 * 
 * @param <T>
 *            Tipo della classe della lista creata
 */
public class StringToSetPropertyEditor<T, U> extends PropertyEditorSupport {

    private Class<T> clazz;
    private Class<U> subClazz;
    private String clazzProperty;

    /**
     * 
     * @param clazz
     *            Classe da istanziare in modo da creare un Set con tanti oggetti quanti sono i token della stringa
     *            passata al metodo setAsText
     * @param clazzProperty
     *            nome della proprietà della classe clazz da popolare con il valore del singolo token.
     */
    public StringToSetPropertyEditor(Class<T> clazz, Class<U> subClazz, String clazzProperty) {

	super();
	this.clazz = clazz;
	this.subClazz = subClazz;
	this.clazzProperty = clazzProperty;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

	Set<T> set = new HashSet<T>();
	StringTokenizer st = new StringTokenizer(text, ",");
	while (st.hasMoreTokens()) {
	    String token = st.nextToken();
	    T obj = null;
	    try {
		obj = clazz.newInstance();
	    } catch (InstantiationException e) {
		throw new IllegalArgumentException(e);
	    } catch (IllegalAccessException e) {
		throw new IllegalArgumentException(e);
	    }
	    Method method = null;
	    if (clazzProperty.indexOf(".") > -1) {
		String[] subProperties = clazzProperty.split("\\.");
		Method getId = ReflectionUtils.findMethod(clazz, "get" + StringUtils.capitalize(subProperties[0]), null);
		Object id = ReflectionUtils.invokeMethod(getId, obj, null);
		method = ReflectionUtils.findMethod(subClazz, "set" + StringUtils.capitalize(subProperties[1]), new Class[] { String.class });
		ReflectionUtils.invokeMethod(method, id, new String[] { token });
	    } else {
		method = ReflectionUtils.findMethod(clazz, "set" + StringUtils.capitalize(clazzProperty), new Class[] { String.class });
		ReflectionUtils.invokeMethod(method, obj, new String[] { token });
	    }
	    set.add(obj);
	}
	if (set.size() > 0) {
	    setValue(set);
	} else {
	    setValue(null);
	}
    }
}
