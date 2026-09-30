package it.gruppoinit.pal.gp.core.jmesa;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class JMesaTableLinkHelper implements IJMesaLinkHelper {

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
	    try {
		uriBack = URLEncoder.encode(uriBack, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
		//log.error("Errore durante l'ecoding della stringa {}", uriBack);
	    }
	    //link = "javascript:historySet('" + uriBack + "','" + link + "','" + StringUtils.defaultIfEmpty(this.messageToConfirmLink, "") + "');";
	    link = "javascript:doHref('../history/set.htm?ReturnTo=" + uriBack + "&GoTo=" + link + "','"
		    + StringUtils.defaultIfEmpty(this.messageToConfirmLink, "") + "')";
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
	    link = "../istanze/view.htm%253fcodice%3D<" + p + "id.codice>%2526software%3D<" + p + "software.codice>";
	    break;
	case DETTAGLIO_ISTANZA_VWCONCESSIONI:
	    link = "../istanze/view.htm%253fcodice%3D<ist_codiceistanza>";
	    break;
	case DETTAGLIO_MOVIMENTO:
	    link = "../movimenti/view.htm%253fcodice%3D<" + p + "id.codice>%2526software%3D<" + p + "istanza.software.codice>";
	    break;
	case DETTAGLIO_AUTORIZZAZIONE:
	    link = "../autorizzazioni/viewAutorizzazione.htm%253fcodice%3D<" + p + "id.codice>%2526codiceIstanza%3D<" + p + "istanza.id.codice>";
	    break;
	case DETTAGLIO_CONCESSIONI:
	    link = "../autorizzazioni/viewConcessione.htm%253fcodiceAutorizzazione%3D<conc_id>%2526codiceIstanza%3D<ist_codiceistanza>";
	    break;
	case DETTAGLIO_ANAGRAFE:
	    link = "../anagrafe/view.htm%253fcodice%3D<" + p + "id.codice>";
	    break;
	case DETTAGLIO_ANAGRAFE_VWCONCESSIONI:
	    link = "../anagrafe/view.htm%253fcodice%3D<conc_codicetitolare>";
	    break;
	case ELIMINA_FO_RICHIESTE:
	    link = "../batchscadenzario/deleteFoRichiesta.htm%253fcodice%3D<" + p + "id.codice>";
	    break;
	case BATCH_SCADENZARIO_ISTANZE:
	    link = "../istanze/view.htm%253fcodice%3D<codiceistanza>%2526software%3D<software>";
	    break;
	case BATCH_SCADENZARIO_MOVIMENTI:
	    link = "../movimenti/view.htm%253fcodice%3D<id>%2526software%3D<software>";
	    break;
	case ISTANZE_EVENTI_HELPER_ISTANZE:
	    link = "../istanze/view.htm%253fcodice%3D<codiceistanza>%2526software%3D<codicesoftware>";
	    break;
	case ISTANZE_EVENTI_HELPER_MOVIMENTI:
	    link = "../movimenti/view.htm%253fcodice%3D<codicemovimento>%2526software%3D<codicesoftware>";
	    break;
	case ISTANZE_STC_SCADENZARIO:
	    link = "../istanze/view.htm%253fcodice%3D<codiceistanza>%2526software%3D<codSoftware>";
	    break;
	case INVENTARIOPROCEDIMENTISOFTWARE:
	    link = "../inventarioprocedimenti/listmodalita.htm%253fcodiceendo%3D<" + p + "inventarioprocedimento.id.codice>%2526software%3D<" + p
		    + "inventarioprocedimento.software.codice>";
	    break;
	case INVENTARIOPROCEDIMENTI:
	    link = "../inventarioprocedimenti/view.htm%253fcodice%3D<" + p + "inventarioprocedimento.id.codice>%2526software%3D<" + p
		    + "inventarioprocedimento.software.codice>";
	    break;
	case DOCUMENTI_DA_FIRMARE_ISTANZE:
	    link = "../istanze/view.htm%253fcodice%3D<istanze.id.codice>%2526software%3D<istanze.software.codice>";
	    break;
	case DOCUMENTI_DA_FIRMARE_MOVIMENTI:
	    link = "../movimenti/view.htm%253fcodice%3D<movimentiallegati.movimento.id.codice>%2526software%3D<istanze.software.codice>";
	    break;
	case CREATE_AUTORIZZAZIONE:
	    link = "../autorizzazioni/create.htm%253fcodiceIstanza%3D<istanze.id.codice>";
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
