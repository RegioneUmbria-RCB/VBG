
package it.gruppoinit.ws.wsatti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AllegatoOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AllegatoOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Serial" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TipoFile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Image" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="Commento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IDBase" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Versione" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="TipoAllegato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Schema" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SottoEstensione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Firmato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NomeAllegato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllegatoOut", propOrder = {
    "serial",
    "tipoFile",
    "contentType",
    "image",
    "commento",
    "idBase",
    "versione",
    "tipoAllegato",
    "schema",
    "sottoEstensione",
    "firmato",
    "nomeAllegato"
})
public class AllegatoOut {

    @XmlElement(name = "Serial")
    protected int serial;
    @XmlElementRef(name = "TipoFile", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> tipoFile;
    @XmlElementRef(name = "ContentType", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> contentType;
    @XmlElementRef(name = "Image", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<byte[]> image;
    @XmlElementRef(name = "Commento", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> commento;
    @XmlElement(name = "IDBase")
    protected int idBase;
    @XmlElement(name = "Versione")
    protected short versione;
    @XmlElementRef(name = "TipoAllegato", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> tipoAllegato;
    @XmlElementRef(name = "Schema", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> schema;
    @XmlElementRef(name = "SottoEstensione", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> sottoEstensione;
    @XmlElementRef(name = "Firmato", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> firmato;
    @XmlElementRef(name = "NomeAllegato", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> nomeAllegato;

    /**
     * Gets the value of the serial property.
     * 
     */
    public int getSerial() {
        return serial;
    }

    /**
     * Sets the value of the serial property.
     * 
     */
    public void setSerial(int value) {
        this.serial = value;
    }

    /**
     * Gets the value of the tipoFile property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipoFile() {
        return tipoFile;
    }

    /**
     * Sets the value of the tipoFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipoFile(JAXBElement<String> value) {
        this.tipoFile = value;
    }

    /**
     * Gets the value of the contentType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getContentType() {
        return contentType;
    }

    /**
     * Sets the value of the contentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setContentType(JAXBElement<String> value) {
        this.contentType = value;
    }

    /**
     * Gets the value of the image property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public JAXBElement<byte[]> getImage() {
        return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public void setImage(JAXBElement<byte[]> value) {
        this.image = value;
    }

    /**
     * Gets the value of the commento property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCommento() {
        return commento;
    }

    /**
     * Sets the value of the commento property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCommento(JAXBElement<String> value) {
        this.commento = value;
    }

    /**
     * Gets the value of the idBase property.
     * 
     */
    public int getIDBase() {
        return idBase;
    }

    /**
     * Sets the value of the idBase property.
     * 
     */
    public void setIDBase(int value) {
        this.idBase = value;
    }

    /**
     * Gets the value of the versione property.
     * 
     */
    public short getVersione() {
        return versione;
    }

    /**
     * Sets the value of the versione property.
     * 
     */
    public void setVersione(short value) {
        this.versione = value;
    }

    /**
     * Gets the value of the tipoAllegato property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipoAllegato() {
        return tipoAllegato;
    }

    /**
     * Sets the value of the tipoAllegato property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipoAllegato(JAXBElement<String> value) {
        this.tipoAllegato = value;
    }

    /**
     * Gets the value of the schema property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSchema() {
        return schema;
    }

    /**
     * Sets the value of the schema property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSchema(JAXBElement<String> value) {
        this.schema = value;
    }

    /**
     * Gets the value of the sottoEstensione property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSottoEstensione() {
        return sottoEstensione;
    }

    /**
     * Sets the value of the sottoEstensione property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSottoEstensione(JAXBElement<String> value) {
        this.sottoEstensione = value;
    }

    /**
     * Gets the value of the firmato property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getFirmato() {
        return firmato;
    }

    /**
     * Sets the value of the firmato property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setFirmato(JAXBElement<String> value) {
        this.firmato = value;
    }

    /**
     * Gets the value of the nomeAllegato property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNomeAllegato() {
        return nomeAllegato;
    }

    /**
     * Sets the value of the nomeAllegato property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNomeAllegato(JAXBElement<String> value) {
        this.nomeAllegato = value;
    }

}
