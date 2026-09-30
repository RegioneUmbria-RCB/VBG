//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2022.01.17 alle 10:12:21 AM CET 
//


package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.schemanotify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Richiesta utilizzata per notificare i dati di rendicontazione: data valuta, IBAN  
 * 
 * <p>Classe Java per RichiestaNotificaRendicontazionePagamento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="RichiestaNotificaRendicontazionePagamento">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="chiaviDebito" type="{http://schemi.informatica.maggioli.it/operations/jcgpagopa/notify/1_2}ctChiaviDebito"/>
 *         &lt;element name="DataRegolamento" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="CodicePSP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IBAN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="isContoPostale" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiestaNotificaRendicontazionePagamento", propOrder = {
    "chiaviDebito",
    "dataRegolamento",
    "codicePSP",
    "iban",
    "isContoPostale"
})
public class RichiestaNotificaRendicontazionePagamento {

    @XmlElement(required = true)
    protected CtChiaviDebito chiaviDebito;
    @XmlElement(name = "DataRegolamento")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataRegolamento;
    @XmlElement(name = "CodicePSP")
    protected String codicePSP;
    @XmlElement(name = "IBAN")
    protected String iban;
    protected Boolean isContoPostale;

    /**
     * Recupera il valore della proprietà chiaviDebito.
     * 
     * @return
     *     possible object is
     *     {@link CtChiaviDebito }
     *     
     */
    public CtChiaviDebito getChiaviDebito() {
        return chiaviDebito;
    }

    /**
     * Imposta il valore della proprietà chiaviDebito.
     * 
     * @param value
     *     allowed object is
     *     {@link CtChiaviDebito }
     *     
     */
    public void setChiaviDebito(CtChiaviDebito value) {
        this.chiaviDebito = value;
    }

    /**
     * Recupera il valore della proprietà dataRegolamento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataRegolamento() {
        return dataRegolamento;
    }

    /**
     * Imposta il valore della proprietà dataRegolamento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataRegolamento(XMLGregorianCalendar value) {
        this.dataRegolamento = value;
    }

    /**
     * Recupera il valore della proprietà codicePSP.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodicePSP() {
        return codicePSP;
    }

    /**
     * Imposta il valore della proprietà codicePSP.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodicePSP(String value) {
        this.codicePSP = value;
    }

    /**
     * Recupera il valore della proprietà iban.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIBAN() {
        return iban;
    }

    /**
     * Imposta il valore della proprietà iban.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIBAN(String value) {
        this.iban = value;
    }

    /**
     * Recupera il valore della proprietà isContoPostale.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsContoPostale() {
        return isContoPostale;
    }

    /**
     * Imposta il valore della proprietà isContoPostale.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsContoPostale(Boolean value) {
        this.isContoPostale = value;
    }

}
