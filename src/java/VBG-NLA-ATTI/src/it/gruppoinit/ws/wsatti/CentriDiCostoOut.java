
package it.gruppoinit.ws.wsatti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CentriDiCostoOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CentriDiCostoOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Tipo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Voce" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CdC_provento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Propon_ammor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Importo" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CentriDiCostoOut", propOrder = {
    "tipo",
    "voce",
    "cdCProvento",
    "proponAmmor",
    "importo"
})
public class CentriDiCostoOut {

    @XmlElementRef(name = "Tipo", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> tipo;
    @XmlElementRef(name = "Voce", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> voce;
    @XmlElementRef(name = "CdC_provento", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> cdCProvento;
    @XmlElementRef(name = "Propon_ammor", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> proponAmmor;
    @XmlElement(name = "Importo")
    protected double importo;

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTipo(JAXBElement<String> value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the voce property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getVoce() {
        return voce;
    }

    /**
     * Sets the value of the voce property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setVoce(JAXBElement<String> value) {
        this.voce = value;
    }

    /**
     * Gets the value of the cdCProvento property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCdCProvento() {
        return cdCProvento;
    }

    /**
     * Sets the value of the cdCProvento property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCdCProvento(JAXBElement<String> value) {
        this.cdCProvento = value;
    }

    /**
     * Gets the value of the proponAmmor property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getProponAmmor() {
        return proponAmmor;
    }

    /**
     * Sets the value of the proponAmmor property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setProponAmmor(JAXBElement<String> value) {
        this.proponAmmor = value;
    }

    /**
     * Gets the value of the importo property.
     * 
     */
    public double getImporto() {
        return importo;
    }

    /**
     * Sets the value of the importo property.
     * 
     */
    public void setImporto(double value) {
        this.importo = value;
    }

}
