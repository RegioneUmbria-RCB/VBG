
package it.alveo.ricalcoloaree.sigeprosecurity;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.</p>
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.</p>
 * 
 * <pre>{@code
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="tokenPartnerApp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="codicecomune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         <element name="software" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "token",
    "tokenPartnerApp",
    "codicecomune",
    "software"
})
@XmlRootElement(name = "SetTokenPartnerAppPerComuneESoftwareRequest")
public class SetTokenPartnerAppPerComuneESoftwareRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String tokenPartnerApp;
    protected String codicecomune;
    protected String software;

    /**
     * Recupera il valore della proprietà token.
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
     * Imposta il valore della proprietà token.
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
     * Recupera il valore della proprietà tokenPartnerApp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenPartnerApp() {
        return tokenPartnerApp;
    }

    /**
     * Imposta il valore della proprietà tokenPartnerApp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenPartnerApp(String value) {
        this.tokenPartnerApp = value;
    }

    /**
     * Recupera il valore della proprietà codicecomune.
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
     * Imposta il valore della proprietà codicecomune.
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
     * Recupera il valore della proprietà software.
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
     * Imposta il valore della proprietà software.
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
