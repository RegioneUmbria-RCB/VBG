package it.gov.impresainungiorno.schema.suap.ente;

import it.gov.impresainungiorno.schema.suap.pratica.BaseAllegatoSUAP;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttachmentRef;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per AllegatoCooperazione complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AllegatoCooperazione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.impresainungiorno.gov.it/schema/suap/pratica}BaseAllegatoSUAP"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="embedded-file-ref" type="{http://ws-i.org/profiles/basic/1.1/xsd}swaRef" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="nome-file" use="required" type="{http://www.impresainungiorno.gov.it/schema/suap/ente}TipoFileAllegatoCooperazione" /&gt;
 *       &lt;attribute name="cod" type="{http://www.impresainungiorno.gov.it/schema/base}Stringa" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AllegatoCooperazione", propOrder = { "embeddedFileRef" })
public class AllegatoCooperazione extends BaseAllegatoSUAP {

    @XmlElement(name = "embedded-file-ref", type = String.class)
    @XmlAttachmentRef
    @XmlSchemaType(name = "anyURI")
    protected DataHandler embeddedFileRef;
    @XmlAttribute(name = "nome-file", required = true)
    protected String nomeFile;
    @XmlAttribute(name = "cod")
    protected String cod;

    /**
     * Recupera il valore della proprietà embeddedFileRef.
     * 
     * @return possible object is {@link String }
     * 
     */
    public DataHandler getEmbeddedFileRef() {

	return embeddedFileRef;
    }

    /**
     * Imposta il valore della proprietà embeddedFileRef.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEmbeddedFileRef(DataHandler value) {

	this.embeddedFileRef = value;
    }

    /**
     * Recupera il valore della proprietà nomeFile.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNomeFile() {

	return nomeFile;
    }

    /**
     * Imposta il valore della proprietà nomeFile.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNomeFile(String value) {

	this.nomeFile = value;
    }

    /**
     * Recupera il valore della proprietà cod.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCod() {

	return cod;
    }

    /**
     * Imposta il valore della proprietà cod.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCod(String value) {

	this.cod = value;
    }
}
