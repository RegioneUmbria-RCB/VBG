
package it.gov.impresainungiorno.schema.base;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per Ricevuta complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Ricevuta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="operazione" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/&gt;
 *         &lt;element name="codice-fiscale-impresa" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceFiscale" minOccurs="0"/&gt;
 *         &lt;element name="ragione-sociale-impresa" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" minOccurs="0"/&gt;
 *         &lt;element name="operatore" type="{http://www.impresainungiorno.gov.it/schema/base}UtentePortale" form="qualified"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="ente" type="{http://www.impresainungiorno.gov.it/schema/base}EnteRicevuta" maxOccurs="unbounded"/&gt;
 *           &lt;element name="errore" type="{http://www.impresainungiorno.gov.it/schema/base}Errore" maxOccurs="unbounded"/&gt;
 *         &lt;/choice&gt;
 *         &lt;element name="lista-adempimenti" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="ente" type="{http://www.impresainungiorno.gov.it/schema/base}StringaBreve"/&gt;
 *                   &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="riferimento-portale" type="{http://www.impresainungiorno.gov.it/schema/base}RiferimentoPortale" /&gt;
 *       &lt;attribute name="tempo-invio" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Ricevuta", propOrder = {
    "operazione",
    "codiceFiscaleImpresa",
    "ragioneSocialeImpresa",
    "operatore",
    "ente",
    "errore",
    "listaAdempimenti"
})
public class Ricevuta {

    @XmlElement(required = true)
    protected String operazione;
    @XmlElement(name = "codice-fiscale-impresa")
    protected String codiceFiscaleImpresa;
    @XmlElement(name = "ragione-sociale-impresa")
    protected String ragioneSocialeImpresa;
    @XmlElement(namespace = "http://www.impresainungiorno.gov.it/schema/base", required = true)
    protected UtentePortale operatore;
    protected List<EnteRicevuta> ente;
    protected List<Errore> errore;
    @XmlElement(name = "lista-adempimenti")
    protected List<Ricevuta.ListaAdempimenti> listaAdempimenti;
    @XmlAttribute(name = "riferimento-portale")
    protected String riferimentoPortale;
    @XmlAttribute(name = "tempo-invio", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar tempoInvio;

    /**
     * Recupera il valore della proprietà operazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperazione() {
        return operazione;
    }

    /**
     * Imposta il valore della proprietà operazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperazione(String value) {
        this.operazione = value;
    }

    /**
     * Recupera il valore della proprietà codiceFiscaleImpresa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscaleImpresa() {
        return codiceFiscaleImpresa;
    }

    /**
     * Imposta il valore della proprietà codiceFiscaleImpresa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscaleImpresa(String value) {
        this.codiceFiscaleImpresa = value;
    }

    /**
     * Recupera il valore della proprietà ragioneSocialeImpresa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRagioneSocialeImpresa() {
        return ragioneSocialeImpresa;
    }

    /**
     * Imposta il valore della proprietà ragioneSocialeImpresa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRagioneSocialeImpresa(String value) {
        this.ragioneSocialeImpresa = value;
    }

    /**
     * Recupera il valore della proprietà operatore.
     * 
     * @return
     *     possible object is
     *     {@link UtentePortale }
     *     
     */
    public UtentePortale getOperatore() {
        return operatore;
    }

    /**
     * Imposta il valore della proprietà operatore.
     * 
     * @param value
     *     allowed object is
     *     {@link UtentePortale }
     *     
     */
    public void setOperatore(UtentePortale value) {
        this.operatore = value;
    }

    /**
     * Gets the value of the ente property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ente property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEnte().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EnteRicevuta }
     * 
     * 
     */
    public List<EnteRicevuta> getEnte() {
        if (ente == null) {
            ente = new ArrayList<EnteRicevuta>();
        }
        return this.ente;
    }

    /**
     * Gets the value of the errore property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the errore property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getErrore().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Errore }
     * 
     * 
     */
    public List<Errore> getErrore() {
        if (errore == null) {
            errore = new ArrayList<Errore>();
        }
        return this.errore;
    }

    /**
     * Gets the value of the listaAdempimenti property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listaAdempimenti property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListaAdempimenti().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Ricevuta.ListaAdempimenti }
     * 
     * 
     */
    public List<Ricevuta.ListaAdempimenti> getListaAdempimenti() {
        if (listaAdempimenti == null) {
            listaAdempimenti = new ArrayList<Ricevuta.ListaAdempimenti>();
        }
        return this.listaAdempimenti;
    }

    /**
     * Recupera il valore della proprietà riferimentoPortale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRiferimentoPortale() {
        return riferimentoPortale;
    }

    /**
     * Imposta il valore della proprietà riferimentoPortale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRiferimentoPortale(String value) {
        this.riferimentoPortale = value;
    }

    /**
     * Recupera il valore della proprietà tempoInvio.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTempoInvio() {
        return tempoInvio;
    }

    /**
     * Imposta il valore della proprietà tempoInvio.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTempoInvio(XMLGregorianCalendar value) {
        this.tempoInvio = value;
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
     *         &lt;element name="ente" type="{http://www.impresainungiorno.gov.it/schema/base}StringaBreve"/&gt;
     *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "ente",
        "id"
    })
    public static class ListaAdempimenti {

        @XmlElement(required = true)
        protected String ente;
        @XmlElement(required = true)
        protected String id;

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
         * Recupera il valore della proprietà id.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getId() {
            return id;
        }

        /**
         * Imposta il valore della proprietà id.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setId(String value) {
            this.id = value;
        }

    }

}
