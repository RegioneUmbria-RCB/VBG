package it.gruppoinit.pal.gp.backoffice.schemas.messages.oggetti;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mimeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="binaryData" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="metadati" type="{http://gruppoinit.it/sigepro/schemas/messages/oggetti}MetadatoType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "token", "fileName", "mimeType", "binaryData", "metadati" })
@XmlRootElement(name = "OggettiInsertV2Request")
public class OggettiInsertV2Request {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String fileName;
    protected String mimeType;
    @XmlElement(required = true)
    @XmlMimeType("application/octet-stream")
    protected DataHandler binaryData;
    protected List<MetadatoType> metadati;

    public OggettiInsertV2Request() {

	super();
    }

    public OggettiInsertV2Request(OggettiInsertRequest oggettiInsertRequest) {

	this();
	if (oggettiInsertRequest == null) {
	    return;
	}
	this.setToken(oggettiInsertRequest.getToken());
	this.setFileName(oggettiInsertRequest.getFileName());
	this.setMimeType(oggettiInsertRequest.getMimeType());
	this.setBinaryData(oggettiInsertRequest.getBinaryData());
    }

    /**
     * Gets the value of the token property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getToken() {

	return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setToken(String value) {

	this.token = value;
    }

    /**
     * Gets the value of the fileName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFileName() {

	return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setFileName(String value) {

	this.fileName = value;
    }

    /**
     * Gets the value of the mimeType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMimeType() {

	return mimeType;
    }

    /**
     * Sets the value of the mimeType property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setMimeType(String value) {

	this.mimeType = value;
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

    /**
     * Gets the value of the metadati property.
     * 
     * @return possible object is {@link MetadatoType }
     * 
     */
    public List<MetadatoType> getMetadati() {

	if (this.metadati == null) {
	    this.metadati = new ArrayList<MetadatoType>();
	}
	return metadati;
    }
}
