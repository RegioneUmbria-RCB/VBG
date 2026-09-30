
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.server.esitopagamentiattesi.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="esitoComunicazione" type="{it/lineacomune/pagopa/be/ws/endpoint}codiceEsitoComunicazione"/&gt;
 *         &lt;element name="codiceErrore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="descrizioneErrore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codiceServizio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "esitoComunicazione",
    "codiceErrore",
    "descrizioneErrore",
    "ente",
    "codiceServizio"
})
@XmlRootElement(name = "pagamentiAttesiEsitoPagamentoResponse")
public class PagamentiAttesiEsitoPagamentoResponse {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected CodiceEsitoComunicazione esitoComunicazione;
    protected String codiceErrore;
    protected String descrizioneErrore;
    @XmlElement(required = true)
    protected String ente;
    @XmlElement(required = true)
    protected String codiceServizio;

    /**
     * Recupera il valore della proprietà esitoComunicazione.
     * 
     * @return
     *     possible object is
     *     {@link CodiceEsitoComunicazione }
     *     
     */
    public CodiceEsitoComunicazione getEsitoComunicazione() {
        return esitoComunicazione;
    }

    /**
     * Imposta il valore della proprietà esitoComunicazione.
     * 
     * @param value
     *     allowed object is
     *     {@link CodiceEsitoComunicazione }
     *     
     */
    public void setEsitoComunicazione(CodiceEsitoComunicazione value) {
        this.esitoComunicazione = value;
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

    /**
     * Recupera il valore della proprietà ente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnte() {
        return ente;
    }

    /**
     * Imposta il valore della proprietà ente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnte(String value) {
        this.ente = value;
    }

    /**
     * Recupera il valore della proprietà codiceServizio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceServizio() {
        return codiceServizio;
    }

    /**
     * Imposta il valore della proprietà codiceServizio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceServizio(String value) {
        this.codiceServizio = value;
    }

}
