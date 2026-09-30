package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

@XmlRootElement(name = "cessaconcessioni")
public class CessaConcessioniRequest extends AbstractRequest {

    @XmlElement(name = "data_cessazione")
    private Date dataCessazione;
    @XmlElement(name = "id_causale_cessazione")
    private int idCausaleCessazione;
    
    public Date getDataCessazione() {

	return dataCessazione;
    }
    
    public void setDataCessazione(Date dataCessazione) {

	this.dataCessazione = dataCessazione;
    }
    
    public int getIdCausaleCessazione() {

	return idCausaleCessazione;
    }
    
    public void setIdCausaleCessazione(int idCausaleCessazione) {

	this.idCausaleCessazione = idCausaleCessazione;
    }
    
    @Override
    public void valida() throws BusinessValidationException {

	if( this.dataCessazione == null ) {
	    throw new BusinessValidationException("La proprietà data_cessazione non è valorizzata");
	}
	
    }
}
