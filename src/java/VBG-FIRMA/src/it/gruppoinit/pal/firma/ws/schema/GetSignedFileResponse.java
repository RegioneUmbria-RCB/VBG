
package it.gruppoinit.pal.firma.ws.schema;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clientFileId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fileName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="isSigned" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="binaryData" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "clientFileId",
    "fileName",
    "isSigned",
    "binaryData"
})
@XmlRootElement(name = "GetSignedFileResponse")
public class GetSignedFileResponse {

    @XmlElement(required = true)
    protected String clientFileId;
    @XmlElement(required = true)
    protected String fileName;
    protected boolean isSigned;
    @XmlElement(required = true)
    @XmlMimeType("application/octet-stream")
    protected DataHandler binaryData;

    /**
     * Recupera il valore della propriet clientFileId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientFileId() {
        return clientFileId;
    }

    /**
     * Imposta il valore della propriet clientFileId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientFileId(String value) {
        this.clientFileId = value;
    }

    /**
     * Recupera il valore della propriet fileName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Imposta il valore della propriet fileName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Recupera il valore della propriet isSigned.
     * 
     */
    public boolean isIsSigned() {
        return isSigned;
    }

    /**
     * Imposta il valore della propriet isSigned.
     * 
     */
    public void setIsSigned(boolean value) {
        this.isSigned = value;
    }

    /**
     * Recupera il valore della propriet binaryData.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getBinaryData() {
        return binaryData;
    }

    /**
     * Imposta il valore della propriet binaryData.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setBinaryData(DataHandler value) {
        this.binaryData = value;
    }

}
