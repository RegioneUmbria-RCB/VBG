
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codiceEnte" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esitoComunicazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="elencoPagamentiResponse" type="{it/lineacomune/pagopa/be/ws/endpoint/public}elencoPagamentiResponse" minOccurs="0"/&gt;
 *         &lt;element name="codiceErrore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descrizioneErrore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "codiceEnte",
    "esitoComunicazione",
    "elencoPagamentiResponse",
    "codiceErrore",
    "descrizioneErrore"
})
@XmlRootElement(name = "getRavvedimentoOperosoResponse")
public class GetRavvedimentoOperosoResponse {

    @XmlElement(required = true)
    protected String codiceEnte;
    @XmlElement(required = true)
    protected String esitoComunicazione;
    protected ElencoPagamentiResponse elencoPagamentiResponse;
    protected String codiceErrore;
    protected String descrizioneErrore;

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
     * Recupera il valore della proprietà esitoComunicazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitoComunicazione() {
        return esitoComunicazione;
    }

    /**
     * Imposta il valore della proprietà esitoComunicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitoComunicazione(String value) {
        this.esitoComunicazione = value;
    }

    /**
     * Recupera il valore della proprietà elencoPagamentiResponse.
     * 
     * @return
     *     possible object is
     *     {@link ElencoPagamentiResponse }
     *     
     */
    public ElencoPagamentiResponse getElencoPagamentiResponse() {
        return elencoPagamentiResponse;
    }

    /**
     * Imposta il valore della proprietà elencoPagamentiResponse.
     * 
     * @param value
     *     allowed object is
     *     {@link ElencoPagamentiResponse }
     *     
     */
    public void setElencoPagamentiResponse(ElencoPagamentiResponse value) {
        this.elencoPagamentiResponse = value;
    }

    /**
     * Recupera il valore della proprietà codiceErrore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceErrore() {
        return codiceErrore;
    }

    /**
     * Imposta il valore della proprietà codiceErrore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceErrore(String value) {
        this.codiceErrore = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneErrore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneErrore() {
        return descrizioneErrore;
    }

    /**
     * Imposta il valore della proprietà descrizioneErrore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneErrore(String value) {
        this.descrizioneErrore = value;
    }

}
