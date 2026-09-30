package it.gruppoinit.pal.gp.pay.connector.govpay.rest.client;

import java.util.Map;

import it.gruppoinit.pal.gp.pay.domain.PayConnectorConfigParams.ConfigParamNames;

public class GovPayClientParameters {

    private String urlApiProfilo;
    private String urlApiPendenze;
    private String urlApiPagamenti;
    private String baseUrl;
    private BasicAuthParams basicAuthParams;
    private SSLClientParameters sslClientParameters;
    private Map<ConfigParamNames, String> connectorConfigParams;

    public GovPayClientParameters(String baseUrl, String urlApiProfilo, String urlApiPendenze, String urlApiPagamenti,
	    BasicAuthParams basicAuthParams, SSLClientParameters sslClientParameters, Map<ConfigParamNames, String> connectorConfigParams) {

	super();
	this.urlApiProfilo = urlApiProfilo;
	this.urlApiPendenze = urlApiPendenze;
	this.urlApiPagamenti = urlApiPagamenti;
	this.baseUrl = baseUrl;
	this.basicAuthParams = basicAuthParams;
	this.sslClientParameters = sslClientParameters;
	this.connectorConfigParams = connectorConfigParams;
    }

    public String getUrlApiProfilo() {

	return urlApiProfilo;
    }

    public String getUrlApiPendenze() {

	return urlApiPendenze;
    }

    public String getUrlApiPagamenti() {

	return urlApiPagamenti;
    }

    public String getBaseUrl() {

	return baseUrl;
    }

    public BasicAuthParams getBasicAuthParams() {

	return basicAuthParams;
    }

    public SSLClientParameters getSslClientParameters() {

	return sslClientParameters;
    }

    public Map<ConfigParamNames, String> getConnectorConfigParams() {

	return connectorConfigParams;
    }
}
