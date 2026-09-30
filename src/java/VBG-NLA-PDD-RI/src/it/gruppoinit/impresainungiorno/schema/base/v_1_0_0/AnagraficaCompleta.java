
package it.gruppoinit.impresainungiorno.schema.base.v_1_0_0;

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
 * <p>Java class for AnagraficaCompleta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AnagraficaCompleta">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/base}Anagrafica">
 *       &lt;sequence>
 *         &lt;element name="sesso" type="{http://www.impresainungiorno.gov.it/schema/base}Sesso"/>
 *         &lt;element name="nascita">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}Citta"/>
 *                   &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
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
    protected Sesso sesso;
    @XmlElement(required = true)
    protected AnagraficaCompleta.Nascita nascita;

    /**
     * Gets the value of the sesso property.
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
     * Sets the value of the sesso property.
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
     * Gets the value of the nascita property.
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
     * Sets the value of the nascita property.
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;group ref="{http://www.impresainungiorno.gov.it/schema/base}Citta"/>
     *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
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
         * Gets the value of the stato property.
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
         * Sets the value of the stato property.
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
         * Gets the value of the provincia property.
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
         * Sets the value of the provincia property.
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
         * Gets the value of the comune property.
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
         * Sets the value of the comune property.
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
         * Gets the value of the cittaStraniera property.
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
         * Sets the value of the cittaStraniera property.
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
         * Gets the value of the data property.
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
         * Sets the value of the data property.
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
