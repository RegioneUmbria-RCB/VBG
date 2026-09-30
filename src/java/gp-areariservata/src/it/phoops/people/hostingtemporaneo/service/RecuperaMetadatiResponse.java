
package it.phoops.people.hostingtemporaneo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for recuperaMetadatiResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recuperaMetadatiResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rispostaRecuperoMetadati" type="{http://service.hostingtemporaneo.people.phoops.it/}rispostaRecuperoMetadati" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recuperaMetadatiResponse", propOrder = {
    "rispostaRecuperoMetadati"
})
public class RecuperaMetadatiResponse {

    protected RispostaRecuperoMetadati rispostaRecuperoMetadati;

    /**
     * Gets the value of the rispostaRecuperoMetadati property.
     * 
     * @return
     *     possible object is
     *     {@link RispostaRecuperoMetadati }
     *     
     */
    public RispostaRecuperoMetadati getRispostaRecuperoMetadati() {
        return rispostaRecuperoMetadati;
    }

    /**
     * Sets the value of the rispostaRecuperoMetadati property.
     * 
     * @param value
     *     allowed object is
     *     {@link RispostaRecuperoMetadati }
     *     
     */
    public void setRispostaRecuperoMetadati(RispostaRecuperoMetadati value) {
        this.rispostaRecuperoMetadati = value;
    }

}
