package org.jmesa.custom;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Sorteggidettaglio;

import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jmesa.view.editor.AbstractCellEditor;

public class LinkSorteggiCellEditor extends AbstractCellEditor {

    public LinkSorteggiCellEditor() {

	super();
    }

    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Istanze istanza = (Istanze) item;
	Set<Sorteggidettaglio> sorteggidettaglios = istanza.getSorteggidettaglios();
	String valueItem = null;
	if (sorteggidettaglios.size() > 0) {
	    boolean almenoUno = false;
	    String titleText = "";
	    String labelSorteggio = "";
	    String linkText = "";
	    for (Sorteggidettaglio sort : sorteggidettaglios) {
		if (BooleanUtils.isTrue(sort.getSorteggiata())) {
		    if (almenoUno == false) {
			linkText = getCoreContext().getMessage("label.S");
			labelSorteggio = getCoreContext().getMessage("label.sorteggio");
			almenoUno = true;
		    }
		    titleText += labelSorteggio + ": " + sort.getSorteggitestata().getStDescrizione() + "\n";
		}
	    }
	    if (StringUtils.isNotBlank(titleText)) {
		valueItem = "<a style=\"cursor:pointer;\" title=\"" + titleText + "\">" + linkText + "</a>";
	    }
	}
	return valueItem;
    }
}
