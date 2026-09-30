package it.gruppoinit.pal.gp.core.features.manifestazioni.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

@XmlRootElement(name = "mercato")
public class InserisciMercatoRequest extends AbstractRequest {
    
    @XmlElement(name = "attivo")
    private boolean attivo;
    @XmlElement(name = "codice_esterno")
    private String codiceEsterno;
    @XmlElement(name = "codice_comune")
    private String codiceComune;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "id_comune")
    private String idComune;
    @XmlElement(name = "integrato_con_nodo_pagamenti")
    private boolean integratoConNodoPagamenti;
    @XmlElement(name = "software")
    private String software;
    @XmlElement(name = "temporaneo")
    private boolean temporaneo;
    @XmlElement(name = "tipo_manifestazione")
    private int tipoManifestazione;
    
    public boolean isAttivo() {

	return attivo;
    }
    
    public void setAttivo(boolean attivo) {

	this.attivo = attivo;
    }
    
    public String getCodiceEsterno() {

	return codiceEsterno;
    }
    
    public void setCodiceEsterno(String codiceEsterno) {

	this.codiceEsterno = codiceEsterno;
    }
    
    public String getCodiceComune() {

	return codiceComune;
    }
    
    public void setCodiceComune(String codiceComune) {

	this.codiceComune = codiceComune;
    }
    
    public String getDescrizione() {

	return descrizione;
    }
    
    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    
    public boolean isIntegratoConNodoPagamenti() {

	return integratoConNodoPagamenti;
    }
    
    public void setIntegratoConNodoPagamenti(boolean integratoConNodoPagamenti) {

	this.integratoConNodoPagamenti = integratoConNodoPagamenti;
    }
    
    public String getSoftware() {

	return software;
    }
    
    public void setSoftware(String software) {

	this.software = software;
    }
    
    public boolean isTemporaneo() {

	return temporaneo;
    }
    
    public void setTemporaneo(boolean temporaneo) {

	this.temporaneo = temporaneo;
    }
    
    public int getTipoManifestazione() {

	return tipoManifestazione;
    }
    
    public void setTipoManifestazione(int tipoManifestazione) {

	this.tipoManifestazione = tipoManifestazione;
    }

    @Override
    public void valida() throws BusinessValidationException {

	if( StringUtils.isBlank(this.codiceComune) ) {
	    throw new BusinessValidationException("La proprietà codice_comune non è valorizzata");
	}
	
	if( StringUtils.isBlank(this.descrizione) ) {
	    throw new BusinessValidationException("La proprietà descrizione non è valorizzata");
	}
	
	if( StringUtils.isBlank(this.software) ) {
	    throw new BusinessValidationException("La proprietà software non è valorizzata");
	}
    }
}
