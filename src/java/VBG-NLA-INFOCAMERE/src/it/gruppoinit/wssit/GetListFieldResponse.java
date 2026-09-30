
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
 *         &lt;element name="GetListFieldResult" type="{http://init.sigepro.it}ListSit" minOccurs="0"/>
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
    "getListFieldResult"
})
@XmlRootElement(name = "GetListFieldResponse")
public class GetListFieldResponse {

    @XmlElement(name = "GetListFieldResult")
    protected ListSit getListFieldResult;

    /**
     * Gets the value of the getListFieldResult property.
     * 
     * @return
     *     possible object is
     *     {@link ListSit }
     *     
     */
    public ListSit getGetListFieldResult() {
        return getListFieldResult;
    }

    /**
     * Sets the value of the getListFieldResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListSit }
     *     
     */
    public void setGetListFieldResult(ListSit value) {
        this.getListFieldResult = value;
    }

}
