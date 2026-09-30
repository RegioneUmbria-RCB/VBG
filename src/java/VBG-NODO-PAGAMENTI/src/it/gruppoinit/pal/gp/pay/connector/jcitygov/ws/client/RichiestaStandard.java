package it.gruppoinit.pal.gp.pay.connector.jcitygov.ws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for RichiestaStandard complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RichiestaStandard">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodiceIPA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CodiceServizio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IDOperazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DataRichiesta" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Versione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DatiDettaglioRichiesta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichiestaStandard", propOrder = { "codiceIPA", "codiceServizio", "idOperazione", "dataRichiesta", "versione",
	"datiDettaglioRichiesta" })
public class RichiestaStandard {

    @XmlElement(name = "CodiceIPA", required = true)
    protected String codiceIPA;
    @XmlElement(name = "CodiceServizio", required = true)
    protected String codiceServizio;
    @XmlElement(name = "IDOperazione", required = true)
    protected String idOperazione;
    @XmlElement(name = "DataRichiesta", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataRichiesta;
    @XmlElement(name = "Versione", required = true)
    protected String versione;
    @XmlElement(name = "DatiDettaglioRichiesta", required = true)
    protected String datiDettaglioRichiesta;

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
     * Gets the value of the codiceServizio property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCodiceServizio() {

	return codiceServizio;
    }

    /**
     * Sets the value of the codiceServizio property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCodiceServizio(String value) {

	this.codiceServizio = value;
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
     * Gets the value of the dataRichiesta property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getDataRichiesta() {

	return dataRichiesta;
    }

    /**
     * Sets the value of the dataRichiesta property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setDataRichiesta(XMLGregorianCalendar value) {

	this.dataRichiesta = value;
    }

    /**
     * Gets the value of the versione property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getVersione() {

	return versione;
    }

    /**
     * Sets the value of the versione property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setVersione(String value) {

	this.versione = value;
    }

    /**
     * Gets the value of the datiDettaglioRichiesta property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDatiDettaglioRichiesta() {

	return datiDettaglioRichiesta;
    }

    /**
     * Sets the value of the datiDettaglioRichiesta property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDatiDettaglioRichiesta(String value) {

	this.datiDettaglioRichiesta = value;
    }
}
