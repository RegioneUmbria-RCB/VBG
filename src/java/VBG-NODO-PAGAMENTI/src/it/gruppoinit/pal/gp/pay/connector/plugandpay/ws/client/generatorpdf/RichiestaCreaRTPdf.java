
package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.generatorpdf;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RichiestaCreaRTPdf complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RichiestaCreaRTPdf"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://e-fil.eu/GeneratorPdf}GeneratorPdfBytesAuthenticatedRequestBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CodiceFiscalePartitaIva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codiceEnteCreditore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="identificativoPosizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiestaCreaRTPdf", namespace = "http://schemas.datacontract.org/2004/07/GeneratorPdfBytes.Service.Contracts", propOrder = {
    "codiceFiscalePartitaIva",
    "codiceEnteCreditore",
    "identificativoPosizione"
})
public class RichiestaCreaRTPdf
    extends GeneratorPdfBytesAuthenticatedRequestBase
{

    @XmlElement(name = "CodiceFiscalePartitaIva", required = false)
    protected String codiceFiscalePartitaIva;
    @XmlElement(name = "codiceEnteCreditore", required = false)
    protected String codiceEnteCreditore;
    @XmlElement(name = "identificativoPosizione", required = false)
    protected String identificativoPosizione;

    /**
     * Recupera il valore della proprietà codiceFiscalePartitaIva.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getCodiceFiscalePartitaIva() {
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
    public void setCodiceFiscalePartitaIva(String value) {
        this.codiceFiscalePartitaIva = value;
    }

    /**
     * Recupera il valore della proprietà codiceEnteCreditore.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getCodiceEnteCreditore() {
        return codiceEnteCreditore;
    }

    /**
     * Imposta il valore della proprietà codiceEnteCreditore.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodiceEnteCreditore(String value) {
        this.codiceEnteCreditore = value;
    }

    /**
     * Recupera il valore della proprietà identificativoPosizione.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public String getIdentificativoPosizione() {
        return identificativoPosizione;
    }

    /**
     * Imposta il valore della proprietà identificativoPosizione.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdentificativoPosizione(String value) {
        this.identificativoPosizione = value;
    }

}
