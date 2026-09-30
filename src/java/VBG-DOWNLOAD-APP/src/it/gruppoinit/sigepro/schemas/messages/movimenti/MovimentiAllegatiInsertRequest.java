
package it.gruppoinit.sigepro.schemas.messages.movimenti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.gruppoinit.sigepro.schemas.messages.base.AllegatoBaseType;


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
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizione">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="4000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="note" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="4000"/>
 *               &lt;minLength value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="codicemovimento" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="flagPubblica" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="stcIdDocumento" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="200"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="stcIdAllegato" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="0"/>
 *               &lt;maxLength value="200"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="dataRegistrazione" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="allegato" type="{http://gruppoinit.it/sigepro/schemas/messages/base}AllegatoBaseType"/>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "token",
    "descrizione",
    "note",
    "codicemovimento",
    "flagPubblica",
    "stcIdDocumento",
    "stcIdAllegato",
    "dataRegistrazione",
    "allegato",
    "software"
})
@XmlRootElement(name = "MovimentiAllegatiInsertRequest")
public class MovimentiAllegatiInsertRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String descrizione;
    protected String note;
    protected int codicemovimento;
    protected Boolean flagPubblica;
    protected String stcIdDocumento;
    protected String stcIdAllegato;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataRegistrazione;
    @XmlElement(required = true)
    protected AllegatoBaseType allegato;
    @XmlElement(required = true)
    protected String software;

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

    /**
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the codicemovimento property.
     * 
     */
    public int getCodicemovimento() {
        return codicemovimento;
    }

    /**
     * Sets the value of the codicemovimento property.
     * 
     */
    public void setCodicemovimento(int value) {
        this.codicemovimento = value;
    }

    /**
     * Gets the value of the flagPubblica property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlagPubblica() {
        return flagPubblica;
    }

    /**
     * Sets the value of the flagPubblica property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlagPubblica(Boolean value) {
        this.flagPubblica = value;
    }

    /**
     * Gets the value of the stcIdDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStcIdDocumento() {
        return stcIdDocumento;
    }

    /**
     * Sets the value of the stcIdDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStcIdDocumento(String value) {
        this.stcIdDocumento = value;
    }

    /**
     * Gets the value of the stcIdAllegato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStcIdAllegato() {
        return stcIdAllegato;
    }

    /**
     * Sets the value of the stcIdAllegato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStcIdAllegato(String value) {
        this.stcIdAllegato = value;
    }

    /**
     * Gets the value of the dataRegistrazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRegistrazione() {
        return dataRegistrazione;
    }

    /**
     * Sets the value of the dataRegistrazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRegistrazione(XMLGregorianCalendar value) {
        this.dataRegistrazione = value;
    }

    /**
     * Gets the value of the allegato property.
     * 
     * @return
     *     possible object is
     *     {@link AllegatoBaseType }
     *     
     */
    public AllegatoBaseType getAllegato() {
        return allegato;
    }

    /**
     * Sets the value of the allegato property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllegatoBaseType }
     *     
     */
    public void setAllegato(AllegatoBaseType value) {
        this.allegato = value;
    }

    /**
     * Gets the value of the software property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSoftware() {
        return software;
    }

    /**
     * Sets the value of the software property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftware(String value) {
        this.software = value;
    }

}
