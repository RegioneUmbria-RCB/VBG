package it.gruppoinit.pal.gp.core.filters;

public enum AndOrRestriction {
    AND("E"), OR("O");

    private String statusCode;

    private AndOrRestriction(String s) {

	statusCode = s;
    }

    public String getStatusCode() {

	return statusCode;
    }
}
