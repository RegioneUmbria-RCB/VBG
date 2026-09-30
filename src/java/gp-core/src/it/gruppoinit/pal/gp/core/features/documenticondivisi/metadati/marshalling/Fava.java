package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.marshalling;

import java.util.LinkedHashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class Fava {

    LinkedHashMap<String, String> properties = new LinkedHashMap<String, String>();

    @XmlElement(name = "lista-fava")
    @XmlJavaTypeAdapter(MapAdapter.class)
    public LinkedHashMap<String, String> getProperties() {

	return properties;
    }

    public void addProperty(String name, String value) {

	this.properties.put(name, value);
    }
}
