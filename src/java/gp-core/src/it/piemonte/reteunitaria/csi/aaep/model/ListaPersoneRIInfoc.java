
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListaPersoneRIInfoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListaPersoneRIInfoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="progrPersona" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ruoloRL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoPersona" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoPersona" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAAEPFonteDato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ragSocSP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListaPersoneRIInfoc", propOrder = {
    "codiceFiscale",
    "progrPersona",
    "nome",
    "ruoloRL",
    "idAAEPAzienda",
    "descrTipoPersona",
    "tipoPersona",
    "idAAEPFonteDato",
    "ragSocSP",
    "cognome"
})
public class ListaPersoneRIInfoc {

    @XmlElement(required = true, nillable = true)
    protected String codiceFiscale;
    @XmlElement(required = true, nillable = true)
    protected String progrPersona;
    @XmlElement(required = true, nillable = true)
    protected String nome;
    @XmlElement(required = true, nillable = true)
    protected String ruoloRL;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPAzienda;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoPersona;
    @XmlElement(required = true, nillable = true)
    protected String tipoPersona;
    @XmlElement(required = true, nillable = true)
    protected String idAAEPFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String ragSocSP;
    @XmlElement(required = true, nillable = true)
    protected String cognome;

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
     * Gets the value of the progrPersona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgrPersona() {
        return progrPersona;
    }

    /**
     * Sets the value of the progrPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgrPersona(String value) {
        this.progrPersona = value;
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
     * Gets the value of the ruoloRL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuoloRL() {
        return ruoloRL;
    }

    /**
     * Sets the value of the ruoloRL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuoloRL(String value) {
        this.ruoloRL = value;
    }

    /**
     * Gets the value of the idAAEPAzienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAAEPAzienda() {
        return idAAEPAzienda;
    }

    /**
     * Sets the value of the idAAEPAzienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAAEPAzienda(String value) {
        this.idAAEPAzienda = value;
    }

    /**
     * Gets the value of the descrTipoPersona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrTipoPersona() {
        return descrTipoPersona;
    }

    /**
     * Sets the value of the descrTipoPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrTipoPersona(String value) {
        this.descrTipoPersona = value;
    }

    /**
     * Gets the value of the tipoPersona property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoPersona() {
        return tipoPersona;
    }

    /**
     * Sets the value of the tipoPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoPersona(String value) {
        this.tipoPersona = value;
    }

    /**
     * Gets the value of the idAAEPFonteDato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAAEPFonteDato() {
        return idAAEPFonteDato;
    }

    /**
     * Sets the value of the idAAEPFonteDato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAAEPFonteDato(String value) {
        this.idAAEPFonteDato = value;
    }

    /**
     * Gets the value of the ragSocSP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRagSocSP() {
        return ragSocSP;
    }

    /**
     * Sets the value of the ragSocSP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRagSocSP(String value) {
        this.ragSocSP = value;
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

}
