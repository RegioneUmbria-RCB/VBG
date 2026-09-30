
package it.gruppoinit.pal.gp.pay.ws.schema;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EsitoDocumentoPosizioneDebitoriaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="EsitoDocumentoPosizioneDebitoriaType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.paevolution.com/ws/pagamenti_types/}EsitoOperazionePosizioneDebitoriaType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="nomeDocumento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="statoDocumento" type="{http://www.paevolution.com/ws/pagamenti_types/}StatoDocumentoType"/&gt;
 *         &lt;element name="tipoDocumento" type="{http://www.paevolution.com/ws/pagamenti_types/}TipoDocumentoType"/&gt;
 *         &lt;element name="documento" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsitoDocumentoPosizioneDebitoriaType", propOrder = {
    "nomeDocumento",
    "statoDocumento",
    "tipoDocumento",
    "documento"
})
public class EsitoDocumentoPosizioneDebitoriaType
    extends EsitoOperazionePosizioneDebitoriaType
{

    @XmlElement(required = false)
    protected String nomeDocumento;
    @XmlElement(required = false)
    @XmlSchemaType(name = "string")
    protected StatoDocumentoType statoDocumento;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected TipoDocumentoType tipoDocumento;
    @XmlElement(required = false)
    @XmlMimeType("application/octet-stream")
    protected DataHandler documento;

    /**
     * Recupera il valore della proprietà nomeDocumento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeDocumento() {
        return nomeDocumento;
    }

    /**
     * Imposta il valore della proprietà nomeDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeDocumento(String value) {
        this.nomeDocumento = value;
    }

    /**
     * Recupera il valore della proprietà statoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link StatoDocumentoType }
     *     
     */
    public StatoDocumentoType getStatoDocumento() {
        return statoDocumento;
    }

    /**
     * Imposta il valore della proprietà statoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoDocumentoType }
     *     
     */
    public void setStatoDocumento(StatoDocumentoType value) {
        this.statoDocumento = value;
    }

    /**
     * Recupera il valore della proprietà tipoDocumento.
     * 
     * @return
     *     possible object is
     *     {@link TipoDocumentoType }
     *     
     */
    public TipoDocumentoType getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Imposta il valore della proprietà tipoDocumento.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDocumentoType }
     *     
     */
    public void setTipoDocumento(TipoDocumentoType value) {
        this.tipoDocumento = value;
    }

    /**
     * Recupera il valore della proprietà documento.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getDocumento() {
        return documento;
    }

    /**
     * Imposta il valore della proprietà documento.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setDocumento(DataHandler value) {
        this.documento = value;
    }

}
