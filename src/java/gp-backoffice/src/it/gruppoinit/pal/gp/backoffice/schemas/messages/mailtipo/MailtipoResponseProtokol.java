
package it.gruppoinit.pal.gp.backoffice.schemas.messages.mailtipo;

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
 *       &lt;all>
 *         &lt;element name="oggettoProt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="corpoProt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "MailtipoResponseProtokol")
public class MailtipoResponseProtokol {

    @XmlElement
    protected String oggettoProt;
    @XmlElement
    protected String corpoProt;
    @XmlElement
    protected String oggetto;
    @XmlElement
    protected String corpo;

    /**
     * Gets the value of the oggetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Sets the value of the oggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Gets the value of the corpo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorpo() {
        return corpo;
    }

    /**
     * Sets the value of the corpo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorpo(String value) {
        this.corpo = value;
    }
    

    /**
     * Gets the value of the oggettoProt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggettoProt() {
        return oggettoProt;
    }

    /**
     * Sets the value of the oggettoProt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggettoProt(String value) {
        this.oggettoProt = value;
    }

    /**
     * Gets the value of the corpoProt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorpoProt() {
        return corpoProt;
    }

    /**
     * Sets the value of the corpoProt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorpoProt(String value) {
        this.corpoProt = value;
    }

}
