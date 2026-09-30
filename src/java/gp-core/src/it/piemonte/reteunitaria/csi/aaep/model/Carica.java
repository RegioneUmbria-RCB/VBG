
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Carica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Carica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataFineCarica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="idAaepAzienda" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="progrCarica" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="proPersona" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="codiceCarica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioCarica" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="descrCarica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAaepFonteDato" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="flagRappresentanteLegale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Carica", propOrder = {
    "dataFineCarica",
    "idAaepAzienda",
    "progrCarica",
    "proPersona",
    "codiceCarica",
    "dataInizioCarica",
    "descrCarica",
    "idAaepFonteDato",
    "flagRappresentanteLegale"
})
public class Carica {

    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFineCarica;
    protected long idAaepAzienda;
    protected long progrCarica;
    protected long proPersona;
    @XmlElement(required = true, nillable = true)
    protected String codiceCarica;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioCarica;
    @XmlElement(required = true, nillable = true)
    protected String descrCarica;
    protected long idAaepFonteDato;
    @XmlElement(required = true, nillable = true)
    protected String flagRappresentanteLegale;

    /**
     * Gets the value of the dataFineCarica property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineCarica() {
        return dataFineCarica;
    }

    /**
     * Sets the value of the dataFineCarica property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineCarica(XMLGregorianCalendar value) {
        this.dataFineCarica = value;
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
     * Gets the value of the progrCarica property.
     * 
     */
    public long getProgrCarica() {
        return progrCarica;
    }

    /**
     * Sets the value of the progrCarica property.
     * 
     */
    public void setProgrCarica(long value) {
        this.progrCarica = value;
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
     * Gets the value of the codiceCarica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCarica() {
        return codiceCarica;
    }

    /**
     * Sets the value of the codiceCarica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCarica(String value) {
        this.codiceCarica = value;
    }

    /**
     * Gets the value of the dataInizioCarica property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioCarica() {
        return dataInizioCarica;
    }

    /**
     * Sets the value of the dataInizioCarica property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioCarica(XMLGregorianCalendar value) {
        this.dataInizioCarica = value;
    }

    /**
     * Gets the value of the descrCarica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrCarica() {
        return descrCarica;
    }

    /**
     * Sets the value of the descrCarica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrCarica(String value) {
        this.descrCarica = value;
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
     * Gets the value of the flagRappresentanteLegale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlagRappresentanteLegale() {
        return flagRappresentanteLegale;
    }

    /**
     * Sets the value of the flagRappresentanteLegale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlagRappresentanteLegale(String value) {
        this.flagRappresentanteLegale = value;
    }

}
