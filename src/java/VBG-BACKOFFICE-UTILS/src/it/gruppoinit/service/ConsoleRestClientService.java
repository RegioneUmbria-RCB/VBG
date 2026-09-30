package it.gruppoinit.service;

import it.gruppoinit.domain.AmministrazioniBean;
import it.gruppoinit.domain.CampiDinamiciBean;
import it.gruppoinit.domain.InformazioniAlberoInterventiBean;
import it.gruppoinit.domain.InventarioprocedimentoBean;
import it.gruppoinit.domain.ListaSchedeDinamicheBean;
import it.gruppoinit.domain.TipiCausaliOneriBean;
import it.gruppoinit.domain.TipiEndoBean;
import it.gruppoinit.domain.TipiFamiglieEndoBean;
import it.gruppoinit.domain.TipiSoggettoBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class ConsoleRestClientService {

    private String aliasOrigine;
    private String softwareOrigine;
    private String idOperazione;
    private String consoleRemotaURL;
    private String token;
    private ObjectMapper mapper;

    public ConsoleRestClientService(String aliasOrigine, String softwareOrigine, String idOperazione, String token, String consoleRemotaURL) {

	super();
	this.aliasOrigine = aliasOrigine;
	this.softwareOrigine = softwareOrigine;
	this.idOperazione = idOperazione;
	this.token = token;
	this.mapper = new ObjectMapper();
	this.consoleRemotaURL = consoleRemotaURL;
    }

    private static final Logger log = LoggerFactory.getLogger(ConsoleRestClientService.class);

    private String getAPIConsoleRemotaWsUrl() {

	// String url = "http://212.104.15.38:8080/backoffice-utils/services/rest";
	if (StringUtils.isBlank(this.consoleRemotaURL)) {
	    throw new RuntimeException("Il parametro consoleRemotaURL non è stato settato correttamente");
	}
	return this.consoleRemotaURL;
    }

    public static void main(String[] args) throws Exception {

	ConsoleRestClientService c = new ConsoleRestClientService("CONSOLE", "SS", "AAAA", String.valueOf(System.currentTimeMillis()),
		"http://212.104.15.38:8080/backoffice-utils/services/rest");
	Set<Integer> s = new HashSet<Integer>();
	s.add(91000168);
	s.add(91000165);
	s.add(91013225);
	s.add(91000157);
	s.add(91000156);
	s.add(91000162);
	s.add(91013226);
	s.add(91000004);
	System.out.println(c.getListaAmministrazioni(s));
	c.getTipicausaliOneri();
    }

    private String getAmministrazioniLegacy(String scCodice) throws Exception {

	String url = getAPIConsoleRemotaWsUrl() + "/configurazioni";
	try {
	    WebClient client = WebClient.create(url).path("token/{token}/{aliasOrigine}/amministrazioni", token, aliasOrigine);
	    if (StringUtils.isNotBlank(scCodice)) {
		client.query("scCodice", scCodice);
	    }
	    if (StringUtils.isNotBlank(softwareOrigine)) {
		client.query("softwareOrigine", softwareOrigine);
	    }
	    // connection timeout
	    HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	    conduit.getClient().setConnectionTimeout(600000);
	    conduit.getClient().setReceiveTimeout(600000);
	    client.accept(MediaType.TEXT_PLAIN);
	    Response response = client.get();
	    InputStream is = ((InputStream) response.getEntity());
	    String s = IOUtils.toString(is, "UTF-8");
	    return s;
	} catch (IOException ioex) {
	    log.error("{}", ioex);
	} finally {
	}
	return null;
    }

    public String getAmministrazioni(String scCodice) throws Exception {

	String url = getAPIConsoleRemotaWsUrl() + "/console";
	try {
	    WebClient client = WebClient.create(url).path("{aliasOrigine}/amministrazioni", aliasOrigine);
	    if (StringUtils.isNotBlank(scCodice)) {
		client.query("scCodice", scCodice);
	    }
	    if (StringUtils.isNotBlank(softwareOrigine)) {
		client.query("softwareOrigine", softwareOrigine);
	    }
	    // connection timeout
	    HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	    conduit.getClient().setConnectionTimeout(600000);
	    conduit.getClient().setReceiveTimeout(600000);
	    client.accept(MediaType.TEXT_PLAIN);
	    Response response = client.get();
	    InputStream is = ((InputStream) response.getEntity());
	    String s = IOUtils.toString(is, "UTF-8");
	    return s;
	} catch (IOException ioex) {
	    log.error("{}", ioex);
	} finally {
	}
	return null;
    }

    public List<AmministrazioniBean> getListaAmministrazioni(Set<Integer> codiciAmministrazioni) throws Exception {

	String url = getAPIConsoleRemotaWsUrl() + "/console";
	List<AmministrazioniBean> result = new ArrayList<AmministrazioniBean>();
	try {
	    WebClient client = WebClient.create(url).path("{aliasOrigine}/lista-amministrazioni", aliasOrigine);
	    if (StringUtils.isNotBlank(softwareOrigine)) {
		client.query("softwareOrigine", softwareOrigine);
	    }
	    if (codiciAmministrazioni != null && codiciAmministrazioni.size() > 0) {
		List<Integer> l = new ArrayList<Integer>(codiciAmministrazioni.size());
		l.addAll(codiciAmministrazioni);
		client.query("codiciAmministrazioni", codiciAmministrazioni);
	    }
	    // connection timeout
	    HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	    conduit.getClient().setConnectionTimeout(600000);
	    conduit.getClient().setReceiveTimeout(600000);
	    client.accept(MediaType.APPLICATION_JSON);
	    Response response = client.get();
	    InputStream is = ((InputStream) response.getEntity());
	    result = (List<AmministrazioniBean>) mapper.readValue(is,
		    TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, AmministrazioniBean.class));
	    return result;
	} catch (IOException ioex) {
	    log.error("{}", ioex);
	} finally {
	}
	return null;
    }

    public List<TipiCausaliOneriBean> getTipicausaliOneri() {

	List<TipiCausaliOneriBean> result = new ArrayList<TipiCausaliOneriBean>();
	String url = getAPIConsoleRemotaWsUrl() + "/console";
	try {
	    WebClient client = WebClient.create(url).path("{aliasOrigine}/{softwareOrigine}/tipicausalioneri", aliasOrigine, softwareOrigine);
	    HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	    conduit.getClient().setConnectionTimeout(600000);
	    conduit.getClient().setReceiveTimeout(600000);
	    client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
	    Response response = client.get();
	    InputStream is = ((InputStream) response.getEntity());
	    // String jsonInString = IOUtils.toString(is, "UTF-8");
	    result = (List<TipiCausaliOneriBean>) mapper.readValue(is,
		    TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, TipiCausaliOneriBean.class));
	    return result;
	} catch (IOException ioex) {
	    log.error("{}", ioex);
	} finally {
	}
	return result;
    }

    public List<TipiSoggettoBean> getTipisoggetto() {

	List<TipiSoggettoBean> result = new ArrayList<TipiSoggettoBean>();
	String url = getAPIConsoleRemotaWsUrl() + "/console";
	try {
	    WebClient client = WebClient.create(url).path("{aliasOrigine}/{softwareOrigine}/tipisoggetto", aliasOrigine, softwareOrigine);
	    HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	    conduit.getClient().setConnectionTimeout(600000);
	    conduit.getClient().setReceiveTimeout(600000);
	    client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
	    Response response = client.get();
	    InputStream is = ((InputStream) response.getEntity());
	    // String jsonInString = IOUtils.toString(is, "UTF-8");
	    result = (List<TipiSoggettoBean>) mapper.readValue(is,
		    TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, TipiSoggettoBean.class));
	    return result;
	} catch (IOException ioex) {
	    log.error("{}", ioex);
	} finally {
	}
	return result;
    }

    public List<TipiFamiglieEndoBean> getListaTipifamiglieEndo(Set<Integer> codiciFamiglieEndo) {

	String url = getAPIConsoleRemotaWsUrl() + "/console";
	List<TipiFamiglieEndoBean> result = new ArrayList<TipiFamiglieEndoBean>();
	try {
	    WebClient client = WebClient.create(url).path("{aliasOrigine}/lista-tipi-famiglie-endo", aliasOrigine);
	    if (StringUtils.isNotBlank(softwareOrigine)) {
		client.query("softwareOrigine", softwareOrigine);
	    }
	    if (codiciFamiglieEndo != null && codiciFamiglieEndo.size() > 0) {
		List<Integer> l = new ArrayList<Integer>(codiciFamiglieEndo.size());
		l.addAll(codiciFamiglieEndo);
		client.query("codiciFamiglieEndo", codiciFamiglieEndo);
	    }
	    // connection timeout
	    HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	    conduit.getClient().setConnectionTimeout(600000);
	    conduit.getClient().setReceiveTimeout(600000);
	    client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
	    Response response = client.get();
	    InputStream is = ((InputStream) response.getEntity());
	    result = (List<TipiFamiglieEndoBean>) mapper.readValue(is,
		    TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, TipiFamiglieEndoBean.class));
	    return result;
	} catch (IOException ioex) {
	    log.error("{}", ioex);
	} finally {
	}
	return null;
    }

    public List<TipiEndoBean> getListaTipiEndo(Set<Integer> codiciTipiEndo) {

	String url = getAPIConsoleRemotaWsUrl() + "/console";
	List<TipiEndoBean> result = new ArrayList<TipiEndoBean>();
	try {
	    WebClient client = WebClient.create(url).path("{aliasOrigine}/lista-tipi-endo", aliasOrigine);
	    if (StringUtils.isNotBlank(softwareOrigine)) {
		client.query("softwareOrigine", softwareOrigine);
	    }
	    if (codiciTipiEndo != null && codiciTipiEndo.size() > 0) {
		List<Integer> l = new ArrayList<Integer>(codiciTipiEndo.size());
		l.addAll(codiciTipiEndo);
		client.query("codiciTipiEndo", codiciTipiEndo);
	    }
	    // connection timeout
	    HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	    conduit.getClient().setConnectionTimeout(600000);
	    conduit.getClient().setReceiveTimeout(600000);
	    client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
	    Response response = client.get();
	    InputStream is = ((InputStream) response.getEntity());
	    result = (List<TipiEndoBean>) mapper.readValue(is,
		    TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, TipiEndoBean.class));
	    return result;
	} catch (IOException ioex) {
	    log.error("{}", ioex);
	} finally {
	}
	return null;
    }

    public List<InventarioprocedimentoBean> getListaEndoprodicedimenti(Set<Integer> codiciEndoProcedimenti) {

	String url = getAPIConsoleRemotaWsUrl() + "/console";
	List<InventarioprocedimentoBean> result = new ArrayList<InventarioprocedimentoBean>();
	try {
	    WebClient client = WebClient.create(url).path("{aliasOrigine}/lista-endo-procedimenti", aliasOrigine);
	    if (StringUtils.isNotBlank(softwareOrigine)) {
		client.query("softwareOrigine", softwareOrigine);
	    }
	    if (codiciEndoProcedimenti != null && codiciEndoProcedimenti.size() > 0) {
		List<Integer> l = new ArrayList<Integer>(codiciEndoProcedimenti.size());
		l.addAll(codiciEndoProcedimenti);
		client.query("codiciEndoProcedimenti", codiciEndoProcedimenti);
	    }
	    // connection timeout
	    HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	    conduit.getClient().setConnectionTimeout(600000);
	    conduit.getClient().setReceiveTimeout(600000);
	    client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
	    Response response = client.get();
	    InputStream is = ((InputStream) response.getEntity());
	    result = (List<InventarioprocedimentoBean>) mapper.readValue(is,
		    TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, InventarioprocedimentoBean.class));
	    return result;
	} catch (IOException ioex) {
	    log.error("{}", ioex);
	} finally {
	}
	return null;
    }

    public InformazioniAlberoInterventiBean getInformazioniAlberoInterventi(String scCodice, Boolean escludiDisabilitati) {

	String url = getAPIConsoleRemotaWsUrl() + "/console";
	InformazioniAlberoInterventiBean result = null;
	try {
	    WebClient client = WebClient.create(url).path("{aliasOrigine}/informazioni-albero-interventi", aliasOrigine);
	    if (StringUtils.isNotBlank(softwareOrigine)) {
		client.query("softwareOrigine", softwareOrigine);
	    }
	    // connection timeout
	    HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	    conduit.getClient().setConnectionTimeout(600000);
	    conduit.getClient().setReceiveTimeout(600000);
	    client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
	    Response response = client.get();
	    InputStream is = ((InputStream) response.getEntity());
	    result = (InformazioniAlberoInterventiBean) mapper.readValue(is, InformazioniAlberoInterventiBean.class);
	    return result;
	} catch (IOException ioex) {
	    log.error("{}", ioex);
	} finally {
	}
	return null;
    }

    public ListaSchedeDinamicheBean getListaSchedeDinamiche(Set<Integer> codiciSchedeEndo) {

	String url = getAPIConsoleRemotaWsUrl() + "/console";
	ListaSchedeDinamicheBean result = null;
	try {
	    WebClient client = WebClient.create(url).path("{aliasOrigine}/lista-schede-dinamiche", aliasOrigine);
	    if (StringUtils.isNotBlank(softwareOrigine)) {
		client.query("softwareOrigine", softwareOrigine);
	    }
	    if (StringUtils.isNotBlank(softwareOrigine)) {
		client.query("codiciSchedeEndo", codiciSchedeEndo);
	    }
	    // connection timeout
	    HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	    conduit.getClient().setConnectionTimeout(600000);
	    conduit.getClient().setReceiveTimeout(600000);
	    client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
	    Response response = client.get();
	    InputStream is = ((InputStream) response.getEntity());
	    result = (ListaSchedeDinamicheBean) mapper.readValue(is, ListaSchedeDinamicheBean.class);
	    return result;
	} catch (IOException ioex) {
	    log.error("{}", ioex);
	} finally {
	}
	return null;
    }

    public List<CampiDinamiciBean> getCampiDinamici(Set<Integer> codiciCampi) {

	String url = getAPIConsoleRemotaWsUrl() + "/console";
	List<CampiDinamiciBean> result = null;
	try {
	    WebClient client = WebClient.create(url).path("{aliasOrigine}/lista-campi-dinamici", aliasOrigine);
	    if (StringUtils.isNotBlank(softwareOrigine)) {
		client.query("softwareOrigine", softwareOrigine);
	    }
	    if (StringUtils.isNotBlank(softwareOrigine)) {
		client.query("codiciCampi", codiciCampi);
	    }
	    // connection timeout
	    HTTPConduit conduit = WebClient.getConfig(client).getHttpConduit();
	    conduit.getClient().setConnectionTimeout(600000);
	    conduit.getClient().setReceiveTimeout(600000);
	    client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
	    Response response = client.get();
	    InputStream is = ((InputStream) response.getEntity());
	    result = (List<CampiDinamiciBean>) mapper.readValue(is,
		    TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, CampiDinamiciBean.class));
	    return result;
	} catch (IOException ioex) {
	    log.error("{}", ioex);
	} finally {
	}
	return null;
    }
}
