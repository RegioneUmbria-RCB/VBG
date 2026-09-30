package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.client.VerificaStatoWSResponse;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class VerificaStatoResponse {

    @XmlElement(name = "stato")
    private String stato;
    @XmlElement(name = "documenti-caricati")
    private Integer documentiCaricati;
    @XmlElement(name = "documenti-firmati")
    private Integer documentiFirmati;

    public static VerificaStatoResponse fromWSResponse(VerificaStatoWSResponse wsResponse) {

	if (wsResponse == null || wsResponse.getDocumenti() == null) {
	    return null;
	}
	VerificaStatoResponse response = new VerificaStatoResponse();
	response.setStato(wsResponse.getStato());
	response.setDocumentiCaricati(wsResponse.getDocumenti().getCaricati());
	response.setDocumentiFirmati(wsResponse.getDocumenti().getFirmati());
	return response;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public Integer getDocumentiCaricati() {

	return documentiCaricati;
    }

    public void setDocumentiCaricati(Integer documentiCaricati) {

	this.documentiCaricati = documentiCaricati;
    }

    public Integer getDocumentiFirmati() {

	return documentiFirmati;
    }

    public void setDocumentiFirmati(Integer documentiFirmati) {

	this.documentiFirmati = documentiFirmati;
    }
}
