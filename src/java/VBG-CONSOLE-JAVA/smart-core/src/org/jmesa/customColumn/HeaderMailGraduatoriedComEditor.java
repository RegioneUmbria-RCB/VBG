package org.jmesa.customColumn;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.web.SpringWebContext;

public class HeaderMailGraduatoriedComEditor extends AbstractContextSupport implements HeaderEditor {

    public HeaderMailGraduatoriedComEditor() {

    }

    @Override
    public Object getValue() {

	StringBuffer title = new StringBuffer();
	title.append("<table>");
	title.append("<tr>");
	title.append("<td>").append(getLabel("label.email")).append("</td>");
	title.append("<td>").append(getLabel("label.accettata")).append("</td>");
	title.append("<td>").append(getLabel("label.consegnata")).append("</td>");
	title.append("</tr>");
	title.append("</table>");
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
