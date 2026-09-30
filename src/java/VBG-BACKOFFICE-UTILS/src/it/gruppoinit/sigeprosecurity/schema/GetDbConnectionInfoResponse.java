
package it.gruppoinit.sigeprosecurity.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="alias" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="idComune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="connectionString" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dbUser" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dbPassword" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="provider" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dbOwner" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="dbMsName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
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
     * Recupera il valore della proprietà alias.
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
     * Imposta il valore della proprietà alias.
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
     * Recupera il valore della proprietà idComune.
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
     * Imposta il valore della proprietà idComune.
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
     * Recupera il valore della proprietà connectionString.
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
     * Imposta il valore della proprietà connectionString.
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
     * Recupera il valore della proprietà dbUser.
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
     * Imposta il valore della proprietà dbUser.
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
     * Recupera il valore della proprietà dbPassword.
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
     * Imposta il valore della proprietà dbPassword.
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
     * Recupera il valore della proprietà provider.
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
     * Imposta il valore della proprietà provider.
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
     * Recupera il valore della proprietà dbOwner.
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
     * Imposta il valore della proprietà dbOwner.
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
     * Recupera il valore della proprietà dbMsName.
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
     * Imposta il valore della proprietà dbMsName.
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
