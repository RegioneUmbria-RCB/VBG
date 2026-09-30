/**
 * 
 */
package org.jmesa.custom;

import java.util.ArrayList;
import java.util.List;

import org.jmesa.view.html.editor.DroplistFilterEditor;

/**
 * Custom Droplist per specificare una label e un valore custom. Tramite il FilterMatcher faccio il match tra valore
 * della droplist con il valore effettivo della proprietà del domain. Tale classe viene richiamata nella jsp
 * d'interesse.
 * 
 * @author Francesco Palenga
 */
public class PariDispariDroplist extends DroplistFilterEditor {

    @Override
    protected List<Option> getOptions() {

	List<Option> options = new ArrayList<Option>();
	String pari = getCoreContext().getMessage("form.areedettagli.droplist.pari");
	String dispari = getCoreContext().getMessage("form.areedettagli.droplist.dispari");
	String tutti = getCoreContext().getMessage("form.areedettagli.droplist.tutti");
	if (pari == null) {
	    pari = "???form.areedettagli.droplist.pari???";
	}
	if (dispari == null) {
	    dispari = "???form.areedettagli.droplist.dispari???";
	}
	if (tutti == null) {
	    tutti = "???form.areedettagli.droplist.tutti???";
	}
	options.add(new Option(tutti, tutti));
	options.add(new Option(pari, pari));
	options.add(new Option(dispari, dispari));
	return options;
    }
}
