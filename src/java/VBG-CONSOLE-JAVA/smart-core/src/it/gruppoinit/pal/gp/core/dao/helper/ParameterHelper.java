package it.gruppoinit.pal.gp.core.dao.helper;

import org.hibernate.type.Type;

/**
 * Rappresenta un parametro da settare alla query di selezione
 * 
 * @author riccardob
 * 
 */
public class ParameterHelper {

    private int position;
    private Object value;
    private Type type;

    private ParameterHelper() {

	super();
    }

    public ParameterHelper(int position, Object value, Type type) {

	this();
	this.position = position;
	this.value = value;
	this.type = type;
    }

    /**
     * @return the position
     */
    public int getPosition() {

	return position;
    }

    /**
     * @return the value
     */
    public Object getValue() {

	return value;
    }

    /**
     * @return the type
     */
    public Type getType() {

	return type;
    }
}