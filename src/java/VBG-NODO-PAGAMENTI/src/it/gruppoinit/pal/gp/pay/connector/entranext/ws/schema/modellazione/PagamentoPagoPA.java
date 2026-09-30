
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per PagamentoPagoPA complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PagamentoPagoPA"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Stato" type="{http://entranext.it/}StatoPagamentoPagoPA"/&gt;
 *         &lt;element name="DataRichiesta" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="Datarisposta" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="Importo" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="IdentificativoTransazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IdentificativoPSP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DescrizionePSP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TipoVersamnetoPSP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PagamentoPagoPA", propOrder = {
    "stato",
    "dataRichiesta",
    "datarisposta",
    "importo",
    "identificativoTransazione",
    "identificativoPSP",
    "descrizionePSP",
    "tipoVersamnetoPSP"
})
public class PagamentoPagoPA {

    @XmlElement(name = "Stato", required = true)
    @XmlSchemaType(name = "string")
    protected StatoPagamentoPagoPA stato;
    @XmlElement(name = "DataRichiesta")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRichiesta;
    @XmlElement(name = "Datarisposta")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datarisposta;
    @XmlElement(name = "Importo")
    protected BigDecimal importo;
    @XmlElement(name = "IdentificativoTransazione")
    protected String identificativoTransazione;
    @XmlElement(name = "IdentificativoPSP")
    protected String identificativoPSP;
    @XmlElement(name = "DescrizionePSP")
    protected String descrizionePSP;
    @XmlElement(name = "TipoVersamnetoPSP")
    protected String tipoVersamnetoPSP;

    /**
     * Recupera il valore della proprietà stato.
     * 
     * @return
     *     possible object is
     *     {@link StatoPagamentoPagoPA }
     *     
     */
    public StatoPagamentoPagoPA getStato() {
        return stato;
    }

    /**
     * Imposta il valore della proprietà stato.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoPagamentoPagoPA }
     *     
     */
    public void setStato(StatoPagamentoPagoPA value) {
        this.stato = value;
    }

    /**
     * Recupera il valore della proprietà dataRichiesta.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRichiesta() {
        return dataRichiesta;
    }

    /**
     * Imposta il valore della proprietà dataRichiesta.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRichiesta(XMLGregorianCalendar value) {
        this.dataRichiesta = value;
    }

    /**
     * Recupera il valore della proprietà datarisposta.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatarisposta() {
        return datarisposta;
    }

    /**
     * Imposta il valore della proprietà datarisposta.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatarisposta(XMLGregorianCalendar value) {
        this.datarisposta = value;
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
     * Recupera il valore della proprietà identificativoTransazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoTransazione() {
        return identificativoTransazione;
    }

    /**
     * Imposta il valore della proprietà identificativoTransazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoTransazione(String value) {
        this.identificativoTransazione = value;
    }

    /**
     * Recupera il valore della proprietà identificativoPSP.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoPSP() {
        return identificativoPSP;
    }

    /**
     * Imposta il valore della proprietà identificativoPSP.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoPSP(String value) {
        this.identificativoPSP = value;
    }

    /**
     * Recupera il valore della proprietà descrizionePSP.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizionePSP() {
        return descrizionePSP;
    }

    /**
     * Imposta il valore della proprietà descrizionePSP.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizionePSP(String value) {
        this.descrizionePSP = value;
    }

    /**
     * Recupera il valore della proprietà tipoVersamnetoPSP.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoVersamnetoPSP() {
        return tipoVersamnetoPSP;
    }

    /**
     * Imposta il valore della proprietà tipoVersamnetoPSP.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoVersamnetoPSP(String value) {
        this.tipoVersamnetoPSP = value;
    }

}
