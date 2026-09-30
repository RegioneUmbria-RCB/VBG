package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.modellazione;

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
 * Classe Java per Contribuente_Domiciliazione complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Contribuente_Domiciliazione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CodiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Nominativo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="IBAN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="NumeroMandato" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CausaleRID" type="{http://entranext.it/}Causali_CBI"/&gt;
 *         &lt;element name="PrimaDisposizioneAddebito" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="DataOperazione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="SottoserviziAbilitati" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="VociDiCostoAbilitate" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contribuente_Domiciliazione", propOrder = { "codiceFiscale", "nominativo", "iban", "numeroMandato", "causaleRID",
	"primaDisposizioneAddebito", "dataOperazione", "sottoserviziAbilitati", "vociDiCostoAbilitate" })
public class ContribuenteDomiciliazione {

    @XmlElement(name = "CodiceFiscale", required = true, nillable = true)
    protected String codiceFiscale;
    @XmlElement(name = "Nominativo", required = true, nillable = true)
    protected String nominativo;
    @XmlElement(name = "IBAN")
    protected String iban;
    @XmlElement(name = "NumeroMandato", required = true, nillable = true)
    protected String numeroMandato;
    @XmlElement(name = "CausaleRID", required = true)
    @XmlSchemaType(name = "string")
    protected CausaliCBI causaleRID;
    @XmlElement(name = "PrimaDisposizioneAddebito")
    protected boolean primaDisposizioneAddebito;
    @XmlElement(name = "DataOperazione", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataOperazione;
    @XmlElement(name = "SottoserviziAbilitati", nillable = true)
    protected List<String> sottoserviziAbilitati;
    @XmlElement(name = "VociDiCostoAbilitate", nillable = true)
    protected List<String> vociDiCostoAbilitate;

    /**
     * Recupera il valore della proprietà codiceFiscale.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCodiceFiscale() {

	return codiceFiscale;
    }

    /**
     * Imposta il valore della proprietà codiceFiscale.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCodiceFiscale(String value) {

	this.codiceFiscale = value;
    }

    /**
     * Recupera il valore della proprietà nominativo.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNominativo() {

	return nominativo;
    }

    /**
     * Imposta il valore della proprietà nominativo.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNominativo(String value) {

	this.nominativo = value;
    }

    /**
     * Recupera il valore della proprietà iban.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIBAN() {

	return iban;
    }

    /**
     * Imposta il valore della proprietà iban.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIBAN(String value) {

	this.iban = value;
    }

    /**
     * Recupera il valore della proprietà numeroMandato.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumeroMandato() {

	return numeroMandato;
    }

    /**
     * Imposta il valore della proprietà numeroMandato.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNumeroMandato(String value) {

	this.numeroMandato = value;
    }

    /**
     * Recupera il valore della proprietà causaleRID.
     * 
     * @return possible object is {@link CausaliCBI }
     * 
     */
    public CausaliCBI getCausaleRID() {

	return causaleRID;
    }

    /**
     * Imposta il valore della proprietà causaleRID.
     * 
     * @param value
     *            allowed object is {@link CausaliCBI }
     * 
     */
    public void setCausaleRID(CausaliCBI value) {

	this.causaleRID = value;
    }

    /**
     * Recupera il valore della proprietà primaDisposizioneAddebito.
     * 
     */
    public boolean isPrimaDisposizioneAddebito() {

	return primaDisposizioneAddebito;
    }

    /**
     * Imposta il valore della proprietà primaDisposizioneAddebito.
     * 
     */
    public void setPrimaDisposizioneAddebito(boolean value) {

	this.primaDisposizioneAddebito = value;
    }

    /**
     * Recupera il valore della proprietà dataOperazione.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getDataOperazione() {

	return dataOperazione;
    }

    /**
     * Imposta il valore della proprietà dataOperazione.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setDataOperazione(XMLGregorianCalendar value) {

	this.dataOperazione = value;
    }

    /**
     * Gets the value of the sottoserviziAbilitati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the sottoserviziAbilitati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getSottoserviziAbilitati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     * 
     * 
     */
    public List<String> getSottoserviziAbilitati() {

	if (sottoserviziAbilitati == null) {
	    sottoserviziAbilitati = new ArrayList<String>();
	}
	return this.sottoserviziAbilitati;
    }

    /**
     * Gets the value of the vociDiCostoAbilitate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the vociDiCostoAbilitate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getVociDiCostoAbilitate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     * 
     * 
     */
    public List<String> getVociDiCostoAbilitate() {

	if (vociDiCostoAbilitate == null) {
	    vociDiCostoAbilitate = new ArrayList<String>();
	}
	return this.vociDiCostoAbilitate;
    }

    public void setSottoserviziAbilitati(List<String> sottoserviziAbilitati) {

	this.sottoserviziAbilitati = sottoserviziAbilitati;
    }

    public void setVociDiCostoAbilitate(List<String> vociDiCostoAbilitate) {

	this.vociDiCostoAbilitate = vociDiCostoAbilitate;
    }
}
