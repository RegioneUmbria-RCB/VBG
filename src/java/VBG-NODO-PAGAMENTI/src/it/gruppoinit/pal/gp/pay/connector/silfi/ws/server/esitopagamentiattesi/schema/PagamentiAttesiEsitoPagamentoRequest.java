
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.server.esitopagamentiattesi.schema;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="ente" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codiceServizio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="esiti"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
 *                   &lt;element name="esito"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="iuv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="identificativo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="positivo" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                             &lt;element name="statoPagamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="dataPagamento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *                             &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *                             &lt;element name="extraPagoPa" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *                             &lt;element name="canalePagamentoExtraPagoPa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="canalePagamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                             &lt;element name="rt" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
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
@XmlType(name = "", propOrder = {
    "ente",
    "codiceServizio",
    "esiti"
})
@XmlRootElement(name = "pagamentiAttesiEsitoPagamentoRequest")
public class PagamentiAttesiEsitoPagamentoRequest {

    @XmlElement(required = true)
    protected String ente;
    @XmlElement(required = true)
    protected String codiceServizio;
    @XmlElement(required = true)
    protected PagamentiAttesiEsitoPagamentoRequest.Esiti esiti;

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
     * Recupera il valore della proprietà codiceServizio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceServizio() {
        return codiceServizio;
    }

    /**
     * Imposta il valore della proprietà codiceServizio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceServizio(String value) {
        this.codiceServizio = value;
    }

    /**
     * Recupera il valore della proprietà esiti.
     * 
     * @return
     *     possible object is
     *     {@link PagamentiAttesiEsitoPagamentoRequest.Esiti }
     *     
     */
    public PagamentiAttesiEsitoPagamentoRequest.Esiti getEsiti() {
        return esiti;
    }

    /**
     * Imposta il valore della proprietà esiti.
     * 
     * @param value
     *     allowed object is
     *     {@link PagamentiAttesiEsitoPagamentoRequest.Esiti }
     *     
     */
    public void setEsiti(PagamentiAttesiEsitoPagamentoRequest.Esiti value) {
        this.esiti = value;
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
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0"&gt;
     *         &lt;element name="esito"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="iuv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="identificativo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="positivo" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *                   &lt;element name="statoPagamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="dataPagamento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
     *                   &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
     *                   &lt;element name="extraPagoPa" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
     *                   &lt;element name="canalePagamentoExtraPagoPa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="canalePagamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *                   &lt;element name="rt" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
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
    @XmlType(name = "", propOrder = {
        "esito"
    })
    public static class Esiti {

        protected List<PagamentiAttesiEsitoPagamentoRequest.Esiti.Esito> esito;

        /**
         * Gets the value of the esito property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the esito property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEsito().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PagamentiAttesiEsitoPagamentoRequest.Esiti.Esito }
         * 
         * 
         */
        public List<PagamentiAttesiEsitoPagamentoRequest.Esiti.Esito> getEsito() {
            if (esito == null) {
                esito = new ArrayList<PagamentiAttesiEsitoPagamentoRequest.Esiti.Esito>();
            }
            return this.esito;
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
         *         &lt;element name="iuv" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="identificativo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="positivo" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
         *         &lt;element name="statoPagamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="dataPagamento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
         *         &lt;element name="importo" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
         *         &lt;element name="extraPagoPa" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
         *         &lt;element name="canalePagamentoExtraPagoPa" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="canalePagamento" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
         *         &lt;element name="rt" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
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
            "iuv",
            "identificativo",
            "positivo",
            "statoPagamento",
            "dataPagamento",
            "importo",
            "extraPagoPa",
            "canalePagamentoExtraPagoPa",
            "canalePagamento",
            "rt"
        })
        public static class Esito {

            @XmlElement(required = true)
            protected String iuv;
            @XmlElement(required = true)
            protected String identificativo;
            protected boolean positivo;
            @XmlElement(required = true)
            protected String statoPagamento;
            @XmlElement(required = true)
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar dataPagamento;
            @XmlElement(required = true)
            protected BigDecimal importo;
            protected boolean extraPagoPa;
            @XmlElement(required = true)
            protected String canalePagamentoExtraPagoPa;
            @XmlElement(required = true)
            protected String canalePagamento;
            @XmlElement(required = true)
            protected byte[] rt;

            /**
             * Recupera il valore della proprietà iuv.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIuv() {
                return iuv;
            }

            /**
             * Imposta il valore della proprietà iuv.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIuv(String value) {
                this.iuv = value;
            }

            /**
             * Recupera il valore della proprietà identificativo.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIdentificativo() {
                return identificativo;
            }

            /**
             * Imposta il valore della proprietà identificativo.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIdentificativo(String value) {
                this.identificativo = value;
            }

            /**
             * Recupera il valore della proprietà positivo.
             * 
             */
            public boolean isPositivo() {
                return positivo;
            }

            /**
             * Imposta il valore della proprietà positivo.
             * 
             */
            public void setPositivo(boolean value) {
                this.positivo = value;
            }

            /**
             * Recupera il valore della proprietà statoPagamento.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getStatoPagamento() {
                return statoPagamento;
            }

            /**
             * Imposta il valore della proprietà statoPagamento.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setStatoPagamento(String value) {
                this.statoPagamento = value;
            }

            /**
             * Recupera il valore della proprietà dataPagamento.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getDataPagamento() {
                return dataPagamento;
            }

            /**
             * Imposta il valore della proprietà dataPagamento.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setDataPagamento(XMLGregorianCalendar value) {
                this.dataPagamento = value;
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
             * Recupera il valore della proprietà extraPagoPa.
             * 
             */
            public boolean isExtraPagoPa() {
                return extraPagoPa;
            }

            /**
             * Imposta il valore della proprietà extraPagoPa.
             * 
             */
            public void setExtraPagoPa(boolean value) {
                this.extraPagoPa = value;
            }

            /**
             * Recupera il valore della proprietà canalePagamentoExtraPagoPa.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCanalePagamentoExtraPagoPa() {
                return canalePagamentoExtraPagoPa;
            }

            /**
             * Imposta il valore della proprietà canalePagamentoExtraPagoPa.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCanalePagamentoExtraPagoPa(String value) {
                this.canalePagamentoExtraPagoPa = value;
            }

            /**
             * Recupera il valore della proprietà canalePagamento.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getCanalePagamento() {
                return canalePagamento;
            }

            /**
             * Imposta il valore della proprietà canalePagamento.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setCanalePagamento(String value) {
                this.canalePagamento = value;
            }

            /**
             * Recupera il valore della proprietà rt.
             * 
             * @return
             *     possible object is
             *     byte[]
             */
            public byte[] getRt() {
                return rt;
            }

            /**
             * Imposta il valore della proprietà rt.
             * 
             * @param value
             *     allowed object is
             *     byte[]
             */
            public void setRt(byte[] value) {
                this.rt = value;
            }

        }

    }

}
