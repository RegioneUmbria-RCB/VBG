package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.jmesa.IJMesaLinkHelper;

import org.apache.commons.lang.StringUtils;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;

/**
 * 
 * @author fabrizioc
 */
public class LinkCellEditor extends AbstractCellEditor {

    private IJMesaLinkHelper linkHelper;

    public LinkCellEditor(IJMesaLinkHelper linkHelper) {

	this.linkHelper = linkHelper;
    }

    /**
     * 
     * @see LinkCellEditor#doStringReplacement(Object, String, String[])
     */
    @Override
    public Object getValue(Object item, String property, int rowcount) {

	Object o = ItemUtils.getItemValue(item, property);
	String itemPropertyValue = "";
	if (o instanceof String) {
	    itemPropertyValue = (String) o;
	} else {
	    if (null != o) {
		itemPropertyValue = String.valueOf(o);
	    }
	}
	String value = "";
	if (StringUtils.isNotBlank(itemPropertyValue)) {
	    String _hrefValue = doStringReplacement(item);
	    value = "<a href=\"" + _hrefValue + "\">" + itemPropertyValue + "</a>";
	}
	return value;
    }

    /**
     * metodo per eseguire la sostituzione di tutti i segnaposto ("placeHolders") con il valore estratto dall'oggetto
     * item utilizzando il segnaposto come path.<br />
     * 
     * es.<br />
     * data la stringa: javascript:historySet(
     * '/welcome/start.htm','../istanze/view.htm?codice=<id.codice>&software=<software.id.codice>');<br />
     * dati i segnaposto: [<id.codice>, <software.id.codice>]<br />
     * recupero dall'item (istanza di Istanze.class) il valore delle proprietà "id.codice" e "software.id.codice"<br />
     * sostituisco i valori recuperati nella stringa di partenza<br />
     * 
     * @param item
     * 
     * @return
     */
    private String doStringReplacement(Object item) {

	String link = linkHelper.getLink();
	String[] placeHolders = linkHelper.getPlaceHolders();
	if (placeHolders != null && placeHolders.length > 0) {
	    Object itemValue = null;
	    String property = "";
	    for (int j = 0; j < placeHolders.length; j++) {
		property = placeHolders[j].substring(1, placeHolders[j].length() - 1);
		itemValue = ItemUtils.getItemValue(item, property);
		if (itemValue != null) {
		    link = link.replaceAll(placeHolders[j], itemValue.toString());
		} else {
		    link = link.replaceAll(placeHolders[j], "");
		}
	    }
	}
	return link;
    }
}
