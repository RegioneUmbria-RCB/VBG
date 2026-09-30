package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;

public class SaldoBorsellinoAppModel {
    
    @XmlElement
    private String uuid;
    @XmlElement
    private BigDecimal attuale;
    @XmlElement(name = "max_ricaricabile")
    private BigDecimal maxRicaricabile;
    @XmlElement
    private BigDecimal limite;
    
    public String getUuid() {
    
        return uuid;
    }
    
    public void setUuid(String uuid) {
    
        this.uuid = uuid;
    }
    
    public BigDecimal getAttuale() {
    
        return attuale;
    }
    
    public void setAttuale(BigDecimal attuale) {
    
        this.attuale = attuale;
    }
    
    public BigDecimal getMaxRicaricabile() {
    
        return maxRicaricabile;
    }
    
    public void setMaxRicaricabile(BigDecimal maxRicaricabile) {
    
        this.maxRicaricabile = maxRicaricabile;
    }
    
    public BigDecimal getLimite() {
    
        return limite;
    }
    
    public void setLimite(BigDecimal limite) {
    
        this.limite = limite;
    }
    
}
