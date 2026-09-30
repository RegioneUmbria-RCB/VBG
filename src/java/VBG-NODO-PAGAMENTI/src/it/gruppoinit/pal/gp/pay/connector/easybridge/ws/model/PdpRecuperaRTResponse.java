
package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pdpRecuperaRTResult" type="{http://easybridge.eu/bridge/}Output" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pdpRecuperaRTResult"
})
@XmlRootElement(name = "pdpRecuperaRTResponse")
public class PdpRecuperaRTResponse {

    protected Output pdpRecuperaRTResult;

    /**
     * Recupera il valore della proprietà pdpRecuperaRTResult.
     * 
     * @return
     *     possible object is
     *     {@link Output }
     *     
     */
    public Output getPdpRecuperaRTResult() {
        return pdpRecuperaRTResult;
    }

    /**
     * Imposta il valore della proprietà pdpRecuperaRTResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Output }
     *     
     */
    public void setPdpRecuperaRTResult(Output value) {
        this.pdpRecuperaRTResult = value;
    }

}
