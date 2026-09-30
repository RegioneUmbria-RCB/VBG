
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RipartizioneDiImportoInAccertamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RipartizioneDiImportoInAccertamento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Codice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ImportoInCentesimi" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="PeriodoDiRiferimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RipartizioneDiImportoInAccertamento", namespace = "http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts", propOrder = {
    "codice",
    "descrizione",
    "importoInCentesimi",
    "periodoDiRiferimento"
})
public class RipartizioneDiImportoInAccertamento {

    @XmlElementRef(name = "Codice", namespace = "http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> codice;
    @XmlElementRef(name = "Descrizione", namespace = "http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> descrizione;
    @XmlElement(name = "ImportoInCentesimi")
    protected Long importoInCentesimi;
    @XmlElementRef(name = "PeriodoDiRiferimento", namespace = "http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts", type = JAXBElement.class, required = false)
    protected JAXBElement<String> periodoDiRiferimento;

    /**
     * Recupera il valore della proprietà codice.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCodice() {
        return codice;
    }

    /**
     * Imposta il valore della proprietà codice.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodice(JAXBElement<String> value) {
        this.codice = value;
    }

    /**
     * Recupera il valore della proprietà descrizione.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDescrizione() {
        return descrizione;
    }

    /**
     * Imposta il valore della proprietà descrizione.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDescrizione(JAXBElement<String> value) {
        this.descrizione = value;
    }

    /**
     * Recupera il valore della proprietà importoInCentesimi.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getImportoInCentesimi() {
        return importoInCentesimi;
    }

    /**
     * Imposta il valore della proprietà importoInCentesimi.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setImportoInCentesimi(Long value) {
        this.importoInCentesimi = value;
    }

    /**
     * Recupera il valore della proprietà periodoDiRiferimento.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPeriodoDiRiferimento() {
        return periodoDiRiferimento;
    }

    /**
     * Imposta il valore della proprietà periodoDiRiferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPeriodoDiRiferimento(JAXBElement<String> value) {
        this.periodoDiRiferimento = value;
    }

}
