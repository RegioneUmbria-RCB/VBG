
package it.gruppoinit.impresainungiorno.schema.suap.ri.dettaglio;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for rispostaREAStd complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="rispostaREAStd">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allegati" type="{http://ejb.protocollo.infocamere.it/}allegatoSUAP" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="dettaglioEsitoRichiesta" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esitoRichiesta" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="protocollo" type="{http://ejb.protocollo.infocamere.it/}protocolloRI" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "rispostaREAStd", propOrder = {
    "allegati",
    "dettaglioEsitoRichiesta",
    "esitoRichiesta",
    "protocollo"
})
public class RispostaREAStd {

    @XmlElement(nillable = true)
    protected List<AllegatoSUAP> allegati;
    protected String dettaglioEsitoRichiesta;
    protected int esitoRichiesta;
    protected ProtocolloRI protocollo;

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
    public List<AllegatoSUAP> getAllegati() {
        if (allegati == null) {
            allegati = new ArrayList<AllegatoSUAP>();
        }
        return this.allegati;
    }

    /**
     * Gets the value of the dettaglioEsitoRichiesta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDettaglioEsitoRichiesta() {
        return dettaglioEsitoRichiesta;
    }

    /**
     * Sets the value of the dettaglioEsitoRichiesta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDettaglioEsitoRichiesta(String value) {
        this.dettaglioEsitoRichiesta = value;
    }

    /**
     * Gets the value of the esitoRichiesta property.
     * 
     */
    public int getEsitoRichiesta() {
        return esitoRichiesta;
    }

    /**
     * Sets the value of the esitoRichiesta property.
     * 
     */
    public void setEsitoRichiesta(int value) {
        this.esitoRichiesta = value;
    }

    /**
     * Gets the value of the protocollo property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolloRI }
     *     
     */
    public ProtocolloRI getProtocollo() {
        return protocollo;
    }

    /**
     * Sets the value of the protocollo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolloRI }
     *     
     */
    public void setProtocollo(ProtocolloRI value) {
        this.protocollo = value;
    }

}
