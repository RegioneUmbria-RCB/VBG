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
 *         &lt;element name="ListaPagamenti">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Pagamenti" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctDebitoPagato" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "listaPagamenti"
})
@XmlRootElement(name = "RispostaListaPagamenti")
public class RispostaListaPagamenti {

    @XmlElement(name = "ListaPagamenti", required = true)
    protected RispostaListaPagamenti.ListaPagamenti listaPagamenti;

    /**
     * Recupera il valore della proprietà listaPagamenti.
     * 
     * @return
     *     possible object is
     *     {@link RispostaListaPagamenti.ListaPagamenti }
     *     
     */
    public RispostaListaPagamenti.ListaPagamenti getListaPagamenti() {
        return listaPagamenti;
    }

    /**
     * Imposta il valore della proprietà listaPagamenti.
     * 
     * @param value
     *     allowed object is
     *     {@link RispostaListaPagamenti.ListaPagamenti }
     *     
     */
    public void setListaPagamenti(RispostaListaPagamenti.ListaPagamenti value) {
        this.listaPagamenti = value;
    }


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
     *         &lt;element name="Pagamenti" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctDebitoPagato" maxOccurs="unbounded" minOccurs="0"/>
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
        "pagamenti"
    })
    public static class ListaPagamenti {

        @XmlElement(name = "Pagamenti")
        protected List<CtDebitoPagato> pagamenti;

        /**
         * Gets the value of the pagamenti property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the pagamenti property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPagamenti().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CtDebitoPagato }
         * 
         * 
         */
        public List<CtDebitoPagato> getPagamenti() {
            if (pagamenti == null) {
                pagamenti = new ArrayList<CtDebitoPagato>();
            }
            return this.pagamenti;
        }

    }

}
