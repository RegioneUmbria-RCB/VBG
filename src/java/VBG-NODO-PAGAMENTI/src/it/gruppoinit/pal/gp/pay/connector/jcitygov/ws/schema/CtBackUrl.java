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
 * <p>Classe Java per ctBackUrl complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctBackUrl">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BackUrlOK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BackUrlKO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BackUrlNotify" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctBackUrl", propOrder = {
    "backUrlOK",
    "backUrlKO",
    "backUrlNotify"
})
public class CtBackUrl {

    @XmlElement(name = "BackUrlOK", required = true)
    protected String backUrlOK;
    @XmlElement(name = "BackUrlKO", required = true)
    protected String backUrlKO;
    @XmlElement(name = "BackUrlNotify", required = true)
    protected String backUrlNotify;

    /**
     * Recupera il valore della proprietà backUrlOK.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackUrlOK() {
        return backUrlOK;
    }

    /**
     * Imposta il valore della proprietà backUrlOK.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackUrlOK(String value) {
        this.backUrlOK = value;
    }

    /**
     * Recupera il valore della proprietà backUrlKO.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackUrlKO() {
        return backUrlKO;
    }

    /**
     * Imposta il valore della proprietà backUrlKO.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackUrlKO(String value) {
        this.backUrlKO = value;
    }

    /**
     * Recupera il valore della proprietà backUrlNotify.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackUrlNotify() {
        return backUrlNotify;
    }

    /**
     * Imposta il valore della proprietà backUrlNotify.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackUrlNotify(String value) {
        this.backUrlNotify = value;
    }

}
