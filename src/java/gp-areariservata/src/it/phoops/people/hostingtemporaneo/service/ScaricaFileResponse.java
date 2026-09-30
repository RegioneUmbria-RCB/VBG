
package it.phoops.people.hostingtemporaneo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for scaricaFileResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="scaricaFileResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rispostaScaricamentoFile" type="{http://service.hostingtemporaneo.people.phoops.it/}rispostaScaricamentoFile" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scaricaFileResponse", propOrder = {
    "rispostaScaricamentoFile"
})
public class ScaricaFileResponse {

    protected RispostaScaricamentoFile rispostaScaricamentoFile;

    /**
     * Gets the value of the rispostaScaricamentoFile property.
     * 
     * @return
     *     possible object is
     *     {@link RispostaScaricamentoFile }
     *     
     */
    public RispostaScaricamentoFile getRispostaScaricamentoFile() {
        return rispostaScaricamentoFile;
    }

    /**
     * Sets the value of the rispostaScaricamentoFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link RispostaScaricamentoFile }
     *     
     */
    public void setRispostaScaricamentoFile(RispostaScaricamentoFile value) {
        this.rispostaScaricamentoFile = value;
    }

}
