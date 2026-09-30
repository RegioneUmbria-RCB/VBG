package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedComDTO;
import it.gruppoinit.pal.gp.core.features.firmadigitale.DocumentiDaFirmareService;

import javax.servlet.http.HttpServletRequest;

import org.jmesa.view.editor.AbstractCellEditor;

public class DocDaFirmareCustomColumn extends AbstractCellEditor {

    private HttpServletRequest request;
    private DocumentiDaFirmareService documentiDaFirmareService;

    public DocDaFirmareCustomColumn(HttpServletRequest request, DocumentiDaFirmareService documentiDaFirmareService) {

	super();
	this.request = request;
	this.documentiDaFirmareService = documentiDaFirmareService;
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	// Recupero il valore da passare come parametro da passare al controllor
	GraduatoriedComDTO oggetto = (GraduatoriedComDTO) item;
	if (oggetto == null) {
	    return "";
	}
	Integer codiceIstanza = null;
	if (oggetto.getGraduatoried() != null) {
	    if (oggetto.getGraduatoried().getIstanza() != null) {
		if (oggetto.getGraduatoried().getIstanza().getId() != null) {
		    if (oggetto.getGraduatoried().getIstanza().getId().getCodice() != null) {
			codiceIstanza = oggetto.getGraduatoried().getIstanza().getId().getCodice();
		    }
		}
	    }
	}
	if (codiceIstanza == null) {
	    return "";
	}
	Integer codiceOggetto = (Integer) UtilityJmesa.getParametro(oggetto, "oggetto");
	if (codiceOggetto == null) {
	    return "";
	}
	if (documentiDaFirmareService.countDocumentiDafirmarePerOggettoIstanza(codiceOggetto, codiceIstanza) > 0) {
	    return "<a href=\"javascript:verificaDocumentiFirmati('" + oggetto.getId().getCodice() + "')\" ><img src=\""
		    + request.getSession().getServletContext().getContextPath() + "/images/error.png\" border=\"0\"/></a><div id=\"docDaFirmarePanel"
		    + oggetto.getId().getCodice() + "\" style=\"display: none\"/>";
	}
	return "<img src=\"" + request.getSession().getServletContext().getContextPath() + "/images/success.png\" border=\"0\"/>";
    }
}
