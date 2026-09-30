
package it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for comunicazioneREAStd complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="comunicazioneREAStd">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="soggetti" type="{http://ejb.protocollo.infocamere.it/}soggettoSUAP" minOccurs="0"/>
 *         &lt;element name="pridPratica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="suapReaXml" type="{http://ejb.protocollo.infocamere.it/}allegatoSUAPReaXml" minOccurs="0"/>
 *         &lt;element name="suapXml" type="{http://ejb.protocollo.infocamere.it/}allegatoSUAPXml" minOccurs="0"/>
 *         &lt;element name="allegati" type="{http://ejb.protocollo.infocamere.it/}allegatoSUAP" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="visuraXML" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="visuraPDF" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comunicazioneREAStd", propOrder = {
    "soggetti",
    "pridPratica",
    "suapReaXml",
    "suapXml",
    "allegati",
    "visuraXML",
    "visuraPDF"
})
public class ComunicazioneREAStd implements IComunicazioneREA {

    protected SoggettoSUAP soggetti;
    protected String pridPratica;
    protected AllegatoSUAPReaXml suapReaXml;
    protected AllegatoSUAPXml suapXml;
    protected List<AllegatoSUAP> allegati;
    protected boolean visuraXML;
    protected boolean visuraPDF;

    /**
     * Gets the value of the soggetti property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoSUAP }
     *     
     */
    @Override
    public SoggettoSUAP getSoggetti() {
        return soggetti;
    }

    /**
     * Sets the value of the soggetti property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoSUAP }
     *     
     */
    @Override
    public void setSoggetti(SoggettoSUAP value) {
        this.soggetti = value;
    }

    /**
     * Gets the value of the pridPratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Override
    public String getPridPratica() {
        return pridPratica;
    }

    /**
     * Sets the value of the pridPratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    @Override
    public void setPridPratica(String value) {
        this.pridPratica = value;
    }

    /**
     * Gets the value of the suapReaXml property.
     * 
     * @return
     *     possible object is
     *     {@link AllegatoSUAPReaXml }
     *     
     */
    @Override
    public AllegatoSUAPReaXml getSuapReaXml() {
        return suapReaXml;
    }

    /**
     * Sets the value of the suapReaXml property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllegatoSUAPReaXml }
     *     
     */
    @Override
    public void setSuapReaXml(AllegatoSUAPReaXml value) {
        this.suapReaXml = value;
    }

    /**
     * Gets the value of the suapXml property.
     * 
     * @return
     *     possible object is
     *     {@link AllegatoSUAPXml }
     *     
     */
    @Override
    public AllegatoSUAPXml getSuapXml() {
        return suapXml;
    }

    /**
     * Sets the value of the suapXml property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllegatoSUAPXml }
     *     
     */
    @Override
    public void setSuapXml(AllegatoSUAPXml value) {
        this.suapXml = value;
    }

    /**
     * Gets the value of the allegati property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allegati property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllegati().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AllegatoSUAP }
     * 
     * 
     */
    @Override
    public List<AllegatoSUAP> getAllegati() {
        if (allegati == null) {
            allegati = new ArrayList<AllegatoSUAP>();
        }
        return this.allegati;
    }

    /**
     * Gets the value of the visuraXML property.
     * 
     */
    @Override
    public boolean isVisuraXML() {
        return visuraXML;
    }

    /**
     * Sets the value of the visuraXML property.
     * 
     */
    @Override
    public void setVisuraXML(boolean value) {
        this.visuraXML = value;
    }

    /**
     * Gets the value of the visuraPDF property.
     * 
     */
    @Override
    public boolean isVisuraPDF() {
        return visuraPDF;
    }

    /**
     * Sets the value of the visuraPDF property.
     * 
     */
    @Override
    public void setVisuraPDF(boolean value) {
        this.visuraPDF = value;
    }

}
