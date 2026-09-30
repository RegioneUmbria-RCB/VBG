package org.jmesa.custom;

import java.util.ArrayList;
import java.util.List;

import org.jmesa.view.html.editor.DroplistFilterEditor;

public class AttivoDisattSospesoDroplist extends DroplistFilterEditor {

    @Override
    protected List<Option> getOptions() {

	List<Option> options = new ArrayList<Option>();
	String attivo = getCoreContext().getMessage("list.jmesa.celleditor.attivo");
	String disattivo = getCoreContext().getMessage("list.jmesa.celleditor.disattivo");
	String sospeso = getCoreContext().getMessage("list.jmesa.celleditor.sospeso");
	if (attivo == null) {
	    attivo = "???list.jmesa.celleditor.attivo???";
	}
	if (disattivo == null) {
	    disattivo = "???list.jmesa.celleditor.disattivo???";
	}
	if (sospeso == null) {
	    sospeso = "???list.jmesa.celleditor.sospeso???";
	}
	options.add(new Option(attivo, attivo));
	options.add(new Option(disattivo, disattivo));
	options.add(new Option(sospeso, sospeso));
	return options;
    }
}
