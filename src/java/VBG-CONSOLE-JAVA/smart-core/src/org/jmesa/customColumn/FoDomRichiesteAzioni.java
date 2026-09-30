package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.domain.FoDomRichieste;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import org.jmesa.view.editor.AbstractCellEditor;

public class FoDomRichiesteAzioni extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int countRecords) {

	FoDomRichieste fod = (FoDomRichieste) item;
	String output = "";
	output += "<div class=\"dettaglio-link\" data-qsmac=\""
		+ Utilities.getLinkForFile("id=" + fod.getId().getCodice() + "&idComuneRecord=" + fod.getId().getIdcomune() + "&ts_"
			+ System.currentTimeMillis()) + "\" >Apri</div>";
	output += "<div class=\"segna-letto-link\" data-qsmac=\""
		+ Utilities.getLinkForFile("id=" + fod.getId().getCodice() + "&idComuneRecord=" + fod.getId().getIdcomune() + "&ts_"
			+ System.currentTimeMillis()) + "\" >Segna come letto </div>";
	return output;
    }
}
