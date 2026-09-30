package it.gruppoinit.pal.gp.core.web.util;

import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;

import java.beans.PropertyEditorSupport;

public class CustomAndORFilterEditor extends PropertyEditorSupport {

    public CustomAndORFilterEditor() {

    }

    @Override
    public String getAsText() {

	AndOrRestriction result = (AndOrRestriction) getValue();
	if (result != null) {
	    return result.toString();
	}
	return "";
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
	
	setValue(text);
    }
}
