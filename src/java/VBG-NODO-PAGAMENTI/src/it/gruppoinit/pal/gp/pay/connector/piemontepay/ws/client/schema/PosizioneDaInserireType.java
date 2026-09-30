
package it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.client.schema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.gruppoinit.pal.gp.pay.connector.piemontepay.ws.common.schema.SoggettoType;


/**
 * <p>Classe Java per PosizioneDaInserireType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PosizioneDaInserireType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IdPosizioneDebitoria" type="{http://www.csi.it/epay/epaywso/types}String50Type"/&gt;
 *         &lt;element name="AnnoRiferimento" type="{http://www.csi.it/epay/epaywso/types}AnnoType" minOccurs="0"/&gt;
 *         &lt;element name="ImportoTotale" type="{http://www.csi.it/epay/epaywso/types}ImportoType"/&gt;
 *         &lt;element name="DataScadenza" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="DataInizioValidita" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="DataFineValidita" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="DescrizioneCausaleVersamento" type="{http://www.csi.it/epay/epaywso/types}String100Type"/&gt;
 *         &lt;element name="DescrizioneRata" type="{http://www.csi.it/epay/epaywso/types}String16Type" minOccurs="0"/&gt;
 *         &lt;element name="ComponentiImporto" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ComponenteImporto" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}ComponenteImportoType" maxOccurs="5"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="SoggettoPagatore" type="{http://www.csi.it/epay/epaywso/types}SoggettoType"/&gt;
 *         &lt;element name="NotePerIlPagatore" type="{http://www.csi.it/epay/epaywso/types}String1000Type" minOccurs="0"/&gt;
 *         &lt;element name="RiferimentiPagamento" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Riferimento" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}RiferimentoType" maxOccurs="5"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PosizioneDaInserireType", propOrder = {
    "idPosizioneDebitoria",
    "annoRiferimento",
    "importoTotale",
    "dataScadenza",
    "dataInizioValidita",
    "dataFineValidita",
    "descrizioneCausaleVersamento",
    "descrizioneRata",
    "componentiImporto",
    "soggettoPagatore",
    "notePerIlPagatore",
    "riferimentiPagamento"
})
public class PosizioneDaInserireType {

    @XmlElement(name = "IdPosizioneDebitoria", required = true)
    protected String idPosizioneDebitoria;
    @XmlElement(name = "AnnoRiferimento")
    @XmlSchemaType(name = "integer")
    protected Integer annoRiferimento;
    @XmlElement(name = "ImportoTotale", required = true)
    protected BigDecimal importoTotale;
    @XmlElement(name = "DataScadenza")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataScadenza;
    @XmlElement(name = "DataInizioValidita")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInizioValidita;
    @XmlElement(name = "DataFineValidita")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFineValidita;
    @XmlElement(name = "DescrizioneCausaleVersamento", required = true)
    protected String descrizioneCausaleVersamento;
    @XmlElement(name = "DescrizioneRata")
    protected String descrizioneRata;
    @XmlElement(name = "ComponentiImporto")
    protected PosizioneDaInserireType.ComponentiImporto componentiImporto;
    @XmlElement(name = "SoggettoPagatore", required = true)
    protected SoggettoType soggettoPagatore;
    @XmlElement(name = "NotePerIlPagatore")
    protected String notePerIlPagatore;
    @XmlElement(name = "RiferimentiPagamento")
    protected PosizioneDaInserireType.RiferimentiPagamento riferimentiPagamento;

    /**
     * Recupera il valore della proprietà idPosizioneDebitoria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPosizioneDebitoria() {
        return idPosizioneDebitoria;
    }

    /**
     * Imposta il valore della proprietà idPosizioneDebitoria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPosizioneDebitoria(String value) {
        this.idPosizioneDebitoria = value;
    }

    /**
     * Recupera il valore della proprietà annoRiferimento.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAnnoRiferimento() {
        return annoRiferimento;
    }

    /**
     * Imposta il valore della proprietà annoRiferimento.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAnnoRiferimento(Integer value) {
        this.annoRiferimento = value;
    }

    /**
     * Recupera il valore della proprietà importoTotale.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoTotale() {
        return importoTotale;
    }

    /**
     * Imposta il valore della proprietà importoTotale.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoTotale(BigDecimal value) {
        this.importoTotale = value;
    }

    /**
     * Recupera il valore della proprietà dataScadenza.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Imposta il valore della proprietà dataScadenza.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenza(XMLGregorianCalendar value) {
        this.dataScadenza = value;
    }

    /**
     * Recupera il valore della proprietà dataInizioValidita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Imposta il valore della proprietà dataInizioValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioValidita(XMLGregorianCalendar value) {
        this.dataInizioValidita = value;
    }

    /**
     * Recupera il valore della proprietà dataFineValidita.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Imposta il valore della proprietà dataFineValidita.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineValidita(XMLGregorianCalendar value) {
        this.dataFineValidita = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneCausaleVersamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneCausaleVersamento() {
        return descrizioneCausaleVersamento;
    }

    /**
     * Imposta il valore della proprietà descrizioneCausaleVersamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneCausaleVersamento(String value) {
        this.descrizioneCausaleVersamento = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneRata.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneRata() {
        return descrizioneRata;
    }

    /**
     * Imposta il valore della proprietà descrizioneRata.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneRata(String value) {
        this.descrizioneRata = value;
    }

    /**
     * Recupera il valore della proprietà componentiImporto.
     * 
     * @return
     *     possible object is
     *     {@link PosizioneDaInserireType.ComponentiImporto }
     *     
     */
    public PosizioneDaInserireType.ComponentiImporto getComponentiImporto() {
        return componentiImporto;
    }

    /**
     * Imposta il valore della proprietà componentiImporto.
     * 
     * @param value
     *     allowed object is
     *     {@link PosizioneDaInserireType.ComponentiImporto }
     *     
     */
    public void setComponentiImporto(PosizioneDaInserireType.ComponentiImporto value) {
        this.componentiImporto = value;
    }

    /**
     * Recupera il valore della proprietà soggettoPagatore.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoType }
     *     
     */
    public SoggettoType getSoggettoPagatore() {
        return soggettoPagatore;
    }

    /**
     * Imposta il valore della proprietà soggettoPagatore.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoType }
     *     
     */
    public void setSoggettoPagatore(SoggettoType value) {
        this.soggettoPagatore = value;
    }

    /**
     * Recupera il valore della proprietà notePerIlPagatore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotePerIlPagatore() {
        return notePerIlPagatore;
    }

    /**
     * Imposta il valore della proprietà notePerIlPagatore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotePerIlPagatore(String value) {
        this.notePerIlPagatore = value;
    }

    /**
     * Recupera il valore della proprietà riferimentiPagamento.
     * 
     * @return
     *     possible object is
     *     {@link PosizioneDaInserireType.RiferimentiPagamento }
     *     
     */
    public PosizioneDaInserireType.RiferimentiPagamento getRiferimentiPagamento() {
        return riferimentiPagamento;
    }

    /**
     * Imposta il valore della proprietà riferimentiPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link PosizioneDaInserireType.RiferimentiPagamento }
     *     
     */
    public void setRiferimentiPagamento(PosizioneDaInserireType.RiferimentiPagamento value) {
        this.riferimentiPagamento = value;
    }


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
     *         &lt;element name="ComponenteImporto" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}ComponenteImportoType" maxOccurs="5"/&gt;
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
        "componenteImporto"
    })
    public static class ComponentiImporto {

        @XmlElement(name = "ComponenteImporto", required = true)
        protected List<ComponenteImportoType> componenteImporto;

        /**
         * Gets the value of the componenteImporto property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the componenteImporto property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getComponenteImporto().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ComponenteImportoType }
         * 
         * 
         */
        public List<ComponenteImportoType> getComponenteImporto() {
            if (componenteImporto == null) {
                componenteImporto = new ArrayList<ComponenteImportoType>();
            }
            return this.componenteImporto;
        }

    }


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
     *         &lt;element name="Riferimento" type="{http://www.csi.it/epay/epaywso/enti2epaywso/types}RiferimentoType" maxOccurs="5"/&gt;
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
        "riferimento"
    })
    public static class RiferimentiPagamento {

        @XmlElement(name = "Riferimento", required = true)
        protected List<RiferimentoType> riferimento;

        /**
         * Gets the value of the riferimento property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the riferimento property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRiferimento().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RiferimentoType }
         * 
         * 
         */
        public List<RiferimentoType> getRiferimento() {
            if (riferimento == null) {
                riferimento = new ArrayList<RiferimentoType>();
            }
            return this.riferimento;
        }

    }

}
