
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.pagopabe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.common.GetPagamentoAttesoWs;


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
 *         &lt;element name="codiceErrore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="descrizioneErrore" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pagamentoAttesoWs" type="{it/lineacomune/pagopa/be/ws/endpoint/shared}getPagamentoAttesoWs" minOccurs="0"/&gt;
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
    "codiceErrore",
    "descrizioneErrore",
    "pagamentoAttesoWs"
})
@XmlRootElement(name = "getPagamentoAttesoByIuvResponse")
public class GetPagamentoAttesoByIuvResponse {

    @XmlElement(required = true)
    protected String codiceErrore;
    @XmlElement(required = true)
    protected String descrizioneErrore;
    protected GetPagamentoAttesoWs pagamentoAttesoWs;

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
     * Recupera il valore della proprietà pagamentoAttesoWs.
     * 
     * @return
     *     possible object is
     *     {@link GetPagamentoAttesoWs }
     *     
     */
    public GetPagamentoAttesoWs getPagamentoAttesoWs() {
        return pagamentoAttesoWs;
    }

    /**
     * Imposta il valore della proprietà pagamentoAttesoWs.
     * 
     * @param value
     *     allowed object is
     *     {@link GetPagamentoAttesoWs }
     *     
     */
    public void setPagamentoAttesoWs(GetPagamentoAttesoWs value) {
        this.pagamentoAttesoWs = value;
    }

}
