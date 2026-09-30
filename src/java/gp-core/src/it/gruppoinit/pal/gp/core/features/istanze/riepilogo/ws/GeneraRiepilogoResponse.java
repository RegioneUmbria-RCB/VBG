package it.gruppoinit.pal.gp.core.features.istanze.riepilogo.ws;

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
 *         &lt;element name="GeneraRiepilogoResult" type="{http://tempuri.org/}BinaryFile" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "generaRiepilogoResult" })
@XmlRootElement(name = "GeneraRiepilogoResponse")
public class GeneraRiepilogoResponse {

    @XmlElement(name = "GeneraRiepilogoResult")
    protected BinaryFile generaRiepilogoResult;

    /**
     * Gets the value of the generaRiepilogoResult property.
     * 
     * @return possible object is {@link BinaryFile }
     * 
     */
    public BinaryFile getGeneraRiepilogoResult() {

	return generaRiepilogoResult;
    }

    /**
     * Sets the value of the generaRiepilogoResult property.
     * 
     * @param value
     *            allowed object is {@link BinaryFile }
     * 
     */
    public void setGeneraRiepilogoResult(BinaryFile value) {

	this.generaRiepilogoResult = value;
    }
}
