
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
 *         &lt;element name="DettaglioRidottoImpresaReturn" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "dettaglioRidottoImpresaReturn"
})
@XmlRootElement(name = "DettaglioRidottoImpresaResponse")
public class DettaglioRidottoImpresaResponse {

    @XmlElement(name = "DettaglioRidottoImpresaReturn", required = true)
    protected String dettaglioRidottoImpresaReturn;

    /**
     * Gets the value of the dettaglioRidottoImpresaReturn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDettaglioRidottoImpresaReturn() {
        return dettaglioRidottoImpresaReturn;
    }

    /**
     * Sets the value of the dettaglioRidottoImpresaReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDettaglioRidottoImpresaReturn(String value) {
        this.dettaglioRidottoImpresaReturn = value;
    }

}
