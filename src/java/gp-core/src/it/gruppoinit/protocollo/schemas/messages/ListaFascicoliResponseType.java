
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaFascicoliResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaFascicoliResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Fascicolo" type="{http://it.gruppoinit/Protocollazione}ArrayOfDatiFascType" minOccurs="0"/>
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
@XmlType(name = "ListaFascicoliResponseType", propOrder = {
    "fascicolo",
    "errore"
})
public class ListaFascicoliResponseType {

    @XmlElement(name = "Fascicolo", nillable = true)
    protected ArrayOfDatiFascType fascicolo;
    @XmlElement(name = "Errore", nillable = true)
    protected ErroreProtocolloType errore;

    /**
     * Gets the value of the fascicolo property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDatiFascType }
     *     
     */
    public ArrayOfDatiFascType getFascicolo() {
        return fascicolo;
    }

    /**
     * Sets the value of the fascicolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDatiFascType }
     *     
     */
    public void setFascicolo(ArrayOfDatiFascType value) {
        this.fascicolo = value;
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
