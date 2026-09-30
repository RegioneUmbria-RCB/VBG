package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.clientnotify;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for RispostaStandard complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RispostaStandard">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodiceIPA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IDOperazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DataRisposta" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="EsitoOperazione">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="OK"/>
 *               &lt;enumeration value="Error"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Messaggi" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Messaggio" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DatiDettaglioRisposta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RispostaStandard", propOrder = { "codiceIPA", "idOperazione", "dataRisposta", "esitoOperazione", "messaggi",
	"datiDettaglioRisposta" })
public class RispostaStandard {

    @XmlElement(name = "CodiceIPA", required = true)
    protected String codiceIPA;
    @XmlElement(name = "IDOperazione", required = true)
    protected String idOperazione;
    @XmlElement(name = "DataRisposta", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRisposta;
    @XmlElement(name = "EsitoOperazione", required = true)
    protected String esitoOperazione;
    @XmlElement(name = "Messaggi")
    protected RispostaStandard.Messaggi messaggi;
    @XmlElement(name = "DatiDettaglioRisposta", required = true)
    protected String datiDettaglioRisposta;

    /**
     * Gets the value of the codiceIPA property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCodiceIPA() {

	return codiceIPA;
    }

    /**
     * Sets the value of the codiceIPA property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCodiceIPA(String value) {

	this.codiceIPA = value;
    }

    /**
     * Gets the value of the idOperazione property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIDOperazione() {

	return idOperazione;
    }

    /**
     * Sets the value of the idOperazione property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIDOperazione(String value) {

	this.idOperazione = value;
    }

    /**
     * Gets the value of the dataRisposta property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getDataRisposta() {

	return dataRisposta;
    }

    /**
     * Sets the value of the dataRisposta property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setDataRisposta(XMLGregorianCalendar value) {

	this.dataRisposta = value;
    }

    /**
     * Gets the value of the esitoOperazione property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEsitoOperazione() {

	return esitoOperazione;
    }

    /**
     * Sets the value of the esitoOperazione property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEsitoOperazione(String value) {

	this.esitoOperazione = value;
    }

    /**
     * Gets the value of the messaggi property.
     * 
     * @return possible object is {@link RispostaStandard.Messaggi }
     * 
     */
    public RispostaStandard.Messaggi getMessaggi() {

	return messaggi;
    }

    /**
     * Sets the value of the messaggi property.
     * 
     * @param value
     *            allowed object is {@link RispostaStandard.Messaggi }
     * 
     */
    public void setMessaggi(RispostaStandard.Messaggi value) {

	this.messaggi = value;
    }

    /**
     * Gets the value of the datiDettaglioRisposta property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDatiDettaglioRisposta() {

	return datiDettaglioRisposta;
    }

    /**
     * Sets the value of the datiDettaglioRisposta property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDatiDettaglioRisposta(String value) {

	this.datiDettaglioRisposta = value;
    }

    /**
     * <p>
     * Java class for anonymous complex type.
     * 
     * <p>
     * The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Messaggio" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    @XmlType(name = "", propOrder = { "messaggio" })
    public static class Messaggi {

	@XmlElement(name = "Messaggio", required = true)
	protected List<RispostaStandard.Messaggi.Messaggio> messaggio;

	/**
	 * Gets the value of the messaggio property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you
	 * make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE>
	 * method for the messaggio property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getMessaggio().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link RispostaStandard.Messaggi.Messaggio }
	 * 
	 * 
	 */
	public List<RispostaStandard.Messaggi.Messaggio> getMessaggio() {

	    if (messaggio == null) {
		messaggio = new ArrayList<RispostaStandard.Messaggi.Messaggio>();
	    }
	    return this.messaggio;
	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="Codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *         &lt;element name="Descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "codice", "descrizione" })
	public static class Messaggio {

	    @XmlElement(name = "Codice", required = true)
	    protected String codice;
	    @XmlElement(name = "Descrizione", required = true)
	    protected String descrizione;

	    /**
	     * Gets the value of the codice property.
	     * 
	     * @return possible object is {@link String }
	     * 
	     */
	    public String getCodice() {

		return codice;
	    }

	    /**
	     * Sets the value of the codice property.
	     * 
	     * @param value
	     *            allowed object is {@link String }
	     * 
	     */
	    public void setCodice(String value) {

		this.codice = value;
	    }

	    /**
	     * Gets the value of the descrizione property.
	     * 
	     * @return possible object is {@link String }
	     * 
	     */
	    public String getDescrizione() {

		return descrizione;
	    }

	    /**
	     * Sets the value of the descrizione property.
	     * 
	     * @param value
	     *            allowed object is {@link String }
	     * 
	     */
	    public void setDescrizione(String value) {

		this.descrizione = value;
	    }
	}
    }
}
