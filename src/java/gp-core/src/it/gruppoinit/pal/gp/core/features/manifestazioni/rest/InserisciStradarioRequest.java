package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

@XmlRootElement(name = "mercatistradario")
public class InserisciStradarioRequest extends AbstractRequest {
    
    @XmlTransient
    private int codiceMercato;
    @XmlElement(name = "codice_stradario")
    private int codiceStradario;
    
    public int getCodiceMercato() {

	return codiceMercato;
    }
    
    public void setCodiceMercato(int codiceMercato) {

	this.codiceMercato = codiceMercato;
    }
    
    public int getCodiceStradario() {

	return codiceStradario;
    }
    
    public void setCodiceStradario(int codiceStradario) {

	this.codiceStradario = codiceStradario;
    }

    @Override
    public void valida() throws BusinessValidationException {

	// non ci sono campi nullabili al momento
	
    }
}


