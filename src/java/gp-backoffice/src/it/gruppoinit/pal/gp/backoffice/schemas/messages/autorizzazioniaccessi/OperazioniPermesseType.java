
package it.gruppoinit.pal.gp.backoffice.schemas.messages.autorizzazioniaccessi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OperazioniPermesseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OperazioniPermesseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="proroga" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="preavviso" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="rinnovo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperazioniPermesseType", propOrder = {
    "proroga",
    "preavviso",
    "rinnovo"
})
public class OperazioniPermesseType {

    protected boolean proroga;
    protected boolean preavviso;
    protected boolean rinnovo;

    /**
     * Gets the value of the proroga property.
     * 
     */
    public boolean isProroga() {
        return proroga;
    }

    /**
     * Sets the value of the proroga property.
     * 
     */
    public void setProroga(boolean value) {
        this.proroga = value;
    }

    /**
     * Gets the value of the preavviso property.
     * 
     */
    public boolean isPreavviso() {
        return preavviso;
    }

    /**
     * Sets the value of the preavviso property.
     * 
     */
    public void setPreavviso(boolean value) {
        this.preavviso = value;
    }

    /**
     * Gets the value of the rinnovo property.
     * 
     */
    public boolean isRinnovo() {
        return rinnovo;
    }

    /**
     * Sets the value of the rinnovo property.
     * 
     */
    public void setRinnovo(boolean value) {
        this.rinnovo = value;
    }

}
