package it.gruppoinit.pal.gp.backoffice.schemas.messages.schededinamiche;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Dyn2ModelliTScriptType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Dyn2ModelliTScriptType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="evento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="script" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Dyn2ModelliTScriptType", propOrder = { "evento", "binaryData" })
public class Dyn2ModelliTScriptType {

    @XmlElement(required = true)
    protected String evento;
    @XmlMimeType("application/octet-stream")
    protected DataHandler binaryData;

    /**
     * Gets the value of the evento property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEvento() {

	return evento;
    }

    /**
     * Sets the value of the evento property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEvento(String value) {

	this.evento = value;
    }

    /**
     * Gets the value of the binaryData property.
     * 
     * @return possible object is {@link DataHandler }
     * 
     */
    public DataHandler getBinaryData() {

	return binaryData;
    }

    /**
     * Sets the value of the binaryData property.
     * 
     * @param value
     *            allowed object is {@link DataHandler }
     * 
     */
    public void setBinaryData(DataHandler value) {

	this.binaryData = value;
    }
}
