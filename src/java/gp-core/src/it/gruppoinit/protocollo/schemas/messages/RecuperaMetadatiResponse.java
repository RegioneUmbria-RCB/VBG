
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RecuperaMetadatiResult" type="{http://it.gruppoinit/Protocollazione}ArrayOfMetadatoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "recuperaMetadatiResult"
})
@XmlRootElement(name = "RecuperaMetadatiResponse")
public class RecuperaMetadatiResponse {

    @XmlElement(name = "RecuperaMetadatiResult", nillable = true)
    protected ArrayOfMetadatoType recuperaMetadatiResult;

    /**
     * Gets the value of the recuperaMetadatiResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMetadatoType }
     *     
     */
    public ArrayOfMetadatoType getRecuperaMetadatiResult() {
        return recuperaMetadatiResult;
    }

    /**
     * Sets the value of the recuperaMetadatiResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMetadatoType }
     *     
     */
    public void setRecuperaMetadatiResult(ArrayOfMetadatoType value) {
        this.recuperaMetadatiResult = value;
    }

}
