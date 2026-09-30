
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for VariazioneAnagrafica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VariazioneAnagrafica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataVariaz" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="newDescrizioneStatoAttiv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oldDenominazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idTipoVariazione" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="descrizioneNewIdFonteDato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oldIdFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="descrizioneOldIdFonteDato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oldIdNaturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oldDescrizioneStatoAttiv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oldIdStatoAttiv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="newDescrizioneNaturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oldDescrizioneNaturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="newIdFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="descrizioneTipoVariazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="newIdStatoAttiv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="newIdNaturaGiuridica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="newDenominazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VariazioneAnagrafica", propOrder = {
    "dataVariaz",
    "newDescrizioneStatoAttiv",
    "oldDenominazione",
    "idTipoVariazione",
    "descrizioneNewIdFonteDato",
    "oldIdFonteDato",
    "descrizioneOldIdFonteDato",
    "oldIdNaturaGiuridica",
    "oldDescrizioneStatoAttiv",
    "idAzienda",
    "oldIdStatoAttiv",
    "newDescrizioneNaturaGiuridica",
    "oldDescrizioneNaturaGiuridica",
    "newIdFonteDato",
    "descrizioneTipoVariazione",
    "newIdStatoAttiv",
    "newIdNaturaGiuridica",
    "newDenominazione"
})
public class VariazioneAnagrafica {

    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVariaz;
    @XmlElement(required = true, nillable = true)
    protected String newDescrizioneStatoAttiv;
    @XmlElement(required = true, nillable = true)
    protected String oldDenominazione;
    protected long idTipoVariazione;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneNewIdFonteDato;
    protected long oldIdFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneOldIdFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String oldIdNaturaGiuridica;
    @XmlElement(required = true, nillable = true)
    protected String oldDescrizioneStatoAttiv;
    @XmlElement(required = true, nillable = true)
    protected String idAzienda;
    @XmlElement(required = true, nillable = true)
    protected String oldIdStatoAttiv;
    @XmlElement(required = true, nillable = true)
    protected String newDescrizioneNaturaGiuridica;
    @XmlElement(required = true, nillable = true)
    protected String oldDescrizioneNaturaGiuridica;
    protected long newIdFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneTipoVariazione;
    @XmlElement(required = true, nillable = true)
    protected String newIdStatoAttiv;
    @XmlElement(required = true, nillable = true)
    protected String newIdNaturaGiuridica;
    @XmlElement(required = true, nillable = true)
    protected String newDenominazione;

    /**
     * Gets the value of the dataVariaz property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataVariaz() {
        return dataVariaz;
    }

    /**
     * Sets the value of the dataVariaz property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataVariaz(XMLGregorianCalendar value) {
        this.dataVariaz = value;
    }

    /**
     * Gets the value of the newDescrizioneStatoAttiv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewDescrizioneStatoAttiv() {
        return newDescrizioneStatoAttiv;
    }

    /**
     * Sets the value of the newDescrizioneStatoAttiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewDescrizioneStatoAttiv(String value) {
        this.newDescrizioneStatoAttiv = value;
    }

    /**
     * Gets the value of the oldDenominazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldDenominazione() {
        return oldDenominazione;
    }

    /**
     * Sets the value of the oldDenominazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldDenominazione(String value) {
        this.oldDenominazione = value;
    }

    /**
     * Gets the value of the idTipoVariazione property.
     * 
     */
    public long getIdTipoVariazione() {
        return idTipoVariazione;
    }

    /**
     * Sets the value of the idTipoVariazione property.
     * 
     */
    public void setIdTipoVariazione(long value) {
        this.idTipoVariazione = value;
    }

    /**
     * Gets the value of the descrizioneNewIdFonteDato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneNewIdFonteDato() {
        return descrizioneNewIdFonteDato;
    }

    /**
     * Sets the value of the descrizioneNewIdFonteDato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneNewIdFonteDato(String value) {
        this.descrizioneNewIdFonteDato = value;
    }

    /**
     * Gets the value of the oldIdFonteDato property.
     * 
     */
    public long getOldIdFonteDato() {
        return oldIdFonteDato;
    }

