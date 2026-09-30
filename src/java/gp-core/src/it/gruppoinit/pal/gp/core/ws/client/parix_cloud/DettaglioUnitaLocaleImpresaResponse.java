
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
 *         &lt;element name="DettaglioUnitaLocaleImpresaReturn" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "dettaglioUnitaLocaleImpresaReturn"
})
@XmlRootElement(name = "DettaglioUnitaLocaleImpresaResponse")
public class DettaglioUnitaLocaleImpresaResponse {

    @XmlElement(name = "DettaglioUnitaLocaleImpresaReturn", required = true)
    protected String dettaglioUnitaLocaleImpresaReturn;

    /**
     * Gets the value of the dettaglioUnitaLocaleImpresaReturn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDettaglioUnitaLocaleImpresaReturn() {
        return dettaglioUnitaLocaleImpresaReturn;
    }

    /**
     * Sets the value of the dettaglioUnitaLocaleImpresaReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDettaglioUnitaLocaleImpresaReturn(String value) {
        this.dettaglioUnitaLocaleImpresaReturn = value;
    }

}
