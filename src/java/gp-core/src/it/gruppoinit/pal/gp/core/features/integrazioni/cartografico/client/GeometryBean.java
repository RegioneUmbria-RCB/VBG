package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeometryBean {

    @JsonProperty("type")
    private String type;
    @JsonProperty("coordinates")
    private List<List<BigDecimal>> coordinates;

    public String getType() {

	return type;
    }

    public void setType(String type) {

	this.type = type;
    }

    public List<List<BigDecimal>> getCoordinates() {

	return coordinates;
    }

    public void setCoordinates(List<List<BigDecimal>> coordinates) {

	this.coordinates = coordinates;
    }
}
