package it.gruppoinit.pal.gp.core.ws.client;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import javax.xml.ws.soap.MTOMFeature;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paevolution.ws.pagamenti_types.AnnullaPosizioniDebitorieType;
import com.paevolution.ws.pagamenti_types.AttivaSessionePagamentoResponseType;
import com.paevolution.ws.pagamenti_types.AttivaSessionePagamentoType;
import com.paevolution.ws.pagamenti_types.CaricamentoMassivoPosizioniDebitorieType;
import com.paevolution.ws.pagamenti_types.DocumentiPosizioneDebitoriaType;
import com.paevolution.ws.pagamenti_types.ElencoDocumentiEsitoType;
import com.paevolution.ws.pagamenti_types.ElencoDocumentiType;
import com.paevolution.ws.pagamenti_types.GeneraFattureType;
import com.paevolution.ws.pagamenti_types.InfoConnettoreType;
import com.paevolution.ws.pagamenti_types.InserisciPosizioniDebitorieType;
import com.paevolution.ws.pagamenti_types.InviaAvvisiPagamentoType;
import com.paevolution.ws.pagamenti_types.ModificaDataFineValiditaResponseType;
import com.paevolution.ws.pagamenti_types.ModificaDataFineValiditaType;
import com.paevolution.ws.pagamenti_types.ModificaDataScadenzaResponseType;
import com.paevolution.ws.pagamenti_types.ModificaDataScadenzaType;
import com.paevolution.ws.pagamenti_types.NotificaPagamentoOffline;
import com.paevolution.ws.pagamenti_types.OperazionePosizioniDebitorieResponseType;
import com.paevolution.ws.pagamenti_types.Pagamenti;
import com.paevolution.ws.pagamenti_types.PagamentiService;
import com.paevolution.ws.pagamenti_types.PayRequestType;
import com.paevolution.ws.pagamenti_types.ScaricaRicevuteTelematicheType;
import com.paevolution.ws.pagamenti_types.VerificaStatoPosizioniResponseType;
import com.paevolution.ws.pagamenti_types.VerificaStatoPosizioniType;

