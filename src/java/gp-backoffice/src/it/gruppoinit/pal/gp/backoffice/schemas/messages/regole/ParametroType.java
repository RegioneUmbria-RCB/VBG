package it.gruppoinit.pal.gp.backoffice.schemas.messages.regole;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ParametroType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParametroType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParametroType", propOrder = { "descrizione", "valore" })
public class ParametroType {

    @XmlElement(required = true)
    protected Object descrizione;
    @XmlElement(required = true)
    protected String valore;

    /**
     * Gets the value of the descrizione property.
     * 
     * @return possible object is {@link Object }
     * 
     */
    public Object getDescrizione() {

	return descrizione;
    }

    /**
     * Sets the value of the descrizione property.
     * 
     * @param value
     *            allowed object is {@link Object }
     * 
     */
    public void setDescrizione(Object value) {

	this.descrizione = value;
    }

    /**
     * Gets the value of the valore property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getValore() {

	return valore;
    }

    /**
     * Sets the value of the valore property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setValore(String value) {

	this.valore = value;
    }
}
