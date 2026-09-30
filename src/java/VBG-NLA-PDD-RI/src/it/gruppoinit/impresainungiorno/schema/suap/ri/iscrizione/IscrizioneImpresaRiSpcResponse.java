
package it.gruppoinit.impresainungiorno.schema.suap.ri.iscrizione;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.AttivitaISTAT;
import it.gruppoinit.impresainungiorno.schema.base.v_1_0_0.IndirizzoConRecapiti;


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
 *         &lt;choice>
 *           &lt;element name="errore" type="{http://www.impresainungiorno.gov.it/schema/suap/ri/spc}ErroreDettaglioImpresa"/>
 *           &lt;sequence>
 *             &lt;element name="warning" type="{http://www.impresainungiorno.gov.it/schema/suap/ri/spc}WarningDettaglioImpresa" maxOccurs="unbounded" minOccurs="0"/>
 *             &lt;element name="dati-identificativi">
 *               &lt;complexType>
 *                 &lt;complexContent>
 *                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                     &lt;sequence>
 *                       &lt;element name="stato-impresa" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" minOccurs="0"/>
 *                       &lt;element name="denominazione" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/>
 *                       &lt;element name="codice-fiscale" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceFiscale"/>
 *                       &lt;element name="cciaa" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/>
 *                       &lt;element name="nrea" type="{http://www.impresainungiorno.gov.it/schema/base}NumeroREA"/>
 *                       &lt;element name="partita-iva" type="{http://www.impresainungiorno.gov.it/schema/base}PartitaIVA" minOccurs="0"/>
 *                     &lt;/sequence>
 *                   &lt;/restriction>
 *                 &lt;/complexContent>
 *               &lt;/complexType>
 *             &lt;/element>
 *             &lt;element name="sede-legale" type="{http://www.impresainungiorno.gov.it/schema/base}IndirizzoConRecapiti"/>
 *             &lt;element name="attivita" type="{http://www.impresainungiorno.gov.it/schema/base}AttivitaISTAT" maxOccurs="unbounded" minOccurs="0"/>
 *             &lt;element name="unita-locale" type="{http://www.impresainungiorno.gov.it/schema/suap/ri/spc}UnitaLocale" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;/sequence>
 *         &lt;/choice>
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
    "errore",
    "warning",
    "datiIdentificativi",
    "sedeLegale",
    "attivita",
    "unitaLocale"
})
@XmlRootElement(name = "IscrizioneImpresaRiSpcResponse")
public class IscrizioneImpresaRiSpcResponse {

    protected ErroreDettaglioImpresa errore;
    protected List<WarningDettaglioImpresa> warning;
    @XmlElement(name = "dati-identificativi")
    protected IscrizioneImpresaRiSpcResponse.DatiIdentificativi datiIdentificativi;
    @XmlElement(name = "sede-legale")
    protected IndirizzoConRecapiti sedeLegale;
    protected List<AttivitaISTAT> attivita;
    @XmlElement(name = "unita-locale")
    protected List<UnitaLocale> unitaLocale;

    /**
     * Gets the value of the errore property.
     * 
     * @return
     *     possible object is
     *     {@link ErroreDettaglioImpresa }
     *     
     */
    public ErroreDettaglioImpresa getErrore() {
        return errore;
    }

    /**
     * Sets the value of the errore property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErroreDettaglioImpresa }
     *     
     */
    public void setErrore(ErroreDettaglioImpresa value) {
        this.errore = value;
    }

    /**
     * Gets the value of the warning property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the warning property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWarning().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WarningDettaglioImpresa }
     * 
     * 
     */
    public List<WarningDettaglioImpresa> getWarning() {
        if (warning == null) {
            warning = new ArrayList<WarningDettaglioImpresa>();
        }
        return this.warning;
    }

    /**
     * Gets the value of the datiIdentificativi property.
     * 
     * @return
     *     possible object is
     *     {@link IscrizioneImpresaRiSpcResponse.DatiIdentificativi }
     *     
     */
    public IscrizioneImpresaRiSpcResponse.DatiIdentificativi getDatiIdentificativi() {
        return datiIdentificativi;
    }

