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
 * <p>Classe Java per ctIdentificativoSospeso complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctIdentificativoSospeso">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NumeroSospeso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AnnoSospeso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctIdentificativoSospeso", propOrder = {
    "numeroSospeso",
    "annoSospeso"
})
public class CtIdentificativoSospeso {

    @XmlElement(name = "NumeroSospeso", required = true)
    protected String numeroSospeso;
    @XmlElement(name = "AnnoSospeso", required = true)
    protected String annoSospeso;

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

}
