
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
 *         &lt;element name="DettaglioUnitaLocaliImpresaReturn" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "dettaglioUnitaLocaliImpresaReturn"
})
@XmlRootElement(name = "DettaglioUnitaLocaliImpresaResponse")
public class DettaglioUnitaLocaliImpresaResponse {

    @XmlElement(name = "DettaglioUnitaLocaliImpresaReturn", required = true)
    protected String dettaglioUnitaLocaliImpresaReturn;

    /**
     * Gets the value of the dettaglioUnitaLocaliImpresaReturn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDettaglioUnitaLocaliImpresaReturn() {
        return dettaglioUnitaLocaliImpresaReturn;
    }

    /**
     * Sets the value of the dettaglioUnitaLocaliImpresaReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDettaglioUnitaLocaliImpresaReturn(String value) {
        this.dettaglioUnitaLocaliImpresaReturn = value;
    }

}
