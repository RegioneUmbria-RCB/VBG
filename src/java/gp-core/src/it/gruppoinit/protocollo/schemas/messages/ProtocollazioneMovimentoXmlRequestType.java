
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProtocollazioneMovimentoXmlRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProtocollazioneMovimentoXmlRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodiceMovimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Dati" type="{http://it.gruppoinit/Protocollazione}DatiRequestType" minOccurs="0"/>
 *         &lt;element name="Metadati" type="{http://it.gruppoinit/Protocollazione}ArrayOfMetadatoType" minOccurs="0"/>
 *         &lt;element name="Source" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProtocollazioneMovimentoXmlRequestType", propOrder = {
    "codiceMovimento",
    "dati",
    "metadati",
    "source",
    "token"
})
public class ProtocollazioneMovimentoXmlRequestType {

    @XmlElement(name = "CodiceMovimento", nillable = true)
    protected String codiceMovimento;
    @XmlElement(name = "Dati", nillable = true)
    protected DatiRequestType dati;
    @XmlElement(name = "Metadati", nillable = true)
    protected ArrayOfMetadatoType metadati;
    @XmlElement(name = "Source")
    protected Integer source;
    @XmlElement(name = "Token", nillable = true)
    protected String token;

    /**
     * Gets the value of the codiceMovimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceMovimento() {
        return codiceMovimento;
    }

    /**
     * Sets the value of the codiceMovimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceMovimento(String value) {
        this.codiceMovimento = value;
    }

    /**
     * Gets the value of the dati property.
     * 
     * @return
     *     possible object is
     *     {@link DatiRequestType }
     *     
     */
    public DatiRequestType getDati() {
        return dati;
    }

    /**
     * Sets the value of the dati property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiRequestType }
     *     
     */
    public void setDati(DatiRequestType value) {
        this.dati = value;
    }

    /**
     * Gets the value of the metadati property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMetadatoType }
     *     
     */
    public ArrayOfMetadatoType getMetadati() {
        return metadati;
    }

    /**
     * Sets the value of the metadati property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMetadatoType }
     *     
     */
    public void setMetadati(ArrayOfMetadatoType value) {
        this.metadati = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSource(Integer value) {
        this.source = value;
    }

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

}
