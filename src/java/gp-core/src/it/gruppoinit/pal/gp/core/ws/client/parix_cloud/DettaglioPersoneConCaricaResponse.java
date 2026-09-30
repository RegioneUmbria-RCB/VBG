
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
 *         &lt;element name="DettaglioPersoneConCaricaReturn" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "dettaglioPersoneConCaricaReturn"
})
@XmlRootElement(name = "DettaglioPersoneConCaricaResponse")
public class DettaglioPersoneConCaricaResponse {

    @XmlElement(name = "DettaglioPersoneConCaricaReturn", required = true)
    protected String dettaglioPersoneConCaricaReturn;

    /**
     * Gets the value of the dettaglioPersoneConCaricaReturn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDettaglioPersoneConCaricaReturn() {
        return dettaglioPersoneConCaricaReturn;
    }

    /**
     * Sets the value of the dettaglioPersoneConCaricaReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDettaglioPersoneConCaricaReturn(String value) {
        this.dettaglioPersoneConCaricaReturn = value;
    }

}
