package it.gruppoinit.pal.gp.core.features.integrazioni.cartografico.client;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoJSONBean {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("geometry")
    private GeometryBean geometry;
    @JsonProperty("properties")
    private Map<String, Object> properties;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getType() {

	return type;
    }

    public void setType(String type) {

	this.type = type;
    }

    public GeometryBean getGeometry() {

	return geometry;
    }

    public void setGeometry(GeometryBean geometry) {

	this.geometry = geometry;
    }

    public Map<String, Object> getProperties() {

	return properties;
    }

    public void setProperties(Map<String, Object> properties) {

	this.properties = properties;
    }
}
