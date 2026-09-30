package org.jmesa.custom;

import java.util.ArrayList;
import java.util.List;

import org.jmesa.view.html.editor.DroplistFilterEditor;

public class ScadenzaAvvisoDroplist extends DroplistFilterEditor {

    @Override
    protected List<Option> getOptions() {

	List<Option> options = new ArrayList<Option>();
	String scadenza = getCoreContext().getMessage("list.jmesa.celleditor.scadenza");
	String avviso = getCoreContext().getMessage("list.jmesa.celleditor.avviso");
	String intedizione = getCoreContext().getMessage("list.jmesa.celleditor.interdizione");
	if (scadenza == null) {
	    scadenza = "???list.jmesa.celleditor.scadenza???";
	}
	if (avviso == null) {
	    avviso = "???list.jmesa.celleditor.avviso???";
	}
	if (intedizione == null) {
	    intedizione = "???list.jmesa.celleditor.interdizione???";
	}
	options.add(new Option(scadenza, scadenza));
	options.add(new Option(avviso, avviso));
	options.add(new Option(intedizione, intedizione));
	return options;
    }
}
