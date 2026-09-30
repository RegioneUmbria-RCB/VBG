
package it.gruppoinit.wssit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="EffettuaValidazioneFormaleResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "effettuaValidazioneFormaleResult"
})
@XmlRootElement(name = "EffettuaValidazioneFormaleResponse")
public class EffettuaValidazioneFormaleResponse {

    @XmlElement(name = "EffettuaValidazioneFormaleResult")
    protected boolean effettuaValidazioneFormaleResult;

    /**
     * Gets the value of the effettuaValidazioneFormaleResult property.
     * 
     */
    public boolean isEffettuaValidazioneFormaleResult() {
        return effettuaValidazioneFormaleResult;
    }

    /**
     * Sets the value of the effettuaValidazioneFormaleResult property.
     * 
     */
    public void setEffettuaValidazioneFormaleResult(boolean value) {
        this.effettuaValidazioneFormaleResult = value;
    }

}
