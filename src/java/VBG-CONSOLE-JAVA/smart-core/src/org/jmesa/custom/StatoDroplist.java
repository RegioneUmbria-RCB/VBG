package org.jmesa.custom;

import java.util.ArrayList;
import java.util.List;

import org.jmesa.view.html.editor.DroplistFilterEditor;

public class StatoDroplist extends DroplistFilterEditor {

    @Override
    protected List<Option> getOptions() {

	List<Option> options = new ArrayList<Option>();
	String aperta = getCoreContext().getMessage("list.jmesa.celleditor.aperta");
	String chiusa = getCoreContext().getMessage("list.jmesa.celleditor.chiusa");
	if (aperta == null) {
	    aperta = "???list.jmesa.celleditor.aperta???";
	}
	if (chiusa == null) {
	    chiusa = "???list.jmesa.celleditor.chiusa???";
	}
	options.add(new Option(aperta, aperta));
	options.add(new Option(chiusa, chiusa));
	return options;
    }
}
