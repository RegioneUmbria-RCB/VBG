package org.jmesa.custom;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.web.SpringWebContext;


public class AcquisitoHeaderEditor extends AbstractContextSupport implements HeaderEditor {
    
    @Override
    public Object getValue() {

	return "<span title=\"" + getLabel("istanzeprocedimenti.help.colonna_acquisito") + "\">" + getLabel("label.acquis")
		+ "</span>";
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
