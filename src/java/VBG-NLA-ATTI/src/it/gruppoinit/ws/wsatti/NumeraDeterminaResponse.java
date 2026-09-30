
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
 *         &lt;element name="NumerazioneDeterminaOut" type="{http://tempuri.org/}NumerazioneDeterminaOut"/>
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
    "numerazioneDeterminaOut"
})
@XmlRootElement(name = "NumeraDeterminaResponse")
public class NumeraDeterminaResponse {

    @XmlElement(name = "NumerazioneDeterminaOut", required = true)
    protected NumerazioneDeterminaOut numerazioneDeterminaOut;

    /**
     * Gets the value of the numerazioneDeterminaOut property.
     * 
     * @return
     *     possible object is
     *     {@link NumerazioneDeterminaOut }
     *     
     */
    public NumerazioneDeterminaOut getNumerazioneDeterminaOut() {
        return numerazioneDeterminaOut;
    }

    /**
     * Sets the value of the numerazioneDeterminaOut property.
     * 
     * @param value
     *     allowed object is
     *     {@link NumerazioneDeterminaOut }
     *     
     */
    public void setNumerazioneDeterminaOut(NumerazioneDeterminaOut value) {
        this.numerazioneDeterminaOut = value;
    }

}
