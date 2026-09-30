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
 *         &lt;element name="IdentificativoUnivocoVersamento" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "identificativoUnivocoVersamento",
    "pagamentiSingoli"
})
@XmlRootElement(name = "RispostaInfoRiconciliazioneVersamento")
public class RispostaInfoRiconciliazioneVersamento {

    @XmlElement(name = "CodiceIpaBeneficiario", required = true)
    protected String codiceIpaBeneficiario;
    @XmlElement(name = "IdentificativoUnivocoVersamento", required = true)
    protected String identificativoUnivocoVersamento;
    @XmlElement(name = "PagamentiSingoli", required = true)
    protected RispostaInfoRiconciliazioneVersamento.PagamentiSingoli pagamentiSingoli;

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
     * Recupera il valore della proprietà identificativoUnivocoVersamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoUnivocoVersamento() {
        return identificativoUnivocoVersamento;
    }

    /**
     * Imposta il valore della proprietà identificativoUnivocoVersamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoUnivocoVersamento(String value) {
        this.identificativoUnivocoVersamento = value;
    }

    /**
     * Recupera il valore della proprietà pagamentiSingoli.
     * 
     * @return
     *     possible object is
     *     {@link RispostaInfoRiconciliazioneVersamento.PagamentiSingoli }
     *     
     */
    public RispostaInfoRiconciliazioneVersamento.PagamentiSingoli getPagamentiSingoli() {
        return pagamentiSingoli;
    }

    /**
     * Imposta il valore della proprietà pagamentiSingoli.
     * 
     * @param value
     *     allowed object is
     *     {@link RispostaInfoRiconciliazioneVersamento.PagamentiSingoli }
     *     
     */
    public void setPagamentiSingoli(RispostaInfoRiconciliazioneVersamento.PagamentiSingoli value) {
        this.pagamentiSingoli = value;
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
