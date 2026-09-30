package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "")
public class ConfigurazioneParametroWs {

    @JsonProperty("chiave")
    private String chiave;
    @JsonProperty("descrizione")
    private String descrizione;
    @JsonProperty("valore")
    private String valore;
    @JsonProperty("obbligatorio")
    private Boolean obbligatorio;

    public ConfigurazioneParametroWs() {

    }

    public ConfigurazioneParametroWs(String chiave, String descrizione, String valore, boolean obbligatorio) {

	this.chiave = chiave;
	this.descrizione = descrizione;
	this.valore = valore;
	this.obbligatorio = obbligatorio;
    }

    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    public Boolean isObbligatorio() {

	return obbligatorio;
    }

    public void setObbligatorio(Boolean obbligatorio) {

	this.obbligatorio = obbligatorio;
    }
}
