
package it.gruppoinit.ws.wsatti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AllegatoIn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AllegatoIn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoFile" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContentType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Image" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="Commento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IdAllegatoPrincipale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Schema" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NomeAllegato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="hash" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoAllegato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="uri" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllegatoIn", propOrder = {
    "tipoFile",
    "contentType",
    "image",
    "commento",
    "idAllegatoPrincipale",
    "schema",
    "nomeAllegato",
    "hash",
    "tipoAllegato",
    "uri"
})
public class AllegatoIn {

    @XmlElementRef(name = "TipoFile", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> tipoFile;
    @XmlElementRef(name = "ContentType", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> contentType;
    @XmlElementRef(name = "Image", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<byte[]> image;
    @XmlElementRef(name = "Commento", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> commento;
    @XmlElementRef(name = "IdAllegatoPrincipale", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> idAllegatoPrincipale;
    @XmlElementRef(name = "Schema", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> schema;
    @XmlElementRef(name = "NomeAllegato", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> nomeAllegato;
    @XmlElement(required = true, nillable = true)
    protected String hash;
    @XmlElement(required = true, nillable = true)
    protected String tipoAllegato;
    @XmlElement(required = true, nillable = true)
    protected String uri;

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
     * Gets the value of the idAllegatoPrincipale property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIdAllegatoPrincipale() {
        return idAllegatoPrincipale;
    }

    /**
     * Sets the value of the idAllegatoPrincipale property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIdAllegatoPrincipale(JAXBElement<String> value) {
        this.idAllegatoPrincipale = value;
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

    /**
     * Gets the value of the hash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHash() {
        return hash;
    }

    /**
     * Sets the value of the hash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHash(String value) {
        this.hash = value;
    }

    /**
     * Gets the value of the tipoAllegato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoAllegato() {
        return tipoAllegato;
    }

    /**
     * Sets the value of the tipoAllegato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoAllegato(String value) {
        this.tipoAllegato = value;
    }

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

}
