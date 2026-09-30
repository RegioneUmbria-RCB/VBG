
package it.gruppoinit.ws.wsatti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RegistroAssegnatoOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RegistroAssegnatoOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TipoRegistro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AnnoRegistro" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NumeroRegistro" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DataRegistro" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistroAssegnatoOut", propOrder = {
    "tipoRegistro",
    "annoRegistro",
    "numeroRegistro",
    "dataRegistro"
})
public class RegistroAssegnatoOut {

    @XmlElementRef(name = "TipoRegistro", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> tipoRegistro;
    @XmlElement(name = "AnnoRegistro")
    protected int annoRegistro;
    @XmlElement(name = "NumeroRegistro")
    protected int numeroRegistro;
    @XmlElementRef(name = "DataRegistro", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> dataRegistro;

    /**
     * Gets the value of the tipoRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipoRegistro() {
        return tipoRegistro;
    }

    /**
     * Sets the value of the tipoRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipoRegistro(JAXBElement<String> value) {
        this.tipoRegistro = value;
    }

    /**
     * Gets the value of the annoRegistro property.
     * 
     */
    public int getAnnoRegistro() {
        return annoRegistro;
    }

    /**
     * Sets the value of the annoRegistro property.
     * 
     */
    public void setAnnoRegistro(int value) {
        this.annoRegistro = value;
    }

    /**
     * Gets the value of the numeroRegistro property.
     * 
     */
    public int getNumeroRegistro() {
        return numeroRegistro;
    }

    /**
     * Sets the value of the numeroRegistro property.
     * 
     */
    public void setNumeroRegistro(int value) {
        this.numeroRegistro = value;
    }

    /**
     * Gets the value of the dataRegistro property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDataRegistro() {
        return dataRegistro;
    }

    /**
     * Sets the value of the dataRegistro property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDataRegistro(JAXBElement<String> value) {
        this.dataRegistro = value;
    }

}
