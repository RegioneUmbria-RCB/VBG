
package it.toscana.regione.suap.sem.types.procedimento;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;


/**
 * <p>Java class for comunicazioneType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="comunicazioneType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="destinatario" type="{http://www.suap.regione.toscana.it/sem/types/attori}attoreReteSuap"/>
 *         &lt;element name="oggetto" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}oggettoType"/>
 *         &lt;element name="corpo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="allegato" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}allegatoType" maxOccurs="unbounded"/>
 *         &lt;element name="attesaRisposta" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="giorni" type="{http://www.w3.org/2001/XMLSchema}duration" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comunicazioneType", propOrder = {
    "destinatario",
    "oggetto",
    "corpo",
    "allegato",
    "attesaRisposta"
})
public class ComunicazioneType {

    @XmlElement(required = true)
    protected String destinatario;
    @XmlElement(required = true)
    protected String oggetto;
    protected String corpo;
    @XmlElement(required = true)
    protected List<AllegatoType> allegato;
    protected ComunicazioneType.AttesaRisposta attesaRisposta;

    /**
     * Gets the value of the destinatario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinatario() {
        return destinatario;
    }

    /**
     * Sets the value of the destinatario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinatario(String value) {
        this.destinatario = value;
    }

    /**
     * Gets the value of the oggetto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOggetto() {
        return oggetto;
    }

    /**
     * Sets the value of the oggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOggetto(String value) {
        this.oggetto = value;
    }

    /**
     * Gets the value of the corpo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorpo() {
        return corpo;
    }

    /**
     * Sets the value of the corpo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorpo(String value) {
        this.corpo = value;
    }

    /**
     * Gets the value of the allegato property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allegato property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllegato().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AllegatoType }
     * 
     * 
     */
    public List<AllegatoType> getAllegato() {
        if (allegato == null) {
            allegato = new ArrayList<AllegatoType>();
        }
        return this.allegato;
    }

    /**
     * Gets the value of the attesaRisposta property.
     * 
     * @return
     *     possible object is
     *     {@link ComunicazioneType.AttesaRisposta }
     *     
     */
    public ComunicazioneType.AttesaRisposta getAttesaRisposta() {
        return attesaRisposta;
    }

    /**
     * Sets the value of the attesaRisposta property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComunicazioneType.AttesaRisposta }
     *     
     */
    public void setAttesaRisposta(ComunicazioneType.AttesaRisposta value) {
        this.attesaRisposta = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="giorni" type="{http://www.w3.org/2001/XMLSchema}duration" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class AttesaRisposta {

        @XmlAttribute
        protected Duration giorni;

        /**
         * Gets the value of the giorni property.
         * 
         * @return
         *     possible object is
         *     {@link Duration }
         *     
         */
        public Duration getGiorni() {
            return giorni;
        }

        /**
         * Sets the value of the giorni property.
         * 
         * @param value
         *     allowed object is
         *     {@link Duration }
         *     
         */
        public void setGiorni(Duration value) {
            this.giorni = value;
        }

    }

}
