
package it.gruppoinit.pal.gp.backoffice.schemas.messages.autorizzazioniaccessi;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for OperazioneAutorizzazioneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OperazioneAutorizzazioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceIstanza" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="numeroIstanza" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataIstanza" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="numeroProtocollo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataProtocollo" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="stato" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataAutorizzazione" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperazioneAutorizzazioneType", propOrder = {
    "codiceIstanza",
    "numeroIstanza",
    "dataIstanza",
    "numeroProtocollo",
    "dataProtocollo",
    "tipo",
    "stato",
    "dataAutorizzazione"
})
public class OperazioneAutorizzazioneType {

    @XmlElement(required = true)
    protected BigInteger codiceIstanza;
    @XmlElement(required = true)
    protected String numeroIstanza;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataIstanza;
    @XmlElement(required = true)
    protected String numeroProtocollo;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataProtocollo;
    @XmlElement(required = true)
    protected String tipo;
    @XmlElement(required = true)
    protected String stato;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataAutorizzazione;

    /**
     * Gets the value of the codiceIstanza property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodiceIstanza() {
        return codiceIstanza;
    }

    /**
     * Sets the value of the codiceIstanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodiceIstanza(BigInteger value) {
        this.codiceIstanza = value;
    }

    /**
     * Gets the value of the numeroIstanza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroIstanza() {
        return numeroIstanza;
    }

    /**
     * Sets the value of the numeroIstanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroIstanza(String value) {
        this.numeroIstanza = value;
    }

    /**
     * Gets the value of the dataIstanza property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataIstanza() {
        return dataIstanza;
    }

    /**
     * Sets the value of the dataIstanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataIstanza(XMLGregorianCalendar value) {
        this.dataIstanza = value;
    }

    /**
     * Gets the value of the numeroProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProtocollo() {
        return numeroProtocollo;
    }

    /**
     * Sets the value of the numeroProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProtocollo(String value) {
        this.numeroProtocollo = value;
    }

    /**
     * Gets the value of the dataProtocollo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataProtocollo() {
        return dataProtocollo;
    }

    /**
     * Sets the value of the dataProtocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataProtocollo(XMLGregorianCalendar value) {
        this.dataProtocollo = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the stato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStato() {
        return stato;
    }

    /**
     * Sets the value of the stato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStato(String value) {
        this.stato = value;
    }

    /**
     * Gets the value of the dataAutorizzazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataAutorizzazione() {
        return dataAutorizzazione;
    }

    /**
     * Sets the value of the dataAutorizzazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataAutorizzazione(XMLGregorianCalendar value) {
        this.dataAutorizzazione = value;
    }

}
