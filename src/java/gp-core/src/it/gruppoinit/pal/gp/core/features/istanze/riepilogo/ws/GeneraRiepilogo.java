package it.gruppoinit.pal.gp.core.features.istanze.riepilogo.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="tokenApplicativo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uidPratica" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "tokenApplicativo", "uidPratica" })
@XmlRootElement(name = "GeneraRiepilogo")
public class GeneraRiepilogo {

    protected String tokenApplicativo;
    protected String uidPratica;

    /**
     * Gets the value of the tokenApplicativo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTokenApplicativo() {

	return tokenApplicativo;
    }

    /**
     * Sets the value of the tokenApplicativo property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTokenApplicativo(String value) {

	this.tokenApplicativo = value;
    }

    /**
     * Gets the value of the uidPratica property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getUidPratica() {

	return uidPratica;
    }

    /**
     * Sets the value of the uidPratica property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setUidPratica(String value) {

	this.uidPratica = value;
    }
}
