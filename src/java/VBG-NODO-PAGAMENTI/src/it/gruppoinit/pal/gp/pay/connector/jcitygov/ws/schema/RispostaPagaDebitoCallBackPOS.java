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
 *         &lt;element name="ChiaviDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctChiaveDebito"/>
 *         &lt;element name="IdentificativoUnivocoVersamento" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stIUV"/>
 *         &lt;element name="IdDebito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Esito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctEsito"/>
 *         &lt;element name="ScontrinoPos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "chiaviDebito",
    "identificativoUnivocoVersamento",
    "idDebito",
    "esito",
    "scontrinoPos"
})
@XmlRootElement(name = "RispostaPagaDebitoCallBackPOS")
public class RispostaPagaDebitoCallBackPOS {

    @XmlElement(name = "ChiaviDebito", required = true)
    protected CtChiaveDebito chiaviDebito;
    @XmlElement(name = "IdentificativoUnivocoVersamento", required = true)
    protected String identificativoUnivocoVersamento;
    @XmlElement(name = "IdDebito", required = true)
    protected String idDebito;
    @XmlElement(name = "Esito", required = true)
    protected CtEsito esito;
    @XmlElement(name = "ScontrinoPos")
    protected String scontrinoPos;

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
     * Recupera il valore della proprietà idDebito.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdDebito() {
        return idDebito;
    }

    /**
     * Imposta il valore della proprietà idDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdDebito(String value) {
        this.idDebito = value;
    }

    /**
     * Recupera il valore della proprietà esito.
     * 
     * @return
     *     possible object is
     *     {@link CtEsito }
     *     
     */
    public CtEsito getEsito() {
        return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     * @param value
     *     allowed object is
     *     {@link CtEsito }
     *     
     */
    public void setEsito(CtEsito value) {
        this.esito = value;
    }

    /**
     * Recupera il valore della proprietà scontrinoPos.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScontrinoPos() {
        return scontrinoPos;
    }

    /**
     * Imposta il valore della proprietà scontrinoPos.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScontrinoPos(String value) {
        this.scontrinoPos = value;
    }

}
