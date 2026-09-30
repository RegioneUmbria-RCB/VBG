package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdditionalInfoBean {

    @JsonProperty("chiave")
    private String chiave;
    @JsonProperty("valore")
    private String valore;

    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
