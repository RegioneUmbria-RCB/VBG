
package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.caricaposizioni.schema.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per TRTSender complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="TRTSender"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SenderId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SenderSys" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TRTSender", propOrder = {
    "senderId",
    "senderSys"
})
public class TRTSender {

    @XmlElement(name = "SenderId", required = true)
    protected String senderId;
    @XmlElement(name = "SenderSys", required = true)
    protected String senderSys;

    /**
     * Recupera il valore della proprietà senderId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * Imposta il valore della proprietà senderId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderId(String value) {
        this.senderId = value;
    }

    /**
     * Recupera il valore della proprietà senderSys.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderSys() {
        return senderSys;
    }

    /**
     * Imposta il valore della proprietà senderSys.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderSys(String value) {
        this.senderSys = value;
    }

}
