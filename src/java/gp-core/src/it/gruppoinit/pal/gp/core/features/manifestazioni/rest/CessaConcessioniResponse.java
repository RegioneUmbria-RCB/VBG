package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.Mercati;

@XmlRootElement(name = "cessaconcessioni")
public class CessaConcessioniResponse {
    
    @XmlElement(name = "totale_cessate")
    private int totaleCessate;
    
    
    public int getTotaleCessate() {

	return totaleCessate;
    }
    
    public static CessaConcessioniResponse FromTotaleCessate(int totaleCessate) {

	CessaConcessioniResponse response = new CessaConcessioniResponse();
	
	response.totaleCessate = totaleCessate;
	
	return response;
    }
}
