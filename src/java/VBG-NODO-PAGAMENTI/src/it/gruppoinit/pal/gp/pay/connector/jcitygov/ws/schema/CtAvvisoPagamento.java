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
 * <p>Classe Java per ctAvvisoPagamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctAvvisoPagamento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NumeroAvviso" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctNumeroAvviso" minOccurs="0"/>
 *         &lt;element name="Debito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctDebito"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctAvvisoPagamento", propOrder = {
    "numeroAvviso",
    "debito"
})
public class CtAvvisoPagamento {

    @XmlElement(name = "NumeroAvviso")
    protected CtNumeroAvviso numeroAvviso;
    @XmlElement(name = "Debito", required = true)
    protected CtDebito debito;

    /**
     * Recupera il valore della proprietà numeroAvviso.
     * 
     * @return
     *     possible object is
     *     {@link CtNumeroAvviso }
     *     
     */
    public CtNumeroAvviso getNumeroAvviso() {
        return numeroAvviso;
    }

    /**
     * Imposta il valore della proprietà numeroAvviso.
     * 
     * @param value
     *     allowed object is
     *     {@link CtNumeroAvviso }
     *     
     */
    public void setNumeroAvviso(CtNumeroAvviso value) {
        this.numeroAvviso = value;
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

}
