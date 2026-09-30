
package it.piemonte.reteunitaria.csi.aaep.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="testResourcesReturn" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "testResourcesReturn"
})
@XmlRootElement(name = "testResourcesResponse")
public class TestResourcesResponse {

    protected boolean testResourcesReturn;

    /**
     * Gets the value of the testResourcesReturn property.
     * 
     */
    public boolean isTestResourcesReturn() {
        return testResourcesReturn;
    }

    /**
     * Sets the value of the testResourcesReturn property.
     * 
     */
    public void setTestResourcesReturn(boolean value) {
        this.testResourcesReturn = value;
    }

}
