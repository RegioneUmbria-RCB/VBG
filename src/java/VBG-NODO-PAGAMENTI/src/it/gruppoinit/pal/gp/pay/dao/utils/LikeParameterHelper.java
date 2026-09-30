package it.gruppoinit.pal.gp.pay.dao.utils;

import org.apache.commons.lang.StringUtils;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

public class LikeParameterHelper implements IParameterHelper {

    private int position;
    private Object value;
    private Type type;

    private LikeParameterHelper() {

	super();
    }

    public LikeParameterHelper(int position, String value) {

	this();
	this.position = position;
	if (!StringUtils.isEmpty(value)) {
	    this.value = "%" + value + "%";
	}
	this.type = new StringType();
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
