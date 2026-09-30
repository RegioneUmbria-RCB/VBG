package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import javax.xml.bind.annotation.XmlElement;

public class DettagliAppioComunicazione {
    
    @XmlElement(name = "codice_fiscale")
    private String codicefiscale;
    @XmlElement(name = "oggetto")
    private String oggetto;
    @XmlElement(name = "stato_messaggio")
    private String statomessaggio;
    
    public String getCodicefiscale() {
    
        return codicefiscale;
    }
    
    public void setCodicefiscale(String codicefiscale) {
    
        this.codicefiscale = codicefiscale;
    }
    
    public String getOggetto() {
    
        return oggetto;
    }
    
    public void setOggetto(String oggetto) {
    
        this.oggetto = oggetto;
    }
    
    public String getStatomessaggio() {
    
        return statomessaggio;
    }
    
    public void setStatomessaggio(String statomessaggio) {
    
        this.statomessaggio = statomessaggio;
    }
    
}
