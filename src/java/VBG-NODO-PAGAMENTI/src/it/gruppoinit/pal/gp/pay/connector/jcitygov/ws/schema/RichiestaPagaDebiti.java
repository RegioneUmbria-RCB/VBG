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
 *         &lt;element name="IdRichiestaPagamento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Versante" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctSoggetto"/>
 *         &lt;element name="Debito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctDebito" maxOccurs="unbounded"/>
 *         &lt;element name="BackUrl" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctBackUrl"/>
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
    "idRichiestaPagamento",
    "versante",
    "debito",
    "backUrl"
})
@XmlRootElement(name = "RichiestaPagaDebiti")
public class RichiestaPagaDebiti {

    @XmlElement(name = "IdRichiestaPagamento", required = true)
    protected String idRichiestaPagamento;
    @XmlElement(name = "Versante", required = true)
    protected CtSoggetto versante;
    @XmlElement(name = "Debito", required = true)
    protected List<CtDebito> debito;
    @XmlElement(name = "BackUrl", required = true)
    protected CtBackUrl backUrl;

    /**
     * Recupera il valore della proprietà idRichiestaPagamento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdRichiestaPagamento() {
        return idRichiestaPagamento;
    }

    /**
     * Imposta il valore della proprietà idRichiestaPagamento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdRichiestaPagamento(String value) {
        this.idRichiestaPagamento = value;
    }

    /**
     * Recupera il valore della proprietà versante.
     * 
     * @return
     *     possible object is
     *     {@link CtSoggetto }
     *     
     */
    public CtSoggetto getVersante() {
        return versante;
    }

    /**
     * Imposta il valore della proprietà versante.
     * 
     * @param value
     *     allowed object is
     *     {@link CtSoggetto }
     *     
     */
    public void setVersante(CtSoggetto value) {
        this.versante = value;
    }

    /**
     * Gets the value of the debito property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the debito property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDebito().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CtDebito }
     * 
     * 
     */
    public List<CtDebito> getDebito() {
        if (debito == null) {
            debito = new ArrayList<CtDebito>();
        }
        return this.debito;
    }

    /**
     * Recupera il valore della proprietà backUrl.
     * 
     * @return
     *     possible object is
     *     {@link CtBackUrl }
     *     
     */
    public CtBackUrl getBackUrl() {
        return backUrl;
    }

    /**
     * Imposta il valore della proprietà backUrl.
     * 
     * @param value
     *     allowed object is
     *     {@link CtBackUrl }
     *     
     */
    public void setBackUrl(CtBackUrl value) {
        this.backUrl = value;
    }

}
