
package it.gruppoinit.ws.wsatti;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ImpegniAccertamentiIn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImpegniAccertamentiIn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdDocumento" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Utente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Ruolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ImpegniAccertamenti" type="{http://tempuri.org/}ArrayOfImpegnoAccertamentoIn" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImpegniAccertamentiIn", propOrder = {
    "idDocumento",
    "utente",
    "ruolo",
    "impegniAccertamenti"
})
public class ImpegniAccertamentiIn {

    @XmlElement(name = "IdDocumento")
    protected int idDocumento;
    @XmlElementRef(name = "Utente", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> utente;
    @XmlElementRef(name = "Ruolo", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<String> ruolo;
    @XmlElementRef(name = "ImpegniAccertamenti", namespace = "http://tempuri.org/", type = JAXBElement.class)
    protected JAXBElement<ArrayOfImpegnoAccertamentoIn> impegniAccertamenti;

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
     * Gets the value of the utente property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUtente() {
        return utente;
    }

    /**
     * Sets the value of the utente property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUtente(JAXBElement<String> value) {
        this.utente = value;
    }

    /**
     * Gets the value of the ruolo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRuolo() {
        return ruolo;
    }

    /**
     * Sets the value of the ruolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRuolo(JAXBElement<String> value) {
        this.ruolo = value;
    }

    /**
     * Gets the value of the impegniAccertamenti property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfImpegnoAccertamentoIn }{@code >}
     *     
     */
    public JAXBElement<ArrayOfImpegnoAccertamentoIn> getImpegniAccertamenti() {
        return impegniAccertamenti;
    }

    /**
     * Sets the value of the impegniAccertamenti property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfImpegnoAccertamentoIn }{@code >}
     *     
     */
    public void setImpegniAccertamenti(JAXBElement<ArrayOfImpegnoAccertamentoIn> value) {
        this.impegniAccertamenti = value;
    }

}
