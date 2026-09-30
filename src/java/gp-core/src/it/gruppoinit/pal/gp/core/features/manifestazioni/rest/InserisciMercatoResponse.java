package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.Mercati;

@XmlRootElement(name = "mercato")
public class InserisciMercatoResponse {

    @XmlElement(name = "codice_mercato")
    private int codiceMercato;
    
    public int getCodiceMercato() {

	return codiceMercato;
    }
    

    public static InserisciMercatoResponse FromMercati(Mercati mercato) {

	InserisciMercatoResponse response = new InserisciMercatoResponse();
	
	response.codiceMercato = mercato.getId().getCodice();
	
	return response;
    }
}
