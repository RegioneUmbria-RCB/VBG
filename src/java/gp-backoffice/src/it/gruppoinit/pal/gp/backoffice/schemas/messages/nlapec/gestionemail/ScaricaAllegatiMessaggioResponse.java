
package it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="messaggio" type="{http://gruppoinit.it/nlapec}MessaggioType"/>
 *         &lt;element name="allegati" type="{http://gruppoinit.it/nlapec}AllegatoMailType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="idaccount" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
    "messaggio",
    "allegati",
    "idaccount"
})
@XmlRootElement(name = "ScaricaAllegatiMessaggioResponse")
public class ScaricaAllegatiMessaggioResponse {

    @XmlElement(required = true)
    protected MessaggioType messaggio;
    protected List<AllegatoMailType> allegati;
    protected BigInteger idaccount;

    /**
     * Gets the value of the messaggio property.
     * 
     * @return
     *     possible object is
     *     {@link MessaggioType }
     *     
     */
    public MessaggioType getMessaggio() {
        return messaggio;
    }

    /**
     * Sets the value of the messaggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessaggioType }
     *     
     */
    public void setMessaggio(MessaggioType value) {
        this.messaggio = value;
    }

    /**
     * Gets the value of the allegati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allegati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllegati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AllegatoMailType }
     * 
     * 
     */
    public List<AllegatoMailType> getAllegati() {
        if (allegati == null) {
            allegati = new ArrayList<AllegatoMailType>();
        }
        return this.allegati;
    }

    /**
     * Gets the value of the idaccount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdaccount() {
        return idaccount;
    }

    /**
     * Sets the value of the idaccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdaccount(BigInteger value) {
        this.idaccount = value;
    }

}
