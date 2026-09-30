package org.jmesa.custom;

import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractFilterEditor;

/**
 * 
 * @author francescop
 * 
 *         Filtro Custom per la proprietà datascadenza di Bandi
 */
public class DatascadenzaCustomFilter extends AbstractFilterEditor {

    @Override
    public Object getValue() {

	return UtilityJmesa.getCustomFilterDate("bandi_id", "datascadenza");
    }
}
