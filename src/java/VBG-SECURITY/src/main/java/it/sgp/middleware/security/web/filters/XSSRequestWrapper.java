package it.sgp.middleware.security.web.filters;

import org.owasp.encoder.Encode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class XSSRequestWrapper extends HttpServletRequestWrapper {

    public XSSRequestWrapper(HttpServletRequest request) {

	super(request);
    }

    @Override
    public String[] getParameterValues(String parameter) {

	String[] values = super.getParameterValues(parameter);
	if (values == null) {
	    return null;
	}
	int count = values.length;
	String[] encodedValues = new String[count];
	for (int i = 0; i < count; i++) {
	    encodedValues[i] = sanitizeInput(values[i]);
	}
	return encodedValues;
    }

    private String sanitizeInput(String string) {

	return Encode.forHtml(string);
    }
}
