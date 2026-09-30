package it.gruppoinit.pal.gp.core.features.istanze.riepilogo.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for BinaryFile complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BinaryFile">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MimeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FileContent" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BinaryFile", propOrder = { "fileName", "mimeType", "fileContent" })
public class BinaryFile {

    @XmlElement(name = "FileName")
    protected String fileName;
    @XmlElement(name = "MimeType")
    protected String mimeType;
    @XmlElement(name = "FileContent")
    protected byte[] fileContent;

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
     * Gets the value of the fileContent property.
     * 
     * @return possible object is byte[]
     */
    public byte[] getFileContent() {

	return fileContent;
    }

    /**
     * Sets the value of the fileContent property.
     * 
     * @param value
     *            allowed object is byte[]
     */
    public void setFileContent(byte[] value) {

	this.fileContent = value;
    }
}
