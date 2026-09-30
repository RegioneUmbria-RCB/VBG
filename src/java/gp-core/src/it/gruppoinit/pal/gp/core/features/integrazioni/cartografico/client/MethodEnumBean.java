package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MethodEnumBean {

    GET("GET"),
    POST("POST");

    private final String value;

    MethodEnumBean(String value) {

	this.value = value;
    }

    @JsonValue
    public String getValue() {

	return value;
    }

    @Override
    public String toString() {

	return String.valueOf(value);
    }

    @JsonCreator
    public static MethodEnumBean fromValue(String value) {

	for (MethodEnumBean b : MethodEnumBean.values()) {
	    if (b.value.equals(value)) {
		return b;
	    }
	}
	throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}