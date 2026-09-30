
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
 *         &lt;element name="GetDetailFieldResult" type="{http://init.sigepro.it}DetailSit" minOccurs="0"/>
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
    "getDetailFieldResult"
})
@XmlRootElement(name = "GetDetailFieldResponse")
public class GetDetailFieldResponse {

    @XmlElement(name = "GetDetailFieldResult")
    protected DetailSit getDetailFieldResult;

    /**
     * Gets the value of the getDetailFieldResult property.
     * 
     * @return
     *     possible object is
     *     {@link DetailSit }
     *     
     */
    public DetailSit getGetDetailFieldResult() {
        return getDetailFieldResult;
    }

    /**
     * Sets the value of the getDetailFieldResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DetailSit }
     *     
     */
    public void setGetDetailFieldResult(DetailSit value) {
        this.getDetailFieldResult = value;
    }

}
