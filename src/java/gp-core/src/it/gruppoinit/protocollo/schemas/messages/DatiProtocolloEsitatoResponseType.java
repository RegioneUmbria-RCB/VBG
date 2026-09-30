
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiProtocolloEsitatoResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiProtocolloEsitatoResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Esitato" type="{http://it.gruppoinit/Protocollazione}EnumEsitatoType" minOccurs="0"/>
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
@XmlType(name = "DatiProtocolloEsitatoResponseType", propOrder = {
    "esitato",
    "errore"
})
public class DatiProtocolloEsitatoResponseType {

    @XmlElement(name = "Esitato")
    protected EnumEsitatoType esitato;
    @XmlElement(name = "Errore", nillable = true)
    protected ErroreProtocolloType errore;

    /**
     * Gets the value of the esitato property.
     * 
     * @return
     *     possible object is
     *     {@link EnumEsitatoType }
     *     
     */
    public EnumEsitatoType getEsitato() {
        return esitato;
    }

    /**
     * Sets the value of the esitato property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumEsitatoType }
     *     
     */
    public void setEsitato(EnumEsitatoType value) {
        this.esitato = value;
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
