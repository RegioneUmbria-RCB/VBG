
package it.gruppoinit.pal.gp.core.schema.importexport.sivbg;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://model.repertorio.webred.it/xsd}RepositoryVersion" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "_return"
})
@XmlRootElement(name = "getUltimaVersioneResponse")
public class GetUltimaVersioneResponse {

    @XmlElementRef(name = "return", namespace = "http://ws.repertorio.webred.it", type = JAXBElement.class)
    protected JAXBElement<RepositoryVersion> _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RepositoryVersion }{@code >}
     *     
     */
    public JAXBElement<RepositoryVersion> getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RepositoryVersion }{@code >}
     *     
     */
    public void setReturn(JAXBElement<RepositoryVersion> value) {
        this._return = value;
    }

}
