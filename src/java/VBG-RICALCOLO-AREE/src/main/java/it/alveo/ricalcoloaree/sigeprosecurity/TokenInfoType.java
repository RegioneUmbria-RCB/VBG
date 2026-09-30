
package it.alveo.ricalcoloaree.sigeprosecurity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TokenInfoType complex type.</p>
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.</p>
 * 
 * <pre>{@code
 * <complexType name="TokenInfoType">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="contesto" type="{http://sigeprosecurity.gruppoinit.it/schema}ContestoType"/>
 *         <element name="clientIp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="alias" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="idcomune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="firstrequest" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="lastrequest" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="userid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
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
    @XmlSchemaType(name = "string")
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
     * Recupera il valore della proprietà contesto.
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
     * Imposta il valore della proprietà contesto.
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
     * Recupera il valore della proprietà clientIp.
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
     * Imposta il valore della proprietà clientIp.
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
     * Recupera il valore della proprietà idcomune.
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
     * Imposta il valore della proprietà idcomune.
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
     * Recupera il valore della proprietà firstrequest.
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
     * Imposta il valore della proprietà firstrequest.
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
     * Recupera il valore della proprietà lastrequest.
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
     * Imposta il valore della proprietà lastrequest.
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
     * Recupera il valore della proprietà userid.
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
     * Imposta il valore della proprietà userid.
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
