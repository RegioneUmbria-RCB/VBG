
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
 *         &lt;element name="DataUltimoAggiornamentoImpresaReturn" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "dataUltimoAggiornamentoImpresaReturn"
})
@XmlRootElement(name = "DataUltimoAggiornamentoImpresaResponse")
public class DataUltimoAggiornamentoImpresaResponse {

    @XmlElement(name = "DataUltimoAggiornamentoImpresaReturn", required = true)
    protected String dataUltimoAggiornamentoImpresaReturn;

    /**
     * Gets the value of the dataUltimoAggiornamentoImpresaReturn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataUltimoAggiornamentoImpresaReturn() {
        return dataUltimoAggiornamentoImpresaReturn;
    }

    /**
     * Sets the value of the dataUltimoAggiornamentoImpresaReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataUltimoAggiornamentoImpresaReturn(String value) {
        this.dataUltimoAggiornamentoImpresaReturn = value;
    }

}
