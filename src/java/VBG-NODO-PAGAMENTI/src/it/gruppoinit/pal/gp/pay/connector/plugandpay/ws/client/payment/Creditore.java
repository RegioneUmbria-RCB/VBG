package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.payment;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pClasse Java per Creditore complex type.
 * 
 * <pIl seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre
 * &lt;complexType name="Creditore"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CodiceEnte" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CodiceFiscalePartitaIva" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IBAN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Intestazione" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Creditore", propOrder = { "codiceEnte", "codiceFiscalePartitaIva", "iban", "intestazione" })
public class Creditore {

    @XmlElement(name = "CodiceEnte", required = true, nillable = true)
    protected String codiceEnte;
    @XmlElement(name = "CodiceFiscalePartitaIva", required = false)
    protected String codiceFiscalePartitaIva;
    @XmlElement(name = "IBAN", required = false)
    protected String iban;
    @XmlElement(name = "Intestazione", required = true, nillable = true)
    protected String intestazione;

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
     * Recupera il valore della proprietà codiceFiscalePartitaIva.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getCodiceFiscalePartitaIva() {

	return codiceFiscalePartitaIva;
    }

    /**
     * Imposta il valore della proprietà codiceFiscalePartitaIva.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setCodiceFiscalePartitaIva(String value) {

	this.codiceFiscalePartitaIva = value;
    }

    /**
     * Recupera il valore della proprietà iban.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
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
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setIBAN(String value) {

	this.iban = value;
    }

    /**
     * Recupera il valore della proprietà intestazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntestazione() {

	return intestazione;
    }

    /**
     * Imposta il valore della proprietà intestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntestazione(String value) {

	this.intestazione = value;
    }
}
