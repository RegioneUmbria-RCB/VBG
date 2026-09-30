
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
 *         &lt;element name="pdpInviaCarrelloRPTResult" type="{http://easybridge.eu/bridge/}Output" minOccurs="0"/&gt;
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
    "pdpInviaCarrelloRPTResult"
})
@XmlRootElement(name = "pdpInviaCarrelloRPTResponse")
public class PdpInviaCarrelloRPTResponse {

    protected Output pdpInviaCarrelloRPTResult;

    /**
     * Recupera il valore della proprietà pdpInviaCarrelloRPTResult.
     * 
     * @return
     *     possible object is
     *     {@link Output }
     *     
     */
    public Output getPdpInviaCarrelloRPTResult() {
        return pdpInviaCarrelloRPTResult;
    }

    /**
     * Imposta il valore della proprietà pdpInviaCarrelloRPTResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Output }
     *     
     */
    public void setPdpInviaCarrelloRPTResult(Output value) {
        this.pdpInviaCarrelloRPTResult = value;
    }

}
