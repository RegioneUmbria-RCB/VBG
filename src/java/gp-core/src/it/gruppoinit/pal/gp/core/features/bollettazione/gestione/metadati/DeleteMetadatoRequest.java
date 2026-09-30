package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteMetadatoRequest {

    @XmlElement(name = "id")
    private Integer id;

    public DeleteMetadatoRequest() {

    }

    public DeleteMetadatoRequest(Integer id) {

	this.id = id;
    }

    public Integer getId() {

	return id;
    }
}
