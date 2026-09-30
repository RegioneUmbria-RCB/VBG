package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;

import org.apache.commons.lang.BooleanUtils;
import org.jmesa.view.editor.AbstractCellEditor;

public class CheckBoxAjaxInsertInventarioprocedimentisoftware extends AbstractCellEditor {

    public CheckBoxAjaxInsertInventarioprocedimentisoftware() {

	super();
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Inventarioprocedimentisoftware oggetto = (Inventarioprocedimentisoftware) item;
	String checked = "";
	String idEl = "ips_" + oggetto.getId().getIdcomune() + "_" + String.valueOf(oggetto.getInventarioprocedimento().getId().getCodice());
	if (BooleanUtils.isTrue(oggetto.getAttivo())) {
	    checked = "checked=\"checked\"";
	}
	String valueItem = "";
	String idInput = "inventarioprocSw_attivoId" + oggetto.getInventarioprocedimento().getId().getCodice();
	String secondaFunzione = "";
	secondaFunzione = "setTimeout('viewContent(\\'" + idEl + "\\'," + String.valueOf(oggetto.getInventarioprocedimento().getId().getCodice())
		+ ")',2000)";
	valueItem = "<input id=\"" + idInput + "\" type=\"checkbox\" " + checked + " onclick=\"changeCheckboxValue('" + idInput
		+ "','../inventarioprocedimenti/ajaxInsertInventarioprocedimentisoftware.htm?codice="
		+ oggetto.getInventarioprocedimento().getId().getCodice() + "');" + secondaFunzione + "\" />";
	return valueItem;
    }
}
