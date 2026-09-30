
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiAnagraficiType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiAnagraficiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Cod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Mezzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ModalitaTrasmissione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiAnagraficiType", propOrder = {
    "cod",
    "mezzo",
    "modalitaTrasmissione",
    "email"
})
public class DatiAnagraficiType {

    @XmlElement(name = "Cod", nillable = true)
    protected String cod;
    @XmlElement(name = "Mezzo", nillable = true)
    protected String mezzo;
    @XmlElement(name = "ModalitaTrasmissione", nillable = true)
    protected String modalitaTrasmissione;
    @XmlElement(name = "Email", nillable = true)
    protected String email;

    /**
     * Gets the value of the cod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCod() {
        return cod;
    }

    /**
     * Sets the value of the cod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCod(String value) {
        this.cod = value;
    }

    /**
     * Gets the value of the mezzo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMezzo() {
        return mezzo;
    }

    /**
     * Sets the value of the mezzo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMezzo(String value) {
        this.mezzo = value;
    }

    /**
     * Gets the value of the modalitaTrasmissione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModalitaTrasmissione() {
        return modalitaTrasmissione;
    }

    /**
     * Sets the value of the modalitaTrasmissione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModalitaTrasmissione(String value) {
        this.modalitaTrasmissione = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

}
