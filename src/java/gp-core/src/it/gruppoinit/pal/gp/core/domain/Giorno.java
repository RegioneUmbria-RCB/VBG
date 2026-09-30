package it.gruppoinit.pal.gp.core.domain;

import java.util.Calendar;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

// Oggetto non appartenete al dominio
public class Giorno {

    private Calendar data;

    public Calendar getData() {

	return data;
    }

    public void setData(Calendar data) {

	this.data = data;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
