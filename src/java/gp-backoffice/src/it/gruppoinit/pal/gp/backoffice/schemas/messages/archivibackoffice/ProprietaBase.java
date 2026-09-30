
package it.gruppoinit.pal.gp.backoffice.schemas.messages.archivibackoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Proprieta minime per un elemento
 * 			
 * 
 * <p>Java class for ProprietaBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProprietaBase">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceBackoffice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceSistemaEsterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProprietaBase", propOrder = {
    "codiceBackoffice",
    "codiceSistemaEsterno",
    "descrizione"
})
public class ProprietaBase {

    protected String codiceBackoffice;
    protected String codiceSistemaEsterno;
    @XmlElement(required = true)
    protected String descrizione;

    /**
     * Gets the value of the codiceBackoffice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceBackoffice() {
        return codiceBackoffice;
    }

    /**
     * Sets the value of the codiceBackoffice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceBackoffice(String value) {
        this.codiceBackoffice = value;
    }

    /**
     * Gets the value of the codiceSistemaEsterno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceSistemaEsterno() {
        return codiceSistemaEsterno;
    }

    /**
     * Sets the value of the codiceSistemaEsterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceSistemaEsterno(String value) {
        this.codiceSistemaEsterno = value;
    }

    /**
     * Gets the value of the descrizione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizione(String value) {
        this.descrizione = value;
    }

}
