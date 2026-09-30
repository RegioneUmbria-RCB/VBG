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
public class TipoEntrataUscitaDroplist extends DroplistFilterEditor {

    @Override
    protected List<Option> getOptions() {

	List<Option> options = new ArrayList<Option>();
	String entrata = getCoreContext().getMessage("form.registrazioniInOut.tipo.e");
	String uscita = getCoreContext().getMessage("form.registrazioniInOut.tipo.u");
	if (entrata == null) {
	    entrata = "???form.registrazioniInOut.tipo.e???";
	}
	if (uscita == null) {
	    uscita = "???form.registrazioniInOut.tipo.u???";
	}
	options.add(new Option(entrata, entrata));
	options.add(new Option(uscita, uscita));
	return options;
    }
}
