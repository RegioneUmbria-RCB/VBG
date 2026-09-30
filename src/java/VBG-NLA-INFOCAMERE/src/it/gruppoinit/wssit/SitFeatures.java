
package it.gruppoinit.wssit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SitFeatures complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SitFeatures">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VisualizzazioniFrontoffice" type="{http://init.sigepro.it}ArrayOfBaseDtoOfTipoVisualizzazioneString" minOccurs="0"/>
 *         &lt;element name="VisualizzazioniBackoffice" type="{http://init.sigepro.it}ArrayOfBaseDtoOfTipoVisualizzazioneString" minOccurs="0"/>
 *         &lt;element name="CampiGestiti" type="{http://init.sigepro.it}ArrayOfString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SitFeatures", propOrder = {
    "visualizzazioniFrontoffice",
    "visualizzazioniBackoffice",
    "campiGestiti"
})
public class SitFeatures {

    @XmlElement(name = "VisualizzazioniFrontoffice")
    protected ArrayOfBaseDtoOfTipoVisualizzazioneString visualizzazioniFrontoffice;
    @XmlElement(name = "VisualizzazioniBackoffice")
    protected ArrayOfBaseDtoOfTipoVisualizzazioneString visualizzazioniBackoffice;
    @XmlElement(name = "CampiGestiti")
    protected ArrayOfString campiGestiti;

    /**
     * Gets the value of the visualizzazioniFrontoffice property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfBaseDtoOfTipoVisualizzazioneString }
     *     
     */
    public ArrayOfBaseDtoOfTipoVisualizzazioneString getVisualizzazioniFrontoffice() {
        return visualizzazioniFrontoffice;
    }

    /**
     * Sets the value of the visualizzazioniFrontoffice property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfBaseDtoOfTipoVisualizzazioneString }
     *     
     */
    public void setVisualizzazioniFrontoffice(ArrayOfBaseDtoOfTipoVisualizzazioneString value) {
        this.visualizzazioniFrontoffice = value;
    }

    /**
     * Gets the value of the visualizzazioniBackoffice property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfBaseDtoOfTipoVisualizzazioneString }
     *     
     */
    public ArrayOfBaseDtoOfTipoVisualizzazioneString getVisualizzazioniBackoffice() {
        return visualizzazioniBackoffice;
    }

    /**
     * Sets the value of the visualizzazioniBackoffice property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfBaseDtoOfTipoVisualizzazioneString }
     *     
     */
    public void setVisualizzazioniBackoffice(ArrayOfBaseDtoOfTipoVisualizzazioneString value) {
        this.visualizzazioniBackoffice = value;
    }

    /**
     * Gets the value of the campiGestiti property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfString }
     *     
     */
    public ArrayOfString getCampiGestiti() {
        return campiGestiti;
    }

    /**
     * Sets the value of the campiGestiti property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfString }
     *     
     */
    public void setCampiGestiti(ArrayOfString value) {
        this.campiGestiti = value;
    }

}
