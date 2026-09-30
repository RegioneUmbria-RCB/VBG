package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;

import org.jmesa.view.editor.AbstractCellEditor;

public class CheckBoxAjaxUpdateInventarioprocsoftwareMov extends AbstractCellEditor {

    public CheckBoxAjaxUpdateInventarioprocsoftwareMov() {

	super();
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Inventarioprocedimentisoftware oggetto = (Inventarioprocedimentisoftware) item;
	String idEl = "ips_" + oggetto.getId().getIdcomune() + "_" + String.valueOf(oggetto.getInventarioprocedimento().getId().getCodice());
	String valueItem2 = "<span id=\"spinner-" + idEl
		+ "\" style=\"display: none;\" ><img alt=\"richiesta in corso\" src=\"../images/spinner.gif\" />richiesta in corso</span>";
	if (oggetto.getInventarioprocedimento().getId().getCodice() != null) {
	    valueItem2 += "<div id=\"" + idEl + "\" ></div><script> jQuery(document).ready(function(){viewContent('" + idEl + "',"
		    + String.valueOf(oggetto.getInventarioprocedimento().getId().getCodice()) + ")});</script>";
	} else {
	    valueItem2 += "<div id=\"" + idEl + "\" />";
	}
	return valueItem2;
    }
}
