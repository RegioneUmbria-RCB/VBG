package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class InsertMetadatoResponse {

    @XmlElement(name = "id")
    public Integer id;
    @XmlElement(name = "esito")
    private MetadatoResponse esito;

    public InsertMetadatoResponse() {

    }

    public InsertMetadatoResponse(Integer id) {

	this.id = id;
	this.esito = MetadatoResponse.OK();
    }

    public InsertMetadatoResponse(Exception ex) {

	this.esito = MetadatoResponse.KO(ex.getMessage());
    }
}
