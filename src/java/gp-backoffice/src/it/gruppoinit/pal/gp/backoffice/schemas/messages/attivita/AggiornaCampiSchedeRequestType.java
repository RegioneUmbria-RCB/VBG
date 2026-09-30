package it.gruppoinit.pal.gp.backoffice.schemas.messages.attivita;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for AggiornaCampiSchedeRequestType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AggiornaCampiSchedeRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="software" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceAttivita" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiceIstanza" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codiceScheda" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AggiornaCampiSchedeRequestType", propOrder = { "token", "software", "codiceAttivita", "codiceIstanza", "codiceScheda" })
public class AggiornaCampiSchedeRequestType {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String software;
    protected int codiceAttivita;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer codiceIstanza;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer codiceScheda;

    /**
     * Gets the value of the token property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getToken() {

	return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setToken(String value) {

	this.token = value;
    }

    /**
     * Gets the value of the software property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSoftware() {

	return software;
    }

    /**
     * Sets the value of the software property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSoftware(String value) {

	this.software = value;
    }

    /**
     * Gets the value of the codiceAttivita property.
     * 
     */
    public int getCodiceAttivita() {

	return codiceAttivita;
    }

    /**
     * Sets the value of the codiceAttivita property.
     * 
     */
    public void setCodiceAttivita(int value) {

	this.codiceAttivita = value;
    }

    /**
     * Gets the value of the codiceIstanza property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    /**
     * Sets the value of the codiceIstanza property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setCodiceIstanza(Integer value) {

	this.codiceIstanza = value;
    }

    /**
     * Gets the value of the codiceScheda property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getCodiceScheda() {

	return codiceScheda;
    }

    /**
     * Sets the value of the codiceScheda property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setCodiceScheda(Integer value) {

	this.codiceScheda = value;
    }
}
