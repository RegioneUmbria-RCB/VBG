
package it.init.sigepro.rte;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.init.sigepro.rte.types.DocumentiType;
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
 *         &lt;element name="idPraticaDest" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="documenti" type="{http://sigepro.init.it/rte/types}DocumentiType" maxOccurs="unbounded"/>
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
    "idPraticaDest",
    "documenti"
})
@XmlRootElement(name = "AggiungiDocumentiNLARequest")
public class AggiungiDocumentiNLARequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected SportelloType sportelloMittente;
    @XmlElement(required = true)
    protected SportelloType sportelloDestinatario;
    @XmlElement(required = true)
    protected String idPraticaDest;
    @XmlElement(required = true)
    protected List<DocumentiType> documenti;

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
     * Gets the value of the idPraticaDest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPraticaDest() {
        return idPraticaDest;
    }

    /**
     * Sets the value of the idPraticaDest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPraticaDest(String value) {
        this.idPraticaDest = value;
    }

    /**
     * Gets the value of the documenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentiType }
     * 
     * 
     */
    public List<DocumentiType> getDocumenti() {
        if (documenti == null) {
            documenti = new ArrayList<DocumentiType>();
        }
        return this.documenti;
    }

}
