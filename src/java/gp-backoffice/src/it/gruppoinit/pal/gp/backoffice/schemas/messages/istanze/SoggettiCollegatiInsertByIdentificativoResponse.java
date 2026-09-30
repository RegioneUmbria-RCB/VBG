
package it.gruppoinit.pal.gp.backoffice.schemas.messages.istanze;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType;

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
 *         &lt;element name="codiceSoggetto" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="nominativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EsitoOperazioneType" type="{http://gruppoinit.it/sigepro/schemas/messages/base}EsitoOperazioneType"/>
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
    "codiceSoggetto",
    "nominativo",
    "esitoOperazioneType"
})
@XmlRootElement(name = "SoggettiCollegatiInsertByIdentificativoResponse", namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze")
public class SoggettiCollegatiInsertByIdentificativoResponse {

    @XmlElement(namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze")
    protected BigInteger codiceSoggetto;
    @XmlElement(namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze")
    protected String nominativo;
    @XmlElement(name = "EsitoOperazioneType", namespace = "http://gruppoinit.it/sigepro/schemas/messages/istanze", required = true)
    protected EsitoOperazioneType esitoOperazioneType;

    /**
     * Gets the value of the codiceSoggetto property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCodiceSoggetto() {
        return codiceSoggetto;
    }

    /**
     * Sets the value of the codiceSoggetto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCodiceSoggetto(BigInteger value) {
        this.codiceSoggetto = value;
    }

    /**
     * Gets the value of the nominativo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNominativo() {
        return nominativo;
    }

    /**
     * Sets the value of the nominativo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNominativo(String value) {
        this.nominativo = value;
    }

    /**
     * Gets the value of the esitoOperazioneType property.
     * 
     * @return
     *     possible object is
     *     {@link EsitoOperazioneType }
     *     
     */
    public EsitoOperazioneType getEsitoOperazioneType() {
        return esitoOperazioneType;
    }

    /**
     * Sets the value of the esitoOperazioneType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsitoOperazioneType }
     *     
     */
    public void setEsitoOperazioneType(EsitoOperazioneType value) {
        this.esitoOperazioneType = value;
    }

}
