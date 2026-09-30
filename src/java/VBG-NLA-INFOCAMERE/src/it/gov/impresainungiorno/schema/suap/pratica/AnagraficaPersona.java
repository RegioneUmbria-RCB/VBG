
package it.gov.impresainungiorno.schema.suap.pratica;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import it.gov.impresainungiorno.schema.base.Comune;
import it.gov.impresainungiorno.schema.base.EMail;
import it.gov.impresainungiorno.schema.base.Indirizzo;
import it.gov.impresainungiorno.schema.base.Provincia;
import it.gov.impresainungiorno.schema.base.Sesso;
import it.gov.impresainungiorno.schema.base.Stato;
import it.gov.impresainungiorno.schema.base.Telefono;


/**
 *  Dati relativi ad una persona fisica 
 * 
 * <p>Classe Java per AnagraficaPersona complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AnagraficaPersona"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/suap/pratica}Anagrafica"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="sesso" type="{http://www.impresainungiorno.gov.it/schema/base}Sesso"/&gt;
 *         &lt;element name="nascita"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}Citta"/&gt;
 *                   &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="residenza" type="{http://www.impresainungiorno.gov.it/schema/base}Indirizzo" minOccurs="0"/&gt;
 *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}Recapiti"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnagraficaPersona", propOrder = {
    "sesso",
    "nascita",
    "residenza",
    "eMail",
    "telefono",
    "sitoWeb"
})
public class AnagraficaPersona
    extends Anagrafica
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected Sesso sesso;
    @XmlElement(required = true)
    protected AnagraficaPersona.Nascita nascita;
    protected Indirizzo residenza;
    @XmlElement(name = "e-mail")
    protected List<EMail> eMail;
    protected List<Telefono> telefono;
    @XmlElement(name = "sito-web")
    @XmlSchemaType(name = "anyURI")
    protected List<String> sitoWeb;

    /**
     * Recupera il valore della proprietà sesso.
     * 
     * @return
     *     possible object is
     *     {@link Sesso }
     *     
     */
    public Sesso getSesso() {
        return sesso;
    }

    /**
     * Imposta il valore della proprietà sesso.
     * 
     * @param value
     *     allowed object is
     *     {@link Sesso }
     *     
     */
    public void setSesso(Sesso value) {
        this.sesso = value;
    }

    /**
     * Recupera il valore della proprietà nascita.
     * 
     * @return
     *     possible object is
     *     {@link AnagraficaPersona.Nascita }
     *     
     */
    public AnagraficaPersona.Nascita getNascita() {
        return nascita;
    }

    /**
     * Imposta il valore della proprietà nascita.
     * 
     * @param value
     *     allowed object is
     *     {@link AnagraficaPersona.Nascita }
     *     
     */
    public void setNascita(AnagraficaPersona.Nascita value) {
        this.nascita = value;
    }

    /**
     * Recupera il valore della proprietà residenza.
     * 
     * @return
     *     possible object is
     *     {@link Indirizzo }
     *     
     */
    public Indirizzo getResidenza() {
        return residenza;
    }

    /**
     * Imposta il valore della proprietà residenza.
     * 
     * @param value
     *     allowed object is
     *     {@link Indirizzo }
     *     
     */
    public void setResidenza(Indirizzo value) {
        this.residenza = value;
    }

    /**
     * Gets the value of the eMail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eMail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEMail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EMail }
     * 
     * 
     */
    public List<EMail> getEMail() {
        if (eMail == null) {
            eMail = new ArrayList<EMail>();
        }
        return this.eMail;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the telefono property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTelefono().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Telefono }
     * 
     * 
     */
    public List<Telefono> getTelefono() {
        if (telefono == null) {
            telefono = new ArrayList<Telefono>();
        }
        return this.telefono;
    }

    /**
     * Gets the value of the sitoWeb property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sitoWeb property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSitoWeb().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSitoWeb() {
        if (sitoWeb == null) {
            sitoWeb = new ArrayList<String>();
        }
        return this.sitoWeb;
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
     *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}Citta"/&gt;
     *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
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
        "stato",
        "provincia",
        "comune",
        "cittaStraniera",
        "data"
    })
    public static class Nascita {

        @XmlElement(required = true)
        protected Stato stato;
        protected Provincia provincia;
        protected Comune comune;
        @XmlElement(name = "citta-straniera")
        protected String cittaStraniera;
        @XmlElement(required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar data;

        /**
         * Recupera il valore della proprietà stato.
         * 
         * @return
         *     possible object is
         *     {@link Stato }
         *     
         */
        public Stato getStato() {
            return stato;
        }

        /**
         * Imposta il valore della proprietà stato.
         * 
         * @param value
         *     allowed object is
         *     {@link Stato }
         *     
         */
        public void setStato(Stato value) {
            this.stato = value;
        }

        /**
         * Recupera il valore della proprietà provincia.
         * 
         * @return
         *     possible object is
         *     {@link Provincia }
         *     
         */
        public Provincia getProvincia() {
            return provincia;
        }

        /**
         * Imposta il valore della proprietà provincia.
         * 
         * @param value
         *     allowed object is
         *     {@link Provincia }
         *     
         */
        public void setProvincia(Provincia value) {
            this.provincia = value;
        }

        /**
         * Recupera il valore della proprietà comune.
         * 
         * @return
         *     possible object is
         *     {@link Comune }
         *     
         */
        public Comune getComune() {
            return comune;
        }

        /**
         * Imposta il valore della proprietà comune.
         * 
         * @param value
         *     allowed object is
         *     {@link Comune }
         *     
         */
        public void setComune(Comune value) {
            this.comune = value;
        }

        /**
         * Recupera il valore della proprietà cittaStraniera.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCittaStraniera() {
            return cittaStraniera;
        }

        /**
         * Imposta il valore della proprietà cittaStraniera.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCittaStraniera(String value) {
            this.cittaStraniera = value;
        }

        /**
         * Recupera il valore della proprietà data.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getData() {
            return data;
        }

        /**
         * Imposta il valore della proprietà data.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setData(XMLGregorianCalendar value) {
            this.data = value;
        }

    }

}
