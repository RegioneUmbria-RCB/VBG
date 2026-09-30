
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaTipiDocumentoResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaTipiDocumentoResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Documento" type="{http://it.gruppoinit/Protocollazione}ArrayOfListaTipiDocumentoDocumentoType" minOccurs="0"/>
 *         &lt;element name="Errore" type="{http://it.gruppoinit/Protocollazione}ErroreProtocolloType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaTipiDocumentoResponseType", propOrder = {
    "documento",
    "errore"
})
public class ListaTipiDocumentoResponseType {

    @XmlElement(name = "Documento", nillable = true)
    protected ArrayOfListaTipiDocumentoDocumentoType documento;
    @XmlElement(name = "Errore", nillable = true)
    protected ErroreProtocolloType errore;

    /**
     * Gets the value of the documento property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfListaTipiDocumentoDocumentoType }
     *     
     */
    public ArrayOfListaTipiDocumentoDocumentoType getDocumento() {
        return documento;
    }

    /**
     * Sets the value of the documento property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfListaTipiDocumentoDocumentoType }
     *     
     */
    public void setDocumento(ArrayOfListaTipiDocumentoDocumentoType value) {
        this.documento = value;
    }

    /**
     * Gets the value of the errore property.
     * 
     * @return
     *     possible object is
     *     {@link ErroreProtocolloType }
     *     
     */
    public ErroreProtocolloType getErrore() {
        return errore;
    }

    /**
     * Sets the value of the errore property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErroreProtocolloType }
     *     
     */
    public void setErrore(ErroreProtocolloType value) {
        this.errore = value;
    }

}
