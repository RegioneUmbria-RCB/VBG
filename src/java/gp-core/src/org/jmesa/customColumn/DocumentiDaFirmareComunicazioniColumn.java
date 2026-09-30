package org.jmesa.customColumn;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.view.editor.AbstractCellEditor;

import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;

public class DocumentiDaFirmareComunicazioniColumn extends AbstractCellEditor {

    private HttpServletRequest request;
    private DocumentiDaFirmareService documentiDaFirmareService;
    private String pathIstanza;

    public DocumentiDaFirmareComunicazioniColumn(HttpServletRequest request, String pathIstanza, DocumentiDaFirmareService documentiDaFirmareService) {

	super();
	this.request = request;
	this.documentiDaFirmareService = documentiDaFirmareService;
	this.pathIstanza = pathIstanza;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	Object oggetto = (Object) item;
	if (oggetto == null) {
	    return "";
	}
	Integer codiceistanza = (Integer) UtilityJmesa.getParametro(oggetto, pathIstanza + ".id.codice");
	if (codiceistanza == null) {
	    return "";
	}
	Integer codiceOggetto = (Integer) UtilityJmesa.getParametro(oggetto, "oggetto");
	if (codiceOggetto == null) {
	    return "";
	}
	Integer codiceComunicazioneD = (Integer) UtilityJmesa.getParametro(oggetto, "idcomunicazioned");
	if (codiceComunicazioneD == null) {
	    return "";
	}
	if (documentiDaFirmareService.countDocumentiDafirmarePerOggettoIstanza(codiceOggetto, codiceistanza) > 0) {
	    return "<a href=\"javascript:verificaDocumentiFirmatiInComunicazioni('" + codiceComunicazioneD + "')\" ><img src=\""
		    + request.getSession().getServletContext().getContextPath() + "/images/error.png\" border=\"0\"/></a><div id=\"docDaFirmarePanel"
		    + codiceComunicazioneD + "\" style=\"display: none\"/>";
	}
	return "<img src=\"" + request.getSession().getServletContext().getContextPath() + "/images/success.png\" border=\"0\"/>";
    }
}
