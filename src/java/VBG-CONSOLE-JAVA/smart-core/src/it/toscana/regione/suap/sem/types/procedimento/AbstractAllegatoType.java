
package it.toscana.regione.suap.sem.types.procedimento;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for abstractAllegatoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="abstractAllegatoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="hashedFile" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}hashedFileType"/>
 *           &lt;element name="contentID" type="{http://ws-i.org/profiles/basic/1.1/xsd}swaRef"/>
 *         &lt;/choice>
 *         &lt;element name="contentType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fileSize" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "abstractAllegatoType", propOrder = {
    "hashedFile",
    "contentID",
    "contentType",
    "fileSize"
})
@XmlSeeAlso({
    AllegatoPDFType.class,
    AllegatoInModuloType.class,
    AllegatoXMLType.class,
    AllegatoType.class
})
public abstract class AbstractAllegatoType {

    protected HashedFileType hashedFile;
    @XmlElement(type = String.class)
    @XmlAttachmentRef
    protected DataHandler contentID;
    @XmlElement(required = true)
    protected String contentType;
    protected Long fileSize;

    /**
     * Gets the value of the hashedFile property.
     * 
     * @return
     *     possible object is
     *     {@link HashedFileType }
     *     
     */
    public HashedFileType getHashedFile() {
        return hashedFile;
    }

    /**
     * Sets the value of the hashedFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link HashedFileType }
     *     
     */
    public void setHashedFile(HashedFileType value) {
        this.hashedFile = value;
    }

    /**
     * Gets the value of the contentID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public DataHandler getContentID() {
        return contentID;
    }

    /**
     * Sets the value of the contentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentID(DataHandler value) {
        this.contentID = value;
    }

    /**
     * Gets the value of the contentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the value of the contentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentType(String value) {
        this.contentType = value;
    }

    /**
     * Gets the value of the fileSize property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * Sets the value of the fileSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFileSize(Long value) {
        this.fileSize = value;
    }

}
