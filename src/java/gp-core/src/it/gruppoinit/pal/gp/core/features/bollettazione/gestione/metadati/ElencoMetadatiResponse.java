package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dettaglio")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElencoMetadatiResponse {

    @XmlElement(name = "metadati")
    List<MetadatoBollettazione> metadati;

    public ElencoMetadatiResponse() {

	this.metadati = new ArrayList<MetadatoBollettazione>(0);
    }

    public ElencoMetadatiResponse(List<MetadatoBollettazione> metadati) {

	this.metadati = metadati;
    }
}
