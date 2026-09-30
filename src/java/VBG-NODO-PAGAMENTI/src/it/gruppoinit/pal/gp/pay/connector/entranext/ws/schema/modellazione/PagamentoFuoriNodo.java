
package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per PagamentoFuoriNodo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PagamentoFuoriNodo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TipoChiaveApplicativa" type="{http://entranext.it/}TipoChiaveApplicativa"/&gt;
 *         &lt;element name="ChiaveApplicativa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Modalita" type="{http://entranext.it/}ModalitaPagamentoFuoriNodo"/&gt;
 *         &lt;element name="RiferimentoPagamentoImportato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Importo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="DataVersamento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DataAccredito" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Dettagli" type="{http://entranext.it/}PagamentoFuoriNodo_Dettaglio" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PagamentoFuoriNodo", propOrder = {
    "tipoChiaveApplicativa",
    "chiaveApplicativa",
    "modalita",
    "riferimentoPagamentoImportato",
    "importo",
    "dataVersamento",
    "dataAccredito",
    "note",
    "dettagli"
})
public class PagamentoFuoriNodo {

    @XmlElement(name = "TipoChiaveApplicativa", required = true)
    @XmlSchemaType(name = "string")
    protected TipoChiaveApplicativa tipoChiaveApplicativa;
    @XmlElement(name = "ChiaveApplicativa")
    protected String chiaveApplicativa;
    @XmlElement(name = "Modalita", required = true)
    @XmlSchemaType(name = "string")
    protected ModalitaPagamentoFuoriNodo modalita;
    @XmlElement(name = "RiferimentoPagamentoImportato")
    protected String riferimentoPagamentoImportato;
    @XmlElement(name = "Importo", required = true)
    protected BigDecimal importo;
    @XmlElement(name = "DataVersamento", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVersamento;
    @XmlElement(name = "DataAccredito", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataAccredito;
    @XmlElement(name = "Note")
    protected String note;
    @XmlElement(name = "Dettagli", nillable = true)
    protected List<PagamentoFuoriNodoDettaglio> dettagli;

    /**
     * Recupera il valore della proprietà tipoChiaveApplicativa.
     * 
     * @return
     *     possible object is
     *     {@link TipoChiaveApplicativa }
     *     
     */
    public TipoChiaveApplicativa getTipoChiaveApplicativa() {
        return tipoChiaveApplicativa;
    }

    /**
     * Imposta il valore della proprietà tipoChiaveApplicativa.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoChiaveApplicativa }
     *     
     */
    public void setTipoChiaveApplicativa(TipoChiaveApplicativa value) {
        this.tipoChiaveApplicativa = value;
    }

    /**
     * Recupera il valore della proprietà chiaveApplicativa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChiaveApplicativa() {
        return chiaveApplicativa;
    }

    /**
     * Imposta il valore della proprietà chiaveApplicativa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChiaveApplicativa(String value) {
        this.chiaveApplicativa = value;
    }

    /**
     * Recupera il valore della proprietà modalita.
     * 
     * @return
     *     possible object is
     *     {@link ModalitaPagamentoFuoriNodo }
     *     
     */
    public ModalitaPagamentoFuoriNodo getModalita() {
        return modalita;
    }

    /**
     * Imposta il valore della proprietà modalita.
     * 
     * @param value
     *     allowed object is
     *     {@link ModalitaPagamentoFuoriNodo }
     *     
     */
    public void setModalita(ModalitaPagamentoFuoriNodo value) {
        this.modalita = value;
    }

    /**
     * Recupera il valore della proprietà riferimentoPagamentoImportato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoPagamentoImportato() {
        return riferimentoPagamentoImportato;
    }

    /**
     * Imposta il valore della proprietà riferimentoPagamentoImportato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoPagamentoImportato(String value) {
        this.riferimentoPagamentoImportato = value;
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
     * Recupera il valore della proprietà dataVersamento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataVersamento() {
        return dataVersamento;
    }

    /**
     * Imposta il valore della proprietà dataVersamento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataVersamento(XMLGregorianCalendar value) {
        this.dataVersamento = value;
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

    /**
     * Recupera il valore della proprietà note.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNote() {
        return note;
    }

    /**
     * Imposta il valore della proprietà note.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNote(String value) {
        this.note = value;
    }

    /**
     * Gets the value of the dettagli property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dettagli property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDettagli().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PagamentoFuoriNodoDettaglio }
     * 
     * 
     */
    public List<PagamentoFuoriNodoDettaglio> getDettagli() {
        if (dettagli == null) {
            dettagli = new ArrayList<PagamentoFuoriNodoDettaglio>();
        }
        return this.dettagli;
    }

}
