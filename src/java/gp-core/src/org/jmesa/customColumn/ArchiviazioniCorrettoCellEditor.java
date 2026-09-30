package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.domain.Archiviazioni;

import org.apache.commons.lang.BooleanUtils;
import org.jmesa.view.editor.AbstractCellEditor;

public class ArchiviazioniCorrettoCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowCount) {

	Boolean corretto = ((Archiviazioni) item).getCorretto();
	if (BooleanUtils.isTrue(corretto)) {
	    return "<div class='accepted'></div>";
	}
	return "<div class='to-accept' data-id='" + ((Archiviazioni) item).getId().getCodice() + "'></div>";
    }
}
