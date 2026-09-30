/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.cart;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.eng.suap.xengine.model.modulistica.ModuloType;


/**
 * @author francol
 * Classe wrapper che serve solo per consentire la serializzazione XML della definizione di un singolo modulo CART.
 * Utilizzata solo per le funzionalità denominate STANDARD 0 BACK.
 */
@XmlRootElement(name = "modulistica")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modulistica", propOrder = { "modulo"})
public class ModuloWrapper {
    
    @XmlElement(name = "modulo", required = true)
    private ModuloType modulo;

    
    /**
     * @return the modulo
     */
    public ModuloType getModulo() {
    
        return modulo;
    }

    
    /**
     * @param modulo the modulo to set
     */
    public void setModulo(ModuloType modulo) {
    
        this.modulo = modulo;
    }
    
    
}
