
package it.phoops.people.hostingtemporaneo.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for recuperaMetadati complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recuperaMetadati">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="richiestaRecuperoMetadati" type="{http://service.hostingtemporaneo.people.phoops.it/}richiestaRecuperoMetadati" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recuperaMetadati", propOrder = {
    "richiestaRecuperoMetadati"
})
public class RecuperaMetadati {

    protected RichiestaRecuperoMetadati richiestaRecuperoMetadati;

    /**
     * Gets the value of the richiestaRecuperoMetadati property.
     * 
     * @return
     *     possible object is
     *     {@link RichiestaRecuperoMetadati }
     *     
     */
    public RichiestaRecuperoMetadati getRichiestaRecuperoMetadati() {
        return richiestaRecuperoMetadati;
    }

    /**
     * Sets the value of the richiestaRecuperoMetadati property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiestaRecuperoMetadati }
     *     
     */
    public void setRichiestaRecuperoMetadati(RichiestaRecuperoMetadati value) {
        this.richiestaRecuperoMetadati = value;
    }

}
