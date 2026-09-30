
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
 * <p>Java class for DettaglioPraticaBreveType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DettaglioPraticaBreveType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceComune" type="{http://sigepro.init.it/rte/types}ComuneType" minOccurs="0"/>
 *         &lt;element name="idPratica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroPratica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataPratica" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="oraDataPratica" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[0-2][0-9]:[0-5][0-9]"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="numeroProtocolloGenerale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataProtocolloGenerale" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="richiedente" type="{http://sigepro.init.it/rte/types}RichiedenteType" minOccurs="0"/>
 *         &lt;element name="aziendaRichiedente" type="{http://sigepro.init.it/rte/types}PersonaGiuridicaType" minOccurs="0"/>
 *         &lt;element name="intermediario" type="{http://sigepro.init.it/rte/types}AnagrafeType" minOccurs="0"/>
 *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="intervento" type="{http://sigepro.init.it/rte/types}InterventoType" minOccurs="0"/>
 *         &lt;element name="localizzazione" type="{http://sigepro.init.it/rte/types}LocalizzazioneNelComuneType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="statoPratica" type="{http://sigepro.init.it/rte/types}StatoPraticaType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioPraticaBreveType", propOrder = {
    "codiceComune",
    "idPratica",
    "numeroPratica",
    "dataPratica",
    "oraDataPratica",
    "numeroProtocolloGenerale",
    "dataProtocolloGenerale",
    "richiedente",
    "aziendaRichiedente",
    "intermediario",
    "oggetto",
    "intervento",
    "localizzazione",
    "statoPratica"
})
public class DettaglioPraticaBreveType {

    protected ComuneType codiceComune;
    @XmlElement(required = true)
    protected String idPratica;
    @XmlElement(required = true)
    protected String numeroPratica;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataPratica;
    protected String oraDataPratica;
    protected String numeroProtocolloGenerale;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataProtocolloGenerale;
    protected RichiedenteType richiedente;
    protected PersonaGiuridicaType aziendaRichiedente;
    protected AnagrafeType intermediario;
    protected String oggetto;
    protected InterventoType intervento;
    protected List<LocalizzazioneNelComuneType> localizzazione;
    @XmlElement(required = true)
    protected StatoPraticaType statoPratica;

    /**
     * Gets the value of the codiceComune property.
     * 
     * @return
     *     possible object is
     *     {@link ComuneType }
     *     
     */
    public ComuneType getCodiceComune() {
        return codiceComune;
    }

    /**
     * Sets the value of the codiceComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComuneType }
     *     
     */
    public void setCodiceComune(ComuneType value) {
        this.codiceComune = value;
    }

    /**
     * Gets the value of the idPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPratica() {
        return idPratica;
    }

    /**
     * Sets the value of the idPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPratica(String value) {
        this.idPratica = value;
    }

    /**
     * Gets the value of the numeroPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroPratica() {
        return numeroPratica;
    }

    /**
     * Sets the value of the numeroPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroPratica(String value) {
        this.numeroPratica = value;
    }

    /**
     * Gets the value of the dataPratica property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataPratica() {
        return dataPratica;
    }

    /**
     * Sets the value of the dataPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataPratica(XMLGregorianCalendar value) {
        this.dataPratica = value;
    }

    /**
     * Gets the value of the oraDataPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOraDataPratica() {
        return oraDataPratica;
    }

    /**
     * Sets the value of the oraDataPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOraDataPratica(String value) {
        this.oraDataPratica = value;
    }

    /**
     * Gets the value of the numeroProtocolloGenerale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProtocolloGenerale() {
        return numeroProtocolloGenerale;
    }

    /**
     * Sets the value of the numeroProtocolloGenerale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProtocolloGenerale(String value) {
        this.numeroProtocolloGenerale = value;
    }

    /**
     * Gets the value of the dataProtocolloGenerale property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataProtocolloGenerale() {
        return dataProtocolloGenerale;
    }

    /**
     * Sets the value of the dataProtocolloGenerale property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataProtocolloGenerale(XMLGregorianCalendar value) {
        this.dataProtocolloGenerale = value;
    }

    /**
     * Gets the value of the richiedente property.
     * 
     * @return
     *     possible object is
     *     {@link RichiedenteType }
     *     
     */
    public RichiedenteType getRichiedente() {
        return richiedente;
    }

    /**
     * Sets the value of the richiedente property.
     * 
     * @param value
     *     allowed object is
     *     {@link RichiedenteType }
     *     
     */
    public void setRichiedente(RichiedenteType value) {
        this.richiedente = value;
    }

    /**
     * Gets the value of the aziendaRichiedente property.
     * 
     * @return
     *     possible object is
     *     {@link PersonaGiuridicaType }
     *     
     */
    public PersonaGiuridicaType getAziendaRichiedente() {
        return aziendaRichiedente;
    }

    /**
     * Sets the value of the aziendaRichiedente property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonaGiuridicaType }
     *     
     */
    public void setAziendaRichiedente(PersonaGiuridicaType value) {
        this.aziendaRichiedente = value;
    }

    /**
     * Gets the value of the intermediario property.
     * 
     * @return
     *     possible object is
     *     {@link AnagrafeType }
     *     
     */
    public AnagrafeType getIntermediario() {
        return intermediario;
    }

    /**
     * Sets the value of the intermediario property.
     * 
     * @param value
     *     allowed object is
     *     {@link AnagrafeType }
     *     
     */
    public void setIntermediario(AnagrafeType value) {
        this.intermediario = value;
    }

    /**
     * Gets the value of the oggetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Sets the value of the oggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Gets the value of the intervento property.
     * 
     * @return
     *     possible object is
     *     {@link InterventoType }
     *     
     */
    public InterventoType getIntervento() {
        return intervento;
    }

    /**
     * Sets the value of the intervento property.
     * 
     * @param value
     *     allowed object is
     *     {@link InterventoType }
     *     
     */
    public void setIntervento(InterventoType value) {
        this.intervento = value;
    }

    /**
     * Gets the value of the localizzazione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the localizzazione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocalizzazione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LocalizzazioneNelComuneType }
     * 
     * 
     */
    public List<LocalizzazioneNelComuneType> getLocalizzazione() {
        if (localizzazione == null) {
            localizzazione = new ArrayList<LocalizzazioneNelComuneType>();
        }
        return this.localizzazione;
    }

    /**
     * Gets the value of the statoPratica property.
     * 
     * @return
     *     possible object is
     *     {@link StatoPraticaType }
     *     
     */
    public StatoPraticaType getStatoPratica() {
        return statoPratica;
    }

    /**
     * Sets the value of the statoPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatoPraticaType }
     *     
     */
    public void setStatoPratica(StatoPraticaType value) {
        this.statoPratica = value;
    }

}
