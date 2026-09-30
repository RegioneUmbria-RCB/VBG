
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per datiNuovoPagamentoAttesoWs complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="datiNuovoPagamentoAttesoWs"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nuovoPagamentoAttesoWs" type="{it/lineacomune/pagopa/be/ws/endpoint/public}nuovoPagamentoAttesoWs"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiNuovoPagamentoAttesoWs", propOrder = {
    "nuovoPagamentoAttesoWs"
})
public class DatiNuovoPagamentoAttesoWs {

    @XmlElement(required = true)
    protected NuovoPagamentoAttesoWs nuovoPagamentoAttesoWs;

    /**
     * Recupera il valore della proprietà nuovoPagamentoAttesoWs.
     * 
     * @return
     *     possible object is
     *     {@link NuovoPagamentoAttesoWs }
     *     
     */
    public NuovoPagamentoAttesoWs getNuovoPagamentoAttesoWs() {
        return nuovoPagamentoAttesoWs;
    }

    /**
     * Imposta il valore della proprietà nuovoPagamentoAttesoWs.
     * 
     * @param value
     *     allowed object is
     *     {@link NuovoPagamentoAttesoWs }
     *     
     */
    public void setNuovoPagamentoAttesoWs(NuovoPagamentoAttesoWs value) {
        this.nuovoPagamentoAttesoWs = value;
    }

}
