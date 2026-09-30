
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.verificaposizioni.schema.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per HeaderTRT complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="HeaderTRT"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ServiceName" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}ServiceName"/&gt;
 *         &lt;element name="MsgId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="XMLCrtDt" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/&gt;
 *         &lt;element name="Sender" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}TRTSender"/&gt;
 *         &lt;element name="Receiver" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}TRTReceiver"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderTRT", propOrder = {
    "serviceName",
    "msgId",
    "xmlCrtDt",
    "sender",
    "receiver"
})
public class HeaderTRT {

    @XmlElement(name = "ServiceName", required = true)
    @XmlSchemaType(name = "string")
    protected ServiceName serviceName;
    @XmlElement(name = "MsgId", required = true)
    protected String msgId;
    @XmlElement(name = "XMLCrtDt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar xmlCrtDt;
    @XmlElement(name = "Sender", required = true)
    protected TRTSender sender;
    @XmlElement(name = "Receiver", required = true)
    protected TRTReceiver receiver;

    /**
     * Recupera il valore della proprietà serviceName.
     * 
     * @return
     *     possible object is
     *     {@link ServiceName }
     *     
     */
    public ServiceName getServiceName() {
        return serviceName;
    }

    /**
     * Imposta il valore della proprietà serviceName.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceName }
     *     
     */
    public void setServiceName(ServiceName value) {
        this.serviceName = value;
    }

    /**
     * Recupera il valore della proprietà msgId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * Imposta il valore della proprietà msgId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgId(String value) {
        this.msgId = value;
    }

    /**
     * Recupera il valore della proprietà xmlCrtDt.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getXMLCrtDt() {
        return xmlCrtDt;
    }

    /**
     * Imposta il valore della proprietà xmlCrtDt.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setXMLCrtDt(XMLGregorianCalendar value) {
        this.xmlCrtDt = value;
    }


    /**
     * Recupera il valore della proprietà sender.
     * 
     * @return
     *     possible object is
     *     {@link TRTSender }
     *     
     */
    public TRTSender getSender() {
        return sender;
    }

    /**
     * Imposta il valore della proprietà sender.
     * 
     * @param value
     *     allowed object is
     *     {@link TRTSender }
     *     
     */
    public void setSender(TRTSender value) {
        this.sender = value;
    }

    /**
     * Recupera il valore della proprietà receiver.
     * 
     * @return
     *     possible object is
     *     {@link TRTReceiver }
     *     
     */
    public TRTReceiver getReceiver() {
        return receiver;
    }

    /**
     * Imposta il valore della proprietà receiver.
     * 
     * @param value
     *     allowed object is
     *     {@link TRTReceiver }
     *     
     */
    public void setReceiver(TRTReceiver value) {
        this.receiver = value;
    }

}
