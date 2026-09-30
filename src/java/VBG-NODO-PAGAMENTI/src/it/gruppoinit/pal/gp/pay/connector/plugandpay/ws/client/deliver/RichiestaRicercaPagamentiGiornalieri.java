package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.deliver;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <pClasse Java per RichiestaRicercaPagamentiGiornalieri complex type.
 * 
 * <pIl seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre
 * &lt;complexType name="RichiestaRicercaPagamentiGiornalieri"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://e-fil.eu/PnP/PlugAndPayDeliver}DeliverAuthenticatedRequestBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CodiceEnte" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Codiceservizio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Data" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="OraFine" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="OraInizio" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiestaRicercaPagamentiGiornalieri", propOrder = { "codiceEnte", "codiceservizio", "data", "oraFine", "oraInizio" })
public class RichiestaRicercaPagamentiGiornalieri extends DeliverAuthenticatedRequestBase {

    @XmlElement(name = "CodiceEnte", required = true, nillable = true)
    protected String codiceEnte;
    @XmlElement(name = "Codiceservizio", required = true, nillable = true)
    protected String codiceservizio;
    @XmlElement(name = "Data", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar data;
    @XmlElement(name = "OraFine", required = false)
    protected Integer oraFine;
    @XmlElement(name = "OraInizio", required = false)
    protected Integer oraInizio;

    /**
     * Recupera il valore della proprietà codiceEnte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceEnte() {

	return codiceEnte;
    }

    /**
     * Imposta il valore della proprietà codiceEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceEnte(String value) {

	this.codiceEnte = value;
    }

    /**
     * Recupera il valore della proprietà codiceservizio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceservizio() {

	return codiceservizio;
    }

    /**
     * Imposta il valore della proprietà codiceservizio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceservizio(String value) {

	this.codiceservizio = value;
    }

    /**
     * Recupera il valore della proprietà data.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getData() {

	return data;
    }

    /**
     * Imposta il valore della proprietà data.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setData(XMLGregorianCalendar value) {

	this.data = value;
    }

    /**
     * Recupera il valore della proprietà oraFine.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code }
     *     
     */
    public Integer getOraFine() {

	return oraFine;
    }

    /**
     * Imposta il valore della proprietà oraFine.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code }
     *     
     */
    public void setOraFine(Integer value) {

	this.oraFine = value;
    }

    /**
     * Recupera il valore della proprietà oraInizio.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code }
     *     
     */
    public Integer getOraInizio() {

	return oraInizio;
    }

    /**
     * Imposta il valore della proprietà oraInizio.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code }
     *     
     */
    public void setOraInizio(Integer value) {

	this.oraInizio = value;
    }
}
