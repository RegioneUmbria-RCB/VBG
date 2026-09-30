
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MittDestOutType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MittDestOutType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdSoggetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CognomeNome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MittDestOutType", propOrder = {
    "idSoggetto",
    "cognomeNome"
})
public class MittDestOutType {

    @XmlElement(name = "IdSoggetto", nillable = true)
    protected String idSoggetto;
    @XmlElement(name = "CognomeNome", nillable = true)
    protected String cognomeNome;

    /**
     * Gets the value of the idSoggetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSoggetto() {
        return idSoggetto;
    }

    /**
     * Sets the value of the idSoggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSoggetto(String value) {
        this.idSoggetto = value;
    }

    /**
     * Gets the value of the cognomeNome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognomeNome() {
        return cognomeNome;
    }

    /**
     * Sets the value of the cognomeNome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognomeNome(String value) {
        this.cognomeNome = value;
    }

}
