
package it.gruppoinit.ws.wsatti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TabellaUtente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TabellaUtente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="nomeTabella" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="righe" type="{http://tempuri.org/}ArrayOfRecordUtente"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TabellaUtente", propOrder = {
    "nomeTabella",
    "righe"
})
public class TabellaUtente {

    @XmlElement(required = true, nillable = true)
    protected String nomeTabella;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfRecordUtente righe;

    /**
     * Gets the value of the nomeTabella property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeTabella() {
        return nomeTabella;
    }

    /**
     * Sets the value of the nomeTabella property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeTabella(String value) {
        this.nomeTabella = value;
    }

    /**
     * Gets the value of the righe property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRecordUtente }
     *     
     */
    public ArrayOfRecordUtente getRighe() {
        return righe;
    }

    /**
     * Sets the value of the righe property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRecordUtente }
     *     
     */
    public void setRighe(ArrayOfRecordUtente value) {
        this.righe = value;
    }

}
