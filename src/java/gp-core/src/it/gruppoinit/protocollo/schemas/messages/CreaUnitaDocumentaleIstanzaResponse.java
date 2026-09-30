
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
 *         &lt;element name="CreaUnitaDocumentaleIstanzaResult" type="{http://it.gruppoinit/Protocollazione}CreaUnitaDocumentaleResponseType" minOccurs="0"/>
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
    "creaUnitaDocumentaleIstanzaResult"
})
@XmlRootElement(name = "CreaUnitaDocumentaleIstanzaResponse")
public class CreaUnitaDocumentaleIstanzaResponse {

    @XmlElement(name = "CreaUnitaDocumentaleIstanzaResult", nillable = true)
    protected CreaUnitaDocumentaleResponseType creaUnitaDocumentaleIstanzaResult;

    /**
     * Gets the value of the creaUnitaDocumentaleIstanzaResult property.
     * 
     * @return
     *     possible object is
     *     {@link CreaUnitaDocumentaleResponseType }
     *     
     */
    public CreaUnitaDocumentaleResponseType getCreaUnitaDocumentaleIstanzaResult() {
        return creaUnitaDocumentaleIstanzaResult;
    }

    /**
     * Sets the value of the creaUnitaDocumentaleIstanzaResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreaUnitaDocumentaleResponseType }
     *     
     */
    public void setCreaUnitaDocumentaleIstanzaResult(CreaUnitaDocumentaleResponseType value) {
        this.creaUnitaDocumentaleIstanzaResult = value;
    }

}
