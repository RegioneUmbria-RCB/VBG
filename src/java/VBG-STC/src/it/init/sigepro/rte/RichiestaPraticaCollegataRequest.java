
package it.init.sigepro.rte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.init.sigepro.rte.types.SportelloType;


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
 *         &lt;element name="sportelloMittente" type="{http://sigepro.init.it/rte/types}SportelloType"/>
 *         &lt;element name="sportelloDestinatario" type="{http://sigepro.init.it/rte/types}SportelloType"/>
 *         &lt;element name="idPraticaMitt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idProcedimentoMitt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "sportelloMittente",
    "sportelloDestinatario",
    "idPraticaMitt",
    "idProcedimentoMitt"
})
@XmlRootElement(name = "RichiestaPraticaCollegataRequest")
public class RichiestaPraticaCollegataRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected SportelloType sportelloMittente;
    @XmlElement(required = true)
    protected SportelloType sportelloDestinatario;
    @XmlElement(required = true)
    protected String idPraticaMitt;
    protected String idProcedimentoMitt;

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
     * Gets the value of the sportelloMittente property.
     * 
     * @return
     *     possible object is
     *     {@link SportelloType }
     *     
     */
    public SportelloType getSportelloMittente() {
        return sportelloMittente;
    }

    /**
     * Sets the value of the sportelloMittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link SportelloType }
     *     
     */
    public void setSportelloMittente(SportelloType value) {
        this.sportelloMittente = value;
    }

    /**
     * Gets the value of the sportelloDestinatario property.
     * 
     * @return
     *     possible object is
     *     {@link SportelloType }
     *     
     */
    public SportelloType getSportelloDestinatario() {
        return sportelloDestinatario;
    }

    /**
     * Sets the value of the sportelloDestinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link SportelloType }
     *     
     */
    public void setSportelloDestinatario(SportelloType value) {
        this.sportelloDestinatario = value;
    }

    /**
     * Gets the value of the idPraticaMitt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPraticaMitt() {
        return idPraticaMitt;
    }

    /**
     * Sets the value of the idPraticaMitt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPraticaMitt(String value) {
        this.idPraticaMitt = value;
    }

    /**
     * Gets the value of the idProcedimentoMitt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdProcedimentoMitt() {
        return idProcedimentoMitt;
    }

    /**
     * Sets the value of the idProcedimentoMitt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdProcedimentoMitt(String value) {
        this.idProcedimentoMitt = value;
    }

}
