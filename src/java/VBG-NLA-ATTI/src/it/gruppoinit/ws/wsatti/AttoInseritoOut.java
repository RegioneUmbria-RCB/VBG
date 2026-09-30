package it.gruppoinit.ws.wsatti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for AttoInseritoOut complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttoInseritoOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdDocumento" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TipoDocumento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Numero" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Anno" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Annullato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Messaggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Errore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttoInseritoOut", propOrder = { "idDocumento", "tipoDocumento", "numero", "anno", "annullato", "messaggio", "errore" })
public class AttoInseritoOut {

    @XmlElement(name = "IdDocumento")
    protected int idDocumento;
    @XmlElementRef(name = "TipoDocumento", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> tipoDocumento;
    @XmlElement(name = "Numero")
    protected int numero;
    @XmlElement(name = "Anno")
    protected int anno;
    @XmlElementRef(name = "Annullato", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> annullato;
    @XmlElementRef(name = "Messaggio", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> messaggio;
    @XmlElementRef(name = "Errore", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> errore;

    /**
     * Gets the value of the idDocumento property.
     * 
     */
    public int getIdDocumento() {

	return idDocumento;
    }

    /**
     * Sets the value of the idDocumento property.
     * 
     */
    public void setIdDocumento(int value) {

	this.idDocumento = value;
    }

    /**
     * Gets the value of the tipoDocumento property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getTipoDocumento() {

	return tipoDocumento;
    }

    /**
     * Sets the value of the tipoDocumento property.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setTipoDocumento(JAXBElement<String> value) {

	this.tipoDocumento = value;
    }

    /**
     * Gets the value of the numero property.
     * 
     */
    public int getNumero() {

	return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     */
    public void setNumero(int value) {

	this.numero = value;
    }

    /**
     * Gets the value of the anno property.
     * 
     */
    public int getAnno() {

	return anno;
    }

    /**
     * Sets the value of the anno property.
     * 
     */
    public void setAnno(int value) {

	this.anno = value;
    }

    /**
     * Gets the value of the annullato property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getAnnullato() {

	return annullato;
    }

    /**
     * Sets the value of the annullato property.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setAnnullato(JAXBElement<String> value) {

	this.annullato = value;
    }

    /**
     * Gets the value of the messaggio property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getMessaggio() {

	return messaggio;
    }

    /**
     * Sets the value of the messaggio property.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setMessaggio(JAXBElement<String> value) {

	this.messaggio = value;
    }

    /**
     * Gets the value of the errore property.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public JAXBElement<String> getErrore() {

	return errore;
    }

    /**
     * Sets the value of the errore property.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setErrore(JAXBElement<String> value) {

	this.errore = value;
    }
}
