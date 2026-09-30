
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PaymentAuthenticatedRequestBase complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PaymentAuthenticatedRequestBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdApplicazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentAuthenticatedRequestBase", propOrder = {
    "idApplicazione"
})
@XmlSeeAlso({
    RichiestaInviaCarrelloPosizioni.class,
    RichiestaDownloadDatiRicevuta.class,
    RichiestaDownloadDatiOriginaliRicevuta.class
})
public class PaymentAuthenticatedRequestBase {

    @XmlElement(name = "IdApplicazione", required = true, nillable = true)
    protected String idApplicazione;

    /**
     * Recupera il valore della proprietà idApplicazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdApplicazione() {
        return idApplicazione;
    }

    /**
     * Imposta il valore della proprietà idApplicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdApplicazione(String value) {
        this.idApplicazione = value;
    }

}
