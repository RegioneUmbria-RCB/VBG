
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ComponenteImportoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ComponenteImportoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Importo" type="{http://www.csi.it/epay/epaywso/types}ImportoType"/&gt;
 *         &lt;element name="CausaleDescrittiva" type="{http://www.csi.it/epay/epaywso/types}String80Type"/&gt;
 *         &lt;element name="DatiSpecificiRiscossione" type="{http://www.csi.it/epay/epaywso/types}String140Type" minOccurs="0"/&gt;
 *         &lt;element name="AnnoAccertamento" type="{http://www.csi.it/epay/epaywso/types}AnnoType" minOccurs="0"/&gt;
 *         &lt;element name="NumeroAccertamento" type="{http://www.csi.it/epay/epaywso/types}String35Type" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComponenteImportoType", propOrder = {
    "importo",
    "causaleDescrittiva",
    "datiSpecificiRiscossione",
    "annoAccertamento",
    "numeroAccertamento"
})
public class ComponenteImportoType {

    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;
    @XmlElement(name = "CausaleDescrittiva", required = true)
    protected String causaleDescrittiva;
    @XmlElement(name = "DatiSpecificiRiscossione")
    protected String datiSpecificiRiscossione;
    @XmlElement(name = "AnnoAccertamento")
    @XmlSchemaType(name = "integer")
    protected Integer annoAccertamento;
    @XmlElement(name = "NumeroAccertamento")
    protected String numeroAccertamento;

    /**
     * Recupera il valore della proprietà importo.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImporto() {
        return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImporto(BigDecimal value) {
        this.importo = value;
    }

    /**
     * Recupera il valore della proprietà causaleDescrittiva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausaleDescrittiva() {
        return causaleDescrittiva;
    }

    /**
     * Imposta il valore della proprietà causaleDescrittiva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausaleDescrittiva(String value) {
        this.causaleDescrittiva = value;
    }

    /**
     * Recupera il valore della proprietà datiSpecificiRiscossione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatiSpecificiRiscossione() {
        return datiSpecificiRiscossione;
    }

    /**
     * Imposta il valore della proprietà datiSpecificiRiscossione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatiSpecificiRiscossione(String value) {
        this.datiSpecificiRiscossione = value;
    }

    /**
     * Recupera il valore della proprietà annoAccertamento.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoAccertamento() {
        return annoAccertamento;
    }

    /**
     * Imposta il valore della proprietà annoAccertamento.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoAccertamento(Integer value) {
        this.annoAccertamento = value;
    }

    /**
     * Recupera il valore della proprietà numeroAccertamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroAccertamento() {
        return numeroAccertamento;
    }

    /**
     * Imposta il valore della proprietà numeroAccertamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroAccertamento(String value) {
        this.numeroAccertamento = value;
    }

}
