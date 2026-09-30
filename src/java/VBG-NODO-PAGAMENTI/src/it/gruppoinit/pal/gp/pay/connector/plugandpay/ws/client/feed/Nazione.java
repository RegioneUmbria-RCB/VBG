package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pClasse Java per Nazione complex type.
 * 
 * <pIl seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre
 * &lt;complexType name="Nazione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CodiceIsoNazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NomeNazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Nazione", propOrder = { "codiceIsoNazione", "nomeNazione" })
public class Nazione {

    @XmlElement(name = "CodiceIsoNazione", required = false)
    protected String codiceIsoNazione;
    @XmlElement(name = "NomeNazione", required = false)
    protected String nomeNazione;

    /**
     * Recupera il valore della proprietà codiceIsoNazione.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getCodiceIsoNazione() {

	return codiceIsoNazione;
    }

    /**
     * Imposta il valore della proprietà codiceIsoNazione.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setCodiceIsoNazione(String value) {

	this.codiceIsoNazione = value;
    }

    /**
     * Recupera il valore della proprietà nomeNazione.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getNomeNazione() {

	return nomeNazione;
    }

    /**
     * Imposta il valore della proprietà nomeNazione.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setNomeNazione(String value) {

	this.nomeNazione = value;
    }
}
