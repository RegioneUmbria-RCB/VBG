
package it.gruppoinit.mailservice.schemas.messages;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="accountid" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codicecomune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codicemovimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mailMessage" type="{http://gruppoinit.it/mailService/schemas/messages}MailMessageType"/>
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
    "accountid",
    "software",
    "codicecomune",
    "codicemovimento",
    "mailMessage"
})
@XmlRootElement(name = "MessageRequest2")
public class MessageRequest2 {

    @XmlElement(required = true)
    protected String token;
    protected BigInteger accountid;
    @XmlElement(required = true)
    protected String software;
    protected String codicecomune;
    protected String codicemovimento;
    @XmlElement(required = true)
    protected MailMessageType mailMessage;

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
     * Gets the value of the accountid property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAccountid() {
        return accountid;
    }

    /**
     * Sets the value of the accountid property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAccountid(BigInteger value) {
        this.accountid = value;
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

    /**
     * Gets the value of the codicecomune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodicecomune() {
        return codicecomune;
    }

    /**
     * Sets the value of the codicecomune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodicecomune(String value) {
        this.codicecomune = value;
    }

    /**
     * Gets the value of the codicemovimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodicemovimento() {
        return codicemovimento;
    }

    /**
     * Sets the value of the codicemovimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodicemovimento(String value) {
        this.codicemovimento = value;
    }

    /**
     * Gets the value of the mailMessage property.
     * 
     * @return
     *     possible object is
     *     {@link MailMessageType }
     *     
     */
    public MailMessageType getMailMessage() {
        return mailMessage;
    }

    /**
     * Sets the value of the mailMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link MailMessageType }
     *     
     */
    public void setMailMessage(MailMessageType value) {
        this.mailMessage = value;
    }

}
