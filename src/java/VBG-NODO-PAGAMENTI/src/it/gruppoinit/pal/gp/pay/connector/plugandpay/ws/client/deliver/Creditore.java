
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Creditore complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Creditore"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CodiceEnte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceFiscalePartitaIva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IBAN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Intestazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Creditore", propOrder = {
    "codiceEnte",
    "codiceFiscalePartitaIva",
    "iban",
    "intestazione"
})
public class Creditore {

    @XmlElementRef(name = "CodiceEnte", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> codiceEnte;
    @XmlElementRef(name = "CodiceFiscalePartitaIva", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> codiceFiscalePartitaIva;
    @XmlElementRef(name = "IBAN", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> iban;
    @XmlElementRef(name = "Intestazione", namespace = "http://e-fil.eu/PnP/PlugAndPayDeliver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> intestazione;

    /**
     * Recupera il valore della proprietà codiceEnte.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCodiceEnte() {
        return codiceEnte;
    }

    /**
     * Imposta il valore della proprietà codiceEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodiceEnte(JAXBElement<String> value) {
        this.codiceEnte = value;
    }

    /**
     * Recupera il valore della proprietà codiceFiscalePartitaIva.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCodiceFiscalePartitaIva() {
        return codiceFiscalePartitaIva;
    }

    /**
     * Imposta il valore della proprietà codiceFiscalePartitaIva.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodiceFiscalePartitaIva(JAXBElement<String> value) {
        this.codiceFiscalePartitaIva = value;
    }

    /**
     * Recupera il valore della proprietà iban.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIBAN() {
        return iban;
    }

    /**
     * Imposta il valore della proprietà iban.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIBAN(JAXBElement<String> value) {
        this.iban = value;
    }

    /**
     * Recupera il valore della proprietà intestazione.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIntestazione() {
        return intestazione;
    }

    /**
     * Imposta il valore della proprietà intestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIntestazione(JAXBElement<String> value) {
        this.intestazione = value;
    }

}
