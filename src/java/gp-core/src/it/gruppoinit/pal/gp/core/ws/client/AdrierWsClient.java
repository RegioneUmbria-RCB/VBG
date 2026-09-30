package it.gruppoinit.pal.gp.core.ws.client;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.lepida.adrigate.RicercaImpreseService;
import it.lepida.adrigate.RicercaImpreseServiceLocator;
import it.lepida.adrigate.RicercaImpreseSoapBindingStub;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AdrierWsClient {

    private static final Logger log = LoggerFactory.getLogger(AdrierWsClient.class);

    public AdrierWsClient() {

	super();
    }

    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    public String getDettaglioImpresa(String provinciaRea, String numeroRea) throws RemoteException, MalformedURLException, ServiceException {

	if (log.isDebugEnabled()) {
	    log.debug("getDettaglioImpresa# creo la porta.....");
	}
	RicercaImpreseSoapBindingStub port = getWsPort();
	if (log.isDebugEnabled()) {
	    log.debug("getDettaglioImpresa# porta creata effettuo la chiamata con ProvRea: {}, NrRea: {}", provinciaRea, numeroRea);
	}
	String result = port.dettaglioCompletoImpresa(provinciaRea, numeroRea, getWsSwitchControl(), getWsUsername(), getWsPassword());
	if (log.isDebugEnabled()) {
	    log.debug("getDettaglioImpresa# result: {}", result);
	}
	return result;
    }

    public String getRicercaImpreseNoncessateByCodiceFiscale(String codiceFiscale) throws RemoteException, MalformedURLException, ServiceException {

	if (log.isDebugEnabled()) {
	    log.debug("getRicercaImpreseNoncessateByCodiceFiscale# creo la porta");
	}
	RicercaImpreseSoapBindingStub port = getWsPort();
	if (log.isDebugEnabled()) {
	    log.debug("getRicercaImpreseNoncessateByCodiceFiscale# porta creata effettuo la chiamata con codiceFiscale: {}, NrRea: {}", codiceFiscale);
	}
	String result = port.ricercaImpreseNonCessatePerCodiceFiscale(codiceFiscale, getWsSwitchControl(), getWsUsername(), getWsPassword());
	if (log.isDebugEnabled()) {
	    log.debug("getRicercaImpreseNoncessateByCodiceFiscale# result: {}", result);
	}
	return result;
    }

    private RicercaImpreseSoapBindingStub getWsPort() throws MalformedURLException, ServiceException {

	RicercaImpreseService service = new RicercaImpreseServiceLocator();
	log.debug("getWsPort# Istanzio la porta");
	RicercaImpreseSoapBindingStub port = (RicercaImpreseSoapBindingStub) service.getRicercaImprese(new URL(getWsUrl()));
	//	log.debug("getWsPort# recupero l'username dalla verticalizzazione....");
	//	String userWs = getWsUsername();
	//	log.debug("getWsPort# recupero password dalla verticalizzazione....");
	//	String passwordWs = getWsPassword();
	//	log.debug("getWsPort# setto username e password....");
	//	port.setPassword(userWs);
	//	port.setUsername(passwordWs);
	return port;
    }

    private String getParametroVerticalizzazione(String nomeParametro, boolean throwExceptionIfNull) {

	Verticalizzazioniparametri urlParix = verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_ADRIER, nomeParametro);
	if (urlParix != null) {
	    String result = StringUtils.defaultString(urlParix.getValore()).trim();
	    if (StringUtils.isNotBlank(result)) {
		return result;
	    }
	}
	if (throwExceptionIfNull) {
	    throw new RuntimeException("Il parametro " + nomeParametro + " della verticalizzazione "
		    + WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_ADRIER + " non è stato configurato correttamente");
	}
	return "";
    }

    private String getWsUrl() {

	return getParametroVerticalizzazione(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_ADRIER_URL, true);
    }

    private String getWsPassword() {

	return getParametroVerticalizzazione(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_ADRIER_PASSWORD, true);
    }

    private String getWsUsername() {

	return getParametroVerticalizzazione(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_ADRIER_USER, true);
    }

    private String getWsSwitchControl() {

	if (StringUtils.isBlank(getParametroVerticalizzazione(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_ADRIER_SWITCHCONTROL, false))) {
	    return getParametroVerticalizzazione(WebConstants.VERTICALIZZAZIONE_WSANAGRAFE_ADRIER_SWITCHCONTROL, false);
	}
	return "?";
    }
    //    private String getWsBasicAuthUsername() {
    //
    //	return getParametroVerticalizzazione("BASIC_AUTH_USER", false);
    //    }
    //
    //    private String getWsBasicAuthPassword() {
    //
    //	return getParametroVerticalizzazione("BASIC_AUTH_PASSWORD", false);
    //    }
    //
    //    private String getProxyAddress() {
    //
    //	return getParametroVerticalizzazione("PROXY_ADDRESS", false);
    //    }
}
