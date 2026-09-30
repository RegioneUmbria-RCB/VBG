package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.domain.Registrazioni;

import java.math.BigDecimal;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class RegistrazioniAzioniCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int count) {

	Registrazioni reg = (Registrazioni) item;
	String valueItem = "";
	valueItem = "<a class=\"dettaglioColumn\" href=\"javascript:dettaglioRegistrazione('" + reg.getId().getCodice() + "');\">";
	valueItem += "<label>" + getLabel("label.edit.record.image") + "</label></a>";
	if (reg.getVwRegistrazionisaldo() != null) {
	    if (reg.getVwRegistrazionisaldo().getSaldo() != null && reg.getVwRegistrazionisaldo().getSaldo().compareTo(BigDecimal.ZERO) > 0) {
		if (reg.getAnagrafe() != null) {
		    if (reg.getAnagrafe().getId() != null) {
			valueItem += "<a class=\"assegnaColumn\" href=\"javascript:vaiAScadenze('" + reg.getProgressivo() + "','"
				+ reg.getAnagrafe().getId().getCodice() + "');\">";
			valueItem += "<label>" + getLabel("label.assegna.image") + "</label></a>";
		    }
		}
	    }
	}
	return valueItem;
    }

    // recupera la label dal CoreContext
    private String getLabel(String label) {

	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String message = messages.getMessage(label);
	if (message == null) {
	    message = "???" + label + "???";
	}
	return message;
    }
}
