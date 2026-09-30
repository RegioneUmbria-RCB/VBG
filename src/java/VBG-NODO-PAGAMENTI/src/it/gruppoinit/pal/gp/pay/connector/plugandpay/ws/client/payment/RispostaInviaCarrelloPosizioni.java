
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <pClasse Java per RispostaInviaCarrelloPosizioni complex type.
 * 
 * <pIl seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre
 * &lt;complexType name="RispostaInviaCarrelloPosizioni"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CarrelloArricchito" type="{http://e-fil.eu/PnP/PlugAndPayPayment}ArrayOfPosizione" minOccurs="0"/&gt;
 *         &lt;element name="EsitiInvioCarrelloPosizioni" type="{http://e-fil.eu/PnP/PlugAndPayPayment}ArrayOfEsitoInvioCarrelloPosizioni" minOccurs="0"/&gt;
 *         &lt;element name="IdTransazione" type="{http://schemas.microsoft.com/2003/10/Serialization/}guid" minOccurs="0"/&gt;
 *         &lt;element name="UrlRedirect" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RispostaInviaCarrelloPosizioni", propOrder = {
    "carrelloArricchito",
    "esitiInvioCarrelloPosizioni",
    "idTransazione",
    "urlRedirect"
})
public class RispostaInviaCarrelloPosizioni {

    @XmlElement(name = "CarrelloArricchito", required = false)
    protected ArrayOfPosizione carrelloArricchito;
    @XmlElement(name = "EsitiInvioCarrelloPosizioni", required = false)
    protected ArrayOfEsitoInvioCarrelloPosizioni esitiInvioCarrelloPosizioni;
    @XmlElement(name = "IdTransazione")
    protected String idTransazione;
    @XmlElement(name = "UrlRedirect", required = false)
    protected String urlRedirect;

    /**
     * Recupera il valore della proprietà carrelloArricchito.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPosizione }{@code }
     *     
     */
    public ArrayOfPosizione getCarrelloArricchito() {
        return carrelloArricchito;
    }

    /**
     * Imposta il valore della proprietà carrelloArricchito.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPosizione }{@code }
     *     
     */
    public void setCarrelloArricchito(ArrayOfPosizione value) {
        this.carrelloArricchito = value;
    }

    /**
     * Recupera il valore della proprietà esitiInvioCarrelloPosizioni.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfEsitoInvioCarrelloPosizioni }{@code }
     *     
     */
    public ArrayOfEsitoInvioCarrelloPosizioni getEsitiInvioCarrelloPosizioni() {
        return esitiInvioCarrelloPosizioni;
    }

    /**
     * Imposta il valore della proprietà esitiInvioCarrelloPosizioni.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfEsitoInvioCarrelloPosizioni }{@code }
     *     
     */
    public void setEsitiInvioCarrelloPosizioni(ArrayOfEsitoInvioCarrelloPosizioni value) {
        this.esitiInvioCarrelloPosizioni = value;
    }

    /**
     * Recupera il valore della proprietà idTransazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdTransazione() {
        return idTransazione;
    }

    /**
     * Imposta il valore della proprietà idTransazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdTransazione(String value) {
        this.idTransazione = value;
    }

    /**
     * Recupera il valore della proprietà urlRedirect.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getUrlRedirect() {
        return urlRedirect;
    }

    /**
     * Imposta il valore della proprietà urlRedirect.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setUrlRedirect(String value) {
        this.urlRedirect = value;
    }

}
