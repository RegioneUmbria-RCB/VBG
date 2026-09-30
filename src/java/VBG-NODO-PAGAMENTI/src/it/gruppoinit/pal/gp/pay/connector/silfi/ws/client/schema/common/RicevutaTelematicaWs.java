
package it.gruppoinit.pal.gp.pay.connector.silfi.ws.client.schema.common;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Restituisce l'xml della
 * 				Ricevuta Telematica e, se disponibile, il blob della Ricevuta
 * 				Telematica firmata
 * 			
 * 
 * <p>Classe Java per ricevutaTelematicaWs complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ricevutaTelematicaWs"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="rtXml"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;minLength value="1"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="rtFirmata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricevutaTelematicaWs", propOrder = {
    "rtXml",
    "rtFirmata"
})
public class RicevutaTelematicaWs {

    @XmlElement(required = true)
    protected String rtXml;
    protected String rtFirmata;

    /**
     * Recupera il valore della proprietà rtXml.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRtXml() {
        return rtXml;
    }

    /**
     * Imposta il valore della proprietà rtXml.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRtXml(String value) {
        this.rtXml = value;
    }

    /**
     * Recupera il valore della proprietà rtFirmata.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRtFirmata() {
        return rtFirmata;
    }

    /**
     * Imposta il valore della proprietà rtFirmata.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRtFirmata(String value) {
        this.rtFirmata = value;
    }

}
