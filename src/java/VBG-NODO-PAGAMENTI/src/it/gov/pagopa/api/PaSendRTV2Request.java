package it.gov.pagopa.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * The `paSendRT` request contains : - `idPA` : alphanumeric field containing the tax code of the structure sending the
 * payment request. - `idBrokerPA` : identification of subject that operates as an intermediary for the PA. -
 * `idStation` : identification of the station of the PA into pagoPa system. - `receipt` : the payment receipt (_see
 * below to details_)
 * 
 * 
 * 
 * <p>
 * Java class for paSendRTReq complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paSendRTReq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idPA" type="{http://pagopa-api.pagopa.gov.it/pa/paForNode.xsd}stText35"/>
 *         &lt;element name="idBrokerPA" type="{http://pagopa-api.pagopa.gov.it/pa/paForNode.xsd}stText35"/>
 *         &lt;element name="idStation" type="{http://pagopa-api.pagopa.gov.it/pa/paForNode.xsd}stText35"/>
 *         &lt;element name="receipt" type="{http://pagopa-api.pagopa.gov.it/pa/paForNode.xsd}ctReceipt"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "paSendRTReq", propOrder = { "idPA", "idBrokerPA", "idStation", "receipt" })
@XmlRootElement(name = "paSendRTV2Request")
public class PaSendRTV2Request {

    @XmlElement(required = true)
    protected String idPA;
    @XmlElement(required = true)
    protected String idBrokerPA;
    @XmlElement(required = true)
    protected String idStation;
    @XmlElement(required = true)
    protected CtReceipt receipt;

    /**
     * Gets the value of the idPA property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIdPA() {

	return idPA;
    }

    /**
     * Sets the value of the idPA property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIdPA(String value) {

	this.idPA = value;
    }

    /**
     * Gets the value of the idBrokerPA property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIdBrokerPA() {

	return idBrokerPA;
    }

    /**
     * Sets the value of the idBrokerPA property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIdBrokerPA(String value) {

	this.idBrokerPA = value;
    }

    /**
     * Gets the value of the idStation property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIdStation() {

	return idStation;
    }

    /**
     * Sets the value of the idStation property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIdStation(String value) {

	this.idStation = value;
    }

    /**
     * Gets the value of the receipt property.
     * 
     * @return possible object is {@link CtReceipt }
     * 
     */
    public CtReceipt getReceipt() {

	return receipt;
    }

    /**
     * Sets the value of the receipt property.
     * 
     * @param value
     *            allowed object is {@link CtReceipt }
     * 
     */
    public void setReceipt(CtReceipt value) {

	this.receipt = value;
    }
}