import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.ConnettoriListType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.ParametriListType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.PosizioneDebitoriaListResponseType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.PosizioneDebitoriaRequestType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.PosizioneDebitoriaResponseType;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class NodoPagamentiWsClient extends BaseWsClient {

    private static final Logger log = LoggerFactory.getLogger(NodoPagamentiWsClient.class);
    private long connectionTimeOut = 20000;
    private long receivetimeout = 600000;
    private String wsUrl;

    public NodoPagamentiWsClient(String urlWs) {

	this.wsUrl = urlWs;
	// this.wsUrl =  "http://localhost:8080/nodo-pagamenti/services/pagamentiSOAP?wsdl";
    }

    private WebClient getRestWebClient(String restServiceUrl, long connectionTimeOut, long receivetimeout) {

	if (StringUtils.isBlank(wsUrl)) {
	    throw new InvalidConfigurationException(
		    "Url ws non configurata. Controllare il parametro " + VerticalizzazioneNodoPagamentiServiceImpl.URL_WS +
						    " della verticalizzazione " + VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE);
	}
	if (StringUtils.defaultString(wsUrl).indexOf("/services/") == -1) {
	    throw new InvalidConfigurationException(
		    "Non è stato possibile recuperare le informazioni del servizio rest dal parametro VERTICALIZZAZIONE_NODO_PAGAMENTI_URL_WS");
	}
	// String wsUrl = "http://localhost:8080/nodo-pagamenti/services/pagamentiSOAP?wsdl";
	// wsUrl è http://devel3/nodo-pagamenti/services/pagamentiSOAP?wsdl devo recuperare fino a nodo-pagamenti/services/ e scrivere SERVICES/REST
	wsUrl = wsUrl.substring(0, wsUrl.indexOf("/services/") + 9) + "/rest";
	WebClient client = WebClient.create(wsUrl + restServiceUrl);
	// connection timeout
	HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	conduit.getClient().setConnectionTimeout(connectionTimeOut);
	conduit.getClient().setReceiveTimeout(receivetimeout);
	return client;
    }

    private PagamentiService getPagamentiPort() throws Exception {

	if (StringUtils.isBlank(wsUrl)) {
	    throw new InvalidConfigurationException(
		    "Url ws non confugurata. Controllare il parametro " + VerticalizzazioneNodoPagamentiServiceImpl.URL_WS +
						    " della verticalizzazione " + VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE);
	}
	log.debug("Url Nodo Pagamenti: {}", wsUrl);
	Pagamenti client = new Pagamenti(new URL(wsUrl));
	MTOMFeature mtomFeature = new MTOMFeature(false, 0);
	PagamentiService port = client.getPagamenti(mtomFeature);
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	// HTTPClientPolicy - Properties used to configure a client-side HTTP port  
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy(); // Line #1  
	httpClientPolicy.setConnectionTimeout(12000); // Line #2  
	httpClientPolicy.setReceiveTimeout(360000); // Line #3  
	conduit.setClient(httpClientPolicy);
	log.debug("Porta nodo-pagamenti istanziata correttamente");
	return port;
    }

    public InfoConnettoreType infoConnettore(PayRequestType r) throws FunzioneBusinessRemotaException {

	try {
	    return getPagamentiPort().infoConnettore(r);
	} catch (Exception e) {
	    log.error("Errore nella chiamata a getPagamentiPort().infoConnettore {}", e);
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    public OperazionePosizioniDebitorieResponseType annullaPosizioneDebitoria(AnnullaPosizioniDebitorieType posizioneDebitoria)
	    throws FunzioneBusinessRemotaException {

	OperazionePosizioniDebitorieResponseType result = null;
	try {
	    result = getPagamentiPort().annullaPosizioniDebitorie(posizioneDebitoria);
	} catch (Exception e) {
	    log.error("Errore nella chiamata a getPagamentiPort().inserisciPosizioniDebitorie {}", e);
	    throw new FunzioneBusinessRemotaException(e);
	}
	return result;
    }

    public OperazionePosizioniDebitorieResponseType inserisciPosizioneDebitoria(InserisciPosizioniDebitorieType posizioneDebitoria)
	    throws FunzioneBusinessRemotaException {

	OperazionePosizioniDebitorieResponseType result = null;
	try {
	    result = getPagamentiPort().inserisciPosizioniDebitorie(posizioneDebitoria);
	} catch (Exception e) {
	    log.error("Errore nella chiamata a getPagamentiPort().inserisciPosizioniDebitorie {}", e);
	    throw new FunzioneBusinessRemotaException(e);
	}
	return result;
    }

    public OperazionePosizioniDebitorieResponseType caricamentoMassivoPosizioneDebitoria(CaricamentoMassivoPosizioniDebitorieType posizioneDebitoria)
	    throws FunzioneBusinessRemotaException {

	OperazionePosizioniDebitorieResponseType result = null;
	try {
	    result = getPagamentiPort().caricamentoMassivoPosizioniDebitorie(posizioneDebitoria);
	} catch (Exception e) {
	    log.error("Errore nella chiamata a getPagamentiPort().inserisciPosizioneDebitoria {}", e);
	    throw new FunzioneBusinessRemotaException(e);
	}
	return result;
    }

    public AttivaSessionePagamentoResponseType attivaSessionePagamento(AttivaSessionePagamentoType parameters)
	    throws FunzioneBusinessRemotaException {

	AttivaSessionePagamentoResponseType result = null;
	try {
	    result = getPagamentiPort().attivaSessionePagamento(parameters);
	} catch (Exception e) {
	    log.error("Errore nella chiamata a getPagamentiPort().inserisciPosizioniDebitorie {}", e);
	    throw new FunzioneBusinessRemotaException(e);
	}
	return result;
    }

    public OperazionePosizioniDebitorieResponseType registraPagamentiOffline(NotificaPagamentoOffline request)
	    throws FunzioneBusinessRemotaException {

	OperazionePosizioniDebitorieResponseType result = null;
	try {
	    result = getPagamentiPort().registraPagamentiOffline(request);
	} catch (Exception e) {
	    log.error("Errore nella chiamata a getPagamentiPort().inserisciPosizioniDebitorie {}", e);
	    throw new FunzioneBusinessRemotaException(e);
	}
	return result;
    }

    public VerificaStatoPosizioniResponseType verificaStatoPosizioniDebitorie(VerificaStatoPosizioniType request)
	    throws FunzioneBusinessRemotaException {

	VerificaStatoPosizioniResponseType result = null;
	try {
	    result = getPagamentiPort().verificaStatoPosizioni(request);
	} catch (Exception e) {
	    log.error("Errore nella chiamata a getPagamentiPort().inserisciPosizioniDebitorie {}", e);
	    throw new FunzioneBusinessRemotaException(e);
	}
	return result;
    }

    public ElencoDocumentiEsitoType inviaAvvisoPagamento(InviaAvvisiPagamentoType inviaAvvisoPagamentoType) throws FunzioneBusinessRemotaException {

	ElencoDocumentiEsitoType result = null;
	try {
	    result = getPagamentiPort().inviaAvvisoPagamento(inviaAvvisoPagamentoType);
	} catch (Exception e) {
	    log.error("Errore nella chiamata a getPagamentiPort().inviaAvvisoPagamento {}", e);
	    throw new FunzioneBusinessRemotaException(e);
	}
	return result;
    }

    public ElencoDocumentiType documentiPosizione(DocumentiPosizioneDebitoriaType request) throws FunzioneBusinessRemotaException {

	ElencoDocumentiType result = null;
	try {
	    result = getPagamentiPort().documentiPosizione(request);
	} catch (Exception e) {
	    log.error("Errore nella chiamata a getPagamentiPort().documentiPosizione {}", e);
	    throw new FunzioneBusinessRemotaException(e);
	}
	return result;
    }

    public ElencoDocumentiEsitoType generaFattura(GeneraFattureType generaFatturaType) throws FunzioneBusinessRemotaException {

	ElencoDocumentiEsitoType result = null;
	try {
	    result = getPagamentiPort().generaFattura(generaFatturaType);
	} catch (Exception e) {
	    log.error("Errore nella chiamata a getPagamentiPort().generaFattura {}", e);
	    throw new FunzioneBusinessRemotaException(e);
	}
	return result;
    }

    public ElencoDocumentiEsitoType scaricaRicevutaTelematica(ScaricaRicevuteTelematicheType scaricaRicevuteTelematicheType)
	    throws FunzioneBusinessRemotaException {

	ElencoDocumentiEsitoType result = null;
	try {
	    result = getPagamentiPort().scaricaRicevutaTelematica(scaricaRicevuteTelematicheType);
	} catch (Exception e) {
	    log.error("Errore nella chiamata a getPagamentiPort().scaricaRicevutaTelematica {}", e);
	    throw new FunzioneBusinessRemotaException(e);
	}
	return result;
    }

    public PosizioneDebitoriaResponseType getDettaglioPosizioneDebitoria(String cfenteCreditore, Integer idPosizioneDebitoria)
	    throws FunzioneBusinessRemotaException {

	WebClient c = getRestWebClient("/pagamenti/posizionidebitorie/", connectionTimeOut, receivetimeout)
		.path("{identecreditore}/{idposizionedebitoria}", cfenteCreditore, idPosizioneDebitoria);
	c.accept(MediaType.APPLICATION_JSON);
	Response response = c.get();
	InputStream is = ((InputStream) response.getEntity());
	if (response.getStatus() == 200) {
	    try {
		return Utilities.unMarshallJsonStream(is, PosizioneDebitoriaResponseType.class, true);
	    } catch (JAXBException e) {
		throw new FunzioneBusinessRemotaException(cfenteCreditore);
	    }
	} else {
	    String s = "";
	    try {
		s = IOUtils.toString(is);
	    } catch (Exception e) {
	    }
	    log.error("getDettaglioPosizioneDebitoria {}", s);
	    throw new FunzioneBusinessRemotaException("Errore nel recupero del dettaglio della posizione debitoria " + idPosizioneDebitoria + " [" +
						      cfenteCreditore + "]. Dettaglio Errore: \n" + s);
	}
    }

    @SuppressWarnings("unchecked")
    public List<ConnettoriListType> getMappaturaConnettore(ParametriListType request) throws Exception {

	WebClient c = getRestWebClient("/pagamenti/configurazioni/causali", connectionTimeOut, receivetimeout);
	c.accept(MediaType.APPLICATION_JSON);
	c.type(MediaType.APPLICATION_JSON);
	String richiesta = Utilities.marshalJsonObject(request, ParametriListType.class, true, Utilities.JAXB_ENCODING_UTF_8);
	Response response = c.post(richiesta);
	InputStream is = ((InputStream) response.getEntity());
	if (response.getStatus() == 200) {
	    try {
		List<ConnettoriListType> ct = (List<ConnettoriListType>) Utilities.unMarshallJsonStream(is, ConnettoriListType.class, false);
		return ct;
	    } catch (JAXBException e) {
		throw new FunzioneBusinessRemotaException(e);
	    }
	} else {
	    String s = "";
	    try {
		s = IOUtils.toString(is);
	    } catch (Exception e) {
	    }
	    log.error("getMappaturaConnettore {}", s);
	    throw new FunzioneBusinessRemotaException("Nessuna mappatura trovata. Dettaglio Errore: \n" + s);
	}
    }

    public PosizioneDebitoriaListResponseType getListaPosizioniDebitorie(String cfEnteCreditore, PosizioneDebitoriaRequestType request, int offset,
	    int limit) throws FunzioneBusinessRemotaException, Exception {

	WebClient c = getRestWebClient("/pagamenti/posizionidebitorie/", connectionTimeOut, receivetimeout).path("{identecreditore}", cfEnteCreditore)
		.query("limit", limit).query("offset", offset);
	c.accept(MediaType.APPLICATION_JSON);
	c.type(MediaType.APPLICATION_JSON);
	String richiesta = Utilities.marshalJsonObject(request, PosizioneDebitoriaRequestType.class, true, Utilities.JAXB_ENCODING_UTF_8);
	Response response = c.post(richiesta);
	InputStream is = ((InputStream) response.getEntity());
	if (response.getStatus() == 200) {
	    try {
		return Utilities.unMarshallJsonStream(is, PosizioneDebitoriaListResponseType.class, true);
	    } catch (JAXBException e) {
		throw new FunzioneBusinessRemotaException(e);
	    }
	} else {
	    String s = "";
	    try {
		s = IOUtils.toString(is);
	    } catch (Exception e) {
	    }
	    log.error("getListaPosizioniDebitorie {}", s);
	    throw new FunzioneBusinessRemotaException(
		    "Errore nel recupero della lista delle posizioni debitorie  [" + cfEnteCreditore + "]. Dettaglio Errore: \n" + s);
	}
    }

    public ModificaDataScadenzaResponseType modificaDataScadenzaPosizioneDebitoria(ModificaDataScadenzaType request)
	    throws FunzioneBusinessRemotaException {

	ModificaDataScadenzaResponseType result = null;
	try {
	    result = getPagamentiPort().modificaDataScadenza(request);
	} catch (Exception e) {
	    log.error("Errore nella chiamata a getPagamentiPort().modificaDataScadenzaPosizioneDebitoria {}", e);
	    throw new FunzioneBusinessRemotaException(e);
	}
	return result;
    }
    
    public ModificaDataFineValiditaResponseType modificaDataFineValiditaPosizioneDebitoria(ModificaDataFineValiditaType request)
	    throws FunzioneBusinessRemotaException {

	ModificaDataFineValiditaResponseType result = null;
	try {
	    result = getPagamentiPort().modificaDataFineValidita(request);
	} catch (Exception e) {
	    log.error("Errore nella chiamata a getPagamentiPort().modificaDataScadenzaPosizioneDebitoria {}", e);
	    throw new FunzioneBusinessRemotaException(e);
	}
	return result;
    }
}
