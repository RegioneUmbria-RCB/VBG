
package it.gruppoinit.protocollo.schemas.messages;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DatiProtocolloAnnullatoResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiProtocolloAnnullatoResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Annullato" type="{http://it.gruppoinit/Protocollazione}EnumAnnullatoType" minOccurs="0"/>
 *         &lt;element name="MotivoAnnullamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NoteAnnullamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Errore" type="{http://it.gruppoinit/Protocollazione}ErroreProtocolloType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiProtocolloAnnullatoResponseType", propOrder = {
    "annullato",
    "motivoAnnullamento",
    "noteAnnullamento",
    "errore"
})
public class DatiProtocolloAnnullatoResponseType {

    @XmlElement(name = "Annullato")
    protected EnumAnnullatoType annullato;
    @XmlElement(name = "MotivoAnnullamento", nillable = true)
    protected String motivoAnnullamento;
    @XmlElement(name = "NoteAnnullamento", nillable = true)
    protected String noteAnnullamento;
    @XmlElement(name = "Errore", nillable = true)
    protected ErroreProtocolloType errore;

    /**
     * Gets the value of the annullato property.
     * 
     * @return
     *     possible object is
     *     {@link EnumAnnullatoType }
     *     
     */
    public EnumAnnullatoType getAnnullato() {
        return annullato;
    }

    /**
     * Sets the value of the annullato property.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumAnnullatoType }
     *     
     */
    public void setAnnullato(EnumAnnullatoType value) {
        this.annullato = value;
    }

    /**
     * Gets the value of the motivoAnnullamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivoAnnullamento() {
        return motivoAnnullamento;
    }

    /**
     * Sets the value of the motivoAnnullamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivoAnnullamento(String value) {
        this.motivoAnnullamento = value;
    }

    /**
     * Gets the value of the noteAnnullamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNoteAnnullamento() {
        return noteAnnullamento;
    }

    /**
     * Sets the value of the noteAnnullamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNoteAnnullamento(String value) {
        this.noteAnnullamento = value;
    }

    /**
     * Gets the value of the errore property.
     * 
     * @return
     *     possible object is
     *     {@link ErroreProtocolloType }
     *     
     */
    public ErroreProtocolloType getErrore() {
        return errore;
    }

    /**
     * Sets the value of the errore property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErroreProtocolloType }
     *     
     */
    public void setErrore(ErroreProtocolloType value) {
        this.errore = value;
    }

}
