
package it.gruppoinit.pal.gp.backoffice.schemas.messages.posizionidebitorie;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType;


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
 *         &lt;element name="idDettaglioPosizioneDebitoria" type="{http://gruppoinit.it/sigepro/schemas/messages/posizionidebitorie}PosizioneInseritaPerOnereType" minOccurs="0"/>
 *         &lt;element name="esito" type="{http://gruppoinit.it/sigepro/schemas/messages/base}EsitoOperazioneType"/>
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
    "idDettaglioPosizioneDebitoria",
    "esito"
})
@XmlRootElement(name = "InserisciPosizioneDaOnereResponse")
public class InserisciPosizioneDaOnereResponse {

    protected PosizioneInseritaPerOnereType idDettaglioPosizioneDebitoria;
    @XmlElement(required = true)
    protected EsitoOperazioneType esito;

    /**
     * Gets the value of the idDettaglioPosizioneDebitoria property.
     * 
     * @return
     *     possible object is
     *     {@link PosizioneInseritaPerOnereType }
     *     
     */
    public PosizioneInseritaPerOnereType getIdDettaglioPosizioneDebitoria() {
        return idDettaglioPosizioneDebitoria;
    }

    /**
     * Sets the value of the idDettaglioPosizioneDebitoria property.
     * 
     * @param value
     *     allowed object is
     *     {@link PosizioneInseritaPerOnereType }
     *     
     */
    public void setIdDettaglioPosizioneDebitoria(PosizioneInseritaPerOnereType value) {
        this.idDettaglioPosizioneDebitoria = value;
    }

    /**
     * Gets the value of the esito property.
     * 
     * @return
     *     possible object is
     *     {@link EsitoOperazioneType }
     *     
     */
    public EsitoOperazioneType getEsito() {
        return esito;
    }

    /**
     * Sets the value of the esito property.
     * 
     * @param value
     *     allowed object is
     *     {@link EsitoOperazioneType }
     *     
     */
    public void setEsito(EsitoOperazioneType value) {
        this.esito = value;
    }

}
