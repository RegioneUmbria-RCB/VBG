
package it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Dyn2BasetipitestoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dyn2BasetipitestoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idDyn2Basetipitesto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipotesto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dyn2BasetipitestoType", propOrder = {
    "idDyn2Basetipitesto",
    "tipotesto"
})
public class Dyn2BasetipitestoType {

    @XmlElement(required = true)
    protected String idDyn2Basetipitesto;
    protected String tipotesto;

    /**
     * Gets the value of the idDyn2Basetipitesto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdDyn2Basetipitesto() {
        return idDyn2Basetipitesto;
    }

    /**
     * Sets the value of the idDyn2Basetipitesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdDyn2Basetipitesto(String value) {
        this.idDyn2Basetipitesto = value;
    }

    /**
     * Gets the value of the tipotesto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipotesto() {
        return tipotesto;
    }

    /**
     * Sets the value of the tipotesto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipotesto(String value) {
        this.tipotesto = value;
    }

}
