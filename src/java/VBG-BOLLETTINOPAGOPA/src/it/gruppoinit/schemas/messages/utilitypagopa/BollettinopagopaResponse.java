package it.gruppoinit.schemas.messages.utilitypagopa;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per anonymous complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="esito" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="binaryData" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "esito", "binaryData" })
@XmlRootElement(name = "BollettinopagopaResponse")
public class BollettinopagopaResponse {

    @XmlElement(required = true)
    protected String esito;
    @XmlMimeType("application/octet-stream")
    protected DataHandler binaryData;

    /**
     * Recupera il valore della proprietà esito.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEsito() {

	return esito;
    }

    /**
     * Imposta il valore della proprietà esito.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEsito(String value) {

	this.esito = value;
    }

    /**
     * Recupera il valore della proprietà binaryData.
     * 
     * @return possible object is {@link DataHandler }
     * 
     */
    public DataHandler getBinaryData() {

	return binaryData;
    }

    /**
     * Imposta il valore della proprietà binaryData.
     * 
     * @param value
     *            allowed object is {@link DataHandler }
     * 
     */
    public void setBinaryData(DataHandler value) {

	this.binaryData = value;
    }
}
