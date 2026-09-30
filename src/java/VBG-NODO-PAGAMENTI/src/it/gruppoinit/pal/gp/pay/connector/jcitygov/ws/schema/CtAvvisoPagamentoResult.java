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
 * <p>Classe Java per ctAvvisoPagamentoResult complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctAvvisoPagamentoResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NumeroAvviso" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctNumeroAvviso"/>
 *         &lt;element name="ChiaveDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctChiaveDebito"/>
 *         &lt;element name="Esito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctEsito"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctAvvisoPagamentoResult", propOrder = {
    "numeroAvviso",
    "chiaveDebito",
    "esito"
})
public class CtAvvisoPagamentoResult {

    @XmlElement(name = "NumeroAvviso", required = true)
    protected CtNumeroAvviso numeroAvviso;
    @XmlElement(name = "ChiaveDebito", required = true)
    protected CtChiaveDebito chiaveDebito;
    @XmlElement(name = "Esito", required = true)
    protected CtEsito esito;

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
     * Recupera il valore della proprietà chiaveDebito.
     * 
     * @return
     *     possible object is
     *     {@link CtChiaveDebito }
     *     
     */
    public CtChiaveDebito getChiaveDebito() {
        return chiaveDebito;
    }

    /**
     * Imposta il valore della proprietà chiaveDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link CtChiaveDebito }
     *     
     */
    public void setChiaveDebito(CtChiaveDebito value) {
        this.chiaveDebito = value;
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

}
