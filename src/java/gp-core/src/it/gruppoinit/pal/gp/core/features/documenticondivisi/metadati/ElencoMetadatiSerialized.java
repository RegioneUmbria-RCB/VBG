package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati;

import java.util.LinkedHashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.marshalling.MapAdapter;

@XmlRootElement(name = "metadati_file")
public class ElencoMetadatiSerialized {

    public ElencoMetadatiSerialized() {

    }

    public ElencoMetadatiSerialized(ElencoMetadati elenco) {

	for (DocumentiCondivisiMetadato metadato : elenco.getMetadati()) {
	    if (metadato != null) {
		this.addProperty(metadato.getChiave(), metadato.getValore());
	    }
	}
    }

    LinkedHashMap<String, String> properties = new LinkedHashMap<String, String>();

    @XmlElement(name = "elenco_metadati")
    @XmlJavaTypeAdapter(MapAdapter.class)
    public LinkedHashMap<String, String> getProperties() {

	return properties;
    }

    public void addProperty(String name, String value) {

	this.properties.put(name, value);
    }
}