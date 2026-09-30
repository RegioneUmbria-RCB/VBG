
package it.gruppoinit.pal.gp.backoffice.schemas.messages.responsabili;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Responsabili complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Responsabili">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="responsabile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="useridCopiaPermessi" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="amministratore" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="amministratoresoftware" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="softwareAbilitati" type="{http://gruppoinit.it/pal/gp/backoffice/schemas/messages/responsabili}SoftwareAblilitati" minOccurs="0"/>
 *         &lt;element name="comuniAssociati" type="{http://gruppoinit.it/pal/gp/backoffice/schemas/messages/responsabili}ComuniAssociati" minOccurs="0"/>
 *         &lt;element name="readonly" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="disabilitato" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="anagrafe" type="{http://gruppoinit.it/pal/gp/backoffice/schemas/messages/responsabili}Anagrafe" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Responsabili", propOrder = {
    "responsabile",
    "userid",
    "password",
    "useridCopiaPermessi",
    "amministratore",
    "amministratoresoftware",
    "softwareAbilitati",
    "comuniAssociati",
    "readonly",
    "disabilitato",
    "anagrafe"
})
public class Responsabili {

    @XmlElement(required = true)
    protected String responsabile;
    @XmlElement(required = true)
    protected String userid;
    protected String password;
    protected String useridCopiaPermessi;
    protected Boolean amministratore;
    protected Boolean amministratoresoftware;
    protected SoftwareAblilitati softwareAbilitati;
    protected ComuniAssociati comuniAssociati;
    protected Boolean readonly;
    protected Boolean disabilitato;
    protected Anagrafe anagrafe;

    /**
     * Gets the value of the responsabile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponsabile() {
        return responsabile;
    }

    /**
     * Sets the value of the responsabile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponsabile(String value) {
        this.responsabile = value;
    }

    /**
     * Gets the value of the userid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Sets the value of the userid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserid(String value) {
        this.userid = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the useridCopiaPermessi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseridCopiaPermessi() {
        return useridCopiaPermessi;
    }

    /**
     * Sets the value of the useridCopiaPermessi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseridCopiaPermessi(String value) {
        this.useridCopiaPermessi = value;
    }

    /**
     * Gets the value of the amministratore property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAmministratore() {
        return amministratore;
    }

    /**
     * Sets the value of the amministratore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAmministratore(Boolean value) {
        this.amministratore = value;
    }

    /**
     * Gets the value of the amministratoresoftware property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAmministratoresoftware() {
        return amministratoresoftware;
    }

    /**
     * Sets the value of the amministratoresoftware property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAmministratoresoftware(Boolean value) {
        this.amministratoresoftware = value;
    }

    /**
     * Gets the value of the softwareAbilitati property.
     * 
     * @return
     *     possible object is
     *     {@link SoftwareAblilitati }
     *     
     */
    public SoftwareAblilitati getSoftwareAbilitati() {
        return softwareAbilitati;
    }

    /**
     * Sets the value of the softwareAbilitati property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoftwareAblilitati }
     *     
     */
    public void setSoftwareAbilitati(SoftwareAblilitati value) {
        this.softwareAbilitati = value;
    }

    /**
     * Gets the value of the comuniAssociati property.
     * 
     * @return
     *     possible object is
     *     {@link ComuniAssociati }
     *     
     */
    public ComuniAssociati getComuniAssociati() {
        return comuniAssociati;
    }

    /**
     * Sets the value of the comuniAssociati property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComuniAssociati }
     *     
     */
    public void setComuniAssociati(ComuniAssociati value) {
        this.comuniAssociati = value;
    }

    /**
     * Gets the value of the readonly property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReadonly() {
        return readonly;
    }

    /**
     * Sets the value of the readonly property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReadonly(Boolean value) {
        this.readonly = value;
    }

    /**
     * Gets the value of the disabilitato property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDisabilitato() {
        return disabilitato;
    }

    /**
     * Sets the value of the disabilitato property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDisabilitato(Boolean value) {
        this.disabilitato = value;
    }

    /**
     * Gets the value of the anagrafe property.
     * 
     * @return
     *     possible object is
     *     {@link Anagrafe }
     *     
     */
    public Anagrafe getAnagrafe() {
        return anagrafe;
    }

    /**
     * Sets the value of the anagrafe property.
     * 
     * @param value
     *     allowed object is
     *     {@link Anagrafe }
     *     
     */
    public void setAnagrafe(Anagrafe value) {
        this.anagrafe = value;
    }

}
