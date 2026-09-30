
package it.gruppoinit.pal.gp.backoffice.schemas.messages.nlapec.gestionemail;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrizioneEnte" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="alias" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="avviso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="listaDettagli" type="{http://gruppoinit.it/nlapec}DettaglioReportType"/>
 *         &lt;element name="idaccount" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
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
    "descrizioneEnte",
    "alias",
    "errore",
    "avviso",
    "listaDettagli",
    "idaccount"
})
@XmlRootElement(name = "ProcessaMessaggiResponse")
public class ProcessaMessaggiResponse {

    @XmlElement(required = true)
    protected String descrizioneEnte;
    @XmlElement(required = true)
    protected String alias;
    protected String errore;
    protected String avviso;
    @XmlElement(required = true)
    protected DettaglioReportType listaDettagli;
    protected BigInteger idaccount;

    /**
     * Gets the value of the descrizioneEnte property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneEnte() {
        return descrizioneEnte;
    }

    /**
     * Sets the value of the descrizioneEnte property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneEnte(String value) {
        this.descrizioneEnte = value;
    }

    /**
     * Gets the value of the alias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the value of the alias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlias(String value) {
        this.alias = value;
    }

    /**
     * Gets the value of the errore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrore() {
        return errore;
    }

    /**
     * Sets the value of the errore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrore(String value) {
        this.errore = value;
    }

    /**
     * Gets the value of the avviso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvviso() {
        return avviso;
    }

    /**
     * Sets the value of the avviso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvviso(String value) {
        this.avviso = value;
    }

    /**
     * Gets the value of the listaDettagli property.
     * 
     * @return
     *     possible object is
     *     {@link DettaglioReportType }
     *     
     */
    public DettaglioReportType getListaDettagli() {
        return listaDettagli;
    }

    /**
     * Sets the value of the listaDettagli property.
     * 
     * @param value
     *     allowed object is
     *     {@link DettaglioReportType }
     *     
     */
    public void setListaDettagli(DettaglioReportType value) {
        this.listaDettagli = value;
    }

    /**
     * Gets the value of the idaccount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getIdaccount() {
        return idaccount;
    }

    /**
     * Sets the value of the idaccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setIdaccount(BigInteger value) {
        this.idaccount = value;
    }

}
