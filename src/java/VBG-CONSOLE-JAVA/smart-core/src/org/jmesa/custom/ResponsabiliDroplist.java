/**
 * 
 */
package org.jmesa.custom;

import java.util.ArrayList;
import java.util.List;

import org.jmesa.view.html.editor.DroplistFilterEditor;

/**
 * @author francescop
 * 
 */
public class ResponsabiliDroplist extends DroplistFilterEditor {

    @Override
    protected List<Option> getOptions() {

	List<Option> options = new ArrayList<Option>();
	String s = getCoreContext().getMessage("responsabili.label.amministratori_value.table");
	if (s == null) {
	    s = "???responsabili.label.amministratori_value.table???";
	}
	options.add(new Option(s, s));
	return options;
    }
}
