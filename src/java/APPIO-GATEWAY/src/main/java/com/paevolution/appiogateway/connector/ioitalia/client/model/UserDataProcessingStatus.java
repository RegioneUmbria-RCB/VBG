package com.paevolution.appiogateway.connector.ioitalia.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The status of a user data processing request
 */
public enum UserDataProcessingStatus {

    PENDING("PENDING"), WIP("WIP"), CLOSED("CLOSED");

    private String value;

    UserDataProcessingStatus(String value) {

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
    public static UserDataProcessingStatus fromValue(String value) {

	for (UserDataProcessingStatus b : UserDataProcessingStatus.values()) {
	    if (b.value.equals(value)) {
		return b;
	    }
	}
	throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
