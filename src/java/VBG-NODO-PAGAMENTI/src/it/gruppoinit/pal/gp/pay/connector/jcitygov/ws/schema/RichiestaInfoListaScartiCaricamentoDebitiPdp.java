//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.13 alle 12:05:21 PM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="CodiceIpa" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DataInizio" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="DataFine" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
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
    "codiceIpa",
    "dataInizio",
    "dataFine"
})
@XmlRootElement(name = "RichiestaInfoListaScartiCaricamentoDebitiPdp")
public class RichiestaInfoListaScartiCaricamentoDebitiPdp {

    @XmlElement(name = "CodiceIpa", required = true)
    protected String codiceIpa;
    @XmlElementRef(name = "DataInizio", namespace = "http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dataInizio;
    @XmlElementRef(name = "DataFine", namespace = "http://schemi.informatica.maggioli.it/operations/jcgpagopa/1_2", type = JAXBElement.class, required = false)
    protected JAXBElement<XMLGregorianCalendar> dataFine;

    /**
     * Recupera il valore della proprietà codiceIpa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIpa() {
        return codiceIpa;
    }

    /**
     * Imposta il valore della proprietà codiceIpa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIpa(String value) {
        this.codiceIpa = value;
    }

    /**
     * Recupera il valore della proprietà dataInizio.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDataInizio() {
        return dataInizio;
    }

    /**
     * Imposta il valore della proprietà dataInizio.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDataInizio(JAXBElement<XMLGregorianCalendar> value) {
        this.dataInizio = value;
    }

    /**
     * Recupera il valore della proprietà dataFine.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public JAXBElement<XMLGregorianCalendar> getDataFine() {
        return dataFine;
    }

    /**
     * Imposta il valore della proprietà dataFine.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     *     
     */
    public void setDataFine(JAXBElement<XMLGregorianCalendar> value) {
        this.dataFine = value;
    }

}
