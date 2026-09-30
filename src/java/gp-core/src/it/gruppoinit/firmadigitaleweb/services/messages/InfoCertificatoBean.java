
package it.gruppoinit.firmadigitaleweb.services.messages;

import java.math.BigInteger;
import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for infoCertificatoBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="infoCertificatoBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addressCRL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataFile" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="issuerDN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="publicKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="revocato" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="serialNumber" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="signatureAlgoritmoName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subject" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoAlgoritmo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valido" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="validoDa" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="validoFinoAl" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="descrizioneRevocato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneValido" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "infoCertificatoBean", propOrder = {
    "addressCRL",
    "dataFile",
    "issuerDN",
    "publicKey",
    "revocato",
    "serialNumber",
    "signatureAlgoritmoName",
    "subject",
    "tipoAlgoritmo",
    "valido",
    "validoDa",
    "validoFinoAl",
    "descrizioneRevocato",
    "descrizioneValido"
})
public class InfoCertificatoBean {

    @XmlElement(required = true, nillable = true)
    protected String addressCRL;
    @XmlElement(required = true)
    @XmlMimeType("application/octet-stream")
    protected DataHandler dataFile;
    @XmlElement(required = true, nillable = true)
    protected String issuerDN;
    @XmlElement(required = true, nillable = true)
    protected String publicKey;
    protected boolean revocato;
    @XmlElement(required = true, nillable = true)
    protected BigInteger serialNumber;
    @XmlElement(required = true, nillable = true)
    protected String signatureAlgoritmoName;
    @XmlElement(required = true, nillable = true)
    protected String subject;
    @XmlElement(required = true, nillable = true)
    protected String tipoAlgoritmo;
    protected boolean valido;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar validoDa;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar validoFinoAl;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneRevocato;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneValido;

    /**
     * Gets the value of the addressCRL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressCRL() {
        return addressCRL;
    }

    /**
     * Sets the value of the addressCRL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressCRL(String value) {
        this.addressCRL = value;
    }

    /**
     * Gets the value of the dataFile property.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getDataFile() {
        return dataFile;
    }

    /**
     * Sets the value of the dataFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setDataFile(DataHandler value) {
        this.dataFile = value;
    }

    /**
     * Gets the value of the issuerDN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuerDN() {
        return issuerDN;
    }

    /**
     * Sets the value of the issuerDN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuerDN(String value) {
        this.issuerDN = value;
    }

    /**
     * Gets the value of the publicKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublicKey() {
        return publicKey;
    }

    /**
     * Sets the value of the publicKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublicKey(String value) {
        this.publicKey = value;
    }

    /**
     * Gets the value of the revocato property.
     * 
     */
    public boolean isRevocato() {
        return revocato;
    }

    /**
     * Sets the value of the revocato property.
     * 
     */
    public void setRevocato(boolean value) {
        this.revocato = value;
    }

    /**
     * Gets the value of the serialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSerialNumber(BigInteger value) {
        this.serialNumber = value;
    }

    /**
     * Gets the value of the signatureAlgoritmoName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSignatureAlgoritmoName() {
        return signatureAlgoritmoName;
    }

    /**
     * Sets the value of the signatureAlgoritmoName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignatureAlgoritmoName(String value) {
        this.signatureAlgoritmoName = value;
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubject(String value) {
        this.subject = value;
    }

    /**
     * Gets the value of the tipoAlgoritmo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoAlgoritmo() {
        return tipoAlgoritmo;
    }

    /**
     * Sets the value of the tipoAlgoritmo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoAlgoritmo(String value) {
        this.tipoAlgoritmo = value;
    }

    /**
     * Gets the value of the valido property.
     * 
     */
    public boolean isValido() {
        return valido;
    }

    /**
     * Sets the value of the valido property.
     * 
     */
    public void setValido(boolean value) {
        this.valido = value;
    }

    /**
     * Gets the value of the validoDa property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidoDa() {
        return validoDa;
    }

    /**
     * Sets the value of the validoDa property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidoDa(XMLGregorianCalendar value) {
        this.validoDa = value;
    }

    /**
     * Gets the value of the validoFinoAl property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValidoFinoAl() {
        return validoFinoAl;
    }

    /**
     * Sets the value of the validoFinoAl property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValidoFinoAl(XMLGregorianCalendar value) {
        this.validoFinoAl = value;
    }

    /**
     * Gets the value of the descrizioneRevocato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneRevocato() {
        return descrizioneRevocato;
    }

    /**
     * Sets the value of the descrizioneRevocato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneRevocato(String value) {
        this.descrizioneRevocato = value;
    }

    /**
     * Gets the value of the descrizioneValido property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneValido() {
        return descrizioneValido;
    }

    /**
     * Sets the value of the descrizioneValido property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneValido(String value) {
        this.descrizioneValido = value;
    }

}
