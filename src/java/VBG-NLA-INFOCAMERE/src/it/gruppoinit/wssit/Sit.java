
package it.gruppoinit.wssit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Sit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Sit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdComune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodVia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Civico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Km" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Esponente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Colore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Scala" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Interno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EsponenteInterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodCivico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoCatasto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sezione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Foglio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Particella" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sub" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Fabbricato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OggettoTerritoriale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DescrizioneVia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CAP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Circoscrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Frazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Zona" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Piano" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Quartiere" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodiceComune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccessoTipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccessoNumero" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AccessoDescrizione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Sit", propOrder = {
    "idComune",
    "codVia",
    "civico",
    "km",
    "esponente",
    "colore",
    "scala",
    "interno",
    "esponenteInterno",
    "codCivico",
    "tipoCatasto",
    "sezione",
    "foglio",
    "particella",
    "sub",
    "ui",
    "fabbricato",
    "oggettoTerritoriale",
    "descrizioneVia",
    "cap",
    "circoscrizione",
    "frazione",
    "zona",
    "piano",
    "quartiere",
    "codiceComune",
    "accessoTipo",
    "accessoNumero",
    "accessoDescrizione"
})
public class Sit {

    @XmlElement(name = "IdComune")
    protected String idComune;
    @XmlElement(name = "CodVia")
    protected String codVia;
    @XmlElement(name = "Civico")
    protected String civico;
    @XmlElement(name = "Km")
    protected String km;
    @XmlElement(name = "Esponente")
    protected String esponente;
    @XmlElement(name = "Colore")
    protected String colore;
    @XmlElement(name = "Scala")
    protected String scala;
    @XmlElement(name = "Interno")
    protected String interno;
    @XmlElement(name = "EsponenteInterno")
    protected String esponenteInterno;
    @XmlElement(name = "CodCivico")
    protected String codCivico;
    @XmlElement(name = "TipoCatasto")
    protected String tipoCatasto;
    @XmlElement(name = "Sezione")
    protected String sezione;
    @XmlElement(name = "Foglio")
    protected String foglio;
    @XmlElement(name = "Particella")
    protected String particella;
    @XmlElement(name = "Sub")
    protected String sub;
    @XmlElement(name = "UI")
    protected String ui;
    @XmlElement(name = "Fabbricato")
    protected String fabbricato;
    @XmlElement(name = "OggettoTerritoriale")
    protected String oggettoTerritoriale;
    @XmlElement(name = "DescrizioneVia")
    protected String descrizioneVia;
    @XmlElement(name = "CAP")
    protected String cap;
    @XmlElement(name = "Circoscrizione")
    protected String circoscrizione;
    @XmlElement(name = "Frazione")
    protected String frazione;
    @XmlElement(name = "Zona")
    protected String zona;
    @XmlElement(name = "Piano")
    protected String piano;
    @XmlElement(name = "Quartiere")
    protected String quartiere;
    @XmlElement(name = "CodiceComune")
    protected String codiceComune;
    @XmlElement(name = "AccessoTipo")
    protected String accessoTipo;
    @XmlElement(name = "AccessoNumero")
    protected String accessoNumero;
    @XmlElement(name = "AccessoDescrizione")
    protected String accessoDescrizione;

