
package it.gruppoinit.pal.gp.backoffice.schemas.messages.autorizzazioniaccessi;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RicercaAutorizzazioniAccessiResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RicercaAutorizzazioniAccessiResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="autorizzazione" type="{http://gruppoinit.it/sigepro/schemas/messages/autorizzazioniaccessi}DatiAutorizzazioneType"/>
 *         &lt;element name="operazioni" type="{http://gruppoinit.it/sigepro/schemas/messages/autorizzazioniaccessi}OperazioneAutorizzazioneType" maxOccurs="unbounded"/>
 *         &lt;element name="operazioniPermesse" type="{http://gruppoinit.it/sigepro/schemas/messages/autorizzazioniaccessi}OperazioniPermesseType"/>
 *         &lt;element name="numeroTransitiRimanenti" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="numeroTransitiConsentiti" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RicercaAutorizzazioniAccessiResponseType", propOrder = {
    "autorizzazione",
    "operazioni",
    "operazioniPermesse",
    "numeroTransitiRimanenti",
    "numeroTransitiConsentiti"
})
public class RicercaAutorizzazioniAccessiResponseType {

    @XmlElement(required = true)
    protected DatiAutorizzazioneType autorizzazione;
    @XmlElement(required = true)
    protected List<OperazioneAutorizzazioneType> operazioni;
    @XmlElement(required = true)
    protected OperazioniPermesseType operazioniPermesse;
    @XmlElement(required = true)
    protected BigInteger numeroTransitiRimanenti;
    protected BigInteger numeroTransitiConsentiti;

    /**
     * Gets the value of the autorizzazione property.
     * 
     * @return
     *     possible object is
     *     {@link DatiAutorizzazioneType }
     *     
     */
    public DatiAutorizzazioneType getAutorizzazione() {
        return autorizzazione;
    }

    /**
     * Sets the value of the autorizzazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiAutorizzazioneType }
     *     
     */
    public void setAutorizzazione(DatiAutorizzazioneType value) {
        this.autorizzazione = value;
    }

    /**
     * Gets the value of the operazioni property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the operazioni property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOperazioni().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OperazioneAutorizzazioneType }
     * 
     * 
     */
    public List<OperazioneAutorizzazioneType> getOperazioni() {
        if (operazioni == null) {
            operazioni = new ArrayList<OperazioneAutorizzazioneType>();
        }
        return this.operazioni;
    }

    /**
     * Gets the value of the operazioniPermesse property.
     * 
     * @return
     *     possible object is
     *     {@link OperazioniPermesseType }
     *     
     */
    public OperazioniPermesseType getOperazioniPermesse() {
        return operazioniPermesse;
    }

    /**
     * Sets the value of the operazioniPermesse property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperazioniPermesseType }
     *     
     */
    public void setOperazioniPermesse(OperazioniPermesseType value) {
        this.operazioniPermesse = value;
    }

    /**
     * Gets the value of the numeroTransitiRimanenti property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroTransitiRimanenti() {
        return numeroTransitiRimanenti;
    }

    /**
     * Sets the value of the numeroTransitiRimanenti property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroTransitiRimanenti(BigInteger value) {
        this.numeroTransitiRimanenti = value;
    }

    /**
     * Gets the value of the numeroTransitiConsentiti property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroTransitiConsentiti() {
        return numeroTransitiConsentiti;
    }

    /**
     * Sets the value of the numeroTransitiConsentiti property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroTransitiConsentiti(BigInteger value) {
        this.numeroTransitiConsentiti = value;
    }

}
