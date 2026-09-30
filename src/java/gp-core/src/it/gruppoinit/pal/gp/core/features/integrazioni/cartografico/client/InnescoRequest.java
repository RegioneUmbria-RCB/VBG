package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "")
public class InnescoRequest {

    @JsonProperty("chiamante")
    private String chiamante;
    @JsonProperty("utilizzo")
    private String utilizzo;
    @JsonProperty("comune")
    private ComuneBean comune;
    @JsonProperty("callback")
    private CallbackBean callback;
    @JsonProperty("localizzazioni")
    private List<LocalizzazioneBean> localizzazioni;

    public String getChiamante() {

	return chiamante;
    }

    public void setChiamante(String chiamante) {

	this.chiamante = chiamante;
    }

    public String getUtilizzo() {

	return utilizzo;
    }

    public void setUtilizzo(String utilizzo) {

	this.utilizzo = utilizzo;
    }

    public ComuneBean getComune() {

	return comune;
    }

    public void setComune(ComuneBean comune) {

	this.comune = comune;
    }

    public CallbackBean getCallback() {

	return callback;
    }

    public void setCallback(CallbackBean callback) {

	this.callback = callback;
    }

    public List<LocalizzazioneBean> getLocalizzazioni() {

	return localizzazioni;
    }

    public void setLocalizzazioni(List<LocalizzazioneBean> localizzazioni) {

	this.localizzazioni = localizzazioni;
    }
}
