
package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.caricaposizioni.schema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DettaglioTransazione complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DettaglioTransazione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DataPagamento" type="{http://www.w3.org/2001/XMLSchema}anySimpleType"/&gt;
 *         &lt;element name="CanalePagamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="MezzoPagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ImportoPagamento" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="NotePagamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioTransazione", propOrder = {
    "dataPagamento",
    "canalePagamento",
    "mezzoPagamento",
    "importoPagamento",
    "notePagamento"
})
public class DettaglioTransazione {

    @XmlElement(name = "DataPagamento", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected Object dataPagamento;
    @XmlElement(name = "CanalePagamento", required = true)
    protected String canalePagamento;
    @XmlElement(name = "MezzoPagamento")
    protected String mezzoPagamento;
    @XmlElement(name = "ImportoPagamento")
    protected BigDecimal importoPagamento;
    @XmlElement(name = "NotePagamento")
    protected String notePagamento;

    /**
     * Recupera il valore della proprietà dataPagamento.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDataPagamento() {
        return dataPagamento;
    }

    /**
     * Imposta il valore della proprietà dataPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDataPagamento(Object value) {
        this.dataPagamento = value;
    }

    /**
     * Recupera il valore della proprietà canalePagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanalePagamento() {
        return canalePagamento;
    }

    /**
     * Imposta il valore della proprietà canalePagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanalePagamento(String value) {
        this.canalePagamento = value;
    }

    /**
     * Recupera il valore della proprietà mezzoPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMezzoPagamento() {
        return mezzoPagamento;
    }

    /**
     * Imposta il valore della proprietà mezzoPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMezzoPagamento(String value) {
        this.mezzoPagamento = value;
    }

    /**
     * Recupera il valore della proprietà importoPagamento.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoPagamento() {
        return importoPagamento;
    }

    /**
     * Imposta il valore della proprietà importoPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoPagamento(BigDecimal value) {
        this.importoPagamento = value;
    }

    /**
     * Recupera il valore della proprietà notePagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotePagamento() {
        return notePagamento;
    }

    /**
     * Imposta il valore della proprietà notePagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotePagamento(String value) {
        this.notePagamento = value;
    }

}
