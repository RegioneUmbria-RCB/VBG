package org.jmesa.custom;

import java.util.ArrayList;
import java.util.List;

import org.jmesa.view.html.editor.DroplistFilterEditor;

/**
 * 
 * @author lucap
 * 
 */
public class TrueFalseDroplist extends DroplistFilterEditor {

    @Override
    protected List<Option> getOptions() {

	List<Option> options = new ArrayList<Option>();
	String uno = getCoreContext().getMessage("list.jmesa.celleditor.true");
	String zero = getCoreContext().getMessage("list.jmesa.celleditor.false");
	if (uno == null) {
	    uno = "???list.jmesa.celleditor.true???";
	}
	if (zero == null) {
	    zero = "???list.jmesa.celleditor.false???";
	}
	options.add(new Option(uno, "Si"));
	options.add(new Option(zero, "No"));
	return options;
    }
}
