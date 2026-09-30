package org.jmesa.filter;

import java.util.ArrayList;
import java.util.List;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.web.SpringWebContext;

public class SiNoCustomDropListEditor extends DroplistFilterEditor {

    @Override
    protected List<Option> getOptions() {

	List<Option> options = new ArrayList<Option>();
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String si = messages.getMessage("label.si");
	String no = messages.getMessage("label.no");
	if (si == null) {
	    si = "???label.si???";
	}
	if (no == null) {
	    no = "???label.no???";
	}
	options.add(new Option("true", si));
	options.add(new Option("false", no));
	return options;
    }
}
