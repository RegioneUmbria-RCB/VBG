package org.jmesa.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.web.SpringWebContext;

public class IntegerCustomDropListEditor extends DroplistFilterEditor {

    private Map<String, Integer> mappaLabelValore;

    public IntegerCustomDropListEditor(Map<String, Integer> mappaLabelValore) {

	super();
	this.mappaLabelValore = mappaLabelValore;
    }

    @Override
    protected List<Option> getOptions() {

	List<Option> options = new ArrayList<Option>();
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	Set<String> keys = mappaLabelValore.keySet();
	for (String key : keys) {
	    mappaLabelValore.get(key);
	    String label = messages.getMessage(key);
	    if (label == null) {
		label = "???" + key + "???";
	    }
	    options.add(new Option(String.valueOf(mappaLabelValore.get(key)), label));
	}
	return options;
    }
}
