package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.ws.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per anonymous complex type.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="idElaborazione" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "token", "idElaborazione" })
@XmlRootElement(name = "Elabora")
public class Elabora {

    @XmlElement(name = "token", namespace = "http://tempuri.org/")
    protected String token;
    protected Integer idElaborazione;

    /**
     * Recupera il valore della propriet� token.
     * 
     * @return possible object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public String getToken() {

	return token;
    }

    /**
     * Imposta il valore della propriet� token.
     * 
     * @param value
     *            allowed object is {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     */
    public void setToken(String value) {

	this.token = value;
    }

    /**
     * Recupera il valore della propriet� idElaborazione.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getIdElaborazione() {

	return idElaborazione;
    }

    /**
     * Imposta il valore della propriet� idElaborazione.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setIdElaborazione(Integer value) {

	this.idElaborazione = value;
    }
}
