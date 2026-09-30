package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateMetadatoResponse {

    @XmlElement(name = "esito")
    private MetadatoResponse esito;

    public UpdateMetadatoResponse() {

	this.esito = MetadatoResponse.OK();
    }

    public UpdateMetadatoResponse(Exception ex) {

	this.esito = MetadatoResponse.KO(ex.getMessage());
    }
}
