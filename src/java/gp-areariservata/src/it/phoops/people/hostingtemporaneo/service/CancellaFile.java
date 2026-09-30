
package it.phoops.people.hostingtemporaneo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cancellaFile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cancellaFile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="richiestaCancellazioneFile" type="{http://service.hostingtemporaneo.people.phoops.it/}richiestaCancellazioneFile" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cancellaFile", propOrder = {
    "richiestaCancellazioneFile"
})
public class CancellaFile {

    protected RichiestaCancellazioneFile richiestaCancellazioneFile;

    /**
     * Gets the value of the richiestaCancellazioneFile property.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaCancellazioneFile }
     *     
     */
    public RichiestaCancellazioneFile getRichiestaCancellazioneFile() {
        return richiestaCancellazioneFile;
    }

    /**
     * Sets the value of the richiestaCancellazioneFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaCancellazioneFile }
     *     
     */
    public void setRichiestaCancellazioneFile(RichiestaCancellazioneFile value) {
        this.richiestaCancellazioneFile = value;
    }

}
