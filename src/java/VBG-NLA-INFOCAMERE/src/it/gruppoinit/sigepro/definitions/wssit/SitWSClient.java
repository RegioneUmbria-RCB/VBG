package it.gruppoinit.sigepro.definitions.wssit;

import it.gruppoinit.sigepro.definitions.regole.RegoleWSClient;
import it.gruppoinit.wssit.Sit;
import it.gruppoinit.wssit.ValidateSit;
import it.gruppoinit.wssit.WsSit;
import it.gruppoinit.wssit.WsSitSoap;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SitWSClient {

    private static final Logger log = LoggerFactory.getLogger(RegoleWSClient.class);
    private String sitWsHostURL;

    public void setSitWsHostURL(String sitWsHostURL) {

	this.sitWsHostURL = sitWsHostURL;
    }

    public List<String> getListaZone(String codiceVia, String codiceCivico, String letteraEsponente, String letteraColore, String token,
	    String software) throws Exception {

	List<String> list = new ArrayList<String>();
	try {
	    WsSitSoap port = getPort();
	    if (StringUtils.isBlank(software)) {
		software = null;
	    }
	    log.debug("getListaZone# Ricerca zona. codice starada = {}, civico = {}, esponente = {}, colore = {}, software = {}", codiceVia,
		    codiceCivico, letteraEsponente, letteraColore, software);
	    Sit dataSit = new Sit();
	    dataSit.setCodVia(codiceVia);
	    dataSit.setCivico(codiceCivico);
	    dataSit.setEsponente(letteraEsponente);
	    dataSit.setColore(letteraColore);
	    ValidateSit v = port.validateField(token, "civico", dataSit, software);
	    String _zone = v.getDataSit().getOggettoTerritoriale();
	    log.debug("getListaZone# Zone = {}", _zone);
	    if (StringUtils.isNotBlank(_zone)) {
		String[] zoneArray = StringUtils.split(_zone, ",");
		list = Arrays.asList(zoneArray);
	    }
	    return list;
	} catch (Exception e) {
	    log.error("getListaZone", e);
	    throw e;
	}
    }

    private WsSitSoap getPort() throws Exception {

	try {
	    WsSit ss = new WsSit(new URL(sitWsHostURL));
	    WsSitSoap port = ss.getWsSitSoap();
	    return port;
	} catch (Exception e) {
	    log.error("getPort", e);
	    throw e;
	}
    }
}
