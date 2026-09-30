
package it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice;

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
 *         &lt;element name="famigliaEndoprocedimento" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}FamigliaEndoprocedimento" maxOccurs="unbounded"/>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="aggiorna" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "famigliaEndoprocedimento",
    "software",
    "token",
    "aggiorna"
})
@XmlRootElement(name = "EndoprocedimentiRequest")
public class EndoprocedimentiRequest {

    @XmlElement(required = true)
    protected List<FamigliaEndoprocedimento> famigliaEndoprocedimento;
    @XmlElement(required = true)
    protected String software;
    @XmlElement(required = true)
    protected String token;
    protected boolean aggiorna;

    /**
     * Gets the value of the famigliaEndoprocedimento property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the famigliaEndoprocedimento property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFamigliaEndoprocedimento().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FamigliaEndoprocedimento }
     * 
     * 
     */
    public List<FamigliaEndoprocedimento> getFamigliaEndoprocedimento() {
        if (famigliaEndoprocedimento == null) {
            famigliaEndoprocedimento = new ArrayList<FamigliaEndoprocedimento>();
        }
        return this.famigliaEndoprocedimento;
    }

    /**
     * Gets the value of the software property.
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
     * Sets the value of the software property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSoftware(String value) {
        this.software = value;
    }

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
     * Gets the value of the aggiorna property.
     * 
     */
    public boolean isAggiorna() {
        return aggiorna;
    }

    /**
     * Sets the value of the aggiorna property.
     * 
     */
    public void setAggiorna(boolean value) {
        this.aggiorna = value;
    }

}
