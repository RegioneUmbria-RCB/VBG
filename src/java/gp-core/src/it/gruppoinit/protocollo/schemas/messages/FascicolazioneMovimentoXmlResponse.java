
package it.gruppoinit.protocollo.schemas.messages;

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
 *         &lt;element name="FascicolazioneMovimentoXmlResult" type="{http://it.gruppoinit/Protocollazione}DatiFascicoloResponseType" minOccurs="0"/>
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
    "fascicolazioneMovimentoXmlResult"
})
@XmlRootElement(name = "FascicolazioneMovimentoXmlResponse")
public class FascicolazioneMovimentoXmlResponse {

    @XmlElement(name = "FascicolazioneMovimentoXmlResult", nillable = true)
    protected DatiFascicoloResponseType fascicolazioneMovimentoXmlResult;

    /**
     * Gets the value of the fascicolazioneMovimentoXmlResult property.
     * 
     * @return
     *     possible object is
     *     {@link DatiFascicoloResponseType }
     *     
     */
    public DatiFascicoloResponseType getFascicolazioneMovimentoXmlResult() {
        return fascicolazioneMovimentoXmlResult;
    }

    /**
     * Sets the value of the fascicolazioneMovimentoXmlResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiFascicoloResponseType }
     *     
     */
    public void setFascicolazioneMovimentoXmlResult(DatiFascicoloResponseType value) {
        this.fascicolazioneMovimentoXmlResult = value;
    }

}
