package it.gruppoinit.pal.gp.pay.connector.pagoumbria.ws.client.generazioneavvisi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per FaultType complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FaultType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FaultCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FaultString" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FaultDescription" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FaultType", propOrder = { "faultCode", "faultString", "faultDescription" })
public class FaultType {

    @XmlElement(name = "FaultCode", required = true)
    protected String faultCode;
    @XmlElement(name = "FaultString", required = true)
    protected String faultString;
    @XmlElement(name = "FaultDescription", required = true)
    protected String faultDescription;

    /**
     * Recupera il valore della proprietà faultCode.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFaultCode() {

	return faultCode;
    }

    /**
     * Imposta il valore della proprietà faultCode.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setFaultCode(String value) {

	this.faultCode = value;
    }

    /**
     * Recupera il valore della proprietà faultString.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFaultString() {

	return faultString;
    }

    /**
     * Imposta il valore della proprietà faultString.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setFaultString(String value) {

	this.faultString = value;
    }

    /**
     * Recupera il valore della proprietà faultDescription.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFaultDescription() {

	return faultDescription;
    }

    /**
     * Imposta il valore della proprietà faultDescription.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setFaultDescription(String value) {

	this.faultDescription = value;
    }
}
