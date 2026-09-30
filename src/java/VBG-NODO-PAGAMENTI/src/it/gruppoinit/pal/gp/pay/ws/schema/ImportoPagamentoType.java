package it.gruppoinit.pal.gp.pay.ws.schema;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per ImportoPagamentoType complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ImportoPagamentoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="importo" type="{http://www.paevolution.com/ws/pagamenti_types/}ImportoType"/&gt;
 *         &lt;element name="descrizioneCausale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="datiRiscossione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="annoAccertamento" type="{http://www.paevolution.com/ws/pagamenti_types/}AnnoType" minOccurs="0"/&gt;
 *         &lt;element name="numeroAccertamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="numeroSottoAccertamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="codiceMappatura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImportoPagamentoType", propOrder = { "importo", 
	"descrizioneCausale", 
	"datiRiscossione", 
	"annoAccertamento", 
	"numeroAccertamento",
	"numeroSottoAccertamento",
	"codiceMappatura"})
public class ImportoPagamentoType {

    @XmlElement(required = true)
    protected BigDecimal importo;
    protected String descrizioneCausale;
    protected String datiRiscossione;
    @XmlSchemaType(name = "integer")
    protected Integer annoAccertamento;
    protected String numeroAccertamento;
    protected String numeroSottoAccertamento;
    protected String codiceMappatura;

    /**
     * Recupera il valore della proprietà importo.
     * 
     * @return possible object is {@link BigDecimal }
     * 
     */
    public BigDecimal getImporto() {

	return importo;
    }

    /**
     * Imposta il valore della proprietà importo.
     * 
     * @param value
     *            allowed object is {@link BigDecimal }
     * 
     */
    public void setImporto(BigDecimal value) {

	this.importo = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneCausale.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDescrizioneCausale() {

	return descrizioneCausale;
    }

    /**
     * Imposta il valore della proprietà descrizioneCausale.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDescrizioneCausale(String value) {

	this.descrizioneCausale = value;
    }

    /**
     * Recupera il valore della proprietà datiRiscossione.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDatiRiscossione() {

	return datiRiscossione;
    }

    /**
     * Imposta il valore della proprietà datiRiscossione.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDatiRiscossione(String value) {

	this.datiRiscossione = value;
    }
    //    /**
    //     * Recupera il valore della proprietà codiceConto.
    //     * 
    //     * @return
    //     *     possible object is
    //     *     {@link BigInteger }
    //     *     
    //     */
    //    public BigInteger getCodiceConto() {
    //        return codiceConto;
    //    }
    //
    //    /**
    //     * Imposta il valore della proprietà codiceConto.
    //     * 
    //     * @param value
    //     *     allowed object is
    //     *     {@link BigInteger }
    //     *     
    //     */
    //    public void setCodiceConto(BigInteger value) {
    //        this.codiceConto = value;
    //    }

    /**
     * Recupera il valore della proprietà annoAccertamento.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getAnnoAccertamento() {

	return annoAccertamento;
    }

    /**
     * Imposta il valore della proprietà annoAccertamento.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setAnnoAccertamento(Integer value) {

	this.annoAccertamento = value;
    }

    /**
     * Recupera il valore della proprietà numeroAccertamento.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumeroAccertamento() {

	return numeroAccertamento;
    }

    /**
     * Imposta il valore della proprietà numeroAccertamento.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNumeroAccertamento(String value) {

	this.numeroAccertamento = value;
    }

    /**
     * Recupera il valore della proprietà numeroSottoAccertamento.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumeroSottoAccertamento() {

	return numeroSottoAccertamento;
    }

    /**
     * Imposta il valore della proprietà numeroSottoAccertamento.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNumeroSottoAccertamento(String value) {

	this.numeroSottoAccertamento = value;
    }

    public String getCodiceMappatura() {

	return codiceMappatura;
    }

    public void setCodiceMappatura(String codiceMappatura) {

	this.codiceMappatura = codiceMappatura;
    }
}
