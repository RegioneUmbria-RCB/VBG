package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;

import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.view.editor.AbstractCellEditor;

public class LinkAltriIndirizziCellEditor extends AbstractCellEditor {

    public LinkAltriIndirizziCellEditor() {

	super();
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Istanze istanza = (Istanze) item;
	Set<Istanzestradario> stradari = istanza.getIstanzestradarios();
	String valueItem = null;
	if (stradari.size() > 0) {
	    String linkText = getCoreContext().getMessage("label.I");
	    String titleText = "";
	    for (Istanzestradario istrads : stradari) {
		if (BooleanUtils.isFalse(istrads.getPrimario())) {
		    titleText += istrads.getStradario().getDescrizioneCompleta();
		    if (StringUtils.isNotBlank(istrads.getCivico())) {
			titleText += " " + istrads.getCivico();
		    }
		    if (istrads.getStradariocolore() != null) {
			if (StringUtils.isNotBlank(istrads.getStradariocolore().getColore())) {
			    titleText += " " + istrads.getStradariocolore().getColore();
			}
		    }
		    titleText += "\n";
		}
	    }
	    if (StringUtils.isNotBlank(titleText)) {
		valueItem = "<a style=\"cursor:pointer;\" title=\"" + titleText + "\">" + linkText + "</a>";
	    }
	}
	return valueItem;
    }
}
