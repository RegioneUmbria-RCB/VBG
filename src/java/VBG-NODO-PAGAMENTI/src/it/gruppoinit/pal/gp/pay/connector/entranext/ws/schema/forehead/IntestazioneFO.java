package it.gruppoinit.pal.gp.pay.connector.entranext.ws.schema.forehead;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

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
 *         &lt;element name="TokenAuth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="IdentificativoConnettore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *         &lt;element name="CodiceFiscaleEnte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0" form="unqualified"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;anyAttribute/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tokenAuth", "identificativoConnettore", "codiceFiscaleEnte" })
@XmlRootElement(name = "IntestazioneFO")
public class IntestazioneFO {

    @XmlElement(name = "TokenAuth")
    protected String tokenAuth;
    @XmlElement(name = "IdentificativoConnettore")
    protected String identificativoConnettore;
    @XmlElement(name = "CodiceFiscaleEnte")
    protected String codiceFiscaleEnte;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Recupera il valore della proprietà tokenAuth.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTokenAuth() {

	return tokenAuth;
    }

    /**
     * Imposta il valore della proprietà tokenAuth.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTokenAuth(String value) {

	this.tokenAuth = value;
    }

    /**
     * Recupera il valore della proprietà identificativoConnettore.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getIdentificativoConnettore() {

	return identificativoConnettore;
    }

    /**
     * Imposta il valore della proprietà identificativoConnettore.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setIdentificativoConnettore(String value) {

	this.identificativoConnettore = value;
    }

    /**
     * Recupera il valore della proprietà codiceFiscaleEnte.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCodiceFiscaleEnte() {

	return codiceFiscaleEnte;
    }

    /**
     * Imposta il valore della proprietà codiceFiscaleEnte.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCodiceFiscaleEnte(String value) {

	this.codiceFiscaleEnte = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute by updating the map directly. Because of
     * this design, there's no setter.
     * 
     * 
     * @return always non-null
     */
    public Map<QName, String> getOtherAttributes() {

	return otherAttributes;
    }
}
