package it.gruppoinit.pal.gp.pay.connector.iris.ws.client.generazioneavvisi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per ResponseBase complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ResponseBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Fault" type="{http://idp.tasgroup.it/GenerazioneAvvisi/}FaultType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseBase", propOrder = { "fault" })
@XmlSeeAlso({ GeneraAvvisoResponseType.class, GeneraLottoAvvisiResponseType.class })
public class ResponseBase {

    @XmlElement(name = "Fault")
    protected FaultType fault;

    /**
     * Recupera il valore della proprietà fault.
     * 
     * @return possible object is {@link FaultType }
     * 
     */
    public FaultType getFault() {

	return fault;
    }

    /**
     * Imposta il valore della proprietà fault.
     * 
     * @param value
     *            allowed object is {@link FaultType }
     * 
     */
    public void setFault(FaultType value) {

	this.fault = value;
    }
}
