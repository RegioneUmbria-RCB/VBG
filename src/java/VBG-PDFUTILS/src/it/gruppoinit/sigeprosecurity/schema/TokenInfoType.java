
package it.gruppoinit.sigeprosecurity.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TokenInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TokenInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contesto" type="{http://sigeprosecurity.gruppoinit.it/schema}ContestoType"/>
 *         &lt;element name="clientIp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="alias" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idcomune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firstrequest" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lastrequest" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TokenInfoType", propOrder = {
    "contesto",
    "clientIp",
    "alias",
    "idcomune",
    "firstrequest",
    "lastrequest",
    "userid"
})
public class TokenInfoType {

    @XmlElement(required = true)
    protected ContestoType contesto;
    @XmlElement(required = true)
    protected String clientIp;
    @XmlElement(required = true)
    protected String alias;
    @XmlElement(required = true)
    protected String idcomune;
    @XmlElement(required = true)
    protected String firstrequest;
    @XmlElement(required = true)
    protected String lastrequest;
    @XmlElement(required = true)
    protected String userid;

    /**
     * Gets the value of the contesto property.
     * 
     * @return
     *     possible object is
     *     {@link ContestoType }
     *     
     */
    public ContestoType getContesto() {
        return contesto;
    }

    /**
     * Sets the value of the contesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContestoType }
     *     
     */
    public void setContesto(ContestoType value) {
        this.contesto = value;
    }

    /**
     * Gets the value of the clientIp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientIp() {
        return clientIp;
    }

    /**
     * Sets the value of the clientIp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientIp(String value) {
        this.clientIp = value;
    }

    /**
     * Gets the value of the alias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the value of the alias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlias(String value) {
        this.alias = value;
    }

    /**
     * Gets the value of the idcomune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdcomune() {
        return idcomune;
    }

    /**
     * Sets the value of the idcomune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdcomune(String value) {
        this.idcomune = value;
    }

    /**
     * Gets the value of the firstrequest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstrequest() {
        return firstrequest;
    }

    /**
     * Sets the value of the firstrequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstrequest(String value) {
        this.firstrequest = value;
    }

    /**
     * Gets the value of the lastrequest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastrequest() {
        return lastrequest;
    }

    /**
     * Sets the value of the lastrequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastrequest(String value) {
        this.lastrequest = value;
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

}