    /**
     * Sets the value of the oldIdFonteDato property.
     * 
     */
    public void setOldIdFonteDato(long value) {
        this.oldIdFonteDato = value;
    }

    /**
     * Gets the value of the descrizioneOldIdFonteDato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneOldIdFonteDato() {
        return descrizioneOldIdFonteDato;
    }

    /**
     * Sets the value of the descrizioneOldIdFonteDato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneOldIdFonteDato(String value) {
        this.descrizioneOldIdFonteDato = value;
    }

    /**
     * Gets the value of the oldIdNaturaGiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldIdNaturaGiuridica() {
        return oldIdNaturaGiuridica;
    }

    /**
     * Sets the value of the oldIdNaturaGiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldIdNaturaGiuridica(String value) {
        this.oldIdNaturaGiuridica = value;
    }

    /**
     * Gets the value of the oldDescrizioneStatoAttiv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldDescrizioneStatoAttiv() {
        return oldDescrizioneStatoAttiv;
    }

    /**
     * Sets the value of the oldDescrizioneStatoAttiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldDescrizioneStatoAttiv(String value) {
        this.oldDescrizioneStatoAttiv = value;
    }

    /**
     * Gets the value of the idAzienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAzienda() {
        return idAzienda;
    }

    /**
     * Sets the value of the idAzienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAzienda(String value) {
        this.idAzienda = value;
    }

    /**
     * Gets the value of the oldIdStatoAttiv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldIdStatoAttiv() {
        return oldIdStatoAttiv;
    }

    /**
     * Sets the value of the oldIdStatoAttiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldIdStatoAttiv(String value) {
        this.oldIdStatoAttiv = value;
    }

    /**
     * Gets the value of the newDescrizioneNaturaGiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewDescrizioneNaturaGiuridica() {
        return newDescrizioneNaturaGiuridica;
    }

    /**
     * Sets the value of the newDescrizioneNaturaGiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewDescrizioneNaturaGiuridica(String value) {
        this.newDescrizioneNaturaGiuridica = value;
    }

    /**
     * Gets the value of the oldDescrizioneNaturaGiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldDescrizioneNaturaGiuridica() {
        return oldDescrizioneNaturaGiuridica;
    }

    /**
     * Sets the value of the oldDescrizioneNaturaGiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldDescrizioneNaturaGiuridica(String value) {
        this.oldDescrizioneNaturaGiuridica = value;
    }

    /**
     * Gets the value of the newIdFonteDato property.
     * 
     */
    public long getNewIdFonteDato() {
        return newIdFonteDato;
    }

    /**
     * Sets the value of the newIdFonteDato property.
     * 
     */
    public void setNewIdFonteDato(long value) {
        this.newIdFonteDato = value;
    }

    /**
     * Gets the value of the descrizioneTipoVariazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneTipoVariazione() {
        return descrizioneTipoVariazione;
    }

    /**
     * Sets the value of the descrizioneTipoVariazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneTipoVariazione(String value) {
        this.descrizioneTipoVariazione = value;
    }

    /**
     * Gets the value of the newIdStatoAttiv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewIdStatoAttiv() {
        return newIdStatoAttiv;
    }

    /**
     * Sets the value of the newIdStatoAttiv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewIdStatoAttiv(String value) {
        this.newIdStatoAttiv = value;
    }

    /**
     * Gets the value of the newIdNaturaGiuridica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewIdNaturaGiuridica() {
        return newIdNaturaGiuridica;
    }

    /**
     * Sets the value of the newIdNaturaGiuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewIdNaturaGiuridica(String value) {
        this.newIdNaturaGiuridica = value;
    }

    /**
     * Gets the value of the newDenominazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewDenominazione() {
        return newDenominazione;
    }

    /**
     * Sets the value of the newDenominazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewDenominazione(String value) {
        this.newDenominazione = value;
    }

}
