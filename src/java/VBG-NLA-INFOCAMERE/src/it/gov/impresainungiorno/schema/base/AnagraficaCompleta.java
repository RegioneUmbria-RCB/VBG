
package it.gov.impresainungiorno.schema.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Anagrafica completa, contiene i dati sufficienti e necessari a validare un cf
 * 
 * <p>Classe Java per AnagraficaCompleta complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AnagraficaCompleta"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/base}Anagrafica"&gt;
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
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AnagraficaCompleta", propOrder = {
    "sesso",
    "nascita"
})
@XmlSeeAlso({
    DatiPersona.class
})
public class AnagraficaCompleta
    extends Anagrafica
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected Sesso sesso;
    @XmlElement(required = true)
    protected AnagraficaCompleta.Nascita nascita;

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
     *     {@link AnagraficaCompleta.Nascita }
     *     
     */
    public AnagraficaCompleta.Nascita getNascita() {
        return nascita;
    }

    /**
     * Imposta il valore della proprietà nascita.
     * 
     * @param value
     *     allowed object is
     *     {@link AnagraficaCompleta.Nascita }
     *     
     */
    public void setNascita(AnagraficaCompleta.Nascita value) {
        this.nascita = value;
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
