
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaProspDisabSILP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaProspDisabSILP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrTipoProspDisab" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataProtocollo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataClassificazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="controparte" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idProspDisab" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numProtocollo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="annoRiferimento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="siglaProv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numClassificazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInvio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaProspDisabSILP", propOrder = {
    "descrTipoProspDisab",
    "dataProtocollo",
    "dataClassificazione",
    "controparte",
    "idProspDisab",
    "numProtocollo",
    "annoRiferimento",
    "siglaProv",
    "numClassificazione",
    "dataInvio"
})
public class ListaProspDisabSILP {

    @XmlElement(required = true, nillable = true)
    protected String descrTipoProspDisab;
    @XmlElement(required = true, nillable = true)
    protected String dataProtocollo;
    @XmlElement(required = true, nillable = true)
    protected String dataClassificazione;
    @XmlElement(required = true, nillable = true)
    protected String controparte;
    @XmlElement(required = true, nillable = true)
    protected String idProspDisab;
    @XmlElement(required = true, nillable = true)
    protected String numProtocollo;
    @XmlElement(required = true, nillable = true)
    protected String annoRiferimento;
    @XmlElement(required = true, nillable = true)
    protected String siglaProv;
    @XmlElement(required = true, nillable = true)
    protected String numClassificazione;
    @XmlElement(required = true, nillable = true)
    protected String dataInvio;

    /**
     * Gets the value of the descrTipoProspDisab property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoProspDisab() {
        return descrTipoProspDisab;
    }

    /**
     * Sets the value of the descrTipoProspDisab property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoProspDisab(String value) {
        this.descrTipoProspDisab = value;
    }

    /**
     * Gets the value of the dataProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataProtocollo() {
        return dataProtocollo;
    }

    /**
     * Sets the value of the dataProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataProtocollo(String value) {
        this.dataProtocollo = value;
    }

    /**
     * Gets the value of the dataClassificazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataClassificazione() {
        return dataClassificazione;
    }

    /**
     * Sets the value of the dataClassificazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataClassificazione(String value) {
        this.dataClassificazione = value;
    }

    /**
     * Gets the value of the controparte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControparte() {
        return controparte;
    }

    /**
     * Sets the value of the controparte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControparte(String value) {
        this.controparte = value;
    }

    /**
     * Gets the value of the idProspDisab property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdProspDisab() {
        return idProspDisab;
    }

    /**
     * Sets the value of the idProspDisab property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdProspDisab(String value) {
        this.idProspDisab = value;
    }

    /**
     * Gets the value of the numProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumProtocollo() {
        return numProtocollo;
    }

    /**
     * Sets the value of the numProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumProtocollo(String value) {
        this.numProtocollo = value;
    }

    /**
     * Gets the value of the annoRiferimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoRiferimento() {
        return annoRiferimento;
    }

    /**
     * Sets the value of the annoRiferimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoRiferimento(String value) {
        this.annoRiferimento = value;
    }

    /**
     * Gets the value of the siglaProv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProv() {
        return siglaProv;
    }

    /**
     * Sets the value of the siglaProv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProv(String value) {
        this.siglaProv = value;
    }

    /**
     * Gets the value of the numClassificazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumClassificazione() {
        return numClassificazione;
    }

    /**
     * Sets the value of the numClassificazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumClassificazione(String value) {
        this.numClassificazione = value;
    }

    /**
     * Gets the value of the dataInvio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInvio() {
        return dataInvio;
    }

    /**
     * Sets the value of the dataInvio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInvio(String value) {
        this.dataInvio = value;
    }

}
