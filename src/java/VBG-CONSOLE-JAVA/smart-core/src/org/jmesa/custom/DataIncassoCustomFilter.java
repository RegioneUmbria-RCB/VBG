/**
 * 
 */
package org.jmesa.custom;

import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractFilterEditor;

/**
 * @author lucap
 * 
 */
public class DataIncassoCustomFilter extends AbstractFilterEditor {

    @Override
    public Object getValue() {

	return UtilityJmesa.getCustomFilterDate("registrazioniInOut_id", "dataDistinta");
    }
}
