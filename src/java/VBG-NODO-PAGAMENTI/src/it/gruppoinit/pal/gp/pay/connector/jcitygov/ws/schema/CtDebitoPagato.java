//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctDebitoPagato complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctDebitoPagato">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ChiaviDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctChiaveDebito"/>
 *         &lt;element name="IdentificativoUnivocoVersamento" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stIUV"/>
 *         &lt;element name="ModalitaPagamento" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stModPagamento"/>
 *         &lt;element name="StatoPagamento" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stStatoPagamento"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctDebitoPagato", propOrder = {
    "chiaviDebito",
    "identificativoUnivocoVersamento",
    "modalitaPagamento",
    "statoPagamento"
})
public class CtDebitoPagato {

    @XmlElement(name = "ChiaviDebito", required = true)
    protected CtChiaveDebito chiaviDebito;
    @XmlElement(name = "IdentificativoUnivocoVersamento", required = true)
    protected String identificativoUnivocoVersamento;
    @XmlElement(name = "ModalitaPagamento", required = true)
    @XmlSchemaType(name = "string")
    protected StModPagamento modalitaPagamento;
    @XmlElement(name = "StatoPagamento", required = true)
    @XmlSchemaType(name = "string")
    protected StStatoPagamento statoPagamento;

    /**
     * Recupera il valore della proprietà chiaviDebito.
     * 
     * @return
     *     possible object is
     *     {@link CtChiaveDebito }
     *     
     */
    public CtChiaveDebito getChiaviDebito() {
        return chiaviDebito;
    }

    /**
     * Imposta il valore della proprietà chiaviDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link CtChiaveDebito }
     *     
     */
    public void setChiaviDebito(CtChiaveDebito value) {
        this.chiaviDebito = value;
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
     * Recupera il valore della proprietà modalitaPagamento.
     * 
     * @return
     *     possible object is
     *     {@link StModPagamento }
     *     
     */
    public StModPagamento getModalitaPagamento() {
        return modalitaPagamento;
    }

    /**
     * Imposta il valore della proprietà modalitaPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link StModPagamento }
     *     
     */
    public void setModalitaPagamento(StModPagamento value) {
        this.modalitaPagamento = value;
    }

    /**
     * Recupera il valore della proprietà statoPagamento.
     * 
     * @return
     *     possible object is
     *     {@link StStatoPagamento }
     *     
     */
    public StStatoPagamento getStatoPagamento() {
        return statoPagamento;
    }

    /**
     * Imposta il valore della proprietà statoPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link StStatoPagamento }
     *     
     */
    public void setStatoPagamento(StStatoPagamento value) {
        this.statoPagamento = value;
    }

}
