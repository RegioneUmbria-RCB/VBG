package it.gruppoinit.pal.gp.core.jmesa;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class LinkCreaCollegamentoMultiploIstanzeAttiDCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	Object oggetto = (Object) item;
	Object parametro = null;
	parametro = UtilityJmesa.getParametro(oggetto, "codiceistanza");
	String valueItem = "";
	valueItem = "<input type=\"checkbox\" value=\"" + parametro + "\" id=\"codice_istanza_id" + parametro + ""
		+ "\" class=\"istanze_da_collegare_acesso_atti_cls\" onclick=\"addToIstanzaDacollegareAccessoAtti();\"/>";
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
