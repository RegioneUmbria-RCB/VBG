//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodiceIpaBeneficiario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SospesiRiconciliati">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SospesoRiconciliato" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="NumeroSospeso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="AnnoSospeso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="TipoIdentificativoUnivocoMittente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="CodiceIdentificativoUnivocoMittente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="CodiceBicBancaDiRiversamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                             &lt;element name="PagamentiSingoli">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="PagamentoSingolo" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctRiconciliazionePagamentoSingolo" maxOccurs="unbounded" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="EsitoRiconciliazione" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stEsitoRiconciliazioneSospeso"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "codiceIpaBeneficiario",
    "sospesiRiconciliati"
})
@XmlRootElement(name = "RispostaInfoRiconciliazione")
public class RispostaInfoRiconciliazione {

    @XmlElement(name = "CodiceIpaBeneficiario", required = true)
    protected String codiceIpaBeneficiario;
    @XmlElement(name = "SospesiRiconciliati", required = true)
    protected RispostaInfoRiconciliazione.SospesiRiconciliati sospesiRiconciliati;

    /**
     * Recupera il valore della proprietà codiceIpaBeneficiario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIpaBeneficiario() {
        return codiceIpaBeneficiario;
    }

    /**
     * Imposta il valore della proprietà codiceIpaBeneficiario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIpaBeneficiario(String value) {
        this.codiceIpaBeneficiario = value;
    }

    /**
     * Recupera il valore della proprietà sospesiRiconciliati.
     * 
     * @return
     *     possible object is
     *     {@link RispostaInfoRiconciliazione.SospesiRiconciliati }
     *     
     */
    public RispostaInfoRiconciliazione.SospesiRiconciliati getSospesiRiconciliati() {
        return sospesiRiconciliati;
    }

