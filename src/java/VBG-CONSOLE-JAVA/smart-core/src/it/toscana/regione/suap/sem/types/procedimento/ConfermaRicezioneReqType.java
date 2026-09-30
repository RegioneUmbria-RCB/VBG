
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for confermaRicezioneReqType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="confermaRicezioneReqType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mittente" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}soggettoReteSuapType"/>
 *         &lt;element name="idMessaggio" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}stringaNonVuota"/>
 *         &lt;element name="ricezione" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="errore" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}erroreDestinatarioType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "confermaRicezioneReqType", propOrder = {
    "mittente",
    "idMessaggio",
    "ricezione",
    "errore"
})
public class ConfermaRicezioneReqType {

    @XmlElement(required = true)
    protected SoggettoReteSuapType mittente;
    @XmlElement(required = true)
    protected String idMessaggio;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar ricezione;
    protected ErroreDestinatarioType errore;

    /**
     * Gets the value of the mittente property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoReteSuapType }
     *     
     */
    public SoggettoReteSuapType getMittente() {
        return mittente;
    }

    /**
     * Sets the value of the mittente property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoReteSuapType }
     *     
     */
    public void setMittente(SoggettoReteSuapType value) {
        this.mittente = value;
    }

    /**
     * Gets the value of the idMessaggio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdMessaggio() {
        return idMessaggio;
    }

    /**
     * Sets the value of the idMessaggio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdMessaggio(String value) {
        this.idMessaggio = value;
    }

    /**
     * Gets the value of the ricezione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRicezione() {
        return ricezione;
    }

    /**
     * Sets the value of the ricezione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRicezione(XMLGregorianCalendar value) {
        this.ricezione = value;
    }

    /**
     * Gets the value of the errore property.
     * 
     * @return
     *     possible object is
     *     {@link ErroreDestinatarioType }
     *     
     */
    public ErroreDestinatarioType getErrore() {
        return errore;
    }

    /**
     * Sets the value of the errore property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErroreDestinatarioType }
     *     
     */
    public void setErrore(ErroreDestinatarioType value) {
        this.errore = value;
    }

}
