
package it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for caricaTabellePraticaSUAP complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="caricaTabellePraticaSUAP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="soggetti" type="{http://ejb.protocollo.infocamere.it/}soggettoSUAP" minOccurs="0"/>
 *         &lt;element name="nomePratica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="suapXml" type="{http://ejb.protocollo.infocamere.it/}allegatoSUAPXml" minOccurs="0"/>
 *         &lt;element name="protocollo" type="{http://ejb.protocollo.infocamere.it/}protocollo" minOccurs="0"/>
 *         &lt;element name="cciaaProt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "caricaTabellePraticaSUAP", propOrder = {
    "soggetti",
    "nomePratica",
    "suapXml",
    "protocollo",
    "cciaaProt",
    "prid"
})
public class CaricaTabellePraticaSUAP {

    protected SoggettoSUAP soggetti;
    protected String nomePratica;
    protected AllegatoSUAPXml suapXml;
    protected Protocollo protocollo;
    protected String cciaaProt;
    protected String prid;

    /**
     * Gets the value of the soggetti property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoSUAP }
     *     
     */
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
    public void setSoggetti(SoggettoSUAP value) {
        this.soggetti = value;
    }

    /**
     * Gets the value of the nomePratica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomePratica() {
        return nomePratica;
    }

    /**
     * Sets the value of the nomePratica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomePratica(String value) {
        this.nomePratica = value;
    }

    /**
     * Gets the value of the suapXml property.
     * 
     * @return
     *     possible object is
     *     {@link AllegatoSUAPXml }
     *     
     */
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
    public void setSuapXml(AllegatoSUAPXml value) {
        this.suapXml = value;
    }

    /**
     * Gets the value of the protocollo property.
     * 
     * @return
     *     possible object is
     *     {@link Protocollo }
     *     
     */
    public Protocollo getProtocollo() {
        return protocollo;
    }

    /**
     * Sets the value of the protocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Protocollo }
     *     
     */
    public void setProtocollo(Protocollo value) {
        this.protocollo = value;
    }

    /**
     * Gets the value of the cciaaProt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCciaaProt() {
        return cciaaProt;
    }

    /**
     * Sets the value of the cciaaProt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCciaaProt(String value) {
        this.cciaaProt = value;
    }

    /**
     * Gets the value of the prid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrid() {
        return prid;
    }

    /**
     * Sets the value of the prid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrid(String value) {
        this.prid = value;
    }

}
