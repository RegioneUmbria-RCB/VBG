package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;

import org.apache.commons.lang.StringUtils;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;

public class NodoStcDecodeCellEditor extends AbstractCellEditor {

    private VerticalizzazioniparametriService verticalizzazioniparametriService;

    public NodoStcDecodeCellEditor(VerticalizzazioniparametriService verticalizzazioniparametriService) {

	this.verticalizzazioniparametriService = verticalizzazioniparametriService;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	String value = (String) ItemUtils.getItemValue(item, property);
	String[] idNodoAndIdMittente = new String[2];
	if (StringUtils.isNotBlank(value)) {
	    idNodoAndIdMittente = value.split("#");
	}
	String idNodo = StringUtils.isNotBlank(idNodoAndIdMittente[0]) ? idNodoAndIdMittente[0] : null;
	String idMittente = StringUtils.isNotBlank(idNodoAndIdMittente[1]) ? idNodoAndIdMittente[1] : null;
	String valueItem = null;
	valueItem = verticalizzazioniparametriService.decodeNomeNodoModuloSTC(idNodo, idMittente);
	return valueItem;
    }
}
