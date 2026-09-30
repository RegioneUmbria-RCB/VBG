
package it.toscana.regione.suap.sem.types.procedimento;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for confermaRicezioneRespType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="confermaRicezioneRespType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esito" type="{http://www.suap.regione.toscana.it/sem/types/procedimento}esitoConfermaRicezioneType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="msgErrore" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "confermaRicezioneRespType", propOrder = {
    "esito"
})
public class ConfermaRicezioneRespType {

    @XmlElement(required = true)
    protected EsitoConfermaRicezioneType esito;
    @XmlAttribute
    protected String msgErrore;

    /**
     * Gets the value of the esito property.
     * 
     * @return
     *     possible object is
     *     {@link EsitoConfermaRicezioneType }
     *     
     */
    public EsitoConfermaRicezioneType getEsito() {
        return esito;
    }

    /**
     * Sets the value of the esito property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsitoConfermaRicezioneType }
     *     
     */
    public void setEsito(EsitoConfermaRicezioneType value) {
        this.esito = value;
    }

    /**
     * Gets the value of the msgErrore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgErrore() {
        return msgErrore;
    }

    /**
     * Sets the value of the msgErrore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgErrore(String value) {
        this.msgErrore = value;
    }

}
