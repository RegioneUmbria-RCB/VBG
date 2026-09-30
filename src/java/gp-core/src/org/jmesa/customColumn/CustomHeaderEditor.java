package org.jmesa.customColumn;

import org.apache.commons.lang.StringUtils;
import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.web.SpringWebContext;

/**
 * 
 * @author gianpaolot
 * 
 */
public class CustomHeaderEditor extends AbstractContextSupport implements HeaderEditor {

    private String labelHeader = "";
    private String titleHeader = "";

    public CustomHeaderEditor(String labelHeader, String titleHeader) {

	this.labelHeader = labelHeader;
	this.titleHeader = titleHeader;
    }

    @Override
    public Object getValue() {

	String title = "<span title=\"" + getLabel(titleHeader) + "\">" + getLabel(labelHeader) + "</span>";
	if (StringUtils.isBlank(title)) {
	    title = "???Contenuto non Settato???";
	}
	return title;
    }

    // recupera la label dal CoreContext
    private String getLabel(String label) {

	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String message = messages.getMessage(label);
	if (message == null) {
	    message = "???" + label + "???";
	}
	return message;
    }
}
