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
public class SiNoDroplist extends DroplistFilterEditor {

    @Override
    protected List<Option> getOptions() {

	List<Option> options = new ArrayList<Option>();
	String si = getCoreContext().getMessage("list.jmesa.celleditor.si");
	String no = getCoreContext().getMessage("list.jmesa.celleditor.no");
	if (si == null) {
	    si = "???list.jmesa.celleditor.si???";
	}
	if (no == null) {
	    no = "???list.jmesa.celleditor.no???";
	}
	options.add(new Option(si, si));
	options.add(new Option(no, no));
	return options;
    }
}
