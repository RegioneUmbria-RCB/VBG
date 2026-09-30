package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.math.BigDecimal;

import org.jmesa.view.editor.AbstractCellEditor;

public class BigDecimalCellEditor extends AbstractCellEditor {

    private int minFractionDigit = 2;
    private int maxFractionDigit = 2;
    private boolean groupingUsed = false;

    public BigDecimalCellEditor(int minFractionDigit, int maxFractionDigit, boolean groupingUsed) {

	this.minFractionDigit = minFractionDigit;
	this.maxFractionDigit = maxFractionDigit;
	this.groupingUsed = groupingUsed;
    }

    @Override
    public Object getValue(Object item, String property, int count) {

	Object oggetto = (Object) item;
	String valueItem = "<div style=\"text-align:right;\">";
	BigDecimal importo = (BigDecimal) UtilityJmesa.getParametro(oggetto, property);
	valueItem += Utilities.formatImporto(importo, minFractionDigit, maxFractionDigit, groupingUsed);
	return valueItem + "</div>";
    }
}
