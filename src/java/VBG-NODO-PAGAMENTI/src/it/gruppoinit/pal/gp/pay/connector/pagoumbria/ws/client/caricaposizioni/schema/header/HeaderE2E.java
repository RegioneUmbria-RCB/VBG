
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema.header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per HeaderE2E complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="HeaderE2E"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="E2ESrvcNm" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="E2EMsgId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="XMLCrtDt" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/&gt;
 *         &lt;element name="Sender" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}E2ESender"/&gt;
 *         &lt;element name="Receiver" type="{http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader}E2EReceiver"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderE2E", propOrder = {
    "e2ESrvcNm",
    "e2EMsgId",
    "xmlCrtDt",
    "sender",
    "receiver"
})
public class HeaderE2E {

    @XmlElement(name = "E2ESrvcNm", required = true)
    protected String e2ESrvcNm;
    @XmlElement(name = "E2EMsgId", required = true)
    protected String e2EMsgId;
    @XmlElement(name = "XMLCrtDt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar xmlCrtDt;
    @XmlElement(name = "Sender", required = true)
    protected E2ESender sender;
    @XmlElement(name = "Receiver", required = true)
    protected E2EReceiver receiver;

    /**
     * Recupera il valore della proprietà e2ESrvcNm.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE2ESrvcNm() {
        return e2ESrvcNm;
    }

    /**
     * Imposta il valore della proprietà e2ESrvcNm.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE2ESrvcNm(String value) {
        this.e2ESrvcNm = value;
    }

    /**
     * Recupera il valore della proprietà e2EMsgId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE2EMsgId() {
        return e2EMsgId;
    }

    /**
     * Imposta il valore della proprietà e2EMsgId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE2EMsgId(String value) {
        this.e2EMsgId = value;
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
     *     {@link E2ESender }
     *     
     */
    public E2ESender getSender() {
        return sender;
    }

    /**
     * Imposta il valore della proprietà sender.
     * 
     * @param value
     *     allowed object is
     *     {@link E2ESender }
     *     
     */
    public void setSender(E2ESender value) {
        this.sender = value;
    }

    /**
     * Recupera il valore della proprietà receiver.
     * 
     * @return
     *     possible object is
     *     {@link E2EReceiver }
     *     
     */
    public E2EReceiver getReceiver() {
        return receiver;
    }

    /**
     * Imposta il valore della proprietà receiver.
     * 
     * @param value
     *     allowed object is
     *     {@link E2EReceiver }
     *     
     */
    public void setReceiver(E2EReceiver value) {
        this.receiver = value;
    }

}
