package it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client;

public class FvgPayClientParams {

    private String baseUrl;
    private BasicAuthParams basicAuthParams;
    private String idEnte;
    private String idServizio;

    public FvgPayClientParams(String baseUrl, BasicAuthParams basicAuthParams, String idEnte, String idServizio) {

	super();
	this.baseUrl = baseUrl;
	this.basicAuthParams = basicAuthParams;
	this.idEnte = idEnte;
	this.idServizio = idServizio;
    }

    public String getBaseUrl() {

	return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {

	this.baseUrl = baseUrl;
    }

    public String getIdEnte() {

	return idEnte;
    }

    public void setIdEnte(String idEnte) {

	this.idEnte = idEnte;
    }

    public String getIdServizio() {

	return idServizio;
    }

    public void setIdServizio(String idServizio) {

	this.idServizio = idServizio;
    }

    public BasicAuthParams getBasicAuthParams() {

	return basicAuthParams;
    }

    public void setBasicAuthParams(BasicAuthParams basicAuthParams) {

	this.basicAuthParams = basicAuthParams;
    }
}
