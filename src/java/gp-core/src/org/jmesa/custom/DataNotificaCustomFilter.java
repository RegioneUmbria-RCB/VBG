package org.jmesa.custom;

import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractFilterEditor;

/**
 * 
 * @author francescop
 * 
 *         Filtro Custom per la proprietà dataPerv di Notifiche Ausl
 */
public class DataNotificaCustomFilter extends AbstractFilterEditor {

    @Override
    public Object getValue() {

	return UtilityJmesa.getCustomFilterDate("notiche_id", "dataPerv");
    }
}
