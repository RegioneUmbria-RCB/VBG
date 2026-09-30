package org.jmesa.custom;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Questa classe estende BeanComparator e esegue l'override del metodo compare in modo tale da eliminare il null value
 * exception quando si esegue il compare tra proprietà nulle di bean interni.
 * 
 * @author Francesco Palenga
 * 
 */
@SuppressWarnings("serial")
public class JMesaBeanComparator extends BeanComparator {

    public JMesaBeanComparator() {

	super();
    }

    @SuppressWarnings("unchecked")
    public JMesaBeanComparator(String property, Comparator comparator) {

	super(property, comparator);
    }

    public JMesaBeanComparator(String property) {

	super(property);
    }

    @SuppressWarnings("unchecked")
    @Override
    public int compare(Object o1, Object o2) {

	if (getProperty() == null) {
	    return getComparator().compare(o1, o2);
	}
	try {
	    Object value1 = null;
	    Object value2 = null;
	    try {
		value1 = PropertyUtils.getProperty(o1, getProperty());
	    } catch (NestedNullException nne) {
	    }
	    try {
		value2 = PropertyUtils.getProperty(o2, getProperty());
	    } catch (NestedNullException nne) {
	    }
	    if (value1 instanceof String || value2 instanceof String) {
		return getComparator().compare(StringUtils.defaultString((String) value1).toLowerCase(),
			StringUtils.defaultString((String) value2).toLowerCase());
	    }
	    return getComparator().compare(value1, value2);
	} catch (IllegalAccessException e) {
	    throw new RuntimeException("IllegalAccessException: " + e.toString());
	} catch (InvocationTargetException e) {
	    throw new RuntimeException("InvocationTargetException: " + e.toString());
	} catch (NoSuchMethodException e) {
	    throw new RuntimeException("NoSuchMethodException: " + e.toString());
	}
    }
}
