
package it.init.sigepro.rte.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PersonaFisicaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonaFisicaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="titolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sesso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataNascita" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="comuneNascita" type="{http://sigepro.init.it/rte/types}ComuneType" minOccurs="0"/>
 *         &lt;element name="residenza" type="{http://sigepro.init.it/rte/types}LocalizzazioneType" minOccurs="0"/>
 *         &lt;element name="corrispondenza" type="{http://sigepro.init.it/rte/types}LocalizzazioneType" minOccurs="0"/>
 *         &lt;element name="cittadinanza" type="{http://sigepro.init.it/rte/types}CittadinanzaType" minOccurs="0"/>
 *         &lt;element name="altriDati" type="{http://sigepro.init.it/rte/types}ParametroType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="procura" type="{http://sigepro.init.it/rte/types}DocumentiType" minOccurs="0"/>
 *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telefonoCellulare" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="datiIscrizioneAlbo" type="{http://sigepro.init.it/rte/types}DatiIscrizioneAlboType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaFisicaType", propOrder = {
    "codiceFiscale",
    "nome",
    "cognome",
    "titolo",
    "sesso",
    "dataNascita",
    "comuneNascita",
    "residenza",
    "corrispondenza",
    "cittadinanza",
    "altriDati",
    "procura",
    "telefono",
    "email",
    "pec",
    "telefonoCellulare",
    "datiIscrizioneAlbo"
})
public class PersonaFisicaType {

    @XmlElement(required = true)
    protected String codiceFiscale;
    @XmlElement(required = true)
    protected String nome;
    @XmlElement(required = true)
    protected String cognome;
    protected String titolo;
    protected String sesso;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataNascita;
    protected ComuneType comuneNascita;
    protected LocalizzazioneType residenza;
    protected LocalizzazioneType corrispondenza;
    protected CittadinanzaType cittadinanza;
    protected List<ParametroType> altriDati;
    protected DocumentiType procura;
    protected String telefono;
    protected String email;
    protected String pec;
    protected String telefonoCellulare;
    protected DatiIscrizioneAlboType datiIscrizioneAlbo;

    /**
     * Gets the value of the codiceFiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Sets the value of the codiceFiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Gets the value of the nome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets the value of the nome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Gets the value of the cognome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Sets the value of the cognome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Gets the value of the titolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Sets the value of the titolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitolo(String value) {
        this.titolo = value;
    }

    /**
     * Gets the value of the sesso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSesso() {
        return sesso;
    }

    /**
     * Sets the value of the sesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSesso(String value) {
        this.sesso = value;
    }

    /**
     * Gets the value of the dataNascita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataNascita() {
        return dataNascita;
    }

    /**
     * Sets the value of the dataNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataNascita(XMLGregorianCalendar value) {
        this.dataNascita = value;
    }

    /**
     * Gets the value of the comuneNascita property.
     * 
     * @return
     *     possible object is
     *     {@link ComuneType }
     *     
     */
    public ComuneType getComuneNascita() {
        return comuneNascita;
    }

    /**
     * Sets the value of the comuneNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComuneType }
     *     
     */
    public void setComuneNascita(ComuneType value) {
        this.comuneNascita = value;
    }

    /**
     * Gets the value of the residenza property.
     * 
     * @return
     *     possible object is
     *     {@link LocalizzazioneType }
     *     
     */
    public LocalizzazioneType getResidenza() {
        return residenza;
    }

    /**
     * Sets the value of the residenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalizzazioneType }
     *     
     */
    public void setResidenza(LocalizzazioneType value) {
        this.residenza = value;
    }

    /**
     * Gets the value of the corrispondenza property.
     * 
     * @return
     *     possible object is
     *     {@link LocalizzazioneType }
     *     
     */
    public LocalizzazioneType getCorrispondenza() {
        return corrispondenza;
    }

    /**
     * Sets the value of the corrispondenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalizzazioneType }
     *     
     */
    public void setCorrispondenza(LocalizzazioneType value) {
        this.corrispondenza = value;
    }

    /**
     * Gets the value of the cittadinanza property.
     * 
     * @return
     *     possible object is
     *     {@link CittadinanzaType }
     *     
     */
    public CittadinanzaType getCittadinanza() {
        return cittadinanza;
    }

    /**
     * Sets the value of the cittadinanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link CittadinanzaType }
     *     
     */
    public void setCittadinanza(CittadinanzaType value) {
        this.cittadinanza = value;
    }

    /**
     * Gets the value of the altriDati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the altriDati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAltriDati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParametroType }
     * 
     * 
     */
    public List<ParametroType> getAltriDati() {
        if (altriDati == null) {
            altriDati = new ArrayList<ParametroType>();
        }
        return this.altriDati;
    }

    /**
     * Gets the value of the procura property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentiType }
     *     
     */
    public DocumentiType getProcura() {
        return procura;
    }

    /**
     * Sets the value of the procura property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentiType }
     *     
     */
    public void setProcura(DocumentiType value) {
        this.procura = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets the value of the telefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the pec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPec() {
        return pec;
    }

    /**
     * Sets the value of the pec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPec(String value) {
        this.pec = value;
    }

    /**
     * Gets the value of the telefonoCellulare property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefonoCellulare() {
        return telefonoCellulare;
    }

    /**
     * Sets the value of the telefonoCellulare property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefonoCellulare(String value) {
        this.telefonoCellulare = value;
    }

    /**
     * Gets the value of the datiIscrizioneAlbo property.
     * 
     * @return
     *     possible object is
     *     {@link DatiIscrizioneAlboType }
     *     
     */
    public DatiIscrizioneAlboType getDatiIscrizioneAlbo() {
        return datiIscrizioneAlbo;
    }

    /**
     * Sets the value of the datiIscrizioneAlbo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiIscrizioneAlboType }
     *     
     */
    public void setDatiIscrizioneAlbo(DatiIscrizioneAlboType value) {
        this.datiIscrizioneAlbo = value;
    }

}
