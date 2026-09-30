
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
 *         &lt;element name="CreaUnitaDocumentaleMovimentoResult" type="{http://it.gruppoinit/Protocollazione}CreaUnitaDocumentaleResponseType" minOccurs="0"/>
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
    "creaUnitaDocumentaleMovimentoResult"
})
@XmlRootElement(name = "CreaUnitaDocumentaleMovimentoResponse")
public class CreaUnitaDocumentaleMovimentoResponse {

    @XmlElement(name = "CreaUnitaDocumentaleMovimentoResult", nillable = true)
    protected CreaUnitaDocumentaleResponseType creaUnitaDocumentaleMovimentoResult;

    /**
     * Gets the value of the creaUnitaDocumentaleMovimentoResult property.
     * 
     * @return
     *     possible object is
     *     {@link CreaUnitaDocumentaleResponseType }
     *     
     */
    public CreaUnitaDocumentaleResponseType getCreaUnitaDocumentaleMovimentoResult() {
        return creaUnitaDocumentaleMovimentoResult;
    }

    /**
     * Sets the value of the creaUnitaDocumentaleMovimentoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreaUnitaDocumentaleResponseType }
     *     
     */
    public void setCreaUnitaDocumentaleMovimentoResult(CreaUnitaDocumentaleResponseType value) {
        this.creaUnitaDocumentaleMovimentoResult = value;
    }

}
