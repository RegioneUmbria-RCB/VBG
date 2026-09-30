
package it.gruppoinit.pal.gp.backoffice.schemas.messages.responsabili;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="isAbilitato" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "isAbilitato"
})
@XmlRootElement(name = "ResponsabiliVerificaAbilitazioneResponse")
public class ResponsabiliVerificaAbilitazioneResponse {

    protected boolean isAbilitato;

    /**
     * Gets the value of the isAbilitato property.
     * 
     */
    public boolean isIsAbilitato() {
        return isAbilitato;
    }

    /**
     * Sets the value of the isAbilitato property.
     * 
     */
    public void setIsAbilitato(boolean value) {
        this.isAbilitato = value;
    }

}
