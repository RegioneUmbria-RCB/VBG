package it.gruppoinit.pal.gp.core.features.documenticondivisi.metadati.marshalling;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;

public class MapWrapper {

    @XmlAnyElement
    public List<JAXBElement<String>> properties = new ArrayList<JAXBElement<String>>();
}