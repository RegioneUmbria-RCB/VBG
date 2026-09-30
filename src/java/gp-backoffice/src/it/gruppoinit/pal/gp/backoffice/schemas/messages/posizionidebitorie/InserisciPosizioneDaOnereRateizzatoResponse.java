
package it.gruppoinit.pal.gp.backoffice.schemas.messages.posizionidebitorie;

import java.util.ArrayList;
import java.util.List;

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
 *         &lt;element name="idDettaglioPosizioneDebitoria" type="{http://gruppoinit.it/sigepro/schemas/messages/posizionidebitorie}PosizioneInseritaPerOnereType" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlRootElement(name = "InserisciPosizioneDaOnereRateizzatoResponse")
public class InserisciPosizioneDaOnereRateizzatoResponse {

    protected List<PosizioneInseritaPerOnereType> idDettaglioPosizioneDebitoria;
    @XmlElement(required = true)
    protected it.gruppoinit.pal.gp.backoffice.schemas.messages.base.EsitoOperazioneType esito;

    /**
     * Gets the value of the idDettaglioPosizioneDebitoria property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the idDettaglioPosizioneDebitoria property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIdDettaglioPosizioneDebitoria().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PosizioneInseritaPerOnereType }
     * 
     * 
     */
    public List<PosizioneInseritaPerOnereType> getIdDettaglioPosizioneDebitoria() {
        if (idDettaglioPosizioneDebitoria == null) {
            idDettaglioPosizioneDebitoria = new ArrayList<PosizioneInseritaPerOnereType>();
        }
        return this.idDettaglioPosizioneDebitoria;
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
