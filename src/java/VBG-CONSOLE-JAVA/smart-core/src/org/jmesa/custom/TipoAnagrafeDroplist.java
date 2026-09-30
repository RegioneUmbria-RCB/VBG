package org.jmesa.custom;

import java.util.ArrayList;
import java.util.List;

import org.jmesa.view.html.editor.DroplistFilterEditor;

/**
 * 
 * @author gianpaolot Classe che permette di prensetare una drop list in un campo di ricerca di jmesa per selezionare un
 *         tipo anagrafe (Persona giuridica, Persona fisica)
 */
public class TipoAnagrafeDroplist extends DroplistFilterEditor {

    @Override
    protected List<Option> getOptions() {

	List<Option> options = new ArrayList<Option>();
	String personaFisica = getCoreContext().getMessage("list.jmesa.celleditor.persona_fisica");
	String personaGiuridica = getCoreContext().getMessage("list.jmesa.celleditor.persona_giuridica");
	if (personaFisica == null) {
	    personaFisica = "???list.jmesa.celleditor.persona_fisica???";
	}
	if (personaGiuridica == null) {
	    personaGiuridica = "???list.jmesa.celleditor.persona_giuridica???";
	}
	options.add(new Option(personaFisica, personaFisica));
	options.add(new Option(personaGiuridica, personaGiuridica));
	return options;
    }
}
