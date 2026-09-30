package it.gruppoinit.pal.gp.pay.connector.govpay.rest.client;

public class SSLClientParameters {

    private String certAlias;
    private String trustStoreLocation;
    private String trustStorePassword;
    private String keyStoreLocation;
    private String keyStorePassword;

    public SSLClientParameters(String certAlias, String trustStoreLocation, String trustStorePassword, String keyStoreLocation,
	    String keyStorePassword) {

	super();
	this.certAlias = certAlias;
	this.trustStoreLocation = trustStoreLocation;
	this.trustStorePassword = trustStorePassword;
	this.keyStoreLocation = keyStoreLocation;
	this.keyStorePassword = keyStorePassword;
    }

    public String getCertAlias() {

	return certAlias;
    }

    public String getTrustStoreLocation() {

	return trustStoreLocation;
    }

    public String getTrustStorePassword() {

	return trustStorePassword;
    }

    public String getKeyStoreLocation() {

	return keyStoreLocation;
    }

    public String getKeyStorePassword() {

	return keyStorePassword;
    }
}
