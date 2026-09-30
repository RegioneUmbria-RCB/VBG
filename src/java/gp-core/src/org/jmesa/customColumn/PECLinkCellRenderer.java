package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.domain.helper.PECMessageHelper;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.renderer.HtmlCellRendererImpl;

public class PECLinkCellRenderer extends HtmlCellRendererImpl {

    @Override
    public Object render(Object item, int rowcount) {

	String propName = getColumn().getProperty();
	Object propVal = getCellEditor().getValue(item, propName, rowcount);
	String titleValue = (String) propVal;
	propVal = abbreviateCampoOggetto(propVal);
	PECMessageHelper pecMsg = (PECMessageHelper) item;
	HtmlBuilder html = new HtmlBuilder();
	html.td(1);
	html.close();
	StringBuilder sb = new StringBuilder("javascript:dettaglioPec('");
	sb.append(pecMsg.getIdentificativo()).append("');");
	//html.a().href(sb.toString()).title("visualizza il dettaglio della PEC").close();
	html.a().href(sb.toString()).title(titleValue).close();
	html.append(propVal).aEnd();
	html.tdEnd();
	return html.toString();
    }

    //Abbrevia il campo oggetto ai primi  100 caratteri
    private Object abbreviateCampoOggetto(Object propVal) {

	String oggetto = (String) propVal;
	String oggettoRestrict = StringUtils.abbreviate(oggetto, 100);
	propVal = oggettoRestrict;
	return propVal;
    }
}
