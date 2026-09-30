
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RichiestaInviaCarrelloPosizioni complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RichiestaInviaCarrelloPosizioni"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://e-fil.eu/PnP/PlugAndPayPayment}PaymentAuthenticatedRequestBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Carrello" type="{http://e-fil.eu/PnP/PlugAndPayPayment}ArrayOfPosizione"/&gt;
 *         &lt;element name="UrlBack" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="UrlReturn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiestaInviaCarrelloPosizioni", propOrder = {
    "carrello",
    "urlBack",
    "urlReturn"
})
public class RichiestaInviaCarrelloPosizioni
    extends PaymentAuthenticatedRequestBase
{

    @XmlElement(name = "Carrello", required = true, nillable = true)
    protected ArrayOfPosizione carrello;
    @XmlElement(name = "UrlBack", required = true, nillable = true)
    protected String urlBack;
    @XmlElement(name = "UrlReturn", required = true, nillable = true)
    protected String urlReturn;

    /**
     * Recupera il valore della proprietà carrello.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPosizione }
     *     
     */
    public ArrayOfPosizione getCarrello() {
        return carrello;
    }

    /**
     * Imposta il valore della proprietà carrello.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPosizione }
     *     
     */
    public void setCarrello(ArrayOfPosizione value) {
        this.carrello = value;
    }

    /**
     * Recupera il valore della proprietà urlBack.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlBack() {
        return urlBack;
    }

    /**
     * Imposta il valore della proprietà urlBack.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlBack(String value) {
        this.urlBack = value;
    }

    /**
     * Recupera il valore della proprietà urlReturn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrlReturn() {
        return urlReturn;
    }

    /**
     * Imposta il valore della proprietà urlReturn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrlReturn(String value) {
        this.urlReturn = value;
    }

}
