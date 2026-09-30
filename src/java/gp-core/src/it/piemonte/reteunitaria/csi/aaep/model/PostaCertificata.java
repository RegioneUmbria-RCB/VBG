
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PostaCertificata complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostaCertificata">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="postaElettronicaCertificata" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostaCertificata", propOrder = {
    "postaElettronicaCertificata"
})
public class PostaCertificata {

    @XmlElement(required = true, nillable = true)
    protected String postaElettronicaCertificata;

    /**
     * Gets the value of the postaElettronicaCertificata property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostaElettronicaCertificata() {
        return postaElettronicaCertificata;
    }

    /**
     * Sets the value of the postaElettronicaCertificata property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostaElettronicaCertificata(String value) {
        this.postaElettronicaCertificata = value;
    }

}
