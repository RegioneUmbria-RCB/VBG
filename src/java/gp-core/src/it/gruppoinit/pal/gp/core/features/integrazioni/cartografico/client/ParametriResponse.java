package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "")
public class ParametriResponse {

    @JsonProperty("parametri")
    private List<ParametroResponse> parametri;
    @JsonProperty("esito")
    private EsitoBean esito;

    public List<ParametroResponse> getParametri() {

	return parametri;
    }

    public void setParametri(List<ParametroResponse> parametri) {

	this.parametri = parametri;
    }

    public EsitoBean getEsito() {

	return esito;
    }

    public void setEsito(EsitoBean esito) {

	this.esito = esito;
    }
}
