package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.view.editor.AbstractCellEditor;

public class RegistrazioniImportoCellEditor extends AbstractCellEditor {

    private HttpServletRequest request;
    private String propertyName;
    private boolean adeguamento;
    private BigDecimal percentuale;

    public RegistrazioniImportoCellEditor(HttpServletRequest request, String propertyName, boolean adeguamento, BigDecimal percentuale) {

	this.request = request;
	this.propertyName = propertyName;
	this.adeguamento = adeguamento;
	this.percentuale = percentuale;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Object oggetto = (Object) item;
	String valueItem = "<div style=\"text-align:right;\">";
	BigDecimal importo = (BigDecimal) UtilityJmesa.getParametro(oggetto, propertyName);
	if (adeguamento) {
	    if (importo != null) {
		BigDecimal importoAdeguato = (importo.multiply(percentuale)).divide(BigDecimal.valueOf(100));
		importoAdeguato = importoAdeguato.add(importo);
		String importoAdeguatoStr = formatImporto(importoAdeguato);
		String importoStr = formatImporto(importo);
		valueItem += "<b style=\"color: red\">" + importoAdeguatoStr + "</b>&nbsp;(" + importoStr + ")";
	    }
	} else {
	    if (importo != null) {
		valueItem += formatImporto(importo);
	    }
	}
	return valueItem + "</div>";
    }

    private String formatImporto(BigDecimal importo) {

	return Utilities.formatImporto(importo, 2, 2, false);
    }
}
