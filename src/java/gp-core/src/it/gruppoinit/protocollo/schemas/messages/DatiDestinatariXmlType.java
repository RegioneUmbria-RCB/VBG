
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiDestinatariXmlType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiDestinatariXmlType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Anagrafe" type="{http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data}ArrayOfProtocolloAnagrafe" minOccurs="0"/>
 *         &lt;element name="Amministrazione" type="{http://schemas.datacontract.org/2004/07/VBG.Backend.Protocollo.AppLogic.Shared.Data}ArrayOfProtocolloAmministrazioni" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiDestinatariXmlType", propOrder = {
    "anagrafe",
    "amministrazione"
})
public class DatiDestinatariXmlType {

    @XmlElement(name = "Anagrafe", nillable = true)
    protected ArrayOfProtocolloAnagrafe anagrafe;
    @XmlElement(name = "Amministrazione", nillable = true)
    protected ArrayOfProtocolloAmministrazioni amministrazione;

    /**
     * Gets the value of the anagrafe property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProtocolloAnagrafe }
     *     
     */
    public ArrayOfProtocolloAnagrafe getAnagrafe() {
        return anagrafe;
    }

    /**
     * Sets the value of the anagrafe property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProtocolloAnagrafe }
     *     
     */
    public void setAnagrafe(ArrayOfProtocolloAnagrafe value) {
        this.anagrafe = value;
    }

    /**
     * Gets the value of the amministrazione property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProtocolloAmministrazioni }
     *     
     */
    public ArrayOfProtocolloAmministrazioni getAmministrazione() {
        return amministrazione;
    }

    /**
     * Sets the value of the amministrazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProtocolloAmministrazioni }
     *     
     */
    public void setAmministrazione(ArrayOfProtocolloAmministrazioni value) {
        this.amministrazione = value;
    }

}
