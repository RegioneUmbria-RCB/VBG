
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per PagamentoFuoriNodo_Dettaglio complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PagamentoFuoriNodo_Dettaglio"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RiferimentoDettaglioPraticaEsterna" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Importo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Iva" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="DataAccredito" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PagamentoFuoriNodo_Dettaglio", propOrder = {
    "riferimentoDettaglioPraticaEsterna",
    "importo",
    "iva",
    "dataAccredito"
})
public class PagamentoFuoriNodoDettaglio {

    @XmlElement(name = "RiferimentoDettaglioPraticaEsterna")
    protected String riferimentoDettaglioPraticaEsterna;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;
    @XmlElement(name = "Iva", required = true)
    protected BigDecimal iva;
    @XmlElement(name = "DataAccredito", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataAccredito;

    /**
     * Recupera il valore della proprietà riferimentoDettaglioPraticaEsterna.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoDettaglioPraticaEsterna() {
        return riferimentoDettaglioPraticaEsterna;
    }

    /**
     * Imposta il valore della proprietà riferimentoDettaglioPraticaEsterna.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoDettaglioPraticaEsterna(String value) {
        this.riferimentoDettaglioPraticaEsterna = value;
    }

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
     * Recupera il valore della proprietà iva.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIva() {
        return iva;
    }

    /**
     * Imposta il valore della proprietà iva.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIva(BigDecimal value) {
        this.iva = value;
    }

    /**
     * Recupera il valore della proprietà dataAccredito.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAccredito() {
        return dataAccredito;
    }

    /**
     * Imposta il valore della proprietà dataAccredito.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAccredito(XMLGregorianCalendar value) {
        this.dataAccredito = value;
    }

}
