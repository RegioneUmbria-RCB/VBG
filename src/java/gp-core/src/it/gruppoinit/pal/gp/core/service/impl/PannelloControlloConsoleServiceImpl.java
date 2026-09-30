package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CartInfoDizionarioHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.EndoTipo1Helper;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.PannelloControlloConsoleService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.EndoTipo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PannelloControlloConsoleServiceImpl implements PannelloControlloConsoleService {

    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private AlberoprocService alberoprocService;

    @Override
    public CartInfoDizionarioHelper preElaboraMessaggio() {

	UUID idMessaggio = UUID.randomUUID();
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	CartInfoDizionarioHelper risultato = new CartInfoDizionarioHelper();
	String idEgov = idMessaggio.toString();
	risultato.setIdEgov(idEgov);
	List<EndoTipo1Helper> endoTipo1Helpers;
	try {
	    endoTipo1Helpers = prepareInserimentoInventarioprocedimento();
	} catch (IOException e) {
	    throw new RuntimeException();
	}
	risultato.setEndoTipo1Helpers(endoTipo1Helpers);
	return risultato;
    }

    private List<EndoTipo1Helper> prepareInserimentoInventarioprocedimento() throws IOException {

	// Definizioni variabili
	CartInfoDizionarioHelper cartInfoDizionarioHelper = new CartInfoDizionarioHelper();
	List<EndoTipo1> endoTipo1s = new ArrayList<EndoTipo1>();
	List<EndoTipo1Helper> endoTipo1Helpers = new ArrayList<EndoTipo1Helper>();
	// Scorro tutta la lista per separare i tre diversi tipi di oggetti presenti TipologiaEndoTipo1,CategoriaEndoTipo1,EndoTipo1
	List<CodiceDescrizioneBean> list = leggiAmministrazioni();
	for (CodiceDescrizioneBean cdb : list) {
	    EndoTipo1 e1 = new EndoTipo1();
	    e1.setEnteCompetente(cdb.getCodice());
	    endoTipo1s.add(e1);
	}
	//Creo la lista di EndoTipo1Helper utilizzato per la pre configurazione necessaria per l'elaborazione dei messaggi 
	List<String> amministrazioneTrovate = new ArrayList<String>();
	EndoTipo1Helper endoTipo1Helper = null;
	for (EndoTipo1 endoTipo1 : endoTipo1s) {
	    endoTipo1Helper = new EndoTipo1Helper();
	    String codiceAmministrazione = null;
	    if (StringUtils.isNotBlank(endoTipo1.getEnteCompetente())) {
		codiceAmministrazione = endoTipo1.getEnteCompetente();
	    }
	    if (!amministrazioneTrovate.contains(codiceAmministrazione)) {
		endoTipo1Helper.setCodiceAmministrazioneCart(codiceAmministrazione);
		endoTipo1Helpers.add(endoTipo1Helper);
		amministrazioneTrovate.add(codiceAmministrazione);
	    }
	}
	cartInfoDizionarioHelper.setEndoTipo1Helpers(endoTipo1Helpers);
	return endoTipo1Helpers;
    }

    private List<CodiceDescrizioneBean> leggiAmministrazioni() throws IOException {

	List<CodiceDescrizioneBean> result = new ArrayList<CodiceDescrizioneBean>();
	String url = getUrlAppAllineamento();
	String alias = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_CONSOLE,
		WebConstants.VERTICALIZZAZIONE_CONSOLE_ALIAS_DATI_CONSOLE);
	String scCodice = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_CONSOLE,
		WebConstants.VERTICALIZZAZIONE_CONSOLE_SC_CODICE_PARTENZA);
	String softwareOrigine = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_CONSOLE,
		WebConstants.VERTICALIZZAZIONE_CONSOLE_SOFTWARE_DATI_CONSOLE);
	HttpClient cli = new HttpClient();
	HttpMethod method = null;
	int executeMethod = 0;
	url = url + "/token/" + ORMHelper.getToken() + "/" + alias + "/amministrazioni";
	if (StringUtils.isNotBlank(scCodice)) {
	    url += "?scCodice=" + scCodice + "&softwareOrigine=" + softwareOrigine;
	}
	try {
	    method = new GetMethod(url);
	    executeMethod = cli.executeMethod(method);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	if (executeMethod != 200) {
	    throw new RuntimeException("Errore nell'esecuzione dell'aggiornamento");
	}
	InputStream inputStream = method.getResponseBodyAsStream();
	// Trasformo lo stream in una stringa (conterrà solo il codice)
	//InputStream inputStream = method.getResponseBodyAsStream();
	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	String line = null;
	while ((line = reader.readLine()) != null) {
	    String[] cd = line.split("\\|");
	    String codice = cd[0];
	    String descrizione = cd[1];
	    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
	    cdb.setCodice(codice);
	    cdb.setDescrizione(descrizione);
	    result.add(cdb);
	}
	inputStream.close();
	return result;
    }

    @Override
    public void elaboraMessaggio(CartInfoDizionarioHelper cartInfoDizionarioHelper) {

	try {
	    String url = getUrlAppAllineamento();
	    String alias = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_CONSOLE,
		    WebConstants.VERTICALIZZAZIONE_CONSOLE_ALIAS_DATI_CONSOLE);
	    String software = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_CONSOLE,
		    WebConstants.VERTICALIZZAZIONE_CONSOLE_SOFTWARE_DATI_CONSOLE);
	    String scCodice = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_CONSOLE,
		    WebConstants.VERTICALIZZAZIONE_CONSOLE_SC_CODICE_PARTENZA);
	    HttpClient cli = new HttpClient();
	    HttpClientParams params = new HttpClientParams();
	    params.setConnectionManagerTimeout(60000);
	    params.setSoTimeout(2400000);
	    cli.setParams(params);
	    HttpMethod method = null;
	    // token/{token}/{aliasOrigine}/{softwareOrigine}/{aliasDestinazione}/{softwareDestinazione}/copia/{idoperazione}
	    url = url + "/token/" + ORMHelper.getToken() + "/" + alias + "/" + software + "/" + ORMHelper.getIdcomuneAlias() + "/"
		    + ORMHelper.getSoftware() + "/copia/" + cartInfoDizionarioHelper.getIdEgov() + "/escludiDisabilitati/"
		    + cartInfoDizionarioHelper.isEscludiDisabilitati();
	    if (StringUtils.isNotBlank(scCodice)) {
		url += "?scCodice=" + scCodice;
	    }
	    method = new GetMethod(url);
	    int executeMethod = cli.executeMethod(method);
	    alberoprocService.updateAlberoprocCache();
	    InputStream inputStream = method.getResponseBodyAsStream();
	    // Trasformo lo stream in una stringa (conterrà solo il codice)
	    //InputStream inputStream = method.getResponseBodyAsStream();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	    String line = null;
	    StringBuffer sbf = new StringBuffer();
	    while ((line = reader.readLine()) != null) {
		//
		sbf.append(line);
		sbf.append("\n");
	    }
	    inputStream.close();
	    if (executeMethod != 200) {
		throw new RuntimeException("Errore nell'esecuzione dell'aggiornamento: " + sbf.toString());
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public String statoElaborazione(CartInfoDizionarioHelper cartInfoDizionarioHelper) {

	try {
	    String url = getUrlAppAllineamento();
	    HttpClient cli = new HttpClient();
	    HttpMethod method = null;
	    try {
		// token/{token}/verifica/{idoperazione}
		method = new GetMethod(url + "/token/verifica/" + cartInfoDizionarioHelper.getIdEgov());
		cli.executeMethod(method);
	    } catch (Exception e) {
		throw new RuntimeException(e);
	    }
	    InputStream inputStream = method.getResponseBodyAsStream();
	    // Trasformo lo stream in una stringa (conterrà solo il codice)
	    //InputStream inputStream = method.getResponseBodyAsStream();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	    String line = null;
	    String res = null;
	    while ((line = reader.readLine()) != null) {
		res += line;
	    }
	    inputStream.close();
	    return res;
	} catch (Exception e) {
	}
	return "";
    }

    private String getUrlAppAllineamento() {

	String url = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_CONSOLE,
		WebConstants.VERTICALIZZAZIONE_CONSOLE_URL_APP_ALLINEAMENTO);
	String isOffLine = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_CONSOLE,
		"SERVIZI_CONSOLE_REMOTA");
	if (StringUtils.defaultIfEmpty(isOffLine, "N").equalsIgnoreCase("S")) {
	    return url + "/configurazionioffline";
	}
	return url + "/configurazioni";
    }
}
