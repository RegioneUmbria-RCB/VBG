package org.jmesa.customColumn;

import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.jmesa.view.editor.AbstractCellEditor;

public class LinkStatoIstanzaCellEditor extends AbstractCellEditor {

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Object oggetto = (Object) item;
	Object codiceistanza = null;
	codiceistanza = UtilityJmesa.getParametro(oggetto, "codiceistanza");
	String coloreStato = (String) UtilityJmesa.getParametro(oggetto, "colorestatoistanza");
	String statoistanza = (String) UtilityJmesa.getParametro(oggetto, "statoistanza");
	String goTo = URLEncoder.encode("../istanze/infoView.htm?codice=" + codiceistanza + "&software="
		+ UtilityJmesa.getParametro(oggetto, "software"));
	String pre = "", post = "";
	if (StringUtils.isNotBlank(coloreStato)) {
	    pre = pre.concat("<div style=\"width:100%; background-color:" + coloreStato + ";padding-top: 5px;padding-bottom: 5px;\">");
	    post = post.concat("</div>");
	}
	String valueItem = pre
		.concat("<a style=\"cursor: pointer;\" href=\"javascript:doHref('../history/set.htm?ReturnTo=../istanze/listIstanze.htm&GoTo=")
		.concat(goTo).concat("')\" title=\" ").concat(statoistanza).concat("\">").concat(statoistanza).concat("</a>").concat(post);
	return valueItem;
    }
}
