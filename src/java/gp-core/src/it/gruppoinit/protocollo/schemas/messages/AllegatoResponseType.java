package it.gruppoinit.protocollo.schemas.messages;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for AllegatoResponseType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AllegatoResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Serial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TipoFile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Image" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="Commento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IDBase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Versione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Errore" type="{http://it.gruppoinit/Protocollazione}ErroreProtocolloType" minOccurs="0"/>
 *         &lt;element name="Uo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Ruolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllegatoResponseType", propOrder = { "serial", "tipoFile", "contentType", "image", "commento", "idBase", "versione", "errore", "uo",
	"ruolo" })
public class AllegatoResponseType {

    @XmlElement(name = "Serial", nillable = true)
    protected String serial;
    @XmlElement(name = "TipoFile", nillable = true)
    protected String tipoFile;
    @XmlElement(name = "ContentType", nillable = true)
    protected String contentType;
    @XmlElement(name = "Image", nillable = true)
    @XmlMimeType("application/octet-stream")
    protected DataHandler image;
    @XmlElement(name = "Commento", nillable = true)
    protected String commento;
    @XmlElement(name = "IDBase", nillable = true)
    protected String idBase;
    @XmlElement(name = "Versione", nillable = true)
    protected String versione;
    @XmlElement(name = "Errore", nillable = true)
    protected ErroreProtocolloType errore;
    @XmlElement(name = "Uo", nillable = true)
    protected String uo;
    @XmlElement(name = "Ruolo", nillable = true)
    protected String ruolo;

    /**
     * Gets the value of the serial property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSerial() {

	return serial;
    }

    /**
     * Sets the value of the serial property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSerial(String value) {

	this.serial = value;
    }

    /**
     * Gets the value of the tipoFile property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTipoFile() {

	return tipoFile;
    }

    /**
     * Sets the value of the tipoFile property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTipoFile(String value) {

	this.tipoFile = value;
    }

    /**
     * Gets the value of the contentType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getContentType() {

	return contentType;
    }

    /**
     * Sets the value of the contentType property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setContentType(String value) {

	this.contentType = value;
    }

    /**
     * Gets the value of the image property.
     * 
     * @return possible object is {@link DataHandler }
     * 
     */
    public DataHandler getImage() {

	return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *            allowed object is {@link DataHandler }
     * 
     */
    public void setImage(DataHandler value) {

	this.image = value;
    }

    /**
     * Gets the value of the commento property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCommento() {

	return commento;
    }

    /**
     * Sets the value of the commento property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCommento(String value) {

	this.commento = value;
    }

    /**
     * Gets the value of the idBase property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIDBase() {

	return idBase;
    }

    /**
     * Sets the value of the idBase property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIDBase(String value) {

	this.idBase = value;
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
     * Gets the value of the errore property.
     * 
     * @return possible object is {@link ErroreProtocolloType }
     * 
     */
    public ErroreProtocolloType getErrore() {

	return errore;
    }

    /**
     * Sets the value of the errore property.
     * 
     * @param value
     *            allowed object is {@link ErroreProtocolloType }
     * 
     */
    public void setErrore(ErroreProtocolloType value) {

	this.errore = value;
    }

    /**
     * Gets the value of the uo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getUo() {

	return uo;
    }

    /**
     * Sets the value of the uo property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setUo(String value) {

	this.uo = value;
    }

    /**
     * Gets the value of the ruolo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRuolo() {

	return ruolo;
    }

    /**
     * Sets the value of the ruolo property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setRuolo(String value) {

	this.ruolo = value;
    }
}
