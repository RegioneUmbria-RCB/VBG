package it.gruppoinit.pal.gp.backoffice.schemas.messages.albopretorio;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Oggetto complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Oggetto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="mime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="filecontent" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="nomefile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dimensioneFile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Oggetto", propOrder = {})
public class Oggetto {

    @XmlElement(required = true)
    protected String mime;
    @XmlElement(required = true)
    @XmlMimeType("application/octet-stream")
    protected DataHandler filecontent;
    @XmlElement(required = true)
    protected String nomefile;
    @XmlElement(required = true)
    protected String dimensioneFile;

    /**
     * Gets the value of the mime property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMime() {

	return mime;
    }

    /**
     * Sets the value of the mime property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setMime(String value) {

	this.mime = value;
    }

    /**
     * Gets the value of the filecontent property.
     * 
     * @return possible object is byte[]
     */
    public DataHandler getFilecontent() {

	return filecontent;
    }

    /**
     * Sets the value of the filecontent property.
     * 
     * @param value
     *            allowed object is byte[]
     */
    public void setFilecontent(DataHandler value) {

	this.filecontent = value;
    }

    /**
     * Gets the value of the nomefile property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNomefile() {

	return nomefile;
    }

    /**
     * Sets the value of the nomefile property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNomefile(String value) {

	this.nomefile = value;
    }

    /**
     * Gets the value of the dimensioneFile property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDimensioneFile() {

	return dimensioneFile;
    }

    /**
     * Sets the value of the dimensioneFile property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDimensioneFile(String value) {

	this.dimensioneFile = value;
    }
}
