package it.gruppoinit.pal.gp.core.jmesa;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class JMesaTableLinkHelper implements IJMesaLinkHelper{

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
    public JMesaTableLinkHelper(String property, String uriBack, LinkTargetEnum linkTarget) {

	link = createLink(property, uriBack, linkTarget);
	placeHolders = getLinkPlaceHolders();
    }

    public JMesaTableLinkHelper(String property, String uriBack, LinkTargetEnum linkTarget, String messageToConfirmLink) {

	this.messageToConfirmLink = messageToConfirmLink;
	link = createLink(property, uriBack, linkTarget);
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
     * @param linkTarget
     *            nome della funzionalità di destinazione
     * @return
     */
    private String createLink(String property, String uriBack, LinkTargetEnum linkTarget) {

	String p = "";
	if (StringUtils.isNotBlank(property)) {
	    p = property + ".";
	}
	String link = getLinkTarget(linkTarget, p);
	if (StringUtils.isNotBlank(uriBack)) {
	    link = "javascript:historySet('" + uriBack + "','" + link + "','" + StringUtils.defaultIfEmpty(this.messageToConfirmLink, "") + "');";
	}
	return link;
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

    /**
     * restituisce il link alla detinazione(target) specificata
     * 
     * @param linkTarget
     * @param p
     * @return
     */
    private String getLinkTarget(LinkTargetEnum linkTarget, String p) {

	String link = "";
	switch (linkTarget) {
	case DETTAGLIO_ISTANZA:
	    link = "../istanze/view.htm?codice=<" + p + "id.codice>&software=<" + p + "software.codice>";
	    break;
	case DETTAGLIO_MOVIMENTO:
	    link = "../movimenti/view.htm?codice=<" + p + "id.codice>&software=<" + p + "istanza.software.codice>";
	    break;
	case DETTAGLIO_AUTORIZZAZIONE:
	    link = "../autorizzazioni/viewAutorizzazione.htm?codice=<" + p + "id.codice>&codiceIstanza=<" + p + "istanza.id.codice>";
	    break;
	case DETTAGLIO_CONCESSIONI:
	    link = "../autorizzazioni/viewConcessione.htm?codiceAutorizzazione=<id.concId>&codiceIstanza=<" + p + "istanza.id.codice>";
	    break;
	case DETTAGLIO_ANAGRAFE:
	    link = "../anagrafe/view.htm?codice=<" + p + "id.codice>";
	    break;
	case ELIMINA_FO_RICHIESTE:
	    link = "../batchscadenzario/deleteFoRichiesta.htm?codice=<" + p + "id.codice>";
	    break;
	case BATCH_SCADENZARIO_ISTANZE:
	    link = "../istanze/view.htm?codice=<codiceistanza>&software=<software>";
	    break;
	case BATCH_SCADENZARIO_MOVIMENTI:
	    link = "../movimenti/view.htm?codice=<id>&software=<software>";
	    break;
	case ISTANZE_EVENTI_HELPER_ISTANZE:
	    link = "../istanze/view.htm?codice=<codiceistanza>&software=<codicesoftware>";
	    break;
	case ISTANZE_EVENTI_HELPER_MOVIMENTI:
	    link = "../movimenti/view.htm?codice=<codicemovimento>&software=<codicesoftware>";
	    break;
	case ISTANZE_STC_SCADENZARIO:
	    link = "../istanze/view.htm?codice=<codiceistanza>&software=<codSoftware>";
	    break;
	case INVENTARIOPROCEDIMENTISOFTWARE:
	    link = "../inventarioprocedimenti/listmodalita.htm?codiceendo=<" + p + "inventarioprocedimento.id.codice>&software=<" + p
		    + "inventarioprocedimento.software.codice>";
	    break;
	case INVENTARIOPROCEDIMENTI:
	    link = "../inventarioprocedimenti/view.htm?codice=<" + p + "inventarioprocedimento.id.codice>&software=<" + p
		    + "inventarioprocedimento.software.codice>";
	    break;
	case DOCUMENTI_DA_FIRMARE_ISTANZE:
	    link = "../istanze/view.htm?codice=<istanze.id.codice>&software=<istanze.software.codice>";
	    break;
	case DOCUMENTI_DA_FIRMARE_MOVIMENTI:
	    link = "../movimenti/view.htm?codice=<movimentiallegati.movimento.id.codice>&software=<istanze.software.codice>";
	    break;
	default:
	    break;
	}
	return link;
    }

    public String getLink() {

	return link;
    }

    public String[] getPlaceHolders() {

	return placeHolders;
    }
}
