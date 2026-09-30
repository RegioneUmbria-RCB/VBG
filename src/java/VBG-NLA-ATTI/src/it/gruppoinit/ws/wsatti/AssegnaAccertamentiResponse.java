
package it.gruppoinit.ws.wsatti;

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
 *         &lt;element name="AssegnaAccertamentiResult" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "assegnaAccertamentiResult"
})
@XmlRootElement(name = "AssegnaAccertamentiResponse")
public class AssegnaAccertamentiResponse {

    @XmlElement(name = "AssegnaAccertamentiResult", required = true)
    protected String assegnaAccertamentiResult;

    /**
     * Gets the value of the assegnaAccertamentiResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssegnaAccertamentiResult() {
        return assegnaAccertamentiResult;
    }

    /**
     * Sets the value of the assegnaAccertamentiResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssegnaAccertamentiResult(String value) {
        this.assegnaAccertamentiResult = value;
    }

}
