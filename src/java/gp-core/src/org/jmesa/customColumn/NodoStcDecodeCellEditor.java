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
	String[] idNodoAndIdMittenteAndIdSportello = null;
	if (StringUtils.defaultString(value).indexOf("|") >= 0) {
	    idNodoAndIdMittenteAndIdSportello = value.split("\\|");
	}
	String valueItem = value;
	if (idNodoAndIdMittenteAndIdSportello != null && idNodoAndIdMittenteAndIdSportello.length == 3) {
	    String idNodo = StringUtils.defaultString(idNodoAndIdMittenteAndIdSportello[0]);
	    String idEnte = StringUtils.defaultString(idNodoAndIdMittenteAndIdSportello[1]);
	    String idSportello = StringUtils.defaultString(idNodoAndIdMittenteAndIdSportello[2]);
	    valueItem = verticalizzazioniparametriService.decodeNomeNodoModuloSTC(idNodo, idEnte, idSportello);
	}
	return valueItem;
    }
}
