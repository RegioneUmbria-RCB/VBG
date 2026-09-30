package it.gruppoinit.pal.gp.pay.connector.govpay.rest.client;

public class BasicAuthParams {

    public BasicAuthParams(String password, String utente) {

	super();
	this.password = password;
	this.utente = utente;
    }

    private String password;
    private String utente;

    public String getPassword() {

	return password;
    }

    public String getUtente() {

	return utente;
    }
}
