
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
 *         &lt;element name="alberoprocEndo" type="{http://gruppoinit.it/sigepro/schemas/messages/archiviBackoffice}AlberoprocEndo" maxOccurs="unbounded"/>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "alberoprocEndo",
    "software",
    "token"
})
@XmlRootElement(name = "AlberoprocEndoRequest")
public class AlberoprocEndoRequest {

    @XmlElement(required = true)
    protected List<AlberoprocEndo> alberoprocEndo;
    @XmlElement(required = true)
    protected String software;
    @XmlElement(required = true)
    protected String token;

    /**
     * Gets the value of the alberoprocEndo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alberoprocEndo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlberoprocEndo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AlberoprocEndo }
     * 
     * 
     */
    public List<AlberoprocEndo> getAlberoprocEndo() {
        if (alberoprocEndo == null) {
            alberoprocEndo = new ArrayList<AlberoprocEndo>();
        }
        return this.alberoprocEndo;
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

}
