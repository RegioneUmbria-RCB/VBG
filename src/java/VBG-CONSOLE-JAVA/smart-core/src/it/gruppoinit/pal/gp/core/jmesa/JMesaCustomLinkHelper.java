package it.gruppoinit.pal.gp.core.jmesa;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class JMesaCustomLinkHelper implements IJMesaLinkHelper {

    private String customLink;
    private String link;
    private String[] placeHolders;
    private String messageToConfirmLink;

    /**
     * crea un oggetto da fornire al LinkCellEditor per la creazione dell'url al dettaglio istanza
     * 
     * @param property
     *            nome della property dell'oggetto Istanze contenuta nell'oggetto corrente.( se l'oggetto corrente è
     *            Istanza allora lasciare a null)
     * @param uriBack
     *            uri della funzionalità a cui tornare. (se passato costruisce l'url tramite la historySet, altrimenti
     *            costruisce un normale link)
     */
    public JMesaCustomLinkHelper(String customLink, String uriBack) {

	this.customLink = customLink;
	link = createLink(uriBack);
	placeHolders = getLinkPlaceHolders();
    }

    public JMesaCustomLinkHelper(String customLink, String uriBack, String messageToConfirmLink) {

	this.customLink = customLink;
	this.messageToConfirmLink = messageToConfirmLink;
	link = createLink(uriBack);
	placeHolders = getLinkPlaceHolders();
    }

    /**
     * metodo per la creazione del link al dettaglio istanza
     * 
     * @param property
     *            nome della property dell'oggetto Istanze contenuta nell'oggetto corrente.( se l'oggetto corrente è
     *            Istanze stesso lasciare a null o stringa vuota)
     * 
     * @param uriBack
     *            uri della funzionalità a cui tornare. (se passato l'url utilizza la historySet, altrimenti l'url è un
     *            normale link)
     * 
     * @return
     */
    private String createLink(String uriBack) {

	if (StringUtils.isNotBlank(uriBack)) {
	    this.link = "javascript:historySet('" + uriBack + "','" + customLink + "','" + StringUtils.defaultIfEmpty(this.messageToConfirmLink, "")
		    + "');";
	}
	return this.link;
    }

    /**
     * recupera la lista dei segnaposto presenti nella property 'link'
     * 
     * @return
     */
    private String[] getLinkPlaceHolders() {

	List<String> matches = new ArrayList<String>();
	String regex = "<((\\w+)(\\.)?)+>";
	Pattern p = Pattern.compile(regex);
	Matcher m = p.matcher(link);
	while (m.find()) {
	    matches.add(m.group());
	}
	return matches.toArray(new String[matches.size()]);
    }

    public String getLink() {

	return link;
    }

    public String[] getPlaceHolders() {

	return placeHolders;
    }
}
