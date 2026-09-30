
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per datiSegnaPagatoPagamentoAttesoWs complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="datiSegnaPagatoPagamentoAttesoWs"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="segnaPagatoPagamentoAttesoWs" type="{it/lineacomune/pagopa/be/ws/endpoint/public}segnaPagatoPagamentoAttesoWs"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiSegnaPagatoPagamentoAttesoWs", propOrder = {
    "segnaPagatoPagamentoAttesoWs"
})
public class DatiSegnaPagatoPagamentoAttesoWs {

    @XmlElement(required = true)
    protected SegnaPagatoPagamentoAttesoWs segnaPagatoPagamentoAttesoWs;

    /**
     * Recupera il valore della proprietà segnaPagatoPagamentoAttesoWs.
     * 
     * @return
     *     possible object is
     *     {@link SegnaPagatoPagamentoAttesoWs }
     *     
     */
    public SegnaPagatoPagamentoAttesoWs getSegnaPagatoPagamentoAttesoWs() {
        return segnaPagatoPagamentoAttesoWs;
    }

    /**
     * Imposta il valore della proprietà segnaPagatoPagamentoAttesoWs.
     * 
     * @param value
     *     allowed object is
     *     {@link SegnaPagatoPagamentoAttesoWs }
     *     
     */
    public void setSegnaPagatoPagamentoAttesoWs(SegnaPagatoPagamentoAttesoWs value) {
        this.segnaPagatoPagamentoAttesoWs = value;
    }

}
