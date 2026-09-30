package org.jmesa.custom;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.AbstractContextSupport;
import org.jmesa.view.editor.HeaderEditor;
import org.jmesa.web.SpringWebContext;

public class DocIstanzaPresenteHeaderEditor extends AbstractContextSupport implements HeaderEditor {

    @Override
    public Object getValue() {

	//String javascript = createJavascript(corpoJavascript);
	//	return " <input id=\"id_check_presente_tot\" type=\"checkbox\" name=\"\" onclick=\"selezionaTuttiPresentiDocIstanza();\" title=\""
	//		+ getLabel("label.presente") + "\">" + "</input>  <span title=\"" + getLabel("label.presente") + "\">" + getLabel("label.presente")
	//		+ "</span>";
	return "<span>"
		+ "<img id=\"id_check_presente_tot\" align=\"left\" src=\"../images/piu.gif\" onclick=\"selezionaDeselezionaTuttiPresentiDocIstanza(true);\" title=\"Seleziona tutti\" /> "
		+ "<img id=\"id_check_presente_tot\" align=\"left\" src=\"../images/meno.gif\" onclick=\"selezionaDeselezionaTuttiPresentiDocIstanza(false);\" title=\"Deseleziona tutti\" />"
		+ getLabel("label.presente") + "</span>";
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
