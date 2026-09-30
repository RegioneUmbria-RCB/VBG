package org.jmesa.customColumn;

import it.gruppoinit.pal.gp.core.jmesa.IJMesaLinkHelper;

import org.jmesa.core.message.SpringMessages;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.AbstractCellEditor;
import org.jmesa.web.SpringWebContext;

public class LinkCellWithIconEditor extends AbstractCellEditor {

    private IJMesaLinkHelper linkHelper;
    private String classIcon;
    private String title;

    public LinkCellWithIconEditor(IJMesaLinkHelper linkHelper, String classIcon, String title) {

	this.linkHelper = linkHelper;
	this.classIcon = classIcon;
	this.title = title;
    }

    /**
     * 
     * @see LinkCellEditor#doStringReplacement(Object, String, String[])
     */
    @Override
    public Object getValue(Object item, String property, int rowcount) {

	//String itemPropertyValue = (String) ItemUtils.getItemValue(item, property);
	SpringMessages messages = new SpringMessages(null, (SpringWebContext) getWebContext());
	String decodificakey = messages.getMessage(title);
	if (decodificakey == null) {
	    decodificakey = "???" + title + "???";
	}
	String _hrefValue = doStringReplacement(item);
	String value = "";
	value = "<a class=\"" + classIcon + "\" href=\"" + _hrefValue + "\" title=\"" + decodificakey + "\" ><label>" + decodificakey
		+ "</label></a>";
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
