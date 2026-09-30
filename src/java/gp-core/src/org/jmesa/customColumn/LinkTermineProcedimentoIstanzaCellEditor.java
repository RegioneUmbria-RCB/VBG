package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.Calendar;
import java.util.Date;

import org.jmesa.view.editor.AbstractCellEditor;

public class LinkTermineProcedimentoIstanzaCellEditor extends AbstractCellEditor {

    private Calendar dataRiferimento = null;

    public LinkTermineProcedimentoIstanzaCellEditor(final Calendar dataRiferimento) {

	this.dataRiferimento = dataRiferimento;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Object oggetto = (Object) item;
	Object termineprocedimento = null;
	termineprocedimento = UtilityJmesa.getParametro(oggetto, "termineprocedimento");
	if (termineprocedimento != null && termineprocedimento instanceof Date) {
	    Date tp = (Date) termineprocedimento;
	    try {
		int compare = Utilities.compareDates(tp, dataRiferimento.getTime());
		String cssClass = "data_nei_termini";
		String testo = "";
		if (compare == 0) {
		    // giallo warning
		    cssClass = "data_in_scadenza";
		    testo = "In scadenza";
		} else if (compare < 0) {
		    // rosso scaduto
		    cssClass = "data_scaduta";
		    testo = "Scaduta";
		}
		return "<div title=\"" + testo + "\" class=\"" + cssClass + "\">" + Utilities.formatDate(tp, false) + "</div>";
	    } catch (Exception e) {
		// do nothing
	    }
	    return tp;
	}
	return "";
    }
}
