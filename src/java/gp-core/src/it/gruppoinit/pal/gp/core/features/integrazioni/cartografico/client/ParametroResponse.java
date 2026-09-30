package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParametroResponse {

    @JsonProperty("geojson")
    private GeoJSONBean geojson;
    @JsonProperty("additionalInfo")
    private List<AdditionalInfoBean> additionalInfo;
    @JsonProperty("latitudine")
    private BigDecimal latitudine;
    @JsonProperty("longitudine")
    private BigDecimal longitudine;
    @JsonProperty("codicestradario")
    private String codicestradario;
    @JsonProperty("stradario")
    private String stradario;
    @JsonProperty("km")
    private String km;
    @JsonProperty("uuid")
    private String uuid;

    public GeoJSONBean getGeojson() {

	return geojson;
    }

    public void setGeojson(GeoJSONBean geojson) {

	this.geojson = geojson;
    }

    public List<AdditionalInfoBean> getAdditionalInfo() {

	return additionalInfo;
    }

    public void setAdditionalInfo(List<AdditionalInfoBean> additionalInfo) {

	this.additionalInfo = additionalInfo;
    }

    public BigDecimal getLatitudine() {

	return latitudine;
    }

    public void setLatitudine(BigDecimal latitudine) {

	this.latitudine = latitudine;
    }

    public BigDecimal getLongitudine() {

	return longitudine;
    }

    public void setLongitudine(BigDecimal longitudine) {

	this.longitudine = longitudine;
    }

    public String getCodicestradario() {

	return codicestradario;
    }

    public void setCodicestradario(String codicestradario) {

	this.codicestradario = codicestradario;
    }

    public String getStradario() {

	return stradario;
    }

    public void setStradario(String stradario) {

	this.stradario = stradario;
    }

    public String getKm() {

	return km;
    }

    public void setKm(String km) {

	this.km = km;
    }

    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    @JsonIgnore
    public String getAdditionalPropertyValue(String propertyName) {

	if (this.getAdditionalInfo() == null || this.getAdditionalInfo().isEmpty()) {
	    return null;
	}
	for (AdditionalInfoBean property : this.getAdditionalInfo()) {
	    if (property.getChiave().equalsIgnoreCase(propertyName)) {
		return property.getValore();
	    }
	}
	return null;
    }
}
