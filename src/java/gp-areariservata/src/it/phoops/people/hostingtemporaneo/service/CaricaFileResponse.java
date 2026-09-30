
package it.phoops.people.hostingtemporaneo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for caricaFileResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="caricaFileResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rispostaCaricamentoFile" type="{http://service.hostingtemporaneo.people.phoops.it/}rispostaCaricamentoFile" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "caricaFileResponse", propOrder = {
    "rispostaCaricamentoFile"
})
public class CaricaFileResponse {

    protected RispostaCaricamentoFile rispostaCaricamentoFile;

    /**
     * Gets the value of the rispostaCaricamentoFile property.
     * 
     * @return
     *     possible object is
     *     {@link RispostaCaricamentoFile }
     *     
     */
    public RispostaCaricamentoFile getRispostaCaricamentoFile() {
        return rispostaCaricamentoFile;
    }

    /**
     * Sets the value of the rispostaCaricamentoFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link RispostaCaricamentoFile }
     *     
     */
    public void setRispostaCaricamentoFile(RispostaCaricamentoFile value) {
        this.rispostaCaricamentoFile = value;
    }

}
