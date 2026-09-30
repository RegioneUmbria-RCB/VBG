package it.gruppoinit.pal.gp.pay.connector.plugandpay.ws.client.feed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <pClasse Java per ParametroPosizione complex type.
 * 
 * <pIl seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre
 * &lt;complexType name="ParametroPosizione"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Chiave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Valore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParametroPosizione", propOrder = { "chiave", "valore" })
public class ParametroPosizione {

    @XmlElement(name = "Chiave", required = false)
    protected String chiave;
    @XmlElement(name = "Valore", required = false)
    protected String valore;

    /**
     * Recupera il valore della proprietà chiave.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getChiave() {

	return chiave;
    }

    /**
     * Imposta il valore della proprietà chiave.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setChiave(String value) {

	this.chiave = value;
    }

    /**
     * Recupera il valore della proprietà valore.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public String getValore() {

	return valore;
    }

    /**
     * Imposta il valore della proprietà valore.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code }
     *     
     */
    public void setValore(String value) {

	this.valore = value;
    }
}
