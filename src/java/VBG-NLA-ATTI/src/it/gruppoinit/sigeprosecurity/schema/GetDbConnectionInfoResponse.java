
package it.gruppoinit.sigeprosecurity.schema;

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
 *         &lt;element name="alias" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idComune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="connectionString" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dbUser" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dbPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="provider" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dbOwner" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dbMsName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "alias",
    "idComune",
    "connectionString",
    "dbUser",
    "dbPassword",
    "provider",
    "dbOwner",
    "dbMsName"
})
@XmlRootElement(name = "GetDbConnectionInfoResponse")
public class GetDbConnectionInfoResponse {

    @XmlElement(required = true)
    protected String alias;
    protected String idComune;
    @XmlElement(required = true)
    protected String connectionString;
    @XmlElement(required = true)
    protected String dbUser;
    @XmlElement(required = true)
    protected String dbPassword;
    protected String provider;
    @XmlElement(required = true)
    protected String dbOwner;
    protected String dbMsName;

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
     * Gets the value of the idComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdComune() {
        return idComune;
    }

    /**
     * Sets the value of the idComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdComune(String value) {
        this.idComune = value;
    }

    /**
     * Gets the value of the connectionString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnectionString() {
        return connectionString;
    }

    /**
     * Sets the value of the connectionString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnectionString(String value) {
        this.connectionString = value;
    }

    /**
     * Gets the value of the dbUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDbUser() {
        return dbUser;
    }

    /**
     * Sets the value of the dbUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDbUser(String value) {
        this.dbUser = value;
    }

    /**
     * Gets the value of the dbPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDbPassword() {
        return dbPassword;
    }

    /**
     * Sets the value of the dbPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDbPassword(String value) {
        this.dbPassword = value;
    }

    /**
     * Gets the value of the provider property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvider() {
        return provider;
    }

    /**
     * Sets the value of the provider property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvider(String value) {
        this.provider = value;
    }

    /**
     * Gets the value of the dbOwner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDbOwner() {
        return dbOwner;
    }

    /**
     * Sets the value of the dbOwner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDbOwner(String value) {
        this.dbOwner = value;
    }

    /**
     * Gets the value of the dbMsName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDbMsName() {
        return dbMsName;
    }

    /**
     * Sets the value of the dbMsName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDbMsName(String value) {
        this.dbMsName = value;
    }

}
