package it.gruppoinit.pal.gp.areariservata.ws.client;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.ws.client.StcService;
import it.init.sigepro.rte.AggiungiDocumentiRequest;
import it.init.sigepro.rte.AggiungiDocumentiResponse;
import it.init.sigepro.rte.InserimentoPraticaRequest;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.LoginRequest;
import it.init.sigepro.rte.LoginResponse;
import it.init.sigepro.rte.NotificaAttivitaRequest;
import it.init.sigepro.rte.NotificaAttivitaResponse;
import it.init.sigepro.rte.RichiestaPraticaRequest;
import it.init.sigepro.rte.RichiestaPraticaResponse;
import it.init.sigepro.rte.RichiestaPraticheListaRequest;
import it.init.sigepro.rte.RichiestaPraticheListaResponse;
import it.init.sigepro.rte.definitions.Stc;
import it.init.sigepro.rte.types.DettaglioAttivitaType;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.FiltriPraticaType;
import it.init.sigepro.rte.types.FiltriUtenteType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.SportelloType;

import java.net.URL;
import java.util.List;

import javax.xml.ws.soap.MTOMFeature;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ARJSTCWSClient {

    private static final Logger log = LoggerFactory.getLogger(ARJSTCWSClient.class);
    private String stcWsUrl;
    private String stcWsUser;
    private String stcWsPassord;
    private String stcIdNodoMitt;
    private String stcIdNodoDest;
    private long timeout = 30000;

    public NotificaAttivitaResponse notificaAttivita(DettaglioAttivitaType dettaglioAttivitaType, RiferimentiPraticaType riferimentiPraticaType) {

	NotificaAttivitaResponse notificaAttivitaResponse;
	try {
	    Stc port = this.getStcWsPort();
	    String token = this.login(port);
	    NotificaAttivitaRequest notificaAttivitaRequest = new NotificaAttivitaRequest();
	    notificaAttivitaRequest.setDatiAttivita(dettaglioAttivitaType);
	    notificaAttivitaRequest.setRifPraticaDestinatario(riferimentiPraticaType);
	    SportelloType dest = new SportelloType();
	    dest.setIdNodo(stcIdNodoDest);
	    dest.setIdEnte(ORMHelper.getIdcomuneAlias());
	    dest.setIdSportello(ORMHelper.getSoftware());
	    this.logSportelloDestinatario("notificaAttivita", dest);
	    notificaAttivitaRequest.setToken(token);
	    notificaAttivitaRequest.setSportelloMittente(getSportelloMittente());
	    notificaAttivitaRequest.setSportelloDestinatario(dest);
	    notificaAttivitaResponse = port.notificaAttivita(notificaAttivitaRequest);
	} catch (Exception e) {
	    log.error("notificaAttivita: ", e);
	    throw new RuntimeException(e);
	}
	return notificaAttivitaResponse;
    }

    public InserimentoPraticaResponse inserimentoPratica(DettaglioPraticaType dettaglioPraticaType, SportelloType dest) {

	InserimentoPraticaResponse inserimentoPraticaResponse;
	try {
	    Stc port = this.getStcWsPort();
	    String token = this.login(port);
	    InserimentoPraticaRequest inserimentoPraticaRequest = new InserimentoPraticaRequest();
	    inserimentoPraticaRequest.setToken(token);
	    inserimentoPraticaRequest.setDettaglioPratica(dettaglioPraticaType);
	    this.logSportelloDestinatario("inserimentoPratica", dest);
	    inserimentoPraticaRequest.setSportelloMittente(getSportelloMittente());
	    inserimentoPraticaRequest.setSportelloDestinatario(dest);
	    inserimentoPraticaResponse = port.inserimentoPratica(inserimentoPraticaRequest);
	} catch (Exception e) {
	    log.error("inserimentoPratica: ", e);
	    throw new RuntimeException(e);
	}
	return inserimentoPraticaResponse;
    }

    public RichiestaPraticheListaResponse richiestaPratiche(SportelloType dest, FiltriPraticaType filtriPratica, FiltriUtenteType filtriUtente) {

	RichiestaPraticheListaResponse praticheListaResponse;
	try {
	    RichiestaPraticheListaRequest praticheListaRequest = new RichiestaPraticheListaRequest();
	    Stc port = this.getStcWsPort();
	    String token = this.login(port);
	    this.logSportelloDestinatario("richiestaPratiche", dest);
	    praticheListaRequest.setSportelloDestinatario(dest);
	    praticheListaRequest.setSportelloMittente(getSportelloMittente());
	    praticheListaRequest.setToken(token);
	    praticheListaRequest.setFiltriPratica(filtriPratica);
	    praticheListaRequest.setFiltriUtenteConnesso(filtriUtente);
	    praticheListaResponse = port.richiestaPraticheLista(praticheListaRequest);
	} catch (Exception e) {
	    log.error("richiestaPratiche: ", e);
	    throw new RuntimeException(e);
	}
	return praticheListaResponse;
    }

    public RichiestaPraticaResponse richiestaPratica(String idPraticaDest, SportelloType dest) {

	RichiestaPraticaResponse praticaResponse;
	try {
	    RichiestaPraticaRequest praticaRequest = new RichiestaPraticaRequest();
	    Stc port = this.getStcWsPort();
	    String token = this.login(port);
	    this.logSportelloDestinatario("richiestaPratica", dest);
	    SportelloType mitt = getSportelloMittente();
	    praticaRequest.setSportelloDestinatario(dest);
	    praticaRequest.setSportelloMittente(mitt);
	    praticaRequest.setToken(token);
	    log.debug("richiestaPratica: idPraticaDest={}", idPraticaDest);
	    RiferimentiPraticaType riferimentiPraticaType = new RiferimentiPraticaType();
	    riferimentiPraticaType.setIdPratica(idPraticaDest);
	    praticaRequest.setRifPratica(riferimentiPraticaType);
	    praticaResponse = port.richiestaPratica(praticaRequest);
	} catch (Exception e) {
	    log.error("richiestaPratica: ", e);
	    throw new RuntimeException(e);
	}
	return praticaResponse;
    }

    public AggiungiDocumentiResponse aggiungiDocumenti(String idPraticaDest, SportelloType dest, List<DocumentiType> docs) {

	try {
	    AggiungiDocumentiRequest req = new AggiungiDocumentiRequest();
	    Stc port = this.getStcWsPort();
	    String token = this.login(port);
	    req.setToken(token);
	    req.setIdPraticaDest(idPraticaDest);
	    req.setSportelloDestinatario(dest);
	    this.logSportelloDestinatario("aggiungiDocumenti", dest);
	    req.setSportelloMittente(getSportelloMittente());
	    req.getDocumenti().addAll(docs);
	    AggiungiDocumentiResponse resp = port.aggiungiDocumenti(req);
	    throwExceptionIfErrors(resp.getDettaglioErrore());
	    return resp;
	} catch (Exception e) {
	    log.error("aggiungiDocumenti: ", e);
	    throw new RuntimeException(e);
	}
    }

    private String login(Stc port) {

	LoginRequest loginRequest = new LoginRequest();
	loginRequest.setUsername(stcWsUser);
	loginRequest.setPassword(stcWsPassord);
	log.debug("login: user={}, pwd={}", stcWsUser, stcWsPassord);
	LoginResponse loginResponse = port.login(loginRequest);
	log.debug("login: response token={}", loginResponse.getToken());
	return loginResponse.getToken();
    }

    private void logSportelloDestinatario(String methodName, SportelloType dest) {

	log.debug("{}: sportello dest[idNodo={}, idEnte={}, idSportello={}]",
		new Object[] { methodName, dest.getIdNodo(), dest.getIdEnte(), dest.getIdSportello() });
    }

    private void throwExceptionIfErrors(List<ErroreType> errors) throws Exception {

	if (!errors.isEmpty()) {
	    StringBuffer buff = new StringBuffer();
	    for (ErroreType error : errors) {
		buff.append(error.getNumeroErrore()).append("-").append(error.getDescrizione()).append("\n");
	    }
	    throw new Exception(buff.toString());
	}
    }

    private SportelloType getSportelloMittente() {

	SportelloType mitt = new SportelloType();
	mitt.setIdNodo(stcIdNodoMitt);
	mitt.setIdEnte(ORMHelper.getIdcomuneAlias());
	mitt.setIdSportello(ORMHelper.getSoftware());
	log.debug("getSportelloMittente: IdNodo={}, IdEnte={}, IdSportello={} ",
		new Object[] { mitt.getIdNodo(), mitt.getIdEnte(), mitt.getIdSportello() });
	return mitt;
    }

    private Stc getStcWsPort() throws Exception {

	log.debug("getStcWsPort: url={}, timeout={}", stcWsUrl, timeout);
	StcService client = new StcService(new URL(this.stcWsUrl));
	MTOMFeature mtomFeature = new MTOMFeature(true, 0);
	Stc port = client.getStcSoap11(mtomFeature);
	Client proxy = ClientProxy.getClient(port);
	HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	httpClientPolicy.setConnectionTimeout(this.timeout);
	httpClientPolicy.setReceiveTimeout(this.timeout);
	conduit.setClient(httpClientPolicy);
	return port;
    }

    public void setStcWsUrl(String stcWsUrl) {

	this.stcWsUrl = stcWsUrl;
    }

    public void setStcWsUser(String stcWsUser) {

	this.stcWsUser = stcWsUser;
    }

    public void setStcWsPassord(String stcWsPassord) {

	this.stcWsPassord = stcWsPassord;
    }

    public void setTimeout(long timeout) {

	this.timeout = timeout;
    }

    public void setStcIdNodoMitt(String stcIdNodoMitt) {

	this.stcIdNodoMitt = stcIdNodoMitt;
    }

    public void setStcIdNodoDest(String stcIdNodoDest) {

	this.stcIdNodoDest = stcIdNodoDest;
    }

    public String getStcIdNodoDest() {

	return stcIdNodoDest;
    }
}