    /**
     * Sets the value of the datiIdentificativi property.
     * 
     * @param value
     *     allowed object is
     *     {@link IscrizioneImpresaRiSpcResponse.DatiIdentificativi }
     *     
     */
    public void setDatiIdentificativi(IscrizioneImpresaRiSpcResponse.DatiIdentificativi value) {
        this.datiIdentificativi = value;
    }

    /**
     * Gets the value of the sedeLegale property.
     * 
     * @return
     *     possible object is
     *     {@link IndirizzoConRecapiti }
     *     
     */
    public IndirizzoConRecapiti getSedeLegale() {
        return sedeLegale;
    }

    /**
     * Sets the value of the sedeLegale property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndirizzoConRecapiti }
     *     
     */
    public void setSedeLegale(IndirizzoConRecapiti value) {
        this.sedeLegale = value;
    }

    /**
     * Gets the value of the attivita property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attivita property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttivita().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttivitaISTAT }
     * 
     * 
     */
    public List<AttivitaISTAT> getAttivita() {
        if (attivita == null) {
            attivita = new ArrayList<AttivitaISTAT>();
        }
        return this.attivita;
    }

    /**
     * Gets the value of the unitaLocale property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unitaLocale property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnitaLocale().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UnitaLocale }
     * 
     * 
     */
    public List<UnitaLocale> getUnitaLocale() {
        if (unitaLocale == null) {
            unitaLocale = new ArrayList<UnitaLocale>();
        }
        return this.unitaLocale;
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
     *         &lt;element name="stato-impresa" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" minOccurs="0"/>
     *         &lt;element name="denominazione" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/>
     *         &lt;element name="codice-fiscale" type="{http://www.impresainungiorno.gov.it/schema/base}CodiceFiscale"/>
     *         &lt;element name="cciaa" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa"/>
     *         &lt;element name="nrea" type="{http://www.impresainungiorno.gov.it/schema/base}NumeroREA"/>
     *         &lt;element name="partita-iva" type="{http://www.impresainungiorno.gov.it/schema/base}PartitaIVA" minOccurs="0"/>
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
        "statoImpresa",
        "denominazione",
        "codiceFiscale",
        "cciaa",
        "nrea",
        "partitaIva"
    })
    public static class DatiIdentificativi {

        @XmlElement(name = "stato-impresa")
        protected String statoImpresa;
        @XmlElement(required = true)
        protected String denominazione;
        @XmlElement(name = "codice-fiscale", required = true)
        protected String codiceFiscale;
        @XmlElement(required = true)
        protected String cciaa;
        @XmlElement(required = true)
        protected String nrea;
        @XmlElement(name = "partita-iva")
        protected String partitaIva;

        /**
         * Gets the value of the statoImpresa property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatoImpresa() {
            return statoImpresa;
        }

        /**
         * Sets the value of the statoImpresa property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatoImpresa(String value) {
            this.statoImpresa = value;
        }

        /**
         * Gets the value of the denominazione property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDenominazione() {
            return denominazione;
        }

        /**
         * Sets the value of the denominazione property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDenominazione(String value) {
            this.denominazione = value;
        }

        /**
         * Gets the value of the codiceFiscale property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodiceFiscale() {
            return codiceFiscale;
        }

        /**
         * Sets the value of the codiceFiscale property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodiceFiscale(String value) {
            this.codiceFiscale = value;
        }

        /**
         * Gets the value of the cciaa property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCciaa() {
            return cciaa;
        }

        /**
         * Sets the value of the cciaa property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCciaa(String value) {
            this.cciaa = value;
        }

        /**
         * Gets the value of the nrea property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNrea() {
            return nrea;
        }

        /**
         * Sets the value of the nrea property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNrea(String value) {
            this.nrea = value;
        }

        /**
         * Gets the value of the partitaIva property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPartitaIva() {
            return partitaIva;
        }

        /**
         * Sets the value of the partitaIva property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPartitaIva(String value) {
            this.partitaIva = value;
        }

    }

}