    /**
     * Gets the value of the idComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdComune() {
        return idComune;
    }

    /**
     * Sets the value of the idComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdComune(String value) {
        this.idComune = value;
    }

    /**
     * Gets the value of the codVia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodVia() {
        return codVia;
    }

    /**
     * Sets the value of the codVia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodVia(String value) {
        this.codVia = value;
    }

    /**
     * Gets the value of the civico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCivico() {
        return civico;
    }

    /**
     * Sets the value of the civico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCivico(String value) {
        this.civico = value;
    }

    /**
     * Gets the value of the km property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKm() {
        return km;
    }

    /**
     * Sets the value of the km property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKm(String value) {
        this.km = value;
    }

    /**
     * Gets the value of the esponente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsponente() {
        return esponente;
    }

    /**
     * Sets the value of the esponente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsponente(String value) {
        this.esponente = value;
    }

    /**
     * Gets the value of the colore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColore() {
        return colore;
    }

    /**
     * Sets the value of the colore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColore(String value) {
        this.colore = value;
    }

    /**
     * Gets the value of the scala property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScala() {
        return scala;
    }

    /**
     * Sets the value of the scala property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScala(String value) {
        this.scala = value;
    }

    /**
     * Gets the value of the interno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterno() {
        return interno;
    }

    /**
     * Sets the value of the interno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterno(String value) {
        this.interno = value;
    }

    /**
     * Gets the value of the esponenteInterno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsponenteInterno() {
        return esponenteInterno;
    }

    /**
     * Sets the value of the esponenteInterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsponenteInterno(String value) {
        this.esponenteInterno = value;
    }

    /**
     * Gets the value of the codCivico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCivico() {
        return codCivico;
    }

    /**
     * Sets the value of the codCivico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCivico(String value) {
        this.codCivico = value;
    }

    /**
     * Gets the value of the tipoCatasto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoCatasto() {
        return tipoCatasto;
    }

    /**
     * Sets the value of the tipoCatasto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoCatasto(String value) {
        this.tipoCatasto = value;
    }

    /**
     * Gets the value of the sezione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSezione() {
        return sezione;
    }

    /**
     * Sets the value of the sezione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSezione(String value) {
        this.sezione = value;
    }

    /**
     * Gets the value of the foglio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFoglio() {
        return foglio;
    }

    /**
     * Sets the value of the foglio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFoglio(String value) {
        this.foglio = value;
    }

    /**
     * Gets the value of the particella property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParticella() {
        return particella;
    }

    /**
     * Sets the value of the particella property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParticella(String value) {
        this.particella = value;
    }

    /**
     * Gets the value of the sub property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSub() {
        return sub;
    }

    /**
     * Sets the value of the sub property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSub(String value) {
        this.sub = value;
    }

    /**
     * Gets the value of the ui property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUI() {
        return ui;
    }

    /**
     * Sets the value of the ui property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUI(String value) {
        this.ui = value;
    }

    /**
     * Gets the value of the fabbricato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFabbricato() {
        return fabbricato;
    }

    /**
     * Sets the value of the fabbricato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFabbricato(String value) {
        this.fabbricato = value;
    }

    /**
     * Gets the value of the oggettoTerritoriale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggettoTerritoriale() {
        return oggettoTerritoriale;
    }

    /**
     * Sets the value of the oggettoTerritoriale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggettoTerritoriale(String value) {
        this.oggettoTerritoriale = value;
    }

    /**
     * Gets the value of the descrizioneVia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneVia() {
        return descrizioneVia;
    }

    /**
     * Sets the value of the descrizioneVia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneVia(String value) {
        this.descrizioneVia = value;
    }

    /**
     * Gets the value of the cap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCAP() {
        return cap;
    }

    /**
     * Sets the value of the cap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCAP(String value) {
        this.cap = value;
    }

    /**
     * Gets the value of the circoscrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCircoscrizione() {
        return circoscrizione;
    }

    /**
     * Sets the value of the circoscrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCircoscrizione(String value) {
        this.circoscrizione = value;
    }

    /**
     * Gets the value of the frazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrazione() {
        return frazione;
    }

    /**
     * Sets the value of the frazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrazione(String value) {
        this.frazione = value;
    }

    /**
     * Gets the value of the zona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZona() {
        return zona;
    }

    /**
     * Sets the value of the zona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZona(String value) {
        this.zona = value;
    }

    /**
     * Gets the value of the piano property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPiano() {
        return piano;
    }

    /**
     * Sets the value of the piano property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPiano(String value) {
        this.piano = value;
    }

    /**
     * Gets the value of the quartiere property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuartiere() {
        return quartiere;
    }

    /**
     * Sets the value of the quartiere property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuartiere(String value) {
        this.quartiere = value;
    }

    /**
     * Gets the value of the codiceComune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceComune() {
        return codiceComune;
    }

    /**
     * Sets the value of the codiceComune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceComune(String value) {
        this.codiceComune = value;
    }

    /**
     * Gets the value of the accessoTipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessoTipo() {
        return accessoTipo;
    }

    /**
     * Sets the value of the accessoTipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessoTipo(String value) {
        this.accessoTipo = value;
    }

    /**
     * Gets the value of the accessoNumero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessoNumero() {
        return accessoNumero;
    }

    /**
     * Sets the value of the accessoNumero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessoNumero(String value) {
        this.accessoNumero = value;
    }

    /**
     * Gets the value of the accessoDescrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessoDescrizione() {
        return accessoDescrizione;
    }

    /**
     * Sets the value of the accessoDescrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessoDescrizione(String value) {
        this.accessoDescrizione = value;
    }

}
