package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.domain.helper.PECMessageHelper;

import org.jmesa.view.editor.AbstractCellEditor;

public class PECComputedCellRenderer extends AbstractCellEditor {

    @Override
    public Object getValue(Object arg0, String arg1, int arg2) {

	PECMessageHelper row = (PECMessageHelper) arg0;
	String title = row.getProcessato() ? "il messaggio è stato elaborato" : "il messaggio non è stato elaborato";
	StringBuilder sb = new StringBuilder("<div style=\"width: 100%;\" title=\"").append(title).append("\" align=\"center\">");
	if (row.getProcessato()) {
	    sb.append("<img src='../images/lettera_E.png'/>");
	} else {
	    //sb.append("email_unread.png");
	}
	sb.append("</div>");
	return sb.toString();
    }
}
