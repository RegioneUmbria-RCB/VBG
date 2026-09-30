package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.domain.helper.PECMessageHelper;

import org.jmesa.view.editor.AbstractCellEditor;


public class PECReadUnreadCellRenderer extends AbstractCellEditor {

    @Override
    public Object getValue(Object arg0, String arg1, int arg2) {

	PECMessageHelper row = (PECMessageHelper)arg0;
	String title = row.getLetto() ? "imposta il messaggio come non letto" : "imposta il messaggio come letto";
	StringBuilder sb = new StringBuilder("<div align=\"center\"><a href=\"javascript: switchFlagLetta('").append(row.getIdentificativo());
	sb.append("');\"><img title=\"").append(title);
	sb.append("\" id=\"imgLetta_").append(row.getIdentificativo()).append("\" src='../images/");
	if(row.getLetto()){
	    sb.append("email_letta.gif");
	}
	else{
	    sb.append("email_unread.png");
	}
	sb.append("'/></a><img id=\"imgWait_").append(row.getIdentificativo()).append("\" src=\"../images/spinner.gif\" style=\"display:none;\"/>");
	sb.append("</div>");
	return sb.toString();
    }
}
