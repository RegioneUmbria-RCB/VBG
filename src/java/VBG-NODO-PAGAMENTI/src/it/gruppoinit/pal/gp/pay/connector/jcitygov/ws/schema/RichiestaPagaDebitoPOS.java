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
 *         &lt;element name="IdRichiestaPagamento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Versante" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctSoggetto"/>
 *         &lt;element name="Debito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctDebito"/>
 *         &lt;element name="IdentificativoPostazionePOS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DebugMode" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctDebugMode" minOccurs="0"/>
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
    "idRichiestaPagamento",
    "versante",
    "debito",
    "identificativoPostazionePOS",
    "debugMode"
})
@XmlRootElement(name = "RichiestaPagaDebitoPOS")
public class RichiestaPagaDebitoPOS {

    @XmlElement(name = "IdRichiestaPagamento", required = true)
    protected String idRichiestaPagamento;
    @XmlElement(name = "Versante", required = true)
    protected CtSoggetto versante;
    @XmlElement(name = "Debito", required = true)
    protected CtDebito debito;
    @XmlElement(name = "IdentificativoPostazionePOS", required = true)
    protected String identificativoPostazionePOS;
    @XmlElement(name = "DebugMode")
    protected CtDebugMode debugMode;

    /**
     * Recupera il valore della proprietà idRichiestaPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRichiestaPagamento() {
        return idRichiestaPagamento;
    }

    /**
     * Imposta il valore della proprietà idRichiestaPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRichiestaPagamento(String value) {
        this.idRichiestaPagamento = value;
    }

    /**
     * Recupera il valore della proprietà versante.
     * 
     * @return
     *     possible object is
     *     {@link CtSoggetto }
     *     
     */
    public CtSoggetto getVersante() {
        return versante;
    }

    /**
     * Imposta il valore della proprietà versante.
     * 
     * @param value
     *     allowed object is
     *     {@link CtSoggetto }
     *     
     */
    public void setVersante(CtSoggetto value) {
        this.versante = value;
    }

    /**
     * Recupera il valore della proprietà debito.
     * 
     * @return
     *     possible object is
     *     {@link CtDebito }
     *     
     */
    public CtDebito getDebito() {
        return debito;
    }

    /**
     * Imposta il valore della proprietà debito.
     * 
     * @param value
     *     allowed object is
     *     {@link CtDebito }
     *     
     */
    public void setDebito(CtDebito value) {
        this.debito = value;
    }

    /**
     * Recupera il valore della proprietà identificativoPostazionePOS.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentificativoPostazionePOS() {
        return identificativoPostazionePOS;
    }

    /**
     * Imposta il valore della proprietà identificativoPostazionePOS.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificativoPostazionePOS(String value) {
        this.identificativoPostazionePOS = value;
    }

    /**
     * Recupera il valore della proprietà debugMode.
     * 
     * @return
     *     possible object is
     *     {@link CtDebugMode }
     *     
     */
    public CtDebugMode getDebugMode() {
        return debugMode;
    }

    /**
     * Imposta il valore della proprietà debugMode.
     * 
     * @param value
     *     allowed object is
     *     {@link CtDebugMode }
     *     
     */
    public void setDebugMode(CtDebugMode value) {
        this.debugMode = value;
    }

}
