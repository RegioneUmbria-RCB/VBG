package com.paevolution.appiogateway.connector.ioitalia.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * User's choice to delete or download his own data.
 */
public enum UserDataProcessingChoice {

    DOWNLOAD("DOWNLOAD"), DELETE("DELETE");

    private String value;

    UserDataProcessingChoice(String value) {

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
    public static UserDataProcessingChoice fromValue(String value) {

	for (UserDataProcessingChoice b : UserDataProcessingChoice.values()) {
	    if (b.value.equals(value)) {
		return b;
	    }
	}
	throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
