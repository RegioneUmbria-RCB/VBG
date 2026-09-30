
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
 *         &lt;element name="GetFeaturesResult" type="{http://init.sigepro.it}SitFeatures" minOccurs="0"/>
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
    "getFeaturesResult"
})
@XmlRootElement(name = "GetFeaturesResponse")
public class GetFeaturesResponse {

    @XmlElement(name = "GetFeaturesResult")
    protected SitFeatures getFeaturesResult;

    /**
     * Gets the value of the getFeaturesResult property.
     * 
     * @return
     *     possible object is
     *     {@link SitFeatures }
     *     
     */
    public SitFeatures getGetFeaturesResult() {
        return getFeaturesResult;
    }

    /**
     * Sets the value of the getFeaturesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SitFeatures }
     *     
     */
    public void setGetFeaturesResult(SitFeatures value) {
        this.getFeaturesResult = value;
    }

}
