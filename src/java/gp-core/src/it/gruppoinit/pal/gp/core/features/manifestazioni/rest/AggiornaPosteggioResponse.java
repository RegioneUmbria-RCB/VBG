package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.MercatiD;

@XmlRootElement(name = "mercatiposteggio")
public class AggiornaPosteggioResponse {

    @XmlElement(name = "id")
    private int idPosteggio;
    
    public int getIdPosteggio() {
    
        return idPosteggio;
    }
    
    public static AggiornaPosteggioResponse FromMercatiD(MercatiD posteggio) {

	AggiornaPosteggioResponse response = new AggiornaPosteggioResponse();
	response.idPosteggio = posteggio.getId().getCodice();
	return response;
    }
}
