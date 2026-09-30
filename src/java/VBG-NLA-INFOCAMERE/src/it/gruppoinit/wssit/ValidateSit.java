
package it.gruppoinit.wssit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidateSit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidateSit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ReturnValue" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="MessageCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataSit" type="{http://init.sigepro.it}Sit" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateSit", propOrder = {
    "returnValue",
    "messageCode",
    "message",
    "dataSit"
})
public class ValidateSit {

    @XmlElement(name = "ReturnValue")
    protected boolean returnValue;
    @XmlElement(name = "MessageCode")
    protected String messageCode;
    @XmlElement(name = "Message")
    protected String message;
    @XmlElement(name = "DataSit")
    protected Sit dataSit;

    /**
     * Gets the value of the returnValue property.
     * 
     */
    public boolean isReturnValue() {
        return returnValue;
    }

    /**
     * Sets the value of the returnValue property.
     * 
     */
    public void setReturnValue(boolean value) {
        this.returnValue = value;
    }

    /**
     * Gets the value of the messageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageCode() {
        return messageCode;
    }

    /**
     * Sets the value of the messageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageCode(String value) {
        this.messageCode = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the dataSit property.
     * 
     * @return
     *     possible object is
     *     {@link Sit }
     *     
     */
    public Sit getDataSit() {
        return dataSit;
    }

    /**
     * Sets the value of the dataSit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sit }
     *     
     */
    public void setDataSit(Sit value) {
        this.dataSit = value;
    }

}
