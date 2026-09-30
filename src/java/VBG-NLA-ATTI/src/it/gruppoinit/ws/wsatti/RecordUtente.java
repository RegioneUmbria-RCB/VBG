
package it.gruppoinit.ws.wsatti;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RecordUtente complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RecordUtente">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="campi" type="{http://tempuri.org/}ArrayOfCampoUtente"/>
 *         &lt;element name="progressivo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecordUtente", propOrder = {
    "campi",
    "progressivo"
})
public class RecordUtente {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfCampoUtente campi;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer progressivo;

    /**
     * Gets the value of the campi property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCampoUtente }
     *     
     */
    public ArrayOfCampoUtente getCampi() {
        return campi;
    }

    /**
     * Sets the value of the campi property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCampoUtente }
     *     
     */
    public void setCampi(ArrayOfCampoUtente value) {
        this.campi = value;
    }

    /**
     * Gets the value of the progressivo property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getProgressivo() {
        return progressivo;
    }

    /**
     * Sets the value of the progressivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setProgressivo(Integer value) {
        this.progressivo = value;
    }

}
