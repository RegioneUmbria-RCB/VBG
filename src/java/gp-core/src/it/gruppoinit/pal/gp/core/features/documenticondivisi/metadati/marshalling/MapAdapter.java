package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.marshalling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.namespace.QName;

public class MapAdapter extends XmlAdapter<MapWrapper, Map<String, String>> {

    @Override
    public MapWrapper marshal(Map<String, String> m) throws Exception {

	MapWrapper wrapper = new MapWrapper();
	List<JAXBElement<String>> elements = new ArrayList<JAXBElement<String>>();
	for (Map.Entry<String, String> property : m.entrySet()) {
	    elements.add(new JAXBElement<String>(new QName(property.getKey().replace("-", "_")), String.class, property.getValue()));
	}
	wrapper.properties = elements;
	return wrapper;
    }

    @Override
    public Map<String, String> unmarshal(MapWrapper v) throws Exception {

	// TODO
	throw new OperationNotSupportedException();
    }
}
