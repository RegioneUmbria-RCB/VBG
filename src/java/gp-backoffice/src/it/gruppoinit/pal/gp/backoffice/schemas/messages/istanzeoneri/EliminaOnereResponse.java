package it.gruppoinit.pal.gp.backoffice.schemas.messages.istanzeoneri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esitoOperazione" type="{http://gruppoinit.it/sigepro/schemas/messages/base}EsitoOperazioneType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "esitoOperazione" })
@XmlRootElement(name = "EliminaOnereResponse")
public class EliminaOnereResponse {

    @XmlElement(required = true)
    protected EsitoOperazioneType esitoOperazione;

    /**
     * Gets the value of the esitoOperazione property.
     * 
     * @return possible object is {@link EsitoOperazioneType }
     * 
     */
    public EsitoOperazioneType getEsitoOperazione() {

	return esitoOperazione;
    }

    /**
     * Sets the value of the esitoOperazione property.
     * 
     * @param value
     *            allowed object is {@link EsitoOperazioneType }
     * 
     */
    public void setEsitoOperazione(EsitoOperazioneType value) {

	this.esitoOperazione = value;
    }
}
