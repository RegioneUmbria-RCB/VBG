/**
 * 
 */
package org.jmesa.custom;

import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractFilterEditor;

/**
 * @author francescop
 * 
 */
public class DataRegRegistrazioniCustomFilter extends AbstractFilterEditor {

    @Override
    public Object getValue() {

	return UtilityJmesa.getCustomFilterDate("registrazioni_id", "dataRegistrazione");
    }
}
