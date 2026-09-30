
package it.phoops.people.hostingtemporaneo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for scaricaFile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="scaricaFile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="richiestaScaricamentoFile" type="{http://service.hostingtemporaneo.people.phoops.it/}richiestaScaricamentoFile" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "scaricaFile", propOrder = {
    "richiestaScaricamentoFile"
})
public class ScaricaFile {

    protected RichiestaScaricamentoFile richiestaScaricamentoFile;

    /**
     * Gets the value of the richiestaScaricamentoFile property.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaScaricamentoFile }
     *     
     */
    public RichiestaScaricamentoFile getRichiestaScaricamentoFile() {
        return richiestaScaricamentoFile;
    }

    /**
     * Sets the value of the richiestaScaricamentoFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaScaricamentoFile }
     *     
     */
    public void setRichiestaScaricamentoFile(RichiestaScaricamentoFile value) {
        this.richiestaScaricamentoFile = value;
    }

}
