
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RiceviEsitoTransazioneResponse complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RiceviEsitoTransazioneResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://entranext.it/}LinkNextResponse"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EsitoTransazione" type="{http://entranext.it/}PagamentoPagoPA" minOccurs="0"/&gt;
 *         &lt;element name="RT_XML" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="RT_PDF" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RiceviEsitoTransazioneResponse", propOrder = {
    "esitoTransazione",
    "rtxml",
    "rtpdf"
})
public class RiceviEsitoTransazioneResponse
    extends LinkNextResponse
{

    @XmlElement(name = "EsitoTransazione")
    protected PagamentoPagoPA esitoTransazione;
    @XmlElement(name = "RT_XML")
    protected byte[] rtxml;
    @XmlElement(name = "RT_PDF")
    protected byte[] rtpdf;

    /**
     * Recupera il valore della proprietà esitoTransazione.
     * 
     * @return
     *     possible object is
     *     {@link PagamentoPagoPA }
     *     
     */
    public PagamentoPagoPA getEsitoTransazione() {
        return esitoTransazione;
    }

    /**
     * Imposta il valore della proprietà esitoTransazione.
     * 
     * @param value
     *     allowed object is
     *     {@link PagamentoPagoPA }
     *     
     */
    public void setEsitoTransazione(PagamentoPagoPA value) {
        this.esitoTransazione = value;
    }

    /**
     * Recupera il valore della proprietà rtxml.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getRTXML() {
        return rtxml;
    }

    /**
     * Imposta il valore della proprietà rtxml.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setRTXML(byte[] value) {
        this.rtxml = value;
    }

    /**
     * Recupera il valore della proprietà rtpdf.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getRTPDF() {
        return rtpdf;
    }

    /**
     * Imposta il valore della proprietà rtpdf.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setRTPDF(byte[] value) {
        this.rtpdf = value;
    }

}
