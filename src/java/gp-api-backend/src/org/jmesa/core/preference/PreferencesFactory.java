package org.jmesa.core.preference;

import org.jmesa.web.WebContext;

public class PreferencesFactory {

    private static final String JMESA_PREFERENCES_LOCATION = "jmesaPreferencesLocation";

    public static Preferences getPreferences(WebContext webContext) {

	String jmesaPreferencesLocation = (String) webContext.getApplicationInitParameter(JMESA_PREFERENCES_LOCATION);
	return new UserPreferences(jmesaPreferencesLocation, webContext);
    }
}
