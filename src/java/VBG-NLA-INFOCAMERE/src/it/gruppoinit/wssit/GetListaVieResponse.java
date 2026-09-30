
package it.gruppoinit.wssit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="GetListaVieResult" type="{http://init.sigepro.it}ArrayOfDettagliVia" minOccurs="0"/>
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
    "getListaVieResult"
})
@XmlRootElement(name = "GetListaVieResponse")
public class GetListaVieResponse {

    @XmlElement(name = "GetListaVieResult")
    protected ArrayOfDettagliVia getListaVieResult;

    /**
     * Gets the value of the getListaVieResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDettagliVia }
     *     
     */
    public ArrayOfDettagliVia getGetListaVieResult() {
        return getListaVieResult;
    }

    /**
     * Sets the value of the getListaVieResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDettagliVia }
     *     
     */
    public void setGetListaVieResult(ArrayOfDettagliVia value) {
        this.getListaVieResult = value;
    }

}
