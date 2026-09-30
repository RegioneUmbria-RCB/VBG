
package it.phoops.people.hostingtemporaneo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for caricaFile complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="caricaFile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="richiestaCaricamentoFile" type="{http://service.hostingtemporaneo.people.phoops.it/}richiestaCaricamentoFile" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "caricaFile", propOrder = {
    "richiestaCaricamentoFile"
})
public class CaricaFile {

    protected RichiestaCaricamentoFile richiestaCaricamentoFile;

    /**
     * Gets the value of the richiestaCaricamentoFile property.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaCaricamentoFile }
     *     
     */
    public RichiestaCaricamentoFile getRichiestaCaricamentoFile() {
        return richiestaCaricamentoFile;
    }

    /**
     * Sets the value of the richiestaCaricamentoFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaCaricamentoFile }
     *     
     */
    public void setRichiestaCaricamentoFile(RichiestaCaricamentoFile value) {
        this.richiestaCaricamentoFile = value;
    }

}
