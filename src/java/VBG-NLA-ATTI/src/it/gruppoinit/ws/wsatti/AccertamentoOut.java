
package it.gruppoinit.ws.wsatti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccertamentoOut complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccertamentoOut">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AnnoAccertamento" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="NumeroAccertamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CapitoloAccertamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ArticoloAccertamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CodSiopeAccertamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImportoAccertamento" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="SoggettoAccertamento" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccertamentoOut", propOrder = {
    "annoAccertamento",
    "numeroAccertamento",
    "capitoloAccertamento",
    "articoloAccertamento",
    "codSiopeAccertamento",
    "importoAccertamento",
    "soggettoAccertamento"
})
public class AccertamentoOut {

    @XmlElement(name = "AnnoAccertamento")
    protected int annoAccertamento;
    @XmlElementRef(name = "NumeroAccertamento", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> numeroAccertamento;
    @XmlElementRef(name = "CapitoloAccertamento", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> capitoloAccertamento;
    @XmlElementRef(name = "ArticoloAccertamento", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> articoloAccertamento;
    @XmlElementRef(name = "CodSiopeAccertamento", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> codSiopeAccertamento;
    @XmlElement(name = "ImportoAccertamento")
    protected double importoAccertamento;
    @XmlElement(name = "SoggettoAccertamento")
    protected double soggettoAccertamento;

    /**
     * Gets the value of the annoAccertamento property.
     * 
     */
    public int getAnnoAccertamento() {
        return annoAccertamento;
    }

    /**
     * Sets the value of the annoAccertamento property.
     * 
     */
    public void setAnnoAccertamento(int value) {
        this.annoAccertamento = value;
    }

    /**
     * Gets the value of the numeroAccertamento property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNumeroAccertamento() {
        return numeroAccertamento;
    }

    /**
     * Sets the value of the numeroAccertamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNumeroAccertamento(JAXBElement<String> value) {
        this.numeroAccertamento = value;
    }

    /**
     * Gets the value of the capitoloAccertamento property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCapitoloAccertamento() {
        return capitoloAccertamento;
    }

    /**
     * Sets the value of the capitoloAccertamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCapitoloAccertamento(JAXBElement<String> value) {
        this.capitoloAccertamento = value;
    }

    /**
     * Gets the value of the articoloAccertamento property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getArticoloAccertamento() {
        return articoloAccertamento;
    }

    /**
     * Sets the value of the articoloAccertamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setArticoloAccertamento(JAXBElement<String> value) {
        this.articoloAccertamento = value;
    }

    /**
     * Gets the value of the codSiopeAccertamento property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getCodSiopeAccertamento() {
        return codSiopeAccertamento;
    }

    /**
     * Sets the value of the codSiopeAccertamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setCodSiopeAccertamento(JAXBElement<String> value) {
        this.codSiopeAccertamento = value;
    }

    /**
     * Gets the value of the importoAccertamento property.
     * 
     */
    public double getImportoAccertamento() {
        return importoAccertamento;
    }

    /**
     * Sets the value of the importoAccertamento property.
     * 
     */
    public void setImportoAccertamento(double value) {
        this.importoAccertamento = value;
    }

    /**
     * Gets the value of the soggettoAccertamento property.
     * 
     */
    public double getSoggettoAccertamento() {
        return soggettoAccertamento;
    }

    /**
     * Sets the value of the soggettoAccertamento property.
     * 
     */
    public void setSoggettoAccertamento(double value) {
        this.soggettoAccertamento = value;
    }

}
