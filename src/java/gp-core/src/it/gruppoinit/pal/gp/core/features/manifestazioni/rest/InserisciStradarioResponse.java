package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.Mercatistradario;

@XmlRootElement(name = "mercatistradario")
public class InserisciStradarioResponse {

    @XmlElement(name = "id")
    private int idMercatoStradario;
    
    
    public int getIdMercatoStradario() {

	return idMercatoStradario;
    }
    
    public static InserisciStradarioResponse FromMercatistradario(Mercatistradario stradario) {

	InserisciStradarioResponse response = new InserisciStradarioResponse();
	response.idMercatoStradario = stradario.getId().getCodice();
	return response;
    }
}
