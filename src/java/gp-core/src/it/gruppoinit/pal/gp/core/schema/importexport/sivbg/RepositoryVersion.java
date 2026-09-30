
package it.gruppoinit.pal.gp.core.schema.importexport.sivbg;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RepositoryVersion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RepositoryVersion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="localVers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="remoteVers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="servizio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RepositoryVersion", namespace = "http://model.repertorio.webred.it/xsd", propOrder = {
    "localVers",
    "note",
    "remoteVers",
    "servizio"
})
public class RepositoryVersion {

    @XmlElementRef(name = "localVers", namespace = "http://model.repertorio.webred.it/xsd", type = JAXBElement.class)
    protected JAXBElement<String> localVers;
    @XmlElementRef(name = "note", namespace = "http://model.repertorio.webred.it/xsd", type = JAXBElement.class)
    protected JAXBElement<String> note;
    @XmlElementRef(name = "remoteVers", namespace = "http://model.repertorio.webred.it/xsd", type = JAXBElement.class)
    protected JAXBElement<String> remoteVers;
    @XmlElementRef(name = "servizio", namespace = "http://model.repertorio.webred.it/xsd", type = JAXBElement.class)
    protected JAXBElement<String> servizio;

    /**
     * Gets the value of the localVers property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getLocalVers() {
        return localVers;
    }

    /**
     * Sets the value of the localVers property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setLocalVers(JAXBElement<String> value) {
        this.localVers = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getNote() {
        return note;
    }

    /**
     * Sets the value of the note property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setNote(JAXBElement<String> value) {
        this.note = value;
    }

    /**
     * Gets the value of the remoteVers property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRemoteVers() {
        return remoteVers;
    }

    /**
     * Sets the value of the remoteVers property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRemoteVers(JAXBElement<String> value) {
        this.remoteVers = value;
    }

    /**
     * Gets the value of the servizio property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getServizio() {
        return servizio;
    }

    /**
     * Sets the value of the servizio property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setServizio(JAXBElement<String> value) {
        this.servizio = value;
    }

}
