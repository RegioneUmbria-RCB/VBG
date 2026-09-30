package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Configurazione;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.io.StringWriter;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ComunicazioniTelematicheHelper {

    public static String getXMLSUAPComunicazione(Movimenti mov, Configurazione conf) {

	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	StringWriter sw = new StringWriter();
	try {
	    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	    // suap-comunicazione elements
	    Document doc = docBuilder.newDocument();
	    Element suapComunicazioneElement = doc.createElement("suap-comunicazione");
	    doc.appendChild(suapComunicazioneElement);
	    // ufficio-mittente elements
	    Element ufficioMittente = doc.createElement("ufficio-mittente");
	    ufficioMittente.appendChild(doc.createTextNode(StringUtils.defaultString(conf.getDenominazione())));
	    suapComunicazioneElement.appendChild(ufficioMittente);
	    // destinatario elements
	    Element destinatario = doc.createElement("destinatario");
	    destinatario.appendChild(doc.createTextNode(mov.getIstanza().getTitolarelegale() != null ? mov.getIstanza().getTitolarelegale()
		    .getDescrizioneRichiedente() : ""));
	    suapComunicazioneElement.appendChild(destinatario);
	    // destinatario-cc elements
	    Element destinatarioCC = doc.createElement("destinatario-cc");
	    destinatarioCC.appendChild(doc.createTextNode(mov.getIstanza().getRichiedente() != null ? mov.getIstanza().getRichiedente()
		    .getDescrizioneRichiedente() : ""));
	    suapComunicazioneElement.appendChild(destinatarioCC);
	    // oggetto elements
	    Element oggetto = doc.createElement("oggetto");
	    oggetto.appendChild(doc.createTextNode(StringUtils.defaultString(mov.getIstanza().getLavori())));
	    suapComunicazioneElement.appendChild(oggetto);
	    // testo elements
	    Element testo = doc.createElement("testo");
	    testo.appendChild(doc.createTextNode(StringUtils.defaultString(mov.getMovimento())));
	    suapComunicazioneElement.appendChild(testo);
	    // responsabile elements
	    // (Viene ricercato il responsabile del procedimento, se non lo trova allora prende il responsabile)
	    Element responsabile = doc.createElement("responsabile");
	    if (EntityUtils.getNestedProperty(mov.getIstanza().getResponsabileProcedimento(), "id.codice") != null) {
		responsabile.appendChild(doc.createTextNode(mov.getIstanza().getResponsabileProcedimento().getResponsabile()));
	    } else {
		responsabile.appendChild(doc.createTextNode(mov.getIstanza().getResponsabile() != null ? mov.getIstanza().getResponsabile()
			.getResponsabile() : ""));
	    }
	    suapComunicazioneElement.appendChild(responsabile);
	    // protocollo elements
	    Element protocollo = doc.createElement("protocollo");
	    protocollo.appendChild(doc.createTextNode(StringUtils.defaultString(mov.getMovimento())));
	    suapComunicazioneElement.appendChild(protocollo);
	    // numero elements
	    Element numero = doc.createElement("numero");
	    numero.appendChild(doc.createTextNode(StringUtils.defaultString(mov.getIstanza().getNumeroprotocollo())));
	    protocollo.appendChild(numero);
	    // data elements
	    Element data = doc.createElement("data");
	    data.appendChild(doc.createTextNode(Utilities.formatDate(mov.getIstanza().getDataprotocollo(), false)));
	    protocollo.appendChild(data);
	    // creazione xml
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);
	    StreamResult result = new StreamResult(sw);
	    transformer.transform(source, result);
	} catch (ParserConfigurationException pce) {
	    pce.printStackTrace();
	} catch (TransformerException tfe) {
	    tfe.printStackTrace();
	}
	return sw.toString();
    }

    public static String getXMLSUAPRicevuta(Movimenti mov, Configurazione confSportello, Configurazione confEnte) {

	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	StringWriter sw = new StringWriter();
	try {
	    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	    // suap-ricevuta elements
	    Document doc = docBuilder.newDocument();
	    Element suapRicevuta = doc.createElement("suap-ricevuta");
	    doc.appendChild(suapRicevuta);
	    // ente-denominazione elements
	    Element enteDenominazione = doc.createElement("ente-denominazione");
	    enteDenominazione.appendChild(doc.createTextNode(StringUtils.defaultString(confEnte.getDenominazione())));
	    suapRicevuta.appendChild(enteDenominazione);
	    // codice-accreditamento elements
	    Element codiceAccreditamento = doc.createElement("codice-accreditamento");
	    codiceAccreditamento.appendChild(doc.createTextNode(StringUtils.defaultString(confEnte.getCodiceaccreditamento())));
	    suapRicevuta.appendChild(codiceAccreditamento);
	    // ufficio-ricevente elements
	    Element ufficioRicevente = doc.createElement("ufficio-ricevente");
	    ufficioRicevente.appendChild(doc.createTextNode(StringUtils.defaultString(confSportello.getDenominazione())));
	    suapRicevuta.appendChild(ufficioRicevente);
	    // istanza elements
	    getIstanzaXML(suapRicevuta, doc, mov);
	    // creazione xml
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);
	    StreamResult result = new StreamResult(sw);
	    transformer.transform(source, result);
	} catch (ParserConfigurationException pce) {
	    pce.printStackTrace();
	} catch (TransformerException tfe) {
	    tfe.printStackTrace();
	}
	return sw.toString();
    }

    private static Element getIstanzaXML(Element element, Document doc, Movimenti mov) throws ParserConfigurationException {

	// istanza elements
	Element istanza = doc.createElement("istanza");
	element.appendChild(istanza);
	// numero elements
	Element numero = doc.createElement("numero");
	numero.appendChild(doc.createTextNode(StringUtils.defaultString(mov.getIstanza().getNumeroistanza())));
	istanza.appendChild(numero);
	// data elements
	Element data = doc.createElement("data");
	data.appendChild(doc.createTextNode(Utilities.formatDate(mov.getIstanza().getData(), false)));
	istanza.appendChild(data);
	// protocollo elements
	Element protocollo = doc.createElement("protocollo");
	istanza.appendChild(protocollo);
	// numeroP elements
	Element numeroP = doc.createElement("numero");
	numeroP.appendChild(doc.createTextNode(StringUtils.defaultString(mov.getIstanza().getNumeroprotocollo())));
	protocollo.appendChild(numeroP);
	// dataP elements
	Element dataP = doc.createElement("data");
	dataP.appendChild(doc.createTextNode(Utilities.formatDate(mov.getIstanza().getDataprotocollo(), false)));
	protocollo.appendChild(dataP);
	Anagrafe richiedente = mov.getIstanza().getRichiedente() != null ? mov.getIstanza().getRichiedente() : new Anagrafe();
	// dichiarante elements
	Element dichiarante = doc.createElement("dichiarante");
	istanza.appendChild(dichiarante);
	// nome elements
	Element nome = doc.createElement("nome");
	nome.appendChild(doc.createTextNode(StringUtils.defaultString(richiedente.getNome())));
	dichiarante.appendChild(nome);
	// cognome elements
	Element cognome = doc.createElement("cognome");
	cognome.appendChild(doc.createTextNode(StringUtils.defaultString(richiedente.getNominativo())));
	dichiarante.appendChild(cognome);
	// descrizione elements
	Element descrizione = doc.createElement("descrizione");
	descrizione.appendChild(doc.createTextNode(StringUtils.defaultString(richiedente.getDescrizioneRichiedente())));
	dichiarante.appendChild(descrizione);
	// comune-nascita elements
	Element comuneNascita = doc.createElement("comune-nascita");
	if (EntityUtils.getNestedProperty(richiedente, "comuneNascita.comune") != null) {
	    if (EntityUtils.getNestedProperty(richiedente, "comuneNascita.siglaprovincia") != null) {
		comuneNascita.appendChild(doc.createTextNode(richiedente.getComuneNascita().getComune() + " ("
			+ StringUtils.defaultString(richiedente.getComuneNascita().getSiglaprovincia()) + ")"));
	    } else {
		comuneNascita.appendChild(doc.createTextNode(StringUtils.defaultString(richiedente.getComuneNascita().getComune())));
	    }
	} else {
	    comuneNascita.appendChild(doc.createTextNode(""));
	}
	dichiarante.appendChild(comuneNascita);
	// data-nascita elements
	Element dataNascita = doc.createElement("data-nascita");
	dataNascita.appendChild(doc.createTextNode(Utilities.formatDate(richiedente.getDatanascita(), false)));
	dichiarante.appendChild(dataNascita);
	// cf elements
	Element cf = doc.createElement("cf");
	cf.appendChild(doc.createTextNode(StringUtils.defaultString(richiedente.getCodicefiscale())));
	dichiarante.appendChild(cf);
	// residenza elements
	Element residenza = doc.createElement("residenza");
	dichiarante.appendChild(residenza);
	// via elements
	Element via = doc.createElement("via");
	via.appendChild(doc.createTextNode(StringUtils.defaultString(richiedente.getIndirizzo())));
	residenza.appendChild(via);
	// comune elements
	Element comune = doc.createElement("comune");
	comune.appendChild(doc.createTextNode(StringUtils.defaultString(richiedente.getComuneResidenza() != null ? richiedente.getComuneResidenza()
		.getComune() : "")));
	residenza.appendChild(comune);
	// provincia elements
	Element provincia = doc.createElement("provincia");
	provincia.appendChild(doc.createTextNode(StringUtils.defaultString(richiedente.getProvincia())));
	residenza.appendChild(provincia);
	// indirizzo-pec elements
	Element indirizzoPec = doc.createElement("indirizzo-pec");
	indirizzoPec.appendChild(doc.createTextNode(StringUtils.defaultString(richiedente.getPec())));
	dichiarante.appendChild(indirizzoPec);
	// indirizzo-email elements
	Element indirizzoEmail = doc.createElement("indirizzo-email");
	indirizzoEmail.appendChild(doc.createTextNode(StringUtils.defaultString(richiedente.getEmail())));
	dichiarante.appendChild(indirizzoEmail);
	// telefono elements
	Element telefono = doc.createElement("telefono");
	telefono.appendChild(doc.createTextNode(StringUtils.defaultString(richiedente.getTelefono())));
	dichiarante.appendChild(telefono);
	// cellulare elements
	Element cellulare = doc.createElement("cellulare");
	cellulare.appendChild(doc.createTextNode(StringUtils.defaultString(richiedente.getTelefonocellulare())));
	dichiarante.appendChild(cellulare);
	Tipisoggetto tipisoggetto = mov.getIstanza().getTipisoggetto() != null ? mov.getIstanza().getTipisoggetto() : new Tipisoggetto();
	// in-qualita-di elements
	Element inQualitaDi = doc.createElement("in-qualita-di");
	inQualitaDi.appendChild(doc.createTextNode(StringUtils.defaultString(tipisoggetto.getTiposoggetto())));
	istanza.appendChild(inQualitaDi);
	Anagrafe anagrafeImpresa = mov.getIstanza().getTitolarelegale() != null ? mov.getIstanza().getTitolarelegale() : new Anagrafe();
	// impresa elements
	Element impresa = doc.createElement("impresa");
	istanza.appendChild(impresa);
	// ragione-sociale elements
	if (EntityUtils.getNestedProperty(anagrafeImpresa, "id.codice") != null) {
	    Element ragioneSociale = doc.createElement("ragione-sociale");
	    if (EntityUtils.getNestedProperty(anagrafeImpresa, "formagiuridica") != null) {
		ragioneSociale.appendChild(doc.createTextNode(StringUtils.defaultString(anagrafeImpresa.getNominativo()) + " "
			+ anagrafeImpresa.getFormagiuridica().getFormagiuridica()));
	    } else {
		ragioneSociale.appendChild(doc.createTextNode(StringUtils.defaultString(anagrafeImpresa.getNominativo())));
	    }
	    impresa.appendChild(ragioneSociale);
	}
	// num-reg-imprese elements
	Element numRegImprese = doc.createElement("num-reg-imprese");
	numRegImprese.appendChild(doc.createTextNode(StringUtils.defaultString(anagrafeImpresa.getRegtrib())));
	impresa.appendChild(numRegImprese);
	// p-iva elements
	Element pIva = doc.createElement("p-iva");
	pIva.appendChild(doc.createTextNode(StringUtils.defaultString(anagrafeImpresa.getPartitaiva())));
	impresa.appendChild(pIva);
	// cf elements
	Element cfImpresa = doc.createElement("cf");
	cfImpresa.appendChild(doc.createTextNode(StringUtils.defaultString(anagrafeImpresa.getCodicefiscale())));
	impresa.appendChild(cfImpresa);
	// sede-legale elements
	Element sedeLegale = doc.createElement("sede-legale");
	impresa.appendChild(sedeLegale);
	// via elements
	Element viaImpresa = doc.createElement("via");
	viaImpresa.appendChild(doc.createTextNode(StringUtils.defaultString(anagrafeImpresa.getIndirizzo())));
	sedeLegale.appendChild(viaImpresa);
	// comune elements
	Element comuneImpresa = doc.createElement("comune");
	comuneImpresa.appendChild(doc.createTextNode(StringUtils.defaultString(anagrafeImpresa.getComuneResidenza() != null ? anagrafeImpresa
		.getComuneResidenza().getComune() : "")));
	sedeLegale.appendChild(comuneImpresa);
	// provincia elements
	Element provinciaImpresa = doc.createElement("provincia");
	provinciaImpresa.appendChild(doc.createTextNode(StringUtils.defaultString(anagrafeImpresa.getProvincia())));
	sedeLegale.appendChild(provinciaImpresa);
	// responsabile elements 
	// (Viene ricercato il responsabile del procedimento, se non lo trova allora prende il responsabile)
	Element responsabile = doc.createElement("responsabile");
	if (EntityUtils.getNestedProperty(mov.getIstanza().getResponsabileProcedimento(), "id.codice") != null) {
	    responsabile.appendChild(doc.createTextNode(StringUtils.defaultString(mov.getIstanza().getResponsabileProcedimento().getResponsabile())));
	} else {
	    responsabile.appendChild(doc.createTextNode(mov.getIstanza().getResponsabile() != null ? mov.getIstanza().getResponsabile()
		    .getResponsabile() : ""));
	}
	istanza.appendChild(responsabile);
	// intervento elements
	Element intervento = doc.createElement("intervento");
	intervento.appendChild(doc.createTextNode(StringUtils.defaultString(mov.getIstanza().getAlberoproc().getVwAlberoproc().getScDescrizione())));
	istanza.appendChild(intervento);
	// oggetto elements
	Element oggetto = doc.createElement("oggetto");
	oggetto.appendChild(doc.createTextNode(StringUtils.defaultString(mov.getIstanza().getLavori())));
	istanza.appendChild(oggetto);
	// indirizzi elements
	Element indirizzi = doc.createElement("indirizzi");
	istanza.appendChild(indirizzi);
	Set<Istanzestradario> indirizziIstanza = mov.getIstanza().getIstanzestradarios();
	for (Istanzestradario indirizzoIstanza : indirizziIstanza) {
	    // indirizzo elements
	    Element indirizzo = doc.createElement("indirizzo");
	    indirizzi.appendChild(indirizzo);
	    // descrizione elements
	    Element descrizioneIndirizzo = doc.createElement("descrizione");
	    descrizioneIndirizzo.appendChild(doc.createTextNode(StringUtils.defaultString(indirizzoIstanza.getDescrizioneEstesaTransient())));
	    indirizzo.appendChild(descrizioneIndirizzo);
	    // via elements
	    Element viaIndirizzo = doc.createElement("via");
	    viaIndirizzo.appendChild(doc.createTextNode(StringUtils.defaultString(indirizzoIstanza.getStradario().getDescrizioneCompleta())));
	    indirizzo.appendChild(viaIndirizzo);
	    // comune elements
	    Element comuneIndirizzo = doc.createElement("comune");
	    comuneIndirizzo
		    .appendChild(doc.createTextNode(StringUtils.defaultString(indirizzoIstanza.getStradario().getComune() != null ? indirizzoIstanza
			    .getStradario().getComune().getComune() : "")));
	    indirizzo.appendChild(comuneIndirizzo);
	    // provincia elements
	    Element provinciaIndirizzo = doc.createElement("provincia");
	    provinciaIndirizzo.appendChild(doc.createTextNode(StringUtils
		    .defaultString(indirizzoIstanza.getStradario().getComune() != null ? indirizzoIstanza.getStradario().getComune().getProvincia()
			    : "")));
	    indirizzo.appendChild(provinciaIndirizzo);
	}
	// dati-catastali elements
	Element datiCatastali = doc.createElement("dati-catastali");
	istanza.appendChild(datiCatastali);
	Set<Istanzemappali> mappali = mov.getIstanza().getIstanzemappalis();
	for (Istanzemappali istanzemappali : mappali) {
	    // mappale elements
	    Element mappale = doc.createElement("mappale");
	    datiCatastali.appendChild(mappale);
	    // foglio elements
	    Element foglio = doc.createElement("foglio");
	    foglio.appendChild(doc.createTextNode(StringUtils.defaultString(istanzemappali.getFoglio())));
	    mappale.appendChild(foglio);
	    // particella elements
	    Element particella = doc.createElement("particella");
	    particella.appendChild(doc.createTextNode(StringUtils.defaultString(istanzemappali.getParticella())));
	    mappale.appendChild(particella);
	    // particella elements
	    Element sub = doc.createElement("sub");
	    sub.appendChild(doc.createTextNode(StringUtils.defaultString(istanzemappali.getSub())));
	    mappale.appendChild(sub);
	}
	// allegati elements
	Element allegati = doc.createElement("allegati");
	istanza.appendChild(allegati);
	if (mov.getIstanza().getDocumentiistanzas() != null) {
	    for (Documentiistanza doc2 : mov.getIstanza().getDocumentiistanzas()) {
		if (EntityUtils.getNestedProperty(doc2.getOggetto(), "id.codice") != null) {
		    // allegato elements
		    Element allegato = doc.createElement("allegato");
		    allegati.appendChild(allegato);
		    // nome elements
		    Element nomeAllegato = doc.createElement("nome");
		    nomeAllegato.appendChild(doc.createTextNode(StringUtils.defaultString(doc2.getOggetto().getNomefile())));
		    allegato.appendChild(nomeAllegato);
		    // descrizione elements
		    Element descrizioneAllegato = doc.createElement("descrizione");
		    descrizioneAllegato.appendChild(doc.createTextNode(StringUtils.defaultString(doc2.getDocumento())));
		    allegato.appendChild(descrizioneAllegato);
		}
	    }
	}
	if (mov.getIstanza().getIstanzeallegatis() != null && !mov.getIstanza().getIstanzeallegatis().isEmpty()) {
	    for (Istanzeallegati istanzeallegati : mov.getIstanza().getIstanzeallegatis()) {
		if (EntityUtils.getNestedProperty(istanzeallegati.getOggetto(), "id.codice") != null) {
		    Oggetti istAllegati = istanzeallegati.getOggetto();
		    // allegato elements
		    Element allegato = doc.createElement("allegato");
		    allegati.appendChild(allegato);
		    // nome elements
		    Element nomeAllegato = doc.createElement("nome");
		    nomeAllegato.appendChild(doc.createTextNode(StringUtils.defaultString(istAllegati.getNomefile())));
		    allegato.appendChild(nomeAllegato);
		    // descrizione elements
		    Element descrizioneAllegato = doc.createElement("descrizione");
		    descrizioneAllegato.appendChild(doc.createTextNode(StringUtils.defaultString(istanzeallegati.getAllegatoextra())));
		    allegato.appendChild(descrizioneAllegato);
		}
	    }
	}
	return istanza;
    }
}
