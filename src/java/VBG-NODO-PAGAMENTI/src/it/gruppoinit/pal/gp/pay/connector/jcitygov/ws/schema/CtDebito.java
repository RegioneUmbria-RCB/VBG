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
 * <p>Classe Java per ctDebito complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctDebito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TestataDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctTestataDebito"/>
 *         &lt;element name="DettaglioDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctDettaglioDebito"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctDebito", propOrder = {
    "testataDebito",
    "dettaglioDebito"
})
public class CtDebito {

    @XmlElement(name = "TestataDebito", required = true)
    protected CtTestataDebito testataDebito;
    @XmlElement(name = "DettaglioDebito", required = true)
    protected CtDettaglioDebito dettaglioDebito;

    /**
     * Recupera il valore della proprietà testataDebito.
     * 
     * @return
     *     possible object is
     *     {@link CtTestataDebito }
     *     
     */
    public CtTestataDebito getTestataDebito() {
        return testataDebito;
    }

    /**
     * Imposta il valore della proprietà testataDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link CtTestataDebito }
     *     
     */
    public void setTestataDebito(CtTestataDebito value) {
        this.testataDebito = value;
    }

    /**
     * Recupera il valore della proprietà dettaglioDebito.
     * 
     * @return
     *     possible object is
     *     {@link CtDettaglioDebito }
     *     
     */
    public CtDettaglioDebito getDettaglioDebito() {
        return dettaglioDebito;
    }

    /**
     * Imposta il valore della proprietà dettaglioDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link CtDettaglioDebito }
     *     
     */
    public void setDettaglioDebito(CtDettaglioDebito value) {
        this.dettaglioDebito = value;
    }

}
