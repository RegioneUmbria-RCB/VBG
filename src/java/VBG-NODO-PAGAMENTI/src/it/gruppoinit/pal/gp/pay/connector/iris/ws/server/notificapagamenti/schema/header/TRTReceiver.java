
package it.gruppoinit.pal.gp.pay.connector.iris.ws.server.notificapagamenti.schema.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TRTReceiver complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="TRTReceiver"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ReceiverId" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/&gt;
 *         &lt;element name="ReceiverSys" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpInclude}Max35Text"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TRTReceiver", propOrder = {
    "receiverId",
    "receiverSys"
})
public class TRTReceiver {

    @XmlElement(name = "ReceiverId", required = true)
    protected String receiverId;
    @XmlElement(name = "ReceiverSys", required = true)
    protected String receiverSys;

    /**
     * Recupera il valore della proprietà receiverId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
     * Imposta il valore della proprietà receiverId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiverId(String value) {
        this.receiverId = value;
    }

    /**
     * Recupera il valore della proprietà receiverSys.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceiverSys() {
        return receiverSys;
    }

    /**
     * Imposta il valore della proprietà receiverSys.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceiverSys(String value) {
        this.receiverSys = value;
    }

}
