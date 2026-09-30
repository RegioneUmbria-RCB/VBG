package it.gruppoinit.pal.gp.backoffice.web.util;

import it.gruppoinit.pal.gp.core.domain.Software;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

public class SoftwareClassEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

	if (StringUtils.isNotBlank(text)) {
	    Software software = new Software();
	    software.setCodice(text);
	    setValue(software);
	} else {
	    setValue(null);
	}
    }
}
