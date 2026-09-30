
package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *     &lt;extension base="{http://www.paevolution.com/ws/pagamenti_types/}PayRequestType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="posizione" type="{http://www.paevolution.com/ws/pagamenti_types/}ElencoPagamentiOfflineType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "posizione"
})
@XmlRootElement(name = "NotificaPagamentoOffline")
public class NotificaPagamentoOffline
    extends PayRequestType
{

    @XmlElement(required = true)
    protected ElencoPagamentiOfflineType posizione;

    /**
     * Recupera il valore della proprietà posizione.
     * 
     * @return
     *     possible object is
     *     {@link ElencoPagamentiOfflineType }
     *     
     */
    public ElencoPagamentiOfflineType getPosizione() {
        return posizione;
    }

    /**
     * Imposta il valore della proprietà posizione.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoPagamentiOfflineType }
     *     
     */
    public void setPosizione(ElencoPagamentiOfflineType value) {
        this.posizione = value;
    }

}
