package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EsitoBean {

    @JsonProperty("esito")
    private EsitoEnum esito;
    @JsonProperty("exceptions")
    private List<String> exceptions;

    public EsitoEnum getEsito() {

	return esito;
    }

    public void setEsito(EsitoEnum esito) {

	this.esito = esito;
    }

    public List<String> getExceptions() {

	return exceptions;
    }

    public void setExceptions(List<String> exceptions) {

	this.exceptions = exceptions;
    }
}
