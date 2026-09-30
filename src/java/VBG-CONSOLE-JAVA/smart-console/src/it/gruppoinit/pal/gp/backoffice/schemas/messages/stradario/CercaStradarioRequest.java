package it.gruppoinit.pal.gp.backoffice.schemas.messages.stradario;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceCatastaleComune" type="{http://gruppoinit.it/anagrafici/stradario/types}CodiceCatastaleType"/>
 *         &lt;element name="testoDaCercare">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "token", "codiceCatastaleComune", "testoDaCercare" })
@XmlRootElement(name = "CercaStradarioRequest")
public class CercaStradarioRequest {

    @XmlElement(required = true)
    protected String token;
    @XmlElement(required = true)
    protected String codiceCatastaleComune;
    @XmlElement(required = true)
    protected String testoDaCercare;

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
     * Gets the value of the codiceCatastaleComune property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCodiceCatastaleComune() {

	return codiceCatastaleComune;
    }

    /**
     * Sets the value of the codiceCatastaleComune property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCodiceCatastaleComune(String value) {

	this.codiceCatastaleComune = value;
    }

    /**
     * Gets the value of the testoDaCercare property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTestoDaCercare() {

	return testoDaCercare;
    }

    /**
     * Sets the value of the testoDaCercare property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTestoDaCercare(String value) {

	this.testoDaCercare = value;
    }
}
