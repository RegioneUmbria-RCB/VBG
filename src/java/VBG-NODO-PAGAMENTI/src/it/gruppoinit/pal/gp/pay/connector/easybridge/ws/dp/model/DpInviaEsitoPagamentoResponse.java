
package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.dp.model;

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
 *         &lt;element name="dpInviaEsitoPagamentoResult" type="{http://easybridge.eu/bridge/}Output" minOccurs="0"/&gt;
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
    "dpInviaEsitoPagamentoResult"
})
@XmlRootElement(name = "dpInviaEsitoPagamentoResponse")
public class DpInviaEsitoPagamentoResponse {

    protected Output dpInviaEsitoPagamentoResult;

    /**
     * Recupera il valore della proprietà pdpChiediStatoRPTResult.
     * 
     * @return
     *     possible object is
     *     {@link Output }
     *     
     */
    public Output getDpInviaEsitoPagamentoResult() {
        return dpInviaEsitoPagamentoResult;
    }

    /**
     * Imposta il valore della proprietà pdpChiediStatoRPTResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Output }
     *     
     */
    public void setDpInviaEsitoPagamentoResult(Output value) {
        this.dpInviaEsitoPagamentoResult = value;
    }

}
