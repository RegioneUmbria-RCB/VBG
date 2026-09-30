package it.gruppoinit.pal.gp.core.features.attivita.istanze.query;

import org.hibernate.type.Type;

public class Param {

    private String sqlFragment;
    private Object value;
    private Type tipoValore;

    public Param(String sqlFragment, Object value, Type tipoValore) {

	super();
	this.sqlFragment = sqlFragment;
	this.value = value;
	this.tipoValore = tipoValore;
    }

    public Type getTipoValore() {

	return tipoValore;
    }

    public String getSqlFragment() {

	return sqlFragment;
    }

    public Object getValue() {

	return value;
    }
}
