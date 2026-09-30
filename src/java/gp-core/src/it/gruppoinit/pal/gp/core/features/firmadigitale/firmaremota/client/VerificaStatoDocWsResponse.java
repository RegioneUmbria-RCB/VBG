package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VerificaStatoDocWsResponse {

    @JsonProperty("caricati")
    private Integer caricati;
    @JsonProperty("firmati")
    private Integer firmati;

    public Integer getCaricati() {

	return caricati;
    }

    public void setCaricati(Integer caricati) {

	this.caricati = caricati;
    }

    public Integer getFirmati() {

	return firmati;
    }

    public void setFirmati(Integer firmati) {

	this.firmati = firmati;
    }
}
