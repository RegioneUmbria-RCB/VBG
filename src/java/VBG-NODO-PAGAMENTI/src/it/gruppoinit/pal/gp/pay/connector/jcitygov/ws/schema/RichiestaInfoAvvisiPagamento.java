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
 *         &lt;element name="CriteriRicerca" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctCriteriRicerca"/>
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
    "criteriRicerca"
})
@XmlRootElement(name = "RichiestaInfoAvvisiPagamento")
public class RichiestaInfoAvvisiPagamento {

    @XmlElement(name = "CriteriRicerca", required = true)
    protected CtCriteriRicerca criteriRicerca;

    /**
     * Recupera il valore della proprietà criteriRicerca.
     * 
     * @return
     *     possible object is
     *     {@link CtCriteriRicerca }
     *     
     */
    public CtCriteriRicerca getCriteriRicerca() {
        return criteriRicerca;
    }

    /**
     * Imposta il valore della proprietà criteriRicerca.
     * 
     * @param value
     *     allowed object is
     *     {@link CtCriteriRicerca }
     *     
     */
    public void setCriteriRicerca(CtCriteriRicerca value) {
        this.criteriRicerca = value;
    }

}
