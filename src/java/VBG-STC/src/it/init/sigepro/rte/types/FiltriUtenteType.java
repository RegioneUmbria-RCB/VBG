
package it.init.sigepro.rte.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FiltriUtenteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FiltriUtenteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="cercaComeRichiedente" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="cercaComeAziendaRichiedente" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="cercaNeiSoggettiCollegati" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="cercaComeIntermediario" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FiltriUtenteType", propOrder = {
    "codiceFiscale"
})
public class FiltriUtenteType {

    @XmlElement(required = true)
    protected String codiceFiscale;
    @XmlAttribute(name = "cercaComeRichiedente")
    protected Boolean cercaComeRichiedente;
    @XmlAttribute(name = "cercaComeAziendaRichiedente")
    protected Boolean cercaComeAziendaRichiedente;
    @XmlAttribute(name = "cercaNeiSoggettiCollegati")
    protected Boolean cercaNeiSoggettiCollegati;
    @XmlAttribute(name = "cercaComeIntermediario")
    protected Boolean cercaComeIntermediario;

    /**
     * Gets the value of the codiceFiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Sets the value of the codiceFiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Gets the value of the cercaComeRichiedente property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCercaComeRichiedente() {
        return cercaComeRichiedente;
    }

    /**
     * Sets the value of the cercaComeRichiedente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCercaComeRichiedente(Boolean value) {
        this.cercaComeRichiedente = value;
    }

    /**
     * Gets the value of the cercaComeAziendaRichiedente property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCercaComeAziendaRichiedente() {
        return cercaComeAziendaRichiedente;
    }

    /**
     * Sets the value of the cercaComeAziendaRichiedente property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCercaComeAziendaRichiedente(Boolean value) {
        this.cercaComeAziendaRichiedente = value;
    }

    /**
     * Gets the value of the cercaNeiSoggettiCollegati property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCercaNeiSoggettiCollegati() {
        return cercaNeiSoggettiCollegati;
    }

    /**
     * Sets the value of the cercaNeiSoggettiCollegati property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCercaNeiSoggettiCollegati(Boolean value) {
        this.cercaNeiSoggettiCollegati = value;
    }

    /**
     * Gets the value of the cercaComeIntermediario property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCercaComeIntermediario() {
        return cercaComeIntermediario;
    }

    /**
     * Sets the value of the cercaComeIntermediario property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCercaComeIntermediario(Boolean value) {
        this.cercaComeIntermediario = value;
    }

}
