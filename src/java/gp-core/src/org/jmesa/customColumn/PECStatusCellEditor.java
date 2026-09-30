/**
 * 
 */
package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.domain.helper.PECMessageHelper;

import org.jmesa.view.editor.AbstractCellEditor;


/**
 * @author francol
 *
 */
public class PECStatusCellEditor extends AbstractCellEditor {

    /* (non-Javadoc)
     * @see org.jmesa.view.editor.CellEditor#getValue(java.lang.Object, java.lang.String, int)
     */
    @Override
    public Object getValue(Object item, String property, int rowCount) {

	PECMessageHelper pec = (PECMessageHelper)item;
	return pec.getStatus();
    }
}
