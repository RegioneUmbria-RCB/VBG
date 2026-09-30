
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per RichiestaRettificaPosizione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RichiestaRettificaPosizione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://e-fil.eu/PnP/PlugAndPayFeed}FeedAuthenticatedRequestBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Causale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CodiceEnte" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CodiceIdentificativo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DataScandenza" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="ImportoInCentesimi" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="RipartizioniDiImporto" type="{http://schemas.datacontract.org/2004/07/PlugAndPay.Feed.Service.Contracts}ArrayOfRipartizioneDiImportoInAccertamento" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiestaRettificaPosizione", propOrder = {
    "causale",
    "codiceEnte",
    "codiceIdentificativo",
    "dataScandenza",
    "importoInCentesimi",
    "ripartizioniDiImporto"
})
public class RichiestaRettificaPosizione
    extends FeedAuthenticatedRequestBase
{

    @XmlElementRef(name = "Causale", namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", type = JAXBElement.class, required = false)
    protected JAXBElement<String> causale;
    @XmlElement(name = "CodiceEnte", required = true, nillable = true)
    protected String codiceEnte;
    @XmlElement(name = "CodiceIdentificativo", required = true, nillable = true)
    protected String codiceIdentificativo;
    @XmlElementRef(name = "DataScandenza", namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dataScandenza;
    @XmlElementRef(name = "ImportoInCentesimi", namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", type = JAXBElement.class, required = false)
    protected JAXBElement<Long> importoInCentesimi;
    @XmlElementRef(name = "RipartizioniDiImporto", namespace = "http://e-fil.eu/PnP/PlugAndPayFeed", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfRipartizioneDiImportoInAccertamento> ripartizioniDiImporto;

    /**
     * Recupera il valore della proprietà causale.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCausale() {
        return causale;
    }

    /**
     * Imposta il valore della proprietà causale.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCausale(JAXBElement<String> value) {
        this.causale = value;
    }

    /**
     * Recupera il valore della proprietà codiceEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEnte() {
        return codiceEnte;
    }

    /**
     * Imposta il valore della proprietà codiceEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEnte(String value) {
        this.codiceEnte = value;
    }

    /**
     * Recupera il valore della proprietà codiceIdentificativo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIdentificativo() {
        return codiceIdentificativo;
    }

    /**
     * Imposta il valore della proprietà codiceIdentificativo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIdentificativo(String value) {
        this.codiceIdentificativo = value;
    }

    /**
     * Recupera il valore della proprietà dataScandenza.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDataScandenza() {
        return dataScandenza;
    }

    /**
     * Imposta il valore della proprietà dataScandenza.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDataScandenza(JAXBElement<XMLGregorianCalendar> value) {
        this.dataScandenza = value;
    }

    /**
     * Recupera il valore della proprietà importoInCentesimi.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public JAXBElement<Long> getImportoInCentesimi() {
        return importoInCentesimi;
    }

    /**
     * Imposta il valore della proprietà importoInCentesimi.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Long }{@code >}
     *     
     */
    public void setImportoInCentesimi(JAXBElement<Long> value) {
        this.importoInCentesimi = value;
    }

    /**
     * Recupera il valore della proprietà ripartizioniDiImporto.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRipartizioneDiImportoInAccertamento }{@code >}
     *     
     */
    public JAXBElement<ArrayOfRipartizioneDiImportoInAccertamento> getRipartizioniDiImporto() {
        return ripartizioniDiImporto;
    }

    /**
     * Imposta il valore della proprietà ripartizioniDiImporto.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfRipartizioneDiImportoInAccertamento }{@code >}
     *     
     */
    public void setRipartizioniDiImporto(JAXBElement<ArrayOfRipartizioneDiImportoInAccertamento> value) {
        this.ripartizioniDiImporto = value;
    }

}
