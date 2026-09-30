package it.gruppoinit.pal.gp.core.domain.helper;

import java.math.BigDecimal;

import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.Tipiunitamisura;


public class SuperficiAttivitaHelper {
    
    private String attivitaIstat;
    private String unitaMisura;
    private String settore;
    private BigDecimal superficieTotale;
    
    /**
     * @return the attivitaIstat
     */
    public String getAttivitaIstat() {
    
        return attivitaIstat;
    }
    
    /**
     * @param attivitaIstat the attivitaIstat to set
     */
    public void setAttivitaIstat(String attivitaIstat) {
    
        this.attivitaIstat = attivitaIstat;
    }
    
    /**
     * @return the unitaMisura
     */
    public String getUnitaMisura() {
    
        return unitaMisura;
    }
    
    /**
     * @param unitaMisura the unitaMisura to set
     */
    public void setUnitaMisura(String unitaMisura) {
    
        this.unitaMisura = unitaMisura;
    }
    
    /**
     * @return the settore
     */
    public String getSettore() {
    
        return settore;
    }
    
    /**
     * @param settore the settore to set
     */
    public void setSettore(String settore) {
    
        this.settore = settore;
    }
    
    /**
     * @return the superficieTotale
     */
    public BigDecimal getSuperficieTotale() {
    
        return superficieTotale;
    }
    
    /**
     * @param superficieTotale the superficieTotale to set
     */
    public void setSuperficieTotale(BigDecimal superficieTotale) {
    
        this.superficieTotale = superficieTotale;
    }
    
    
}
