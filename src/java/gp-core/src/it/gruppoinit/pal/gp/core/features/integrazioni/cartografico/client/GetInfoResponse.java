package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "")
public class GetInfoResponse {

    @JsonProperty("connettore")
    private String connettore;
    @JsonProperty("utilizzo")
    private Utilizzo utilizzo;

    public GetInfoResponse() {

	this.utilizzo = new Utilizzo();
    }

    public String getConnettore() {

	return connettore;
    }

    public void setConnettore(String connettore) {

	this.connettore = connettore;
    }

    public Utilizzo getUtilizzo() {

	return utilizzo;
    }

    public void setUtilizzo(Utilizzo utilizzo) {

	this.utilizzo = utilizzo;
    }

    public static GetInfoResponse NonInstallato() {

	GetInfoResponse response = new GetInfoResponse();
	return response;
    }
}
