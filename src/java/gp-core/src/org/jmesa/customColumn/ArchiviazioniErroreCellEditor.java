package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.domain.Archiviazioni;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.view.editor.AbstractCellEditor;

public class ArchiviazioniErroreCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowCount) {

	String errore = ((Archiviazioni) item).getErrore();
	errore = StringEscapeUtils.escapeHtml(StringUtils.defaultIfEmpty(errore, ""));
	if (StringUtils.isNotEmpty(errore)) {
	    return "<div class='toggle'></div><div style='display:none;'>" + errore + "</div>";
	}
	return "";
    }
}
