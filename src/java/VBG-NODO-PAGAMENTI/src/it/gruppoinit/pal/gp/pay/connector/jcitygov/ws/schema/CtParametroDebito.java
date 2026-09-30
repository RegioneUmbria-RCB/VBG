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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ctParametroDebito complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctParametroDebito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodiceParametro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ValoreParametro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctParametroDebito", propOrder = {
    "codiceParametro",
    "valoreParametro"
})
public class CtParametroDebito {

    @XmlElement(name = "CodiceParametro", required = true)
    protected String codiceParametro;
    @XmlElement(name = "ValoreParametro", required = true)
    protected String valoreParametro;

    /**
     * Recupera il valore della proprietà codiceParametro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceParametro() {
        return codiceParametro;
    }

    /**
     * Imposta il valore della proprietà codiceParametro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceParametro(String value) {
        this.codiceParametro = value;
    }

    /**
     * Recupera il valore della proprietà valoreParametro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValoreParametro() {
        return valoreParametro;
    }

    /**
     * Imposta il valore della proprietà valoreParametro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValoreParametro(String value) {
        this.valoreParametro = value;
    }

}
