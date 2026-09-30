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
 * <p>Classe Java per ctDebugMode complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ctDebugMode">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EsitoCallBack" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stEsito"/>
 *         &lt;element name="Messaggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ctDebugMode", propOrder = {
    "esitoCallBack",
    "messaggio"
})
public class CtDebugMode {

    @XmlElement(name = "EsitoCallBack", required = true)
    @XmlSchemaType(name = "string")
    protected StEsito esitoCallBack;
    @XmlElement(name = "Messaggio")
    protected String messaggio;

    /**
     * Recupera il valore della proprietà esitoCallBack.
     * 
     * @return
     *     possible object is
     *     {@link StEsito }
     *     
     */
    public StEsito getEsitoCallBack() {
        return esitoCallBack;
    }

    /**
     * Imposta il valore della proprietà esitoCallBack.
     * 
     * @param value
     *     allowed object is
     *     {@link StEsito }
     *     
     */
    public void setEsitoCallBack(StEsito value) {
        this.esitoCallBack = value;
    }

    /**
     * Recupera il valore della proprietà messaggio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessaggio() {
        return messaggio;
    }

    /**
     * Imposta il valore della proprietà messaggio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessaggio(String value) {
        this.messaggio = value;
    }

}
