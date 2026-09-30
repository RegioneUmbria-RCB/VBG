package org.jmesa.custom;

import org.jmesa.customColumn.UtilityJmesa;
import org.jmesa.view.editor.AbstractFilterEditor;

/**
 * 
 * @author francescop
 * 
 *         Filtro Custom per la proprietà datapubblicazione di Bandi
 */
public class DatapubblicazioneCustomFilter extends AbstractFilterEditor {

    @Override
    public Object getValue() {

	return UtilityJmesa.getCustomFilterDate("bandi_id", "datapubblicazione");
    }
}
