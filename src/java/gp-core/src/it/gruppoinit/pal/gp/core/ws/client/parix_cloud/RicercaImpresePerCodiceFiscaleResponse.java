
package it.gruppoinit.pal.gp.core.ws.client.parix_cloud;

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
 *         &lt;element name="RicercaImpresePerCodiceFiscaleReturn" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "ricercaImpresePerCodiceFiscaleReturn"
})
@XmlRootElement(name = "RicercaImpresePerCodiceFiscaleResponse")
public class RicercaImpresePerCodiceFiscaleResponse {

    @XmlElement(name = "RicercaImpresePerCodiceFiscaleReturn", required = true)
    protected String ricercaImpresePerCodiceFiscaleReturn;

    /**
     * Gets the value of the ricercaImpresePerCodiceFiscaleReturn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRicercaImpresePerCodiceFiscaleReturn() {
        return ricercaImpresePerCodiceFiscaleReturn;
    }

    /**
     * Sets the value of the ricercaImpresePerCodiceFiscaleReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRicercaImpresePerCodiceFiscaleReturn(String value) {
        this.ricercaImpresePerCodiceFiscaleReturn = value;
    }

}
