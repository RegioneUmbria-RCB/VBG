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
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="NumeroAvviso" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}ctNumeroAvviso"/>
 *         &lt;element name="codiceMotivoEliminazione" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2}stCodiceMotivoEliminazione" minOccurs="0"/>
 *         &lt;element name="descrizioneMotivoEliminazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "numeroAvviso",
    "codiceMotivoEliminazione",
    "descrizioneMotivoEliminazione"
})
@XmlRootElement(name = "RichiestaEliminaAvvisoPagamento")
public class RichiestaEliminaAvvisoPagamento {

    @XmlElement(name = "NumeroAvviso", required = true)
    protected CtNumeroAvviso numeroAvviso;
    @XmlSchemaType(name = "string")
    protected StCodiceMotivoEliminazione codiceMotivoEliminazione;
    protected String descrizioneMotivoEliminazione;

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
     * Recupera il valore della proprietà codiceMotivoEliminazione.
     * 
     * @return
     *     possible object is
     *     {@link StCodiceMotivoEliminazione }
     *     
     */
    public StCodiceMotivoEliminazione getCodiceMotivoEliminazione() {
        return codiceMotivoEliminazione;
    }

    /**
     * Imposta il valore della proprietà codiceMotivoEliminazione.
     * 
     * @param value
     *     allowed object is
     *     {@link StCodiceMotivoEliminazione }
     *     
     */
    public void setCodiceMotivoEliminazione(StCodiceMotivoEliminazione value) {
        this.codiceMotivoEliminazione = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneMotivoEliminazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneMotivoEliminazione() {
        return descrizioneMotivoEliminazione;
    }

    /**
     * Imposta il valore della proprietà descrizioneMotivoEliminazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneMotivoEliminazione(String value) {
        this.descrizioneMotivoEliminazione = value;
    }

}
