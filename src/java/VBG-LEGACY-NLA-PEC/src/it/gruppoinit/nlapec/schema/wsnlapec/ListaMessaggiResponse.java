
package it.gruppoinit.nlapec.schema.wsnlapec;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="messaggio" type="{http://gruppoinit.it/nlapec}MessaggioType" maxOccurs="unbounded" minOccurs="0"/>
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
    "idaccount"
})
@XmlRootElement(name = "ListaMessaggiResponse")
public class ListaMessaggiResponse {

    protected List<MessaggioType> messaggio;
    protected BigInteger idaccount;

    /**
     * Gets the value of the messaggio property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the messaggio property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessaggio().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MessaggioType }
     * 
     * 
     */
    public List<MessaggioType> getMessaggio() {
        if (messaggio == null) {
            messaggio = new ArrayList<MessaggioType>();
        }
        return this.messaggio;
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
