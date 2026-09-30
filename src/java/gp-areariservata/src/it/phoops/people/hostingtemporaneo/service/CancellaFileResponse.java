
package it.phoops.people.hostingtemporaneo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cancellaFileResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cancellaFileResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rispostaCancellazioneFile" type="{http://service.hostingtemporaneo.people.phoops.it/}rispostaCancellazioneFile" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cancellaFileResponse", propOrder = {
    "rispostaCancellazioneFile"
})
public class CancellaFileResponse {

    protected RispostaCancellazioneFile rispostaCancellazioneFile;

    /**
     * Gets the value of the rispostaCancellazioneFile property.
     * 
     * @return
     *     possible object is
     *     {@link RispostaCancellazioneFile }
     *     
     */
    public RispostaCancellazioneFile getRispostaCancellazioneFile() {
        return rispostaCancellazioneFile;
    }

    /**
     * Sets the value of the rispostaCancellazioneFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link RispostaCancellazioneFile }
     *     
     */
    public void setRispostaCancellazioneFile(RispostaCancellazioneFile value) {
        this.rispostaCancellazioneFile = value;
    }

}