    /**
     * Imposta il valore della proprietà sospesiRiconciliati.
     * 
     * @param value
     *     allowed object is
     *     {@link RispostaInfoRiconciliazione.SospesiRiconciliati }
     *     
     */
    public void setSospesiRiconciliati(RispostaInfoRiconciliazione.SospesiRiconciliati value) {
        this.sospesiRiconciliati = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="SospesoRiconciliato" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="NumeroSospeso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="AnnoSospeso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="TipoIdentificativoUnivocoMittente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="CodiceIdentificativoUnivocoMittente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="CodiceBicBancaDiRiversamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *                   &lt;element name="PagamentiSingoli">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="PagamentoSingolo" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctRiconciliazionePagamentoSingolo" maxOccurs="unbounded" minOccurs="0"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="EsitoRiconciliazione" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stEsitoRiconciliazioneSospeso"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "sospesoRiconciliato"
    })
    public static class SospesiRiconciliati {

        @XmlElement(name = "SospesoRiconciliato", required = true)
        protected List<RispostaInfoRiconciliazione.SospesiRiconciliati.SospesoRiconciliato> sospesoRiconciliato;

        /**
         * Gets the value of the sospesoRiconciliato property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the sospesoRiconciliato property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSospesoRiconciliato().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RispostaInfoRiconciliazione.SospesiRiconciliati.SospesoRiconciliato }
         * 
         * 
         */
        public List<RispostaInfoRiconciliazione.SospesiRiconciliati.SospesoRiconciliato> getSospesoRiconciliato() {
            if (sospesoRiconciliato == null) {
                sospesoRiconciliato = new ArrayList<RispostaInfoRiconciliazione.SospesiRiconciliati.SospesoRiconciliato>();
            }
            return this.sospesoRiconciliato;
        }


        /**
         * <p>Classe Java per anonymous complex type.
         * 
         * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="NumeroSospeso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="AnnoSospeso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="TipoIdentificativoUnivocoMittente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="CodiceIdentificativoUnivocoMittente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="CodiceBicBancaDiRiversamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
         *         &lt;element name="PagamentiSingoli">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="PagamentoSingolo" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctRiconciliazionePagamentoSingolo" maxOccurs="unbounded" minOccurs="0"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="EsitoRiconciliazione" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stEsitoRiconciliazioneSospeso"/>
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
            "numeroSospeso",
            "annoSospeso",
            "tipoIdentificativoUnivocoMittente",
            "codiceIdentificativoUnivocoMittente",
            "codiceBicBancaDiRiversamento",
            "pagamentiSingoli",
            "esitoRiconciliazione"
        })
        public static class SospesoRiconciliato {

            @XmlElement(name = "NumeroSospeso")
            protected String numeroSospeso;
            @XmlElement(name = "AnnoSospeso")
            protected String annoSospeso;
            @XmlElement(name = "TipoIdentificativoUnivocoMittente")
            protected String tipoIdentificativoUnivocoMittente;
            @XmlElement(name = "CodiceIdentificativoUnivocoMittente")
            protected String codiceIdentificativoUnivocoMittente;
            @XmlElement(name = "CodiceBicBancaDiRiversamento")
            protected String codiceBicBancaDiRiversamento;
            @XmlElement(name = "PagamentiSingoli", required = true)
            protected RispostaInfoRiconciliazione.SospesiRiconciliati.SospesoRiconciliato.PagamentiSingoli pagamentiSingoli;
            @XmlElement(name = "EsitoRiconciliazione", required = true)
            @XmlSchemaType(name = "string")
            protected StEsitoRiconciliazioneSospeso esitoRiconciliazione;

            /**
             * Recupera il valore della proprietà numeroSospeso.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumeroSospeso() {
                return numeroSospeso;
            }

            /**
             * Imposta il valore della proprietà numeroSospeso.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumeroSospeso(String value) {
                this.numeroSospeso = value;
            }

            /**
             * Recupera il valore della proprietà annoSospeso.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAnnoSospeso() {
                return annoSospeso;
            }

            /**
             * Imposta il valore della proprietà annoSospeso.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAnnoSospeso(String value) {
                this.annoSospeso = value;
            }

            /**
             * Recupera il valore della proprietà tipoIdentificativoUnivocoMittente.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTipoIdentificativoUnivocoMittente() {
                return tipoIdentificativoUnivocoMittente;
            }

            /**
             * Imposta il valore della proprietà tipoIdentificativoUnivocoMittente.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTipoIdentificativoUnivocoMittente(String value) {
                this.tipoIdentificativoUnivocoMittente = value;
            }

            /**
             * Recupera il valore della proprietà codiceIdentificativoUnivocoMittente.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodiceIdentificativoUnivocoMittente() {
                return codiceIdentificativoUnivocoMittente;
            }

            /**
             * Imposta il valore della proprietà codiceIdentificativoUnivocoMittente.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodiceIdentificativoUnivocoMittente(String value) {
                this.codiceIdentificativoUnivocoMittente = value;
            }

            /**
             * Recupera il valore della proprietà codiceBicBancaDiRiversamento.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCodiceBicBancaDiRiversamento() {
                return codiceBicBancaDiRiversamento;
            }

            /**
             * Imposta il valore della proprietà codiceBicBancaDiRiversamento.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCodiceBicBancaDiRiversamento(String value) {
                this.codiceBicBancaDiRiversamento = value;
            }

            /**
             * Recupera il valore della proprietà pagamentiSingoli.
             * 
             * @return
             *     possible object is
             *     {@link RispostaInfoRiconciliazione.SospesiRiconciliati.SospesoRiconciliato.PagamentiSingoli }
             *     
             */
            public RispostaInfoRiconciliazione.SospesiRiconciliati.SospesoRiconciliato.PagamentiSingoli getPagamentiSingoli() {
                return pagamentiSingoli;
            }

            /**
             * Imposta il valore della proprietà pagamentiSingoli.
             * 
             * @param value
             *     allowed object is
             *     {@link RispostaInfoRiconciliazione.SospesiRiconciliati.SospesoRiconciliato.PagamentiSingoli }
             *     
             */
            public void setPagamentiSingoli(RispostaInfoRiconciliazione.SospesiRiconciliati.SospesoRiconciliato.PagamentiSingoli value) {
                this.pagamentiSingoli = value;
            }

            /**
             * Recupera il valore della proprietà esitoRiconciliazione.
             * 
             * @return
             *     possible object is
             *     {@link StEsitoRiconciliazioneSospeso }
             *     
             */
            public StEsitoRiconciliazioneSospeso getEsitoRiconciliazione() {
                return esitoRiconciliazione;
            }

            /**
             * Imposta il valore della proprietà esitoRiconciliazione.
             * 
             * @param value
             *     allowed object is
             *     {@link StEsitoRiconciliazioneSospeso }
             *     
             */
            public void setEsitoRiconciliazione(StEsitoRiconciliazioneSospeso value) {
                this.esitoRiconciliazione = value;
            }


            /**
             * <p>Classe Java per anonymous complex type.
             * 
             * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="PagamentoSingolo" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctRiconciliazionePagamentoSingolo" maxOccurs="unbounded" minOccurs="0"/>
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
                "pagamentoSingolo"
            })
            public static class PagamentiSingoli {

                @XmlElement(name = "PagamentoSingolo")
                protected List<CtRiconciliazionePagamentoSingolo> pagamentoSingolo;

                /**
                 * Gets the value of the pagamentoSingolo property.
                 * 
                 * <p>
                 * This accessor method returns a reference to the live list,
                 * not a snapshot. Therefore any modification you make to the
                 * returned list will be present inside the JAXB object.
                 * This is why there is not a <CODE>set</CODE> method for the pagamentoSingolo property.
                 * 
                 * <p>
                 * For example, to add a new item, do as follows:
                 * <pre>
                 *    getPagamentoSingolo().add(newItem);
                 * </pre>
                 * 
                 * 
                 * <p>
                 * Objects of the following type(s) are allowed in the list
                 * {@link CtRiconciliazionePagamentoSingolo }
                 * 
                 * 
                 */
                public List<CtRiconciliazionePagamentoSingolo> getPagamentoSingolo() {
                    if (pagamentoSingolo == null) {
                        pagamentoSingolo = new ArrayList<CtRiconciliazionePagamentoSingolo>();
                    }
                    return this.pagamentoSingolo;
                }

            }

        }

    }

}
