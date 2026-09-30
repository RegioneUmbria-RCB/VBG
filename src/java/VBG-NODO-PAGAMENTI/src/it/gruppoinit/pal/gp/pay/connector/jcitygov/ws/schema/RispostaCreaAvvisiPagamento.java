//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="AvvisoPagamentoResult" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctAvvisoPagamentoResult" maxOccurs="unbounded"/>
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
    "avvisoPagamentoResult"
})
@XmlRootElement(name = "RispostaCreaAvvisiPagamento")
public class RispostaCreaAvvisiPagamento {

    @XmlElement(name = "AvvisoPagamentoResult", required = true)
    protected List<CtAvvisoPagamentoResult> avvisoPagamentoResult;

    /**
     * Gets the value of the avvisoPagamentoResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the avvisoPagamentoResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAvvisoPagamentoResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtAvvisoPagamentoResult }
     * 
     * 
     */
    public List<CtAvvisoPagamentoResult> getAvvisoPagamentoResult() {
        if (avvisoPagamentoResult == null) {
            avvisoPagamentoResult = new ArrayList<CtAvvisoPagamentoResult>();
        }
        return this.avvisoPagamentoResult;
    }

}
