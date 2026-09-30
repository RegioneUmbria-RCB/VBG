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
 *         &lt;element name="AttivaAvvisoResult" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctAttivaAvvisoResult" maxOccurs="unbounded"/>
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
    "attivaAvvisoResult"
})
@XmlRootElement(name = "RispostaAttivaAvvisoPagamento")
public class RispostaAttivaAvvisoPagamento {

    @XmlElement(name = "AttivaAvvisoResult", required = true)
    protected List<CtAttivaAvvisoResult> attivaAvvisoResult;

    /**
     * Gets the value of the attivaAvvisoResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attivaAvvisoResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttivaAvvisoResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtAttivaAvvisoResult }
     * 
     * 
     */
    public List<CtAttivaAvvisoResult> getAttivaAvvisoResult() {
        if (attivaAvvisoResult == null) {
            attivaAvvisoResult = new ArrayList<CtAttivaAvvisoResult>();
        }
        return this.attivaAvvisoResult;
    }

}
