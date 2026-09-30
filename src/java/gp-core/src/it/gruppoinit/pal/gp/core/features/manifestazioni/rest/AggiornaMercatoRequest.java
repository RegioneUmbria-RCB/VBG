package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

@XmlRootElement(name = "mercato")
public class AggiornaMercatoRequest extends AbstractRequest {
    
    @XmlElement(name = "attivo")
    private boolean attivo;
    @XmlElement(name = "descrizione", nillable = false, required = true)
    private String descrizione;
    
    
    public String getDescrizione() {

	return descrizione;
    }
    
    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }
    
    public boolean isAttivo() {

	return attivo;
    }
    
    public void setAttivo(boolean attivo) {

	this.attivo = attivo;
    }

    @Override
    public void valida() throws BusinessValidationException {

	if( StringUtils.isBlank(this.descrizione) ) {
	    throw new BusinessValidationException("La proprietà descrizione non è valorizzata");
	}
	
    }
}
