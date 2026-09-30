package org.jmesa.custom;

import java.util.ArrayList;
import java.util.List;

import org.jmesa.view.html.editor.DroplistFilterEditor;

public class TipibandoDroplist extends DroplistFilterEditor {

    @Override
    protected List<Option> getOptions() {

	List<Option> options = new ArrayList<Option>();
	String si = getCoreContext().getMessage("form.tipibando.celleditor.si");
	String no = getCoreContext().getMessage("form.tipibando.celleditor.no");
	if (si == null) {
	    si = "???form.tipibando.celleditor.si???";
	}
	if (no == null) {
	    no = "???form.tipibando.celleditor.no???";
	}
	options.add(new Option(si, si));
	options.add(new Option(no, no));
	return options;
    }
}
