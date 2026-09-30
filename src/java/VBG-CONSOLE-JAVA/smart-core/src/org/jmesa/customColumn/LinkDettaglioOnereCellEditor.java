package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.helper.OneriPerCausaleHelper;

import java.util.Map;

import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class LinkDettaglioOnereCellEditor extends AbstractCellEditor {

    private Inventarioprocedimenti procediemnto;

    public LinkDettaglioOnereCellEditor(Inventarioprocedimenti procedimento) {

	super();
	this.procediemnto = procedimento;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object getValue(Object item, String property, int rowcount) {

	/*
	Map itemasmap = (Map) item;
	OneriPerCausaleHelper oggetto = (OneriPerCausaleHelper) itemasmap.get("jmesa-item");
	*/
	OneriPerCausaleHelper oggetto = (OneriPerCausaleHelper) item;
	String uriBack = oggetto.getUrlListaOneri();
	String uriTo = oggetto.getUrlOnereComuneBase();
	try {
	    //uriTo = URLEncoder.encode(uriTo, "UTF-8");
	    //uriBack = URLEncoder.encode(uriBack, "UTF-8");
	} catch (Exception e) {
	}
	String text = UtilityJmesa.getLabel("label.edit.record", (SpringWebContext) getWebContext());
	String valueItem = "<a class=\"dettaglioColumn\" style=\"cursor: pointer;\" href=\"javascript:historySet(URLEncode('" + uriBack + "'),'"
		+ uriTo + "','');\" title=\"" + text + "\"><label>Det</label></a>";
	return valueItem;
    }
}
