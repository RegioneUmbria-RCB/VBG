package org.hibernate.transform;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.Setter;

import org.hibernate.property.BasicIgnoreCasePropertyAccessor;

/**
 * @author a011705
 * 
 *         Transforms resultset to value a bean, via setters.
 * 
 *         Examples:
 * 
 *         bean MyBean as 2 setters : - setId : takes an Integer - setProduct : takes a Product bean
 * 
 *         Product bean as 2 setters : - setId - setLib
 * 
 *         resultset may have these columns : - ID - PRODUCT_ID - PRODUCT_LIB
 * 
 *         A MyBean instance will be created for each tupple, and a Product instance will be also created, filled with
 *         PRODUCT_ID and PRODUCT_LIB values.
 * 
 */
public class IgnoreCaseAliasToBeanResultTransformer implements ResultTransformer {

    private final Class resultClass;
    private Setter[] setters;
    private PropertyAccessor propertyAccessor;
    private String[] instAliases;

    public IgnoreCaseAliasToBeanResultTransformer(Class resultClass) {

	this(resultClass, null);
    }

    /**
     * 
     * @param resultClass
     *            : lCreated bean target class.
     * @param aliases
     *            : allows to override default aliases.
     */
    public IgnoreCaseAliasToBeanResultTransformer(Class resultClass, String[] aliases) {

	if (resultClass == null)
	    throw new IllegalArgumentException("resultClass cannot be null");
	this.resultClass = resultClass;
	this.propertyAccessor = new BasicIgnoreCasePropertyAccessor();
	instAliases = aliases;
    }

    /**
     * @see org.hibernate.transform.ResultTransformer#transformTuple(java.lang.Object[], java.lang.String[])
     */
    public Object transformTuple(Object[] tuple, String[] aliases) {

	Object result;
	if (instAliases != null)
	    aliases = instAliases;
	try {
	    if (setters == null) {
		setters = new Setter[aliases.length];
		for (int i = 0; i < aliases.length; i++) {
		    String alias = aliases[i];
		    if (alias != null) {
			setters[i] = propertyAccessor.getSetter(resultClass, alias);
		    }
		}
	    }
	    result = resultClass.newInstance();
	    for (int i = 0; i < aliases.length; i++) {
		if (setters[i] != null) {
		    setters[i].set(result, tuple[i], null);
		}
	    }
	} catch (InstantiationException e) {
	    throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
	} catch (IllegalAccessException e) {
	    throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
	}
	return result;
    }

    /**
     * @see org.hibernate.transform.ResultTransformer#transformList(java.util.List)
     */
    public List transformList(List collection) {

	return collection;
    }
}
