
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.server.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per CorpoNotifichePagamentoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CorpoNotifichePagamentoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ElencoNotifichePagamento"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="NotificaPagamento" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}NotificaPagamentoType" maxOccurs="1000"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CorpoNotifichePagamentoType", propOrder = {
    "elencoNotifichePagamento"
})
public class CorpoNotifichePagamentoType {

    @XmlElement(name = "ElencoNotifichePagamento", required = true)
    protected CorpoNotifichePagamentoType.ElencoNotifichePagamento elencoNotifichePagamento;

    /**
     * Recupera il valore della proprietà elencoNotifichePagamento.
     * 
     * @return
     *     possible object is
     *     {@link CorpoNotifichePagamentoType.ElencoNotifichePagamento }
     *     
     */
    public CorpoNotifichePagamentoType.ElencoNotifichePagamento getElencoNotifichePagamento() {
        return elencoNotifichePagamento;
    }

    /**
     * Imposta il valore della proprietà elencoNotifichePagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link CorpoNotifichePagamentoType.ElencoNotifichePagamento }
     *     
     */
    public void setElencoNotifichePagamento(CorpoNotifichePagamentoType.ElencoNotifichePagamento value) {
        this.elencoNotifichePagamento = value;
    }


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
     *         &lt;element name="NotificaPagamento" type="{http://www.csi.it/epay/epaywso/epaywso2enti/types}NotificaPagamentoType" maxOccurs="1000"/&gt;
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
        "notificaPagamento"
    })
    public static class ElencoNotifichePagamento {

        @XmlElement(name = "NotificaPagamento", required = true)
        protected List<NotificaPagamentoType> notificaPagamento;

        /**
         * Gets the value of the notificaPagamento property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the notificaPagamento property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getNotificaPagamento().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link NotificaPagamentoType }
         * 
         * 
         */
        public List<NotificaPagamentoType> getNotificaPagamento() {
            if (notificaPagamento == null) {
                notificaPagamento = new ArrayList<NotificaPagamentoType>();
            }
            return this.notificaPagamento;
        }

    }

}
