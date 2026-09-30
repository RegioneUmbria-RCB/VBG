package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CallbackBean {

    @JsonProperty("callbackURL")
    private String callbackURL;
    @JsonProperty("cancelURL")
    private String cancelURL;

    public String getCallbackURL() {

	return callbackURL;
    }

    public void setCallbackURL(String callbackURL) {

	this.callbackURL = callbackURL;
    }

    public String getCancelURL() {

	return cancelURL;
    }

    public void setCancelURL(String cancelURL) {

	this.cancelURL = cancelURL;
    }
}
