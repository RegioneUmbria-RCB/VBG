package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "")
public class LocalizzazioneBean {

    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("identificativo")
    private String identificativo;
    @JsonProperty("codViario")
    private String codViario;
    @JsonProperty("descrizione")
    private String descrizione;
    @JsonProperty("civico")
    private String civico;
    @JsonProperty("km")
    private String km;
    @JsonProperty("latitudine")
    private String latitudine;
    @JsonProperty("longitudine")
    private String longitudine;

    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    public String getIdentificativo() {

	return identificativo;
    }

    public void setIdentificativo(String identificativo) {

	this.identificativo = identificativo;
    }

    public String getCodViario() {

	return codViario;
    }

    public void setCodViario(String codViario) {

	this.codViario = codViario;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getCivico() {

	return civico;
    }

    public void setCivico(String civico) {

	this.civico = civico;
    }

    public String getKm() {

	return km;
    }

    public void setKm(String km) {

	this.km = km;
    }

    public String getLatitudine() {

	return latitudine;
    }

    public void setLatitudine(String latitudine) {

	this.latitudine = latitudine;
    }

    public String getLongitudine() {

	return longitudine;
    }

    public void setLongitudine(String longitudine) {

	this.longitudine = longitudine;
    }
}
