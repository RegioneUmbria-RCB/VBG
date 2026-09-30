
package it.gruppoinit.firmadigitaleweb.services.messages;

import java.math.BigInteger;
import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dataBean complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dataBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="chiavePubblica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneFirmato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneRevocato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrizioneValido" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="emittente" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fileInChiaro" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="firmatario" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="firmato" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="indirizzoCRL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nomeAlgoritmo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numeroSeriale" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="revocato" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="tipoAlgoritmo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valido" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="validoAl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="validoDa" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="verificaFirma" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataBean", propOrder = {
    "chiavePubblica",
    "descrizioneFirmato",
    "descrizioneRevocato",
    "descrizioneValido",
    "emittente",
    "fileInChiaro",
    "firmatario",
    "firmato",
    "indirizzoCRL",
    "nomeAlgoritmo",
    "numeroSeriale",
    "revocato",
    "tipoAlgoritmo",
    "valido",
    "validoAl",
    "validoDa",
    "verificaFirma"
})
public class DataBean {

    @XmlElement(required = true, nillable = true)
    protected String chiavePubblica;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneFirmato;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneRevocato;
    @XmlElement(required = true, nillable = true)
    protected String descrizioneValido;
    @XmlElement(required = true, nillable = true)
    protected String emittente;
    @XmlElement(required = true)
    @XmlMimeType("application/octet-stream")
    protected DataHandler fileInChiaro;
    @XmlElement(required = true, nillable = true)
    protected String firmatario;
    protected boolean firmato;
    @XmlElement(required = true, nillable = true)
    protected String indirizzoCRL;
    @XmlElement(required = true, nillable = true)
    protected String nomeAlgoritmo;
    @XmlElement(required = true, nillable = true)
    protected BigInteger numeroSeriale;
    protected boolean revocato;
    @XmlElement(required = true, nillable = true)
    protected String tipoAlgoritmo;
    protected boolean valido;
    @XmlElement(required = true, nillable = true)
    protected String validoAl;
    @XmlElement(required = true, nillable = true)
    protected String validoDa;
    protected boolean verificaFirma;

    /**
     * Gets the value of the chiavePubblica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChiavePubblica() {
        return chiavePubblica;
    }

    /**
     * Sets the value of the chiavePubblica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChiavePubblica(String value) {
        this.chiavePubblica = value;
    }

    /**
     * Gets the value of the descrizioneFirmato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneFirmato() {
        return descrizioneFirmato;
    }

    /**
     * Sets the value of the descrizioneFirmato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneFirmato(String value) {
        this.descrizioneFirmato = value;
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

    /**
     * Gets the value of the emittente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmittente() {
        return emittente;
    }

    /**
     * Sets the value of the emittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmittente(String value) {
        this.emittente = value;
    }

    /**
     * Gets the value of the fileInChiaro property.
     * 
     * @return
     *     possible object is
     *     {@link DataHandler }
     *     
     */
    public DataHandler getFileInChiaro() {
        return fileInChiaro;
    }

    /**
     * Sets the value of the fileInChiaro property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataHandler }
     *     
     */
    public void setFileInChiaro(DataHandler value) {
        this.fileInChiaro = value;
    }

    /**
     * Gets the value of the firmatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirmatario() {
        return firmatario;
    }

    /**
     * Sets the value of the firmatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirmatario(String value) {
        this.firmatario = value;
    }

    /**
     * Gets the value of the firmato property.
     * 
     */
    public boolean isFirmato() {
        return firmato;
    }

    /**
     * Sets the value of the firmato property.
     * 
     */
    public void setFirmato(boolean value) {
        this.firmato = value;
    }

    /**
     * Gets the value of the indirizzoCRL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoCRL() {
        return indirizzoCRL;
    }

    /**
     * Sets the value of the indirizzoCRL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoCRL(String value) {
        this.indirizzoCRL = value;
    }

    /**
     * Gets the value of the nomeAlgoritmo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeAlgoritmo() {
        return nomeAlgoritmo;
    }

    /**
     * Sets the value of the nomeAlgoritmo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeAlgoritmo(String value) {
        this.nomeAlgoritmo = value;
    }

    /**
     * Gets the value of the numeroSeriale property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumeroSeriale() {
        return numeroSeriale;
    }

    /**
     * Sets the value of the numeroSeriale property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumeroSeriale(BigInteger value) {
        this.numeroSeriale = value;
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
     * Gets the value of the validoAl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidoAl() {
        return validoAl;
    }

    /**
     * Sets the value of the validoAl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidoAl(String value) {
        this.validoAl = value;
    }

    /**
     * Gets the value of the validoDa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidoDa() {
        return validoDa;
    }

    /**
     * Sets the value of the validoDa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidoDa(String value) {
        this.validoDa = value;
    }

    /**
     * Gets the value of the verificaFirma property.
     * 
     */
    public boolean isVerificaFirma() {
        return verificaFirma;
    }

    /**
     * Sets the value of the verificaFirma property.
     * 
     */
    public void setVerificaFirma(boolean value) {
        this.verificaFirma = value;
    }

}
