
package it.gruppoinit.mailservice.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MailMessageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MailMessageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="inviaComeHtml" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="mittente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="corpoMail" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="destinatari" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="destinatariInCopia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="destinatariInCopiaNascosta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="messageID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attachments" type="{http://gruppoinit.it/mailService/schemas/messages}AttachmentsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MailMessageType", propOrder = {
    "inviaComeHtml",
    "mittente",
    "corpoMail",
    "oggetto",
    "destinatari",
    "destinatariInCopia",
    "destinatariInCopiaNascosta",
    "messageID",
    "attachments"
})
public class MailMessageType {

    protected Boolean inviaComeHtml;
    protected String mittente;
    @XmlElement(required = true)
    protected String corpoMail;
    @XmlElement(required = true)
    protected String oggetto;
    @XmlElement(required = true)
    protected String destinatari;
    protected String destinatariInCopia;
    protected String destinatariInCopiaNascosta;
    protected String messageID;
    protected AttachmentsType attachments;

    /**
     * Gets the value of the inviaComeHtml property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInviaComeHtml() {
        return inviaComeHtml;
    }

    /**
     * Sets the value of the inviaComeHtml property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInviaComeHtml(Boolean value) {
        this.inviaComeHtml = value;
    }

    /**
     * Gets the value of the mittente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMittente() {
        return mittente;
    }

    /**
     * Sets the value of the mittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMittente(String value) {
        this.mittente = value;
    }

    /**
     * Gets the value of the corpoMail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorpoMail() {
        return corpoMail;
    }

    /**
     * Sets the value of the corpoMail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorpoMail(String value) {
        this.corpoMail = value;
    }

    /**
     * Gets the value of the oggetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Sets the value of the oggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Gets the value of the destinatari property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinatari() {
        return destinatari;
    }

    /**
     * Sets the value of the destinatari property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinatari(String value) {
        this.destinatari = value;
    }

    /**
     * Gets the value of the destinatariInCopia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinatariInCopia() {
        return destinatariInCopia;
    }

    /**
     * Sets the value of the destinatariInCopia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinatariInCopia(String value) {
        this.destinatariInCopia = value;
    }

    /**
     * Gets the value of the destinatariInCopiaNascosta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinatariInCopiaNascosta() {
        return destinatariInCopiaNascosta;
    }

    /**
     * Sets the value of the destinatariInCopiaNascosta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinatariInCopiaNascosta(String value) {
        this.destinatariInCopiaNascosta = value;
    }

    /**
     * Gets the value of the messageID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageID() {
        return messageID;
    }

    /**
     * Sets the value of the messageID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageID(String value) {
        this.messageID = value;
    }

    /**
     * Gets the value of the attachments property.
     * 
     * @return
     *     possible object is
     *     {@link AttachmentsType }
     *     
     */
    public AttachmentsType getAttachments() {
        return attachments;
    }

    /**
     * Sets the value of the attachments property.
     * 
     * @param value
     *     allowed object is
     *     {@link AttachmentsType }
     *     
     */
    public void setAttachments(AttachmentsType value) {
        this.attachments = value;
    }

}
