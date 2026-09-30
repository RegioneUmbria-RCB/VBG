package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;
import org.springframework.web.context.ContextLoader;

public class LinkIstanzeprocedimentiCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	IstanzeprocedimentiService istanzeprocedimentiService = (IstanzeprocedimentiService) ContextLoader.getCurrentWebApplicationContext().getBean(
		"istanzeprocedimentiServiceImpl", IstanzeprocedimentiService.class);
	Object oggetto = (Object) item;
	Object codiceistanza = null;
	codiceistanza = UtilityJmesa.getParametro(oggetto, "id.codice");
	int valoreDicontrollo = istanzeprocedimentiService.countByIstanza((Integer) codiceistanza);
	String p = getLabel("label.P");
	String valueItem = "<a style=\"cursor: pointer;\" href=\"javascript:doHref('../history/set.htm?ReturnTo=../istanze/listIstanze.htm&GoTo=../istanzeprocedimenti/riepilogo.htm?codiceIstanza="
		+ codiceistanza + "')\" title=\" " + p + "\">" + p + "</a>";
	if (valoreDicontrollo > 0) {
	    return valueItem;
	}
	return "";
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
