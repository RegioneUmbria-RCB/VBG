
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Persona complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Persona">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrTipoPersona" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoPersona" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAaepAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="proPersona" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="idAaepFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="listaCariche" type="{urn:AAEPCSI}ArrayOfCarica"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Persona", propOrder = {
    "codiceFiscale",
    "nome",
    "descrTipoPersona",
    "tipoPersona",
    "idAaepAzienda",
    "proPersona",
    "idAaepFonteDato",
    "cognome",
    "listaCariche"
})
public class Persona {

    @XmlElement(required = true, nillable = true)
    protected String codiceFiscale;
    @XmlElement(required = true, nillable = true)
    protected String nome;
    @XmlElement(required = true, nillable = true)
    protected String descrTipoPersona;
    @XmlElement(required = true, nillable = true)
    protected String tipoPersona;
    protected long idAaepAzienda;
    protected long proPersona;
    protected long idAaepFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String cognome;
    @XmlElement(required = true, nillable = true)
    protected ArrayOfCarica listaCariche;

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
     * Gets the value of the idAaepAzienda property.
     * 
     */
    public long getIdAaepAzienda() {
        return idAaepAzienda;
    }

    /**
     * Sets the value of the idAaepAzienda property.
     * 
     */
    public void setIdAaepAzienda(long value) {
        this.idAaepAzienda = value;
    }

    /**
     * Gets the value of the proPersona property.
     * 
     */
    public long getProPersona() {
        return proPersona;
    }

    /**
     * Sets the value of the proPersona property.
     * 
     */
    public void setProPersona(long value) {
        this.proPersona = value;
    }

    /**
     * Gets the value of the idAaepFonteDato property.
     * 
     */
    public long getIdAaepFonteDato() {
        return idAaepFonteDato;
    }

    /**
     * Sets the value of the idAaepFonteDato property.
     * 
     */
    public void setIdAaepFonteDato(long value) {
        this.idAaepFonteDato = value;
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
     * Gets the value of the listaCariche property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCarica }
     *     
     */
    public ArrayOfCarica getListaCariche() {
        return listaCariche;
    }

    /**
     * Sets the value of the listaCariche property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCarica }
     *     
     */
    public void setListaCariche(ArrayOfCarica value) {
        this.listaCariche = value;
    }

}
