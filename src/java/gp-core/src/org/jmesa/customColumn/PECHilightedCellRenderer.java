/**
 * 
 */
package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.domain.helper.PECMessageHelper;
import it.gruppoinit.pal.gp.core.utils.CustomHtmlBuilder;

import org.jmesa.view.editor.AbstractCellEditor;

/**
 * @author francol Cell renderer di Jmesa per rappresentare la colonna ?In Evidenza'
 */
public class PECHilightedCellRenderer extends AbstractCellEditor {

    /* (non-Javadoc)
     * @see org.jmesa.view.editor.CellEditor#getValue(java.lang.Object, java.lang.String, int)
     */
    @Override
    public Object getValue(Object item, String property, int rowcount) {

	PECMessageHelper pec = (PECMessageHelper) item;
	CustomHtmlBuilder html = new CustomHtmlBuilder();
	html.div().align("center").close();
	String imgName = "favorites-icon-disabled.gif";
	String title = "Metti in evidenza";
	if (pec.getFkRespInEvidenza() != null) {
	    imgName = "favorites-icon.png";
	    title = "Togli in evidenza ";
	}
	StringBuilder sbJs = new StringBuilder("mettiInEvidenza('").append(pec.getIdentificativo()).append("',");
	sbJs.append(pec.getFkRespInEvidenza() == null).append(");");
	html.a().href("javascript:void(0)").onclick(sbJs.toString()).title(title).close();
	html.img().id("imgEvidenza_" + pec.getIdentificativo()).src("../images/" + imgName).close();
	html.aEnd();
	// ID_RESPEVIDENZA
	html.span().style("display: none;").id("fkIdRespEvidenza_" + pec.getIdentificativo()).close();
	if (pec.getFkRespInEvidenza() != null) {
	    html.append(pec.getFkRespInEvidenza().toString());
	}
	html.spanEnd();
	//  html.input().type("hidden").id("fkIdRespEvidenza_" + pec.getIdentificativo());
	//  html.value(pec.getFkRespInEvidenza() != null ? pec.getFkRespInEvidenza().toString() : "");
	//  html.name("fkIdRespEvidenza_" + pec.getIdentificativo()).close();
	// RESPEVIDENZA
	html.span().style("display: none;").id("fkRespEvidenza_" + pec.getIdentificativo()).close();
	if (pec.getRespInEvidenza() != null) {
	    html.append(pec.getRespInEvidenza().toString());
	}
	html.spanEnd();
	//	html.input().type("hidden").id("fkRespEvidenza_" + pec.getIdentificativo());
	//	html.value(pec.getRespInEvidenza() != null ? pec.getRespInEvidenza().toString() : "");
	//	html.name("fkRespEvidenza_" + pec.getIdentificativo()).close();
	
	html.img().id("imgEvidenzaWait_" + pec.getIdentificativo()).src("../images/spinner.gif").style("display:none;").close();
	html.divEnd();
	return html.toString();
    }
}
