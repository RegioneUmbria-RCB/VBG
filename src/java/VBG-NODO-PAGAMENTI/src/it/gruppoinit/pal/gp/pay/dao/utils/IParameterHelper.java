package it.gruppoinit.pal.gp.pay.dao.utils;

import org.hibernate.type.Type;

public interface IParameterHelper {

    public int getPosition();

    public Object getValue();

    public Type getType();
}
