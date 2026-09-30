
package it.gruppoinit.pal.gp.pay.ws.client.pdfutils.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Font complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Font">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fontType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dimensione" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Font", propOrder = {
    "fontType",
    "dimensione"
})
public class Font {

    protected String fontType;
    protected Integer dimensione;

    /**
     * Gets the value of the fontType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFontType() {
        return fontType;
    }

    /**
     * Sets the value of the fontType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFontType(String value) {
        this.fontType = value;
    }

    /**
     * Gets the value of the dimensione property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDimensione() {
        return dimensione;
    }

    /**
     * Sets the value of the dimensione property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDimensione(Integer value) {
        this.dimensione = value;
    }

}
