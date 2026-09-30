package org.jmesa.custom;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.editor.HeaderEditor;

public class CustomHeaderEditor implements HeaderEditor {

    private String headerContent = "";

    public CustomHeaderEditor(String headerContent) {

	this.headerContent = headerContent;
    }

    @Override
    public Object getValue() {

	if (StringUtils.isBlank(this.headerContent)) {
	    this.headerContent = "???Contenuto non Settato???";
	}
	return this.headerContent;
    }
}
