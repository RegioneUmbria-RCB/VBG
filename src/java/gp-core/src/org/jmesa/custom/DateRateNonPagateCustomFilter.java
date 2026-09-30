/**
 * 
 */
package org.jmesa.custom;

import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractFilterEditor;

/**
 * @author francescop
 * 
 *         Filtro Custom per la proprietà dataregistrazione rate non pagate
 */
public class DateRateNonPagateCustomFilter extends AbstractFilterEditor {

    @Override
    public Object getValue() {

	return UtilityJmesa.getCustomFilterDate(getCoreContext().getLimit().getId(), "dataRegistrazione");
    }
}